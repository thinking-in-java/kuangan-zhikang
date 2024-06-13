$(function(){
	
	layui.use([ 'layer', 'form', 'laydate', 'notice' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		
	 //var data = JSON.parse(sessionStorage.getItem("viscera"))//读取数据语法：====获取数据并将数据装换成json格式
	
		 $.ajax({
	   		 type:'get',
	   		 url:'/YhUserPersonageViscera/selectViscera',
	   		 data:{userCode:userCode},
	   		success : function(data){
	   			form.val('visceraFrom', {
					"id" : data.id,
					"visionleft":data.visionleft,
					"visionright":data.visionright,
					"correctleft":data.correctleft,
					"correctright":data.correctright
					
				});
	   			
	   			
	   		//口唇
	   			var lips = data.lips;
	   			$("#lipsArr input").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == lips) {
	   						$(this).prop("checked", true)
	   					}
	   			})
	   			//齿列
				var tooth = data.tooth;
				$("#toothArr input").each(function(index, domEle) {
					var radios = $(this).val();
						if (radios == tooth) {
							$(this).prop("checked", true)
						}
			
				})
				//咽部
				var throat = data.throat;
				$("#throatArr input").each(function(index, domEle) {
						var radios = $(this).val();
						if (radios == throat) {
						$(this).prop("checked", true)
					}
			
				})
			
				//听力情况
				var audiometry = data.audiometry;
				$("#audiometryArr input").each(function(index, domEle) {
					var radios = $(this).val();
						if (radios == audiometry) {
							$(this).prop("checked", true)
						}
						
				})
				//运动能力
				var sportssituation = data.sportssituation;
				$("#sportssituationArr input").each(function(index, domEle) {
					var radios = $(this).val();
						if (radios == sportssituation) {
							$(this).prop("checked", true)
						}
						
				})
						form.render();
	   		
	   		
	   		}
	   		 
	   	 })
		

	//监听提交
	form.on('submit(saveViscera)', function(data) {
		
		$.ajax({
			type : 'post',
			url : '/YhUserPersonageViscera/updateViscera',
			data : data.field,
			traditional: true, //是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!!
			success : function(data) {
				form.val('visceraFrom', {
                    "id":data.id
               });
				if (data !=null) {
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