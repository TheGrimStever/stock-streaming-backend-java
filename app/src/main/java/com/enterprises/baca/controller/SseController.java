package com.enterprises.baca.controller;

import com.enterprises.baca.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class SseController {

    private final SseEmitterService emitterService;

    @GetMapping("/stream/metrics")
    public SseEmitter streamMetrics() {
        return emitterService.createEmitter();
    }
}
