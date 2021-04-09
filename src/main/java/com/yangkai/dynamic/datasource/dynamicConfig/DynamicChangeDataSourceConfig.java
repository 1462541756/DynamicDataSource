package com.yangkai.dynamic.datasource.dynamicConfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 杨锴
 * @date 2021/4/8 10:34
 * @description：
 */
public class DynamicChangeDataSourceConfig extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return ThreadLocalDataSource.getLocalSource();
    }



}
