package com.huigao.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.Project;
import com.huigao.pojo.Task;
import com.huigao.pojo.TaskLog;
import com.huigao.pojo.Users;
import com.huigao.security.support.SecurityUserHolder;
import com.huigao.service.ProjectService;
import com.huigao.service.TaskService;
import com.huigao.service.UserService;

public class TaskAction extends MappingDispatchActionSupport{
	
	private TaskService taskService;
	
	private UserService userService;
	
	private ProjectService projectService;

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	/* 列出所有的任务*/
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer start = 0;
		Integer limit = 12;
		
		//Users users = (Users) request.getSession().getAttribute("users");
		Users users = SecurityUserHolder.getCurrentUser();
		
		String s_start = request.getParameter("start"); //起始记录
		if(s_start != null) start = new Integer(s_start);
		String s_limit = request.getParameter("limit"); //每页条数限制
		if(s_limit != null) limit = new Integer(s_limit);
		String userName = request.getParameter("s_userName"); //Users表中的userName
		String sort = request.getParameter("sort");//排序的列
		String dir = request.getParameter("dir");//排序方向（'ASC' or 'DESC'） 
		String all = request.getParameter("all");
		String state = request.getParameter("state");
		
		List<Task> list = null;
		Integer totalCount = null;
		String audit = (String)request.getParameter("audit");
		String schedule = (String)request.getParameter("schedule");
		String my = (String)request.getParameter("my");
		
		if(audit!=null && userName==null){//列出待审核任务（任务状态：待审核）
			if(users!=null){
				list = taskService.listAuditTask(start, limit,sort,dir);
				totalCount = taskService.getTaskCount("audit", users);
			}else{
				response.getWriter().print("请重新登陆！");
				return null;
			}
		}else if(schedule!=null && userName==null){//列出要调度的任务，即从新指定负责人（任务状态：除去已完成）
			if(users!=null){
				list = taskService.listScheduleTask(start, limit,sort,dir);
				totalCount = taskService.getTaskCount("schedule", users);
			}else{
				response.getWriter().print("请重新登陆！");
				return null;
			}
		}else if(my!=null && my.equals("yes")){//列出自己的待完成的任务
			if(users!=null){
				list = taskService.listMyTask(start, limit, sort, dir, users);
				totalCount = taskService.getTaskCount("my", users);
			}else{
				response.getWriter().print("请重新登陆！");
				return null;
			}
		}else if(all!=null && all.equals("yes")){
			list = taskService.listTask(userName,start, limit,sort,dir,state);
			totalCount = taskService.getTaskCount();
		}else{//列出所有的任务
			list = taskService.listTask(userName,start, limit,sort,dir,state);
			totalCount = taskService.getTaskCount();
		}
		
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
	
	/* 列出待审核的任务*/
	public ActionForward listAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer start = 0;
		Integer limit = 12;
		
		Users users = SecurityUserHolder.getCurrentUser();
		
		String s_start = request.getParameter("start"); //起始记录
		if(s_start != null) start = new Integer(s_start);
		String s_limit = request.getParameter("limit"); //每页条数限制
		if(s_limit != null) limit = new Integer(s_limit);
		String userName = request.getParameter("s_userName"); //Users表中的userName
		if(userName==null) userName = "all";
		String sort = request.getParameter("sort"); //排序的列
		String dir = request.getParameter("dir"); //排序方向（'ASC' or 'DESC'） 
		
		List<Task> list = null;
		Integer totalCount = null;
		
		if(users!=null){
			list = taskService.listAuditTask(userName, start, limit, sort, dir);
			totalCount = taskService.getAuditCount(userName);
		}else{
			response.getWriter().print("请重新登陆！");
			return null;
		}
		
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
	
	/* 列出待调度的任务*/
	public ActionForward listSchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer start = 0;
		Integer limit = 12;
		
		Users users = SecurityUserHolder.getCurrentUser();
		
		String s_start = request.getParameter("start"); //起始记录
		if(s_start != null) start = new Integer(s_start);
		String s_limit = request.getParameter("limit"); //每页条数限制
		if(s_limit != null) limit = new Integer(s_limit);
		String userName = request.getParameter("s_userName"); //Users表中的userName
		if(userName==null) userName = "all";
		String sort = request.getParameter("sort"); //排序的列
		String dir = request.getParameter("dir"); //排序方向（'ASC' or 'DESC'） 
		
		List<Task> list = null;
		Integer totalCount = null;
		
		if(users!=null){
			list = taskService.listScheduleTask(userName, start, limit, sort, dir);
			totalCount = taskService.getScheduleCount(userName);
		}else{
			response.getWriter().print("请重新登陆！");
			return null;
		}
		
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
	
	/* 列出自己、待完成的任务 */
	public ActionForward listMy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer start = 0;
		Integer limit = 12;
		
