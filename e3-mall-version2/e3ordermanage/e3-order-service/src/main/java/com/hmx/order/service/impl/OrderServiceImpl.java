package com.hmx.order.service.impl;


import com.hmx.e3_common.jedis.JedisClient;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_dao.mapper.TbOrderItemMapper;
import com.hmx.e3_dao.mapper.TbOrderMapper;
import com.hmx.e3_dao.mapper.TbOrderShippingMapper;
import com.hmx.e3_pojo.TbOrderItem;
import com.hmx.e3_pojo.TbOrderShipping;
import com.hmx.order.poto.OrderInfo;
import com.hmx.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Value(value = "${ORDER_ID_CREATE_KEY}")
    private String ORDER_ID_CREATE_KEY;
    @Value(value = "${ORDER_ID_DEFAULT_VALUE}")
    private String ORDER_ID_DEFAULT_VALUE;
    @Value(value = "${ORDER_ITEM_ID_KEY}")
    private String ORDER_ITEM_ID_KEY;
    /**
     * 创建订单的服务
     * @param orderInfo 用于接受表单订单数据的poto
     * @return TaotaoResult 携带订单号
     */
    @Override
    public TaotaoResult CreateOrder(OrderInfo orderInfo) {

        if (!jedisClient.exists(ORDER_ID_CREATE_KEY)) {
            jedisClient.set(ORDER_ID_CREATE_KEY,ORDER_ID_DEFAULT_VALUE);
        }
        //补全订单表
        Long orderId = jedisClient.incr(ORDER_ID_CREATE_KEY);
        //生成订单号
        orderInfo.setOrderId(orderId.toString());
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭'
        orderInfo.setStatus(1);
        //订单创建时间
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        tbOrderMapper.insert(orderInfo);
        //补全订单详情表
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem:orderItems) {
            //id
            Long id = jedisClient.incr(ORDER_ITEM_ID_KEY);
            orderItem.setId(id.toString());
            orderItem.setOrderId(orderId.toString());
            tbOrderItemMapper.insert(orderItem);
        }
        //补全物流信息表
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId.toString());
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        tbOrderShippingMapper.insert(orderShipping);

        return TaotaoResult.ok(orderId);
    }
}
