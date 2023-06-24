package org.example.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CoinRequest {
    @NotBlank
    private String symbol;
    @NotNull
    private Long startTime;
    @NotNull
    private Long endTime;

}
