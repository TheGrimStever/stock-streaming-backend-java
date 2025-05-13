package com.enterprises.baca.service;

import com.enterprises.baca.model.TradeStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TradeRankingServiceTest {

    private TradeMetricsStore store;
    private TradeRankingService rankingService;

    @BeforeEach
    void setUp() {
        store = mock(TradeMetricsStore.class);
        rankingService = new TradeRankingService(store);
    }

    @Test
    void shouldRankByVolumeDescending() {
        when(store.getTrackedTickers()).thenReturn(Set.of("AAA", "BBB", "CCC"));
        when(store.getStats("AAA")).thenReturn(Optional.of(new TradeStats(100.0, 200, 105.0, new ArrayDeque<>())));
        when(store.getStats("BBB")).thenReturn(Optional.of(new TradeStats(90.0, 1000, 92.0, new ArrayDeque<>())));
        when(store.getStats("CCC")).thenReturn(Optional.of(new TradeStats(50.0, 500, 51.0, new ArrayDeque<>())));

        Map<String, TradeStats> result = rankingService.rankBy(TradeStats::totalVolume, 2, false);
        assertEquals(List.of("BBB", "CCC"), new ArrayList<>(result.keySet()));
    }
}