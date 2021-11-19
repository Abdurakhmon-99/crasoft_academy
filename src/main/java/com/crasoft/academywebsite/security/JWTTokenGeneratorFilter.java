package com.crasoft.academywebsite.security;

import com.crasoft.academywebsite.constants.SecurityConstants;
import com.crasoft.academywebsite.documents.Admin;
import com.crasoft.academywebsite.documents.Mentors;
import com.crasoft.academywebsite.service.AdminService;
import com.crasoft.academywebsite.service.MentorsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    @Autowired
    AdminService adminService;
    @Autowired
    MentorsService mentorsService;
    @Autowired
    Environment environment;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(null != auth){
            String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
            if(jwt==null) {
                String token = Jwts.builder()
                        .setIssuer("Crasoft Academy").setSubject("JWT Token")
                        .claim("username", auth.getName())
                        .claim("authorities", populateAuthorities(auth.getAuthorities()))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                        .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_KEY).compact();

                response.setHeader(SecurityConstants.JWT_HEADER, token);
            } else{
                response.setHeader(SecurityConstants.JWT_HEADER, jwt);
            }
        }
        filterChain.doFilter(request,response);
    }
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection){
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority : collection){
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
