package com.enterprises.baca.service;

import com.enterprises.baca.model.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TradeProcessorServiceTest {

    private TradeMetricsStore metricsStore;
    private TradeProcessorService processor;

    @BeforeEach
    void setUp() {
        metricsStore = mock(TradeMetricsStore.class);
        SseEmitterService emitterService = mock(SseEmitterService.class);
        processor = new TradeProcessorService(metricsStore, emitterService);
    }

    @Test
    void shouldRecordTradeWhenProcessed() {
        Trade trade = new Trade("STRK", 150.0, 100, System.currentTimeMillis());

        processor.process(trade);

        verify(metricsStore).recordTrade("STRK", 150.0, 100);
    }
}