package br.ufpr.aquitemsus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://admin.aquitemsus.com.br", "https://app.aquitemsus.com.br")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .exposedHeaders("X-Total-Count");
    }
}
