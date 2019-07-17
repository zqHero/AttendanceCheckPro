package com.huigao.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.huigao.pojo.DakaLog;
import com.huigao.pojo.Users;
import com.huigao.pojo.WorkTime;

/**
 * 打卡管理
 * @author cgx
 * @version 1.0
 */
public interface DakaService {
	
	/**
	 * 分页查询某个员工在某段时间的打卡记录
	 * @param userId 用户编号
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param start 其实记录
	 * @param limit 每页显示记录数
	 * @return 打卡记录集合
	 */
	public List<DakaLog> listByDateTime(Integer userId,Date beginDate,Date endDate,Integer start,Integer limit);
	
	/**
	 * 查询某个员工在某段时间的打卡记录数
	 * @param userId 用户编号
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 打卡记录数
	 */
	public Integer getCountByDateTime(Integer userId,Date beginDate,Date endDate);
	
	public WorkTime getWorkTimeById(Integer id);
	
	/**
	 * 按部门分页查询某段时间的打卡记录
	 * @param departmentId 部门编号
	 * @param beginDate 开始时间
	 * @param endDate 结束时间 
	 * @param start 开始记录
	 * @param limit 每页显示记录数
	 * @return 打卡记录集合
	 */
	public List<DakaLog> listByDateTimeAndDepartment(Integer departmentId,Date beginDate,Date endDate,Integer start,Integer limit);
	
	/**
	 * 按部门查询某段时间的打卡记录数
	 * @param departmentId 部门编号
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 打卡记录数
	 */
	public Integer getCountByDateTimeAndDepartment(Integer departmentId,Date beginDate,Date endDate); 
	
	/**
	 * 员工打卡，添加或修改一条打卡记录
	 * @param users 打卡用户
	 * @return 打卡反馈信息
	 * @throws Exception
	 */
	public String saveDakaLog(Users users) throws Exception;
	
    /**
     * 人工调整添加一条打卡记录
     * @param dakaLog 打卡记录
     * @throws Exception
     */
	public void saveDakaLog(DakaLog dakaLog) throws Exception; 
	
	/**
	 * 获取一条打卡记录
	 * @param id 打卡记录编号
	 * @return 打卡记录
	 * @throws Exception
	 */
	public DakaLog getDakaLogById(Integer id) throws Exception; 
	
	/**
	 * 通过用户和日期查询某条打卡记录
	 * @param userId 用户编号
	 * @param day 日期
	 * @return 打卡记录
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
	 * 删除打卡记录
	 * @param dakaLog 打卡记录
	 * @throws Exception
	 */
	public void deleteDakaLog(DakaLog dakaLog) throws Exception; 
	
	/**
	 * 更新打卡记录批量更新打卡记录
	 * @param collect 打卡记录集合
	 * @throws Exception
	 */
	public void updateDakaLog(Collection<DakaLog> collect) throws Exception; 
	
	/**
	 * 通过JsonArray字符串转换为相应的List集合
	 * @param array json对象字符串
	 * @return 打卡记录集合
	 */
	public Collection<DakaLog> getListByJsonArray(String array); 
	
}
