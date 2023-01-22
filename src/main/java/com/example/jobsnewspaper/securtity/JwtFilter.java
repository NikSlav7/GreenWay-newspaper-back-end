package com.example.jobsnewspaper.securtity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class JwtFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    public JwtFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        authToken = authToken.split("\\s+")[1];

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(AuthFilter.SIGNING_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(authToken);

        Claims body = claimsJws.getBody();
        var map = (List<Map<String, String>>) body.get("authorities");
        List<SimpleGrantedAuthority> authorities = map.stream().map(auth -> new SimpleGrantedAuthority(auth.get("authority"))).collect(Collectors.toList());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(body.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
