package com.huigao.service;

import java.util.List;

import com.huigao.pojo.Role;

/**
 * 角色管理
 * @author cgx
 * @version 1.0
 */
public interface RoleService {
	
	/**
	 * 获取一个角色
	 * @param roleId 角色编号
	 * @return 角色
	 */
	public Role getRoleById(Integer roleId);
	
	/**
	 * 删除角色
	 * @param roleId 角色编号
	 */
	public void deleteRole(Integer roleId);
	
	/**
	 * 角色列表
	 * @return 角色列表集合
	 */
	public List<Role> listRole();
	
	/**
	 * 添加保存新的角色
	 * @param role 角色
	 */
	public void saveRole(Role role);
	
	/**
	 * 更新一个角色
	 * @param role 角色
 	 */
	public void update(Role role);
	
	/**
	 * 合并数据库中的某条角色记录
	 * @param role 角色
	 */
	public void merge(Role role);
}
