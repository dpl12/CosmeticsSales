package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.data.Login;
/**
 * 功能：生成订单
 */
public class HandleBuyGoods extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){}
	}
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String buyGoodsMess=request.getParameter("buy");//获取商品订单数据
        if(buyGoodsMess==null||buyGoodsMess.length()==0){
        	fail(request,response,"购物车没有商品，无法生成订单");
        	return;
        }
        String price=request.getParameter("price");
        if(price==null||price.length()==0){
        	fail(request,response,"没有计算价格和，无法生成订单");
        	return;
        }
        float sum=Float.parseFloat(price);
        Login loginBean=null;
        HttpSession session=request.getSession(true);
        try{
        	loginBean=(Login) session.getAttribute("loginBean");
        	boolean b=loginBean.getLogname()==null||loginBean.getLogname().length()==0;
        	if(b)
        		response.sendRedirect("login.jsp");//重定向到login.jsp        	
        }catch(Exception e){
        	response.sendRedirect("login.jsp");
        }
        String uri="jdbc:mysql://127.0.0.1/shop?"+"user=root&password=dpl1215&characterEncoding=utf-8";
        Connection con;
        PreparedStatement sql;
        try{
        	con=DriverManager.getConnection(uri);
        	sql=con.prepareStatement("insert into orderform values(?,?,?,?)");
        	sql.setInt(1, 0);//订单序号会自定增长
        	sql.setString(2, loginBean.getLogname());
        	sql.setString(3, buyGoodsMess);
        	sql.setFloat(4, sum);
        	sql.executeUpdate();
        	LinkedList<String> car=loginBean.getCar();
        	car.clear();//清空购物车
        	success(request,response,"生成订单成功");
        	
        }catch(Exception e){
        	fail(request, response, "生成订单失败"+e);
        }
	}
	private void success(HttpServletRequest request,
			HttpServletResponse response,String backNews) {
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out =response.getWriter();
			out.println("<html><body background='image/back.jpg' style='color:white'>");
			out.println("<h2>"+backNews+"</h2>");
			out.println("返回主页");
			out.println("<a href='index.jsp'>主页</a>");
			out.println("<br>查看订单");
			out.println("<a href='lookOrderForm.jsp'>查看订单</a>");
			out.println("</body></html>");
		}catch(Exception e){}
	}
	private void fail(HttpServletRequest request, HttpServletResponse response,
			String backNews) {
		response.setCharacterEncoding("utf-8");
		try{
			PrintWriter out=response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+backNews+"</h2>");
			out.println("返回主页：");
			out.println("<a href='index.jsp'>主页</a>");
			out.println("</body></html>");
		}catch(Exception e){}
	}

}
