package com.hmx.e3_ssoColltroller;

import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_common.utils.CookieUtils;
import com.hmx.e3_sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ShowLoginController {

    @Autowired
    private LoginService loginService;
    @Value(value = "${TOKEN_KRY}")
    private String TOKEN_KRY;
    /**
     * 显示登陆界面
     * @return 登陆的视图
     */
    @RequestMapping(value = "/page/login")
    public String login() {
        return "login";
    }

    /**
     * 登录业务逻辑视图
     * @param username 用户名
     * @param password 密码
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return TaoTaoResult对象
     */
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        //调用登录服务
        TaotaoResult result = loginService.login(username, password);
        //判断用户是否登录成功
        if (result.getStatus()==200) {
            //若成功则将token写入Cookie
            String token = result.getData().toString();
            CookieUtils.setCookie(request,response,TOKEN_KRY,token);
        }
        return result;
    }
}
