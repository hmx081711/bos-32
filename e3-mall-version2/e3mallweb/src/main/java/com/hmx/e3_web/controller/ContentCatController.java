package com.hmx.e3_web.controller;

import com.hmx.content.interf.ContentCatgory;
import com.hmx.e3_common.pojo.EasyUiTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCatController {
    @Autowired
    private ContentCatgory contentCatgory;

    /**
     * 显示商品分类列表
     * @param id
     * @return
     */
    @RequestMapping(value = "/content/category/list")
    @ResponseBody
    public List<EasyUiTreeNode> getCatNodeList(@RequestParam(name = "id",defaultValue = "0") Long id) {
        List<EasyUiTreeNode> catList = contentCatgory.getCategoryCatList(id);
        return catList;
    }
}
