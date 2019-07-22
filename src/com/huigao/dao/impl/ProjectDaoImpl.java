package com.huigao.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huigao.dao.ProjectDao;
import com.huigao.pojo.Project;

public class ProjectDaoImpl extends HibernateDaoSupport implements ProjectDao {

	public void delete(final int id) {
		// TODO Auto-generated method stub
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(" delete Project where id=:id ")
					   .setInteger("id", id).executeUpdate();
			}
			
		});
	}

	public Project getById(int id) {
		// TODO Auto-generated method stub
		return (Project) getHibernateTemplate().get(Project.class, id);
	}

	public void saveProject(Project project) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(project);
	}

	public void update(Project project) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(project);
	}

	public Integer getProjectCount() {
		// TODO Auto-generated method stub
		String hql = " select count(*) from Project ";
		return ((Long)getHibernateTemplate().find(hql).get(0)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Project> listProject(final int start, final int limit) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Project ";
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Project getByName(String projectName) {
		// TODO Auto-generated method stub
		List<Project> project = getHibernateTemplate().findByNamedParam("from Project where projectName=:projectName", "projectName", projectName); 
		if(project == null || project.isEmpty()) return null;
		return project.get(0);
	}

}
