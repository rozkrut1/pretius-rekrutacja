package org.jakub.rozkrut.service.impl;

import org.jakub.rozkrut.commons.enums.CurrencyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
class OtherToOtherConverter implements CurrencyConverter {

    private final CurrencyConverter plnToOtherConverter;
    private final CurrencyConverter otherToPlnConverter;

    @Autowired
    OtherToOtherConverter(@Qualifier("plnToOtherConverter") CurrencyConverter plnToOtherConverter,
                          @Qualifier("otherToPlnConverter") CurrencyConverter otherToPlnConverter) {
        this.plnToOtherConverter = plnToOtherConverter;
        this.otherToPlnConverter = otherToPlnConverter;
    }

    @Override
    public BigDecimal convert(String inputCurrency, String outputCurrency, BigDecimal value) {
        return plnToOtherConverter.convert(
                CurrencyEnum.PLN.name(),
                outputCurrency,
                otherToPlnConverter.convert(
                        inputCurrency,
                        CurrencyEnum.PLN.name(),
                        value
                ));
    }

    @Override
    public Boolean shouldConvert(String inputValue, String outputValue) {
        return !CurrencyEnum.PLN.name().equalsIgnoreCase(inputValue) && !CurrencyEnum.PLN.name().equalsIgnoreCase(outputValue);
    }
}
