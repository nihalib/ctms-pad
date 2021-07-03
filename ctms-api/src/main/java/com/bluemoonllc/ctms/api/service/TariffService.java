package com.bluemoonllc.ctms.api.service;

import com.bluemoonllc.ctms.api.dao.StationDao;
import com.bluemoonllc.ctms.api.dao.TariffDao;
import com.bluemoonllc.ctms.api.repository.StationRepository;
import com.bluemoonllc.ctms.api.repository.TariffRepository;
import com.bluemoonllc.ctms.api.service.bi.TariffBI;
import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Station;
import com.bluemoonllc.ctms.model.Tariff;
import com.bluemoonllc.ctms.model.common.ChargingModes;
import com.bluemoonllc.ctms.model.common.CtmsResponseStatus;
import com.bluemoonllc.ctms.model.common.PaginatedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bluemoonllc.ctms.model.common.ChargingModes.fromValues;

@Slf4j
@Service
public class TariffService implements TariffBI {

    private final String cpaEndpoint;
    private final TariffRepository tariffRepository;
    private final StationRepository stationRepository;

    @Autowired
    public TariffService(@Value("${customer.profile.api.url}") String cpaEndpoint,
                         TariffRepository tariffRepository, StationRepository stationRepository) {
        this.cpaEndpoint = cpaEndpoint;
        this.tariffRepository = tariffRepository;
        this.stationRepository = stationRepository;
    }

