package com.wang.lms.identity.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wang.lms.common.page.PageModel;
import com.wang.lms.identity.bean.Module;
import com.wang.lms.identity.bean.Role;
import com.wang.lms.identity.bean.User;


public interface IdentityService {

	//异步登录
	String ajaxLogin(User user, String vcode, String rem, HttpServletResponse response, HttpServletRequest request);

	//根据用户账号获取用户信息
	User getUserByUserId(String userId);
	
	//用户 多条件分页查询
	List<User> selectUserByPage(User user, PageModel pageModel);

	 //异步加载部门以及职位信息
	String ajaxLoadDeptAndJob();

	//删除用户信息
	void deleteUserByIds(String userIds)throws Exception;

	//校验账号是否存在
	String userExistOrNot(String userId);

	//保存用户信息
	void addUser(User user, HttpSession session);

	//更新用户信息
	void updateUser(User user, HttpSession session);

	// 激活用户信息
	void activeUser(User user, HttpSession session);
	
	//更新个人信息
	void updateSelf(User user, HttpSession session);
	
    /*****************************************角色管理模块****************************************/
	//角色分页查询
	List<Role> selectRoleByPage(PageModel pageModel);

	//删除角色信息
	void deleteRoleByIds(String roleIds);

	//保存角色信息
	void addRole(Role role, HttpSession session);

	//展示修改角色页面
	Role getRoleByRoleId(Long roleId);

	//更新角色信息
	void updateRole(Role role, HttpSession session);

	

	//获取指定角色已经绑定的用户信息
	List<User> selectRoleUser(Long id, PageModel pageModel);

	//解绑指定角色下的指定的用户信息
	void unbindRole(Long id, String userIds);

	//获取指定的角色未绑定的用户信息
	List<User> showUnBindUser(Long roleId, PageModel pageModel);

	//获取指定角色为绑定的用户信息
	void bindUser(Long roleId, String userIds);

	
	
    /*****************************************菜单管理|模块管理****************************************/
	//异步加载所有的模块信息   
	String ajaxLoadModule();

	//模块分页查询
	List<Module> selectModuleByParentCode(Module module, PageModel pageModel);

	//删除模块信息   
	void deleteModuleByIds(String codes);

	//根据模块的code(主键)获取模块信息
	Module getModuleByCode(String code);

	//更新模块信息
	void updateModule(Module module, HttpSession session);

	//保存模块信息
	void addModule(Module module, String parentCode, HttpSession session);

	//根据第二级模块的code获取第三级模块信息
	List<Module> selectThirdModuleByParentCode(String pCode);

	//获取指定角色已经绑定好指定二级模块下 的哪些操作      查询权限表（角色 与 模块的中间表   Popedom）
	List<String> findOperasByRoleIdAndSecondCode(Long roleId, String pCode);

	//绑定权限信息
	void bindPopedom(String pCode, Long roleId, String codes, HttpSession session);

	//获取用户拥有哪些一级二级模块的操作权限
	Map<Module, List<Module>> findMenuOperas(HttpSession session);

	//获取用户的所有操作权限（第三级模块的操作权限）  用于控制页面中的按钮的显示于隐藏
	List<String> findUserOperas(HttpSession session);

}
