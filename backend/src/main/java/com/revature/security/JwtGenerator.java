package com.revature.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


@Component
public class JwtGenerator {

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // check User obj
    public boolean isValid(String token) {
        String username = extractUsername(token);
        return (username != null && !isTokenExpired(token));
    }

//    public boolean isValid(String token) {
//        String username = extractUsername(token);
//        return (username.equals(!isTokenExpired(token)));
//    }

//    public boolean isValid(String token) {
//        String username = extractUsername(token);
////        return (username.equals(!isTokenExpired(token)));
//        return (true);
//    }

//    public boolean isValid(String token, UserDetails user) {
//        String username = extractUsername(token);
//        return (username.equals(user.getUsername())) && !isTokenExpired(token);
//    }

//    public boolean isValid(String token) {
//        try {
//            Jwts.parser().setSigningKey(getSigninKey()).parse
//        }
//    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken (Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
//                .signWith(getSigninKey())
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public Claims extractAllClaims (String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(SecurityConstants.JWT_SECRET)
//                .parse
        return Jwts
                .parser()
//                .verifyWith(getSigninKey())
                .setSigningKey(getSigninKey())
                .build()
//                .parseSignedClaims(token)
                .parseClaimsJws(token)
//                .getPayload();
                .getBody();
    }

    // to use secret
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SecurityConstants.JWT_SECRET);
//        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
