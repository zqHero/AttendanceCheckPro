<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.springframework.security.context.SecurityContextHolder" %>
<%@ page import="com.huigao.pojo.Users" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>协同管理系统登录</title>
    <link rel="shortcut icon" href="favicon.ico"/>
    <link rel="Bookmark" href="favicon.ico"/>
    <LINK href="css/global.css" type=text/css rel=STYLESHEET>
    <style type="text/css">
        <!--
        .STYLE1 {
            color: #000000;
            font-weight: bold;
        }

        -->
        a:link {
            text-decoration: underline;
        }

        a:visited {
            text-decoration: underline;
        }

        a:hover {
            text-decoration: underline;
        }

        a:active {
            text-decoration: underline;
        }

        .form_input_div{
            display: flex;
            text-align: center;
            margin-top: 15px;
            margin-left: 50px;
            align-items: center;
        }

    </style>
    <script>
        function check() {
            var frm = document.form1;
            if (frm.j_username.value == "") {
                alert("用户名不能为空!");
                document.form1.j_username.focus();
                return false;
            } else if (frm.j_password.value == "") {
                alert("登录密码不能为空!");
                frm.j_password.focus();
                return false;
            } else {
                return true;
            }
        }
    </script>
</head>
<body bgColor=#ffffff onload="javascript:document.form1.j_username.focus();" style="padding-top: 80px">
<div style="text-align: center;display:flex;align-items: center;flex-direction: column">
    <div style="background-color: #d0d0d0;width: 800px;height:260px;padding: 6px;border-radius: 10px">
        <div style="border-radius: 8px;background-color: #FFFFFF;height:260px">
            <div style="font-size: 20px;font-family: '幼圆';padding: 15px 0 30px 0">
                <img src="images/xietong.gif"/>&nbsp;&nbsp;欢迎登录协同管理系统
            </div>

            <div style="display: flex;flex-direction:row;align-items: center">
                <div style="display: flex;flex-direction: column;width: 100%;">
                    <strong>登录</strong>
                    <form method="post" action="j_spring_security_check"
                          name="form1" onsubmit="return check()">
                        <div class="form_input_div">
                            <span class=content_black_bold>账号&nbsp;:&nbsp;</span>
                            <input class=form value="${SPRING_SECURITY_LAST_USERNAME}" size="25" name="j_username" id="j_username"/>
                            <span style="color:#FF0000;font-size: 11px">&nbsp;${param.error=='true'? '用户名或密码错误' : ''}</span>
                        </div>
                        <div class="form_input_div">
                            <span class=content_black_bold>密码&nbsp;:&nbsp;</span>
                            <input class=form value="${users.password}" type="password" name="j_password" id="j_password" size="25"/>
                        </div>
                        <div class="form_input_div" style="justify-content: space-between">
                            <span style="font-size: 12px;align-items: center;display: flex">
                                <input type="checkbox" name="_spring_security_remember_me">&nbsp;保存密码
                            </span>
                            <span style="padding-right: 20px;"><input type="submit" value="提  交"/></span>
                        </div>
                    </form>
                </div>
                <div style="height: 180px;width: 2px;background-color: #d0d0d0"></div>
                <div style="width: 100%;font-size: 11px;color: #666;line-height: 14px;font-family:Lucida Grande,Arial">
                    <embed width="330" height="152" src="images/banner.swf" menu="false"/><br/>
                    为了更合理的管理公司,让公司有良好的规章制度
                    <br/>
                    促进公司健康良好的发展.
                    <br/>
                </div>
            </div>
        </div>
    </div>

    <div style="font-size: 13px;margin-top: 5px">
        <img src="images/spider.gif" style="margin-bottom:-5px;">
        黑塔工作室出品&nbsp;&nbsp;&nbsp;
        <a href="<%=path %>/copyRight/copyRight.html">版权声明</a>
    </div>
    <h6 class=disclaimer face="Geneva, Verdana, Arial, Helvetica"
        color=#999999 size=1>©&nbsp;Copyright 2008-2009 </h6>
</div>
</body>
</html>
