package com.yihe.applet.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.mapper.YhFoodSrecipeApiNewMapper;
import com.yihe.applet.api.mapper.YhSportFrequencyAppMapper;
import com.yihe.applet.api.service.*;
import com.yihe.applet.api.util.common.constant.ApiResult;
import com.yihe.applet.api.util.common.constant.ApiResultConstant;
import com.yihe.applet.api.util.common.util.DateUtil;
import com.yihe.applet.api.util.common.util.UUIDUtil;
import com.yihe.applet.api.util.constans.Constant;
import com.yihe.applet.api.util.request.DefaultYiheClient;
import com.yihe.applet.api.util.request.YiheClient;
import com.yihe.common.config.Global;
import com.yihe.common.utils.*;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhFoodEnergyWebMapper;
import com.yihe.system.mapper.YhTaskServiceAPIMapper;
import com.yihe.system.service.YhTaskServiceAPIService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:问卷控制层
 * Created by lll on 2020/6/22 17:15
 */
@RestController
@RequestMapping("/api/questionAssessTitleFront")
public class YhQuestionAssessTitleFrontAppController {
    //    private static Logger logger= LoggerFactory.getLogger(YhQuestionAssessTitleFrontController.class);
    @Resource
    private YhQuestionAssessTitleFrontAppService yhQuestionAssessTitleAppFrontService;
    @Resource
    private YhDiseaseCategoryAppService yhDiseaseCategoryAppService;
    @Resource
    private YhDiseaseAssessReportAppService yhDiseaseAssessReportAppService;
    @Resource
    private YhDiseaseAssessResolveAppService yhDiseaseAssessResolveAppService;
    @Resource
    private YhDiseaseAssessTotalReportAppService yhDiseaseAssessTotalReportAppService;
    @Resource
    private YhDiseaseRiskResultAppService yhDiseaseRiskResultAppService;
    @Resource
    private YhQuestionCheckJsonAppService yhQuestionCheckJsonAppService;
    @Resource
    private YhFoodSrecipeAppService yhFoodSrecipeAppService;
    @Resource
    private YhFoodSrecipeRecordAppService yhFoodSrecipeRecordAppService;
    @Resource
    private YhSportRecommendRecordAppService yhSportRecommendRecordAppService;
    @Resource
    private YhSportSetmealAppService yhSportSetmealAppService;
    @Resource
    YhTaskServiceAPIService yhtaskservuceapiservice;
    @Autowired
    YhTaskServiceAPIMapper yhtaskserviceapimapper;
    @Autowired
    YhFoodSrecipeApiNewMapper YhFoodSrecipeApiNewMapper;
    @Autowired
    YhFoodEnergyWebMapper YhFoodEnergyWebMapper;
    @Autowired
    YhSportFrequencyAppMapper yhSportFrequencyAppMapper;



    @RequestMapping("/selAllQuestion")
    public Result selAllQuestion(String gender) {
        int sex = Integer.parseInt(gender);
        List<YhQuestionAssessTitleFrontApp> quesList = yhQuestionAssessTitleAppFrontService.selAllQuestion(sex);
        List<YhQuestionAssessTitleFrontApp> fatherQuesList = new ArrayList<>();//父级list
        //给数据加上下标以及显示层级
        for (int i = 0; i < quesList.size(); i++) {
            quesList.get(i).setIndex(i);
            quesList.get(i).setZIndex(quesList.size() - i);
        }
        List<YhQuestionAssessTitleFrontApp> childQuesList = new ArrayList<>();//子级list
        //第一轮筛选，将一级父级题目和一级子题目、二级子题目筛选出来
        for (int i = 0; i < quesList.size(); i++) {
            YhQuestionAssessTitleFrontApp question = quesList.get(i);
            //如果是大题且没有上级题目的
            if (question.getParentId().equals("-1") || question.getParentId().equals("0")) {
                fatherQuesList.add(question);
            } else {
                childQuesList.add(question);
            }
        }
        //第二轮筛选，将子题目赋值进父级题目中去
        for (int i = 0; i < fatherQuesList.size(); i++) {
            //父级题目可能不止一个子级题目
            List<YhQuestionAssessTitleFrontApp> childList = new ArrayList<>();//即将插入父级的子级list
            for (int j = 0; j < childQuesList.size(); j++) {
                YhQuestionAssessTitleFrontApp childQuestion = childQuesList.get(j);
                List<YhQuestionAssessTitleFrontApp> childTwoQuesList = new ArrayList<>();//二级子级list
                String[] parentId = childQuesList.get(j).getParentId().split(",");
                //如果一级子题目中还有子级题目
                if (parentId.length > 1) {
                    for (int a = j; a < childQuesList.size(); a++) {
                        if (Integer.parseInt(childQuesList.get(a).getParentId().split(",")[0]) == childQuestion.getId()) {
                            childTwoQuesList.add(childQuesList.get(a));
                            childQuestion.setChildList(childTwoQuesList);
                        }
                    }
                }
                //将一级子题目插入父题目
                if (fatherQuesList.get(i).getId() == Integer.parseInt(parentId[0])) {
                    childList.add(childQuestion);
                    fatherQuesList.get(i).setChildList(childList);
                }
            }
        }
        return Result.ok(fatherQuesList);
    }

