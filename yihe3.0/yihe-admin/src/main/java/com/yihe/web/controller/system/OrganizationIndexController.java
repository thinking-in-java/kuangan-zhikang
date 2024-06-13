/**
 *
 */
package com.yihe.web.controller.system;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.*;
import com.yihe.system.service.YhAuditCheckService;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 机构首页
 * @author dong
 *
 */
@Controller
@RequestMapping("/OrganizationIndexController")
public class OrganizationIndexController {
    @Autowired
    YhAuditCheckService yhAuditCheckService;

    @Autowired
    YhUserMakeCardMapper yhUserMakeCardMapper;

    @Autowired
    YhUserReginfoMapper yhUserReginfoMapper;

    @Autowired
    YhOrgTreeMapper yhOrgTreeMapper;

    @Autowired
    YhFoodUserDiaryMapper yhFoodUserDiaryMapper;//膳食日记

    @Autowired
    YhSportDiaryMapper yhSportDiaryMapper;//运动日记

    @Autowired
    YhSleepUserDiaryMapper yhSleepUserDiaryMapper;//睡眠日记

    @Autowired
    YhSleepReportMapper yhSleepReportMapper;//睡眠评估

    @Autowired
    YhDiseaseAssessReportMapper yhDiseaseAssessReportMapper;//健康评估

    @Autowired
    YHUserTemperatureMapper yHUserTemperatureMapper;//体温

    @Autowired
    YHUserOxygenMapper yHUserOxygenMapper;//血氧/心率

    @Autowired
    YHUserBmiMapper yHUserBmiMapper;//体重

    @Autowired
    YHUserBloodPressureMapper yHUserBloodPressureMapper;//血压

    @Autowired
    YHUserBloodsugerMapper yHUserBloodsugerMapper;//血糖

    @Autowired
    YHUserBodyFatMapper yHUserBodyFatMapper;//体脂

    @Autowired
    YHUserBloodfatMapper yHUserBloodfatMapper;//血脂四项

    @Autowired
    YHUserRenalfunctionMapper yHUserRenalfunctionMapper;//肾功能

    @Autowired
    YHUserLiverfunctionMapper yHUserLiverfunctionMapper;//肝功能

    @Autowired
    YHUserEcgMapper yHUserEcgMapper;//心电图

    @Autowired
    YHUserUbaMapper yHUserUbaMapper;//尿检

    @Autowired
    YHUserOtherfunctionMapper yHUserOtherfunctionMapper;//其他指标

    @Autowired
    YhPressureEvaluateMapper yhPressureEvaluateMapper;//压力管理

    @Autowired
    YhUserHealthyPlanMapper yhUserHealthyPlanMapper;//用户健康计划

    @Autowired
    YhUserServicestatisticMapper yhUserServicestatisticMapper;//用户服务统计

    @Autowired
    YhDiseaseRiskResultMapper yhDiseaseRiskResultMapper;//改善因素

    @Autowired
    YhDiseaseAssessTotalReportMapper yhDiseaseAssessTotalReportMapper;//评估报告_基本信息

    @Autowired
    YhDiseaseAssessTotalReport2Mapper yhDiseaseAssessTotalReport2Mapper;//评估报告_基本信息

    @Autowired
    private YhOrgTreeService YhOrgTreeService;

    /**
     * @Title: unicodeEncode
     * @Description: unicode编码
     * @param string
     * @return
     */
    public static String unicodeEncode(String string) {
        char[] utfBytes = string.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * @Title: unicodeDecode
     * @Description: unicode解码
     * @param str
     * @return
     */
    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }

    public static void main(String[] args) {
        String resultStr = "{\"now_value\":\"0\",\"isRadar\":2,\"last_value\":\"0\",\"Ideal_value\":\"避免高脂饮食\",\"change_value\":\"健康\"}";
        JsonParser jp = new JsonParser();
        //将json字符串转化成json对象
        JsonObject jo = jp.parse(resultStr).getAsJsonObject();
        //获取message对应的值
        String message = jo.get("Ideal_value").getAsString();
        System.out.println("message：" + message);

    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        //Log.e(null, result);
        return result;
    }

