package com.trainee.pack.webstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trainee.pack.service.UserService;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String secretKey = "a very secret secretKey"; // Your secret key for JWT validation
    
    @Autowired
    private UserService userService; // Your user service to fetch user details

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    	super(new AntPathRequestMatcher("/**")); // Use the correct superclass constructor
        setAuthenticationManager(authenticationManager);
    }
    
    public String getSecretKey() {
    	return secretKey;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

    	if ("/login".equals(request.getRequestURI()) || "/afterLogin".equals(request.getRequestURI()) || "/register".equals(request.getRequestURI())) {
    		
            // Skip JWT authentication for the login page
    		return new UsernamePasswordAuthenticationToken(null, null, Collections.emptyList());
        }
    	
    	Cookie[] cookies = request.getCookies();
    	
    	if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    // Found the "jwtToken" cookie, retrieve its value
                    String token = cookie.getValue();
                    
                    try {
                    	System.out.println(token);
                        // Parse the JWT token to access its claims
                        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

                        // Retrieve the username from the claims
                        String username = claims.getSubject();
                        
                        Authentication auth = getLoggedIn();
                        
                        if(auth != null) {
                        	String usernameAuth = auth.getName();
                        	
                        	if(username.equals(usernameAuth)) {
                        		return new UsernamePasswordAuthenticationToken(auth.getName(), null, auth.getAuthorities());
                        	}
                        }
                        else {
                        	
                        	throw new AuthenticationException("JWT authentication failed") {
                            };
                        }
                        
                    } catch (Exception e) {
                    	// Throw an AuthenticationException or another custom exception to indicate failed authentication
                    	e.printStackTrace();
                        throw new AuthenticationException("JWT authentication failed") {
                        };
                        // Token is invalid or expired
                        // You can log the error or handle it as needed
                    }
                }
            }
        }
        // Throw an AuthenticationException or another custom exception to indicate failed authentication
        
        throw new AuthenticationException("JWT authentication failed") {
        };
    }

    // Implement a method to get the username of the logged-in user
    private Authentication getLoggedIn() {
        // You should implement this method based on how you track the logged-in user
        // For example, if you store user details in a Spring Security context, retrieve it from there
        // Or, if you have a custom implementation, fetch it accordingly
        // For demonstration purposes, we'll return a sample username here
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication != null && authentication.isAuthenticated()) {   		
    		return authentication;
    	}
    	else {
    		return null;
    	}
    }
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException {
        // Implement success handling (e.g., generating a JWT token).
    	try {
//    		// Create a SecurityContext
//    	    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//
//    	    // Populate it with the authenticated Authentication object
//    	    securityContext.setAuthentication(authResult);
//
//    	    // Set the SecurityContext in the SecurityContextHolder
//    	    SecurityContextHolder.setContext(securityContext);
			chain.doFilter(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}