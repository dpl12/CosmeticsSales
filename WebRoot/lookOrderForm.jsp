<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="head.jsp"/>
  </head>
  <jsp:useBean id="loginBean" class="mybean.data.Login" scope="session"/>
  <body background="image/back.jpg" style="color:white">
    <div align="center">
      <%
        if(loginBean==null){
          response.sendRedirect("login.jsp");//没登录时跳转到登录页面
        }else{
          boolean b=loginBean.getLogname()==null||loginBean.getLogname().length()==0;
          if(b)
            response.sendRedirect("login.jsp"); 
        }
        Connection con;
        Statement sql;
        ResultSet rs;
        try{
           Class.forName("com.mysql.jdbc.Driver");
        }catch(Exception e){}
        try{
           String uri="jdbc:mysql://127.0.0.1/shop";
           String user="root";
           String password="dpl1215";
           con=DriverManager.getConnection(uri, user, password);
           sql=con.createStatement();
           String cdn="select id,mess,sum from orderForm where logname='"+loginBean.getLogname()+"'";
           rs=sql.executeQuery(cdn);
           out.print("<table border=2>");
           out.print("<tr>");
           out.print("<th width=100>"+"订单号");
           out.print("<th width=100>"+"信息");
           out.print("<th width=100>"+"价格");
           out.print("</tr>");
           while(rs.next()){
              out.print("<tr>");
              out.print("<td>"+rs.getString(1)+"</td>");
              out.print("<td>"+rs.getString(2)+"</td>");
              out.print("<td>"+rs.getString(3)+"</td>");
              out.print("<tr>");
           }
           out.print("</table>");
           con.close();
        }catch(Exception e){
           out.print(e);
        }
       %>
    </div>
  </body>
</html>
