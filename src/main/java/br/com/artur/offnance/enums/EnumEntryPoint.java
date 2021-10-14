package br.com.artur.offnance.enums;

import lombok.Getter;
import lombok.Setter;

public enum EnumEntryPoint {

  LOGIN_ROUTE("/api/login"),
  REGISTER_ROUTE("/api/signup"),
  TURN_OFF_ROUTE("/api/logout");

  @Getter
  @Setter
  private String route;

  EnumEntryPoint(String route) {
    this.route = route;
  }
}
