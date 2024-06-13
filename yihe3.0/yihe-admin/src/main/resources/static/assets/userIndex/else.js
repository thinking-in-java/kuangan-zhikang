$(function(){

	layui.use([ 'layer', 'form', 'laydate', 'notice','table' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		var table = layui.table;
		
		var userCode = $("input[name=userCode]").val();
		
		  //  第一个实例
		 var elseTable =  table.render({
			 id:'elseTable',
		    elem: '#elseTable'
		    ,url: '/YHUserOtherfunction/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		      {field: 'urineMicroprotein',title: '尿微量蛋白',
		    	   templet:function(d){
		    		   return d.urineMicroprotein + 'μg/min';
		    	   }
		       },
		       {field: 'glycosylatedHemoglobin',title: '糖化血红蛋白',
		    	   templet:function(d){
		    		   return d.glycosylatedHemoglobin + '%';
		    	   }
		       },
		       {field: 'hcy',title: '同型半胱氨酸',
		    	   templet:function(d){
		    		   return d.hcy + 'μmol/L';
		    	   }
		       }
		      ,{field:'inserttime',align: 'center', title: '检测时间',width: 300}
		      
		    ]],
		    done: function(res, curr, count){
		        if(count > 20) {
		        	$("#else .layui-table-page").show();
		        }else {
		        	$("#else .layui-table-page").hide();
		        }
		      }
		  });
	});
})