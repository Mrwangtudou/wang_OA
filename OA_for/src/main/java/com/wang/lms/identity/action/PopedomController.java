package com.wang.lms.identity.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wang.lms.common.page.PageModel;
import com.wang.lms.hrm.bean.Dept;
import com.wang.lms.hrm.service.HrmService;
import com.wang.lms.identity.bean.Module;
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
@RequestMapping("/identity/popedom")
public class PopedomController {
	

	@Resource(name="identityService")
	private IdentityService identityService;
	
	
	//跳转至绑定操作主页面
	@RequestMapping(value="/mgrPopedom.jspx")
	public String mgrPopedom(Role role){
		
		return "/identity/role/bindPopedom/mgrPopedom";
	}
	
	
	//根据第二级模块的code获取第三级模块信息
	@RequestMapping(value="/selectThirdModuleByParentCode.jspx")
	public String selectThirdModuleByParentCode(@RequestParam("pCode")String pCode,@RequestParam("roleId")Long roleId,Model model){
		
		try {
			//根据第二级模块的code获取第三级模块信息
			List<Module> modules = identityService.selectThirdModuleByParentCode(pCode);
			model.addAttribute("modules", modules);
			
			//根据角色id获取角色信息
			Role role = identityService.getRoleByRoleId(roleId);
			model.addAttribute("role", role);
			
			//根据模块code获取模块信息
			Module module = identityService.getModuleByCode(pCode);
			model.addAttribute("module", module);
			
			//获取指定角色已经绑定好指定二级模块下 的哪些操作      查询权限表（角色 与 模块的中间表   Popedom）
			List<String> operas = identityService.findOperasByRoleIdAndSecondCode(roleId,pCode);
			model.addAttribute("operas", operas);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/identity/role/bindPopedom/operas";
	}
	
	
	//绑定权限信息
	@RequestMapping(value="/bindPopedom.jspx")
	public String bindPopedom(@RequestParam("pCode")String pCode,@RequestParam("roleId")Long roleId,@RequestParam("codes")String codes,HttpSession session,Model model){

		try {
			
			identityService.bindPopedom(pCode,roleId,codes,session);
			model.addAttribute("message", "绑定成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}
		return "forward:/identity/popedom/selectThirdModuleByParentCode.jspx";
	}
}
