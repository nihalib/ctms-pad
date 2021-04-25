package com.bluemoonllc.ctms.acceptance.steps.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.function.Supplier;

import static com.bluemoonllc.ctms.acceptance.config.CtmsAcceptanceConfig.getRestTemplate;

public class CtmsPadClient {
    private static final String PAD_URL = "http://localhost:9900/ctms-api/";
    private static final RestTemplate REST_TEMPLATE = getRestTemplate();

    public static <T> T getRequest(String endPointUrl, Class clazz) {
        return (T) withExceptionHandling(() -> REST_TEMPLATE.getForEntity(getEndpointUrl(endPointUrl), clazz));
    }

    public static <T> T getRequestWithParams(String endPointUrl, Class clazz, Map<String, String> pathVariables) {
        return (T) withExceptionHandling(() -> REST_TEMPLATE.getForEntity(getEndpointUrl(endPointUrl), clazz, pathVariables));
    }

    public static <T> T postRequest(String endPointUrl, HttpEntity request, Class clazz) {
        return (T) withExceptionHandling(() -> getRestTemplate().exchange(getEndpointUrl(endPointUrl), HttpMethod.POST, request, clazz));
    }

    public static <T> T postRequestWithParams(String endPointUrl, HttpEntity request, Class clazz, Map<String, String> pathVariables) {
        return (T) withExceptionHandling(() -> getRestTemplate().exchange(getEndpointUrl(endPointUrl), HttpMethod.POST, request, clazz, pathVariables));
    }

    private static <T> ResponseEntity<T> withExceptionHandling(Supplier<ResponseEntity<T>> action) {
        try {
            return action.get();
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(ex.getStatusCode());
        }
    }

    private static String getEndpointUrl(String serviceEndpoint) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(PAD_URL);
        if (StringUtils.isNotEmpty(serviceEndpoint)) {
            urlBuilder.path(serviceEndpoint);
            return urlBuilder.build().toUriString();
        }
        return null;
    }
}
