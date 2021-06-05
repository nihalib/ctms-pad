package com.bluemoonllc.ctms.api.service;

import com.bluemoonllc.ctms.api.dao.TariffDao;
import com.bluemoonllc.ctms.api.repository.TariffRepository;
import com.bluemoonllc.ctms.api.service.bi.TariffBI;
import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.client.cpa.CpaResponse;
import com.bluemoonllc.ctms.model.Tariff;
import com.bluemoonllc.ctms.model.common.CtmsResponseStatus;
import com.bluemoonllc.ctms.model.common.PaginatedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.bluemoonllc.ctms.api.config.RestServiceConfig.postRequest;

@Slf4j
@Service
public class TariffService implements TariffBI {

    private final String cpaEndpoint;
    private final TariffRepository tariffRepository;

    @Autowired
    public TariffService(@Value("${customer.profile.api.url}") String cpaEndpoint,
                         TariffRepository tariffRepository) {
        this.cpaEndpoint = cpaEndpoint;
        this.tariffRepository = tariffRepository;
    }

    public CtmsResponse getTariff(Integer page, Integer pageSize) {
        PaginatedResponse response = new PaginatedResponse();

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<TariffDao> result = tariffRepository.findAll(pageable);

        List<TariffDao> resultContent = result.getContent();
        if (resultContent.isEmpty()) {
            CtmsResponseStatus status = CtmsResponseStatus.NOT_FOUND;
            return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", null);
        }
        response.setData(resultContent);
        response.setPageSize(result.getSize());
        response.setTotalPage(result.getTotalPages());
        response.setTotalRecords((int) result.getTotalElements());

        CtmsResponseStatus status = CtmsResponseStatus.DATA_FOUND;
        return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", response);
    }

    public CtmsResponse addTariff(Tariff tariff) {
        HttpEntity<Tariff> httpEntity = new HttpEntity<>(tariff);
        ResponseEntity<CpaResponse> response = postRequest(cpaEndpoint, httpEntity, CpaResponse.class);
        log.info("Request sent to cpaEndpoint {}", cpaEndpoint);
        if (response.getStatusCode().is2xxSuccessful()) {
            CpaResponse responseBody = response.getBody();
            CtmsResponseStatus status = CtmsResponseStatus.DATA_FOUND;
            return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", responseBody.getData());
        }
        CtmsResponseStatus status = CtmsResponseStatus.NOT_FOUND;
        return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", null);
    }

    public CtmsResponse addNewStation(String location, Tariff tariff) {
        TariffDao tariffDao = TariffDao.builder()
                                       .stationCode(tariff.getStationCode())
                                       .currencyCode(tariff.getCurrencyCode())
                                       .location(location)
                                       .price(tariff.getPrice()).build();
        TariffDao result = tariffRepository.save(tariffDao);
        CtmsResponseStatus status = CtmsResponseStatus.DATA_FOUND;
        return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", result);
    }

    public CtmsResponse getStation(String location) {
        Optional<TariffDao> result = tariffRepository.findByLocation(location);
        if (result.isPresent()) {
            CtmsResponseStatus status = CtmsResponseStatus.DATA_FOUND;
            TariffDao record = result.get();
            Tariff tariff = Tariff.builder()
                    .currencyCode(record.getCurrencyCode())
                    .price(record.getPrice())
                    .stationCode(record.getStationCode()).build();
            return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", tariff);
        } else {
            CtmsResponseStatus status = CtmsResponseStatus.NOT_FOUND;
            return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", null);
        }
    }
}
