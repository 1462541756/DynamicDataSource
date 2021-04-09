package com.yangkai.dynamic.datasource.dynamicConfig;
import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.yangkai.dynamic.datasource.dynamicConfig.MyDynamicDataSource.DataSourceCache;

@Configuration
public class DataSourceConfig {

    @Autowired
    DataSourceProperty dataSourceProperty;
    /**
     * 主数据源配置
     * @return
     */
    @Bean(name = "primaryDataSource")
    @Primary
    public DataSource getDataSource(){

        DataSource  datasource = MyDynamicDataSource.changeToDataSource(dataSourceProperty);
        //设置默认的数据源
        DataSourceCache.put("default", datasource);
        ThreadLocalDataSource.setLocalSource("default");
        return datasource;
    }
    /**
     * 动态装配所有的数据源
     * @param primaryDataSource
     * @return
     */
    @Bean("dynamicDataSource")
    public DynamicChangeDataSourceConfig setDynamicDataSource(@Qualifier("primaryDataSource") DataSource primaryDataSource){
        //定义实现了AbstractDataSource的自定义aop切换类
        DynamicChangeDataSourceConfig dynamicChangeDataSourceConfig = new DynamicChangeDataSourceConfig();
        //把上面的所有的数据源的map放进去
        dynamicChangeDataSourceConfig.setTargetDataSources(DataSourceCache);
        //设置默认的数据源
        dynamicChangeDataSourceConfig.setDefaultTargetDataSource(primaryDataSource);

        return dynamicChangeDataSourceConfig;
    }

    @Bean("sqlSessionFactory")
    @Primary
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
}
