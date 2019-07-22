package com.huigao.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.StatisticDao;
import com.huigao.pojo.Statistic;
import com.huigao.pojo.Users;

public class StatisticDaoImpl extends HibernateDaoSupport implements StatisticDao {

	public void delete(Statistic som) {
		getHibernateTemplate().delete(som);
	}
	
	public void delete(final Integer year,final Integer month) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = " delete Statistic where year=:year and month=:month ";
				return session.createQuery(hql)
							  .setInteger("year", year)
							  .setInteger("month", month)
							  .executeUpdate();
			}
		});
	}
	
	public void statistic(final Integer year,final Integer month) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = " {Call statistic_proc(?,? )} ";
				return session.createSQLQuery(sql)
							  .setInteger(0, year)
							  .setInteger(1, month)
							  .executeUpdate();
			}
		});
	}
	
	public void deleteByUser(final Integer year,final Integer month,final Integer userId) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = " delete Statistic where users.id=:userId and year=:year and month=:month ";
				return session.createQuery(hql)
							  .setInteger("userId", userId) 
							  .setInteger("year", year)
							  .setInteger("month", month)
							  .executeUpdate();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Statistic> listByDepartment(final Integer departmentId,
			final Integer year, final Integer month, final int start, final int limit) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = " from Statistic where year=:year and month=:month  ";
				if(departmentId != 0)  hql = hql + " and users.department.id=:departmentId ";
				Query query = session.createQuery(hql)
							  .setInteger("year", year)
						      .setInteger("month", month);
				if(departmentId != 0) query.setInteger("departmentId", departmentId);
				return query.setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Statistic> listByDepartment(final Integer departmentId,
			final Integer year,final int start, final int limit) {
		return (List<Statistic>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				String hql = " select users.id, sum(workTime),sum(overTime),sum(businessTime),sum(sickTime), " +
								" sum(vacationTime),sum(countOfLate), sum(countOfEO), year  from Statistic " +
								" where year=:year  ";
				if(departmentId != 0) hql = hql + " and users.department.id=:departmentId  ";
				hql = hql + " group by year,users.id "; 
				
				Query query = session.createQuery(hql)
							  .setInteger("year", year);
				if(departmentId != 0) query.setInteger("departmentId", departmentId);
				List<Object[]> t_list = query.setFirstResult(start).setMaxResults(limit).list(); 
				List<Statistic> list = new ArrayList<Statistic>();
				if(t_list == null) return list;
				for (int i = 0; i < t_list.size(); i++) {
					Object[] objs = t_list.get(i);
					Statistic som = new Statistic();
					som.setUsers((Users)session.get(Users.class, (Integer)objs[0]));
					som.setWorkTime(((Long)objs[1]).intValue());
					som.setOverTime(((Long)objs[2]).intValue());
					som.setBusinessTime(((Long)objs[3]).intValue());
					som.setSickTime(((Long)objs[4]).intValue());
					som.setVacationTime(((Long)objs[5]).intValue());
					som.setCountOfLate(((Long)objs[6]).intValue());
					som.setCountOfEO(((Long)objs[7]).intValue());
					som.setYear((Integer)objs[8]); 
					list.add(som);
				}
				return list;
			}
		});
	}

	public void save(Statistic som) {
		getHibernateTemplate().save(som);
	}

	public Integer getCountByDepartment(final Integer departmentId, final Integer year, final Integer month) {
		return ((Long) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select count(*) from Statistic where year=:year and month=:month  ";
				if(departmentId != 0)  hql = hql + " and users.department.id=:departmentId ";
				Query query = session.createQuery(hql)
							  .setInteger("year", year)
						      .setInteger("month", month);
				if(departmentId != 0) query.setInteger("departmentId", departmentId);
				return query.uniqueResult(); 
			}
		})).intValue();
	}

	public Integer getCountByDepartment(final Integer departmentId, final Integer year) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = " select users.id from Statistic where year=:year  ";
				if(departmentId != 0) hql = hql + " and users.department.id=:departmentId  ";
				hql = hql + " group by year,users.id "; 
				Query query = session.createQuery(hql).setInteger("year", year);
				if(departmentId != 0) query.setInteger("departmentId", departmentId);
				return query.list().size(); 
			}
		});
	}
	
}
