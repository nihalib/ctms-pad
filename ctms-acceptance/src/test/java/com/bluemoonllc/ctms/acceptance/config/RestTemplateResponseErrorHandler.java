package com.bluemoonllc.ctms.acceptance.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return ( httpResponse.getStatusCode().series() == CLIENT_ERROR
                || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        HttpStatus statusCode = clientHttpResponse.getStatusCode();
        MediaType contentType = clientHttpResponse
                .getHeaders()
                .getContentType();
        Charset charset = contentType != null ? contentType.getCharset() : null;
        byte[] body = FileCopyUtils.copyToByteArray(clientHttpResponse.getBody());

        switch (statusCode.series()) {
            case CLIENT_ERROR:
                throw new HttpClientErrorException(statusCode, clientHttpResponse.getStatusText(), body, charset);
            case SERVER_ERROR:
                throw new HttpServerErrorException(statusCode, clientHttpResponse.getStatusText(), body, charset);
            default:
                throw new RestClientException("Unknown status code [" + statusCode + "]");
        }
    }
}
