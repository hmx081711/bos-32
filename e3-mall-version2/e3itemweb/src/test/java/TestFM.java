import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TestFM {

    @Test
    public void testfreemarker() throws IOException, TemplateException {
        //第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        //第二步：设置模板文件所在的路径。
        configuration.setDirectoryForTemplateLoading(new File("E:\\eclipse\\e3-mall-version2\\e3itemweb\\src\\main\\webapp\\WEB-INF"));
        //第三步：设置模板文件使用的字符集。一般就是utf-8.
        configuration.setDefaultEncoding("utf-8");
        //第四步：加载一个模板，创建一个模板对象。
        Template template = configuration.getTemplate("hello.ftl");
        //第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        HashMap data = new HashMap<>();
        data.put("hello","this is my first freeMarker test 我的");
        Student student = new Student(1, "小明", 18, "南京");
        data.put("student",student);
        ArrayList<Student> stulist = new ArrayList<>();
        stulist.add(new Student(1,"小华",22,"上海"));
        stulist.add(new Student(2,"小方",15,"广东"));
        stulist.add(new Student(3,"张三",17,"江苏"));
        stulist.add(new Student(4,"李四",19,"北京"));
        data.put("stulist",stulist);
        Date date = new Date();
        data.put("date",date);
        Integer[] myval = null;
        data.put("myval",myval);
        //第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
        FileWriter writer = new FileWriter(new File("E:\\eclipse\\e3-mall-version2\\e3itemweb\\src\\test\\java\\hello.html"));
        //第七步：调用模板对象的process方法输出文件。
        template.process(data,writer);
        //第八步：关闭流
        writer.close();
    }
}
