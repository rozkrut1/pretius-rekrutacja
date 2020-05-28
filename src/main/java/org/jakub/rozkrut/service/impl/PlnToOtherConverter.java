package org.jakub.rozkrut.service.impl;

import org.jakub.rozkrut.commons.enums.CurrencyEnum;
import org.jakub.rozkrut.providers.CurrencyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
class PlnToOtherConverter implements CurrencyConverter {

    private final CurrencyProvider currencyProvider;
    private final int scale;
    private final int roundingMode;

    @Autowired
    PlnToOtherConverter(CurrencyProvider currencyProvider, @Value("${converter.scale}") int scale,
                        @Value("${converter.rounding_mode}") int roundingMode) {
        this.currencyProvider = currencyProvider;
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    @Override
    public BigDecimal convert(String inputCurrency, String outputCurrency, BigDecimal value) {
        return value.divide(currencyProvider.getAvgPrice(outputCurrency), scale, roundingMode);
    }

    @Override
    public Boolean shouldConvert(String inputValue, String outputValue) {
        return CurrencyEnum.PLN.name().equalsIgnoreCase(inputValue) && !CurrencyEnum.PLN.name().equalsIgnoreCase(outputValue);
    }
}
