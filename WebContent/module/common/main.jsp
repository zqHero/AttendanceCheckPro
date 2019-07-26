<!DOCTYPE HTML>
<%@page language="java" pageEncoding="UTF-8" %>
<%@page import="com.huigao.security.support.SecurityUserHolder" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>协同管理系统</title>

    <link rel="shortcut icon" href="favicon.ico"/>
    <link rel="Bookmark" href="favicon.ico"/>
    <link href="css/xietong_loading_main.css" rel="stylesheet" type="text/css">
    <link href="css/xietong_ext_main.css" rel="stylesheet" type="text/css">
    <link href="js/ext-2.0/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
    <link href="js/ext-2.0/resources/css/portal.css" rel="stylesheet" type="text/css"/>
    <link href="js/ext-2.0/custom/Datetime/datetime.css" rel="stylesheet" type="text/css"/>

    <!--导入prototype文件 -->
    <script language="javascript" type="text/javascript" src="js/prototype.js"></script>
    <script language="javascript" type="text/javascript" src="js/ext-2.0/adapter/ext/ext-base.js"></script>
    <script language="javascript" type="text/javascript" src="js/ext-2.0/ext-all.js"></script>
    <script language="javascript" type="text/javascript" src="js/RowExpander.js"></script>
    <script language="javascript" type="text/javascript" src="js/ext-2.0/source/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/localXHR.js"></script>
    <script language="javascript" type="text/javascript" src="js/Ext.ux/Ext.ux.panel.Portal.js"></script>
    <script language="javascript" type="text/javascript" src="js/Ext.ux/Ext.ux.tree.TreeCheckNodeUI.js"></script>

    <script language="javascript" type="text/javascript" src="module/common/daka.js"></script>
    <script language="javascript" type="text/javascript" src="module/common/main.js"></script>
</head>

<body>
    <!--loading加载 -->
    <div id="loadingTab">
        <div class="loading-indicator" style="margin-right: 0;padding-right: 0">
            <img src="images/public/loader.gif" width="20" height="20"
                 style="margin-right:8px;float:left;vertical-align:top;"/>
            <a href="main.jsp">协同管理系统</a>
        </div>
    </div>

    <div id="north" style="visibility:hidden;display: flex;justify-content: space-between">
            <span class="api-title" style="display: flex;align-items: center;">
                <img src="images/xietong.gif"/>
                <h2 style="margin-left: 5px">协同管理系统</h2>
            </span>
        <span style="display: flex;align-items: center">
                欢迎您:<%= SecurityUserHolder.getCurrentUser().getRealName() %>
                    &nbsp;&nbsp;
                <a href="${pageContext.request.contextPath}/j_spring_security_logout"
                   style="display: flex;align-items: center;">
                    <img src="images/lock.gif"/>
                    <h4>注销登录</h4>
                </a>
                &nbsp;
                <a href="${pageContext.request.contextPath}/module/help/index.html"
                   style="display: flex;align-items: center;">
                    <img src="images/help.png"/>
                    <h4>用户手册</h4>
                </a>
                &nbsp;&nbsp;
            </span>
    </div>
    <div id="south">
        <div class="power" id="power" style="visibility:hidden;">&nbsp;</div>
        <div class="bq" id="banquan" style="visibility:hidden;">
            <img src="images/spider.gif" style="margin-bottom:-5px;">
            黑塔工作室出品&nbsp;
            <a href="${pageContext.request.contextPath}/copyRight/copyRight.html">
                版权声明
            </a>&nbsp;
        </div>
    </div>

    <script type="text/javascript">
        Ext.get('loadingTab').fadeOut({remove: true});//让加载标签消失
        $('north').style.visibility = "visible";
        $('power').style.visibility = "visible";
        $('banquan').style.visibility = "visible"
    </script>

</body>
</html>
