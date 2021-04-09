package com.yangkai.dynamic.datasource.controller;

import com.yangkai.dynamic.datasource.dynamicConfig.ThreadLocalDataSource;
import com.yangkai.dynamic.datasource.entity.User;
import com.yangkai.dynamic.datasource.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-04-07 13:56:41
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    @GetMapping("fetchAll")
    public List<User> fetchAll() {
        ThreadLocalDataSource.setLocalSource("default");
        return this.userService.queryAllByLimit(0,10);
    }

}
