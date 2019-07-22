package com.huigao.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.Project;
import com.huigao.service.ProjectService;

public class ProjectAction extends MappingDispatchActionSupport{
	private ProjectService projectService;

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer start = 0;
		Integer limit = 12;
		
		String s_start = request.getParameter("start"); //起始记录
		if(s_start != null) start = new Integer(s_start);
		String s_limit = request.getParameter("limit"); //每页条数限制
		if(s_limit != null) limit = new Integer(s_limit);
		
		List<Project> list = projectService.listProject(start, limit);
		Integer totalCount = projectService.getProjectCount();
		
		StringBuffer json = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			if(i != 0) json.append(",");
			json.append(list.get(i).toJsonString());
		}
		response.setCharacterEncoding("UTF-8");
		String str = "{totalCount:" + totalCount + ",root:[" + json + "] }";
		System.out.println(str);
		response.getWriter().print(str);
		return null;
	}
	
	//添加新项目
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		try {
			
			Project project = new Project();
			project.setProjectname(request.getParameter("projectname"));
			project.setProjectdescription(request.getParameter("projectdescription"));
			
			projectService.saveProject(project);
			response.getWriter().print("{success:true,msg:'保存成功!'}");
			
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{success:true,msg:'保存失败!'}");
		}
		return null;
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		Integer id = new Integer(request.getParameter("id"));
		String projectname = request.getParameter("projectname");
		String projectdescription = request.getParameter("projectdescription");
		try {
			Project project = projectService.getById(id);
			project.setProjectdescription(projectdescription);
			project.setProjectname(projectname);
			
			projectService.update(project); 
			response.getWriter().print("{success:true,msg:'修改成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{success:true,msg:'修改失败!'}");
		}
		return null;
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String projectIds = request.getParameter("dids");
		String[] depts = projectIds.split(",");
		for (int i = 0; i < depts.length; i++) {
			projectService.delete(new Integer(depts[i]));
		}
		response.getWriter().print("删除成功");
		return null;
	}
	

}
