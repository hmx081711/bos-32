package com.hmx.e3_sso.service;

import com.hmx.e3_common.pojo.TaotaoResult;

public interface TokenService {

    TaotaoResult getUserInfoByToken(String token);
}
