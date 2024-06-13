$(function(){
	
	layui.use([ 'layer', 'form', 'laydate', 'notice' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		
	//var data = JSON.parse(sessionStorage.getItem("examination"))//读取数据语法：====获取数据并将数据装换成json格式
		 $.ajax({
	   		 type:'get',
	   		 url:'/YhUserPersonageExamination/selectExamination',
	   		 data:{userCode:userCode},
	   		success : function(data){
	   			
	   			
	   			//尿蛋白
	   		var urine = data.urine;
	   		$("input[name='urine']").each(function(index, domEle) {
	   			var radios = $(this).val();
	   				if (radios == urine) {
	   					$(this).prop("checked", true)
	   				}
	   				
	   		})
	   		//尿糖
	   		var uriensugar = data.uriensugar;
	   		$("#uriensugarArr input").each(function(index, domEle) {
	   			var radios = $(this).val();
	   				if (radios == uriensugar) {
	   					$(this).prop("checked", true)
	   				}
	   				
	   		})
	   		
	   		//尿酮体
	   		var urineacetone = data.urineacetone;
	   		$("#urineacetoneArr input").each(function(index, domEle) {
	   			var radios = $(this).val();
	   				if (radios == urineacetone) {
	   					$(this).prop("checked", true)
	   				}
	   				
	   		})
	   		
	   		//尿潜血
	   		var urineblood = data.urineblood;
	   		$("#urinebloodArr input").each(function(index, domEle) {
	   			var radios = $(this).val();
	   				if (radios == urineblood) {
	   					$(this).prop("checked", true)
	   				}
	   				
	   		})
	   		//大便潜血
	   		var excrement = data.excrement;
	   		$("#excrementArr input").each(function(index, domEle) {
	   			var radios = $(this).val();
	   				if (radios == excrement) {
	   					$(this).prop("checked", true)
	   				}
	   				
	   		})
	   		

	   		//心电
	   		var ecg = data.ecg;
	   			var ecgArr = $("#ecgArr").val();
	   				if (ecgArr == ecg) {
	   					$("#ecgArr").attr("checked", true)
	   				}
	   				

	   		
	   		
	   		//胸部X射线
	   		var chest = data.chest;
	   			var chestArr = $("#chestArr").val();
	   				if (chestArr == chest) {
	   					$("#chestArr").attr("checked", true)
	   				}
	   				
	   		
	   		
	   		//B超
	   		var bultrasound = data.bultrasound;
	   		var bultrasoundArr = $("#bultrasoundArr").val();
	   			if (bultrasoundArr == bultrasound) {
	   				$("#bultrasoundArr").attr("checked", true)
	   			}
	   		
	   		
	   		//工颈图片
	   		var cervix = data.cervix;
	   		var cervixArr = $("#cervixArr").val();
	   			if (cervixArr == cervix) {
	   				$("#cervixArr").attr("checked", true)
	   			}
	   		
	   		
	   		//眼底
	   		var fundus = data.fundus;
	   			var fundusArr = $("input[name='fundus']").val();
	   				if (fundusArr == fundus) {
	   					$("#fundusArr").attr("checked", true)
	   				}
	   				
	   		
	   		
	   		//乙型
	   		var hepatitis = data.hepatitis;
	   		$("#hepatitisArr input").each(function(index, domEle) {
	   			var radios = $(this).val();
	   				if (radios == hepatitis) {
	   					$(this).prop("checked", true)
	   				}
	   				
	   		})
			
	   		
	   		
	   		form.val('examinationForm', {
	   				"id" : data.id,
	   				"fbg":data.fbg,
	   				"urinarymicro":data.urinarymicro,
	   				"hemocyte":data.hemocyte,
	   				"blood":data.blood,
	   				"urinarymicro":data.urinarymicro,
	   				"cerealthird":data.cerealthird,
	   				"milletstraw":data.milletstraw,
	   				"albumin":data.albumin,
	   				"sweetpotato":data.sweetpotato,
	   				"combine":data.combine,
	   				"serum":data.serum,
	   				"hematuresis":data.hematuresis,
	   				"potassium":data.potassium,
	   				
	   				"sodium":data.sodium,
	   				"urinarymicro":data.urinarymicro,
	   				"tc":data.tc,
	   				"tcbottom":data.tcbottom,
	   				"tg":data.tg,
	   				"tctall":data.tctall,
	   				"saccharify":data.saccharify,
	   				"hemoglobin":data.hemoglobin
	   			});
	   		
	   		
	   		}
	   	 })
		

		//输入框的输入事件
	$('#fundus').bind('input propertychange', function(event) {
		var fundus =  $("#fundus").val();
		var fundusArr = $("#fundusArr").val();
		$("#fundusArr").attr('checked', false);
		console.log(fundus)
		
		if(fundus ==null || fundus==""){
			$("#fundusArr").prop('checked', true);
			
		}
		form.render();
		
	});
	
	//输入框的输入事件
	$('#ecg').bind('input propertychange', function(event) {
		var ecg =  $("#ecg").val();
		var ecgArr = $("#ecgArr").val();
		$("#ecgArr").attr('checked', false);
		if(ecg ==null || ecg==""){
			$("#ecgArr").prop('checked', true);
			
		}
		form.render();
		
	});
	
	//输入框的输入事件
	$('#chest').bind('input propertychange', function(event) {
		var chest =  $("#chest").val();
		var chestArr = $("#chestArr").val();
		$("#chestArr").attr('checked', false);
		
		if(chest ==null || chest==""){
			$("#chestArr").prop('checked', true);
			
		}
		form.render();
		
	});
	
	//输入框的输入事件
	$('#bultrasound').bind('input propertychange', function(event) {
		var bultrasound =  $("#bultrasound").val();
		var bultrasoundArr = $("#bultrasoundArr").val();
		$("#bultrasoundArr").attr('checked', false);
		if(bultrasound ==null || bultrasound==""){
			$("#bultrasoundArr").prop('checked', true);
			
		}
		form.render();
		
	});
	
	//输入框的输入事件
	$('#cervix').bind('input propertychange', function(event) {
		var cervix =  $("#cervix").val();
		var cervixArr = $("#cervixArr").val();
		$("#cervixArr").attr('checked', false);
		if(cervix ==null || cervix==""){
			$("#cervixArr").prop('checked', true);
			
		}
		form.render();
		
	});
	
		
		

	
	

	
	
	
	
	//监听提交
	form.on('submit(examination)', function(data) {
		
		$.ajax({
			type : 'post',
			url : '/YhUserPersonageExamination/updateExamination',
			data : data.field,
			//traditional: true, //是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!!
			success : function(data) {
				form.val('examinationForm', {
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