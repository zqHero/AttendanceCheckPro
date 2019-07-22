package com.huigao.service.impl;

import java.util.List;

import com.huigao.dao.RoleDao;
import com.huigao.pojo.Role;
import com.huigao.service.RoleService;

public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void deleteRole(Integer roleId) {
		roleDao.delete(roleId);
	}

	public Role getRoleById(Integer roleId) {
		return roleDao.getById(roleId);
	}

	public List<Role> listRole() {
		return roleDao.list();
	}

	public void saveRole(Role role) {
		roleDao.save(role);
	}
	
	public void update(Role role) {
		roleDao.update(role);
	}
	
	public void merge(Role role) {
		roleDao.merge(role);
	}

}
