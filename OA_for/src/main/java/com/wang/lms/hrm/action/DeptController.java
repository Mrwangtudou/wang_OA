package com.wang.lms.hrm.action;

import java.util.List;

import com.wang.lms.hrm.bean.Dept;
import com.wang.lms.hrm.service.HrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/hrm")
public class DeptController {
	
	@Autowired
	//@Qualifier("hrmService")
	private HrmService hrmService;
	
	//获取所有的部门信息
	@RequestMapping("/getAllDept.jspx")
	public String getAllDept(){

		try {
			
			List<Dept> depts = hrmService.getAllDepts();
			System.out.println("部门数量："+depts.size());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
