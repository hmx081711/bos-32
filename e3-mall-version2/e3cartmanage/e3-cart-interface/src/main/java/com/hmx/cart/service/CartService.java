package com.hmx.cart.service;

import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_pojo.TbItem;

import java.util.List;

public interface CartService {

    TaotaoResult addToCart(long userId,long itemId,int num);
    List<TbItem> getItemListFromRedis(long userId);
    TaotaoResult mergeCart(long userId,List<TbItem> cookieList);
    TaotaoResult updateCartNum(long userId,long itemId,int num);
    TaotaoResult deleteCart(long userId,long itemId);
}
