package com.yangkai.dynamic.datasource.dynamicConfig;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

import static com.yangkai.dynamic.datasource.dynamicConfig.MyDynamicDataSource.DataSourceCache;

@Aspect
@Component
@Slf4j
public class DataSourceAop {
    @Resource(name = "dynamicDataSource")
    DynamicChangeDataSourceConfig dynamicChangeDataSourceConfig;

    @Pointcut("execution(public * com.yangkai.dynamic.datasource.service.*.*(..))")
    public void datasourceAspect() {
    }
    @Before("datasourceAspect()")
    public void beforeAspect(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String databaseKey = attributes.getRequest().getHeader("database_key");
        ThreadLocalDataSource.setLocalSource(databaseKey);
    }

    /**
     * 在切入service方法之后执行
     * 设置回默认数据源
     */
    @After("datasourceAspect()")
    public void afterAspect() {
        ThreadLocalDataSource.setLocalSource("default");
    }

}
