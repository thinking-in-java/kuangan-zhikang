/**
 *
 */
package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.*;
import com.yihe.system.service.*;
import com.yihe.system.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * K5,K3接口对接
 * @author dong
 *
 *2019-10-22
 */
@Controller
public class LilinMachineController {

    @Autowired
    protected YhUserReginfoMapper yhUserReginfoMapper;

    @Autowired
    protected YhUserMakeCardMapper yhUserMakeCardMapper;

    @Autowired
    protected YhUserAdminInfoMapper yhUserAdminInfoMapper;

    @Autowired
    protected YhUserRoleService yhUserRoleService;

    @Autowired
    protected YhUserRolePowerService yhUserRolePowerService;

    @Autowired
    protected YHUserEcgMapper yHUserEcgMapper;

    @Autowired
    protected YhUserWaistlineMapper yhUserWaistlineMapper;

    @Autowired
    protected YHUserBloodsugerMapper yHUserBloodsugerMapper;

    @Autowired
    protected YHUserBloodfatMapper yHUserBloodfatMapper;

    @Autowired
    protected YHUserBloodPressureMapper yHUserBloodPressureMapper;

    @Autowired
    protected YHUserRenalfunctionMapper yHUserRenalfunctionMapper;

    @Autowired
    protected YHUserBodyFatMapper yHUserBodyFatMapper;

    @Autowired
    protected YHUserOxygenMapper yHUserOxygenMapper;

    @Autowired
    private YhSysConfigService yhSysConfigService;

    @Autowired
    protected YHUserBmiMapper yHUserBmiMapper;

    @Autowired
    protected YHUserTemperatureMapper yHUserTemperatureMapper;

    @Autowired
    YhTaskServiceAPIService yhtaskservuceapiservice;

    @Autowired
    YhUserPersonageConditionMapper yhUserPersonageConditionMapper;

    @Autowired
    private RedisTemplate<String, String> stringTemplate;

    @Autowired
    YhOrgFacilityMapper yhOrgFacilityMapper;
    @Autowired
    YHUserHeartRateMapper yhUserHeartRateMapper;

    @Autowired
    private YhOrgTreeService yhOrgTreeService;

