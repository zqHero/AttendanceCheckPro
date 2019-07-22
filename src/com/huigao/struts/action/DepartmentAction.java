package com.huigao.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.Department;
import com.huigao.service.DepartmentService;

/**
 * 部门管理
 * @author cgx
 * @version 1.0
 */
public class DepartmentAction extends MappingDispatchActionSupport {

	private DepartmentService departmentService;

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<Department> list = departmentService.list();

		StringBuffer buf = new StringBuffer("");

		for (int i = 0; i < list.size(); i++) {
			Department department = list.get(i);
			if(i != 0) buf.append(",");
			buf.append("[")
			   .append(department.getId()).append(",'")
			   .append(department.getDepartmentName()).append("','")
			   .append(department.getDepartmentDesc()).append("']");
		}
		String str = "[" + buf.toString() + "]";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(str);
		return null;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		String departmentName = request.getParameter("departmentName");
		String departmentDesc = request.getParameter("departmentDesc");
		System.out.println(departmentName);
		System.out.println(departmentDesc); 
		try {
			Department department = new Department();
			department.setDepartmentName(departmentName);
			department.setDepartmentDesc(departmentDesc);
			
			departmentService.save(department);
			response.getWriter().print("{success:true,msg:'保存成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{success:true,msg:'保存失败!'}");
		}
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String departmentIds = request.getParameter("dids");
		String[] depts = departmentIds.split(",");
		for (int i = 0; i < depts.length; i++) {
			departmentService.delete(new Integer(depts[i])); 
		}
		response.getWriter().print("删除成功");
		return null;
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		Integer departmentId = new Integer(request.getParameter("departmentId"));
		String departmentName = request.getParameter("departmentName");
		String departmentDesc = request.getParameter("departmentDesc");
		System.out.println(departmentName);
		System.out.println(departmentDesc); 
		try {
			Department department = departmentService.getById(departmentId); 
			department.setDepartmentName(departmentName);
			department.setDepartmentDesc(departmentDesc);
			
			departmentService.update(department); 
			response.getWriter().print("{success:true,msg:'修改成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{success:true,msg:'修改失败!'}");
		}
		return null;
	}

	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//String s_departmentId = request.getParameter("departmentId");
		
		return null;
	}

}
