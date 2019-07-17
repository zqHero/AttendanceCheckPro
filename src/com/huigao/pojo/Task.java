package com.huigao.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="t_task")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="taskname")
	private String taskname;//任务名称
	
	@ManyToOne
	private Users users;//负责人
	
	@ManyToOne
	private Project project;//所属项目
	
	@Column
	private String tasktype;//任务类型（如：开发任务、测试任务、修改任务等）
	
	@Column(name="priority")
	private String priority;//优先级
	
	@Column(name="taskstate")
	private String taskstate;//任务状态：待完成、待审核、已完成、审核不通过
    
	@Column(name="taskdescription")
	private String taskdescription;//任务描述

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTaskstate() {
		return taskstate;
	}

	public void setTaskstate(String taskstate) {
		this.taskstate = taskstate;
	}

	public String getTaskdescription() {
		return taskdescription;
	}

	public void setTaskdescription(String taskdescription) {
		this.taskdescription = taskdescription;
	}

	public String toJsonString() {
		StringBuffer buf = new StringBuffer("");
		buf.append("id:").append(id).append(",") 
		   .append("taskname:'").append(taskname).append("',")
		   .append("usersId:'").append(users.getId()).append("',")
		   .append("usersName:'").append(users.getRealName()).append("',")
		   .append("projectId:'").append(project.getId()).append("',")
		   .append("projectName:'").append(project.getProjectname()).append("',")
		   .append("tasktype:'").append(tasktype).append("',")
		   .append("priority:'").append(priority).append("',")
		   .append("taskstate:'").append(taskstate).append("',")
		   .append("taskdescription:'").append(taskdescription).append("'");
		return "{" + buf.toString() + "}";
	}
	
}
