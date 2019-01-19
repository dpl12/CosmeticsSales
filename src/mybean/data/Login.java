package mybean.data;

import java.util.LinkedList;
//存储用户登录的信息
public class Login {
   private String logname="";
   private String backNews="未登录";
   private LinkedList<String> car;//用户的购物车
   public Login(){
	   car=new LinkedList<String>();
   }
   public String getLogname() {
		return logname;
   }
   public void setLogname(String logname) {
		this.logname = logname;
   }
   public String getBackNews() {
		return backNews;
   }
   public void setBackNews(String backNews) {
		this.backNews = backNews;
   }
   public LinkedList<String> getCar() {
		return car;
   } 
}
