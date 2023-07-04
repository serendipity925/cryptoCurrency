package org.example.repository;

import org.example.model.Kline;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RetrieveMyBatisRepository {
    public List<Kline> getKlineData();

    public List<Kline> getTargetData(String symbol, Long openTime, Long closeTime, int freq);
}
