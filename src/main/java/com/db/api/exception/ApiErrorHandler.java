package com.db.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class ApiErrorHandler implements ResponseErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        if (response.getStatusCode().series() == SERVER_ERROR) {
            // handle 5xx errors
            // raw http status code e.g `500`
            logger.error(String.valueOf(response.getRawStatusCode()));

            // http status code e.g. `500 INTERNAL_SERVER_ERROR`
            logger.error(String.valueOf(response.getStatusCode()));

        } else if (response.getStatusCode().series() == CLIENT_ERROR) {
            // handle 4xx errors
            // raw http status code e.g `404`
            logger.error(String.valueOf(response.getRawStatusCode()));

            // http status code e.g. `404 NOT_FOUND`
            logger.error(String.valueOf(response.getStatusCode()));

            // get response body
            logger.error(String.valueOf(response.getBody()));

            // get http headers
            HttpHeaders headers = response.getHeaders();
            logger.error(String.valueOf(headers.get("Content-Type")));
            logger.error(String.valueOf(headers.get("Server")));
        }
    }
}
