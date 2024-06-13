/**记录点击地图位置的区域*/
let tiqu_name='';

/**
 * @author hotsuitor@qq.com
 * @createTime 2018/2/23
 * @version v3.0  2018/4/3
 * @version v3.1  2018/4/10
 * @version v3.3  2018/4/12
 */
//地图容器
var chart = echarts.init(document.getElementById('main'))
//启动加载动画，当数据还没准备好时可以显示
chart.showLoading()

loadMap(searchtime)

/**
 * 封装绘制地图函数，month 改变 map重新渲染
 * searchtime 代表当前日期 格式：年-月
 */
function loadMap(searchtime) {
    let mapName = '中国'
    //绘制全国地图  chinaJson中国地图的json文件
    $.getJSON(chinaJson, function (data) {
        //关闭加载动画
        chart.hideLoading()
        // 组装临时数据,存储每个省份对应的id以及名称
        let mapJsonData = []
        for (var i = 0; i < data.features.length; i++) {
            mapJsonData.push({
                id: data.features[i].id,
                name: data.features[i].properties.name,
            })
        }
        //此处是注册全国地图
        echarts.registerMap(mapName, data)

        /**获取省份数据*/
        Promise.all([ajaxRequest(getProvNumberUrl, searchtime)]).then(
            (result) => {
                console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>', JSON.parse(result))
                renderPrimaryMap(result)
            },
            (error) => {
                renderPrimaryMap(mapJsonData, true)
            }
        )

        /**
         * @description: 渲染二级地图，城市
         * @param {Array}  result=后台返回的地区关联数据
         * @param {Boolean} flag=不用后台数据渲染
         * @return:
         */
        function renderPrimaryMap(result, flag) {
            //记录每个省份的name,value 目的是让不同的省份根据value的值显示不同程度的颜色
            let tmp = []
            //查询当前登录用户的机构及子机构在每个省的分布数量
            var url = "/OrganizationIndexController/showprovincial";
            $.post(url, function (e) {
                if (flag) {
                    result.forEach((item) => {
                        tmp.push({
                            id: item.id,
                            name: item.name,
                            value: Math.floor(Math.random() * 100 + parseInt(item.id)),
                        })
                    })
                } else {
                    if (e != null || e != "") {
                        curMonthResult = stringToJson(result[0])
                        if (curMonthResult.errcode == 1) {
                            /**通过id关联地图上对应位置的数据 */
                            mapJsonData.forEach(function (val) {
                                curMonthResult.msg.forEach(function (val2, index) {
                                    if (val.id === val2.provinceid) {
                                        for (var i = 0; i < e.length; i++) {
                                            if (e[i].province == val.name) {
                                                tmp.push({
                                                    id: val.id,
                                                    name: val.name,
                                                    value: e[i].num,
                                                })
                                            } else {
                                                tmp.push({
                                                    id: val.id,
                                                    name: val.name,
                                                    value: val2.num,
                                                })
                                            }
                                        }
                                    }
                                })
                            })
                        }
                    } else {
                        curMonthResult = stringToJson(result[0])
                        if (curMonthResult.errcode == 1) {
                            /**通过id关联地图上对应位置的数据 */
                            mapJsonData.forEach(function (val) {
                                curMonthResult.msg.forEach(function (val2, index) {
                                    if (val.id === val2.provinceid) {
                                        tmp.push({
                                            id: val.id,
                                            name: val.name,
                                            value: val2.num,
                                        })
                                    }
                                })
                            })
                        }
                    }
                }

                //获取最大值，并排序
                let maxData = getMaxDataAndSort(tmp)
                //绘制地图，拿到数据后再渲染一次
                renderMap(mapName, data, tmp, maxData.maxData)
                getRegionPreMonthRatio(maxData.maxDataId, searchtime)
            })

        }
    })
}

