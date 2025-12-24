package com.example.newcracker.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "naver.api")
@Data
public class NaverProperties {
    private String clientId;
    private String clientSecret;
    private String newsUrl;
}
