package com.hmx.e3_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmx.e3_dao.mapper.TbItemMapper;
import com.hmx.e3_interface.ItemService;
import com.hmx.e3_pojo.TbItem;

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
	
}