//地图单击事件
chart.on('click', function (params) {
    if (params.value != 0) {
        if (params.data.cityid != undefined) {
            var date = params.data.name.substr(0, params.data.name.length - 1);
            tiqu_name = date;
            selectByImprovability(date);
            Userdatastatistics(date);
            selectCurrentSituation(date);
            showFoodExercise(date);
        } else {
            tiqu_name = params.data.name;
            Userdatastatistics(params.data.name);
            selectByImprovability(params.data.name);
            selectCurrentSituation(params.data.name);
            showFoodExercise(params.data.name);
        }
        if (!(params.data.id || params.data.cityid)) {
            // 有省id，市id才有下一级
            console.error('该地图没有下一级地区了')
            return
        }
        //隐藏右键返回菜单
        $('#contextMenu').hide()
        let mapJsonData = [] // 渲染地图name的数组
        if (params.name in provinces) {
            //二级直辖市数据渲染
            if (special.indexOf(params.name) >= 0) {
                let postData2 = {
                    parentid: 'provinceid',
                    value: params.data.id,
                }
                Promise.all([ajaxRequest(getCityNumberUrl, searchtime, postData2)]).then(
                    (result) => {
                        try {
                            let curMonthResult = stringToJson(result[0])
                            if (curMonthResult.errcode == 1) {
                                getAreaNumber(params.name, curMonthResult.msg[0].cityid, searchtime)
                            }
                        } catch (error) {
                            getSecondMap(params)
                        }
                    },
                    (error) => {
                        console.error('请求市级数据失败', error)
                        getSecondMap(params)
                    }
                )
            } else {
                //如果点击的是34个省、市、自治区，绘制选中地区的二级地图
                getSecondMap(params)
            }
        } else {
            //显示县级地图
            getThridMap(params)
        }

        /**
         * @description: 请求获取二级地图json文件
         * @param {Object} params=地图参数
         * @return:
         */
        function getSecondMap(params) {
            $.getJSON(provinceJson + provinces[params.name] + '.json', function (data) {
                echarts.registerMap(params.name, data)
                for (var i = 0; i < data.features.length; i++) {
                    // 读取地图的 name 用来组成 echart 需要的形式
                    mapJsonData.push({
                        name: data.features[i].properties.name,
                        value: Math.floor(Math.random() * 10000),
                    })
                }
                renderMap(params.name, mapJsonData)
                if (params.data.id !== 'undifiend') {
                    getCityNumber(params.name, params.data.id, searchtime, data)
                }
            })
        }

        /**
         * @description: 请求获取三级地图json文件
         * @param {Object} params=地图参数
         * @return:
         */
        function getThridMap(params) {
            $.getJSON(cityJson + cityMap[params.name] + '.json', function (data) {
                echarts.registerMap(params.name, data)
                let mapJsonData = []
                for (var i = 0; i < data.features.length; i++) {
                    mapJsonData.push({
                        name: data.features[i].properties.name,
                    })
                }
                renderMap(params.name, mapJsonData)
                if (params.data.cityid) {
                    let postData3 = {
                        parentid: 'cityid',
                        value: params.data.cityid,
                    }
                    // console.log('204', res.msg[0].cityid, res.msg[0].city)
                    //这里传递的城市名有问题“北京市”，渲染地图的名字是“北京”，所以地图名要用原来的名字渲染
                    getAreaNumber(params.name, params.data.cityid, searchtime, data)
                }
            })
        }

        /**
         * 绑定数据入栈事件
         */
        let n = 1
        if (special.indexOf(params.seriesName) == -1) {
            n = 2
        }
        // FiXED:  2级下钻会有问题， 函数顶部加入下钻层级判断
        if (mapStack.length < n) {
            //将上一级地图信息压入mapStack
            mapStack.push({
                mapName: curMap.mapName,
                mapJson: curMap.mapJson,
                colorMax: curMap.colorMax,
                sortData: curMap.sortData,
                titledata: curMap.titledata,
            })
            console.log('数据入栈', mapStack)
        }
    } else {
        //hint()
    }
})

/**
 * 右键直接返回上一级
 */
chart.on('contextmenu', (params) => {
    goBack()
})
/**
 * 左上角返回按钮
 */
$('#goBack').on('click', function (data) {
    goBack()
})

/**
 * 封装返回上一级事件
 */
