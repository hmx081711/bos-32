package com.hmx.dao;

import com.hmx.e3_common.pojo.SearchItem;
import com.hmx.e3_common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SrearchDao {

    @Autowired
    private SolrServer solrServer;

    public SearchResult getSearchResult(SolrQuery query) throws SolrServerException {
        //执行查询
        QueryResponse queryResponse = solrServer.query(query);
        SolrDocumentList results = queryResponse.getResults();
        //取高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        SearchResult result = new SearchResult();
        result.setRecourdCount(results.getNumFound());
        //遍历结果集
        ArrayList<SearchItem> list = new ArrayList<>();
        for (SolrDocument doc: results) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) doc.get("id"));
            List<String> Hlist = highlighting.get(doc.get("id")).get("item_title");
            String title = "";
            if (Hlist != null&&Hlist.size() > 0) {
                title = Hlist.get(0);
            }else {
                title = (String) doc.get("item_title");
            }
            searchItem.setTitle(title);
            searchItem.setSell_point((String) doc.get("item_sell_point"));
            searchItem.setImage((String) doc.get("item_image"));
            searchItem.setPrice((Long) doc.get("item_price"));
            searchItem.setCategory_name((String) doc.get("item_category_name"));
            list.add(searchItem);
        }
        result.setItemList(list);
        //返回结果
        return result;
    }

}
