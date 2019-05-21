package com.hmx.messagelistener;

import com.hmx.e3_common.pojo.SearchItem;
import com.hmx.mapper.SearchMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

@Service
public class AddtemMessageLintener implements MessageListener {

    @Autowired
    private SearchMapper searchMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage meg= (TextMessage) message;
            String s = meg.getText();
            Long itemId = new Long(s);
            //等待事务提交
            Thread.sleep(100);
            //同步到索引库
            SearchItem item = searchMapper.getItemById(itemId);
            SolrInputDocument inputDocument = new SolrInputDocument();
            inputDocument.addField("id", item.getId());
            inputDocument.addField("item_title", item.getTitle());
            inputDocument.addField("item_sell_point", item.getSell_point());
            inputDocument.addField("item_price", item.getPrice());
            inputDocument.addField("item_image", item.getImage());
            inputDocument.addField("item_category_name", item.getCategory_name());
            solrServer.add(inputDocument);
            solrServer.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
