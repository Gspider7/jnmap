package com.jia.jnmap.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录拦截请求，如果未登录
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final List<String> whiteList = new ArrayList<>();

    static {
        whiteList.add("/user/sign");
        whiteList.add("/user/sign/req");
        whiteList.add("/user/login");
        whiteList.add("/user/login/req");
    }
    
    @Override  
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (whiteList.contains(uri)) return true;
          
        Integer userId = (Integer) request.getSession(true).getAttribute("userId");
        if (userId == null)  {
            response.sendRedirect("/user/login");
            return false;  
        }
        return true;
    }  
}
