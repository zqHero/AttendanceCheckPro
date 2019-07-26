package com.huigao.dao.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.OverTimeDao;
import com.huigao.pojo.OverTime;
import com.huigao.pojo.Users;

public class OverTimeDaoImpl extends HibernateDaoSupport implements OverTimeDao {

	public void delete(OverTime overTime) {
		getHibernateTemplate().delete(overTime);
	}

	public OverTime getById(Integer overTimeId) {
		return (OverTime) getHibernateTemplate().get(OverTime.class, overTimeId);
	}

	public boolean isOverTime(Users user,Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 9);
		String tabName = "OverTime";
		String hql = " select count(*) from " + tabName + " o where o.user=:user and :date between  o.startTime and o.endTime ";
		return ((Long)(getHibernateTemplate().findByNamedParam(hql, new String[]{"date","user"}, new Object[]{cal.getTime(),user}).get(0))).intValue() > 0;
	}

	@SuppressWarnings("unchecked")
	public List<OverTime> list() {
		String tabName = "OverTime";
		return getHibernateTemplate().find(" from " + tabName);
	}
	
	public void save(OverTime overTime) {
		getHibernateTemplate().save(overTime);
	}
	
	@SuppressWarnings("unchecked")
	public List<OverTime> list(final int start, final int limit) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String tabName = "OverTime";
				return session.createQuery("from " + tabName).setFirstResult(start).setMaxResults(limit).list();
			}
		}); 
	}
	
	public Integer getTotalCount(){
		String tabName = "OverTime";
		return ((Long)(getHibernateTemplate().find("select count(*) from " + tabName).get(0))).intValue();
	}

}
