package com.huigao.service.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.huigao.dao.HolidayDao;
import com.huigao.pojo.Holiday;
import com.huigao.pojo.HolidayLog;
import com.huigao.service.HolidayService;

public class HolidayServiceImpl implements HolidayService {

	private HolidayDao holidayDao;
	
	public List<Holiday> listHoliday() {
		return holidayDao.listHoliday();
	}
	
	public Holiday getById(Integer holidayId) {
		return holidayDao.getById(holidayId);
	}

	public void deleteHolidayLog(HolidayLog holidayLog) throws Exception {
		HolidayLog log = new HolidayLog();
		BeanUtils.copyProperties(log, holidayLog); //备份
		holidayDao.deleteHolidayLog(holidayLog);
		this.checkAndFix_delete(log);//检查并修正
	}

	public Integer getHolidayLogCountByUser(int userId) {
		return holidayDao.getHolidayLogCountByUser(userId);
	}
	
	public List<HolidayLog> listHolidayLogByUser(int userId, int start, int limit) {
		return holidayDao.listHolidayLogByUser(userId, start, limit);
	}
	
	public void saveHolidayLog(HolidayLog holidayLog) {
		holidayDao.saveHolidayLog(holidayLog);
		this.checkAndFix_save(holidayLog);//检查并修正
	}
	
	public HolidayLog getLogById(Integer holidayLogId) {
		return holidayDao.getLogById(holidayLogId);
	}
	
	//--- inject ---
	public void setHolidayDao(HolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}
	
	private void checkAndFix_save(HolidayLog holidayLog){
		//检查上班迟到，如果迟到并且属于假期，将迟到修改为相应提示
		/*Date standardStartTime = DateUtil.getDateFromTime(workTimeDao.getById(1).getTime());
		try {
			DakaLog log = dakaDao.getDakaLogByUserAndDay(holidayLog.getUsers().getId(), holidayLog.getEndTime());
			if(log != null && log.getStartTips().getId() != 1){
				boolean isHoliday = holidayDao.isHoliday(standardStartTime, log.getStartTime());
				if(isHoliday) {
					if(holidayLog.getHoliday().getId() == 1)log.setStartTips(systemTipsDao.getById(8));
					else if(holidayLog.getHoliday().getId() == 2)log.setStartTips(systemTipsDao.getById(9));
					else if(holidayLog.getHoliday().getId() == 3)log.setStartTips(systemTipsDao.getById(10));
				}
				dakaDao.updateDakaLog(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//检查下班
		Date standardEndTime = DateUtil.getDateFromTime(workTimeDao.getById(4).getTime());
		try {
			DakaLog log = dakaDao.getDakaLogByUserAndDay(holidayLog.getUsers().getId(), holidayLog.getStartTime());
			if(log != null && log.getStartTips().getId() != 6){
				boolean isHoliday = holidayDao.isHoliday(log.getEndTime(), standardEndTime);
				if(isHoliday) {
					if(holidayLog.getHoliday().getId() == 1)log.setStartTips(systemTipsDao.getById(8));
					else if(holidayLog.getHoliday().getId() == 2)log.setStartTips(systemTipsDao.getById(9));
					else if(holidayLog.getHoliday().getId() == 3)log.setStartTips(systemTipsDao.getById(10));
				}
				dakaDao.updateDakaLog(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	private void checkAndFix_delete(HolidayLog holidayLog){
		
		//TODO ...
		
		/*//检查上班迟到，如果迟到并且属于假期，将迟到修改为正常上班
		Date standardStartTime = DateUtil.getDateFromTime(workTimeDao.getById(1).getTime());
		try {
			DakaLog log = dakaDao.getDakaLogByUserAndDay(holidayLog.getUsers().getId(), holidayLog.getEndTime());
			if(log != null && log.getStartTips().getId() == 1) {
				boolean isHoliday = holidayDao.isHoliday(standardStartTime, log.getStartTime());
				if(isHoliday) log.setStartTips(systemTipsDao.getById(1));
				dakaDao.updateDakaLog(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//检查下班
		Date standardEndTime = DateUtil.getDateFromTime(workTimeDao.getById(4).getTime());
		try {
			DakaLog log = dakaDao.getDakaLogByUserAndDay(holidayLog.getUsers().getId(), holidayLog.getStartTime());
			if(log != null && log.getStartTips().getId() != 6){
				boolean isHoliday = holidayDao.isHoliday(log.getEndTime(), standardEndTime);
				if(isHoliday) log.setStartTips(systemTipsDao.getById(6));
				dakaDao.updateDakaLog(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
}
