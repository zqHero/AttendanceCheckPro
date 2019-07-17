package com.huigao.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.HolidayDao;
import com.huigao.pojo.Holiday;
import com.huigao.pojo.HolidayLog;

public class HolidayDaoImpl extends HibernateDaoSupport implements HolidayDao {

	public Integer getHolidayTime(Integer userId,Integer holidayId, Date startDate, Date endDate) {
		String hql = " select sum(endTime-startTime) from HolidayLog where holiday.id=:holidayId and users.id=:userId " +
				" and startTime between :startTime and :endTime  ";
		Double seconds = (Double) getHibernateTemplate().findByNamedParam(hql, new String[]{"holidayId","userId","startTime","endTime"}, new Object[]{holidayId,userId,startDate,endDate}).get(0);
		if(seconds == null || seconds.intValue() == 0) return 0;
		return new Double(seconds / 60).intValue();
	}
	
	public Holiday getById(Integer holidayId) {
		return (Holiday) getHibernateTemplate().get(Holiday.class, holidayId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Holiday> listHoliday() {
		return getHibernateTemplate().find(" from Holiday ");
	}

	public void deleteHolidayLog(HolidayLog holidayLog) {
		getHibernateTemplate().delete(holidayLog);
	}
	
	@SuppressWarnings("unchecked")
	public List<HolidayLog> listHolidayLogByUser(final int userId, final int start, final int limit) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = " from HolidayLog ";
				if(userId != 0) hql = hql + " where users.id=:userId ";
				hql = hql + " order by startTime desc ";
				Query query = session.createQuery(hql);
				if(userId != 0) query.setInteger("userId", userId);
				return query.setFirstResult(start).setMaxResults(limit).list();
			}
		}); 
	}
	
	public Integer getHolidayLogCountByUser(final int userId) {
		return ((Long)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = " select count(*) from HolidayLog  ";
				if(userId != 0) hql = hql + " where users.id=:userId ";
				Query query = session.createQuery(hql);
				if(userId != 0) query.setInteger("userId", userId);
				return query.list().get(0); 
			}
		})).intValue();
	}

	public void saveHolidayLog(HolidayLog holidayLog) {
		getHibernateTemplate().save(holidayLog);
	}

	public HolidayLog getLogById(Integer holidayLogId) {
		return (HolidayLog) getHibernateTemplate().get(HolidayLog.class, holidayLogId);
	}
	
	public boolean isHoliday(Date startTime,Date endTime){
		String hql = " select count(*) from HolidayLog where startTime <=:startTime and endTime >= :endTime ";
		return ((Long)(getHibernateTemplate().findByNamedParam(hql, new String[]{"startTime","endTime"}, new Object[]{startTime,endTime}).get(0))).intValue() > 0;
	}
}
