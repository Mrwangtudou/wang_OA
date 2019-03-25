package com.wang.lms.notice.service;

import javax.servlet.http.HttpSession;

import com.wang.lms.notice.bean.Notice;


public interface NoticeService {

	//添加公告
	void save(Notice notice, HttpSession session);



}
