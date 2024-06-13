$(function(){
	window.onload = function() {
		if (location.search.indexOf("?") == -1) {
			location.href += "?myurl";
		} else {
			if (location.search.indexOf("myurl") == -1)
				location.href += "&myurl";
		}
	}
	layui.use([ 'layer', 'form', 'laydate', 'notice','table' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		var table = layui.table;
		
		// var userCode = [[${usercode}]]; 
		var userCode = $("input[name=userCode]").val();
		console.log("用户code=================="+userCode);
		  // 第一个实例
		 var bloodTable =  table.render({
			 id:'bloodTable',
		    elem: '#bloodTable'
		    ,url: '/YHUserOxygen/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		      {field: 'oxygen',title: '血氧',
		    	   templet:function(d){
		    		   return d.oxygen + '%';
		    	   }
		       }
		      ,{field:'entryTime',align: 'center', title: '检测时间',width: 300}
		      
		    ]],
		    done: function(res, curr, count){
		        if(count > 20) {
		        	$("#oxygen .layui-table-page").show();
		        }else {
		        	$("#oxygen .layui-table-page").hide();
		        }
		      }
		  });
		 
			// 基于准备好的dom，初始化echarts实例
			$('#blood').css('width',$(window).width()-50);
			var myChart = echarts.init(document.getElementById('blood'));
			var count = 0;
		 
		 /*var tip_index;
		 $("#bloodlist").hover(function(){
		     tip_index =  layer.tips('列表显示', this,{
		         tips: [3, 'rgba(0, 0, 0, 1)'],
		         time:0
		     });
		 },function(){
		     layer.close(tip_index);
		 });
		 
		 var tip_index;
		 $("#bloodchart").hover(function(){
		     tip_index =  layer.tips('图表显示', this,{
		         tips: [3, 'rgba(0, 0, 0, 1)'],
		         time:0
		     });
		 },function(){
		     layer.close(tip_index);
		 });*/
		 
		 $("#bloodlist").on('click',function(){
			 $(".bloodNoData").hide();
			 $("#blood2").hide();
			 $("#bloodTable2").show();
			 var chart=document.querySelector("#bloodchart");
			 chart.style.color="rgba(153, 153, 153, 1)";
			 
			 var list=document.querySelector("#bloodlist");
			 list.style.color="rgba(24, 144, 255, 1)";
			
		})  
		 
		 $("#bloodchart").on('click',function(){
		    	OxygenData();
		    	$("#blood2").show();
				 $("#bloodTable2").hide();
				 var chart=document.querySelector("#bloodchart");
				 chart.style.color="rgba(24, 144, 255, 1)";
				 
				 var list=document.querySelector("#bloodlist");
				 list.style.color="rgba(153, 153, 153, 1)";
			 
		  })
		  
		 $("#bloodleft").on('click',function(){
			 $("#bloodright").show();
		  page = page-1;
			OxygenData();
			  if(page==1){
				 	$("#bloodleft").hide();
				 	notice.error({
						title : '消息通知',
						message : '没有更多数据了!'
					});
				}
	  })
	  
	   $("#bloodright").on('click',function(){
		 $("#bloodleft").show();
		page = page+1;
		//当前page乘以size
		presentPage = page*limit
		if(presentPage>=count){
		 	notice.error({
				title : '消息通知',
				message : '没有更多数据了!'
			});
		 	 $("#bloodright").hide();
		}
		//$('#bloodright').removeAttr('click');//去掉标签中的onclick事件
		OxygenData();
	  })
		 
		  var page = 1;
		  var limit = 30;
		  function OxygenData(){
			  $.ajax({
					type:'get', 
					url:'/YHUserOxygen/selectOxygen',
					data:{userCode:userCode,page:page,limit:limit}, 
					success : function(data){
						if(data.count==0){
							$("#blood2").hide();
							$(".bloodNoData").show();
						}
						console.log("后台返回的数据：",data)
						count = data.count;
						 var oxygenArray = data.data.map( item => {
						    return item.oxygen;
						  }) 
						  console.log(oxygenArray,"!!!")
					 	var entryTimeArray = data.data.map( item => {
							  return dateFormat(item.entryTime, "MM-dd");
						  })
						  initblood(oxygenArray,entryTimeArray); 	
						
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
			
			function initblood(oxygenArray,entryTimeArray){
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
					    	name: '%',
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
					        formatter:'血氧 （%）{c0}'
					    },
					    series: [{
					        data: oxygenArray,
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
					    	name: '%',
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
					    	name:'血氧 （%）',
					        data: oxygenArray,
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