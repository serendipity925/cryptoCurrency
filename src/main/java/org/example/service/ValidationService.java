package org.example.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.model.exception.ApiInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

@Service
@Validated
public class ValidationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${symbolUrl}")
    private String symbolUrl;

    public void validateSymbol(@NotBlank String symbol) {
        String url = symbolUrl + symbol;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        } catch (Exception ex) {
            String errorMessage = String.format(
                    "Invalid Symbol: symbol %s does not supported. Please verify your symbol", symbol
            );
            throw new ApiInputException(errorMessage);
        }
    }

    public void validateInputData(@NotNull Long startTime, @NotNull Long endTime) {
        if (startTime >= endTime) {
            String errorMessage = String.format(
                    "Entered startTime %s, endTime %s. Please enter a valid startTime/endTime.(startTime < endTime)",
                    startTime, endTime);
            throw new ApiInputException(errorMessage);
        }
    }
}
