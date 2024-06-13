$(function () {
 
 	//var hospitalizedData = {};
 	
	layui.use(['layer', 'form', 'laydate', 'notice'], function () {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;


		// 日期范围

		laydate.render({
			elem: '.test6',
			range: true,
			trigger: 'click'
		});

		laydate.render({
			elem: '.test8',
			range: true,
			trigger: 'click'
		});


		var hospitalizedData =JSON.parse(sessionStorage.getItem("hospitalized")) // 读取数据语法：====获取数据并将数据装换成json格式
		var userCode = sessionStorage.getItem("userCode");
		/*
		 * console.info(hospitalizedData, "++++")
		 * console.info(hospitalizedData[1], "+++++++")
		 */
		
		
		/* $.ajax({
  		 type:'get',
  		 url:'/YhUserPersonageHospitalihospitalizedDatazed/selectHospitalized',
  		 data:{userCode:userCode},
  		success : function(data){
  			hospitalizedData = data;
		}
  	 })   */
		
			if (hospitalizedData.hasOwnProperty('1')) {
				form.val('hospitalFrom', {
					"haveHospital": 1,
				});
			}
			if (!hospitalizedData.hasOwnProperty('1')) {
				hospitalizedData['1'] = [];
			}
			if (!hospitalizedData.hasOwnProperty('2')) {
				hospitalizedData['2'] = [];
			}
		
		

	
		
		// 添加数据，type：类型（person，family）
		function addData(type) {
			// 新增数据的下标
			let maxIndex = 0;
			let hospitalizedtype = 1;
			// var userCode = userCode;
			if (type === 'person') {
				maxIndex = hospitalizedData[1].length;
				hospitalizedtype = 1;
			} else if (type === 'family') {
				maxIndex = hospitalizedData[2].length;
				hospitalizedtype = 2;
			}

			let _tempObj = {
				id: -1,
				name: '',
				userCode: userCode,
				hospitalizedtype: 1,
				timedeta: '',
				cause: '',
				yiyuanname: '',
				disease: '',
				identityCode: makeCode(8)
			};

			// 省略其它赋值语句，都是赋空值

			if (type === 'person') {
				hospitalizedData[1].push(_tempObj);
			} else if (type === 'family') {
				hospitalizedData[2].push(_tempObj);
			}
			
			return _tempObj;
		}
		// 个人动态渲染HTML
		function addPerson() {
			const newPersonObj = addData('person');
			console.log('增加新的个人住院信息：', hospitalizedData[1]);
			const maxIndex = hospitalizedData[1].length - 1;
			var html = "";
		
			const i = maxIndex;
			html += "<div class='layui-form-item person-rowContainer' code='" + newPersonObj.identityCode + "'>";
			
			html += "		<input type='hidden'name='id'>";
			html += "		<input type='hidden'name='userCode' value='" + userCode + "'>";
			html += "		<input type='hidden'  name='person_" + i + "_id'>";
			html += "		<input type='hidden'  name='person_" + i + "_hospitalizedtype' value='1'>";
			
			html += "		<div class='layui-inline'>";
			html += "				<label class='layui-form-label' style='margin-left: 10px;'> 入出院日期</label>";
			html += "				<div class='layui-input-block'>";
			html += "					<div class='layui-row'>";
			html += "						<input style='width: 180px;margin-left: -76px;'lay-verify='required' lay-reqText='不能为空' type='text' name='person_" + i + "_timedeta'  class='layui-input test6 timedeta'  placeholder='请选择日期'>";
			html += "					</div>";
			html += "				</div>";
			html += "		</div>";

			html += "		<div class='layui-inline'>";
			html += "				<label class='layui-form-label' style='margin-left: -31px;'>原因</label>";
			html += "				<div class='layui-input-block'>";
			html += "				<div class='layui-row'>";
			html += "					<input style='width: 300px;margin-left: -76px;' type='text'lay-verify='required' lay-reqText='不能为空' name='person_" + i + "_cause'  class='layui-input'  placeholder='请填写'>";
			html += "					</div>";
			html += "				</div>";
			html += "		</div>";

			html += "		<div class='layui-inline'>";
			html += "			<label class='layui-form-label' style='margin-left: 18px;width: 93px;'>医疗机构名称</label>";
			html += "			<div class='layui-input-block'>";
			html += "			<div class='layui-row'>";
			html += "				<input style='width: 220px;margin-left: -67px;' type='text' lay-verify='required' lay-reqText='不能为空' name='person_" + i + "_yiyuanname'  class='layui-input'  placeholder='请填写'>";
			html += "					</div>";
			html += "				</div>";
			html += "		</div>";

			html += "		<div class='layui-inline'>";
			html += "			<label class='layui-form-label' style='margin-left: -18px;margin-top: -5px;'>病案号</label>";
			html += "			<div class='layui-input-block'>";
			html += "				<div class='layui-row'>";
			html += "					<input style='width: 220px;margin-left: -73px;' type='text' lay-verify='required' lay-reqText='不能为空' id='disease' name='person_" + i + "_disease'  class='layui-input'  placeholder='请填写'>";
			
			html += "					<div style='margin-left: 159px; margin-top: -27px;'>";
			if (i != 0) {
				html += "					<i class='layui-icon layui-icon-delete delete' data-code='" + newPersonObj.identityCode + "' data-id='-1' style='font-size: 20px;' ></i>";
			}

			html += "						<i class='layui-icon layui-icon-add-circle addhospital' style='font-size: 20px;color: rgba(67, 207, 124, 1);'></i>";
			html += "					</div>";
			html += "				</div>";
			html += "			</div>";
			html += "		</div>";
			html += "</div>";
			$("#increase").append(html)
			var len = $(".test6").length-1;
			lay('.test6').each(function (index) {
				if (index === len) {
					laydate.render({
						elem: this,
						range: true,
						trigger: 'click'
					});
				}
			});
			
			
		}

		// 个人列表渲染
		function personListRender() {
			var personHtml = "";
			var arr = [];
			
			// 遍历个人住院集合，给每一个对象都增加一个唯一标识码的属性
			
			hospitalizedData[1] = hospitalizedData[1].map(item => {
				item.identityCode = makeCode(8);
				return item;
				
			});
		
			//console.log('遍历个人住院信息：', hospitalizedData[1]);
			// 遍历个人住院
			
			for (var i = 0; i < hospitalizedData[1].length; i++) {
				const item = hospitalizedData[1][i];
				personHtml += "<div class='layui-form-item person-rowContainer' code='" + item.identityCode + "'>";
				
				personHtml += "		<input type='hidden'name='userCode' value='" + item.userCode + "'>";
				personHtml += "		<input type='hidden'name='id' value='" + item.id + "'>";
				personHtml += "		<input type='hidden' value='" + item.id + "' name='person_" + i + "_id'>";
				personHtml += "		<input type='hidden' value='" + item.hospitalizedtype + "'name='person_" + i + "_hospitalizedtype'>";
				
				personHtml += "		<div class='layui-inline'>";
				personHtml += "				<label class='layui-form-label' style='margin-left: 10px;'>  入出院日期</label>";
				personHtml += "				<div class='layui-input-block'>";
				personHtml += "					<div class='layui-row'>";
				personHtml += "						<input style='width: 180px;margin-left: -76px;' type='text' name='person_" + i + "_timedeta' value='" + item.timedeta + " - " + item.dischargetime + "' class='layui-input test6 timedeta'  placeholder='请选择日期'>";
				personHtml += "					</div>";
				personHtml += "				</div>";
				personHtml += "		</div>";

				personHtml += "		<div class='layui-inline'>";
				personHtml += "				<label class='layui-form-label' style='margin-left: -31px;'>原因</label>";
				personHtml += "				<div class='layui-input-block'>";
				personHtml += "				<div class='layui-row'>";
				personHtml += "					<input style='width: 300px;margin-left: -76px;' lay-verify='required' lay-reqText='不能为空' type='text' name='person_" + i + "_cause' value='" + item.cause + "' class='layui-input'  placeholder='请填写'>";
				personHtml += "					</div>";
				personHtml += "				</div>";
				personHtml += "		</div>";

				personHtml += "		<div class='layui-inline'>";
				personHtml += "			<label class='layui-form-label' style='margin-left: 18px;width: 93px;'>医疗机构名称</label>";
				personHtml += "			<div class='layui-input-block'>";
				personHtml += "			<div class='layui-row'>";
				personHtml += "				<input style='width: 220px;margin-left: -67px;' type='text' lay-verify='required' lay-reqText='不能为空' name='person_" + i + "_yiyuanname' value='" + item.yiyuanname + "' class='layui-input'  placeholder='请填写'>";
				personHtml += "					</div>";
				personHtml += "				</div>";
				personHtml += "		</div>";

				personHtml += "		<div class='layui-inline'>";
				personHtml += "			<label class='layui-form-label' style='margin-left: -18px;margin-top: -5px;'>病案号</label>";
				personHtml += "			<div class='layui-input-block'>";
				personHtml += "				<div class='layui-row'>";
				personHtml += "					<input style='width: 220px;margin-left: -73px;' type='text' id='disease' lay-verify='required' lay-reqText='不能为空' name='person_" + i + "_disease' value='" + item.disease + "' class='layui-input'  placeholder='请填写'>";
				
				personHtml += "					<div style='margin-left: 159px; margin-top: -27px;'>";
				if (i != 0) {
					personHtml += "					<i class='layui-icon layui-icon-delete delete' data-code='" + item.identityCode + "' data-id='" + item.id + "' style='font-size: 20px;' ></i>";
				}

				personHtml += "						<i class='layui-icon layui-icon-add-circle addhospital' style='font-size: 20px;color: rgba(67, 207, 124, 1);'></i>";
				personHtml += "					</div>";
				personHtml += "				</div>";
				personHtml += "			</div>";
				personHtml += "		</div>";
				personHtml += "</div>";
			}
		

			$("#increase").html(personHtml)
		}

		personListRender();
		lay('.test6').each(function () {
			laydate.render({
				elem: this,
				range: true,
				isInitValue: true,// 是否允许填充初始值，默认为true
				trigger: 'click'
			});
		});

		// 个人删除添加
		$("#increase").on('click', '.addhospital', function () {
			addPerson();
		})
		
	$("#increase").on('click', '.delete', function () {
			// const id = $(this).attr("data-id");
			// 获取ID
			const id = $(this).data('id');
			// 获取对象的唯一标识
			const code = $(this).data('code');
			// 通过对象的唯一标识，从个人住院集合中寻找所在下标（页面上的每一行与个人住院集合的对象通过唯一标识都可以一一对应上）
			const rowindex = hospitalizedData[1].findIndex(x => x.identityCode === code);
			// debugger;
			layer.confirm('是否确认删除？', {
				btn: ['确定', '取消'] // 可以无限个按钮
			}, function (index, layero) {
				console.log('刪除的當前下标为：', rowindex, '删除的当前ID为：', id);
				if(id===-1){
					$(".person-rowContainer[code='" + code + "']").remove();
		 			hospitalizedData[1].splice(rowindex, 1);
					console.log('刪除完最新數組：', hospitalizedData[1]);
				}else{
					 $.ajax({
						 	type: 'delete',
						 	url: '/YhUserPersonageHospitalized/deleteHospitalized',
						 	data: {
						 		_method: 'delete',
						 		id: id
						 	},
						 	success: function (data) {
						 		if (data === 1) {
									$(".person-rowContainer[code='" + code + "']").remove();
						 			hospitalizedData[1].splice(rowindex, 1);
									console.log('刪除完最新數組：', hospitalizedData[1]);
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

		$("#increase").on('click', '.test6', function () {

		})


		// 家庭动态渲染HTML
		function chaseFamily() {
			const newFamilyObj = addData('family');
			console.log('增加新的家庭住院信息：', hospitalizedData[2]);
			const maxIndex = hospitalizedData[2].length - 1;
			console.info(maxIndex, "\\\\\\\\");
			var familyHtml = "";
			const i = maxIndex;
			familyHtml += "<div class='layui-form-item family-rowContainer' code='" + newFamilyObj.identityCode + "'>";
			
			familyHtml += "		<input type='hidden'name='userCode' value='" + userCode + "'>";
			familyHtml += "		<input type='hidden'  name='family_" + i + "_id'>";
			familyHtml += "		<input type='hidden'  name='family_" + i + "_hospitalizedtype' value='1'>";
			
			familyHtml += "		<div class='layui-inline'>";
			familyHtml += "				<label class='layui-form-label' style='margin-left: 10px;'> 入出院日期</label>";
			familyHtml += "				<div class='layui-input-block'>";
			familyHtml += "					<div class='layui-row'>";
			familyHtml += "						<input style='width: 180px;margin-left: -76px;' lay-verify='required' lay-reqText='不能为空' type='text' name='family_" + i + "_timedeta'  class='layui-input test8 timedeta'  placeholder='请选择日期'>";
			familyHtml += "					</div>";
			familyHtml += "				</div>";
			familyHtml += "		</div>";

			familyHtml += "		<div class='layui-inline'>";
			familyHtml += "				<label class='layui-form-label' style='margin-left: -31px;'>原因</label>";
			familyHtml += "				<div class='layui-input-block'>";
			familyHtml += "				<div class='layui-row'>";
			familyHtml += "					<input style='width: 300px;margin-left: -76px;' lay-verify='required' lay-reqText='不能为空' type='text' name='family_" + i + "_cause'  class='layui-input'  placeholder='请填写'>";
			familyHtml += "					</div>";
			familyHtml += "				</div>";
			familyHtml += "		</div>";

			familyHtml += "		<div class='layui-inline'>";
			familyHtml += "			<label class='layui-form-label' style='margin-left: 18px;width: 93px;'>医疗机构名称</label>";
			familyHtml += "			<div class='layui-input-block'>";
			familyHtml += "			<div class='layui-row'>";
			familyHtml += "				<input style='width: 220px;margin-left: -67px;' lay-verify='required' lay-reqText='不能为空' type='text' name='family_" + i + "_yiyuanname'  class='layui-input'  placeholder='请填写'>";
			familyHtml += "					</div>";
			familyHtml += "				</div>";
			familyHtml += "		</div>";

			familyHtml += "		<div class='layui-inline'>";
			familyHtml += "			<label class='layui-form-label' style='margin-left: -18px;margin-top: -5px;'>病案号</label>";
			familyHtml += "			<div class='layui-input-block'>";
			familyHtml += "				<div class='layui-row'>";
			familyHtml += "					<input style='width: 220px;margin-left: -73px;' lay-verify='required' lay-reqText='不能为空' type='text' id='disease' name='family_" + i + "_disease'  class='layui-input'  placeholder='请填写'>";
			
			familyHtml += "					<div style='margin-left: 159px; margin-top: -27px;'>";
			if (i != 0) {
				familyHtml += "					<i class='layui-icon layui-icon-delete deletefamily' data-code='" + newFamilyObj.identityCode + "' data-id='-1' style='font-size: 20px;' ></i>";
			}

			familyHtml += "						<i class='layui-icon layui-icon-add-circle addfamily' style='font-size: 20px;color: rgba(67, 207, 124, 1);'></i>";
			familyHtml += "					</div>";
			familyHtml += "				</div>";
			familyHtml += "			</div>";
			familyHtml += "		</div>";
			familyHtml += "</div>";
			
			$(".family").append(familyHtml)
			var len = $(".test8").length - 1;
			lay('.test8').each(function (index) {
				if (index === len) {
					laydate.render({
						elem: this,
						range: true,
						trigger: 'click'
					});
				}
			});
		}

		
		function familyListRender(){
			// 遍历家庭住院数据渲染
			var familyHtml = "";
			// 遍历家庭住院集合，给每一个对象都添加一个唯一标识码的属性
			hospitalizedData[2] = hospitalizedData[2].map(item=>{
				item.identityCode = makeCode(8);
				return item;
			})
		
			
			//console.log('遍历家庭住院信息：', hospitalizedData[2]);
			for (var i = 0; i < hospitalizedData[2].length; i++) {
				const item = hospitalizedData[2][i];
				familyHtml += "<div class='layui-form-item family-rowContainer' code='" + item.identityCode + "'>";
				
				familyHtml += "		<input type='hidden'name='userCode' value='" + item.userCode + "'>";
				familyHtml += "		<input type='hidden' value='" + item.id + "' name='family_" + i + "_id'>";
				familyHtml += "		<input type='hidden' value='" + item.hospitalizedtype + "'name='family_" + i + "_hospitalizedtype'>";
				
				familyHtml += "		<div class='layui-inline'>";
				familyHtml += "				<label class='layui-form-label' style='margin-left: 10px;'> 入出院日期</label>";
				familyHtml += "				<div class='layui-input-block'>";
				familyHtml += "					<div class='layui-row'>";
				familyHtml += "						<input style='width: 180px;margin-left: -76px;' type='text' name='family_" + i + "_timedeta' value='" + item.timedeta + " - " + item.dischargetime + "' class='layui-input test8 timedeta'  placeholder='请选择日期'>";
				familyHtml += "					</div>";
				familyHtml += "				</div>";
				familyHtml += "		</div>";

				familyHtml += "		<div class='layui-inline'>";
				familyHtml += "				<label class='layui-form-label' style='margin-left: -31px;'>原因</label>";
				familyHtml += "				<div class='layui-input-block'>";
				familyHtml += "				<div class='layui-row'>";
				familyHtml += "					<input style='width: 300px;margin-left: -76px;' type='text' lay-verify='required' lay-reqText='不能为空' name='family_" + i + "_cause' value='" + item.cause + "' class='layui-input'  placeholder='请填写'>";
				familyHtml += "					</div>";
				familyHtml += "				</div>";
				familyHtml += "		</div>";

				familyHtml += "		<div class='layui-inline'>";
				familyHtml += "			<label class='layui-form-label' style='margin-left: 18px;width: 93px;'>医疗机构名称</label>";
				familyHtml += "			<div class='layui-input-block'>";
				familyHtml += "			<div class='layui-row'>";
				familyHtml += "				<input style='width: 220px;margin-left: -67px;' type='text' lay-verify='required' lay-reqText='不能为空' name='family_" + i + "_yiyuanname' value='" + item.yiyuanname + "' class='layui-input'  placeholder='请填写'>";
				familyHtml += "					</div>";
				familyHtml += "				</div>";
				familyHtml += "		</div>";

				familyHtml += "		<div class='layui-inline'>";
				familyHtml += "			<label class='layui-form-label' style='margin-left: -18px;margin-top: -5px;'>病案号</label>";
				familyHtml += "			<div class='layui-input-block'>";
				familyHtml += "				<div class='layui-row'>";
				familyHtml += "					<input style='width: 220px;margin-left: -73px;' lay-verify='required' lay-reqText='不能为空' type='text' id='disease' name='family_" + i + "_disease' value='" + item.disease + "' class='layui-input'  placeholder='请填写'>";
				
				familyHtml += "					<div style='margin-left: 159px; margin-top: -27px;'>";
				if (i != 0) {
					familyHtml += "					<i class='layui-icon layui-icon-delete deletefamily' data-code='" + item.identityCode + "' data-id='" + item.id + "' style='font-size: 20px;' ></i>";
				}

				familyHtml += "						<i class='layui-icon layui-icon-add-circle addfamily' style='font-size: 20px;color: rgba(67, 207, 124, 1);'></i>";
				familyHtml += "					</div>";
				familyHtml += "				</div>";
				familyHtml += "			</div>";
				familyHtml += "		</div>";
				familyHtml += "</div>";
			}
		
			$(".family").html(familyHtml)
		}
			familyListRender();
		
		lay('.test8').each(function () {
			laydate.render({
				elem: this,
				range: true,
				isInitValue: true,// 是否允许填充初始值，默认为true
				trigger: 'click'
			});
		});


		// 家庭添加
		$(".family").on('click', '.addfamily', function () {
			chaseFamily()
		})
		// 删除
		$(".family").on('click', '.deletefamily', function () {
			const id = $(this).data('id');
			// 获取对象的唯一标识code
			const code = $(this).data('code');
			console.log("当前点击删除的ID为：",id,"Code为：",code)
			// 通过对象的唯一标识，从家庭住院集合中寻找所在的下标（页面上的每一行与家庭住院集合中的对象通过唯一标识一一对应上）
			const rowindex = hospitalizedData[2].findIndex(subscript=>subscript.identityCode===code);
			layer.confirm('是否确认删除？', {
				btn: ['确定', '取消'] // 可以无限个按钮
			}, function (index, layero) {
				console.log('刪除的當前下标为：', rowindex, '删除的当前ID为：', id);
				if(id===-1){
					$(".family-rowContainer[code='" + code + "']").remove();
		 			hospitalizedData[2].splice(rowindex, 1);
					console.log('刪除完最新數組：', hospitalizedData[2]);
				}else{
					 $.ajax({
						 	type: 'delete',
						 	url: '/YhUserPersonageHospitalized/deleteHospitalized',
						 	data: {
						 		_method: 'delete',
						 		id: id
						 	},
						 	success: function (data) {
						 		if (data === 1) {
									$(".family-rowContainer[code='" + code + "']").remove();
						 			hospitalizedData[2].splice(rowindex, 1);
									console.log('刪除完最新數組：', hospitalizedData[2]);
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

		$(".family").on('click', '.test8', function () {

		})

		// 单选框点击事件（括号里边是lay-filter的值）
		form.on('radio(have1)', function (data) {
			if (data.value == 1) {
				addPerson();
				$("#hidehospital").show();
			} else if (data.value == 2) {
				$("#hidehospital").hide();
			}
			form.render();
		});

		// 单选框点击事件（括号里边是lay-filter的值）
		form.on('radio(without)', function (data) {
			//console.log("单选框的值",data)
			if (data.value == 1) {
				chaseFamily()
				$("#hidefamily").show();
			} else if (data.value == 2) {
				$("#hidefamily").hide();
			}
		});



		// 监听提交


		form.on('submit(savehospital)', function (data) {
			console.log('form data：', data.field);
			// data.field是整个表单（对象），每一个字段（表单项）的格式：[类型，个人/家庭]_[行标]_[属性名]
			// 提交到后台的数据，格式：{personData:[{id:1,userCode:1}],familyData:[{id:1,userCode:1}]}
			let submitData = {
				personData: [],
				familyData: []
			};
			// submitData.personData = [];
			// submitData.familyData = [];

			// attrArr是这个表单对象的所有属性
			let attrArr = Object.keys(data.field)
			// console.log(attrArr, "---------------");
			// 从原始属性数组中过滤出包含person单词的属性，放入personAttrArr数组中
			let personAttrArr = attrArr.filter(item => item.includes('person'));
			// 从原始属性数组中过滤出包含family单词的属性，放入familyAttrArr数组中
			let familyAttrArr = attrArr.filter(item => item.includes('family'));
				// 等价于
			/*
			 * let familyAttrArr2 = function(item){ return
			 * item.includes('family') }
			 */
			// 遍历personAttrArr属性数组，将属性中person_字样替换成空字符串
			personAttrArr = personAttrArr.map(item => item.replace('person_', ''));
			// personAttrArr = personAttrArr.map(item =>{
			// item.replace('person_', '')});
			// 遍历familyAttrArr属性数组，将属性中family_字样替换成空字符串返回一个新数组
			familyAttrArr = familyAttrArr.map(item => item.replace('family_', ''));

			// -----------------------------个人住院信息Begin---------------------------------------------
			// 个人住院数据对象，格式：{"0":{"id":"1","timedeta":""},"1":{"id":"2","timedeta":""}}
			let personDataObj = {};
			// 遍历personAttrArr属性数组
			for (var i = 0; i < personAttrArr.length; i++) {
				// 格式：[下标]_[属性名]，如：0_yiyuanname
				const attr = personAttrArr[i];
			
				// 临时数组，格式：[下标]_[属性名]，如：0_yiyuanname
				let _tempArr = attr.split('_');
				const _rowIndex = _tempArr[0]; // 行标
				const _fieldName = _tempArr[1]; // 字段名
				
				// 判断个人住院数据对象是否存在指定行标的属性
				// 1、如果不存在则生成一个默认的空对象，并添加当前属性到对象中
				// 2、如果存在则直接将当前属性添加到对象中
				if (personDataObj.hasOwnProperty(_rowIndex) === false) {
					// 给personDataObj增加了名称为[_rowIndex]的一个属性
					personDataObj[_rowIndex] = {};
				}
				personDataObj[_rowIndex][_fieldName] = data.field['person_' + attr];
			}
			console.log(personDataObj, "+++++++++++++");
			// 遍历个人住院数据对象
			for (let attr in personDataObj) {
				// 通过属性(此处指行下标)获取属性对应的行数据对象
				const _tempObj = personDataObj[attr];

				// 添加公共信息
				_tempObj['userCode'] = data.field.userCode;
				_tempObj['hospitalizedtype'] = data.field['person_' + attr + '_hospitalizedtype'];
				// _tempObj['dischargetime'] ="1";
				// 将行数据对象添加到个人住院数据数组中
				submitData.personData.push(_tempObj);
			}
			console.log(submitData.personData, "----------");
			// -----------------------------个人住院信息
			// End---------------------------------------------

			// -----------------------------家庭住院信息
			// Begin---------------------------------------------
			// 家庭住院数据对象，格式：{0:{id:"1",timedeta:""},1:{id:"2",timedeta:""}}
			let familyDataObj = {};

			// 遍历familyAttrArr属性数组
			for (var i = 0; i < familyAttrArr.length; i++) {
				// 格式：[下标]_[属性名]，如：0_yiyuanname
				const attr = familyAttrArr[i];

				// 临时数组，格式：[下标]_[属性名]，如：0_yiyuanname
				let _tempArr = attr.split('_');
				const _rowIndex = _tempArr[0]; // 行标
				const _fieldName = _tempArr[1]; // 字段名

				// 判断家庭住院数据对象是否存在指定行标的属性
				// 1、如果不存在则生成一个默认的空对象，并添加当前属性到对象中
				// 2、如果存在则直接将当前属性添加到对象中
				if (!familyDataObj.hasOwnProperty(_rowIndex)) familyDataObj[_rowIndex] = {};
				familyDataObj[_rowIndex][_fieldName] = data.field['family_' + attr];
			}
			// console.log(familyDataObj);
			// 遍历家庭住院数据对象的属性
			for (let attr in familyDataObj) {
				// 通过属性(此处指行下标)获取属性对应的行数据对象
				const _tempObj = familyDataObj[attr];

				// 添加公共信息
				_tempObj['userCode'] = data.field.userCode;
				_tempObj['hospitalizedtype'] = data.field['family_' + attr + '_hospitalizedtype'];
				// _tempObj['dischargetime'] ="1";
				// 将行数据对象添加到家庭住院数据数组中
				submitData.familyData.push(_tempObj);
			}
			
			// 将JSON对象转化为JSON字符串
			var data = JSON.stringify(submitData);
			console.log(data,"!!!!!!!!!!!!!!!!!!!");
			 //console.log(data,"===========================");
			// -----------------------------家庭住院信息
			// alert(data)

			// End---------------------------------------------

			
			$.ajax({
			    type: 'post',
			    url: '/YhUserPersonageHospitalized/compileHospitalized',
			    data: data,
			    contentType: 'application/json;charset=UTF-8',
			    traditional: true,
			    // 是否使用传统的方式浅层序列化,若有数组参数或对象参数需要设置true!!!!!! 
			   success : function(data) {
				   console.log(data,"======================")
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

		// pasLen是你想要的密码的长度
		function makeCode(pasLen) {
			let pasArr = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'];
// const pasArr =
// ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9','_','-','$','%','&','@','+','!'];
			var password = '';
			var pasArrLen = pasArr.length;
			for (var i=0; i<pasLen; i++){
				var x = Math.floor(Math.random()*pasArrLen);
				password += pasArr[x];
			}
			return password;
// console.log(password);
		}
	});
})


