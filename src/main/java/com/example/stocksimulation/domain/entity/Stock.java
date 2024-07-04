package com.example.stocksimulation.domain.entity;

import com.example.stocksimulation.dto.stock.StockDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Stock {
    @Id
    private String code;
    private long price;
    private String name;

    public void updatePrice(int price) {
        this.price = price;
    }

    @Builder
    public Stock(String code, int price, String name) {
        this.code= code;
        this.price = price;
        this.name = name;
    }
}
