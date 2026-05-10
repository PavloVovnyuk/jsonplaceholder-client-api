package com.pavlo.vovnyuk.jsonplaceholder.client.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
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
        var status = response.getStatusCode();
        if (status.is5xxServerError()) {
            log.error("server error | status={} reason={}", status.value(), response.getStatusText());
        } else if (status.is4xxClientError()) {
            log.error("client error | status={} reason={}", status.value(), response.getStatusText());
        }
    }
}