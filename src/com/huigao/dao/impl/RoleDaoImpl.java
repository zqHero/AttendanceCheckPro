package com.huigao.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.RoleDao;
import com.huigao.pojo.Role;

public class RoleDaoImpl extends HibernateDaoSupport implements RoleDao {

	public void delete(final Integer roleId) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String tabName = "Role";
				String hql = " delete " + tabName + " r where r.id=:roleId ";
				return session.createQuery(hql).setInteger("roleId", roleId).executeUpdate();
			}
		});
	}

	public Role getById(Integer roleId) {
		return (Role) getHibernateTemplate().get(Role.class, roleId);
	}

	@SuppressWarnings("unchecked")
	public List<Role> list() {
		String tabName = "Role";
		return getHibernateTemplate().find(" from " + tabName);
	}

	public void save(Role role) {
		getHibernateTemplate().save(role);
	}
	
	public void update(Role role) {
		getHibernateTemplate().update(role);
	}
	
	public void merge(Role role) {
		getHibernateTemplate().merge(role);
	}
}
