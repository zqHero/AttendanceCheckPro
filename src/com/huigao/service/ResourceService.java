package com.huigao.service;

import java.util.List;

import com.huigao.pojo.Resource;

/**
 * 系统资源管理
 * @author cgx
 * @version 1.0
 */
public interface ResourceService {
	
	/**
	 * 获取一个系统资源
	 * @param resourceId 资源编号
	 * @return 系统资源
	 */
	public Resource getById(Integer resourceId);
	
	/**
	 * 系统资源列表
	 * @return 资源集合
	 */
	public List<Resource> listResources(); 
	
	/**
	 * 更新系统资源
	 * @param resource 系统资源
	 */
	public void update(Resource resource);
	
	/**
	 * 合并数据库系统资源
	 * @param resource 系统资源
	 */
	public void merge(Resource resource);
}
