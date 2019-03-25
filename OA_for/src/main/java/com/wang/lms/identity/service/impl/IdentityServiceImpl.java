package com.wang.lms.identity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.aspectj.apache.bcel.classfile.Constant;
import com.wang.lms.common.page.PageModel;
import com.wang.lms.hrm.dao.DeptDao;
import com.wang.lms.hrm.dao.JobDao;
import com.wang.lms.identity.bean.Module;
import com.wang.lms.identity.bean.Popedom;
import com.wang.lms.identity.bean.Role;
import com.wang.lms.identity.bean.User;
import com.wang.lms.identity.dao.ModuleDao;
import com.wang.lms.identity.dao.PopedomDao;
import com.wang.lms.identity.dao.RoleDao;
import com.wang.lms.identity.dao.UserDao;
import com.wang.lms.identity.service.IdentityService;
import com.wang.lms.util.ConstantUtil;
import com.wang.lms.util.CookieUtil;
import com.wang.lms.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.*;



@Service("identityService")
@Transactional(readOnly=true,rollbackFor= Exception.class)
public class IdentityServiceImpl implements IdentityService {

	@Resource(name="userDao")
	private UserDao userDao;
	
	@Resource(name="deptDao")
	private DeptDao deptDao;
	
	@Resource(name="jobDao")
	private JobDao jobDao;
	
	@Resource(name="roleDao")
	private RoleDao roleDao;
	
	@Resource(name="moduleDao")
	private ModuleDao moduleDao;
	
	@Resource(name="popedomDao")
	private PopedomDao popedomDao;
	
