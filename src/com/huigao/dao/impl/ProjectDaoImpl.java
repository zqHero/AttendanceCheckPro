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
				String tabName = "Project";
				return session.createQuery(" delete " + tabName + " p where p.id=:id ")
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
		String tabName = "Project";
		String hql = " select count(*) from " + tabName;
		return ((Long)getHibernateTemplate().find(hql).get(0)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Project> listProject(final int start, final int limit) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String tabName = "Project";
				String hql = "from " + tabName;
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Project getByName(String projectName) {
		// TODO Auto-generated method stub
		String tabName = "Project";
		List<Project> project = getHibernateTemplate().findByNamedParam("from " + tabName + " p where p.projectName=:projectName", "projectName", projectName);
		if(project == null || project.isEmpty()) return null;
		return project.get(0);
	}

}
