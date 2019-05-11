package com.hmx.e3_web.controller;

import com.hmx.content.interf.ContentCatgory;
import com.hmx.e3_common.pojo.EasyUiTreeNode;
import com.hmx.e3_common.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增商品分类列表页面
     * @param parentId 新增分类的父Id
     * @param name 新增分类的名称
     * @return TaotaoResult对象
     */
    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addCats(Long parentId,String name) {
        TaotaoResult result = contentCatgory.addCategory(parentId,name);
        return result;
    }

    /**
     * 删除商品分类界面
     * @param id 要删除商品分类的id
     * @return TaotaoResult对象
     */
    @RequestMapping(value = "/content/category/delete",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteCats(Long id) {
        TaotaoResult result = contentCatgory.deleteCategory(id);
        return result;
    }

    /**
     * 重命名商品界面
     * @param id 商品的id
     * @param name 更新后的商品类别名
     * @return TaotaoResult对象
     */
    @RequestMapping(value = "/content/category/update", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult renameCats(Long id,String name) {
        TaotaoResult result = contentCatgory.renameCategory(id, name);
        return result;
    }
}
