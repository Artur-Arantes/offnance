package br.com.artur.offnance.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@RequiredArgsConstructor
public class TokenState {
  private final String accessToken;
  private final Long expiresIn;



}