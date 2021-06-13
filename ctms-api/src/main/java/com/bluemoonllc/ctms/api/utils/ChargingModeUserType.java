package com.bluemoonllc.ctms.api.utils;

import com.bluemoonllc.ctms.model.common.ChargingModes;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChargingModeUserType extends AbstractEnumListUserType {

    @Override
    protected Object mapValues(Stream<String> stringStream) {
        return stringStream.map(ChargingModes::valueOf).collect(Collectors.toList());
    }
}
