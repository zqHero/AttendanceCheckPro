package com.huigao.struts.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.Resource;
import com.huigao.pojo.Users;
import com.huigao.security.support.SecurityUserHolder;


/**
 * 导航树操作
 * @author cgx
 * @version 1.0
 */
public class ExtTreeAction extends MappingDispatchActionSupport {
	
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 生成导航树的json对象字符串
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getJsonTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		boolean daka = false; //是否有打卡管理的权限
		//boolean holiday = false;
		boolean user = false; //是否有用户管理的权限
		boolean department = false; //是否有部门管理的权限
		boolean resource = false; //是否有资源(权限)管理的权限
		boolean role = false;  //是否有角色管理的权限
		//boolean overtime = false;
		boolean project = false; //是否有项目管理的权限
		boolean taskmanage = false; //是否有任务管理的权限
		boolean schedule = false; 
		boolean audit = false;
		
		Users u = SecurityUserHolder.getCurrentUser();
		Map<String,List<Resource>> m = u.getRoleResources(); 
		Set<String> set = m.keySet();
		Iterator<String> it = set.iterator(); 
		for (int i = 0; i < m.size(); i++) {
			String key = it.next();
			List<Resource> list = m.get(key);
			for (int j = 0; j < list.size(); j++) {
				Resource r = list.get(j);
				if(r.getValue().contains("/module/daka"))daka = true;
				else if(r.getValue().contains("/module/department")) department = true;
				else if(r.getValue().contains("/module/users")) user = true;
				//else if(r.getValue().contains("/module/holidaylog")) holiday = true;
				//else if(r.getValue().contains("/module/overtime")) overtime = true;
				else if(r.getValue().contains("/module/role")) role = true;
				else if(r.getValue().contains("/module/resource")) resource = true;
				else if(r.getValue().contains("/module/project")) project = true;
				else if(r.getValue().contains("/module/task/taskmanage")) taskmanage = true;
				else if(r.getValue().contains("/module/task/schedule")) schedule = true;
				else if(r.getValue().contains("/module/task/audit")) audit = true;
			}
		}
		JSONArray array = new JSONArray();
		
		
		int i = 0;
		int j = 0;
		
		
		JSONObject daka_json = new JSONObject();
			
			daka_json.put("text", "打卡管理");
			daka_json.put("id", ++i);
			
			JSONArray daka_array_child = new JSONArray();
				JSONObject monthlist_json = new JSONObject();
				monthlist_json.put("text", "个人打卡月度明细");
				monthlist_json.put("id", i + "" + (++j));
				monthlist_json.put("leaf", true);
				monthlist_json.put("link", "module/common/monthlist.jsp");
					JSONArray monthlist_array_js = new JSONArray();
					JSONObject monthlist_json_js = new JSONObject();
					monthlist_json_js.put("js", "module/common/monthlist.js");
					monthlist_array_js.add(monthlist_json_js);
				monthlist_json.put("jsArray", monthlist_array_js);
			daka_array_child.add(monthlist_json);
			
