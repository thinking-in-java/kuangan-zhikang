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
		 var kidneyTable =  table.render({
			 id:'kidneyTable',
		    elem: '#kidneyTable'
		    ,url: '/YHUserRenalfunction/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		      {field: 'creatinine',title: '血肌酐',
		    	   templet:function(d){
		    		   return d.creatinine + 'μmol/L';
		    	   }
		       },
		       {field: 'ureaNitrogen',title: '血尿素氮',
		    	   templet:function(d){
		    		   return d.ureaNitrogen + 'mmol/L';
		    	   }
		       },
		       {field: 'uricAcid',title: '血尿酸',
		    	   templet:function(d){
		    		   return d.uricAcid + 'μmol/L';
		    	   }
		       }
		      ,{field:'inerttime',align: 'center', title: '检测时间',width: 300}
		      
		    ]],
		    done: function(res, curr, count){
		        if(count > 20) {
		        	$("#kidney .layui-table-page").show();
		        }else {
		        	$("#kidney .layui-table-page").hide();
		        }
		      }
		  });
	});
})