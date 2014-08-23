package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.ivy.ant.IvyPublish.PublishArtifact;

import com.google.gson.annotations.Expose;

import flexjson.JSON;
import play.db.jpa.Model;

@Entity
public class Users extends Model{
	public static final int ROLE_PUBLISHER = 1;
	public static final int ROLE_SUBSCRIBER = 2;	
	
	//@Expose(serialize=true)
	public  String username;
	//@Expose(serialize=true)
	public   String password;
	//@Expose(serialize=true)
	public  String name;
	//@Expose(serialize=true)
	public  String phone;
	//@Expose(serialize=true)
	public  int role;

	public String CVLink;
	
	public String email;
	
	//@Expose(serialize=false)
	@OneToMany(mappedBy="subscribedUser", cascade=CascadeType.ALL)
	public  List<Subscription> subscribedAds;
	
	//@Expose(serialize=false)	
	
	@OneToMany(mappedBy="publisher", cascade=CascadeType.ALL)
	public  List<Ads> myAds;
	
	
	@Override
	public String toString() {
	return 	 name;
	}
}
