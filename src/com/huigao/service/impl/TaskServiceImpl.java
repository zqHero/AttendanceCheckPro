package com.huigao.service.impl;

import java.util.List;

import com.huigao.dao.TaskDao;
import com.huigao.pojo.Project;
import com.huigao.pojo.Task;
import com.huigao.pojo.TaskLog;
import com.huigao.pojo.Users;
import com.huigao.service.TaskService;

public class TaskServiceImpl implements TaskService{
	private TaskDao taskdao;

	public void setTaskdao(TaskDao taskdao) {
		this.taskdao = taskdao;
	}

	public Integer getTaskCount() {
		// TODO Auto-generated method stub
		return taskdao.getTaskCount();
	}
	
	public Project getProjectById(int id) {
		// TODO Auto-generated method stub
		return taskdao.getProjectById(id);
	}

	public void saveTask(Task task) {
		// TODO Auto-generated method stub
		taskdao.saveTask(task);
	}

	public void sendAudit(int id) {
		// TODO Auto-generated method stub
		taskdao.sendAudit(id);	
	}

	public void audit(String taskstate, int id) {
		// TODO Auto-generated method stub
		taskdao.audit(taskstate, id);
		
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		taskdao.delete(id);
	}

	public List<Task> listTask(String userName, int start, int limit, String sort, String dir, String state) {
		// TODO Auto-generated method stub
		return taskdao.listTask(userName, start, limit, sort, dir, state);
	}

	public List<Task> listAuditTask(int start, int limit, String sort, String dir) {
		// TODO Auto-generated method stub
		return taskdao.listAuditTask(start, limit, sort, dir);
	}

	public List<Task> listScheduleTask(int start, int limit, String sort, String dir) {
		// TODO Auto-generated method stub
		return taskdao.listScheduleTask(start, limit, sort, dir);
	}

	public void schedule(Users users, int id) {
		// TODO Auto-generated method stub
		taskdao.schedule(users, id);
	}

	public Task getById(int id) {
		// TODO Auto-generated method stub
		return taskdao.getById(id);
	}

	public void update(Task task) {
		// TODO Auto-generated method stub
		taskdao.update(task);
	}

	public List<Task> listMyTask(int start, int limit, String sort, String dir, Users users) {
		// TODO Auto-generated method stub
		return taskdao.listMyTask(start, limit, sort, dir, users);
	}

	public Integer getTaskCount(String state, Users users) {
		// TODO Auto-generated method stub
		return taskdao.getTaskCount(state, users);
	}

	public Integer getLogCount(int taskId) {
		// TODO Auto-generated method stub
		return taskdao.getLogCount(taskId);
	}

	public List<TaskLog> listLog(int start, int limit, int taskId) {
		// TODO Auto-generated method stub
		return taskdao.listLog(start, limit, taskId);
	}

	public void saveLog(TaskLog tasklog) {
		// TODO Auto-generated method stub
		taskdao.saveLog(tasklog);
	}
	
	public List<Task> listAuditTask(String userName, int start,int limit,String sort,String dir){
		return taskdao.listAuditTask(userName, start, limit, sort, dir);
	}
	
	public Integer getAuditCount(String userName){
		return taskdao.getAuditCount(userName);
	}

	public Integer getALLCount(String userName) {
		// TODO Auto-generated method stub
		return taskdao.getALLCount(userName);
	}

	public Integer getMyCount(Users users) {
		// TODO Auto-generated method stub
		return taskdao.getMyCount(users);
	}

	public Integer getScheduleCount(String userName) {
		// TODO Auto-generated method stub
		return taskdao.getScheduleCount(userName);
	}

	public List<Task> listALL(String userName, int start, int limit,
			String sort, String dir) {
		// TODO Auto-generated method stub
		return taskdao.listALL(userName, start, limit, sort, dir);
	}

	public List<Task> listScheduleTask(String userName, int start, int limit,
			String sort, String dir) {
		// TODO Auto-generated method stub
		return taskdao.listScheduleTask(userName, start, limit, sort, dir);
	}

}
