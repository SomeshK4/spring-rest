package com.db.api.controller;

import com.db.api.constant.ApiConstants;
import com.db.api.exception.ApiErrorHandler;
import com.db.api.service.ApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.db.api.constant.ApiConstants.GET_AIRPORT_SUCCESS;
import static com.db.api.constant.ApiConstants.IATA;
import static com.db.api.constant.ApiConstants.INVALID_MESSAGE;
import static com.db.api.constant.ApiConstants.NOT_FOUND;
import static com.db.api.constant.ApiConstants.SYSTEM_ERROR;

@RestController
@RequestMapping("/api/v1")
@Api(value = "List of APIs to fetchh airport, countries and states")
@RequiredArgsConstructor
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiErrorHandler.class);
    private final ApiService apiService;

    @ApiOperation(value = ApiConstants.GET_AIRPORT)
    @ApiResponses(value = {@ApiResponse(code = 200, message = GET_AIRPORT_SUCCESS, response = String.class),
            @ApiResponse(code = 400, message = NOT_FOUND, response = String.class),
            @ApiResponse(code = 404, message = INVALID_MESSAGE, response = String.class),
            @ApiResponse(code = 500, message = SYSTEM_ERROR, response = String.class)})
    @GetMapping(value = "/single", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getAirport(@ApiParam(value = IATA, required = true) @RequestParam String iata) {
        logger.info("Get Airport info for : {}", iata);
        return apiService.remoteRestCall("single?iata=" + iata);
    }

    @ApiOperation(value = ApiConstants.GET_COUNTRY)
    @ApiResponses(value = {@ApiResponse(code = 200, message = ApiConstants.GET_COUNTRY_SUCCESS, response = String.class),
            @ApiResponse(code = 400, message = ApiConstants.NOT_FOUND, response = String.class),
            @ApiResponse(code = 404, message = ApiConstants.INVALID_MESSAGE, response = String.class),
            @ApiResponse(code = 500, message = ApiConstants.SYSTEM_ERROR, response = String.class)})
    @GetMapping(value = "/countries", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getCountry(@ApiParam(value = ApiConstants.FIELD_NAME, required = true) @RequestParam String field_name) {
        logger.info("Get Countries info for : {}", field_name);
        return apiService.remoteRestCall("countries?field_name=" + field_name);
    }

    @ApiOperation(value = ApiConstants.GET_STATES)
    @ApiResponses(value = {@ApiResponse(code = 200, message = ApiConstants.GET_STATE_SUCCESS, response = String.class),
            @ApiResponse(code = 400, message = ApiConstants.NOT_FOUND, response = String.class),
            @ApiResponse(code = 404, message = ApiConstants.INVALID_MESSAGE, response = String.class),
            @ApiResponse(code = 500, message = ApiConstants.SYSTEM_ERROR, response = String.class)})
    @GetMapping(value = "/states", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getStates(@ApiParam(value = ApiConstants.COUNTRY, required = true) @RequestParam String country) {
        logger.info("Get States info for country name : {}", country);
        return apiService.remoteRestCall("states?country=" + country);
    }
}

