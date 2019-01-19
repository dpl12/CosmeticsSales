<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!-- 用于选择某个商品分类 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="head.jsp"/>
  </head>
  
  <body background="image/back.jpg">
    <font size="3">
    <div align="center">
    <%
     try{
       Class.forName("com.mysql.jdbc.Driver");
     }catch(Exception e){}
     String uri="jdbc:mysql://127.0.0.1/shop?"+"user=root&password=dpl1215&characterEncoding=utf-8";
     Connection con;
     Statement sql;
     ResultSet rs;
     try{
       con=DriverManager.getConnection(uri);
       sql=con.createStatement();
       rs=sql.executeQuery("select * from classify");//获取分类
       out.print("<form action='queryServlet' method='post'>");
       out.print("<select name='fenleiNumber'>");
       while(rs.next()){
          int id=rs.getInt(1);
          String name=rs.getString(2);
          out.print("<option value="+id+">"+name+"</option>");
       }
       out.print("</select><input type='submit' value='提交'></form>");
       con.close();
     }catch(Exception e){
       out.print(e);
     }
     %>
    </div>
    </font>
  </body>
</html>
