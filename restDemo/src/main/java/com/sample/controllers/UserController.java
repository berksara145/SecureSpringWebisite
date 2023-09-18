package com.sample.controllers;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import com.sample.models.User;
import com.sample.service.UserService;
import javax.ws.rs.core.Response;


@Path("user")
public class UserController {
	
	@Inject
	private UserService service;
	
	@GET
    @Path("getAll")
	@Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
		System.out.println("was at Cont");
        return service.getUsers();
    }
    
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public User addUser(User user) {
		System.out.println("was at Cont add");
        User temp = service.saveUser(user);
        System.out.println(temp.getName());
        return temp;
    }

	@GET
    @Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
		User user1 = service.getUserById(id);
        if(user1 == null) {
        	return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
        	return Response.ok(user1).build();
        }
    }
	
	@GET
    @Path("name")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@QueryParam("name") String name) {
        User user1 = service.getUserByName(name);
        if(user1 == null) {
        	return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
        	return Response.ok(user1).build();
        }
    }
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int sku, User user) {
		if (service.itemExists(sku)) {
			user.setId(sku);
			service.updateUser(user);
	        //status code 201
	         return Response.ok().build();
		} 
		else {
			return Response.noContent().build();
	    }
    }
	//1521  
	//berkvedatabase145.
	//jdbc:mysql://127.0.0.1:1521/user_database
	//root
	
	@DELETE
    @Path("{id}")
    public String deleteUser(@PathParam("id") int id) {
        return service.deleteUser(id);
    }
}