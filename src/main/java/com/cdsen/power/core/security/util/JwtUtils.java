package com.cdsen.power.core.security.util;

import com.cdsen.user.SecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author HuSen
 * create on 2019/8/27 17:30
 */
@Component
public class JwtUtils {

    private static final String SUB = "sub";
    private static final String CREATED = "created";

    private final SecurityConfig securityConfig;

    public JwtUtils(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    private String generateToken(Map<String, Object> claims) {
        Date created = (Date) claims.get(CREATED);
        Date expirationDate = new Date(created.getTime() + TimeUnit.MINUTES.toMillis(securityConfig.getExpiration()));
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, securityConfig.getSecret()).compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(securityConfig.getSecret()).parseClaimsJws(token).getBody();
        } catch (Exception ignored) {}
        return claims;
    }

    public boolean isExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return true;
        }
        return claims.getExpiration().getTime() < System.currentTimeMillis();
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(SUB, username);
        claims.put(CREATED, new Date());
        return generateToken(claims);
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            // claims 可以获得Token的过期时间
            username = claims.getSubject();
        } catch (Exception ignored) {}
        return username;
    }

    public boolean validateToken(String token, String username) {
        String gUsername = getUsernameFromToken(token);
        return StringUtils.endsWith(gUsername, username);
    }

    // TODO 支持Token刷新
}