	/* 
	 * 异步登录
	 */
	@Override
	public String ajaxLogin(User user, String vcode, String rem, HttpServletResponse response,HttpServletRequest request) {
		// TODO Auto-generated method stub
		try {
			
			//先判断验证码是否正确
			
			//获取session中的验证码
			String v_code = (String)request.getSession().getAttribute(ConstantUtil.VERIFY_CODE);
			if(!vcode.equals(v_code)){
				return "验证码不正确！";
			}else{
				//验证账号以及密码
				User u = userDao.get(User.class, user.getUserId());
				if(u==null || u.getDelFlag().equals("0")){
					//账号不正确
					return "账号不正确，请核实！";
				}else if(!u.getPassWord().equals(MD5.getMD5(user.getPassWord()))){
					//密码不正确
					return "密码不正确，请核实！";
				}else{
					//信息都正确，将用户信息存放在session中，还要判断是否需要  记住一周
					request.getSession().setAttribute(ConstantUtil.SESSION_USER, u);
					
					if(rem.equals("1")){
						//记住用户信息，记住一周，信息存放在cookie中   
						//账号   request  response  存活时间   Cookie名字
						CookieUtil.addCookie(user.getUserId(),request,response,7*24*60*60,ConstantUtil.REME);
					}
				}
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("登录失败！");
		}
	}
	
	
	/* (non-Javadoc)
	 * 根据用户账号获取用户信息
	 */
	@Override
	public User getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		try {
			return userDao.get(User.class, userId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("用户信息检索失败！");
		}
	}


	/* (non-Javadoc)
	 * 用户 多条件分页查询
	 */
	@Override
	public List<User> selectUserByPage(User user, PageModel pageModel) {
		// TODO Auto-generated method stub
		try {
			//定义集合用于封装查询条件
			List<Object> params = new ArrayList<>();
			
			StringBuffer  hql = new StringBuffer();
			
			hql.append(" from  User where delFlag = '1' ");
			
			if(StringUtils.isNotEmpty(user.getName())){
				hql.append(" and name like ? ");
				params.add("%"+user.getName()+"%");
			}
			if(StringUtils.isNotEmpty(user.getPhone())){
				hql.append(" and phone like ? ");
				params.add("%"+user.getPhone()+"%");
			}
			
			if(user.getDept()!=null && user.getDept().getId()!=null  && user.getDept().getId() !=0){
				hql.append(" and dept.id = ? ");
				params.add(user.getDept().getId());
			}
			
			if(user.getJob()!=null && StringUtils.isNotEmpty(user.getJob().getCode()) && !user.getJob().getCode().equals("0")){
				hql.append(" and job.code = ? ");
				params.add(user.getJob().getCode());
			}
			
		
			return userDao.findByPage(hql.toString(), pageModel, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("用户信息分页查询异常！");
		}
	}


	/* (non-Javadoc)
	 * 异步加载部门以及职位信息
	 */
	@Override
	public String ajaxLoadDeptAndJob() {
		// TODO Auto-generated method stub
		try {
			//获取部门信息   id   name
			List<Map<Long,String>> depts = deptDao.findAllDepts();
			
			System.out.println("depts:"+depts);
			//获取职位信息  code   name
			List<Map<String,String>> jobs = jobDao.findAllJobs();
			System.out.println("jobs:"+jobs);
			
			
			JSONObject jsonUtil = new JSONObject();
			jsonUtil.put("depts", depts);
			jsonUtil.put("jobs", jobs);
			
			
			return jsonUtil.toString();

			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("网络异常！");
		}
	}


	/* (non-Javadoc)
	 * 删除用户信息     逻辑删除
	 */
	@Transactional(readOnly=false)
	@Override    
	public void deleteUserByIds(String userIds) {
		// TODO Auto-generated method stub
		try {
			StringBuilder hql = new StringBuilder();

			hql.append(" update User set delFlag = '0' where ");
			
			String[] ids = userIds.split(",");

			for (int i = 0; i < ids.length; i++) {
				hql.append(i==0?" userId = ? " :  "or userId = ?" );
			}
			
			userDao.bulkUpdate(hql.toString(), ids);
						
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("删除失败！");
		}
	}


	/* (non-Javadoc)
	 * 校验账号是否存在
	 */
	@Override
	public String userExistOrNot(String userId) {
		// TODO Auto-generated method stub
		try {
		
			User user = userDao.get(User.class, userId);
			return user ==null ? "":"您输入的账号已存在，请重新输入！";		
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("账号校验失败！");
		}
	}


	/* (non-Javadoc)
	 * 保存用户信息
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void addUser(User user, HttpSession session) {
		// TODO Auto-generated method stub
		try {
			
			//设置创建人与创建时间
			User creater = (User)session.getAttribute(ConstantUtil.SESSION_USER);
			user.setCreater(creater);
			user.setCreateDate(new Date());
			//对密码进行MD5加密
			user.setPassWord(MD5.getMD5(user.getPassWord()));
			
			userDao.save(user);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("添加失败！");
		}
	}


	/* (non-Javadoc)
	 * 更新用户信息
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void updateUser(User user, HttpSession session) {
		// TODO Auto-generated method stub
	try {
		
		   //用户信息修改的两种方式：
		
		    //第一种：在临时状态下进行修改  user:瞬时状态
			//设置修改人与修改时间
		/*	User updater = (User)session.getAttribute(ConstantUtil.SESSION_USER);
			//设置修改人
			user.setModifier(updater);
			//设置修改事件
			user.setModifyDate(new Date());
			
			//根据用户id获取用户的信息 
			userDao.update(user);*/
		
		   //第二种：在持久化状态下进行修改    u:持久化状态
		   User u = userDao.get(User.class, user.getUserId());
		   u.setModifyDate(new Date());
			//设置修改人
			u.setModifier((User)session.getAttribute(ConstantUtil.SESSION_USER));
			u.setName(user.getName());
			u.setEmail(user.getEmail());
			u.setSex(user.getSex());
			u.setTel(user.getTel());
			u.setPhone(user.getPhone());
			u.setQuestion(user.getQuestion());
			u.setAnswer(user.getAnswer());
			u.setQqNum(user.getQqNum());
			//对密码进行MD5加密
			u.setPassWord(MD5.getMD5(user.getPassWord()));			
			u.setDept(user.getDept());
			u.setJob(user.getJob());
		
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("更新失败！");
		}
	}


