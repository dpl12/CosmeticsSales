<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!-- 功能：负责提交用户注册信息到HandleRegister 的servlet控制器,并负责显示注册是否成功 -->
<jsp:useBean id="userBean" class="mybean.data.Register" scope="request"/>
<head><jsp:include page="head.jsp"/></head>

<title>注册页面</title>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <body background="image/back.jpg" style="color:white"><font size="3">
  <div align="center">
  <form action="registerServlet" method="post" name=form>
  <table>
       用户名由字母、数字、下划线组成，*注释的选项必须填写。
  <br>
  <tr>
  <td>*用户名称：</td><td><input type="text" name="logname"/></td>
  <td>*用户密码：</td><td><input type="password" name="password"/></td>
  </tr>
  <tr>
  <td>*重复密码：</td><td><input type="password" name="again_password"/></td>
  <td>联系电话：</td><td><input type="text" name="phone"/></td>
  </tr>
  <tr>
  <td>邮寄地址：</td><td><input type="text" name="address"/></td>
  <td>真实姓名：</td><td><input type="text" name="realname"/></td>
  <td><input type="submit" name="g" value="提交"/></td>
  </tr>
  </table>
  </form>
  </div>
  <div align="center">
  <p>注册反馈：
  <jsp:getProperty property="backNews" name="userBean"/>
  <table border="3">
  <tr><td>会员名称：</td>
  <td><jsp:getProperty property="logname" name="userBean"/></td>
  </tr>
  <tr><td>姓名：</td>
  <td><jsp:getProperty property="realname" name="userBean"/></td>
  </tr>
  <tr><td>地址：</td>
  <td><jsp:getProperty property="address" name="userBean"/></td>
  </tr>
  <tr><td>电话：</td>
  <td><jsp:getProperty property="phone" name="userBean"/></td>
  </tr>
  </table>
  </div>
  </font>    
  </body>
</html>
