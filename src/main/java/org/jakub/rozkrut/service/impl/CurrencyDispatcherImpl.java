package org.jakub.rozkrut.service.impl;

import lombok.RequiredArgsConstructor;
import org.jakub.rozkrut.commons.dto.response.CurrencyResponseDTO;
import org.jakub.rozkrut.exceptions.BusinessException;
import org.jakub.rozkrut.service.CurrencyDispatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyDispatcherImpl implements CurrencyDispatcher {

    private final List<CurrencyConverter> converters;

    @Override
    public CurrencyResponseDTO convertCurrency(String inputCurrency, String outputCurrency, BigDecimal value) {

        final BigDecimal result;
        if (!inputCurrency.equalsIgnoreCase(outputCurrency)) {
            result = converters.stream().filter(c -> c.shouldConvert(inputCurrency, outputCurrency)).findFirst()
                    .orElseThrow(() ->
                            new BusinessException(String.format("Nie prawidłowe zapytanie! Nie jest możliwa konwersja z %s do %s",
                                    inputCurrency, outputCurrency)))
                    .convert(inputCurrency, outputCurrency, value);
        } else {
            result = value;
        }

        return CurrencyResponseDTO.builder()
                .inputCurrency(inputCurrency)
                .inputValue(value)
                .outputCurrency(outputCurrency)
                .outputValue(result)
                .build();
    }
}
