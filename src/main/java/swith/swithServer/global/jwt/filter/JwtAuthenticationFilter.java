package swith.swithServer.global.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import swith.swithServer.global.jwt.service.JwtTokenProvider;
import swith.swithServer.global.oauth.service.CustomUserDetailsService;
import swith.swithServer.global.oauth.util.CustomUserDetails;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        String header = request.getHeader("Authorization");
        String token = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7); // "Bearer " 부분을 제거하고 토큰만 가져옴
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) { // 토큰이 존재하고 현재 인증되지 않은 상태인지 확인
            try {
                String userEmail = jwtTokenProvider.getUserEmail(token); // 사용자 email 추출

                if (userEmail != null) { //사용자의 email이 정상적으로 추출된 경우

                    CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

                    if (jwtTokenProvider.validateToken(token, userDetails)) { // 토큰이 유효한 경우 Authentication 객체 생성

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        // 세션 생성 및 SecurityContext 저장
                        HttpSession session = request.getSession(true);
                        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

                    }
                }
            } catch (Exception e) { // 유효하지 않은 토큰 예외 처리
                logger.error("인증할 수 없음: ", e);
            }
        }

        chain.doFilter(request, response);
    }

}
