package com.huigao.dao.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.DakaDao;
import com.huigao.pojo.DakaLog;

public class DakaDaoImpl extends HibernateDaoSupport implements DakaDao {
	
	public void deleteDakaLog(DakaLog dakaLog) throws Exception {
		getHibernateTemplate().delete(dakaLog);
	}
	
	public Integer getCountByDateTime(Integer userId, Date startDate, Date endDate) {
		if(userId == 0) {
			String hql = "  select count(*) from DakaLog where date1 between :startDate and :endDate or date3 between :startDate and :endDate  ";
			return ((Long) getHibernateTemplate().findByNamedParam(hql, new String[]{"startDate","endDate"}, new Object[]{startDate,endDate}).get(0)).intValue();
		} else {
			String hql = "  select count(*) from DakaLog where users.id=:userId and ( date1 between :startDate and :endDate or date3 between :startDate and :endDate ) ";
			return ((Long) getHibernateTemplate().findByNamedParam(hql, new String[]{"userId","startDate","endDate"}, new Object[]{userId,startDate,endDate}).get(0)).intValue();
		}
	}
	
	public DakaLog getDakaLogById(Integer id) {
		return (DakaLog) getHibernateTemplate().get(DakaLog.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public DakaLog getDakaLogByUserAndDay(Integer userId, Date day) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, 0);  cal.set(Calendar.MINUTE, 0); cal.set(Calendar.SECOND, 0);
		day = cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date d2 = cal.getTime();
		String hql = " from DakaLog where users.id=:userId and ( date1 between :startDate and :endDate or date2 between :startDate and :endDate or date3 between :startDate and :endDate or date4 between :startDate and :endDate ) ";
		List<DakaLog> list = getHibernateTemplate().findByNamedParam(hql, new String[]{"userId","startDate","endDate"}, new Object[]{userId,day,d2});
		if(list==null || list.isEmpty()) return null;
		return list.get(0);
	}
	
	public void saveOrUpdate(DakaLog dakaLog) {
		getHibernateTemplate().saveOrUpdate(dakaLog);
	}
	
	@SuppressWarnings("unchecked")
	public List<DakaLog> listByDateTime(final Integer userId, final Date startDate, final Date endDate, final Integer start, final Integer limit) {
		return (List<DakaLog>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = " from DakaLog where ( date1 between :startDate and :endDate or date3 between :startDate and :endDate ) ";
				if(userId != 0) hql = hql + " and users.id=:userID ";
				hql = hql + " order by ifnull(date1,date3) asc ";
				Query query = session.createQuery(hql)
							  .setDate("startDate", startDate)
						      .setDate("endDate", endDate);
				if(userId != 0) query.setInteger("userID", userId);
				return query.setFirstResult(start).setMaxResults(limit).list(); 
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<DakaLog> listByDateTimeAndDepartment(final Integer departmentId,final Date beginDate,final Date endDate,final Integer start,final Integer limit){ 
		return (List<DakaLog>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = " from DakaLog where ( date1 between :startDate and :endDate or date3 between :startDate and :endDate ) ";
				if(departmentId.intValue() != 0) hql = hql + " and users.department.id=:departmentId ";
				hql = hql + " order by ifnull(date1,date3) asc ";
				Query query = session.createQuery(hql)
							  .setDate("startDate", beginDate)
						      .setDate("endDate", endDate);
				if(departmentId.intValue() != 0) query.setInteger("departmentId", departmentId);
				return query.setFirstResult(start).setMaxResults(limit).list(); 
			}
		});
	}
	
	public Integer getCountByDateTimeAndDepartment(Integer departmentId,Date beginDate,Date endDate){ 
		if(departmentId == 0) {
			String hql = "  select count(*) from DakaLog where ( date1 between :startDate and :endDate or date3 between :startDate and :endDate ) ";
			return ((Long) getHibernateTemplate().findByNamedParam(hql, new String[]{"startDate","endDate"}, new Object[]{beginDate,endDate}).get(0)).intValue();
		} else {
			String hql = " select count(*) from DakaLog where users.department.id=:departmentId and ( date1 between :startDate and :endDate or date3 between :startDate and :endDate ) ";
			return ((Long) getHibernateTemplate().findByNamedParam(hql, new String[]{"departmentId","startDate","endDate"}, new Object[]{departmentId,beginDate,endDate}).get(0)).intValue();
		}
	}
	
	public void saveDakaLog(DakaLog dakaLog) throws Exception {
		getHibernateTemplate().save(dakaLog);
	}
	
	public void updateDakaLog(DakaLog dakaLog) throws Exception {
		getHibernateTemplate().update(dakaLog);
	}
	
/*	
	public Integer getWorkTime(final Integer userid, final Date startDate, final Date endDate) {
		if(startDate==null || endDate==null) return null;
		final String sql = " select (" + QUERY1 + " where users_id=:userid and tip1_id not in (8,9,10,11) and tip2_id not in (8,9,10,11) and ( date1 between :startDate and :endDate or date3 between :startDate and :endDate ) " + ") "
				+ " + (" + QUERY2 + " where users_id=:userid and  tip3_id not in (8,9,10,11) and tip4_id not in (8,9,10,11) and ( date1 between :startDate and :endDate or date3 between :startDate and :endDate )  " + ") as total from dual ";
		System.out.println(sql); 
		Double seconds = (Double)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).setInteger("userid", userid).setDate("startDate", startDate).setDate("endDate", endDate).uniqueResult();
			}
		});
		if(seconds == null || seconds.intValue() == 0) return 0;
		return new Double(seconds / 60).intValue();
	}
	
	public Integer getLateCount(Integer userid, Date startDate, Date endDate) {
		String hql = "select count(*) from DakaLog where users.id=:userid and (tip1.id in (2,3,4) or tip2.id in (2,3,4) or tip3.id in (2,3,4) or tip4.id in (2,3,4) ) and ( date1 between :startDate and :endDate or date3 between :startDate and :endDate ) ";
		return ((Long)getHibernateTemplate().findByNamedParam(hql,new String[]{"userid","startDate","endDate"},new Object[]{userid,startDate,endDate}).get(0)).intValue(); 
	}
	
	public Integer getEarlyCount(Integer userid, Date startDate, Date endDate) {
		String hql = "select count(*) from DakaLog where users.id=:userid and (tip1.id=5 or tip2.id=5 or tip3.id=5 or tip4.id=5 ) and ( date1 between :startDate and :endDate or date3 between :startDate and :endDate ) ";
		return ((Long)getHibernateTemplate().findByNamedParam(hql,new String[]{"userid","startDate","endDate"},new Object[]{userid,startDate,endDate}).get(0)).intValue(); 
	}
	
	public Integer getOverTime(Integer userid, Date startDate, Date endDate) {
		if(startDate==null || endDate==null) return null;
		String hql = " select sum(time_to_sec(date4 - date1)) from OverTime where user.id=:userid and ( date1 between :startDate and :endDate or date3 between :startDate and :endDate ) ";
		Double seconds = (Double)getHibernateTemplate().findByNamedParam(hql, new String[]{"userid","startDate","endDate"}, new Object[]{userid,startDate,endDate}).get(0); 
		if(seconds == null || seconds.intValue() == 0) return 0;
		return new Double(seconds / 60).intValue();
	}
	*/
}
