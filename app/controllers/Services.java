package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.h2.engine.User;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonSerializer;
import com.sun.xml.bind.v2.runtime.reflect.ListIterator;

import flexjson.JSONSerializer;
import groovy.json.JsonBuilder;
import models.Ads;
import models.Subscription;
import models.Users;
import play.mvc.Controller;

public class Services extends Controller{
	
	public static final Gson gson;
	
	static{
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		gson = builder.create();	
	}	
	
	public static void insertUser(Users user){
		user.save();
	}
	
	public static void getAllUsers(){
		// JSONSerializer serializer = new JSONSerializer().prettyPrint(true);
		 JSONSerializer serializer = new JSONSerializer();
		 
		
		 List<Users> users=Users.findAll();
		
		 
		 String flexJsonString = serializer.include("id", "username").exclude("*").serialize(users);
		
	//	renderJSON(gson.toJson();
		//renderJSON(Users.findAll());s
		
		 
		 JSONSerializer ser = new JSONSerializer();
			String res = ser.exclude("*").serialize(users);
		 
		 renderJSON(res);
	}
	
	public static void getAdsFromPublisher(Users user){
		//System.out.println(publisher);
		
		//renderJSON(gson.toJson(publisher.myAds));
		//renderJSON(publisher.getmyAds());
		
		JSONSerializer serializer = new JSONSerializer(); 
		//List<Ads> ads= new ArrayList<Ads>();
		
		Users u=Users.findById(user.id);
		 
		 String flexJsonString = serializer.exclude("*").serialize(u.myAds);
		 renderJSON(flexJsonString);
		
	}
	
	public static void getAdSubscribers(Ads ad){
		
		//Ads a=Ads.findById(ad.id);
		
		JSONSerializer serializer = new JSONSerializer(); 
		
		 
		String flexJsonString = serializer.include("subscribedUser.name", "description").exclude("*").serialize(ad.subscribers);
		
		renderJSON(flexJsonString);
		
	}
		
	public static void getAllAds(){
		
		 JSONSerializer serializer = new JSONSerializer();
		 
			
		 List<Ads> ads=Ads.findAll();
		
		 
		 String flexJsonString = serializer.exclude("*").serialize(ads);
		 
		 renderJSON(flexJsonString);
		
		
	}
	
	public static void adsNotSubByUser(Users user){
		
		 List<Ads> allAds=Ads.findAll();
		 List<Subscription> allSubcribtion= Subscription.findAll();
		 Users u=Users.findById(user.id);
		 Long id_user=u.id;
		 List<Ads> subAdsByUser= new ArrayList<Ads>();
		 List<Ads> result= new ArrayList<Ads>();
		 
		for(Subscription sub : allSubcribtion){
			if(sub.subscribedUser.id==id_user)
				subAdsByUser.add(sub.ad);
		}
		
		for(Ads a: allAds){
			if(!subAdsByUser.contains(a))
				result.add(a);
		}
		 
		
		
		 JSONSerializer serializer = new JSONSerializer();
		 
		 String flexJsonString = serializer.exclude("*").serialize(result);
		 
		 renderJSON(flexJsonString);
		
		
	}
	
	
	public static void checkLogin(Users user){
		
		 List<Users> users=Users.findAll();
		if(user!=null && user.username!=null && user.password!=null)
		{
			for (Users u : users) {
				if(u.password.compareTo(user.password)==0 && u.username.compareTo(user.username)==0){
					 JSONSerializer serializer = new JSONSerializer();					 
					 String flexJsonString = serializer.exclude("*").serialize(u);					 
					 renderJSON(flexJsonString);
					break;
				}
			}
		}
		else
			renderJSON("");
	}
	
	
	public static void createSubcription(String user_id, String ad_id, String description){
		
		Long id_user=Long.parseLong(user_id);
		Long id_ad=Long.parseLong(ad_id);
		
		Users user=Users.findById(id_user);
		Ads ad=Ads.findById(id_ad);
		
		Subscription subscription= new Subscription();
		subscription.ad=ad;
		subscription.subscribedUser=user;
		subscription.shortDescription=description;
		
		subscription.save();
		
		JSONSerializer serializer = new JSONSerializer();					 
		 String flexJsonString = serializer.exclude("*").serialize(subscription);					 
		 renderJSON(flexJsonString);
		
		
	}
	
