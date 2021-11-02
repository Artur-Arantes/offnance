package br.com.artur.offnance.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EnumHashCodeAuthentication {
  ANONYMUS_HASH_CODE(7);

  @Getter
  private final int code;
}
