package com.bluemoonllc.ctms.acceptance.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@ComponentScan({"com.bluemoonllc.ctms.acceptance"})
@CucumberContextConfiguration
public class CtmsAcceptanceConfig {

    public static RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        //restclient.setErrorHandler(new RestTemplateResponseErrorHandler());
        restTemplate.setInterceptors(Collections.singletonList(new JsonMimeInterceptor()));
        return restTemplate;
    }
}
