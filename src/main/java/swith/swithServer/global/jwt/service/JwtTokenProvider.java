package swith.swithServer.global.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.global.jwt.dto.JwtTokenDto;
import swith.swithServer.global.jwt.refreshToken.service.RefreshTokenService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private Long refreshExpiration;

    public String createAccessToken(String email) {

        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessExpiration);


//        return Jwts.builder()
//                    .setClaims(claims)
//                    .setIssuedAt(now)
//                    .setExpiration(validity)
//                    .signWith(SignatureAlgorithm.HS256, secretKey)
//                    .compact();



        // 시크릿 키를 Key 객체로 변환
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256) // Key 객체 사용
                .compact();

    }

    @Transactional
    public String createRefreshToken(String email) {

        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshExpiration);

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String refreshToken =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256) // Key 객체 사용
                .compact();

        updateRefreshToken(email,refreshToken);
        return refreshToken;

    }

    @Transactional
    public void updateRefreshToken(String id, String refreshToken) {
        refreshTokenService.updateToken(id, refreshToken);
    }


    public JwtTokenDto createTokens(String email){
        String accessToken = createAccessToken(email);
        String refreshToken = createRefreshToken(email);

        return JwtTokenDto.of(email,accessToken,refreshToken);
    }



    // 2. JWT에서 사용자 ID 추출 메서드
    public String getUserEmail(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT 토큰이 비어있거나 null입니다.");
        }
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            log.error("유효하지 않은 JWT 토큰입니다.", e);
            throw new RuntimeException("JWT 토큰 파싱 중 오류가 발생했습니다.", e);
        }
    }

    // 3. JWT 유효성 검증 메서드
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String emailFromToken = claims.getSubject();
            return (emailFromToken.equals(userDetails.getUsername()) && !claims.getExpiration().before(new Date()));

        } catch (JwtException | IllegalArgumentException e) {   // 토큰이 유효하지 않은 경우
            return false;
        }
    }
}
