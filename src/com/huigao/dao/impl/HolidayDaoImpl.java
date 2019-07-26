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
		String tabName = "HolidayLog";
		String hql = " select sum(d.endTime-d.startTime) from " + tabName + " d where d.holiday.id=:holidayId and d.users.id=:userId " +
				" and d.startTime between :startTime and :endTime  ";
		Double seconds = (Double) getHibernateTemplate().findByNamedParam(hql, new String[]{"holidayId","userId","startTime","endTime"}, new Object[]{holidayId,userId,startDate,endDate}).get(0);
		if(seconds == null || seconds.intValue() == 0) return 0;
		return new Double(seconds / 60).intValue();
	}
	
	public Holiday getById(Integer holidayId) {
		return (Holiday) getHibernateTemplate().get(Holiday.class, holidayId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Holiday> listHoliday() {
		String tabName = "Holiday";
		return getHibernateTemplate().find(" from " + tabName);
	}

	public void deleteHolidayLog(HolidayLog holidayLog) {
		getHibernateTemplate().delete(holidayLog);
	}
	
	@SuppressWarnings("unchecked")
	public List<HolidayLog> listHolidayLogByUser(final int userId, final int start, final int limit) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String tabName = "HolidayLog";
				String hql = " from " + tabName;
				if(userId != 0){
					hql = hql + " h where h.users.id=:userId order by h.startTime desc ";
				}else{
					hql = hql + " h order by h.startTime desc ";
				}
//				if(userId != 0) hql = hql + " h where h.users.id=:userId";
//				hql = hql + " h order by h.startTime desc ";

				Query query = session.createQuery(hql);
				if(userId != 0) query.setInteger("userId", userId);
				return query.setFirstResult(start).setMaxResults(limit).list();
			}
		}); 
	}
	
	public Integer getHolidayLogCountByUser(final int userId) {
		return ((Long)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String tabName = "HolidayLog";
				String hql = " select count(*) from " + tabName;
				if(userId != 0) hql = hql + " h where h.users.id=:userId ";
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
		String tabName = "HolidayLog";
		String hql = " select count(*) from " + tabName + " h where h.startTime <=:startTime and h.endTime >= :endTime ";
		return ((Long)(getHibernateTemplate().findByNamedParam(hql, new String[]{"startTime","endTime"}, new Object[]{startTime,endTime}).get(0))).intValue() > 0;
	}
}
