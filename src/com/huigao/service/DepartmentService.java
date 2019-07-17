package com.huigao.service;

import java.util.List;

import com.huigao.pojo.Department;

/**
 * 部门管理
 * @author cgx
 * @version 1.0
 */
public interface DepartmentService {
	
	/**
	 * 添加一个新部门
	 * @param department 部门
	 */
	public void save(Department department);
	
	/**
	 * 删除一个部门
	 * @param department 部门
	 */
	public void delete(Department department);
	
	/**
	 * 删除一个部门
	 * @param departmentId 部门编号
	 */
	public void delete(Integer departmentId);
	
	/**
	 * 部门列表
	 * @return 部门列表集合
	 */
	public List<Department> list();
	
	/**
	 * 获取某个部门
	 * @param departmentId 部门编号
	 * @return 部门
	 */
	public Department getById(Integer departmentId);
	
	/**
	 * 跟新部门信息
	 * @param department 部门
	 */
	public void update(Department department);
	
}
