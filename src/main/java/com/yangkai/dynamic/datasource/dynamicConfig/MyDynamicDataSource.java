package com.yangkai.dynamic.datasource.dynamicConfig;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kai.yang
 */
public class MyDynamicDataSource extends HikariDataSource {

    @Autowired
    DataSourceProperty dataSourceProperty;

    private static HikariDataSource defaultDataSource;
    /**
     * 定义缓存数据源的变量
     */
    public static final Map<Object, Object> DataSourceCache = new ConcurrentHashMap<Object, Object>();

    @Override
    public Connection getConnection() throws SQLException {
        String localSourceKey = ThreadLocalDataSource.getLocalSource();
        DataSource dataSource = (DataSource) DataSourceCache.get(localSourceKey);
        if (dataSource == null) {
            dataSource = getDefaultDataSource();
        }
        return dataSource.getConnection();
    }

    public static HikariDataSource changeToDataSource(DataSourceProperty dataSourceProperty) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceProperty.getUrl());
        dataSource.setUsername(dataSourceProperty.getUsername());
        dataSource.setPassword(dataSourceProperty.getPassword());
        dataSource.setDriverClassName(dataSourceProperty.getDriverClassName());
        return dataSource;
    }

    public HikariDataSource getDefaultDataSource() {
        if (defaultDataSource == null) {
            defaultDataSource = changeToDataSource(dataSourceProperty);
        }
        return defaultDataSource;
    }
}
