package com.huigao.service;

import java.util.List;

import com.huigao.pojo.Holiday;
import com.huigao.pojo.HolidayLog;

/**
 * 请假管理(未用)
 * @author cgx
 * @version 1.0
 */
public interface HolidayService {
	
	public List<Holiday> listHoliday();
	
	public Holiday getById(Integer holidayId);
	
	public List<HolidayLog> listHolidayLogByUser(int userId, int start, int limit); 
	
	public Integer getHolidayLogCountByUser(int userId);
	
	public void saveHolidayLog(HolidayLog holidayLog);
	
	public void deleteHolidayLog(HolidayLog holidayLog) throws Exception;
	
	public HolidayLog getLogById(Integer holidayLogId) ;
}
