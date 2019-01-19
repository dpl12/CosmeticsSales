package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybean.data.Register;
/**
 *功能：接收inputRegisterMess.jsp提交的信息，注册用户
 */
public class HandleRegister extends HttpServlet {


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");//加载mysql的jdbc驱动
		}catch(Exception e){
			e.printStackTrace();
		}
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
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        String uri="jdbc:mysql://127.0.0.1/shop?"+"user=root&password=dpl1215&characterEncoding=utf-8";
        Connection con;
        PreparedStatement sql;
		Register userBean=new Register();
		request.setAttribute("userBean", userBean);
		String logname=request.getParameter("logname").trim();
		String password=request.getParameter("password").trim();
		String again_password=request.getParameter("again_password").trim();
		String phone=request.getParameter("phone").trim();
		String address=request.getParameter("address").trim();
		String realname=request.getParameter("realname").trim();
		if(logname==null)
			logname="";
		if(password==null)
			password="";
		if(!password.equals(again_password)){
			userBean.setBackNews("两次密码不同，注册失败！");
			RequestDispatcher dispatcher=request.getRequestDispatcher("inputRegisterMess.jsp");
			dispatcher.forward(request, response);
			return;
		}
		boolean isLD=true;
		for(int i=0;i<logname.length();i++){//用户名字符或数字的判定
			char c=logname.charAt(i);
			if(!((c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0')))
				isLD=false;
		}
		boolean boo=logname.length()>0&&password.length()>0&&isLD;
		String backNews="";//返回是否成功的消息
		try{
			con=DriverManager.getConnection(uri);
			String insertCondition="insert into user values(?,?,?,?,?)";
			sql=con.prepareStatement(insertCondition);
			if(boo){//判定输入的数据是否为空或者格式是否正确,然后存到数据库中
				sql.setString(1, handleString(logname));
				sql.setString(2,handleString(password));
				sql.setString(3, handleString(phone));
				sql.setString(4, handleString(address));
				sql.setString(5, handleString(realname));
				int m=sql.executeUpdate();
				if(m!=0){//更新成功之后，将数据存到userBean中
					backNews="注册成功,请登录！";
					userBean.setBackNews(backNews);
					userBean.setLogname(handleString(logname));
					userBean.setPhone(handleString(phone));
					userBean.setAddress(handleString(address));
					userBean.setRealname(handleString(realname));
				}
			}
			else{
				backNews="您输入的信息不完整或用户名中有非法字符";
				userBean.setBackNews(backNews);
			}
			con.close();
		}catch(Exception e){
			backNews="该会员名已被使用，请您更换名字"+e;
			userBean.setBackNews(backNews);
		}
		
		//注册数据的页面显示
		RequestDispatcher dispatcher=request.getRequestDispatcher("inputRegisterMess.jsp");
		dispatcher.forward(request, response);
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


}
