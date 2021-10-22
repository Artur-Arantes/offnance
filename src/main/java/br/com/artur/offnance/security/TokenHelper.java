package br.com.artur.offnance.security;


import br.com.artur.offnance.config.ConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Component
public class TokenHelper {

    private final static SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    ConfigProperties configProperties;


    public String getUsernameFromToken(final String token) {
        return this.getClaims(token).map(Claims::getSubject).orElse(null);
    }


    public String create(final String username) {
        return Jwts.builder()
                .setIssuer(configProperties.getAppAuthroization())
                .setSubject(username)
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .signWith(KEY)
                .compact();
    }


    private Optional<Claims> getClaims(final String token) {
        final var jwtParser = Jwts.parserBuilder().setSigningKey(KEY).build();
        return Optional.of(jwtParser).map(jwt -> jwt.parseClaimsJws(token))
                .map(jws -> jws.getBody());
    }


    public String getToken(final @NonNull HttpServletRequest request) {

        Cookie authCookie = getCookeValueByName(request, configProperties.getCookie());
        if (authCookie != null) {
            return authCookie.getValue();
        }

        String authHeader = request.getHeader(configProperties.getHeader());
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    private long getCurrentTimeMillis() {
        return ZonedDateTime.now().toEpochSecond();
    }

    private Date generateCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    private Date generateExpirationDate() {

        return new Date(getCurrentTimeMillis() + this.configProperties.getExpiresIn() * 1000);
    }


    public Cookie getCookeValueByName(@NonNull final HttpServletRequest request, final String name) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(name))
                .findFirst().orElse(null);
    }
}
