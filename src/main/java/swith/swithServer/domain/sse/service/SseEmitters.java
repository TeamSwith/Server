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

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("SSE 연결 성공"));
        } catch (IOException e) {
            emitters.remove(userId);
            System.out.println("더미 데이터 전송 실패: 사용자 ID: " + userId);
        }

//        startKeepAlive(userId, emitter);

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


    public String cleanupEmitter(Long userId) {
        SseEmitter emitter = getUserEmitter(userId);
        if (emitter != null) {
            try {
                emitter.complete(); // 연결 정상 종료
            } catch (Exception e) {
                return "Failed to complete emitter for userId: " + userId;
            } finally {
                emitters.remove(userId); // 삭제
            }
            return "Emitter removed for userId: " + userId;
        } else {
            return "No emitter found for userId: " + userId;
        }
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

//    private void startKeepAlive(Long userId, SseEmitter emitter) {
//        new Thread(() -> {
//            try {
//                while (emitters.containsKey(userId)) {
//                    Thread.sleep(30000);
//                    emitter.send(SseEmitter.event().name("ping").data("keep-alive"));
//                }
//            } catch (IOException | InterruptedException e) {
//                emitters.remove(userId);
//                System.out.println("Keep-Alive 메시지 전송 중 오류 발생. 사용자 ID: " + userId);
//            }
//        }).start();
//    }
}