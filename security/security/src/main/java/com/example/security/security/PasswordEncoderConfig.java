package com.example.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
public class PasswordEncoder {

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
