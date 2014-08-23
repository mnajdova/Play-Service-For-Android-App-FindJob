package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

import play.db.jpa.Model;

@Entity 
public class Subscription extends Model{

	//@Expose(serialize=true)
	@ManyToOne
	public Users subscribedUser;
	//@Expose(serialize=true)
	@ManyToOne
	public Ads ad;
	//@Expose(serialize=true)
	public String shortDescription;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "objavil "+ subscribedUser.name+ " za oglasot "+ ad.name;
	}
}
