package com.huigao.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.DepartmentDao;
import com.huigao.pojo.Department;

public class DepartmentDaoImpl extends HibernateDaoSupport implements DepartmentDao {

	public void delete(Department department) {
		getHibernateTemplate().delete(department);
	}
	
	public void delete(final Integer departmentId) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String tabName = "Department";
				return session.createQuery(" delete " + tabName + " d where d.id=:departmentId ")
					   .setInteger("departmentId", departmentId).executeUpdate();
			}
			
		});
	}

	public Department getById(Integer departmentId) {
		return (Department) getHibernateTemplate().get(Department.class, departmentId);
	}

	@SuppressWarnings("unchecked")
	public List<Department> list() {
		String tabName = "Department";
		return getHibernateTemplate().find(" from " + tabName);
	}

	public void save(Department department) {
		getHibernateTemplate().save(department);
	}

	public void update(Department department) {
		getHibernateTemplate().update(department);
	}
}
