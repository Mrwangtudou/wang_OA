package com.wang.lms.identity.dao;

import java.util.List;

import com.wang.lms.common.baseDao.BaseDao;
import org.springframework.stereotype.Repository;


public interface PopedomDao extends BaseDao{

	//获取用户拥有哪些一级二级模块的操作权限
	List<String> findMenuOperas(String userId);

	//获取用户的所有操作权限（第三级模块的操作权限）  用于控制页面中的按钮的显示于隐藏
	List<String> findUserOperas(String userId);

	

}
