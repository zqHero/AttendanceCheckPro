package com.huigao.service;

import java.util.List;

import com.huigao.pojo.OverTime;

/**
 * 加班管理(未用)
 * @author cgx
 * @version 1.0
 */
public interface OverTimeService {
	
	public OverTime getOverTimeById(Integer overTimeId);
	
	public List<OverTime> list();
	
	public List<OverTime> list(int start, int limit);
	public Integer getTotalCount();
	
	public void deleteOverTime(OverTime overTime);
	
	public void saveOverTime(OverTime overTime);
	
}
