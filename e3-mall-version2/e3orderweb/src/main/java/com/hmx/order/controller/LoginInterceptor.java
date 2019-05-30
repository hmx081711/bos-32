package com.hmx.order.controller;

import com.hmx.cart.service.CartService;
import com.hmx.e3_common.pojo.JsonUtils;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_common.utils.CookieUtils;
import com.hmx.e3_pojo.TbItem;
import com.hmx.e3_pojo.TbUser;
import com.hmx.e3_sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginInterceptor implements HandlerInterceptor {


    @Value(value = "${LOGIN_REDIRECT_URL}")
    private String LOGIN_REDIRECT_URL;

    @Autowired
    private CartService cartService;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断用户是否登陆
        String e3_token = CookieUtils.getCookieValue(request, "e3_token");
        if (StringUtils.isBlank(e3_token)) {
            //没有取到token
            response.sendRedirect(LOGIN_REDIRECT_URL+"page/login?redirect="+request.getRequestURL());
            //拦截
            return false;
        }
        TaotaoResult result = tokenService.getUserInfoByToken(e3_token);
        if (result.getStatus()!=200) {
            //用户登录过期
            response.sendRedirect(LOGIN_REDIRECT_URL+"page/login?redirect="+request.getRequestURL());
            return false;
        }
        //判断当前cookie中是否有商品数据
        String cartJson = CookieUtils.getCookieValue(request, "cart", true);
        TbUser user = (TbUser) result.getData();
        request.setAttribute("user",user);
        if (StringUtils.isNotBlank(cartJson)) {
            cartService.mergeCart(user.getId(),JsonUtils.jsonToList(cartJson, TbItem.class));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
