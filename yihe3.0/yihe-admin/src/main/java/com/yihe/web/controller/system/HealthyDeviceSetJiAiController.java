package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.*;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.utils.GetDistance;
import com.yihe.system.utils.LimitRequest;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import com.yihe.web.controller.netty.SocketIOService;
import com.yihe.web.controller.tool.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/SmartWatchJiAi")
public class HealthyDeviceSetJiAiController {
    /**
     * Login interface code
     */

    private static final String LOCATION_PATTERN = "JA10001";
    private static final String SOS_NUMBER = "JA10002";
    private static final String LOCATION_REQUEST = "JA10003";
    private static final String MEASUREMENT_REQUEST = "JA10004";
    private static final String HEALTHINTERVAL = "JA10005";
    private static final String POSITIONINGSETTINGS = "JA10006";
    private static final String SETMEASUREMENTCYCLE = "JA10007";
    private static final String BLOODPRESSURE = "JA10008";
    private static final String WARNING = "warning";

    @Autowired
    private JiaiInterfaceListMapper jiaiInterfaceListDasImpl;
    @Autowired
    com.yihe.system.mapper.JiaiLocateMapper JiaiLocateMapper;
    @Autowired
    com.yihe.system.mapper.JiaiAlarmMapper JiaiAlarmMapper;
    @Autowired
    com.yihe.system.mapper.YHUserHeartRateMapper YHUserHeartRateMapper;
    @Autowired
    com.yihe.system.mapper.YhUserDeviceMapper YhUserDeviceMapper;
    @Autowired
    YHUserBloodPressureMapper YHUserBloodPressureMapper;
    @Autowired
    YHUserOxygenMapper YHUserOxygenMapper;
    @Autowired
    YHUserBloodsugerMapper YHUserBloodsugerMapper;
    @Autowired
    YHUserTemperatureMapper YHUserTemperatureMapper;
    @Autowired
    JiaiEnclosureMapper JiaiEnclosureMapper;
    @Autowired
    YhHeThresholdService yhHeThresholdService;
    @Autowired
    private SocketIOService ioService;
    @Autowired
    YhUserReginfoMapper yhUserReginfoMapper;

    @Autowired
    JiaiWatchInfoMapper JiaiWatchInfoMapper;
    @Autowired
    YhAcceptserviceMapper yhAcceptserviceMapper;


    @RequestMapping(value = "/delEnclosure", method = RequestMethod.POST)
    @ResponseBody
    public Result enclosure(String imei){
        if(StringUtil.isNotEmpty(imei)){
            return Result.ok(JiaiEnclosureMapper.deljiaiEncloInfo(imei));
        }
        return Result.error();
    }

    @RequestMapping(value = "/enclosure", method = RequestMethod.POST)
    @ResponseBody
    public Result enclosure(String imei, String lnglat, Integer radius){
        if(StringUtil.isEmpty(imei)||StringUtil.isEmpty(lnglat)||null==radius||0==radius){
            return Result.error();
        }
        //截取当前系统时间
        Date currentTime = new Date();
//改变输出格式（自己想要的格式）
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//得到字符串时间
        String s8 = formatter.format(currentTime);

        String[] split = lnglat.split(",");
        JiaiEnclosure record=new JiaiEnclosure();
        record.setEstablishtime(s8);
        JiaiEnclosure enclosureNewInfo = JiaiEnclosureMapper.findEnclosureNewInfo(imei);
        if(null==enclosureNewInfo){
            //为空新增

            record.setLatitude(split[1]);
            record.setLongitude(split[0]);
            record.setRadius(radius);
            record.setTerminalid(imei);

            JiaiEnclosureMapper.insertSelective(record);
        }else{
            //不为空修改

            record.setLatitude(split[1]);
            record.setLongitude(split[0]);
            record.setRadius(radius);
            record.setTerminalid(imei);
            JiaiEnclosureMapper.updateByPrimaryKeySelective(record);
        }

        return Result.ok(record);
    }