			if(daka) {
				
			JSONObject monthcollect_json = new JSONObject();
				monthcollect_json.put("text", "月度汇总");
				monthcollect_json.put("id", i + "" + (++j));
				monthcollect_json.put("leaf", true);
				monthcollect_json.put("link", "module/daka/monthcollect.jsp");
					JSONArray monthcollect_array_js = new JSONArray();
					JSONObject monthcollect_json_js = new JSONObject();
					monthcollect_json_js.put("js", "module/daka/monthcollect_js.jsp");
					monthcollect_array_js.add(monthcollect_json_js);
				monthcollect_json.put("jsArray", monthcollect_array_js);
			daka_array_child.add(monthcollect_json);
			
			JSONObject yearcollect_json = new JSONObject();
				yearcollect_json.put("text", "年度汇总");
				yearcollect_json.put("id", i + "" + (++j));
				yearcollect_json.put("leaf", true);
				yearcollect_json.put("link", "module/daka/yearcollect.jsp");
					JSONArray yearcollect_array_js = new JSONArray();
					JSONObject yearcollect_json_js = new JSONObject();
					yearcollect_json_js.put("js", "module/daka/yearcollect.js");
					yearcollect_array_js.add(yearcollect_json_js);
				yearcollect_json.put("jsArray", yearcollect_array_js);
			daka_array_child.add(yearcollect_json);
			
			JSONObject dakamanage_json = new JSONObject();
				dakamanage_json.put("text", "打卡记录调整");
				dakamanage_json.put("id", i + "" + (++j));
				dakamanage_json.put("leaf", true);
				dakamanage_json.put("link", "module/daka/dakamanage.jsp");
					JSONArray dakamanage_array_js = new JSONArray();
					JSONObject dakamanage_json_js = new JSONObject();
					dakamanage_json_js.put("js", "module/daka/dakamanage.js");
					dakamanage_array_js.add(dakamanage_json_js);
				dakamanage_json.put("jsArray", dakamanage_array_js);
			daka_array_child.add(dakamanage_json);
			}
			daka_json.put("children", daka_array_child);
		array.add(daka_json);
			
		/*if(holiday) {
			JSONObject holiday_json = new JSONObject();
			
			holiday_json.put("text", "请假管理");
			holiday_json.put("id", ++i);
			holiday_json.put("leaf", true);
			holiday_json.put("link", "module/holidaylog/holidaymanage.jsp");
			JSONArray holiday_array_js = new JSONArray();
				JSONObject holiday_json_js = new JSONObject();
				holiday_json_js.put("js", "module/holidaylog/holidaymanage.js");
			holiday_array_js.add(holiday_json_js);
			holiday_json.put("jsArray", holiday_array_js);
			
			array.add(holiday_json);
		}
		
		if(overtime) {
			JSONObject overtime_json = new JSONObject();
			overtime_json.put("text", "加班管理");
			overtime_json.put("id", ++i);
			overtime_json.put("leaf", true);
			overtime_json.put("link", "module/overtime/overtimemanage.jsp");
			JSONArray overtime_array_js = new JSONArray();
				JSONObject overtime_json_js = new JSONObject();
				overtime_json_js.put("js", "module/overtime/overtimemanage.js");
				overtime_array_js.add(overtime_json_js);
			overtime_json.put("jsArray", overtime_array_js);
			array.add(overtime_json);
		}*/
		
		if(user) {
			JSONObject user_json = new JSONObject();
			user_json.put("text", "员工管理");
			user_json.put("id", ++i);
			user_json.put("leaf", true);
			user_json.put("link", "module/users/personmanage.jsp");
			JSONArray user_array_js = new JSONArray();
				JSONObject user_json_js = new JSONObject();
				user_json_js.put("js", "module/users/personmanage.js");
				user_array_js.add(user_json_js);
			user_json.put("jsArray", user_array_js);
			array.add(user_json);
		}
		
		if(department) {
			JSONObject department_json = new JSONObject();
			department_json.put("text", "部门管理");
			department_json.put("id", ++i);
			department_json.put("leaf", true);
			department_json.put("link", "module/department/departmentmanage.jsp");
			JSONArray department_array_js = new JSONArray();
				JSONObject department_json_js = new JSONObject();
				department_json_js.put("js", "module/department/departmentmanage.js");
				department_array_js.add(department_json_js);
			department_json.put("jsArray", department_array_js);
			array.add(department_json);
		}
		
		if(resource) {
			JSONObject resource_json = new JSONObject(); 
			resource_json.put("text", "权限管理");
			resource_json.put("id", ++i);
			resource_json.put("leaf", true);
			resource_json.put("link", "module/resource/resourcemanage.jsp");
			JSONArray resource_array_js = new JSONArray();
				JSONObject resource_json_js = new JSONObject();
				resource_json_js.put("js", "module/resource/resourcemanage.js");
				resource_array_js.add(resource_json_js);
			resource_json.put("jsArray", resource_array_js);
			array.add(resource_json);
		}
		
