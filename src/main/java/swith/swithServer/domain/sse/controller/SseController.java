package swith.swithServer.domain.sse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import swith.swithServer.domain.sse.service.SseEmitters;
import swith.swithServer.domain.study.service.StudyService;
import swith.swithServer.global.oauth.service.OauthService;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseEmitters sseEmitters;
    private final OauthService authService;
    private final StudyService studyService;

    // SSE 구독
    @GetMapping("/connect")
    public ResponseEntity<SseEmitter> connect() {
        Long userId = authService.getLoginUser().getId(); // 로그인한 사용자의 ID 가져오기
        return ResponseEntity.ok(sseEmitters.add(userId));
    }
}