package com.huigao.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.TaskDao;
import com.huigao.pojo.Project;
import com.huigao.pojo.Task;
import com.huigao.pojo.TaskLog;
import com.huigao.pojo.Users;

public class TaskDaoImpl extends HibernateDaoSupport implements TaskDao{

	public Integer getTaskCount() {
		String hql = " select count(*) from Task ";
		return ((Long)getHibernateTemplate().find(hql).get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Task> listTask(final String userName, final int start, final int limit, final String sort, final String dir, final String state ) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Task task";
				if(userName != "" && userName!=null) hql = hql + " where task.users.userName = '" + userName+"'";
				if(userName != null && userName != "" && state!=null){
					if(state.equals("audit")){
						hql = hql + " and taskstate = '待审核'";
					}else if(state.equals("schedule")){
						hql = hql + " and taskstate != '已完成'";
					}
				}
				if((userName == null||userName.equals("")) && state != null){
					if(state.equals("audit")){
						hql = hql + " where taskstate = '待审核'";
					}else if(state.equals("schedule")){
						hql = hql + " where taskstate != '已完成'";
					}
				}
				if(sort != "" && sort!=null && dir != "" && dir!=null){
					if(sort.equals("usersName")){
						hql = hql + " order by users.realName " + dir;
					}else if(sort.equals("projectName")){
						hql = hql + " order by project.projectname " + dir;
					}else{
						hql = hql + " order by " + sort + " " + dir;
					}
				}
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	public Project getProjectById(int id) {
		// TODO Auto-generated method stub
		return (Project) getHibernateTemplate().get(Project.class, id); 
	}

	public void saveTask(Task task) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(task);	
	}

	public void sendAudit(int id) {
		// TODO Auto-generated method stub
		Task task = (Task) this.getHibernateTemplate().get(Task.class, id);
		task.setTaskstate("待审核");
		this.getHibernateTemplate().update(task);	
	}

	@SuppressWarnings("unchecked")
	public List<Task> listAuditTask(final int start, final int limit, final String sort, final String dir) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Task where taskstate = '待审核'";
				if(sort != "" && sort!=null && dir != "" && dir!=null){
					if(sort.equals("usersName")){
						hql = hql + " order by users.realName " + dir;
					}else if(sort.equals("projectName")){
						hql = hql + " order by project.projectname " + dir;
					}else{
						hql = hql + " order by " + sort + " " + dir;
					}
				} 
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	public void audit(String taskstate, int id) {
		// TODO Auto-generated method stub
		Task task = (Task) this.getHibernateTemplate().get(Task.class, id);
		if("审核通过".equals(taskstate)) task.setTaskstate("已完成");
		else task.setTaskstate("待完成");
		this.getHibernateTemplate().update(task);
	}

	public void delete(final int id) {
		// TODO Auto-generated method stub
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(" delete Task where id=:id ")
					   .setInteger("id", id).executeUpdate();
			}
			
		});
	}

	@SuppressWarnings("unchecked")
	public List<Task> listScheduleTask(final int start, final int limit, final String sort, final String dir) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Task where taskstate != '已完成'";
				if(sort != "" && sort!=null && dir != "" && dir!=null){
					if(sort.equals("usersName")){
						hql = hql + " order by users.realName " + dir;
					}else if(sort.equals("projectName")){
						hql = hql + " order by project.projectname " + dir;
					}else{
						hql = hql + " order by " + sort + " " + dir;
					}
				}
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	public void schedule(Users users, int id) {
		// TODO Auto-generated method stub
		Task task = (Task) this.getHibernateTemplate().get(Task.class, id);
		task.setUsers(users);
		this.getHibernateTemplate().update(task);
	}

	public Task getById(int id) {
		// TODO Auto-generated method stub
		return (Task)this.getHibernateTemplate().get(Task.class, id);
	}

	public void update(Task task) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(task);
	}

	@SuppressWarnings({"unchecked"})
	public List<Task> listMyTask(final int start, final int limit, final String sort, final String dir, final Users users) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Task where taskstate = '待完成' and users.id = " + users.getId();
				if(sort != "" && sort!=null && dir != "" && dir!=null){
					if(sort.equals("usersName")){
						hql = hql + " order by users.realName " + dir;
					}else if(sort.equals("projectName")){
						hql = hql + " order by project.projectname " + dir;
					}else{
						hql = hql + " order by " + sort + " " + dir;
					}
				} 
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	public Integer getTaskCount(String state, Users users) {
		// TODO Auto-generated method stub
		String hql = " select count(*) from Task ";
		if(state.equals("schedule")){
			hql += " where taskstate != '已完成'";	
		}else if(state.equals("audit")){
		    hql += " where taskstate != '待审核'";
		}else if(state.equals("my")){
			hql += " where taskstate = '待完成' and users.id = " + users.getId();
		}
		return ((Long)getHibernateTemplate().find(hql).get(0)).intValue();
	}

	public Integer getLogCount(int taskId) {
		// TODO Auto-generated method stub
		String hql = " select count(*) from TaskLog where task.id = " + taskId;
		return ((Long)getHibernateTemplate().find(hql).get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<TaskLog> listLog(final int start, final int limit, final int taskId) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from TaskLog where task.id = " + taskId;
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	public void saveLog(TaskLog tasklog) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(tasklog);
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> listAuditTask(final String userName, final int start, final int limit, final String sort, final String dir){
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Task where taskstate = '待审核'";
				if(!userName.equals("all")) hql += " and users.userName = '" + userName + "'";
				if(sort != "" && sort!=null && dir != "" && dir!=null){
					if(sort.equals("usersName")){
						hql = hql + " order by users.realName " + dir;
					}else if(sort.equals("projectName")){
						hql = hql + " order by project.projectname " + dir;
					}else{
						hql = hql + " order by " + sort + " " + dir;
					}
				}
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	public Integer getALLCount(String userName) {
		// TODO Auto-generated method stub
		String hql = " select count(*) from Task ";
		if(!userName.equals("all")) hql += " where users.userName like '" + userName + "%'";
		return ((Long)this.getHibernateTemplate().find(hql).get(0)).intValue();
	}

	public Integer getAuditCount(String userName) {
		// TODO Auto-generated method stub
		String hql = " select count(*) from Task where taskstate = '待审核'";
		if(!userName.equals("all")) hql += " and users.userName = '" + userName + "'";
		return ((Long)this.getHibernateTemplate().find(hql).get(0)).intValue();
	}

	public Integer getMyCount(Users users) {
		// TODO Auto-generated method stub
		String hql = " select count(*) from Task where taskstate = '待完成' and users.id = " + users.getId();
		return ((Long)this.getHibernateTemplate().find(hql).get(0)).intValue();
	}

	public Integer getScheduleCount(String userName) {
		// TODO Auto-generated method stub
		String hql = " select count(*) from Task where taskstate != '已完成' ";
		if(!userName.equals("all")) hql += " and users.userName = '" + userName + "'";
		return ((Long)this.getHibernateTemplate().find(hql).get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Task> listALL(final String userName, final int start, final int limit,
			final String sort, final String dir) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Task ";
				if(!userName.equals("all")) hql += " where users.userName like '" + userName + "%'";
				if(sort != "" && sort!=null && dir != "" && dir!=null){
					if(sort.equals("usersName")){
						hql = hql + " order by users.realName " + dir;
					}else if(sort.equals("projectName")){
						hql = hql + " order by project.projectname " + dir;
					}else{
						hql = hql + " order by " + sort + " " + dir;
					}
				}
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Task> listScheduleTask(final String userName, final int start, final int limit,
			final String sort, final String dir) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Task where taskstate != '已完成'";
				if(!userName.equals("all")) hql += " and users.userName = '" + userName + "'";
				if(sort != "" && sort!=null && dir != "" && dir!=null){
					if(sort.equals("usersName")){
						hql = hql + " order by users.realName " + dir;
					}else if(sort.equals("projectName")){
						hql = hql + " order by project.projectname " + dir;
					}else{
						hql = hql + " order by " + sort + " " + dir;
					}
				}
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

}
