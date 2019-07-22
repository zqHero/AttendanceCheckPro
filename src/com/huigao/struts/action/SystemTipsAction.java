package com.huigao.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.MappingDispatchActionSupport;

import com.huigao.pojo.SystemTips;
import com.huigao.service.SystemTipsService;

/**
 * 系统提示管理
 * @author cgx
 * @version 1.0
 */
public class SystemTipsAction extends MappingDispatchActionSupport {
	
	private SystemTipsService systemTipsService;

	public void setSystemTipsService(SystemTipsService systemTipsService) {
		this.systemTipsService = systemTipsService;
	}

	/**
	 * 系统提示集合
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listTips(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		
		List<SystemTips> list = systemTipsService.list();
		String jsonString = " { totalCount:" + list.size() + ",root:" + JSONArray.fromObject(list) + " } ";
		response.getWriter().print(jsonString);
		return null;
		
		/*String json = " " +
		"{" +
			"totalCount:10," +
			"root:[" +
			"	{id:1,tipName:'上班'}," +
			"	{id:2,tipName:'一级迟到'}," +
			"	{id:3,tipName:'二级迟到'}," +
			"	{id:4,tipName:'三级迟到'}," +
			"	{id:5,tipName:'早退'}," +
			"	{id:6,tipName:'下班'}," +
			"	{id:7,tipName:'事假'}," +
			"	{id:8,tipName:'病假'}," +
			"	{id:9,tipName:'休假'}," +
			"	{id:10,tipName:'加班'}" +
			"] " +
		"} ";*/
	}

}
