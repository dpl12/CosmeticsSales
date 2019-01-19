package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.data.Login;

/**
 * 功能：登录功能
 */
public class HandleLogin extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){}
	}
	
	/**
	 * 汉字乱码处理
	 */
	public String handleString(String s){
		try{
			byte[] bb=s.getBytes("iso-8859-1");
			s=new String(bb,"UTF-8");
		}catch(Exception e){}
		return s;
	}
	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        Connection con;
        Statement sql;
        String logname=request.getParameter("logname").trim();
        String password=request.getParameter("password").trim();
		logname=handleString(logname);
		password=handleString(password);
		String uri="jdbc:mysql://127.0.0.1/shop?"+"user=root&password=dpl1215&characterEncoding=utf-8";
		boolean boo=(logname.length()>0)&&(password.length()>0);
		try{
			con=DriverManager.getConnection(uri);
			String condition="select * from user where logname='"+logname+"'and password='"+password+"'";
			sql=con.createStatement();
			if(boo){
		       ResultSet rs=sql.executeQuery(condition);
		       boolean m=rs.next();
		       if(m){
		    	   //调用登陆成功的方法
		    	   success(request,response,logname,password);
		    	   RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
		    	   dispatcher.forward(request, response);
		       }else{
		    	   String backNews="您输入的账号不存在，或密码错误";
		    	   fail(request,response,logname,backNews);
		       }
			}else{
				String backNews="请输入账号和密码";
				fail(request,response,logname,backNews);
			}
			con.close();
		}catch(Exception e){
			String backNews=""+e;
			fail(request,response,logname,backNews);
		}
	}

	private void fail(HttpServletRequest request, HttpServletResponse response,
			String logname, String backNews) {
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out=response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+logname+"登陆反馈结果<br>"+backNews+"</h2>");
			out.println("返回登录界面或主页<br>");
			out.println("<a href=login.jsp>登录界面</a>");
			out.println("<br><a href=index.jsp>主页</a>");
			out.println("</body></html>");
		}catch(Exception e){}
	}

	private void success(HttpServletRequest request,
			HttpServletResponse response, String logname, String password) {
		Login loginBean=null;
		HttpSession session=request.getSession();
		try{
			loginBean=(Login) session.getAttribute("loginBean");
			if(loginBean==null){
				loginBean=new Login();
				session.setAttribute("loginBean", loginBean);
				loginBean=(Login) session.getAttribute("loginBean");
			}
			String name=loginBean.getLogname();
			if(name.equals(logname)){
				loginBean.setBackNews(logname+"已经登陆了");
				loginBean.setLogname(logname);
			}else{
				loginBean.setBackNews(logname+"登陆成功");
				loginBean.setLogname(logname);
			}
		}catch(Exception e){
			loginBean=new Login();
			session.setAttribute("loginBean", loginBean);
			loginBean.setBackNews(logname+"登陆成功");
			loginBean.setLogname(logname);
		}
	}
}
