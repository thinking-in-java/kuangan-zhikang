
$(function(){

	layui.use([ 'layer', 'form', 'laydate', 'notice','table','upload' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		var table = layui.table;
		var upload = layui.upload;
		var userCode = $("input[name=userCode]").val();
		//监听行工具事件
		table.on('tool(quantumdetectionTable)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
			var data = obj.data //获得当前行数据
				,layEvent = obj.event; //获得 lay-event 对应的值
			  if(layEvent === 'del'){
				layer.confirm('真的删除行么', function(index){
					obj.del(); //删除对应行（tr）的DOM结构
					layer.close(index);
					//向服务端发送删除指令
					$.ajax({
						type:'get',
						url:'/YhUserQuantumDetectionWeb/addInfo',
						data:{usercode:userCode,qid:data.qid,isDel:1},
						success : function(data){
							table.reload('quantumdetectionTable',{
								url: '/YhUserQuantumDetectionWeb/page?usercode='+userCode
							})
						}

					})
				});
			} else if(layEvent === 'edit'){

				  layer.open({
					  type: 1,
					  skin: 'layui-layer-rim', //加上边框
					  area: ['420px', '440px'], //宽高
					  content: $("#file_upload_div_updateq"),
					  cancel: function(index, layero){
						   $(".file_upload_div_updateq").css("display","none");

					  }
				  });
				  $("#qidupdateq").val(data.qid)
				  $("#resurtTextupdateq").val(data.resurtText)
				  layui.$('#uploadDemoViewupdateq').removeClass('layui-hide').find('img').attr('src', data.img);
				  $("#imgupdateq").val(data.img);
			}
		});
		 var photosDemoId = [];
		  //  第一个实例
		 var quantumdetectionTable =  table.render({
			 id:'quantumdetectionTable',
		    elem: '#quantumdetectionTable'
		    ,url: '/YhUserQuantumDetectionWeb/page?usercode='+userCode
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		      {field: 'img',title: '图片',style:"height:50px",
		    	   //templet: '<div><img src="{{ d.imgUrl }}" alt="" style="width:80px; height:80px;" onclick="showBigImage(this)""></div>'
		    	   templet:function(d){
		    		   let chaseCode = makeCode(8);
		    		   //console.info(chaseCode+"随机数");
		    		   var divId = "layer-photos-demo" + chaseCode;
		    		   photosDemoId.push(divId);
		    		   //console.info(photosDemoId+"所有的id");
		    		   return "<div id='"+divId+"' class='layer-photos-demo'> <img  src='" + d.img+"' style=\"height:50px\" /></div>";
		    	  
		    	   }
		       }
		       /*{field: 'milletStraw',title: '谷草转氨酶',
		    	   templet:function(d){
		    		   return d.milletStraw + 'u/L';
		    	   }
		       }*/
				 ,{field:'resurtText',align: 'center', title: '标题',width: 100}
		      ,{field:'qtime',align: 'center', title: '上传时间',width: 300}
				 ,{ title: '操作', templet:function (d){
				 	return " <a class=\"layui-btn layui-btn-xs upload_btn\" value='"+d.qid+"' lay-data=\"{qid: '"+d.qid+"'}\"   lay-event=\"edit\">编辑</a>\n" +

						"\t\t\t\t\t <a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"del\">删除</a>"
					 }}

		    ]],
		    done: function(res, curr, count){

		    	for(var i = 0 ; i<photosDemoId.length ; i++){
		    		layer.photos({
		 			   photos: '#'+photosDemoId[i]
		 			   ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
		 			   ,closeBtn: true
		 			   ,shade: .1
		 			 }); 
		    	}
		        if(count > 20) {
		        	$("#quantumdetection .layui-table-page").show();
		        }else {
		        	$("#quantumdetection .layui-table-page").hide();
		        }
		      }
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
//				console.log(password);
			}



	});
	 
})

