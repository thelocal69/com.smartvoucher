package com.smartvoucher.webEcommercesmartvoucher.util;

import com.google.gson.Gson;
import com.smartvoucher.webEcommercesmartvoucher.dto.OAuth2DTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JWTHelper {
    @Value("${public-key}")
    private String publicKey;
    @Value("${private-key}")
    private String privateKey;
    @Value("${access.token.expired}")
    private Long accessTokenExpired;
    @Value("${refresh.token.expired}")
    private Long refreshTokenExpired;
    private final Gson gson;

    @Autowired
    public JWTHelper(Gson gson) {
        this.gson = gson;
    }

    private RSAPrivateKey getPrivateKeys() {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(privateKey)
        );
        RSAPrivateKey rsaPrivateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.info(e.getMessage());
        }
        return rsaPrivateKey;
    }

    private RSAPublicKey getPublicKeys() {
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                Base64.getDecoder().decode(publicKey)
        );
        RSAPublicKey rsaPublicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.info(e.getMessage());
        }
        return rsaPublicKey;
    }

    public String generateToken(String data){
        long expiredTime = System.currentTimeMillis() + accessTokenExpired;
        Date newExpiredTime = new Date(expiredTime);
        return Jwts.builder()
                .setSubject(data)
                .setIssuer("com.smartvoucher")
                .signWith(getPrivateKeys())
                .setExpiration(newExpiredTime)
                .compact();
    }

    public String generateRefreshToken(String data){
        long expiredTime = System.currentTimeMillis() + refreshTokenExpired;
        Date newExpiredTime = new Date(expiredTime);
        return Jwts.builder()
                .setSubject(data)
                .signWith(getPrivateKeys())
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
                .signWith(getPrivateKeys())
                .compact();
    }


    public String parserToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKeys()).build()
                .parseClaimsJws(token).getBody()
                .getSubject();
    }

    public OAuth2DTO parserTokenGoogle(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Map<String, Object> mapping = gson.fromJson(payload, HashMap.class);
        OAuth2DTO oAuth2DTO = new OAuth2DTO();
        oAuth2DTO.setEmailGoogle(mapping.get("email").toString());
        oAuth2DTO.setNameGoogle(mapping.get("name").toString());
        oAuth2DTO.setAvatarGoogle(mapping.get("picture").toString());
        return oAuth2DTO;
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getPublicKeys())
                .build()
                .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validationToke(String token) {
        return !isTokenExpired((token));
    }
}
