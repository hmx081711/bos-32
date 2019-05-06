package com.hmx.e3_web.controller;

import com.hmx.e3_common.pojo.EasyUiDataGridresult;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_interface.ItemService;
import com.hmx.e3_pojo.TbItem;
import com.hmx.e3_pojo.TbItemDesc;
import org.apache.zookeeper.server.Request;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class ItemController {

    @RequestMapping(value = "/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item/{id}")
    @ResponseBody
    public TbItem findItemById(@PathVariable long id) {
        TbItem item = itemService.findItemByd(id);
        return item;
    }

    @RequestMapping(value = "/item/list")
    @ResponseBody
    public EasyUiDataGridresult getList(Integer page,Integer rows) {
        EasyUiDataGridresult result = itemService.getItemList(page, rows);
        return result;
    }

    /**
     * 商品添加展示界面
     * @param item POST请求的商品对象信息
     * @param itemDesc POST请求的商品描述
     * @return TaotaoResult对象
     */
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItemHandler(TbItem item,String itemDesc) {
        TaotaoResult result = itemService.addItem(item, itemDesc);
        return result;
    }

    /**
     * 回显数据
     * @return 编辑界面
     */
    @RequestMapping(value = "/rest/page/item-edit")
    public String edtItem() {
        return "item-edit";
    }

    /**
     * 商品更新界面
     * @param item POST请求的商品对象信息
     * @param desc POST请求的商品描述
     * @return TaotaoResult对象
     */
    @RequestMapping(value = "/rest/item/update",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult updateItem(TbItem item,String desc) {
        // 查询出创建时间,状态
        Byte status = itemService.findItemByd(item.getId()).getStatus();
        Date created = itemService.findItemByd(item.getId()).getCreated();
        TaotaoResult result = itemService.updateItem(item, desc,status,created);
        return result;
    }

    /**
     * 商品的删除界面
     * @param ids
     * @return
     */
    @RequestMapping(value = "/rest/item/delete",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteItem(Long ids) {
        TbItem item = itemService.findItemByd(ids);
        TaotaoResult result = itemService.deleteItem(item);
        return result;
    }

    /**
     * 商品的上架界面
     * @param ids
     * @return
     */
    @RequestMapping(value = "/rest/item/reshelf",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult onSaleItem(Long ids) {
        TbItem item = itemService.findItemByd(ids);
        TaotaoResult result = itemService.onSaleItem(item);
        return result;
    }

    /**
     * 商品的下架界面
     * @param ids
     * @return
     */
    @RequestMapping(value = "/rest/item/instock")
    @ResponseBody
    public TaotaoResult offSaleItem(Long ids) {
        TbItem item = itemService.findItemByd(ids);
        TaotaoResult result = itemService.offSaleItem(item);
        return result;
    }
}
