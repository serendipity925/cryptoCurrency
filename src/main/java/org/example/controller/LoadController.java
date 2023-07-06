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

        long startTime = coinRequestInfo.getStartTime();
        long endTime = coinRequestInfo.getEndTime();
        String symbol = coinRequestInfo.getSymbol();

        validationService.validateSymbol(symbol);
        validationService.validateInputData(startTime, endTime);


        service.load(symbol, startTime, endTime);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

//TODO: Retrieve controller, get mapping, input(symbol, startTime, endTime, frequency)
// frequency: default 1min, 可选
//EC2