package com.wang.lms.identity.dao.impl;

import org.apache.commons.lang.StringUtils;
import com.wang.lms.common.baseDao.impl.BaseDaoImpl;
import com.wang.lms.identity.dao.ModuleDao;
import org.springframework.stereotype.Repository;



@Repository("moduleDao")
public class ModuleDaoImpl extends BaseDaoImpl implements ModuleDao {

	/* (non-Javadoc)
	 * 根据父级模块code获取最大子模块code值
	 */
	@Override
	public String getMaxModuleCodeByPcode(String parentCode) {
		// TODO Auto-generated method stub
		String hql = "SELECT MAX(code) FROM Module WHERE code LIKE ?  AND LENGTH(code) = ?";
		return this.findUniqueEntity(hql, new Object[]{StringUtils.isEmpty(parentCode)?"%":parentCode+"%",StringUtils.isEmpty(parentCode)?4:parentCode.length()+4});
	}

}
