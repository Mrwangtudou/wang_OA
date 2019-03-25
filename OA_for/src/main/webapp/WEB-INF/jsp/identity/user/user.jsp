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
<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
<%-- <script type="text/javascript" src="${ctx}/resources/blockUI/jquery.blockUI.js"></script>
 --%><!-- 引入分页样式 -->
<link rel=stylesheet type=text/css href="${ctx}/css/pager.css"/>

<!-- 引入操作列表页面 css样式的js文件 -->
<script type="text/javascript"  src="${ctx}/js/operaPageCss.js"></script>

	
<script type="text/javascript">

   alert("${userOperas}");

  /** 文档加载完成*/
     $(function(){
    	 
    	 if("${message}"){
    		 
    		 $.messager.show({
 				title:'温馨提示',
 				msg:"<font color='red'>${message}</font>",
 				showType:'show',
 				timeout:3000
 			});
    	 }
    	 
 
	   /*  $(document).ajaxStart($.blockUI({ css: { backgroundColor: '#11a9e2', color: '#fff' } , message: '<h6>正在加载..</h6>'})).ajaxStop($.unblockUI);
*/
			 // 激活用户操作
    	  $("input[id^='checkUser_']").switchbutton({
              onChange: function(checked){
            	    var status = checked?"1":"0";
            		window.location = "${ctx}/identity/user/activeUser.jspx?userId="+this.value+"&status="+status
						+"&pageIndex=${pageModel.pageIndex}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}";
              }
          }); 
			 
			 
			 //异步加载部门以及职位信息
			 $.post("${ctx}/identity/user/ajaxLoadDeptAndJob.jspx",function(data){
                 
				  //获取部门信息
				  var depts = data.depts;
				  
				  //获取职位信息
				  var jobs = data.jobs;
				  
				  //获取用户选中的部门以及职位
				  var deptId = "${user.dept.id}";
				  var jobCode = "${user.job.code}";
				  
				  
				  //填充部门信息
				  $.each(depts,function(i,item){
					  //$("#deptSelect").append("<option value='"+item.id+"'>"+item.name+"</option>");
					 // $("<option>").val(item.id).html(item.name).appendTo("#deptSelect");
					  $("<option>").val(this.id).html(this.name).prop("selected",this.id == deptId).appendTo("#deptSelect");
				  })
				  
				   //填充职位信息
				  $.each(jobs,function(i,item){
					 // $("#jobSelect").append("<option value='"+item.code+"'>"+item.name+"</option>");
					 
					  $("<option>").val(this.code).html(this.name).prop("selected",this.code == jobCode).appendTo("#jobSelect");

				  })
				  

			 },"json");
			 
			  //获取所有的子checkbox
		        var boxes = $("input[id^='box_']");
			 
			 //为删除按钮绑定事件
			 $("#deleteUser").on("click",function(){
				var checkedBoxes = boxes.filter(":checked");
				if(checkedBoxes.length == 0){
		    		  $.messager.alert('错误提示',"请选择需要删除的用户信息！",'warning');
				}else{
					
					
					$.messager.confirm('删除提示', "您是否确认删除该记录？", function(flag){
						if (flag){
							//定义数组用于存放删除删除的用户账号
							var array = new Array();
							
							for(var i=0;i<checkedBoxes.length;i++){
								array.push(checkedBoxes[i].value);
							}
							//发送请求至后台删除用户信息
							window.location = "${ctx}/identity/user/deleteUser.jspx?userIds="+array+"&pageIndex=${pageModel.pageIndex}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}";
							
						}
					});
					
				
				}
				 
				 
			 })
			 
			 
			 //未添加用户按钮绑定点击事件
			 $("#addUser").bind("click",function(){
				 $("#divDialog").dialog({
						title : "添加用户", // 标题
						cls : "easyui-dialog", // class
						width : 680, // 宽度
						height : 410, // 高度
						maximizable : true, // 最大化
						minimizable : false, // 最小化
						collapsible : true, // 可伸缩
						modal : true, // 模态窗口
						onClose : function(){ // 关闭窗口
							window.location = "${ctx}/identity/user/selectUser.jspx?pageIndex=${pageModel.pageIndex}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}";
						}
					});
				 
				 //通过ifame引入一个添加用户的jsp进来
				 $("#iframe").prop("src","${ctx}/identity/user/showAddUser.jspx");

			 })
			 
     })
     
      //修改用户
      function updateUser(userId){
    	 $("#divDialog").dialog({
				title : "修改用户", // 标题
				cls : "easyui-dialog", // class
				width : 680, // 宽度
				height : 410, // 高度
				maximizable : true, // 最大化
				minimizable : false, // 最小化
				collapsible : true, // 可伸缩
				modal : true, // 模态窗口
				onClose : function(){ // 关闭窗口
					window.location = "${ctx}/identity/user/selectUser.jspx?pageIndex=${pageModel.pageIndex}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}";
				}
			});
		 
		 //通过ifame引入一个更新用户的jsp进来
		 $("#iframe").prop("src","${ctx}/identity/user/showUpdateUser.jspx?userId="+userId);
      }
  
      //预览用户
      function preUser(userId){
    	  $("#divDialog").dialog({
				title : "预览用户", // 标题
				cls : "easyui-dialog", // class
				width : 780, // 宽度
				height : 410, // 高度
				maximizable : true, // 最大化
				minimizable : false, // 最小化
				collapsible : true, // 可伸缩
				modal : true, // 模态窗口
				
			});
		 
		 //通过ifame引入一个更新用户的jsp进来
		 $("#iframe").prop("src","${ctx}/identity/user/preUser.jspx?userId="+userId+"&name=杨过");
      }
