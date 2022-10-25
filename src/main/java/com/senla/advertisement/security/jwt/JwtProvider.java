package com.senla.advertisement.security.jwt;

import com.senla.advertisement.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private int expirationTimeInMillis;


    public String createToken(String username, List<Role> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(roles));

        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTimeInMillis);

        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(key).compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.split(" ")[1].trim();
        }
        return null;
    }

    public boolean validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

        if (claims.getBody().getExpiration().before(new Date())) {
            return false;
        }

        return true;
    }

    private List<String> getRoleNames(List<Role> userRoles) {
        return userRoles.stream().map(Role::getName).toList();
    }
}