function goBack() {

    //获取上一级地图信息
    let map = mapStack.pop()
    if (!map) {
        console.log('没有入栈数据了')
        return
    }
    echarts.registerMap(map.mapName, map.mapJson)
    if (map.mapName == "中国") {
        tiqu_name = '';
        selectByImprovability();
        Userdatastatistics();
        selectCurrentSituation();
        showFoodExercise();
    } else {
        tiqu_name = map.mapName;
        selectByImprovability(map.mapName);
        Userdatastatistics(map.mapName);
        selectCurrentSituation(map.mapName);
        showFoodExercise(map.mapName);
    }
    sortData = map.sortData
    titledata = map.titledata
    renderMap(map.mapName, map.mapJson, map.sortData, map.colorMax)
}

/**
 *
 * @param {地图标题} mapTitle
 * @param {客户数} customerNum
 * @param {地图json数据} mapJson
 * @param {最大颜色值} colorMax
 */
function renderMap(mapTitle, mapJson, customerNum, colorMax = 1500) {
    //地图配置参数，参数按顺序渲染
    option = {
        backgroundColor: '', //地图画布背景颜色  "#F7EED6"米黄色  "#efefef"灰色
        tooltip: {
            //提示框信息
            trigger: 'item',
            formatter: '{b}\n{c}个机构',
        },
        //工具栏设置 数据视图、保存为图片的工具栏
        toolbox: {
            show: true,
            //工具栏朝向 竖着
            orient: 'vertical',
            left: 'right',
            top: 'center',
            right: 20,
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {},
            },
            iconStyle: {
                normal: {
                    color: '#fff',
                },
            },
        },
        //左下角的颜色条
        visualMap: {
            show: false,
            min: 0,
            max: colorMax,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'], // 文本，默认为数值文本
            calculable: true,
            color: ['#0657BB', '#064AA4', '#062F74'], //色阶范围
            dimension: 0,
        },

        xAxis: [
            {
                position: 'top',
                type: 'value',
                boundaryGap: false,
                splitLine: {
                    show: false,
                },
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },
                axisLabel: {
                    color: '#ff461f',
                },
            },
        ],
        yAxis: [
            {
                type: 'category',

                triggerEvent: true,
                axisTick: {
                    alignWithLabel: true,
                },
                axisLine: {
                    show: false,
                    lineStyle: {
                        show: true,
                        color: '#2ec7c9',
                    },
                },
            },
        ],
        series: [
            {
                name: mapTitle, //上面的下钻用到seriesName绑定下一级，换name绑定
                type: 'map',
                map: mapTitle,
                roam: false,
                height: '100%',
                zoom: 0.85,
                z: 1,
                label: {
                    //地图上的文本标签
                    normal: {
                        show: true,
                        position: 'inside', //文本标签显示的位置
                        textStyle: {
                            color: '#fff', //文本颜色
                            fontSize: 14,
                        },
                        formatter: '{b}\n{c}', //文本上显示的值  data:[{name: "地名", value: 数据}],  {b}表示label信息,{c}代表value
                    },
                    emphasis: {
                        show: true,
                        position: 'inside',
                        textStyle: {
                            color: '#fff',
                            fontSize: 13,
                        },
                    },
                },
                itemStyle: {
                    normal: {
                        areaColor: '#5ab1ef', //地图块颜色#DCE2F1  浅蓝#2B91B7
                        borderColor: '#EBEBE4', //#EBEBE4灰色
                    },
                    emphasis: {
                        areaColor: 'rgb(254,153,78)', //s鼠标放上去，地图块高亮显示的颜色
                    },
                },
                data: customerNum,
            }


        ],
        // 初始动画的时长，支持回调函数，可以通过每个数据返回不同的 delay 时间实现更戏剧的初始动画效果：
        animationDuration: 1000,
        animationEasing: 'cubicOut',
        // 数据更新动画的时长。
        animationDurationUpdate: 1000,
    }

    //渲染地图
    chart.setOption(option)
    window.addEventListener("resize",
        function () {
            chart.resize()
        })
    //保存当前状态数据，用于入栈出栈
    curMap = {
        mapName: mapTitle,
        mapJson: mapJson,
        colorMax: colorMax,
        sortData: sortData,
        titledata: titledata,
    }
}

/**
 * 获取城市数据
 * @param {省份} name
 * @param {省份id} id
 * @param {月份} searchtime
 * @param {地图json数据} data
 */
