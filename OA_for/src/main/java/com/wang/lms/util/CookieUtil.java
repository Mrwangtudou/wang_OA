package com.wang.lms.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtil {

	//添加Cookie   
	public static void addCookie(String userId, HttpServletRequest request, HttpServletResponse response, int cookieAge,String cookieName) {
		// TODO Auto-generated method stub
		
		//根据Cookie名字判断浏览器中是否存在对应的Cookie，类似判断用户是否点击过  记住一周
		Cookie cookie = getCookie(cookieName,request);
		
		if(cookie==null){
			//cookie==null：说明 要么没有点击过记住一周，要么点击过但是Cookie已经过期
			cookie = new Cookie(cookieName, userId);//第一个参数：Cookie名字    第二个参数：value  都不能为中文 
		}
		
		//设置存活时间
		cookie.setMaxAge(cookieAge);
		
		//设置Cookie的值
		cookie.setValue(userId);
		
		//设置Cookie的作用域   
		cookie.setPath(request.getContextPath());
		
		System.out.println("request.getContextPath():"+request.getContextPath()); 

	     //将Cookie响应至客户端|用户的浏览器中
		response.addCookie(cookie);
	}
	
	
	//根据Cookie名字获取Cookie    request在此处的作用？ 浏览器发送请求，请求最先交给Tomcat服务器，交互过程中会将浏览器中的Cookie信息封装到request
	public static Cookie getCookie(String cookieName,HttpServletRequest request) {
		// TODO Auto-generated method stub
		Cookie[] cookies = request.getCookies();
		
		if(cookies!=null){
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(cookieName)){
					return cookie;
				}
			}
			
		}
		return null;
	}
	
	

    //清除Cookie信息
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		// TODO Auto-generated method stub
		
		//现根据Cookie名字获取Cookie信息
		Cookie cookie = getCookie(cookieName, request);
		if(cookie!=null){
			
			//将Cookie的存活时间设置 0 
			cookie.setMaxAge(0);
			
			//设置Cookie的作用域
			cookie.setPath(request.getContextPath());
			
			response.addCookie(cookie);
		}
		
		
	}
	
	

}
