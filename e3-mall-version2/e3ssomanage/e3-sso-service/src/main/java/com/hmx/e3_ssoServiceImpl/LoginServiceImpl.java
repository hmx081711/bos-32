package com.hmx.e3_ssoServiceImpl;

import com.hmx.e3_common.jedis.JedisClient;
import com.hmx.e3_common.pojo.JsonUtils;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_dao.mapper.TbUserMapper;
import com.hmx.e3_pojo.TbUser;
import com.hmx.e3_pojo.TbUserExample;
import com.hmx.e3_sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private JedisClient jedisClient;
    @Value(value = "${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    /**
     * 验证登录信息 生成token并将token存入redis
     * @param username 用户名
     * @param password 密码
     * @return TaoTaoResult对象
     */
    @Override
    public TaotaoResult login(String username, String password) {

        //1.验证用户名是否存在
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if (tbUsers==null||tbUsers.size()==0) {
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //2.根据用户名取密码，判断密码是否正确
        TbUser user = tbUsers.get(0);
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //3.生成token,不携带user的密码信息
        user.setPassword(null);
        String token = UUID.randomUUID().toString();
        //4.将token存入redis
        jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
        //5.设置redis的过期时间
        jedisClient.expire("SESSION:"+token,SESSION_EXPIRE);
        return TaotaoResult.ok(token);
    }
}
