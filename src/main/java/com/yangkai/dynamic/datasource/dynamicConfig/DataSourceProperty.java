package com.yangkai.dynamic.datasource.dynamicConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 杨锴
 * @date 2021/4/7 17:18
 * @description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DataSourceProperty{
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
}
