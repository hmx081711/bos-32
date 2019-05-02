import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hmx.e3_dao.mapper.TbItemMapper;
import com.hmx.e3_pojo.TbItem;
import com.hmx.e3_pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TsetPage {
    @Test
    public void t1() {
        ApplicationContext ac = new ClassPathXmlApplicationContext();
        TbItemMapper itemMapper = ac.getBean(TbItemMapper.class);
        PageHelper.startPage(1,10);
        TbItemExample example = new TbItemExample();
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(tbItems);
        // 获取分页的总页数
        System.out.println(pageInfo.getTotal());
    }
}
