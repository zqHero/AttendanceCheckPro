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
				String hql = " delete Role where id=:roleId ";
				return session.createQuery(hql).setInteger("roleId", roleId).executeUpdate();
			}
		});
	}

	public Role getById(Integer roleId) {
		return (Role) getHibernateTemplate().get(Role.class, roleId);
	}

	@SuppressWarnings("unchecked")
	public List<Role> list() {
		return getHibernateTemplate().find(" from Role ");
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
