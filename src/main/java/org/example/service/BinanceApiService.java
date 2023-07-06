package org.example.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${load.limit}")
    private Integer limit;


    public String[][] load(@NotEmpty String symbol, @NotNull int limit, @NotNull long startTime, @NotNull long endTime) {
        String url = String.format(
                loadUrl, symbol, startTime, endTime, limit
        );
        ResponseEntity<String[][]> response = restTemplate.getForEntity(url, String[][].class);
        return response.getBody();
    }
}


