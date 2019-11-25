package com.zavier.project.manager.security;

import com.zavier.project.common.constant.AdminConstants;
import com.zavier.project.common.util.StringUtil;
import com.zavier.project.manager.bo.UserBO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The type Jwt token util.
 */
@Slf4j
@Component
public class JwtTokenUtil implements Serializable {
    /**
     * The constant JWT_TOKEN_VALIDITY.
     */
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    /**
     * The Secret.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Gets username from token.
     *
     * @param token the token
     * @return the username from token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Gets expiration date from token.
     *
     * @param token the token
     * @return the expiration date from token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Gets claim from token.
     *
     * @param <T>            the type parameter
     * @param token          the token
     * @param claimsResolver the claims resolver
     * @return the claim from token
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Gets all claims from token.
     *
     * @param token the token
     * @return the all claims from token
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Is token expired boolean.
     *
     * @param token the token
     * @return the boolean
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate token string.
     *
     * @param UserBO the user bo
     * @return the string
     */
    public String generateToken(UserBO UserBO) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, UserBO.getUserName());
    }

    /**
     * Generate token string.
     *
     * @param UserBO the user bo
     * @param claims the claims
     * @return the string
     */
    public String generateToken(UserBO UserBO, Map<String, Object> claims) {
        return doGenerateToken(claims, UserBO.getUserName());
    }

    /**
     * while creating the token -
     * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using the HS512 algorithm and secret key.
     * 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
     * compaction of the JWT to a URL-safe string
     *
     * @param claims  the claims
     * @param subject the subject
     * @return string
     */
    public String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .setClaims(claims)
                .compact();
    }

    /**
     * 校验token有效性
     *
     * @param token the token
     * @return boolean
     */
    public boolean validateToken(String token) {
        if (StringUtil.isBlank(token)) {
            return false;
        }
        Claims claims = getAllClaimsFromToken(token);
        Map<String, Object> map = new HashMap<>();
        claims.keySet().forEach(k -> {
            Object s = claims.get(k);
            map.put(k, s);
        });
        String subject = claims.getSubject();
        String newToken = doGenerateToken(map, subject);
        return newToken.equals(token);
    }

    /**
     * Gets jwt token from request.
     *
     * @param request the request
     * @return the jwt token from request
     */
    public String getJwtTokenFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader(AdminConstants.TOKEN_HEADER);
        if (StringUtil.isBlank(authorization)) {
            return null;
        }
        if (authorization.startsWith(AdminConstants.TOKEN_BEARER)) {
            return authorization.substring(AdminConstants.TOKEN_BEARER.length());
        }
        log.warn("jwt token does not have bearer");
        return authorization;
    }
}