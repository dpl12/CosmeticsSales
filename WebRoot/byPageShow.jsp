<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="mybean.data.DataByPage" %>
<%@ page import="com.sun.rowset.*" %>

<!-- 分页显示商品 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="head.jsp"/>
  </head>
  <jsp:useBean id="dataBean" class="mybean.data.DataByPage" scope="session"/>
  <body background="image/back.jpg" style="color: white">
     <font size="3">
     <center>
     <br>当前显示的内容是：
     <table border="2">
      <tr>
        <th>化妆品标识号</th>
        <th>化妆品名称</th>
        <th>化妆品制造商</th>
        <th>化妆品价格</th>
        <th>查看详情</th>
        <td><font color=blue>添加到购物车</font></td>
      </tr>
      <jsp:setProperty property="pageSize" name="dataBean" param="pageSize"/>
      <jsp:setProperty property="currentPage" name="dataBean" param="currentPage"/>
      <%
        CachedRowSetImpl rowSet=dataBean.getRowSet();
        if(rowSet==null){
          out.print("没有任何查询信息，无法浏览");
          return;
        }
        rowSet.last();  //最后一行
        int totalRecord=rowSet.getRow();//获取总记录数
        out.println("全部记录数"+totalRecord);
        int pageSize=dataBean.getPageSize();//每页显示的记录数
        int totalPages=dataBean.getTotalPages();//获取分页后的总页数
        if(totalRecord%pageSize==0){
           totalPages=totalRecord/pageSize;//总页数
        }else{
           totalPages=totalRecord/pageSize+1;
        }
        dataBean.setPageSize(pageSize);
        dataBean.setTotalPages(totalPages);
        if(totalPages>=1){
           if(dataBean.getCurrentPage()<1)
              dataBean.setCurrentPage(dataBean.getTotalPages());
           if(dataBean.getCurrentPage()>dataBean.getTotalPages())
              dataBean.setCurrentPage(1);
           int index=(dataBean.getCurrentPage()-1)*pageSize+1;
           rowSet.absolute(index);//查询位置移动到currentPage页起始位置
           boolean boo=true;
           for(int i=1;i<=pageSize&&boo;i++){
              String number=rowSet.getString(1);
              String name=rowSet.getString(2);
              String maker=rowSet.getString(3);
              String price=rowSet.getString(4);
              String goods="("+number+","+name+","+maker+","+price+")#"+price;//便于购物车计算价格，尾缀上“#”价格值
              goods=goods.replaceAll("\\p{Blank}","");
              String button="<form action='putGoodsServlet' method='post'>"+
                            "<input type='hidden' name='java' value= "+goods+">"+    //隐藏hidden，提交时直接将其value的值提交
                            "<input type='submit' value='放入购物车'></form>";
              String detail="<form action='showDetail.jsp' method='post'>"+
                            "<input type='hidden' name='xijie' value= "+number+">"+
                            "<input type='submit' value='查看细节'></form>" ;
              out.print("<tr>");
              out.print("<td>"+number+"</td>");
              out.print("<td>"+name+"</td>");
              out.print("<td>"+maker+"</td>");
              out.print("<td>"+price+"</td>");
              out.print("<td>"+detail+"</td>");
              out.print("<td>"+button+"</td>");
              out.print("</tr>");
              boo=rowSet.next();
           }   
        }
       %>
     </table>
     <br>每页最多显示<jsp:getProperty property="pageSize" name="dataBean"/>条信息
     <br>当前显示第<font color=blue>
       <jsp:getProperty property="currentPage" name="dataBean"/>
     </font>页，共有
     <font color=blue>
       <jsp:getProperty property="totalPages" name="dataBean"/>
     </font>页。
     <table>
       <tr>
         <td>
           <form action="" method="post">
             <input type="hidden" name="currentPage" value="<%=dataBean.getCurrentPage()-1%>">
             <input type="submit" name="g" value="上一页">
           </form>
         </td>
         <td>
           <form action="" method="post">
             <input type="hidden" name="currentPage" value="<%=dataBean.getCurrentPage()+1%>">
             <input type="submit" name="g" value="下一页">
           </form>
         </td>
       </tr>
       <tr>
         <td>
           <form action="" method="post">
                                 每页显示<input type="text" name="pageSize" value=1 size=3>
                                 条记录<input type="submit" name="g" value="确定">                      
           </form>
         </td>
         <td>
           <form action="" method="post">
                                 输入页码：<input type="text" name="currentPage" size=2>
           <input type="submit" name="g" value="提交">                      
           </form>
         </td>
       </tr>
     </table>
     </center>
     </font>
  </body>
</html>
