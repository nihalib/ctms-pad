package com.bluemoonllc.ctms.api.config;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.function.Supplier;

@Component
public class RestServiceConfig {

    private static RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public static <T> T getRequest(String endPointUrl, Class clazz) {
        return (T) withExceptionHandling(() -> getRestTemplate().getForEntity(endPointUrl, clazz));
    }

    public static <T> T getRequestWithParams(String endPointUrl, Class clazz, Map<String, String> pathVariables) {
        return (T) withExceptionHandling(() -> getRestTemplate().getForEntity(endPointUrl, clazz, pathVariables));
    }

    public static <T> T postRequest(String endPointUrl, HttpEntity request, Class clazz) {
        return (T) withExceptionHandling(() -> getRestTemplate().exchange(endPointUrl, HttpMethod.POST, request, clazz));
    }

    private static <T> ResponseEntity<T> withExceptionHandling(Supplier<ResponseEntity<T>> action) {
        try {
            return action.get();
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(ex.getStatusCode());
        }
    }
}
