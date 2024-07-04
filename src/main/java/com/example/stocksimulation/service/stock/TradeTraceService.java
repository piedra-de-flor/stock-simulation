package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.entity.Member;
import com.example.stocksimulation.domain.entity.Trade;
import com.example.stocksimulation.domain.entity.TradeTrace;
import com.example.stocksimulation.dto.trade.TradeTraceDto;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.repository.TradeTraceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TradeTraceService {
    private final TradeTraceRepository repository;
    private final MemberRepository memberRepository;

    public List<TradeTraceDto> readAll(String memberEmail) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        List<TradeTrace> traces = member.getAccount().getTraces();
        List<TradeTraceDto> response = new ArrayList<>();

        for (TradeTrace trace : traces) {
            response.add(TradeTraceDto.create(trace));
        }

        return response;
    }

    public void recordTrace(Trade trade) {
        TradeTrace trace = TradeTrace.builder()
                .trade(trade)
                .build();

        repository.save(trace);
    }
}
