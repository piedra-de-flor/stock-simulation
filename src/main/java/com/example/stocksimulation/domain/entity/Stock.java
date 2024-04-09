package com.example.stocksimulation.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stock {
    @Id
    private String code;
    private int price;
    private String name;

    public void updatePrice(int price) {
        this.price = price;
    }
}
