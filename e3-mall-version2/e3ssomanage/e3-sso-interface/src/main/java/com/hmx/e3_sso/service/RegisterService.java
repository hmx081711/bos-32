package com.hmx.e3_sso.service;

import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_pojo.TbUser;

public interface RegisterService {

    TaotaoResult checkData(String param,int type);

    TaotaoResult registerUser(TbUser tbUser);
}
