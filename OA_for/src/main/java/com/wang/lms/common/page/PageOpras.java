package com.wang.lms.common.page;

import java.util.List;

import javax.servlet.http.HttpSession;
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
 */
public class PageOpras  extends TagSupport{

	private String name;
	
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		
		System.out.println("name:"+name);
		
		//EVAL_BODY_INCLUDE:显示开始与结束标签中间的内容
		//SKIP_BODY:跳过开始与结束标签中间的内容
		
		//获取用户的操作权限
		HttpSession session =  this.pageContext.getSession();
		List<String> userOperas = (List<String>)session.getAttribute("userOperas");
		
		for (int i = 0; i < userOperas.size(); i++) {
			if(userOperas.get(i).equals(name)){
				return EVAL_BODY_INCLUDE;
			}
		}
		
		return SKIP_BODY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
