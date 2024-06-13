$(function(){
	
	layui.use([ 'layer', 'form', 'laydate', 'notice','table' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		var table = layui.table;
		
		var userCode = $("input[name=userCode]").val();
	
		 var photosDemoId = [];
		  //  第一个实例
		 var ecgTable =  table.render({
			 id:'ecgTable',
		    elem: '#ecgTable'
		    ,url: '/YHUserEcg/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		      {field: 'pigPath',title: '心电图',style:"height:50px",
		    	   //templet: '<div><img src="{{ d.imgUrl }}" alt="" style="width:80px; height:80px;" onclick="showBigImage(this)""></div>'
		    	   templet:function(d){
		    		   let chaseCode = makeCode(8);
		    		   //console.info(chaseCode+"随机数");
		    		   var divId = "layer-photos-demo" + chaseCode;
		    		   photosDemoId.push(divId);
		    		   //console.info(photosDemoId+"所有的id");
		    		   return "<div id='"+divId+"' class='layer-photos-demo'> <img  src='" + d.pigPath+"' style=\"height:50px\" /></div>" + d.resurtText;
		    	  
		    	   }
		       }
		       /*{field: 'milletStraw',title: '谷草转氨酶',
		    	   templet:function(d){
		    		   return d.milletStraw + 'u/L';
		    	   }
		       }*/	
		      ,{field:'entryTime',align: 'center', title: '检测时间',width: 300}
		      
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
		        	$("#ecg .layui-table-page").show();
		        }else {
		        	$("#ecg .layui-table-page").hide();
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
//function showBigImage() {
//			var src = $("#imgClick").attr("src");
//			//调用示例
//		 	layer.photos({
//			   photos: '.layer-photos-demo'
//			   ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
//			   ,closeBtn: true
//			   ,shade: .1
//			 }); 
//		 }

