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

}