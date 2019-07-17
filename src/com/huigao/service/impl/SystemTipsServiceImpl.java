package com.huigao.service.impl;

import java.util.List;

import com.huigao.dao.SystemTipsDao;
import com.huigao.pojo.SystemTips;
import com.huigao.service.SystemTipsService;

public class SystemTipsServiceImpl implements SystemTipsService {
	
	private SystemTipsDao systemTipsDao;

	public void setSystemTipsDao(SystemTipsDao systemTipsDao) {
		this.systemTipsDao = systemTipsDao;
	}

	public SystemTips getById(Integer id) {
		return systemTipsDao.getById(id);
	}

	public List<SystemTips> list() {
		return systemTipsDao.list();
	}

	public void update(SystemTips tips) {
		systemTipsDao.update(tips);
	}

}
