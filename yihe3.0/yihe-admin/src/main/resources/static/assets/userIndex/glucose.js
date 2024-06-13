$(function(){
	var env = '';//当前所选的类型
	layui.use([ 'layer', 'form', 'laydate', 'notice','table' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		var table = layui.table;
		
		var userCode = $("input[name=userCode]").val();
		
		  //  第一个实例
		 var glucoseTable =  table.render({
			 id:'glucoseTable',
		    elem: '#glucoseTable'
		    ,url: '/YHUserBloodsuger/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		      {field: 'bs',title: '血糖',
		    	   templet:function(d){
		    		   return d.bs + 'mmol/L';
		    	   }
		       },
		       
		       {field: 'env',title: '测量场景'}
		      ,{field:'entryTime',align: 'center', title: '检测时间',width: 300}
		      
		    ]],
		    done: function(res, curr, count){
		        if(count > 20) {
		        	$("#glucoseVo .layui-table-page").show();
		        }else {
		        	$("#glucoseVo .layui-table-page").hide();
		        }
		      }
		  });
		 
			// 基于准备好的dom，初始化echarts实例
			$('#glucose').css('width',$(window).width()-50);
			var myChart = echarts.init(document.getElementById('glucose'));
			var count = 0;
		 
		/* var tip_index;
		 $("#glucoselist").hover(function(){
		     tip_index =  layer.tips('列表显示', this,{
		         tips: [3, 'rgba(0, 0, 0, 1)'],
		         time:0
		     });
		 },function(){
		     layer.close(tip_index);
		 });
		 
		 var tip_index;
		 $("#glucosechart").hover(function(){
		     tip_index =  layer.tips('图表显示', this,{
		         tips: [3, 'rgba(0, 0, 0, 1)'],
		         time:0
		     });
		 },function(){
		     layer.close(tip_index);
		 });*/
		 
		 $("#glucoselist").on('click',function(){
			 $(".glucoseNoData").hide();
			 $(".btnGroup_bloodGlucoseType").hide();
			 $("#glucose2").hide();
			 $("#glucoseTable2").show();
			 var chart=document.querySelector("#glucosechart");
			 chart.style.color="rgba(153, 153, 153, 1)";
			 
			 var list=document.querySelector("#glucoselist");
			 list.style.color="rgba(24, 144, 255, 1)";
			
		})  
		 
		 $("#glucosechart").on('click',function(){
			 $(".btnGroup_bloodGlucoseType").show();
			//如果数据类型为空
			 if(env == ""){
				 //点击第一个按钮
				 $(".btnGroup_bloodGlucoseType button:first").click();
			 }
			 glucoseData();
		    	//$("#glucose2").show();
				 $("#glucoseTable2").hide();
				 var chart=document.querySelector("#glucosechart");
				 chart.style.color="rgba(24, 144, 255, 1)";
				 
				 var list=document.querySelector("#glucoselist");
				 list.style.color="rgba(153, 153, 153, 1)";
			 
		  })
	  
		  
		  //血糖按钮组事件
		  $(".btnGroup_bloodGlucoseType button").click(function(){
			  $(".btnGroup_bloodGlucoseType button").removeClass("layui-btn-normal").addClass("layui-btn-primary");
			  	$(this).removeClass("layui-btn-primary").addClass("layui-btn-normal");
			  	env = $(this).attr("data-type");
			  	console.log("切换的类型"+env)
			  	 $("#glucoseright").show();
			  	page = 1;
			  	glucoseData();
			  	
		  });
		 
		 $("#glucoseleft").on('click',function(){
			 $("#glucoseright").show();
			 page = page-1;
		  	//当前page乘以size
			glucoseData();
		  if(page == 1){
			 	$("#glucoseleft").hide();
		 		  notice.error({
						title : '消息通知',
						message : '没有更多数据了!'
					});  
			 	
			}
		 
	  })
	  
	   $("#glucoseright").on('click',function(){
		 $("#glucoseleft").show();
		page = page+1;
		//当前page乘以size
		presentPage = page*limit
		if(presentPage>=count){
		 	notice.error({
				title : '消息通知',
				message : '没有更多数据了!'
			});
		 	$("#glucoseright").hide();
		}
		
		glucoseData();
	  })
		 
		  var page = 1;
		  var limit = 30;
		  function glucoseData(){
			  $.ajax({
					type:'get', 
					url:'/YHUserBloodsuger/selectBloodsuger',
					data:{userCode:userCode,page:page,env:env,limit:limit}, 
					success : function(data){
						 if(data.count==0){
                             $("#glucose2").hide();
                             $(".glucoseNoData").show();
                        }
						 
						 if(data.count>0){
                             $("#glucose2").show();
                             $(".glucoseNoData").hide();
                        }
						 
						count = data.count;
						 var bsArray = data.data.map( item => {
						    return item.bs;
						  }) 
						  console.log(bsArray,"!!!")
					 	var entryTimeArray = data.data.map( item => {
							  return dateFormat(item.entryTime, "MM-dd");
						  })
						  initglucose(bsArray,entryTimeArray); 	
						
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
			
			function initglucose(bsArray,entryTimeArray){
			    // 指定图表的配置项和数据
					/*option = {
						color: ["rgba(42, 130, 228, 1)"],
							
						  tooltip:{
						        trigger :'item',
						        formatter:'血糖 （mmol/L）{c0}'
						    },
							
					    xAxis: {
					        type: 'category',
					        data: entryTimeArray,
					        nameTextStyle:{
				                fontSize:15,
				                color:'red'
				            }
					    },
					    yAxis: {
					    	name: 'mmol/L',
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
					    
					    series: [{
					        data: bsArray,
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
					    /*toolbox: {
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
					    	name: 'mmol/L',
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
					    series: [{
					    	name:'血糖 （mmol/L）',
					        data: bsArray,
					        type: 'line',
					        smooth: true
					    }]
					};
			    // 使用刚指定的配置项和数据显示图表。
			    myChart.setOption(option);	
			}
	
	});
})