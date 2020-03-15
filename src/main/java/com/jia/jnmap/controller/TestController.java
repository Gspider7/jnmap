package com.jia.jnmap.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xutao
 * @date 2020-03-15 10:07
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/add")
    public String testReq(@RequestParam("a")Integer a, @RequestParam("b")Integer b) {
        if (a != null && b != null) {
            return a + b + "";
        }
        return "请输入int类型的参数a、b";
    }
}
