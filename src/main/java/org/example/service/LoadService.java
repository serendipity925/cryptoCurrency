package org.example.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.model.Kline;
import org.example.repository.KlineMyBatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

@Service
@Validated
public class LoadService {
    @Autowired
    private KlineMyBatisRepository klineRepository;

    @Autowired
    private BinanceApiService service;

    @Value("${load.limit}")
    private Integer limit;

    public void load(@NotBlank String symbol, @NotNull Long startTime, @NotNull Long endTime) {
        long interval = limit * 60 * 1000L;
        LongStream.range(startTime, endTime)
                .parallel()
                .filter(s -> (s - startTime) % interval == 0)
                .forEach(s -> this.loadIndividual(symbol, s, Math.min(endTime - 1, s + interval - 1)));
    }


    private void loadIndividual(@NotBlank String symbol, @NotNull Long startTime, @NotNull Long endTime) {
        String[][] res = service.load(symbol, limit, startTime, endTime);
        List<Kline> lines = Arrays.stream(res)
                .map(line -> this.convertToKline(line, symbol))
                .toList();
        klineRepository.batchInsert(lines);
    }


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
