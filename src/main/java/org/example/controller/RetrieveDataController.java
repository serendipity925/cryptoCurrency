package org.example.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.model.Kline;
import org.example.model.TargetDataResponse;
import org.example.repository.RetrieveMyBatisRepository;
import org.example.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class RetrieveDataController {
    @Autowired
    private RetrieveMyBatisRepository retrieveService;

    @Autowired
    private ValidationService validationService;

    @GetMapping("/getData")
    public @NotNull List<Kline> getData() {
        return this.retrieveService.getKlineData();
    }

    @GetMapping("/targetData/symbol/{symbol}/startTime/{startTime}/endTime/{endTime}/freq/{freq}")
    public TargetDataResponse getTargetData(@PathVariable("symbol") @NotEmpty String symbol,
                                            @PathVariable("startTime") @NotNull Long startTime,
                                            @PathVariable("endTime") @NotNull Long endTime,
                                            @PathVariable("freq") @NotNull int freq) {
        validationService.validateSymbol(symbol);
        validationService.validateInputData(startTime, endTime);
        List<Kline> targetKline = this.retrieveService.getTargetData(symbol, startTime, endTime, freq);
        return new TargetDataResponse(this.retrieveService.getAbandonedData(), targetKline);
    }
}
