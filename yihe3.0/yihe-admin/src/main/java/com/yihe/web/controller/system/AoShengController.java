package com.yihe.web.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.icu.util.Calendar;
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
import com.yihe.web.controller.tool.BizPackage;
import com.yihe.web.controller.tool.Router;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: 贺平
 * @Date: 2021/10/28
 * @ClassName: com.yihe.web.controller.system
 * @Description:
 **/
@Controller
public class AoShengController {

    @Autowired
    protected YhUserDeviceMapper yhUserDeviceMapper;

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
    protected YHUserUbaMapper yHUserUbaMapper;

    @Autowired
    protected YHUserTemperatureMapper yHUserTemperatureMapper;

    @Autowired
    protected YHUserOxygenMapper yHUserOxygenMapper;

    @Autowired
    protected YHUserBmiMapper yHUserBmiMapper;

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
    private YhSysConfigService yhSysConfigService;

    @Autowired
    YhTaskServiceAPIService yhtaskservuceapiservice;

    @Autowired
    YhUserEnrolinfoMapper yhUserEnrolinfoMapper;

    @Autowired
    YhUserPersonageConditionMapper yhUserPersonageConditionMapper;


    @Autowired
    YhOrgTreeMapper yhOrgTreeMapper;

    @Autowired
    YHUserHeartRateMapper yHUserHeartRateMapper;


    @Autowired
    YhHeThresholdService yhHeThresholdService;

    @Autowired
    YhOrgFacilityMapper yhOrgFacilityMapper;

    @Autowired
    private YhOrgTreeService YhOrgTreeService;


    @Autowired
    YhUserArteryMapper YhUserArteryMapper;

    @Autowired
    private RedisTemplate<String, String> stringTemplate;