	/* (non-Javadoc)
	 * 激活用户信息
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void activeUser(User user, HttpSession session) {
		// TODO Auto-generated method stub
		try {
			
			  User u = userDao.get(User.class, user.getUserId());
			  //设置审核时间
			  u.setCheckDate(new Date());
			  //设置审核人|谁激活的
			  u.setChecker((User)session.getAttribute(ConstantUtil.SESSION_USER));
			  u.setStatus(user.getStatus());
			  
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException(user.getStatus()==0?"冻结失败！":"激活失败");
			}
		
	}

    /*****************************************角色管理模块****************************************/
	/* (non-Javadoc)
	 * 角色分页查询
	 */
	@Override
	public List<Role> selectRoleByPage(PageModel pageModel) {
		// TODO Auto-generated method stub
		
		try {
			
			String  hql = " from  Role where delFlag = '1' ";
			List<Role> roles = roleDao.findByPage(hql, pageModel, null);
			
			for (int i = 0; i < roles.size(); i++) {
				if(roles.get(i).getCreater()!=null) roles.get(i).getCreater().getName();
				
				if(roles.get(i).getModifier()!=null) roles.get(i).getModifier().getName();
			}
			

			return roles;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("角色信息分页查询异常！");
		}
	}


	/* (non-Javadoc)
	 * 删除角色信息
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void deleteRoleByIds(String roleIds) {
		// TODO Auto-generated method stub
		try {

			String[] ids = roleIds.split(",");
			
			for (int i = 0; i < ids.length; i++) {
				Role role = roleDao.get(Role.class, Long.valueOf(ids[i]));
				//对角色进行逻辑删除
				role.setDelFlag("0");
			}		
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("删除失败！");
		}
	}


	/* (non-Javadoc)
	 * 保存角色信息
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void addRole(Role role, HttpSession session) {
		// TODO Auto-generated method stub
      try {
			
			//设置创建人与创建时间
			User creater = (User)session.getAttribute(ConstantUtil.SESSION_USER);
			role.setCreater(creater);
			role.setCreateDate(new Date());
			roleDao.save(role);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("添加失败！");
		} 
	}


	/* (non-Javadoc)
	 * 展示修改角色页面
	 */
	@Override
	public Role getRoleByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		  try {
				
				return roleDao.get(Role.class, roleId);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("数据获取失败！");
			} 
	}


	/* (non-Javadoc)
	 * 更新角色信息
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void updateRole(Role role, HttpSession session) {
		// TODO Auto-generated method stub
      try {
			
		Role r = roleDao.get(Role.class, role.getId());
		
		r.setName(role.getName());
		r.setRemark(role.getRemark());
		r.setModifyDate(new Date());
		r.setModifier((User)session.getAttribute(ConstantUtil.SESSION_USER));
    	  
    	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("更新失败！");
		} 
	}


	/* (non-Javadoc)
	 * 更新个人信息
	 */
	@Transactional(readOnly=false)
	@Override
	public void updateSelf(User user, HttpSession session) {
		// TODO Auto-generated method stub
		try {
		
			
			   //在持久化状态下进行修改    u:持久化状态
			   User u = userDao.get(User.class, user.getUserId());
			   u.setModifyDate(new Date());
				//设置修改人
				u.setModifier((User)session.getAttribute(ConstantUtil.SESSION_USER));
				u.setName(user.getName());
				u.setEmail(user.getEmail());
				u.setTel(user.getTel());
				u.setPhone(user.getPhone());
				u.setQuestion(user.getQuestion());
				u.setAnswer(user.getAnswer());
				u.setQqNum(user.getQqNum());
				u.setPassWord(user.getPassWord());
				
			    //更新session
				session.setAttribute(ConstantUtil.SESSION_USER, u);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("更新失败！");
			}
	}


	/* (non-Javadoc)
	 * 获取指定角色已经绑定的用户信息
	 */
	@Override
	public List<User> selectRoleUser(Long id,PageModel pageModel) {
		   // TODO Auto-generated method stub
		   try {
			   //准备hql语句
			   String hql = "select u from User u inner join u.roles r where u.delFlag = '1' and  r.id = "+id+"";
			   
				return roleDao.findByPage(hql, pageModel, null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("查询失败！");
			}
	
	}


	/* (non-Javadoc)
	 * 解绑指定角色下的指定的用户信息      操作角色与用户的额中间表 ，删除指定的记录
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void unbindRole(Long id, String userIds) {
		// TODO Auto-generated method stub
		   try {
			   
			   //根据角色id获取指定的角色信息
			   Role role = roleDao.get(Role.class, id);
			   //获取该角已经绑定的用户信息
			   Set<User> roleUsers = role.getUsers();
			   
			   String[] uIds = userIds.split(",");
			   for (int i = 0; i < uIds.length; i++) {
				//根据用户id获取用户信息
				  User u = userDao.get(User.class, uIds[i]);
				  roleUsers.remove(u);
			}
			   
			 
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("查询失败！");
			}
	}


	/* (non-Javadoc)
	 * 获取指定的角色未绑定的用户信息
	 */
	@Override
	public List<User> showUnBindUser(Long roleId, PageModel pageModel) {
		// TODO Auto-generated method stub
		try {
			   //子查询语句
			
			   //准备hql语句
			   StringBuilder hql = new StringBuilder();
			   hql.append(" from User  where delFlag = '1' and userId not in (");
			   hql.append(" select u.userId from User u inner join u.roles r where u.delFlag = '1' and  r.id = "+roleId+")");
			   
			   
				return roleDao.findByPage(hql.toString(), pageModel, null);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("查询失败！");
			}
	
	}


	/* (non-Javadoc)
	 * 获取指定角色为绑定的用户信息
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void bindUser(Long roleId, String userIds) {
		// TODO Auto-generated method stub
		 try {
			   
			   //根据角色id获取指定的角色信息
			   Role role = roleDao.get(Role.class, roleId);
			   //获取该角已经绑定的用户信息
			   Set<User> roleUsers = role.getUsers();
			   
			   String[] uIds = userIds.split(",");
			   for (int i = 0; i < uIds.length; i++) {
				//根据用户id获取用户信息
				  User u = userDao.get(User.class, uIds[i]);
				  roleUsers.add(u);
			}
			   
			 
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("查询失败！");
			}
	}


	 /*****************************************菜单管理|模块管理****************************************/
	/* (non-Javadoc)
	 * 异步加载所有的模块信息 
	 */
	@Override
	public String ajaxLoadModule() {
		// TODO Auto-generated method stub
		 try {
			   
			 String hql = "from Module where delFlag = '1' ";
			 
			 //获取所有的模块信息   一级（0001）  二级（00010001）   三级（000100010001）
			 List<Module> modules = moduleDao.find(hql);
			 
			 JSONArray arr = new JSONArray();
			 for(int i = 0; i < modules.size(); i++){
				 
				 JSONObject obj = new JSONObject();
				 
				 String code = modules.get(i).getCode();//00010001  ==>   0001    000100010001==>00010001
				 obj.put("code", code);
				 String pCode = code.length()==4? "0":code.substring(0, code.length() - 4);
				 obj.put("pCode", pCode);
				 String name = modules.get(i).getName();
				 obj.put("name", name);
				 
				 arr.add(obj);
				
			 }
			   //  [{"code":xxxx,"pCode":xxx,"name":xxxx},{},{}]
			 System.out.println("arr.toString():"+arr.toString());
			   return arr.toString();
			 
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("查询失败！");
			}
	}


	/* (non-Javadoc)
	 * 模块分页查询
	 */
	@Override
	public List<Module> selectModuleByParentCode(Module module, PageModel pageModel) {
		// TODO Auto-generated method stub
		 try {
		
			 String hql = "from Module where delFlag = '1' and length(code) = ? and code like ? ";
			 
			 List<Object> params = new ArrayList<>();
			 params.add(module.getCode().length() + 4);
			 params.add(module.getCode()+"%");
		
			 return moduleDao.findByPage(hql, pageModel, params);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("查询失败！");
			}
	}


	/* (non-Javadoc)
	 * 删除模块信息   
	 * 删除模块的时候，必须把子模块也删除
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void deleteModuleByIds(String codes) {
		// TODO Auto-generated method stub
		 try {
				
				StringBuffer hql = new StringBuffer();
				hql.append(" delete from Module where ");
				String[] mCodes = codes.split(",");
				
				for (int i = 0; i < mCodes.length; i++) {
					//code like ? or code like ?
						hql.append(i==0?" code like '"+mCodes[i]+"%' " : " or code like '"+mCodes[i]+"%' ");
				}
				
				moduleDao.bulkUpdate(hql.toString(), null);
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("删除失败！");
			}
		
		
	}


	/* (non-Javadoc)
	 * 根据模块的code(主键)获取模块信息
	 */
	@Override
	public Module getModuleByCode(String code) {
		// TODO Auto-generated method stub
		 try {
				
			   return moduleDao.get(Module.class, code);
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("数据加载失败！");
			}
	}


	/* (non-Javadoc)
	 * 更新模块信息
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void updateModule(Module module, HttpSession session) {
		// TODO Auto-generated method stub
		try {
			
			
			   //在持久化状态下进行修改    u:持久化状态
			   Module m = userDao.get(Module.class, module.getCode());
			   m .setModifyDate(new Date());
				//设置修改人
			   m .setModifier((User)session.getAttribute(ConstantUtil.SESSION_USER));
			   m.setName(module.getName());
			   m.setRemark(module.getRemark());
			   m.setUrl(module.getUrl());
			   
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("更新失败！");
			}
	}


	/* (non-Javadoc)
	 * 保存模块信息
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void addModule(Module module, String parentCode, HttpSession session) {
		// TODO Auto-generated method stub
		try {
			//计算新增模块的code    parentCode：0001     maxCode： 00010006   == >00010007   
			//                  parentCode：0001      maxCode： ''   == >00010001  
            //                  parentCode：0001      maxCode： 00019999   == >00020000  出现该情况则抛出异常
			String maxCode = moduleDao.getMaxModuleCodeByPcode(parentCode);
			System.out.println("maxCode:"+maxCode);
			
			parentCode = parentCode==null?"":parentCode;
			
			//定义最终需要生成的模块的code
			String newCode = "";
			if(StringUtils.isEmpty(maxCode)){
				newCode = parentCode + "0001";
			}else{
				
				//获取最大子模块的后四位    maxCode:00010009
				//code2:0009
				String code2 = maxCode.substring(maxCode.length()-4, maxCode.length());
				if(Integer.valueOf(code2) == 9999){
					throw new RuntimeException("子模块数量已超出最大限制，无法继续添加至模块！");
				}else{
					//code3:9
					int code3 = Integer.valueOf(code2);
					//code3:10
					code3 = code3 + 1;
					
					newCode = parentCode;

					//补零
					for (int i = 0; i < 4 - String.valueOf(code3).length(); i++) {
						newCode += "0";
					}
					newCode += code3;
					
				}
			}
			
			module.setCode(newCode);
			module.setCreateDate(new Date());
			module.setCreater((User)session.getAttribute(ConstantUtil.SESSION_USER));
			
			moduleDao.save(module);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("添加失败！");
		}
	}


	/* (non-Javadoc)
	 * 根据第二级模块的code获取第三级模块信息
	 */
	@Override
	public List<Module> selectThirdModuleByParentCode(String pCode) {
		// TODO Auto-generated method stub
		try {
			
			 String hql = "from Module where delFlag = '1' and length(code) = "+(pCode.length()+4)+" and code like '"+pCode+"%' ";
             
			 return moduleDao.find(hql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("查询失败！");
		}
	}


	/* (non-Javadoc)
	 * 获取指定角色已经绑定好指定二级模块下 的哪些操作      查询权限表（角色 与 模块的中间表   Popedom）
	 */
	@Override
	public List<String> findOperasByRoleIdAndSecondCode(Long roleId, String pCode) {
		// TODO Auto-generated method stub
		try {
			
			String hql = "select p.opera.code from Popedom p where p.module.code = ? and p.role.id = ?";
			return (List<String>)popedomDao.find(hql, new Object[]{pCode,roleId});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("查询失败！");
		}
	}


	/* (non-Javadoc)
	 * 绑定权限信息
	 */
	@Transactional(readOnly=false,rollbackFor= Exception.class)
	@Override
	public void bindPopedom(String pCode, Long roleId, String codes,HttpSession session) {
		// TODO Auto-generated method stub
       try {
			
			String hql = "delete from Popedom p where p.module.code = ? and p.role.id = ?";
			//清除权限表中  与当前指定的 角色与二级模块code相关的权限记录
			popedomDao.bulkUpdate(hql, new Object[]{pCode,roleId});
		
			if(StringUtils.isNotEmpty(codes)){
				String[] operasCodes = codes.split(",");
				
				Module m = new Module();
				m.setCode(pCode);
				
				Role r = new Role();
				r.setId(roleId);
				
				
				for (int i = 0; i < operasCodes.length; i++) {
					
					Popedom popedom = new Popedom();
					popedom.setCreateDate(new Date());
					popedom.setCreater((User)session.getAttribute(ConstantUtil.SESSION_USER));
					//设置二级模块信息   该权限记录属于哪一个二级模块
					popedom.setModule(m);
					
					Module thModule = new Module();
					thModule.setCode(operasCodes[i]);
					
					popedom.setOpera(thModule);
					
					popedom.setRole(r);
					
					popedomDao.save(popedom);
				}
			}
			       

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("绑定失败！");
		}
	}


	/* (non-Javadoc)
	 * 获取用户拥有哪些一级二级模块的操作权限
	 */
	@Override
	public Map<Module, List<Module>> findMenuOperas(HttpSession session) {
		// TODO Auto-generated method stub
		try {
			//获取session用户信息
			User user = (User)session.getAttribute(ConstantUtil.SESSION_USER);
			
			//00010001   00010002 00020001 00020003
			List<String> codes = popedomDao.findMenuOperas(user.getUserId());
			
			//用于封装一级以及二级模块信息
			Map<Module, List<Module>>  modules = new LinkedHashMap<>();
			
			//用于封装一级模块信息
			Module firstModule = null;
			//用于封装二级模块信息
			List<Module> secondModule = null;
			
			if(codes!=null && codes.size()>0){
				for (int i = 0; i < codes.size(); i++) {
					
					//根据二级模块code获取一级模块的code
					String fCode = codes.get(i).substring(0, 4);
					
					//根据一级模块的code获取一级模块信息
					Module fModule = moduleDao.get(Module.class, fCode);
					
					//00010001  00010002    00020001 00020003  
					//if语句中用于填充一级模块信息
					
					//进入if语句说明第一次将该一级模块存放至map集合中
					if(!modules.containsKey(fModule)){
						secondModule = new LinkedList<>();
					
						//将一级模块存放至map集合中，通过创建二级模块List对象并存入map集合中，单此处secondModule中并没有存放二级模块信息
						modules.put(fModule, secondModule);
					}
					
					//填充二级模块信息
					Module seModule = moduleDao.get(Module.class, codes.get(i));
					secondModule.add(seModule);
					
					
				}
				
			}
			
			return modules;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("权限查询失败！");
		}
	}


	/* (non-Javadoc)
	 * 获取用户的所有操作权限（第三级模块的操作权限）  用于控制页面中的按钮的显示于隐藏
	 */
	@Override
	public List<String> findUserOperas(HttpSession session) {
		// TODO Auto-generated method stub
		try {
			//获取session用户信息
			User user = (User)session.getAttribute(ConstantUtil.SESSION_USER);
			
			//000100010001   000100010002  ==》  selectUser   addUser   
			List<String> codes = popedomDao.findUserOperas(user.getUserId());
			
			if(codes != null){
				List<String> urls = new ArrayList<>();
				
				for (int i = 0; i < codes.size(); i++) {
					
					Module module = moduleDao.get(Module.class, codes.get(i));
					//获取模块的  url
					String mUrl = module.getUrl();
					urls.add(mUrl);
				}
				return urls;
			}
			
		   return null;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("权限查询失败！");
		}
	}

}
