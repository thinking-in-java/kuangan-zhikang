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
			 elem: '.test7',
			 trigger: 'click'
			  });
		
		
		var pharmacyData =JSON.parse(sessionStorage.getItem("pharmacy"))//读取数据语法：====获取数据并将数据装换成json格式
		var userCode = sessionStorage.getItem("userCode");
		
		if (pharmacyData!=null && pharmacyData!="") {
			form.val('pharmacyFrom', {
				"pharmacyRadio": 1,
			});
		}
		
	
			
		
	
		
		function addData() {
			// 新增数据的下标
			let maxIndex = 0;
			let _tempObj = {
				id: -1,
				name: '',
				userCode: userCode,
				drugname: '',
				usagefa: '',
				dosage: '',
				timedata: '',
			};
			
			pharmacyData.push(_tempObj);
			
			return _tempObj;
		}
		
		
		//用药动态追加HTML
		function appendPharmacy() {
			addData()
			var html="";
			const i = pharmacyData.length;
			let chaseCode = makeCode(8);
			html+="<div class='layui-form-item rowContainer 'code = '"+ chaseCode +"'>";
			
			html+="		<input type='hidden'name='"+ i +"_id'>";
			html+="		<input type='hidden'name='userCode' value='" + userCode + "'>";
			
			html+="		<div class='layui-inline'>";
			html+="			<label class='layui-form-label' style='margin-left: -5px;'>药物名称</label>";
			html+="			<div class='layui-input-block'>";
			html+=" 			<div class='layui-row'>";
			html+="					<input style='width: 170px;margin-left: -76px;' lay-verify='required' lay-reqText='不能为空'  name='"+ i +"_drugname'   type='text' class='layui-input' placeholder='请填写'>";
			html+="				</div>"
			html+="			</div>"
			html+="		</div>"
				
			html+="		<div class='layui-inline'>";
			html+="			<label class='layui-form-label' style='margin-left: -61px;'> 用法</label>";
			html+="				<div class='layui-input-block'>";
			html+=" 				<div class='layui-row'>";
			html+="						<input style='width: 170px;margin-left: -106px' lay-verify='required' lay-reqText='不能为空'  name='"+ i +"_usagefa'  type='text' class='layui-input' placeholder='请填写'>";
			html+="				</div>"
			html+="			</div>"
			html+="		</div>"
				
			html+="		<div class='layui-inline'>";
			html+="				<label class='layui-form-label' style='margin-left: -62px;'>用量</label>";
			html+="				<div class='layui-input-block'>";
			html+=" 				<div class='layui-row'>";
			html+="						<input style='width: 170px;margin-left: -106px;' lay-verify='required' lay-reqText='不能为空'  name='"+ i +"_dosage'   type='text' class='layui-input' placeholder='请填写'>";
			html+="					</div>"
			html+="				</div>"
			html+="		</div>"
				
				
			html+=		"<div class='layui-inline'>";
			html+="			<label class='layui-form-label' style='margin-left: -47px;margin-top: -2px;width: 121px;'> 用药开始时间</label>";
			html+="				<div class='layui-input-block'>";
			html+=" 				<div class='layui-row'>";
			html+="						<input style='width: 170px;margin-left: -106px;' lay-verify='required' lay-reqText='不能为空'  name='"+ i +"_timedata'  type='text' class='layui-input test7' placeholder='请选择日期'>";
			html+="					</div>"
			html+="				</div>"
			html+="		</div>"
				
			html+="		<div class='layui-inline'>";
			html+="			<label class='layui-form-label' style='margin-left: -32px;margin-top: -5px;width: 123px;'>服药依从性</label>";
			html+="				<span style='width: 56px; height: 21px; left: 503px; top: 228px; color: rgba(166, 166, 166, 1); font-size: 12px; line-height: 208%; text-align: left;margin-left: -10px;'>（单选）</span></label>";
			html+="					<div class='layui-input-block'style='margin-left: 35px;'>";
			html+=" 					<div class='layui-row' lay-verify='radiocheckout' >";
			html+="							<input type='radio'  lay-filter='comply' name='"+ i +"_comply'value='1' title='间断'> "
			html+="							<input type='radio'  lay-filter='comply' name='"+ i +"_comply'value='2' title='规律'> "
			html+="							<input type='radio' lay-filter='comply' name='"+ i +"_comply'value='3' title='不服药'> "
			html+="								<div style='margin-left: 259px; margin-top: -25px;'>"
			html+="									<i class='layui-icon layui-icon-delete deletepharmacy' data-code ='"+ chaseCode +"' data-id='-1' style='font-size: 20px;'></i>";
			html+="									<i class='layui-icon layui-icon-add-circle addpharmacy' style='font-size: 20px; color: rgba(67, 207, 124, 1);'></i>";
			html+="								</div>"
			html+="						</div>"
			html+="				</div>"
			html+="			</div>"
			html+="	</div>"
			
			var len = $ (".test7").length;
			$("#pharmacy").append(html)
			form.render();
			lay('.test7').each(function(index) {
				 if (index === len)
		            {
					 laydate.render({
							elem: this,
						 trigger: 'click'
						});
		            }
				
			});
			
			
		}
		 form.verify({
			 radiocheckout: function(value,dom){
				 var radioVerify = $(dom).find("input[type='radio']:checked").val();
			      if(typeof(radioVerify)=="undefined"){
			        return '请选择服药依从性';
			      }
			    }
		})	
	
		
		//遍历渲染用药情况
		var html="";
		for(var i =0 ; i<pharmacyData.length;i++){
			const item = pharmacyData[i];
			const soleCode = makeCode(8);
			html+="<div class='layui-form-item rowContainer' code='" + soleCode+ "'>";
			html += "		<input type='hidden'name='userCode' value='" + item.userCode + "'>";
			html+="		<input type='hidden' name='"+ i +"_id' value='"+item.id+"'>";
			
			html+="		<div class='layui-inline'>";
			html+="			<label class='layui-form-label' style='margin-left: -5px;'>药物名称</label>";
			html+="			<div class='layui-input-block'>";
			html+=" 			<div class='layui-row'>";
			html+="					<input style='width: 170px;margin-left: -76px;' lay-verify='required'  value='"+item.drugname+"'  name='"+ i +"_drugname'  type='text' class='layui-input' placeholder='请填写'>";
			html+="				</div>"
			html+="			</div>"
			html+="		</div>"
				
			html+="		<div class='layui-inline'>";
			html+="			<label class='layui-form-label' style='margin-left: -61px;'>用法</label>";
			html+="				<div class='layui-input-block'>";
			html+=" 				<div class='layui-row'>";
			html+="						<input style='width: 170px;margin-left: -106px' lay-verify='required'   value='"+item.usagefa+"' name='"+ i +"_usagefa' type='text' class='layui-input' placeholder='请填写'>";
			html+="				</div>"
			html+="			</div>"
			html+="		</div>"
				
			html+="		<div class='layui-inline'>";
			html+="				<label class='layui-form-label' style='margin-left: -62px;'>用量</label>";
			html+="				<div class='layui-input-block'>";
			html+=" 				<div class='layui-row'>";
			html+="						<input style='width: 170px;margin-left: -106px;' lay-verify='required'  value='"+item.dosage+"' name='"+ i +"_dosage'  type='text' class='layui-input' placeholder='请填写'>";
			html+="					</div>"
			html+="				</div>"
			html+="		</div>"
				
				
			html+=		"<div class='layui-inline'>";
			html+="			<label class='layui-form-label' style='margin-left: -47px;margin-top: -2px;width: 121px;'>用药开始时间</label>";
			html+="				<div class='layui-input-block'>";
			html+=" 				<div class='layui-row'>";
			html+="						<input style='width: 170px;margin-left: -106px;' lay-verify='required'   value='"+item.timedata+"'  name='"+ i +"_timedata' type='text' class='layui-input test7' placeholder='请选择日期'>";
			html+="					</div>"
			html+="				</div>"
			html+="		</div>"
				
			html+="		<div class='layui-inline'>";
			html+="			<label class='layui-form-label' style='margin-left: -32px;margin-top: -5px;width: 123px;'>服药依从性</label>";
			html+="				<span style='width: 56px; height: 21px; left: 503px; top: 228px; color: rgba(166, 166, 166, 1); font-size: 12px; line-height: 208%; text-align: left;margin-left: -10px;'>（单选）</span></label>";
			html+="					<div class='layui-input-block'style='margin-left: 35px;'>";
			html+=" 					<div class='layui-row' >";
			html+="							<input type='radio'  name='"+ i +"_comply'value='1'" + (item.comply == '1' ? 'checked' : '') + " title='间断'> "
			html+="							<input type='radio'  name='"+ i +"_comply'value='2'" + (item.comply == '2' ? 'checked' : '') + " title='规律'> "
			html+="							<input type='radio'  name='"+ i +"_comply'value='3'" + (item.comply == '3' ? 'checked' : '') + " title='不服药'> "
			html+="								<div style='margin-left: 259px; margin-top: -25px;'>"
			if(i!=0){
				html+="									<i class='layui-icon layui-icon-delete deletepharmacy' data-id='" + item.id + "' data-code='"+soleCode+"' style='font-size: 20px;'></i>";
			}
			html+="									<i class='layui-icon layui-icon-add-circle addpharmacy' style='font-size: 20px; color: rgba(67, 207, 124, 1);'></i>";
			html+="								</div>"
			html+="						</div>"
			html+="				</div>"
			html+="			</div>"
			html+="	</div>"
			
		}
		
	
		if(pharmacyData.length>0){
			$("#pharmacy").html(html)
			form.render();
		}
		lay('.test7').each(function() {
			laydate.render({
				elem: this,
				trigger: 'click'
				//,value: '$(".timedeta").html()'
				,isInitValue:  true //是否允许填充初始值，默认为true
			});
		});	
		
		 		
		
				
					   
				
			
			
			
			
		$("#pharmacy").on('click','.addpharmacy',function(){
			appendPharmacy();
		})
		
		$("#pharmacy").on('click','.deletepharmacy',function(){
			// const id = $(this).attr("data-id");
			//获取ID
			const id = $(this).data('id');
			//获取对象的唯一标识
			const code = $(this).data('code');
			//通过对象的唯一标识，从用药集合中寻找所在下标（页面上的每一行与个人住院集合的对象通过唯一标识都可以一一对应上）
			const rowindex = pharmacyData.findIndex(x => x.identityCode === code);
			//debugger;
			layer.confirm('是否确认删除？', {
				btn: ['确定', '取消'] // 可以无限个按钮
			}, function (index, layero) {
				//console.log('刪除的當前下标为：', rowindex, '删除的当前ID为：', id);
				if(id===-1){
					$(".rowContainer[code='" + code + "']").remove();
					pharmacyData.splice(rowindex, 1);
					console.log('刪除完最新數組：', pharmacyData);
				}else{
					 $.ajax({
						 	type: 'delete',
						 	url: '/YhUserPersonagePharmacy/deletePharmacy',
						 	data: {
						 		_method: 'delete',
						 		id: id
						 	},
						 	success: function (data) {
						 		if (data === 1) {
									$(".rowContainer[code='" + code + "']").remove();
									pharmacyData.splice(rowindex, 1);
									console.log('刪除完最新數組：', pharmacyData);
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
		
		
		
		$("#increase").on('click','.test7',function(){
			
			
		})
		//单选框点击事件（括号里边是lay-filter的值）
		　　form.on('radio(pharmacyRadio)', function (data) {
			if(data.value == 1){
				appendPharmacy();
				$("#hidepharmacy").show();
			}else if(data.value == 2){
				$("#hidepharmacy").hide();
			}
		　});　
	
		
	
	//监听提交
	
	form.on('submit(savepharmacy)', function(data) {
		console.log('form data：', data.field);
		let submitData = {
				pharmacyData:[]
		}
		// attrArr是这个表单对象的所有属性
		let attrArr = Object.keys(data.field)
		/*if (!data.field._comply){
			 alert('已选择服药依从性')
        }else {
            alert('已选择服药依从性')
        }
        return false;*/
		console.log("表单的属性",attrArr)
		// 从原始属性数组中过滤出包含_单词的属性，放入harmacyAttrArr数组中
			let harmacyAttrArr = attrArr.filter(item => item.includes('_'));
		// 用药数据对象，格式：{"0":{"id":"1","timedata":""},"1":{"id":"2","timedata":""}}
			let pharmacyDataObj = {};
			for(var i = 0; i < harmacyAttrArr.length; i++){
				const attr = harmacyAttrArr[i];
				// 临时数组，格式：[下标]_[属性名]，如：0_id
				let _tempArr = attr.split('_');
				const _rowIndex = _tempArr[0]; // 行标
				const _fieldName = _tempArr[1]; // 字段名
				
				// 判断用药数据对象是否存在指定行标的属性
				// 1、如果不存在则生成一个默认的空对象，并添加当前属性到对象中
				// 2、如果存在则直接将当前属性添加到对象中
				if (pharmacyDataObj.hasOwnProperty(_rowIndex) === false) {
					//给personDataObj增加了名称为[_rowIndex]的一个属性
					pharmacyDataObj[_rowIndex] = {};
				}
				pharmacyDataObj[_rowIndex][_fieldName] = data.field[ attr];
			}
			//console.log(pharmacyDataObj,"+++++++++++++");
			// 遍历用药数据对象的属性
			for (let attr in pharmacyDataObj) {
				// 通过属性(此处指行下标)获取属性对应的行数据对象
				_tempObj = pharmacyDataObj[attr];

				// 添加公共信息
				_tempObj['userCode'] = data.field.userCode;
				// 将行数据对象添加到个人住院数据数组中
				submitData.pharmacyData.push(_tempObj);
			}
			var data = JSON.stringify(submitData);
			console.log(data,"!!!!!!!!!!!!!!!!!!!");
		
		$.ajax({
			type : 'post',
			url : '/YhUserPersonagePharmacy/compilePharmacy',
			data : data,
			 contentType: 'application/json;charset=UTF-8',
			traditional: true, //是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!!
			success : function(data) {
				console.log("用药------",data)
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