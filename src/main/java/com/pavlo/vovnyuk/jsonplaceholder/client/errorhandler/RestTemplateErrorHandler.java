package com.pavlo.vovnyuk.jsonplaceholder.client.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        HttpStatusCode status = response.getStatusCode();

        if (status.is5xxServerError()) {
            log.error("Server error | status={} reason={}", status.value(), response.getStatusText());
            throw HttpServerErrorException.create(status, response.getStatusText(), response.getHeaders(), null, null);
        }

        if (status.is4xxClientError()) {
            log.error("Client error | status={} reason={}", status.value(), response.getStatusText());
            throw HttpClientErrorException.create(status, response.getStatusText(), response.getHeaders(), null, null);
        }
    }
}