        /**
         * 定位间隔自由设置
         */
    @RequestMapping(value = POSITIONINGSETTINGS, method = RequestMethod.POST)
    @ResponseBody
    public Result dingweiTime(String gps, String intervalTime, String terminalId, HttpServletRequest request) throws Exception {
        if (StringUtil.isNotEmpty(terminalId) && StringUtil.isNotEmpty(gps) && StringUtil.isNotEmpty(intervalTime)) {
            Map<String, Object> json = new HashMap<String, Object>();
            String describe = "自由设置定位时间";
            JiaiInterfaceList selectByPrimaryKeyByDescribe = jiaiInterfaceListDasImpl.selectByPrimaryKeyByDescribe(describe);
            String api = selectByPrimaryKeyByDescribe.getApi();
            json.put("imei", terminalId);
            json.put("mode", "8");
            json.put("intervalTime", intervalTime);
            json.put("gpsFlg", gps);
            String jsonString = JSONObject.toJSONString(json);
            System.out.println(jsonString);
            String doPut = HttpClientUtil.doPost(api, terminalId, selectByPrimaryKeyByDescribe.getKey(), selectByPrimaryKeyByDescribe.getCpid(), jsonString);
            JSONObject jsonObject = JSONObject.parseObject(doPut);
            return Result.ok(jsonObject.getString("message"));
        }
        return Result.error("参数错误");
    }

    /**
     * 设置sos号码
     *
     * @param mobileName1 联系人姓名（1）
     * @param mobile1     联系人手机号（2）
     * @param mobileName2 ...
     * @param mobile2
     * @param mobileName3
     * @param mobile3
     * @param terminalId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = SOS_NUMBER, method = RequestMethod.POST)
    @ResponseBody
    public Result sosNumber(String mobileName1, String mobile1, String mobileName2, String mobile2, String mobileName3, String mobile3, String terminalId, HttpServletRequest request) throws Exception {
        List<WatchesSosBean> sosList = new ArrayList<WatchesSosBean>();
        JiaiWatchInfo record=new JiaiWatchInfo();
        if (null != mobileName1 && null != mobile1 && !"".equals(mobileName1) && !"".equals(mobile1)) {
            WatchesSosBean ws = new WatchesSosBean();
            ws.setName(mobileName1);
            ws.setMobileNo(mobile1);
            sosList.add(ws);
            record.setMobile1(mobile1);
            record.setSosname1(mobileName1);
        }
        if (null != mobileName2 && null != mobile2 && !"".equals(mobileName2) && !"".equals(mobile2)) {
            WatchesSosBean ws = new WatchesSosBean();
            ws.setName(mobileName2);
            ws.setMobileNo(mobile2);
            sosList.add(ws);
            record.setMobile2(mobile2);
            record.setSosname2(mobileName2);
        }
        if (null != mobileName3 && null != mobile3 && !"".equals(mobileName3) && !"".equals(mobile3)) {
            WatchesSosBean ws = new WatchesSosBean();
            ws.setName(mobileName3);
            ws.setMobileNo(mobile3);
            sosList.add(ws);
            record.setMobile3(mobile3);
            record.setSosname3(mobileName3);
        }

        if (sosList.size() == 0 || null == terminalId) {
            //没填写sos手机号
            return Result.error("没有填写手机号码或无terminalId");
        }
        Map<String, Object> json = new HashMap<String, Object>();
        String describe = "设置SOS号码";
        JiaiInterfaceList selectByPrimaryKeyByDescribe = jiaiInterfaceListDasImpl.selectByPrimaryKeyByDescribe(describe);
        String api = selectByPrimaryKeyByDescribe.getApi();
        json.put("imei", terminalId);
        json.put("sosList", sosList);
        String jsonString = JSONObject.toJSONString(json);
        System.out.println(jsonString);
        String doPut = HttpClientUtil.doPut(api, terminalId, selectByPrimaryKeyByDescribe.getKey(), selectByPrimaryKeyByDescribe.getCpid(), jsonString);
        JSONObject jsonObject = JSONObject.parseObject(doPut);

        JiaiWatchInfo jiaiWatchInfo = JiaiWatchInfoMapper.selectByPrimaryKey(terminalId);

        record.setTerminalid(terminalId);
        if("0000".equals(jsonObject.getString("code"))){
            if(null==jiaiWatchInfo){
                JiaiWatchInfoMapper.insertSelective(record);
            }else{
                JiaiWatchInfoMapper.updateByPrimaryKeySelective(record);
            }
        }


        return Result.ok(jsonObject.getString("message"));

    }

    /**
     * 设置心率/血压/体温计测量周期
     */
    @RequestMapping(value = SETMEASUREMENTCYCLE, method = RequestMethod.POST)
    @ResponseBody
    public Result settingTime(String type, String interval, String terminalId, HttpServletRequest request) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        if (StringUtil.isEmpty(type) || StringUtil.isEmpty(interval) || StringUtil.isEmpty(terminalId)) {
            return Result.error("参数异常");
        }
        String describe = "设置心率/血压/体温计测量周期";
        JiaiInterfaceList selectByPrimaryKeyByDescribe = jiaiInterfaceListDasImpl.selectByPrimaryKeyByDescribe(describe);
        String api = selectByPrimaryKeyByDescribe.getApi();
        json.put("imei", terminalId);
        json.put("type", type);
        json.put("interval", interval);
        String jsonString = JSONObject.toJSONString(json);
        System.out.println(jsonString);
        String doPut = HttpClientUtil.doPost(api, terminalId, selectByPrimaryKeyByDescribe.getKey(), selectByPrimaryKeyByDescribe.getCpid(), jsonString);
        JSONObject jsonObject = JSONObject.parseObject(doPut);
        JiaiWatchInfo jiaiWatchInfo = JiaiWatchInfoMapper.selectByPrimaryKey(terminalId);

