package com.example.stocksimulation.domain.entity;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Stock {
    @Id
    private String code;
    private long price;
    private String name;

    public void updatePrice(long price) {
        this.price = price;
    }

    @Builder
    public Stock(String code, long price, String name) {
        this.code= code;
        this.price = price;
        this.name = name;
    }
}
