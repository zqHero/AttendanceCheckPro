package com.huigao.service;

import java.util.List;

import com.huigao.pojo.Users;

/**
 * 员工管理
 * @author cgx
 * @version 1.0
 */
public interface UserService {

	/**
	 * 用户登录(未用)
	 * @param username 用户名
	 * @param password 密码(明文)
	 * @return 登录成功返回用户对象，否则返回null
	 */
	Users login(String username, String password);
	
	/**
	 * 检查用户名是否存在
	 * @param username 用户名
	 * @return 存在返回true，否则返回false
	 */
	public boolean checkUsername(String username);
	
	/**
	 * 添加保存新的用户
	 * @param users 用户
	 */
	public void save(Users users);
	
	/**
	 * 按部门查询用户集合
	 * @param departmentID 部门编号，如果为0表示查询所有部门员工
	 * @param start 开始记录
	 * @param limit 每页显示记录数
	 * @return
	 */
	public List<Users> listByDepartment(int departmentID, int start, int limit);
	
	/**
	 * 通过用户名前缀和部门编号匹配查询用户列表
	 * @param departmentID 部门编号
	 * @param realName 用户名
	 * @return 用户集合
	 */
	public List<Users> listByDepartmentAndRealName(int departmentID,String realName);
	
	/**
	 * 查询某个部门的员工数量
	 * @param departmentID 部门编号
	 * @return 员工数量
	 */
	public Integer getCountByDepartment(Integer departmentID);
	
	/**
	 * 获取用户
	 * @param userId 用户编号
	 * @return 用户
	 */
	public Users getById(Integer userId);
	
	/**
	 * 按用户名查询用户
	 * @param username 用户名
	 * @return 用户
	 */
	public Users getByUsername(String username);
	
	/**
	 * 删除用户
	 * @param users 用户
	 */
	public void deleteUsers(Users users);
	
	/**
	 * 删除用户
	 * @param userId 用户编号
	 */
	public void deleteUsersById(Integer userId);
	
	/**
	 * 更新用户信息(密码用明文，自动转为密文)
	 * @param user 用户
	 */
	public void updateUsers(Users user);
	
	/**
	 * 合并数据库中的某条用户信息
	 * @param user 用户
	 */
	public void mergeUsers(Users user);
	
}
