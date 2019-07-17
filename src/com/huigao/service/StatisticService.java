package com.huigao.service;

import java.util.List;

import com.huigao.pojo.Statistic;

/**
 * 统计管理
 * @author cgx
 * @version 1.0
 */
public interface StatisticService {
	
	/**
	 * 查询某个部门的员工月度统计的数量
	 * @param departmentId 部门编号
	 * @param year 年
	 * @param month 月
	 * @return 月度统计数量
	 */
	public Integer getCountByDepartment(Integer departmentId,Integer year,Integer month);
	
	/**
	 * 分页查询某个部门的员工月度统计
	 * @param departmentId 部门编号
	 * @return 部门集合
	 */
	public List<Statistic> listByDepartment(Integer departmentId,Integer year,Integer month, int start, int limit);
	
	
	/**
	 * 查询某个部门的员工年度统计的数量
	 * @param departmentId 部门编号
	 * @param year 年
	 * @return 年度统计数量
	 */
	public Integer getCountByDepartment(Integer departmentId,Integer year);
	
	
	/**
	 * 查询某个部门的员工年度统计
	 * @param departmentId 部门编号
	 * @param year 年
	 * @return 年度统计集合
	 */
	public List<Statistic> listByDepartment(Integer departmentId,Integer year,int start,int limit);
	
	
	/**
	 * 某个月的打卡数据是否已经统计
	 * @param year 年
	 * @param month 月
	 * @return 如果该月已经统计过返回true，否则返回false
	 */
	public boolean hasStatistic(Integer year,Integer month);
	
	/**
	 * 生成月度统计
	 * @param year 年
	 * @param month 月
	 */
	public void statistic(Integer year, Integer month);
	
}
