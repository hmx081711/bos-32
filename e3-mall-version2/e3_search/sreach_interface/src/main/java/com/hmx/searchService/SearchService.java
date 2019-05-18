package com.hmx.searchService;

import com.hmx.e3_common.pojo.SearchResult;
import com.hmx.e3_common.pojo.TaotaoResult;

public interface SearchService {
    TaotaoResult getAllItems();
    SearchResult getSearchItemList(String keyword,int page,int rows) throws org.apache.solr.client.solrj.SolrServerException;
}
