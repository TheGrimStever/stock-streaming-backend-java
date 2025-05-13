package com.enterprises.baca.service;

import com.enterprises.baca.model.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeProcessorService {

    private final TradeMetricsStore metricsStore;
    private final SseEmitterService emitterService;

    public void process(Trade trade) {
        metricsStore.recordTrade(trade.ticker(), trade.price(), trade.volume());
        emitterService.publish(trade);
    }
}
