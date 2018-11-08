package com.codingdojo.springsecurity.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codingdojo.springsecurity.models.Role;
import com.codingdojo.springsecurity.models.User;
import com.codingdojo.springsecurity.repositories.RoleRepository;
import com.codingdojo.springsecurity.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // Saves a client with only the user role.
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);
    }
     
    // Saves a client with only the admin role.
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
        userRepository.save(user);
    }    
    
    // Finds a user by their username/email.
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // UsersCtrl /home
    public void updateUser(User user){ 
		user.setUpdatedAt(new Date());
		userRepository.save(user);
    }
    
    // UsersCtrl /home
    public boolean checkIfAdmin(User user) {
		List<Role> roles = user.getRoles();
		for(int i = 0; i < roles.size(); i++) {
			if(roles.get(i).getName().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
    }

}