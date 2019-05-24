package com.hmx.e3_service;

import com.hmx.e3_common.jedis.JedisClient;
import com.hmx.e3_common.pojo.JsonUtils;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_dao.mapper.TbItemDescMapper;
import com.hmx.e3_interface.ItemDescService;
import com.hmx.e3_pojo.TbItemDesc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceImpl implements ItemDescService {

    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${ITEM_INFO_PRE}")
    private String ITEM_INFO_PRE;
    @Value("${ITEM_CACHE_EXPIRE}")
    private Integer ITEM_CACHE_EXPIRE;

    @Override
    public TaotaoResult getItemDescById(Long id) {
        return TaotaoResult.ok();
    }

    @Override
    public TbItemDesc findItemDescById(long id) {
        //查询缓存
        try {
            String s = jedisClient.get(ITEM_INFO_PRE + id + ":DESC");
            if (StringUtils.isNotBlank(s)) {
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(s, TbItemDesc.class);
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(id);
        // 存缓存
        try {
            jedisClient.set(ITEM_INFO_PRE + id +":DESC", JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(ITEM_INFO_PRE + id +":DESC",ITEM_CACHE_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }
}
