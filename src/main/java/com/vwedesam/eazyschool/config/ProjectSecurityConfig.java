package com.vwedesam.eazyschool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
//                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/home").authenticated()
//                .mvcMatchers("/").permitAll()
                .mvcMatchers ("/holidays/**").permitAll()
                .mvcMatchers("/contact").permitAll()
                .mvcMatchers("/saveMsg").permitAll()
                .mvcMatchers(HttpMethod.GET, "/saveMsg").denyAll()
                .mvcMatchers(" /courses").permitAll()
                .mvcMatchers("/about").permitAll()
                .and().formLogin()
                .and().httpBasic();

    }

}
