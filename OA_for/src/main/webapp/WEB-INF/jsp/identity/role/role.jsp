<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 引入日期格式化标签库 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

    
<!-- 引入分页标签 -->
 <%@ taglib prefix="fk" uri="/pageTag-j1708"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-角色管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${ctx}/fkjava.ico" rel="shortcut icon" type="image/x-icon" />
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
	
	<!-- 引入操作列表页面 css样式的js文件 -->
<script type="text/javascript"  src="${ctx}/js/operaPageCss.js"></script>

	<!-- 引入分页样式 -->
    <link rel=stylesheet type=text/css href="${ctx}/css/pager.css"/>

	<script type="text/javascript">
	
	
	
	$(function(){
		
		if("${message}"){
			
			$.messager.show({
				title:'温馨提示',
				msg:"<font color='red'>${message}</font>",
				showType:'show',
				timeout:3000
			});
		}
		 
		 
		 
		  //获取所有的子checkbox
          var boxes = $("input[id^='box_']");
		 //为删除按钮绑定事件
		 $("#deleteRole").on("click",function(){
			var checkedBoxes = boxes.filter(":checked");
			if(checkedBoxes.length == 0){
	   		  $.messager.alert('错误提示',"请选择需要删除的角色信息！",'warning');
			}else{
				
				
				$.messager.confirm('删除提示', "您是否确认删除该记录？", function(flag){
					if (flag){
						//定义数组用于存放删除删除的用户账号
						var array = new Array();
						
						for(var i=0;i<checkedBoxes.length;i++){
							array.push(checkedBoxes[i].value);
						}
						//发送请求至后台删除用户信息
						window.location = "${ctx}/identity/role/deleteRole.jspx?roleIds="+array+"&pageIndex=${pageModel.pageIndex}";
						
					}
				});
				
			
			}
			 
			 
		 })
		 
		 
			 //未添加用户按钮绑定点击事件
			 $("#addRole").bind("click",function(){
				 $("#divDialog").dialog({
						title : "添加角色", // 标题
						cls : "easyui-dialog", // class
						width : 580, // 宽度
						height : 310, // 高度
						maximizable : true, // 最大化
						minimizable : false, // 最小化
						collapsible : true, // 可伸缩
						modal : true, // 模态窗口
						onClose : function(){ // 关闭窗口
							window.location = "${ctx}/identity/role/selectRole.jspx?pageIndex=${pageModel.pageIndex}";
						}
					});
				 
				 //通过ifame引入一个添加用户的jsp进来
				 $("#iframe").prop("src","${ctx}/identity/role/showAddRole.jspx");
		
			 })
			 
		
	})
	 
	


	//修改角色
	function updateRole(roleId){
	$("#divDialog").dialog({
			title : "修改角色", // 标题
			cls : "easyui-dialog", // class
			width : 580, // 宽度
			height : 310, // 高度
			maximizable : true, // 最大化
			minimizable : false, // 最小化
			collapsible : true, // 可伸缩
			modal : true, // 模态窗口
			onClose : function(){ // 关闭窗口
				window.location = "${ctx}/identity/role/selectRole.jspx?pageIndex=${pageModel.pageIndex}";
			}
		});
	
	   //通过ifame引入一个更新用户的jsp进来
	   $("#iframe").prop("src","${ctx}/identity/role/showUpdateRole.jspx?roleId="+roleId);
	}
		 
	

	</script>
</head>
<body style="overflow: hidden;width: 100%;height: 100%;padding: 5px;">
	<div>
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<div style="padding-top: 4px;padding-bottom: 4px;">
				<a  id="addRole" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
				<a  id="deleteRole" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
			</div>
			
			<div class="panel-body">
				<table class="table table-bordered" style="float: right;">
					<thead>
					    <tr>
						<th style="text-align: center;"><input type="checkbox" id="checkAll"/></th>
						<th style="text-align: center;">名称</th>
						<th style="text-align: center;">备注</th>
						<th style="text-align: center;">操作</th>
						<th style="text-align: center;">创建日期</th>
						<th style="text-align: center;">创建人</th>
						<th style="text-align: center;">修改日期</th>
						<th style="text-align: center;">修改人</th>
						<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					
					
					<c:forEach items="${roles}" var="role" varStatus="stat">
				         <tr align="center" id="dataTr_${stat.index}">
							<td><input type="checkbox" name="box" id="box_${stat.index}" value="${role.id}"/></td>
							<td>${role.name}</td>
							<td>${role.remark}</td>
							<td><span class="label label-success"><a href="${ctx}/identity/role/selectRoleUser.jspx?id=${role.id}" style="color: white;">绑定用户</a></span>&nbsp;
								<span class="label label-info"><a href="${ctx}/identity/popedom/mgrPopedom.jspx?id=${role.id}" style="color: white;">绑定操作</a></span></td>
						    <td>  
						    <fmt:formatDate value="${role.createDate}" pattern="yyyy年MM月dd日  HH:mm:ss"/></td>
							<td>${role.creater.name}</td>
							<td><fmt:formatDate value="${role.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${role.modifier.name}</td>
							<td>   <span class="label label-info"><a href="javascript:updateRole('${role.id}')">修改</a></span></td>
						</tr>
		   			 </c:forEach>
				</table>
			</div>
			
			<!-- 分页标签区 -->
			<fk:pager pageIndex="${pageModel.pageIndex}"  totalSize="${pageModel.recordCount}" pageSize="${pageModel.pageSize}"  submitUrl="${ctx}/identity/role/selectRole.jspx?pageIndex={0}" ></fk:pager>

		</div>
	</div>
    <!-- div作为弹出窗口 -->
    <div id="divDialog" style="overflow: hidden;">
		<iframe id="iframe"  scrolling="no" frameborder="0" width="100%" height="100%" ></iframe>
	</div>
	
</body>
</html>