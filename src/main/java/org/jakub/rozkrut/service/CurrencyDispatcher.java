package org.jakub.rozkrut.service;

import org.jakub.rozkrut.commons.dto.response.CurrencyResponseDTO;

import java.math.BigDecimal;

public interface CurrencyDispatcher {

    CurrencyResponseDTO convertCurrency(String inputCurrency, String outputCurrency, BigDecimal value);
}
