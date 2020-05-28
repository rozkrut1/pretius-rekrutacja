package org.jakub.rozkrut.service.impl;

import org.jakub.rozkrut.service.CurrencyDispatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyDispatcherImplTest {

    @Mock
    private OtherToOtherConverter otherToOtherConverter;
    @Mock
    private OtherToPlnConverter otherToPlnConverter;
    @Mock
    private PlnToOtherConverter plnToOtherConverter;

    private CurrencyDispatcher currencyDispatcher;

    @Before
    public void setUp() {
        this.currencyDispatcher = new CurrencyDispatcherImpl(Arrays.asList(otherToOtherConverter, otherToPlnConverter, plnToOtherConverter));
        Mockito.when(otherToOtherConverter.shouldConvert(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Mockito.when(otherToPlnConverter.shouldConvert(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Mockito.when(plnToOtherConverter.shouldConvert(Mockito.any(), Mockito.any())).thenCallRealMethod();
    }

    @Test
    public void testOtherToOtherStrategy() {
        final String inputCurrency = "EUR";
        final String outputCurrency = "USD";
        final BigDecimal value = BigDecimal.TEN;

        currencyDispatcher.convertCurrency(inputCurrency, outputCurrency, value);

        Mockito.verify(otherToOtherConverter).convert(inputCurrency, outputCurrency, value);
    }

    @Test
    public void testOtherToPlnStrategy() {
        final String inputCurrency = "EUR";
        final String outputCurrency = "PLN";
        final BigDecimal value = BigDecimal.TEN;

        currencyDispatcher.convertCurrency(inputCurrency, outputCurrency, value);

        Mockito.verify(otherToPlnConverter).convert(inputCurrency, outputCurrency, value);
    }

    @Test
    public void testPlnToOtherStrategy() {
        final String inputCurrency = "PLN";
        final String outputCurrency = "EUR";
        final BigDecimal value = BigDecimal.TEN;

        currencyDispatcher.convertCurrency(inputCurrency, outputCurrency, value);

        Mockito.verify(plnToOtherConverter).convert(inputCurrency, outputCurrency, value);
    }

    @Test
    public void testSameCurrencies() {
        final String inputCurrency = "PLN";
        final String outputCurrency = "PLN";
        final BigDecimal value = BigDecimal.TEN;

        Assert.assertEquals(value, currencyDispatcher.convertCurrency(inputCurrency, outputCurrency, value).getOutputValue());
    }



}