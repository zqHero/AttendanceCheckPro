package com.huigao.service.impl;

import java.util.List;

import com.huigao.dao.ProjectDao;
import com.huigao.pojo.Project;
import com.huigao.service.ProjectService;

public class ProjectServiceImpl implements ProjectService {
	ProjectDao projectdao;

	public void setProjectdao(ProjectDao projectdao) {
		this.projectdao = projectdao;
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		projectdao.delete(id);
	}

	public Project getById(int id) {
		// TODO Auto-generated method stub
		return projectdao.getById(id);
	}

	public void saveProject(Project project) {
		// TODO Auto-generated method stub
		projectdao.saveProject(project);
	}

	public void update(Project project) {
		// TODO Auto-generated method stub
		projectdao.update(project);
	}

	public Integer getProjectCount() {
		// TODO Auto-generated method stub
		return projectdao.getProjectCount();
	}

	public List<Project> listProject(int start, int limit) {
		// TODO Auto-generated method stub
		return projectdao.listProject(start, limit);
	}

	public Project getByName(String projectName) {
		// TODO Auto-generated method stub
		return projectdao.getByName(projectName);
	}

}