function getCityNumber(name, id, searchtime, data) {
    if (!id) {
        return
    }
    let postData2 = {
        parentid: 'provinceid',
        value: id,
    }
    Promise.all([ajaxRequest(getCityNumberUrl, searchtime, postData2)]).then(
        (result) => {
            Promise.all([ajaxRequest(getCityNumberUrl + id + '.json')])
                .then((resp) => {
                    renderSecondMap(resp)
                })
                .catch(() => {
                    // 请求后台数据或者mock数据异常，用地图自身数据渲染
                    renderSecondMap(name, data, true)
                })
        },
        (error) => {
            //console.error('请求城市数据失败', error)
            renderSecondMap(name, data, true)
        }
    )

    /**
     * @description: 渲染二级地图，城市
     * @param {JSONString} resp=请求后台返回的地区关联数据
     * @param {Boolean} flag=标识位，请求mock数据失败，用地图数据渲染的 true=地图数据渲染
     * @return:
     */
    function renderSecondMap(name, resp, flag = false) {
        let tmp = []
        var url = "/OrganizationIndexController/showCity";
        var data2 = {"name": name};
        $.post(url, data2, function (e) {
            if (e != null && e != "" && e != 0) {
                if (flag) {
                    resp.features.forEach((item) => {
                        for (var i = 0; i < e.length; i++) {
                            if (e[i].city + "市" == item.properties.name || e[i].county == item.properties.name || e[i].county == item.properties.name || e[i].city == item.properties.name) {
                                tmp.push({
                                    //需要加上cityid传递渲染，下一级地图渲染需要用到，点击的时候有判断，没有下级id直接return
                                    cityid: item.id,
                                    name: item.properties.name,
                                    value: e[i].num,
                                })
                            } else {
                                tmp.push({
                                    //需要加上cityid传递渲染，下一级地图渲染需要用到，点击的时候有判断，没有下级id直接return
                                    cityid: item.id,
                                    name: item.properties.name,
                                    value: 0,
                                })
                            }
                        }

                    })
                } else {
                    curMonthResult = stringToJson(resp[0])
                    if (curMonthResult.errcode == 1) {
                        citySaleData = []
                        for (let i = 0; i < curMonthResult.msg.length; i++) {
                            tmp.push({
                                cityid: curMonthResult.msg[i].cityid, //需要加上cityid传递渲染，下一级地图渲染需要用到
                                name: curMonthResult.msg[i].city,
                                value: curMonthResult.msg[i].num,
                            })
                        }
                    }
                }
                let maxData = getMaxDataAndSort(tmp)
                renderMap(name, data, tmp, maxData.maxData)
                getRegionPreMonthRatio(maxData.maxDataId, searchtime)
            } else {
                if (flag) {
                    resp.features.forEach((item) => {
                        tmp.push({
                            //需要加上cityid传递渲染，下一级地图渲染需要用到，点击的时候有判断，没有下级id直接return
                            cityid: item.id,
                            name: item.properties.name,
                            value: 0,
                        })
                    })
                } else {
                    curMonthResult = stringToJson(resp[0])
                    if (curMonthResult.errcode == 1) {
                        citySaleData = []
                        for (let i = 0; i < curMonthResult.msg.length; i++) {
                            tmp.push({
                                cityid: curMonthResult.msg[i].cityid, //需要加上cityid传递渲染，下一级地图渲染需要用到
                                name: curMonthResult.msg[i].city,
                                value: curMonthResult.msg[i].num,
                            })
                        }
                    }
                }
                let maxData = getMaxDataAndSort(tmp)
                renderMap(name, data, tmp, maxData.maxData)
                getRegionPreMonthRatio(maxData.maxDataId, searchtime)
            }
        })

    }
}

/**
 * 获取县区数据
 * @param {城市名} cityName
 * @param {城市id} cityId
 * @param {月份}  searchtime
 * @param {地图json数据} data
 */
