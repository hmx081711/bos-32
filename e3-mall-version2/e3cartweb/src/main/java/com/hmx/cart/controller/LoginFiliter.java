package com.hmx.cart.controller;

import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_common.utils.CookieUtils;
import com.hmx.e3_pojo.TbUser;
import com.hmx.e3_sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginFiliter implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //从cookie中取token
        String e3_token = CookieUtils.getCookieValue(request, "e3_token");
        if (StringUtils.isBlank(e3_token)) {
            //没有取到token 直接放行
            return true;
        }
        //从token中取用户信息
        TaotaoResult result = tokenService.getUserInfoByToken(e3_token);
        if (result.getStatus()!=200) {
            //token已过期 未登录 直接放行
            return true;
        }
        //将用户信息存入request
        TbUser user = (TbUser) result.getData();
        request.setAttribute("user",user);
        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
