package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.data.Login;

/**
 * 功能：商品添加到购物车
 */
public class PutGoodsToCar extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        doPost(request,response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		String goods=request.getParameter("java");//获取添加购物车时隐藏的数据
		System.out.println(goods);
		Login loginBean=null;
		HttpSession session=request.getSession(true);
		try{
			loginBean=(Login) session.getAttribute("loginBean");
			boolean b=loginBean.getLogname()==null||loginBean.getLogname().length()==0;
			if(b)
				response.sendRedirect("login.jsp");
			LinkedList<String> car=loginBean.getCar();//获取用户购物车双线列表
			car.add(goods);//购物车添加商品各种数据
			speakSomeMess(request,response,goods);
		}catch(Exception e){}
	}

	private void speakSomeMess(HttpServletRequest request,
			HttpServletResponse response, String goods) {
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out=response.getWriter();
			out.print("<html><head>" +
					"<div align='center'>" +
					"<font color=red><h3>'青山绿水'化妆品销售网</h3></font> "+
                    "<table cellSpacing='1' cellPadding='1' width='660' align='center' border='0'>"+
                    "<tr align='bottom'>"+  
                    "<td><a href='index.jsp'>主页</a></td>"+
                    "<td><a href='lookCosmetic.jsp'>浏览化妆品</a></td>"+
                    "<td><a href='searchCosmetic.jsp'>查询化妆品</a></td>"+
                    "<td><a href='lookShoppingCar.jsp'>查看购物车</a></td>"+
                    "<td><a href='lookOrderForm.jsp'>查看订单</a></td>"+
                    "<td><a href='login.jsp'>登录</a></td>"+
                    "<td><a href='inputRegisterMess.jsp'>注册</a></td>"+
                    "<td><a href='exitServlet'>退出</a></td>"+
                    "</tr></table></div><br><hr><br></head>");
			out.println("<body background='image/back.jpg' style='color:white'><center>");
			out.println("<h2>商品已放入购物车</h2>");
			out.println("查看购物车或返回浏览化妆品<br><br>");
			out.println("<a href='lookShoppingCar.jsp'>查看购物车</a>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='byPageShow.jsp'>浏览化妆品</a>");
			out.println("</center></body></html>");
		}catch(Exception e){}
	}
}
