package com.crasoft.academywebsite.security;

import com.crasoft.academywebsite.service.AdminService;
import com.crasoft.academywebsite.service.MentorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AdminService adminService;
    @Autowired
    MentorsService mentorsService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.addAllowedOrigin("*");
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(List.of("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }).and().csrf().disable();
        http.authorizeRequests((requests) -> {
            try {
                requests
                        .antMatchers("/login", "/token/refresh/**").permitAll()
                        .antMatchers("/applications/**").hasRole("ADMIN")
                        .antMatchers("/courses/**").hasRole("ADMIN")
                        .antMatchers("/mentors/**").hasRole("ADMIN")
                        .antMatchers("/admin").denyAll()
                        .antMatchers("/statistics").hasRole("ADMIN")
                        .antMatchers("/students").hasAnyRole("ADMIN", "MENTOR")
                        .and()
                        .addFilter(getAuthenticationFilter());
                http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        http.headers().frameOptions().disable();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(adminService).passwordEncoder(bCryptPasswordEncoder);
        auth.userDetailsService(mentorsService).passwordEncoder(bCryptPasswordEncoder);
    }
    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        return new AuthenticationFilter(adminService, mentorsService, authenticationManager());
    }
}