    public Result doDMRisk2(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject queestionData) throws Exception {
        //生成请求不重复流水号
        String serialNumUUID = UUIDUtil.getJavaUUID();
        queestionData.put("serialNum", serialNumUUID);
        JSONObject person = queestionData.getJSONObject("person");
        //体力活动
        String foodactivity = queestionData.getString("activity");
        String userCode = person.getString("id");
        if(userCode.indexOf(Global.getName()) == -1){
            person.put("id", Global.getName()+""+userCode);
        }
        queestionData.put("person", person);
        Integer reportIns=0;//评估分表
        //第一阶段,评估完将queestionData插入暂存表yh_question_check_json
        int checkResult = insertYhQuestionCheckJson(userCode, queestionData);
        //接口路径
        String doAssess_url = Constant.DOASSESS_URL;
        //格林时间戳
        String matTime = DateUtil.getMatTime(new Date());
        //初始化client
        YiheClient yiheClient = new DefaultYiheClient(userCode, Constant.PRIVATEKEY, matTime);
        //根据性别查询疾病分类中的id，将id插入接口得到返回结果
        List<YhDiseaseCategoryApp> categoryList = yhDiseaseCategoryAppService.newFindDis(person.getIntValue("sex"));
        //收集疾病id以及风险等级
        List<Map<String, Object>> levelIdList = new ArrayList<>();
        YhDiseaseRiskResultApp resultApp = new YhDiseaseRiskResultApp();
        for (YhDiseaseCategoryApp app : categoryList) {
            queestionData.put("diseaseid", app.getDiseaseCode());//向json中添加病种
            String result = yiheClient.execute(queestionData, doAssess_url);
            JSONObject fromObject = JSONObject.parseObject(result);
            //获取返回数据
            JSONObject dataObject = fromObject.getJSONObject("data");
            //获取返回结果流水号
            String resultSerialnum = fromObject.getString("sno");
            //查看返回的数据
            JSONObject riskObject = fromObject.getJSONObject("risk");
            //获取now_risk数据
            JSONObject nowRiskObject = fromObject.getJSONObject("now_risk");
            //获取Ideal_risk数据
            JSONObject idealRiskObject = fromObject.getJSONObject("Ideal_risk");
            //获取age_forecast数据
            JSONObject ageForecastObject = fromObject.getJSONObject("age_forecast");
            //获取age_op_forecast数据
            JSONObject ageOpForecastObject = fromObject.getJSONObject("age_op_forecast");
            //获取person数据
            JSONObject personObject = fromObject.getJSONObject("person");
            //获取返回数据中的风险数据
            String optimal = dataObject.getString("optimal");
            //收集疾病id与level等级
            Map<String, Object> levelIdMap = new HashMap<>();
            levelIdMap.put("diseaseid", app.getDiseaseCode());//病ID
            levelIdMap.put("diseaseName", app.getDiseaseName());//病名
            levelIdMap.put("level", riskObject.getString("level"));//患病情况
            levelIdList.add(levelIdMap);
            JSONArray suggests = dataObject.getJSONArray("suggest");
            StringBuffer improves = new StringBuffer();
            for (int i = 0; i < suggests.size(); i++) {
                JSONObject suggestsJson = suggests.getJSONObject(i);
                improves.append(suggestsJson.getString("improve"));
                if(i != suggests.size()-1) improves.append(";");
            }
            //评估报告结果
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
            Date date = simpleDateFormat.parse(dataObject.getString("qtime"));//评估时间
            YhDiseaseAssessReport yhDiseaseAssessReportApp = new YhDiseaseAssessReport();
            yhDiseaseAssessReportApp.setUserCode(userCode);
            yhDiseaseAssessReportApp.setRequestSerialnum(serialNumUUID);
            yhDiseaseAssessReportApp.setQtime(date);
            yhDiseaseAssessReportApp.setResultSerialnum(resultSerialnum);
            yhDiseaseAssessReportApp.setDiseasid((int)app.getDiseaseCode());//病号code
            yhDiseaseAssessReportApp.setLastTime(dataObject.getString("last_time"));
            yhDiseaseAssessReportApp.setRiskLevel(riskObject.getString("level"));//患病情况
            yhDiseaseAssessReportApp.setNowRiskLevel(nowRiskObject.getString("level"));
            yhDiseaseAssessReportApp.setNowRiskValue(nowRiskObject.getString("value"));
            yhDiseaseAssessReportApp.setNowRiskLastValue(nowRiskObject.getString("last_value"));
            yhDiseaseAssessReportApp.setIdealRiskLevel(idealRiskObject.getString("level"));
            yhDiseaseAssessReportApp.setAgeForecastR20(ageForecastObject.getString("R20"));
            yhDiseaseAssessReportApp.setAgeForecastR30(ageForecastObject.getString("R30"));
            yhDiseaseAssessReportApp.setAgeForecastR40(ageForecastObject.getString("R40"));
            yhDiseaseAssessReportApp.setAgeForecastR50(ageForecastObject.getString("R50"));
            yhDiseaseAssessReportApp.setAgeForecastR60(ageForecastObject.getString("R60"));
            yhDiseaseAssessReportApp.setAgeForecastR70(ageForecastObject.getString("R70"));
            yhDiseaseAssessReportApp.setAgeForecastR80(ageForecastObject.getString("R80"));
            yhDiseaseAssessReportApp.setAgeOpForecastR20Op(ageOpForecastObject.getString("R20_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR30Op(ageOpForecastObject.getString("R30_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR40Op(ageOpForecastObject.getString("R40_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR50Op(ageOpForecastObject.getString("R50_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR60Op(ageOpForecastObject.getString("R60_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR70Op(ageOpForecastObject.getString("R70_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR80Op(ageOpForecastObject.getString("R80_op"));
            yhDiseaseAssessReportApp.setImproves(improves.toString());
            //第二阶段 插入结果分表yh_disease_assess_report
            reportIns += yhDiseaseAssessReportAppService.insertYhDiseaseAssessReport(yhDiseaseAssessReportApp);
            //第四阶段插入受影响的疾病因素表中yh_disease_risk_result2
            doAssessReportNewApp(optimal, serialNumUUID, userCode);
        }
        YhDiseaseAssessTotalReportNewApp totalApp = new YhDiseaseAssessTotalReportNewApp();
        // 获取高风险和已患病的疾病
        for (int i = 0; i < levelIdList.size(); i++) {
            String level = levelIdList.get(i).get("level").toString();
            //判断人群分类
            if (level.indexOf("异常") >= 0 || level.indexOf("患") >= 0) {
                totalApp.setClassify("2");
                break;
            } else if (level.indexOf("高") >= 0 || level.indexOf("肥胖") >= 0  || level.indexOf("超重")>0) {
                totalApp.setClassify("3");
            } else{
                if(!"3".equals(totalApp.getClassify())){
                    totalApp.setClassify("1");
                }
            }
        }
        StringBuffer dfNamesString  = new StringBuffer();
        StringBuffer skNamesString  = new StringBuffer();
        StringBuffer ldNamesString  = new StringBuffer();
        StringBuffer lrNamesString  = new StringBuffer();
        // 获取高风险和已患病的疾病
        for (int i = 0; i < levelIdList.size(); i++) {
            String level = levelIdList.get(i).get("level").toString();
            //判断人群分类
            if (level.indexOf("异常") >= 0 || level.indexOf("患") >= 0) {
                skNamesString.append(levelIdList.get(i).get("diseaseName").toString()).append(";");
            } else if (level.indexOf("高") >= 0 || level.indexOf("肥胖")>0 || level.indexOf("超重")>0) {
                dfNamesString.append(levelIdList.get(i).get("diseaseName").toString()).append(";");
            } else if(level.indexOf("中") >= 0){
                ldNamesString.append(levelIdList.get(i).get("diseaseName").toString()).append(";");
            }else{
                lrNamesString.append(levelIdList.get(i).get("diseaseName").toString()).append(";");
            }
        }
        //查询分表中评估的次数
        int count = yhDiseaseAssessReportAppService.selMyAssessCount(userCode);
        totalApp.setRequestSerialnum(serialNumUUID);
        totalApp.setUserCode(userCode);
        totalApp.setQtime(new Date());
        totalApp.setGender(queestionData.getInteger("sex"));
        totalApp.setAge(queestionData.getInteger("age"));
        totalApp.setTimes(count);
        double height = Double.valueOf(queestionData.getString("height"));
        double weight = Double.valueOf(queestionData.getString("weight"));
        double hei = Arith.div(height, 100, 2);
        totalApp.setBmi(Arith.div(weight, Arith.mul(hei, hei), 2));
        YhJzServiceBodydataSwitch ok=yhtaskservuceapiservice.queryswitchbyid1(7,2, "健康风险评估");
        if(ok.getEvery_switch()==1) {
            YhManageTask task=new YhManageTask();
            task.setUsercode(userCode);
            Date d = new Date();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
            String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
            task.setService_waiting_time(newtime);
            task.setService_type(7);
            task.setService_content(ok.getService_content());
            task.setService_record("完成健康风险评估");
            task.setReturnvisit(ok.getReturnvisit());
            task.setChildnodes(ok.getId());
            task.setChildtag("2");
            yhtaskservuceapiservice.insertperson(task);
        }
        Integer totalReport = yhDiseaseAssessTotalReportAppService.insertIntoTotalReportNew(totalApp);//评估基本信息
        //yh_food_srecipe_record膳食推荐
        int recordResult = insertYhFoodSrecipeRecord(userCode,queestionData, levelIdList,foodactivity,serialNumUUID);
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>recordResult膳食推荐记录:" + recordResult);
        //yh_sport_recommend_record//运动推荐
        int sportRecordResult = insertYhSportRecommendRecord(userCode,queestionData, levelIdList);
        //插入结果
        Map<String,Integer> resultMap=new HashMap<>();
        resultMap.put("评估暂存",checkResult);
        resultMap.put("评估分表",reportIns);
        resultMap.put("评估基本信息",totalReport);
        resultMap.put("膳食推荐记录",recordResult);
        resultMap.put("运动推荐记录",sportRecordResult);
        return Result.ok(resultMap);
    }

    @Transactional
    @RequestMapping("/doDMRisk")
    public Result doDMRisk(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject queestionData) throws Exception {

        ApiResult apiResult = null;
        net.sf.json.JSONObject jsonApiResult = null;
        //生成请求不重复流水号
        String serialNumUUID = UUIDUtil.getJavaUUID();

        queestionData.put("serialNum", serialNumUUID);
        JSONObject person = (JSONObject) JSON.toJSON(queestionData.get("person"));
        //体力活动
        String foodactivity = queestionData.get("activity").toString();
        //体力活动
        //int foodactivity = Integer.parseInt(queestionData.get("activity").toString());
        String userCode = person.get("id").toString();
        if(userCode.indexOf(Global.getName()) !=-1){
            userCode=userCode.substring(Global.getName().length());
            person.put("id", Global.getName()+""+userCode);
        }else{
            person.put("id", Global.getName()+""+userCode);
        }
        queestionData.put("person", person);
        Integer reportIns=0;//评估分表
        Integer totalReport=0;//评估基本信息
        Integer resolve=0;//评估改善
        //第一阶段,评估完将queestionData插入暂存表yh_question_check_json
        int checkResult = insertYhQuestionCheckJson(userCode, queestionData);
        int disCode = 0;
        if (person.get("sex").toString().equals("1")) {
            disCode = 10;
        } else {
            disCode = 9;
        }
        //接口路径
        String doAssess_url = Constant.DOASSESS_URL;
        //格林时间戳
        String matTime = DateUtil.getMatTime(new Date());
        //初始化client
        YiheClient yiheClient = new DefaultYiheClient(userCode, Constant.PRIVATEKEY, matTime);
        //根据性别查询疾病分类中的id，将id插入接口得到返回结果
        List<YhDiseaseCategoryApp> categoryList = yhDiseaseCategoryAppService.selTopTenData(disCode);
        //插入的总表Map
        Map<String, Object> totalMap = new HashMap<>();
        //收集疾病id以及风险等级
        List<Map<String, Object>> levelIdList = new ArrayList<>();
        YhDiseaseRiskResultApp resultApp = new YhDiseaseRiskResultApp();
        for (int i = 0; i < categoryList.size(); i++) {
            YhDiseaseCategoryApp yhDiseaseCategoryApp = categoryList.get(i);
            Long diseaseCode = yhDiseaseCategoryApp.getDiseaseCode();
            queestionData.put("diseaseid", diseaseCode);
            String result = yiheClient.execute(queestionData, doAssess_url);
            //进行json转换
            net.sf.json.JSONObject fromObject = net.sf.json.JSONObject.fromObject(result);
            if (!fromObject.getString("result").equals("success")) {
                apiResult = new ApiResult(ApiResultConstant.FAIL);
                jsonApiResult = net.sf.json.JSONObject.fromObject(apiResult);
                break;
            }
            //获取返回数据
            String dataString = fromObject.getString("data");
            //获取返回结果流水号
            String resultSerialnum = fromObject.getString("sno");
            net.sf.json.JSONObject dataObject = net.sf.json.JSONObject.fromObject(dataString);
            //查看返回的数据
            //获取risk数据
            if(!dataObject.has("risk")){
            	apiResult = new ApiResult(ApiResultConstant.FAIL);
                jsonApiResult = net.sf.json.JSONObject.fromObject(apiResult);
                break;
            }
            String risk = dataObject.getString("risk");
            net.sf.json.JSONObject riskObject = net.sf.json.JSONObject.fromObject(risk);
            //获取now_risk数据
            String nowRisk = dataObject.getString("now_risk");
            net.sf.json.JSONObject nowRiskObject = net.sf.json.JSONObject.fromObject(nowRisk);
            //获取Ideal_risk数据
            String idealRisk = dataObject.getString("Ideal_risk");
            net.sf.json.JSONObject idealRiskObject = net.sf.json.JSONObject.fromObject(idealRisk);
            //获取age_forecast数据
            String ageForecast = dataObject.getString("age_forecast");
            net.sf.json.JSONObject ageForecastObject = net.sf.json.JSONObject.fromObject(ageForecast);
            //获取age_op_forecast数据
            String ageOpForecast = dataObject.getString("age_op_forecast");
            net.sf.json.JSONObject ageOpForecastObject = net.sf.json.JSONObject.fromObject(ageOpForecast);
            //获取person数据
            String personObj = dataObject.getString("person");
            net.sf.json.JSONObject personObject = net.sf.json.JSONObject.fromObject(personObj);
            //获取返回数据中的风险数据
            String optimal = dataObject.getString("optimal");
            String resultString = dataObject.getString("suggest");
            //收集疾病id与level等级
            Map<String, Object> levelIdMap = new HashMap<>();
            levelIdMap.put("diseaseid", diseaseCode);//病ID
            levelIdMap.put("diseaseName", yhDiseaseCategoryApp.getDiseaseName());//病名
            levelIdMap.put("level", riskObject.getString("level"));//患病情况
            levelIdList.add(levelIdMap);
            //评估报告结果
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
            Date date = simpleDateFormat.parse(dataObject.getString("qtime"));//评估时间
            YhDiseaseAssessReport yhDiseaseAssessReportApp = new YhDiseaseAssessReport();
            yhDiseaseAssessReportApp.setUserCode(userCode);
            yhDiseaseAssessReportApp.setRequestSerialnum(serialNumUUID);
            yhDiseaseAssessReportApp.setQtime(date);
            yhDiseaseAssessReportApp.setResultSerialnum(resultSerialnum);
            yhDiseaseAssessReportApp.setDiseasid(diseaseCode.intValue());//病号code
            yhDiseaseAssessReportApp.setLastTime(dataObject.getString("last_time"));
            yhDiseaseAssessReportApp.setRiskLevel(riskObject.getString("level"));//患病情况
            if(riskObject.getString("level").contains("已患")){
                if(3==diseaseCode){
                	//判断是否患过高血压
                	JSONObject hbpHis = (JSONObject) JSON.toJSON(queestionData.get("hbp_his"));
                	if(hbpHis.get("disFlag").toString().equals("1")){
                		yhDiseaseAssessReportApp.setRiskDesc("根据你您提供的信息，您已被诊断患有高血压，你可以前往本系统的饮食和运动主页，获取推荐的康复方案，或者咨询医生获得更多诊疗方案。出现特殊情况，应及时到医院就诊。");
                	}else{
                		String sbp = queestionData.get("sbp").toString();
                		String dbp = queestionData.get("dbp").toString();
                		String sbpcontent = null;
                		if(StringUtil.isNotEmpty(sbp) && StringUtil.isNotEmpty(dbp) ){
                            double reqsbp = Double.valueOf(sbp);
                            double reqdbp = Double.valueOf(dbp);
                            if (140 <= reqsbp && reqsbp <= 159 || 90<=reqdbp && reqdbp <=99) {
                            	sbpcontent = "您的血压（收缩压（mmHg）140～159和/或舒张压（mmHg）90～99）已达到1级高血压(轻度) 诊断标准";
                    		}
                            if (160 <= reqsbp && reqsbp <= 179 || 100<=reqdbp && reqdbp <=109) {
                            	sbpcontent = "您的血压（收缩压（mmHg）160～179和/或舒张压（mmHg）100～109）已达到2级高血压(中度) 诊断标准";
                    		}
                            if (180 <= reqsbp || 110<=reqdbp) {
                            	sbpcontent = "您的血压（收缩压（mmHg）≥180和/或舒张压（mmHg）≥ 110）已达到3级高血压（重度）诊断标准";
                    		}
                            yhDiseaseAssessReportApp.setRiskDesc("根据你您提供的信息，"+sbpcontent+"，请咨询医生进一步确诊。");
                     	}
                	}
	            }else if(4==diseaseCode){
	            	//判断是否患过糖尿病
	            	JSONObject diab2His = (JSONObject) JSON.toJSON(queestionData.get("diab2_his"));
                	if(diab2His.get("disFlag").toString().equals("1")){
                		yhDiseaseAssessReportApp.setRiskDesc("根据您提供的信息，您已被诊断患有糖尿病，你可以前往本系统的饮食和运动主页，获取推荐的康复方案，或者咨询医生获得更多诊疗方案。出现特殊情况，应及时到医院就诊。");
                	}else{
                		yhDiseaseAssessReportApp.setRiskDesc("根据您提供的信息，您的空腹血糖（>6.1mmol/L）已达到糖尿病的诊断标准，请咨询医生进一步确诊。");
                	}
	            }else{
               	   yhDiseaseAssessReportApp.setRiskDesc("根据您提供的信息，您已被诊断患有"+yhDiseaseCategoryApp.getDiseaseName()+"，你可以前往本系统的饮食和运动主页，获取推荐的康复方案，或者咨询医生获得更多诊疗方案。出现特殊情况，应及时到医院就诊。");
	            }
            }else{
            	  yhDiseaseAssessReportApp.setRiskDesc(riskObject.getString("desc"));
            }
            yhDiseaseAssessReportApp.setNowRiskLastValue(nowRiskObject.getString("last_value"));
            yhDiseaseAssessReportApp.setNowRiskLevel(nowRiskObject.getString("level"));
            yhDiseaseAssessReportApp.setNowRiskValue(nowRiskObject.getString("value"));
            yhDiseaseAssessReportApp.setIdealRiskLevel(idealRiskObject.getString("level"));
            yhDiseaseAssessReportApp.setAgeForecastR20(ageForecastObject.getString("R20"));
            yhDiseaseAssessReportApp.setAgeForecastR30(ageForecastObject.getString("R30"));
            yhDiseaseAssessReportApp.setAgeForecastR40(ageForecastObject.getString("R40"));
            yhDiseaseAssessReportApp.setAgeForecastR50(ageForecastObject.getString("R50"));
            yhDiseaseAssessReportApp.setAgeForecastR60(ageForecastObject.getString("R60"));
            yhDiseaseAssessReportApp.setAgeForecastR70(ageForecastObject.getString("R70"));
            yhDiseaseAssessReportApp.setAgeForecastR80(ageForecastObject.getString("R80"));
            yhDiseaseAssessReportApp.setAgeOpForecastR20Op(ageOpForecastObject.getString("R20_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR30Op(ageOpForecastObject.getString("R30_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR40Op(ageOpForecastObject.getString("R40_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR50Op(ageOpForecastObject.getString("R50_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR60Op(ageOpForecastObject.getString("R60_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR70Op(ageOpForecastObject.getString("R70_op"));
            yhDiseaseAssessReportApp.setAgeOpForecastR80Op(ageOpForecastObject.getString("R80_op"));
            //第二阶段 插入结果分表yh_disease_assess_report
            reportIns += yhDiseaseAssessReportAppService.insertYhDiseaseAssessReport(yhDiseaseAssessReportApp);
            //做if判断reportIns返回的结果大于0则插入总表
          
            totalMap.put("length", i);
            totalMap.put("requestSerialnum", serialNumUUID);
            totalMap.put("userCode", userCode);
            totalMap.put("qtime", date);
            totalMap.put("sex", personObject.getString("sex"));
            totalMap.put("age", personObject.getString("age"));
            totalMap.put("userName", personObject.getString("name"));
            //第三阶段，进行判断并插入总表yh_disease_assess_total_report2
            //System.out.println(">>>>>>>>>>>>>>>>>totalReport评估基本信息:" + totalReport);
            //做if判断totalReport返回的结果大于0则插入疾病风险因素表
            //第四阶段插入受影响的疾病因素表中yh_disease_risk_result2
            doAssessReportApp(optimal, resultApp);
            totalReport = insertIntoTotalReport(totalMap,levelIdList,queestionData,resultApp,request);
            //做if判断riskResult返回的结果大于0则插入改善表
            //第五阶段返回的结果插入改善表yh_disease_assess_resolve mapping中通过sql批量插入
            List<YHDiseaseAssessResolve> parseArray = JSONObject.parseArray(resultString, YHDiseaseAssessResolve.class);
            if (null != parseArray && parseArray.size() > 0) {
                for (int j = 0, x = parseArray.size(); j < x; j++) {
                    parseArray.get(j).setResultSerialNum(serialNumUUID);
                    parseArray.get(j).setDiseaseId(diseaseCode.intValue());
                }
                resolve += yhDiseaseAssessResolveAppService.insertBatch(parseArray);
            }
        }
        //yh_food_srecipe_record膳食推荐
        int recordResult = insertYhFoodSrecipeRecord(userCode,queestionData, levelIdList,foodactivity,serialNumUUID);
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>recordResult膳食推荐记录:" + recordResult);
        //yh_sport_recommend_record//运动推荐
        int sportRecordResult = insertYhSportRecommendRecord(userCode,queestionData, levelIdList);
        //推送风险评估信息
        YhDiseaseAssessTotalReportApp assessTotalReportApp = yhDiseaseAssessTotalReportAppService.queryOaUserAssessInfo(userCode);
        JSONArray jsonArray = new JSONArray();
        JSONObject map = new JSONObject();
        JSONObject operationinfo = new JSONObject();
        operationinfo.put("operator", "1");
        map.put("operationinfo",operationinfo);
        JSONObject mainTable = new JSONObject();
        mainTable.put("xm", assessTotalReportApp.getUserName());
        mainTable.put("xb", assessTotalReportApp.getGender().equals("1") ? "男" : "女");
        mainTable.put("pgcs", assessTotalReportApp.getTimes());
        mainTable.put("zxpgrq", DateUtils.dateTime(assessTotalReportApp.getQtime()));
        mainTable.put("gfx", assessTotalReportApp.getDfNames());
        mainTable.put("hbhyc", assessTotalReportApp.getSkNames());
        map.put("mainTable", mainTable);
        jsonArray.add(map);
        RestfulDemo.doAction("http://"+ Global.getOaIPAddress() +"/api/cube/restful/interface/saveOrUpdateModeData/syncJKBG", jsonArray);

        //插入结果
        Map<String,Integer> resultMap=new HashMap<>();
        resultMap.put("评估暂存",checkResult);
        resultMap.put("评估分表",reportIns);
        resultMap.put("评估基本信息",totalReport);
        resultMap.put("评估改善",resolve);
        resultMap.put("膳食推荐记录",recordResult);
        resultMap.put("运动推荐记录",sportRecordResult);
        return Result.ok(resultMap);
    }

    /**
     * 推荐运动
     * @param userCode 
     */
    public int insertYhSportRecommendRecord(String userCode, JSONObject queestionData, List<Map<String, Object>> levelIdList) {
        int sportRecordResult = 0;
        //推荐运动套餐
        YhSportSetmealApp yhSportSetmealApp = new YhSportSetmealApp();
        //推荐运动记录
        YhSportRecommendRecordApp recommendRecordApp = new YhSportRecommendRecordApp();
        //多个高风险疾病返回的运动套餐
        List<YhSportSetmealApp> sportSetmealAppList = new ArrayList<>();
        JSONObject person = (JSONObject) JSON.toJSON(queestionData.get("person"));
        
        String sportin = queestionData.get("sport_in").toString();//您在过去3个月中是否参加过运动锻炼
        String sportfreq =queestionData.get("sport_freq").toString();//一般情况下您参加运动锻炼的频率是？
        
        YhSportRecommendRecordApp recommendRecordApp1 = new YhSportRecommendRecordApp();
        recommendRecordApp1.setUserCode(userCode);
        yhSportRecommendRecordAppService.uptYhSportRecommendRecord(recommendRecordApp1);
        int randomNum = 1;
        YhSportFrequencyApp freq=yhSportFrequencyAppMapper.selectByPrimaryKey(userCode);
        YhSportFrequencyApp sportfr=new YhSportFrequencyApp();
        sportfr.setFrType(1);
        if(sportin.equals("1")) {
        	if(Integer.valueOf(sportfreq)<=2) {
        		randomNum=1;
        		sportfr.setFrFrequency("每周二,四,六");
        	}else if(Integer.valueOf(sportfreq)==3){
        		randomNum=2;
        		sportfr.setFrFrequency("每周一,三,五");
        	}else {
        		sportfr.setFrFrequency("每周一,二,四,五,七");
        		randomNum=3;
        	}
        }else {
        	sportfr.setFrFrequency("每周二,四,六");
        	randomNum=1;
        }
        sportfr.setUserCode(userCode);
        if(freq==null) {
        	 yhSportFrequencyAppMapper.insertSelective(sportfr);
        }else {
        	 yhSportFrequencyAppMapper.updateByPrimaryKeySelective(sportfr);
        }
       
        yhSportSetmealApp.setExerciseFrequency(randomNum);
        List<Integer> suffering =new ArrayList<Integer>();//已患或者异常
        
        List<Integer> high =new ArrayList<Integer>();//高
       
        //获取高风险和已患病的疾病
        for (int i = 0; i < levelIdList.size(); i++) {
            String level = levelIdList.get(i).get("level").toString();
            
            if (level.indexOf("异常") >= 0 || level.indexOf("患") >= 0 || level.indexOf("肥胖")>0 ) {
            	suffering.add(Integer.valueOf(levelIdList.get(i).get("diseaseid").toString()));
 	        } else if (level.indexOf("高") >= 0 || level.indexOf("超重")>0 || level.indexOf("中") >= 0) {
 	        	high.add(Integer.valueOf(levelIdList.get(i).get("diseaseid").toString()));
 	        }
            
//            if (level.indexOf("低") < 0) {
//                yhSportSetmealApp.setDiseaseid(Long.parseLong(levelIdList.get(i).get("diseaseid").toString()));
//                sportSetmealAppList.add(yhSportSetmealAppService.selectYhSportSetmeal(yhSportSetmealApp));
//            }
        }
        if(suffering.size()>=3) {
        	for (Integer long1 : suffering) {
        		yhSportSetmealApp.setDiseaseCode(long1);
                sportSetmealAppList.add(yhSportSetmealAppService.selectYhSportSetmeal(yhSportSetmealApp));
    		}
        }else {
        	for (Integer long1 : suffering) {
        		yhSportSetmealApp.setDiseaseCode(long1);
                sportSetmealAppList.add(yhSportSetmealAppService.selectYhSportSetmeal(yhSportSetmealApp));
    		}
        	if(3-suffering.size()<=0) {
        		for (int i = 0; i < 3-suffering.size(); i++) {
            		yhSportSetmealApp.setDiseaseCode(high.get(i));
                    sportSetmealAppList.add(yhSportSetmealAppService.selectYhSportSetmeal(yhSportSetmealApp));
    			}
        	}else {
        		 for (Integer integer : high) {
        			 yhSportSetmealApp.setDiseaseCode(integer);
                     sportSetmealAppList.add(yhSportSetmealAppService.selectYhSportSetmeal(yhSportSetmealApp));
        		}
        	}
        	
        }
        //如果全是低风险
        if (sportSetmealAppList.size() == 0) {
            yhSportSetmealApp.setDiseaseCode(13);
            //推荐健康类膳食
            sportSetmealAppList.add(yhSportSetmealAppService.selectYhSportSetmeal(yhSportSetmealApp));
        }
        for (int i = 0; i < sportSetmealAppList.size(); i++) {
            if (sportSetmealAppList.get(i) != null) {
                //获取天数
                String[] subject = sportSetmealAppList.get(i).getSubjectId().split(",");
                recommendRecordApp.setRecordData(new Date());
                recommendRecordApp.setUserCode(userCode);
                recommendRecordApp.setDiseaseid(sportSetmealAppList.get(i).getDiseaseCode().toString());
                recommendRecordApp.setSetmealid(sportSetmealAppList.get(i).getId().toString());
                recommendRecordApp.setNumber("0");
                recommendRecordApp.setRecordType(1);
                recommendRecordApp.setRecordMode("系统自动");
                recommendRecordApp.setSportName(sportSetmealAppList.get(i).getSetmealMame().toString());
                recommendRecordApp.setStandby(Integer.toString(subject.length));
                sportRecordResult += yhSportRecommendRecordAppService.insertYhSportRecommendRecord(recommendRecordApp);
            }
        }
        return sportRecordResult;
    }
 
    
    
    
    /**
     * 推荐膳食
     */
    /**
	 * 推荐膳食
     * @param userCode 
	 */
	public int insertYhFoodSrecipeRecord(String userCode, JSONObject queestionData, List<Map<String, Object>> levelIdList, String sport,String serialNumUUID) {
		int recordResult = 0;
		 
		// 推荐膳食记录
		YhFoodSrecipeRecordApp srecipeRecord = new YhFoodSrecipeRecordApp();
        srecipeRecord.setRequestSerialNum(serialNumUUID);
		// 获得性别
		JSONObject person = (JSONObject) JSON.toJSON(queestionData.get("person"));
		
		//①性别
		int sexInt = Integer.parseInt(person.getString("sex"));
				//②年龄
		int ageInt = Integer.parseInt(person.getString("age"));
				//③身高
		double height = Double.valueOf(queestionData.getString("height"));
				//④体重
		double weight = Double.valueOf(queestionData.getString("weight"));
		
				//⑤劳动强度 sport
		srecipeRecord.setAge(ageInt);
		srecipeRecord.setHeight((float)height);
		srecipeRecord.setWeight((float)weight);
		srecipeRecord.setStrength(Integer.valueOf(sport));
		srecipeRecord.setSex(sexInt);
	
		 //得到计算出来的每日能量推荐
			double findEnergyNum = yhFoodSrecipeRecordAppService.findEnergyNum(sexInt, ageInt, height, weight, String.valueOf(Integer.valueOf(sport)+1));
		 
		srecipeRecord.setUserCode(userCode);
		
		//修改用户膳食推荐状态为结束
		//yhFoodSrecipeRecordAppService.uptYhFoodSrecipeRecord(srecipeRecord);
		
		//查询是否有正在执行的健康计划膳食推荐
		YhFoodSrecipeRecordApp findInProgressFoodRecord = yhFoodSrecipeRecordAppService.findInProgressFoodRecord(userCode);
		
		//已患集合
		Map<Integer, String> skmap=new HashMap<Integer, String>();
		//高风险集合
		Map<Integer, String> dfmap=new HashMap<Integer, String>();
		//中风险集合
		Map<Integer, String> ldmap=new HashMap<Integer, String>();
		
		
		
         // 获取高风险和已患病的疾病
         for (int i = 0; i < levelIdList.size(); i++) {
     		String level = levelIdList.get(i).get("level").toString();
     		if (Integer.parseInt(levelIdList.get(i).get("diseaseid").toString()) == 8
					|| Integer.parseInt(levelIdList.get(i).get("diseaseid").toString()) == 9
					|| Integer.parseInt(levelIdList.get(i).get("diseaseid").toString()) == 10) {
				continue;
			}
 	        //判断人群分类
 	        if (level.indexOf("异常") >= 0 || level.indexOf("患") >= 0) {
 	        	skmap.put(Integer.parseInt(levelIdList.get(i).get("diseaseid").toString()), levelIdList.get(i).get("diseaseName").toString());
 	        } else if (level.indexOf("高") >= 0 || level.indexOf("肥胖")>0  || level.indexOf("超重")>0) {
 	        	dfmap.put(Integer.parseInt(levelIdList.get(i).get("diseaseid").toString()), levelIdList.get(i).get("diseaseName").toString());
 	        } else if(level.indexOf("中") >= 0){
 	        	ldmap.put(Integer.parseInt(levelIdList.get(i).get("diseaseid").toString()), levelIdList.get(i).get("diseaseName").toString());
 	        }
         }
         
         //默认健康人群
         Integer diseaseid=13;
         String disName="健康";
         //根据系统逻辑获得饮食推荐疾病id
         if(!skmap.isEmpty()){
        	 Iterator keys = skmap.keySet().iterator();
        	 while(keys.hasNext()){
        		 int key = (int) keys.next();
        		 diseaseid=key;
        		 disName = skmap.get(diseaseid);
        		 break;
             }
        	 
        	 
        	 
        	 //如果存在痛风已患
        	 boolean flag5=skmap.containsKey("5");
        	 if(flag5){
        		 diseaseid=5;
        		 disName = skmap.get(diseaseid);
        	 }else{
        		 //如果没有5，判断是否有
        		 boolean flag4=skmap.containsKey("4");
        		if(flag4){
        			 diseaseid=4;
        			 disName = skmap.get(diseaseid);
        		}
        	 }
        	 
         }else if(!dfmap.isEmpty()){
        	 Iterator keys = dfmap.keySet().iterator();
        	 while(keys.hasNext()){
        		 int key = (int) keys.next();
        		 diseaseid=key;
        		 disName = dfmap.get(diseaseid);
        		 break;
             }
        	 
         }else if(!ldmap.isEmpty()){
        	 Iterator keys = ldmap.keySet().iterator();
        	 while(keys.hasNext()){
        		 int key = (int) keys.next();
        		 diseaseid=key;
        		 disName = ldmap.get(diseaseid);
        		 break;
             }
         }

         YhFoodSrecipeApiNew yhFoodSrecipeApiNew =null;
         List<YhFoodSrecipeApiNew> findNearFoodSrecipeInfo = YhFoodSrecipeApiNewMapper.findNearFoodSrecipeInfo(diseaseid, findEnergyNum);
         if(null!=findNearFoodSrecipeInfo&&findNearFoodSrecipeInfo.size()>0){
        	 Random random = new Random();
        	        int n = random.nextInt(findNearFoodSrecipeInfo.size());
        	        yhFoodSrecipeApiNew=findNearFoodSrecipeInfo.get(n); 
         }
         
         
         if(null==findNearFoodSrecipeInfo){
        	   List<YhFoodSrecipeApiNew> findNearFoodSrecipeInfoForJianKang = YhFoodSrecipeApiNewMapper.findNearFoodSrecipeInfoForJianKang(findEnergyNum);
        	   if(null==findNearFoodSrecipeInfoForJianKang){
        		   return 0;
        	   }
        	   Random random = new Random();
        	   int n = random.nextInt(findNearFoodSrecipeInfoForJianKang.size());
   	        	yhFoodSrecipeApiNew=findNearFoodSrecipeInfoForJianKang.get(n); 
         }
				 
				srecipeRecord.setRecordData(new Date());
				srecipeRecord.setRecordName(disName);
				srecipeRecord.setRecordKay(yhFoodSrecipeApiNew.geteLevel().longValue());
				//如果有正在执行的健康计划，膳食推荐状态为3
				//有正在执行的健康计划饮食套餐时，本次评估推荐的新套餐“挂起”，以前被“挂起”的套餐“结束”。
				if(null!=findInProgressFoodRecord){
					srecipeRecord.setRecordType(Long.parseLong("3"));
					yhFoodSrecipeRecordAppService.modifyType3Fortype2(userCode);
					srecipeRecord.setStandby(new Date());// new Date()为获取当前系统时间
				}else{
					srecipeRecord.setRecordType(Long.parseLong("1"));
					yhFoodSrecipeRecordAppService.modifyType1Fortype2(userCode);
				}
				
				srecipeRecord.setRecordMode("系统自动");
				srecipeRecord.setUserCode(userCode);
				srecipeRecord.setMealId(String.valueOf(yhFoodSrecipeApiNew.getId()));
				srecipeRecord.setIllnessId(String.valueOf(diseaseid));
				
				 
				 yhFoodSrecipeRecordAppService.insertYhFoodSrecipeRecord(srecipeRecord);
		return recordResult;
	}

	
	
 
    /**
     * 推荐膳食
     */
    /**
	 * 推荐膳食
	 */
	public int insertYhFoodSrecipeRecord(JSONObject queestionData, List<Map<String, Object>> levelIdList, Integer sport) {
		int recordResult = 0;
		// 推荐膳食套餐
		YhFoodSrecipe srecipe = new YhFoodSrecipe();
		// 推荐膳食记录
		YhFoodSrecipeRecordApp srecipeRecord = new YhFoodSrecipeRecordApp();
		// 获得性别
		JSONObject person = (JSONObject) JSON.toJSON(queestionData.get("person"));
		srecipe.setGenter(Integer.parseInt(person.getString("sex")));
		// 多个疾病返回的膳食推荐
		List<YhFoodSrecipe> srecipeList = new ArrayList<>();
		// 随机获得1-4之间的口味
		Random r = new Random();
		srecipe.setdType(r.nextInt(4) + 1);
		double height = Double.valueOf(queestionData.getString("height"));
		if (height > 175) {
			srecipe.setPackageNumber(3);
		} else if (height >= 165 && height <= 175) {
			srecipe.setPackageNumber(2);
		} else {
			srecipe.setPackageNumber(1);
		}
		// 获得体力活动
		if (sport == 2) {
			srecipe.setIsUse(2);
		} else {
			srecipe.setIsUse(1);
		}
		srecipeRecord.setUserCode(person.getString("id"));
		yhFoodSrecipeRecordAppService.uptYhFoodSrecipeRecord(srecipeRecord);
		// 获取高风险和已患病的疾病
		for (int i = 0; i < levelIdList.size(); i++) {
			String level = levelIdList.get(i).get("level").toString();
			if (level.indexOf("低") < 0) {
				if (Integer.parseInt(levelIdList.get(i).get("diseaseid").toString()) == 8
						|| Integer.parseInt(levelIdList.get(i).get("diseaseid").toString()) == 9
						|| Integer.parseInt(levelIdList.get(i).get("diseaseid").toString()) == 10) {
					continue;
				}
				srecipe.setDiseaseid(levelIdList.get(i).get("diseaseid").toString());
				srecipeList.add(yhFoodSrecipeAppService.selectYhFoodSrecipeById2(srecipe));
			}
		}
		// 如果全是低风险
		if (srecipeList.size() == 0) {
			srecipe.setDiseaseid("13");
			// 推荐健康类膳食
			srecipeList.add(yhFoodSrecipeAppService.selectYhFoodSrecipeById2(srecipe));
		}
		for (int i = 0; i < srecipeList.size(); i++) {
			if (srecipeList.get(i) != null) {
				String name = yhDiseaseCategoryAppService
						.selCategoryByCode(Integer.parseInt(srecipeList.get(i).getDiseaseid().toString()))
						.getDiseaseName();
				srecipeRecord.setRecordData(new Date());
				srecipeRecord.setRecordName(name);
				srecipeRecord.setRecordKay(Long.parseLong(srecipeList.get(i).geteLevel().toString()));
				srecipeRecord.setRecordType(Long.parseLong("1"));
				srecipeRecord.setRecordMode("系统自动");
				srecipeRecord.setUserCode(person.getString("id"));
				srecipeRecord.setMealId(srecipeList.get(i).getId().toString());
				srecipeRecord.setIllnessId(srecipeList.get(i).getDiseaseid().toString());
				if(i>0){
					srecipeRecord.setRecordType(Long.parseLong("3"));
				}else{
					srecipeRecord.setRecordType(Long.parseLong("1"));
				}
				recordResult += yhFoodSrecipeRecordAppService.insertYhFoodSrecipeRecord(srecipeRecord);
			}
		}
		return recordResult;
	}

    /**
     * 评估完添加到暂存表中
     */
    public int insertYhQuestionCheckJson(String userCode, JSONObject queestionData) {
        YhQuestionCheckJsonApp checkJsonApp = new YhQuestionCheckJsonApp();
        checkJsonApp.setUserCode(userCode);
        checkJsonApp.setUserCheckAnswer(queestionData.toString());
        checkJsonApp.setUserCheckIndex(Long.parseLong("1"));
        checkJsonApp.setUserCheckTs(Long.parseLong("1"));
        checkJsonApp.setUserCheckTime(new Date());
        checkJsonApp.setRequestSerialNum(queestionData.getString("serialNum"));
        return yhQuestionCheckJsonAppService.insertYhQuestionCheckJson(checkJsonApp);
       
    }

    /**
     * 评估完添加数据到总表中
     */
    public int insertIntoTotalReport(Map<String, Object> totalMap,List<Map<String, Object>> levelIdList,JSONObject queestionData,YhDiseaseRiskResultApp resultApp, HttpServletRequest request) throws ParseException {

        //当循环到最后一条数据时进行总表的插入yh_disease_assess_total_report2
        if (Integer.parseInt(totalMap.get("length").toString()) >= 9) {
        	resultApp.setDiseaseid("0");
            resultApp.setUserCode(totalMap.get("userCode").toString());
            resultApp.setRequestSerialize(totalMap.get("requestSerialnum").toString());
            resultApp.setQtime((Date)totalMap.get("qtime"));
        	yhDiseaseRiskResultAppService.insertYhDiseaseRiskResult(resultApp);
            YhDiseaseAssessTotalReportApp totalApp = new YhDiseaseAssessTotalReportApp();
            // 获取高风险和已患病的疾病
            for (int i = 0; i < levelIdList.size(); i++) {
        		String level = levelIdList.get(i).get("level").toString();
    	        //判断人群分类
    	        if (level.indexOf("异常") >= 0 || level.indexOf("患") >= 0) {
    	            totalApp.setClassify("2");
    	            break;
    	        } else if (level.indexOf("高") >= 0 || level.indexOf("肥胖") >= 0  || level.indexOf("超重")>0) {
    	            totalApp.setClassify("3");
    	        } else{
                    if(!"3".equals(totalApp.getClassify())){
                        totalApp.setClassify("1");
                    }
    	        }
            }
            StringBuffer dfNamesString  = new StringBuffer();
            StringBuffer skNamesString  = new StringBuffer();
            StringBuffer ldNamesString  = new StringBuffer();
            StringBuffer lrNamesString  = new StringBuffer();
            // 获取高风险和已患病的疾病
            for (int i = 0; i < levelIdList.size(); i++) {
        		String level = levelIdList.get(i).get("level").toString();
    	        //判断人群分类
    	        if (level.indexOf("异常") >= 0 || level.indexOf("患") >= 0) {
    	        	skNamesString.append(levelIdList.get(i).get("diseaseName").toString()).append(";");
    	        } else if (level.indexOf("高") >= 0 || level.indexOf("肥胖")>0 || level.indexOf("超重")>0) {
    	        	dfNamesString.append(levelIdList.get(i).get("diseaseName").toString()).append(";");
    	        } else if(level.indexOf("中") >= 0){
    	        	ldNamesString.append(levelIdList.get(i).get("diseaseName").toString()).append(";");
    	        }else{
    	        	lrNamesString.append(levelIdList.get(i).get("diseaseName").toString()).append(";");
    	        }
            }
            //查询分表中评估的次数
            int count = yhDiseaseAssessReportAppService.selMyAssessCount(totalMap.get("userCode").toString());
            totalApp.setRequestSerialnum(totalMap.get("requestSerialnum").toString());
            totalApp.setUserCode(totalMap.get("userCode").toString());
            totalApp.setQtime((Date) totalMap.get("qtime"));
            totalApp.setGender(Integer.parseInt(totalMap.get("sex").toString()));
            totalApp.setAge(Integer.parseInt(totalMap.get("age").toString()));
            totalApp.setUserName(totalMap.get("userName").toString());
            totalApp.setTimes(count + 1);
            totalApp.setDfNames(dfNamesString.indexOf(";")>0?dfNamesString.substring(0, dfNamesString.length()-1):"");
            totalApp.setSkNames(skNamesString.indexOf(";")>0?skNamesString.substring(0, skNamesString.length()-1):"");
            totalApp.setLdNames(ldNamesString.indexOf(";")>0?ldNamesString.substring(0, ldNamesString.length()-1):"");
            totalApp.setLrNames(lrNamesString.indexOf(";")>0?lrNamesString.substring(0, lrNamesString.length()-1):"");
            JSONObject chd_his = (JSONObject) JSON.toJSON(queestionData.get("chd_his"));
            totalApp.setChdHis(Integer.parseInt(chd_his.getString("disFlag")));
            JSONObject stroke_his = (JSONObject) JSON.toJSON(queestionData.get("stroke_his"));
            totalApp.setStrokeHis(Integer.parseInt(stroke_his.getString("disFlag")));
            JSONObject hbp_his = (JSONObject) JSON.toJSON(queestionData.get("hbp_his"));
            totalApp.setHbpHis(Integer.parseInt(hbp_his.getString("disFlag")));
            JSONObject diab2_his = (JSONObject) JSON.toJSON(queestionData.get("diab2_his"));
            totalApp.setDiab2His(Integer.parseInt(diab2_his.getString("disFlag")));
            if(queestionData.getDoubleValue("cholesterol") >= 5.18 || queestionData.getDoubleValue("tg") >= 1.7 || queestionData.getDoubleValue("hdlc") < 1.04 || queestionData.getDoubleValue("ldlc") >= 3.37){
            	totalApp.setBfHis(1);
            }else{
            	totalApp.setBfHis(2);
            }
            JSONObject hua_his = (JSONObject) JSON.toJSON(queestionData.get("hua_his"));
            totalApp.setHuaHis(Integer.parseInt(hua_his.getString("disFlag")));
            JSONObject gae_his = (JSONObject) JSON.toJSON(queestionData.get("gae_his"));
            totalApp.setGaeHis(Integer.parseInt(gae_his.getString("disFlag")));
            JSONObject gna_his = (JSONObject) JSON.toJSON(queestionData.get("gna_his"));
            totalApp.setGnaHis(Integer.parseInt(gna_his.getString("disFlag")));
            JSONObject op_his = (JSONObject) JSON.toJSON(queestionData.get("op_his"));
            totalApp.setOpHis(Integer.parseInt(op_his.getString("disFlag")));
            JSONObject fl_his = (JSONObject) JSON.toJSON(queestionData.get("fl_his"));
            totalApp.setFlHis(Integer.parseInt(fl_his.getString("disFlag")));
            JSONObject bronch_his = (JSONObject) JSON.toJSON(queestionData.get("bronch_his"));
            totalApp.setBronchHis(Integer.parseInt(bronch_his.getString("disFlag")));
            JSONObject emphy_his = (JSONObject) JSON.toJSON(queestionData.get("emphy_his"));
            totalApp.setEmphyHis(Integer.parseInt(emphy_his.getString("disFlag")));
            JSONObject lc_his = (JSONObject) JSON.toJSON(queestionData.get("lc_his"));
            totalApp.setLcHis(Integer.parseInt(lc_his.getString("disFlag")));
            JSONObject decline_his = (JSONObject) JSON.toJSON(queestionData.get("decline_his"));
            totalApp.setDeclineHis(Integer.parseInt(decline_his.getString("disFlag")));
            JSONObject fracture_his = (JSONObject) JSON.toJSON(queestionData.get("fracture_his"));
            totalApp.setFractureHis(Integer.parseInt(fracture_his.getString("disFlag")));
            JSONObject prostate_disease = (JSONObject) JSON.toJSON(queestionData.get("prostate_disease"));
            totalApp.setProstateDisease(Integer.parseInt(prostate_disease.getString("disFlag")));
            JSONObject pc_his = (JSONObject) JSON.toJSON(queestionData.get("pc_his"));
            totalApp.setPcHis(Integer.parseInt(pc_his.getString("disFlag")));
            JSONObject bc_his = (JSONObject) JSON.toJSON(queestionData.get("bc_his"));
            totalApp.setBcHis(Integer.parseInt(bc_his.getString("disFlag")));
            //判断是否患有家族史
            String[] family_str = { "f", "m", "bs", "ch" };
            String[] family_breast_str = { "m", "s", "d" };
            String[] family_pro_str = { "f", "b", "s" };
            Integer family_diab = getNewChoose(family_str, queestionData, "family_diab_");
            Integer family_hbp = getNewChoose(family_str, queestionData, "family_hbp_");
            Integer family_chd = getNewChoose(family_str, queestionData, "family_chd_");
            Integer family_stroke = getNewChoose(family_str, queestionData, "family_stroke_");
            Integer family_hf = getNewChoose(family_str, queestionData, "family_hf_");
            Integer family_lung = getNewChoose(family_str, queestionData, "family_lung_");
            Integer family_gout = getNewChoose(family_str, queestionData, "family_gout_");
            Integer family_breast = 2;
            Integer family_pro =2;
            if (Integer.parseInt(totalMap.get("sex").toString())==1) {
            	 family_pro = getNewChoose(family_pro_str, queestionData, "family_pro_");
            }else{
            	 family_breast = getNewChoose(family_breast_str, queestionData, "family_breast_");
            }
            totalApp.setFamilyDiab(family_diab);
            totalApp.setFamilyHbp(family_hbp);
            totalApp.setFamilyChd(family_chd);
            totalApp.setFamilyStroke(family_stroke);
            totalApp.setFamilyHf(family_hf);
            totalApp.setFamilyLung(family_lung);
            totalApp.setFamilyGout(family_gout);
            totalApp.setFamilyPro(family_pro);
            totalApp.setFamilyBreast(family_breast);
            totalApp.setSmokeStatus(Integer.parseInt(queestionData.getString("smoke_status")));
            totalApp.setFoodDrink(Integer.parseInt(queestionData.getString("food_drink")));
            Integer foodvegetables = Integer.parseInt(queestionData.getString("food_vegetables"))==5?2:1;
            Integer fooddairy = Integer.parseInt(queestionData.getString("food_dairy"))>1?2:1;
            Integer foodpurine = Integer.parseInt(queestionData.getString("seafood_cnt"))<3?2:1;
            Integer foodfruits = Integer.parseInt(queestionData.getString("food_fruits"))==5?2:1;
            Integer B41 = Integer.parseInt(queestionData.getString("food_fired"))<3?2:1;
            totalApp.setFoodVegetables(foodvegetables);
            totalApp.setFoodDairy(fooddairy);
            totalApp.setFoodPurine(foodpurine);
            totalApp.setFoodFruits(foodfruits);
            totalApp.setB4(B41);
            totalApp.setSportIn(Integer.parseInt(queestionData.getString("sport_in")));
            double height = Double.valueOf(queestionData.getString("height"));
            double weight = Double.valueOf(queestionData.getString("weight"));
            Double bf = weight/((height/ 100) * (height / 100));
			BigDecimal b = new BigDecimal(bf);
			float bmi = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
			totalApp.setBmi(bmi);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String createdate = sdf.format(date);
            YhJzServiceBodydataSwitch ok=yhtaskservuceapiservice.queryswitchbyid1(7,2, "健康风险评估");
            if(ok.getEvery_switch()==1) {
                YhManageTask task=new YhManageTask();
                task.setUsercode(totalMap.get("userCode").toString());
                Date d = null;
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                d = sd.parse(createdate);
                long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                task.setService_waiting_time(newtime);
                task.setService_type(7);
                task.setService_content(ok.getService_content());
                task.setService_record("完成健康风险评估");
                task.setReturnvisit(ok.getReturnvisit());
                task.setChildnodes(ok.getId());
                task.setChildtag("2");
                yhtaskservuceapiservice.insertperson(task);
            }
            return yhDiseaseAssessTotalReportAppService.insertIntoTotalReport(totalApp);
        } else {
            return 0;
        }

    }

   
    //判断家族中有人是否相关家族史
	private Integer getNewChoose(String[] str, JSONObject queestionData,String code) {
		Integer isdis = 2;
		for (int x = 0; x < str.length; x++) {
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append(code).append(str[x]);
			int checkValue = Integer.parseInt(StringUtils.isNotEmpty(queestionData.getString(sBuffer.toString()))?queestionData.getString(sBuffer.toString()):"2");
			if(checkValue==1){
				isdis = 1;
				break;
			}
		}
		return isdis;
	}
	
	/**
     * 反射类属性set数据
     *
     * @param personString json中的数据
     * @param object       要插入的类
     */
    public void doAssessReportApp(String personString, Object object) {
        JSONObject pensonObject = JSONObject.parseObject(personString);
        Set<String> keys = pensonObject.keySet();
        for (String key : keys) {
            String oldTeams = key;
            String newTeams = key.toLowerCase();
            if (newTeams.indexOf("_") >= 0) {
                newTeams = upperTable(key);
            }
            Collection<String> objectReportreflect;
            try {
                objectReportreflect = ReflectionUtil.getReflect(object);
                for (String string : objectReportreflect) {
                    if (string.equals(newTeams)) {
                        String doJsontoModel = pensonObject.getString(oldTeams);
                        ReflectionUtil.setFieldValue(object, string, doJsontoModel);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 插入疾病因素
     *
     * @param personString json中的数据
     */
    public void doAssessReportNewApp(String personString, String serialNumUUID, String userCode) {
        JSONObject pensonObject = JSONObject.parseObject(personString);
        Set<String> keys = pensonObject.keySet();
        for (String key : keys) {
            YhDiseaseRiskResultNewApp app = new YhDiseaseRiskResultNewApp();
            app.setRequestSerialNum(serialNumUUID);
            app.setUserCode(userCode);
            app.setResultName(key);
            app.setResultJson(pensonObject.getString(key));
            yhDiseaseRiskResultAppService.insertYhDiseaseRiskResultNew(app);
        }
    }
    
    
    /**
     * 替换_且首字母大写
     *
     * @param str
     * @return
     */
    public static String upperTable(String str) {
        // 字符串缓冲区
        StringBuffer sbf = new StringBuffer();
        // 如果字符串包含 下划线
        // 按下划线来切割字符串为数组
        String[] split = str.split("_");
        // 循环数组操作其中的字符串
        for (int i = 0, index = split.length; i < index; i++) {
            if (i != 0) {
                split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
            }
            sbf.append(split[i]);
        }
        // 返回
        return sbf.toString();
    }
    
    
 
}


