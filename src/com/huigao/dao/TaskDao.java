package com.huigao.dao;

import java.util.List;

import com.huigao.pojo.Project;
import com.huigao.pojo.Task;
import com.huigao.pojo.TaskLog;
import com.huigao.pojo.Users;

public interface TaskDao {
	public List<Task> listTask(String userName,int start,int limit,String sort,String dir,String state);
	public Integer getTaskCount();
	public Integer getTaskCount(String state,Users users);
	public Project getProjectById(int id);
	public List<Task> listAuditTask(int start,int limit,String sort,String dir);
	public List<Task> listScheduleTask(int start,int limit,String sort,String dir);
	
	//任务
	public void saveTask(Task task);
	public void sendAudit(int id);
	public void audit(String taskstate,int id);
	public void schedule(Users users,int id);
	public void delete(int id);
	public Task getById(int id);
	public void update(Task task);
	
	//日志
	public List<TaskLog> listLog(int start, int limit, int taskId);
	public Integer getLogCount(int taskId);
	public void saveLog(TaskLog tasklog);
	
	//列出所有待审核的任务
	public List<Task> listAuditTask(String userName, int start,int limit,String sort,String dir);
	public Integer getAuditCount(String userName);
	
	//列出所有待调度的任务
	public List<Task> listScheduleTask(String userName, int start,int limit,String sort,String dir);
	public Integer getScheduleCount(String userName);
	
	//列出自己待完成的任务
	public List<Task> listMyTask(int start,int limit,String sort,String dir, Users users);
	public Integer getMyCount(Users users);
	
	//列出所有的任务
	public List<Task> listALL(String userName, int start,int limit,String sort,String dir);
	public Integer getALLCount(String userName);
}
