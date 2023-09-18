package com.trainee.pack.webstore.config;

import com.trainee.pack.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        if (authentication != null) {
    		if(authentication.isAuthenticated()) {
    			
    			System.out.println(authentication.getAuthorities());
    			
        	    System.out.println(username);
    		}
    	}
        
        UserDetails userDetails = userService.loadUserByUsername(username);
        
        System.out.println(password + userService.getSalt());
        System.out.println(userDetails.getPassword());
        System.out.println(passwordEncoder.matches(password + userService.getSalt(), userDetails.getPassword()));
        
        
        if (!passwordEncoder.matches(password + userService.getSalt(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        } 
        /*
        if ( !password.equals(userDetails.getPassword()) ) {
            throw new BadCredentialsException("Invalid credentials");
        } */

        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

/*
@PostMapping("/login") 
public String handleFormSubmission(@Valid LoginForm loginForm, BindingResult result, Model model) {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication != null) {
		if(authentication.isAuthenticated()) {
			String username = authentication.getName();
    	    System.out.println(username);
		}
	}
	
	if (result.hasErrors()) {
		model.addAttribute("greeting", "There was an Error");
        model.addAttribute("tagline", "The error");
        model.addAttribute("loginForm", loginForm);
        return "loginPage";
    }
	else {
		Response response = getJsonUser(loginForm.getFirstName(), loginForm.getLastName());
		if(response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
			model.addAttribute("greeting", "There was an Error");
			model.addAttribute("tagline", "There is no user like that");
			model.addAttribute("loginForm", new LoginForm());
    		return "loginPage"; 
		}
		else if(response.getStatus() == Response.Status.OK.getStatusCode()) {
			model.addAttribute("greeting", "Welcome");
            model.addAttribute("loginMessage", "You are now logined " + loginForm.getFirstName() + " " + loginForm.getLastName());
    		return "afterLogin"; 
		}
		else {
			model.addAttribute("greeting", "There was an Error");
			model.addAttribute("tagline", "Error is unknown");
			model.addAttribute("loginForm", new LoginForm());
    		return "loginPage"; 
		}
	}
}*/