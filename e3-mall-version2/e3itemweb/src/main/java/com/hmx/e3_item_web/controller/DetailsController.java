package com.hmx.e3_item_web.controller;


import com.hmx.e3_interface.ItemDescService;
import com.hmx.e3_interface.ItemService;
import com.hmx.e3_item_web.poto.Item;
import com.hmx.e3_pojo.TbItem;
import com.hmx.e3_pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DetailsController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDescService itemDescService;


    @RequestMapping(value = "/item/{itemId}")
    public String showDetails(@PathVariable Long itemId, Model model) {
        TbItem tbItem = itemService.findItemByd(itemId);
        Item item = new Item(tbItem);
        TbItemDesc itemDesc = itemDescService.findItemDescById(itemId);
        //返回给页面信息
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);
        return "item";
    }
}
