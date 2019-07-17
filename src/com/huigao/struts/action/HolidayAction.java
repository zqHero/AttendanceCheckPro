package com.huigao.struts.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.Holiday;
import com.huigao.pojo.HolidayLog;
import com.huigao.service.HolidayService;
import com.huigao.service.UserService;
import com.huigao.util.DateUtil;

/**
 * 请假管理(未用)
 * @author cgx
 * @version 1.0
 */
public class HolidayAction extends MappingDispatchActionSupport {

	private HolidayService holidayService;
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setHolidayService(HolidayService holidayService) {
		this.holidayService = holidayService;
	}
	
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Holiday> list = holidayService.listHoliday();
		StringBuffer array = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			Holiday h = list.get(i);
			if(i != 0) array.append(",");
			array.append("[").append(h.getId()).append(",'").append(h.getHolidayName()).append("']"); 
		}
		response.setCharacterEncoding("UTF-8");
		String str = "[" + array + "] ";
		System.out.println(str);
		response.getWriter().print(str);
		return null;
	}


	public ActionForward listLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer userId = 0;
		Integer start = 0;
		Integer limit = 12;
		
		String s_userId = request.getParameter("userId"); //员工编号
		if(s_userId != null && !"".equals(s_userId)) userId = new Integer(s_userId);
		String s_start = request.getParameter("start"); //起始记录
		if(s_start != null) start = new Integer(s_start);
		String s_limit = request.getParameter("limit"); //每页条数限制
		if(s_limit != null) limit = new Integer(s_limit);
		
		List<HolidayLog> list = holidayService.listHolidayLogByUser(userId, start, limit);
		Integer totalCount = holidayService.getHolidayLogCountByUser(userId); 
		
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
	
	/* 添加新记录 */
	public ActionForward saveLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		try {
			String userName = request.getParameter("userName");
			Integer holidayId = new Integer(request.getParameter("holidayId"));
			Date startTime = DateUtil.getDate(request.getParameter("startTime"),"yyyy-MM-dd HH:mm");
			Date endTime = DateUtil.getDate(request.getParameter("endTime"),"yyyy-MM-dd HH:mm");
			String reason = request.getParameter("reason");
			
			if(startTime.compareTo(endTime)>0) {
				response.getWriter().print("{success:true,msg:'开始日期不能大于结束日期!'}");
				return null;
			}
			
			HolidayLog hl = new HolidayLog();
			hl.setUsers(userService.getByUsername(userName));
			hl.setHoliday(holidayService.getById(holidayId));
			hl.setStartTime(startTime);
			hl.setEndTime(endTime);
			hl.setReason(reason);
			holidayService.saveHolidayLog(hl);
			response.getWriter().print("{success:true,msg:'保存成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{success:true,msg:'保存失败!'}");
		}
		return null;
	}
	
	/* 删除记录 */
	public ActionForward deleteLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String holidayLogId = request.getParameter("id");
		try {
			HolidayLog log = holidayService.getLogById(new Integer(holidayLogId));
			holidayService.deleteHolidayLog(log);
			response.getWriter().print("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("删除失败");
		}
		return null;
	}
	
	
	public ActionForward updateLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		try {
			String json = request.getParameter("data");
			System.out.println(json);
			
			JSONArray array = JSONArray.fromObject(json);
			Object[] objs = array.toArray();
			for (Object object : objs) {//未判断开始时间应该小于结束时间
				JSONObject obj = (JSONObject) object;
				HolidayLog log = holidayService.getLogById(obj.getInt("id")); 
				log.setHoliday(holidayService.getById(obj.getInt("holidayId")));
				log.setStartTime(DateUtil.getDate(obj.getString("startTime").replaceAll("T", " "), "yyyy-MM-dd HH:mm:ss"));
				log.setEndTime(DateUtil.getDate(obj.getString("endTime").replaceAll("T", " "), "yyyy-MM-dd HH:mm:ss"));
				log.setReason(obj.getString("reason")); 
				//holidayService.updateLog(log);
			}
			response.getWriter().print("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("保存失败");
		}
		return null;
	}
}
