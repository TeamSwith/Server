package swith.swithServer.domain.sse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import swith.swithServer.domain.sse.service.SseEmitters;
import swith.swithServer.global.oauth.service.OauthService;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseEmitters sseEmitters;
    private final OauthService authService;


    @GetMapping("/connect")    // SSE 연결
    public ResponseEntity<SseEmitter> connect() {
        Long userId = authService.getLoginUser().getId(); // 로그인한 사용자의 ID 가져오기
        SseEmitter sseEmitter = sseEmitters.add(userId);
        return ResponseEntity.ok(sseEmitter);
    }

    @DeleteMapping("/cleanUserEmitter") // 특정 회원의 emitter 삭제
    public ResponseEntity<String> clean(){
        Long userId = authService.getLoginUser().getId();
        return ResponseEntity.ok(sseEmitters.cleanupEmitter(userId));
    }

    @GetMapping("/checkUserEmitter") // 특정 회원의 emitter 존재 여부 확인
    public ResponseEntity<String> checkUserEmitter(){
        Long userId = authService.getLoginUser().getId();
        if(sseEmitters.hasEmitter(userId)){
            return ResponseEntity.ok("Emitter with userID "+userId+" exists");
        }
        return ResponseEntity.ok("Emitter with userID "+userId+" doesn't exist");
    }

    @GetMapping("/checkAllEmitters") // 현재 존재하는 모든 emitter 의 존재 여부 확인
    public ResponseEntity<ConcurrentHashMap<Long, SseEmitter>> checkAllEmitters(){
        return ResponseEntity.ok(sseEmitters.getAllEmitter());
    }

}