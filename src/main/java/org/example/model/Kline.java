package org.example.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class Kline {
    @NotBlank
    private String symbol;
    @NotNull
    private Long openTime;
    @NotNull
    private BigDecimal openPrice;
    @NotNull
    private BigDecimal highPrice;
    @NotNull
    private BigDecimal lowPrice;
    @NotNull
    private BigDecimal closePrice;
    @NotNull
    private Long closeTime;
    @NotNull
    private BigDecimal volume;
    @Min(0)
    private Integer numberOfTrades;
    @NotNull
    private BigDecimal takerBuyBaseAssetVolume;
    @NotNull
    private BigDecimal takerBuyQuoteAssetVolume;
}
