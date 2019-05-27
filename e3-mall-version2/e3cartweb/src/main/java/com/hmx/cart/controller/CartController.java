package com.hmx.cart.controller;

import com.hmx.cart.service.CartService;
import com.hmx.e3_common.pojo.JsonUtils;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_common.utils.CookieUtils;
import com.hmx.e3_interface.ItemService;
import com.hmx.e3_pojo.TbItem;
import com.hmx.e3_pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {


    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;

    @Value(value = "${COOKIE_CART_EXPIRE}")
    private int COOKIE_CART_EXPIRE;


    /**
     * 添加商品到购物车
     * @param product_id 商品的id
     * @param num 商品的个数
     * @param request 当前的请求
     * @param response 当前的响应
     * @return 购物车添加成功视图
     */
    @RequestMapping(value = "/cart/add/{product_id}")
    public String addCart(@PathVariable Long product_id, @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response) {

        //判断用户是否登陆
        TbUser user = (TbUser) request.getAttribute("user");
        //用户登录了
        if (user != null) {
            //调用添加购物车服务
            cartService.addToCart(user.getId(),product_id,num);
            return "cartSuccess";
        }
        //从cookie中取商品列表
        List<TbItem> tbItems = getCartListFromCookie(request);
        boolean flag = false;
        for (TbItem item:tbItems) {
            //若商品列表中有该商品，则该商品的num+1
            if (item.getId()==product_id.longValue()) {
                flag = true;
                item.setNum(item.getNum() + num);
                break;
            }
        }
        //若没有则去数据库中取商品信息
        if (!flag) {
            TbItem item = itemService.findItemByd(product_id);
            //设置商品的数量
            item.setNum(num);
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                item.setImage(image.split(",")[0]);
            }
            // 添加到商品列表中
            tbItems.add(item);
        }
        //将商品信息写入Cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(tbItems),
                COOKIE_CART_EXPIRE,true);
        //返回成功视图
        return "cartSuccess";

    }

    /**
     * 展示购物车页面
     * @param request 当前的请求对象
     * @param model
     * @return 购物车视图
     */
    @RequestMapping(value = "/cart/cart")
    public String showCart(HttpServletRequest request,HttpServletResponse response, Model model) {
        List<TbItem> list = getCartListFromCookie(request);
        //判断用户是否登陆
        TbUser user = (TbUser) request.getAttribute("user");
        if (user !=null) {
            //登陆了从redis中取商品列表
            if (list!=null&&list.size()>0) {
                //合并cookie和redis中的数据
                cartService.mergeCart(user.getId(), list);
                //删除cookie中的数据
                CookieUtils.deleteCookie(request,response,"cart");
            }
            //将商品列表展示给页面
            list = cartService.getItemListFromRedis(user.getId());
        }
        //用户没有登录
        //将商品列表展示给页面
        model.addAttribute("cartList",list);
        //返回逻辑视图
        return "cart";
    }

    /**
     * 在购物车界面修改购物车内商品的数量
     * @param itemId 要修改的商品的id
     * @param itemNum 修改后的数量
     * @param request 当前的请求
     * @param response 当前的响应
     * @return TaoTaoResult对象
     */
    @RequestMapping(value = "/cart/update/num/{itemId}/{itemNum}")
    @ResponseBody
    public TaotaoResult updateCartInfo(@PathVariable Long itemId,@PathVariable Integer itemNum,
                                       HttpServletRequest request,HttpServletResponse response) {

        //判断用户是否登陆
        TbUser user = (TbUser) request.getAttribute("user");
        if (user !=null) {
            cartService.updateCartNum(user.getId(),itemId,itemNum);
        }
        //从cookie中取购物车列表
        List<TbItem> tbItems = getCartListFromCookie(request);
        //找到id为itemId的商品
        for (TbItem item:tbItems) {
            if (item.getId()==itemId.longValue()) {
                //修改它的数量
                item.setNum(itemNum);
                // 跳出循环
                break;
            }
        }
        //将修改过的商品信息写回cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(tbItems),
                COOKIE_CART_EXPIRE,true);
        //返回成功
        return TaotaoResult.ok();
    }

    /**
     * 删除购物车中的商品
     * @param itemId 商品的Id
     * @param request 当前的请求
     * @param response 当前的响应
     * @return 购物车的视图
     */
    @RequestMapping(value = "/cart/delete/{itemId}")
    public String deleteCart(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response) {
        //判断用户是否登陆
        TbUser user = (TbUser) request.getAttribute("user");
        if (user !=null) {
            cartService.deleteCart(user.getId(),itemId);
        }
        //从cookie中取购物车商品
        List<TbItem> tbItems = getCartListFromCookie(request);
        //找到要删除的商品
        for (TbItem item:tbItems) {
            if (itemId.longValue()==item.getId()) {
                tbItems.remove(item);
                break;
            }
        }
        //将新的购物车写入cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(tbItems)
                ,COOKIE_CART_EXPIRE,true);
        //返回购物车视图
        return "redirect:/cart/cart.html";
    }
    /**
     * 从cookie中取商品对象
     * @param request 当前的请求对象
     * @return 查询的商品列表
     */
    private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, "cart", true);
        if (StringUtils.isNotBlank(json)) {
            List<TbItem> tbItemList = JsonUtils.jsonToList(json, TbItem.class);
            return tbItemList;
        }
        return new ArrayList<TbItem>();
    }

}