function getAreaNumber(cityName, cityId, searchtime, data) {
    let postData3 = {
        parentid: 'cityid',
        value: cityId,
    }
    Promise.all([ajaxRequest(getAreaNumberUrl, searchtime, postData3)]).then(
        (result) => {
            Promise.all([ajaxRequest(getAreaNumberUrl + cityId + '.json')])
                .then((resp) => {
                    renderThirdMap(cityName, resp)
                })
                .catch(() => {
                    renderThirdMap(cityName, data, true)
                })
        },
        (error) => {
            console.error('获取县区数据失败', error)
            renderThirdMap(cityName, data, true)
        }
    )

    /**
     * @description: 渲染三级级地图，县区
     * @param {JSONString} resp=请求后台返回的地区关联数据
     * @param {Boolean} flag=标识位，请求mock数据失败，用地图数据渲染的 true=地图数据渲染
     * @return:
     */
    function renderThirdMap(cityName, resp, flag = false) {
        let tmp = []
        var date = cityName.substr(0, cityName.length - 1);
        var url = "/OrganizationIndexController/showCounty";
        var data2 = {"name": date};
        $.post(url, data2, function (e) {
            if (e != null && e != "" && e != 0) {
                if (flag) {
                    resp.features.forEach((item) => {
                        for (var i = 0; i < e.length; i++) {
                            if (item.properties.name == e[i].county) {
                                tmp.push({
                                    areaid: item.id,
                                    name: item.properties.name,
                                    value: e[i].num,
                                })
                            } else {
                                tmp.push({
                                    areaid: item.id,
                                    name: item.properties.name,
                                    value: 0,
                                })
                            }
                        }

                    })
                } else {
                    curMonthResult = stringToJson(resp[0])
                    if (curMonthResult.errcode == 1) {
                        areaSaleData = []
                        for (let i = 0; i < curMonthResult.msg.length; i++) {
                            // 返回是一个数组，array({id: 1954, areaid: "440301", area: "深圳市", num: 0})
                            tmp.push({
                                areaid: curMonthResult.msg[i].areaid,
                                name: curMonthResult.msg[i].area,
                                value: curMonthResult.msg[i].num,
                            })
                        }
                    }
                }
                let maxData = getMaxDataAndSort(tmp)
                renderMap(cityName, data, tmp, maxData.maxData)
                getRegionPreMonthRatio(maxData.maxDataId, searchtime)
            } else {
                if (flag) {
                    resp.features.forEach((item) => {
                        tmp.push({
                            areaid: item.id,
                            name: item.properties.name,
                            value: 0,
                        })
                    })
                } else {
                    curMonthResult = stringToJson(resp[0])
                    if (curMonthResult.errcode == 1) {
                        areaSaleData = []
                        for (let i = 0; i < curMonthResult.msg.length; i++) {
                            // 返回是一个数组，array({id: 1954, areaid: "440301", area: "深圳市", num: 0})
                            tmp.push({
                                areaid: curMonthResult.msg[i].areaid,
                                name: curMonthResult.msg[i].area,
                                value: curMonthResult.msg[i].num,
                            })
                        }
                    }
                }
                let maxData = getMaxDataAndSort(tmp)
                renderMap(cityName, data, tmp, maxData.maxData)
                getRegionPreMonthRatio(maxData.maxDataId, searchtime)
            }
        })

    }
}

let areaList = []
let vm = new Vue({
    el: '#trend-line',
    data: {
        areaList: areaList,
        modalTitle: [],
        cpModalTitle: [],
        searchtime: searchtime,
        time: 0,
        otime: 0,
    },
    methods: {
        setAreaList(data) {
            // let $_self = this
            // $_self._data.areaList = data //这里有个坑，_data
            this.areaList = data // vue是这样写的，内部有代理
        },
    },
    watch: {
        modalTitle(val, oldVal) {
            let $_self = this
            if (val.length > oldVal) {
                this.time++
                this.cpModalTitle.push(val)
            } else {
                // $_self._data.time--;
            }
            //TODO 取消不请求数据
            if (this.time > this.otime) {
                getRegionPreMonthRatio(val, this.searchtime)
            }
        },
    },
})

/**
 *
 * @param {未排序数据} originData
 * @return {倒序排序的数据} maxData
 */
