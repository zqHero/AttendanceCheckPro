package com.huigao.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.SystemTipsDao;
import com.huigao.pojo.SystemTips;

public class SystemTipsDaoImpl extends HibernateDaoSupport implements SystemTipsDao {

	public SystemTips getById(Integer id) {
		return (SystemTips) getHibernateTemplate().get(SystemTips.class, id);
	} 
	
	
	public void update(SystemTips tips) {
		getHibernateTemplate().update(tips);
	}


	@SuppressWarnings("unchecked")
	public List<SystemTips> list() {
		return getHibernateTemplate().find(" from SystemTips "); 
	}

}
