package com.enterprises.baca.controller;

import com.enterprises.baca.model.TradeStats;
import com.enterprises.baca.service.TradeMetricsStore;
import com.enterprises.baca.service.TradeRankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeMetricsController.class)
class TradeMetricsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeMetricsStore metricsStore;

    @MockitoBean
    private TradeRankingService rankingService;

    @Test
    void getMetricsForTicker_shouldReturn200WithStats() throws Exception {
        TradeStats stats = new TradeStats(105.0, 1000, 110.0, new ArrayDeque<>(List.of(100.0, 110.0)));
        when(metricsStore.getStats("STRK")).thenReturn(Optional.of(stats));

        mockMvc.perform(get("/metrics/STRK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averagePrice").value(105.0))
                .andExpect(jsonPath("$.totalVolume").value(1000));
    }

    @Test
    void getMetricsForTicker_shouldReturn404IfNotFound() throws Exception {
        when(metricsStore.getStats("ZZZ")).thenReturn(Optional.empty());

        mockMvc.perform(get("/metrics/ZZZ"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRankedByVolume_shouldReturnSortedResults() throws Exception {
        Map<String, TradeStats> mockRanking = new LinkedHashMap<>();
        mockRanking.put("AAA", new TradeStats(100.0, 500, 105.0, new ArrayDeque<>()));
        mockRanking.put("BBB", new TradeStats(90.0, 300, 95.0, new ArrayDeque<>()));

        when(rankingService.rankBy(any(Function.class), eq(2), eq(false))).thenReturn(mockRanking);

        mockMvc.perform(get("/metrics/rank/volume?limit=2&order=desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.AAA.totalVolume").value(500))
                .andExpect(jsonPath("$.BBB.totalVolume").value(300));
    }
}