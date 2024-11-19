package swith.swithServer.global.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.repository.UserRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.jwt.dto.JwtTokenDto;
import swith.swithServer.global.jwt.service.JwtTokenProvider;
import swith.swithServer.global.oauth.dto.KakaoUserDto;



@Service
@RequiredArgsConstructor
public class OauthService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    private final String kakaoTokenUri = "https://kauth.kakao.com/oauth/token";
    private final String kakaoUserUri = "https://kapi.kakao.com/v2/user/me";

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;



    public String getKakaoAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);
        params.add("client_secret", kakaoClientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(kakaoTokenUri, HttpMethod.POST, request, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }

    public KakaoUserDto getKakaoUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(kakaoUserUri, HttpMethod.GET, request, String.class);

        JsonNode jsonNode = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(response.getBody());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("JSON 파싱에 실패했습니다.", e);
        }

        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.path("kakao_account").path("email").asText();
        String nickname = jsonNode.path("properties").path("nickname").asText();

        return KakaoUserDto.of(id, email, nickname);
    }


    public boolean isUserExists(KakaoUserDto kakaoUser) {
        return userRepository.findByEmail(kakaoUser.getEmail()).isPresent();
    }


    public JwtTokenDto loginUser(KakaoUserDto kakaoUser) {
        User user = userRepository.findByEmail(kakaoUser.getEmail())
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        return jwtTokenProvider.createTokens(user.getEmail());
    }

    @Transactional
    public JwtTokenDto SignupUser(KakaoUserDto kakaoUser){
        User user = createUser(kakaoUser);
        return jwtTokenProvider.createTokens(user.getEmail());
    }


    private User createUser(KakaoUserDto kakaoUser) {
        User user =User.builder()
                    .email(kakaoUser.getEmail())
                    .nickname(kakaoUser.getNickname())
                    .build();
        return userRepository.save(user);
    }

    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal(); // 이메일

        return userRepository.findByEmail(principal)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));

    }

}
