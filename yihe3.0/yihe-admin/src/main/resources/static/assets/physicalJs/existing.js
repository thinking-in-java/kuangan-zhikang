$(function(){
	
	layui.use([ 'layer', 'form', 'laydate', 'notice' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		
	

	//var data = JSON.parse(sessionStorage.getItem("existing"))//读取数据语法：====获取数据并将数据装换成json格式
				//脑血管
		  $.ajax({
		   		 type:'get',
		   		 url:'/YhUserPersonageExisting/selectExisting',
		   		 data:{userCode:userCode},
		   		success : function(data){

		   			form.val('healthProblemFrom', {
		   					"id" : data.id,
		   					//"rests":data.rests
		   				});
		   			
					if(data.bloodvessel!=null && data.bloodvessel!=""){
						var arrayBloodvessel = data.bloodvessel.split(",")
						
						 for(let i in arrayBloodvessel){
							 	$("#bloodvessel input[value='"+arrayBloodvessel[i]+"']").attr("checked",true);
						 	} 
					}
			
			//肾脏疾病
			if(data.kidney!=null && data.kidney!=""){
				var arraykidney = data.kidney.split(",")
				
				 for(let i in arraykidney){
					 	$("#kidney input[value='"+arraykidney[i]+"']").attr("checked",true);
				 	} 
			}
			
			//心脏疾病
			if(data.heart!=null && data.heart!=""){
				var arrayHeart = data.heart.split(",")
				
				 for(let i in arrayHeart){
					 	$("#heart input[value='"+arrayHeart[i]+"']").attr("checked",true);
				 	} 
			}
			
			//血管疾病
			if(data.blooddisease!=null && data.blooddisease!=""){
				var arrayBlooddisease = data.blooddisease.split(",")
				
				 for(let i in arrayBlooddisease){
					 	$("#blooddisease input[value='"+arrayBlooddisease[i]+"']").attr("checked",true);
				 	} 
			}
			
			//眼部疾病
			if(data.ocular!=null && data.ocular!=""){
				var arrayOcular = data.ocular.split(",")
				
				 for(let i in arrayOcular){
					 	$("#ocular input[value='"+arrayOcular[i]+"']").attr("checked",true);
				 	} 
			}
						
			
			
			//系统疾病
			var nervoussystem = data.nervoussystem;
			var nervouArr = $("input[name='nervoussystem']").val();
					if (nervouArr == nervoussystem) {
						$("#nervouArr").attr("checked", true)
					}
					
						
				
					
					
					//其他疾病
					var rests = data.rests;
					var restsArr = $("input[name='rests']").val();
							if (restsArr == rests) {
								$("#restsArr").attr("checked", true)
						}
				
		   		}
		   	 })
		
		   	 	//监听脑血管疾病复选框
		   	 	form.on('checkbox(vesselCheckbox)', function (data) {
	   				           console.log(data.elem.name); //得到name
	   				           console.log(data.elem.checked); //是否被选中，true或者false
	   				           console.log(data.value); 
	   				           console.info('111',data)
	   				           	
	   				        // 如果选中未见异常
	   				        if(data.value == 1 && data.elem.checked){
	   				        	// 把其他选项设为不选中
	   				        	$("input[name='bloodvessel'][value!='1']").prop("checked", false);
	   				        // 如果选中其他选项
	   				        }else if(data.value != 1 && data.elem.checked){
	   				        	// 把未见异常设为不选中
	   				        	$("input[name='bloodvessel'][value='1']").prop("checked", false);
	   				        }
	   				        form.render();
	   				})
	   				
	   				//监听肾脏疾病管疾病复选框
		   	 	form.on('checkbox(kidneyCheckbox)', function (data) {
	   				           console.log(data.elem.name); //得到name
	   				           console.log(data.elem.checked); //是否被选中，true或者false
	   				           console.log(data.value); 
	   				           console.info('111',data)
	   				           	
	   				        // 如果选中未见异常
	   				        if(data.value == 1 && data.elem.checked){
	   				        	// 把其他选项设为不选中
	   				        	$("input[name='kidney'][value!='1']").prop("checked", false);
	   				        // 如果选中其他选项
	   				        }else if(data.value != 1 && data.elem.checked){
	   				        	// 把未见异常设为不选中
	   				        	$("input[name='kidney'][value='1']").prop("checked", false);
	   				        }
	   				        form.render();
	   				})
	   				
	   					
	   				//监听心脏疾病管疾病复选框
		   	 	form.on('checkbox(heartCheckbox)', function (data) {
	   				           console.log(data.elem.name); //得到name
	   				           console.log(data.elem.checked); //是否被选中，true或者false
	   				           console.log(data.value); 
	   				           console.info('111',data)
	   				           	
	   				        // 如果选中未见异常
	   				        if(data.value == 1 && data.elem.checked){
	   				        	// 把其他选项设为不选中
	   				        	$("input[name='heart'][value!='1']").prop("checked", false);
	   				        // 如果选中其他选项
	   				        }else if(data.value != 1 && data.elem.checked){
	   				        	// 把未见异常设为不选中
	   				        	$("input[name='heart'][value='1']").prop("checked", false);
	   				        }
	   				        form.render();
	   				})
	   				
	   					//监听血管疾病管疾病复选框
		   	 	form.on('checkbox(iseaseCheckbox)', function (data) {
	   				           console.log(data.elem.name); //得到name
	   				           console.log(data.elem.checked); //是否被选中，true或者false
	   				           console.log(data.value); 
	   				           console.info('111',data)
	   				           	
	   				        // 如果选中未见异常
	   				        if(data.value == 1 && data.elem.checked){
	   				        	// 把其他选项设为不选中
	   				        	$("input[name='blooddisease'][value!='1']").prop("checked", false);
	   				        // 如果选中其他选项
	   				        }else if(data.value != 1 && data.elem.checked){
	   				        	// 把未见异常设为不选中
	   				        	$("input[name='blooddisease'][value='1']").prop("checked", false);
	   				        }
	   				        form.render();
	   				})
	   							//监听眼部疾病管疾病复选框
		   	 	form.on('checkbox(ocularCheckbox)', function (data) {
	   				           console.log(data.elem.name); //得到name
	   				           console.log(data.elem.checked); //是否被选中，true或者false
	   				           console.log(data.value); 
	   				           console.info('111',data)
	   				           	
	   				        // 如果选中未见异常
	   				        if(data.value == 1 && data.elem.checked){
	   				        	// 把其他选项设为不选中
	   				        	$("input[name='ocular'][value!='1']").prop("checked", false);
	   				        // 如果选中其他选项
	   				        }else if(data.value != 1 && data.elem.checked){
	   				        	// 把未见异常设为不选中
	   				        	$("input[name='ocular'][value='1']").prop("checked", false);
	   				        }
	   				        form.render();
	   				})
		   	 
		   	 
		   	 //输入框的输入事件
						$('#nervoussystem').bind('input propertychange', function(event) {
							var nervoussystem =  $("#nervoussystem").val();
							$("#nervouArr").attr('checked', false);
							if(nervoussystem ==null || nervoussystem==""){
								$("#nervouArr").prop('checked', true);
								
							}
							form.render();
							
						});
						
						//其他疾病
			$('#rests').bind('input propertychange', function(event) {
				var rests =  $("#rests").val();
				$("input[name='rests']").prop('checked', false);
				if(rests ==null || rests==""){
					$("input[name='rests']").prop('checked', true);
					
				}
				form.render();
				
			});
			
			
	
	
	
	
	

	
	
	
	
	//监听提交
	form.on('submit(healthProblem)', function(data) {
		//获取checkbox数据,组装成数组!!!!!!
	    var bloodvessel =[];
	    var kidney =[];
	    var heart =[];
	    var blooddisease =[];
	    var ocular =[];
		$('input[name="bloodvessel"]:checked').each(function(index, domEle){ 
			bloodvessel.push($(this).val()); 
		});
		data.field.bloodvessel = bloodvessel.join(",");//将数组合并成字符串
		
		$('input[name="kidney"]:checked').each(function(index, domEle){ 
			kidney.push($(this).val()); 
		});
		data.field.kidney = kidney.join(",");//将数组合并成字符串
		
		$('input[name="heart"]:checked').each(function(index, domEle){ 
			heart.push($(this).val()); 
		});
		data.field.heart = kidney.join(",");//将数组合并成字符串
		
		$('input[name="blooddisease"]:checked').each(function(index, domEle){ 
			blooddisease.push($(this).val()); 
		});
		data.field.blooddisease = blooddisease.join(",");//将数组合并成字符串
		
		$('input[name="ocular"]:checked').each(function(index, domEle){ 
			ocular.push($(this).val()); 
		});
		data.field.ocular = ocular.join(",");//将数组合并成字符串
		
		$.ajax({
			type : 'post',
			url : '/YhUserPersonageExisting/updateExisting',
			data : data.field,
			traditional: true, //是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!!
			success : function(data) {
				form.val('healthProblemFrom', {
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