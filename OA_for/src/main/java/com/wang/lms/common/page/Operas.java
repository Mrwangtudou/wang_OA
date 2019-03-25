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
public class Operas  extends TagSupport{
	
	private String name;
	

	
	

	//页面解析 <fk:pager>开始标签时  会触发   doStartTag方法
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		try{
			
			System.out.println("name:"+this.name);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		return super.doStartTag();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


    
}
