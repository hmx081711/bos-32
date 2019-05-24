package com.hmx.e3_interface;

import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_pojo.TbItemDesc;

public interface ItemDescService {
    TaotaoResult getItemDescById(Long id);
    TbItemDesc findItemDescById(long id);
}
