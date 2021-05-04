package com.bluemoonllc.ctms.api.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tariff", schema = "ctms_pad")
public class TariffDao implements Serializable {

    private static final long serialVersionUID = 9191035735922539009L;

    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(
            name = "tariff_generator",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "optimizer", value = "pooled-lo"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "10"),
                    @Parameter(name = "sequence_name", value = "ctms_pad.ctms_pad_tariff_sqc")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tariff_generator")
    private Long id;
    @Column(name = "station_code", nullable = false)
    private String stationCode;
    @Column(name = "location")
    private String location;
    @Column(name = "currency_code")
    private String currencyCode;
    @Column(name = "price")
    private Double price;
}
