package com.trainee.pack.webstore.config;

import com.trainee.pack.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.authentication.builders.*;    
import org.springframework.security.config.annotation.web.builders.HttpSecurity;    
import org.springframework.security.config.annotation.web.configuration.*;    
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.userdetails.UserDetailsService;    
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;  
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;
  

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {    
    
	private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private CustomAuthenticationProvider authenticationProvider;
    
    @Autowired
    public WebSecurityConfig(UserService userService, PasswordEncoder passwordEncoder, CustomAuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
    }
    
	@Override    
	protected void configure(HttpSecurity http) throws Exception {    
	        
		 http
	      .csrf().disable()
	      .authorizeRequests()
	      .antMatchers("/test").hasRole("ADMIN") // Ensure correct role name
	      .antMatchers("/admin/**").hasRole("ADMIN")
	      .antMatchers("/guest/**").hasAnyRole("GUEST")
	      .antMatchers("/user/**").hasAnyRole("USER")
	      .antMatchers("/login","/register").permitAll()
         .and()
         .authorizeRequests()
	          .anyRequest()
	          .authenticated()
	          .and()
         .formLogin()
	          .loginPage("/login")
	          .defaultSuccessUrl("/afterLogin") 
	          .permitAll()
	          .and()
	      .addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
	}    
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.authenticationProvider(authenticationProvider);
	}
	/*
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }*/
}   