    public CtmsResponse getTariff(String fetchType, Integer page, Integer pageSize) {
        PaginatedResponse response = new PaginatedResponse();

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<TariffDao> result;
        if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("ALL")) {
            result = tariffRepository.findAll(pageable);
        } else if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("TEST")) {
            result = tariffRepository.findTariffDaoByIsTestData(true, pageable);
        } else {
            result = tariffRepository.findTariffDaoByIsTestData(false, pageable);
        }

        List<TariffDao> resultContent = result.getContent();
        if (resultContent.isEmpty()) {
            CtmsResponseStatus status = CtmsResponseStatus.NOT_FOUND;
            return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", null);
        }
        List<Tariff> tariffs = resultContent.stream()
                .map(record -> Tariff.builder()
                .currencyCode(record.getCurrencyCode())
                .tariffId(record.getTariffId())
                .price(record.getPrice())
                .stationId(record.getStationCode().getStationId())
                .cityCode(record.getLocation()).build())
                .collect(Collectors.toList());

        response.setData(tariffs);
        response.setPageSize(result.getSize());
        response.setTotalPage(result.getTotalPages());
        response.setTotalRecords((int) result.getTotalElements());

        CtmsResponseStatus status = CtmsResponseStatus.DATA_FOUND;
        return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", response);
    }

    public CtmsResponse addTariff(Tariff tariff, Boolean testMode) {
        Optional<StationDao> station = stationRepository.findByStationId(tariff.getStationId());
        if (station.isPresent()) {
            TariffDao tariffDao = TariffDao.builder()
                    .stationCode(station.get())
                    .tariffId(tariff.getTariffId())
                    .location(tariff.getCityCode())
                    .price(tariff.getPrice())
                    .currencyCode(tariff.getCurrencyCode())
                    .isTestData(testMode)
                    .build();
            TariffDao response = tariffRepository.save(tariffDao);
            Tariff result = Tariff.builder()
                    .currencyCode(response.getCurrencyCode())
                    .tariffId(response.getTariffId())
                    .stationId(response.getStationCode().getStationId())
                    .cityCode(response.getLocation())
                    .price(response.getPrice())
                    .build();
            CtmsResponseStatus status = CtmsResponseStatus.DATA_UPDATED;
            return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", result);
        }
        CtmsResponseStatus status = CtmsResponseStatus.UNPROCESSABLE_ENTITY;
        String message = String.format(status.getMessage(), tariff.getStationId());
        return new CtmsResponse<>(status.getDescription(), status, message, "CTMS", null);
//        HttpEntity<Tariff> httpEntity = new HttpEntity<>(tariff);
//        ResponseEntity<CpaResponse> response = postRequest(cpaEndpoint, httpEntity, CpaResponse.class);
//        log.info("Request sent to cpaEndpoint {}", cpaEndpoint);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            CpaResponse responseBody = response.getBody();
//            CtmsResponseStatus status = CtmsResponseStatus.DATA_FOUND;
//            return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", responseBody.getData());
//        }
    }

    public CtmsResponse addNewStation(Station station, Boolean testMode) {
        StationDao stationDao = StationDao.builder()
                .stationId(station.getStationId())
                .location(station.getLocation())
                .providerId(station.getProviderId())
                .supportedChargingModes(getChargingModes(station))
                .timeZone(station.getTimeZone())
                .lastUpdate(station.getLastUpdate())
                .isPublic(station.getIsPublic())
                .isTestData(testMode)
                .tariffs(new ArrayList<>())
                .build();

        if (!station.getTariffs().isEmpty()) {
            List<TariffDao> tariffs = new ArrayList<>();
            for (Tariff tariff : station.getTariffs()) {
                TariffDao tariffDao = TariffDao.builder()
                        .stationCode(stationDao)
                        .tariffId(tariff.getTariffId())
                        .location(tariff.getCityCode())
                        .price(tariff.getPrice())
                        .currencyCode(tariff.getCurrencyCode())
                        .isTestData(testMode)
                        .build();
                tariffs.add(tariffDao);
            }
            stationDao.getTariffs().addAll(tariffs);
        }

        StationDao result = stationRepository.save(stationDao);
        CtmsResponseStatus status = CtmsResponseStatus.DATA_UPDATED;
        return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", result);
    }

    public CtmsResponse getStation(String fetchType, Integer page, Integer pageSize) {
        log.info("Get Station Request fetch type {}, page {}, pageSize {}", fetchType, page, pageSize);
        PaginatedResponse response = new PaginatedResponse();

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<StationDao> result;
        if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("ALL")) {
            result = stationRepository.findAll(pageable);
        } else if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("TEST")) {
            result = stationRepository.findStationDaoByIsTestData(true, pageable);
        } else {
            result = stationRepository.findStationDaoByIsTestData(false, pageable);
        }

        List<StationDao> resultContent = result.getContent();
        if (resultContent.isEmpty()) {
            log.info("Requested station not found");
            CtmsResponseStatus status = CtmsResponseStatus.NOT_FOUND;
            return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", null);
        }

        List<Station> stations = resultContent.stream()
                .map(record -> Station.builder()
                        .isPublic(record.getIsPublic())
                        .lastUpdate(record.getLastUpdate())
                        .providerId(record.getProviderId())
                        .location(record.getLocation())
                        .stationId(record.getStationId())
                        .supportedChargingModes(record.getSupportedChargingModes())
                        .timeZone(record.getTimeZone())
                        .tariffs(convertTariffs(record.getTariffs()))
                        .build())
                .collect(Collectors.toList());

        response.setData(stations);
        response.setPageSize(result.getSize());
        response.setTotalPage(result.getTotalPages());
        response.setTotalRecords((int) result.getTotalElements());

        log.info("Requested data returned");
        CtmsResponseStatus status = CtmsResponseStatus.DATA_FOUND;
        return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", response);
    }

    @Override
    public CtmsResponse getStationByLocation(String fetchType, String location, Integer page, Integer pageSize) {
        log.info("Get Station by location request fetch type {}, page {}, pageSize {}", fetchType, page, pageSize);
        PaginatedResponse response = new PaginatedResponse();

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<StationDao> result;
        if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("ALL")) {
            result = stationRepository.findAllByLocation(location, pageable);
        } else if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("TEST")) {
            result = stationRepository.findStationDaoByLocationAndIsTestData(location, true, pageable);
        } else {
            result = stationRepository.findStationDaoByLocationAndIsTestData(location, false, pageable);
        }

        List<StationDao> resultContent = result.getContent();
        if (resultContent.isEmpty()) {
            log.info("Requested station not found");
            CtmsResponseStatus status = CtmsResponseStatus.NOT_FOUND;
            return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", null);
        }

        List<Station> stations = resultContent.stream()
                .map(record -> Station.builder()
                        .isPublic(record.getIsPublic())
                        .lastUpdate(record.getLastUpdate())
                        .providerId(record.getProviderId())
                        .location(record.getLocation())
                        .stationId(record.getStationId())
                        .supportedChargingModes(record.getSupportedChargingModes())
                        .timeZone(record.getTimeZone())
                        .tariffs(convertTariffs(record.getTariffs()))
                        .build())
                .collect(Collectors.toList());

        response.setData(stations);
        response.setPageSize(result.getSize());
        response.setTotalPage(result.getTotalPages());
        response.setTotalRecords((int) result.getTotalElements());

        log.info("Requested data returned");
        CtmsResponseStatus status = CtmsResponseStatus.DATA_FOUND;
        return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", response);
    }

    private List<Tariff> convertTariffs(List<TariffDao> tariffs) {
        List<Tariff> tariffList = new ArrayList<>();
        for (TariffDao tariffDao : tariffs) {
            Tariff tariff = Tariff.builder()
                    .cityCode(tariffDao.getLocation())
                    .stationId(tariffDao.getStationCode().getStationId())
                    .tariffId(tariffDao.getTariffId())
                    .price(tariffDao.getPrice())
                    .currencyCode(tariffDao.getCurrencyCode())
                    .build();
            tariffList.add(tariff);
        }
        return tariffList;
    }

    private List<ChargingModes> getChargingModes(Station station) {
        Set<ChargingModes> chargingModes = EnumSet.noneOf(ChargingModes.class);
        station.getSupportedChargingModes().forEach(mode -> chargingModes.add(fromValues(mode.toString())));
        return new ArrayList<>(chargingModes);
    }
}
