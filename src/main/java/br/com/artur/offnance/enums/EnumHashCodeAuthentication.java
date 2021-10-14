package br.com.artur.offnance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EnumHashCodeAuthentication {
 ANONYMUS_HASH_CODE(7);

  @Getter
  private int code;
}
