package com.codingdojo.springsecurity.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codingdojo.springsecurity.models.Role;
import com.codingdojo.springsecurity.models.User;
import com.codingdojo.springsecurity.repositories.UserRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    private UserRepository userRepository;
    
    public UserDetailsServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	// loadUserByUserName(String username): Finds the user by their username/email.
        User user = userRepository.findByEmail(email);
        
        // If the username does not exist, the method throws an error.
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // If a user is found, it returns it with the correct authorities.
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
    }
    
    // getAuthorities(User user): returns a list of authorities/permissions for a specific user.
    // For example, our clients can be 'user', 'admin', or both.
    private List<GrantedAuthority> getAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // For Spring Security to implement authorization, we must get the name of the possibles roles for current user from our database
        for(Role role : user.getRoles()) {
        	// and create a new `SimpleGrantedAuthority' object with those roles.
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}