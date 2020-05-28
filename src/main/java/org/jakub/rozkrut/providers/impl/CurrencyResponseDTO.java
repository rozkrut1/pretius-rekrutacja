package org.jakub.rozkrut.providers.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
class CurrencyResponseDTO {

    private String table;
    private String currency;
    private String code;
    private List<RateDTO> rates;
}
