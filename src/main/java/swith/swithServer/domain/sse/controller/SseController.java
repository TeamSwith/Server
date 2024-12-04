package swith.swithServer.domain.sse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import swith.swithServer.domain.sse.service.SseEmitters;
import swith.swithServer.global.oauth.service.OauthService;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseEmitters sseEmitters;
    private final OauthService authService;

    // SSE 구독
    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        Long userId = authService.getLoginUser().getId(); // 로그인한 사용자의 ID 가져오기
        return sseEmitters.subscribe(userId);
    }

    // 테스트용 알림 전송
    @PostMapping("/notify_test")
    public ResponseEntity<String> sendNotification(@RequestParam String message) {
        Long userId = authService.getLoginUser().getId(); // 로그인한 사용자의 ID 가져오기
        sseEmitters.sendNotification(userId, message);
        return ResponseEntity.ok("Notification sent to user " + userId);
    }
}