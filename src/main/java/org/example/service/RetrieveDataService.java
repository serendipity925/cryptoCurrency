package org.example.service;

import jakarta.validation.Valid;
import org.example.model.Kline;
import org.example.repository.KlineMyBatisRepository;
import org.example.repository.RetrieveMyBatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Validated
public class RetrieveDataService implements RetrieveMyBatisRepository {

    @Autowired
    private KlineMyBatisRepository repo;


    @Override
    public List<@Valid Kline> getKlineData() {
        return repo.findAll();
    }

    private int abandonedData = 0;

    public List<@Valid Kline> getTargetData(String symbol, Long startTime, Long endTime, int frequency) {
        abandonedData = 0;
        List<Kline> defaultKline = repo.findAll();

        List<Kline> targetKline = IntStream.range(0, defaultKline.size())
                .filter(start -> start % frequency == 0)
                .mapToObj(start -> this.convertToKline(defaultKline, start, frequency))
                .collect(Collectors.toList());
        System.out.println("++++++++" + abandonedData);
        if (abandonedData != 0) {
            targetKline.remove(targetKline.size() - 1);
        }

        return targetKline;
    }

    public String getAbandonedData() {
        if (abandonedData != 0) {
            return "Number of abandoned data: " + abandonedData;
        }
        return "ALl data has been used!";
    }

    //TODO frequency out of bound bug
    protected Kline convertToKline(List<Kline> defaultKline, int i, int frequency) {

        if (i + frequency - 1 >= defaultKline.size()) {
            abandonedData += defaultKline.size() - i;
            return null;
            //  frequency = defaultKline.size() - i; // Adjust the frequency to the remaining valid data
        }

        String symbol = defaultKline.get(i).getSymbol();
        Long newOpenTime = defaultKline.get(i).getOpenTime();
        Long newCloseTime = defaultKline.get(i + frequency - 1).getCloseTime();
        BigDecimal newOpenPrice = defaultKline.get(i).getOpenPrice();
        BigDecimal newClosePrice = defaultKline.get(i + frequency - 1).getClosePrice();

        BigDecimal currHighPrice = defaultKline.get(i).getHighPrice();
        BigDecimal currLowPrice = defaultKline.get(i).getLowPrice();
        BigDecimal currVolume = defaultKline.get(i).getVolume();
        int currNumOfTrades = defaultKline.get(i).getNumberOfTrades();
        BigDecimal currTakerBase = defaultKline.get(i).getTakerBuyBaseAssetVolume();
        BigDecimal currTakerQuot = defaultKline.get(i).getTakerBuyQuoteAssetVolume();

        for (int j = 1; j < frequency; j++) {
            currHighPrice = currHighPrice.compareTo(defaultKline.get(i + j).getHighPrice()) >= 0 ?
                    currHighPrice : defaultKline.get(i + j).getHighPrice();
            currLowPrice = currLowPrice.compareTo(defaultKline.get(i + j).getLowPrice()) <= 0 ?
                    currLowPrice : defaultKline.get(i + j).getLowPrice();
            currVolume = currVolume.add(defaultKline.get(i + j).getVolume());
            currNumOfTrades += defaultKline.get(i + j).getNumberOfTrades();
            currTakerBase = currTakerBase.add(defaultKline.get(i + j).getTakerBuyBaseAssetVolume());
            currTakerQuot = currTakerQuot.add(defaultKline.get(i + j).getTakerBuyQuoteAssetVolume());
        }

        Kline kline = new Kline()
                .setSymbol(symbol)
                .setOpenTime(newOpenTime)
                .setOpenPrice(newOpenPrice)
                .setHighPrice(currHighPrice)
                .setLowPrice(currLowPrice)
                .setClosePrice(newClosePrice)
                .setCloseTime(newCloseTime)
                .setVolume(currVolume)
                .setNumberOfTrades(currNumOfTrades)
                .setTakerBuyBaseAssetVolume(currTakerBase)
                .setTakerBuyQuoteAssetVolume(currTakerQuot);

        return kline;
    }
}