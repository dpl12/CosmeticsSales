package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;

import mybean.data.DataByPage;
/**
 * 功能：浏览商品记录
 */
public class QueryAllRecord extends HttpServlet {
    CachedRowSetImpl rowSet=null;   //创建行集对象
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
        String idNumber=request.getParameter("fenleiNumber");
        if(idNumber==null)
        	idNumber="0";
        int id=Integer.parseInt(idNumber);
        HttpSession session=request.getSession();
        Connection con=null;
        DataByPage dataBean=null;
        try{
        	dataBean=(DataByPage) session.getAttribute("dataBean");
        	if(dataBean==null){
        		dataBean=new DataByPage();//创建JavaBean对象
        		session.setAttribute("dataBean", dataBean);
        	}
        }catch(Exception e){
        	dataBean=new DataByPage();//创建JavaBean对象
    		session.setAttribute("dataBean", dataBean);
        }
        String uri="jdbc:mysql://127.0.0.1/shop";
        try{
        	con=DriverManager.getConnection(uri, "root", "dpl1215");
        	Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        	//TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。
        	//ResultSet.CONCUR_READ_ONLY 不能用结果集更新数据库中的表。
        	ResultSet rs=sql.executeQuery("select * from cosmeticForm where id="+id);
        	rowSet=new CachedRowSetImpl();
        	rowSet.populate(rs);//填充结果集（CachedRowSetImpl代替ResultSet）
        	dataBean.setRowSet(rowSet);//行集数据存储在dataBean中
        	con.close();//关闭连接
        }catch(Exception e){}
        response.sendRedirect("byPageShow.jsp");//重定向byPageShow.jsp
	}
}
