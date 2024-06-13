$(function(){

	layui.use([ 'layer', 'form', 'laydate', 'notice','table' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		var table = layui.table;
		
		var  userCode = $("input[name=userCode]").val();
		
		  // 第一个实例
		 var bimTable =  table.render({
			 id:'bimTable',
		    elem: '#bimTable'
		    ,url: '/YHUserBmi/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		      {field: 'uWeight',title: '体重',
		    	   templet:function(d){
		    		   return d.uWeight + 'kg';
		    	   }
		       }
		      ,{field:'entryTime',align: 'center', title: '检测时间',width: 300}
		      
		    ]],
		    done: function(res, curr, count){
		        if(count > 20) {
		        	$("#bimVo .layui-table-page").show();
		        }else {
		        	$("#bimVo .layui-table-page").hide();
		        }
		      }
		  });
		 
			// 基于准备好的dom，初始化echarts实例
			$('#bim').css('width',$(window).width()-50);
			var myChart = echarts.init(document.getElementById('bim'));
			var count = 0;
		 
		 /*var tip_index;
		 $("#bimlist").hover(function(){
		     tip_index =  layer.tips('列表显示', this,{
		         tips: [3, 'rgba(0, 0, 0, 1)'],
		         time:0
		     });
		 },function(){
		     layer.close(tip_index);
		 });
		 
		 var tip_index;
		 $("#bimchart").hover(function(){
		     tip_index =  layer.tips('图表显示', this,{
		         tips: [3, 'rgba(0, 0, 0, 1)'],
		         time:0
		     });
		 },function(){
		     layer.close(tip_index);
		 });*/
		 
		 $("#bimlist").on('click',function(){
			 $(".bimNoData").hide();
			 $("#bim2").hide();
			 $("#bimTable2").show();
			 var chart=document.querySelector("#bimchart");
			 chart.style.color="rgba(153, 153, 153, 1)";
			 
			 var list=document.querySelector("#bimchart");
			 list.style.color="rgba(24, 144, 255, 1)";
			
		})  
		 
		 $("#bimchart").on('click',function(){
			 bimData();
		    	$("#bim2").show();
				 $("#bimTable2").hide();
				 var chart=document.querySelector("#bimchart");
				 chart.style.color="rgba(24, 144, 255, 1)";
				 
				 var list=document.querySelector("#bimchart");
				 list.style.color="rgba(153, 153, 153, 1)";
			 
		  })
		  
		 $("#bimleft").on('click',function(){
			 $("#bimright").show();
			  page = page-1;
			  //当前page乘以size
			  bimData();
		  	if(page==1){
			 	$("#bimleft").hide();
		 			notice.error({
						title : '消息通知',
						message : '没有更多数据了!'
					}); 
			 	
			}
		 
	  })
	  
	   $("#bimright").on('click',function(){
		 $("#bimleft").show();
		page = page+1;
		//当前page乘以size
		presentPage = page*limit
		if(presentPage>=count){
		 	notice.error({
				title : '消息通知',
				message : '没有更多数据了!'
			});
		 	$("#bimright").hide();
		}
		
		bimData();
	  })
		 
		  var page = 1;
		  var limit = 30;
		  function bimData(){
			  $.ajax({
					type:'get', 
					url:'/YHUserBmi/selectYHUserBmi',
					data:{userCode:userCode,page:page,limit:limit}, 
					success : function(data){
						console.info("体重数据",data);
						 if(data.count==0){
                             $("#bim2").hide();
                             $(".bimNoData").show();
                        }
						count = data.count;
						 var uWeightArray = data.data.map( item => {
						    return item.uWeight;
						  }) 
						
					 	var entryTimeArray = data.data.map( item => {
							  return dateFormat(item.entryTime, "MM-dd");
						  })
						  initbim(uWeightArray,entryTimeArray); 	
						
					}
					
				})
				
		  }
		  
			function dateFormat(dateStr, fmt) {
			    var date = new Date(dateStr);
			    var o = {
			        "M+": date.getMonth() + 1, // 月份
			        "d+": date.getDate(), // 日
			        "h+": date.getHours(), // 小时
			        "m+": date.getMinutes(), // 分
			        "s+": date.getSeconds(), // 秒
			        "q+": Math.floor((date.getMonth() + 3) / 3), // 季度
			        "S": date.getMilliseconds() // 毫秒
			    };
			    if (/(y+)/.test(fmt))
			        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
			    for (var k in o)
			        if (new RegExp("(" + k + ")").test(fmt))
			            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			    return fmt;
			}
			
			function initbim(uWeightArray,entryTimeArray){
			    // 指定图表的配置项和数据
				/*	option = {
							   color: ["rgba(42, 130, 228, 1)"],
					    xAxis: {
					        type: 'category',
					        data: entryTimeArray,
					        nameTextStyle:{
				                fontSize:15,
				                color:'red'
				            }
					    },
					    yAxis: {
					    	name: 'kg',
					        type: 'value',
					        scale:true,
					        axisLine:{
				                show:false
				            },
				            axisTick:{
				                show:false
				            },
				            splitLine: {
				                show: true,
				                lineStyle:{
				                    type:'dashed'
				                }
				            },
					    },
					    
					    tooltip:{
					        trigger :'item',
					        formatter:'体重 （kg）{c0}'
					    },
					    series: [{
					        data: uWeightArray,
					        type: 'line',
					        smooth: true
					    }]
					};*/
				option = {
						color: ["rgba(42, 130, 228, 1)"],
					    tooltip: {
					        trigger: 'axis'
					    },
					    
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
					   /* toolbox: {
					        feature: {
					            saveAsImage: {}
					        }
					    },*/
					    xAxis: {
					        type: 'category',
					        data: entryTimeArray,
					        nameTextStyle:{
				                fontSize:15,
				                color:'red'
				            }
					    },
					    yAxis: {
					    	name: 'kg',
					        type: 'value',
					        scale:true,
					        axisLine:{
				                show:false
				            },
				            axisTick:{
				                show:false
				            },
				            splitLine: {
				                show: true,
				                lineStyle:{
				                    type:'dashed'
				                }
				            },
					    },
					    series: [
					    	{
					    	name:'体重 （kg）',
					    	data: uWeightArray,
					        type: 'line',
					        smooth: true
					    },
					    
					  ]
					};
			    // 使用刚指定的配置项和数据显示图表。
			    myChart.setOption(option);	
			}
	
	});
})