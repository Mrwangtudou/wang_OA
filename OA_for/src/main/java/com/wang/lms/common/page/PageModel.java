package com.wang.lms.common.page;


/**
 * @Version 1.0
 * @Time 2016年3月10日/上午11:25:56
 * @From www.fkjava.org
 *  分页实体 
 */
public class PageModel {
	
	
	/** 分页总数据条数  */
	private int recordCount;
	/** 当前页面 */
	private int pageIndex=1;
	/** 每页分多少条数据   */
	private int pageSize = 4;
	

	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public int getPageIndex() {		
		
		//当前页码不能大于总页码
		
		//计算总页数
		int totalPageSize = recordCount % pageSize==0 ? recordCount / pageSize : (recordCount / pageSize)+1;
		
		return pageIndex > totalPageSize ?totalPageSize:pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {

		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	//查询数据的起始行   
	public int getFirstLimitParam(){
		return (this.getPageIndex()-1)*this.getPageSize() ;
	}
	
}
