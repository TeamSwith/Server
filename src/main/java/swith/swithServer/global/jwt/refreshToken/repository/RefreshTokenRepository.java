package swith.swithServer.global.jwt.refreshToken.repository;

import org.springframework.data.repository.CrudRepository;
import swith.swithServer.global.jwt.refreshToken.dto.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
