<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:useBean id="loginBean" class="mybean.data.Login" scope="session"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="head.jsp"/>
  </head> 
  <body background="image/login_back.jpg">
    <font size=3>
    <div align="center">
    <table border="1">
    <tr><th>登录</th></tr>
    <form action="loginServlet" method="post">
    <tr><td>账号：<input type="text" name="logname"/></td></tr>
    <tr><td>密码：<input type="password" name="password"/></td></tr>
    <tr><td align="center"><input type="submit" name="g" value="提交" style="width: 60px"/></td></tr>
    </form>
    </table>
    </div>
    <div align="center">
             登录信息反馈：<jsp:getProperty property="backNews" name="loginBean"/>
    <br>账号：<jsp:getProperty property="logname" name="loginBean"/>         
    </div>
    </font>
  </body>
</html>
