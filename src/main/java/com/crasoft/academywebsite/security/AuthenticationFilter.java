package com.crasoft.academywebsite.security;

import com.crasoft.academywebsite.constants.SecurityConstants;
import com.crasoft.academywebsite.models.LoginRequestModel;
import com.crasoft.academywebsite.service.AdminService;
import com.crasoft.academywebsite.service.MentorsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AdminService adminService;
    private MentorsService mentorsService;

    public AuthenticationFilter(AdminService adminService, MentorsService mentorsService, AuthenticationManager authenticationManager){
        this.adminService = adminService;
        this.mentorsService = mentorsService;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        try{
            LoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
            String username = creds.getUsername();
            String password = creds.getPassword();
            if(adminService.isAdmin(username) || mentorsService.isMentor(username)){
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(!adminService.isAdmin(username) ? "ROLE_MENTOR" : "ROLE_ADMIN"));
                return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username,password, authorities));
            } else {
                throw new BadCredentialsException("Invalid Password!");
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException{
        User user = (User)auth.getPrincipal();
        String accessToken = Jwts.builder()
                .setIssuer("Crasoft Academy").setSubject("JWT Token")
                .claim("username", auth.getName())
                .claim("authorities", populateAuthorities(auth.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_KEY).compact();
        String refreshToken = Jwts.builder()
                .setIssuer("Crasoft Academy").setSubject("JWT Token")
                .claim("username", auth.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 120 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_KEY).compact();

        /*response.setHeader("access_token", accessToken);
        response.setHeader("refresh_header", refreshToken);*/
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection){
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority : collection){
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}