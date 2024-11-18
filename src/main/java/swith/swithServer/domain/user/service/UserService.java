package swith.swithServer.domain.user.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.studyGroup.dto.GroupRequestDto;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.repository.UserRepository;
import swith.swithServer.domain.userGroup.dto.UserGroupDto;
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

    public User getUserById(GroupRequestDto groupRequestDto){
        User user = userRepository.findById(groupRequestDto.getUserId())
                .orElseThrow(()->new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        return user;
    }

    public User getUserById(UserGroupDto userGroupDto){
        User user = userRepository.findById(userGroupDto.getUserId())
                .orElseThrow(()->new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        return user;
    }
}
