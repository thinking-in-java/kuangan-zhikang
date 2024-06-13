$(function(){
	
	layui.use([ 'layer', 'form', 'laydate', 'notice','laytpl' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		var laytpl = layui.laytpl;
	
		//日期范围

		 laydate.render({
			 elem: '.test9',
			 trigger: 'click'
			  });
		
		
		var ponimmuneData =JSON.parse(sessionStorage.getItem("ponimmune"))//读取数据语法：====获取数据并将数据装换成json格式
		console.log(ponimmuneData,"---------")
		//console.log(ponimmuneData[1].id,"+++++")
		var userCode = sessionStorage.getItem("userCode");
		
		if(ponimmuneData!=null && ponimmuneData!=""){
		if(ponimmuneData.length>0){
			form.val('immuneFrom', {
				'immuneadio':1
			});
		}
	}
		//追加动态非免疫史HTML
		function appendimmune() {
			const i = ponimmuneData.length;
			let chaseCode = makeCode(8);
			var html="";
			html+="<div class='layui-form-item rowContainer 'code = '"+ chaseCode +"'>";
			
			html+="		<input type='hidden'name='"+ i +"_id'>";
			html+="		<input type='hidden'name='userCode' value='" + userCode + "'>";
			
			html+="		<div class='layui-inline'>";
			html+="			<label class='layui-form-label' style='margin-left: -28px;'>名称</label>";
			html+="			<div class='layui-input-block'>";
			html+=" 			<div class='layui-row'>";
			html+="					<input style='width: 285px;margin-left: -71px;' lay-verify='required' lay-reqText='不能为空'  name='"+ i +"_immunename'   type='text' class='layui-input' placeholder='请填写'>";
			html+="				</div>"
			html+="			</div>"
			html+="		</div>"
				
				
			html+="		<div class='layui-inline'>";
			html+="				<label class='layui-form-label' style='margin-left: -36px;'>接种机构</label>";
			html+="				<div class='layui-input-block'>";
			html+=" 				<div class='layui-row'>";
			html+="						<input style='width: 252px;margin-left: -106px;' lay-verify='required' lay-reqText='不能为空'  name='"+ i +"_organization'   type='text' class='layui-input' placeholder='请填写'>";
			html+="					</div>"
			html+="				</div>"
			html+="		</div>"
				
				
			html += "		<div class='layui-inline'>";
			html += "			<label class='layui-form-label' style='margin-left: -37px;margin-top: -5px;'>接种日期</label>";
			html += "			<div class='layui-input-block'>";
			html += "				<div class='layui-row'>";
			html += "					<input style='width: 170px;margin-left: -106px;' lay-verify='required' lay-reqText='不能为空'  name='"+ i +"_timedate'  type='text' class='layui-input test9' placeholder='请选择日期'>";
			
			html += "					<div style='margin-left: 75px; margin-top: -27px;'>";
			if (i != 0) {
				html += "					<i class='layui-icon layui-icon-delete deleteimmune' data-code='" + chaseCode + "' data-id='-1' style='font-size: 20px;' ></i>";
			}

			html += "						<i class='layui-icon layui-icon-add-circle addimmune' style='font-size: 20px;color: rgba(67, 207, 124, 1);'></i>";
			html += "					</div>";
			html += "				</div>";
			html += "			</div>";
			html += "		</div>";
			html += "</div>";
			
			var len = $ (".test9").length;
			$("#immune").append(html)
			form.render();
			lay('.test9').each(function(index) {
				 if (index === len)
		            {
					 laydate.render({
							elem: this,
						 trigger: 'click'
						});
		            }
				
			});
			
		}
		//遍历用药情况
		var html="";
		for(var i =0 ; i<ponimmuneData.length;i++){
			const item = ponimmuneData[i];
			const soleCode = makeCode(8);
			html+="<div class='layui-form-item rowContainer 'code = '"+ soleCode +"' value='"+item.id+"'>";
			
			html+="		<input type='hidden'name='"+ i +"_id' value='"+item.id+"'>";
			html+="		<input type='hidden'name='userCode' value='"+item.userCode+"'>";
			
			html+="		<div class='layui-inline'>";
			html+="			<label class='layui-form-label' style='margin-left: -28px;'>名称</label>";
			html+="			<div class='layui-input-block'>";
			html+=" 			<div class='layui-row'>";
			html+="					<input style='width: 285px;margin-left: -71px;' lay-verify='required' lay-reqText='不能为空'  name='"+ i +"_immunename' value='"+item.immunename+"'  type='text' class='layui-input' placeholder='请填写'>";
			html+="				</div>"
			html+="			</div>"
			html+="		</div>"
				
				
			html+="		<div class='layui-inline'>";
			html+="				<label class='layui-form-label' style='margin-left: -36px;'>接种机构</label>";
			html+="				<div class='layui-input-block'>";
			html+=" 				<div class='layui-row'>";
			html+="						<input style='width: 252px;margin-left: -106px;' lay-verify='required' lay-reqText='不能为空'  value='"+item.organization+"' name='"+ i +"_organization'   type='text' class='layui-input' placeholder='请填写'>";
			html+="					</div>"
			html+="				</div>"
			html+="		</div>"
				
				
			html += "		<div class='layui-inline'>";
			html += "			<label class='layui-form-label' style='margin-left: -37px;margin-top: -5px;'>接种日期</label>";
			html += "			<div class='layui-input-block'>";
			html += "				<div class='layui-row'>";
			html += "					<input style='width: 170px;margin-left: -106px;' lay-verify='required' lay-reqText='不能为空'  name='"+ i +"_timedate' value='"+item.timedate+"' type='text' class='layui-input test9' placeholder='请选择日期'>";
			
			html += "					<div style='margin-left: 75px; margin-top: -27px;'>";
			if (i != 0) {
				html += "					<i class='layui-icon layui-icon-delete deleteimmune' data-code='" + soleCode + "' data-id='"+item.id+"' style='font-size: 20px;' ></i>";
			}

			html += "						<i class='layui-icon layui-icon-add-circle addimmune' style='font-size: 20px;color: rgba(67, 207, 124, 1);'></i>";
			html += "					</div>";
			html += "				</div>";
			html += "			</div>";
			html += "		</div>";
			html += "</div>";
			
			
			 form.render();

		}
		if(ponimmuneData.length>0){
		$("#immune").html(html)
		}
		 form.render();
		lay('.test9').each(function() {
			laydate.render({
				elem: this,
				trigger: 'click'
				,isInitValue:  true //是否允许填充初始值，默认为true
			});
		});	
					
		$("#immune").on('click','.addimmune',function(){
			appendimmune();
		})
		
		$("#immune").on('click','.deleteimmune',function(){
			//$(this).parent().parent().parent().parent().parent("div").remove();
			//获取ID
			const id = $(this).data('id');
			//获取对象的唯一标识
			const code = $(this).data('code');
			//通过对象的唯一标识，从非免疫史集合中寻找所在下标（页面上的每一行与非免疫史集合的对象通过唯一标识都可以一一对应上）
			const rowindex = ponimmuneData.findIndex(x => x.identityCode === code);
			//debugger;
			layer.confirm('是否确认删除？', {
				btn: ['确定', '取消'] // 可以无限个按钮
			}, function (index, layero) {
				//console.log('刪除的當前下标为：', rowindex, '删除的当前ID为：', id);
				if(id===-1){
					$(".rowContainer[code='" + code + "']").remove();
					ponimmuneData.splice(rowindex, 1);
					console.log('刪除完最新數組：', ponimmuneData);
				}else{
					 $.ajax({
						 	type: 'delete',
						 	url: '/YhUserPersonageNonimmune/deleteNonimmune',
						 	data: {
						 		_method: 'delete',
						 		id: id
						 	},
						 	success: function (data) {
						 		if (data === 1) {
									$(".rowContainer[code='" + code + "']").remove();
									ponimmuneData.splice(rowindex, 1);
									console.log('刪除完最新數組：', ponimmuneData);
						 			notice.success({
						 				title: '消息通知',
						 				message: '操作成功!'
						 			});
						 		} else {
						 			notice.error({
						 				title: '消息通知',
						 				message: '操作失败!'
						 			});
						 		}
						 	}
						 })
				}
				
				layer.close(index);
			}, function (index) {
				// 按钮【按钮二】的回调
			});
		})
		
		
		
		$("#immune").on('click','.test9',function(){
			
			
		})
		//单选框点击事件（括号里边是lay-filter的值）
		　　form.on('radio(immuneadio)', function (data) {
			if(data.value == 1){
				appendimmune();
				$("#hidepimmune").show();
			}else if(data.value == 2){
				$("#hidepimmune").hide();
			}
		　});　
	
		
	
	//监听提交
	
	form.on('submit(saveimmune)', function(data) {
		console.log('form data：', data.field);
		// data.field是整个表单（对象），每一个字段（表单项）的格式：[类型，个人/家庭]_[行标]_[属性名]
		// 提交到后台的数据，格式：{ponimmuneData:[{id:1,userCode:1}]}
		let submitData = {
				ponimmuneData:[]
		}
		// attrArr是这个表单对象的所有属性
		let attrArr = Object.keys(data.field)
		// 从原始属性数组中过滤出包含_单词的属性，放入harmacyAttrArr数组中
			let harmacyAttrArr = attrArr.filter(item => item.includes('_'));
		console.info(attrArr,"---------------------------------------------")
		// 用药数据对象，格式：{"0":{"id":"1","timedata":""},"1":{"id":"2","timedata":""}}
			let ponimmuneDataObj = {};
			for(var i = 0; i < harmacyAttrArr.length; i++){
				const attr = harmacyAttrArr[i];
				// 临时数组，格式：[下标]_[属性名]，如：0_id
				let _tempArr = attr.split('_');
				const _rowIndex = _tempArr[0]; // 行标
				const _fieldName = _tempArr[1]; // 字段名
				
				// 判断用药数据对象是否存在指定行标的属性
				// 1、如果不存在则生成一个默认的空对象，并添加当前属性到对象中
				// 2、如果存在则直接将当前属性添加到对象中
				if (ponimmuneDataObj.hasOwnProperty(_rowIndex) === false) {
					//给personDataObj增加了名称为[_rowIndex]的一个属性
					ponimmuneDataObj[_rowIndex] = {};
				}
				ponimmuneDataObj[_rowIndex][_fieldName] = data.field[ attr];
			}
			console.log(ponimmuneDataObj,"+++++++++++++");
			// 遍历用药数据对象的属性
			for (let attr in ponimmuneDataObj) {
				// 通过属性(此处指行下标)获取属性对应的行数据对象
				const _tempObj = ponimmuneDataObj[attr];

				// 添加公共信息
				_tempObj['userCode'] = data.field.userCode;
				// 将行数据对象添加到非免疫史数据数组中
				submitData.ponimmuneData.push(_tempObj);
			}
			// 将JSON对象转化为JSON字符串
			var data = JSON.stringify(submitData);
			console.log(data,"!!!!!!!!!!!!!!!!!!!");
		
		$.ajax({
			type : 'post',
			url : '/YhUserPersonageNonimmune/compileNonimmune',
			data : data,
			traditional: true, //是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!!
			 contentType: 'application/json;charset=UTF-8',
			success : function(data) {
				console.log("非免疫史------",data)
				if (code=200) {
					layer.msg("操作成功");
				} else {
					layer.msg("操作失败");
				}
			}

		});
		// 阻止表单跳转。如果需要表单跳转，去掉这段即可。
		return false;
	});
	//pasLen是你想要的密码的长度
	function makeCode(pasLen) {
		let pasArr = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'];
		var password = '';
		var pasArrLen = pasArr.length;
		for (var i=0; i<pasLen; i++){
			var x = Math.floor(Math.random()*pasArrLen);
			password += pasArr[x];
		}
		return password;
//		console.log(password);
	}
	
});
})