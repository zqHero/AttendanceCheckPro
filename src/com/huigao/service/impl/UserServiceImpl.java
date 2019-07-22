package com.huigao.service.impl;

import java.util.List;

import com.huigao.dao.UsersDao;
import com.huigao.pojo.Users;
import com.huigao.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UsersDao userDao;

	public void setUserDao(UsersDao userDao) {
		this.userDao = userDao;
	}

	public Users login(String username, String password) {
		return userDao.login(username, password);
	}
	
	public boolean checkUsername(String username){
		return userDao.checkUsername(username);
	}

	public Integer getCountByDepartment(Integer departmentID) {
		return userDao.getCountByDepartment(departmentID);
	}

	public List<Users> listByDepartment(int departmentID, int start, int limit) {
		return userDao.listByDepartment(departmentID, start, limit);
	}
	
	public List<Users> listByDepartmentAndRealName(int departmentID,String realName) {
		return userDao.listByDepartmentAndRealName(departmentID,realName);
	}

	public void save(Users users) {
		userDao.save(users);
	}

	public Users getById(Integer userId) {
		return userDao.getById(userId);
	}
	
	public Users getByUsername(String username){
		return userDao.getByUsername(username);
	}

	public void deleteUsers(Users users) {
		userDao.delete(users);
	}

	public void deleteUsersById(Integer userId) {
		userDao.delete(userId);
	}

	public void updateUsers(Users user) {
		userDao.update(user);
	}
	
	public void mergeUsers(Users user) {
		userDao.merge(user);
	}
 
}
