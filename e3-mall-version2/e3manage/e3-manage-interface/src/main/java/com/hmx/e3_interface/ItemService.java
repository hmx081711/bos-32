package com.hmx.e3_interface;

import com.hmx.e3_common.pojo.EasyUiDataGridresult;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_pojo.TbItem;

import java.util.Date;


public interface ItemService {
	TbItem findItemByd(Long id);
	EasyUiDataGridresult getItemList(Integer page, Integer rows);
	TaotaoResult addItem(TbItem item, String desc);
	TaotaoResult updateItem(TbItem item,String desc, Byte status, Date created);
	TaotaoResult deleteItem(TbItem item);
	TaotaoResult onSaleItem(TbItem item);
	TaotaoResult offSaleItem(TbItem item);
}
