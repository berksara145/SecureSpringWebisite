package com.sample.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	@Column
	private String name;
	
	@Column
	private int user_id;
		 
	public Role(String name, int user_id, int id ) {
		this.name = name;
		this.user_id = user_id;
		this.id = id;
	}
    
    public String getName() {
        return name;
    }
    
    public Role() {
        super();
    }
    
    public void setName(String role) {
    	this.name = role;
    }
    
    public int getUser_id() {
    	return user_id;
    }
    
    public void setUser_id(int userId) {
    	this.user_id = userId;
    }
    
    public int getId() {
    	return id;
    }
}