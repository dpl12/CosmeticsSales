package mybean.data;

import com.sun.rowset.CachedRowSetImpl;
/**
 * 用于存储商品的数据库记录
 */
public class DataByPage {
    CachedRowSetImpl rowSet=null;   //存储表中全部记录的行集对象
    private int pageSize=1;         //每页显示的记录数
    private int totalPages=1;       //分页后的总页数
    private int currentPage=1;      //当前显示页
    public void setRowSet(CachedRowSetImpl set){
    	rowSet=set;
    }
    public CachedRowSetImpl getRowSet(){
    	return rowSet;
    }
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
    
}
