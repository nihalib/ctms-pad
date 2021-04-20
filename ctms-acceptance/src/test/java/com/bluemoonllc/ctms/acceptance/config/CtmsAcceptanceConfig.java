package com.bluemoonllc.ctms.acceptance.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@ComponentScan({"com.bluemoonllc.ctms.acceptance"})
@CucumberContextConfiguration
public class CtmsAcceptanceConfig {

    public static RestTemplate getRestTemplate(){
        /*RestTemplate restclient = new RestTemplate();
        restclient.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restclient;*/
        return new RestTemplate();
    }
}
