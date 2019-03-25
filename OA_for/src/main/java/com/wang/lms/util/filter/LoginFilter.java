package com.wang.lms.util.filter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wang.lms.identity.bean.User;
import com.wang.lms.identity.dao.UserDao;
import com.wang.lms.identity.service.IdentityService;
import com.wang.lms.util.ConstantUtil;
import com.wang.lms.util.CookieUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LoginFilter extends HandlerInterceptorAdapter {
	
	@Resource(name="identityService")
	private IdentityService identityService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		// TODO Auto-generated method stub
		
		//先判断session中是否有用户信息
		User user = (User)request.getSession().getAttribute(ConstantUtil.SESSION_USER);

		if(user!=null){
			//session中有用户信息，说明用户已经登录，应该放行
			return true;
		}else{
			//session中没有用户信息，继续判断Cookie中是否有用户信息，如果有说明用户点击过记住一周，并且Cookie还未失效
			Cookie cookie = CookieUtil.getCookie(ConstantUtil.REME, request);
			
			if(cookie!=null){
				//说明cookie中存在用户信息，查询数据库将 用户信息存放在session中，并且放行
				
				User u = identityService.getUserByUserId(cookie.getValue());
				
				if(u!=null){
					//将用户信息存放至session中
					request.getSession().setAttribute(ConstantUtil.SESSION_USER, u);
				    return true;
				}else{
					
					request.setAttribute("message", "你尚未登录，请登录后再进行相关操作！");
					//跳转至登录页面
					request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
					return false;
				}
				
				
			}else{
				//cookie中不存在用户信息
				
				request.setAttribute("message", "你尚未登录，请登录后再进行相关操作！");
				//跳转至登录页面
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				return false;
			}
		}
		
	}
	
	

}
