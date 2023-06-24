package org.example.repository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.*;
import org.example.model.Kline;

import java.util.List;


@Mapper
public interface KlineMyBatisRepository {
    @Select("select * from kline_time_price")
    public List < Kline > findAll();

    @Select("SELECT * FROM kline_time_price WHERE symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public Kline findBySymbolStartEnd(@NotBlank String symbol, @NotNull Long openTime, @NotNull Long closeTime);

    @Delete("DELETE FROM kline_time_price WHERE symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public int deleteBySymbolStartEnd(@NotBlank String symbol, @NotNull Long openTime, @NotNull Long closeTime);

    @Insert("INSERT INTO kline_time_price(symbol, open_time, open_price, high_price, low_price, close_price, close_time, volume, number_of_trades, taker_buy_base_asset_volume, taker_buy_quote_asset_volume) " +
            " VALUES (#{symbol}, #{openTime}, #{openPrice}, #{highPrice}, #{lowPrice}, #{closePrice}, #{closeTime}, #{volume}, #{numberOfTrades}, #{takerBuyBaseAssetVolume}, #{takerBuyQuoteAssetVolume})")
    public int insert(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set open_price=#{openPrice} where symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public int updateOpenPrice(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set high_price=#{highPrice} where symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public int updateHighPrice(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set low_price=#{lowPrice} where symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public int updateLowPrice(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set close_price=#{closePrice} where symbol = #{symbol} AND open_ime = #{openTime} AND close_time = #{closeTime}")
    public int updateClosePrice(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set volume=#{volume} where symbol = #{symbol} AND open_time = #{open_time} AND end_time = #{closeTime}")
    public int updateVolume(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set number_of_trades=#{numberOfTrades} where symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public int updateNumOfTrades(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set taker_buy_base_asset_volume=#{takerBuyBaseAssetVolume} where symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public int updateTakerBuyBaseAssetVolume(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set taker_bu_quote_asset_volume=#{takerBuyQuoteAssetVolume} where symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public int updateTakerBuyQuoteAssetVolume(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set symbol=#{symbol} where symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public int updateSymbol(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set open_time=#{openTime} where symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public int updateStartTime(@NotNull @Valid Kline kline);

    @Update("Update kline_time_price set close_time=#{closeTime} where symbol = #{symbol} AND open_time = #{openTime} AND close_time = #{closeTime}")
    public int updateEndTime(@NotNull @Valid Kline kline);

    @Insert({
            "<script>",
            "INSERT INTO kline_time_price(symbol, open_time, open_price, high_price, low_price, close_price, close_time, volume, number_of_trades, taker_buy_base_asset_volume, taker_buy_quote_asset_volume) ",
            "values ",
            "<foreach  collection='klineList' item='Kline' separator=','>",
            "( #{Kline.symbol}, #{Kline.openTime}, " +
                    "#{Kline.openPrice}, #{Kline.highPrice},#{Kline.lowPrice}," +
                    "#{Kline.closePrice},#{Kline.closeTime},#{Kline.volume},#{Kline.numberOfTrades}," +
                    "#{Kline.takerBuyBaseAssetVolume},#{Kline.takerBuyQuoteAssetVolume})",
            "</foreach>",
            "</script>"
    })
    public int batchInsert(@Param("klineList")@NotEmpty List<@Valid Kline> klineList);
}
