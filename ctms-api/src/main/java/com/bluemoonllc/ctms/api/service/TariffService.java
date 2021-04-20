package com.bluemoonllc.ctms.api.service;

import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.client.cpa.CpaResponse;
import com.bluemoonllc.ctms.model.Tariff;
import com.bluemoonllc.ctms.model.common.CtmsResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.bluemoonllc.ctms.api.config.RestServiceConfig.postRequest;

@Service
@Slf4j
public class TariffService {

    private final String cpaEndpoint;

    public TariffService(@Value("${customer.profile.api.url}") String cpaEndpoint) {
        this.cpaEndpoint = cpaEndpoint;
    }

    public CtmsResponse addTariff(Tariff tariff) {
        log.info("Request sent to cpaEndpoint {}", cpaEndpoint);
        HttpEntity<Tariff> httpEntity = new HttpEntity<>(tariff);
        ResponseEntity<CpaResponse> response = postRequest(cpaEndpoint, httpEntity, CpaResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            CpaResponse responseBody = response.getBody();
            CtmsResponseStatus status = CtmsResponseStatus.DATA_FOUND;
            return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", responseBody.getData());
        }
        CtmsResponseStatus status = CtmsResponseStatus.NOT_FOUND;
        return new CtmsResponse<>(status.getDescription(), status, status.getMessage(), "CTMS", null);
    }
}
