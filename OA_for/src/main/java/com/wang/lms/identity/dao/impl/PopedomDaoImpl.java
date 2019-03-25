package com.wang.lms.identity.dao.impl;

import java.util.List;

import com.wang.lms.common.baseDao.impl.BaseDaoImpl;
import com.wang.lms.identity.dao.PopedomDao;
import org.springframework.stereotype.Repository;


@Repository("popedomDao")
public class PopedomDaoImpl extends BaseDaoImpl implements PopedomDao {

	/* (non-Javadoc)
	 * 获取用户拥有哪些一级二级模块的操作权限
	 */
	@Override
	public List<String> findMenuOperas(String userId) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		//查询权限表根据角色获取权限信息
		hql.append(" select distinct p.module.code from Popedom p where p.role.id in ( ");
		//根据用户的账号，获取用户拥有哪些角色
		hql.append(" select r.id from Role r inner join r.users u where u.userId = '"+userId+"') order by p.module.code asc");
		
		return this.find(hql.toString());
	}

	/* (non-Javadoc)
	 * 获取用户的所有操作权限（第三级模块的操作权限）  用于控制页面中的按钮的显示于隐藏
	 */
	@Override
	public List<String> findUserOperas(String userId) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		//查询权限表根据角色获取权限信息
		hql.append(" select distinct p.opera.code from Popedom p where p.role.id in ( ");
		//根据用户的账号，获取用户拥有哪些角色
		hql.append(" select r.id from Role r inner join r.users u where u.userId = '"+userId+"') order by p.module.code asc");
		
		return this.find(hql.toString());
	}

}
