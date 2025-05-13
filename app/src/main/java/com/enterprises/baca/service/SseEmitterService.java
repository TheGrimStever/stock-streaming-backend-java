package com.enterprises.baca.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class SseEmitterService {

    final Set<SseEmitter> emitterSet = Collections.synchronizedSet(new LinkedHashSet<>());

     public SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter(0L);
        emitterSet.add(emitter);

        emitter.onCompletion(() -> emitterSet.remove(emitter));
        emitter.onTimeout( () -> emitterSet.remove(emitter));
        emitter.onError( (e) -> emitterSet.remove(emitter));

        return emitter;
    }

    public void publish(Object event) {
         synchronized (emitterSet) {
             for (SseEmitter emitter : emitterSet) {
                 try {
                     emitter.send(event);
                 } catch (IOException e) {
                     emitterSet.remove(emitter);
                 }
             }
         }
    }
}
