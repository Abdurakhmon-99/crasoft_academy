package com.crasoft.academywebsite.controller;

import com.crasoft.academywebsite.constants.SecurityConstants;
import com.crasoft.academywebsite.documents.Admin;
import com.crasoft.academywebsite.documents.Mentors;
import com.crasoft.academywebsite.models.ApplicantFormsResponseModel;
import com.crasoft.academywebsite.models.DashboardStatisticsResponseModel;
import com.crasoft.academywebsite.models.LoginRequestModel;
import com.crasoft.academywebsite.repository.AdminRepository;
import com.crasoft.academywebsite.repository.MentorsRepository;
import com.crasoft.academywebsite.service.AdminService;
import com.crasoft.academywebsite.service.ApplicantFormsService;
import com.crasoft.academywebsite.service.MentorsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "Documentation of the Courses of the IT academy", tags = {"Admin"})
@RestController
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    MentorsService mentorsService;
    @Autowired
    ApplicantFormsService applicantFormsService;

    @PostMapping("/admin")
    public Admin createAdmin(@RequestBody LoginRequestModel adminDetails){
        return adminService.createAdmin(adminDetails);
    }
    @GetMapping("/statistics")
    public ResponseEntity<DashboardStatisticsResponseModel> getApplicationStatistics(){
        DashboardStatisticsResponseModel model = applicantFormsService.getStatistics();
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        String authorizationHeader = request.getHeader(SecurityConstants.JWT_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConstants.JWT_KEY)
                        .parseClaimsJws(refreshToken)
                        .getBody();
                String username = String.valueOf(claims.get("username"));
                if(adminService.isAdmin(username) || mentorsService.isMentor(username)){
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(!adminService.isAdmin(username) ? "ROLE_MENTOR" : "ROLE_ADMIN"));
                    String accessToken = Jwts.builder()
                            .setIssuer("Crasoft Academy").setSubject("JWT Token")
                            .claim("username", username)
                            .claim("authorities", populateAuthorities(authorities))
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                            .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_KEY).compact();

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", accessToken);
                    tokens.put("refresh_token", refreshToken);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }

            }catch (Exception e) {
                throw new BadCredentialsException("Invalid Token received!");
            }
        } else{
            throw new RuntimeException("Valid refresh token is missing!");
        }

    }
    @ApiOperation("login")
    @PostMapping("/login")
    public void fakeLogin(@RequestBody LoginRequestModel model) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
    private String populateAuthorities(Collection<? extends GrantedAuthority> collection){
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority : collection){
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
