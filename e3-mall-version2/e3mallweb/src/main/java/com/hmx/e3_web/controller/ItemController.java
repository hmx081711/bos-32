package com.hmx.e3_web.controller;

import com.hmx.e3_common.pojo.EasyUiDataGridresult;
import com.hmx.e3_interface.ItemService;
import com.hmx.e3_pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
