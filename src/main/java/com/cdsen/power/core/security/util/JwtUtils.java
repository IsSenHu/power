package com.cdsen.power.core.security.util;

import com.cdsen.power.core.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final AppProperties appProperties;

    public JwtUtils(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    private String generateToken(Map<String, Object> claims) {
        Date created = (Date) claims.get(CREATED);
        AppProperties.Security security = appProperties.getSecurity();
        Date expirationDate = new Date(created.getTime() + TimeUnit.MINUTES.toMillis(security.getExpiration()));
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, security.getSecret()).compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            AppProperties.Security security = appProperties.getSecurity();
            claims = Jwts.parser().setSigningKey(security.getSecret()).parseClaimsJws(token).getBody();
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

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(SUB, userDetails.getUsername());
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

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return StringUtils.endsWith(username, userDetails.getUsername());
    }

    // TODO 支持Token刷新
}
