package com.hmx.e3_protal_web.controller;

import com.hmx.content.interf.ContentServce;
import com.hmx.e3_pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ContentServce contentServce;

    //加载配置文件中的值
    @Value("${CONTENT_LUNBO_CID}")
    private Long CONTENT_LUNBO_CID;

    /**
     * 加载首页数据
     * @param model 携带数据到首页加载
     * @return index.jsp
     */
    @RequestMapping(value = "/index")
    public String showindex(Model model) {
        // 查询内容列表
        List<TbContent> ad1List = contentServce.getContentBycid(CONTENT_LUNBO_CID);
        model.addAttribute("ad1List",ad1List);
        return "index";
    }
}
