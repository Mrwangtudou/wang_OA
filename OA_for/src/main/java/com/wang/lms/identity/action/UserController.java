package com.wang.lms.identity.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wang.lms.common.page.PageModel;
import com.wang.lms.hrm.bean.Dept;
import com.wang.lms.hrm.service.HrmService;
import com.wang.lms.identity.bean.User;
import com.wang.lms.identity.service.IdentityService;
import com.wang.lms.util.ConstantUtil;
import com.wang.lms.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/identity/user")
public class UserController {
	

	@Resource(name="identityService")
	private IdentityService identityService;
	
	//用户异步登录  异步请求不需要返回页面，直接将信息响应至前台|页面，通过注解@ResponseBody声明
	@RequestMapping(value="/ajaxLogin.jspx",produces={"application/text;charset=utf-8"})
	@ResponseBody
	public String ajaxLogin(User user,String vcode,String rem,HttpServletResponse response,HttpServletRequest request){

		try {
			
			String message = identityService.ajaxLogin(user,vcode,rem,response,request);
			
			return message;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}
	
	
    //用户退出
	@RequestMapping(value="/logout.jspx")
	public String logout(HttpServletRequest request,HttpServletResponse response){

		try {
			
			//将session中的用户信息清空  以及  Cookie中的用户信息清除
			//session.invalidate();//让session失效
		      request.getSession().removeAttribute(ConstantUtil.SESSION_USER);
		      
		      //将Cookie中的用户信息清除或者让Cookie失效
		      CookieUtil.removeCookie(request,response,ConstantUtil.REME);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
			
		return "login";   
	}
	
	
	
	
	
	//用户多条件分页查询
	@RequestMapping(value="/selectUser.jspx")
	public String selectUser(User user,PageModel pageModel,Model model){
		try {
			
            //用户 多条件分页查询
			List<User> users = identityService.selectUserByPage(user,pageModel);
			
			//将数据存放在model中，model的访问范文和request是一样的
			model.addAttribute("users", users);
			
			return "/identity/user/user";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("网络异常！");
		}
	}
	
	//异步加载部门以及职位信息
	@RequestMapping(value="/ajaxLoadDeptAndJob.jspx",produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String ajaxLoadDeptAndJob(){
		
		try {
			//返回json格式的字符窜
			String data = identityService.ajaxLoadDeptAndJob();
			
			return data;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}
	
	//删除用户信息
	@RequestMapping(value="/deleteUser.jspx")
	public String deleteUser(@RequestParam("userIds")String userIds,Model model){
		
		try {
			
			identityService.deleteUserByIds(userIds);
			
			model.addAttribute("message", "删除成功！");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", "删除失败！");
		}
		
		return "forward:/identity/user/selectUser.jspx";		
		
	}
	
    //加载添加用户信息页面
	@RequestMapping(value="/showAddUser.jspx")
	public String showAddUser(){
		return "/identity/user/addUser";		
		
	}

	//校验账号是否存在
	@RequestMapping(value="/userExistOrNot.jspx",produces={"application/text;charset=utf-8"})
	@ResponseBody
	public String userExistOrNot(@RequestParam("userId")String userId){
		
		try {
			
			String message = identityService.userExistOrNot(userId);
			
			return message;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}
	
	
	//保存用户信息
	@RequestMapping(value="/addUser.jspx")
	public String addUser(User user,HttpSession session,Model model){
		
		try {
			
			identityService.addUser(user,session);
			
			model.addAttribute("message", "添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			throw new RuntimeException();
		}
		
		return  "/identity/user/addUser";
		
	}
	
	//展示修改用户页面
	@RequestMapping(value="/showUpdateUser.jspx")
	public String showUpdateUser(@RequestParam("userId")String userId,Model model){

		try {
			
			User user = identityService.getUserByUserId(userId);
			model.addAttribute("user", user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return  "/identity/user/updateUser";
		
	}
	
	//更新用户信息
	@RequestMapping(value="/updateUser.jspx")
	public String updateUser(User user,HttpSession session,Model model){
		
		try {
			
			identityService.updateUser(user,session);
			
			model.addAttribute("message", "更新成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			throw new RuntimeException();
		}
		
		return  "/identity/user/updateUser";
		
	}
	
	//预览用户信息
	@RequestMapping(value="/preUser.jspx")
	public String preUser(User user,HttpSession session,Model model){
		
		try {
			
			user = identityService.getUserByUserId(user.getUserId());
			model.addAttribute("user", user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return  "/identity/user/preUser";
		
	}
	
	
	// 激活用户信息
		@RequestMapping(value="/activeUser.jspx")
		public String activeUser(User user,PageModel pageModel,HttpSession session,Model model){
			
			try {
				
				identityService.activeUser(user,session);
				
				model.addAttribute("message", user.getStatus()==0?"冻结成功！":"激活成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				model.addAttribute("message", e.getMessage());
				throw new RuntimeException();
			}
			
			return  "forward:/identity/user/selectUser.jspx";
			
		}
		
		
		//更新个人信息
		@RequestMapping(value="/updateSelf.jspx")
		public String updateSelf(User user,HttpSession session,Model model){
			
			try {
				
				identityService.updateSelf(user,session);
				
				model.addAttribute("message", "更新成功！");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				model.addAttribute("message", e.getMessage());
				throw new RuntimeException();
			}
			
			return  "home";
			
		}
}
