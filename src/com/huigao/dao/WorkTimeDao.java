package com.huigao.dao;

import java.util.List;

import com.huigao.pojo.WorkTime;

/**
 * 工作时间表 数据库操作
 * @author cgx
 * @version 1.0
 */
public interface WorkTimeDao {
	
	/**
	 * 工作时间表集合
	 * @return 工作时间表集合
	 */
	public List<WorkTime> list();
	
	/**
	 * 获取时间表上的某个时间
	 * @param id 时间表编号
	 * @return 时间
	 */
	public WorkTime getById(Integer id);
}
