$(function(){
	
	layui.use([ 'layer', 'form', 'laydate', 'notice' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		
	//var data = JSON.parse(sessionStorage.getItem("liveWay"))//读取数据语法：====获取数据并将数据装换成json格式
	
	$.ajax({
   		 type:'get',
   		 url:'/YhUserPersonageLive/selectLive',
   		 data:{userCode:userCode},
   		success : function(data){
   			if(data.exercise!=null && data.exercise!=""){
   				if(data.exercise == 1){
   					$("#never").show();
   				}else if(data.exercise == 2){
   					$("#never").show();
   				}else if(data.exercise == 3){
   					$("#never").hide();
   				}
   				
   			}
   			
   			if(data.smoke == 1){
   				$("#smokeHide").hide();
   				$("#smokeHide2").hide();
   				$("#smokeHide3").hide();
   			}else if(data.smoke == 2){
   				$("#smokeHide").show();
   				$("#smokeHide2").show();
   				$("#smokeHide3").hide();
   			}else if(data.smoke == 3){
   				$("#smokeHide").hide();
   				$("#smokeHide2").hide();
   				$("#smokeHide3").show();
   			}
   			if(data.harm == 1){
   				$("#harmhide").show();
   				
   			}else if(data.harm == 2){
   				$("#harmhide").hide();
   			}
   			

   			
   			form.val('liveFrom', {
   					"id" : data.id,
   					"days" : data.days,
   					"dietsup":data.dietsup,
   					"smoke":data.smoke,
   					"harm":data.harm,
   					"sinsist" : data.sinsist,
   					"have" : data.have,
   					"exercise":data.exercise,
   					"average" : data.average,
   					"age" : data.age,
   					"quitsmokingtopic" : data.quitsmokingtopic,
   					"obtain" : data.obtain,
   					"specificsj" : data.specificsj,
   					"defend":data.defend,
   					
   					
   				});

   			//复选框
   			if(data.dietsup!=null && data.dietsup!=""){
   				var arrayDietary = data.dietsup.split(",");
   				$("#dietary input").each(function(index, domEle) {
   					var checkbox = $(this).val();
   					
   					for (var i = 0; i < arrayDietary.length; i++) {
   						if (checkbox == arrayDietary[i]) {
   							$(this).attr("checked", true)
   						}
   					
   					}
   				})
   				form.render();
   			}
   			//危害因素
   			if(data.harmelement!=null && data.harmelement!=""){
				var arrayharmelement = data.harmelement.split(",")
				 for(let i in arrayharmelement){
					 	$("#harmelement input[value='"+arrayharmelement[i]+"']").prop("checked",true);
				 	} 
   			form.render();
			}
   			
   			var exercise = data.exercise;
   			$("#exerciseArr input").each(function(index, domEle) {
   				var radios = $(this).val();
   					if (radios == exercise) {
   						$(this).attr("checked", true)
   					}
   					
   			})
   			//吸烟情况
   			var smoke = data.smoke;
   			$("#smokeArr input").each(function(index, domEle) {
   				var radios = $(this).val();
   					if (radios == smoke) {
   						$(this).attr("checked", true)
   					}
   					
   			})
   			
   			//职业病危害
   			var harm = data.harm;
   			$("#harmArr input").each(function(index, domEle) {
   				var radios = $(this).val();
   					if (radios == harm) {
   						$(this).attr("checked", true)
   					}
   					
   			})
   			
   			//防护措施
   			var defend = data.defend;
   			$("#entrench input").each(function(index, domEle) {
   				var radios = $(this).val();
   					if (radios == defend) {
   						$(this).attr("checked", true)
   					}
   					
   			})
   			
   			 	//监听脑血管疾病复选框
		   	 	form.on('checkbox(harmelement)', function (data) {
//	   				           console.log(data.elem.name); //得到name
//	   				           console.log(data.elem.checked); //是否被选中，true或者false
//	   				           console.log(data.value); 
//	   				           console.info('111',data)
	   				           	
	   				        // 如果选中未见异常
	   				        if(data.value == 1 && data.elem.checked){
	   				        	// 把其他选项设为不选中
	   				        	$("input[name='harmelement'][value!='1']").prop("checked", false);
	   				        // 如果选中其他选项
	   				        }else if(data.value != 1 && data.elem.checked){
	   				        	// 把未见异常设为不选中
	   				        	$("input[name='harmelement'][value='1']").prop("checked", false);
	   				        }
	   				        form.render();
	   				})
   			
   		}
   	})
	
	
	
	
			
			//单选框点击事件（括号里边是lay-filter的值）
		　　form.on('radio(exercise)', function (data) {
			if(data.value == 1){
				$("#never").show();
			}else if(data.value == 2){
				$("#never").show();
			}else if(data.value == 3){
				$("#never").hide();
			}
		　});　
	
	/*else{
		$("#smoking").show();
	}*/
	
	//单选框点击事件（括号里边是lay-filter的值）
	　　form.on('radio(smoke)', function (data) {
		if(data.value == 1){
			$("#smokeHide").hide();
			$("#smokeHide2").hide();
			$("#smokeHide3").hide();
		}else if(data.value == 2){
			$("#smokeHide").show();
			$("#smokeHide2").show();
			$("#smokeHide3").hide();
		}else if(data.value == 3){
			$("#smokeHide").hide();
			$("#smokeHide2").hide();
			$("#smokeHide3").show();
		}
	　});　
	//职业
	　form.on('radio(harm)', function (data) {
		if(data.value == 1){
			$("#harmhide").show();
			
		}else if(data.value == 2){
			$("#harmhide").hide();
		}
	　});　
	
	
	//监听提交
	form.on('submit(saveLive)', function(data) {
		 var dietsup =[]; 
		 var harmelement =[]; 
			$('input[name="dietsup1"]:checked').each(function(index, domEle){ 
				dietsup.push($(this).val()); 
			});
			$('input[name="harmelement"]:checked').each(function(index, domEle){ 
				harmelement.push($(this).val()); 
			});
			data.field.dietsup = dietsup.join(",");//将数组合并成字符串
			data.field.harmelement = harmelement.join(",");//将数组合并成字符串
		$.ajax({
			type : 'post',
			url : '/YhUserPersonageLive/updateLive',
			data : data.field,
			traditional: true, //是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!!
			success : function(data) {
				form.val('liveFrom', {
	  				"id":data.id
	  			});
				if (data != null) {
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