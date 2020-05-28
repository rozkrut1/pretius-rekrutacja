package org.jakub.rozkrut.service.impl;

import java.math.BigDecimal;

interface CurrencyConverter {

    BigDecimal convert(String inputCurrency, String outputCurrency, BigDecimal value);

    Boolean shouldConvert(String inputValue, String outputValue);
}
