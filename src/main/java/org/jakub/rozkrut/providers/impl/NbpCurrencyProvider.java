package org.jakub.rozkrut.providers.impl;

import lombok.extern.slf4j.Slf4j;
import org.jakub.rozkrut.exceptions.BusinessException;
import org.jakub.rozkrut.providers.CurrencyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Slf4j
public class NbpCurrencyProvider implements CurrencyProvider {

    //{table}/{code}
    private static final String EXCHANGE_URL = "exchangerates/rates/%s/%s";
    private static final String MAIN_TABLE = "A";
    private static final String SECONDARY_TABLE = "B";
    private final String url;

    @Autowired
    public NbpCurrencyProvider(@Value("${providers.nbp.url}") String url) {
        this.url = url;
    }

    @Override
    @Cacheable(value = "nbpAvgPrice", key = "#currency")
    public BigDecimal getAvgPrice(String currency) {
        try {
            return getPrice(currency, MAIN_TABLE);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return getPrice(currency, SECONDARY_TABLE);
        }
    }

    private BigDecimal getPrice(String currency, String table) {
        return Optional.ofNullable(new RestTemplate().getForEntity(String.format(url + EXCHANGE_URL, table, currency), CurrencyResponseDTO.class)
                .getBody()).map(c -> c.getRates().stream().map(RateDTO::getMid).findFirst().orElseThrow(
                () -> new BusinessException(String.format("Nie znaleziono ceny w tabeli %s", table))))
                .orElseThrow(() -> new BusinessException(String.format("Nie znaleziono ceny w tabeli %s", table)));
    }
}
