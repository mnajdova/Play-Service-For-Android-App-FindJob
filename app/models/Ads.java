package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.google.gson.annotations.Expose;

import play.db.jpa.Model;

@Entity
public class Ads extends Model{
	
	
	//@Expose(serialize=true)
	public  String name;
	//@Expose(serialize=true)
	public  String description;
	//@Expose(serialize=true)
	public  Date dateFrom;
	//@Expose(serialize=true)
	public  Date dateTo;
	
	public String Location;
	
	//@Expose(serialize=true)
	@ManyToOne
	public  Users publisher;
	
	
	//@Expose(serialize=true)
	@OneToMany(mappedBy="ad", cascade=CascadeType.ALL)
	public  List<Subscription> subscribers;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " ";
	}
	
	
}
