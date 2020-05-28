package org.jakub.rozkrut.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.jakub.rozkrut.StandaloneApplication;
import org.jakub.rozkrut.commons.dto.response.CurrencyResponseDTO;
import org.jakub.rozkrut.exceptions.BusinessException;
import org.jakub.rozkrut.providers.CurrencyProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class CurrencyControllerTestIT {

    @MockBean
    @Autowired
    private CurrencyProvider currencyProvider;

    @LocalServerPort
    private int port;

    @Test
    public void testEnumEndpoint() {
        Map<String, String> params = new HashMap<>();
        params.put("value", BigDecimal.TEN.toString());

        Mockito.when(currencyProvider.getAvgPrice("EUR")).thenReturn(BigDecimal.valueOf(2));

        CurrencyResponseDTO response = RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/currency/PLN/EUR?value=10")
                .body().as(CurrencyResponseDTO.class);

        Assert.assertEquals("PLN", response.getInputCurrency());
        Assert.assertEquals(BigDecimal.TEN, response.getInputValue());
        Assert.assertEquals("EUR", response.getOutputCurrency());
        Assert.assertEquals(BigDecimal.valueOf(5).setScale(4), response.getOutputValue());
    }

    @Test
    public void testGenericEndpoint() {
        Map<String, String> params = new HashMap<>();
        params.put("value", BigDecimal.TEN.toString());

        Mockito.when(currencyProvider.getAvgPrice("EUR")).thenReturn(BigDecimal.valueOf(2));

        CurrencyResponseDTO response = RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/currency/generic/PLN/EUR?value=10")
                .body().as(CurrencyResponseDTO.class);

        Assert.assertEquals("PLN", response.getInputCurrency());
        Assert.assertEquals(BigDecimal.TEN, response.getInputValue());
        Assert.assertEquals("EUR", response.getOutputCurrency());
        Assert.assertEquals(BigDecimal.valueOf(5).setScale(4), response.getOutputValue());
    }

    @Test
    public void testIfBusinessExceptionReturns400() {
        Mockito.when(currencyProvider.getAvgPrice("EUR")).thenThrow(new BusinessException(""));

        RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/currency/generic/PLN/EUR?value=10")
                .then()
                .statusCode(400);
    }

}