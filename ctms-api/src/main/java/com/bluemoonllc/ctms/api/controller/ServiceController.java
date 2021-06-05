package com.bluemoonllc.ctms.api.controller;

import com.bluemoonllc.ctms.api.service.TariffService;
import com.bluemoonllc.ctms.api.service.bi.TariffBI;
import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Tariff;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/services/v1",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceController {

    private final TariffBI service;

    public ServiceController(TariffService service) {
        this.service = service;
    }


    @GetMapping(value = "/tariff")
    public ResponseEntity getTariff(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "pageSize", defaultValue = "1") Integer pageSize) {
        CtmsResponse response = service.getTariff(page, pageSize);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @GetMapping(value = "/tariff/{location}")
    public ResponseEntity<CtmsResponse> getTariffDetails(@PathVariable String location) {
        CtmsResponse response = service.getStation(location);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @PostMapping(value = "/tariff/{location}")
    public ResponseEntity<CtmsResponse> addNewStation(@PathVariable("location") String location,
                                                      @RequestBody Tariff tariff) {
        CtmsResponse response = service.addNewStation(location, tariff);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @PostMapping(value = "/tariff")
    public ResponseEntity<CtmsResponse> addTariffDetails(@RequestBody Tariff tariff){
        CtmsResponse response = service.addTariff(tariff);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }
}
