package com.bluemoonllc.ctms.model;

import com.bluemoonllc.ctms.model.common.ChargingModes;
import com.bluemoonllc.ctms.model.common.ProviderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Station {
    @Schema(example = "11010", description = "Charging station code")
    private Long stationId;
    @Schema(example = "paris", description = "Station location")
    private String location;
    @Schema(example = "DCS", description = "Charging provider id", allowableValues = "DCS, CPI, NAV")
    private ProviderEnum providerId;
    @Schema(example = "[\"AC1\",\"DC\"]", description = "Charging mode", allowableValues = "AC1, AC3, DC")
    private List<ChargingModes> supportedChargingModes;
    @Schema(example = "Asia/Shangai", description = "Time zone")
    private String timeZone;
    @Schema(example = "2021-01-01T00:00:00Z", description = "Last station updated time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastUpdate;
    @Schema(example = "true", description = "Is station public")
    private Boolean isPublic;
    @Schema(description = "List of tariffs")
    private List<Tariff> tariffs;
}
