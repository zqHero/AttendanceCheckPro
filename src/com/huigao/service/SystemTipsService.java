package com.huigao.service;

import java.util.List;

import com.huigao.pojo.SystemTips;

/**
 * 系统提示管理
 * @author cgx
 * @version 1.0
 */
public interface SystemTipsService {

	/**
	 * 获取系统提示 
	 * @param id  系统提示编号
	 * @return 系统提示
	 */
	public SystemTips getById(Integer id);
	
	/**
	 * 更新系统提示信息
	 * @param tips 系统提示
	 */
	public void update(SystemTips tips) ;
	
	/**
	 * 系统提示列表集合
	 * @return 系统提示集合
	 */
	public List<SystemTips> list();
}
