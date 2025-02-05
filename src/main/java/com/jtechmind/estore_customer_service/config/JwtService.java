package com.jtechmind.estore_customer_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    // Replace this with a secure secret key (at least 256 bits)
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    // Token expiration time (24 hours)
    private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds

    /**
     * Generates a JWT token for the given UserDetails.
     *
     * @param userDetails The UserDetails object containing user information.
     * @return A JWT token as a String.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Creates a JWT token with the specified claims and subject.
     *
     * @param claims  The claims to include in the token.
     * @param subject The subject (usually the username) of the token.
     * @return A JWT token as a String.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Add custom claims (if any)
                .setSubject(subject) // Set the subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issue time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set the expiration time
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Sign the token with the secret key
                .compact(); // Build the token
    }

    /**
     * Validates the JWT token against the provided UserDetails.
     *
     * @param token       The JWT token to validate.
     * @param userDetails The UserDetails object to compare with the token.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token The JWT token.
     * @return The username as a String.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token          The JWT token.
     * @param claimsResolver A function to resolve the desired claim.
     * @return The extracted claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token.
     * @return A Claims object containing all claims.
     */
    private Claims extractAllClaims(String token) {
               return Jwts.parser()
                       .verifyWith((SecretKey) getSigningKey())
                       .build()
                       .parseSignedClaims(token)
                       .getPayload();
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token The JWT token.
     * @return True if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date as a Date object.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generates the signing key from the secret key.
     *
     * @return A Key object for signing the JWT.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decode the secret key
        return Keys.hmacShaKeyFor(keyBytes); // Create a signing key
    }
}

