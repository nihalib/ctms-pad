package com.bluemoonllc.ctms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tariff {
    private String stationCode;
    private double price;
    private String currencyCode;
}
