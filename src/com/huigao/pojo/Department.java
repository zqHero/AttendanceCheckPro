package com.huigao.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 部门实体类
 * @author cgx
 * @version 1.0
 */
@Entity
@Table(name="t_department")
public class Department implements java.io.Serializable {

	private static final long serialVersionUID = 4963421146150125902L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id; //编号
	
	@Column
	private String departmentName; //部门名称
	
	@Column
	private String departmentDesc; //部门描述
	
	
	@OneToMany(mappedBy="department",cascade=CascadeType.ALL)
	private Set<Users> users = new HashSet<Users>(); 
	
	public Set<Users> getUsers() {
		return users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}
	
}
