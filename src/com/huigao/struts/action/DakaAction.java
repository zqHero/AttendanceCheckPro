package com.huigao.struts.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.util.Assert;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.DakaLog;
import com.huigao.pojo.Statistic;
import com.huigao.pojo.Users;
import com.huigao.security.support.SecurityUserHolder;
import com.huigao.service.DakaService;
import com.huigao.service.StatisticService;
import com.huigao.service.SystemTipsService;
import com.huigao.service.UserService;
import com.huigao.util.DateUtil;


public class DakaAction extends MappingDispatchActionSupport {
	
	private DakaService dakaService;
	private UserService userService;
	private StatisticService statisticService;
	private SystemTipsService systemTipsService;
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 通过Ajax实时获取服务器系统时间
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCurrentTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users users = SecurityUserHolder.getCurrentUser();
		Assert.notNull(users);
		String info;
		Date date = new Date();
		if(dakaService.getDakaLogByUserAndDay(users.getId(), date) == null && DateUtil.getMinites(DateUtil.getDateFromTime((dakaService.getWorkTimeById(4).getTime())), date)>0) info = "上班打卡";
		else info = "下班打卡";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(info + ",  现在时间：" + new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date())); 
		return null;
	}
	
	/**
	 * 员工打卡
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward daka(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users users = SecurityUserHolder.getCurrentUser();
		Assert.notNull(users);
		response.setCharacterEncoding("UTF-8");
		
		DakaLog dakaLog = new DakaLog();
		dakaLog.setUsers(users);
		String tips = dakaService.saveDakaLog(users);
		
		response.getWriter().print(tips);
		return null;
	}
	
	/**
	 * 个人打卡月度明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward monthlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Users user = SecurityUserHolder.getCurrentUser(); //获取当前登录用户
		
		Date startDate = null;
		Date endDate = null;
		String month = request.getParameter("month");
		Date date = DateUtil.getDate(month, "yyyy-MM");
		Calendar cal = Calendar.getInstance();
		if(date == null) {
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			startDate = cal.getTime();
			cal.add(Calendar.MONTH, 1);
			endDate = cal.getTime();
		} else {
			startDate = date;
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			endDate = cal.getTime();
		}
		
		Integer userId = user.getId();
		String s_userid = request.getParameter("userId");
		if(s_userid != null) userId = new Integer(s_userid);
		
		Integer totalCount = dakaService.getCountByDateTime(userId, startDate, endDate);
		int limit;
		int start;
		try {
			limit = new Integer(request.getParameter("limit"));
			start = new Integer(request.getParameter("start"));
		} catch (Exception e) {
			limit = 31;
			start = 0;
		} 
		
		//------分页查询
		StringBuffer buf = new StringBuffer("{totalCount :").append(totalCount).append(", root :[");
		List<DakaLog> list = dakaService.listByDateTime(userId , startDate, endDate, start, limit);
		if(list!=null && !list.isEmpty())  {
			for (int i = 0; i < list.size(); i++) {
				DakaLog dakaLog = list.get(i);
				if(i != 0)  buf.append(",");
				buf.append(dakaLog.toJsonString());
			} 
		}
		buf.append("] }");
		logger.debug(buf.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(buf.toString());
		return null;
	}
	
	/**
	 * 个人打卡详细列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date startDate = null;
		Date endDate = null;
		String month = request.getParameter("month");
		Date date = DateUtil.getDate(month, "yyyy-MM");
		Calendar cal = Calendar.getInstance();
		if(date == null) {
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			startDate = cal.getTime();
			cal.add(Calendar.MONTH, 1);
			endDate = cal.getTime();
		} else {
			startDate = date;
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			endDate = cal.getTime();
		}
		
		int limit = 10000;
		int start = 0; 
		String s_start = request.getParameter("start");
		String s_limit = request.getParameter("limit");
		if(s_start != null) start = new Integer(s_start);
		if(s_limit != null) limit = new Integer(s_limit);
		
		List<DakaLog> list = new ArrayList<DakaLog>();
		Integer totalCount = 0;
		
		Integer departmentId = 0;
		String s_departmentId = request.getParameter("departmentId"); 
		String userName = request.getParameter("userName");
		
		//-----是按部门查询  还是 按用户名查询
		if(s_departmentId != null && !"".equals(s_departmentId.trim())) {
			departmentId = new Integer(s_departmentId);
			list = dakaService.listByDateTimeAndDepartment(departmentId, startDate, endDate, start, limit);
			totalCount = dakaService.getCountByDateTimeAndDepartment(departmentId, startDate, endDate);
		}else if(userName != null && !"".equals(userName.trim())) {
			int userId = userService.getByUsername(userName).getId(); 
			list = dakaService.listByDateTime(userId , startDate, endDate, start, limit);
			totalCount = dakaService.getCountByDateTime(userId, startDate, endDate);
		}
		
		//----json对象
		StringBuffer buf = new StringBuffer("{totalCount :").append(totalCount).append(", root :[");
		if(list!=null && !list.isEmpty())  {
			for (int i = 0; i < list.size(); i++) {
				DakaLog dakaLog = list.get(i);
				if(i != 0) buf.append(",");
				buf.append(dakaLog.toJsonString());
			}
		}
		buf.append("] }");
		logger.debug(buf.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(buf.toString());
		return null;
	}
	
	/**
	 * 判断某个月份的数据是否已经统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward hasStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String month = request.getParameter("month");
		Date date = DateUtil.getDate(month, "yyyy-MM");
		if(date == null) {
			response.getWriter().print("后台出错");
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		boolean flag = statisticService.hasStatistic(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1);
		response.getWriter().print(flag);
		return null;
	}
	
	/**
	 * 统计某个月份的数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String month = request.getParameter("month");
		try {
			Date date = DateUtil.getDate(month, "yyyy-MM");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			statisticService.statistic(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1);
			response.getWriter().print(true);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(false);
		}
		return null;
	}
	
	/**
	 * 月度汇总列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward monthcollect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Integer start = new Integer(request.getParameter("start"));
		Integer limit = new Integer(request.getParameter("limit"));
		
		Integer departmentId = 0;
		String s_department = request.getParameter("departmentId"); 
		if(s_department!=null && !"".equals(s_department)) departmentId = new Integer(s_department);
		String month = request.getParameter("month");
		Date date = DateUtil.getDate(month, "yyyy-MM");
		Calendar cal = Calendar.getInstance();
		if(date != null) cal.setTime(date);
		else cal.add(Calendar.MONTH, -1);
		Integer totalCount = statisticService.getCountByDepartment(departmentId, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1);
		List<Statistic> list = statisticService.listByDepartment(departmentId, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, start, limit);
		StringBuffer buf = new StringBuffer("{totalCount :").append(totalCount).append(", root : [");
		for (int i = 0; i < list.size(); i++) {
			if(i != 0) buf.append(",");
			buf.append(list.get(i).toJsonString());
		}
		buf.append(" ] } "); 
		System.out.println(buf.toString()); 
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(buf.toString());
		return null;
	}
	
	/**
	 * 年度统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward yearcollect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Integer start = new Integer(request.getParameter("start"));
		Integer limit = new Integer(request.getParameter("limit"));
		
		Integer departmentId = 0;
		String s_department = request.getParameter("departmentId");
		if(s_department!=null) departmentId = new Integer(s_department);
		Integer year = 0;
		String s_year = request.getParameter("year");
		if(s_year!=null) year = new Integer(s_year);
		Integer totalCount = statisticService.getCountByDepartment(departmentId, year);
		List<Statistic> list = statisticService.listByDepartment(departmentId, year, start, limit);
		StringBuffer buf = new StringBuffer("{totalCount :").append(totalCount).append(", root : [");
		for (int i = 0; i < list.size(); i++) {
			if(i != 0) buf.append(",");
			buf.append(list.get(i).toJsonString());
		}
		buf.append(" ] } "); 
		System.out.println(buf.toString()); 
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(buf.toString());
		return null;
	}
	
	/**
	 * 打卡调整--添加一条打卡记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception { 
		response.setCharacterEncoding("UTF-8");
		String date = request.getParameter("date");
		String userName = request.getParameter("userName");
		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date1");
		String date3 = request.getParameter("date1");
		String date4 = request.getParameter("date1");
		
		String tip1 = request.getParameter("tip1");
		String tip2 = request.getParameter("tip2");
		String tip3 = request.getParameter("tip3");
		String tip4 = request.getParameter("tip4");
		
		try {
			DakaLog dakaLog = new DakaLog();
			dakaLog.setUsers(userService.getByUsername(userName));
			dakaLog.setDate1(DateUtil.getDate(date + " " + date1, "yyyy-MM-dd HH:mm:ss"));
			dakaLog.setDate2(DateUtil.getDate(date + " " + date2, "yyyy-MM-dd HH:mm:ss")); 
			dakaLog.setDate3(DateUtil.getDate(date + " " + date3, "yyyy-MM-dd HH:mm:ss")); 
			dakaLog.setDate4(DateUtil.getDate(date + " " + date4, "yyyy-MM-dd HH:mm:ss")); 
			dakaLog.setTip1(systemTipsService.getById(new Integer(tip1)));
			dakaLog.setTip2(systemTipsService.getById(new Integer(tip2)));
			dakaLog.setTip3(systemTipsService.getById(new Integer(tip3)));
			dakaLog.setTip4(systemTipsService.getById(new Integer(tip4)));
			dakaService.saveDakaLog(dakaLog);
			response.getWriter().print("{success:'true',msg:'添加成功'} ");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{success:'true',msg:'添加失败'} ");
		}
		return null;
	}
	
	/**
	 * 删除某条打卡记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception { 
		response.setCharacterEncoding("UTF-8");
		String dakaLogId = request.getParameter("dakaLogId");
		try {
			dakaService.deleteDakaLog(dakaService.getDakaLogById(new Integer(dakaLogId)));  
			response.getWriter().print("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("删除失败");
		}
		return null;
	}
	
	/**
	 * 更新一条打卡记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateDakaLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception { 
		response.setCharacterEncoding("UTF-8");
		String json = request.getParameter("data");
		logger.debug(json);
		Collection<DakaLog> col = (Collection<DakaLog>) dakaService.getListByJsonArray(json);
		dakaService.updateDakaLog(col);
		response.getWriter().print("修改成功");
		return null;
	}

	// --- Inject ---
	public void setStatisticService(
			StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setSystemTipsService(SystemTipsService systemTipsService) {
		this.systemTipsService = systemTipsService;
	}
	public void setDakaService(DakaService dakaService) {
		this.dakaService = dakaService;
	}

}

