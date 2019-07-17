package com.huigao.service;

import java.util.List;

import com.huigao.pojo.Project;

public interface ProjectService {
	public List<Project> listProject(int start,int limit);
	public Integer getProjectCount();
	public void saveProject(Project project);
	public Project getById(int id);
	public void update(Project project); 
	public void delete(int id);
	public Project getByName(String projectName);
}
