$(function(){
	
	layui.use([ 'layer', 'form', 'laydate', 'notice' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		
	

	//var data = JSON.parse(sessionStorage.getItem("constitution"))//读取数据语法：====获取数据并将数据装换成json格式
		$.ajax({
	   		 type:'get',
	   		 url:'/YhUserPersonageConstitution/selectConstitution',
	   		 data:{userCode:userCode},
	   		success : function(data){
	   			
	   			//和平
	   			$("input[name=peaceQuality]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.peaceQuality) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			//气虚
	   			$("input[name=deficiencyQuality]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.deficiencyQuality) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			//阳虚
	   			$("input[name=sunQuality]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.sunQuality) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			//阴虚
	   			$("input[name=cloudyQuality]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.cloudyQuality) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			//痰湿质phlegmQuality
	   			$("input[name=phlegmQuality]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.phlegmQuality) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			
	   			//湿热
	   			$("input[name=dampQuality]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.dampQuality) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			//血瘀质
	   			$("input[name=bloodQuality]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.bloodQuality) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			
	   			//气郁质
	   			$("input[name=gasQuality]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.gasQuality) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			
	   			
	   			//血瘀质
	   			$("input[name=specialQuality]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.specialQuality) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			
	   			form.val('corporeityFrom', {
	   				"id" : data.id
	   			});
	   		
	   			
			}
	   	 })
		

	
	
	
	
	
	
	

	
	
	
	
	//监听提交
	form.on('submit(saveCorporeity)', function(data) {
		$.ajax({
			type : 'post',
			url : '/YhUserPersonageConstitution/updateConstitution',
			data : data.field,
			traditional: true, //是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!!
			success : function(data) {
				form.val('corporeityFrom', {
					"id" : data.id
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