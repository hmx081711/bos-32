//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServer;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.SolrInputDocument;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//public class TestSolrJ {
//    @Test
//    public void testSolrj() throws IOException, SolrServerException {
//        //创建solrj连接
//        SolrServer solrServer = new HttpSolrServer("http://192.168.208.133:8080/solr/collection1");
//        //创建一个solrinputdocument文档对象
//        SolrInputDocument sid = new SolrInputDocument();
//        //向文档中添加域
//        sid.addField("id","doc01");
//        sid.addField("item_title","测试方法01");
//        sid.addField("item_price",1000);
//        //添加solrinputdocument到Solr中
//        solrServer.add(sid);
//        //提交
//        solrServer.commit();
//    }
//    @Test
//    public void testdelete() throws IOException, SolrServerException {
//        //创建solrj连接
//        SolrServer solrServer = new HttpSolrServer("http://192.168.208.133:8080/solr/collection1");
//        // 根据id删除
////        solrServer.deleteById("doc01");
//        // 根据query删除
//        solrServer.deleteByQuery("id:doc01");
//        solrServer.commit();
//    }
//
//    /**
//     * solr简单查询的测试
//     * @throws SolrServerException
//     */
//    @Test
//    public void testUpdate() throws SolrServerException {
//        SolrServer solrServer = new HttpSolrServer("http://192.168.208.133:8080/solr/collection1");
//        //设置查询条件
//        SolrQuery solrQuery = new SolrQuery();
//        solrQuery.set("q","*:*");
//        QueryResponse queryResponse = solrServer.query(solrQuery);
//        //返回结果集
//        SolrDocumentList results = queryResponse.getResults();
//        System.out.println("查询的总计录数为" + results.getNumFound());
//        //遍历结果集
//        for (SolrDocument item:results) {
//            System.out.println(item.get("id"));
//            System.out.println(item.get("item_title"));
//            System.out.println(item.get("item_price"));
//            System.out.println(item.get("item_category_name"));
//        }
//    }
//
//    @Test
//    public void testUpdateFuza() throws SolrServerException {
//        SolrServer solrServer = new HttpSolrServer("http://192.168.208.133:8080/solr/collection1");
//        SolrQuery query = new SolrQuery();
//        query.setQuery("手机");
//        //设置起始页码
//        query.setStart(0);
//        //设置每页显示的数据条数
//        query.setRows(20);
//        //设置高亮显示
//        query.setHighlight(true);
//        //设置高亮显示的字段
//        query.addHighlightField("item_title");
//        query.setHighlightSimplePre("<em>");
//        query.setHighlightSimplePost("</em>");
//        //设置默认业务域
//        query.set("df","item_title");
//        QueryResponse queryResponse = solrServer.query(query);
//        SolrDocumentList results = queryResponse.getResults();
//        //取高亮字段
//        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
//        System.out.println("查询的总计录数为" + results.getNumFound());
//        for (SolrDocument doc:results) {
//            System.out.println(doc.get("id"));
//            List<String> list = highlighting.get(doc.get("id")).get("item_title");
//            if (list!=null&&list.size()>0) {
//                System.out.println(list.get(0));
//            }else {
//                System.out.println(doc.get("item_title"));
//            }
//            System.out.println(doc.get("item_price"));
//            System.out.println(doc.get("item_sell_point"));
//            System.out.println(doc.get("item_category_name"));
//        }
//
//    }
//}
