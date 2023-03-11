package com.rest.countrydata.exception;

import org.springframework.http.HttpStatus;

import java.io.IOException;


public class ResponseException extends IOException {
    private HttpStatus statusCode;

    private String body;

    public ResponseException(String msg) {
        super(msg);
    }

    public ResponseException(HttpStatus statusCode, String msg) {
        super(msg);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
