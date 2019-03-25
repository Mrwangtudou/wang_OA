<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html> 
<html lang="en"> 
<head> 
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
    <title>捷途软件--智能办公</title> 
    <link href="${ctx}/css/base.css" rel="stylesheet">
    <link href="${ctx}/css/login.css" rel="stylesheet">
    <link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
     <script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
     <script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
     
     
     <link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
	 <script type="text/javascript">
	 
	   
	 
	    $(function(){
	    	//window.location：当前窗口的位置     parent：父级   top:最顶级
	    	if(window.location != top.window.location){
	    		//如果当前页面 login.jsp不是最定义窗口，将login.jsp作为顶级窗口
	    		top.window.location = window.location;
	    	}
	    	
	    	if("${message}"){
	    		alert("${message}");
	    	}
	    	
	    	//为验证码绑定点击事件
	    	$("#vimg").click(function(){
	    		
	    		//重新改变 验证码对应的img的src路径，重新生成一张新的验证码 ，需要注意，如果src中的路径不发生变化，则浏览器不会请求后台
	    		//$("#vimg").prop("src","${ctx}/createCode.jspx?randomData="+Math.random());
	    		//this：dom对象
	    		//$(this).prop("src","${ctx}/createCode.jspx?randomData="+Math.random());
	    		//通过js改变属性
	    		this.src = "${ctx}/createCode.jspx?randomData="+Math.random();
	    		
	    	})
	    	
	    	
	    	//为登录按钮绑定事件 ---    异步登录
	    	$("#login_id").on("click",function(){
	    		
	    		//获取账号
	    		var account = $("#userId").val();
	    		//获取密码
	    		var pass = $("#passWord").val();
	    		//获取验证码
	    		var vcode = $("#vcode").val();
	    		
	    		//通过正则表达式校验用户的信息
	    		
	    		if(!/^[0-9a-zA-Z]{5,10}$/.test(account)){
	    			$.messager.alert('错误提示',"您输入的账号格式不正确！",'warning');
	    			//清空信息
	    			$("#userId").val("");
	    			//光标聚焦
	    			$("#userId").focus();
	    		}else if(!/^[0-9a-zA-Z]{5,10}$/.test(pass)){
	    			$.messager.alert('错误提示',"您输入的密码格式不正确！",'warning');
	    			//清空信息
	    			$("#passWord").val("");
	    			//光标聚焦
	    			$("#passWord").focus();
	    		}else if(!/^[0-9a-zA-Z]{4}$/.test(vcode)){
	    			$.messager.alert('错误提示',"您输入的验证码格式不正确！",'warning');
	    			//清空信息
	    			$("#vcode").val("");
	    			//光标聚焦
	    			$("#vcode").focus();
	    		}else{
	    			//准备异步校验|异步登录
	    			
	    			//判断是否记住用户
	    			var flag = $("#rem").prop("checked");//选中 flag等于true
	    			
	    			$.ajax({
	    				  type:"post",//请求方式
	    				  url:"${ctx}/identity/user/ajaxLogin.jspx",//请求地址
	    				  //data:"userId="+account+"&passWord="+pass+"vcode="+vcode+"rem="+(flag==true?"1":"0"),//传递至后台的参数
	    				  data:{"userId":account,"passWord":pass,"vcode":vcode,"rem":(flag==true?"1":"0")},//传递至后台的参数
	    				  datatype:"text", //服务器|java后台响应至页面的数据类型
	    				  success:function(msg){ //后台成功响应时，回调函数
	    					  
	    					  //隐式boolean类型  非空字符窜 可以作为 true处理
                             if(msg){
                            	 //将提示信息弹出给用户
                            	 $.messager.alert('错误提示',msg,'warning');
                            	 //刷新验证码  方式1
                            	 //$("#vimg").prop("src","${ctx}/createCode.jspx?randomData="+Math.random());
                                 //刷新验证码  方式2
                                 $("#vimg").trigger("click");//触发验证码的点击事件
                             }else{
                            	 //跳转至首页
                            	 window.location = "${ctx}/main.jspx";
                             }

	    				  },error:function(){//后台响应失败时，回调函数
	    		    		  $.messager.alert('错误提示',"网络异常",'warning');

	    				  }
	    				 })
	    			
	    			
	    		}
	    		
	    	})
	    	
	    })
	    
	    
	    //按回车键 进行异步登录
	    $(window).keydown(function(event){
	    	if(event.keyCode == 13){
	    		//用户按回车键 进行异步登录
	    		//触发登录按钮的点击事件
	    		$("#login_id").trigger("click");
	    	}
 
         });
	 
		
	 </script>
</head> 
<body>
	<div class="login-hd">
		<div class="left-bg"></div>
		<div class="right-bg"></div>
		<div class="hd-inner">
			<span class="logo"></span>
			<span class="split"></span>
			<span class="sys-name">智能办公平台</span>
		</div>
	</div>
	<div class="login-bd">
		<div class="bd-inner">
			<div class="inner-wrap">
				<div class="lg-zone">
					<div class="lg-box">
						<div class="panel-heading" style="background-color: #11a9e2;">
							<h3 class="panel-title" style="color: #FFFFFF;font-style: italic;">用户登陆</h3>
						</div>
						<form id="loginForm">
						   <div class="form-horizontal" style="padding-top: 20px;padding-bottom: 30px; padding-left: 20px;">
								
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<input class="form-control" id="userId" name="userId" type="text" placeholder="账号/邮箱">
									</div>
								</div>
				
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<input  class="form-control"  id="passWord" name="passWord" type="password" placeholder="请输入密码">
									</div>
								</div>
				
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<div class="input-group">
										<input class="form-control " id="vcode" maxlength="4"  name="vcode" type="text" placeholder="验证码">
										<span class="input-group-addon" id="basic-addon2"><img class="check-code" id="vimg" width="62px" height="20px" style="cursor:pointer" alt="" src="${ctx}/createCode.jspx"></span>
										</div>
									</div>
								</div>
				
						</div>
							<div class="tips clearfix">
											<label><input type="checkbox" id="rem">记住一周</label>
											<a href="javascript:;" class="register">忘记密码？</a>
										</div>
							<div class="enter">
								<a href="javascript:;" id="login_id" class="purchaser" >登录</a>
								<a href="javascript:;" class="supplier" onClick="javascript:window.location='main.html'">重 置</a>
							</div>
						</form>
					</div>
				</div>
				<div class="lg-poster"></div>
			</div>
	</div>
	</div>
	<div class="login-ft">
		<div class="ft-inner">
			<div class="about-us">
				<a href="javascript:;">关于我们</a>
				<a href="javascript:;">法律声明</a>
				<a href="javascript:;">服务条款</a>
				<a href="javascript:;">联系方式</a>
			</div>
			<div class="address">
			地址：广州市天河区车陂大岗路4号,沣宏大厦3011
			&nbsp;邮编：510000&nbsp;&nbsp;
			Copyright&nbsp;©&nbsp;2017&nbsp;-&nbsp;2019&nbsp;没有最好，只有更好&nbsp;版权所有</div>
			<div class="other-info">
			建议使用火狐、谷歌浏览器，不建议使用IE浏览器！</div>
		</div>
	</div>
</body> 
</html>
<script type="text/javascript">

</script>