package com.bluemoonllc.ctms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tariff {
    private String location;
    private double price;
    private String currencyCode;
}
