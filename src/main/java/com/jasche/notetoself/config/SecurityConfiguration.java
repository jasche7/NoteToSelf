package com.jasche.notetoself.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Security configuration class for application using OAuth2 for authentication.
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Requires http requests to be authenticated through OAuth2, after which a CSRF token
     * will be persisted as a cookie. Allows unauthorized access for the React frontend and
     * for the homepage and user login route.
     * @param http  security configuration for http requests
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .oauth2Login().and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers("/**/*.{js,html,css}").permitAll()
                .antMatchers("/", "/api/user").permitAll()
                .anyRequest().authenticated();
    }
}