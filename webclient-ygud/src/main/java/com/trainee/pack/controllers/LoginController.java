package com.trainee.pack.controllers;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.ws.rs.core.UriInfo;

import com.trainee.pack.models.LoginForm;
import com.trainee.pack.models.User;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class LoginController {
	private static final String key = "a very secret secretKey";
	
    @GetMapping("/afterLogin") 
    public ResponseEntity<String> afterLogin(Model model, HttpServletResponse response) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
		String username = authentication.getName();
		System.out.println(authentication.getAuthorities());
		model.addAttribute("greeting", "LOGINED");
        model.addAttribute("tagline", "You are now logined as " + username);
	    System.out.println(username);
	    
	    // Generate the JWT token
        String token = Jwts.builder()
            .setSubject(username)
            .signWith(SignatureAlgorithm.HS512, key)
            .compact();

        // Include the token in the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        
        // Set the JWT token as an HTTP-only cookie
        Cookie cookie = new Cookie("jwtToken", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        
        return new ResponseEntity<>("afterLogin", headers, HttpStatus.OK);
    }
}
/*
private static final String REST_URI = "http://localhost:8085/rest/user";

private Client client = ClientBuilder.newClient();

UriInfo uriInfo;

@Autowired
private KeyGenerator keyGenerator;

public User getJsonUser(int id) {
  	return client
    .target(REST_URI)
    .path(String.valueOf(id))
    .request(MediaType.APPLICATION_JSON)
    .get(User.class);
}
  
private String issueToken(String login) {
	  Key key = keyGenerator.generateKey();
	  
	  
	  String jwtToken = Jwts.builder()
	  .setSubject(login)
	  .setIssuer(uriInfo.getAbsolutePath().toString())
	  .setIssuedAt(new Date())
	  .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
	  .signWith(SignatureAlgorithm.HS512, key)
	  .compact();
	  
	  return jwtToken;
	  }

private Date toDate(LocalDateTime localDateTime) {
	return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
}
public Response getJsonUser(String name, String surname) {
  return client
    .target(REST_URI)
    .path("name/")
    .queryParam("name", name)
    .queryParam("surname", surname)
    .request(MediaType.APPLICATION_JSON)
    .get();
}*/

