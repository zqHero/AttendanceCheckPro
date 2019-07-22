package com.huigao.struts.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.OverTime;
import com.huigao.service.OverTimeService;
import com.huigao.service.UserService;
import com.huigao.util.DateUtil;

/**
 * 加班管理(未用)
 * @author cgx
 * @version 1.0
 */
public class OverTimeAction extends MappingDispatchActionSupport {

	private OverTimeService overTimeService;
	private UserService userService;
	private Logger logger = Logger.getLogger(this.getClass());
	

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Integer start = 0;
		Integer limit = 25;
		String s_start = request.getParameter("start");
		String s_limit = request.getParameter("limit");
		
		if(s_start != null) start = new Integer(s_start);
		if(s_limit != null) limit = new Integer(s_limit);
		
		List<OverTime> list = overTimeService.list(start,limit);
		Integer totalCount = overTimeService.getTotalCount();
		StringBuffer array = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			OverTime ot = list.get(i);
			if(i != 0) array.append(",");
			array.append(ot.toJsonString()); 
		}
		response.setCharacterEncoding("UTF-8");
		String str = " { totalCount:" + totalCount + ", root:[" + array + "] } ";
		logger.debug(str);
		response.getWriter().print(str);
		return null;
	}


	/* 添加加班记录 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		try {
			String userName = request.getParameter("userName");
			Date startTime = DateUtil.getDate(request.getParameter("startTime"),"yyyy-MM-dd HH:mm");
			Date endTime = DateUtil.getDate(request.getParameter("endTime"),"yyyy-MM-dd HH:mm");
			String reason = request.getParameter("reason");
			
			if(startTime.compareTo(endTime)>0) {
				response.getWriter().print("{success:true,msg:'开始日期不能大于结束日期!'}");
				return null;
			}
			
			OverTime ot = new OverTime();
			ot.setUser(userService.getByUsername(userName));
			ot.setStartTime(startTime);
			ot.setEndTime(endTime);
			ot.setReason(reason);
			overTimeService.saveOverTime(ot);
			
			response.getWriter().print("{success:true,msg:'保存成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{success:true,msg:'保存失败!'}");
		}
		return null;
	}
	
	/* 删除 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String overTimeId = request.getParameter("overTimeId");
		try {
			OverTime ot = overTimeService.getOverTimeById(new Integer(overTimeId));
			overTimeService.deleteOverTime(ot);
			response.getWriter().print("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("删除失败");
		}
		return null;
	}
	
	
	// --- Inject ---
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setOverTimeService(OverTimeService overTimeService) {
		this.overTimeService = overTimeService;
	}
}
