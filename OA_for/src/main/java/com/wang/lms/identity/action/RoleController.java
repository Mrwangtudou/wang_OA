package com.wang.lms.identity.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wang.lms.common.page.PageModel;
import com.wang.lms.hrm.bean.Dept;
import com.wang.lms.hrm.service.HrmService;
import com.wang.lms.identity.bean.Role;
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
@RequestMapping("/identity/role")
public class RoleController {
	
	
 

	@Resource(name="identityService")
	private IdentityService identityService;
	
	
	
	//角色分页查询
	@RequestMapping(value="/selectRole.jspx")
	public String selectRole(PageModel pageModel,Model model){
		try {
			
            //用户 多条件分页查询
			List<Role> roles = identityService.selectRoleByPage(pageModel);
			
			//将数据存放在model中，model的访问范文和request是一样的
			model.addAttribute("roles", roles);

			return "/identity/role/role";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("网络异常！");
		}
	}
	
	
	
	//删除角色信息
	@RequestMapping(value="/deleteRole.jspx")
	public String deleteRole(@RequestParam("roleIds") String roleIds,Model model){
		
		try {
			
			identityService.deleteRoleByIds(roleIds);
			
			model.addAttribute("message", "删除成功！");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}
		
		return "forward:/identity/role/selectRole.jspx";		
		
	}
	
    //加载添加角色信息页面
	@RequestMapping(value="/showAddRole.jspx")
	public String showAddRole(){
		return "/identity/role/addRole";		
		
	}

	
	
	
	//保存角色信息
	@RequestMapping(value="/addRole.jspx")
	public String addRole(Role role,HttpSession session,Model model){
		
		try {
			
			identityService.addRole(role,session);
			
			model.addAttribute("message", "添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			throw new RuntimeException();
		}
		
		return  "/identity/role/addRole";
		
	}
	
	//展示修改角色页面
	@RequestMapping(value="/showUpdateRole.jspx")
	public String showUpdateRole(@RequestParam("roleId")Long roleId,Model model){
		
		try {
			
			Role role = identityService.getRoleByRoleId(roleId);
			model.addAttribute("role", role);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return  "/identity/role/updateRole";
		
	}
	
	//更新角色信息
	@RequestMapping(value="/updateRole.jspx")
	public String updateRole(Role role,HttpSession session,Model model){
		
		try {
			
			identityService.updateRole(role,session);
			
			model.addAttribute("message", "更新成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			throw new RuntimeException();
		}
		
		return  "/identity/role/updateRole";
		
	}
	
	
	//展示角色绑定用户页面     --  获取指定的角色已经绑定的用户信息    --  需要查询角色与用户的中间表
	@RequestMapping(value="/selectRoleUser.jspx")
	public String selectRoleUser(@RequestParam("id")Long id,PageModel pageModel,Model model){
		
		try {
			
			List<User> users = identityService.selectRoleUser(id,pageModel);
			
			Role role = identityService.getRoleByRoleId(id);
			model.addAttribute("users", users);
			model.addAttribute("role", role);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return  "/identity/role/bindUser/roleUser";
		
	}
	
	
	//解绑指定角色下的指定的用户信息
	@RequestMapping(value="/unbindRole.jspx")
	public String unbindRole(@RequestParam("id")Long id,@RequestParam("userIds")String userIds,Model model){
		
		try {
			
			identityService.unbindRole(id,userIds);
			model.addAttribute("message", "解绑成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}
		
		return  "forward:/identity/role/selectRoleUser.jspx";
		
	}
	
	//获取指定角色为绑定的用户信息
	@RequestMapping(value="/showUnBindUser.jspx")
	public String showUnBindUser(@RequestParam("roleId")Long roleId,PageModel pageModel,Model model){
		
		try {
			
			List<User> users = identityService.showUnBindUser(roleId,pageModel);
			model.addAttribute("users", users);
			model.addAttribute("roleId", roleId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return  "/identity/role/bindUser/bindUser";
		
	}
	

	
	//获取指定角色为绑定的用户信息
	@RequestMapping(value="/bindUser.jspx")
	public String bindUser(@RequestParam("roleId")Long roleId,@RequestParam("userIds")String userIds,Model model){
		try {
			
			identityService.bindUser(roleId,userIds);
			model.addAttribute("message", "绑定成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}
		
		return  "forward:/identity/role/showUnBindUser.jspx";
		
	}
	
	
}
