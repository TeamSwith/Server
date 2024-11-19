package swith.swithServer.global.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.repository.UserRepository;
import swith.swithServer.global.oauth.util.CustomUserDetails;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override

    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 가진 회원이 없습니다: " + email));

        return new CustomUserDetails(
                user.getEmail(),
                null,
                Collections.emptyList());
    }
}
