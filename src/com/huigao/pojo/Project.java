package com.huigao.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_project")
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="projectname")
	private String projectname;
	
	@Column(name="projectdescription")
	private String projectdescription;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getProjectdescription() {
		return projectdescription;
	}

	public void setProjectdescription(String projectdescription) {
		this.projectdescription = projectdescription;
	}
	
	public String toJsonString() {
		StringBuffer buf = new StringBuffer("");
		buf.append("id:").append(id).append(",") 
		   .append("projectname:'").append(projectname).append("',")
		   .append("projectdescription:'").append(projectdescription).append("'");
		return "{" + buf.toString() + "}";
	}
	
}
