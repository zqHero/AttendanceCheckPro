package com.huigao.service.impl;

import java.util.List;

import com.huigao.dao.ResourceDao;
import com.huigao.pojo.Resource;
import com.huigao.service.ResourceService;

public class ResourceServiceImpl implements ResourceService {

	private ResourceDao resourceDao;
	

	public Resource getById(Integer resourceId) {
		return resourceDao.getById(resourceId);
	}

	public List<Resource> listResources() {
		return resourceDao.list();
	}

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
	
	public void update(Resource resource){
		resourceDao.update(resource);
	}
	
	public void merge(Resource resource) {
		resourceDao.merge(resource);
	}

}
