package com.enterprises.baca.service;

import com.enterprises.baca.model.TradeStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TradeMetricsStoreTest {

    private TradeMetricsStore store;

    @BeforeEach
    void setUp() {
        store = new TradeMetricsStore();
    }

    @Test
    void shouldStoreAndReturnMetrics() {
        store.recordTrade("STRK", 100.0, 50);
        store.recordTrade("STRK", 110.0, 150);

        Optional<TradeStats> statsOpt = store.getStats("STRK");
        assertTrue(statsOpt.isPresent());

        TradeStats stats = statsOpt.get();
        assertEquals(2, stats.recentPrices().size());
        assertEquals(50 + 150, stats.totalVolume());
        assertEquals(110.0, stats.lastPrice());
        assertEquals(105.0, stats.averagePrice(), 0.01);
    }
}