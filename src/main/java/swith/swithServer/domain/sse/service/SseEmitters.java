package swith.swithServer.domain.sse.service;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmitters {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void addEmitter(String userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
    }

    public void removeEmitter(String userId) {
        emitters.remove(userId);
    }
}