		if(role) {
			JSONObject role_json = new JSONObject();
			role_json.put("text", "角色管理");
			role_json.put("id", ++i);
			role_json.put("leaf", true);
			role_json.put("link", "module/role/rolemanage.jsp");
			JSONArray role_array_js = new JSONArray();
				JSONObject role_json_js = new JSONObject();
				role_json_js.put("js", "module/role/rolemanage.js");
				role_array_js.add(role_json_js);
			role_json.put("jsArray", role_array_js);
			array.add(role_json);
		}
		
		if(project) {
			JSONObject project_json = new JSONObject();
			project_json.put("text", "项目管理");
			project_json.put("id", ++i);
			project_json.put("leaf", true);
			project_json.put("link", "module/project/projectmanage.jsp");
			JSONArray project_array_js = new JSONArray();
				JSONObject project_json_js = new JSONObject();
				project_json_js.put("js", "module/project/projectmanage.js");
				project_array_js.add(project_json_js);
				project_json.put("jsArray", project_array_js);
			array.add(project_json);
		}
		
		j = 0;
		JSONObject taskManage = new JSONObject();
		taskManage.put("text", "任务管理");
		taskManage.put("id", ++i);
		
		JSONArray project_array_children = new JSONArray();
		
		if(taskmanage) {
			JSONObject json_children_project = new JSONObject();
			json_children_project.put("text", "管理任务");
			json_children_project.put("id", i + "" + (++j));
			json_children_project.put("leaf", true);
			json_children_project.put("link", "module/task/taskmanage.jsp");
			JSONArray array_children_project_js = new JSONArray();
			JSONObject json_children_project_js = new JSONObject();
			json_children_project_js.put("js", "module/task/taskmanage.js");
			array_children_project_js.add(json_children_project_js);
			json_children_project.put("jsArray", array_children_project_js);
		    project_array_children.add(json_children_project);
		}
		
		if(schedule) {
			JSONObject json_children_project = new JSONObject();
			json_children_project.put("text", "调度任务");
			json_children_project.put("id", i + "" + (++j));
			json_children_project.put("leaf", true);
			json_children_project.put("link", "module/task/schedule.jsp");
			JSONArray array_children_project_js = new JSONArray();
			JSONObject json_children_project_js = new JSONObject();
			json_children_project_js.put("js", "module/task/schedule.js");
			array_children_project_js.add(json_children_project_js);
			json_children_project.put("jsArray", array_children_project_js);
		    project_array_children.add(json_children_project);
		}
		
		if(audit) {
			JSONObject json_children_project = new JSONObject();
			json_children_project.put("text", "审核任务");
			json_children_project.put("id", i + "" + (++j));
			json_children_project.put("leaf", true);
			json_children_project.put("link", "module/task/audit.jsp");
			JSONArray array_children_project_js = new JSONArray();
			JSONObject json_children_project_js = new JSONObject();
			json_children_project_js.put("js", "module/task/audit.js");
			array_children_project_js.add(json_children_project_js);
			json_children_project.put("jsArray", array_children_project_js);
		    project_array_children.add(json_children_project);
		}
		
			
			JSONObject json_children_task = new JSONObject();
			json_children_task.put("text", "提交管理");
			json_children_task.put("id", i + "" + (++j));
			json_children_task.put("leaf", true);
			json_children_task.put("link", "module/task/query.jsp");
			JSONArray array_children_task_js = new JSONArray();
			JSONObject json_children_task_js = new JSONObject();
			json_children_task_js.put("js", "module/task/query.js");
			array_children_task_js.add(json_children_task_js);
			json_children_task.put("jsArray", array_children_task_js);
		    project_array_children.add(json_children_task);
		    taskManage.put("children", project_array_children);
		
		array.add(taskManage);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array);
		logger.debug(array);
		return null;
	}
	
}
	