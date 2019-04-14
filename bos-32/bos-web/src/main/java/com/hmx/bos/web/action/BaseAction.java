package com.hmx.bos.web.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	// ģ�Ͷ���
	private T model;
	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	// �ӹ��췽���л�ȡʵ�������ͣ�ͨ�����䴴��ʵ�������
	public BaseAction() {
		// TODO Auto-generated constructor stub
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		// ͨ����������������
		try {
			this.model = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
