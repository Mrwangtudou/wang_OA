package com.wang.lms.notice.service.impl;


import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.wang.lms.identity.bean.User;
import com.wang.lms.notice.bean.Notice;
import com.wang.lms.notice.dao.NoticeDetailDao;
import com.wang.lms.notice.service.NoticeService;
import com.wang.lms.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("noticeService")
@Transactional(readOnly=true,rollbackFor= Exception.class)
public class NoticeServiceImpl implements NoticeService {

	
	@Resource(name="noticeDetailDao")
	private NoticeDetailDao noticeDetailDao;
	
	
	/* (non-Javadoc)
	 * 添加公告
	 */
	@Transactional(readOnly=false)
	@Override
	public void save(Notice notice, HttpSession session) {
		// TODO Auto-generated method stub
		try {
			//设置创建人与创建时间
			User creater = (User)session.getAttribute(ConstantUtil.SESSION_USER);
			notice.setCreater(creater);
			notice.setCreateDate(new Date());
			noticeDetailDao.save(notice);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("保存失败！");
		}
	}

	

}