		Users users = SecurityUserHolder.getCurrentUser();
		
		String s_start = request.getParameter("start"); //起始记录
		if(s_start != null) start = new Integer(s_start);
		String s_limit = request.getParameter("limit"); //每页条数限制
		if(s_limit != null) limit = new Integer(s_limit);
		String sort = request.getParameter("sort"); //排序的列
		String dir = request.getParameter("dir"); //排序方向（'ASC' or 'DESC'） 
		
		List<Task> list = null;
		Integer totalCount = null;
		
		if(users!=null){
			list = taskService.listMyTask(start, limit, sort, dir, users);
			totalCount = taskService.getMyCount(users);
		}else{
			response.getWriter().print("请重新登陆！");
			return null;
		}
		
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
	
	/* 列出所有的任务 */
	public ActionForward listAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer start = 0;
		Integer limit = 12;
		
		Users users = SecurityUserHolder.getCurrentUser();
		
		String s_start = request.getParameter("start"); //起始记录
		if(s_start != null) start = new Integer(s_start);
		String s_limit = request.getParameter("limit"); //每页条数限制
		if(s_limit != null) limit = new Integer(s_limit);
		String userName = request.getParameter("s_userName"); //Users表中的userName
		if(userName==null) userName = "all";
		String sort = request.getParameter("sort"); //排序的列
		String dir = request.getParameter("dir"); //排序方向（'ASC' or 'DESC'） 
		
		List<Task> list = null;
		Integer totalCount = null;
		
		if(users!=null){
			list = taskService.listALL(userName, start, limit, sort, dir);
			totalCount = taskService.getALLCount(userName);
		}else{
			response.getWriter().print("请重新登陆！");
			return null;
		}
		
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
	
	/* 列出所有的任务操作记录*/
	public ActionForward listLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer start = 0;
		Integer limit = 5;
		
		String s_start = request.getParameter("start"); //起始记录
		if(s_start != null) start = new Integer(s_start);
		String s_limit = request.getParameter("limit"); //每页条数限制
		if(s_limit != null) limit = new Integer(s_limit);
        String s_taskId = request.getParameter("taskId");
        Integer taskId = null;
        if(s_taskId!=null) taskId = Integer.parseInt(s_taskId);
        if(taskId==null) taskId = 0;
        
        List<TaskLog> list = null;
        Integer totalCount = null;
        
        list = taskService.listLog(start, limit, taskId);
        totalCount = taskService.getLogCount(taskId);
		
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
	
