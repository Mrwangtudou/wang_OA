package com.wang.lms.hrm.dao.impl;

import java.util.List;
import java.util.Map;

import com.wang.lms.common.baseDao.impl.BaseDaoImpl;
import com.wang.lms.hrm.dao.DeptDao;
import org.springframework.stereotype.Repository;


@Repository("deptDao")
public class DeptDaoImpl extends BaseDaoImpl implements DeptDao {

	/* (non-Javadoc)
	 * 获取部门信息   id   name
	 */
	@Override
	public List<Map<Long, String>> findAllDepts() {
		// TODO Auto-generated method stub
		String hql = "select new Map(d.id as id,d.name as name) from Dept d order by id asc";
		return this.find(hql);
	}

}
