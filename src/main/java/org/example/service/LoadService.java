package org.example.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.model.CoinRequest;
import org.example.model.Kline;
import org.example.repository.KlineMyBatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Validated
public class LoadService {
    @Autowired
    private KlineMyBatisRepository klineRepository;

    @Autowired
    private BinanceApiService service;

    @Value("${load.limit}")
    private Integer limit;
    public void load(@NotNull @Valid CoinRequest coinRequestInfo) {
        long startTime = coinRequestInfo.getStartTime();
        long endTime = coinRequestInfo.getEndTime();

        List<Kline> lines = new ArrayList<>();

        while (true) {
            String[][] res = service.load(coinRequestInfo, limit, startTime, endTime);
            lines.addAll(Arrays.stream(res)
                    .parallel()
                    .map(line -> this.convertToKline(line, coinRequestInfo.getSymbol()))
                    .toList());

            if (res.length < limit) {
                break;  // Break the loop if the response contains less than the limit
            }

            // Adjust the start time for the next request
            startTime = Long.parseLong(res[res.length - 1][0]) + 1; // Add 1 millisecond to the last timestamp
        }

        System.out.println(lines.size());
        klineRepository.batchInsert(lines);
    }

    /*public void load(@NotNull @Valid CoinRequest coinRequestInfo) {
        String[][] res = service.load(coinRequestInfo);



        List<Kline> lines = Arrays.stream(res)
                .parallel()
                .map(line -> this.convertToKline(line, coinRequestInfo.getSymbol()))
                .toList();
        System.out.println(lines.size());
        klineRepository.batchInsert(lines);
    }
    */


    protected Kline convertToKline(String[] line, String symbol) {
        return new Kline()
                .setSymbol(symbol)
                .setOpenTime(Long.parseLong(line[0]))
                .setOpenPrice(new BigDecimal(line[1]))
                .setHighPrice(new BigDecimal(line[2]))
                .setLowPrice(new BigDecimal(line[3]))
                .setClosePrice(new BigDecimal(line[4]))
                .setCloseTime(Long.parseLong(line[6]))
                .setVolume(new BigDecimal(line[5]))
                .setNumberOfTrades(Integer.parseInt(line[8]))
                .setTakerBuyBaseAssetVolume(new BigDecimal(line[9]))
                .setTakerBuyQuoteAssetVolume(new BigDecimal(line[10]));
    }
}
