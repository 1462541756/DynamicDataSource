package com.yangkai.dynamic.datasource.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 杨锴
 * @date 2021/4/7 14:26
 * @description：
 */

@Configuration
@EnableTransactionManagement
@MapperScan({"com.yangkai.dynamic.datasource.dao"})
public class MyBatisConfig {
}
