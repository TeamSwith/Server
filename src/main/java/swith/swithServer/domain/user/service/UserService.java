package swith.swithServer.domain.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.repository.UserRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.oauth.service.OauthService;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final OauthService oauthService;

    //id로 찾기
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        return user;
    }

    public User getLoginUser() {
        return oauthService.getLoginUser();
    }
}