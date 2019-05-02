package com.hmx.e3_service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hmx.e3_common.pojo.EasyUiDataGridresult;
import com.hmx.e3_pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;


import com.hmx.e3_dao.mapper.TbItemMapper;
import com.hmx.e3_interface.ItemService;
import com.hmx.e3_pojo.TbItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 根据id查询商品
 * @author DELL
 *
 */
@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private TbItemMapper tbitemMapper;

	@Override
	public TbItem findItemByd(Long id) {
		// TODO Auto-generated method stub
		return tbitemMapper.selectByPrimaryKey(id);
	}

	/**
	 * 返回分页后的商品结果
	 * @author DELL
	 * @param page 分页商品的页数
	 * @param rows 每页的数据条数
	 * @return easyUIGridResult对象
	 */
	@Override
	public EasyUiDataGridresult getItemList(Integer page, Integer rows) {

		PageHelper.startPage(page,rows);

		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbitemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);

		EasyUiDataGridresult easyUiDataGridresult = new EasyUiDataGridresult();
		easyUiDataGridresult.setTotal(pageInfo.getTotal());
		easyUiDataGridresult.setRows(list);
		return easyUiDataGridresult;
	}
}
