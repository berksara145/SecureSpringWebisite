package com.sample.models;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "appuser")
public class User{
	 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;  
	
	@Column
	private String name;
	
	@Column
	private String surname;
	
	@Column
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
	Set<Role> roles = new HashSet<>();
	
    public User() {
        super();
    }
    public User(int i, String name,String surname, String password, HashSet<Role> roles) {
        super();
        this.id = i;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.roles = roles;
        		
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setRole(Role role) {
    	roles.add(role);
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<Role> getroles() {
        return roles;
    }
}
