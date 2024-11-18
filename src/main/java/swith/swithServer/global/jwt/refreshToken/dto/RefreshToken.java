package swith.swithServer.global.jwt.refreshToken.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("refreshToken")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RefreshToken {
    @Id
    private String id;

    private String refreshToken;

    @TimeToLive()
    private Long expiration;
    public RefreshToken(String email) {
        this.id = email;
    }

    public void createRefreshToken(String refreshToken, Long expiration) {
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

}