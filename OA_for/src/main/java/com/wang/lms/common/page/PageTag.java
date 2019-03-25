package com.wang.lms.common.page;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author CHUNLONG.LUO
 * @email 584614151@qq.com
 * @date 2018年1月20日
 * @version 1.0
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * 
 * PageTag：标签处理类
 * 标签处理类的作用：通过该类画分页标签
 */
public class PageTag  extends TagSupport{
	
	private Integer pageIndex;//当前页码
	private Integer totalSize;//总记录数
	private Integer pageSize;//每页显示的记录数
	private String submitUrl;//提交地址    index.action?pageIndex={0}
	private String pageStyle = "yellow";
	
	private final static String TAG = "{0}";

	
	/*
	//页面解析 </fk:pager> 结束标签时  会触发   doEndTag方法
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		System.out.println("==========执行doEndTag方法=========");
		return super.doEndTag();
	}*/

	//页面解析 <fk:pager>开始标签时  会触发   doStartTag方法
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		System.out.println("==========执行doStartTag方法=========");
		try {

			//JspWriter：通过JspWriter可以往jsp中写出数据
			JspWriter out = this.pageContext.getOut();
			
			//该StringBuffer用于拼装 table tr 以及分页的详细信息
			StringBuffer  pager = new StringBuffer();
			
			
			if(this.getTotalSize()>0){
				//存在记录,开始画分页标签
				//拼装分页的详细信息
				StringBuffer  str = new StringBuffer();
				
				//获取总页码   
				int totalPageNum = this.totalSize % this.pageSize == 0 ? this.totalSize / this.pageSize : (this.totalSize / this.pageSize) +1; 

				String jumpUrl = "";
				//当前页码是第一页
				if(this.pageIndex == 1){
					
					str.append("<span class='disabled'>上一页</span>");
					
					
					//处理中间页码
					calcMiddle(str,totalPageNum);
					
					//总页码等于1
					if(totalPageNum == 1){
						//总页码等于1说明只有一页，下一页也不能点击
						str.append("<span class='disabled'>下一页</span>");

					}else{
						
						//submitUrl:index.action?pageIndex={0}
						jumpUrl = this.submitUrl.replace(TAG, String.valueOf(this.pageIndex + 1));
						str.append("<a href='"+jumpUrl+"'>下一页</a>");

					}
				//当前页码是尾页
				}else if(this.pageIndex == totalPageNum){
					
					jumpUrl = this.submitUrl.replace(TAG, String.valueOf(this.pageIndex - 1));
					str.append("<a href='"+jumpUrl+"'>上一页</a>");
					
					//处理中间页码
					calcMiddle(str,totalPageNum);

					
					str.append("<span class='disabled'>下一页</span>");
					
				//当前页码在中间，上一页  和  下一页都可以点击	
				}else{
					jumpUrl = this.submitUrl.replace(TAG, String.valueOf(this.pageIndex - 1));
					str.append("<a href='"+jumpUrl+"'>上一页</a>");
					
					
					//处理中间页码
					calcMiddle(str,totalPageNum);

					
					jumpUrl = this.submitUrl.replace(TAG, String.valueOf(this.pageIndex + 1));
					str.append("<a href='"+jumpUrl+"'>下一页</a>");
				}
				
				
				pager.append("<table align='center' class='"+this.pageStyle+"' style='width:100%;font-size:14px;'><tr><td>"+str.toString()+"&nbsp;&nbsp;跳转到<input type='text' size='2' id='jump_num'/><input type='button' value='跳转' id='jump_page'/></td></tr>");
				//每页开始的记录
				int startSize = (this.pageIndex - 1) * this.pageSize + 1;
				//每页结束的记录
				int endSize = this.pageIndex == totalPageNum?totalSize : this.pageIndex * this.pageSize;
				
				pager.append("<tr><td>总共<font color='red'>"+this.totalSize+"</font>条记录,当前显示"+startSize+"-"+endSize+"条记录</td></tr></table>");
		
			
				//为 ‘跳转’按钮绑定点击事件
				pager.append("<script type='text/javascript'>");
				
				pager.append("document.getElementById('jump_page').onclick = function(){");
				pager.append("var value = document.getElementById('jump_num').value;");
				pager.append("if(!/^[1-9]\\d*$/.test(value)||value > "+totalPageNum+"){");
				pager.append("alert('请输入[1-"+totalPageNum+"]之间的页码值！');");
				pager.append("}else{");
				// index.action?pageIndex = {0}
				pager.append("var submiturl = '"+this.submitUrl+"';");
				pager.append("submiturl = submiturl.replace('"+TAG+"',value);");
				pager.append("window.location = submiturl;");
				
				pager.append("}");
				
				pager.append("}");
				pager.append("</script>");
				
				
			
			}else{
				//不存在记录
				pager.append("<table align='center' class='"+this.pageStyle+"' style='width:100%;font-size:14px;'><tr><td>总共<font color='red'>0</font>条记录,当前显示0-0条记录</td></tr></table>");
			}
			
			
		
			
			out.write(pager.toString());
			out.flush();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		return super.doStartTag();
	}


