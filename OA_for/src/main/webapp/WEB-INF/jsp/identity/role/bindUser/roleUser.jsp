<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	

<!-- 引入分页标签 -->
 <%@ taglib prefix="fk" uri="/pageTag-j1708"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OA办公管理系统-用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet"
	href="${ctx }/resources/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
<!-- 导入bootStrap的库 -->
<script type="text/javascript"
	src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
	
<!-- 引入操作列表页面 css样式的js文件 -->
<script type="text/javascript"  src="${ctx}/js/operaPageCss.js"></script>

<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
	
<!-- 引入分页样式 -->
<link rel=stylesheet type=text/css href="${ctx}/css/pager.css"/>
    
<script type="text/javascript">

      $(function(){
    	  
    	  if("${message}"){
  			
  			parent.$.messager.show({
  				title:'温馨提示',
  				msg:"<font color='red'>${message}</font>",
  				showType:'show',
  				timeout:3000
  			});
  		}
    	  
    	  $("#unBindUser").click(function(){
    		  //获取需要解除的用户信息
    		  //var boxes = $("input[id^='box_']").filter(":checked");
    		  var boxes = $("input[id^='box_']:checked");
    		  if(boxes.length == 0){
    			  $.messager.alert("错误提示","请选择需要解除的用户信息！","warning");
    		  }else{
    			  
    			  var arr= new Array();
    			  for(var i=0;i<boxes.length;i++){
    				  arr.push(boxes[i].value);
    			  }
    			  
    			  //发送请求解除用户  用户的id 角色id 页码
    			  window.location = "${ctx}/identity/role/unbindRole.jspx?userIds="+arr+"&id=${role.id}&pageIndex=${pageModel.pageIndex}";
    		  }
    	  })
    	  
    	  
    	    $("#bindUser").click(function(){
    	    	
    	    	 $("#divDialog").dialog({
						title : "绑定用户", // 标题
						cls : "easyui-dialog", // class
						width : 850, // 宽度
						height : 410, // 高度
						maximizable : true, // 最大化
						minimizable : false, // 最小化
						collapsible : true, // 可伸缩
						modal : true, // 模态窗口
						onClose : function(){ // 关闭窗口
							window.location = "${ctx}/identity/role/selectRoleUser.jspx?pageIndex=${pageModel.pageIndex}&id=${role.id}";
						}
					});
				 
				 //通过ifame引入一个添加用户的jsp进来
				 $("#iframe").prop("src","${ctx}/identity/role/showUnBindUser.jspx?roleId=${role.id}");
    	    })
      })
     
</script>
</head>
<body style="overflow: hidden; width: 98%; height: 100%;" >
		<!-- 工具按钮区 -->
		
 		<div class="panel panel-primary" style="padding-left: 5px;">
 			<div style="padding-top: 4px;padding-bottom: 4px;">
				<a  id="back" class="btn btn-primary" onclick="history.go(-1)"><span class="glyphicon glyphicon-hand-left"></span>&nbsp;返回</a>
				<a  id="bindUser" class="btn btn-success"><span class="glyphicon glyphicon-copy"></span>&nbsp;绑定用户</a>
				<a  id="unBindUser" class="btn btn-danger"><span class="glyphicon glyphicon-paste"></span>&nbsp;解绑用户</a>
			    
			 
			</div>
 			<div class="panel-heading" style="background-color: #11a9e2;">
				<h3 class="panel-title"><label style="color: white;font-size: 15px;"><span class="glyphicon glyphicon-user"></span>&nbsp;${role.name }</label></h3>
			</div>
			<div class="panel-body" >
				<table class="table table-bordered">
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;"><input id="checkAll"
								type="checkbox" /></th>
							<th style="text-align: center;">账户</th>
							<th style="text-align: center;">姓名</th>
							<th style="text-align: center;">性别</th>
							<th style="text-align: center;">部门</th>
							<th style="text-align: center;">职位</th>
							<th style="text-align: center;">手机号码</th>
							<th style="text-align: center;">邮箱</th>
							<th style="text-align: center;">审核人</th>
						</tr>
					</thead>
					<c:forEach items="${requestScope.users}" var="user"
						varStatus="stat">
						<tr id="dataTr_${stat.index}" align="center">
							<td><input type="checkbox" name="box" id="box_${stat.index}"
								value="${user.userId}" /></td>
							<td>${user.userId}</td>
							<td>${user.name}</td>
							<td>${user.sex == 1 ? '男' : '女' }</td>
							<td>${ user.dept.name}</td>
							<td>${ user.job.name}</td>
							<td>${user.phone}</td>
							<td>${user.email}</td>
							<td>${user.checker.name}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<!-- 分页标签区 -->
			<fk:pager pageIndex="${pageModel.pageIndex}"  totalSize="${pageModel.recordCount}" pageSize="${pageModel.pageSize}"  submitUrl="${ctx}/identity/role/selectRoleUser.jspx?pageIndex={0}&id=${role.id}" ></fk:pager>
		</div>
		
		<div id="divDialog">
			 <!-- 放置一个添加用户的界面  -->
			 <iframe id="iframe" frameborder="0" style="width: 100%;height: 100%;"></iframe>
		</div>
	
</body>
</html>