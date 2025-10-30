package com.ecobazaar.ecobazaar.config;


import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.http.HttpMethod;


import com.ecobazaar.ecobazaar.security.JwtFilter;


@Configuration

@EnableMethodSecurity(prePostEnabled = true) // allows @PreAuthorize on controllers

public class SecurityConfig {


private final JwtFilter jwtFilter;


public SecurityConfig(JwtFilter jwtFilter) {

this.jwtFilter = jwtFilter;

}


@Bean

public SecurityFilterChain filterConfig(HttpSecurity http) throws Exception {


http

// Disable CSRF because we’re building a stateless REST API

.csrf(csrf -> csrf.disable())


// Make session stateless (we use JWT, not HTTP sessions)

.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


// Configure endpoint access rules

.authorizeHttpRequests(auth -> auth


// Public authentication endpoints

.requestMatchers("/api/auth/register", "/api/auth/login").permitAll()


// Allow Swagger & OpenAPI endpoints (for docs)

.requestMatchers(

"/v3/api-docs/**",

"/swagger-ui/**",

"/swagger-ui.html"

).permitAll()


// Public product browsing (GET only)

.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()


// Product management -> SELLER or ADMIN

.requestMatchers("/api/products/**").hasAnyRole("SELLER", "ADMIN")


// Cart / Checkout / Orders -> USER only

.requestMatchers("/api/cart/**", "/api/checkout/**", "/api/orders/**")

.hasRole("USER")


// Admin endpoints -> ADMIN only

.requestMatchers("/api/admin/**").hasRole("ADMIN")


// Everything else requires authentication

.anyRequest().authenticated()

)


// Add our custom JWT filter before Spring’s UsernamePasswordAuthenticationFilter

.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)


// Disable default login forms and browser popups

.formLogin(form -> form.disable())

.httpBasic(basic -> basic.disable());


return http.build();

}


@Bean

public PasswordEncoder passwordEncoder() {

// Strong password hashing

return new BCryptPasswordEncoder();
}
}