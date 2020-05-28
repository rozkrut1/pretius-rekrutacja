package org.jakub.rozkrut.service.impl;

import junitparams.JUnitParamsRunner;
import org.jakub.rozkrut.providers.CurrencyProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

@RunWith(JUnitParamsRunner.class)
public class PlnToOtherConverterTest extends AbstractCurrencyConverterTest {

    @Mock
    private CurrencyProvider currencyProvider;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currencyConverter = new PlnToOtherConverter(currencyProvider, 4, 5);
    }

    @Override
    @Test
    public void testConversion() {
        final String inputCurrency = "PLN";
        final String outputCurrency = "EUR";
        final BigDecimal value = BigDecimal.TEN;
        final BigDecimal expected = BigDecimal.valueOf(2, 4);

        Mockito.when(currencyProvider.getAvgPrice(outputCurrency)).thenReturn(value.divide(expected, 4, 5));

        Assert.assertEquals(expected, currencyConverter.convert(inputCurrency, outputCurrency, value));
    }

    @Override
    protected Object[] params() {
        return new Object[] {
                new Object[] {"PLN", "PLN", false},
                new Object[] {"EUR", "PLN", false},
                new Object[] {"EUR", "USD", false},
                new Object[] {"PLN", "EUR", true}
        };
    }
}