    public String ShowJson(String Data) throws IOException {
        File jsonFile = ResourceUtils.getFile("classpath:" + Data + "");
        String json = FileUtils.readFileToString(jsonFile);
        return json;
    }

    @ApiOperation(value = "用户数据统计")
    @RequestMapping(value = "/Userdatastatistics", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> Userdatastatistics(String orgCode) {
        Map<String, Object> map1 = new HashMap<String, Object>();
        if (StringUtil.isEmpty(orgCode)) {
            Subject subject = SecurityUtils.getSubject();
            YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
            orgCode = yh.getOrgCode();//取到当前用户orgcode
        }
        map1.put("orgCode", "reg.Org_Code like '%" + orgCode + "%'");
        int tre = yhUserReginfoMapper.indexnum(map1);//总人数
        YhOrgTree upmsPermissions = YhOrgTreeService.selectBYCodeByNew(orgCode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nohasnum", (tre - upmsPermissions.getHasnum()));//未评估人数
        map.put("hasnum", upmsPermissions.getHasnum());
        map.put("hasdise", upmsPermissions.getHasdise());
        map.put("highrisk", upmsPermissions.getHighrisk());
        map.put("health", upmsPermissions.getHealth());
        return map;
    }

    @ApiOperation(value = "用户数据统计")
    @RequestMapping(value = "/Userdatastatistics1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> Userdatastatistics1(String orgCode) {
        Map<String, Object> map1 = new HashMap<String, Object>();
        if (StringUtil.isEmpty(orgCode)) {
            Subject subject = SecurityUtils.getSubject();
            YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
            orgCode = yh.getOrgCode();//取到当前用户orgcode
        }
        map1.put("orgCode", "reg.Org_Code like '%" + orgCode + "%'");
        int tre = yhUserReginfoMapper.indexnum(map1);//总人数
        YhOrgTree upmsPermissions = YhOrgTreeService.selectBYCodeByNew(orgCode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nohasnum", (tre - upmsPermissions.getHasnum()));//未评估人数
        map.put("hasnum", upmsPermissions.getHasnum());
        map.put("hasdise", upmsPermissions.getHasdise());
        map.put("highrisk", upmsPermissions.getHighrisk());
        map.put("health", upmsPermissions.getHealth());
        return map;
    }

    @ApiOperation(value = "查询省级数据")
    @RequestMapping(value = "/showprovincial", method = RequestMethod.POST)
    @ResponseBody
    public List<YhOrgTree> showprovincial() {
        /*
         * String json=ShowJson(str); String decode=unicodeDecode(json); byte[] b =
         * decode.getBytes("utf-8");//编码 String sa = new String(b,
         * "utf-8");//解码:用什么字符集编码就用什么字符集解码 System.out.println(sa); JSONObject json_test
         * = JSONObject.fromObject(decode); System.out.println(json_test);
         */
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        List<YhOrgTree> tre = yhOrgTreeMapper.showprovincial(yh.getOrgCode());
        return tre;
    }

    @ApiOperation(value = "查询市级数据")
    @RequestMapping(value = "/showCity", method = RequestMethod.POST)
    @ResponseBody
    public List<YhOrgTree> showCity(String name) {
        if (name.equals("北京") || name.equals("天津") || name.equals("重庆") || name.equals("上海")) {
            List<YhOrgTree> tre = yhOrgTreeMapper.showCounty(name);
            return tre;
        } else {
            List<YhOrgTree> tre = yhOrgTreeMapper.showCity(name);
            return tre;
        }


    }

    @ApiOperation(value = "查询区级数据")
    @RequestMapping(value = "/showCounty", method = RequestMethod.POST)
    @ResponseBody
    public List<YhOrgTree> showCounty(String name) {
        List<YhOrgTree> tre = yhOrgTreeMapper.showCounty(name);
        return tre;
    }

    @ApiOperation(value = "健康检测使用分析")
    @RequestMapping(value = "/selecThealthExamination", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selecThealthExamination() {

        int Temperature = yHUserTemperatureMapper.selectByTemperatureCount();//体温检测总数
        int Oxygen = yHUserOxygenMapper.selectByOxygenCount();//血氧/心率检测总数
        int bmi = yHUserBmiMapper.selectByBmiCount();//体重检测总数
        int Bloodpressure = yHUserBloodPressureMapper.selectByBloodpressureCount();//血压检测总数
        int Bloodsuger = yHUserBloodsugerMapper.selectByBloodsugerCount();//血糖总数
        int BodyFat = yHUserBodyFatMapper.selectByBodyFatCount();//体脂总数
        int Bloodfat = yHUserBloodfatMapper.selectByBloodfatCount();//血脂四项总数
        int Renalfunction = yHUserRenalfunctionMapper.selectByRenalfunctionCount();//肾功能总数
        int Liverfunction = yHUserLiverfunctionMapper.selectByLiverfunctionCount();//肝功能总数
        int Ecg = yHUserEcgMapper.selectByEcgCount();//心电图总数
        int Uba = yHUserUbaMapper.selectByUbaCount();//尿检总数
        int Otherfunction = yHUserOtherfunctionMapper.selectByOtherfunctionCount();//其他指标总数

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Temperature", Temperature);
        map.put("Oxygen", Oxygen);
        map.put("bmi", bmi);
        map.put("Bloodpressure", Bloodpressure);
        map.put("Bloodsuger", Bloodsuger);
        map.put("BodyFat", BodyFat);
        map.put("Bloodfat", Bloodfat);
        map.put("Bloodfat", Bloodfat);
        map.put("Renalfunction", Renalfunction);
        map.put("Liverfunction", Liverfunction);
        map.put("Ecg", Ecg);
        map.put("Uba", Uba);
        map.put("Otherfunction", Otherfunction);
        return map;
    }

    @ApiOperation(value = "查询改善因素")
    @RequestMapping(value = "/selectByImprovability", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectByImprovability(String orgCode) {
        return selectByImprovability1(orgCode);
    }
    /**
     * 判定输入的是否是汉字
     *
     * @param c
     *  被校验的字符
     * @return true代表是汉字
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    /**
     * 校验String是否全是中文
     *
     * @param name
     *  被校验的字符串
     * @return true代表全是汉字
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    @ApiOperation(value = "查询改善因素")
    @RequestMapping(value = "/selectByImprovability1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectByImprovability1(String orgCode) {
        if(StringUtil.isEmpty(orgCode)){
            Subject subject = SecurityUtils.getSubject();
            YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
             orgCode = yh.getOrgCode();//取到当前用户orgcode
        }

        String flag=  checkNameChese(orgCode)?"1":"2";
        Map<String, Long> stringObjectMap = yhDiseaseRiskResultMapper.selectByImprovabilityNew(orgCode, flag);
        Map<String, Long> stringObjectMap2 = yhDiseaseRiskResultMapper.selectByImprovabilityNew2(orgCode, flag);

        Long[] right=new Long[7];
        right[0]=stringObjectMap.get("b4");
        right[1]=stringObjectMap.get("food");
        right[2]= stringObjectMap.get("drink");
        right[3]= stringObjectMap.get("smoke");
        right[4]=stringObjectMap.get("BF");
        right[5]= stringObjectMap.get("sbp");
        right[6]= stringObjectMap.get("BMI");


        Long[] bad=new Long[7];
        bad[0]=stringObjectMap2.get("b4");
        bad[1]=stringObjectMap2.get("food");
        bad[2]=stringObjectMap2.get("drink");
        bad[3]=stringObjectMap2.get("smoke");
        bad[4]=stringObjectMap2.get("BF");
        bad[5]=stringObjectMap2.get("sbp");
        bad[6]=stringObjectMap2.get("BMI");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("right", right);
        map.put("bad", bad);
        return map;
    }

    @ApiOperation(value = "查询疾病内容")
    @RequestMapping(value = "/selectCurrentSituation", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectCurrentSituation(String orgCode) {

        return selectCurrentSituation1(orgCode);
    }


    @ApiOperation(value = "查询疾病内容")
    @RequestMapping(value = "/selectCurrentSituation1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectCurrentSituation1(String orgCode) {
        Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map1.put("orgCode", orgCode);
        map2.put("orgCode", orgCode);
        int[] Situation = new int[11];//高
        int[] Hasbeen = new int[11];//已换
        String[] strList={"冠心病","脑卒中","高血压","糖尿病","痛风","血脂异常","骨质疏松症","肥胖","乳腺癌","肺癌","前列腺"};
        for (int i = 0; i < strList.length; i++) {
            map1.put("illness", "df_names like '%"+strList[i]+"%'");
            map2.put("illness", "sk_names like '%"+strList[i]+"%'");
            Situation[i] = yhDiseaseAssessTotalReport2Mapper.selectIllnessCoronary(map1);//冠心病高
            Hasbeen[i] = yhDiseaseAssessTotalReport2Mapper.selectIllnessCoronary(map2);//冠心病高
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Situation", Situation);//高
        map.put("Hasbeen", Hasbeen);//已换
        return map;
    }


    @ApiOperation(value = "查询评估内容")
    @RequestMapping(value = "/showassess", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> showassess() {

        //int assessSleepSum=yhSleepReportMapper.showSleepAssess();//查询睡眠总评估数
        //int assessDiseaseSum=yhDiseaseAssessReportMapper.showDiseaseAssess();//查询健康总评估数

        List<YhUserServicestatistic> statistic = yhUserServicestatisticMapper.selectervicestatistic();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("qtime", getPastDate(180));
        map1.put("qtime1", getPastDate(0));
        int assesspresentSleep = yhSleepReportMapper.showSleepAssessTian();//查询当天睡眠总评估数
        int assesspresentDisease = yhDiseaseAssessReportMapper.showDiseaseAssessTian();//查询当天健康总评估数


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("assesspresentSleep", assesspresentSleep);
        map.put("assesspresentDisease", assesspresentDisease);
        map.put("statistic", statistic);
        return map;
    }

    /**
     * 大数据中心数据封装
     * @return
     */
    @RequestMapping(value = "/bigDataCenter", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bigDataCenter(String orgCode) {
        if (null == orgCode) return null;
        //封装结果集
        Map<String, Object> result = new HashMap<>();
        //封装查询
        Map<String, Object> showassess = this.showassess();
        Map<String, Object> diary = this.diary();
        Map<String, Object> currentSituation = this.selectCurrentSituation1(orgCode);
        Map<String, Object> improvability = this.selectByImprovability1(orgCode);
        Map<String, Object> userdatastatistics = this.Userdatastatistics1(orgCode);
        Map<String, Object> showfoodcount = this.showfoodcount1(orgCode);

        //封装用户数据统计
        result.put("userData", userdatastatistics);

        //分装本月新增数据
        Map<String, Object> map1 = new HashMap<>();
        map1.put("assesspresentSleep", showassess.get("assesspresentSleep"));     //新增睡眠评估
        map1.put("assesspresentDisease", showassess.get("assesspresentDisease")); //新增风险评估
        map1.put("presentfood", diary.get("presentfood"));     //新增饮食日记
        map1.put("presentSport", diary.get("presentSport"));   //新增运动日记
        map1.put("presentSleep", diary.get("presentSleep"));   //新增睡眠日记
        map1.put("count", showfoodcount.get("count"));         //新增用户
        map1.put("evaluateCount", showfoodcount.get("evaluateCount"));   //新增压力评估
        map1.put("planCount", showfoodcount.get("planCount")); //新增健康计划日记
        map1.put("perso", showfoodcount.get("perso"));         //新增服务任务
        result.put("monthNewData", map1);

        //封装服务统计
        Map<String, Object> map2 = new HashMap<>();
        List<YhUserServicestatistic> statistic1 = (List<YhUserServicestatistic>) showassess.get("statistic");
        map2.put("diet", statistic1.get(0).getRecord());      //饮食
        map2.put("consulting", statistic1.get(1).getRecord());//咨询
        map2.put("sleep", statistic1.get(2).getRecord());     //睡眠
        map2.put("sport", statistic1.get(3).getRecord());     //运动
        map2.put("assessment", statistic1.get(4).getRecord());//评估
        result.put("serviceStatistics", map2);

        //可改善人群和以改善人群
        Map<String, Object> rightMap = new HashMap<>();
        Map<String, Object> badMap = new HashMap<>();
        int[] right = (int[]) improvability.get("right");
        int[] bad = (int[]) improvability.get("bad");
        rightMap.put("mayhigh", right[0]);
        rightMap.put("mayvegetable", right[1]);
        rightMap.put("maydrinking", right[2]);
        rightMap.put("maySmoking", right[3]);
        rightMap.put("mayhyperlipidemia", right[4]);
        rightMap.put("mayblood", right[5]);
        rightMap.put("maybmi", right[6]);
        badMap.put("alreadyhigh", bad[0]);
        badMap.put("alreadyvegetable", bad[1]);
        badMap.put("alreadydrinking", bad[2]);
        badMap.put("alreadySmoking", bad[3]);
        badMap.put("alreadyhyperlipidemia", bad[4]);
        badMap.put("alreadyblood", bad[5]);
        badMap.put("alreadybmi", bad[6]);
        result.put("right", rightMap);
        result.put("bad", badMap);

        //封装用户年龄统计、查询年龄小于30、年龄30到40、年龄40到45、查询年龄45到50、年龄50到55、年龄55到60
        Map<String, Object> age = new HashMap<>();
        int[] ages = (int[]) showfoodcount.get("age");
        age.put("LessThirty", ages[0]);
        age.put("ThirtyForty", ages[1]);
        age.put("FortyFive", ages[2]);
        age.put("FortyFiveFifty", ages[3]);
        age.put("FiftyFive", ages[4]);
        age.put("FiftyFiveSixty", ages[5]);
        age.put("Sixty", ages[6]);
        result.put("ages", age);

        //封装高风险和慢病人群
        Map<String, Object> situationMap = new HashMap<>();
        Map<String, Object> hasbeenMap = new HashMap<>();
        int[] situations = (int[]) currentSituation.get("Situation");
        int[] hasbeens = (int[]) currentSituation.get("Hasbeen");
        situationMap.put("coronary", situations[0]); //冠心病
        situationMap.put("stroke", situations[1]); //脑卒中
        situationMap.put("pressure", situations[2]); //高血压
        situationMap.put("diabetes", situations[3]); //糖尿病
        situationMap.put("gout", situations[4]); //痛风
        situationMap.put("dyslipidemia", situations[5]); //血脂异常
        situationMap.put("osteoporosis", situations[6]); //骨质疏松症
        situationMap.put("pca", situations[7]); //肥胖
        situationMap.put("breast", situations[8]); //乳腺癌
        situationMap.put("lung", situations[9]); //肺癌
        situationMap.put("pca", situations[10]); //前列腺

        hasbeenMap.put("coronary", hasbeens[0]); //冠心病
        hasbeenMap.put("stroke", hasbeens[1]); //脑卒中
        hasbeenMap.put("pressure", hasbeens[2]); //高血压
        hasbeenMap.put("diabetes", hasbeens[3]); //糖尿病
        hasbeenMap.put("gout", hasbeens[4]); //痛风
        hasbeenMap.put("dyslipidemia", hasbeens[5]); //血脂异常
        hasbeenMap.put("osteoporosis", hasbeens[6]); //骨质疏松症
        hasbeenMap.put("pca", hasbeens[7]); //肥胖
        hasbeenMap.put("breast", hasbeens[8]); //乳腺癌
        hasbeenMap.put("lung", hasbeens[9]); //肺癌
        hasbeenMap.put("pca", hasbeens[10]); //前列腺
        result.put("situations", situationMap);
        result.put("hasbeens", hasbeenMap);
        return result;
    }


    @ApiOperation(value = "查询日记内容")
    @RequestMapping(value = "/showDiary", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> diary() {

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("cTime", getPastDate(180));
        map1.put("cTime1", getPastDate(0));


        int presentfood = yhFoodUserDiaryMapper.showFoodTian();//查询当天膳食总记录数
        int presentSport = yhSportDiaryMapper.showSportTian();//查询当天运动总记录数
        int presentSleep = yhSleepUserDiaryMapper.showSleepTian();//查询当天睡眠总记录数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("presentfood", presentfood);
        map.put("presentSport", presentSport);
        map.put("presentSleep", presentSleep);

        return map;
    }

    @ApiOperation(value = "点击查询中间机构内容")
    @RequestMapping(value = "/showMapclick", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> showclick(String orgName) {

        YhOrgTree org = yhOrgTreeMapper.showNameByOrg(orgName);

        YhOrgTree tree = yhOrgTreeMapper.selectByPrimaryKey(org.getOrgCode());//当前机构
        List<YhOrgTree> listtree = yhOrgTreeMapper.showCodeByOrg(org.getOrgCode());//二级机构
        String[] showtree = new String[listtree.size() + 1];
        String[] showlisttree = new String[listtree.size()];
        showtree[0] = "{name:'" + tree.getOrgName() + "',symbolSize: 50}";
        int i = 0;
        for (YhOrgTree yhOrgTree : listtree) {
            i++;
            showtree[i] = "{name:'" + yhOrgTree.getOrgName() + "',category: 0}";
            showlisttree[i - 1] = "{source:'" + tree.getOrgName() + "',target:'" + yhOrgTree.getOrgName() + "',value:'1',}";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        JSONArray array = JSONArray.fromObject(showtree);
        JSONArray listtree1 = JSONArray.fromObject(showlisttree);
        map.put("tree", array);
        map.put("listtree", listtree1);
        return map;
    }


    @ApiOperation(value = "查询中间机构内容")
    @RequestMapping(value = "/showMap", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> show() {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        String orgCode = yh.getOrgCode();//取到当前用户orgcode
        YhOrgTree tree = yhOrgTreeMapper.selectByPrimaryKey(orgCode);//当前机构
        List<YhOrgTree> listtree = yhOrgTreeMapper.showCodeByOrg(orgCode);//二级机构
        String[] showtree = new String[listtree.size() + 1];
        String[] showlisttree = new String[listtree.size()];
        showtree[0] = "{name:'" + tree.getOrgName() + "',symbolSize: 50}";
        int i = 0;
        for (YhOrgTree yhOrgTree : listtree) {
            i++;
            showtree[i] = "{name:'" + yhOrgTree.getOrgName() + "',category: 0}";
            showlisttree[i - 1] = "{source:'" + tree.getOrgName() + "',target:'" + yhOrgTree.getOrgName() + "',value:'1',}";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        JSONArray array = JSONArray.fromObject(showtree);
        JSONArray listtree1 = JSONArray.fromObject(showlisttree);
        map.put("tree", array);
        map.put("listtree", listtree1);
        return map;
    }

    @ApiOperation(value = "查询头部内容")
    @RequestMapping(value = "/showFoodExercise", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> showfoodcount(String orgCode) {
        int food = yhAuditCheckService.showfoodcount();//查询膳食新增申请
        int exercise = yhAuditCheckService.showExercisecount();//查询运动新增申请

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("regTime", getPastDate(180));
        map1.put("regTime1", getPastDate(0));
        int count = yhUserReginfoMapper.showaddCount();//用户增加数据
        int evaluateCount = yhPressureEvaluateMapper.showEvaluateCount();//压力管理新增数据
        int planCount = yhUserHealthyPlanMapper.showPlanCount();//用户健康计划新增数据

        int perso = yhUserReginfoMapper.showPersonnum();

        Map<String, Object> map2 = new HashMap<String, Object>();
        if (orgCode == null || orgCode.equals("")) {
            Subject subject = SecurityUtils.getSubject();
            YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
            orgCode = yh.getOrgCode();//取到当前用户orgcode
            map2.put("orgCode", "reg.Org_Code like '%" + orgCode + "%'");
        } else {
            List<YhOrgTree> tre = yhOrgTreeMapper.indexprovince(orgCode);
            String sql = "";
            int i = 0;
            for (YhOrgTree yhOrgTree : tre) {
                i++;
                if (i == 1) {
                    sql += "reg.Org_Code like '%" + yhOrgTree.getOrgCode() + "%'";
                } else {
                    sql += "or reg.Org_Code like '%" + yhOrgTree.getOrgCode() + "%'";
                }
            }
            map2.put("orgCode", sql);
        }

        int LessThirty = yhUserReginfoMapper.showageLessThirty(map2);//查询年龄小于30
        int ThirtyForty = yhUserReginfoMapper.showageThirtyForty(map2);//查询年龄30到40直接
        int FortyFive = yhUserReginfoMapper.showageFortyFive(map2);//查询年龄40到45
        int FortyFiveFifty = yhUserReginfoMapper.showageFortyFiveFifty(map2);//查询年龄45到50
        int FiftyFive = yhUserReginfoMapper.showageFiftyFive(map2);//查询年龄50到55
        int FiftyFiveSixty = yhUserReginfoMapper.showageFiftyFiveSixty(map2);//查询年龄55到60
        int Sixty = yhUserReginfoMapper.showageSixty(map2);//查询年龄60以上


        int[] age = new int[7];
        age[0] = LessThirty;
        age[1] = ThirtyForty;
        age[2] = FortyFive;
        age[3] = FortyFiveFifty;
        age[4] = FiftyFive;
        age[5] = FiftyFiveSixty;
        age[6] = Sixty;


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("food", food);
        map.put("exercise", exercise);
        map.put("age", age);
        map.put("count", count);
        map.put("evaluateCount", evaluateCount);
        map.put("planCount", planCount);
        map.put("perso", perso);
        return map;
    }


    @ApiOperation(value = "查询头部内容")
    @RequestMapping(value = "/showFoodExercise1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> showfoodcount1(String orgCode) {
        int food = yhAuditCheckService.showfoodcount();//查询膳食新增申请
        int exercise = yhAuditCheckService.showExercisecount();//查询运动新增申请

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("regTime", getPastDate(180));
        map1.put("regTime1", getPastDate(0));
        int count = yhUserReginfoMapper.showaddCount();//用户增加数据
        int evaluateCount = yhPressureEvaluateMapper.showEvaluateCount();//压力管理新增数据
        int planCount = yhUserHealthyPlanMapper.showPlanCount();//用户健康计划新增数据

        int perso = yhUserReginfoMapper.showPersonnum();

        Map<String, Object> map2 = new HashMap<String, Object>();
        String sql = "reg.Org_Code like '%" + orgCode + "%'";
        map2.put("orgCode", sql);

        int LessThirty = yhUserReginfoMapper.showageLessThirty(map2);//查询年龄小于30
        int ThirtyForty = yhUserReginfoMapper.showageThirtyForty(map2);//查询年龄30到40直接
        int FortyFive = yhUserReginfoMapper.showageFortyFive(map2);//查询年龄40到45
        int FortyFiveFifty = yhUserReginfoMapper.showageFortyFiveFifty(map2);//查询年龄45到50
        int FiftyFive = yhUserReginfoMapper.showageFiftyFive(map2);//查询年龄50到55
        int FiftyFiveSixty = yhUserReginfoMapper.showageFiftyFiveSixty(map2);//查询年龄55到60
        int Sixty = yhUserReginfoMapper.showageSixty(map2);//查询年龄60以上


        int[] age = new int[7];
        age[0] = LessThirty;
        age[1] = ThirtyForty;
        age[2] = FortyFive;
        age[3] = FortyFiveFifty;
        age[4] = FiftyFive;
        age[5] = FiftyFiveSixty;
        age[6] = Sixty;


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("food", food);
        map.put("exercise", exercise);
        map.put("age", age);
        map.put("count", count);
        map.put("evaluateCount", evaluateCount);
        map.put("planCount", planCount);
        map.put("perso", perso);
        return map;
    }

}
