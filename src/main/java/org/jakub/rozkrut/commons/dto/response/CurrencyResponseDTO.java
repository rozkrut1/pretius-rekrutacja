package org.jakub.rozkrut.commons.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CurrencyResponseDTO {

    @ApiModelProperty(value = "Waluta wejściowa")
    private String inputCurrency;
    @ApiModelProperty(value = "Wartość waluta wejściowa")
    private BigDecimal inputValue;
    @ApiModelProperty(value = "Waluta wyjściowa")
    private String outputCurrency;
    @ApiModelProperty(value = "Wartość waluty wyjściowej")
    private BigDecimal outputValue;
}
