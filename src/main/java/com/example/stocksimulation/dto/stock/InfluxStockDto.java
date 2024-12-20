package com.example.stocksimulation.dto.stock;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.time.Instant;

@Measurement(name = "stock_price")
public class InfluxStockDto {
    @Column(tag = true)
    private String stockCode;
    @Column
    private double price;
    @Column(timestamp = true)
    private Instant time;

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
