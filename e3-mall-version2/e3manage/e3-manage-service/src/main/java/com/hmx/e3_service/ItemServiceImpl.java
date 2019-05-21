package com.hmx.e3_service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hmx.e3_common.pojo.EasyUiDataGridresult;
import com.hmx.e3_common.pojo.IDUtils;
import com.hmx.e3_common.pojo.TaotaoResult;
import com.hmx.e3_dao.mapper.TbItemDescMapper;
import com.hmx.e3_dao.mapper.TbItemMapper;
import com.hmx.e3_interface.ItemService;
import com.hmx.e3_pojo.TbItem;
import com.hmx.e3_pojo.TbItemDesc;
import com.hmx.e3_pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Date;
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
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination destination;
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

	/**
	 * 添加商品
	 * @param item 用于保存商品的poto
	 * @param desc 用于保存商品描述的poto
	 * @return TaotaoResult对象
	 */
	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		//1.生成商品id
		final long itemId = IDUtils.genItemId();
		//2.保存商品信息
		item.setId(itemId);
		//'商品状态，1-正常，2-下架，3-删除'
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		//3.保存商品描述信息
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		// 插入数据
		tbitemMapper.insert(item);
		tbItemDescMapper.insert(tbItemDesc);
		//发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId + "");
				return textMessage;
			}
		});
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateItem(TbItem item, String desc,Byte status,Date created) {

		// 重置修改时间
		Date date = new Date();
		item.setUpdated(date);
		item.setCreated(created);
		item.setStatus(status);
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(created);
		itemDesc.setUpdated(date);
		// 更新数据库
		tbitemMapper.updateByPrimaryKey(item);
		tbItemDescMapper.updateByPrimaryKey(itemDesc);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItem(TbItem item) {
		// 修改上架状态
		item.setStatus((byte)3);
		Date date = new Date();
		item.setUpdated(date);
		tbitemMapper.updateByPrimaryKey(item);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult onSaleItem(TbItem item) {
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setUpdated(date);
		tbitemMapper.updateByPrimaryKey(item);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult offSaleItem(TbItem item) {
		item.setStatus((byte) 2);
		Date date = new Date();
		item.setUpdated(date);
		tbitemMapper.updateByPrimaryKey(item);
		return TaotaoResult.ok();
	}

}
