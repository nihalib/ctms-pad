package com.bluemoonllc.ctms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tariff {
    @Schema(example = "11010", description = "Charging station code")
    private Long stationId;
    @Schema(example = "10.15", description = "Charging cost")
    private double price;
    @Schema(example = "AED", description = "Currency code")
    private String currencyCode;
    @Schema(example = "DXB", description = "Charging station city code")
    private String cityCode;
}
