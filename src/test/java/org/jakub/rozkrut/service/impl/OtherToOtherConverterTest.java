package org.jakub.rozkrut.service.impl;

import junitparams.JUnitParamsRunner;
import org.jakub.rozkrut.commons.enums.CurrencyEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

@RunWith(JUnitParamsRunner.class)
public class OtherToOtherConverterTest extends AbstractCurrencyConverterTest {

    @Mock
    private OtherToPlnConverter otherToPlnConverter;
    @Mock
    private PlnToOtherConverter plnToOtherConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currencyConverter = new OtherToOtherConverter(plnToOtherConverter, otherToPlnConverter);
    }

    @Test
    @Override
    public void testConversion() {
        final String inputCurrency = "EUR";
        final String outputCurrency = "USD";
        final BigDecimal value = BigDecimal.TEN;
        final BigDecimal eurToPlnValue = BigDecimal.valueOf(40);
        final BigDecimal plnToUsd = BigDecimal.valueOf(13);

        Mockito.when(otherToPlnConverter.convert(inputCurrency, CurrencyEnum.PLN.name(), value)).thenReturn(eurToPlnValue);
        Mockito.when(plnToOtherConverter.convert(CurrencyEnum.PLN.name(), outputCurrency, eurToPlnValue)).thenReturn(plnToUsd);

        Assert.assertEquals(plnToUsd, currencyConverter.convert(inputCurrency, outputCurrency, value));
    }

    @Override
    protected Object[] params() {
        return new Object[] {
                new Object[] {"PLN", "PLN", false},
                new Object[] {"PLN", "EUR", false},
                new Object[] {"EUR", "PLN", false},
                new Object[] {"EUR", "USD", true}
        };
    }
}