function getMaxDataAndSort(originData) {
    if (originData == 'undefined') {
        return
    }
    titledata = []
    sortData = []
    sortData = originData.sort(numDescSort)
    let maxData = sortData.slice(-1)[0]['value']
    let maxDataId = sortData.slice(-1)[0]['id']
    if (!maxDataId) {
        maxDataId = sortData.slice(-1)[0]['cityid'] ? sortData.slice(-1)[0]['cityid'] : sortData.slice(-1)[0]['areaid']
    }
    for (let i = 0; i < sortData.length; i++) {
        titledata.push(sortData[i].name)
    }
    areaList = [...sortData].reverse()
    vm.setAreaList(areaList)
    return {
        maxDataId,
        maxData,
    }
}

/**
 * 按月份筛选数据
 */
$('#select-date').change(function () {
    customerNum = []
    let selMonth = $(this).val()
    loadMap(selMonth)
})

/**
 * @param 折线图 异步获取地区月份数据
 */
function getRegionPreMonthRatio(region, year) {
    let tmp = []
    let tmp2 = []
    let tmpMonth = []
    let postRegion = {
        region: region,
        year: year,
    }
    Promise.all([ajaxRequest(getRegionPreMonthCusUrl, '', postRegion)])
        .then(
            (res) => {
                let [allMonthResult] = res
                if (allMonthResult.errcode === 1) {
                    for (let i = 0; i < allMonthResult.data.region.length; i++) {
                        tmp.push(Math.round(allMonthResult.data.region[i].customer_num))
                        tmp2.push(Math.round(allMonthResult.data.region[i].averge_customer_num))
                        tmpMonth.push(allMonthResult.data.region[i].time)
                    }
                }
                let option = {
                    tooltip: {
                        trigger: 'axis',
                    },
                    dataset: {
                        source: [tmpMonth, tmp, tmp2],
                    },
                }
                modalChart.setOption(option)
                let tmpSession = {
                    yearMonth: tmpMonth,
                    region: tmp,
                    level_region: tmp2,
                }
                sessionStorage.setItem(region, JSON.stringify(tmpSession))
                return [allMonthResult.data.region_name, allMonthResult.data.level_name, option]
            },
            (error) => {
            }
        )
        .then((res) => {
            if (res) {
                loadLineTrend(res[0], res[1] + '平均')
                modalChart.setOption(res[3])
            } else {
                vm._data.modalTitle = '没有数据'
                //modalChart.clear()
                console.log('返回数据有问题，无法完成折线图渲染')
            }
        })
}

let modalChart = echarts.init(document.getElementById('line-trend'))

/**
 * 渲染折线图
 * @param {*} region
 * @param {*} upRegion
 */
function loadLineTrend(region, upRegion) {
    // 指定图表的配置项和数据
    let option = {
        title: {
            text: region + '地区月份数据',
            subtext: '',
        },
        tooltip: {
            trigger: 'axis',
        },
        dataset: {},
        legend: {
            data: [region, upRegion],
        },
        // toolbox: {
        //   show: true,
        //   right: "10%",
        //   feature: {
        //     dataZoom: {},
        //     dataView: { readOnly: false },
        //     magicType: { type: ["line", "bar"] },
        //     restore: {},
        //     saveAsImage: {}
        //   }
        // },
        xAxis: {
            type: 'category',
            boundaryGap: ['10%', '10%'], //X轴两边留白策略
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value}',
            },
        },
        series: [
            {
                name: region,
                type: 'line',
                smooth: true,
                label: {
                    normal: {
                        show: true,
                    },
                },
                seriesLayoutBy: 'row',
                markPoint: {
                    data: [
                        // { type: "max", name: "最大值" },
                        // { type: "min", name: "最小值" }
                    ],
                },
                markLine: {
                    data: [{type: 'average', name: '平均值'}],
                },
            },
            {
                name: upRegion,
                type: 'line',
                smooth: true,
                label: {
                    normal: {
                        show: true,
                    },
                },
                seriesLayoutBy: 'row',
                markPoint: {
                    data: [{name: '最低', value: 'mix'}],
                },
                markLine: {
                    data: [{type: 'average', name: '平均值'}],
                },
            },
        ],
    }
    // 使用刚指定的配置项和数据显示图表。
    modalChart.setOption(option)
}
