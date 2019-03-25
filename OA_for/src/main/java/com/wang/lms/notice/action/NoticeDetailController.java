package com.wang.lms.notice.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wang.lms.common.page.PageModel;
import com.wang.lms.hrm.bean.Dept;
import com.wang.lms.hrm.service.HrmService;
import com.wang.lms.identity.bean.User;
import com.wang.lms.identity.service.IdentityService;
import com.wang.lms.notice.bean.Notice;
import com.wang.lms.notice.service.NoticeService;
import com.wang.lms.util.ConstantUtil;
import com.wang.lms.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/noticeManager/noticeDetail")
public class NoticeDetailController {
	

	@Resource(name="noticeService")
	private NoticeService noticeService;
	
	
	//跳转至公告明细列表页面
	@RequestMapping("/selectNoticeDetail.jspx")
	public  String selectNoticeDetail(){
		
		return "notice/noticeDetail/list";
	}
	
	//跳转至添加公告明细页面
	
	@RequestMapping("/showAddNoticeDetail.jspx")
	public  String showAddNoticeDetail(){
		
		return "notice/noticeDetail/addNotice";
	}
	
	//添加公告
	@RequestMapping("/addNoticeDetail.jspx")
	public  String addNoticeDetail(Model model,Notice notice,HttpSession session){
		try {
			
			noticeService.save(notice,session);
			model.addAttribute("message","添加成功！");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}
		
		return "notice/noticeDetail/addNotice";
	}
}
