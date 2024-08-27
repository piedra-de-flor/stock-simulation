package com.example.stocksimulation.domain.vo.trade;

import com.example.stocksimulation.domain.entity.Account;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Trade {
    protected String stockName;
    protected String stockCode;
    protected int quantity;

    public void proceed(Account account, long price) {
    }

    public void buy(int quantity) {
        this.quantity += quantity;
    }

    public void sell(int quantity) {
        this.quantity -= quantity;
    }
}
