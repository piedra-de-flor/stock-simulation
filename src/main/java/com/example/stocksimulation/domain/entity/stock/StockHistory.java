package com.example.stocksimulation.domain.entity.stock;

import com.example.stocksimulation.domain.vo.StockDetail;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StockHistory {
    @Id
    private String stockCode;
    private LocalDateTime time;
    @Embedded
    private StockDetail detail;

    @Builder
    public StockHistory(String stockCode, StockDetail stockDetail) {
        this.stockCode = stockCode;
        this.time = LocalDateTime.now();
        this.detail = stockDetail;
    }
}
