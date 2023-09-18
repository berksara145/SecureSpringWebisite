package com.sample.service;

import java.util.List;
import com.sample.models.Role;
import com.sample.repository.RoleRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RoleService {
   
	@Inject
    private RoleRepository repository;

    public Role saveRole(Role Role) {
        return repository.save(Role);
    }   
}