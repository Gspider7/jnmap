package com.jia.jnmap.controller;

import com.jia.jnmap.entity.User;
import com.jia.jnmap.mapper.UserMapper;
import com.jia.jnmap.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 用户注册 && 登录
 *
 * @author xutao
 * @date 2020-03-17 21:21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // ------------------------------------------------------------------------------------

    @Resource
    UserMapper userMapper;


    // ------------------------------------------------------------------------------------

    /**
     * 注册用户
     */
    @RequestMapping(value = "/sign/req", method = {RequestMethod.GET, RequestMethod.POST})
    public String signUp(@RequestParam(name = "username")String username,
                         @RequestParam(name = "password")String password) {
        if (StringUtils.isBlank(username)) return ResponseUtil.lack_param("用户名");
        if (StringUtils.isBlank(password)) return ResponseUtil.lack_param("密码");

        try {
            User user = new User(username, password);
            userMapper.insert(user);
        } catch (Exception e) {
            return ResponseUtil.username_existed();
        }

        return ResponseUtil.success();
    }

    /**
     * 登录
     */
    @PostMapping("/login/req")
    public String login(@RequestParam(name = "username")String username,
                        @RequestParam(name = "password")String password,
                        HttpSession session) throws Exception {
        if (StringUtils.isBlank(username)) return ResponseUtil.lack_param("用户名");
        if (StringUtils.isBlank(password)) return ResponseUtil.lack_param("密码");

        // 校验用户名密码
        User user = userMapper.selectByUsername(username);
        if (user == null) return ResponseUtil.user_not_exist();
        if (!password.equals(user.getPassword())) return ResponseUtil.invalid_password();

        // 保存session
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", username);

        return ResponseUtil.success();
    }
}
