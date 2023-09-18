package com.trainee.pack.service;

import java.io.IOException;
import java.util.HashSet;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainee.pack.models.Role;
import com.trainee.pack.models.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service("userDetailsService")
public class UserService implements UserDetailsService {
	
	
	private static final String REST_URI = "http://localhost:8085/rest/user";
	private static final String SALT = "helloo";
	@Autowired
	private PasswordEncoder passwordEncoder;
	 
	public UserService(){
		
	}
 
  private Client client = ClientBuilder.newClient();

  public User getJsonUser(int id) {
      	return client
        .target(REST_URI)
        .path(String.valueOf(id))
        .request(MediaType.APPLICATION_JSON)
        .get(User.class);
  }
	  
	  
	public User getJsonUser(String id) {
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(REST_URI + "/name")
    	        .queryParam("name", id)
    	        .build().toString();
    	
    	HttpClient httpClient = HttpClients.createDefault();
    	HttpGet httpGet = new HttpGet(urlTemplate);
    	
		try {
			HttpResponse response3 = httpClient.execute(httpGet);
			String jsonResponse = EntityUtils.toString(response3.getEntity());
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	return objectMapper.readValue(jsonResponse, User.class);
	    	
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	/*
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role("user"));
        User user = new User(1, "berko123","sara123" ,hashedPassword, roles);
        */

    	String hashedPassword = passwordEncoder.encode("pass123");
    	System.out.println(hashedPassword);
    	User user = getJsonUser(username);
    	
		if(user == null) {
			throw new UsernameNotFoundException("Kullanıcı Bulunamadı");
		}
		else{
			System.out.println(user.getAuthorities());
			System.out.println(user);
			user.getAuthorities().stream().forEach((i) -> {
				  System.out.println(i.getAuthority());
			});
			return user;
		}
		
    }
    // a post method that returns the id
    public User postJsonUser(User user) {
        Client client = ClientBuilder.newClient(); // You need to initialize the JAX-RS client.

        Invocation.Builder builder = client
                .target(REST_URI + "/add")
                .request(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON);

        // Use Entity to set the JSON object as the request entity.
        Response response = builder.post(Entity.entity(user, MediaType.APPLICATION_JSON));

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // Assuming your service returns the updated User.
            return response.readEntity(User.class);
        } else {
            // Handle errors here, e.g., throw an exception or return null.
            return null;
        }
    }
    
    public String returnedHashed(String password) {
    	return passwordEncoder.encode(password + SALT);
    }
    
    public String getSalt() {
    	return SALT;
    }
}