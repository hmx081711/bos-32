package com.hmx.e3_web.controller;

import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_interface.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "/rest/item/query/item/desc/{id}")
    @ResponseBody
    public TaotaoResult loadDesc(@PathVariable Long id) {
        TaotaoResult result = itemDescService.getItemDescById(id);
        return result;
    }
}
