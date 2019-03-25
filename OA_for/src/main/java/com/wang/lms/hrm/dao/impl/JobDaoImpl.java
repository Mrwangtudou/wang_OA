package com.wang.lms.hrm.dao.impl;


import java.util.List;
import java.util.Map;

import com.wang.lms.common.baseDao.impl.BaseDaoImpl;
import com.wang.lms.hrm.dao.JobDao;
import org.springframework.stereotype.Repository;


@Repository("jobDao")
public class JobDaoImpl extends BaseDaoImpl implements JobDao {

	/* (non-Javadoc)
	 */
	@Override
	public List<Map<String, String>> findAllJobs() {
		// TODO Auto-generated method stub
		String hql = "select new Map(j.code as code,j.name as name) from Job j order by code asc";
		
		return this.find(hql);
	}

	

}
