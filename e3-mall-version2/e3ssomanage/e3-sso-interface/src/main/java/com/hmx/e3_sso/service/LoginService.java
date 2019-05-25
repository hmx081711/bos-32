package com.hmx.e3_sso.service;

import com.hmx.e3_common.pojo.TaotaoResult;

public interface LoginService {
    TaotaoResult login(String username,String password);
}
