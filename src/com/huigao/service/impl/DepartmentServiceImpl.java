package com.huigao.service.impl;

import java.util.List;

import com.huigao.dao.DepartmentDao;
import com.huigao.pojo.Department;
import com.huigao.service.DepartmentService;


/**
 * 部门管理
 * @author Administrator
 *
 */
public class DepartmentServiceImpl implements DepartmentService {
	
	private DepartmentDao departmentDao;

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public void delete(Department department) {
		departmentDao.delete(department);
	}
	public void delete(Integer departmentId) {
		departmentDao.delete(departmentId);
	}

	public Department getById(Integer departmentId) {
		return departmentDao.getById(departmentId);
	}

	public List<Department> list() {
		return departmentDao.list();
	}

	public void save(Department department) {
		departmentDao.save(department);
	}

	public void update(Department department) {
		departmentDao.update(department);
	}
	
	
	
	
}
