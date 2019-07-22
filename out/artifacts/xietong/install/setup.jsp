<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.huigao.util.Install"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>协同管理系统安装</title>
	</head>
	<body>
		<%
//			String dbHost = request.getParameter("dbHost");
//			String dbPort = request.getParameter("dbPort");
//			String dbName = request.getParameter("dbName");
//			String dbUser = request.getParameter("dbUser");
//			String dbPassword = request.getParameter("dbPassword");

            String dbHost = "localhost";
            String dbPort = "3306";
            String dbName = "xietong_db";
            String dbUser = "root";
            String dbPassword = "admin";

			String tableFileName = "/install/db/xietong_db.sql";
			String procFileName = "/install/db/statistic_proc.sql";
			String dbXmlFileName = "/WEB-INF/classes/jdbc.properties";
			String webXmlFrom = "/install/conf/web.xml";
			String webXmlTo = "/WEB-INF/web.xml";

			//创建数据库
			Install.createDb(dbHost, dbPort, dbName, dbUser, dbPassword);
			//创建表及初始化数据
			String sqlPath = application.getRealPath(tableFileName);
			Install.createTable(dbHost, dbPort, dbName, dbUser, dbPassword, Install.readSql(sqlPath));
			//创建存储过程
			Install.createProcedure(dbHost, dbPort, dbName, dbUser, dbPassword, Install.readProc(application.getRealPath(procFileName)));

			String dbXmlPath = application.getRealPath(dbXmlFileName);
            System.out.println("====dbXmlPath==" + dbXmlPath);
			Install.updateDbConf(dbXmlPath, dbHost, dbPort, dbName, dbUser, dbPassword);

			String webXmlFromPath = application.getRealPath(webXmlFrom);
			String webXmlToPath = application.getRealPath(webXmlTo);
			Install.updateWebConf(webXmlFromPath, webXmlToPath);

			System.out.println("====webXmlToPath==" + webXmlToPath);
		%>
		<table width="600" align="center"
			style="border: #106DBA 1px solid; margin-top: 8%;">
			<tr>
				<td bgcolor="#D1E9FA">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="20" valign="top" align="center">安装系统安装完成，请重启TOMCAT服务。</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="280" align="left" bgcolor="#F0F8FD"
					style="padding: 10px; line-height: 1.7em;">
					恭喜您，系统已经安装成功！<br />
					请稍后点击下面的链接或重启TOMCAT服务后点击链接。只有重启TOMCAT服务之后，安装才能生效。<br />
					系统管理员账号admin，密码admin。<br />
					&nbsp;&nbsp;&nbsp;&nbsp;<a href="index.jsp">点击这里进入系统首页</a>
				</td>
			</tr>
		</table>
	</body>
</html>
