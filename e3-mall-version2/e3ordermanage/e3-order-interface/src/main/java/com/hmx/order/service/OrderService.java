package com.hmx.order.service;

import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.order.poto.OrderInfo;

public interface OrderService {

    TaotaoResult CreateOrder(OrderInfo orderInfo);
}
