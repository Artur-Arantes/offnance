package br.com.artur.offnance.security.auth;

import br.com.artur.offnance.enums.EnumHashCodeAuthentication;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AnonymousAuthentication extends AbstractAuthenticationToken {

  public AnonymousAuthentication() {
    super( null );
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public int hashCode() {
    int hash = EnumHashCodeAuthentication.ANONYMUS_HASH_CODE.getCode();
    return hash;
  }

  @Override
  public boolean equals( Object obj ) {
    if ( this == obj ) {
      return true;
    }
    if ( obj == null ) {
      return false;
    }
    if ( getClass() != obj.getClass() ) {
      return false;
    }
    return true;
  }


}
