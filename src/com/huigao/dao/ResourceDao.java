package com.huigao.dao;

import java.util.List;

import com.huigao.pojo.Resource;

/**
 * 系统资源部分数据库操作
 * @author cgx
 * @version 1.0
 */
public interface ResourceDao {
	
	/**
	 * 系统资源列表
	 * @return 系统资源集合
	 */
	public List<Resource> list();
	
	/**
	 * 获取某个资源
	 * @param resourceId 资源编号
	 * @return 系统资源
	 */
	public Resource getById(Integer resourceId);
	
	/**
	 * 跟新某个系统资源
	 * @param resource 系统资源
	 */
	public void update(Resource resource);
	
	/**
	 * 将某个系统资源与数据库合并
	 * @param resource 资源
	 */
	public void merge(Resource resource);
}
