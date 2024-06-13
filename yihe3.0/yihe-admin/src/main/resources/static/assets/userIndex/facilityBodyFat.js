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
		 var facilitybodyfatTable =  table.render({
			 id:'facilitybodyfatTable',
		    elem: '#facilitybodyfatTable'
		    ,url: '/YHUserBodyFat/page?userCode='+userCode 
		    ,method : 'get'
		    ,where : {} // 你额外要携带数据，以键值对的方式存入
		    ,page: true // 开启分页
		    ,limit : 20// 每页显示的条数
			,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
		    ,cols: [[ // 表头
		       {type:'numbers', title:'序号', align: 'center',width:100},
		       {field: 'bodyscore',title: '身体得分',
		    	   templet:function(d){
		    		   return d.bodyscore + '分';
		    	   }
		       },
		       {field: 'bodyage',title: '身体年龄',
		    	   templet:function(d){
		    		   return d.bodyage + '岁';
		    	   }
		       },
		       {field: 'weightscale',title: '体重',
		    	   templet:function(d){
		    		   return d.weightscale + 'kg'+'('+d.weightstate+')';
		    	   }
		       },
		      {field: 'idealweight',title: 'BMI',
		    	   templet:function(d){
		    		   return d.idealweight;
		    	   }
		       },
		      {field: 'fatRate',title: '脂肪率',
		    	   templet:function(d){
		    		   return d.fatRate + '%'+'('+d.fatstate+')';
		    	   }
		       },
		       {field: 'muscle',title: '肌肉率',
		    	   templet:function(d){
		    		   return d.muscle + '%'+'('+d.musclestate+')';
		    	   }
		       },
		       {field: 'bones',title: '骨骼量',
		    	   templet:function(d){
		    		   return d.bones + 'kg'+'('+d.bonesstate+')';
		    	   }
		       },
		       {field: 'bones',title: '内脏脂肪指数',
		    	   templet:function(d){
		    		   return d.visceralLevel +'('+d.levelstate+')';
		    	   }
		       },
		       {field: 'water',title: '水分',
		    	   templet:function(d){
		    		   return d.water + '%'+'('+d.waterstate+')';
		    	   }
		       },
		       {field: 'storagemusclelevel',title: '储肌能力',
		    	   templet:function(d){
		    		   return d.storagemusclelevel +'('+d.storagestate+')';
		    	   }
		       },
		       {field: 'basalMet',title: '基础代谢率',
		    	   templet:function(d){
		    		   return d.basalMet +'('+d.basalstate+')';
		    	   }
		       },
		       {field: 'protein',title: '蛋白质',
		    	   templet:function(d){
		    		   return d.protein +'('+d.proteinstate+')';
		    	   }
		       }
		      ,{field:'entryTime',align: 'center', title: '检测时间',width: 300}
		      
		    ]],
		    done: function(res, curr, count){
		        if(count > 20) {
		        	$("#bodyfatVo .layui-table-page").show();
		        }else {
		        	$("#bodyfatVo .layui-table-page").hide();
		        }
		      }
		  });
		 
			// 基于准备好的dom，初始化echarts实例
			$('#bodyfat').css('width',$(window).width()-50);
			var myChart = echarts.init(document.getElementById('bodyfat'));
			var count = 0;
		 
		/* var tip_index;
		 $("#bodyfatlist").hover(function(){
		     tip_index =  layer.tips('列表显示', this,{
		         tips: [3, 'rgba(0, 0, 0, 1)'],
		         time:0
		     });
		 },function(){
		     layer.close(tip_index);
		 });
		 
		 var tip_index;
		 $("#bodyfatchart").hover(function(){
		     tip_index =  layer.tips('图表显示', this,{
		         tips: [3, 'rgba(0, 0, 0, 1)'],
		         time:0
		     });
		 },function(){
		     layer.close(tip_index);
		 });*/
		 
		 $("#bodyfatlist").on('click',function(){
			 $(".bodyfatNoData").hide();
			 $("#bodyfat2").hide();
			 $("#facilitybodyfatTable2").show();
			 var chart=document.querySelector("#bodyfatchart");
			 chart.style.color="rgba(153, 153, 153, 1)";

			 var list=document.querySelector("#bodyfatlist");
			 list.style.color="rgba(24, 144, 255, 1)";
			 
			/* var chart=document.querySelector("#heartchart");
			 chart.style.color="rgba(153, 153, 153, 1)";
			 
			 var list=document.querySelector("#heartlist");
			 list.style.color="rgba(24, 144, 255, 1)";*/
			
		})  
		 
		 $("#bodyfatchart").on('click',function(){
		    	bodyFatData();
		    	$("#bodyfat2").show();
				 $("#facilitybodyfatTable2").hide();
				 var chart=document.querySelector("#bodyfatchart");
				 chart.style.color="rgba(24, 144, 255, 1)";
				 
				 var list=document.querySelector("#bodyfatlist");
				 list.style.color="rgba(153, 153, 153, 1)";
			 
		  })
		 
		
		  
		 $("#bodyfatleft").on('click',function(){
			 $("#bodyfatright").show();
			  page = page-1;
			//当前page乘以size
			presentPage = page*limit
			bodyFatData();
			  if(page==1){
				 	//$('#bodyfatleft').removeAttr('click');//去掉标签中的onclick事件
				 	$("#bodyfatleft").hide();
				 	notice.error({
						title : '消息通知',
						message : '没有更多数据了!'
					});
			 		 
				 	
				}
		 
	  })
	  
	   $("#bodyfatright").on('click',function(){
		 $("#bodyfatleft").show();
		page = page+1;
		//当前page乘以size
		presentPage = page*limit
		if(presentPage>=count){
		 	notice.error({
				title : '消息通知',
				message : '没有更多数据了!'
			});
		 	$("#bodyfatright").hide();
		}
		bodyFatData();
	  })
		 
		  var page = 1;
		  var limit = 30;
		  function bodyFatData(){
			  $.ajax({
					type:'get', 
					url:'/YHUserBodyFat/selectBodyFat',
					data:{userCode:userCode,page:page,limit:limit}, 
					success : function(data){
						 if(data.count==0){
                             $("#bodyfat2").hide();
                             $(".bodyfatNoData").show();
                        }
						count = data.count;
						//脂肪率
						 var fatRateArray = data.data.map( item => {
						    return item.fatRate;
						  }) 
						  //肌肉
						   var muscleArray = data.data.map( item => {
						    return item.muscle;
						  }) 
						  //骨骼
						   var bonesArray = data.data.map( item => {
						    return item.bones;
						  }) 
						  console.log(muscleArray,"!!!")
					 	var entryTimeArray = data.data.map( item => {
							  return dateFormat(item.entryTime, "MM-dd");
						  })
						  initbodyfat(fatRateArray,muscleArray,bonesArray,entryTimeArray); 	
						
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
			
			function initbodyfat(fatRateArray,muscleArray,bonesArray,entryTimeArray){
			    // 指定图表的配置项和数据
				/*	option = {
						color: ["rgba(60, 144, 247, 1)","rgba(85, 191, 192, 1)","rgba(94, 190, 103, 1)"],
							
						  tooltip:{
						        trigger :'axis',
						        formatter: '脂肪率 （%） {c0}<br />肌肉率 （%）{c1}<br />骨骼量 （kg）{c2}',
						        
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
					    	//name: '%',
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
					        data: fatRateArray,
					        type: 'line',
					        smooth: true
					      },
						      {
				            type: 'line',
				            smooth: true,
				            data: muscleArray
					        },
					        {
				            type: 'line',
				            smooth: true,
				            data: bonesArray
					        }
				       ]
					};*/
				option = {
						color: ["rgba(60, 144, 247, 1)","rgba(85, 191, 192, 1)","rgba(94, 190, 103, 1)"],
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
					    	//name: '%',
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
					    		name:'脂肪率 （%）',
						        data: fatRateArray,
						        type: 'line',
						        smooth: true
						      },
							      {
						    	  name:'肌肉率 （%）',
					            type: 'line',
					            smooth: true,
					            data: muscleArray
						        },
						        {
						        name:'骨骼量 （Kg）',
					            type: 'line',
					            smooth: true,
					            data: bonesArray
						        }
					       ]
					};

			    // 使用刚指定的配置项和数据显示图表。
			    myChart.setOption(option);	
			}
	
	});
})