	public static void createUser(String name, String number, String username, String password, String role,String cvUrl, String email){
		
		Users user= new Users();
		
		
		user.name=name;
		user.password=password;
		user.username=username;
		user.phone=number;
		user.CVLink=cvUrl;
		user.role=Integer.parseInt(role);
		user.email = email;
		
		List<Users> users=Users.findAll();
		 int rolee=-1;;
		 String id="0";
		 for (Users u : users) {
			if(u.username.compareTo(username)==0){
					rolee=u.role;			
				
			}
		}
		
		 if(rolee==-1){
			 user.save();
			 renderJSON(user);
		}
		 else{
			 renderJSON("");
		 }
			
		
		
		
		
	}
	
	public static void createAd(String name, String description, String user_id, String location, String dateFrom, String dateTo) throws ParseException{

		Long id_user=Long.parseLong(user_id);
		Users user = Users.findById(id_user);
		
		Ads ad= new Ads();
		ad.publisher=user;
		ad.name=name;
		ad.description=description;
		ad.Location=location;
	
		Date dateF = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH).parse(dateFrom);
		Date dateT = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH).parse(dateTo);
		
		ad.dateFrom=dateF;
		ad.dateTo= dateT;		
		
		ad.save();
		
		JSONSerializer serializer = new JSONSerializer();		 
		String flexJsonString = serializer.exclude("*").serialize(ad);	
		System.out.println(flexJsonString);
		renderJSON(flexJsonString);
		
		
	}
	
	public static void deleteAd(String adId){
		
		Long id=Long.parseLong(adId);
		Ads ad=Ads.findById(id);
	
		ad.delete();
		renderJSON("[{'ok':'1'}]");
	
	}
	
	public static void subscribedAdsByUser(Users user){

		 List<Ads> allAds=Ads.findAll();
		 List<Subscription> allSubcribtion= Subscription.findAll();
		 Users u=Users.findById(user.id);
		 Long id_user=u.id;
		 List<Ads> subAdsByUser= new ArrayList<Ads>();
		 List<Ads> result= new ArrayList<Ads>();
		 
		for(Subscription sub : allSubcribtion){
			if(sub.subscribedUser.id==id_user)
				subAdsByUser.add(sub.ad);
		}
		
		
		 
		
		
		 JSONSerializer serializer = new JSONSerializer();
		 
		 String flexJsonString = serializer.exclude("*").serialize(subAdsByUser);
		 
		 renderJSON(flexJsonString);
		
		
	}
	
	
	
	public static void getSearchedAds(Users user, String keyword, String city){
		
		 List<Ads> allAds=Ads.findAll();
		 List<Subscription> allSubcribtion= Subscription.findAll();
		 Users u=Users.findById(user.id);
		 Long id_user=u.id;
		 List<Ads> subAdsByUser= new ArrayList<Ads>();
		 List<Ads> result= new ArrayList<Ads>();
		 
		for(Subscription sub : allSubcribtion){
			if(sub.subscribedUser.id==id_user)
				subAdsByUser.add(sub.ad);
		}
		
		for(Ads a: allAds){
			if(!subAdsByUser.contains(a))
			{
				String descriptionLowerCase = a.description==null ? "" : a.description.toLowerCase();
				String keywordLowerCase = keyword==null ? "" : keyword.toLowerCase();
				String cityLowerCase = city==null ? "" : city.toLowerCase();
				String adLocationLowerCase= a.Location==null ? "" : a.Location.toLowerCase();
				if((descriptionLowerCase.contains(keywordLowerCase) && cityLowerCase.equals("")) || (descriptionLowerCase.contains(keywordLowerCase) && adLocationLowerCase.equalsIgnoreCase(cityLowerCase)))	
					result.add(a);
			}
		}
		 
		
		
		 JSONSerializer serializer = new JSONSerializer();
		 
		 String flexJsonString = serializer.exclude("*").serialize(result);
		 
		 renderJSON(flexJsonString);
		
		
	}
	
	
}
