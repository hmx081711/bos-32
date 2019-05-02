package com.hmx.e3_interface;

import com.hmx.e3_common.pojo.EasyUiDataGridresult;
import com.hmx.e3_pojo.TbItem;


public interface ItemService {
	TbItem findItemByd(Long id);
	EasyUiDataGridresult getItemList(Integer page, Integer rows);
}
