<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-- 查询化妆品页面 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="head.jsp"/>
  </head>
  
  <body background="image/back.jpg" style="color: white">
    <font size="3">
    <div align="center">
    <br>查询时可以输入化妆品的版本号或化妆品名称及价格。<br>
              化妆品名称支持模糊查询。
    <br>输入价格是在2个值之间的价格，格式是：价格1-价格2<br>
             例如258-689
    <form action="searchByConditionServlet" method="post">
       <br>输入查询信息：<input type="text" name="searchMess"><br>
       <input type="radio" name="radio" value="cosmetic_number">化妆品版本号
       <input type="radio" name="radio" value="cosmetic_name" checked="ok">化妆品名称
       <input type="radio" name="radio" value="cosmetic_price">化妆品价格
       <br><input type="submit" name="g" value="提交">
    </form>                   
    </div>
    </font>
  </body>
</html>
