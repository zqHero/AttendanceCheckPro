package com.huigao.service.impl;

import java.util.List;

import com.huigao.dao.StatisticDao;
import com.huigao.pojo.Statistic;
import com.huigao.service.StatisticService;

public class StatisticServiceImpl implements StatisticService {

	private StatisticDao statisticDao;

	public void setStatisticDao(StatisticDao statisticDao) {
		this.statisticDao = statisticDao;
	}

	public void delete(Statistic som) {
		statisticDao.delete(som);
	}

	public List<Statistic> listByDepartment(Integer departmentId,
			Integer year, Integer month, int start, int limit) {
		return statisticDao.listByDepartment(departmentId, year, month, start, limit);
	}

	public List<Statistic> listByDepartment(Integer departmentId,
			Integer year, int start, int limit) {
		return statisticDao.listByDepartment(departmentId, year, start, limit);
	}
	
	public boolean hasStatistic(Integer year,Integer month) {
		return statisticDao.getCountByDepartment(0, year, month) > 0;
	}
	
	public void statistic(Integer year, Integer month) {
		statisticDao.statistic(year, month);
	}
	
	/*public void statisticByUser(Integer year,Integer month, Integer userId) {
		
		statisticDao.deleteByUser(year, month, userId);//先删除已有数据
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, 1, 0, 0, 0);
		Date startDate = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.SECOND, -1);
		Date endDate = cal.getTime();
		
		Statistic som = new Statistic();
		som.setUsers(usersDao.getById(userId)); 
		som.setYear(year);
		som.setMonth(month);
		som.setWorkTime(dakaDao.getWorkTime(userId, startDate, endDate)); 
		som.setBusinessTime(holidayDao.getHolidayTime(userId, 1, startDate, endDate));
		som.setSickTime(holidayDao.getHolidayTime(userId, 2, startDate, endDate));
		som.setVacationTime(holidayDao.getHolidayTime(userId, 3, startDate, endDate));
		som.setOverTime(holidayDao.getHolidayTime(userId, 4, startDate, endDate));
		som.setCountOfEO(dakaDao.getEarlyCount(userId, startDate, endDate));
		som.setCountOfLate(dakaDao.getLateCount(userId, startDate, endDate));
		statisticDao.save(som);
	}*/

	public Integer getCountByDepartment(Integer departmentId, Integer year, Integer month) {
		return statisticDao.getCountByDepartment(departmentId, year, month);
	}

	public Integer getCountByDepartment(Integer departmentId, Integer year) {
		return statisticDao.getCountByDepartment(departmentId, year); 
	}

}
