package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Tạo đường dẫn truy cập: http://localhost:8080/uploads/abc.jpg
        String uploadPath = Paths.get(System.getProperty("user.dir"), "uploads").toUri().toString();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Cấu hình CORS, hợp nhất logic từ cả hai lớp
        registry.addMapping("/**")  // Apply CORS policy to tất cả endpoints
                .allowedOrigins("http://localhost:9000")  // Nhiều origins cho phép
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Các HTTP method
                .allowedHeaders("Authorization", "Content-Type", "*")  // Cho phép tất cả headers
                .allowCredentials(true);  // Allow credentials
    }
}
