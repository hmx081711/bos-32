package com.hmx.bos.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	private Class<T> entityClass;
	
	@Resource
	public void mySessionFactory(SessionFactory factory) {
		super.setSessionFactory(factory);
	}
	public BaseDaoImpl() {
		// TODO Auto-generated constructor stub
		//注意一定要用ParameterizedType来接
		ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获取范型数组
		Type[] actualTypeArguments = superClass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public T findById(Serializable id) {
		// TODO Auto-generated method stub	
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		String hql = "FROM" + entityClass.getSimpleName();
		
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

}
