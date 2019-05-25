package com.hmx.e3_ssoServiceImpl;

import com.hmx.e3_common.jedis.JedisClient;
import com.hmx.e3_common.pojo.JsonUtils;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_pojo.TbUser;
import com.hmx.e3_sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {


    @Autowired
    private JedisClient jedisClient;
    @Value(value = "${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    /**
     * 根据Token取用户信息
     * @param token 用户的登录token
     * @return TaotaoResult对象
     */
    @Override
    public TaotaoResult getUserInfoByToken(String token) {

        //从redis中用户信息
        String userInfo = jedisClient.get("SESSION:" + token);
        if (StringUtils.isBlank(userInfo)) {
            return TaotaoResult.build(201,"用户登录过期");
        }
        //获取TbUser对象
        TbUser user = JsonUtils.jsonToPojo(userInfo, TbUser.class);
        //更新过期时间
        jedisClient.expire("SESSION:" + token,SESSION_EXPIRE);
        return TaotaoResult.ok(user);
    }
}
