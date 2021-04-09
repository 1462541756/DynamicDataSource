package com.yangkai.dynamic.datasource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * @author kai.yang
 */
@SpringBootApplication
public class DatasourceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DatasourceApplication.class, args);
    }
    @Bean
    public ApplicationRunner runner(BeanFactory context) {
        return args -> {
        };
    }

}
