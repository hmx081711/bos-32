package com.hmx.bos.dao.impl;

import java.io.Serializable;
import java.util.List;
/**
 * 持久层提取通用接口
 * @author DELL
 *
 * @param <T>
 */
public interface BaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public T findById(Serializable id);
	public List<T> findAll();
}
