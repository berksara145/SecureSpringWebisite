package com.trainee.pack.controllers;

import com.trainee.pack.models.LoginForm;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("greeting", "Welcome to Web Store!");
        model.addAttribute("tagline", "The one and only amazing web store");
        return "welcome";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication != null) {
    		if(authentication.isAuthenticated()) {
    			String username = authentication.getName();
        	    System.out.println(username);
    		}
    	}
    	
        return "loginPage";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication != null) {
    		if(authentication.isAuthenticated()) {
    			String username = authentication.getName();
        	    System.out.println(username);
    		}
    	}
    	
        return "register";
    }
}