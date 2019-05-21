//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.CloudSolrServer;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.SolrInputDocument;
//import org.junit.Test;
//
//import java.io.IOException;
//
//public class SolrCloudTset {
//
//    @Test
//    public void testAddToSolrCloud() throws IOException, SolrServerException {
//        //创建连接
//        CloudSolrServer solrServer = new CloudSolrServer("192.168.208.133:2181,192.168.208.133:2182,192.168.208.133:2183");
//        //设置默认查找域
//        solrServer.setDefaultCollection("collection2");
//        //创建一个solr文档对象
//        SolrInputDocument inputDocument = new SolrInputDocument();
//        //向文档对象中添加域
//        inputDocument.setField("id","01");
//        inputDocument.setField("item_title","测试商品01");
//        inputDocument.setField("item_price",2000);
//        //将文档写入solr
//        solrServer.add(inputDocument);
//        //提交
//        solrServer.commit();
//    }
//
//    @Test
//    public void testQuerySolrCloud() throws SolrServerException {
//        //创建连接
//        CloudSolrServer solrServer = new CloudSolrServer("192.168.208.133:2181,192.168.208.133:2182,192.168.208.133:2183");
//        //设置默认查找域
//        solrServer.setDefaultCollection("collection2");
//        SolrQuery query = new SolrQuery();
//        query.setQuery("*:*");
//        QueryResponse response = solrServer.query(query);
//        SolrDocumentList results = response.getResults();
//        System.out.println("总的查询数量为" + results.getNumFound());
//
//        for (SolrDocument doc:results) {
//            System.out.println(doc.get("id"));
//            System.out.println(doc.get("item_title"));
//            System.out.println(doc.get("item_price"));
//        }
//
//    }
//}
