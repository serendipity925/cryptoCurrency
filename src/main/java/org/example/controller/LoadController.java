package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.model.CoinRequest;
import org.example.model.Kline;
import org.example.repository.RetrieveMyBatisRepository;
import org.example.service.LoadService;
import org.example.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
public class LoadController {
    @Autowired
    private LoadService service;

    @Autowired
    private ValidationService validationService;

    @PostMapping("/loadData")
    public ResponseEntity<String> loadData(@RequestBody @NotNull @Valid CoinRequest coinRequestInfo) {
        validationService.validateSymbol(coinRequestInfo.getSymbol());
        validationService.validateInputData(coinRequestInfo.getStartTime(), coinRequestInfo.getEndTime());

        long startTime = coinRequestInfo.getStartTime();
        long endTime = coinRequestInfo.getEndTime();
        String symbol = coinRequestInfo.getSymbol();

        service.load(symbol, startTime, endTime);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Autowired
    private RetrieveMyBatisRepository retrieveService;

    @GetMapping("/getData")
    public List<Kline> getData(){
        return this.retrieveService.getKlineData();
    }

    @GetMapping("/targetData/symbol/{symbol}/startTime/{startTime}/endTime/{endTime}/freq/{freq}")
    public List<Kline> getTargetData(@PathVariable("symbol") @NotEmpty String symbol,
                                     @PathVariable("startTime") @NotEmpty String startTime,
                                     @PathVariable("endTime") @NotEmpty String endTime,
                                     @PathVariable("freq") @NotNull int freq){
        return this.retrieveService.getTargetData(symbol, Long.parseLong(startTime), Long.parseLong(endTime), freq);
    }

}

//TODO: Retrieve controller, get mapping, input(symbol, startTime, endTime, frequency)
// frequency: default 1min, 可选
//EC2