package com.wang.lms.identity.action;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wang.lms.hrm.bean.Dept;
import com.wang.lms.hrm.service.HrmService;
import com.wang.lms.identity.bean.Module;
import com.wang.lms.identity.bean.User;
import com.wang.lms.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {
	

	@Resource(name="identityService")
	private IdentityService identityService;
	
	//跳转至主页面
	@RequestMapping(value="/main.jspx")
	public String main(HttpSession session,Model model){
		try {
			
			//获取用户的权限信息，控制main.jsp左侧页面  一二级模块的显示
			//key用于存放一级模块信息   value用于存放二级模块信息
			Map<Module,List<Module>>  operasModules = identityService.findMenuOperas(session);
			model.addAttribute("operasModules", operasModules);
		
			//获取用户的所有操作权限（第三级模块的操作权限）  用于控制页面中的按钮的显示于隐藏
			List<String> userOperas = identityService.findUserOperas(session);
			session.setAttribute("userOperas", userOperas);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	
		return "main";
	}
	
	@RequestMapping(value="/home.jspx")
	public String home(){
	
		return "home";
	}

}
