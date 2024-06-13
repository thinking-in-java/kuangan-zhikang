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
		 var urinalysisTable =  table.render({
			 id:'urinalysisTable',
		    elem: '#urinalysisTable'
		    ,url: '/YHUserUba/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		      {field: 'redbloodcell',title: '红细胞'},
		      {field: 'whitebloodcell',title: '白细胞'},
		       {field: 'urineprotein',title: '尿蛋白'},
		       {field: 'ketone',title: '酮体'}
		      ,{field:'adddate',align: 'center', title: '检测时间',width: 300}
		      
		    ]],
		    done: function(res, curr, count){
		        if(count > 20) {
		        	$("#urinalys .layui-table-page").show();
		        }else {
		        	$("#urinalys .layui-table-page").hide();
		        }
		      }
		  });
	});
})