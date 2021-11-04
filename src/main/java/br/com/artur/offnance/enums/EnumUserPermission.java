package br.com.artur.offnance.enums;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import static br.com.artur.offnance.config.Constants.ROLE;

@RequiredArgsConstructor
@Generated
public enum EnumUserPermission implements PersistableEnum<String> {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    public String getRole() {
        return ROLE + role;
    }

    public static EnumUserPermission getEnum(String valor) {
        EnumUserPermission[] enums = values();
        for (EnumUserPermission en : enums) {
            if (en.getRole().equals(valor)) {
                return en;
            }
        }
        return null;
    }

    @Override
    public String getId() {
        return this.name();
    }

    public static class Converter extends AbstractEnumConverter<EnumUserPermission, String> {
        public Converter() {
            super(EnumUserPermission.class);
        }
    }
}
