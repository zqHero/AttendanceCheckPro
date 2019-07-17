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
		String hql = " select count(*) from OverTime where user=:user and :date between  startTime and endTime ";
		return ((Long)(getHibernateTemplate().findByNamedParam(hql, new String[]{"date","user"}, new Object[]{cal.getTime(),user}).get(0))).intValue() > 0;
	}

	@SuppressWarnings("unchecked")
	public List<OverTime> list() {
		return getHibernateTemplate().find(" from OverTime "); 
	}
	
	public void save(OverTime overTime) {
		getHibernateTemplate().save(overTime);
	}
	
	@SuppressWarnings("unchecked")
	public List<OverTime> list(final int start, final int limit) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from OverTime").setFirstResult(start).setMaxResults(limit).list();
			}
		}); 
	}
	
	public Integer getTotalCount(){
		return ((Long)(getHibernateTemplate().find("select count(*) from OverTime").get(0))).intValue();
	}

}
