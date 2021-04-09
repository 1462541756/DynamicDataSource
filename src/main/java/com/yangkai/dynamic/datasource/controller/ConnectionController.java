package com.yangkai.dynamic.datasource.controller;

import com.yangkai.dynamic.datasource.dynamicConfig.DataSourceProperty;
import com.yangkai.dynamic.datasource.dynamicConfig.DynamicChangeDataSourceConfig;
import com.yangkai.dynamic.datasource.dynamicConfig.MyDynamicDataSource;
import com.yangkai.dynamic.datasource.dynamicConfig.ThreadLocalDataSource;
import com.yangkai.dynamic.datasource.entity.User;
import com.yangkai.dynamic.datasource.service.UserService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.yangkai.dynamic.datasource.dynamicConfig.MyDynamicDataSource.DataSourceCache;

/**
 * @author 杨锴
 * @date 2021/4/7 14:39
 * @description：
 */
@RestController
@RequestMapping("connection")
public class ConnectionController {
    @Autowired
    UserService userService;
    @Resource(name = "dynamicDataSource")
    DynamicChangeDataSourceConfig dynamicChangeDataSourceConfig;
    @RequestMapping(value = "/changeDataSource", method = RequestMethod.POST)
    public boolean changeDataSource( @RequestBody DataSourceProperty dataSource) {
        String key = "demo";
        ThreadLocalDataSource.setLocalSource(key);
//        dataSource=new DataSourceProperty();
//        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/demo?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        HikariDataSource hikariDataSource = MyDynamicDataSource.changeToDataSource(dataSource);

       //将数据源加入到缓存中
        DataSourceCache.put(key, hikariDataSource);

        dynamicChangeDataSourceConfig.afterPropertiesSet();


        List<User> users = userService.queryAllByLimit(0, 10);
        users.stream().forEach(System.out::println);
        return true;
    }

}
