package com.hmx.e3_search_web.controller;

import com.hmx.e3_common.pojo.SearchResult;
import com.hmx.searchService.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;
    /**
     * 显示搜索结果界面
     * @param keyword 搜索的关键字
     * @param page 要显示的页码
     * @return 搜索结果信息
     */
    @RequestMapping(value = "/search")
    public String search(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) throws SolrServerException, UnsupportedEncodingException {

        keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
        //调用服务查询
        SearchResult searchItemList = searchService.getSearchItemList(keyword, page, SEARCH_RESULT_ROWS);
        //把结果传递给界面
        model.addAttribute("totalPages",searchItemList.getTotalPages());
        model.addAttribute("recourdCount",searchItemList.getRecourdCount());
        model.addAttribute("itemList",searchItemList.getItemList());
        model.addAttribute("query",keyword);
        model.addAttribute("page",page);
        //返回逻辑视图
        return "search";
    }
}
