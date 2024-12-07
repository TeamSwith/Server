package swith.swithServer.domain.sse.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Getter
public class SseEmitters {

    private final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter add(Long userId) {
        SseEmitter emitter = new SseEmitter(60 * 60 * 1000L); // 60분 타임아웃
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        return emitter;
    }


    public void sendSse(Long userId, String eventName, Object data) { // SSE 전송
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
            } catch (Exception e) {
                cleanupEmitter(userId);
                throw new RuntimeException("연결 중 오류 발생",e);
            }
        }
    }


    public void sendSseToAll(Object data) { // SSE 전부 전송
        emitters.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("SSE")
                        .data(data));
            } catch (IOException e) {
                throw new RuntimeException("연결 중 오류 발생",e);
            }
        });
    }


    public String cleanupEmitter(Long userId){
        if(!hasEmitter(userId)){
            return "Emitter with userID " + userId + " does not exist.";
        }
        emitters.remove(userId);
        return "Emitter with userID " + userId + " has been successfully deleted.";
    }

    public boolean hasEmitter(Long userId){
        return emitters.containsKey(userId);
    }

    public SseEmitter getUserEmitter(Long userId){
        return emitters.get(userId);
    }

    public ConcurrentHashMap<Long, SseEmitter> getAllEmitter(){
        return emitters;
    }
}