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
		 var arteryTab =  table.render({
			 id:'arteryTab',
		    elem: '#arteryTab'
		    ,url: '/YhUserArtery/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		     /* {field: 'sbp',title: '最大血压 （收缩压）',
		    	   templet:function(d){
		    		   return d.sbp + 'mmHg';
		    	   }
		       },*/
		     /*  {field: 'dbp',title: '最小血压（舒张压）',
		    	   templet:function(d){
		    		   return d.dbp + 'mmHg';
		    	   }
		       },*/
		    /*   {field: 'pulse',title: '脉博（心率）',
		    	   templet:function(d){
		    		   return d.pulse + '次/分钟';
		    	   }
		       }
		       
		       ,*/
		       {field: 'avi',title: '中心动脉硬度指标' }
		       ,
		       {field: 'api',title: '上臂动脉硬度指标'}
		       ,
		       {field: 'csbp',title: '中心动脉血压值',
		    	   templet:function(d){
		    		   return d.csbp + 'mmHg';
		    	   }
		       }
		       ,
		       {field: 'capp',title: '中心动脉脉压值',
		    	   templet:function(d){
		    		   return d.capp + 'mmHg';
		    	   }
		       }
		       
		      ,{field:'time',align: 'center', title: '检测时间',width: 300}
		      
		    ]],
		    done: function(res, curr, count){
		        if(count > 20) {
		        	$("#artery .layui-table-page").show();
		        }else {
		        	$("#artery .layui-table-page").hide();
		        }
		      }
		  });
	});
})