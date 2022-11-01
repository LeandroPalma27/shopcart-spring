package com.leancoder.shopcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Clase de configuracion de spring security en el proyecto.
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Por el momento se permite usar la pagina a cualquiera, sin tener que registrarse.
        http.authorizeRequests().antMatchers("/chocloshop/**").permitAll();
    }
    
}
