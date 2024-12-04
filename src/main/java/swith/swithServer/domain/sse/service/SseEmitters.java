package swith.swithServer.domain.sse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SseEmitters {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 구독
    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 30분 타임아웃
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        return emitter;
    }

    // 알림 전송
    public void sendNotification(Long userId, String message) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(userId.toString())
                        .name("notification")
                        .data(message));
            } catch (IOException e) {
                emitters.remove(userId); // 전송 실패 시 emitter 제거
            }
        }
    }

    // 모든 사용자에게 알림 전송
    public void sendNotificationToAll(String message) {
        emitters.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .id(userId.toString())
                        .name("notification")
                        .data(message));
            } catch (IOException e) {
                emitters.remove(userId); // 실패 시 해당 Emitter 제거
            }
        });
    }
}