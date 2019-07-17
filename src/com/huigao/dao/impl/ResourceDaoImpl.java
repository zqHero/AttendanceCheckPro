package com.huigao.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.ResourceDao;
import com.huigao.pojo.Resource;

public class ResourceDaoImpl extends HibernateDaoSupport implements ResourceDao {

	public Resource getById(Integer resourceId) {
		return (Resource) getHibernateTemplate().get(Resource.class, resourceId); 
	}

	@SuppressWarnings("unchecked")
	public List<Resource> list() {
		return getHibernateTemplate().find(" from Resource "); 
	}
	
	public void update(Resource resource) {
		getHibernateTemplate().update(resource);
	}
	
	public void merge(Resource resource) {
		getHibernateTemplate().merge(resource);
	}

}
