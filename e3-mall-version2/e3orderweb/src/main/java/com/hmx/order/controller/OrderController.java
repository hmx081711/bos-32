package com.hmx.order.controller;

import com.hmx.cart.service.CartService;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_pojo.TbItem;
import com.hmx.e3_pojo.TbUser;
import com.hmx.order.poto.OrderInfo;
import com.hmx.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order/order-cart")
    public String showOrder(HttpServletRequest request) {
        TbUser user = (TbUser) request.getAttribute("user");
        List<TbItem> itemList = cartService.getItemListFromRedis(user.getId());
        request.setAttribute("cartList",itemList);
        return "order-cart";
    }

    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public String createOrder(HttpServletRequest request, Model model, OrderInfo orderInfo) {
        //取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        //生成订单
        TaotaoResult result = orderService.CreateOrder(orderInfo);
        //清空购物车
        if (result.getStatus()==200) {
            cartService.cleanCart(user.getId());
        }
        String orderId = result.getData().toString();
        model.addAttribute("orderId",orderId);
        request.setAttribute("payment",orderInfo.getPayment());
        //返回逻辑视图
        return "success";
    }
}
