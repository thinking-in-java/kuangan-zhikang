$(function(){
	layui.use([ 'layer', 'form', 'laydate', 'notice','table' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		var table = layui.table;
		
		var userCode = $("input[name=userCode]").val();
		
		  // 第一个实例
		 var  bloodTable =  table.render({
			 id:'bloodfatTable',
		    elem: '#bloodfatTable'
		    ,url: '/YHUserBloodfat/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		      {field: 'cholesterin',title: '总胆固醇',
		    	   templet:function(d){
		    		   return d.cholesterin + 'mmol/L';
		    	   }
		       },
		       {field: 'tring',title: '甘油三脂',
		    	   templet:function(d){
		    		   return d.tring + 'mmol/L';
		    	   }
		       },
		       {field: 'hdl',title: '高密度脂蛋白胆固醇',
		    	   templet:function(d){
		    		   return d.hdl + 'mmol/L';
		    	   }
		       },
		       {field: 'ldl',title: '低密度脂蛋白胆固醇',
		    	   templet:function(d){
		    		   return d.ldl + 'mmol/L';
		    	   }
		       }
		      ,{field:'entryTime',align: 'center', title: '检测时间',width: 300}
		      
		    ]],
		    done: function(res, curr, count){
		        if(count > 20) {
		        	$("#bloodfat .layui-table-page").show();
		        }else {
		        	$("#bloodfat .layui-table-page").hide();
		        }
		      }
		  });
	});
})