    //计算中间页码
	public void calcMiddle(StringBuffer str, int totalPageNum) {
		// TODO Auto-generated method stub
		String jumpUrl = "";
		//如果中页码数小于等于 10 则全部显示   1 2 3 4 5 6 7 8 9 10
		if(totalPageNum <=10){
			for (int i = 1; i <= totalPageNum; i++) {
				jumpUrl = this.submitUrl.replace(TAG, String.valueOf(i));
				
				//当前页码不需要用a标签包住
				if(i == this.pageIndex){
					str.append("<span class='current'>"+i+"</span>");
				}else{
					str.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
			}
		//假设当前页码靠近首页  1 2 3 4 5 6 7 8 9 	... 100
		}else if(this.pageIndex <= 8){
			for (int i = 1; i <= 9; i++) {
                jumpUrl = this.submitUrl.replace(TAG, String.valueOf(i));
				
				//当前页码不需要用a标签包住
				if(i == this.pageIndex){
					str.append("<span class='current'>"+i+"</span>");
				}else{
					str.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
			}
			
			str.append("...");
			//拼装尾页
			jumpUrl = this.submitUrl.replace(TAG, String.valueOf(totalPageNum));
			str.append("<a href='"+jumpUrl+"'>"+totalPageNum+"</a>");
		//假设当前页码靠近尾页  1 ... 91 92 93 94 95 96 97 98 99 100
		}else if(this.pageIndex + 8 >= totalPageNum){
			jumpUrl = this.submitUrl.replace(TAG, String.valueOf(1));
			str.append("<a href='"+jumpUrl+"'>"+1+"</a>");
			str.append("...");
			
			
			for (int i = totalPageNum - 9; i <= totalPageNum; i++) {
               
				jumpUrl = this.submitUrl.replace(TAG, String.valueOf(i));
				//当前页码不需要用a标签包住
				if(i == this.pageIndex){
					str.append("<span class='current'>"+i+"</span>");
				}else{
					str.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
			}
		//假设当前页码靠近中间    1 ... 46 47 48 49 50 51 52 53 54 ... 100
		}else{
			jumpUrl = this.submitUrl.replace(TAG, String.valueOf(1));
			str.append("<a href='"+jumpUrl+"'>"+1+"</a>");
			str.append("...");
			
			
			for (int i = this.pageIndex - 4; i <= this.pageIndex + 4; i++) {
	               
				jumpUrl = this.submitUrl.replace(TAG, String.valueOf(i));
				//当前页码不需要用a标签包住
				if(i == this.pageIndex){
					str.append("<span class='current'>"+i+"</span>");
				}else{
					str.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
			}
			
			str.append("...");
			//拼装尾页
			jumpUrl = this.submitUrl.replace(TAG, String.valueOf(totalPageNum));
			str.append("<a href='"+jumpUrl+"'>"+totalPageNum+"</a>");
		}
		
		
	}



	public Integer getPageIndex() {
		return pageIndex;
	}



	public void setPageIndex(Integer pageIndex) {
		if(pageIndex==null){
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
	}



	public Integer getTotalSize() {
		return totalSize;
	}



	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}



	public Integer getPageSize() {
		return pageSize;
	}



	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}



	public String getSubmitUrl() {
		return submitUrl;
	}



	public void setSubmitUrl(String submitUrl) {
		this.submitUrl = submitUrl;
	}


	public String getPageStyle() {
		return pageStyle;
	}


	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}
	
	


}
