<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 引入分页标签 -->
 <%@ taglib prefix="fk" uri="/pageTag-j1708"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OA办公管理系统-公告管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
   <link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
	src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
<script type="text/javascript">

      $(function(){
    	  //未添加用户按钮绑定点击事件
			 $("#addNotice").bind("click",function(){
				 $("#divDialog").dialog({
						title : "添加公告明细", // 标题
						cls : "easyui-dialog", // class
						width :780, // 宽度
						height : 450, // 高度
						maximizable : true, // 最大化
						minimizable : false, // 最小化
						collapsible : true, // 可伸缩
						modal : true, // 模态窗口
						onClose : function(){ // 关闭窗口
							//window.location = "${ctx}/identity/role/selectRole.jspx?pageIndex=${pageModel.pageIndex}";
						
						}
					});
				 
				 //通过ifame引入一个添加用户的jsp进来
				 $("#iframe").prop("src","${ctx}/noticeManager/noticeDetail/showAddNoticeDetail.jspx");
		
			 })
      })

</script>

</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
	<div>
	
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<!-- <div style="padding: 5px;">
				<a  id="addEmp" class="btn btn-info">添加</a>
				<a  id="deleteEmp" class="btn btn-danger">删除</a>
				<a  id="updateEmp" class="btn btn-danger">修改</a>
			</div> -->
				<!-- 工具按钮区 -->
		<form class="form-horizontal"
			action="${ctx }/notice/detail/selectNoticeList.jspx" method="post"
			style="margin-top: 5px;">
			<div class="form-group">
				<div class="col-sm-2">
					<input name="title" type="text" value="${notice.title}" class="form-control" placeholder="标题">
				</div>
				<div class="col-sm-3">
					<fk:operas name="noticeDetail:selectNoticeDetail"> <button type="submit" id="selectNotice" class="btn btn-info">查询</button></fk:operas>
					<fk:operas name="noticeDetail:addNoticeDetail"><a id="addNotice" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a> </fk:operas>
					<fk:operas name="noticeDetail:deleteNoticeDetail"><a id="deleteNotice" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a></fk:operas>
				</div>
			</div>
		
		</form>
			
			<div class="panel-body">
				<table class="table table-bordered" style="float: right;">
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;"><input  id="checkAll" type="checkbox" /></th>
							<th style="text-align: center;">公告名称</th>
							<th style="text-align: center;">公告类型</th>
							<th style="text-align: center;">创建者</th>
							<th style="text-align: center;">创建时间</th>
							<th style="text-align: center;">修改者</th>
							<th style="text-align: center;">修改时间</th>
							<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					  
                  <c:forEach items="${requestScope.notices}" var="notice" varStatus="stat">
                    <tr id="dataTr_${stat.index}" align="center">
					<td><input type="checkbox" name="box" id="box_${stat.index}" value="${notice.id}"/></td>
					<td>${notice.title}</td>
					<td>${notice.noticeType.type}</td>
					<td>${notice.creater.name}</td>
					<td><fmt:formatDate value="${notice.createDate}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>
					<td>${notice.modifier.name}</td>
					<td><fmt:formatDate value="${notice.modifyDate}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>
                    <td>
                        <fk:operas name="noticeDetail:preNoticeDetail"><span class="label label-success"><a href="#" onclick="lookNotice('${employee.id}')" style="color: white;">查看</a></span>&nbsp;</fk:operas>
						<fk:operas name="noticeDetail:updateNoticeDetail"><span class="label label-info"><a href="#" onclick="updateNotice('${notice.id}')"  style="color: white;">修改</a></span></fk:operas>
                    </td>
				   </tr>
          
                 </c:forEach>	
          
				</table>
					<!-- 分页标签区 -->
				
			</div>
			
		</div>
	</div>
    <!-- div作为弹出窗口 -->
    <div id="divDialog" style="overflow: hidden;">
		<iframe id="iframe" scrolling="no" frameborder="0" width="100%" height="100%"></iframe>
	</div>
	
</body>
</html>