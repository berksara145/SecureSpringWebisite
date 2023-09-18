package com.trainee.pack.webstore.config;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KeyGeneratorConfig {

    @Bean
    public KeyGenerator keyGenerator() {
    	try {
            return KeyGenerator.getInstance("HmacSHA512");
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception here, e.g., log an error or throw a custom runtime exception
            throw new RuntimeException("Error initializing KeyGenerator", e);
        }
    }
}