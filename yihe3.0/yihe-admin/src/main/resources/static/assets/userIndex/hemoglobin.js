$(function(){

    layui.use([ 'layer', 'form', 'laydate', 'notice','table' ],function() {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var laydate = layui.laydate;
        var notice = layui.notice;
        var table = layui.table;
        var userCode = $("input[name=userCode]").val();
        var page = 1;
        var limit = 30;

        // 列表
        var hemoTable =  table.render({
            id:'hemoTable',
            elem: '#hemoTable'
            ,url: '/YHUserHemoglobin/page?userCode='+userCode
            ,method : 'get'
            ,where : {} // 你额外要携带数据，以键值对的方式存入
            ,page: true // 开启分页
            ,limit : 20// 每页显示的条数
            ,limits : [ 5, 10, 20, 50, 100 ] // 每页条数的选择项
            ,cols: [[ // 表头
                {type:'numbers', title:'序号', align: 'center',width:100},
                {field: 'heHb',title: '血红蛋白浓度<i class="layui-icon alone-tips" lay-tips="参考范围：男性：13-18 g/dL , 女性：12-16 g/dL"></i>',
                    templet:function(d){
                        return d.heHb + ' g/dL';
                    }
                }
                ,{field:'heData',align: 'center', title: '检测时间',width: 300}

            ]],
            done: function(res, curr, count){
                if(count > 20) {
                    $("#bimVo .layui-table-page").show();
                }else {
                    $("#bimVo .layui-table-page").hide();
                }
            },
        });

        // 基于准备好的dom，初始化echarts实例
        $('#hemo1').css('width',$(window).width()-50);
        $('#hemo1').css('height',$(window).height()-80);
        var myChart = echarts.init(document.getElementById('hemo1'));
        var count = 0;

        //列表显示
        $("#hemolist").on('click',function(){
            $(".hemoNoData").hide();
            $("#hemo").hide();
            $("#hemoTable2").show();
            var list=document.querySelector("#hemolist");
            list.style.color="rgba(24, 144, 255, 1)";   //变亮`

            var chart=document.querySelector("#hemochart");
            chart.style.color="rgba(153, 153, 153, 1)";  //不变亮
        })

        //图表显示
        $("#hemochart").on('click',function(){
            hemoData();
            $("#hemo").show();
            $("#hemoTable2").hide();
            var list=document.querySelector("#hemolist");
            list.style.color="rgba(153, 153, 153, 1)";

            var chart=document.querySelector("#hemochart");
            chart.style.color="rgba(24, 144, 255, 1)";

        })

        $("#hemoleft").on('click',function(){
            $("#hemoright").show();
            page = page-1;
            hemoData();
            if(page==1){
                $("#hemoleft").hide();
                notice.error({
                    title : '消息通知',
                    message : '没有更多数据了!'
                });
            }
        })

        $("#hemoright").on('click',function(){
            $("#hemoleft").show();
            page = page+1;
            //当前page乘以size
            presentPage = page*limit
            if(presentPage>=count){
                notice.error({
                    title : '消息通知',
                    message : '没有更多数据了!'
                });
                $("#hemoright").hide();
            }
            //$('#bloodright').removeAttr('click');//去掉标签中的onclick事件
            hemoData();
        })

        $(".layui-icon")

        //日期格式化
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

        //图表
        function hemoData(){
            $.ajax({
                type:'get',
                url:'/YHUserHemoglobin/selectHemoglobin',
                data:{userCode:userCode,page:page,limit:limit},
                success : function(data){
                    count = data.count;
                    if(count==0){
                        $("#hemo").hide();
                        $(".hemoNoData").show();
                    }
                    var uheHbArray = data.data.map( item => {
                        return item.heHb;
                    })

                    var heDataArray = data.data.map( item => {
                        return dateFormat(item.heData, "MM-dd");
                    })
                    inithemo(uheHbArray,heDataArray);
                }
            })
        }

        <!-- echarts显示  -->
        function inithemo(uheHbArray,heDataArray){
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
                xAxis: {
                    type: 'category',
                    data: heDataArray,
                    nameTextStyle:{
                        fontSize:15,
                        color:'red'
                    }
                },
                yAxis: {
                    name: 'g/dL',
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
                        name:'血红蛋白浓度(g/dL)',
                        data: uheHbArray,
                        type: 'line',
                        smooth: true
                    },
                ]
            };
            // 使用刚指定的配置项和数据显示图表
            myChart.setOption(option);
        }

    });
})