        JiaiWatchInfo record=new JiaiWatchInfo();
        record.setTerminalid(terminalId);
        //心率1 血压2
        if("1".equals(type)){
            record.setHeartinterval(interval);
        }
        if("2".equals(type)){
            record.setBloodpressureinterval(interval);
        }
        if("0000".equals(jsonObject.getString("code"))){
            if(null==jiaiWatchInfo){
                JiaiWatchInfoMapper.insertSelective(record);
            }else{
                JiaiWatchInfoMapper.updateByPrimaryKeySelective(record);
            }
        }
        return Result.ok(jsonObject.getString("message"));
    }

    /**
     * 定位方式修改
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = LOCATION_PATTERN, method = RequestMethod.POST)
    @ResponseBody
    public Result locationPattern(String terminalId, int mode, HttpServletRequest request) {
        String describe = "定位方式修改";
        JiaiInterfaceList selectByPrimaryKeyByDescribe = jiaiInterfaceListDasImpl.selectByPrimaryKeyByDescribe(describe);
        if (null == selectByPrimaryKeyByDescribe) {
            return Result.error("数据错误");
        }
        String api = selectByPrimaryKeyByDescribe.getApi();
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("imei", terminalId);
        json.put("mode", mode);
        String jsonString = JSONObject.toJSONString(json);
        System.out.println(jsonString);
        String doPut = HttpClientUtil.doPost(api, terminalId, selectByPrimaryKeyByDescribe.getKey(), selectByPrimaryKeyByDescribe.getCpid(), jsonString);
        JSONObject jsonDoPut = JSONObject.parseObject(doPut);
        return Result.ok(jsonDoPut.getString("message"));

    }


    /**
     * 定位请求
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = LOCATION_REQUEST, method = RequestMethod.POST)
    @ResponseBody
    public Result locationRequest(String terminalId, HttpServletRequest request) {
        if (null == terminalId) {
            return Result.error("参数错误");
        }
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("imei", terminalId);
        String jsonString = JSONObject.toJSONString(json);
        String describe = "定位请求";
        JiaiInterfaceList selectByPrimaryKeyByDescribe = jiaiInterfaceListDasImpl.selectByPrimaryKeyByDescribe(describe);
        String api = selectByPrimaryKeyByDescribe.getApi();
        String doPut = HttpClientUtil.doPut(api, terminalId, selectByPrimaryKeyByDescribe.getKey(), selectByPrimaryKeyByDescribe.getCpid(), jsonString);
        JSONObject jsonDoPut = JSONObject.parseObject(doPut);
        return Result.ok(jsonDoPut.getString("message"));

    }

    /**
     * 心率测量请求
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = MEASUREMENT_REQUEST, method = RequestMethod.POST)
    @ResponseBody
    public Result measurementRequest(String terminalId, HttpServletRequest request) {
        if (null == terminalId) {
            return Result.error("参数错误");
        }
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("imei", terminalId);
        String jsonString = JSONObject.toJSONString(json);
        String describe = "心率测量请求";
        JiaiInterfaceList selectByPrimaryKeyByDescribe = jiaiInterfaceListDasImpl.selectByPrimaryKeyByDescribe(describe);
        String api = selectByPrimaryKeyByDescribe.getApi();
        String doPut = HttpClientUtil.doPut(api, terminalId, selectByPrimaryKeyByDescribe.getKey(), selectByPrimaryKeyByDescribe.getCpid(), jsonString);
        JSONObject jsonDoPut = JSONObject.parseObject(doPut);
        return Result.ok(jsonDoPut.getString("message"));
    }

    /**
     * 血压测量请求
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = BLOODPRESSURE, method = RequestMethod.POST)
    @ResponseBody
    public Result BLOODPRESSURE(String terminalId, HttpServletRequest request) {
        if (null == terminalId) {
            return Result.error("参数错误");
        }
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("imei", terminalId);
        String jsonString = JSONObject.toJSONString(json);
        String describe = "血压测量请求";
        JiaiInterfaceList selectByPrimaryKeyByDescribe = jiaiInterfaceListDasImpl.selectByPrimaryKeyByDescribe(describe);
        String api = selectByPrimaryKeyByDescribe.getApi();
        String doPut = HttpClientUtil.doPut(api, terminalId, selectByPrimaryKeyByDescribe.getKey(), selectByPrimaryKeyByDescribe.getCpid(), jsonString);
        JSONObject jsonDoPut = JSONObject.parseObject(doPut);
        return Result.ok(jsonDoPut.getString("message"));
    }

    /**
     * 设置心率测量周期
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = HEALTHINTERVAL, method = RequestMethod.POST)
    @ResponseBody
    public Result healthinterval(String terminalId, String span, HttpServletRequest request) {
        if (null == terminalId || null == span) {

            return Result.error("参数错误");
        }
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("imei", terminalId);
        json.put("type", 1);
        json.put("interval", span);
        String jsonString = JSONObject.toJSONString(json);
        String describe = "设置心率测量周期";
        JiaiInterfaceList selectByPrimaryKeyByDescribe = jiaiInterfaceListDasImpl.selectByPrimaryKeyByDescribe(describe);
        String api = selectByPrimaryKeyByDescribe.getApi();
        String doPut = HttpClientUtil.doPost(api, terminalId, selectByPrimaryKeyByDescribe.getKey(), selectByPrimaryKeyByDescribe.getCpid(), jsonString);
        JSONObject jsonDoPut = JSONObject.parseObject(doPut);
        return Result.ok(jsonDoPut.getString("message"));

    }


    /**
     * 心 率 血压 血氧 数据
     * imei String 串号
     * heartRate Int 心率
     * dbp Int 低压
     * sdp Int 高压
     * oxygen Int 血氧
     * bloodSugar String 血糖WellUserBloodPressureDasImpl
     */

    @RequestMapping(value = "/health", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public Result health(HttpServletRequest request,
                         HttpServletResponse response) {
        BufferedReader bf;
        String jsonString = "";
        try {
            request.setCharacterEncoding("UTF-8");
            bf = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            // 循环读取字符流所有字符
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            jsonString = sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject json = JSONObject.parseObject(jsonString);
        System.out.println("/health----------------" + json);
        String imei = json.getString("imei");//设备id
        if(StringUtil.isEmpty(imei)){
            return Result.error("参数异常");
        }
        String usercode = YhUserDeviceMapper.selectNewDeviceUserCode(imei);
        if (StringUtil.isEmpty(usercode)) {
            return Result.error("未绑定鲨鱼web");
        }
        //截取当前系统时间
        Date currentTime = new Date();
//改变输出格式（自己想要的格式）
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//得到字符串时间
        String s8 = formatter.format(currentTime);

        Integer heartRate = json.getInteger("heartRate");//心率
        if (null != heartRate && heartRate > 0) {
            YHUserHeartRate record = new YHUserHeartRate();
            record.seteUserId(usercode);
            record.setHeartRate(heartRate);
            record.setEntryTime(s8);
            record.setDataSource("智能手表:"+imei);
            record.setEnv("常规测量");
            record.setIsDel(2);
            YHUserHeartRateMapper.insertSelective(record);
            try {
                yhHeThresholdService.selectBythresType("pulse", usercode, String.valueOf(heartRate), "心率",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Integer dbp = json.getInteger("dbp");//低压
        Integer sdp = json.getInteger("sdp");//高压
        if (null != dbp && dbp > 0&&null != sdp && sdp > 0) {
            YHUserBloodPressure record=new YHUserBloodPressure();
            record.seteUserId(usercode);
            record.setIsDel(2);
            record.setSbp(sdp);
            record.setDbp(dbp);
            record.setEntryTime(s8);
            record.setTestHj("常规测量");
            record.setType("智能手表:"+imei);
            try {
                yhHeThresholdService.selectBythresType("blood_pressure_dia", usercode, String.valueOf(dbp), "舒张压",request);
                yhHeThresholdService.selectBythresType("blood_pressure_sys", usercode, String.valueOf(sdp), "收缩压",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            YHUserBloodPressureMapper.insertSelective(record);
        }
        Integer oxygen = json.getInteger("oxygen");//血氧
        if (null != oxygen && oxygen > 0) {
            YHUserOxygen record=new YHUserOxygen();
            record.setOxygen((float)oxygen);
            record.setIsDel(2);
            record.setEntryTime(s8);
            record.seteUserId(usercode);
            record.setTestHj("常规");
            record.setType("智能手表:"+imei);
            YHUserOxygenMapper.insertSelective(record);
            try {
                yhHeThresholdService.selectBythresType("boold_oxygen", usercode, String.valueOf(oxygen), "血氧",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String bloodSugar = json.getString("bloodSugar");//血糖
        if (StringUtil.isNotEmpty(bloodSugar)) {
            YHUserBloodsuger record=new YHUserBloodsuger();
            record.setBs(Float.parseFloat(bloodSugar));
            record.setEntryTime(s8);
            record.setIsDel(2);
            record.setEnv("空腹");
            record.seteUserId(usercode);
            record.setDataSource("智能手表:"+imei);
            YHUserBloodsugerMapper.insertSelective(record);
            try {
                yhHeThresholdService.selectBythresType("blood_sugar", usercode, bloodSugar, "空腹血糖",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String temperature = json.getString("temperature");//温度
        if(StringUtil.isNotEmpty(temperature)){
            YHUserTemperature record=new YHUserTemperature();
            record.setTemperature(Float.parseFloat(temperature));
            record.setEntryTime(s8);
            record.setIsDel(2);
            record.seteUserId(usercode);
            record.setDataSource("智能手表:"+imei);
            try {
                yhHeThresholdService.selectBythresType("temperature", usercode, bloodSugar, "体温",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            YHUserTemperatureMapper.insertSelective(record);
        }


        String str="oxygen:"+oxygen+"dbp:"+dbp+"sdp:"+sdp+"heartRate:"+heartRate+"bloodSugar:"+bloodSugar+"temperature:"+temperature;

            return Result.ok(str);

    }


    /**
     * 预 警 数据
     * imei		串号
     * warningType	“1” sos 预警“2” 跌倒预警“3” 脱手预警“4” 久坐预警“5” 低电预警“6” 家庭医生“7” 房颤预警
     * battery 如果是低电预警则有值，其他预警无值
     * address 如果是 SOS 预警则有值，其他预警无值
     * longitude SOS 高德经度，如果是 SOS 预警则有值，其他预警无值；如果定位失败，则无值
     * latitude  SOS 高德纬度；如果是 SOS 预警则有值，其他预警无值；如果定位失败，则无值
     * obj.setMemberId(memberId);
     * obj.setType("pulse");
     * obj.setLow(pulsemin);
     * obj.setHigh(pulsemax);
     * healthyDataDas.updatePersonalThreshold(obj);
     */

    @RequestMapping(value = WARNING, produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public Result warning(HttpServletRequest request,
                          HttpServletResponse response) {
        BufferedReader bf;
        String jsonString = "";
        try {
            request.setCharacterEncoding("UTF-8");
            bf = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            // 循环读取字符流所有字符
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            jsonString = sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject json = JSONObject.parseObject(jsonString);
        System.out.println(json);

        String imei = json.getString("imei");//设备id
        String warningType = json.getString("warningType");//预警类型

        if(StringUtil.isEmpty(imei)||StringUtil.isEmpty(warningType)){
            return Result.error("参数异常");
        }

        //如果是1为sos预警
        if ("1".equals(warningType)) {
            String address = json.getString("address");//如果是 SOS 预警则有值，其他预警无值


            String longitude = json.getString("longitude");//SOS 高德经度，如果是 SOS 预警则有值，其他预警无值；如果定位失败，则无值


            String latitude = json.getString("latitude");//SOS 高德纬度；如果是 SOS 预警则有值，其他预警无值；如果定位失败，则无值
            JiaiAlarm record = new JiaiAlarm();
            record.setTerminalid(imei);
            record.setType(warningType);
            record.setAddress(address);
            record.setLongitude(longitude);
            record.setLatitude(latitude);

            JiaiAlarmMapper.insertSelective(record);

            String usercode = YhUserDeviceMapper.selectNewDeviceUserCode(imei);
            if (StringUtil.isEmpty(usercode)) {
                return Result.error("未绑定鲨鱼web");
            }
            YhUserReginfo info=yhUserReginfoMapper.selectByPrimaryKey(usercode);//查询用户表
            YhUserReginfo doctor=yhUserReginfoMapper.showdoctorCode(usercode);//查询医生code
            com.ibm.icu.text.SimpleDateFormat dateFormat= new com.ibm.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            com.ibm.icu.text.SimpleDateFormat format = new com.ibm.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = format.format(new Date());
            YhJzServicePerson per=new YhJzServicePerson();
            per.setUserCode(usercode);
            per.setServiceWaitingTime(dateStr);
            per.setGender(info.getGender());
            per.setPhone(info.getMobile());
            per.setAge(getAgeByBirth(info.getBirthDate()));
            per.setServiceContent("了解用户情况，提醒用户注意事项");
            per.setServiceType(13);
            per.setServiceRecord(address);
            per.setInsertTime(dateFormat.format(new Date()));
            per.setServiceSubtitle("SOS告警");
            per.setTotaltype(1);
            yhAcceptserviceMapper.insertserviceplan(per);
            int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
            if(acc==0) {
                yhAcceptserviceMapper.inserttotalper(doctor.getUserCode(), usercode);
            }

        } else if ("5".equals(warningType)) {
            //低电预警
            Integer battery = json.getInteger("battery");//如果是低电预警则有值，其他预警无值
            String latitude = json.getString("latitude");//SOS 高德纬度；如果是 SOS 预警则有值，其他预警无值；如果定位失败，则无值
            JiaiAlarm record = new JiaiAlarm();
            record.setTerminalid(imei);
            record.setType(warningType);
            record.setBattery(battery);
            record.setLatitude(latitude);

            JiaiAlarmMapper.insertSelective(record);

        } else {
            JiaiAlarm record = new JiaiAlarm();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            record.setEstablishTime(df.format(new Date()));
            record.setTerminalid(imei);
            record.setType(warningType);
            JiaiAlarmMapper.insertSelective(record);
        }


        return Result.ok();

    }
    /**
     * 根据出生算年龄
     *
     * @param birthday
     * @return
     */
    private int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {// 兼容性更强,异常后返回数据
            return 0;
        }
    }
    public static void main(String[] args) {
		/*List<WatchesSosBean> sosList=new ArrayList<WatchesSosBean>();
		WatchesSosBean ws=new WatchesSosBean();
		ws.setMobileNo("18569089087");
		ws.setName("邓永波");
		sosList.add(ws);
		Map<String, Object> json=new HashMap<String, Object>();
		json.put("imei", "350192557917455");
		json.put("sosList", sosList);
		String jsonString = JSONObject.toJSONString(json);
		String doPut = HttpClientUtil.doPut("http://api.jiai.pro:8080/jiai/sos", "350192557917455","933be24ac830c703c56fe6928f862f87","685", jsonString);
	System.out.println(doPut);*/


    }


    /**
     * 电量/记步数据
     * imei 串号
     * battery	电量
     * pedometer 计步数据
     */
    @RequestMapping(value = "/beatHeart", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public Result beatHeart(HttpServletRequest request,
                            HttpServletResponse response) {
        BufferedReader bf;
        String jsonString = "";
        try {
            request.setCharacterEncoding("UTF-8");
            bf = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            // 循环读取字符流所有字符
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            jsonString = sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject json = JSONObject.parseObject(jsonString);
        System.out.println(json);


        return Result.ok();


    }


    @RequestMapping(value = "/audio", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public Result audio(HttpServletRequest request,
                           HttpServletResponse response) {
        return Result.error();
}

    /**
     * 定位数据获取
     * imei  串号
     * type 地图类型，目前只支持高德
     * lat  高德坐标纬度
     * lon 高德坐标经度
     */
    @RequestMapping(value = "/location", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    @LimitRequest
    public Result location(HttpServletRequest request,
                           HttpServletResponse response) {
        BufferedReader bf;
        String jsonString = "";
        try {
            request.setCharacterEncoding("UTF-8");
            bf = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            // 循环读取字符流所有字符
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            jsonString = sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject json = JSONObject.parseObject(jsonString);
        System.out.println(json);

        String imei = json.getString("imei");//串号


        String lat = json.getString("lat");//高德坐标纬度

        String lon = json.getString("lon");//高德坐标经度
        if(StringUtil.isEmpty(imei)||StringUtil.isEmpty(lat)||StringUtil.isEmpty(lon)){
            return Result.error("参数异常");
        }
        JiaiLocate record = new JiaiLocate();
        record.setImei(imei);
        record.setLat(lat);
        record.setLon(lon);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        record.setEstablishTime(df.format(new Date()));
        JiaiLocateMapper.insertSelective(record);
        JiaiEnclosure enclosureNewInfo = JiaiEnclosureMapper.findEnclosureNewInfo(imei);
        if(null!=enclosureNewInfo){
            String latitude = enclosureNewInfo.getLatitude();
            String longitude = enclosureNewInfo.getLongitude();
            String startLonLat=longitude+","+latitude;
            String endLonLat=lon+","+lat;
            Integer radius = enclosureNewInfo.getRadius();

            String key="d07c5595e42b41bb3de6eed154bc6946";

            System.out.println(startLonLat);
            System.out.println(endLonLat);
            Long dis = GetDistance.getDistance(startLonLat,endLonLat,key);
            String usercode = YhUserDeviceMapper.selectNewDeviceUserCode(imei);
            YhUserReginfo doctor=yhUserReginfoMapper.showdoctorCode(usercode);
            if(dis>radius){
                PushMessage pushMessagel=new PushMessage();
                pushMessagel.setLoginUserNum(doctor.getUserCode());
                pushMessagel.setContent("电子围栏告警");
                ioService.pushMessageToUser(pushMessagel);
                if (StringUtil.isEmpty(usercode)) {
                    return Result.error("未绑定鲨鱼web");
                }
                YhUserReginfo info=yhUserReginfoMapper.selectByPrimaryKey(usercode);//查询用户表
                com.ibm.icu.text.SimpleDateFormat dateFormat= new com.ibm.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                com.ibm.icu.text.SimpleDateFormat format = new com.ibm.icu.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = format.format(new Date());
                YhJzServicePerson per=new YhJzServicePerson();
                per.setUserCode(usercode);
                per.setServiceWaitingTime(dateStr);
                per.setGender(info.getGender());
                per.setPhone(info.getMobile());
                per.setAge(getAgeByBirth(info.getBirthDate()));
                per.setServiceContent("了解用户情况，提醒用户注意事项");
                per.setServiceType(13);
                long l = dis - radius;
                per.setServiceRecord("超出电子围栏:"+l+"米");
                per.setInsertTime(dateFormat.format(new Date()));
                per.setServiceSubtitle("电子围栏告警");
                per.setTotaltype(1);
                yhAcceptserviceMapper.insertserviceplan(per);
                int acc=yhAcceptserviceMapper.selectcountbyuserocde(per);//查询服务任务总表是否存在usercode
                if(acc==0) {
                    yhAcceptserviceMapper.inserttotalper(doctor.getUserCode(), usercode);
                }

            }
            System.out.println(dis);
            return Result.ok(dis);
        }

        return Result.ok();

    }


}
