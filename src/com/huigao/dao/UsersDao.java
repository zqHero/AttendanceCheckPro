package com.huigao.dao;

import java.util.List;

import com.huigao.pojo.Users;

/**
 * 用户管理部分数据库操作
 * @author cgx
 * @version 1.0
 */
public interface UsersDao {
	
	/**
	 * 添加新的用户<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;更新密码的时候，对象中的密码为明文，更新的时候自动转换为经过MD5加密后的密码
	 * @param users 用户
	 */
	public void save(Users users);
	
	/**
	 * 用户登录(未用,未实现)
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户
	 */
	public Users login(String username,String password);
	
	/**
	 * 检查某个用户名是否已经存在
	 * @param username 用户名
	 * @return 如果存在返回true，否则返回false
	 */
	public boolean checkUsername(String username);
	
	/**
	 * 所有用户集合
	 * @return 用户集合
	 */
	public List<Users> list();
	
	/**
	 * 通过 部门编号 和用 户名前缀 匹配查询用户，如果部门编号为0表示查询所有部门
	 * @param departmentId 部门编号
	 * @param userName 用户名前缀
	 * @return 用户集合
	 */
	public List<Users> listByDepartmentAndRealName(int departmentId,String userName);
	
	/**
	 * 按部门分页查询用户集合，如果部门编号为0表示查询所有部门
	 * @param departmentId 部门编号 
	 * @param start 开始记录数
	 * @param limit 每页显示记录数
	 * @return 用户集合
	 */
	public List<Users> listByDepartment(int departmentId, int start, int limit);
	
	/**
	 * 按部门查询员工数量，如果部门编号为0表示查询所有部门
	 * @param departmentID 部门编号
	 * @return 部门员工数量
	 */
	public Integer getCountByDepartment(Integer departmentID);
	
	/**
	 * 查询用户
	 * @param userId 用户编号
	 * @return 用户
	 */
	public Users getById(Integer userId);
	
	/**
	 * 通过用户名查询用户
	 * @param username 用户名
	 * @return 用户
	 */ 
	public Users getByUsername(String username);
	
	/**
	 * 删除用户
	 * @param users 用户
	 */
	public void delete(Users users);
	
	/**
	 * 删除用户
	 * @param userId 用户编号
	 */
	public void delete(Integer userId);
	
	/**
	 * 更新用户信息。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;更新密码的时候，对象中的密码为明文，更新的时候自动转换为经过MD5加密后的密码
	 * @param user 用户
	 */
	public void update(Users user);
	
	/**
	 * 将用户实例与数据库中的某条记录合并
	 * @param user 用户
	 */
	public void merge(Users user);
}
