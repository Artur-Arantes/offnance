package br.com.artur.offnance.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@RequiredArgsConstructor
public enum EnumEntryPoint {

  LOGIN_ROUTE("/api/login"),
  REGISTER_ROUTE("/api/signup"),
  TURN_OFF_ROUTE("/api/logout");

  @Getter
  private final String route;


}
