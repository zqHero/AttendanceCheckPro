package com.huigao.struts.action;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.Resource;
import com.huigao.pojo.Role;
import com.huigao.security.interceptor.SecurityChangeOperation;
import com.huigao.service.ResourceService;
import com.huigao.service.RoleService;

/**
 * 资源权限管理
 * @author cgx
 * @version 1.0
 */
public class ResourceAction extends MappingDispatchActionSupport {

	private ResourceService resourceService;
	private RoleService roleService;
	

	/**
	 * 获取资源列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		List<Resource> list = resourceService.listResources();
		
		StringBuffer json = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			if(i != 0) json.append(",");
			json.append(list.get(i).toJsonString());
		}
		String str = "{totalCount:" + list.size() + ",root:[" + json + "] }";
		System.out.println(str);
		response.getWriter().print(str);
		return null;
	}
	
	/**
	 * 获取角色列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		List<Role> list = roleService.listRole();
		
		StringBuffer json = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			if(i != 0) json.append(",");
			json.append(list.get(i).toJsonString());
		}
		String str = "{totalCount:" + list.size() + ",root:[" + json + "] }";
		System.out.println(str);
		response.getWriter().print(str);
		return null;
	}
	
	/**
	 * 获取访问某个资源需要具备的权限列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		Integer resourceId = new Integer(request.getParameter("resourceId"));
		Resource r = resourceService.getById(resourceId);
		Set<Role> set = r.getRoles();
		if(set == null) return null;
		Iterator<Role> it = set.iterator();
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; it.hasNext(); i++) {
			if(i != 0) buf.append(",");
			buf.append(it.next().toJsonString());
		}
		response.getWriter().print("{totalCount:" + set.size() + ",root:[" + buf.toString() + "]}");
		return null;
	}
	
	/**
	 * 删除(未用，未实现)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		//--TODO
		return null;
	}
	
	
	/**
	 * 修改保存资源与角色的映射关系
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String resourceId = request.getParameter("resourceId");
		String roles = request.getParameter("roles"); 
		
		try {
			Resource resource = resourceService.getById(new Integer(resourceId));
			String[] rs = roles.split(",");
			Set<Role> set = new HashSet<Role>();
			for (int i = 0; i < rs.length; i++) {
				if(rs[i] != null &&!"".equals(rs[i])) 
					set.add(roleService.getRoleById(new Integer(rs[i])));
			}
			Set<Role> sets = resource.getRoles();
			sets.clear();
			sets.addAll(set);
			resource.setRoles(sets);
			resourceService.merge(resource);
			response.getWriter().print("修改成功");
			SecurityChangeOperation.operate(request);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("修改失败");
		}
		return null;
	}

	//---- inject
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
}
