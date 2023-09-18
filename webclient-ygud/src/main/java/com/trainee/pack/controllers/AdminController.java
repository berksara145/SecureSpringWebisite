package com.trainee.pack.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import com.trainee.pack.models.LoginForm;
import com.trainee.pack.models.User;
import com.trainee.pack.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.KeyGenerator;

@RequestMapping("/admin")
@Controller
class AdminController{
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/greeting")
    public String greeting(Model model) {
		
	        model.addAttribute("greeting", "Welcome to Wadmin page");
	        model.addAttribute("tagline", "you are in admin page");
	        return "welcome";
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/info")
    public String info(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	 
    	String username = authentication.getName();
        System.out.println(username);
    	
        User user = userService.getJsonUser(username);
    	
        model.addAttribute("name", "Name : " + user.getName());
        model.addAttribute("surname", "Surname : " + user.getSurname());
        model.addAttribute("id", "" + user.getId());
        model.addAttribute("password", "password : " + user.getPassword());
        return "adminInfo";
       
    }
		
}