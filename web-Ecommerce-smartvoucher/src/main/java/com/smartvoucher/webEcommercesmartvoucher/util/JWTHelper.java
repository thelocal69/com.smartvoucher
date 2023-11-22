package com.smartvoucher.webEcommercesmartvoucher.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JWTHelper {

    @Value("${jwt.token.key}")
    private String jwtKey;
    @Value("${access.token.expired}")
    private Long accessTokenExpired;
    @Value("${refresh.token.expired}")
    private Long refreshTokenExpired;

    private SecretKey getKeys(){
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtKey));
    }

    public String generateToken(String data){
        long expiredTime = System.currentTimeMillis() + accessTokenExpired;
        Date newExpiredTime = new Date(expiredTime);
        return Jwts.builder()
                .setSubject(data)
                .setIssuer("com.smartvoucher")
                .signWith(getKeys())
                .setExpiration(newExpiredTime)
                .compact();
    }

    public String generateRefreshToken(String data){
        long expiredTime = System.currentTimeMillis() + refreshTokenExpired;
        Date newExpiredTime = new Date(expiredTime);
        return Jwts.builder()
                .setSubject(data)
                .signWith(getKeys())
                .setExpiration(newExpiredTime)
                .compact();
    }

    public String createdGoogleToken(String data){
        long expiredTime = System.currentTimeMillis() + accessTokenExpired;
        Date newExpiredTime = new Date(expiredTime);
        return Jwts.builder()
                .setSubject(data)
                .setIssuedAt(new Date())
                .setExpiration(newExpiredTime)
                .signWith(getKeys())
                .compact();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKeys()).build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String parserToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKeys()).build()
                .parseClaimsJws(token).getBody()
                .getSubject();
    }

    public boolean validationToke(String token){
        try {
            Jwts.parserBuilder().setSigningKey(getKeys()).build()
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException exception){
            log.info("Expired token", exception);
        }
        return false;
    }
}
