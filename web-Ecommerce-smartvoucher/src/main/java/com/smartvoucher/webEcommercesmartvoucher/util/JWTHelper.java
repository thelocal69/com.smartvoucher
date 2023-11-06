package com.smartvoucher.webEcommercesmartvoucher.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTHelper {

    @Value("${jwt.token.key}")
    private String jwtKey;

    public String generateToken(String data){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
        long expiredTime = System.currentTimeMillis() + 8 * 60 * 60 * 1000;
        Date newExpiredTime = new Date(expiredTime);
        return Jwts.builder()
                .setSubject(data)
                .signWith(key)
                .setExpiration(newExpiredTime)
                .compact();
    }

    public String parserToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token).getBody()
                .getSubject();
    }
}
