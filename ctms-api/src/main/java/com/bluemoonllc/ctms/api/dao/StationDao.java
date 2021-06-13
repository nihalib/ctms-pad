package com.bluemoonllc.ctms.api.dao;

import com.bluemoonllc.ctms.model.common.ChargingModes;
import com.bluemoonllc.ctms.model.common.ProviderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Table(name = "station", schema = "ctms_pad")
public class StationDao implements Serializable {

    private static final long serialVersionUID = 4140004863069837370L;

    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(
            name = "station_generator",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "optimizer", value = "pooled-lo"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "10"),
                    @Parameter(name = "sequence_name", value = "ctms_pad.ctms_pad_station_sqc")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "station_generator")
    private Long id;
    @Column(name = "station_id", nullable = false)
    private Long stationId;
    @Column(name = "provider_id", nullable = false)
    private ProviderEnum providerId;
    @Column(name = "supported_charging_modes")
    @Type(type = "com.bluemoonllc.ctms.api.utils.ChargingModeUserType")
    private List<ChargingModes> supportedChargingModes;
    @Column(name = "timezone")
    private String timeZone;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdate;
    @Column(name = "is_public")
    private Boolean isPublic;
    @Column(name = "is_test_data")
    private Boolean isTestData;
    @OneToMany(mappedBy = "stationCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = TariffDao.class)
    private List<TariffDao> tariffs = new ArrayList<>();
}
