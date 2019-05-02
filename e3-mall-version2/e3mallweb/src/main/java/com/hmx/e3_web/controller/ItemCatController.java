package com.hmx.e3_web.controller;

import com.hmx.e3_common.pojo.EasyUiTreeNode;
import com.hmx.e3_interface.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUiTreeNode> getItemCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
        List<EasyUiTreeNode> list = itemCatService.getCatList(parentId);
        return list;
    }
}
