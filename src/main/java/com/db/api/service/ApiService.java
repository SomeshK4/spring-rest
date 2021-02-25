package com.db.api.service;

import com.db.api.exception.ApiErrorHandler;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiErrorHandler.class);

    @Value("${remote.base.url}")
    private String remoteBaseUrl;

    @Value("${remote.api.key}")
    private String authKey;

    @Value("${remote.api.secret}")
    private String authSecret;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> remoteRestCall(String appendToBaseUrl) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("APC-Auth", authKey);
            headers.set("APC-Auth-Secret", authSecret);
            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
            URI uri = new URI(remoteBaseUrl + appendToBaseUrl);
            logger.info("Before calling remote URL : {} Headers : {}", uri, headers);
            return restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
        } catch (URISyntaxException e) {
            logger.error("Exception occurred while calling remote URL {1}", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}