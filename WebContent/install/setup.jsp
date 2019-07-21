<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="com.huigao.util.Install" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
    String dbHost = request.getParameter("dbHost");
    String dbPort = request.getParameter("dbPort");
    String dbName = request.getParameter("dbName");
    String dbUser = request.getParameter("dbUser");
    String dbPassword = request.getParameter("dbPassword");

    String tableFileName = "/install/db/xietong_db.sql";
    String procFileName = "/install/db/statistic_proc.sql";
    String dbXmlFileName = "/WEB-INF/classes/jdbc.properties";
    String webXmlFrom = "/install/conf/web.xml";
    String webXmlTo = "/WEB-INF/web.xml";

    //create database     init table
    Install.createDb(dbHost, dbPort, dbName, dbUser, dbPassword);
    String sqlPath = application.getRealPath(tableFileName);
    Install.createTable(dbHost, dbPort, dbName, dbUser, dbPassword, Install.readSql(sqlPath));
    Install.createProcedure(dbHost, dbPort, dbName, dbUser, dbPassword, Install.readProc(application.getRealPath(procFileName)));
    String dbXmlPath = application.getRealPath(dbXmlFileName);
    Install.updateDbConf(dbXmlPath, dbHost, dbPort, dbName, dbUser, dbPassword);
    String webXmlFromPath = application.getRealPath(webXmlFrom);
    String webXmlToPath = application.getRealPath(webXmlTo);
    Install.updateWebConf(webXmlFromPath, webXmlToPath);
%>
<table width="500" align="center" style="border: #106DBA 1px solid; margin-top: 8%;">
    <tr>
        <td bgcolor="#D1E9FA">
            <div style="height: 20px;text-align: center;font-size: 15px">
                安装系统安装完成，请重启TOMCAT服务。
            </div>
        </td>
    </tr>
    <tr>
        <td height="250" align="left" bgcolor="#F0F8FD"  style="font-size: 15px;padding: 10px; line-height: 1.7em;">
            恭喜您，系统已经安装成功！<br/>
            请稍后点击下面的链接或重启TOMCAT服务后点击链接。只有重启TOMCAT服务之后，安装才能生效。<br/>
            系统管理员账号admin,密码admin。<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <div style="margin-top: 20px;text-align: center">
                <a href="index.jsp">点击这里进入系统首页</a>
            </div>
        </td>
    </tr>
</table>


</body>
</html>
