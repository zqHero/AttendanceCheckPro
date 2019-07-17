package com.huigao.dao;

import java.util.List;

import com.huigao.pojo.SystemTips;

/**
 * 系统提示部分数据库操作
 * @author cgx
 * @version 1.0
 */
public interface SystemTipsDao {
	
	/**
	 * 通过编号获取系统提示
	 * @param id 系统提示编号
	 * @return 系统提示
	 */
	public SystemTips getById(Integer id);
	
	/**
	 * 跟新某条系统提示信息
	 * @param tips 系统提示
	 */
	public void update(SystemTips tips) ;
	
	/**
	 * 系统提示列表
	 * @return 系统提示集合
	 */
	public List<SystemTips> list();
}
