package com.huigao.dao;

import java.util.List;

import com.huigao.pojo.Role;

/**
 * 系统角色部分数据库操作
 * @author cgx
 * @version 1.0
 */
public interface RoleDao {
	
	/**
	 * 获取角色
	 * @param roleId 角色编号
	 * @return 角色对象
	 */
	public Role getById(Integer roleId);
	
	/**
	 * 角色列表
	 * @return 角色集合
	 */
	public List<Role> list();
	
	/**
	 * 删除某个角色
	 * @param roleId 角色编号
	 */
	public void delete(Integer roleId);
	
	/**
	 * 添加保存新的角色
	 * @param role 角色
	 */
	public void save(Role role);
	
	/**
	 * 跟新某个角色信息
	 * @param role 角色
	 */
	public void update(Role role);
	
	/**
	 * 将某个角色与数据库进行合并
	 * @param role 角色
	 */
	public void merge(Role role);
}
