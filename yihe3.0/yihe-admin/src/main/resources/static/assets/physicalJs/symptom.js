$(function(){
	/*var userCode = [[${userCode}]];*/
	var arr=[];
	//导航栏
	layui.use('element', function() {
		var $ = layui.jquery, element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块

		$('#nextBtn').on('click', function() {
			alert(1)
			var othis = $(this), type = othis.data('type');
			active[type] ? active[type].call(this, othis) : '';
		});

	});


	layui.use([ 'layer', 'form', 'laydate', 'notice', 'tagsInput'],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		var tagsInput = layui.tagsInput;


		//var symptomData = JSON.parse(sessionStorage.getItem("symptom"))//读取数据语法：====获取数据并将数据装换成json格式

		$.ajax({
			type:'get',
			url:'/YhUserPersonageSymptom/selectSymptom',
			data:{userCode:userCode},
			success : function(data){
				console.log(data,"症状数据")
				form.val('symptom', {
					"id":data.id
				});
				if(data.standby!=null && data.standby!=""){
					var standby = JSON.parse(data.standby)
					for(var i=0;i<standby.length;i++){
						test(standby[i]);
					}

				}

				if(data.personageName!=null && data.personageName!=''){
					var arraySymptom = data.personageName.split(",")
					$("#feedback input").each(function(index, domEle) {
						var checkboxs = $(this).val();
						//console.log(checkboxs+"--------")
						for (var i = 0; i < arraySymptom.length; i++) {
							if (checkboxs == arraySymptom[i]) {
								$(this).prop("checked", true)
							}
						}
					})

					form.render();

				}

			}
		})



		// 无边框样式
		// $('#demoTagsInput2').tagsInput({skin: 'tagsinput-default'});

		function test(date) {
			var html="";
			html+="<div class='tt'>";
			html+=		""+date+"";
			html+=		"<div><img class='tipImg' alt='' src='/assets/images/jianhao.png'></div>";
			html+="</div>";
			$("#addSymptom").append(html)
			arr.push(date);
			$(".tt img").on("click",function(){
				let str=$(this).parent().parent().text();
				$(this).parent().parent().remove();
				if(arr.indexOf(str)>-1){
					arr.splice(arr.indexOf(str),1);
				}
				console.log(arr)
			})
		}

		$('#cancelBtn').click(function() {
			$("#feedback input").each(function(index, domEle) {
				var checkboxs = $(this).val();
				$(this).attr("checked", false)
			})
			form.render();
		})

		//搜索框回车事件
		$('#searchText').on('keydown', function(event) {
			if (event.keyCode == 13) {

				return false
			}
		});


		//输入框的enter事件
		function getStandby() {
			var searchText = $("#searchText").val().replace(/\s+/g, '');
			$(this).val("");
			if(searchText==''){
				layer.msg("内容不能为空");
				return false
			}
			var flag = false
			$("#feedback input").each(function(index, domEle) {
				var checkboxs = $(this).val();
				if(searchText==checkboxs){
					flag = true
				}
			})
			$("#addSymptom .tt").each(function(index, domEle) {
				var checkboxs = $(this).text();
				if(searchText==checkboxs){
					flag = true
				}
			})
			if(flag){
				layer.msg("已存在，请直接选择");
				return false
			}
			test(searchText);
		}

		//监听提交
		form.on('submit(save)', function(data) {
			//console.log(data,"症状提交")
			//获取checkbox数据,组装成数组!!!!!!
			getStandby();
			var like =[];
			var array = JSON.stringify(arr);
			$('input[name="like"]:checked').each(function(index, domEle){
				like.push($(this).val());
			});
			data.field.like = like.join(",");//将数组合并成字符串
			data.field.array = array;
			console.log(JSON.stringify(data.field))
			$.ajax({
				type : 'post',
				url : '/YhUserPersonageSymptom/update',
				data : data.field,
				dataType: "json",
				//traditional: true, //是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!!
				success : function(data) {
					console.log(data,"后台返回的的数据")
					form.val('symptom', {
						"id":data.id
					});
					if (data!='') {
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