package com.huigao.dao;

import java.util.Date;
import java.util.List;

import com.huigao.pojo.Holiday;
import com.huigao.pojo.HolidayLog;

/**
 * 假期管理部分(未用)
 * @author cgx
 *
 */
public interface HolidayDao {
	
	/**
	 * 某人在某段时间的某种类型的假期时间(如果跨月的话，尚有问题)
	 * @param userid 员工编号
	 * @param holidayId 假期类型编号
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 返回假期时间(单位:minute)
	 */
	public Integer getHolidayTime(Integer userId,Integer holidayId, Date startDate, Date endDate);
	
	/**
	 * 获取假期类型
	 * @param HolidayId 假期类型编号
	 * @return
	 */
	public Holiday getById(Integer HolidayId);
	
	/**
	 * 假期类型列表
	 * @return 假期类型集合
	 */
	public List<Holiday> listHoliday();
	
	/**
	 * 查询请假记录
	 * @param userId 员工编号
	 * @param start 开始记录数
	 * @param limit 每页显示记录数
	 * @return 请假记录集合
	 */
	public List<HolidayLog> listHolidayLogByUser(int userId, int start, int limit); 
	
	public Integer getHolidayLogCountByUser(int userId);
	
	public void saveHolidayLog(HolidayLog holidayLog);
	
	public void deleteHolidayLog(HolidayLog holidayLog);
	
	public HolidayLog getLogById(Integer holidayLogId);
	
	public boolean isHoliday(Date startTime,Date endTime);
}
