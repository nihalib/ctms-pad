package com.bluemoonllc.ctms.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public enum ChargingModes {
    AC1("AC1"),
    AC3("AC3"),
    DC("DC");

    private String value;
    @JsonValue
    private String getValue() {
        return value;
    }
    @JsonCreator
    public static ChargingModes fromValues(String value) {
        for (ChargingModes chargingMode : values()) {
            if (chargingMode.value.equalsIgnoreCase(value)) {
                return chargingMode;
            }
        }
        throw new IllegalArgumentException(format("Value %s couldn't be mapped to %s", value, ChargingModes.class.getSimpleName()));
    }

    @Override
    public String toString() {
        return getValue();
    }
}
