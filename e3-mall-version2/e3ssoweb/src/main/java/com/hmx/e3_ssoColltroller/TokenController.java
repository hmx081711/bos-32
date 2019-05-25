package com.hmx.e3_ssoColltroller;

import com.hmx.e3_common.pojo.JsonUtils;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;
    /**
     * 发送ajax请求 取用户的登录信息
     * @param token cookie中的token值
     * @return 用户信息的Json串
     */
    @RequestMapping(value = "/user/token/{token}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String getUserInfoByToken(@PathVariable String token, String callback) {
        TaotaoResult result = tokenService.getUserInfoByToken(token);
        if (StringUtils.isNotBlank(callback)) {
            //请求为jsonp
            return callback + "("+ JsonUtils.objectToJson(result)+")";
        }
        return JsonUtils.objectToJson(result);
    }
//    @RequestMapping(value = "/user/token/{token}")
//    @ResponseBody
//    public Object getUserInfoByToken(@PathVariable String token,String callback) {
//        TaotaoResult result = tokenService.getUserInfoByToken(token);
//        if (StringUtils.isNotBlank(callback)) {
//            //请求为jsonp
//            MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
//            jacksonValue.setJsonpFunction(callback);
//            return jacksonValue;
//        }
//        return result;
//    }
}
