package com.crasoft.academywebsite.security;

import com.crasoft.academywebsite.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/login") || request.getServletPath().equals("/token/refresh")){
            filterChain.doFilter(request,response);
        } else {
            String authorizationHeader = request.getHeader(SecurityConstants.JWT_HEADER);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                String token = authorizationHeader.substring("Bearer ".length());
                try {
                    Claims claims = Jwts.parser()
                            .setSigningKey(SecurityConstants.JWT_KEY)
                            .parseClaimsJws(token)
                            .getBody();
                    String username = String.valueOf(claims.get("username"));

                    String authorities = (String) claims.get("authorities");

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,
                            AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }catch (Exception e) {
                    throw new BadCredentialsException("Invalid Token received!");
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
