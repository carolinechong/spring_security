package com.codingdojo.springsecurity.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.codingdojo.springsecurity.models.User;

@Component
public class UserValidator implements Validator {
	private static final String email_regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;
	private Matcher matcher;
	
	public UserValidator() {
		this.pattern = Pattern.compile(email_regex);
	}
	
	public boolean validateEmail(String email) {
		this.matcher = this.pattern.matcher(email);
		return matcher.matches();
	}
	
    // Specifies that an instance of the User Domain Model can be validated with this custom validator
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    // Creating our custom validation. We can add errors via .rejectValue(String, String).
    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            // First argument is the member variable of our Domain model that we are validating.
        	// The second argument is a code for us to use to set an error message.
            errors.rejectValue("passwordConfirmation", "Match");
        }
        
        if(!validateEmail(user.getEmail())) {
        	errors.rejectValue("email", "Match");
        } 
    }
}