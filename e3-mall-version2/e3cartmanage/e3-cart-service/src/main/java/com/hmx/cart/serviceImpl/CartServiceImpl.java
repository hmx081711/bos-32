package com.hmx.cart.serviceImpl;

import com.hmx.cart.service.CartService;
import com.hmx.e3_common.jedis.JedisClient;
import com.hmx.e3_common.pojo.JsonUtils;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_dao.mapper.TbItemMapper;
import com.hmx.e3_pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Value(value = "${CART_KEY}")
    private String CART_KEY;
    /**
     * 登陆时将用户的购物车添加到redis
     * @param userId 用户的ID
     * @param itemId 商品的ID
     * @return TaotaoResult对象
     */
    @Override
    public TaotaoResult addToCart(long userId, long itemId,int num) {

        //取到了
        if (jedisClient.hexists(CART_KEY+":"+userId,itemId+"")) {
            //从redis中取商品数据
            String itemJson = jedisClient.hget(CART_KEY + ":" + userId, itemId + "");
            TbItem item = JsonUtils.jsonToPojo(itemJson, TbItem.class);
            //修改商品的数量
            item.setNum(item.getNum()+num);
            //重新写入redis
            jedisClient.hset(CART_KEY + ":" + userId,itemId +"",JsonUtils.objectToJson(item));
            return TaotaoResult.ok();
        }
        //没有取到
        //根据商品id查找商品信息
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        //设置商品数量
        tbItem.setNum(num);
        String image = tbItem.getImage();
        if (StringUtils.isNotBlank(image)) {
            tbItem.setImage(image.split(",")[0]);
        }
        //将商品写入redis
        jedisClient.hset(CART_KEY + ":" + userId,itemId+"",JsonUtils.objectToJson(tbItem));
        return TaotaoResult.ok();
    }

    /**
     * 从redis中获取商品列表
     * @param userId 用户的id
     * @return redis中的商品列表
     */
    @Override
    public List<TbItem> getItemListFromRedis(long userId) {

        List<String> itemsJson = jedisClient.hvals(CART_KEY + ":" + userId);
        ArrayList<TbItem> tbItems = new ArrayList<>();
        for (String itemJson:itemsJson) {
            TbItem item = JsonUtils.jsonToPojo(itemJson, TbItem.class);
            tbItems.add(item);
        }
        return tbItems;
    }


    /**
     * 合并购物车
     * @param userId 用户的id
     * @param cookieList cookie中的商品列表
     * @return TaotaoResult对象
     */
    @Override
    public TaotaoResult mergeCart(long userId, List<TbItem> cookieList) {

        for (TbItem item:cookieList) {
            addToCart(userId,item.getId(),item.getNum());
        }
        return TaotaoResult.ok();
    }

    /**
     * 登陆状态更新购物车内的商品数量
     * @param userId 用户的id
     * @param itemId 商品的id
     * @param num 商品的数量
     * @return TaotaoResult对象
     */
    @Override
    public TaotaoResult updateCartNum(long userId, long itemId, int num) {

        String json = jedisClient.hget(CART_KEY + ":" + userId, itemId + "");
        TbItem tbitem = JsonUtils.jsonToPojo(json, TbItem.class);
        tbitem.setNum(num);
        jedisClient.hset(CART_KEY + ":" + userId,itemId + "",JsonUtils.objectToJson(tbitem));
        return TaotaoResult.ok();
    }

    /**
     *
     * @param userId 用户的id
     * @param itemId 商品的id
     * @return TaotaoResult对象
     */
    @Override
    public TaotaoResult deleteCart(long userId, long itemId) {

        jedisClient.hdel(CART_KEY + ":" + userId,itemId + "");
        return TaotaoResult.ok();
    }

    /**
     * 清空购物车
     * @param userId 用户id
     * @return TaotaoResult对象
     */
    @Override
    public TaotaoResult cleanCart(long userId) {
        jedisClient.del(CART_KEY + ":" + userId);
        return TaotaoResult.ok();
    }
}
