package dev.otthon.parkingapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {

    public static final String JWT_BEARER = "Bearer ";

    public static final String JWT_AUTHORIZATION = "Authorization";

    public static final String JWT_SECRET_KEY = "0123456789-0123456789-0123456789";

    public static final long JWT_EXPIRATION_DAYS = 0;

    public static final long JWT_EXPIRATION_HOURS = 0;

    public static final long JWT_EXPIRATION_MINUTES = 2;

    private JwtUtils() {}

    private static Key generateKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpiration(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime
                .plusDays(JWT_EXPIRATION_DAYS)
                .plusHours(JWT_EXPIRATION_DAYS)
                .plusMinutes(JWT_EXPIRATION_MINUTES);

        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JwtToken createJwtToken(String username, String role) {

        Date issuedAt = new Date();
        Date limit = toExpiration(issuedAt);

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(limit)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .claim("role", role)
                .compact();

        return new JwtToken(token);
    }

    private static String refactorToken(String token) {
        if (token.contains(JWT_BEARER)) {
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }

    private static Claims getClaimsFromToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token)).getBody();
        } catch (JwtException exception) {
            log.error(String.format("O token %s é inválido", exception.getMessage()));
        }
        return null;
    }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token));
            return true;
        } catch (JwtException exception) {
            log.error(String.format("O token %s é inválido", exception.getMessage()));
        }
        return false;
    }

    public static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

}
