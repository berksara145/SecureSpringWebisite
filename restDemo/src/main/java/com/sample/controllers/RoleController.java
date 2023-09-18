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

import com.sample.models.Role;
import com.sample.models.User;
import com.sample.service.RoleService;
import com.sample.service.UserService;
import javax.ws.rs.core.Response;


@Path("role")
public class RoleController {
	
	@Inject
	private RoleService service;
	
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Role addUser(Role role) {
		System.out.println("was at Cont add");
        Role temp = service.saveRole(role);
        System.out.println(temp.getName());
        return temp;
    }

}