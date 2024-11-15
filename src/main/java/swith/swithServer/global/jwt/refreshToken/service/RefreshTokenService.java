package swith.swithServer.global.jwt.refreshToken.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import swith.swithServer.global.jwt.refreshToken.dto.RefreshToken;
import swith.swithServer.global.jwt.refreshToken.repository.RefreshTokenRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("604800000")
    private Long expirationPeriod;

    public void updateToken(String email, String token) { //refreshToken을 확인하고, 없다면 생성하는 메서드
        RefreshToken refreshToken = refreshTokenRepository.findById(email).orElse(new RefreshToken(email));
        refreshToken.createRefreshToken(token, expirationPeriod);
        log.info("토큰 값 : " + refreshToken.getRefreshToken());
        log.info("아이디 : " + refreshToken.getId());
        log.info("유효기간 : " + refreshToken.getExpiration());
        refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(String token) { //refreshToken을 찾는 메서드
        return refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Optional<RefreshToken> getRefreshToken(String id) { //refreshToken을 가져오는 메서드
        return refreshTokenRepository.findById(id);
    }
}
