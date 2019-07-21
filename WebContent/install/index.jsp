<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>协同管理系统安装</title>

    <style type="text/css">
        .item_list_div{
            width: 100%;
            display: flex;
            font-size: 14px;
            text-align: center;
            align-items: center;
            margin-top: 10px;
            margin-left: 80px;
        }
    </style>
</head>

<body>
<form action="install/setup.jsp" method="post" >
    <table width="650" align="center" border="0" style="border:#106DBA 1px solid; margin-top:30px;font-size: 15px;">
        <caption>
            <h4>协同管理系统安装</h4>
        </caption>
        <tr style="background-color: #D1E9FA;text-align: center;font-size: 15px">
            <td width="73%" height="30" class="f14b">
                系统参数设置
                <span style="color:#FF0000">
                    （环境要求：jdk1.5或以上、tomcat5.5或以上、mysql5.0或以上）
                </span>
            </td>
        </tr>
        <tr>
            <td height="300" align="center" bgcolor="#F0F8FD">
                <div class="item_list_div">
                    <span height="30" align="right">数据库名称: </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="dbName" type="text" class="input" id="dbName" value="xietong_db"/>
                </div>
                <div class="item_list_div">
                    <span height="30" align="right">数据库用户: </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="dbUser" type="text" class="input" id="dbUser" value="root"/>
                </div>
                <div class="item_list_div">
                    <span height="30" align="right">数据库密码: </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="dbPassword" type="text" class="input" id="root"/>
                    &nbsp;<span align="left">(mysql数据密码)</span>
                </div>
                <div class="item_list_div">
                    <span>数据库主机名: </span> &nbsp;&nbsp;
                    <input name="dbHost" type="text" class="input" id="dbHost" value="<%=request.getServerName()%>"/>
                    &nbsp;<span>(数据库所在服务器的IP地址)</span>
                </div>
                <div class="item_list_div">
                    <span>数据库端口号: </span> &nbsp;&nbsp;
                    <input name="dbPort" type="text" class="input" id="dbPort" value="3306"/>
                    &nbsp;<span>(数据库的端口号)</span>
                </div>
                <div class="item_list_div">
                    <span height="30" align="right">服务器域名: </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input name="domain" type="text" class="input" value="<%=request.getServerName()%>"/>
                    &nbsp;<span align="left">(系统已经检测出您的域名，请勿改动)</span>
                </div>
                <div class="item_list_div">
                    <span height="30" align="right">项目部署路径: </span>&nbsp;&nbsp;
                    <input name="cxtPath" type="text" class="input" value="<%=request.getContextPath()%>"/>
                    &nbsp;<span align="left">(系统已经检测出您的部署路径，请勿改动)</span>
                </div>
                <div class="item_list_div">
                    <span height="30" align="right">服务器端口号: </span>&nbsp;&nbsp;
                    <input name="port" type="text" class="input" value="<%=request.getServerPort()%>"/>
                    &nbsp;<span align="left">(系统已经检测出您的端口号，请勿改动)</span>
                </div>
            </td>
        </tr>

        <tr>
            <td height="30" align="center" bgcolor="#D1E9FA">
			<span id="beforeSubmit"><input type="submit" class="btn" value=" 提 交 "/>
			</span>
                <span id="afterSubmit" style="display:none;color:red;">安装需要十几秒的时间，请您耐心等待...</span>
            </td>
        </tr>
    </table>

    <div style="align-items: center;display: flex;justify-content: center;margin-top: 5px">
        <img src="images/spider.gif"/>&nbsp;
        <span style="font-size:12px;">黑塔工作室出品</span>&nbsp; &nbsp;
        <a href="<%=path %>/copyRight/copyRight.html" style="font-size:12px;">
            版权声明
        </a>
    </div>
</form>
</body>
</html>
