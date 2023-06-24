package org.example.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.model.CoinRequest;
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
public class BinanceApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${load.url}")
    private String loadUrl;

    public String[][] load(@NotNull @Valid CoinRequest request) {
        String url = String.format(
                loadUrl, request.getSymbol(), request.getStartTime(), request.getEndTime()
        );
        System.out.println(url);
        ResponseEntity<String[][]> response = restTemplate.getForEntity(url, String[][].class);
        return response.getBody();
    }
}


