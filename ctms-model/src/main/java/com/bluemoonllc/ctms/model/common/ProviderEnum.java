package com.bluemoonllc.ctms.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public enum ProviderEnum {
    CPI("CPI"),
    DCS("DCS"),
    NAV("NAV");

    private String value;
    @JsonValue
    private String getValue() {
        return value;
    }
    @JsonCreator
    public static ProviderEnum fromValues(String value) {
        for (ProviderEnum provider : values()) {
            if (provider.value.equalsIgnoreCase(value)) {
                return provider;
            }
        }
        throw new IllegalArgumentException(format("Value %s couldn't be mapped to %s", value, ChargingModes.class.getSimpleName()));
    }

    @Override
    public String toString() {
        return getValue();
    }
}
