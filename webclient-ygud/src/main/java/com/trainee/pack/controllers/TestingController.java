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
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.KeyGenerator;
import javax.inject.Inject;

@Controller
public class TestingController {
	
	@Autowired
	private UserService userService;
	
	@Context
	UriInfo uriInfo;
	
	@RequestMapping("/hadi")
    public String hadi(Model model) {
		
	        return "welcome";
    }
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping("/testUser")
    public String testUser(Model model) {
			
			//User user1 = getJsonUser(3);
			
	        model.addAttribute("greeting", "Welcome to Web Store!");
	        model.addAttribute("tagline", "testUser");
	        return "welcome";
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/test")
    public String test(Model model) {
		
//			User user1 = getJsonUser(2);
			
	        model.addAttribute("greeting", "Welcome to Web Store!");
	        model.addAttribute("tagline", "oldu test");
	        return "welcome";
    }
	  
	  @RequestMapping("/test2")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
      public String test2(Model model) {
		
			User user1 = userService.getJsonUser(2);
			
	        model.addAttribute("greeting", "Welcome to Web Store!");
	        model.addAttribute("tagline", user1.getName() + " " + user1.getSurname());
	        return "welcome";
	   }
	  
	  
	  
	  @GetMapping("/echo")
	  @Produces(MediaType.TEXT_PLAIN)
	  public Response echo(@QueryParam("message") String message) {
		  return Response.ok().entity(message == null ? "no message" : message).build();
	  }
	  
	  @GetMapping("/echo/jwt")
	  public Response echoWithJWTToken(@QueryParam("message") String message) {
		  return Response.ok().entity(message == null ? "no message" : message).build();
	  }
	  
	  @Autowired
	  private KeyGenerator keyGenerator;
	  
	  
	  @PostMapping("/loginTest")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response authenticateUser(LoginForm loginForm) {
		  try {
		
			  // Issue a token for the user
			  String token = issueToken(loginForm.getFirstName());
		
			  // Return the token on the response
			  return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
	
		  } catch (Exception e) {
			  return Response.status(Response.Status.UNAUTHORIZED).build();
		  }
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
		
}

