package org.jakub.rozkrut.providers;

import java.math.BigDecimal;

public interface CurrencyProvider {

    BigDecimal getAvgPrice(String currency);

}