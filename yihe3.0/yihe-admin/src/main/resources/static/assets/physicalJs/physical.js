$(function(){
	
	layui.use([ 'layer', 'form', 'laydate', 'notice' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		
	 

	//var data = JSON.parse(sessionStorage.getItem("physical"))//读取数据语法：====获取数据并将数据装换成json格式
		
		$.ajax({
	   		 type:'get',
	   		 url:'/YhUserPersonagePhysical/selectPhysical',
	   		 data:{userCode:userCode},
	   		success : function(data){
	   			

	   			form.val('physicalFrom', {
	   					"id" : data.id,
	   					"heartrate" : data.heartrate
	   					
	   				});
	   			
	   		//皮肤
	   			$("input[name=skin]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.skin) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			//巩膜
	   			$("input[name=sclera]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.sclera) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			//淋巴结
	   			$("input[name=lymph]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.lymph) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			//心律
	   			$("input[name=rhthm]").each(function(index, domEle) {
	   				var radios = $(this).val();
	   					if (radios == data.rhthm) {
	   						$(this).prop("checked", true)
	   					}	
	   			});
	   			

	   			
	   			
	   			//杂音
	   				$("input[name='noise']").each(function(index, domEle) {
						var radios = $(this).val();
							if (radios == data.noise) {
								$(this).prop("checked", true)
							}	
					});
	   					//压痛
	   					$("input[name='tenderness']").each(function(index, domEle) {
								var radios = $(this).val();
									if (radios == data.tenderness) {
										$(this).prop("checked", true)
									}	
							});
	   			
	   				
	   					
	   					//包块
	   					$("input[name='enclosedbao']").each(function(index, domEle) {
							var radios = $(this).val();
								if (radios == data.enclosedbao) {
									$(this).prop("checked", true)
								}	
						});
	   						
	   						//肝大
	   						$("input[name='hepatomegalg']").each(function(index, domEle) {
								var radios = $(this).val();
									if (radios == data.hepatomegalg) {
										$(this).prop("checked", true)
									}	
							});
	   					//脾大
	   					$("input[name='selenaaxe']").each(function(index, domEle) {
							var radios = $(this).val();
								if (radios == data.selenaaxe) {
									$(this).prop("checked", true)
								}	
						});
	   						
	   			
	   					//移动性浊音
	   					$("input[name='movethe']").each(function(index, domEle) {
							var radios = $(this).val();
								if (radios == data.movethe) {
									$(this).prop("checked", true)
								}	
						});
	   					
	   					//外阴
	   					$("input[name='gynecology']").each(function(index, domEle) {
							var radios = $(this).val();
								if (radios == data.gynecology) {
									$(this).prop("checked", true)
								}	
						});
	   					//阴道
	   					$("input[name='vagina']").each(function(index, domEle) {
							var radios = $(this).val();
								if (radios == data.vagina) {
									$(this).prop("checked", true)
								}	
						});
	   					//宫颈
	   					$("input[name='cervix']").each(function(index, domEle) {
							var radios = $(this).val();
								if (radios == data.cervix) {
									$(this).prop("checked", true)
								}	
						});
	   					//宫体
	   					$("input[name='corpus']").each(function(index, domEle) {
							var radios = $(this).val();
								if (radios == data.corpus) {
									$(this).prop("checked", true)
								}	
						});
	   					//妇检
	   					$("input[name='fucheck']").each(function(index, domEle) {
							var radios = $(this).val();
								if (radios == data.fucheck) {
									$(this).prop("checked", true)
								}	
						});
	   					
	   							//下肢水肿
	   							$("input[name='lower']").each(function(index, domEle) {
	   								var radios = $(this).val();
	   									if (radios == data.lower) {
	   										$(this).prop("checked", true)
	   									}	
	   							});
	   							
	   								//足背
	   										
	   							$("input[name='acrotarsium']").each(function(index, domEle) {
	   								var radios = $(this).val();
	   									if (radios == data.acrotarsium) {
	   										$(this).prop("checked", true)
	   									}	
	   							});
	   							
	   						if(data.anas!=null && data.anas!=""){
	   							var arrayAnas = data.anas.split(",");
	   							$("#anasArr input").each(function(index, domEle) {
	   								var checkbox = $(this).val();
	   								
	   								for (var i = 0; i < arrayAnas.length; i++) {
	   									if (checkbox == arrayAnas[i]) {
	   										$(this).attr("checked", true)
	   									}
	   								
	   								}
	   							})		
	   							
	   					form.render();
	   					
	   				}
	   						
	   			if(data.breast!=null && data.breast!=""){
					var arraybreast = data.breast.split(",");
					$("#breastArr input").each(function(index, domEle) {
						var checkbox = $(this).val();
						
						for (var i = 0; i < arraybreast.length; i++) {
							if (checkbox == arraybreast[i]) {
								$(this).attr("checked", true)
							}
						
						}
					})		
					
   					form.render();
	   					
	   				}
	   						
	   						
	   						
	   						
	   						
	   						form.on('checkbox(anasCheckbox)', function (data) {
	   				           console.log(data.elem.name); //得到name
	   				           console.log(data.elem.checked); //是否被选中，true或者false
	   				           console.log(data.value); 
	   				           console.info('111',data)
	   				           	
	   				        // 如果选中未见异常
	   				        if(data.value == 1 && data.elem.checked){
	   				        	// 把其他选项设为不选中
	   				        	$("input[name='anas'][value!='1']").prop("checked", false);
	   				        // 如果选中其他选项
	   				        }else if(data.value != 1 && data.elem.checked){
	   				        	// 把未见异常设为不选中
	   				        	$("input[name='anas'][value='1']").prop("checked", false);
	   				        }
	   				        form.render();
	   				})
	   				
	   						form.on('checkbox(breastCheckbox)', function (data) {
	   				           console.log(data.elem.name); //得到name
	   				           console.log(data.elem.checked); //是否被选中，true或者false
	   				           console.log(data.value); 
	   				           console.info('111',data)
	   				           	
	   				        // 如果选中未见异常
	   				        if(data.value == 1 && data.elem.checked){
	   				        	// 把其他选项设为不选中
	   				        	$("input[name='breast'][value!='1']").prop("checked", false);
	   				        // 如果选中其他选项
	   				        }else if(data.value != 1 && data.elem.checked){
	   				        	// 把未见异常设为不选中
	   				        	$("input[name='breast'][value='1']").prop("checked", false);
	   				        }
	   				        form.render();
	   				})
	   					
			}
	   	 })
		
		
		
	
					
			//杂音输入事件
			$('#noiseArr').bind('input propertychange', function(event) {
				var noiseArr =  $("#noiseArr").val();
				var noise = $("input[name='noise']").val();
				$("input[name='noise']").attr('checked', false);
				if(noiseArr ==null || noiseArr==""){
					$("input[name='noise']").prop('checked', true);
					
				}
				form.render();
				
			});
			
			//压痛输入事件
			$('#tenderness2').bind('input propertychange', function(event) {
				var tenderness2 =  $("#tenderness2").val();
				$("input[name='tenderness']").attr('checked', false);
				if(tenderness2 ==null || tenderness2==""){
					$("input[name='tenderness']").prop('checked', true);
					
				}
				form.render();
				
			});
			
			//包块输入事件
			$('#enclosedbao').bind('input propertychange', function(event) {
				var enclosedbao =  $("#enclosedbao").val();
				$("input[name='enclosedbao']").attr('checked', false);
				if(enclosedbao ==null || enclosedbao==""){
					$("input[name='enclosedbao']").prop('checked', true);
					
				}
				form.render();
				
			});
			
			//肝大输入事件
			$('#hepatomegalg').bind('input propertychange', function(event) {
				var hepatomegalg =  $("#hepatomegalg").val();
				$("input[name='hepatomegalg']").attr('checked', false);
				if(hepatomegalg ==null || hepatomegalg==""){
					$("input[name='hepatomegalg']").prop('checked', true);
					
				}
				form.render();
				
			});
			
			//脾大输入事件
			$('#selenaaxe').bind('input propertychange', function(event) {
				var selenaaxe =  $("#selenaaxe").val();
				$("input[name='selenaaxe']").attr('checked', false);
				if(selenaaxe ==null || selenaaxe==""){
					$("input[name='selenaaxe']").prop('checked', true);
					
				}
				form.render();
				
			});
			
			//移动性输入事件
			$('#movethe').bind('input propertychange', function(event) {
				var movethe =  $("#movethe").val();
				$("input[name='movethe']").attr('checked', false);
				if(movethe ==null || movethe==""){
					$("input[name='movethe']").prop('checked', true);
					
				}
				form.render();
				
			});
			
			
	
	
	
	
	

	
	
	
	
	//监听提交
	form.on('submit(savePhysical)', function(data) {
		//获取checkbox数据,组装成数组!!!!!!
	    var anas =[];
		$('input[name="anas"]:checked').each(function(index, domEle){ 
			anas.push($(this).val()); 
		});
		data.field.anas = anas.join(",");//将数组合并成字符串
		 var breast =[];
		 //debugger
			$('input[name="breast"]:checked').each(function(index, domEle){ 
				breast.push($(this).val()); 
			});
			data.field.breast = breast.join(",");//将数组合并成字符串
		$.ajax({
			type : 'post',
			url : '/YhUserPersonagePhysical/updatePhysical',
			data : data.field,
			traditional: true, //是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!!
			success : function(data) {
				//console.log(data,"后台返回的数据");
				form.val('physicalFrom', {
   					"id" : data.id
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