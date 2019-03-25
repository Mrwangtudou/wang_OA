package com.wang.lms.hrm.service.impl;

import java.util.List;

import com.wang.lms.hrm.bean.Dept;
import com.wang.lms.hrm.dao.DeptDao;
import com.wang.lms.hrm.service.HrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author CHUNLONG.LUO
 * @email 584614151@qq.com
 * @date 2018年1月20日
 * @version 1.0
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 */
@Service("hrmService")
@Transactional(readOnly=false)
public class HrmServiceImpl implements HrmService {
	
	@Autowired
	@Qualifier("deptDao")
	private DeptDao deptDao;

	/* (non-Javadoc)
	 */
	@Override
	public List<Dept> getAllDepts() {
		// TODO Auto-generated method stub
		return deptDao.find(Dept.class);
	}

}
