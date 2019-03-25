package com.wang.lms.hrm.dao;

import java.util.List;
import java.util.Map;

import com.wang.lms.common.baseDao.BaseDao;


public interface DeptDao extends BaseDao{

	//获取部门信息   id   name
	List<Map<Long, String>> findAllDepts();

}
