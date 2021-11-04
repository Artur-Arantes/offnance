package br.com.artur.offnance.enums;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Generated
public enum EnumEntryPoint {

    LOGIN_ROUTE("/api/login"),
    REGISTER_ROUTE("/api/signup"),
    TURN_OFF_ROUTE("/api/logout");

    @Getter
    private final String route;


}
