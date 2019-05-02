package com.hmx.e3_service;

import com.hmx.e3_common.pojo.EasyUiTreeNode;
import com.hmx.e3_dao.mapper.TbItemCatMapper;
import com.hmx.e3_interface.ItemCatService;
import com.hmx.e3_pojo.TbItemCat;
import com.hmx.e3_pojo.TbItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import com.hmx.e3_pojo.TbItemCatExample.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;


    @Override
    public List<EasyUiTreeNode> getCatList(long parentId) {
        // 1、根据parentId查询节点列表
        TbItemCatExample example = new TbItemCatExample();
        //设置查询条件
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        // 2、转换成EasyUITreeNode列表。
        List<EasyUiTreeNode> resultList = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            //添加到列表
            resultList.add(node);
        }
        // 3、返回。
        return resultList;
    }

}
