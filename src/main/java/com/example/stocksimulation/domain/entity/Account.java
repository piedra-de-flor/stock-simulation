package com.example.stocksimulation.domain.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long money;
    @OneToMany
    private Map<Stock, Integer> stocks;
}
