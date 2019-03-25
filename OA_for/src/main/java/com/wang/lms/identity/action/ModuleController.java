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
@RequestMapping("/identity/module")
public class ModuleController {
	

	@Resource(name="identityService")
	private IdentityService identityService;
	
	
	//跳转至模块主页面
	@RequestMapping(value="/mgrModule.jspx")
	public String mgrModule(User user,PageModel pageModel,Model model){
		try {
			
           return "/identity/module/mgrModule";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("网络异常！");
		}
	}
	
	//异步加载所有的模块信息   
	@RequestMapping(value="/ajaxLoadModule.jspx",produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String ajaxLoadModule(User user,PageModel pageModel,Model model){
		try {
			//返回json格式的字符窜
			String  data = identityService.ajaxLoadModule();
            return data;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("网络异常！");
		}
	}
	
	
	//模块分页查询
	@RequestMapping(value="/selectModuleByParentCode.jspx")
	public String selectModuleByParentCode(Module module,PageModel pageModel,Model model){
		try {
			
			List<Module> modules = identityService.selectModuleByParentCode(module,pageModel);
			
			//将数据存放在model中，model的访问范文和request是一样的
			model.addAttribute("modules", modules);
			
	           return "/identity/module/module";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("网络异常！");
		}
	}
	
	
	//删除模块信息    
	@RequestMapping(value="/deleteModule.jspx")
	public String deleteModule(@RequestParam("codes")String codes,Model model){
		
		try {
			
			identityService.deleteModuleByIds(codes);
			
			model.addAttribute("message", "删除成功！");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}
		
		return "forward:/identity/module/selectModuleByParentCode.jspx";		
		
	}
	
    //加载添加模块信息页面
	@RequestMapping(value="/showAddModule.jspx")
	public String showAddModule(Module module){
		return "/identity/module/addModule";		
		
	}


	//保存模块信息  
	@RequestMapping(value="/addModule.jspx")
	public String addModule(Module module,HttpSession session,Model model){
		
		try {
			String pCode = module.getCode();
			identityService.addModule(module,module.getCode(),session);
			module.setCode(pCode);
			
			model.addAttribute("module", module);
			model.addAttribute("message", "添加成功！");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			throw new RuntimeException();
		}
		
		return  "/identity/module/addModule";
		
	}
	
	//展示修改模块页面
	@RequestMapping(value="/showUpdateModule.jspx")
	public String showUpdateModule(@RequestParam("code")String code,Model model){

		try {
			
			Module module = identityService.getModuleByCode(code);
			model.addAttribute("module", module);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return  "/identity/module/updateModule";
		
	}
	
	//更新模块信息
	@RequestMapping(value="/updateModule.jspx")
	public String updateModule(Module module,HttpSession session,Model model){
		
		try {
			
			identityService.updateModule(module,session);
			
			model.addAttribute("message", "更新成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			throw new RuntimeException();
		}
		
		return   "/identity/module/updateModule";
		
	}
	
	
	  
}
