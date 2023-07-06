package org.example.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TargetDataResponse {
    private String checkAbandoned;
    private List<Kline> targetKline;

    public TargetDataResponse(String checkAbandoned, List<Kline> targetKline) {
        this.checkAbandoned = checkAbandoned;
        this.targetKline = targetKline;
    }
}
