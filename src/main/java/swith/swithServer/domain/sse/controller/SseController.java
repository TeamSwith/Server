package swith.swithServer.domain.sse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import swith.swithServer.domain.sse.service.SseEmitters;
import swith.swithServer.global.oauth.service.OauthService;

import java.io.IOException;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseEmitters sseEmitters;
    private final OauthService authService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        // 현재 로그인한 사용자 ID 가져오기
        Long userId = authService.getLoginUser().getId();

        // SSE Emitter 생성
        SseEmitter emitter = new SseEmitter(3600000L); // 1시간 타임아웃
        sseEmitters.addEmitter(userId.toString(), emitter);

        try {
            // 연결 성공 메시지 전송
            emitter.send(SseEmitter.event().name("connect").data("Connected successfully"));
        } catch (IOException e) {
            sseEmitters.removeEmitter(userId.toString()); // 실패 시 제거
        }

        // 연결 종료 처리
        emitter.onCompletion(() -> sseEmitters.removeEmitter(userId.toString()));
        emitter.onTimeout(() -> sseEmitters.removeEmitter(userId.toString()));

        return emitter;
    }
}