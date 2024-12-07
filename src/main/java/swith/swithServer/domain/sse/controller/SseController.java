package swith.swithServer.domain.sse.controller;

import io.swagger.v3.oas.annotations.Operation;
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


    @GetMapping(value = "/connect/{id}", produces = "text/event-stream")
    @Operation(summary = "SSE 연결", description = "")
    public ResponseEntity<SseEmitter> connect(@PathVariable(name = "id")Long userId) {
        //Long userId = authService.getLoginUser().getId();
        SseEmitter sseEmitter = sseEmitters.add(userId);
        return ResponseEntity.ok(sseEmitter);
    }

//    @DeleteMapping("/cleanUserEmitter")
//    @Operation(summary = "현재 로그인된 회원의 emitter 삭제", description = "회원 id")
//    public ResponseEntity<String> clean(){
//        Long userId = authService.getLoginUser().getId();
//        return ResponseEntity.ok(sseEmitters.cleanupEmitter(userId));
//    }

    @DeleteMapping("/cleanUserEmitter/{id}")
    @Operation(summary = "특정 회원의 emitter 삭제", description = "회원 id")
    public ResponseEntity<String> clean(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(sseEmitters.cleanupEmitter(id));
    }

    @GetMapping("/checkUserEmitter/{id}")
    @Operation(summary = "특정 회원의 emitter 존재 여부 확인", description = "")
    public ResponseEntity<String> checkUserEmitter(@PathVariable(name = "id") Long id){
//        Long userId = authService.getLoginUser().getId();
        if(sseEmitters.hasEmitter(id)){
            return ResponseEntity.ok("Emitter with userID "+id+" exists");
        }
        return ResponseEntity.ok("Emitter with userID "+id+" doesn't exist");
    }

    @GetMapping("/checkAllEmitters")
    @Operation(summary = "현재 존재하는 모든 emitter 의 존재 여부 확인", description = "")
    public ResponseEntity<ConcurrentHashMap<Long, SseEmitter>> checkAllEmitters(){
        return ResponseEntity.ok(sseEmitters.getAllEmitter());
    }



}