/**
 *
 */
package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ibm.icu.text.SimpleDateFormat;
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
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.service.YhSysConfigService;
import com.yihe.system.service.YhTaskServiceAPIService;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 随访包上传
 * @author dong
 *
 */
@Controller
public class SuiFangBaoMachineController {


    @Autowired
    private RedisTemplate<String, String> stringTemplate;

    @Autowired
    private YhSysConfigService yhSysConfigService;

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
    protected YhUserReginfoMapper yhUserReginfoMapper;

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
    YhTaskServiceAPIService yhtaskservuceapiservice;

    @Autowired
    YhUserPersonageConditionMapper yhUserPersonageConditionMapper;

    @Autowired
    YHUserHeartRateMapper yHUserHeartRateMapper;

    @Autowired
    YhUserHemoglobinMapper yhUserHemoglobinMapper;

    @Autowired
    YhHeThresholdService yhHeThresholdService;


    @ApiOperation(value = "查询用户手机号")
    @RequestMapping(value = "/selectMobile", method = RequestMethod.GET)
    @ResponseBody
    public int selectMobile(String type, String userCode, Double value, String typeName) throws ParseException {
        return 1;
    }


    @RequestMapping(value = "/suifanbaoCheckData")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public String handleGetDatasFromDevice(HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

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
        //获取用户id
        String userCode = getUserCode(json);
        if (userCode != null) {
            //记录机器编码
            String deviceId = "随访包:" + (String) json.getJSONObject("DeviceInfo").get("MachineId");
            // 获取 时间和 编号
            String measureTime = (String) json.getJSONObject("PhysicalData").get("MeasureTime");
            if (measureTime == null || "".equals(measureTime)) {
                SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前系统日期
                measureTime = dateFm.format(new java.util.Date());
            }

            Integer is_Del = 2;
            String test_hj = "常规";

            //尿液分析
            try {
                JSONObject Urinalysis = json.getJSONObject("PhysicalData").getJSONObject("Urinalysis");
                if (Urinalysis != null) {
                    String uro = "";
                    int URO = (int) Urinalysis.get("URO");//尿胆原
                    if (URO == 0) {
                        uro = "-";
                    } else {
                        uro = "+" + URO;
                    }

                    String bld = "";
                    int BLD = (int) Urinalysis.get("BLD");//潜血
                    if (BLD == 0) {
                        bld = "-";
                    } else if (BLD == 1) {
                        bld = "+-";
                    } else {
                        bld = "+" + (BLD - 1);
                    }

                    String bil = "";
                    int BIL = (int) Urinalysis.get("BIL");//胆红素
                    if (BIL == 0) {
                        bil = "-";
                    } else {
                        bil = "+" + BIL;
                    }

                    String ket = "";
                    int KET = (int) Urinalysis.get("KET");//酮体
                    if (KET == 0) {
                        ket = "-";
                    } else if (KET == 1) {
                        ket = "+-";
                    } else {
                        ket = "+" + (KET - 1);
                    }

                    String leu = "";
                    int LEU = (int) Urinalysis.get("LEU");//白细胞
                    if (LEU == 0) {
                        leu = "-";
                    } else if (LEU == 1) {
                        leu = "+-";
                    } else {
                        leu = "+" + (LEU - 1);
                    }

                    String glu = "";
                    int GLU = (int) Urinalysis.get("GLU");//葡萄糖
                    if (GLU == 0) {
                        glu = "-";
                    } else if (GLU == 1) {
                        glu = "+-";
                    } else {
                        glu = "+" + (GLU - 1);
                    }

                    String pro = "";
                    int PRO = (int) Urinalysis.get("PRO");//蛋白质
                    if (PRO == 0) {
                        pro = "-";
                    } else if (PRO == 1) {
                        pro = "+-";
                    } else {
                        pro = "+" + (PRO - 1);
                    }

                    String ph = "";
                    int PH = (int) Urinalysis.get("PH");//酸碱度
                    if (PH == 0) {
                        ph = "5.0";
                    } else if (PH == 1) {
                        ph = "6.0";
                    } else {
                        ph = String.valueOf((PH - 1) * 0.5 + 6.0);
                    }

                    String nit = "";
                    int NIT = (int) Urinalysis.get("NIT");//亚硝酸盐
                    if (NIT == 0) {
                        nit = "-";
                    } else {
                        nit = "+";
                    }


                    int SG = (int) Urinalysis.get("SG");//比重
                    String sg = String.valueOf(SG * 0.005 + 1);

                    String vc = "";
                    int VC = (int) Urinalysis.get("VC");//维生素
                    if (VC == 0) {
                        vc = "-";
                    } else if (VC == 1) {
                        vc = "+-";
                    } else {
                        vc = "+" + (VC - 1);
                    }


                    int MAL = (int) Urinalysis.get("MAL");//微白蛋白
                    int CR = (int) Urinalysis.get("CR");//肌酐
                    int UCA = (int) Urinalysis.get("UCA");//钙离子
                    int Result = (int) Urinalysis.get("Result");//结果
                    //SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    String dateStr = sdf.format(date);
                    //System.out.println(uro+"--"+bld+"--"+bil+"--"+ket+"--"+leu+"--"+glu+"--"+pro+"--"+ph+"--"+nit+"--"+sg+"--"+vc+"--"+MAL+"--"+CR+"--"+UCA+"--"+Result);
                    YHUserUba wellUserUba = new YHUserUba();
                    wellUserUba.setEnv(test_hj);
                    wellUserUba.setAdddate(dateStr);
                    wellUserUba.setGlucose(glu);
                    wellUserUba.setBilirubin(bil);
                    wellUserUba.setKetone(ket);
                    wellUserUba.setProportion(sg);
                    wellUserUba.setRedbloodcell(bld);
                    wellUserUba.setPh(ph);
                    wellUserUba.setUrineprotein(pro);
                    wellUserUba.setUrobilinogen(uro);
                    wellUserUba.setNitrite(nit);
                    wellUserUba.setWhitebloodcell(leu);
                    wellUserUba.seteUserId(Integer.valueOf(userCode));
                    wellUserUba.setDataSource(deviceId);
                    wellUserUba.setVitamin(vc);
                    yHUserUbaMapper.insertSelective(wellUserUba);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //体温
            try {
                JSONObject temperatureObject = json.getJSONObject("PhysicalData").getJSONObject("Temperature");
                if (temperatureObject != null) {
                    String bodyPart = "耳温";
                    String temperature = (String) temperatureObject.get("Temperature");
                    String result = (String) temperatureObject.get("Result");
                    yhHeThresholdService.selectBythresType("temperature", userCode, temperature, "体温", request);
                    YHUserTemperature temperatureAllInOne = new YHUserTemperature();
                    temperatureAllInOne.setTemperature(Float.valueOf(temperature));
                    temperatureAllInOne.setEntryTime(measureTime);
                    temperatureAllInOne.setIsDel(is_Del);
                    temperatureAllInOne.seteUserId(userCode);
                    temperatureAllInOne.setDataSource(deviceId);
                    yHUserTemperatureMapper.insertSelective(temperatureAllInOne);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //血氧
            try {
                JSONObject boObject = json.getJSONObject("PhysicalData").getJSONObject("Bo");

                if (boObject != null) {

                    String oxygen = (String) boObject.get("Oxygen");
                    String bpm = (String) boObject.get("Bpm");
                    String oxygenList = (String) boObject.get("OxygenList");
                    String bpmList = (String) boObject.get("BpmList");
                    String result = (String) boObject.get("Result");
                    YHUserOxygen oxygenAllInOne = new YHUserOxygen(Float.parseFloat(oxygen), Integer.parseInt(bpm), measureTime, is_Del, userCode, test_hj, deviceId);
                    yHUserOxygenMapper.insertSelective(oxygenAllInOne);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //身高体重
            try {
                JSONObject heightObject = json.getJSONObject("PhysicalData").getJSONObject("Height");
                if (heightObject != null) {
                    Float height = Float.parseFloat(heightObject.getString("Height"));// 身高
                    Float weight = Float.parseFloat(heightObject.getString("Weight"));// 体重

                    YHUserBmi wellUserBmi = new YHUserBmi(height, weight, measureTime,
                            is_Del, Integer.valueOf(userCode), test_hj, deviceId);
                    yHUserBmiMapper.insertSelective(wellUserBmi);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //心电图
            try {
                JSONObject pEEcgObject = json.getJSONObject("PhysicalData").getJSONObject("PEEcg");
                Integer resultCode = 0;
                Integer tr = 0;
                Integer sr = 0;
                String xd_Value = "";
                if (pEEcgObject != null) {
                    Integer pulses = pEEcgObject.getInteger("Hr");// 心率
                    String ecg_img = (String) pEEcgObject.get("EcgImg");  //转化成base64
                    String resurt_text = (String) pEEcgObject.get("ResultStr");

                    byte[] image2byte = generateImage(ecg_img);
                    String key = UUID.randomUUID().toString().replaceAll("\\-", "");
                    String imageurl = put64image(image2byte, key, request);
                    //String imageurl = OSSFactory.build().uploadSuffix(image2byte,".jpg");

                    YHUserEcg wellUserPEecg = new YHUserEcg(tr, sr, measureTime,
                            is_Del, Integer.valueOf(userCode), pulses, test_hj, deviceId, resultCode,
                            xd_Value, imageurl, resurt_text);

                    yHUserEcgMapper.insertSelective(wellUserPEecg);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ReviceDataFromPlatemp temp = convertToMyData(json, userCode, request);

            String reponseJsonString = healthQuestionRedisData(temp, userCode);

            return reponseJsonString;
        } else {
            String reponseJsonString = "{\"success\":\"false\",\"message\":\"找不到相关用户\"}";
            return reponseJsonString;
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
    public String put64image(byte[] base64, String key1, HttpServletRequest request) throws Exception {
        YhSysConfig conf = yhSysConfigService.showConfig();
        String file64 = Base64.encodeToString(base64, 0);
        if (conf == null) {//等于0调用本地上传图片
            MultipartFile file = base64ToMultipart(file64);
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

    public String healthQuestionRedisData(
            ReviceDataFromPlatemp reviceDataFromPlatemp, String userCode) throws JsonProcessingException {

        String reponseJsonString = "{\"success\":\"true\",\"message\":\"上传成功\"}";
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
        valueTemplate.set("physical" + userCode, modelToJSONString, 604800, TimeUnit.SECONDS);
        return reponseJsonString;
    }

    /**
     * 从JSON中获取UserCode
     *
     * @return
     * @throws ParseException
     */
    private String getUserCode(JSONObject json) throws ParseException {
        String userCode = "";
        if (json == null) {
            return "";
        }
        JSONObject memeberObj = json.getJSONObject("MemberInfo");
        if (memeberObj != null) {
            String mobile = (String) memeberObj.get("Mobile");// 电话

            String Name = (String) memeberObj.get("Name");// 姓名
            String IdCode = (String) memeberObj.get("IdCode");//身份证
            int Sex = (int) memeberObj.get("Sex");// 性别
            String Address = (String) memeberObj.get("Address");// 身份证地址
            String Birthday = (String) memeberObj.get("Birthday");// 生日
            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(mobile)) { // 不为空
                userCode = yhUserReginfoMapper.findByMoblie(mobile);
            }
        }
        return userCode;
    }


    /***
     * 将必填的10项
     *
     * @param json
     * @param userCode
     * @throws Exception
     */
    private ReviceDataFromPlatemp convertToMyData(JSONObject json, String userCode, HttpServletRequest request) throws Exception {
        ReviceDataFromPlatemp tmp = new ReviceDataFromPlatemp();

        JSONObject memeberObj = json.getJSONObject("MemberInfo");

        //记录机器编码
        String deviceId = "随访包:" + (String) json.getJSONObject("DeviceInfo").get("MachineId");
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        //获取String类型的时间
        String createdate = sdf.format(date);

        if (memeberObj != null) {
            String mobile = (String) memeberObj.get("Mobile");// 电话
            String name = (String) memeberObj.get("Name");// 姓名
            String idCardId = (String) memeberObj.get("IdCode");// 身份证号
            String sex = (String) memeberObj.get("sex");// 身份证号
            tmp.setPhone(mobile);
            tmp.setSex(sex);
            tmp.setUserName(name);
        }

        JSONObject physicalDataObject = (JSONObject) json.get("PhysicalData");  // 人体成分
        String height = "";
        String weight = "";
        String waistline = "";// 腰围
        String hipline = "";// 臀围
        String bloodSugar = "";// 餐前血糖
        String chol = "";// 总胆固醇
        String highPressure = "";// 收缩压
        String lowPressure = "";// 舒张压
        String pulse = "";// 心率
        String oxygen = "";// 血氧
        String hb = "";// 糖化血红蛋白
        String ua = "";// 血尿酸
        String hdlChol = "";// 高密度蛋白
        String calcLdl = "";// 低密度蛋白
        String trig = "";// 甘油三脂


        if (physicalDataObject != null) {
            JSONObject hwObject = (JSONObject) physicalDataObject.get("Height");// 身高体重
            if (hwObject != null) {
                weight = (String) hwObject.get("Weight");// 体重
                if (null != weight) {
                    boolean matches = weight.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches) {
                        weight = "";
                    }

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
                height = (String) hwObject.get("Height");// 身高
                if (null != height) {
                    boolean matches2 = height.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches2) {
                        height = "";
                    }
                }
                /*YHUserBmi uub = new YHUserBmi();
                uub.setuHeight(Float.valueOf(Float.parseFloat(height)));
                uub.setuWeight(Float.valueOf(Float.parseFloat(weight)));
                uub.setIsDel(Integer.valueOf(2));
                uub.seteUserId(Integer.valueOf(userCode));
                //我要获取当前的日期

                uub.setEntryTime(createdate);//Data.getNowDate()
                uub.setDataSource(deviceId);
                uub.setEnv(deviceId);
                yHUserBmiMapper.insertSelective(uub);*/
            }

            JSONObject whrObj = physicalDataObject.getJSONObject("Whr");
            if (whrObj != null) {
                waistline = (String) whrObj.get("Waistline");// 腰围
                if (null != waistline) {
                    boolean matches = waistline.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches) {
                        waistline = "";
                    }
                }

                hipline = (String) whrObj.get("Hipline");// 臀围
                if (null != hipline) {
                    boolean matches2 = hipline.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches2) {
                        hipline = "";
                    }
                }

                YhUserWaistline wuw = new YhUserWaistline();
                wuw.setWaistline(Float.parseFloat(waistline));
                wuw.setIsDel(2);
                wuw.seteUserId(Integer.valueOf(userCode));
                wuw.setEntryTime(now);
                yhUserWaistlineMapper.insertSelective(wuw);

            }

            JSONObject bloodSugarObj = physicalDataObject.getJSONObject("BloodSugar");
            if (bloodSugarObj != null) {
                bloodSugar = (String) bloodSugarObj.get("BloodSugar");// 餐前血糖
                String BloodsugarType = (String) bloodSugarObj.get("BloodsugarType");
                if (null != bloodSugar) {
                    boolean matches = bloodSugar.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches) {
                        bloodSugar = "";
                    }
                }


                String env = "餐后";
                if (Integer.parseInt(BloodsugarType) == 1) {
                    env = "空腹";
                    if (StringUtil.isNotEmpty(bloodSugar)) {
                        yhHeThresholdService.selectBythresType("blood_sugar", userCode, bloodSugar, "空腹血糖", request);
                    }
                } else if (Integer.parseInt(BloodsugarType) == 2) {
                    env = "餐后";
                    if (StringUtil.isNotEmpty(bloodSugar)) {
                        yhHeThresholdService.selectBythresType("blood_sugar2", userCode, bloodSugar, "餐后血糖", request);
                    }
                } else if (Integer.parseInt(BloodsugarType) == 3) {
                    env = "餐后";
                    if (StringUtil.isNotEmpty(bloodSugar)) {
                        yhHeThresholdService.selectBythresType("blood_sugar2", userCode, bloodSugar, "餐后血糖", request);
                    }
                }

                YHUserBloodsuger uubs = new YHUserBloodsuger();
                uubs.setBs(Float.valueOf(Float.parseFloat(bloodSugar)));
                uubs.setIsDel(Integer.valueOf(2));
                uubs.seteUserId(userCode);
                uubs.setEntryTime(createdate);
                uubs.setEnv(env);
                uubs.setDataSource(deviceId);
                yHUserBloodsugerMapper.insertSelective(uubs);

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
                //Reportanemergency.giveanalarm(userCode, bloodSugar, deviceId, "blood_sugar", "血糖");
            }


            JSONObject cholObj = physicalDataObject.getJSONObject("Chol");
            JSONObject bloodFat = physicalDataObject.getJSONObject("BloodFat");
            YHUserBloodfat uubf = new YHUserBloodfat();
            //BloodfatFunction record=new BloodfatFunction();
            if (bloodFat != null) {
                chol = (String) bloodFat.get("TChol");// 总胆固醇
                if (null != chol) {
                    boolean matches = chol.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches) {
                        chol = "";
                        uubf.setCholesterin(0f);
                        //record.setCholesterol("0");
                    } else {
                        uubf.setCholesterin(Float.parseFloat(chol));
                        //record.setCholesterol(chol);
                    }

                }
                //Reportanemergency.giveanalarm(userCode, chol, deviceId, "tc", "总胆固醇");
            }
            if (StringUtils.isEmpty(chol)) {
                if (cholObj != null) {
                    chol = (String) cholObj.get("Chol");
                    if (null != chol) {
                        boolean matches = chol.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                        if (!matches) {
                            chol = "";
                            uubf.setCholesterin(0f);
                            //	record.setCholesterol("");
                        } else {
                            uubf.setCholesterin(Float.parseFloat(chol));
                            //record.setCholesterol(chol);
                        }
                    }

                }

            }

            if (bloodFat != null) {
                hdlChol = (String) bloodFat.get("HdlChol");// 高密度蛋白
                if (null != hdlChol) {
                    boolean matches = hdlChol.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches) {
                        hdlChol = "";
                        uubf.setHdl(0f);
                        //record.setHdlc("0");
                    } else {
                        uubf.setHdl(Float.parseFloat(hdlChol));
                        //	record.setHdlc(hdlChol);
                        //Reportanemergency.giveanalarm(userCode, hdlChol, deviceId, "hdl-c", "高密度蛋白");
                    }

                }


                calcLdl = (String) bloodFat.get("CalcLdl");// 低密度蛋白
                if (null != calcLdl) {
                    boolean matches2 = calcLdl.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches2) {
                        calcLdl = "";
                        //	record.setLdlc("0");
                        uubf.setLdl(0f);
                    } else {
                        uubf.setLdl(Float.parseFloat(calcLdl));
                        //record.setLdlc(calcLdl);
                        //Reportanemergency.giveanalarm(userCode, calcLdl, deviceId, "ldl-c", "低密度蛋白");
                    }

                }


                trig = (String) bloodFat.get("Trig");// 甘油三脂
                if (null != trig) {
                    boolean matches3 = trig.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches3) {
                        trig = "";
                        //record.setTg("0");
                        uubf.setTring(0f);
                    } else {
                        uubf.setTring(Float.parseFloat(trig));
                        //record.setTg(trig);
                        //Reportanemergency.giveanalarm(userCode, trig, deviceId, "trig", "甘油三脂");
                    }

                }

                try {
                    //record.setUserId(userCode);
                    //bloodfatFunctionServiceImpl.insertSelective(record);
                } catch (Exception e) {
                    // TODO: handle exception
                }


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

                uubf.setIsDel(2);
                uubf.seteUserId(Integer.valueOf(userCode));
                uubf.setEntryTime(createdate);
                uubf.setDataSource(deviceId);
                yHUserBloodfatMapper.insertSelective(uubf);


            }

            JSONObject bloodPressure = physicalDataObject.getJSONObject("BloodPressure");
            if (bloodPressure != null) {
                highPressure = (String) bloodPressure.get("HighPressure");// 收缩压
                if (null != highPressure) {
                    boolean matches = highPressure.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches) {
                        highPressure = "";
                    }
                    //Reportanemergency.giveanalarm(userCode, highPressure, deviceId, "blood_pressure_sys", "收缩压");

                }

                lowPressure = (String) bloodPressure.get("LowPressure");// 舒张压
                if (null != lowPressure) {
                    boolean matches2 = lowPressure.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches2) {
                        lowPressure = "";
                    }


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


                    //Reportanemergency.giveanalarm(userCode, lowPressure, deviceId, "blood_pressure_dia", "舒张压");
                }

                pulse = (String) bloodPressure.get("Pulse");// 心率
                if (null != pulse) {
                    boolean matches3 = pulse.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches3) {
                        pulse = "";
                    }
                    //Reportanemergency.giveanalarm(userCode, pulse, deviceId, "pulse", "心率");
                }


                YHUserHeartRate rate = new YHUserHeartRate();
                rate.setHeartRate(Integer.valueOf(pulse));
                rate.setEntryTime(createdate);
                rate.setIsDel(2);
                rate.setEnv("常规测量");
                rate.setDataSource("随访包录入");
                rate.seteUserId(userCode);
                yHUserHeartRateMapper.insertSelective(rate);

                YHUserBloodPressure uubp = new YHUserBloodPressure();
                uubp.setSbp(Integer.valueOf(Integer.parseInt(highPressure)));
                uubp.setDbp(Integer.valueOf(Integer.parseInt(lowPressure)));
                uubp.setIsDel(Integer.valueOf(2));
                uubp.seteUserId(userCode);
                uubp.setXinlv(Integer.valueOf(pulse));
                uubp.setEntryTime(createdate);
                uubp.setTestHj("常规测量");
                uubp.setType(deviceId);
                yHUserBloodPressureMapper.insertSelective(uubp);
            }

            JSONObject bo = physicalDataObject.getJSONObject("Bo");
            if (bo != null) {
                oxygen = (String) bo.get("Oxygen");// 血氧
                if (null != oxygen) {
                    boolean matches = oxygen.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches) {
                        oxygen = "";
                    }
                }

            }

            JSONObject HBOBJ = physicalDataObject.getJSONObject("Hb");

            if (HBOBJ != null) {
                hb = (String) HBOBJ.get("Hb");// 糖化血红蛋白
                if (hb != null) {
                    boolean matches = hb.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches) {
                        hb = "";
                    }
                }
                YhUserHemoglobin hemog = new YhUserHemoglobin();
                hemog.setUserCode(userCode);
                hemog.setHeHb(Float.valueOf(hb));
                hemog.setHeTyep("随诊包");
                yhUserHemoglobinMapper.insertSelective(hemog);
            }


            JSONObject uaObj = physicalDataObject.getJSONObject("Ua");

            if (uaObj != null) {
                ua = (String) uaObj.get("Ua");// 血尿酸
                if (ua != null) {
                    boolean matches = ua.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
                    if (!matches) {
                        ua = "";
                    }
                }
                if (StringUtil.isNotEmpty(ua)) {
                    double s = Double.valueOf(ua) * 1000;
                    YHUserRenalfunction rf = new YHUserRenalfunction();
                    rf.setUricAcid(String.valueOf(s));
                    rf.setInerttime(createdate);
                    rf.setUserId(Integer.valueOf(userCode));
                    rf.setDataSource("随诊包");
                    yHUserRenalfunctionMapper.insertSelective(rf);
                    System.out.println("随诊包肾功能尿酸insert++");
                }
            }
        }

        if (StringUtil.isNotEmpty(trig)) {
            yhHeThresholdService.selectBythresType("trig", userCode, trig, "甘油三脂", request);
        }
        if (StringUtil.isNotEmpty(calcLdl)) {
            yhHeThresholdService.selectBythresType("ldl-c", userCode, calcLdl, "低密度蛋白", request);
        }
        if (StringUtil.isNotEmpty(hdlChol)) {
            yhHeThresholdService.selectBythresType("hdl-c", userCode, hdlChol, "高密度蛋白", request);
        }
        if (StringUtil.isNotEmpty(ua)) {
            yhHeThresholdService.selectBythresType("blood_uric_acid", userCode, ua, "血尿酸", request);
        }
        if (StringUtil.isNotEmpty(hb)) {
            yhHeThresholdService.selectBythresType("Hba1c_and_protein", userCode, hb, "糖化血红蛋白", request);
        }
        if (StringUtil.isNotEmpty(lowPressure)) {
            yhHeThresholdService.selectBythresType("blood_pressure_dia", userCode, lowPressure, "舒张压", request);
        }
        if (StringUtil.isNotEmpty(highPressure)) {
            yhHeThresholdService.selectBythresType("blood_pressure_sys", userCode, highPressure, "收缩压", request);
        }
        if (StringUtil.isNotEmpty(chol)) {
            yhHeThresholdService.selectBythresType("tc", userCode, chol, "总胆固醇", request);
        }

        if (StringUtil.isNotEmpty(pulse)) {
            yhHeThresholdService.selectBythresType("pulse", userCode, pulse, "心率", request);
        }
        if (StringUtil.isNotEmpty(oxygen)) {
            yhHeThresholdService.selectBythresType("boold_oxygen", userCode, oxygen, "血氧", request);
        }


        YhUserPersonageCondition list = yhUserPersonageConditionMapper.showcondition(userCode);
        YhUserPersonageCondition cond = new YhUserPersonageCondition();
        if (height != null && !height.equals("")) {
            cond.setStature(height);//身高
        }

        if (weight != null && !weight.equals("")) {
            cond.setWeight(weight);//体重
        }

        if (waistline != null && !waistline.equals("")) {
            cond.setWaistline(waistline);//腰围
        }

        if (hipline != null && !hipline.equals("")) {
            cond.setHipline(hipline);//臀围
        }

        if (height.equals("") && weight.equals("") && waistline.equals("") && hipline.equals("")) {
            System.out.println("----");
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

		/*ConList list12 = new ConList();
		list12.setConKey("t_score");
		list12.setConValue(t_score);
		list12.setPos(191);
		conList.add(list12);*/

        ConList list13 = new ConList();
        list13.setConKey("hba1c");
        list13.setConValue(hb);
        list13.setPos(192);
        conList.add(list13);

        ConList list14 = new ConList();
        list14.setConKey("bua");
        list14.setConValue(ua);
        list14.setPos(198);
        conList.add(list14);
        tmp.setConlist(conList);

        return tmp;
    }


    //base64字符串转化成图片
    public String generateImage(String imgStr, String userCode) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {
            //图像数据为空
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }


            Date d_date = new Date();
            Long l_data = d_date.getTime();

            //生成jpeg图片   绝对路径
            String imgFiledir = "E:\\yhy.me\\apache-tomcat-7.0.75\\webapps\\yihe-transfer-web\\egc_pic\\baju\\" + userCode;
            File file1 = new File(imgFiledir);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            String imgFilePath = "E:\\yhy.me\\apache-tomcat-7.0.75\\webapps\\yihe-transfer-web\\egc_pic\\baju\\" + userCode + "\\" + l_data + ".jpg";//新生成的图片路径(保证不重名)
            File file = new File(imgFilePath);
            if (file.exists()) {  //存在了
                return null;
            } else {
                file.createNewFile();//创建这个文件
            }
            OutputStream out = new FileOutputStream(file);
            out.write(b);
            out.flush();
            out.close();
	           
	          /*  String ret = contextPath+"/pages/allinoneimg/"+userCode+"_"+l_data+".jpg";
	            String returnUrl = configInfo.getLocal_url()+ret;*/

            String returnUrl = "http://www.yhy.me/yihe-transfer-web/egc_pic/baju/" + userCode + "/" + l_data + ".jpg";

            return returnUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] generateImage(String imgStr) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {
            //图像数据为空
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
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
