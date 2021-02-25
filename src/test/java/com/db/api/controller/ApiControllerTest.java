package com.db.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenCityyURI_whenMockMVC_thenReturnsResponseStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/single?iata=LON")
                .with(SecurityMockMvcRequestPostProcessors
                        .httpBasic("user", "password")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenCityyURI_whenMockMVC_thenReturnsItsAirportDataC() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/single?iata=LON")
                .with(SecurityMockMvcRequestPostProcessors
                        .httpBasic("user", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("London")));
    }

    @Test
    public void givenCountryURI_whenMockMVC_thenReturnsResponseStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/countries?field_name=IN")
                .with(SecurityMockMvcRequestPostProcessors
                        .httpBasic("user", "password")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenCountryURIWithoutField_whenMockMVC_thenReturnsResponseStatusBad() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/countries").with(SecurityMockMvcRequestPostProcessors
                        .httpBasic("user", "password")))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void givenCountryURI_whenMockMVC_thenReturnsListOfCountriesK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/countries?field_name=IN")
                .with(SecurityMockMvcRequestPostProcessors
                        .httpBasic("user", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("United Kingdom")));
    }

    @Test
    public void givenStateURI_whenMockMVC_thenReturnsResponseStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/states?country=IN")
                .with(SecurityMockMvcRequestPostProcessors
                        .httpBasic("user", "password")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenStateURI_whenMockMVC_thenReturnsAllTheStatesK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/states?country=IN")
                .with(SecurityMockMvcRequestPostProcessors
                        .httpBasic("user", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Maharashtra")));
    }

    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/states?country=IN")
                .with(SecurityMockMvcRequestPostProcessors
                        .httpBasic("user", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWithOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/states?country=IN")
                .with(SecurityMockMvcRequestPostProcessors
                        .httpBasic("user", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoAuthRequestOnPrivateService_shouldBeUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/states?country=IN")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenWrongAuthRequestOnPrivateService_shouldBeUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/states?country=IN")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("bad", "credentials"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}