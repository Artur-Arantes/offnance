package br.com.artur.offnance.domain;

import lombok.Data;

@Data
public class TokenState {
  private String acessTolken;
  private Long expiresIn;

  public TokenState() {
    this.acessTolken = null;
    this.expiresIn = null;
  }

  public TokenState(String tokenDeAcesso, long expiraEm) {
    this.acessTolken = tokenDeAcesso;
    this.expiresIn = expiraEm;
  }

}