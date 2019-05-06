package com.hmx.e3_service;

import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_dao.mapper.TbItemDescMapper;
import com.hmx.e3_interface.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceImpl implements ItemDescService {

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public TaotaoResult getItemDescById(Long id) {
        return TaotaoResult.ok();
    }
}