    /****
     * 获取一体机数据（嘉乐医疗）
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/api/reportUpload")
    @ResponseBody
    public String HandleGetK5DatasFromDevice(HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        YhUserReginfo welluser = new YhUserReginfo();
        // 获取request传输过来的字符流
        BufferedReader bf;
        String jsonString = "";
        try {
            request.setCharacterEncoding("UTF-8");
            bf = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            //循环读取字符流所有字符
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            jsonString = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String reponseJsonString = "{\"errcode\":\"0\",\"errmsg\":\"success\"}";
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        if(jsonObject!=null){
            String userCode = getLilinUserCode(jsonObject);
            JSONObject json = jsonObject.getJSONObject("report");
            JSONObject userjson = jsonObject.getJSONObject("user");
            //直接上传数据
            if (!StringUtils.isEmpty(userCode)) {
                addJLPEecg(userCode, json);
                addJLBloodPressure(userCode, json);
                addJLBloodSugar(userCode, json);
                addJLHeightAndWeight(userCode, json);
                addJLOxygen(userCode, json);
                addJLBloodlipidFour(userCode, json);
                addJLBloodFat(userCode, json);
                addPulseRate(userCode, json); //添加脉率到心率表中
                addTemperature(userCode, json); //添加体温
                ReviceDataFromPlatemp temp = convertk5ToMyData(userCode,jsonObject, json);
                //填写必填10项
                reponseJsonString = healthQuestionRedisData(temp, userCode);
                reponseJsonString = "{\"errcode\":\"0\",\"errmsg\":\"success\"}";
            } else {
                String CardId = "";
                String orgid = "10000", drId = "1";
                if(StringUtil.isNotEmpty(userjson.getString("orgId"))){
                    orgid = userjson.getString("orgId");
                }
                if(StringUtil.isNotEmpty(userjson.getString("drId"))){
                    drId = userjson.getString("drId");
                }
                /*String factoryNumber = jsonObject.getString("deviceId");//设备唯一编码
                if (factoryNumber == null || factoryNumber.equals("")) {
                    CardId = yhUserMakeCardMapper.selectcard1("10000");
                    if (CardId == null || CardId.equals("")) {//如果总机构还是没有注册卡
                        Map<String, Object> sreqData = new HashMap<String, Object>();
                        sreqData.put("errcode", 5);
                        sreqData.put("errmsg", "没有注册卡可用了哦!");
                        return reponseJsonString = JSON.toJSONString(sreqData);
                    }
                } else {
                    YhOrgFacility org = yhOrgFacilityMapper.showOrgCodeBydeviceid(factoryNumber);
                    if (org == null) {//如果没有绑定
                        CardId = yhUserMakeCardMapper.selectcard2(orgid, drId);// 取usercode等于空的CardId
                        if (CardId == null || CardId.equals("")) {//如果总机构还是没有注册卡
                            Map<String, Object> sreqData = new HashMap<String, Object>();
                            sreqData.put("errcode", 5);
                            sreqData.put("errmsg", "没有注册卡可用了哦!");
                            return reponseJsonString = JSON.toJSONString(sreqData);
                        }
                    } else {
                        orgid = org.getOrgCode();
                        CardId = yhUserMakeCardMapper.selectcard1(org.getOrgCode());// 取usercode等于空的CardId
                        if (CardId == null || CardId.equals("")) {//如果当前机构下没有注册卡
                            orgid = "10000";
                            CardId = yhUserMakeCardMapper.selectcard1("10000");
                            if (CardId.equals("") || CardId == null) {//如果总机构还是没有注册卡
                                Map<String, Object> sreqData = new HashMap<String, Object>();
                                sreqData.put("errcode", 5);
                                sreqData.put("errmsg", "没有注册卡可用了哦!");
                                return reponseJsonString = JSON.toJSONString(sreqData);
                            }
                        }
                    }
                }*/
                CardId = yhUserMakeCardMapper.selectcard2(orgid, drId);// 取usercode等于空的CardId
                if (CardId == null || CardId.equals("")) {//如果当前机构下没有注册卡
                    orgid = "10000";
                    CardId = yhUserMakeCardMapper.selectcard1("10000");
                    if (CardId.equals("") || CardId == null) {//如果总机构还是没有注册卡
                        Map<String, Object> sreqData = new HashMap<String, Object>();
                        sreqData.put("errcode", 5);
                        sreqData.put("errmsg", "没有注册卡可用了哦!");
                        return reponseJsonString = JSON.toJSONString(sreqData);
                    }
                }
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("username", userjson.getString("phone"));
                int count1 = yhUserReginfoMapper.showchushen(map1);
                if (count1 > 0) {
                    Map<String, Object> sreqData = new HashMap<String, Object>();
                    sreqData.put("errcode", 5);
                    sreqData.put("errmsg", "用户名已存在!");
                    return reponseJsonString = JSON.toJSONString(sreqData);
                } else {
                    //否则注册用户
                    String UserCode = yhUserReginfoMapper.selectuser();// 取最大的usercode
                    int userid = Integer.valueOf(UserCode) + 1;// userCode加上r
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date data = null;
                    if (userjson.getString("birthday") == null) {
                        data = sdf.parse(sdf.format(new Date()));
                    } else {
                        data = sdf.parse(userjson.getString("birthday"));
                    }
                    welluser.setUserCode(String.valueOf(userid));// usercode
                    welluser.setUserType(5);// 3，机构，4，医生，5，用户
                    welluser.setUserName(userjson.getString("phone"));// 登录用户名
                    String nameAndPwd = new Md5Hash((String) userjson.getString("phone") + "12345678").toHex();
                    welluser.setUserPwd(nameAndPwd);
                    welluser.setGender(userjson.getInteger("sex"));// 性别
                    welluser.setBirthDate(data);// 出生日期
                    // welluser.setEmail(email);//邮箱
                    welluser.setProvinceId("0");// 默认值s
                    welluser.setCityId("0");// 默认值
                    welluser.setAreaId("0");// 默认值
                    welluser.setRegTime(df.parse(df.format(new Date())));// 取当前时间
                    welluser.setLoginTimes(0);
                    welluser.setName((String) userjson.getString("name"));// 真实姓名
                    welluser.setAddress("");// 地址
                    welluser.setMobile((String) userjson.getString("phone"));// 电话号码
                    welluser.setLockFlag(2);
                    welluser.setRegSource(2);
                    welluser.setCardidentity((String) userjson.getString("idCard"));//身份证
                    welluser.setCertType(0);
                    welluser.setOrgCode(orgid);// 单位编码
                    welluser.setEnrolCardId(CardId);
                    yhUserReginfoMapper.insertSelective(welluser);
                    //查询角色表
                    Integer roleId = yhUserRoleService.selectRoleId("用户");
                    //用户角色关联表插入id和code
                    YhUserRolePower yhUserRolePower = new YhUserRolePower();
                    yhUserRolePower.setRoleid(roleId);
                    yhUserRolePower.setUserid(String.valueOf(userid));
                    yhUserRolePowerService.inssert(yhUserRolePower);
                    YhUserMakeCard card = yhUserMakeCardMapper.selectByPrimaryKey(CardId);
                    if (card.getDoctor() == null) {
                        YhUserAdminInfo admin = yhUserAdminInfoMapper.showid();
                        card.setDoctor(admin.getId());
                    }
                    card.setEnrolCardId(CardId);
                    card.setUserCode(String.valueOf(userid));// usercode
                    card.setBeginTime(df.parse(df.format(new Date())));// 去当前时间
                    //card.setOrgId("10000");// 单位编码
                    yhUserMakeCardMapper.updateByPrimaryKeySelective(card);// 更新
                    addJLPEecg(String.valueOf(userid), json);
                    addJLBloodPressure(String.valueOf(userid), json);
                    addJLBloodSugar(String.valueOf(userid), json);
                    addJLHeightAndWeight(String.valueOf(userid), json);
                    addJLOxygen(String.valueOf(userid), json);
                    addJLBloodlipidFour(String.valueOf(userid), json);
                    addJLBloodFat(String.valueOf(userid), json);
                    addPulseRate(String.valueOf(userid), json);
                    addTemperature(String.valueOf(userid), json); //添加体温
                    ReviceDataFromPlatemp temp = convertk5ToMyData(String.valueOf(userid),jsonObject,json);
                    // 填写必填10项
                    reponseJsonString = healthQuestionRedisData(temp, String.valueOf(userid));
                    reponseJsonString =  "{\"errcode\":\"0\",\"errmsg\":\"success\"}";
                }
            }
        }
        return reponseJsonString;
    }

    /****
     * 获取用户信息(手机号体检)
     * @param request
     * @param response
     * @return
     * @throws ParseException
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/api/phoneLogin", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getUserByPhone(HttpServletRequest request,
                                 HttpServletResponse response, @RequestBody Map<String, String> map) throws ParseException, JsonProcessingException {
        Map<String, Object> sreqData = new HashMap<String, Object>();
        String phone = map.get("phone");
        if (StringUtil.isEmpty(phone)) {
            sreqData.put("errcode", 5);
            sreqData.put("errmsg", "手机号码为空");
        } else {
            YhUserReginfo wellRegUserinfo = yhUserReginfoMapper.showmobile(phone);
            if (wellRegUserinfo == null) {
                sreqData.put("errcode", 0);
                sreqData.put("errmsg", "接口调用成功");
                sreqData.put("data", "");
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(wellRegUserinfo.getBirthDate());
                sreqData.put("errcode", 0);
                sreqData.put("errmsg", "接口调用成功");
                Map<Object, Object> usermap = new LinkedHashMap<>();
                usermap.put("name", wellRegUserinfo.getUserName());
                usermap.put("sex", wellRegUserinfo.getGender());
                usermap.put("birthday", dateString);
                usermap.put("phone", wellRegUserinfo.getMobile());
                usermap.put("idCard", wellRegUserinfo.getCardidentity());
                usermap.put("photo", "");
                usermap.put("nation", wellRegUserinfo.getNation());
                usermap.put("address", wellRegUserinfo.getAddress());
                usermap.put("orgId", wellRegUserinfo.getOrgCode());
                usermap.put("drId", wellRegUserinfo.getDoctor());
                sreqData.put("data", usermap);
            }
        }
        return JSON.toJSONString(sreqData);
    }


    /****
     * 获取用户信息(手机号体检)
     * @param request
     * @param response
     * @return
     * @throws ParseException
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/api/idCardLogin", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getUserByIdcard(HttpServletRequest request,
                                  HttpServletResponse response, @RequestBody Map<String, String> map) throws ParseException, JsonProcessingException {
        Map<String, Object> sreqData = new HashMap<String, Object>();
        String idCard = map.get("idCard");
        if (StringUtil.isEmpty(idCard)) {
            sreqData.put("errcode", 5);
            sreqData.put("errmsg", "身份证为空");
        } else {
            YhUserReginfo wellRegUserinfo = yhUserReginfoMapper.showCardId(idCard);
            if (wellRegUserinfo == null) {
                sreqData.put("errcode", 0);
                sreqData.put("errmsg", "接口调用成功");
                sreqData.put("data", "");
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(wellRegUserinfo.getBirthDate());
                sreqData.put("errcode", 0);
                sreqData.put("errmsg", "接口调用成功");
                Map<Object, Object> usermap = new LinkedHashMap<>();
                usermap.put("name", wellRegUserinfo.getUserName());
                usermap.put("sex", wellRegUserinfo.getGender());
                usermap.put("birthday", dateString);
                usermap.put("phone", wellRegUserinfo.getMobile());
                usermap.put("idCard", wellRegUserinfo.getCardidentity());
                usermap.put("photo", "");
                usermap.put("nation", wellRegUserinfo.getNation());
                usermap.put("address", wellRegUserinfo.getAddress());
                usermap.put("orgId", wellRegUserinfo.getOrgCode());
                usermap.put("drId", wellRegUserinfo.getDoctor());
                sreqData.put("data", usermap);
            }
        }
        return JSON.toJSONString(sreqData);
    }

    /**
     * 获取医生信息接口
     */
    @RequestMapping(value = "/api/getOrgDr", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getOrgDr(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) {
        String reponseJsonString = "";
        String deviceId = map.get("deviceId");
        if (StringUtil.isEmpty(deviceId)) {
            reponseJsonString = "{\"success\":\"false\",\"errmsg\":\"设备id不能为空\",\"errcode\":\"5\"}";
        } else {
            //查询设备id绑定的机构
            YhOrgFacility org = yhOrgFacilityMapper.showOrgCodeBydeviceid(deviceId);
            String orgcode = "10000";//默认值
            if (org != null) {
                orgcode = org.getOrgCode();
            }
            //查询这个机构以及此机构的所有子机构信息
            List<YhOrgTreeVojo> upmsPermissionst = yhOrgTreeService.selectalltreeOrg1(orgcode);
            JSONArray jsonArray = new JSONArray();
            for (YhOrgTreeVojo yhOrgTreeVojo : upmsPermissionst) {
                JSONObject jo = new JSONObject();
                jo.put("id", yhOrgTreeVojo.getId());
                jo.put("name", yhOrgTreeVojo.getName());
                JSONArray jsonArray1 = new JSONArray();

                yhOrgTreeVojo.getChildren().forEach(item -> {
                    JSONObject jo1 = new JSONObject();
                    jo1.put("id", item.getId());
                    jo1.put("name", item.getName());
                    jsonArray1.add(jo1);
                });

                jo.put("drList", jsonArray1);
                jsonArray.add(jo);
            }
            reponseJsonString = "{\"success\":\"true\",\"errmsg\":\"接口调用成功\",\"errcode\":\"0\",\"data\":" + jsonArray.toString() + "}";
        }
        return reponseJsonString;
    }

    /**
     * 体温
     * @param userCode
     * @param json
     */
    private void addTemperature(String userCode, JSONObject json) {
        String data_Source = "一体机";
        String env = "常规测量";
        Integer is_Del = 2;
        String date = "";
        String heart;
        date = json.getString("createTime"); //时间
        if (json.getString("thermometer") != null) {
            heart = json.getString("thermometer"); //心率值
            //查询此用户最新的测量记录 如果这条数据与之前的测量数据时间一摸一样，精确到秒
            YHUserTemperature temperature = yHUserTemperatureMapper.selectByPrimaryKeyByUsercodeLimit(userCode);
            if (null != temperature) {
                //判断时间
                String oldTime = temperature.getEntryTime();
                if (oldTime.equals(date)) return;
            }
            YHUserTemperature temperatureAllInOne = new YHUserTemperature();
            temperatureAllInOne.setTemperature(Float.valueOf(heart));
            temperatureAllInOne.setEntryTime(date);
            temperatureAllInOne.setIsDel(is_Del);
            temperatureAllInOne.seteUserId(userCode);
            temperatureAllInOne.setDataSource(data_Source);
            try {
                yHUserTemperatureMapper.insertSelective(temperatureAllInOne);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    /**
     * 将脉率值添加到心率表中
     * @param userCode
     * @param json
     */
    private void addPulseRate(String userCode, JSONObject json) {
        String data_Source = "一体机";
        String env = "常规测量";
        Integer is_Del = 2;
        String date = "";
        int heart = 0;
        date = json.getString("createTime"); //时间
        if (json.getInteger("ecgRate") != null) {
            heart = json.getInteger("ecgRate"); //心率值
        }
        if (heart != 0) {
            //查询此用户最新的测量记录 如果这条数据与之前的测量数据时间一摸一样，精确到秒
            YHUserHeartRate rate1 = yhUserHeartRateMapper.selectByPrimaryKeyByUsercodeLimit(userCode);
            if (null != rate1) {
                //判断时间
                String oldTime = rate1.getEntryTime();
                if (oldTime.equals(date)) return;
            }
            YHUserHeartRate rate = new YHUserHeartRate();
            rate.setHeartRate(heart);
            rate.setDataSource(data_Source);
            rate.setIsDel(is_Del);
            rate.setEntryTime(date);
            rate.setEnv(env);
            rate.seteUserId(userCode);
            try {
                yhUserHeartRateMapper.insertSelective(rate);
                System.out.print("pulseRate: " + heart + " entry_Time: " + date + " is_Del: " + is_Del);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将必填10项保存到redis中
     * @param reviceDataFromPlatemp
     * @param userCode
     * @return
     * @throws JsonProcessingException
     */
    public String healthQuestionRedisData(
            ReviceDataFromPlatemp reviceDataFromPlatemp, String userCode) throws JsonProcessingException {

        String reponseJsonString = "{\"code\":\"2\",\"msg\":\"success\"}";
        List<NewRiskReportContente> conList = new ArrayList<>();
        List<ConList> conList2 = reviceDataFromPlatemp.getConlist();
        for (int i = 0; i < conList2.size(); i++) {
            if (conList2.get(i).getConKey() != null) {
                NewRiskReportContente con = new NewRiskReportContente();
                con.setConKey(conList2.get(i).getConKey());
                con.setConValue(conList2.get(i).getConValue());
                con.setPos(i);
                conList.add(con);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String modelToJSONString = mapper.writeValueAsString(conList);
        ValueOperations<String, String> valueTemplate = stringTemplate.opsForValue();
        return reponseJsonString;

    }


    /***
     * K5将必填的10项
     * @param userCode
     *
     * @param json
     * @throws ParseException
     */
    private ReviceDataFromPlatemp convertk5ToMyData(String userCode, JSONObject memeberObj, JSONObject json) throws ParseException {
        ReviceDataFromPlatemp tmp = new ReviceDataFromPlatemp();
        System.out.print("entry ReviceDataFromPlatemp:");
        if (json != null) {
            String mobile = (String) memeberObj.get("phpne");// 电话
            String name = (String) memeberObj.get("name");// 姓名
            String sex = (String) memeberObj.get("sex");// 性别
            tmp.setPhone(mobile);
            tmp.setSex(sex);
            tmp.setUserName(name);
        }
        String height=null;
        String weight=null;
        String bloodSugar =null;
        String chol = null;
        String hdlChol = null;
        String calcLdl = null;
        String trig = null;
        String highPressure = null;
        String lowPressure = null;
        // 我要获取当前的日期
        Date date = new Date();
        // 设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        // 获取String类型的时间
        String createdate = sdf.format(date);
        System.out.print("tmp: " + tmp.toString());
        if (json.getString("weight") != null) {
             height = json.getString("height");
             weight = json.getString("weight");
            int i = yhtaskservuceapiservice.selectbmicount(userCode);
            if (i == 0) {
                YhJzServiceBodydataSwitch ok = yhtaskservuceapiservice.queryswitchbyid1(1, 1, "体重");
                if (ok.getFirst_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(1);
                    task.setService_content(ok.getService_content());
                    task.setService_record("体重:" + weight);
                    task.setReturnvisit(ok.getReturnvisit());
                    task.setChildnodes(ok.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            } else {
                YhJzServiceBodydataSwitch ok1 = yhtaskservuceapiservice.queryswitchbyid1(2, 1, "体重");
                if (ok1.getEvery_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok1.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(2);
                    task.setService_content(ok1.getService_content());
                    task.setService_record("体重:" + weight);
                    task.setReturnvisit(ok1.getReturnvisit());
                    task.setChildnodes(ok1.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            }
        }

        String waistline = json.getString("waistline");// 腰围
        String hipline = json.getString("hipline");// 臀围
        if (json.getString("bloodSugar") != null) {
            bloodSugar = json.getString("bloodSugar");// 餐前血糖
            int i = yhtaskservuceapiservice.selectbloodsuger(userCode);
            if (i == 0) {
                YhJzServiceBodydataSwitch ok = yhtaskservuceapiservice.queryswitchbyid1(1, 1, "血糖");
                if (ok.getFirst_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(1);
                    task.setService_content(ok.getService_content());
                    task.setService_record("血糖:" + bloodSugar);
                    task.setReturnvisit(ok.getReturnvisit());
                    task.setChildnodes(ok.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            } else {
                YhJzServiceBodydataSwitch ok1 = yhtaskservuceapiservice.queryswitchbyid1(2, 1, "血糖");
                if (ok1.getEvery_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok1.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(2);
                    task.setService_content(ok1.getService_content());
                    task.setService_record("血糖:" + bloodSugar);
                    task.setReturnvisit(ok1.getReturnvisit());
                    task.setChildnodes(ok1.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            }
        }



        if (json.getString("tc") != null) {
             chol = json.getString("tc");// 总胆固醇
             hdlChol = json.getString("hDL");// 高密度蛋白
             calcLdl = json.getString("lDL");// 低密度蛋白
             trig = json.getString("tg");// 甘油三脂
            int i = yhtaskservuceapiservice.selectbloodfat(userCode);
            if (i == 0) {
                YhJzServiceBodydataSwitch ok = yhtaskservuceapiservice.queryswitchbyid1(1, 1, "血脂四项");
                if (ok.getFirst_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(1);
                    task.setService_content(ok.getService_content());
                    task.setService_record("血脂四项:" + "总胆固醇/" + chol + ",甘油三脂/" + trig + ",高密度蛋白/" + hdlChol + ",低密度蛋白/" + calcLdl);
                    task.setReturnvisit(ok.getReturnvisit());
                    task.setChildnodes(ok.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            } else {
                YhJzServiceBodydataSwitch ok1 = yhtaskservuceapiservice.queryswitchbyid1(2, 1, "血脂四项");
                if (ok1.getEvery_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok1.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(2);
                    task.setService_content(ok1.getService_content());
                    task.setService_record("血脂四项:" + "总胆固醇/" + chol + ",甘油三脂/" + trig + ",高密度蛋白/" + hdlChol + ",低密度蛋白/" + calcLdl);
                    task.setReturnvisit(ok1.getReturnvisit());
                    task.setChildnodes(ok1.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            }
        }

        if (json.getString("highPressure") != null) {
             highPressure = json.getString("highPressure");// 收缩压
             lowPressure = json.getString("lowPressure");// 舒张压
            int i = yhtaskservuceapiservice.selectbloodpressurecount(userCode);
            if (i == 0) {
                YhJzServiceBodydataSwitch ok = yhtaskservuceapiservice.queryswitchbyid1(1, 1, "血压");
                if (ok.getFirst_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(1);
                    task.setService_content(ok.getService_content());
                    task.setService_record("血压:" + highPressure + "/" + lowPressure);
                    task.setReturnvisit(ok.getReturnvisit());
                    task.setChildnodes(ok.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            } else {
                YhJzServiceBodydataSwitch ok1 = yhtaskservuceapiservice.queryswitchbyid1(2, 1, "血压");
                if (ok1.getEvery_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok1.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(2);
                    task.setService_content(ok1.getService_content());
                    task.setService_record("血压:" + highPressure + "/" + lowPressure);
                    task.setReturnvisit(ok1.getReturnvisit());
                    task.setChildnodes(ok1.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            }
        }


        String pulse = json.getString("ecgRate");// 心率
        String ua = json.getString("bloodUric");// 血尿酸
        if (StringUtil.isNotEmpty(ua)) {
            double s = Double.valueOf(ua) * 1000;
            YHUserRenalfunction rf = new YHUserRenalfunction();
            rf.setUricAcid(String.valueOf(s));
            rf.setInerttime(createdate);
            rf.setUserId(Integer.valueOf(userCode));
            rf.setDataSource("一体机");
            yHUserRenalfunctionMapper.insertSelective(rf);
            System.out.println("随诊包肾功能尿酸insert++");
        }
        YhUserPersonageCondition list = yhUserPersonageConditionMapper.showcondition(userCode);
        YhUserPersonageCondition cond = new YhUserPersonageCondition();
        cond.setStature(height);//身高
        cond.setWeight(weight);//体重
        cond.setWaistline(waistline);//腰围
        cond.setHipline(hipline);//臀围
        if (height == null || height.equals("") || weight == null || weight.equals("") || waistline == null || waistline.equals("") || hipline == null || hipline.equals("")) {

        } else {
            if (list == null) {
                cond.setUserCode(userCode);
                yhUserPersonageConditionMapper.insertSelective(cond);
            } else {
                cond.setId(list.getId());
                yhUserPersonageConditionMapper.updateByPrimaryKeySelective(cond);
            }
        }
        List<ConList> conList = new ArrayList<ConList>();
        ConList list1 = new ConList();
        list1.setConKey("height");
        list1.setConValue(height);
        list1.setPos(180);
        conList.add(list1);
        ConList list2 = new ConList();
        list2.setConKey("weight");
        list2.setConValue(weight);
        list2.setPos(182);
        conList.add(list2);
        ConList list3 = new ConList();
        list3.setConKey("fbg");
        list3.setConValue(bloodSugar);
        list3.setPos(181);
        conList.add(list3);
        ConList list4 = new ConList();
        list4.setConKey("waist");
        list4.setConValue(waistline);
        list4.setPos(183);
        conList.add(list4);
        ConList list5 = new ConList();
        list5.setConKey("cholesterol");
        list5.setConValue(chol);
        list5.setPos(184);
        conList.add(list5);
        ConList list6 = new ConList();
        list6.setConKey("tg");
        list6.setConValue(trig);
        list6.setPos(185);
        conList.add(list6);
        ConList list7 = new ConList();
        list7.setConKey("hdlc");
        list7.setConValue(hdlChol);
        list7.setPos(186);
        conList.add(list7);
        ConList list8 = new ConList();
        list8.setConKey("ldlc");
        list8.setConValue(calcLdl);
        list8.setPos(187);
        conList.add(list8);
        ConList list9 = new ConList();
        list9.setConKey("sbp");
        list9.setConValue(highPressure);
        list9.setPos(188);
        conList.add(list9);
        ConList list10 = new ConList();
        list10.setConKey("dbp");
        list10.setConValue(lowPressure);
        list10.setPos(189);
        conList.add(list10);
        ConList list11 = new ConList();
        list11.setConKey("heart_rate");
        list11.setConValue(pulse);
        list11.setPos(190);
        conList.add(list11);
        ConList list14 = new ConList();
        list14.setConKey("bua");
        list14.setConValue(ua);
        list14.setPos(198);
        conList.add(list14);
        tmp.setConlist(conList);
        System.out.print("tmp: " + tmp.toString());
        return tmp;
    }



    /**
     * 添加嘉乐一体机血脂四项数据
     * @param userCode
     * @param json
     */
    private void addJLBloodlipidFour(String userCode, JSONObject json) throws ParseException {
        String type = "一体机";
        String test_hj = "常规测量";
        Integer is_Del = 2;
        float totalCholesterol = 0;// 总胆固醇(mmol/L)
        float triglycerides = 0;// 甘油三脂(mmol/L)
        float heightLipoprotein = 0;// 高密度蛋白(mmol/L)
        float lowLipoprotein = 0;// 低密度蛋白(mmol/L)
        String entry_Time = json.getString("createTime");
        if (json.getFloat("tc") == null) {
            return;
        }
        totalCholesterol = json.getFloat("tc");// 总胆固醇
        triglycerides = json.getFloat("tg");// 甘油三脂
        heightLipoprotein = json.getFloat("hDL");// 高密度蛋白
        lowLipoprotein = json.getFloat("lDL");// 低密度蛋白
        if (entry_Time == null || "".equals(entry_Time)) {
            SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
            entry_Time = dateFm.format(new Date());
        }
        if (totalCholesterol != 0 || lowLipoprotein != 0 || heightLipoprotein != 0 || triglycerides != 0) {
            //查询此用户最新的测量记录 如果这条数据与之前的测量数据时间一摸一样，精确到秒
            PageHelper.startPage(0, 1);
            List<YHUserBloodfat> yhUserBloodfats = yHUserBloodfatMapper.selectPageByParam(userCode);
            if (null != yhUserBloodfats && yhUserBloodfats.size() > 0) {
                //判断时间
                String oldTime = yhUserBloodfats.get(0).getEntryTime();
                if (oldTime.equals(entry_Time)) return;
            }
            //血脂四项初始化
            YHUserBloodfat yhUserBloodfat = new YHUserBloodfat();
            yhUserBloodfat.setCholesterin(totalCholesterol);// 总胆固醇
            yhUserBloodfat.setDataSource(test_hj);
            yhUserBloodfat.setEntryTime(entry_Time);
            yhUserBloodfat.setIsDel(is_Del);
            yhUserBloodfat.setLdl(lowLipoprotein);// 低密度蛋白
            yhUserBloodfat.setHdl(heightLipoprotein);// 高密度蛋白
            yhUserBloodfat.setTring(triglycerides);// 甘油三脂
            yhUserBloodfat.seteUserId(Integer.valueOf(userCode));
            // 执行插入血脂四项数据
            try {
                yHUserBloodfatMapper.insert(yhUserBloodfat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 添加嘉乐一体机体脂数据
     *
     * @param userCode
     * @param json
     */
    private void addJLBloodFat(String userCode, JSONObject json) throws ParseException {
        String type = "一体机";
        String test_hj = "常规测量";
        Integer is_Del = 2;
        float muscleRate = 0;// 体脂肪率(%)
        float fatRate = 0;// 肌肉率
        float boneMass = 0;// 心率
        String entry_Time = json.getString("createTime");
        if (json.getFloat("muscleRate") == null) {
            return;
        }
        muscleRate = json.getFloat("muscleRate");// 肌肉率
        fatRate = json.getFloat("fatRate");// 体脂肪率(%)
        boneMass = json.getFloat("boneMass");// 骨量
        if (entry_Time == null || "".equals(entry_Time)) {
            SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
            entry_Time = dateFm.format(new Date());
        }
        if (muscleRate != 0 || fatRate != 0 || boneMass != 0) {
            //查询此用户最新的测量记录 如果这条数据与之前的测量数据时间一摸一样，精确到秒
            PageHelper.startPage(0, 1);
            List<YHUserBodyFat> yhUserBodyFats = yHUserBodyFatMapper.selectPageByParam(userCode);
            if (null != yhUserBodyFats && yhUserBodyFats.size() > 0) {
                //判断时间
                String oldTime = yhUserBodyFats.get(0).getEntryTime();
                if (oldTime.equals(entry_Time)) return;
            }
            //体脂数据初始化
            YHUserBodyFat yhuserBodyFat = new YHUserBodyFat();
            yhuserBodyFat.setEntryTime(entry_Time);
            yhuserBodyFat.setBones(boneMass);
            yhuserBodyFat.setType(test_hj);
            yhuserBodyFat.setFatRate(fatRate);
            yhuserBodyFat.setMuscle(muscleRate);
            yhuserBodyFat.setIsDel(is_Del);
            yhuserBodyFat.seteUserId(Integer.valueOf(userCode));
            yhuserBodyFat.setSource(2);
            // 执行插入体脂数据
            try {
                yHUserBodyFatMapper.insert(yhuserBodyFat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 血氧饱和度
     * @param userCode
     * @param json
     */
    private void addJLOxygen(String userCode, JSONObject json) {
        String data_Source = "一体机";
        String env = "常规测量";
        Integer is_Del = 2;
        String date = "";
        float oxygen = 0f;
        int heart = 0;
        date = json.getString("createTime"); //时间
        if (json.getString("bloodOxygen") == null) {
            return;
        }
        oxygen = json.getFloatValue("bloodOxygen");  //血氧
        if (json.getInteger("pulseRate") != null) {
            heart = json.getInteger("pulseRate");  //脉率值
        }
        if (heart != 0) {
            //查询此用户最新的测量记录 如果这条数据与之前的测量数据时间一摸一样，精确到秒
            PageHelper.startPage(0, 1);
            List<YHUserOxygen> yhUserOxygens = yHUserOxygenMapper.selectPageByParam(userCode);
            if (null != yhUserOxygens && yhUserOxygens.size() > 0) {
                //判断时间
                String oldTime = yhUserOxygens.get(0).getEntryTime();
                if (oldTime.equals(date)) return;
            }
            YHUserOxygen oxygenAllInOne = new YHUserOxygen(oxygen, heart, date, is_Del, userCode, env, data_Source);
            try {
                yHUserOxygenMapper.insertSelective(oxygenAllInOne);
                System.out.print("oxygen: " + oxygen + " entry_Time: " + date + " is_Del: " + is_Del);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 添加嘉乐身高体重
     *
     * @param userCode
     * @param json
     */
    private void addJLHeightAndWeight(String userCode, JSONObject json) {
        String data_Source = "一体机";
        String env = "常规测量";
        Integer is_Del = 2;
        Float height = 0f;
        Float weight = 0f;
        height = json.getFloat("height");// 身高
        weight = json.getFloat("weight");// 体重
        if (json.getFloat("height") == null) {
            return;
        }
        String entry_Time = json.getString("createTime");
        if (height != 0 || weight != 0) {
            //查询此用户最新的测量记录 如果这条数据与之前的测量数据时间一摸一样，精确到秒
            PageHelper.startPage(0, 1);
            List<YHUserBmi> yhUserBmis = yHUserBmiMapper.selectPageByParam(userCode);
            if (null != yhUserBmis && yhUserBmis.size() > 0) {
                //判断时间
                String oldTime = yhUserBmis.get(0).getEntryTime();
                if (oldTime.equals(entry_Time)) return;
            }
            YHUserBmi wellUserBmi = new YHUserBmi(height, weight, entry_Time,is_Del, Integer.valueOf(userCode), env, data_Source);
            // 执行插入数据
            Integer ok = 0;
            try {
                yHUserBmiMapper.insertSelective(wellUserBmi);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加嘉奖医疗血糖数据
     *
     * @param userCode
     * @param json
     */
    private void addJLBloodSugar(String userCode, JSONObject json) throws ParseException {
        Integer E_User_Id = 0;
        String data_Source = "一体机";
        String env = "常规测量";
        Integer is_Del = 2;
        Float bs = 0f;// 收缩压
        Integer BloodsugarType = 3;
        String entry_Time = json.getString("createTime");
        if(json.getFloat("bloodSugar")==null){
            return;
        }
        bs = json.getFloat("bloodSugar");// 血糖
        if(json.getInteger("bloodSugarType")!=null){
            BloodsugarType = json.getInteger("bloodSugarType");
            if (BloodsugarType == 1) {
                env = "空腹";
            } else if (BloodsugarType == 2) {
                env = "餐后";
            } else if (BloodsugarType == 3) {
                env = "常规测量";
            }
        }else{
            env = "空腹";
        }
        Date date = new Date();
        if (bs != 0) {
            //查询此用户最新的测量记录 如果这条数据与之前的测量数据时间一摸一样，精确到秒
            PageHelper.startPage(0, 1);
            List<YHUserBloodsuger> yhUserBloodsugers = yHUserBloodsugerMapper.selectPageByParam(userCode);
            if (null != yhUserBloodsugers && yhUserBloodsugers.size() > 0) {
                //判断时间
                String oldTime = yhUserBloodsugers.get(0).getEntryTime();
                if (oldTime.equals(entry_Time)) return;
            }
            YHUserBloodsuger uubs = new YHUserBloodsuger();
            uubs.setBs(bs);
            uubs.setIsDel(is_Del);
            uubs.seteUserId(userCode);
            uubs.setEntryTime(entry_Time);
            uubs.setEnv(env);
            uubs.setDataSource(data_Source);
            // 执行插入数据
            Integer ok = 0;
            try {
                ok = yHUserBloodsugerMapper.insertSelective(uubs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 添加嘉乐一体机血压数据
     *
     * @param userCode
     * @param json
     */
    private void addJLBloodPressure(String userCode, JSONObject json) throws ParseException {
        String type = "一体机";
        String test_hj = "常规测量";
        Integer is_Del = 2;
        Integer sbp = 0;// 收缩压
        Integer dbp = 0;// 舒张压
        Integer pulse = 0;// 心率
        String entry_Time = json.getString("createTime");
        sbp = json.getInteger("highPressure");// 收缩压
        dbp = json.getInteger("lowPressure");// 舒张压
        if (json.getInteger("highPressure") == null) {
            return;
        }
        pulse = json.getInteger("pulse");// 心率
        if (entry_Time == null || "".equals(entry_Time)) {
            SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
            entry_Time = dateFm.format(new Date());
        }
        if (sbp != 0 || dbp != 0) {
            //查询此用户最新的测量记录 如果这条数据与之前的测量数据时间一摸一样，精确到秒
            PageHelper.startPage(0, 1);
            List<YHUserBloodPressure> yhUserBloodPressures = yHUserBloodPressureMapper.selectPageByParam(userCode);
            if (null != yhUserBloodPressures && yhUserBloodPressures.size() > 0) {
                //判断时间
                String oldTime = yhUserBloodPressures.get(0).getEntryTime();
                if (oldTime.equals(entry_Time)) return;
            }
            YHUserBloodPressure wellUserBloodpressure = new YHUserBloodPressure(sbp, dbp, entry_Time, is_Del, userCode,pulse, test_hj, type);
            // 执行插入数据
            try {
                yHUserBloodPressureMapper.insertSelective(wellUserBloodpressure);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加加乐K5心电图
     *
     * @param userCode
     * @param json
     * @throws Exception
     */
    private void addJLPEecg(String userCode, JSONObject json) throws Exception {
        Integer E_User_Id = 0;
        String data_Source = "一体机";
        String env = "常规测量";
        Integer is_Del = 2;
        Integer pulse = 0;
        Integer resultCode = 0;
        String resurt_text = "";
        Integer tr = 0;
        Integer sr = 0;
        String entry_Time = json.getString("createTime");
        String xd_Value = "";
        String pig_path = "";
        resurt_text = json.getString("ecgResult");
        if (json.getString("ecgImgPath") == null) {
            return;
        }
        String ecgImgPath = json.getString("ecgImgPath");
        String[] strArr = ecgImgPath.split("base64,");
        byte[] image2byte = generateImage(strArr[1]);
        String key = UUID.randomUUID().toString().replaceAll("\\-", "");
        String url = put64image(image2byte, key);
        System.out.println(url + "====--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=url-=-=-=-=-=-=");
        if (StringUtils.isNotBlank(url)) { // 不为空
            pig_path = url;
        } else {
            return;
        }
        pulse = json.getInteger("ecgRate");
        //查询此用户最新的测量记录 如果这条数据与之前的测量数据时间一摸一样，精确到秒
        PageHelper.startPage(0, 1);
        List<YHUserEcg> yhUserEcgs = yHUserEcgMapper.selectPageByParam(userCode);
        if (null != yhUserEcgs && yhUserEcgs.size() > 0) {
            //判断时间
            String oldTime = yhUserEcgs.get(0).getEntryTime();
            if (oldTime.equals(entry_Time)) return;
        }
        YHUserEcg wellUserPEecg = new YHUserEcg(tr, sr, entry_Time, is_Del, Integer.valueOf(userCode), pulse, env, data_Source,
                resultCode, xd_Value, pig_path, resurt_text);
        // 执行插入数据
        Integer ok = 0;
        try {
            yHUserEcgMapper.insertSelective(wellUserPEecg);
            //System.out.print("imageurl: "+imageurl+" resurt_text: "+resurt_text+" entry_Time: "+entry_Time + " is_Del: "+is_Del);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * base64转MultipartFile文件
     *
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[0]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    //base64方式上传
    public String put64image(byte[] base64, String key1) throws Exception {
        YhSysConfig conf = yhSysConfigService.showConfig();
        //先把String对象转换成json对象
        net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(conf.getValue());
        String file64 = Base64.encodeToString(base64, 0);
        String img = "";

        //MultipartFile转换File完整版
        InputStream ins = base64ToMultipart(file64).getInputStream();
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.autoZone());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = (String) object.get("qiniuAccessKey");
        String secretKey = (String) object.get("qiniuSecretKey");
        String bucket = (String) object.get("qiniuBucketName");
//		//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = UUID.randomUUID().toString().replaceAll("\\-", "");
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(ins, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                if (base64ToMultipart(file64).getSize() == 0) {
                } else {
                    img = (String) object.get("qiniuDomain") + "/" + putRet.key;
                    System.out.println((String) object.get("qiniuDomain") + "/" + putRet.key);
                }

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
            }
        } catch (Exception ex) {
            return null;
        }
        return img;
    }

    public byte[] generateImage(String imgStr) { // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {
            // 图像数据为空
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 从JSON中获取UserCode
     *
     * @return
     */
    private String getLilinUserCode(JSONObject json) {
        String userCode = "";
        if (json == null) {
            return "";
        }
        JSONObject memeberObj = json.getJSONObject("user");
        if (memeberObj != null) {
            String mobile = (String) memeberObj.get("phone");// 电话
            if (org.apache.commons.lang.StringUtils.isNotBlank(mobile)) { // 不为空
                userCode = yhUserReginfoMapper.findByMoblie(mobile);
            }
        }
        return userCode;
    }


}
