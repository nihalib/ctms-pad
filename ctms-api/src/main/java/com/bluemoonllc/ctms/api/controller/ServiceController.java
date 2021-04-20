package com.bluemoonllc.ctms.api.controller;

import com.bluemoonllc.ctms.api.service.TariffService;
import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Tariff;
import com.bluemoonllc.ctms.model.common.CtmsResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/services/v1")
public class ServiceController {

    private final TariffService service;

    public ServiceController(TariffService service) {
        this.service = service;
    }


    @GetMapping(value = "/tariff")
    public ResponseEntity getTariff() {
        return new ResponseEntity("Nothing at the moment.", HttpStatus.OK);
    }

    @GetMapping(value = "/tariff/{location}")
    public ResponseEntity<CtmsResponse> getTariffDetails(@PathVariable String location) {
        String result = String.format("No tariff found for %s", location);
        CtmsResponseStatus status = CtmsResponseStatus.NOT_FOUND;
        CtmsResponse response = new CtmsResponse<>(status.getDescription(), status, status.getMessage(),
                         "CTMS", result);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @PostMapping(value = "/tariff")
    public ResponseEntity<CtmsResponse> addTariffDetails(@RequestBody Tariff tariff){
        CtmsResponse response = service.addTariff(tariff);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }
}
