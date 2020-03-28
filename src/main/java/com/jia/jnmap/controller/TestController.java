package com.jia.jnmap.controller;

import com.jia.jnmap.entity.User;
import com.jia.jnmap.mapper.UserMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xutao
 * @date 2020-03-15 10:07
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    UserMapper userMapper;

    @RequestMapping("/add")
    public String testReq(@RequestParam("a")Integer a, @RequestParam("b")Integer b) {
        if (a != null && b != null) {
            return a + b + "";
        }
        return "请输入int类型的参数a、b";
    }

    @RequestMapping("/user")
    public String getTestUser() {
        User user = userMapper.selectByUsername("test");

        if (user != null) {
            return user.getUsername() + user.getPassword();
        }
        return "找不到用户信息";
    }
}
