package com.rest.countrydata.handler;


import com.rest.countrydata.exception.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;


public class OWMResponseErrorHandler implements ResponseErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(OWMResponseErrorHandler.class);

    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    public boolean hasError(ClientHttpResponse response) throws IOException {
        return errorHandler.hasError(response);
    }

    public void handleError(ClientHttpResponse response) throws IOException {
        logger.error("Response error: {} {}", response.getStatusCode(), response.getStatusText());
        ResponseException exception = new ResponseException(response.getStatusCode(), response.getStatusText());
        throw exception;
    }
}
