package com.eastflag.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "datasource")
@Data
public class DatasourceProfile {
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    private String initialSize;
    private String maxActive;
    private String maxIdle;
    private String minIdle;
    private String maxWait;
    private String validationQuery;
    private String validationInterval;
    private String testOnBorrow;
}
