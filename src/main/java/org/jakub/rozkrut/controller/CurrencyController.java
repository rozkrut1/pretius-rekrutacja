package org.jakub.rozkrut.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.jakub.rozkrut.commons.dto.response.CurrencyResponseDTO;
import org.jakub.rozkrut.commons.enums.CurrencyEnum;
import org.jakub.rozkrut.service.CurrencyDispatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
class CurrencyController {

    private final CurrencyDispatcher currencyDispatcher;

    @ApiOperation(value = "Przelicza wartość waluty.",
            response = CurrencyResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sukces"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{inputCurrency}/{outputCurrency}")
    CurrencyResponseDTO genericConvert(@ApiParam(value = "Waluta wejściowa", example = "PLN") @PathVariable CurrencyEnum inputCurrency,
                                       @ApiParam(value = "Waluta wyjściowa", example = "EUR") @PathVariable CurrencyEnum outputCurrency,
                                       @ApiParam(value = "Wartość w walucie wejściowej", example = "10.0") @RequestParam("value") BigDecimal value) {
        return currencyDispatcher.convertCurrency(inputCurrency.name(), outputCurrency.name(), value);
    }

    @ApiOperation(value = "Przelicza wartość waluty. Endpoint służący gdy nie istnieje dydokwany dla danej pary walut",
            response = CurrencyResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sukces"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/generic/{inputCurrency}/{outputCurrency}")
    CurrencyResponseDTO genericConvert(@ApiParam(value = "Waluta wejściowa", example = "PLN") @PathVariable String inputCurrency,
                                       @ApiParam(value = "Waluta wyjściowa", example = "EUR") @PathVariable String outputCurrency,
                                       @ApiParam(value = "Wartość w walucie wejściowej", example = "10.0") @RequestParam("value") BigDecimal value) {
        return currencyDispatcher.convertCurrency(inputCurrency, outputCurrency, value);
    }
}
