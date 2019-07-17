package com.huigao.dao;

import java.util.Date;
import java.util.List;

import com.huigao.pojo.OverTime;
import com.huigao.pojo.Users;

public interface OverTimeDao {
	
	public List<OverTime> list();
	
	public List<OverTime> list(int start, int limit);
	public Integer getTotalCount();
	
	public OverTime getById(Integer overTimeId);
	
	public void delete(OverTime overTime);
	
	public boolean isOverTime(Users user,Date date);
	
	public void save(OverTime overTime);
	
}
