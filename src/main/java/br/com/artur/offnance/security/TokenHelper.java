package br.com.artur.offnance.security;


import br.com.artur.offnance.config.ConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;

@Component
public class TokenHelper {

  private final static SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  @Autowired
  ConfigProperties configProperties;

  @Autowired
  UserDetailsService userDetailsService;


  public String getUsuarioDoToken(String token) {
    String userName;
    try {
      //Retornar o usuário do token, a partir da chave secreta
      final Claims claims = this.getClaimsDoToken(token);
      userName = claims.getSubject();
    } catch (Exception e) {
      userName = null;
    }
    return userName;
  }


  public String makeToken(String username) {
    return Jwts.builder()
        .setIssuer(configProperties.getAppAuthroization())
        .setSubject(username)
        .setIssuedAt(generateCurrentDate())
        .setExpiration(generateExpirationDate())
        .signWith(KEY)
        .compact();
  }


  private Claims getClaimsDoToken(String token) {
    JwtHelper
  }

  String makeToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(generateExpirationDate())
        .signWith(KEY)
        .compact();
  }


  public String getToken(HttpServletRequest request) {

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


  public Boolean tokenCanUptade(String token) {
    try {
      final Date expirationDate = getClaimsDoToken(token).getExpiration();
      String username = getUsuarioDoToken(token);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      return expirationDate.compareTo(generateCurrentDate()) > 0;
    } catch (Exception e) {
      return false;
    }
  }

  public String refreshToken(String token) {
    String refreshedToken;
    try {
      final Claims claims = getClaimsDoToken(token);
      claims.setIssuedAt(generateCurrentDate());
      refreshedToken = makeToken(claims);
    } catch (Exception e) {
      refreshedToken = null;
    }
    return refreshedToken;
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


  public Cookie getCookeValueByName(HttpServletRequest request, String name) {
    if (request.getCookies() == null) {
      return null;
    }
    return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(name))
        .findFirst().orElse(null);
  }
}
