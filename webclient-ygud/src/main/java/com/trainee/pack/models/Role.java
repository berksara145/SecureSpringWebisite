package com.trainee.pack.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")

public class Role implements GrantedAuthority  {
	
	@Column
    private int id;
	
	@Column
	private String name;
	
	@Column
	private int user_id;
		 
	public Role(int id, String name, int user_id) {
		this.name = name;
		this.user_id = user_id;
		this.id = id;
	}
    
    public String getName() {
        return name;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public int getId() {
    	return id;
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

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return name;
	}
}