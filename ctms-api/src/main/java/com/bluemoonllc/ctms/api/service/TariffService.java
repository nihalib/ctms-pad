package com.bluemoonllc.ctms.api.service;

import com.bluemoonllc.ctms.api.dao.TariffDao;
import com.bluemoonllc.ctms.api.repository.TariffRepository;
import com.bluemoonllc.ctms.api.service.bi.TariffBI;
import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.client.cpa.CpaResponse;
import com.bluemoonllc.ctms.model.Tariff;
import com.bluemoonllc.ctms.model.common.CtmsResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public CtmsResponse addNewStation(String stationCode, Tariff tariff) {
        TariffDao tariffDao = TariffDao.builder()
                                       .stationCode(stationCode)
                                       .currencyCode(tariff.getCurrencyCode())
                                       .location(tariff.getLocation())
                                       .price(tariff.getPrice()).build();
        TariffDao result = tariffRepository.save(tariffDao);
        CtmsResponseStatus status = CtmsResponseStatus.DATA_FOUND;
        return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", result);
    }
}
