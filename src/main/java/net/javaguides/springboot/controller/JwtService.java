package net.javaguides.springboot.controller;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
    private String secret = "my-super-secret-key-which-is-very-long-32chars";

    @Value("${jwt.expiration}")
    private long expirationMillis;
    
    
	
	private Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	@PostConstruct
    public void init() {
        // Stable key derived from config, not regenerated randomly
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        logger.info("JWT Service initialized with stable secret key");
    }
	
	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
	
	@Autowired
    private UserDetailsService userDetailsService;
	
    private static final String SECRET = "my-super-secret-key-which-is-very-long-32chars"; // use env variable
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
    
    public Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    
 // ✅ Provide loadUserByUsername
    public UserDetails loadUserByUsername(String username) {
        return userDetailsService.loadUserByUsername(username);
    }
    
    // ✅ Validate token
    public boolean validateToken(String token) {
        try {
        	System.out.println("JWT Service");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims.getExpiration().after(new Date()));

            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
        	// Log the exception with stack trace
            logger.error("JWT validation failed: {}", e.getMessage(), e);
            return false;
        }
    }
    
}

