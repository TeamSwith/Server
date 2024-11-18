package swith.swithServer.global.oauth.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final String email;
    private final String password;
    private final List<GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 권한이 필요하지 않으면 빈 리스트 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 기본값으로 true 설정 (계정 만료 여부)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 기본값으로 true 설정 (계정 잠금 여부)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 기본값으로 true 설정 (자격 증명 만료 여부)
    }

    @Override
    public boolean isEnabled() {
        return true; // 기본값으로 true 설정 (계정 활성화 여부)
    }
}
