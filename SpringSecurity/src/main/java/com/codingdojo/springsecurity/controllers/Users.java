package com.codingdojo.springsecurity.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.springsecurity.models.User;
import com.codingdojo.springsecurity.services.RoleService;
import com.codingdojo.springsecurity.services.UserService;
import com.codingdojo.springsecurity.validator.UserValidator;

@Controller
public class Users {
    private UserService userService;
    private UserValidator userValidator;
    private RoleService roleService;
    
    public Users(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }
    
    // Set a couple of optional request parameters, check for their existence, and add messages accordingly. Views to display msgs.
    @RequestMapping(value = {"/login", "/registration"})
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMsg", "Invalid credentials. Please try again.");
        }
        
        if(logout != null) {
            model.addAttribute("logoutMsg", "You've successfully logged out.");
        }
        
        model.addAttribute("user", new User());
        return "loginReg.jsp";
    }
    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
    	userValidator.validate(user, result);
    	//System.out.println("hit 1");
        
    	if(result.hasErrors()) {
    		//System.out.println("hit 2");
			return "loginReg.jsp";
		
			// create first user role to be admin
		} else if(roleService.findByName("ROLE_ADMIN").getUsers().size() < 1) {
			userService.saveUserWithAdminRole(user);
			session.setAttribute("createdMsg", "Your admin account has been successfully created. Please proceed to login.");
			//System.out.println("created admin");
			return "redirect:/login";
			
		} else {
			// create all other users (after admin=first user) to have role of user
			userService.saveWithUserRole(user);
			session.setAttribute("createdMsg", "Your user account has been successfully created. Please proceed to login.");
			//System.out.println("created user");
			return "redirect:/login";
		}
    }
    
    @RequestMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
    	// After a successful authentication, we are able to get the name of our principal (current user) via the .getName() method. 
        String email = principal.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("currentUser", userService.findByEmail(email));
        userService.updateUser(user);
        
        if(userService.checkIfAdmin(user)) {
    		return "redirect:/admin";
	    } else {
	    	return "homePage.jsp";
	    }
    }
    
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        return "adminPage.jsp";
    }
    
}