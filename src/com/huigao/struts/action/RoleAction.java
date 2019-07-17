package com.huigao.struts.action;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.Role;
import com.huigao.pojo.Users;
import com.huigao.security.interceptor.SecurityChangeOperation;
import com.huigao.service.RoleService;
import com.huigao.service.UserService;

/**
 * 角色管理
 * @author cgx
 * @version 1.0
 */
public class RoleAction extends MappingDispatchActionSupport {

	private RoleService roleService;
	private UserService userService;

	/**
	 * 获取角色列表
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
	 * 获取角色对应的用户列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		Integer roleId = new Integer(request.getParameter("roleId")); 
		Role role = roleService.getRoleById(roleId);
		Set<Users> users = role.getUsers();
		Iterator<Users> it = users.iterator();
		StringBuffer json = new StringBuffer("");
		for (int i = 0; it.hasNext(); i++) {
			Users u = it.next();
			if(i != 0) json.append(",");
			json.append("{id:" + u.getId() + ",userName:'" + u.getUserName() + "'}");
		}
		String str = "{totalCount:" + users.size() + ",root:[" + json + "] }";
		System.out.println(str);
		response.getWriter().print(str);
		return null;
	}
	
	/**
	 * 删除某个角色
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
		Integer roleId = new Integer(request.getParameter("roleId"));
		roleService.deleteRole(roleId);
		response.getWriter().print("删除成功");
		SecurityChangeOperation.operate(request); //通知安全管理器
		return null;
	}
	
	/**
	 * 添加保存新的角色
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		String roleDesc = request.getParameter("roleDesc");
		Role role = new Role();
		String roleName = "ROLE_" + System.currentTimeMillis() + (int)(Math.random()*10);
		role.setName(roleName);
		role.setDescription(roleDesc);
		roleService.saveRole(role);
		response.getWriter().print("保存成功");
		SecurityChangeOperation.operate(request);//通知安全管理器
		return null;
	}
	
	/**
	 * 修改用户角色
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
		String roleId = request.getParameter("roleId");
		String method = request.getParameter("method");
		Role role = roleService.getRoleById(new Integer(roleId));
		Users user = null;
		if("add".equals(method)) {
			String userName = request.getParameter("userName");
			user = userService.getByUsername(userName);
			user.getRoles().add(role);
			userService.mergeUsers(user);
		}
		else if("delete".equals(method)){
			String userId = request.getParameter("userId");
			user = userService.getById(new Integer(userId));
			user.getRoles().remove(role);
			userService.mergeUsers(user);
		}
		response.getWriter().print("操作成功");
		SecurityChangeOperation.operate(request);//通知安全管理器
		return null;
	}
	
	//---- inject ---
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
