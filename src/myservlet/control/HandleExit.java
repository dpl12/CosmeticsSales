package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 功能：退出登录
 */
public class HandleExit extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        HttpSession session=request.getSession(true);
        session.invalidate();//销毁用户的session对象
        response.sendRedirect("index.jsp");
	}
}
