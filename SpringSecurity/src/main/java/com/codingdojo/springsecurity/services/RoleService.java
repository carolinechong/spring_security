package com.codingdojo.springsecurity.services;

import org.springframework.stereotype.Service;

import com.codingdojo.springsecurity.models.Role;
import com.codingdojo.springsecurity.repositories.RoleRepository;

@Service
public class RoleService {
	private RoleRepository roleRepository;
	
	public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}