    /****
     * 奥生设备数据上传
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/aoSheng/getBodyCheckData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String healthQuestionRedisData(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, ParseException {
        String jsonString=null;
        jsonString = request.getParameter("jsonHealthData");
//        // 获取request传输过来的字符流
//        BufferedReader bf;
//        try {
//            request.setCharacterEncoding("UTF-8");
//            bf = request.getReader();
//            StringBuilder sb = new StringBuilder();
//            String line;
//            // 循环读取字符流所有字符
//            while ((line = bf.readLine()) != null) {
//                sb.append(line);
//            }
//            jsonString = sb.toString();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        System.out.println(jsonString+"sourceInfo");
        org.json.JSONObject jObject2 = new org.json.JSONObject(jsonString);
        System.out.println(jObject2+"jObject2");
        BizPackage routerPack = Router.parseBizRouterInfo(jObject2);
        int commandId = routerPack.getCommandId();
        System.out.println("sourceInfo" + commandId + "=" + jsonString);
        String responseJsonString = "{\"result\":{\"resultInfo\":1},\"handleType\":1,\"commandId\":"+commandId+"}";
        try {
            switch (commandId) {
                //血压
                case 30:
                    Integer is_Del = 2;
                    String type = "奥生";
                    String test_hj = "常规测量";

                    JsonParser parser = new JsonParser();
                    JsonObject json = (JsonObject) parser.parse(jsonString);
                    JsonArray array = json.get("date").getAsJsonArray();
                    JsonObject jObjectAll30 = array.get(0).getAsJsonObject();


                    Integer dbp = jObjectAll30.get("dbp").getAsInt();
                    Integer sbp =  jObjectAll30.get("sbp").getAsInt();
                    Integer pulse = jObjectAll30.get("pulse").getAsInt();

                    String time = jObjectAll30.get("time").getAsString();
                    Date date = new Date(Long.parseLong(time));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String entry_Time = sdf.format(date);
                    String cardidentity = jObjectAll30.get("idCard").getAsString();
                    YhUserReginfo yhUserReginfo = yhUserReginfoMapper.selectUserCodeByIdCard(cardidentity);
                    String userCode1 = yhUserReginfo.getUserCode();


                    YHUserBloodPressure wellUserBloodpressure = new YHUserBloodPressure(sbp, dbp, entry_Time, is_Del, userCode1,
                            pulse, test_hj, type);
                    // 执行插入数据
                    try {
                        int i = yHUserBloodPressureMapper.insertSelective(wellUserBloodpressure);
                        System.out.println(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    break;

                //体温
                case 32:
                    String data_Source = "";
                    String env = "常规";
                    Integer is_Del1 = 2;
                    String bodyPart = "耳温";

                    JsonParser parser1 = new JsonParser();
                    JsonObject json1 = (JsonObject) parser1.parse(jsonString);
                    JsonArray array1 = json1.get("date").getAsJsonArray();
                    JsonObject jObjectAll32 = array1.get(0).getAsJsonObject();
                    Float earWd =  jObjectAll32.get("temp").getAsFloat(); // 温度

                    String time2 = jObjectAll32.get("time").getAsString();
                    Date date2 = new Date(Long.parseLong(time2));
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String entry_Time2 = sdf2.format(date2);
                    String cardidentity2 = jObjectAll32.get("idCard").getAsString();
                    YhUserReginfo yhUserReginfo2 = yhUserReginfoMapper.selectUserCodeByIdCard(cardidentity2);
                    String userCode2 = yhUserReginfo2.getUserCode();

                    YHUserTemperature temperatureAllInOne = new YHUserTemperature();
                    temperatureAllInOne.setTemperature(earWd);
                    temperatureAllInOne.setEntryTime(entry_Time2);
                    temperatureAllInOne.setIsDel(is_Del1);
                    temperatureAllInOne.seteUserId(userCode2);
                    temperatureAllInOne.setDataSource(env);

                    try {
                        yHUserTemperatureMapper.insertSelective(temperatureAllInOne);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                //身高，体重，体脂
                case 34:
                    String data_Source9 = "奥生";
                    String env10 = "随机";
                    String env9 = "常规";
                    Integer is_Del10 = 2;

                    JsonParser parser10 = new JsonParser();
                    JsonObject json10 = (JsonObject) parser10.parse(jsonString);
                    JsonArray array10 = json10.get("date").getAsJsonArray();
                    JsonObject jObjectAll34 = array10.get(0).getAsJsonObject();

                    Float height =  jObjectAll34.get("height").getAsFloat(); // 身高
                    Float weight =  jObjectAll34.get("weight").getAsFloat(); // 体重

                    Float bmi = jObjectAll34.get("bmi").getAsFloat();  //体脂
                    Float fat = jObjectAll34.get("fat").getAsFloat();  //脂肪率
                    Float metabolishBaseValue = jObjectAll34.get("metabolishBaseValue").getAsFloat();  //基础代谢

                    String time9 = jObjectAll34.get("time").getAsString();
                    Date date9= new Date(Long.parseLong(time9));
                    SimpleDateFormat sdf9 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String entry_Time9= sdf9.format(date9);
                    String cardidentity9 = jObjectAll34.get("idCard").getAsString();
                    YhUserReginfo yhUserReginfo9 = yhUserReginfoMapper.selectUserCodeByIdCard(cardidentity9);
                    String userCode9 = yhUserReginfo9.getUserCode();

                    if (height != null || weight != null){
                        YHUserBmi wellUserBmi = new YHUserBmi();
                        wellUserBmi.setuHeight(height);
                        wellUserBmi.setuWeight(weight);
                        wellUserBmi.setEntryTime(entry_Time9);
                        wellUserBmi.setIsDel(is_Del10);
                        wellUserBmi.seteUserId(Integer.valueOf(userCode9));
                        wellUserBmi.setEnv(env10);
                        wellUserBmi.setDataSource(data_Source9);

                        // 执行身高体重的数据插入
                        try {
                            yHUserBmiMapper.insertSelective(wellUserBmi);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if(bmi != null || fat!=null || metabolishBaseValue!= null){
                        YHUserBodyFat wellUserBodyFat = new YHUserBodyFat();
                        wellUserBodyFat.setFatRate(bmi);  //体脂
                        wellUserBodyFat.setBodyfatscale(fat);  //脂肪重量
                        wellUserBodyFat.setBasalMet(metabolishBaseValue);  //基础代谢
                        wellUserBodyFat.setType(data_Source9);
                        wellUserBodyFat.setIsDel(is_Del10);
                        wellUserBodyFat.seteUserId(Integer.valueOf(userCode9));
                        wellUserBodyFat.setEntryTime(entry_Time9);

                        yHUserBodyFatMapper.insertSelective(wellUserBodyFat);
                    }

                    break;

                //心电()
                case 36:
                    String data_Source10 = "奥生";
                    String env1 = "常规";
                    Integer is_Del2 = 2;
                    Integer resultCode = 0;
                    String resurt_text = "";
                    Integer tr = 0;
                    Integer sr = 0;
                    String ecg_img = "";

                    JsonParser parser2 = new JsonParser();
                    JsonObject json2 = (JsonObject) parser2.parse(jsonString);
                    JsonArray array2 = json2.get("date").getAsJsonArray();
                    JsonObject jObjectAll36 = array2.get(0).getAsJsonObject();

                    String time3 = jObjectAll36.get("time").getAsString();
                    Date date3 = new Date(Long.parseLong(time3));
                    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String entry_Time3 = sdf3.format(date3);
                    String cardidentity3 = jObjectAll36.get("idCard").getAsString();
                    YhUserReginfo yhUserReginfo3 = yhUserReginfoMapper.selectUserCodeByIdCard(cardidentity3);
                    String userCode3 = yhUserReginfo3.getUserCode();

                    String xd_Value = "";
                    String pig_path = "";
                    Integer pulse1 = jObjectAll36.get("rhythm").getAsInt();

                    ecg_img = jObjectAll36.get("xinDianImage").getAsString(); // 转化成base64
                    byte[] image2byte = generateImage(ecg_img);
                    String key = UUID.randomUUID().toString().replaceAll("\\-", "");
                    String url = put64image(image2byte, key, request);
                    if (StringUtils.isNotBlank(url)) { // 不为空
                        pig_path = url;
                    }


                    YHUserEcg wellUserPEecg = new YHUserEcg(tr, sr, entry_Time3, is_Del2, Integer.valueOf(userCode3), pulse1, env1, data_Source10,
                            resultCode, xd_Value, pig_path, resurt_text);

                    // 执行插入数据
                    Integer ok = 0;
                    try {
                        yHUserEcgMapper.insertSelective(wellUserPEecg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                //血氧
                case 38:
                    Integer E_User_Id = 0;
                    String data_Source3 = "奥生";
                    String env3 = "常规";
                    Integer is_Del3 = 2;
                    int heart = 0;


                    JsonParser parser3 = new JsonParser();
                    JsonObject json3 = (JsonObject) parser3.parse(jsonString);
                    JsonArray array3 = json3.get("date").getAsJsonArray();
                    JsonObject jObjectAll38 = array3.get(0).getAsJsonObject();
                    Float oxygen = jObjectAll38.get("bp").getAsFloat();   // 血氧

                    String time4 = jObjectAll38.get("time").getAsString();
                    Date date4 = new Date(Long.parseLong(time4));
                    SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String entry_Time4 = sdf4.format(date4);
                    String cardidentity4 = jObjectAll38.get("idCard").getAsString();
                    YhUserReginfo yhUserReginfo4 = yhUserReginfoMapper.selectUserCodeByIdCard(cardidentity4);
                    String userCode8 = yhUserReginfo4.getUserCode();

                    YHUserOxygen oxygenAllInOne = new YHUserOxygen(oxygen, heart, entry_Time4, is_Del3, userCode8, env3, data_Source3);
                    try {
                        yHUserOxygenMapper.insertSelective(oxygenAllInOne);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                //尿液
                case 40:
                    String type4 = "奥生";
                    String test_hj4 = "常规";

                    JsonParser parser4 = new JsonParser();
                    JsonObject json4 = (JsonObject) parser4.parse(jsonString);
                    JsonArray array4 = json4.get("date").getAsJsonArray();
                    JsonObject jObjectAll40 = array4.get(0).getAsJsonObject();

                    if (jObjectAll40 != null) {

                        String bld = "";
                        Integer BLD = jObjectAll40.get("bld").getAsInt();// 潜血
                        if (BLD == 0) {
                            bld = "-";
                        } else if (BLD == 1) {
                            bld = "+-";
                        } else {
                            bld = "+" + (BLD - 1);
                        }

                        String bil = "";
                        Integer BIL = jObjectAll40.get("bil").getAsInt();// 胆红素
                        if (BIL == 0) {
                            bil = "-";
                        } else {
                            bil = "+" + BIL;
                        }

                        String ket = "";
                        Integer KET = jObjectAll40.get("ket").getAsInt();// 酮体
                        if (KET == 0) {
                            ket = "-";
                        } else if (KET == 1) {
                            ket = "+-";
                        } else {
                            ket = "+" + (KET - 1);
                        }

                        String leu = "";
                        Integer LEU = jObjectAll40.get("leu").getAsInt();// 白细胞
                        if (LEU == 0) {
                            leu = "-";
                        } else if (LEU == 1) {
                            leu = "+-";
                        } else {
                            leu = "+" + (LEU - 1);
                        }

                        String glu = "";
                        Integer GLU = jObjectAll40.get("glu").getAsInt();// 葡萄糖
                        if (GLU == 0) {
                            glu = "-";
                        } else if (GLU == 1) {
                            glu = "+-";
                        } else {
                            glu = "+" + (GLU - 1);
                        }

                        String pro = "";
                        Integer PRO = jObjectAll40.get("pro").getAsInt();// 蛋白质
                        if (PRO == 0) {
                            pro = "-";
                        } else if (PRO == 1) {
                            pro = "+-";
                        } else {
                            pro = "+" + (PRO - 1);
                        }

                        String ph = "";
                        Integer PH = jObjectAll40.get("ph").getAsInt();// 酸碱度
                        if (PH == 0) {
                            ph = "5.0";
                        } else if (PH == 1) {
                            ph = "6.0";
                        } else {
                            ph = String.valueOf((PH - 1) * 0.5 + 6.0);
                        }

                        String nit = "";
                        Integer NIT = jObjectAll40.get("nit").getAsInt();// 亚硝酸盐
                        if (NIT == 0) {
                            nit = "-";
                        } else {
                            nit = "+";
                        }

                        Integer SG = jObjectAll40.get("sg").getAsInt();// 比重
                        String sg = String.valueOf(SG * 0.005 + 1);

                        String vc = "";
                        Integer VC = jObjectAll40.get("vc").getAsInt();// 维生素
                        if (VC == 0) {
                            vc = "-";
                        } else if (VC == 1) {
                            vc = "+-";
                        } else {
                            vc = "+" + (VC - 1);
                        }

                        String time5 = jObjectAll40.get("time").getAsString();
                        Date date5 = new Date(Long.parseLong(time5));
                        SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String entry_Time5 = sdf5.format(date5);
                        String cardidentity5 = jObjectAll40.get("idCard").getAsString();
                        YhUserReginfo yhUserReginfo5 = yhUserReginfoMapper.selectUserCodeByIdCard(cardidentity5);
                        String userCode5 = yhUserReginfo5.getUserCode();

                        YHUserUba wellUserUba = new YHUserUba();
                        wellUserUba.setEnv(test_hj4);
                        wellUserUba.setAdddate(entry_Time5);
                        wellUserUba.setGlucose(glu);
                        wellUserUba.setBilirubin(bil);
                        wellUserUba.setKetone(ket);
                        wellUserUba.setProportion(sg);
                        wellUserUba.setRedbloodcell(bld);
                        wellUserUba.setPh(ph);
                        wellUserUba.setUrineprotein(pro);
                        wellUserUba.setNitrite(nit);
                        wellUserUba.setWhitebloodcell(leu);
                        wellUserUba.seteUserId(Integer.valueOf(userCode5));
                        wellUserUba.setDataSource(type4);
                        wellUserUba.setVitamin(vc);

                        yHUserUbaMapper.insertSelective(wellUserUba);

                    }

                    break;

                //血糖
                case 42:
                    String data_Source4 = "奥生";

                    JsonParser parser5 = new JsonParser();
                    JsonObject json5= (JsonObject) parser5.parse(jsonString);
                    JsonArray array5 = json5.get("date").getAsJsonArray();
                    JsonObject jObjectAll42 = array5.get(0).getAsJsonObject();

                    Object BloodsugarType = jObjectAll42.get("beforeafter");
                    Float bs = jObjectAll42.get("glu").getAsFloat();
                    if (BloodsugarType.equals("前")) {
                        env = "空腹";
                    } else {
                        env = "餐后";
                    }

                    String time6 = jObjectAll42.get("time").getAsString();
                    Date date6 = new Date(Long.parseLong(time6));
                    SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String entry_Time6 = sdf6.format(date6);
                    String cardidentity6 = jObjectAll42.get("idCard").getAsString();
                    YhUserReginfo yhUserReginfo6 = yhUserReginfoMapper.selectUserCodeByIdCard(cardidentity6);
                    String userCode6 = yhUserReginfo6.getUserCode();


                    YHUserBloodsuger uubs = new YHUserBloodsuger();
                    uubs.setBs(bs);
                    uubs.setIsDel(Integer.valueOf(2));
                    uubs.seteUserId(userCode6);
                    uubs.setEntryTime(entry_Time6);
                    uubs.setEnv(env);
                    uubs.setDataSource(data_Source4);

                    // 执行插入数据
                    try {
                         yHUserBloodsugerMapper.insertSelective(uubs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                //血脂
                case 44:
                    String data_Source5 = "奥生";
                    JsonParser parser6 = new JsonParser();
                    JsonObject json6= (JsonObject) parser6.parse(jsonString);
                    JsonArray array6 = json6.get("date").getAsJsonArray();
                    JsonObject jObjectAll44 = array6.get(0).getAsJsonObject();
                    YHUserBloodfat uubf = new YHUserBloodfat();
                    String chol = jObjectAll44.get("tcHdl").getAsString();   //总胆固醇
                    String trig =  jObjectAll44.get("trig").getAsString();  //甘油三脂
                    String hdlChol = jObjectAll44.get("hdlchol").getAsString();   //高密度蛋白
                    String calcLdl = jObjectAll44.get("calcldl").getAsString();  //低密度蛋白

                    String time7 = jObjectAll44.get("time").getAsString();
                    Date date7 = new Date(Long.parseLong(time7));
                    SimpleDateFormat sdf7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String entry_Time7 = sdf7.format(date7);
                    String cardidentity7= jObjectAll44.get("idCard").getAsString();
                    YhUserReginfo yhUserReginfo7 = yhUserReginfoMapper.selectUserCodeByIdCard(cardidentity7);
                    String userCode7= yhUserReginfo7.getUserCode();

                    uubf.setCholesterin(Float.parseFloat(chol));
                    if (!trig.equals("")) {
                        uubf.setTring(Float.parseFloat(trig));
                    } else {
                        uubf.setTring(0f);
                    }
                    if (!hdlChol.equals("")) {
                        uubf.setHdl(Float.parseFloat(hdlChol));
                    } else {
                        uubf.setHdl(0f);
                    }
                    if (!calcLdl.equals("")) {
                        uubf.setLdl(Float.parseFloat(calcLdl));
                    } else {
                        uubf.setLdl(0f);
                    }
                    uubf.setDataSource(data_Source5);
                    uubf.setIsDel(2);
                    uubf.seteUserId(Integer.valueOf(userCode7));
                    uubf.setEntryTime(entry_Time7);
                    yHUserBloodfatMapper.insertSelective(uubf);
                    break;

                default:
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJsonString;
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
    public String put64image(byte[] base64, String key1, HttpServletRequest request) throws Exception {
        YhSysConfig conf = yhSysConfigService.showConfig();
        String file64 = Base64.encodeToString(base64, 0);
        MultipartFile file = base64ToMultipart(file64);
        if (conf == null) {//等于0调用本地上传图片
            String dicName = "UpFile";//服务器上传文件夹名称
            String newFileName = file.getOriginalFilename();
            newFileName = newFileName.substring(0, newFileName.lastIndexOf("."));
            String picurl = saveFile(file, newFileName, dicName, request);
            return picurl;
        } else {
            //先把String对象转换成json对象
            net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(conf.getValue());


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
                        //news.setImg("");
                    } else {
                        img = (String) object.get("qiniuDomain") + "/" + putRet.key;
                        System.out.println((String) object.get("qiniuDomain") + "/" + putRet.key);

                    }

                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                }
                //sysuser=1;
            } catch (Exception ex) {
                //sysuser=0;
                //ignore
                return null;
            }
            return img;

        }
    }

    public static String saveFile(MultipartFile filedata, String newFileName, String dicName, HttpServletRequest request) {
        // TODO Auto-generated method stub

        //获取Tomcat服务器所在的路径
        String pic_path;
        String tomcat_path = System.getProperty("catalina.home");
        System.out.println(tomcat_path);
        //获取Tomcat服务器所在路径的最后一个文件目录
        String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("/") + 1, tomcat_path.length());
        System.out.println(bin_path + "--------------获取Tomcat服务器所在路径的最后一个文件目录");

        //若最后一个文件目录为bin目录，则服务器为手动启动
        if (("bin").equals(bin_path)) {//手动启动Tomcat时获取路径为：D:\Software\Tomcat-8.5\bin
            //获取保存上传图片的文件路径
            pic_path = tomcat_path.substring(0, System.getProperty("catalina.home").lastIndexOf("/")) + "/webapps/";
        } else {//服务中自启动Tomcat时获取路径为：D:\Software\Tomcat-8.5
            pic_path = tomcat_path + "/webapps/";
        }


        Calendar cale = null;
        cale = Calendar.getInstance();
        String year = String.valueOf(cale.get(Calendar.YEAR));
        String month = String.valueOf(cale.get(Calendar.MONTH) + 1);
        String saveFilePath = dicName + "/" + year + "/" + month + "/";
        /* 构建文件目录 */
        File fileDir = new File(pic_path + saveFilePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        // 上传的文件名
        String filename = filedata.getOriginalFilename();
        // 文件的扩张名
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String imgPath = saveFilePath + uuid + ".gif";

            // String url2=request.getScheme()+"://"+ request.getServerName()+":"+ request.getLocalPort();//+request.getRequestURI();
            String url2 = request.getScheme() + "://" + request.getServerName();//+request.getRequestURI();
            String imgPath1 = url2 + "/" + imgPath;
            // System.out.println(pathval + imgPath);打印图片位置
            FileOutputStream out = new FileOutputStream(pic_path + imgPath);
            // 写入文件
            out.write(filedata.getBytes());
            out.flush();
            out.close();
            return imgPath1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

}
