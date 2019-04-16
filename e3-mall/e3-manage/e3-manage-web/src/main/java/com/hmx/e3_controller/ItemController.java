package com.hmx.e3_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmx.e3_pojo.TbItem;
import com.hmx.e3_service.ItemServiceImpl;

@Controller
public class ItemController {
	
	@Autowired
	private ItemServiceImpl itemservicempl;
	@RequestMapping(value = "/item/{itemId}")
	public @ResponseBody TbItem getItemById(@PathVariable Long itemId) {
		 TbItem item = itemservicempl.findItemByd(itemId);
		return item;		
	}
}
