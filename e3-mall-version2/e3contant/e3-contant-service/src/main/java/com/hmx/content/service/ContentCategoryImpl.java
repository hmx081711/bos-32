package com.hmx.content.service;

import com.hmx.content.interf.ContentCatgory;
import com.hmx.e3_common.pojo.EasyUiTreeNode;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_dao.mapper.TbContentCategoryMapper;
import com.hmx.e3_pojo.TbContentCategory;
import com.hmx.e3_pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmx.e3_pojo.TbContentCategoryExample.Criteria;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public TaotaoResult addCategory(long parentId, String name) {
        // 创建poto对象接收
        TbContentCategory contentCategory = new TbContentCategory();
        //插入父id和节点名称
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        //1(正常),2(删除)
        contentCategory.setStatus(1);
        //新添加的节点一定是叶子节点
        contentCategory.setIsParent(false);
        //默认排序就是1
        contentCategory.setSortOrder(1);
        Date date = new Date();
        contentCategory.setCreated(date);
        contentCategory.setUpdated(date);
        //判断父节点的isParent属性
        TbContentCategory parent = categoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            categoryMapper.updateByPrimaryKey(parent);
        }
        categoryMapper.insert(contentCategory);
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult deleteCategory(long id) {
        //删除指定id的数据
        TbContentCategory contentCategory = categoryMapper.selectByPrimaryKey(id);
        contentCategory.setStatus(0);
        contentCategory.setParentId(-1l);
        //更新数据库
        categoryMapper.updateByPrimaryKeySelective(contentCategory);
        //判断父节点
        Long parentId = contentCategory.getParentId();
        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        if (categoryMapper.countByExample(example)==0) {
            categoryMapper.selectByPrimaryKey(parentId).setIsParent(false);
        }
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult renameCategory(long id, String name) {
        // 根据id查找商品类别
        TbContentCategory tbContentCategory = categoryMapper.selectByPrimaryKey(id);
        // 修改商品类别名称
        tbContentCategory.setName(name);
        // 更新数据库
        tbContentCategory.setUpdated(new Date());
        categoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        return TaotaoResult.ok(tbContentCategory);
    }

}
