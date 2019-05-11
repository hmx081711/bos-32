package com.hmx.content.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hmx.content.interf.ContentServce;
import com.hmx.e3_common.pojo.EasyUiDataGridresult;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_dao.mapper.TbContentMapper;
import com.hmx.e3_pojo.TbContent;
import com.hmx.e3_pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmx.e3_pojo.TbContentExample.Criteria;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentServce {

    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public EasyUiDataGridresult showContentList(long categoryId,Integer page, Integer rows) {
        // 分页
        PageHelper.startPage(page,rows);
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = contentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(tbContents);
        //将结果集转化为easyuDataresult对象
        EasyUiDataGridresult easyUiDataGridresult = new EasyUiDataGridresult();
        easyUiDataGridresult.setTotal(pageInfo.getTotal());
        easyUiDataGridresult.setRows(tbContents);
        return easyUiDataGridresult;
    }

    @Override
    public TaotaoResult addContent(TbContent tbContent) {

        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        // 更新到数据库
        contentMapper.insert(tbContent);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult editContent(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(tbContent);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(long ids) {
        contentMapper.deleteByPrimaryKey(ids);
        return TaotaoResult.ok();
    }

    /**
     * 根据分类id查询内容列表
     * @param cid 分类的id
     * @return 查询到的内容列表
     */
    @Override
    public List<TbContent> getContentBycid(Long cid) {
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> tbContents = contentMapper.selectByExampleWithBLOBs(example);
        return tbContents;
    }


}
