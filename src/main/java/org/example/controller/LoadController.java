package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.model.CoinRequest;
import org.example.model.exception.ApiInputException;
import org.example.service.LoadService;
import org.example.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
        service.load(coinRequestInfo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}