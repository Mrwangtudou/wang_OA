package com.wang.lms.hrm.dao;

import java.util.List;
import java.util.Map;

import com.wang.lms.common.baseDao.BaseDao;


public interface JobDao extends BaseDao{

	//获取职位信息
	List<Map<String, String>> findAllJobs();



}
