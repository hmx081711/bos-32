package com.hmx.e3_ssoServiceImpl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_dao.mapper.TbUserMapper;
import com.hmx.e3_pojo.TbUser;
import com.hmx.e3_pojo.TbUserExample;
import com.hmx.e3_pojo.TbUserExample.Criteria;
import com.hmx.e3_sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {


    @Autowired
    private TbUserMapper tbUserMapper;

    /**
     *验证用户输入的信息
     * @param param 用户输入的数据
     * @param type  要验证的数据类型
     * @return TaotaoResult对象
     */
    @Override
    public TaotaoResult checkData(String param, int type) {
        //设置不同的查询条件
        TbUserExample tbUserExample = new TbUserExample();
        Criteria criteria = tbUserExample.createCriteria();
        //检查用户名type=1,和手机号type=2
        if (type==1) {
            criteria.andUsernameEqualTo(param);
        } else if (type==2) {
            criteria.andPhoneEqualTo(param);
        } else if (type==3) {
            criteria.andEmailEqualTo(param);
        } else {
            return TaotaoResult.build(400,"输入的数据类型有误");
        }
        //执行查询
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if (tbUsers !=null && tbUsers.size()>0) {
            return TaotaoResult.ok(false);
        }
        return TaotaoResult.ok(true);
    }

    /**
     * 注册新用户
     * @param tbUser 前台传过来的用户信息
     * @return TaotaoResult对象
     */
    @Override
    public TaotaoResult registerUser(TbUser tbUser) {
        //验证数据的合法性
        if (StringUtils.isBlank(tbUser.getUsername())||StringUtils.isBlank(tbUser.getPassword())
                ||StringUtils.isBlank(tbUser.getPhone())) {
                return TaotaoResult.build(400,"用户信息不完整,注册失败");
        }
        if (!(boolean)checkData(tbUser.getUsername(),1).getData()) {
            return TaotaoResult.build(400,"用户名已被占用,注册失败");
        } else if (!(boolean)checkData(tbUser.getPhone(),2).getData()) {
            return TaotaoResult.build(400,"手机号已被占用，注册失败");
        }
        //补全tbUser对象的属性
        String md5pwd = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(md5pwd);
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        //插入到数据库
        tbUserMapper.insert(tbUser);
        return TaotaoResult.ok(true);
    }
}