</script>
</head>
<body style="overflow: hidden; width: 98%; height: 100%;" >
   	<!-- 工具按钮区 -->
	<form class="form-horizontal"  action="${ctx}/identity/user/selectUser.jspx" method="post" style="padding-left: 5px;" >
			<table class="table-condensed">
					<tbody>
					<tr>
					<fk:operas name="user:selectUser">
					   <td>
						<input name="name" type="text" class="form-control"
							placeholder="姓名" value="${user.name}" >
						</td>
						<td>	
						<input type="text" name="phone" class="form-control"
							placeholder="手机号码" value="${user.phone}" >
						</td>
<!-- 						<td>	 -->
<!-- 						   <input type="text" class="form-control" placeholder="状态"> -->
<!-- 						</td> -->
						<td>	
						<select class="btn btn-default"
							placeholder="部门" id="deptSelect" name="dept.id">
							<option value="0">==请选择部门==</option>
						</select>
						</td>
						<td>	
						<select class="btn btn-default"
							placeholder="职位" id="jobSelect" name="job.code">
							<option value="0">==请选择职位==</option>
						</select>
						
						</td>
						</fk:operas>
						<td>	
						<fk:operas name="user:selectUser"><button type="submit" id="selectUser"  class="btn btn-info"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></fk:operas>
						<fk:operas name="user:addUser"><a  id="addUser"  class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a></fk:operas> 
					    <fk:operas name="user:deleteUser"><a  id="deleteUser" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a></fk:operas>
					 </td>
					</tr>
					</tbody>
				</table>
		</form>
 		<div class="panel panel-primary" style="padding-left: 10px;">
 			<div class="panel-heading" style="background-color: #11a9e2;">
				<h3 class="panel-title">用户信息列表</h3>
			</div>
			<div class="panel-body" >
				<table class="table table-bordered">
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;">
							<input id="checkAll" type="checkbox"/></th>
							<th style="text-align: center;">账户</th>
							<th style="text-align: center;">姓名</th>
							<th style="text-align: center;">性别</th>
							<th style="text-align: center;">部门</th>
							<th style="text-align: center;">职位</th>
							<th style="text-align: center;">手机号码</th>
							<th style="text-align: center;">邮箱</th>
							<fk:operas name="user:checkUser"><th style="text-align: center;">激活状态</th></fk:operas>
							<th style="text-align: center;">审核人</th>
							<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					
					 <c:forEach items="${users}" var="user" varStatus="stat">
					       <tr id="dataTr_${stat.index}" align="center">
							<td><input type="checkbox" name="box" id="box_${stat.index}" value="${user.userId}"/></td>
							<td>${user.userId}</td>
							<td>${user.name}</td>
							
							<td>${user.sex == 1?'男':'女'}</td>
							
							<td>${user.dept.name}</td>
							<td>${user.job.name}</td>
							<td>${user.phone}</td>
							<td>${user.email}</td>
							<fk:operas name="user:checkUser">
							<td>
							
								 <c:if test="${user.status == 0 }">
									<input id="checkUser_${user.userId }" value="${user.userId }" name="status" class="easyui-switchbutton" data-options="onText:'激活',offText:'冻结'">
								</c:if>
								<c:if test="${user.status == 1 }">
									<input id="checkUser_${user.userId }" value="${user.userId }" name="status" class="easyui-switchbutton" data-options="onText:'激活',offText:'冻结'" checked>
								</c:if>  
								
						    </td>
						    </fk:operas>
								<td>${user.checker.name}</td>
							<td>
							 <fk:operas name="user:updateUser">
							    <span id="updateUser" style="but:updateUser"  class="label label-info operas"><a href="javascript:updateUser('${user.userId}')" style="color: white;">修改</a></span>
							  </fk:operas>
							  <fk:operas name="user:preUser">
							   <span id="preUser" style="but:preUser" class="label label-success operas"><a href="javascript:preUser('${user.userId}')"  style="color: white;">预览</a></span>
							   </fk:operas>
							</td>
						</tr>
					 </c:forEach>
						
						
						
						
				</table>
			</div>
			<!-- 分页标签区 -->
			<fk:pager pageIndex="${pageModel.pageIndex}"  totalSize="${pageModel.recordCount}" pageSize="${pageModel.pageSize}" 
			submitUrl="${ctx}/identity/user/selectUser.jspx?pageIndex={0}&name=${user.name}&phone=${user.phone}&dept.id=${user.dept.id}&job.code=${user.job.code}" >用户分页</fk:pager>
				

		</div>
		
		<div id="divDialog">
			 <!-- 放置一个添加用户的界面  -->
			 <iframe id="iframe" frameborder="0" style="width: 100%;height: 100%;"></iframe>
		</div>
	
</body>
</html>