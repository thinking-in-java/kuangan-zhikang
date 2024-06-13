$(function(){
	
	//导航栏
	layui.use('element', function() {
		var $ = layui.jquery, element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块

		$('#nextBtn2').on('click', function() {
			alert(1)
			var othis = $(this), type = othis.data('type');
			active[type] ? active[type].call(this, othis) : '';
		});
		

		$('#upBtn').on('click', function() {
			alert(1)
			var othis = $(this), type = othis.data('type');
			active[type] ? active[type].call(this, othis) : '';
		});

	});
	
	
	
	layui.use([ 'layer', 'form', 'laydate', 'notice' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		
	//var personageData = JSON.parse(sessionStorage.getItem("personage"))//读取数据语法：====获取数据并将数据装换成json格式
	//console.log(personageData.stature+"zzzz")
	$.ajax({
	  		 type:'get',
	  		 url:'/YhUserPersonageCondition/select',
	  		 data:{userCode:userCode},
	  		success : function(data){
	  			form.val('personageForm', {
	  				 "id":data.id,
	  				"heat" : data.heat,
	  				
	  				"habitas" : data.habitas,
	  				"waistline" : data.waistline,
	  				"hipline" : data.hipline,
	  				"waist" : data.waist,
	  				"dbp" : data.dbp,
	  				"sbp" : data.sbp,
	  				"palse" : data.palse,
	  				"breathe" : data.breathe,
	  				"hrrest":data.hrrest
	  				
	  			});
	  			if(data.habitas !=null && data.habitas!=""){
	  				$("#habitas").text(data.habitas);
	  			}
	  			if(data.waist !=null && data.waist!=""){
	  				$("#waist").text(data.waist);
	  			}
	  			
	  		
	  	 }
	});
	
	
	
	
	//身高输入框的属性事件
//	 $('#stature').on('input propertychange',function() {
//			var stature =  $("#stature").val();
//			
//	 });
	 
	//体重输入框的属性事件
//	 $('#weight').on('input propertychange',function() {
//			var weight =  $("#weight").val();
//			calculate()
//			console.log(weight)
//	 });
	 //form.render();
	 //计算bim
	
//	 function calculate(){
//		 var stature = $("#stature").val();
//		 var weight = $("#weight").val();
//		 var bim = weight / ((stature / 100) * (stature / 100));
//		 var bim2 = bim.toFixed(1); 
//		 $("#habitas").text(bim2); 
//		
//	 }
	 
	//腰围输入框的属性事件
	 $('#waistline').on('input propertychange',function() {
			var waistline =  $("#waistline").val();
			
	 });
	 
	//臀围输入框的属性事件
	 $('#hipline').on('input propertychange',function() {
			var hipline =  $("#hipline").val();
			
			WHRcalculate()
			
	 });
	 function WHRcalculate(){
		 var waistline = $("#waistline").val();
		 var hipline = $("#hipline").val();
		 if(hipline==null || hipline ==""){
				var hipline2 = 0.0;
			 $("#waist").text(hipline2.toFixed(1));
			}else{
				 var WHR = waistline / hipline ;
				 var WHR2 = WHR.toFixed(1); 
				 $("#waist").text(WHR2);
			}
		
	 }
	
	//监听提交 
	form.on('submit(saveOrdinary)', function(data) {
		//data.field.habitas = $("#habitas").text();
		data.field.waist = $("#waist").text();
		
		console.log(JSON.stringify(data.field))
		$.ajax({
			type : 'post',
			url : '/YhUserPersonageCondition/update',
			data : data.field,
			//traditional: true, //是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!!
			success : function(data) {
				form.val('personageForm', {
	  				"id":data.id
	  			});
				if (data!=null) {
					layer.msg("操作成功");
				} else {
					layer.msg("操作失败");
				}
			}

		});
		// 阻止表单跳转。如果需要表单跳转，去掉这段即可。
		return false;
	});
	
	
	
	
	});
})