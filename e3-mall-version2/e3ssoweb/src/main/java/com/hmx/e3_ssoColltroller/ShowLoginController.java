package com.hmx.e3_ssoColltroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowLoginController {

    /**
     * 注册功能
     * @return 注册界面
     */
    @RequestMapping(value = "/pages/register")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/pages/login")
    public String login() {
        return "login";
    }
}
