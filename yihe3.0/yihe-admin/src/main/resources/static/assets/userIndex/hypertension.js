$(function(){
	var testHj = '';//当前所选的类型

	layui.use([ 'layer', 'form', 'laydate', 'notice','table' ],function() {
		var $ = layui.jquery;
		var layer = layui.layer;
		var form = layui.form;
		var laydate = layui.laydate;
		var notice = layui.notice;
		var table = layui.table;
		
		var userCode = $("input[name=userCode]").val();
		
		  //  第一个实例
		 var pressureTable =  table.render({
			 id:'pressureTable',
		    elem: '#pressureTable'
		    ,url: '/YHUserBloodPressure/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		      {field: 'dbp',title: '舒张压',
		    	   templet:function(d){
		    		   return d.dbp + 'mmHg';
		    	   }
		       },
		       {field: 'sbp',title: '收缩压',
		    	   templet:function(d){
		    		   return d.sbp + 'mmHg';
		    	   }
		       },
		       {field: 'testHj',title: '测量场景',
		    	   templet:function(d){
		    		   return d.testHj;
		    	   }
		       }
		      ,{field:'entryTime',align: 'center', title: '检测时间',width: 304}
		      
		    ]],
		    done: function(res, curr, count){
		        if(count > 20) {
		        	$("#pressureVo .layui-table-page").show();
		        }else {
		        	$("#pressureVo .layui-table-page").hide();
		        }
		      }
		  });
		 
			// 基于准备好的dom，初始化echarts实例
			$('#pressure').css('width',$(window).width()-50);
			var myChart = echarts.init(document.getElementById('pressure'));
			var count = 0;
		 
		/* var tip_index;
		 $("#pressurelist").hover(function(){
		     tip_index =  layer.tips('列表显示', this,{
		         tips: [3, 'rgba(0, 0, 0, 1)'],
		         time:0
		     });
		 },function(){
		     layer.close(tip_index);
		 });
		 
		 var tip_index;
		 $("#pressurechart").hover(function(){
		     tip_index =  layer.tips('图表显示', this,{
		         tips: [3, 'rgba(0, 0, 0, 1)'],
		         time:0
		     });
		 },function(){
		     layer.close(tip_index);
		 });*/
		 
		 $("#pressurelist").on('click',function(){
			 $(".btnGroup_bloodPressureType").hide();
			 $("#pressure2").hide();
			 $(".pressureNoData").hide();
			 $("#pressureTable2").show();
			 var chart=document.querySelector("#pressurechart");
			 chart.style.color="rgba(153, 153, 153, 1)";
			 
			 var list=document.querySelector("#pressurelist");
			 list.style.color="rgba(24, 144, 255, 1)";
			
		})  
		 
		 $("#pressurechart").on('click',function(){
			 $(".btnGroup_bloodPressureType").show();
			 //如果数据类型为空
			 if(testHj == ""){
				 //点击第一个按钮
				 $(".btnGroup_bloodPressureType button:first").click();
				
			 }
			 	pressureData();
		    	//$("#pressure2").show();
				 $("#pressureTable2").hide();
				 var chart=document.querySelector("#pressurechart");
				 chart.style.color="rgba(24, 144, 255, 1)";
				 
				 var list=document.querySelector("#pressurelist");
				 list.style.color="rgba(153, 153, 153, 1)";
			 
		  })
		  //血压按钮组事件
		  $(".btnGroup_bloodPressureType button").click(function(){
			  $(".btnGroup_bloodPressureType button").removeClass("layui-btn-normal").addClass("layui-btn-primary");
			  	$(this).removeClass("layui-btn-primary").addClass("layui-btn-normal");
			  	testHj = $(this).attr("data-type");
			  	$("#pressureright").show();
			  	page=1;
			  	pressureData();
			  	
		  });
		 
		 $("#pressureleft").on('click',function(){
			 $("#pressureright").show();
			  page = page-1;
			//当前page乘以size
			presentPage = page*limit
			pressureData();
			  if(page==1){
				 	$("#pressureleft").hide();
				 	notice.error({
						title : '消息通知',
						message : '没有更多数据了!'
					});
				}
		  })
		 
		 
		  
		 $("#pressureright").on('click',function(){
		 $("#pressureleft").show();
		 	page = page+1;
		 	 pressureData();	
		//当前page乘以size
		 	presentPage = page*limit
		 	if(presentPage>=count){
			 	notice.error({
					title : '消息通知',
					message : '没有更多数据了!'
				});
			 	 $("#pressureright").hide();
		}
		
	  })
	  
		  
		  
		 /*  $("#pressureright").on('click',function(){
			   pressureData();
			 $("#pressureleft").show();
			page = page+1;
			//当前page乘以size
			presentPage = page*limit
			if(presentPage>=count){
				$('#pressureright').removeAttr('click');//去掉标签中的onclick事件
			 	notice.error({
					title : '消息通知',
					message : '没有更多数据了!'
				});
			}
				
				
		  })*/
		 
		 
		  var page = 1;
		  var limit = 30;
		  function pressureData(){
			  $.ajax({
					type:'get', 
					url:'/YHUserBloodPressure/selectBloodPressure',
					data:{userCode:userCode,testHj:testHj,page:page,limit:limit}, 
					success : function(data){
						 console.info(data.count,"数据-------------------------------------------");
						 if(data.count==0){
							
                             $("#pressure2").hide();
                             $(".pressureNoData").show();
                        }
						 
						 if(data.count>0){
								
                             $("#pressure2").show();
                             $(".pressureNoData").hide();
                        }
						 
						count = data.count;
						//收缩压
						 var sbpArray = data.data.map( item => {
						    return item.sbp;
						  }) 
						  //舒张压
						  var dbpArray = data.data.map( item => {
						    return item.dbp;
						  }) 
						  
						  console.log(sbpArray,"!!!")
						  //时间
					 	var entryTimeArray = data.data.map( item => {
							  return dateFormat(item.entryTime, "MM-dd");
						  })
						  initPressure(dbpArray,sbpArray,entryTimeArray); 	
						
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
			
		function initPressure(dbpArray,sbpArray,entryTimeArray){
			    // 指定图表的配置项和数据
				/*	option = {
						color: ["rgba(60, 144, 247, 1)","rgba(85, 191, 192, 1)"],
							
						  tooltip:{
						        trigger :'axis',
						        formatter: '舒张压 （mmHg） {c0}<br />收缩压 （mmHg）{c1}',
						     	
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
					    	name: 'mmHg',
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
					        data: dbpArray,
					        type: 'line',
					        smooth: true
					      },
					      {
					            
				            type: 'line',
				            smooth: true,
				            data: sbpArray
					        }
					   ]
					};*/
			option = {
					color: ["rgba(60, 144, 247, 1)","rgba(85, 191, 192, 1)"],  
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
				    	name: 'mmHg',
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
				    	name: '舒张压（mmHg）',
				        data: dbpArray,
				        type: 'line',
				        smooth: true
				      },
				      {
				    	  name: '收缩压（mmHg）',   
			            type: 'line',
			            smooth: true,
			            data: sbpArray
				        }
				   ]
				};

			    // 使用刚指定的配置项和数据显示图表。
			    myChart.setOption(option);	
			}
	
	});
})