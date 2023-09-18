package com.sample.service;

import java.util.List;
import com.sample.models.User;
import com.sample.repository.UserRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {
	
	@Inject
    private UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> getUsers() {
    	System.out.println("was at serv");
        return repository.getAllUsers();
    }

    public User getUserById(int id) {
        return repository.findById(id);
    }
    
    public User getUserByName(String name) {
        return repository.findByName(name);
    }
    

    public String deleteUser(int id) {
    	if(repository.delete(id)) {
    		return "User removed !! " + id;
    	}
    	else {
    		return "No user like that :(";
    	}
        
    }

    public boolean updateUser(User user) {
       return repository.update(user);
    }
    
    public boolean itemExists(int sku) {
    	return (repository.findById(sku) != null);
    }
}
