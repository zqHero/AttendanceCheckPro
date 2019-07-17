package com.huigao.dao;

import java.util.List;

import com.huigao.pojo.Statistic;

/**
 * 打卡统计部分数据库操作
 * @author cgx
 * @version 1.0
 */
public interface StatisticDao {
	
	/**
	 * 保存一条统计信息
	 * @param som 一条统计信息
	 */
	public void save(Statistic som);
	
	/**
	 * 删除一条统计信息
	 * @param som 一条统计信息
	 */
	public void delete(Statistic som);
	
	/**
	 * 删除某个月份的统计信息 
	 * @param year 年
	 * @param month 月
	 */
	public void delete(Integer year,Integer month);
	
	/**
	 * 统计某个月份的打卡信息
	 * @param year 年
	 * @param month 月
	 */
	public void statistic(Integer year,Integer month);
	
	/**
	 * 删除某个人某月份的统计信息
	 * @param year 年
	 * @param month 月
	 */
	public void deleteByUser(Integer year,Integer month,Integer userId);
	
	/**
	 * 查询某个部门的员工月度统计的数量
	 * @param departmentId 部门编号
	 * @param year 年
	 * @param month 月
	 * @return 打卡统计数量
	 */
	public Integer getCountByDepartment(Integer departmentId,Integer year,Integer month);
	
	/**
	 * 查询某个部门的员工月度统计
	 * @param departmentId 部门编号
	 * @return 统计集合
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

}
