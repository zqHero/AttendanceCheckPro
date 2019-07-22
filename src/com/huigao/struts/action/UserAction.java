package com.huigao.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.Role;
import com.huigao.pojo.Users;
import com.huigao.security.interceptor.SecurityChangeOperation;
import com.huigao.security.support.SecurityUserHolder;
import com.huigao.service.DepartmentService;
import com.huigao.service.RoleService;
import com.huigao.service.UserService;
import com.huigao.util.DateUtil;

/**
 * 用户管理
 * @author cgx
 * @version 1.0
 */
public class UserAction extends MappingDispatchActionSupport {

	private UserService userService;
	private DepartmentService departmentService;
	private RoleService roleService;
	private Logger logger = Logger.getLogger(this.getClass());

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 检查用户名是否已经存在
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		boolean result = userService.checkUsername(username);
		response.getWriter().print(result);
		return null;
	}
	
	/**
	 * 用户登录(未用)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		/*String username = request.getParameter("username");
		String password = request.getParameter("password");

		Map<String, String> tips = new HashMap<String, String>();
		
		 * Users users = new Users(); users.setId(1);
		 * users.setUsername(username); users.setPassword(password);
		 

		Users users = userService.login(username, password);

		if (users == null) {
			tips.put("username", "无效的用户名或密码");
			request.setAttribute("tips", tips);
			return mapping.getInputForward();
		} else {
			request.getSession().setAttribute("users", users);
			return mapping.findForward("success");
		}*/
		return null;

	}

	/**
	 * 分页获取员工列表
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
		Integer departmentId = 0;
		Integer start = 0;
		Integer limit = 25;
		
		List<Users> list = null;
		Integer totalCount = 0;
		
		String realName = request.getParameter("query");
		
		String s_departmentId = request.getParameter("departmentId"); //部门编号
		if(s_departmentId != null && !"".equals(s_departmentId)) departmentId = new Integer(s_departmentId);
		String s_start = request.getParameter("start"); //起始记录
		if(s_start != null) start = new Integer(s_start);
		String s_limit = request.getParameter("limit"); //每页条数限制
		if(s_limit != null) limit = new Integer(s_limit);
		
		if(realName != null && !"".equals(realName)) {
			list = userService.listByDepartmentAndRealName(departmentId,realName);
			totalCount = list.size();
		}else {
			totalCount = userService.getCountByDepartment(departmentId);
			list = userService.listByDepartment(departmentId, start, limit);
		}
		
		StringBuffer json = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			if(i != 0) json.append(",");
			json.append(list.get(i).toJsonString());
		}
		response.setCharacterEncoding("UTF-8");
		String str = "{totalCount:" + totalCount + ",root:[" + json + "] }";
		logger.debug(str);
		response.getWriter().print(str);
		return null;
	}
	
	/**
	 * 获取员工列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listByDepartment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer departmentId = 0;
		String realName = request.getParameter("query");
		System.out.println(realName); 
		String s_departmentId = request.getParameter("departmentId"); //部门编号
		if(s_departmentId != null && !"".equals(s_departmentId)) departmentId = new Integer(s_departmentId);
		
		List<Users> list = userService.listByDepartmentAndRealName(departmentId,realName);
		
		StringBuffer json = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			if(i != 0) json.append(",");
			json.append(list.get(i).toJsonString());
		}
		response.setCharacterEncoding("UTF-8");
		String str = "{totalCount:" + list.size() + ",root:[" + json + "] }";
		logger.debug(str);
		response.getWriter().print(str);
		return null;
	}
	
	/**
	 * 角色列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		List<Role> list = roleService.listRole();
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			if(i != 0) buf.append(",");
			buf.append(list.get(i).toJsonString());
		}
		String str = "{totalCount:" + list.size() + ",root:[" + buf.toString() + "] }";
		logger.debug(str); 
		response.getWriter().print(str);
		return null;
	}
	
	/**
	 * 查询用户角色
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		List<Role> list = roleService.listRole();
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			if(i != 0) buf.append(",");
			buf.append(list.get(i).toJsonString());
		}
		String str = "{totalCount:" + list.size() + ",root:[" + buf.toString() + "] }";
		logger.debug(str); 
		response.getWriter().print(str);
		return null;
	}
	
	/**
	 * 添加新员工
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
		
		try {
			Users users = new Users();
			users.setUserName(request.getParameter("userName"));
			users.setPassword(request.getParameter("password"));
			users.setRealName(request.getParameter("realName"));
			users.setGender(request.getParameter("gender"));
			users.setBirthday(DateUtil.getDate(request.getParameter("birthday"),"yyyy-MM-dd")); 
			users.setDepartment(departmentService.getById(new Integer(request.getParameter("departmentId"))));
			users.setSchool(request.getParameter("school"));
			users.setSpecialty(request.getParameter("specialty"));
			users.setSuperiors(userService.getByUsername(request.getParameter("superiorsName")));  
			
			userService.save(users);
			SecurityChangeOperation.operate(request); //通知安全管理器
			response.getWriter().print("{success:true,msg:'保存成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{success:true,msg:'保存失败!'}");
		}
		return null;
	}
	
	/**
	 * 批量删除员工
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
		String uids = request.getParameter("uids");
		if(uids != null) {
			String[] uid = uids.split(",");
			for (int i = 0; i < uid.length; i++) {
				try {
					userService.deleteUsersById(new Integer(uid[i]));
				} catch (Exception e) {
					e.printStackTrace();
					response.getWriter().print("删除失败");
					return null;
				}
			}
			response.getWriter().print("删除成功");
			SecurityChangeOperation.operate(request); //通知安全管理器
		}else response.getWriter().print("没有需要改动的记录"); 
		return null;
	}
	
	/**
	 * 保存修改角色中的用户
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
		
		try {
			String json = request.getParameter("data");
			logger.debug(json);
			
			JSONArray array = JSONArray.fromObject(json);
			Object[] objs = array.toArray();
			for (Object object : objs) {
				JSONObject obj = (JSONObject) object;
				Users users = userService.getById(obj.getInt("id"));
				users.setUserName(obj.getString("userName"));
				users.setPassword(obj.getString("password"));
				users.setRealName(obj.getString("realName"));
				users.setGender(obj.getString("gender"));
				users.setSchool(obj.getString("school"));
				users.setSpecialty(obj.getString("specialty"));
				users.setDepartment(departmentService.getById(obj.getInt("departmentId")));
				users.setSuperiors(userService.getByUsername(obj.getString("superiorsName")));
				users.setBirthday(DateUtil.getDate(obj.getString("birthday"),"yyyy-MM-dd"));
				
				userService.updateUsers(users); 
			}
			SecurityChangeOperation.operate(request); //通知安全管理器
			response.getWriter().print("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("保存失败");
		}
		return null;
	}
	
	/**
	 * 用户修改密码
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		Users user = SecurityUserHolder.getCurrentUser();
		try {
			String password = request.getParameter("password");
			user.setPassword(password);
			userService.updateUsers(user);
			SecurityChangeOperation.operate(request); //通知安全管理器
			response.getWriter().print("修改密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("修改密码失败");
		}
		return null;
	}
}
