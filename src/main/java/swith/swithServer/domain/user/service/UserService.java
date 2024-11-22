package swith.swithServer.domain.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.repository.UserRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //id로 찾기
    public User getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()->new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        return user;
        }
    // User ID로 nickname 조회 API
    public String getNicknameByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USER_ID));
        return user.getNickname();
    }
}