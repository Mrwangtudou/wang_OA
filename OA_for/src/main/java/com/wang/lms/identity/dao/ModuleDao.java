package com.wang.lms.identity.dao;

import com.wang.lms.common.baseDao.BaseDao;
import org.springframework.stereotype.Repository;


public interface ModuleDao extends BaseDao{

	//根据父级模块code获取最大子模块code值
	String getMaxModuleCodeByPcode(String parentCode);

}
