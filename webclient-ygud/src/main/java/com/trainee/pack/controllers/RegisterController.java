package com.trainee.pack.controllers;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.ws.rs.core.UriInfo;

import com.trainee.pack.models.LoginForm;
import com.trainee.pack.models.Role;
import com.trainee.pack.models.User;
import com.trainee.pack.service.RoleService;
import com.trainee.pack.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
    @PostMapping("/register") 
    @Consumes(MediaType.APPLICATION_JSON)
    public String register(Model model, @RequestParam("username") String username,
            @RequestParam("surname") String surname,
            @RequestParam("role") String role,
            @RequestParam("password") String password) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
		System.out.println(authentication.getAuthorities());
    	
    	//first look if  a name like that already exists
    	User user = userService.getJsonUser(username);
    	if(user != null) {
    		model.addAttribute("errorMessage", "this name already exists select something else");
    		return "register";
    	}
    	
    	
    	user = new User();
    	user.setName(username);
    	user.setSurname(surname);
    	user.setPassword(userService.returnedHashed(password));
    	
    	
    	//if not then register
    	//first create user and get id
    	//then use that id to post a role with the role name and id returned
    	user = userService.postJsonUser(user);
    	if(user ==  null) {
    		model.addAttribute("errorMessage", "couldn't create the user");
    		return "register";
    	}
    	Role userRole = new Role(0,role, user.getId());
    	userRole = roleService.postJsonRole(userRole);
    	
    	if(userRole ==  null) {
    		model.addAttribute("errorMessage", "couldn't create the role");
    		return "register";
    	}
    	
    	
    	return "afterRegister";
    }
	
}