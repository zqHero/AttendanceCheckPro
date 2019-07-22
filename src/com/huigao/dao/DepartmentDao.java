package com.huigao.dao;

import java.util.List;

import com.huigao.pojo.Department;

/**
 * 部门管理部分数据库操作
 * @author cgx
 * @version 1.0
 */
public interface DepartmentDao {
	
	/**
	 * 添加保存新部门
	 * @param department 部门
	 */
	public void save(Department department);
	
	/**
	 * 删除某个部门
	 * @param department 部门
	 */
	public void delete(Department department);
	
	/**
	 * 删除某个部门
	 * @param departmentId 部门编号
	 */
	public void delete(Integer departmentId);
	
	/**
	 * 查询所有部门
	 * @return 部门列表
	 */
	public List<Department> list();
	
	/**
	 * 查询部门
	 * @param departmentId 部门编号
	 * @return 部门
	 */
	public Department getById(Integer departmentId);
	
	/**
	 * 修改部门信息
	 * @param department 部门
	 */
	public void update(Department department);
	
}
