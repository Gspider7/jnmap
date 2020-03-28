package com.jia.jnmap.controller;

import com.jia.jnmap.entity.User;
import com.jia.jnmap.mapper.UserMapper;
import com.jia.jnmap.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 用户注册 && 登录
 *
 * @author xutao
 * @date 2020-03-17 21:21
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // ------------------------------------------------------------------------------------

    @Resource
    UserMapper userMapper;


    // ------------------------------------------------------------------------------------

    @RequestMapping(value = "/sign", method = {RequestMethod.GET, RequestMethod.POST})
    public String goToSignPage(Model model) {
        return "sign";
    }

    /**
     * 注册用户
     */
    @ResponseBody
    @RequestMapping(value = "/sign/req", method = {RequestMethod.GET, RequestMethod.POST})
    public String signUp(@RequestParam(name = "username")String username,
                         @RequestParam(name = "password")String password,
                         @RequestParam(name = "confirmPassword")String confirmPassword) {
        // 后台参数校验
        if (StringUtils.isBlank(username)) return ResponseUtil.lack_param("用户名");
        if (StringUtils.isBlank(password)) return ResponseUtil.lack_param("密码");
        if (StringUtils.isBlank(confirmPassword)) return ResponseUtil.lack_param("确认密码");
        if (!password.equals(confirmPassword)) return ResponseUtil.unmatch_password();

        // 执行注册
        try {
            User user = new User(username, password);
            userMapper.insert(user);
        } catch (Exception e) {
            return ResponseUtil.username_existed();
        }

        return ResponseUtil.success();
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String goToLoginPage(Model model) {
        return "login";
    }

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping("/login/req")
    public String login(@RequestParam(name = "username")String username,
                        @RequestParam(name = "password")String password,
                        HttpSession session) {
        // 后台参数校验
        if (StringUtils.isBlank(username)) return ResponseUtil.lack_param("用户名");
        if (StringUtils.isBlank(password)) return ResponseUtil.lack_param("密码");

        // 校验用户名密码
        User user = userMapper.selectByUsername(username);
        if (user == null) return ResponseUtil.user_not_exist();
        if (!password.equals(user.getPassword())) return ResponseUtil.invalid_password();

        // 登录成功，保存session
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", username);

        return ResponseUtil.success();
    }
}
