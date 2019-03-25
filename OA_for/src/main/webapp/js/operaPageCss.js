(function(){

	
	 $(function(){
    	 
	       //获取所有的子checkbox
	        var boxes = $("input[id^='box_']");
			 
			//为所有的数据行  tr绑定
			$("tr[id^='dataTr_']").hover(
			 function(){
				//触发当前行的mouseover事件,给tr增加背景色
				//$(this): 因为this在此处代表dom对象   $(this):得到this对应的jquery对象
				$(this).css("backgroundColor","#eeccff").css("cursor","pointer");
				
				 //$(this).prop("style","background-color:#eeccff;");
				
				
			 },function(){
				//触发当前行的mouseout事件，去掉tr背景色
				 //当前tr对应的checkbox没有选中才能去掉背景色
				  //获取tr的id
	             var trId = this.id;
	             //获取当前tr对应的checkbox的id
	             var boxId = trId.replace("dataTr","box");
	             
				 if(!$("#"+boxId).prop("checked")){
					 $(this).css("backgroundColor","");
				 }
				
			 }).click(function(){

	             //获取tr的id
	             var trId = this.id;
	             //获取当前tr对应的checkbox的id
	             var boxId = trId.replace("dataTr","box");
	             
	             //通过id选择器操作 checkbox
	             //获取当前tr对应的checkbox的状态
	             //var checked =  $("#"+boxId).prop("checked");
	             //$("#"+boxId).prop("checked",!checked);
	             $("#"+boxId).prop("checked",!$("#"+boxId).prop("checked"));
	             
	             //判断全选是否需要选中
	             //获取选中的子checkbox的个数
				 var length = boxes.filter(":checked").length;
				 //如果子checkbox都选中则全选应该选中，否则全选不选中
				 $("#checkAll").prop("checked",length==boxes.length);
	                 
				 })
				 
				 //为全选绑定点击事件
			 $("#checkAll").click(function(){
				 //this.checked:获取全选的选中状态
				 //操作子checkbox选中状态
				 boxes.prop("checked",this.checked);
			     
			    //触发数据行的mouseover或mouseout事件
				 boxes.trigger(this.checked?"mouseover":"mouseout");
				 
			 })
			 
			 //为所有的子checkbox绑定点击事件
			 boxes.click(function(event){
				 
				 //取消自身事件行为
				 event.stopPropagation();
				 
				 //获取选中的子checkbox的个数
				 var length = boxes.filter(":checked").length;
				 //如果子checkbox都选中则全选应该选中，否则全选不选中
				 $("#checkAll").prop("checked",length==boxes.length);
			 })
	     })  
	
})()
  
  