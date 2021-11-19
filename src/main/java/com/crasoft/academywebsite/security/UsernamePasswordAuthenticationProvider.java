package com.crasoft.academywebsite.security;

import com.crasoft.academywebsite.documents.Admin;
import com.crasoft.academywebsite.documents.Mentors;
import com.crasoft.academywebsite.repository.AdminRepository;
import com.crasoft.academywebsite.repository.MentorsRepository;
import com.crasoft.academywebsite.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    MentorsRepository mentorsRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Admin admin = adminRepository.findByUsername(username);
        Mentors mentor = mentorsRepository.findByUsername(username);
        if(admin !=null || mentor !=null) {
            if (bCryptPasswordEncoder.matches(password, admin==null?mentor.getEncryptedPassword():admin.getEncryptedPassword())) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(admin == null ? "ROLE_MENTOR" : "ROLE_ADMIN"));
                return new UsernamePasswordAuthenticationToken(username, password, authorities);
            } else {
                throw new BadCredentialsException("Invalid Password!");
            }
        } else{
            throw new BadCredentialsException("No user found with given username!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
