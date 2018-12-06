package com.homebusiness.prototype.restapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="planets")
public class Planets {
	@Id
	private String id;
	
	private String name;
	private String climate;
	private String land;
	private int countFilms;
	
	/*// Constructors
	public Planets() {}
	  
	  public Planets(String id, String name, String climate, String land, int countFilms) {
	    this.id = id;
	    this.name = name;
	    this.climate = climate;
	    this.land = land;
	    this.countFilms = countFilms;
	  }
	*/
	public int getCountFilms() {
		return countFilms;
	}
	public void setCountFilms(int countFilms) {
		this.countFilms = countFilms;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getClimate() {
		return climate;
	}
	
	public void setClimate(String climate) {
		this.climate = climate;
	}
	
	public String getLand() {
		return land;
	}
	
	public void setLand(String land) {
		this.land = land;
	}
}