	/* 添加新任务 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		try {
			Users users = SecurityUserHolder.getCurrentUser();
			if(users==null){
				response.getWriter().print("{success:true,msg:'请您先登陆!'}");
				return null;
			}else{
				Task task = new Task();
				task.setPriority(request.getParameter("priority"));
				task.setProject(projectService.getById(new Integer(request.getParameter("projectId"))));
				task.setTaskdescription(request.getParameter("taskdescription"));
				task.setTaskname(request.getParameter("taskname"));
				task.setTaskstate("待完成");
				task.setTasktype(request.getParameter("tasktype"));
				Users user = userService.getByUsername(request.getParameter("superiorsName"));
				task.setUsers(user);
			
				taskService.saveTask(task);
				
				TaskLog tasklog = new TaskLog();
				tasklog.setAction("添加任务");
				java.util.Date date = new java.util.Date();
				tasklog.setDate(date);
				tasklog.setExecutor(user);
				tasklog.setOperator(users);
				tasklog.setTask(task);
				
				taskService.saveLog(tasklog);
				response.getWriter().print("{success:true,msg:'保存成功!'}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{success:true,msg:'保存失败!'}");
		}
		return null;
	}
	
	//送审核
	public ActionForward sendAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		try{
			Users users = SecurityUserHolder.getCurrentUser();
			String ids = (String)request.getParameter("ids");
			String userids = (String)request.getParameter("userids");
			if(users!=null){
				if(ids!=null&&userids!=null){
					String taskIds[] = ids.split(",");
					String userIds[] = userids.split(",");
					for(int i=0;i<userIds.length;i++){
						if(Integer.parseInt(userIds[i])!=users.getId()){
							//提示用户只能提交自己的任务return
							response.getWriter().print("只能提交自己的任务！");
							return null;
						}
					}
					for(int i=0;i<taskIds.length;i++){//将任务状态改为待审核
						taskService.sendAudit(Integer.parseInt(taskIds[i]));
						Task task = taskService.getById(Integer.parseInt(taskIds[i]));
						
						TaskLog tasklog = new TaskLog();
						tasklog.setAction("提交任务");
						java.util.Date date = new java.util.Date();
						tasklog.setDate(date);
						tasklog.setOperator(users);
						tasklog.setTask(task);
                        taskService.saveLog(tasklog);
					}
					response.getWriter().print("提交成功！");
				}
			}else{
				//请重新登陆
				response.getWriter().print("请重新登陆");
			}
		}catch(Exception e){
			response.getWriter().print("提交失败！");
		}
		return null;	
	}
	
	//审核任务
	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		response.setCharacterEncoding("utf-8");
		try{
			String taskstates = request.getParameter("taskstates");
			String ids = request.getParameter("ids");
			String[] taskstate = taskstates.split(",");
			String[] id = ids.split(",");
			for(int i=0;i<taskstate.length;i++){
				//如果结果为“审核通过”，就将状态改为“已完成”，否则将状态改为“待完成”
				taskService.audit(taskstate[i], Integer.parseInt(id[i]));
				
				Task task = taskService.getById(Integer.parseInt(id[i]));
				Users users = SecurityUserHolder.getCurrentUser();
				
				TaskLog tasklog = new TaskLog();
				tasklog.setAction("审核任务");
				java.util.Date date = new java.util.Date();
				tasklog.setDate(date);
				tasklog.setExecutor(task.getUsers());
				tasklog.setOperator(users);
				tasklog.setResult(taskstate[i]);
				tasklog.setTask(task);
				taskService.saveLog(tasklog);
			}
			response.getWriter().print("保存成功！");
		}catch(Exception e){
			response.getWriter().print("保存失败！");
		}	
		return null;
	}
	
	//调度任务
	public ActionForward schedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		response.setCharacterEncoding("utf-8");
		try{
			String ids = request.getParameter("ids");
			String userNames = request.getParameter("userNames");
			String[] id = ids.split(",");
			String[] userName = userNames.split(",");
			for(int i=0;i<userName.length;i++){
				Users users = userService.getByUsername(userName[i]);
				taskService.schedule(users, Integer.parseInt(id[i]));
				Users user = SecurityUserHolder.getCurrentUser();
				Task task = taskService.getById(Integer.parseInt(id[i]));
				
				TaskLog tasklog = new TaskLog();
				tasklog.setAction("调度任务");
				java.util.Date date = new java.util.Date();
				tasklog.setDate(date);
				tasklog.setExecutor(users);
				tasklog.setOperator(user);
				tasklog.setTask(task);
				taskService.saveLog(tasklog);
			}
			response.getWriter().print("保存成功！");
		}catch(Exception e){
			response.getWriter().print("保存失败！");
		}	
		return null;
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		response.setCharacterEncoding("UTF-8");
		try{
			//Users users = (Users) request.getSession().getAttribute("users");
			Users users = SecurityUserHolder.getCurrentUser();
			String taskids = (String)request.getParameter("taskids");
			if(users!=null){
				if(taskids!=null){
					String taskIds[] = taskids.split(",");
					for(int i=0;i<taskIds.length;i++){
						taskService.delete(Integer.parseInt(taskIds[i]));
					}
					response.getWriter().print("删除成功！");
				}
			}else{
				//请重新登陆
				response.getWriter().print("请重新登陆");
			}
		}catch(Exception e){
			response.getWriter().print("删除失败！");
		}
		return null;
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		response.setCharacterEncoding("UTF-8");
		
		String idString = request.getParameter("id");
		Integer id = null;
		if(idString!=null){
			id = new Integer(idString);
		}	
		String taskdescription = request.getParameter("taskdescription");
		
		String ids = request.getParameter("ids");
		String tasknames = request.getParameter("tasknames");
		//String userNames = request.getParameter("userNames");
		String projectNames = request.getParameter("projectNames");
		String tasktypes = request.getParameter("tasktypes");
		String prioritys = request.getParameter("prioritys");

		if(id!=null){
			try {
				Task task = (Task) taskService.getById(id);
				task.setTaskdescription(taskdescription);
				taskService.update(task); 
				response.getWriter().print("{success:true,msg:'修改成功!'}");
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().print("{success:true,msg:'修改失败!'}");
			}
			return null;	
		}else{
			try{
				String[] id1 = ids.split(",");
				String[] taskname = tasknames.split(",");
				//String[] userName = userNames.split(",");
				String[] projectName = projectNames.split(",");
				String[] tasktype = tasktypes.split(",");
				String[] priority = prioritys.split(",");
				for(int i=0;i<id1.length;i++){
					Task task = (Task)taskService.getById(Integer.parseInt(id1[i]));
					task.setTaskname(taskname[i]);
					Project project = projectService.getByName(projectName[i]);
					task.setProject(project);
					task.setTasktype(tasktype[i]);
					task.setPriority(priority[i]);
					taskService.update(task);
				}
				response.getWriter().print("修改成功!");
			}catch(Exception e){
				response.getWriter().print("修改失败!");
			}
			
			return null;
		}
	}
	
	


}
