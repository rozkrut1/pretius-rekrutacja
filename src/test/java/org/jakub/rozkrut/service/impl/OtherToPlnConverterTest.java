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
public class OtherToPlnConverterTest extends AbstractCurrencyConverterTest {

    @Mock
    private CurrencyProvider currencyProvider;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currencyConverter = new OtherToPlnConverter(currencyProvider, 4, 5);
    }

    @Override
    @Test
    public void testConversion() {
        final String inputCurrency = "EUR";
        final String outputCurrency = "PLN";
        final BigDecimal value = BigDecimal.TEN;
        final BigDecimal expected = BigDecimal.valueOf(40, 4);

        Mockito.when(currencyProvider.getAvgPrice(inputCurrency)).thenReturn(expected.divide(BigDecimal.TEN, 4, 5));

        Assert.assertEquals(expected, currencyConverter.convert(inputCurrency, outputCurrency, value));
    }

    @Override
    protected Object[] params() {
        return new Object[] {
                new Object[] {"PLN", "PLN", false},
                new Object[] {"PLN", "EUR", false},
                new Object[] {"EUR", "USD", false},
                new Object[] {"EUR", "PLN", true}
        };
    }
}