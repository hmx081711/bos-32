package com.hmx.searchServiceImpl;

import com.hmx.dao.SrearchDao;
import com.hmx.e3_common.pojo.SearchItem;
import com.hmx.e3_common.pojo.SearchResult;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.mapper.SearchMapper;
import com.hmx.searchService.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    /**
     * 导入所有商品到索引库
     *
     * @return TaotaoResult对象
     */
    @Autowired
    private SearchMapper searchMapper;
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SrearchDao srearchDao;

    /**
     * 向索引库中导入数据
     * @return TaotaoResult对象
     */
    @Override
    public TaotaoResult getAllItems() {
        try{
        // 查询所有商品
        List<SearchItem> itemList = searchMapper.getItemList();
        // 遍历所有商品
        for (SearchItem item : itemList) {
                //创建solrinputdocument对象
                SolrInputDocument sid = new SolrInputDocument();
                //向solrinputdocument对象中添加域
                sid.addField("id", item.getId());
                sid.addField("item_title", item.getTitle());
                sid.addField("item_sell_point", item.getSell_point());
                sid.addField("item_price", item.getPrice());
                sid.addField("item_image", item.getImage());
                sid.addField("item_category_name", item.getCategory_name());
                solrServer.add(sid);
                solrServer.commit();
            }
        }catch (Exception e) {
            e.printStackTrace();
            //导入失败
            return TaotaoResult.build(500,"数据导入时发生异常");
        }
        //导入成功
        return TaotaoResult.ok();
    }

    /**
     * 根据搜索条件查询结果集
     * @param keyword 搜索的关键字
     * @param page 要显示的页码
     * @param rows 每页显示的数据数
     * @return 查询的商品结果集
     * @throws SolrServerException solr的异常
     */
    @Override
    public SearchResult getSearchItemList(String keyword, int page, int rows) throws SolrServerException {

        //设置查询条件
        SolrQuery query = new SolrQuery();
        query.setQuery(keyword);
        query.set("df","item_title");
        if (page<0) page = 1;
        query.setStart((page-1)*rows);
        query.setRows(rows);
        query.setHighlight(true);
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //执行查询
        SearchResult result = srearchDao.getSearchResult(query);
        //计算页数
        long count = result.getRecourdCount();
        int totalPages = (int) (count / rows);
        if (count%rows>0) totalPages++;
        result.setTotalPages(totalPages);
        return result;
    }
}

