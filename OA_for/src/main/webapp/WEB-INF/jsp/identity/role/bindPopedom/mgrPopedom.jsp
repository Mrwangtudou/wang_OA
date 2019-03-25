<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>办公管理系统-菜单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta name="Keywords" content="keyword1,keyword2,keyword3" />
<meta name="Description" content="网页信息的描述" />
<meta name="Author" content="fkjava.org" />
<meta name="Copyright" content="All Rights Reserved." />
<link href="${ctx}/fkjava.ico" rel="shortcut icon" type="image/x-icon" />

<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
<script type="text/javascript" src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/dtree/dtree.css"/>
<script type="text/javascript" src="${ctx}/resources/dtree/dtree.js"></script>

<script type="text/javascript">
   
$(function(){
	 d = new dTree('d',"${ctx}");

	 d.add(-2,-1,'系统模块树');
	 d.add(0,-2,'全部','${ctx}/identity/module/selectModuleByParentCode.jspx?code=',"全部","rightFrame");

	 $.ajax({
		type:"post",
		dataType:"json",
		url:"${ctx}/identity/module/ajaxLoadModule.jspx",
		success:function(data){
            $.each(data,function(){
           	//第一个参数：id(必须唯一)  第二个参数：pid(父节点id)  第三个参数：name（节点名字)  第四个参数：url（跳转地址) 第五个参数：title（标题)  第六个参数：target(跳转的目标位置))
           	  if(this.code.length != 12){
               	 d.add(this.code,this.pCode,this.name,'${ctx}/identity/popedom/selectThirdModuleByParentCode.jspx?pCode='+this.code+"&roleId=${role.id}",this.name,"rightFrame"); 
           	  }
            
            })			
			
			
			 $("#tree").html(d.toString());

		},error:function(){
			$.messager.alert("错误提示","网络异常！","warning");
		}
		 
	 })
	 

	 
})    


</script>
</head>
    <body class="easyui-layout" style="width:100%;height:100%;">
			<div id="tree" data-options="region:'west'" title="菜单模块树" style="width:20%;padding:10px">
				 <!-- 展示所有的模块树  -->
			</div>
			
			<div data-options="region:'center'" title="模块菜单"  >
			     <!-- 展示当前模块下的子模块  -->
			     <iframe scrolling="auto"  frameborder="0" id="sonModules"  width="100%" height="100%" name="rightFrame"></iframe>
			</div>
	</body>
</html>
