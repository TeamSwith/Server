package swith.swithServer.domain.user.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //id로 찾기
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
}
