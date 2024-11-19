package swith.swithServer.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/groups/create").permitAll() // 특정 엔드포인트 허용
                        .requestMatchers("/api/groups/insert-id").permitAll()
                        .requestMatchers("/api/groups/update").permitAll()
                        .requestMatchers("/api/groups/details").permitAll()
                        .requestMatchers("/api/groups/delete").permitAll()
                        .requestMatchers("/api/comments/create").permitAll()
                        .requestMatchers("/api/user-groups/users").permitAll()
                        .anyRequest().authenticated() // 나머지는 인증 필요
                );
        return http.build();
    }
}