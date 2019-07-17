package com.huigao.service.impl;

import java.util.List;

import com.huigao.dao.OverTimeDao;
import com.huigao.pojo.OverTime;
import com.huigao.service.OverTimeService;

public class OverTimeServiceImpl implements OverTimeService {

	private OverTimeDao overTimeDao;
	
	public OverTime getOverTimeById(Integer overTimeId) {
		return overTimeDao.getById(overTimeId);
	}

	public List<OverTime> list() {
		return overTimeDao.list();
	}
	
	public void deleteOverTime(OverTime overTime) {
		overTimeDao.delete(overTime);
	}
	
	public void saveOverTime(OverTime overTime) {
		overTimeDao.save(overTime);
	}
	
	public Integer getTotalCount() {
		return overTimeDao.getTotalCount();
	}

	public List<OverTime> list(int start, int limit) {
		return overTimeDao.list(start, limit); 
	}

	// --- Inject ---
	public void setOverTimeDao(OverTimeDao overTimeDao) {
		this.overTimeDao = overTimeDao;
	}

}
