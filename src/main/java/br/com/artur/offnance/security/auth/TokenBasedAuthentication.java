package br.com.artur.offnance.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;


public class TokenBasedAuthentication extends AbstractAuthenticationToken {

  private String token;
  private final UserDetails userDetails;

  public TokenBasedAuthentication(final UserDetails userDetails) {
    super( userDetails.getAuthorities() );
    this.userDetails = userDetails;
  }

  public void setToken( String token ) {
    this.token = token;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public UserDetails getPrincipal() {
    return userDetails;
  }

}
