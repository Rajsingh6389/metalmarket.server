package MaterialMart.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "https://arsh341.netlify.app",
                                "https://metalmarketin.in",
                                "https://www.metalmarketin.in",  // Added www version
                                "https://metalmarketin.com",
                                "https://www.metalmarketin.com", // Added www version
                                "https://metalmarketserver-production.up.railway.app"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}