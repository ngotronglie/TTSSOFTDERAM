package com.example.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Cấu hình CORS
                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // 🔓 Cho phép tất cả API
                )
                .formLogin(form -> form.disable())  // Vô hiệu hóa form login
                .httpBasic(httpBasic -> httpBasic.disable())  // Vô hiệu hóa HTTP basic authentication
                .logout(logout -> logout.permitAll());  // Cho phép logout cho tất cả

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Chỉ định rõ ràng các origins cụ thể
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:9000", "http://localhost:9090")); // Cho phép frontend
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setAllowCredentials(true);  // Cho phép credentials

        // Nếu bạn muốn sử dụng patterns thay vì origins cụ thể, bạn có thể sử dụng allowedOriginPatterns
        // Ví dụ, cho phép mọi origin từ localhost với bất kỳ port nào:
        // corsConfig.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
