package com.hmx.e3_item_web.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FreeMarkerTestConteroller {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping(value = "/genhtml")
    @ResponseBody
    public String test() throws Exception {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello2.ftl");
        Map map = new HashMap<>();
        map.put("hello","你好");
        Writer out = new FileWriter(new File("E:\\eclipse\\e3-mall-version2\\e3itemweb\\src\\test\\java\\hello2.html"));
        template.process(map,out);
        out.close();
        return "ok";
    }
}
