package com.hmx.content.service;

import com.hmx.content.interf.ContentCatgory;
import com.hmx.e3_common.pojo.EasyUiTreeNode;
import com.hmx.e3_dao.mapper.TbContentCategoryMapper;
import com.hmx.e3_pojo.TbContentCategory;
import com.hmx.e3_pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmx.e3_pojo.TbContentCategoryExample.Criteria;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentCategoryImpl implements ContentCatgory {

    /**
     * 内容分类管理Service
     * @param parentId 分类的父id
     * @return 分类的列表
     */
    @Autowired
    private TbContentCategoryMapper categoryMapper;
    @Override
    public List<EasyUiTreeNode> getCategoryCatList(long parentId) {

        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> categoryList = categoryMapper.selectByExample(example);


        List<EasyUiTreeNode> list = new ArrayList<EasyUiTreeNode>();
        for (TbContentCategory category:categoryList) {
                EasyUiTreeNode easyUiTreeNode = new EasyUiTreeNode();
                easyUiTreeNode.setId(category.getId());
                String state = category.getIsParent()?"closed":"open";
                easyUiTreeNode.setState(state);
                easyUiTreeNode.setText(category.getName());
                list.add(easyUiTreeNode);
        }
        return list;
    }
}
