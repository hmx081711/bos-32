package com.hmx.e3_web.controller;

import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.searchService.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AddItemController {

    @Autowired
    private SearchService searchService;

    /**
     * 导入商品到索引库的视图
     * @return TaotaoResult对象
     */
    @RequestMapping(value = "/index/item/import")
    @ResponseBody
    public TaotaoResult addItemToIndex() {
        TaotaoResult result = searchService.getAllItems();
        return result;
    }
}
