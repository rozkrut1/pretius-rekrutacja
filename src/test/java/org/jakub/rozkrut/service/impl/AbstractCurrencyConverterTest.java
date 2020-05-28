package org.jakub.rozkrut.service.impl;

import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractCurrencyConverterTest {

    protected CurrencyConverter currencyConverter;

    @Test
    @Parameters(method = "params")
    public void testShouldConvert(String inputCurrency, String outputCurrency, Boolean expected) {
        Assert.assertEquals(expected, currencyConverter.shouldConvert(inputCurrency, outputCurrency));
    }

    protected abstract Object[] params();

    public abstract void testConversion();
}
