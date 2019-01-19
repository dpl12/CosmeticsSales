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

import mybean.data.DataByPage;

import sun.print.resources.serviceui;

import com.sun.rowset.CachedRowSetImpl;
/**
 * 功能：查询化妆品 
 */
public class SearchByCondition extends HttpServlet {
    
	CachedRowSetImpl rowSet=null;
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){}
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
        request.setCharacterEncoding("utf-8");
        String searchMess=request.getParameter("searchMess");
        String radioMess=request.getParameter("radio");
        if(searchMess==null||searchMess.length()==0){
        	fail(request,response,"没有查询信息，无法查询");
        	return;
        }
        String condition="";
        if(radioMess.equals("cosmetic_number")){
        	condition="select * from cosmeticForm where cosmetic_number='"+searchMess+"'";
        }else if(radioMess.equals("cosmetic_name")){
        	condition="select * from cosmeticForm where cosmetic_name like '%"+searchMess+"%'";
        }else if(radioMess.equals("cosmetic_price")){
        	double max=0,min=0;
        	String regex="[^0123456789.]";
        	String[] priceMess=searchMess.split(regex);
        	if(priceMess.length==1){
        		max=min=Double.parseDouble(priceMess[0]);
        	}else if(priceMess.length==2){
        		min=Double.parseDouble(priceMess[0]);
        		max=Double.parseDouble(priceMess[1]);
        		if(max<min){
        			double t=max;
        			max=min;
        			min=t;
        		}
        	}else{
        		fail(request,response,"输入的价格格式有错误");
        		return;
        	}
        	condition="select * from cosmeticForm where"+"cosmetic_price<="+max+"and cosmetic_price>="+min;
        }
        HttpSession session=request.getSession(true);
        Connection con=null;
        DataByPage dataBean=null;
        try{
        	dataBean=(DataByPage) session.getAttribute("dataBean");
        	if(dataBean==null){
        		dataBean=new DataByPage();
        		session.setAttribute("dataBean", dataBean);
        	}
        }catch(Exception e){
        	dataBean=new DataByPage();
        	session.setAttribute("dataBean", dataBean);
        }
        String uri="jdbc:mysql://127.0.0.1/shop?"+"user=root&password=dpl1215&characterEncoding=utf-8";
        try{
        	con=DriverManager.getConnection(uri);
        	Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        	ResultSet rs=sql.executeQuery(condition);
        	rowSet=new CachedRowSetImpl();//创建行集对象
        	rowSet.populate(rs);//填充结果集
        	dataBean.setRowSet(rowSet);//行集数据存储在dataBean中
        	con.close();
        }catch(Exception e){}
        response.sendRedirect("byPageShow.jsp");
	}

	private void fail(HttpServletRequest request, HttpServletResponse response,
			String backNews) {
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out=response.getWriter();
			out.println("<html><body background='image/back.jpg' style='color:white'>");
			out.println("<h2>"+backNews+"</h2>");
			out.println("返回：");
			out.println("<a href='searchCosmetic.jsp'>查询化妆品</a>");
			out.println("</body></html>");
		}catch(Exception e){}
	}
}
