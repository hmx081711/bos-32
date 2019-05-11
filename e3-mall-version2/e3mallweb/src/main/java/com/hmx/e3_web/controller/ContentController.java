package com.hmx.e3_web.controller;

import com.hmx.content.interf.ContentServce;
import com.hmx.e3_common.pojo.EasyUiDataGridresult;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {

    @Autowired
    private ContentServce contentService;

    /**
     * 展示商品分类内容的界面
     * @param categoryId 商品分类的id
     * @param page 分页显示的页数
     * @param rows 每页显示的数据条数
     * @return EasyUiResult对象
     */
    @RequestMapping(value = "/content/query/list")
    @ResponseBody
    public EasyUiDataGridresult showContentByCatsId(Long categoryId,Integer page, Integer rows) {
        EasyUiDataGridresult dataGridresult = contentService.showContentList(categoryId, page, rows);
        return dataGridresult;
    }

    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addContent(TbContent tbContent) {
        TaotaoResult result = contentService.addContent(tbContent);
        return result;
    }

    @RequestMapping(value = "/rest/content/edit",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult editContent(TbContent content) {
        TaotaoResult result = contentService.editContent(content);
        return result;
    }

    @RequestMapping(value = "/content/delete",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteContent(Long ids) {
        TaotaoResult result = contentService.deleteContent(ids);
        return result;
    }


}
