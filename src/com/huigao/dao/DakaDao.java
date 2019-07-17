package com.huigao.dao;

import java.util.Date;
import java.util.List;

import com.huigao.pojo.DakaLog;

/**
 * 打卡管理部分数据库操作
 * @author cgx
 * @version 1.0
 */
public interface DakaDao {
	
	/**
	 * 查询某个员工在某段时间的打卡记录
	 * @param userId 员工编号
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param start 分页起始记录
	 * @param limit 每页显示记录数
	 * @return 打卡日志列表
	 */
	public List<DakaLog> listByDateTime(Integer userId,Date beginDate,Date endDate,Integer start,Integer limit); 
	
	/**
	 * 查询某个员工在某段时间的打卡记录数
	 * @param userId 员工编号
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 查询结果数
	 */
	public Integer getCountByDateTime(Integer userId,Date beginDate,Date endDate); 
	
	/**
	 * 查询某个部门所有员工在某段时间的打卡记录
	 * @param departmentId 部门编号
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param start 分页起始记录
	 * @param limit 每页显示记录数
	 * @return 打卡记录集合
	 */
	public List<DakaLog> listByDateTimeAndDepartment(Integer departmentId,Date beginDate,Date endDate,Integer start,Integer limit); 
	
	/**
	 * 查询某个部门所有员工在某段时间的打卡记录数
	 * @param departmentId 部门编号
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 打卡记录条数
	 */
	public Integer getCountByDateTimeAndDepartment(Integer departmentId,Date beginDate,Date endDate); 
	
	/**
	 * 添加保存新的打卡记录
	 * @param dakaLog 打卡记录
	 * @throws Exception
	 */
	public void saveDakaLog(DakaLog dakaLog) throws Exception;
	
	/**
	 * 保存或修改打卡记录
	 * @param dakaLog 打卡记录
	 * @throws Exception
	 */
	public void saveOrUpdate(DakaLog dakaLog) throws Exception; 
	
	/**
	 * 通过打卡记录编号查询某条打卡记录
	 * @param id 编号
	 * @return 打卡记录，如果没有返回null。
	 */
	public DakaLog getDakaLogById(Integer id) ; 
	
	/**
	 * 查询一条打卡记录
	 * @param userId 员工编号 
	 * @param day 日期
	 * @return 打卡记录对象，如果没有返回null
	 * @throws Exception
	 */
	public DakaLog getDakaLogByUserAndDay(Integer userId,Date day) throws Exception; 
	
	/**
	 * 更新打卡记录
	 * @param dakaLog 打卡记录
	 * @throws Exception
	 */
	public void updateDakaLog(DakaLog dakaLog) throws Exception;
	
	/**
	 * 删除某条打卡记录
	 * @param dakaLog 打卡记录
	 * @throws Exception
	 */
	public void deleteDakaLog(DakaLog dakaLog) throws Exception; 
	

	/**
	 * 某人在某段时间的工作时间(分钟)
	 * @param userid 
	 * @param startDate
	 * @param endDate
	 * @return
	 *//*
	public Integer getWorkTime(Integer userid, Date startDate, Date endDate);
	
	*//**
	 * 某人在某段时间的加班时间
	 * @param userid
	 * @param startDate
	 * @param endDate
	 * @return
	 *//*
	public Integer getOverTime(Integer userid, Date startDate, Date endDate);
	
	*//**
	 * 某人在某段时间的迟到次数
	 * @param userid
	 * @param startDate
	 * @param endDate
	 * @return
	 *//*
	public Integer getLateCount(Integer userid, Date startDate, Date endDate);
	
	*//**
	 * 某人在某段时间的早退次数
	 * @param userid
	 * @param startDate
	 * @param endDate
	 * @return
	 *//*
	public Integer getEarlyCount(Integer userid, Date startDate, Date endDate);
	*/
}
