package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.data.Login;
/**
 *功能：购物车商品删除
 */
public class HandleDelete extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
		String delete =request.getParameter("delete");
		Login loginBean=null;
		HttpSession session=request.getSession(true);
		try{
			loginBean=(Login) session.getAttribute("loginBean");
			boolean b=loginBean.getLogname()==null||loginBean.getLogname().length()==0;
			if(b)
				response.sendRedirect("login.jsp");
			LinkedList<String> car=loginBean.getCar();
			car.remove(delete);//删除商品
		}catch(Exception e){
			response.sendRedirect("login.jsp");
		}
		RequestDispatcher dispatcher=request.getRequestDispatcher("lookShoppingCar.jsp");
		dispatcher.forward(request, response);
	}

}
