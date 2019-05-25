package com.hmx.e3_ssoColltroller;

import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_pojo.TbUser;
import com.hmx.e3_sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShowRegisterController {


    @Autowired
    private RegisterService registerService;

    /**
     * 注册功能
     * @return 注册界面
     */
    @RequestMapping(value = "/page/register")
    public String register() {
        return "register";
    }


    @RequestMapping(value = "/user/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkData(@PathVariable String param,@PathVariable Integer type) {
        TaotaoResult result = registerService.checkData(param, type);
        return result;
    }

    /**
     * 注册添加到dao界面
     * @param tbUser 前台传来的用户信息
     * @return TaotaoResult对象
     */
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser tbUser) {
        TaotaoResult result = registerService.registerUser(tbUser);
        return result;
    }
}
