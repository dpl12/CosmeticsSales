<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="head.jsp"/>
  </head>
  <jsp:useBean id="loginBean" class="mybean.data.Login" scope="session"/>
  <body background="image/back.jpg" style="color: white">
    <center>
      <%
        if(loginBean==null){
           response.sendRedirect("login.jsp");//重定向到登录页面
        }else{
           boolean b=loginBean.getLogname()==null||loginBean.getLogname().length()==0;
           if(b)
             response.sendRedirect("login.jsp");
        }
        String numberID=request.getParameter("xijie");
        out.print("<th>产品号"+numberID);
        if(numberID==null){
           out.print("没有产品号，无法查看细节!");
           return;
        }
        Connection con;
        Statement sql;
        ResultSet rs;
        try{
           Class.forName("com.mysql.jdbc.Driver");
        }catch(Exception e){}
        String uri="jdbc:mysql://127.0.0.1/shop";
        try{
           con=DriverManager.getConnection(uri, "root", "dpl1215");
           sql=con.createStatement();
           rs=sql.executeQuery("select * from cosmeticForm where cosmetic_number='"+numberID+"'");
           out.print("<table border=2>");
           out.print("<tr>");
           out.print("<th>产品号");
           out.print("<th>名称");
           out.print("<th>制造商");
           out.print("<th>价格");
           out.print("<th><font color=blue>放入购物车</font>");
           out.print("</tr>");
           String picture="welcome.jpg";
           String detailMess="";   //产品详情
           while(rs.next()){
              String number=rs.getString(1);
              String name=rs.getString(2);
              String maker=rs.getString(3);
              String price=rs.getString(4);
              detailMess=rs.getString(5);
              picture=rs.getString(6);
              //便于购物车计算价格，尾缀上"#价格值"
              String goods="("+number+","+name+","+maker+","+price+")#"+price;
              goods=goods.replaceAll("\\p{Blank}", "");//用""代替空格或制表符(\\p{Blank})
              String button="<form action='putGoodsServlet' method='post'>"+
                            "<input type='hidden' name='java' value= "+goods+">"+
                            "<input type='submit' value='加入购物车'></form>";
              out.print("<tr>");
              out.print("<td>"+number+"</td>");
              out.print("<td>"+name+"</td>");
              out.print("<td>"+maker+"</td>");
              out.print("<td>"+price+"</td>");
              out.print("<td>"+button+"</td>");
              out.print("</tr>");              
           }
           out.print("</table>");
           out.print("<br>产品详情：<br>");
           out.println("<div align=center>"+detailMess+"</div><br>");
           String pic="<img src='image/"+picture+"'width=260 height=200></img>";
           out.print(pic);//产品图片
           con.close();
        }catch(Exception e){}
       %>
    </center>
  </body>
</html>
