package com.example.backend.security;

import com.example.backend.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public APIs - Không cần xác thực
                .requestMatchers("/auth/**").permitAll()  // Login, Register
                .requestMatchers("/api/products/**").permitAll()  // Product APIs
                .requestMatchers("/api/categories/**").permitAll()  // Category APIs
                .requestMatchers("/api/cart/**").permitAll()  // Cart APIs
                .requestMatchers("/api/orders/**").permitAll()  // Order APIs
                .requestMatchers("/api/banners/**").permitAll()  // Banner APIs
                .requestMatchers("/api/social-token/**").permitAll()  // Social token APIs
                .requestMatchers("/uploads/**").permitAll()  // Uploaded files
                .requestMatchers("/api/user/**").permitAll()
                .requestMatchers("/api/users/create").permitAll()  // Allow user creation without authentication
                
                // Admin only APIs
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                // User management APIs - Cần xác thực và phân quyền
                .requestMatchers("/api/users/**").hasAnyRole("ADMIN", "USER")
                
                // All other APIs require authentication
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:9000", "http://localhost:9090"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
