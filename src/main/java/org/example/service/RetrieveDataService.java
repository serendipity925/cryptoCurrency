package org.example.service;

import org.apache.ibatis.annotations.Mapper;
import org.example.DBUtil.DBUtil;
import org.example.model.Kline;
import org.example.repository.KlineMyBatisRepository;
import org.example.repository.RetrieveMyBatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@Mapper
public class RetrieveDataService implements RetrieveMyBatisRepository{
    @Autowired
    static List<Kline> klineList = new ArrayList<>();

    Connection connection;

    public RetrieveDataService() throws SQLException {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<Kline> getKlineData() {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM kline_time_price"
            );
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Kline kline = new Kline()
                        .setSymbol(rs.getString(1))
                        .setOpenTime(Long.parseLong(rs.getString(2)))
                        .setOpenPrice(new BigDecimal(rs.getString(3)))
                        .setHighPrice(new BigDecimal(rs.getString(4)))
                        .setLowPrice(new BigDecimal(rs.getString(5)))
                        .setClosePrice(new BigDecimal(rs.getString(6)))
                        .setCloseTime(Long.parseLong(rs.getString(7)))
                        .setVolume(new BigDecimal(rs.getString(8)))
                        .setNumberOfTrades(Integer.parseInt(rs.getString(9)))
                        .setTakerBuyBaseAssetVolume(new BigDecimal(rs.getString(10)))
                        .setTakerBuyQuoteAssetVolume(new BigDecimal(rs.getString(11)));
                klineList.add(kline);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return klineList;
    }
}