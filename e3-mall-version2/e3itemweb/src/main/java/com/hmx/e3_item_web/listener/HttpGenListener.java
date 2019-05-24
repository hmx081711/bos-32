package com.hmx.e3_item_web.listener;

import com.hmx.e3_interface.ItemDescService;
import com.hmx.e3_interface.ItemService;
import com.hmx.e3_item_web.poto.Item;
import com.hmx.e3_pojo.TbItem;
import com.hmx.e3_pojo.TbItemDesc;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service
public class HttpGenListener implements MessageListener {

    /**
     * 用来监听商品添加发来的消息
     * @param message activeMQ发来的消息
     */

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void onMessage(Message message) {
        TextMessage message1 = (TextMessage) message;
        try {
            String text = message1.getText();
            //取商品id
            Long itemId = Long.parseLong(text);
            Thread.sleep(100);
            TbItem tbItem = itemService.findItemByd(itemId);
            Item item = new Item(tbItem);
            //取商品描述
            TbItemDesc itemDesc = itemDescService.findItemDescById(itemId);
            //创建模板对象
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //创建数据集
            Map map = new HashMap<>();
            map.put("item",item);
            map.put("itemDesc",itemDesc);
            Writer out = new FileWriter(new File("D:\\static\\" + itemId +".html"));
            template.process(map,out);
            out.close();
        } catch (JMSException | IOException | TemplateException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
