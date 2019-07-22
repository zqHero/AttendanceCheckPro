package com.huigao.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.WorkTimeDao;
import com.huigao.pojo.WorkTime;

public class WorkTimeDaoImpl extends HibernateDaoSupport implements WorkTimeDao {

	public WorkTime getById(Integer id) {
		return (WorkTime) getHibernateTemplate().get(WorkTime.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<WorkTime> list() {
		return getHibernateTemplate().find(" from WorkTime ");
	} 

}
