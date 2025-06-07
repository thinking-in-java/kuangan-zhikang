package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.yihe.common.config.Global;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.*;
import com.yihe.system.domain.YhUserAdminInfoExample.Criteria;
import com.yihe.system.domain.layim.User;
import com.yihe.system.mapper.*;
import com.yihe.system.service.*;
import com.yihe.system.service.impl.*;
import com.yihe.system.utils.Result;
import com.yihe.web.controller.tool.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/***
 * 页面跳转
 *
 * @author Royiwen
 *
 */
@Controller
@Api(value = "页面跳转", description = "页面跳转")
@RequestMapping("/manage")
public class UpmsJumpPageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsJumpPageController.class);
    @Autowired
    yHUserHemoglobinService yHUserHemoglobinService;
    @Autowired
    YhQuestionCheckJson2WebService yhQuestionCheckJson2WebService;
    @Resource
    YhUserRoleMapper yhUserRoleMapper;
    @Autowired
    IYhUserDeviceService IYhUserDeviceService;
    @Autowired
    YhDiseaseAssessTotalReport2Mapper yhDiseaseAssessTotalReport2Mapper;
    @Autowired
    YhUserPersonageConditionService YhUserPersonageConditionService;
    @Autowired
    YhDiseaseRiskResult2WebService yhDiseaseRiskResult2WebService;
    @Autowired
    YhUserPageSettingService yhuserpagesettingservice;
    @Autowired
    YhUserMakeCardService yhUserMakeCardService;
    @Autowired
    YhUserEnrolinfoServiceImpl YhUserEnrolinfoServiceImpl;
    @Autowired
    YhUserReginfoServiceImpl YhUserReginfoServiceImpl;
    @Autowired
    YHDocGroupServiceImpl YHDocGroupServiceImpl;
    @Autowired
    YhUserArteryService yhUserArteryService;
    @Autowired
    YhHeThresholdAlarmServiceImpl YhHeThresholdAlarmServiceImpl;
    @Autowired
    YHUserTemperatureService yHUserTemperatureService;
    @Autowired
    YHUserOxygenService yHUserOxygenService;
    @Autowired
    YHUserBmiService yHUserBmiService;
    @Autowired
    YHUserBloodPressureService yHUserBloodPressureService;
    @Autowired
    YHUserBloodsugerService yHUserBloodsugerService;
    @Autowired
    YHUserBodyFatService YHUserBodyFatService;
    @Autowired
    YHUserBloodfatService yHUserBloodfatService;
    @Autowired
    YHUserRenalfunctionService YHUserRenalfunctionService;
    @Autowired
    YHUserLiverfunctionService YHUserLiverfunctionService;
    @Autowired
    YHUserEcgService yHUserEcgService;
    @Autowired
    YHUserUbaService yHUserUbaService;
    @Autowired
    YHUserOtherfunctionService yHUserOtherfunctionService;
    @Autowired
    YhUserReginfoService yhUserReginfoService;
    @Autowired
    YhOrgTreeService yhOrgTreeService;
    @Autowired
    YhUserFileService yhUserFileService;
    @Autowired
    YhDiseaseAssessReportServiceImpl YhDiseaseAssessReportServiceImpl;
    @Autowired
    YhHomeinfoConfigService yhHomeinfoConfigService;
    @Autowired
    YhOrgTreeMapper yhOrgTreeMapper;
    @Autowired
    YhInterventionTemplateService yhInterventionTemplateService;
    // 心率
    @Autowired
    YHUserHeartRateService yHUserHeartRateService;
    @Autowired
    YhUserChatRecordWedServiceImpl YhUserChatRecordWedServiceImpl;
    @Autowired
    YhDiseaseCategoryMapper YhDiseaseCategoryMapper;
    @Autowired
    YhFoodSrecipeRecordMapper YhFoodSrecipeRecordMapper;
    @Autowired
    YhFoodUserScrecipeWebNewServiceImpl YhFoodUserScrecipeWebNewServiceImpl;
    @Autowired
    YhFodClassifenergyWebMapper YhFodClassifenergyWebMapper;
    @Autowired
    YhFoodCookBookMapper YhFoodCookBookMapper;
    @Autowired
    JiaiWatchInfoMapper JiaiWatchInfoMapper;
    @Autowired
    YhFoodUserDiaryMapper YhFoodUserDiaryMapper;
    @Autowired
    YhFoodCookedWebMapper YhFoodCookedWebMapper;
    @Autowired
    YhFoodSrecipeNewWebMapper YhFoodSrecipeNewWebMapper;
    @Autowired
    YhLifeHabitImprovementDictMapper yhLifeHabitImprovementDictMapper;
    @Autowired
    YhPainTotalReportService yhPainTotalReportService;
    @Autowired
    YhPainResolveMapper YhPainResolveMapper;
    @Autowired
    JiaiLocateMapper JiaiLocateMapper;
    @Autowired
    JiaiEnclosureMapper JiaiEnclosureMapper;
    @Autowired
    YhUserCellDetectionWebMapper YhUserCellDetectionWebMapper;
    @Autowired
    YhUserQuantumDetectionWebMapper YhUserQuantumDetectionWebMapper;
    @Autowired
    YhcheidentificationService yhcheidentificationService;
    @Autowired
    private YhUserAdminInfoService YhUserAdminInfoService;
    @Autowired
    private YhUserHealthyPlanServiceImpl yhUserHealthyPlanServiceImpl;
    @Autowired
    private YhFoodSrecipeRecordService yhFoodSrecipeRecordService;
    @Autowired
    private YhUserPageSettingService yhUserPageSettingService;
    @Autowired
    private YhManageTaskMapper yhManageTaskMapper;
    @Autowired
    private YhQuestionCheckJson2WebMapper YhQuestionCheckJson2WebMapper;
    @Autowired
    private YhPeriodComparisonService userService;
    @Autowired
    private DefaultLayIMService defaultLayIMService;
    @Autowired
    private YhSleepReportService yhSleepReportService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private YhOrgFacilityMapper yhOrgFacilityMapper;
    @Autowired
    private YhUserPermissionMapper yhUserPermissionMapper;
    @Resource
    private NewEmployeePersonalInformationCardService  newEmployeePersonalInformationCardService;

    /**
     * 计算2个日期之间相差的  以年、月、日为单位，各自计算结果是多少
     * 比如：2011-02-02 到  2017-03-02
     * 以年为单位相差为：6年
     * 以月为单位相差为：73个月
     * 以日为单位相差为：2220天
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int dayCompare(Date fromDate, Date toDate) {
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);
        //只要年月
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);

        int year = toYear - fromYear;
        int month = toYear * 12 + toMonth - (fromYear * 12 + fromMonth);
        int day = (int) ((to.getTimeInMillis() - from.getTimeInMillis()) / (24 * 3600 * 1000));
        return year;
    }

    public static int getAge(Date dateOfBirth) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(dateOfBirth)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(dateOfBirth);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;

    }

    @RequestMapping(value = "/log/loginLog", method = RequestMethod.GET)
    public String loginLog(Model model, String usercode) {
        String getimg = null,topimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            topimg = jsonObject.getString("21");
        }else{
            topimg = yhUserPageSettingService.getimg(21);
        }
        model.addAttribute("topimg", topimg);
        return "log/loginLog";
    }

    @ApiOperation(value = "不活跃用户")
    @RequestMapping(value = "/inactive/user", method = RequestMethod.GET)
    public String inactivity() {
        return "exportdata/inactivity";
    }

    @ApiOperation(value = "用户管理")
    @RequestMapping(value = "/user/index", method = RequestMethod.GET)
    public String userindex() {
        return "background/user";
    }

    @ApiOperation(value = "注册设备管理")
    @RequestMapping(value = "/guest/facility", method = RequestMethod.GET)
    public String facility(String id, Model model, String orgName) {
        model.addAttribute("id", id);
        model.addAttribute("orgName", orgName);
        model.addAttribute("num", yhOrgFacilityMapper.showOrgCodeListCount(id));
        return "organization/facility";
    }

    @ApiOperation(value = "习惯日志管理")
    @RequestMapping(value = "/diary/habit", method = RequestMethod.GET)
    public String diaryhabit(Model model, String strTime) {
        model.addAttribute("strTime", strTime);
        return "health/HabitDiary/index";
    }

    @ApiOperation(value = "睡眠Pdf导出")
    @RequestMapping(value = "/usertosee/sleepshowPdf", method = RequestMethod.GET)
    public String usertoseesleepshowPdf(Model model, String id) {

        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        YhSleepReport port = yhSleepReportService.showSleepDetail(Integer.valueOf(id));
        int nut = dayCompare(port.getBirthdate(), port.getQtime());
        port.setAge(nut);
        //1.把字符串类型的json数组对象转化JSONArray
        JSONArray json = JSONArray.fromObject(port.getReportDesc());
        String reportDesc = "";//现存问题
        //2、循环遍历这个数组
        for (int i = 0; i < json.size(); i++) {
            //3、把里面的对象转化为JSONObject
            net.sf.json.JSONObject job = json.getJSONObject(i);
            // 4、把里面想要的参数一个个用.属性名的方式获取到
            System.out.println(job.get("sleepExistingProblem"));
            reportDesc += job.get("sleepExistingProblem") + ",";
        }
        port.setResult(reportDesc);
        String src = "";//大类
        //2、循环遍历这个数组
        for (int i = 0; i < json.size(); i++) {
            //3、把里面的对象转化为JSONObject
            net.sf.json.JSONObject job = json.getJSONObject(i);
            // 4、把里面想要的参数一个个用.属性名的方式获取到
            Object scr = job.get("dataList");
            JSONArray json1 = JSONArray.fromObject(scr);
            for (int j = 0; j < json1.size(); j++) {
                net.sf.json.JSONObject job1 = json1.getJSONObject(j);
                reportDesc += job1.get("sleepExistingProblem") + ",";
                if (job1.get("sleepTitle") != null && !job1.get("sleepTitle").equals("")) {
                    src += job1.get("sleepTitle") + ",";
                }
            }

        }


        if (src != null && !src.equals("")) {
            Set set = new HashSet();
            String[] strArr = src.split(",");
            for (int j = 0; j < strArr.length; j++) {
                set.add(strArr[j]);
            }
            List list = new ArrayList(set);
            List<SleepVoJo> vojo = new ArrayList<SleepVoJo>();
            for (int i = 0; i < list.size(); i++) {
                SleepVoJo SleepVoJo = new SleepVoJo();
                SleepVoJo.setHeadline(String.valueOf(list.get(i)));
                vojo.add(SleepVoJo);
            }


            for (SleepVoJo sleepVoJo : vojo) {
                List<SubtitleVoJO> listsub = new ArrayList<SubtitleVoJO>();
                for (int i = 0; i < json.size(); i++) {
                    //3、把里面的对象转化为JSONObject
                    net.sf.json.JSONObject job = json.getJSONObject(i);
                    // 4、把里面想要的参数一个个用.属性名的方式获取到
                    Object scr = job.get("dataList");
                    JSONArray json1 = JSONArray.fromObject(scr);
                    for (int j = 0; j < json1.size(); j++) {
                        net.sf.json.JSONObject job1 = json1.getJSONObject(j);
                        if (String.valueOf(job1.get("sleepTitle")) == sleepVoJo.getHeadline() || String.valueOf(job1.get("sleepTitle")).equals(sleepVoJo.getHeadline())) {
                            SubtitleVoJO subtitle = new SubtitleVoJO();
                            if (String.valueOf(job1.get("sleepTitle")).equals("医生干预")) {
                                subtitle.setContent(String.valueOf(job1.get("sleepContent")));
                            } else {
                                String[] arr = String.valueOf(job1.get("sleepContent")).split("：");
                                subtitle.setSubtitle(arr[0]);
                                subtitle.setContent(arr[1]);
                            }
                            listsub.add(subtitle);
                        }


                    }
                }
                sleepVoJo.setSubtitle(listsub);
            }
            port.setVojo(vojo);
        }

        model.addAttribute("getrepotimg", getrepotimg);
        model.addAttribute("port", port);
        String[] result = port.getResult().split(",");
        model.addAttribute("result", result);
        return "personalUser/healthRecord/sleepParticularspdf";
    }

    @ApiOperation(value = "症状Pdf导出")
    @RequestMapping(value = "/usertosee/symptomAssessment", method = RequestMethod.GET)
    public String symptomAssessmentpdf(Model model, Integer reportId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<YhPainResolveWithBLOBs> reportList = new ArrayList<YhPainResolveWithBLOBs>();
        YhPainTotalReport totalReport = yhPainTotalReportService.selectByPrimaryKey(reportId);
        String createdate = sdf.format(totalReport.getQtime());
        totalReport.setCreatedate(createdate);
        arrayList.add(String.valueOf(totalReport.getCervicalResult()));
        arrayList.add(totalReport.getShoulderResult());
        arrayList.add(totalReport.getBackResult());
        arrayList.add(totalReport.getWaistResult());
        arrayList.add(totalReport.getWristResult());
        for (int i = 0; i < arrayList.size(); i++) {
            String resolveId = arrayList.get(i);
            YhPainResolveWithBLOBs yhPainResolve = YhPainResolveMapper.selectByPrimaryKey(Integer.valueOf(resolveId));
            reportList.add(yhPainResolve);
        }

        for (int i = 0; i < reportList.size(); i++) {
            if (reportList.get(i).getResolveSolution() != null) {
                String[] split = reportList.get(i).getResolveSolution().split(";");
                reportList.get(i).setResolveSolution2(split);
            }
            if (reportList.get(i).getTeachVideo() != null) {
                String[] video = reportList.get(i).getTeachVideo().split(";");
                reportList.get(i).setTeachVideo2(video);
            }

        }
        System.out.println(reportList);

        model.addAttribute("reportList", reportList);
        model.addAttribute("getrepotimg", getrepotimg);
        model.addAttribute("totalReport", totalReport);
        return "personalUser/healthRecord/symptomAssessment";

    }

    @ApiOperation(value = "习惯Pdf导出")
    @RequestMapping(value = "/usertosee/habitpdf", method = RequestMethod.GET)
    public String usertoseehabitpdf(Model model, Integer id) {
        YhLifeHabitImprovementDict dict = yhLifeHabitImprovementDictMapper.selectByPrimaryKey(id);


        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        YhUserReginfo userReginfo = yhUserReginfoService.selectByPrimaryKey(dict.getUserCode());
        int age = getAge(userReginfo.getBirthDate());
        model.addAttribute("age", age);
        Integer gender = userReginfo.getGender();
        String sex = "";
        if (gender == 1) {
            sex = "男";
        } else {
            sex = "女";
        }
        String[] title = dict.getTitle().split(",");
        model.addAttribute("title", title);
        model.addAttribute("titlelength", title.length);

        String[] analyzename = dict.getAnalyzename().split(",");
        model.addAttribute("analyzename", analyzename);
        model.addAttribute("analyzenamelength", analyzename.length);


        model.addAttribute("sex", sex);
        model.addAttribute("dict", dict);
        model.addAttribute("userReginfo", userReginfo);
        model.addAttribute("getrepotimg", getrepotimg);
        return "personalUser/healthRecord/habitParticulars";
    }

    @ApiOperation(value = "医生账号增加")
    @RequestMapping(value = "/doctor/doctoradd", method = RequestMethod.GET)
    public String doctordoctoradd(Model model, String id, String orgCode) {
        if (null != id) {
            model.addAttribute("userCode", id);
            model.addAttribute("orgCode", orgCode);
        }
        return "organization/doctoradd";
    }

    @RequestMapping("/getriskfactors")
    public String get(PeriodComparison model, Model mode) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        if (model.getSex() == 0) {
            mode.addAttribute("sex", "全部");
        } else if (model.getSex() == 1) {
            mode.addAttribute("sex", "男");
        } else if (model.getSex() == 2) {
            mode.addAttribute("sex", "女");
        }
        Map map = userService.get(model);

        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        mode.addAttribute("img", getrepotimg);
        mode.addAttribute("befor", map.get("befor"));
        mode.addAttribute("end", map.get("end"));
        List<Integer[]> list = (List<Integer[]>) map.get("list");
        mode.addAttribute("ganyuqiantime", model.getBefortime());

        mode.addAttribute("ganyuhoutime", model.getEndtime());
        mode.addAttribute("ageqian", model.getAgeStart() + "-" + model.getAgeEnd());

        mode.addAttribute("mancount", map.get("mancount"));
        mode.addAttribute("womancount", map.get("womancount"));
        String count = "";
        if (model.getRick()) {
            count += "	危险因素聚集情况";
        }
        if (model.getDisease()) {
            count += "	慢性疾病高风险";
        }
        mode.addAttribute("risk", model.getRick());
        mode.addAttribute("dissess", model.getDisease());
        mode.addAttribute("count", count);
        mode.addAttribute("list1", JSONObject.toJSON(list.get(0)));
        mode.addAttribute("list1man", JSONObject.toJSON(list.get(1)));
        mode.addAttribute("list1woman", JSONObject.toJSON(list.get(2)));
        mode.addAttribute("list2", JSONObject.toJSON(list.get(3)));
        mode.addAttribute("list2man", JSONObject.toJSON(list.get(4)));
        mode.addAttribute("list2woman", JSONObject.toJSON(list.get(5)));
        mode.addAttribute("list3", JSONObject.toJSON(list.get(6)));
        mode.addAttribute("list3man", JSONObject.toJSON(list.get(7)));
        mode.addAttribute("list3woman", JSONObject.toJSON(list.get(8)));
        mode.addAttribute("list4", JSONObject.toJSON(list.get(9)));
        mode.addAttribute("list4man", JSONObject.toJSON(list.get(10)));
        mode.addAttribute("list4woman", JSONObject.toJSON(list.get(11)));
        mode.addAttribute("allpeople", map.get("allpeople"));
        return "exportdata/timealysis";
    }

    @ApiOperation(value = "服务任务短信发送模板")
    @RequestMapping(value = "/acceptse/complete", method = RequestMethod.GET)
    public String acceptsecomplete(Model model, String checked) {
        String str = "";
        Set<String> set = new HashSet<String>();
        String[] strArr = checked.split(",");
        for (int i = 0; i < strArr.length; ++i) {
            str += strArr[i] + ",";
            String userCode = yhManageTaskMapper.selectplanCode(Integer.valueOf(strArr[i]));// 取user_code
            set.add(userCode);// 去重电话号码
        }
        model.addAttribute("checked", str);
        model.addAttribute("setall", set.size());
        return "taskservice/page";
    }

    @ApiOperation(value = "服务任务短信发送模板2")
    @RequestMapping(value = "/acceptse/complete2", method = RequestMethod.GET)
    public String acceptsecomplete2(Model model, String checked) {
        String str = "";
        Set<String> set = new HashSet<String>();
        String[] strArr = checked.split(",");
        for (int i = 0; i < strArr.length; ++i) {
            str += strArr[i] + ",";

            set.add(strArr[i]);// 去重电话号码
        }
        model.addAttribute("checked", str);
        model.addAttribute("setall", set.size());
        return "Interventionprograms/page";
    }

    @ApiOperation(value = "用户查看习惯查看")
    @RequestMapping(value = "/usertosee/habit", method = RequestMethod.GET)
    public String usertoseehabit(Model model, String usercode, @RequestParam(required = false) String type) {
        model.addAttribute("usercode", usercode);
        if (null != type) {
            model.addAttribute("type", type);
        }
        return "personalUser/healthRecord/usertoseeHabit";
    }

    @ApiOperation(value = "习惯评估管理")
    @RequestMapping(value = "/habit/habitAssess", method = RequestMethod.GET)
    public String habitAssess(Model model, String strTime) {
        model.addAttribute("strTime", strTime);
        return "health/HabitAssess/index";
    }

    @ApiOperation(value = "睡眠评估管理")
    @RequestMapping(value = "/sleepassess/pipe", method = RequestMethod.GET)
    public String sleepassess() {
        return "health/SleepPipe/index";
    }

    @ApiOperation(value = "账号信息管理")
    @RequestMapping(value = "/guest/accountinformation", method = RequestMethod.GET)
    public String accountinformation(Model model, String type) {
        if (null != type) {
            model.addAttribute("type", type);
        } else {
            model.addAttribute("type", "0");
        }
        return "organization/account";
    }

    @ApiOperation(value = "用户查看睡眠分析报告")
    @RequestMapping(value = "/usertosee/Sleepanalyze", method = RequestMethod.GET)
    public String usertoseeSleepanalyze(Model model, String id) {

        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        model.addAttribute("id", id);
        model.addAttribute("getrepotimg", getrepotimg);
        return "personalUser/healthRecord/sleepAnalysis";
    }

    @ApiOperation(value = "用户查看习惯分析报告")
    @RequestMapping(value = "/usertosee/habitAnalysis", method = RequestMethod.GET)
    public String usertoseehabitAnalysis(Model model, String id) {

        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        model.addAttribute("id", id);
        model.addAttribute("getrepotimg", getrepotimg);
        return "personalUser/healthRecord/habitAnalysis";
    }

    @ApiOperation(value = "用户查看运动分析报告")
    @RequestMapping(value = "/usertosee/sportanalyze", method = RequestMethod.GET)
    public String usertoseesportanalyze(Model model, String id) {

        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        model.addAttribute("id", id);
        model.addAttribute("getrepotimg", getrepotimg);
        return "personalUser/healthRecord/sportAnalysis";
    }

    @ApiOperation(value = "用户查看膳食分析报告")
    @RequestMapping(value = "/usertosee/foodanalyze", method = RequestMethod.GET)
    public String usertoseefoodanalyze(Model model, String id) {

        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        model.addAttribute("id", id);
        model.addAttribute("getrepotimg", getrepotimg);
        return "personalUser/healthRecord/dietAnalysis";
    }

    @ApiOperation(value = "用户查看习惯分析报告")
    @RequestMapping(value = "/usertosee/habitanalyze", method = RequestMethod.GET)
    public String usertoseehabitanalyze(Model model, String id) {
        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        model.addAttribute("id", id);
        model.addAttribute("getrepotimg", getrepotimg);
        return "personalUser/healthRecord/habitAnalysis";
    }

    @ApiOperation(value = "鏈烘瀯棣栭〉")
    @RequestMapping(value = "/organization/console", method = RequestMethod.GET)
    public String organizationconsole() {
        return "organization/index";
    }

    @ApiOperation(value = "绠＄悊鍛樿处鍙蜂慨鏀�")
    @RequestMapping(value = "/doctor/AdministratorAmend", method = RequestMethod.GET)
    public String doctorAdministratorAmend(Model model, String id, String orgname) {
        model.addAttribute("userCode", id);
        model.addAttribute("orgname", orgname);
        return "organization/AdministratorAmend";
    }

    @ApiOperation(value = "鐢ㄦ埛璐﹀彿淇敼")
    @RequestMapping(value = "/doctor/UserAndphysical", method = RequestMethod.GET)
    public String doctorbasicAndphysical(String userCode, Model model) {
        model.addAttribute("userCode", userCode);
        YhUserReginfo userReginfo = yhUserReginfoService.selectByPrimaryKey(userCode);
        if (userReginfo != null) {
            String orgFullName = yhOrgTreeService.selectFullOrgName(userReginfo.getOrgCode(), new String());
            model.addAttribute("orgFullName", orgFullName);
        }
        String cardId = yhOrgTreeService.selectcardid(userCode);
        String selecendtime = yhOrgTreeService.selectadddate(cardId);
        String linetimes = yhOrgTreeService.selectlinetime(cardId);
        model.addAttribute("AddDate", selecendtime);
        model.addAttribute("linetimes", linetimes);
        return "organization/UserAndphysical";
    }

    @ApiOperation(value = "鐫＄湢鏃ヨ绠＄悊")
    @RequestMapping(value = "/diary/sleep", method = RequestMethod.GET)
    public String diarysleep(Model model, String strTime) {
        model.addAttribute("strTime", strTime);
        return "health/SleepDiary/index";
    }

    @ApiOperation(value = "鐢ㄦ埛鏌ョ湅鐫＄湢绠＄悊")
    @RequestMapping(value = "/usertosee/sleepshow", method = RequestMethod.GET)
    public String usertoseesleepshow(Model model, String id) {
        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        model.addAttribute("getrepotimg", getrepotimg);
        model.addAttribute("id", id);
        return "personalUser/healthRecord/sleepParticulars";
    }

    @ApiOperation(value = "用户查看睡眠报告查看")
    @RequestMapping(value = "/usertosee/sleep", method = RequestMethod.GET)
    public String usertoseesleep(Model model, String usercode, @RequestParam(required = false) String type) {
        model.addAttribute("usercode", usercode);
        if (null != type) {
            model.addAttribute("type", type);
        }
        return "personalUser/healthRecord/usertoseesleep";
    }

    @ApiOperation(value = "用户查看运动套餐查看")
    @RequestMapping(value = "/usertosee/sportshow", method = RequestMethod.GET)
    public String usertoseesportshow(Model model, String id) {
        model.addAttribute("id", id);
        return "personalUser/healthRecord/usertoseesportshow";
    }

    @ApiOperation(value = "用户查看运动管理")
    @RequestMapping(value = "/usertosee/sport", method = RequestMethod.GET)
    public String usertoseesport(Model model, String usercode, @RequestParam(required = false) String type) {
        model.addAttribute("usercode", usercode);
        if (null != type) {
            model.addAttribute("type", type);
        }
        return "personalUser/healthRecord/usertoseesport";
    }

    @ApiOperation(value = "用户查看膳食管理查看")
    @RequestMapping(value = "/usertosee/foodshow", method = RequestMethod.GET)
    public String usertoseefoodshow(Model model, String id) {
        model.addAttribute("id", id);
        return "personalUser/healthRecord/usertoseefoodshow";
    }

    @ApiOperation(value = "用户查看膳食管理")
    @RequestMapping(value = "/usertosee/food", method = RequestMethod.GET)
    public String usertoseefood(Model model, String usercode, @RequestParam(required = false) String type) {
        model.addAttribute("usercode", usercode);
        List<String> findElevelInfoGroupBy = YhFoodSrecipeRecordMapper.findElevelInfoGroupBy();
        if (null != type) {
            model.addAttribute("type", type);
        }
        model.addAttribute("findElevelInfoGroupBy", findElevelInfoGroupBy);
        return "personalUser/healthRecord/usertoseefood";
    }

    @ApiOperation(value = "主页信息配置")
    @RequestMapping(value = "/homepage/index", method = RequestMethod.GET)
    public String settingindex(Model model) {
        int number = yhHomeinfoConfigService.selectByslideshownumber();
        model.addAttribute("number", number);
        return "organization/setting/index";
    }

    @ApiOperation(value = "运动库管理增加")
    @RequestMapping(value = "/content/moveladddit", method = RequestMethod.GET)
    public String sportadddit(int id, Model model) {
        model.addAttribute("id", id);
        return "organization/audit/sportadd";
    }

    @ApiOperation(value = "食物 增加")
    @RequestMapping(value = "/content/foodladddit", method = RequestMethod.GET)
    public String foodadddit(int id, Model model) {
        model.addAttribute("id", id);
        return "organization/audit/foodadd";
    }

    @ApiOperation(value = "审核管理")
    @RequestMapping(value = "/content/manageaudit", method = RequestMethod.GET)
    public String manageaudit() {
        return "organization/audit/audit";
    }

    @ApiOperation(value = "评论管理")
    @RequestMapping(value = "/content/comments", method = RequestMethod.GET)
    public String comments() {
        return "organization/comment/comments";
    }

    @ApiOperation(value = "习惯评论管理")
    @RequestMapping(value = "/content/Habitcomments", method = RequestMethod.GET)
    public String Habitcomments() {
        return "organization/comment/Habitcomments";
    }

    @ApiOperation(value = "运动库管理")
    @RequestMapping(value = "/content/movel", method = RequestMethod.GET)
    public String sport() {
        return "organization/sport/sport";
    }

    @ApiOperation(value = "运动库管理增加")
    @RequestMapping(value = "/content/moveladd", method = RequestMethod.GET)
    public String sportadd(int id, Model model) {
        model.addAttribute("id", id);
        return "organization/sport/sportadd";
    }

    @ApiOperation(value = "食物 增加")
    @RequestMapping(value = "/content/foodladd", method = RequestMethod.GET)
    public String foodadd(int id, Model model) {
        model.addAttribute("id", id);
        // 疾病集合
        List<YhDiseaseCategory> selectByExample = YhDiseaseCategoryMapper.selectByPrimaryKeyByIsAssessment();
        model.addAttribute("selectByExample", selectByExample);
        return "organization/food/foodadd";
    }

    @ApiOperation(value = "菜肴增加")
    @RequestMapping(value = "/content/foodladd2", method = RequestMethod.GET)
    public String foodadd2(int id, Model model) {
        model.addAttribute("id", id);
        // 疾病集合
        List<YhDiseaseCategory> selectByExample = YhDiseaseCategoryMapper.selectByPrimaryKeyByIsAssessment();
        model.addAttribute("selectByExample", selectByExample);
        return "organization/food/foodadd2";
    }

    @ApiOperation(value = "食物库管理")
    @RequestMapping(value = "/content/foodl", method = RequestMethod.GET)
    public String foodl(Model model, String fanhui) {
        model.addAttribute("fanhui", fanhui);
        // 疾病集合
        List<YhDiseaseCategory> selectByExample = YhDiseaseCategoryMapper
                .selectByExample(new YhDiseaseCategoryExample());
        model.addAttribute("dCategory", selectByExample);
        List<String> findFoodUserScrecipeGroupLevel = YhFoodUserScrecipeWebNewServiceImpl
                .findFoodUserScrecipeGroupLevel();
        model.addAttribute("todayFoodGroup", findFoodUserScrecipeGroupLevel);
        return "organization/food/food";
    }

    @ApiOperation(value = "习惯库管理")
    @RequestMapping(value = "/content/habit", method = RequestMethod.GET)
    public String habit(Model model, String type) {
        if (null != type) {
            model.addAttribute("type", type);
        } else {
            model.addAttribute("type", "0");
        }

        return "organization/habit/habitIndex";
    }

    @ApiOperation(value = "习惯库管理新增")
    @RequestMapping(value = "/content/customadd", method = RequestMethod.GET)
    public String customadd(int id, Model model) {
        model.addAttribute("id", id);
        return "organization/habit/customadd";
    }

    @ApiOperation(value = "习惯分类库管理新增")
    @RequestMapping(value = "/content/habitadd", method = RequestMethod.GET)
    public String habitadd(int id, Model model) {
        model.addAttribute("id", id);
        return "organization/habit/habitadd";
    }

    @ApiOperation(value = "健康课堂管理")
    @RequestMapping(value = "/content/healthclass", method = RequestMethod.GET)
    public String healthclass() {
        return "organization/classroom/classroom";
    }

    @ApiOperation(value = "健康课堂管理新增")
    @RequestMapping(value = "/content/healthclassadd", method = RequestMethod.GET)
    public String healthclassadd(int id, String tips, Model model) {
        model.addAttribute("id", id);
        if (tips != null && !tips.equals("")) {
            model.addAttribute("tips", tips);
        }
        return "organization/classroom/classroomadd";
    }

    @ApiOperation(value = "精彩活动管理")
    @RequestMapping(value = "/content/activity", method = RequestMethod.GET)
    public String activity() {
        return "organization/content/activity";
    }

    @ApiOperation(value = "精彩活动管理新增")
    @RequestMapping(value = "/content/activityadd", method = RequestMethod.GET)
    public String activityadd(int id, Model model) {
        model.addAttribute("id", id);
        return "organization/content/activityadd";
    }

    @ApiOperation(value = "客户信息管理")
    @RequestMapping(value = "/guest/clientinformation", method = RequestMethod.GET)
    public String organization(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        String orgCode = yh.getOrgCode();// 取到当前用户orgcode
        model.addAttribute("orgCode", orgCode);
        return "organization/client";
    }

    @ApiOperation(value = "客户信息管理新增")
    @RequestMapping(value = "/guest/clientinformationadd", method = RequestMethod.GET)
    public String clientinformationadd(String id, Model model) {
        if (id == null || id.equals("") || id.equals("0")) {
            Subject subject = SecurityUtils.getSubject();
            YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
            String orgCode = yh.getOrgCode();// 取到当前用户orgcode
            model.addAttribute("id", orgCode);
        } else {
            model.addAttribute("id", id);
        }
        String adddata = yhOrgTreeMapper.showAddDate(String.valueOf(id));
        if (adddata == null || adddata.equals("")) {
            model.addAttribute("type", "1");
        } else {
            model.addAttribute("adddata", adddata);
        }
        return "organization/clientadd";
    }

    @ApiOperation(value = "客户信息管理修改")
    @RequestMapping(value = "/guest/clientamend", method = RequestMethod.GET)
    public String clientamend(String id, Model model) {
        model.addAttribute("id", id);
        return "organization/clientamend";
    }

    @ApiOperation(value = "角色管理")
    @RequestMapping(value = "/role/index", method = RequestMethod.GET)
    public String roleindex(Integer totab, Model model) {
        model.addAttribute("totab", totab);
        return "background/role";
    }

    @ApiOperation(value = "权限管理")
    @RequestMapping(value = "/user/authorities", method = RequestMethod.GET)
    public String permissionindex() {
        return "background/authorities";
    }

    @ApiOperation(value = "客户服务设置")
    @RequestMapping(value = "/setting/clientservice", method = RequestMethod.GET)
    public String clientservice() {
        return "advancedconfiguration/clientservice";
    }

    @ApiOperation(value = "微信消息设置")
    @RequestMapping(value = "/setting/WeChatwx", method = RequestMethod.GET)
    public String WeChatwx() {
        return "advancedconfiguration/WeChatwx";
    }

    @ApiOperation(value = "修改客户服务配置")
    @RequestMapping(value = "/setting/changeclientservice", method = RequestMethod.GET)
    public String changeclientservice() {
        return "advancedconfiguration/changeclientservice";
    }

    @ApiOperation(value = "储存配置")
    @RequestMapping(value = "/setting/storagesetting", method = RequestMethod.GET)
    public String storagesetting() {
        return "advancedconfiguration/storagesetting";
    }

    @ApiOperation(value = "页面信息配置")
    @RequestMapping(value = "/setting/page", method = RequestMethod.GET)
    public String datamonitoring(ModelMap modelMap) {
        List<YhDicIntroduction> yhdic = yhuserpagesettingservice.selectallduction();
        modelMap.put("yhdic", yhdic);
        return "seniorsetting/datamonitoring";
    }

    @ApiOperation(value = "注册卡信息管理")
    @RequestMapping(value = "/register/registrinecardinfo", method = RequestMethod.GET)
    public String registrinecardinfo() {
        return "seniorsetting/registrationcard";
    }

    @ApiOperation(value = "健康评估管理")
    @RequestMapping(value = "/health/healthAssessment", method = RequestMethod.GET)
    public String healthAssessment(String url, Model model, String strTime) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();

        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl
                .selectByPrimaryKeyByDocInfo(userCode);

        model.addAttribute("url", url);
        model.addAttribute("strTime", strTime);
        model.addAttribute("selectAllInfo", selectAllInfo);
        return "health/assess/assessment";
    }

    @ApiOperation(value = "注册管理")
    @RequestMapping(value = "/register/manageinsert", method = RequestMethod.GET)
    public String manageinsert(HttpServletRequest request, Model model) {
        // 取当前登录用户名
        YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        String userName = yhUserReginfo.getUserCode();
        // 注册卡总数
        int makeCardCount = yhUserMakeCardService.selectYhUserMakeCardCount(userName);
        model.addAttribute("makeCardCount", makeCardCount);
        // 注册卡已使用数量
        int makeCardEmploy = yhUserMakeCardService.selectYhUserMakeCardEmploy(userName);
        model.addAttribute("makeCardEmploy", makeCardEmploy);
        // 注册卡剩余
        int makeCardSurplus = yhUserMakeCardService.selectYhUserMakeCardSurplus(userName);
        model.addAttribute("makeCardSurplus", makeCardSurplus);
        return "registrationmanagement/regManage";
    }

    @ApiOperation(value = "注册页面")
    @RequestMapping(value = "register/registerPage", method = RequestMethod.GET)
    public String register(Model model, String cardId, String orgId) {
        model.addAttribute("cardId", cardId);
        model.addAttribute("orgId", orgId);

        // YhUserReginfo userReginfo =
        // yhUserReginfoService.selectByPrimaryKey(orgId);
        String selecendtime = yhOrgTreeService.selectadddate(cardId);
        String orgFullName = yhOrgTreeService.selectFullOrgName(orgId, new String());
        model.addAttribute("orgFullName", orgFullName);
        model.addAttribute("AddDate", selecendtime);
        return "registrationmanagement/register";
    }

    @ApiOperation(value = "个人用户首页")
    @RequestMapping(value = "register/userIndex", method = RequestMethod.GET)
    public String personageIndex(String page, String type2, Model model, String userCode, String orgId, String serviceid)
            throws ParseException {
        String getimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        String topimg, leftimg;
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            topimg = jsonObject.getString("21");
            leftimg = jsonObject.getString("20");
        }else{
            topimg = yhUserPageSettingService.getimg(21);
            leftimg = yhUserPageSettingService.getimg(20);
        }
        model.addAttribute("topimg", topimg);
        model.addAttribute("leftimg", leftimg);
        model.addAttribute("page", page);
        model.addAttribute("userCode", userCode);
        model.addAttribute("orgId", orgId);
        model.addAttribute("type2", type2);
        model.addAttribute("serviceid", serviceid);
        YhUserReginfo userReginfo = yhUserReginfoService.selectByPrimaryKey(userCode);
        int age = getAge(userReginfo.getBirthDate());
        model.addAttribute("age", age);
        model.addAttribute("userReginfo", userReginfo);
        InformationSummary selectUserInfo = selectUserInfo2(userReginfo);
        model.addAttribute("selectUserInfo", selectUserInfo);

        return "personalUser/personageIndex";
    }

    public InformationSummary selectUserInfo2(YhUserReginfo userReginfo) {
        InformationSummary info = new InformationSummary();

        Date birthDate = userReginfo.getBirthDate();
        int ageByBirth = getAgeByBirth(birthDate);
        info.setAge(ageByBirth);
        return info;
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

    @ApiOperation(value = "健康档案")
    @RequestMapping(value = "register/healthRecord", method = RequestMethod.GET)
    public String healthRecord(String userCode, Model model, String orgId, String type) throws ParseException {
        model.addAttribute("userCode", userCode);
        model.addAttribute("orgId", orgId);
        model.addAttribute("type", type);
        YhUserReginfo userReginfo = yhUserReginfoService.selectByPrimaryKey(userCode);
        model.addAttribute("userCode", userCode);
        // 用户体温
        Map<String, Object> temperatureMap = yHUserTemperatureService.selectUserAnimalHeat(userCode);
        model.addAttribute("temperatureMap", temperatureMap);
        // 用户血氧
        Map<String, Object> OxygenMap = yHUserOxygenService.selectYHUserOxygen(userCode);
        model.addAttribute("OxygenMap", OxygenMap);

        // 用户体重
        Map<String, Object> tzMap = yHUserBmiService.selectYHUserBmi(userCode);
        model.addAttribute("tzMap", tzMap);

        // 用户血压
        Map<String, Object> xyMap = yHUserBloodPressureService.selectYHUserBloodPressure(userCode);
        model.addAttribute("xyMap", xyMap);
        // 用户血糖
        Map<String, Object> xtMap = yHUserBloodsugerService.selectYHUserBloodsuger(userCode);
        model.addAttribute("xtMap", xtMap);
        // 用户体脂
        Map<String, Object> bodyFatMap = YHUserBodyFatService.selectYHUserBodyFat(userCode);
        model.addAttribute("bodyFatMap", bodyFatMap);
        // 用户血脂
        Map<String, Object> bloodfatFatMap = yHUserBloodfatService.selectYHUserBloodfat(userCode);
        model.addAttribute("bloodfatFatMap", bloodfatFatMap);

        // 用户肾功能
        Map<String, Object> functionMap = YHUserRenalfunctionService.selectYHUserRenalfunction(userCode);
        model.addAttribute("functionMap", functionMap);
        // 用户心率
        Map<String, Object> xinlvMap = yHUserHeartRateService.selectHeartRate(userCode);
        model.addAttribute("xinlvMap", xinlvMap);

        // 用户肝功能
        Map<String, Object> liverMap = YHUserLiverfunctionService.selectYHUserLiverfunction(userCode);
        model.addAttribute("liverMap", liverMap);

        // 用户心电图
        Map<String, Object> EcgMap = yHUserEcgService.selectYHUserEcg(userCode);
        model.addAttribute("EcgMap", EcgMap);
        // 用户尿检
        Map<String, Object> UbaMap = yHUserUbaService.selectYHUserUba(userCode);
        model.addAttribute("UbaMap", UbaMap);
        // 用户其他
        Map<String, Object> qtMap = yHUserOtherfunctionService.selectYHUserOtherfunction(userCode);
        model.addAttribute("qtMap", qtMap);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        YhUserArtery yhUserArtery = yhUserArteryService.selectUserYhUserArteryByLimitParm(userCode);
		/*		if(yhUserArtery!=null) {
					String createdate = sdf2.format(yhUserArtery.getTime());
					yhUserArtery.setCreatedate(createdate);
				}
				*/
        model.addAttribute("yhUserArtery", yhUserArtery);

        //血红蛋白
        Map<String, Object> hemoglobinMap = yHUserHemoglobinService.selectUserHemoglobin(userCode);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式转换
        if (hemoglobinMap != null) {
            String bdate = sd.format((Date) hemoglobinMap.get("he_data"));
            hemoglobinMap.put("bdate", bdate);
        }
        model.addAttribute("hemoglobinMap", hemoglobinMap);

        //细胞检测
        YhUserCellDetectionWeb cellDeteInfoList = YhUserCellDetectionWebMapper.findCellDeteInfoList(userCode);
        model.addAttribute("cdMap", cellDeteInfoList);
        //量子检测
        YhUserQuantumDetectionWeb quantumInfoList = YhUserQuantumDetectionWebMapper.findQuantumInfoList(userCode);
        model.addAttribute("quMap", quantumInfoList);

        if (orgId != null) {
            String orgFullName = yhOrgTreeService.selectFullOrgName(orgId, new String());
            model.addAttribute("orgFullName", orgFullName);
        }
        String usercode = userCode;
        if (StringUtil.isNotEmpty(usercode)) {
            InformationSummary selectUserInfo = YhUserReginfoServiceImpl.selectUserInfo(usercode);
            // System.out.println(selectUserInfo.getGoodList());
            model.addAttribute("selectUserInfo", selectUserInfo);

        }
        YhQuestionCheckJson2Web questionCheckJson2Web = yhQuestionCheckJson2WebService.selectUserByParam(userCode);
        String result = "";
        if (questionCheckJson2Web == null) {
            result = "2";
        }
        if (questionCheckJson2Web != null) {
            result = "1";

            // 居住地
            String place = "";
            // 空气质量
            String airQuality = "";
            // 平均每天吃多少新鲜蔬菜
            String freshvegetables = "";
            // 平均每天吃多少新鲜水果
            String fruits = "";
            String checkJson = questionCheckJson2Web.getUserCheckAnswer();
            JSONObject object = JSONObject.parseObject(checkJson);
            //环境
            String livingenvir = object.getJSONObject("person").getString("living_envir");
            String livingenvir2 = "";
            if ("0".equals(livingenvir)) {
                livingenvir2 = "不寒冷，不潮湿";
            }
            if ("1".equals(livingenvir)) {
                livingenvir2 = "寒冷潮湿";
            }
            model.addAttribute("livingenvir2", livingenvir2);


            // 居住环境
            String jzhj = object.getJSONObject("person").getString("jzhj");
            String jzd = object.getJSONObject("person").getString("jzd");
            // 空气质量
            String kqzl = object.getString("pm25");
            if ("2".equals(jzd) && "1".equals(jzhj)) {
                place = "北方城市";
            }
            if ("2".equals(jzd) && "2".equals(jzhj)) {
                place = "北方农村";
            }
            if ("1".equals(jzd) && "1".equals(jzhj)) {
                place = "南方城市";
            }
            if ("1".equals(jzd) && "2".equals(jzhj)) {
                place = "南方农村";
            }
            if ("2".equals(kqzl)) {
                airQuality = "差";
            }
            if ("1".equals(kqzl)) {
                airQuality = "优良";
            }

            model.addAttribute("place", place);
            model.addAttribute("airQuality", airQuality);
            // 蔬菜
            String food_vegetables = object.getString("food_vegetables");
            if ("1".equals(food_vegetables)) {
                freshvegetables = "不吃";
            }
            if ("2".equals(food_vegetables)) {
                freshvegetables = "少于50克";
            }
            if ("3".equals(food_vegetables)) {
                freshvegetables = "50-150克";
            }
            if ("4".equals(food_vegetables)) {
                freshvegetables = "150-300克";
            }
            if ("5".equals(food_vegetables)) {
                freshvegetables = "300克以上";
            }
            model.addAttribute("freshvegetables", freshvegetables);
            // 平均每天吃多少水果
            String foodfruits = object.getString("food_fruits");
            if ("1".equals(foodfruits)) {
                fruits = "不吃";
            }
            if ("2".equals(foodfruits)) {
                fruits = "少于50克";
            }
            if ("3".equals(foodfruits)) {
                fruits = "50-100克";
            }
            if ("4".equals(foodfruits)) {
                fruits = "100-200克";
            }
            if ("5".equals(foodfruits)) {
                fruits = "200克以上";
            }

            model.addAttribute("fruits", fruits);

            // 牛奶或酸奶
            String food_milk = object.getString("food_milk");
            String milk = "";
            if ("1".equals(food_milk)) {
                milk = "不喝";
            }
            if ("2".equals(food_milk)) {
                milk = "少于0.5杯";
            }
            if ("3".equals(food_milk)) {
                milk = "0.5-1杯";
            }
            if ("4".equals(food_milk)) {
                milk = "1-2杯 ";
            }
            if ("5".equals(food_milk)) {
                milk = "2杯以上";
            }
            model.addAttribute("milk", milk);

            // 奶制品
            String food_dairy = object.getString("food_dairy");
            String dairyproducts = "";
            if ("1".equals(food_dairy)) {
                dairyproducts = "不吃";
            }
            if ("2".equals(food_dairy)) {
                dairyproducts = "少于20克 ";
            }
            if ("3".equals(food_dairy)) {
                dairyproducts = "20-40克";
            }
            if ("4".equals(food_dairy)) {
                dairyproducts = "40-80克";
            }
            if ("5".equals(food_dairy)) {
                dairyproducts = "80克以上";
            }
            model.addAttribute("dairyproducts", dairyproducts);
            // 饮茶
            String drink_tea_cnt = object.getString("drink_tea_cnt");
            String tea = "";
            if ("1".equals(drink_tea_cnt)) {
                tea = "几乎不饮茶";
            }
            if ("2".equals(drink_tea_cnt)) {
                tea = "不超过1杯";
            }
            if ("3".equals(drink_tea_cnt)) {
                tea = "1杯以上";
            }
            model.addAttribute("tea", tea);

            // 油炸食品
            String food_fired = object.getString("food_fired");
            String fired = "";
            if ("1".equals(food_fired)) {
                fired = "不吃";
            }
            if ("2".equals(food_fired)) {
                fired = "1-4次";
            }
            if ("3".equals(food_fired)) {
                fired = "5-7次";
            }
            if ("4".equals(food_fired)) {
                fired = "7次以上";
            }
            model.addAttribute("fired", fired);
            // 甜点
            String food_sweet = object.getString("food_sweet");
            String sweet = "";
            if ("1".equals(food_sweet)) {
                sweet = "不吃";
            }
            if ("2".equals(food_sweet)) {
                sweet = "1-4次";
            }
            if ("3".equals(food_sweet)) {
                sweet = "5-7次";
            }
            if ("4".equals(food_sweet)) {
                sweet = "7次以上";
            }
            model.addAttribute("sweet", sweet);
            // 含糖饮料
            String food_sugary = object.getString("food_sugary");
            String sugary = "";
            if ("1".equals(food_sugary)) {
                sugary = "不喝";
            }
            if ("2".equals(food_sugary)) {
                sugary = "1-4次";
            }
            if ("3".equals(food_sugary)) {
                sugary = "5-7次";
            }
            if ("4".equals(food_sugary)) {
                sugary = "7次以上";
            }
            model.addAttribute("sugary", sugary);
            // 咸菜或腌渍食品
            String food_pickled = object.getString("food_pickled");
            String pickled = "";
            if ("1".equals(food_pickled)) {
                pickled = "不吃";
            }
            if ("2".equals(food_pickled)) {
                pickled = "1-4次";
            }
            if ("3".equals(food_pickled)) {
                pickled = "5-7次";
            }
            if ("4".equals(food_pickled)) {
                pickled = "7次以上";
            }
            model.addAttribute("pickled", pickled);

            // 咸菜或腌渍食品
            String seafood_cnt = object.getString("seafood_cnt");
            String seafood = "";
            if ("1".equals(seafood_cnt)) {
                seafood = "不吃";
            }
            if ("2".equals(seafood_cnt)) {
                seafood = "1-2次";
            }
            if ("3".equals(seafood_cnt)) {
                seafood = "2-4次";
            }
            if ("4".equals(seafood_cnt)) {
                seafood = "4次以上";
            }
            model.addAttribute("seafood", seafood);
            // 是否吸烟
            String smoke_status = object.getString("smoke_status");
            String smoke = "";
            if ("1".equals(smoke_status)) {
                smoke = "是";
            }
            if ("2".equals(smoke_status)) {
                smoke = "否";
            }
            if ("3".equals(smoke_status)) {
                smoke = "已戒烟";
            }

            model.addAttribute("smoke", smoke);
            model.addAttribute("smoke_status", smoke_status);
            // 开始吸烟时的年龄（岁）
            String smoke_begin_age = object.getString("smoke_begin_age");
            // 平均每日吸烟量（支）
            String smoke_amount = object.getString("smoke_amount");

            model.addAttribute("smoke_begin_age", smoke_begin_age);
            model.addAttribute("smoke_amount", smoke_amount);
            // 二手烟
            String smoke_sencod = object.getString("smoke_sencod");
            String smokesencod = "";
            if ("1".equals(smoke_sencod)) {
                smokesencod = "是";
            }
            if ("2".equals(smoke_sencod)) {
                smokesencod = "否";
            }
            model.addAttribute("smokesencod", smokesencod);
            // 戒烟年龄
            String smoke_end_age = object.getString("smoke_end_age");
            model.addAttribute("smoke_end_age", smoke_end_age);
            // 喝酒吗
            String food_drink = object.getString("food_drink");
            String drink = "";
            if ("1".equals(food_drink)) {
                drink = "是";
            }
            if ("2".equals(food_drink)) {
                drink = "否";
            }
            model.addAttribute("drink", drink);
            model.addAttribute("food_drink", food_drink);
            // 每周喝酒几次
            String food_drink_times = object.getString("food_drink_times");
            String fooddrinktimes = "";
            if ("1".equals(food_drink_times)) {
                fooddrinktimes = "1-2次";
            }
            if ("2".equals(food_drink_times)) {
                fooddrinktimes = "3-5次";
            }
            if ("3".equals(food_drink_times)) {
                fooddrinktimes = ">5次";
            }
            model.addAttribute("fooddrinktimes", fooddrinktimes);
            // 平均每次喝多少酒
            String food_drink_beer = object.getString("food_drink_beer");
            String fooddrinkbeer = "";
            if ("1".equals(food_drink_beer)) {
                fooddrinkbeer = "少于300毫升";
            }
            if ("2".equals(food_drink_beer)) {
                fooddrinkbeer = "300-600毫升";
            }
            if ("3".equals(food_drink_beer)) {
                fooddrinkbeer = "600-900毫升";
            }
            if ("4".equals(food_drink_beer)) {
                fooddrinkbeer = "900毫升以上";
            }
            model.addAttribute("fooddrinkbeer", fooddrinkbeer);
            // 是否参加运动锻炼
            String sport_in = object.getString("sport_in");
            String sport = "";
            if ("1".equals(sport_in)) {
                sport = "参加过";
            }
            if ("2".equals(sport_in)) {
                sport = "没有参加过";
            }
            model.addAttribute("sport", sport);
            model.addAttribute("sport_in", sport_in);
            // 运动锻炼的频率
            String sport_freq = object.getString("sport_freq");
            String sportfreq = "";
            if ("1".equals(sport_freq)) {
                sportfreq = "每月少于1次";
            }
            if ("2".equals(sport_freq)) {
                sportfreq = "每月至少1次，但每周不足1次";
            }
            if ("3".equals(sport_freq)) {
                sportfreq = "每周1-2次";
            }
            if ("4".equals(sport_freq)) {
                sportfreq = "每周3-4次 ";
            }
            if ("5".equals(sport_freq)) {
                sportfreq = "每周5次以上 ";
            }
            model.addAttribute("sportfreq", sportfreq);
            // 锻炼的持续时间
            String sport_time = object.getString("sport_time");
            String sporttime = "";
            if ("1".equals(sport_time)) {
                sporttime = "少于30分钟";
            }
            if ("2".equals(sport_time)) {
                sporttime = "30-60分钟";
            }
            if ("3".equals(sport_time)) {
                sporttime = "60分钟以上";
            }

            model.addAttribute("sporttime", sporttime);
            // 精神
            String psy_nervous = object.getString("psy_nervous");
            String psynervous = "";
            if ("1".equals(psy_nervous)) {
                psynervous = "没有";
            }
            if ("2".equals(psy_nervous)) {
                psynervous = "偶尔";
            }
            if ("3".equals(psy_nervous)) {
                psynervous = "经常";
            }
            model.addAttribute("psynervous", psynervous);
            // 睡眠
            String bedtime = object.getString("bedtime");
            String sleeptime = "";
            if ("1".equals(bedtime)) {
                sleeptime = "每天8小时以上";
            }
            if ("2".equals(bedtime)) {
                sleeptime = "每天6~8小时";
            }
            if ("3".equals(bedtime)) {
                sleeptime = "每天不足6小时";
            }
            model.addAttribute("sleeptime", sleeptime);

            // 近期（2年）是否新房装修
            String decorate_house = "";
            decorate_house = object.getString("decorate_house");
            if ("1".equals(decorate_house)) {
                decorate_house = "有";
            }
            if ("2".equals(decorate_house)) {
                decorate_house = "无";
            }
            model.addAttribute("decorate_house", decorate_house);

            // 有油烟接触史
            String disFlag = "";
            String disFlag2 = object.getJSONObject("lampblack_contact").getString("disFlag");
            if ("1".equals(disFlag2)) {
                disFlag = "有油烟接触史（长期厨房做菜或为厨师职业）；";
            }
            model.addAttribute("disFlag", disFlag);
            model.addAttribute("disFlag2", disFlag2);
            // 长期从事化工、染料、橡胶及印刷等职业
            String chemical_occupation = "";
            String chemical_occupation2 = object.getString("chemical_occupation");
            if ("1".equals(chemical_occupation2)) {
                chemical_occupation = "长期从事化工、染料、橡胶及印刷等职业；";
            }
            model.addAttribute("chemical_occupation", chemical_occupation);
            model.addAttribute("chemical_occupation2", chemical_occupation2);
            // 粉尘接触史（如：矿工、环卫工人等或长期接触过汽油等有害物质）
            String dust_exposure_his = "";
            String dust_exposure_his2 = object.getJSONObject("dust_exposure_his").getString("disFlag");
            if ("1".equals(dust_exposure_his2)) {
                dust_exposure_his = "粉尘接触史（如：矿工、环卫工人等或长期接触过汽油等有害物质）；";
            }
            model.addAttribute("dust_exposure_his", dust_exposure_his);
            model.addAttribute("dust_exposure_his2", dust_exposure_his2);
            // 石棉接触史（长期接触过石棉）
            String asbestos_contact_his = "";
            String asbestos_contact_his2 = object.getJSONObject("asbestos_contact_his").getString("disFlag");
            if ("1".equals(asbestos_contact_his2)) {
                asbestos_contact_his = "石棉接触史（长期接触过石棉）；";
            }
            model.addAttribute("asbestos_contact_his", asbestos_contact_his);
            model.addAttribute("asbestos_contact_his2", asbestos_contact_his2);
            // 您的月收入
            String income = "";
            income = object.getJSONObject("person").getString("income");
            if ("1".equals(income)) {
                income = "1000以下";
            }
            if ("2".equals(income)) {
                income = "1000~2999元";
            }
            if ("3".equals(income)) {
                income = "3000~4999元";
            }
            if ("4".equals(income)) {
                income = "5000元以上";
            }
            model.addAttribute("income", income);
            // 您是否有以下行为
            String med_hbp = "";
            String med_ach = "";
            String med_hbp2 = object.getString("med_hbp");
            String med_ach2 = object.getString("med_ach");
            if ("1".equals(med_hbp2)) {
                med_hbp = "服用降压药；";
            }
            if ("1".equals(med_ach2)) {
                med_ach = "注射肾上腺皮质激素；";
            }
            model.addAttribute("med_hbp", med_hbp);
            model.addAttribute("med_hbp2", med_hbp2);
            model.addAttribute("med_ach", med_ach);
            model.addAttribute("med_ach2", med_ach2);

            // 高血压
            String hbphis = "";
            String hbp_his = object.getJSONObject("hbp_his").getString("disFlag");
            if ("1".equals(hbp_his)) {
                hbphis = "高血压；";
            }
            model.addAttribute("hbphis", hbphis);
            model.addAttribute("hbp_his", hbp_his);
            // 脑卒中
            String strokehis = "";
            String stroke_his = object.getJSONObject("stroke_his").getString("disFlag");
            if ("1".equals(stroke_his)) {
                strokehis = "脑卒中；";
            }
            model.addAttribute("strokehis", strokehis);
            model.addAttribute("stroke_his", stroke_his);
            // 冠心病
            String chdhis = "";
            String chd_his = object.getJSONObject("chd_his").getString("disFlag");
            if ("1".equals(chd_his)) {
                chdhis = "冠心病；";
            }
            model.addAttribute("chdhis", chdhis);
            model.addAttribute("chd_his", chd_his);
            // 严重心律失常
            String mihis = "";
            String mi_his = object.getJSONObject("mi_his").getString("disFlag");
            if ("1".equals(mi_his)) {
                mihis = "严重心律失常；";
            }
            model.addAttribute("mihis", mihis);
            model.addAttribute("mi_his", mi_his);
            // 不稳定心绞痛
            String anginahis = "";
            String angina_his = object.getJSONObject("angina_his").getString("disFlag");
            if ("1".equals(angina_his)) {
                anginahis = "不稳定心绞痛；";
            }
            model.addAttribute("anginahis", anginahis);
            model.addAttribute("angina_his", angina_his);
            // 动脉硬化/狭窄/闭塞
            String arterialstatus = "";
            String arterial_status = object.getString("arterial_status");
            if ("1".equals(arterial_status)) {
                arterialstatus = "动脉硬化/狭窄/闭塞；";
            }
            model.addAttribute("arterialstatus", arterialstatus);
            model.addAttribute("arterial_status", arterial_status);
            // 未控制的有症状的心律衰竭
            String ushfhis = "";
            String ushf_his = object.getJSONObject("ushf_his").getString("disFlag");
            if ("1".equals(ushf_his)) {
                ushfhis = "未控制的有症状的心律衰竭；";
            }
            model.addAttribute("ushfhis", ushfhis);
            model.addAttribute("ushf_his", ushf_his);
            // 左心室肥大
            String lvdhis = "";
            String lvd_his = object.getJSONObject("lvd_his").getString("disFlag");
            if ("1".equals(lvd_his)) {
                lvdhis = "左心室肥大；";
            }
            model.addAttribute("lvdhis", lvdhis);
            model.addAttribute("lvd_his", lvd_his);
            // 房颤
            String afhis = "";
            String af_his = object.getJSONObject("af_his").getString("disFlag");
            if ("1".equals(af_his)) {
                afhis = "房颤；";
            }
            model.addAttribute("afhis", afhis);
            model.addAttribute("af_his", af_his);
            // 其他急性心脏事件
            String acehis = "";
            String ace_his = object.getJSONObject("ace_his").getString("disFlag");
            if ("1".equals(ace_his)) {
                acehis = "其他急性心脏事件；";
            }
            model.addAttribute("acehis", acehis);
            model.addAttribute("ace_his", ace_his);
            // 糖尿病（1或2型）
            String diab2his = "";
            String diab2_his = object.getJSONObject("diab2_his").getString("disFlag");
            if ("1".equals(diab2_his)) {
                diab2his = "糖尿病（1或2型）；";
            }
            model.addAttribute("diab2his", diab2his);
            model.addAttribute("diab2_his", diab2_his);
            // 痛风急性发作期
            String gaehis = "";
            String gae_his = object.getJSONObject("gae_his").getString("disFlag");
            if ("1".equals(gae_his)) {
                gaehis = "痛风急性发作期；";
            }
            model.addAttribute("gaehis", gaehis);
            model.addAttribute("gae_his", gae_his);
            // 痛风非急性发作期
            String gnahis = "";
            String gna_his = object.getJSONObject("gna_his").getString("disFlag");
            if ("1".equals(gna_his)) {
                gnahis = "痛风非急性发作期；";
            }
            model.addAttribute("gnahis", gnahis);
            model.addAttribute("gna_his", gna_his);
            // 慢性支气管炎
            String bronchhis = "";
            String bronch_his = object.getJSONObject("bronch_his").getString("disFlag");
            if ("1".equals(bronch_his)) {
                bronchhis = "慢性支气管炎；";
            }
            model.addAttribute("bronchhis", bronchhis);
            model.addAttribute("bronch_his", bronch_his);
            // 肺气肿
            String emphyhis = "";
            String emphy_his = object.getJSONObject("emphy_his").getString("disFlag");
            if ("1".equals(emphy_his)) {
                emphyhis = "肺气肿；";
            }
            model.addAttribute("emphyhis", emphyhis);
            model.addAttribute("emphy_his", emphy_his);
            // 肺结核
            String declinehis = "";
            String decline_his = object.getJSONObject("decline_his").getString("disFlag");
            if ("1".equals(decline_his)) {
                declinehis = "肺结核；";
            }
            model.addAttribute("declinehis", declinehis);
            model.addAttribute("decline_his", decline_his);
            // 骨质疏松症
            String ophis = "";
            String op_his = object.getJSONObject("op_his").getString("disFlag");
            if ("1".equals(op_his)) {
                ophis = "骨质疏松症；";
            }
            model.addAttribute("ophis", ophis);
            model.addAttribute("op_his", op_his);
            // 既往骨折
            String fracturehis = "";
            String fracture_his = object.getJSONObject("fracture_his").getString("disFlag");
            if ("1".equals(fracture_his)) {
                fracturehis = "既往骨折；";
            }
            model.addAttribute("fracturehis", fracturehis);
            model.addAttribute("fracture_his", fracture_his);
            // 其他限制运动的疾病（如关节炎、足部溃疡、肌肉韧带损伤等）
            String sporthis = "";
            String sport_his = object.getJSONObject("sport_his").getString("disFlag");
            if ("1".equals(sport_his)) {
                sporthis = "其他限制运动的疾病（如关节炎、足部溃疡、肌肉韧带损伤等）；";
            }
            model.addAttribute("sporthis", sporthis);
            model.addAttribute("sport_his", sport_his);
            // 引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、慢性营养不良等）
            String lchis = "";
            String lc_his = object.getJSONObject("lc_his").getString("disFlag");
            if ("1".equals(lc_his)) {
                lchis = "引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、慢性营养不良等）；";
            }
            model.addAttribute("lchis", lchis);
            model.addAttribute("lc_his", lc_his);
            // 前列腺炎或肥大
            String prostatedisease = "";
            String prostate_disease = object.getJSONObject("prostate_disease").getString("disFlag");
            if ("1".equals(prostate_disease)) {
                prostatedisease = "前列腺炎或肥大；";
            }
            model.addAttribute("prostatedisease", prostatedisease);
            model.addAttribute("prostate_disease", prostate_disease);
            // 前列腺癌
            String pchis = "";
            String pc_his = object.getJSONObject("pc_his").getString("disFlag");
            if ("1".equals(pc_his)) {
                pchis = "前列腺癌；";
            }
            model.addAttribute("pchis", pchis);
            model.addAttribute("pc_his", pc_his);
            // 乳腺癌（男性也有患病几率）
            String bchis = "";
            String bc_his = object.getJSONObject("bc_his").getString("disFlag");
            if ("1".equals(bc_his)) {
                bchis = "乳腺癌（男性也有患病几率）；";
            }
            model.addAttribute("bchis", bchis);
            model.addAttribute("bc_his", bc_his);
            // 您的家族中是否有人患有糖尿病
            // 父亲
            String familydiabf = "";
            String family_diab_f = object.getString("family_diab_f");
            if ("1".equals(family_diab_f)) {
                familydiabf = "糖尿病；";
            }
            model.addAttribute("familydiabf", familydiabf);
            model.addAttribute("family_diab_f", family_diab_f);
            // 母亲
            String familydiabm = "";
            String family_diab_m = object.getString("family_diab_m");
            if ("1".equals(family_diab_m)) {
                familydiabm = "糖尿病；";
            }
            model.addAttribute("familydiabm", familydiabm);
            model.addAttribute("family_diab_m", family_diab_m);
            // 兄弟姐妹
            String familydiabbs = "";
            String family_diab_bs = object.getString("family_diab_bs");
            if ("1".equals(family_diab_bs)) {
                familydiabbs = "糖尿病；";
            }
            model.addAttribute("familydiabbs", familydiabbs);
            model.addAttribute("family_diab_bs", family_diab_bs);
            // 子女
            String familydiabch = "";
            String family_diab_ch = object.getString("family_diab_ch");
            if ("1".equals(family_diab_ch)) {
                familydiabch = "糖尿病；";
            }
            model.addAttribute("familydiabch", familydiabch);
            model.addAttribute("family_diab_ch", family_diab_ch);

            // 您的家族中是否有人患高血压
            // 父亲
            String familyhbpf = "";
            String family_hbp_f = object.getString("family_hbp_f");
            if ("1".equals(family_hbp_f)) {
                familyhbpf = "高血压；";
            }
            model.addAttribute("familyhbpf", familyhbpf);
            model.addAttribute("family_hbp_f", family_hbp_f);
            // 母亲
            String familyhbpm = "";
            String family_hbp_m = object.getString("family_hbp_m");
            if ("1".equals(family_hbp_m)) {
                familyhbpm = "高血压；";
            }
            model.addAttribute("familyhbpm", familyhbpm);
            model.addAttribute("family_hbp_m", family_hbp_m);
            // 兄弟姐妹
            String familyhbpbs = "";
            String family_hbp_bs = object.getString("family_hbp_bs");
            if ("1".equals(family_hbp_bs)) {
                familyhbpbs = "高血压；";
            }
            model.addAttribute("familyhbpbs", familyhbpbs);
            model.addAttribute("family_hbp_bs", family_hbp_bs);
            // 子女
            String familyhbpch = "";
            String family_hbp_ch = object.getString("family_hbp_ch");
            if ("1".equals(family_hbp_ch)) {
                familyhbpch = "高血压；";
            }
            model.addAttribute("familyhbpch", familyhbpch);
            model.addAttribute("family_hbp_ch", family_hbp_ch);

            // 您的家族中是否有人患冠心病
            // 父亲
            String familychdf = "";
            String family_chd_f = object.getString("family_chd_f");
            if ("1".equals(family_chd_f)) {
                familychdf = "冠心病；";
            }
            model.addAttribute("familychdf", familychdf);
            model.addAttribute("family_chd_f", family_chd_f);
            // 母亲
            String familychdm = "";
            String family_chd_m = object.getString("family_chd_m");
            if ("1".equals(family_chd_m)) {
                familychdm = "冠心病；";
            }
            model.addAttribute("familychdm", familychdm);
            model.addAttribute("family_chd_m", family_chd_m);
            // 兄弟姐妹
            String familychdbs = "";
            String family_chd_bs = object.getString("family_chd_bs");
            if ("1".equals(family_chd_bs)) {
                familychdbs = "冠心病；";
            }
            model.addAttribute("familychdbs", familychdbs);
            model.addAttribute("family_chd_bs", family_chd_bs);
            // 子女
            String familychdch = "";
            String family_chd_ch = object.getString("family_chd_ch");
            if ("1".equals(family_chd_ch)) {
                familychdch = "冠心病；";
            }
            model.addAttribute("familychdch", familychdch);
            model.addAttribute("family_chd_ch", family_chd_ch);

            // 您的家族中是否有人患脑卒中
            // 父亲
            String familystrokef = "";
            String family_stroke_f = object.getString("family_stroke_f");
            if ("1".equals(family_stroke_f)) {
                familystrokef = "脑卒中；";
            }
            model.addAttribute("familystrokef", familystrokef);
            model.addAttribute("family_stroke_f", family_stroke_f);
            // 母亲
            String familystrokem = "";
            String family_stroke_m = object.getString("family_stroke_m");
            if ("1".equals(family_stroke_m)) {
                familystrokem = "脑卒中；";
            }
            model.addAttribute("familystrokem", familystrokem);
            model.addAttribute("family_stroke_m", family_stroke_m);
            // 兄弟姐妹
            String familystrokebs = "";
            String family_stroke_bs = object.getString("family_stroke_bs");
            if ("1".equals(family_stroke_bs)) {
                familystrokebs = "脑卒中；";
            }
            model.addAttribute("familystrokebs", familystrokebs);
            model.addAttribute("family_stroke_bs", family_stroke_bs);
            // 子女
            String familystrokech = "";
            String family_stroke_ch = object.getString("family_stroke_ch");
            if ("1".equals(family_stroke_ch)) {
                familystrokech = "脑卒中；";
            }
            model.addAttribute("familystrokech", familystrokech);
            model.addAttribute("family_stroke_ch", family_stroke_ch);
            // 您的家族中是否有人患髋部骨折
            // 父亲
            String familyhff = "";
            String family_hf_f = object.getString("family_hf_f");
            if ("1".equals(family_hf_f)) {
                familyhff = "髋部骨折；";
            }
            model.addAttribute("familyhff", familyhff);
            model.addAttribute("family_hf_f", family_hf_f);
            // 母亲
            String familyhfm = "";
            String family_hf_m = object.getString("family_hf_m");
            if ("1".equals(family_hf_m)) {
                familyhfm = "髋部骨折；";
            }
            model.addAttribute("familyhfm", familyhfm);
            model.addAttribute("family_hf_m", family_hf_m);
            // 兄弟姐妹
            String familyhfbs = "";
            String family_hf_bs = object.getString("family_hf_bs");
            if ("1".equals(family_hf_bs)) {
                familyhfbs = "髋部骨折；";
            }
            model.addAttribute("familyhfbs", familyhfbs);
            model.addAttribute("family_hf_bs", family_hf_bs);
            // 子女
            String familyhfch = "";
            String family_hf_ch = object.getString("family_hf_ch");
            if ("1".equals(family_hf_ch)) {
                familyhfch = "髋部骨折；";
            }
            model.addAttribute("familyhfch", familyhfch);
            model.addAttribute("family_hf_ch", family_hf_ch);
            // 您的家族中是否有人患肺癌
            // 父亲
            String familylungf = "";
            String family_lung_f = object.getString("family_lung_f");
            if ("1".equals(family_lung_f)) {
                familylungf = "肺癌；";
            }
            model.addAttribute("familylungf", familylungf);
            model.addAttribute("family_lung_f", family_lung_f);
            // 母亲
            String familylungm = "";
            String family_lung_m = object.getString("family_lung_m");
            if ("1".equals(family_lung_m)) {
                familylungm = "肺癌；";
            }
            model.addAttribute("familylungm", familylungm);
            model.addAttribute("family_lung_m", family_lung_m);
            // 兄弟姐妹
            String familylungbs = "";
            String family_lung_bs = object.getString("family_lung_bs");
            if ("1".equals(family_lung_bs)) {
                familylungbs = "肺癌；";
            }
            model.addAttribute("familylungbs", familylungbs);
            model.addAttribute("family_lung_bs", family_lung_bs);
            // 子女
            String familylungch = "";
            String family_lung_ch = object.getString("family_lung_ch");
            if ("1".equals(family_lung_ch)) {
                familylungch = "肺癌；";
            }
            model.addAttribute("familylungch", familylungch);
            model.addAttribute("family_lung_ch", family_lung_ch);
            // 您的家族中是否有人患乳腺癌
            // 父亲

            // 母亲
            String familybreastm = "";
            String family_breast_m = object.getString("family_breast_m");
            if ("1".equals(family_breast_m)) {
                familybreastm = "乳腺癌；";
            }
            model.addAttribute("familybreastm", familybreastm);
            model.addAttribute("family_breast_m", family_breast_m);
            // 兄弟姐妹
            String familybreasts = "";
            String family_breast_s = object.getString("family_breast_s");
            if ("1".equals(family_breast_s)) {
                familybreasts = "乳腺癌；";
            }
            model.addAttribute("familybreasts", familybreasts);
            model.addAttribute("family_breast_s", family_breast_s);
            // 子女
            String familybreastd = "";
            String family_breast_d = object.getString("family_breast_d");
            if ("1".equals(family_breast_d)) {
                familybreastd = "乳腺癌；";
            }
            model.addAttribute("familybreastd", familybreastd);
            model.addAttribute("family_breast_d", family_breast_d);

            // 您的家族中是否有人患过痛风
            // 父亲
            String familygoutf = "";
            String family_gout_f = object.getString("family_gout_f");
            if ("1".equals(family_gout_f)) {
                familygoutf = "痛风；";
            }
            model.addAttribute("familygoutf", familygoutf);
            model.addAttribute("family_gout_f", family_gout_f);
            // 母亲
            String familygoutm = "";
            String family_gout_m = object.getString("family_gout_m");
            if ("1".equals(family_gout_m)) {
                familygoutm = "痛风；";
            }
            model.addAttribute("familygoutm", familygoutm);
            model.addAttribute("family_gout_m", family_gout_m);
            // 兄弟姐妹
            String familygoutbs = "";
            String family_gout_bs = object.getString("family_gout_bs");
            if ("1".equals(family_gout_bs)) {
                familygoutbs = "痛风；";
            }
            model.addAttribute("familygoutbs", familygoutbs);
            model.addAttribute("family_gout_bs", family_gout_bs);
            // 子女
            String familygoutch = "";
            String family_gout_ch = object.getString("family_gout_ch");
            if ("1".equals(family_gout_ch)) {
                familygoutch = "痛风；";
            }
            model.addAttribute("familygoutch", familygoutch);
            model.addAttribute("family_gout_ch", family_gout_ch);

            // 您的家族中是否有人患前列腺癌
            // 父亲
            String familyprof = "";
            String family_pro_f = object.getString("family_pro_f");
            if ("1".equals(family_pro_f)) {
                familyprof = "前列腺癌 ；";
            }
            model.addAttribute("familyprof", familyprof);
            model.addAttribute("family_pro_f", family_pro_f);

            // 兄弟姐妹
            String familyprob = "";
            String family_pro_b = object.getString("family_pro_b");
            if ("1".equals(family_pro_b)) {
                familyprob = "前列腺癌 ；";
            }
            model.addAttribute("familyprob", familyprob);
            model.addAttribute("family_pro_b", family_pro_b);
            // 子女
            String familypros = "";
            String family_pro_s = object.getString("family_pro_s");
            if ("1".equals(family_pro_s)) {
                familypros = "前列腺癌 ；";
            }
            model.addAttribute("familypros", familypros);
            model.addAttribute("family_pro_s", family_pro_s);
            // 总胆固醇
            String cholesterol = object.getString("cholesterol");
            model.addAttribute("cholesterol", cholesterol);
            // 甘油三酯（单位mmol/l）
            String tg = object.getString("tg");
            model.addAttribute("tg", tg);
            // 高密度蛋白
            String hdlc = object.getString("hdlc");
            model.addAttribute("hdlc", hdlc);
            // 低密度脂蛋白胆固醇（单位mmol/l）
            String ldlc = object.getString("ldlc");
            model.addAttribute("ldlc", ldlc);
            // 血肌酐（单位mmol/L）
            String serum_creatinine = object.getString("serum_creatinine");
            model.addAttribute("serum_creatinine", serum_creatinine);
            // 血尿酸（单位μmol/L）
            String bua = object.getString("bua");
            model.addAttribute("bua", bua);
            // 空腹血糖（单位mmol/l）
            String fbg = object.getString("fbg");
            model.addAttribute("fbg", fbg);
            // 收缩压（单位mmHg）
            String sbp = object.getString("sbp");
            model.addAttribute("sbp", sbp);
            // 舒张压（单位mmHg）
            String dbp = object.getString("dbp");
            model.addAttribute("dbp", dbp);
            // 血常规检查白细胞
            String wbc = object.getString("wbc");
            model.addAttribute("wbc", wbc);
            // 骨密度检查指标-T值
            String t_score = object.getString("t_score");
            model.addAttribute("t_score", t_score);

            // 长期口服避孕药
            String medusepill = "";
            String med_use_pill = object.getString("med_use_pill");
            if ("1".equals(med_use_pill)) {
                medusepill = "长期口服避孕药；";
            }
            model.addAttribute("med_use_pill", med_use_pill);
            model.addAttribute("medusepill", medusepill);

            // 长期使用丰乳产品或做过乳房整形手术
            String breastplasticsurgery = "";
            String breast_plastic_surgery = object.getString("breast_plastic_surgery");
            if ("1".equals(breast_plastic_surgery)) {
                breastplasticsurgery = "长期使用丰乳产品或做过乳房整形手术；";
            }
            model.addAttribute("breast_plastic_surgery", breast_plastic_surgery);
            model.addAttribute("breastplasticsurgery", breastplasticsurgery);

            // 曾有子宫、卵巢和输卵管等的手术
            String gynecologyoperation = "";
            String gynecology_operation = object.getString("gynecology_operation");
            if ("1".equals(gynecology_operation)) {
                gynecologyoperation = "曾有子宫、卵巢和输卵管等的手术；";
            }
            model.addAttribute("gynecologyoperation", gynecologyoperation);
            model.addAttribute("gynecology_operation", gynecology_operation);

            YhUserReginfo yhUserReginfo = yhUserReginfoService.selectByPrimaryKey(userCode);
            String gender = yhUserReginfo.getGender().toString();
            String sex = "";
            //性别
            sex = object.getJSONObject("person").getString("sex");
            if ("1".equals(sex)) {
                sex = "男";
            } else {
                sex = "女";
            }
            model.addAttribute("sex", sex);
            Date birthDate = yhUserReginfo.getBirthDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取String类型的时间
            String createdate = sdf.format(birthDate);
            model.addAttribute("gender", gender);
            model.addAttribute("createdate", createdate);
            //劳动强度
            String palse = "";
            YhUserPersonageCondition personageCondition = YhUserPersonageConditionService.selectByPrimaryKey(userCode);
            if (personageCondition != null) {
                if (personageCondition.getStature() != null && !"".equals(personageCondition.getStature())) {
                    String stature = personageCondition.getStature();
                    model.addAttribute("stature", stature);
                }
                if (personageCondition.getWeight() != null && !"".equals(personageCondition.getWeight())) {
                    String weight = personageCondition.getWeight();
                    model.addAttribute("weight", weight);
                }
                if (personageCondition.getStature() != null && !"".equals(personageCondition.getStature())
                        && personageCondition.getWeight() != null && !"".equals(personageCondition.getWeight())) {
                    //身高
                    double sgao = Double.valueOf(personageCondition.getStature());
                    //体重
                    double tizho = Double.valueOf(personageCondition.getWeight());
                    //bmi
                    String subtract = "";
                    double bim1 = tizho / ((sgao / 100) * (sgao / 100));
                    DecimalFormat df = new DecimalFormat("0.0");//设置保留位数
                    subtract = df.format((float) bim1);
                    model.addAttribute("subtract", subtract);
                }
                //腰围
                String waistline = personageCondition.getWaistline();
                model.addAttribute("waistline", waistline);
                //臀围
                String hipline = personageCondition.getHipline();
                model.addAttribute("hipline", hipline);

                if (personageCondition.getPalse() != null && !"".equals(personageCondition.getPalse())) {
                    String palse2 = personageCondition.getPalse();
                    if ((!Objects.equals("undefined", palse2)) && Integer.valueOf(palse2) <= 3) {
                        if ("0".equals(palse2)) {
                            palse = "极轻";
                        }
                        if ("1".equals(palse2)) {
                            palse = "轻";
                        }
                        if ("2".equals(palse2)) {
                            palse = "中";
                        }
                        if ("3".equals(palse2)) {
                            palse = "重";
                        }

                    }
                }

            }

            YhFoodSrecipeRecord yhFoodSrecipeRecord = yhFoodSrecipeRecordService.findFoodSrecipeRecordByUserCodeForNew(userCode);
            if (yhFoodSrecipeRecord != null) {
                palse = yhFoodSrecipeRecord.getStrength();
                if ("0".equals(palse)) {
                    palse = "极轻";
                }
                if ("1".equals(palse)) {
                    palse = "轻";
                }
                if ("2".equals(palse)) {
                    palse = "中";
                }
                if ("3".equals(palse)) {
                    palse = "重";
                }
            }

            model.addAttribute("palse", palse);


            // 您第一次来月经的年龄是多大？
            String femalemenstruation = "";
            String female_menstruation = object.getString("female_menstruation");
            if ("1".equals(female_menstruation)) {
                femalemenstruation = "<12 岁 ";
            }
            if ("2".equals(female_menstruation)) {
                femalemenstruation = "≥12岁";
            }
            model.addAttribute("femalemenstruation", femalemenstruation);

            // 月经是否规律
            String femalemenstruationregular = "";
            String female_menstruation_regular = object.getString("female_menstruation_regular");
            if ("1".equals(female_menstruation_regular)) {
                femalemenstruationregular = "是 ";
            }
            if ("2".equals(female_menstruation_regular)) {
                femalemenstruationregular = "否";
            }
            model.addAttribute("femalemenstruationregular", femalemenstruationregular);
            // 您是否生育过？
            String femalebirth = "";
            String female_birth = object.getString("female_birth");
            if ("1".equals(female_birth)) {
                femalebirth = "是 ";
            }
            if ("2".equals(female_birth)) {
                femalebirth = "是，产后超过3个月 ";
            }
            if ("3".equals(female_birth)) {
                femalebirth = "否";
            }
            model.addAttribute("femalebirth", femalebirth);
            model.addAttribute("female_birth", female_birth);
            // 您生第一胎年龄是多大？
            String femaletire = "";
            String female_tire = object.getString("female_tire");
            if ("1".equals(female_tire)) {
                femaletire = "<30岁";
            }
            if ("2".equals(female_tire)) {
                femaletire = "≥30岁 ";
            }

            model.addAttribute("femaletire", femaletire);
            model.addAttribute("female_tire", female_tire);
            // 您的孩子是母乳喂养吗？
            String femalebreast = "";
            String female_breast_time = object.getString("female_breast_time");
            if ("1".equals(female_breast_time)) {
                femalebreast = "<12个月";
            }
            if ("2".equals(female_breast_time)) {
                femalebreast = "≥12个月";

            }

            model.addAttribute("femalebreast", femalebreast);
            model.addAttribute("female_breast", female_breast_time);
            // 您是否绝经
            String femalemenopause = "";
            String female_menopause = object.getString("female_menopause");
            if ("1".equals(female_menopause)) {
                femalemenopause = "是";
            }
            if ("2".equals(female_menopause)) {
                femalemenopause = "否 ";
            }

            model.addAttribute("femalemenopause", femalemenopause);
            model.addAttribute("female_menopause", female_menopause);
            // 您的绝经年龄是多大？
            String femalemenopauseage = "";
            String female_menopause_age = object.getString("female_menopause_age");
            if ("1".equals(female_menopause_age)) {
                femalemenopauseage = "<55岁";
            }
            if ("2".equals(female_menopause_age)) {
                femalemenopauseage = "≥55岁 ";
            }

            model.addAttribute("femalemenopauseage", femalemenopauseage);
            model.addAttribute("female_menopause_age", female_menopause_age);
            // 您绝经后有如下哪种情况
            String medert = "";
            String med_ert = object.getString("med_ert");
            if ("1".equals(med_ert)) {
                medert = "使用过雌激素替代疗法（<5年）；";
            }
            if ("2".equals(med_ert)) {
                medert = "使用过雌激素替代疗法（>=5年）； ";
            }
            String weightup = "";
            String weight_up = object.getString("weight_up");
            if ("1".equals(weight_up)) {
                weightup = "体重上升超过5公斤；";
            }
            model.addAttribute("medert", medert);
            model.addAttribute("med_ert", med_ert);
            model.addAttribute("weightup", weightup);
        }
        model.addAttribute("result", result);
        YhDiseaseAssessTotalReport2web report2web = yhDiseaseAssessTotalReport2Mapper.selectAssessByParam(userCode);
        if (report2web != null) {
            // 肺癌
            String feiai = "";
            Integer lcHis = report2web.getLcHis();
            if (lcHis != null) {
                if (lcHis == 1) {
                    feiai = "肺癌；";
                }
            }
            model.addAttribute("feiai", feiai);
        }

        YhDiseaseRiskResult2Web diseaseRiskResult2Web = yhDiseaseRiskResult2WebService
                .selectUserDiseaseRiskByParam(userCode);
        if (diseaseRiskResult2Web != null) {

            String livingEnvir = diseaseRiskResult2Web.getLivingEnvir();
            JSONObject object = JSONObject.parseObject(livingEnvir);
            // 居住环境
            String ideal = object.getString("Ideal_value");
            model.addAttribute("ideal", ideal);

        }
        String cardId = yhOrgTreeService.selectcardid(userCode);
        String selecendtime = yhOrgTreeService.selectadddate(cardId);
        String linetimes = yhOrgTreeService.selectlinetime(cardId);
        model.addAttribute("AddDate", selecendtime);
        model.addAttribute("linetimes", linetimes);
        return "personalUser/healthRecord/basicAndphysical";
    }

    @ApiOperation(value = "附件上传")
    @RequestMapping(value = "accessory/upload", method = RequestMethod.GET)
    public String accessoryUpload(String userCode, Model model) {
        model.addAttribute("userCode", userCode);
        return "personalUser/healthRecord/accessoryUpload";
    }

    @ApiOperation(value = "查看文件")
    @RequestMapping(value = "look/File", method = RequestMethod.GET)
    public String lookFile(String userCode, Model model, Long folderId, String folderName) {
        model.addAttribute("folderName", folderName);
        model.addAttribute("userCode", userCode);
        model.addAttribute("folderId", folderId);
        return "personalUser/healthRecord/lookFile";
    }

    @ApiOperation(value = "健康档案管理")
    @RequestMapping(value = "/health/archives", method = RequestMethod.GET)
    public String healthFileManage(String strTime, Model model) {
        model.addAttribute("strTime", strTime);
        return "healthFileManage/healthFileManage";
    }

    @ApiOperation(value = "个人中心")
    @RequestMapping(value = "/personage/centre", method = RequestMethod.GET)
    public String personagecentre(Model model) {
        YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        String userCode = yhUserReginfo.getUserCode();
        model.addAttribute("userCode", userCode);
        return "personalUser/personage/personagecentre";
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/update/pwd", method = RequestMethod.GET)
    public String updatePwd(String userCode, Model model) {
        model.addAttribute("userCode", userCode);
        return "personalUser/personage/updatePwd";
    }

    @ApiOperation(value = "用户查看健康检测")
    @RequestMapping(value = "/health/detection", method = RequestMethod.GET)
    public String healthDetection(Model model, String userCode) {
        model.addAttribute("userCode", userCode);
        // 用户体温
        Map<String, Object> temperatureMap = yHUserTemperatureService.selectUserAnimalHeat(userCode);
        model.addAttribute("temperatureMap", temperatureMap);
        // 用户血氧
        Map<String, Object> OxygenMap = yHUserOxygenService.selectYHUserOxygen(userCode);
        model.addAttribute("OxygenMap", OxygenMap);

        // 用户体重
        Map<String, Object> tzMap = yHUserBmiService.selectYHUserBmi(userCode);
        model.addAttribute("tzMap", tzMap);

        // 用户血压
        Map<String, Object> xyMap = yHUserBloodPressureService.selectYHUserBloodPressure(userCode);
        model.addAttribute("xyMap", xyMap);
        // 用户血糖
        Map<String, Object> xtMap = yHUserBloodsugerService.selectYHUserBloodsuger(userCode);
        model.addAttribute("xtMap", xtMap);
        // 用户体脂
        Map<String, Object> bodyFatMap = YHUserBodyFatService.selectYHUserBodyFat(userCode);
        model.addAttribute("bodyFatMap", bodyFatMap);
        // 用户血脂
        Map<String, Object> bloodfatFatMap = yHUserBloodfatService.selectYHUserBloodfat(userCode);
        model.addAttribute("bloodfatFatMap", bloodfatFatMap);

        // 用户肾功能
        Map<String, Object> functionMap = YHUserRenalfunctionService.selectYHUserRenalfunction(userCode);
        model.addAttribute("functionMap", functionMap);
        // 用户心率
        Map<String, Object> xinlvMap = yHUserHeartRateService.selectHeartRate(userCode);
        model.addAttribute("xinlvMap", xinlvMap);

        // 用户肝功能
        Map<String, Object> liverMap = YHUserLiverfunctionService.selectYHUserLiverfunction(userCode);
        model.addAttribute("liverMap", liverMap);

        // 用户心电图
        Map<String, Object> EcgMap = yHUserEcgService.selectYHUserEcg(userCode);
        model.addAttribute("EcgMap", EcgMap);
        // 用户尿检
        Map<String, Object> UbaMap = yHUserUbaService.selectYHUserUba(userCode);
        model.addAttribute("UbaMap", UbaMap);
        // 用户其他
        Map<String, Object> qtMap = yHUserOtherfunctionService.selectYHUserOtherfunction(userCode);
        model.addAttribute("qtMap", qtMap);

        return "personalUser/healthRecord/healthDetection";
    }

    @ApiOperation(value = "用户健康检测指标")
    @RequestMapping(value = "/health/indicator", method = RequestMethod.GET)
    public String indicator(Model model, String userCode, String type) {
        model.addAttribute("userCode", userCode);
        model.addAttribute("type", type);
        return "personalUser/healthRecord/indicator";

    }

    @ApiOperation(value = "制卡信息管理")
    @RequestMapping(value = "register/makecard", method = RequestMethod.GET)
    public String makecardinfo() {
        return "seniorsetting/makecardinfo";
    }

    @ApiOperation(value = "制卡页面")
    @RequestMapping(value = "register/tomakecard", method = RequestMethod.GET)
    public String tomakecard() {
        return "seniorsetting/makecard";
    }

    @ApiOperation(value = "健康检测告警设置")
    @RequestMapping(value = "setting/thealarmdetection", method = RequestMethod.GET)
    public String thealarmdetection() {
        return "seniorsetting/thealarmdetection";
    }

    @ApiOperation(value = "健康检测告警设置2")
    @RequestMapping(value = "setting/thealarmdetection2", method = RequestMethod.GET)
    public String thealarmdetection2(String userCode, Model model, String type) {
        YhUserReginfo reginfo = yhUserReginfoService.selectByPrimaryKey(userCode);
        int selectCount = IYhUserDeviceService.selectCount(userCode);
        model.addAttribute("userCode", userCode);
        model.addAttribute("selectCount", selectCount);
        model.addAttribute("type", type);
        model.addAttribute("name", reginfo.getName());
        int age = getAge(reginfo.getBirthDate());
        model.addAttribute("age", age);
        Integer gender = reginfo.getGender();
        String sex = "";
        if (gender == 1) {
            sex = "男";
        } else {
            sex = "女";
        }
        model.addAttribute("sex", sex);
        return "thealarmset/thealarmdetection2";
    }

    @ApiOperation(value = "健康检测告警设置2")
    @RequestMapping(value = "setting/thealarmdetectionMap", method = RequestMethod.GET)
    public String thealarmdetectionMap(String usercode, Model model, String tid) {
        YhUserReginfo reginfo = yhUserReginfoService.selectByPrimaryKey(usercode);
        model.addAttribute("usercode", usercode);
        model.addAttribute("tid", tid);
        JiaiLocate newLocate = JiaiLocateMapper.findNewLocate(tid);
        JiaiEnclosure enclosureNewInfo = JiaiEnclosureMapper.findEnclosureNewInfo(tid);
        model.addAttribute("enclosureNewInfo", enclosureNewInfo);
        model.addAttribute("newLocate", newLocate);
        model.addAttribute("name", reginfo.getName());
        int age = getAge(reginfo.getBirthDate());
        model.addAttribute("age", age);
        Integer gender = reginfo.getGender();
        String sex = "";
        if (gender == 1) {
            sex = "男";
        } else {
            sex = "女";
        }
        model.addAttribute("sex", sex);
        return "thealarmset/map";
    }

    @ApiOperation(value = "更多设置设备")
    @RequestMapping(value = "setting/thealarmdetectionSetting", method = RequestMethod.GET)
    public String thealarmdetectionSetting(String usercode, Model model, String tid) {
        YhUserReginfo reginfo = yhUserReginfoService.selectByPrimaryKey(usercode);
        JiaiWatchInfo jiaiWatchInfo = JiaiWatchInfoMapper.selectByPrimaryKey(tid);
        model.addAttribute("usercode", usercode);
        model.addAttribute("jiaiWatchInfo", jiaiWatchInfo);
        model.addAttribute("tid", tid);
        model.addAttribute("name", reginfo.getName());
        int age = getAge(reginfo.getBirthDate());
        model.addAttribute("age", age);
        Integer gender = reginfo.getGender();
        String sex = "";
        if (gender == 1) {
            sex = "男";
        } else {
            sex = "女";
        }
        model.addAttribute("sex", sex);
        return "thealarmset/setting";
    }


    @ApiOperation(value = "健康干预管理")
    @RequestMapping(value = "health/intervention", method = RequestMethod.GET)
    public String intervention(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        if (null == userCode) {
            return null;
        }
        // 根据userCode查询YhUserAdminInfo表
        YhUserAdminInfoExample example = new YhUserAdminInfoExample();
        Criteria createCriteria = example.createCriteria();
        createCriteria.andUserCodeEqualTo(userCode);
        List<YhUserAdminInfo> selectByExample = YhUserAdminInfoService.selectByExample(example);
        if (null == selectByExample || selectByExample.size() == 0) {
            return null;
        }
        YhUserAdminInfo yhUserAdminInfo = selectByExample.get(0);
        Integer id = yhUserAdminInfo.getId();
        List<YHDocGroup> selectByPrimaryKeyByDocInfo = YHDocGroupServiceImpl
                .selectByPrimaryKeyByDocInfo(String.valueOf(id));

        model.addAttribute("YHDocGroup", selectByPrimaryKeyByDocInfo);

        return "health/intervene/interveneList";
    }

    @ApiOperation(value = "健康检测管理")
    @RequestMapping(value = "health/testing", method = RequestMethod.GET)
    public String testing(String strTime, Model model) {
        model.addAttribute("strTime", strTime);
        return "health/testing/index";
    }

    @ApiOperation(value = "系统升级通知")
    @RequestMapping(value = "setting/systemupdate", method = RequestMethod.GET)
    public String systemupdate() {
        return "seniorsetting/systemupdate";
    }

    @ApiOperation(value = "系统升级通知")
    @RequestMapping(value = "data/export", method = RequestMethod.GET)
    public String export() {
        return "exportdata/exprotdata";
    }

    @ApiOperation(value = "压力评估管理")
    @RequestMapping(value = "health/stressevaluation", method = RequestMethod.GET)
    public String stressevaluation(String strTime, Model model) {
        model.addAttribute("strTime", strTime);
        return "health/pressure/index";
    }

    @ApiOperation(value = "心理评估管理")
    @RequestMapping(value = "health/pressure", method = RequestMethod.GET)
    public String stressevaluation(Model model) {
        return "health/pressure/psychological";
    }

    @ApiOperation(value = "尼古丁评估管理")
    @RequestMapping(value = "health/nicotineAddiction", method = RequestMethod.GET)
    public String nicotineAddiction(String strTime, Model model) {
        model.addAttribute("strTime", strTime);
        return "health/nicotineAddiction/nicotine";
    }

    @ApiOperation(value = "饮食日记管理")
    @RequestMapping(value = "diary/fooddiary", method = RequestMethod.GET)
    public String fooddiary(String strTime, Model model) {
        model.addAttribute("strTime", strTime);
        return "health/FoodDiary/index";
    }

    @ApiOperation(value = "服务任务管理")
    @RequestMapping(value = "task/service", method = RequestMethod.GET)
    public String taskservice(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl.selectAllInfo(userCode);
        model.addAttribute("selectAllInfo", selectAllInfo);
        return "taskservice/managetaskservice";
    }

    @ApiOperation(value = "服务任务管理干预计划")
    @RequestMapping(value = "task/managetaskplanservice", method = RequestMethod.GET)
    public String managetaskplanservice(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl.selectAllInfo(userCode);
        model.addAttribute("selectAllInfo", selectAllInfo);
        return "taskservice/managetaskplanservice";
    }


    @ApiOperation(value = "服务任务管理干预计划")
    @RequestMapping(value = "task/wattingtaskservice", method = RequestMethod.GET)
    public String wattingtaskservice(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl.selectAllInfo(userCode);
        model.addAttribute("selectAllInfo", selectAllInfo);
        return "taskservice/wattingtaskservice";
    }


    @ApiOperation(value = "服务任务管理干预计划")
    @RequestMapping(value = "task/addwaitingfortheservice", method = RequestMethod.GET)
    public String addwaitingfortheservice(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl.selectAllInfo(userCode);
        model.addAttribute("selectAllInfo", selectAllInfo);
        return "taskservice/addwaitingfortheservice";
    }


    @ApiOperation(value = "医生端健康计划")
    @RequestMapping(value = "health/plan", method = RequestMethod.GET)
    public String healthPlan() throws ParseException {

        return "health/plan/index";
    }

    @ApiOperation(value = "医生端健康计划")
    @RequestMapping(value = "/detectiondiary", method = RequestMethod.GET)
    public String planDiary(String strTime, Model model) throws ParseException {
        model.addAttribute("strTime", strTime);
        return "health/planDiary/index";
    }

    @ApiOperation(value = "运动日记管理")
    @RequestMapping(value = "diary/movediary", method = RequestMethod.GET)
    public String movediary(String strTime, Model model) {
        model.addAttribute("strTime", strTime);
        return "health/SportDiary/index";
    }

    @ApiOperation(value = "信息概要")
    @RequestMapping(value = "user/summary", method = RequestMethod.GET)
    public String summary(String usercode, Model model) throws ParseException {
        if (StringUtil.isNotEmpty(usercode)) {
            InformationSummary selectUserInfo = YhUserReginfoServiceImpl.selectUserInfo(usercode);
            model.addAttribute("selectUserInfo", selectUserInfo);
        }

        model.addAttribute("usercode", usercode);
        System.out.println("-----------------" + usercode);
        return "informationSummary/index";
    }

    @ApiOperation(value = "医生控制台")
    @RequestMapping(value = "doctor/comtroller", method = RequestMethod.GET)
    public String comtroller(Model model) throws ParseException {
        doctorConsoleVOJO findDoctorAllInfo = yhUserMakeCardService.findDoctorAllInfo();
        model.addAttribute("info", findDoctorAllInfo);
        return "doctorConsole/index";
    }

    @ApiOperation(value = "常见症状管理")
    @RequestMapping(value = "common/symptoms", method = RequestMethod.GET)
    public String symptomManage(Model model) throws ParseException {

        return "symptomManage/symptomAssess";
    }

    @Resource
    CSDoctorChatListService CSDoctorChatListService;
    @Resource
    CSOrgDoctorChatService CSOrgDoctorChatService;

    @ApiOperation(value = "系统配置")
    @RequestMapping(value = "system/setting", method = RequestMethod.GET)
    public String systemLayout(ModelMap modelMap) throws ServletException {
        List<YhDicIntroduction> yhdic = yhuserpagesettingservice.selectallduction();
        modelMap.put("yhdic", yhdic);
        YhUserReginfo yhUserReginfo = getUserInfo();
        if (1 != yhUserReginfo.getUserType()) {
            throw new ServletException("不是机构账号");
        }
        String orgId = yhUserReginfo.getUserCode();

        List<CSDoctorChatList> listServiceList = CSDoctorChatListService.getList();
        for (com.yihe.system.domain.CSDoctorChatList CSDoctorChatList : listServiceList) {
            Integer id = CSDoctorChatList.getId();
            CSOrgDoctorChat CSOrgDoctorChat = CSOrgDoctorChatService.selectListByOrgAndType(orgId, id);
            if (null != CSOrgDoctorChat) {
                String docId = CSOrgDoctorChat.getDocId();
                CSDoctorChatList.setCheck(docId);
            }
        }

        modelMap.put("doctorList", listServiceList);
        modelMap.put("doctorSelect", yhUserReginfoService.queryDoctorList());


        return "systemLayout/system";
    }

    public static YhUserReginfo getUserInfo(){
        Subject subject = SecurityUtils.getSubject();
        return (YhUserReginfo) subject.getPrincipal();
    }

    @ApiOperation(value = "用户积分")
    @RequestMapping(value = "user/integral", method = RequestMethod.GET)
    public String userIntegral(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();

        int roletype = yhLifeHabitImprovementDictMapper.selectroletype(principal.getUserCode());

        model.addAttribute("roletype", roletype);
        return "integralShopping/userIntegral";
    }

    @ApiOperation(value = "积分商城")
    @RequestMapping(value = "integral/mall", method = RequestMethod.GET)
    public String integralStore(Model model) {
        return "integralShopping/integralStore";
    }

    @ApiOperation(value = "商品分组")
    @RequestMapping(value = "shopping/commodityGroup", method = RequestMethod.GET)
    public String commodityGroup(Model model) {
        return "integralShopping/commodityGroup";
    }

    @ApiOperation(value = "商品管理")
    @RequestMapping(value = "shopping/commodityManage", method = RequestMethod.GET)
    public String commodityManage(Model model) {
        return "integralShopping/commodityManage";
    }

    @ApiOperation(value = "添加商品")
    @RequestMapping(value = "shopping/addCommodity", method = RequestMethod.GET)
    public String addCommodity(Model model, Integer merchandiseId, Integer groupingId) {
        model.addAttribute("merchandiseId", merchandiseId);
        model.addAttribute("groupingId", groupingId);
        return "integralShopping/addCommodity";
    }

    @ApiOperation(value = "兑换记录")
    @RequestMapping(value = "shopping/conversionRecord", method = RequestMethod.GET)
    public String conversionRecord() {
        return "integralShopping/conversionRecord";
    }

    @ApiOperation(value = "用户积分记录")
    @RequestMapping(value = "user/IntegralRecord", method = RequestMethod.GET)
    public String IntegralRecord(Model model, String userCode) {
        model.addAttribute("userCode", userCode);
        return "integralShopping/IntegralRecord";
    }

    @ApiOperation(value = "互动管理")
    @RequestMapping(value = "community/dynamic", method = RequestMethod.GET)
    public String communityDynamic(Model model, String userCode) {
        model.addAttribute("userCode", userCode);
        return "interactmanage/communityDynamic";
    }

    /*
     * @ApiOperation(value = "沟通记录")
     *
     * @RequestMapping(value = "communication/record", method = RequestMethod.GET)
     * public String communicationRecord(Model model, String usercode) { return
     * "goeasy/index"; }
     */

    @ApiOperation(value = "个人健康计划")
    @RequestMapping(value = "health/userPlan", method = RequestMethod.GET)
    public String healthuserPlan(String usercode, Integer type, Model model) throws ParseException {
        System.out.println("health/userPlan....usercode---------" + usercode);
        model.addAttribute("type", type);
        model.addAttribute("usercode", usercode);
        return "personalUser/healthRecord/plan";
    }

    @ApiOperation(value = "积分设置")
    @RequestMapping(value = "shopping/IntegralSset", method = RequestMethod.GET)
    public String IntegralSset(Model model) {
        return "integralShopping/IntegralSet";
    }

    @ApiOperation(value = "个人健康评估")
    @RequestMapping(value = "user/healthAssessment", method = RequestMethod.GET)
    public String userhealthAssessment(String usercode, String type, Model model) throws ParseException {
        System.out.println("-----------------" + usercode);
        model.addAttribute("type", type);
        model.addAttribute("usercode", usercode);

        return "personalUser/healthRecord/userHealthAssessment";
    }

    @ApiOperation(value = "个人健康评估")
    @RequestMapping(value = "user/stressAssessment", method = RequestMethod.GET)
    public String stressAssessment(String usercode, Model model) throws ParseException {
        System.out.println("-----------------" + usercode);

        model.addAttribute("usercode", usercode);

        return "personalUser/healthRecord/stressAssessment";
    }

    @ApiOperation(value = "设备与参数设置")
    @RequestMapping(value = "equipment/setup", method = RequestMethod.GET)
    public String equipmentSetup() throws ParseException {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        return "devicesSettingUp/equipmentList";
    }

    @ApiOperation(value = "告警信息管理")
    @RequestMapping(value = "giveanalarm/administration", method = RequestMethod.GET)
    public String administration(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String usercode = principal.getUserCode();
        List<YhHeThresholdAlarmVOJO> findYhHeThresholdTypeByDoctor = YhHeThresholdAlarmServiceImpl
                .findYhHeThresholdTypeByDoctor(usercode);
        List<YhHeThresholdAlarmVOJO> findYhHeThresholdTypeByDoctorAndStatus = YhHeThresholdAlarmServiceImpl
                .findYhHeThresholdTypeByDoctorAndStatus(usercode);
        model.addAttribute("typeList", findYhHeThresholdTypeByDoctor);
        model.addAttribute("statuList", findYhHeThresholdTypeByDoctorAndStatus);
        return "devicesSettingUp/alarmInformationList";
    }

    @ApiOperation(value = "基本数据分析")
    @RequestMapping(value = "data/basisanalyze", method = RequestMethod.GET)
    public String basicdataanalysis() {
        return "exportdata/basicdataanalysis";
    }

    @ApiOperation(value = "高级数据分析")
    @RequestMapping(value = "data/senioranalyze", method = RequestMethod.GET)
    public String senioranalyze() {

        return "exportdata/seniordataanalysis";
    }


    @ApiOperation(value = "每次检测 检测项设置")
    @RequestMapping(value = "service/testingevery", method = RequestMethod.GET)
    public String testingevery() {
        return "division/testingevery";
    }

    @ApiOperation(value = "完成评估 评估项设置")
    @RequestMapping(value = "service/assessmentfinish", method = RequestMethod.GET)
    public String assessmentfinish() {
        return "division/assessmentfinish";
    }

    @ApiOperation(value = "久未评估 评估项设置")
    @RequestMapping(value = "service/assessmentlongtime", method = RequestMethod.GET)
    public String assessmentlongtime() {
        return "division/assessmentlongtime";
    }

    @ApiOperation(value = "久未检测 检测项设置")
    @RequestMapping(value = "service/testinglongtime", method = RequestMethod.GET)
    public String testinglongtime() {
        return "division/testinglongtime";
    }

    @ApiOperation(value = "服务任务详情页面")
    @RequestMapping(value = "service/serrvicedetails", method = RequestMethod.GET)
    public String serrvicedetails(Model model, Integer serviceid) {
        model.addAttribute("serviceid", serviceid);
        return "taskservice/serrvicedetails";
    }

    @ApiOperation(value = "服务任务受理页面")
    @RequestMapping(value = "service/serrviceaccept", method = RequestMethod.GET)
    public String serrviceaccept(Model model, String usercode, Integer servicestate, Integer type) {
        model.addAttribute("usercode", usercode);
        model.addAttribute("servicestate", servicestate);
        model.addAttribute("type", type);
        return "taskservice/acceptservice";
    }

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS,
            WORK_QUEUE, HANDLER);

    @ApiOperation(value = "评估报告")
    @RequestMapping(value = "assessmentReport/info2", method = RequestMethod.GET)
    public String assessmentReport2(Model model, String rs, String sex) throws InvocationTargetException {
        model.addAttribute("sex", sex);
        String topimg = yhUserPageSettingService.getimg(21);

        model.addAttribute("topimg", topimg);
        StringBuilder huanb = new StringBuilder();
        StringBuilder gaofx = new StringBuilder();
        StringBuilder jiaogaofx = new StringBuilder();
        StringBuilder zhongdengfx = new StringBuilder();
        StringBuilder difx = new StringBuilder();
        List<YhDiseaseAssessReport> findDiseaseNameAndRiskLevelByRs = YhDiseaseAssessReportServiceImpl.findDiseaseNameAndRiskLevelByRs(rs, sex);
        Set<String> goodList = new LinkedHashSet<>();
        Set<String> nogoodList = new LinkedHashSet<>();
        Map<String, String> mapResolve = new HashMap<>();


        for (YhDiseaseAssessReport CSDiseaseAssessReport : findDiseaseNameAndRiskLevelByRs) {
            List<YHDiseaseRiskOptimal> goodList2 = CSDiseaseAssessReport.getGoodList();
            List<YHDiseaseRiskOptimal> nogoodList2 = CSDiseaseAssessReport.getNogoodList();
            List<YHDiseaseAssessResolve> glist = CSDiseaseAssessReport.getGlist();
            if (null != glist) {
                for (YHDiseaseAssessResolve CSDiseaseAssessResolve : glist) {
                    if (!mapResolve.containsKey(CSDiseaseAssessResolve.getType())) {
                        mapResolve.put(CSDiseaseAssessResolve.getType(), CSDiseaseAssessResolve.getName());
                    }

                }
            }
            if (null != goodList2) {
                for (YHDiseaseRiskOptimal CSDiseaseRiskOptimal : goodList2) {
                    if (!"1".equals(CSDiseaseRiskOptimal.getRadarRangePoint())) {
                        goodList.add(CSDiseaseRiskOptimal.getQname() + ":" + CSDiseaseRiskOptimal.getOptimal());
                    }

                }
            }

            if (null != nogoodList2) {
                for (YHDiseaseRiskOptimal CSDiseaseRiskOptimal : nogoodList2) {
                    if (!"1".equals(CSDiseaseRiskOptimal.getRadarRangePoint())) {
                        nogoodList.add(CSDiseaseRiskOptimal.getQname());
                    }
                }

            }


            String riskLevel = CSDiseaseAssessReport.getRiskLevel();
            if (riskLevel.contains("患") || riskLevel.contains("疑")) {
                huanb.append(CSDiseaseAssessReport.getDiseaseName()).append("、");
                CSDiseaseAssessReport.setSign(472);
                continue;
            }

            if (riskLevel.contains("高") && !riskLevel.contains("高血压") && !riskLevel.contains("较高")) {
                gaofx.append(CSDiseaseAssessReport.getDiseaseName()).append("、");
                CSDiseaseAssessReport.setSign(285);
                continue;
            }
            if (riskLevel.contains("较高")) {
                jiaogaofx.append(CSDiseaseAssessReport.getDiseaseName()).append("、");
                CSDiseaseAssessReport.setSign(375);
                continue;
            }
            if (riskLevel.contains("中等")) {
                zhongdengfx.append(CSDiseaseAssessReport.getDiseaseName()).append("、");
                CSDiseaseAssessReport.setSign(190);
                continue;
            }
            if (riskLevel.contains("低") || riskLevel.contains("较低")) {
                if (riskLevel.contains("较低")) {
                    CSDiseaseAssessReport.setSign(90);
                } else {
                    CSDiseaseAssessReport.setSign(0);
                }
                difx.append(CSDiseaseAssessReport.getDiseaseName()).append("、");
            }
        }

        Set<String> strings = YhDiseaseAssessReportServiceImpl.noGoodList(rs);
        model.addAttribute("goodList", goodList);
        model.addAttribute("nogoodList", strings);
        model.addAttribute("mapResolve", mapResolve);
        //查询总表得到各风险等级疾病
        //YiBoDiseaseRiskOptimalAssessTotalReport2web selectByPrimaryRs = YiBoDiseaseRiskOptimalAssessTotalReport2Mapper.selectByPrimaryRs(rs);


        YhQuestionCheckJson2Web selectByRsLimit1 = YhQuestionCheckJson2WebMapper.selectByRsLimit1(rs);
        String findQuestionCheckForCheckAnswerByrs = selectByRsLimit1.getUserCheckAnswer();
        if (StringUtils.isNotEmpty(findQuestionCheckForCheckAnswerByrs)) {
            // 将json字符串解析成document
            Object document;
            document = Configuration.defaultConfiguration().jsonProvider().parse(findQuestionCheckForCheckAnswerByrs);
            String age = JsonPath.read(document, "$.person.age").toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //获取String类型的时间
            String timeInfo = sdf.format(selectByRsLimit1.getUserCheckTime());
            //健康指标具体变化 查询上次
            YhDiseaseAssessTotalReport2web lastRsInfo = YhDiseaseAssessReportServiceImpl.lastRsInfo(selectByRsLimit1.getUserCode(), timeInfo);
            int thisNum = 0;
            int lastNum = 0;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String thisTime = simpleDateFormat.format(selectByRsLimit1.getUserCheckTime());
            if (null != lastRsInfo) {
                String lastTime = simpleDateFormat.format(lastRsInfo.getQtime());
                model.addAttribute("lastTime", lastTime);
                List<YhUserAssessmentReportVOJO> findAssessTotalReportLastInfo = YhDiseaseAssessReportServiceImpl.findAssessTotalReportLastInfo(lastRsInfo, document, rs);
                model.addAttribute("findAssessTotalReportLastInfo", findAssessTotalReportLastInfo);

                YhUserAssessmentReportVOJO findResolveInfo = YhDiseaseAssessReportServiceImpl.findResolveInfo(rs, lastRsInfo);
                model.addAttribute("findResolveInfo", findResolveInfo);
            } else {
                model.addAttribute("lastTime", "--");
            }
            model.addAttribute("thisTime", thisTime);

            List<RiskFactorsVOJO> lifeRiskInfo = YhDiseaseAssessReportServiceImpl.getLifeRiskInfo(rs, document, lastRsInfo);
            RiskFactorsVOJO rvojo = new RiskFactorsVOJO();
            rvojo.setRiskFactors("生活方式评分");
            rvojo.setIdealLevel("优秀（80~100分）");


            for (RiskFactorsVOJO CSRiskFactorsVOJO : lifeRiskInfo) {
                int thisScore = CSRiskFactorsVOJO.getThisScore();
                thisNum = thisNum + thisScore;
                Integer lastScore = CSRiskFactorsVOJO.getLastScore();
                if (null == lastScore) {
                    continue;
                }
                lastNum = lastNum + lastScore;
            }
            rvojo.setThisScore(thisNum);
            if (null != lastRsInfo) {
                rvojo.setLastScore(lastNum);
                if (thisNum == lastNum) {
                    rvojo.setChanges("无改善");
                }
                if (thisNum > lastNum) {
                    rvojo.setChanges("好转");
                }
                if (thisNum < lastNum) {
                    rvojo.setChanges("恶化");
                }

                String evaluateInfo;
                if (thisNum == lastNum) {
                    evaluateInfo = "没有发生变化";
                } else if (thisNum > lastNum) {

                    double shangshen = (double) Math.round(((thisNum - lastNum) / (double) lastNum * 100) * 100) / 100;
                    evaluateInfo = "有上升趋势，比上次积分增加了" + (thisNum - lastNum) + "分，上升率" + shangshen + "%。";
                } else {
                    double shangshen = (double) Math.round(((lastNum - thisNum) / (double) lastNum * 100) * 100) / 100;
                    evaluateInfo = "有下降趋势，比上次积分减少了" + (lastNum - thisNum) + "分，下降率" + shangshen + "%。";
                }
                model.addAttribute("evaluateInfo", evaluateInfo);
            }

            if (thisNum >= 80 && thisNum <= 100) {
                rvojo.setThisTime("优秀");
            } else if (thisNum >= 60 && thisNum < 80) {
                rvojo.setThisTime("良好");
            } else if (thisNum >= 40 && thisNum < 60) {
                rvojo.setThisTime("一般");
            } else {
                rvojo.setThisTime("差");
            }
            lifeRiskInfo.add(rvojo);
            model.addAttribute("lifeRiskInfo", lifeRiskInfo);
            model.addAttribute("thisNum", thisNum);
            model.addAttribute("lastNum", lastNum);


            model.addAttribute("evaluateAge", YhDiseaseAssessReportServiceImpl.evaluateAge(thisNum, Integer.valueOf(age), Integer.parseInt(sex)));

            model.addAttribute("foodInfo", YhDiseaseAssessReportServiceImpl.foodInfo(rs));

            model.addAttribute("sportInfo", YhDiseaseAssessReportServiceImpl.sportInfo(document));


            List<YhUserAssessmentReportVOJO> currentLifestyle = YhDiseaseAssessReportServiceImpl.currentLifestyle(rs, document);
            model.addAttribute("currentLifestyle", currentLifestyle);
            List<YhUserAssessmentReportVOJO> suggestions = YhDiseaseAssessReportServiceImpl.suggestions(rs, document);
            model.addAttribute("suggestions", suggestions);
            //代谢综合症指标

            List<RiskFactorsVOJO> metabolizeList = YhDiseaseAssessReportServiceImpl.metabolize(rs, sex);
            model.addAttribute("metabolizeList", metabolizeList);
            //肥胖症危险指标
            List<RiskFactorsVOJO> obesityRelated = YhDiseaseAssessReportServiceImpl.obesityRelated(rs);
            model.addAttribute("obesityRelated", obesityRelated);
            //医学健康指标 异常

            String height = JsonPath.read(document, "$.height").toString();
            String weight = JsonPath.read(document, "$.weight").toString();
            String serumCreatinine = JsonPath.read(document, "$.serum_creatinine").toString();
            String waistValue = JsonPath.read(document, "$.waist").toString();
            if (StringUtils.isNotEmpty(height) && StringUtils.isNotEmpty(weight)) {
                double bf = (Double.parseDouble(weight)
                        / ((Double.parseDouble(height) / 100) * (Double.parseDouble(height)) / 100));
                BigDecimal b = new BigDecimal(bf);
                double bmiValue = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (bmiValue < 18.5) {
                    model.addAttribute("peipangWeiyi1", 10);
                } else if (bmiValue >= 18.5 && bmiValue < 24) {
                    model.addAttribute("peipangWeiyi1", 130);
                } else if (bmiValue >= 24 && bmiValue <= 28) {
                    model.addAttribute("peipangWeiyi1", 245);
                } else {
                    model.addAttribute("peipangWeiyi1", 365);
                }
                model.addAttribute("bmiValue", bmiValue);
            }
            if (com.yihe.system.utils.StringUtil.isEmpty(waistValue)) {
                waistValue = "0";
            }
            model.addAttribute("waistValue", Double.parseDouble(waistValue));
            if (sex.equals("1")) {
                if (Double.parseDouble(waistValue) < 85) {
                    model.addAttribute("peipangWeiyi2", 125);
                } else if (Double.parseDouble(waistValue) >= 85 && Double.parseDouble(waistValue) < 110) {
                    model.addAttribute("peipangWeiyi2", 280);
                } else {
                    model.addAttribute("peipangWeiyi2", 430);
                }
            } else {
                if (Double.parseDouble(waistValue) < 80) {
                    model.addAttribute("peipangWeiyi2", 125);
                } else if (Double.parseDouble(waistValue) >= 80 && Double.parseDouble(waistValue) < 105) {
                    model.addAttribute("peipangWeiyi2", 280);
                } else {
                    model.addAttribute("peipangWeiyi2", 430);
                }
            }

            YhDiseaseRiskResultAppVOJO findAssessResultInfo = YhDiseaseAssessReportServiceImpl.findAssessResultInfo(rs);

            JSONObject jsonObject = JSONObject.parseObject(findQuestionCheckForCheckAnswerByrs);
            //代谢综合症 数值有0项符合，低危
            //有1项、2项符合，高危
            //三项以及上，现患
            int metabolize = 0;
            YhUserAssessmentReportVOJO vojo = new YhUserAssessmentReportVOJO();
            String age2 = JsonPath.read(document, "$.person.age").toString();
            String sexStr = JsonPath.read(document, "$.person.sex").toString();
            YhUserReginfo userReginfo = yhUserReginfoService.selectByPrimaryKey(selectByRsLimit1.getUserCode());
            vojo.setAge(age2);
            vojo.setName(userReginfo.getName());
            vojo.setSex(sexStr.equals("1") ? "男" : "女");
            String s = yhUserReginfoService.selectOrgName(selectByRsLimit1.getUserCode());
            vojo.setUnit(s);

            //家族史
            List<YhUserAssessmentReportVOJO> mainRiskFactors = YhDiseaseAssessReportServiceImpl.mainRiskFactors(rs, document);
            boolean flagMlist = false;
            if (null != mainRiskFactors) {
                for (YhUserAssessmentReportVOJO mList : mainRiskFactors) {
                    String riskFactors = mList.getRiskFactors();
                    if (StringUtil.isNotEmpty(riskFactors)) {
                        flagMlist = true;
                        break;
                    }

                }
            }
            if (flagMlist) {
                model.addAttribute("mainRiskFactors", mainRiskFactors);
            } else {
                model.addAttribute("mainRiskFactors", null);
            }


            //获取String类型的时间
            String createdate = simpleDateFormat.format(selectByRsLimit1.getUserCheckTime());
            vojo.setPinggutime(createdate);
            //血糖
            String fbg = jsonObject.getString("fbg");
            if (Double.parseDouble(fbg) >= 6.1) {
                metabolize++;
            }
            vojo.setBs(fbg);
            //收缩压
            String sbp = jsonObject.getString("sbp");
            //舒张压
            String dbp = jsonObject.getString("dbp");
            if (StringUtil.isNotEmpty(sbp) && StringUtil.isNotEmpty(dbp)) {
                vojo.setSbp(sbp);
                vojo.setDbp(dbp);
                if (Double.parseDouble(sbp) >= 140 || Double.parseDouble(dbp) >= 90) {
                    metabolize++;
                }
            }
            //腰围
            String waist = jsonObject.getString("waist");
            vojo.setTheWaist(waist);
            //身高
            //体重
            if (StringUtil.isNotEmpty(height) && StringUtil.isNotEmpty(weight)) {
                double heightOf = Double.parseDouble(height);
                double weightOf = Double.parseDouble(weight);
                Double bmi = weightOf / ((heightOf / 100) * (heightOf / 100));
                String format = String.format("%.1f", bmi);

                if (Double.parseDouble(format) >= 25) {
                    metabolize++;
                }
                vojo.setBmi(format);
            }
            //总胆固醇
            String cholesterol = jsonObject.getString("cholesterol");
            vojo.setTc(cholesterol);
            //甘油三酯
            String tg = jsonObject.getString("tg");
            vojo.setTg(tg);
            //高密度脂蛋白
            String hdlc = jsonObject.getString("hdlc");
            vojo.setHdlc(hdlc);
            //低密度脂蛋白胆固醇
            String ldlc = jsonObject.getString("ldlc");
            vojo.setLdlc(ldlc);
            if ("1".equals(sex)) {
                if (Double.parseDouble(tg) >= 1.7 || Double.parseDouble(hdlc) < 0.9) {
                    metabolize++;
                }
            } else {
                if (Double.parseDouble(tg) >= 1.7 || Double.parseDouble(hdlc) < 1) {
                    metabolize++;
                }
            }
            if (null != findAssessResultInfo) {


                //膳食结构

                String lastTime = currentLifestyle.get(0).getLastTime();

                vojo.setB4("您的膳食结果基本合理".equals(lastTime) ? "合理" : "不合理");


                //体力活动
                String sport = findAssessResultInfo.getSport();
                if (StringUtil.isNotEmpty(sport)) {
                    JSONObject sportobj = JSONObject.parseObject(sport);
                    //0：不充足1：充足
                    String sportStr = sportobj.getString("now_value");
                    if (StringUtil.isNotEmpty(sportStr)) {
                        vojo.setSport(sportStr.equals("1") ? "充足" : "不充足");
                    }
                }
            }


            //吸烟
            String smoke_status = jsonObject.getString("smoke_status");
            if (StringUtil.isNotEmpty(smoke_status)) {
                if (smoke_status.equals("1")) {
                    vojo.setSmoke("吸烟");
                }
                if (smoke_status.equals("2")) {
                    vojo.setSmoke("不吸烟");
                }
                if (smoke_status.equals("3")) {
                    vojo.setSmoke("已戒烟");
                }

            }

            //饮酒
            String food_drink = jsonObject.getString("food_drink");
            if (StringUtil.isNotEmpty(food_drink)) {
                if (food_drink.equals("1")) {
                    vojo.setFoodDrink("喝酒");
                }
                if (food_drink.equals("2")) {
                    vojo.setFoodDrink("不喝");
                }


            }
            //T值
            String t_score = jsonObject.getString("t_score");
            vojo.setT_score(t_score);
            //血尿酸
            String bua = jsonObject.getString("bua");

            if (StringUtil.isNotEmpty(bua)) {

                vojo.setBua(bua);

            } else {
                vojo.setBua("--");
            }
            if (StringUtil.isNotEmpty(serumCreatinine)) {
                vojo.setSerumCreatinine(serumCreatinine);
            } else {
                vojo.setSerumCreatinine("--");
            }

            model.addAttribute("vojo", vojo);
            //代谢综合症判断
            if (metabolize == 0) {
                //低危
                model.addAttribute("metabolizeNum", 38);
            }
            if (metabolize == 1 || metabolize == 2) {
                //高危
                model.addAttribute("metabolizeNum", 225);
            }
            if (metabolize > 2) {
                //现患
                model.addAttribute("metabolizeNum", 415);
            }


        } else {
            YhUserAssessmentReportVOJO vojo = new YhUserAssessmentReportVOJO();
            model.addAttribute("vojo", vojo);
        }
        String getrepotimg = userService.getrepotimg();


        model.addAttribute("findDiseaseNameAndRiskLevelByRs", findDiseaseNameAndRiskLevelByRs);
        model.addAttribute("rs", rs);
        model.addAttribute("huanb", huanb.toString().equals("") ? "无" : huanb.substring(0, huanb.length() - 1));
        model.addAttribute("gaofx", gaofx.toString().equals("") ? "无" : gaofx.substring(0, gaofx.length() - 1));
        model.addAttribute("jiaogaofx", jiaogaofx.toString().equals("") ? "无" : jiaogaofx.substring(0, jiaogaofx.length() - 1));
        model.addAttribute("zhongdengfx", zhongdengfx.toString().equals("") ? "无" : zhongdengfx.substring(0, zhongdengfx.length() - 1));
        model.addAttribute("difx", difx.toString().equals("") ? "无" : difx.substring(0, difx.length() - 1));
        model.addAttribute("getrepotimg", getrepotimg);

        return "personalUser/healthRecord/assessmentReportTest2";
    }


    @ApiOperation(value = "时间段对比")
    @RequestMapping(value = "data/timecontrast", method = RequestMethod.GET)
    public String timecontrast() {

        return "exportdata/timecontrast";
    }

    @RequestMapping(value = "health/userPlanPresentation", method = RequestMethod.GET)
    public String userPlanPresentation(Model model, Integer planUnique, String usercode) {
        HealthPlanVOJO dataUserPlanInfo = yhUserHealthyPlanServiceImpl.findYhUserHealthyPlanByplanUniqueByHealthPlanVOJO(planUnique, usercode);
        if (dataUserPlanInfo.getPlanState().equals("0")) {
            dataUserPlanInfo.setPlanDay(dataUserPlanInfo.getPlanDay() - 1);
        }
        String getimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        String leftimg;
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            leftimg = jsonObject.getString("22");
        }else{
            leftimg = yhUserPageSettingService.getimg(22);
        }
        model.addAttribute("leftimg", leftimg);
        model.addAttribute("dataUserPlanInfo", dataUserPlanInfo);
        return "personalUser/healthRecord/planPresentation";
    }

    @ApiOperation(value = "忘记密码")
    @RequestMapping(value = "forgotpassword", method = RequestMethod.GET)
    public String forgotpassword() {
        return "sso/forget";
    }

    @ApiOperation(value = "忘记密码")
    @RequestMapping(value = "toyanzhengma", method = RequestMethod.GET)
    public String toyanzhengma() {
        return "sso/testyanzhengma";
    }

    @ApiOperation(value = "健管师服务任务设置")
    @RequestMapping(value = "service/division", method = RequestMethod.GET)
    public String servicedivision(Integer type, Model model) {
        model.addAttribute("type", type);
        return "division/divisionsetting";
    }

    @ApiOperation(value = "首次检测项设置")
    @RequestMapping(value = "service/testingfirst", method = RequestMethod.GET)
    public String testingfirst() {
        return "division/testingfirst";
    }

    @ApiOperation(value = "久未记录检测项设置")
    @RequestMapping(value = "service/notrecordlongtime", method = RequestMethod.GET)
    public String notrecordlongtime() {
        return "division/notrecordlongtime";
    }


    @ApiOperation(value = "新增服务页面")
    @RequestMapping(value = "taskservice/toaddservicetask", method = RequestMethod.GET)
    public String toaddservicetask() {
        return "taskservice/addservicetask";
    }

    @ApiOperation(value = "评估页面")
    @RequestMapping(value = "/screeningListInfo", method = RequestMethod.GET)
    public String screeningListInfo() {
        return "health/assess/screeningList";
    }

    @ApiOperation(value = "干预计划模板")
    @RequestMapping(value = "Intervention/template", method = RequestMethod.GET)
    public String Interventiontemplate() {
        return "Interventionprograms/Interventiontemplate";
    }

    @ApiOperation(value = "添加干预计划模板")
    @RequestMapping(value = "Intervention/addtemplate", method = RequestMethod.GET)
    public String addInterventiontemplate(Model model, String interventionId) {
        model.addAttribute("id", interventionId);
        return "Interventionprograms/addInterventiontemplate";
    }

    @ApiOperation(value = "干预计划")
    @RequestMapping(value = "division/divisionInterventionplan", method = RequestMethod.GET)
    public String Interventiontemplates(Model model, String type) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl.selectAllInfo(userCode);
        List<YhDiseaseCategory> category = yhInterventionTemplateService.selectallcategory();
        model.addAttribute("selectAllInfo", selectAllInfo);
        model.addAttribute("category", category);
        model.addAttribute("type", type);
        return "Interventionprograms/divisionInterventionplan";
    }

    @ApiOperation(value = "干预计划")
    @RequestMapping(value = "division/grouping", method = RequestMethod.GET)
    public String grouping(Model model, String type) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl.selectAllInfo(userCode);
        List<YhDiseaseCategory> category = yhInterventionTemplateService.selectallcategory();
        model.addAttribute("selectAllInfo", selectAllInfo);
        model.addAttribute("category", category);
        model.addAttribute("type", type);
        return "Interventionprograms/grouping";
    }

    @ApiOperation(value = "点击干预分组管理的管理")
    @RequestMapping(value = "YhGroupIntervention/managegroup", method = RequestMethod.GET)
    public String managegroup(Model model, String gropid, String groupname) {
        model.addAttribute("gropid", gropid);
        model.addAttribute("groupname", groupname);
        return "Interventionprograms/groupIntervention";
    }

    @ApiOperation(value = "查找某人的详细计划")
    @RequestMapping(value = "Interventionprograms/selectdetailplan", method = RequestMethod.GET)
    public String selectdetailplan(Model model, String userCode) {
        model.addAttribute("userCode", userCode);
        return "Interventionprograms/detailplanbyuser";
    }

    @ApiOperation(value = "goeasy")
    @RequestMapping(value = "goeasy/test1", method = RequestMethod.GET)
    public String goeasytest1(Model model, String usercode) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        User findUserNameAndImg = defaultLayIMService.findUserNameAndImg(usercode);
        String getimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        String topimg;
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            topimg = jsonObject.getString("21");
        }else{
            topimg = yhUserPageSettingService.getimg(21);
        }

        model.addAttribute("topimg", topimg);

        model.addAttribute("usercode", usercode);

        model.addAttribute("findUserNameAndImg", findUserNameAndImg);
        model.addAttribute("globalName", Global.getName());
        model.addAttribute("doctor", principal.getUserCode());

        return "goeasy/test1";
    }

    @ApiOperation(value = "goeasy")
    @RequestMapping(value = "goeasy/test2", method = RequestMethod.GET)
    public String goeasytest2(Model model, String usercode) {

        return "goeasy/test2";
    }

    @ApiOperation(value = "goeasy")
    @RequestMapping(value = "goeasy/index", method = RequestMethod.GET)
    public String goeasyindex(Model model, String usercode) {

        return "goeasy/index";
    }

    @ApiOperation(value = "goeasy")
    @RequestMapping(value = "goeasy/chatlog", method = RequestMethod.GET)
    public String goeasychatlog(Model model, String id, String type) {
        model.addAttribute("usercode", id);
        return "goeasy/chatlog";
    }

    @ApiOperation(value = "聊天详情")
    @RequestMapping(value = "goeasy/chatback", method = RequestMethod.GET)
    public String goeasychatback(Model model, String usercode, String username) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();

        List<YhUserChatRecordWed> findChatInfoByUserCode = YhUserChatRecordWedServiceImpl
                .findChatInfoByUserCode(usercode, principal.getUserCode());
        model.addAttribute("usercode", usercode);
        model.addAttribute("username", username);
        model.addAttribute("doctorid", principal.getUserCode());
        model.addAttribute("chatList", findChatInfoByUserCode);
        return "goeasy/chatback";
    }

    @ApiOperation(value = "机构端服务管理")
    @RequestMapping(value = "task/manageservice", method = RequestMethod.GET)
    public String manageservice(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl.selectAllInfo(userCode);
        model.addAttribute("selectAllInfo", selectAllInfo);
        return "taskservice/manageservice";
    }

    @ApiOperation(value = "久未使用服务管理")
    @RequestMapping(value = "task/orgwattingtaskservice", method = RequestMethod.GET)
    public String orgwattingtaskservice(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl.selectAllInfo(userCode);
        model.addAttribute("selectAllInfo", selectAllInfo);
        return "taskservice/orgwattingtaskservice";
    }


    @ApiOperation(value = "干预计划服务管理")
    @RequestMapping(value = "task/orgmanagetaskplanservice", method = RequestMethod.GET)
    public String orgmanagetaskplanservice(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl.selectAllInfo(userCode);
        model.addAttribute("selectAllInfo", selectAllInfo);
        return "taskservice/orgmanagetaskplanservice";
    }

    @ApiOperation(value = "机构端统计分析")
    @RequestMapping(value = "task/statisticalAnalysis", method = RequestMethod.GET)
    public String statisticalAnalysis() {

        return "taskservice/statisticalAnalysis";
    }

    @ApiOperation(value = "复选框")
    @RequestMapping(value = "rolemanage/rolemanagepage", method = RequestMethod.GET)
    public String rolemanagepage(Integer roleid, Model model) {
        //System.out.println(roleid);
        model.addAttribute("roleid", roleid);
        return "background/fuxuankuang";
    }


    @ApiOperation(value = "干预饮食")
    @RequestMapping(value = "intervene/programme", method = RequestMethod.GET)
    public String interveneprogramme(Model model, Integer fanhui) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        // 查全部分组
        List<YHDocGroup> selectAllInfo = YHDocGroupServiceImpl.selectAllInfo(userCode);
        // 疾病集合
        List<YhDiseaseCategory> selectByExample = YhDiseaseCategoryMapper
                .selectByExample(new YhDiseaseCategoryExample());
        List<String> findElevelInfoGroupBy = YhFoodSrecipeRecordMapper.findElevelInfoGroupBy();
        List<String> findFoodUserScrecipeGroupLevel = YhFoodUserScrecipeWebNewServiceImpl
                .findFoodUserScrecipeGroupLevel();
        model.addAttribute("selectAllInfo", selectAllInfo);
        model.addAttribute("dCategory", selectByExample);
        model.addAttribute("todayFoodGroup", findFoodUserScrecipeGroupLevel);
        model.addAttribute("findElevelInfoGroupBy", findElevelInfoGroupBy);
        model.addAttribute("fanhui", fanhui);
        return "interveneProgramme/programme";
    }

    @ApiOperation(value = "新增食谱")
    @RequestMapping(value = "intervene/addDailyRecipes", method = RequestMethod.GET)
    public String interveneaddDailyRecipes(Model model, @RequestParam(required = false, defaultValue = "1200") String flevel, String layer, Integer uFoodErecipeId) {
        // 疾病集合
        List<YhDiseaseCategory> selectByExample = YhDiseaseCategoryMapper.selectByPrimaryKeyByIsAssessment();
        List<String> selectByPrimaryKey = YhFodClassifenergyWebMapper.findFenergyListAll();
        YhFodClassifenergyWeb findFenergyByFlevel = YhFodClassifenergyWebMapper
                .findFenergyByFlevel(flevel);
        // 早餐 1_90;1_90;4_18;3_90;2_90;
        String getfBreakfast = findFenergyByFlevel.getfBreakfast();
        // 午餐1_90;1_45;4_12;4_12;4_12;5_45;12_45;12_45;7_90;
        String getfLunch = findFenergyByFlevel.getfLunch();
        // 晚餐1_90;1_45;4_18;4_18;5_45;14_45;7_90;
        String getfDinner = findFenergyByFlevel.getfDinner();
        // 加餐13_90;
        String getfExtrameal = findFenergyByFlevel.getfExtrameal();
        String[] split = getfBreakfast.split(";");
        String[] split3 = getfLunch.split(";");
        String[] split4 = getfDinner.split(";");
        String[] split5 = getfExtrameal.split(";");

        findFenergyByFlevel.setZaoSplit(split);
        findFenergyByFlevel.setWuSplit(split3);
        findFenergyByFlevel.setWanSplit(split4);
        findFenergyByFlevel.setJiaSplit(split5);




	/*	for (String string : split5) {
			String[] split2 = string.split("_");
			// 食物分类
			String ftype = split2[0];
			// 食物热量
			String string2 = split2[1];
			List<YhFoodCookBook> findCookBookInfoByType = YhFoodCookBookMapper
					.findCookBookInfoByType(Integer.valueOf(ftype));

			jiaBooks.add(findCookBookInfoByType);
		}
		*/


        model.addAttribute("dCategory", selectByExample);
        model.addAttribute("foodFenergy", selectByPrimaryKey);
        model.addAttribute("flevel", flevel);
        model.addAttribute("bookInfo", findFenergyByFlevel.getZaoSplit());
        model.addAttribute("wubookInfo", findFenergyByFlevel.getWuSplit());
        model.addAttribute("wanbookInfo", findFenergyByFlevel.getWanSplit());
        model.addAttribute("jiabookInfo", findFenergyByFlevel.getJiaSplit());
        model.addAttribute("layer", layer);
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        model.addAttribute("yhType", yh.getUserType());
        if (null == uFoodErecipeId) {
            uFoodErecipeId = 0;
        }
        model.addAttribute("uFoodErecipeId", uFoodErecipeId);

        return "interveneProgramme/addDailyRecipes";
    }

    @ApiOperation(value = "新增食谱")
    @RequestMapping(value = "intervene/addSetMeal", method = RequestMethod.GET)
    public String addSetMeal(Model model, Integer id) {
        // 疾病集合
        List<YhDiseaseCategory> selectByExample = YhDiseaseCategoryMapper.selectByPrimaryKeyByIsAssessment();
        List<String> selectByPrimaryKey = YhFodClassifenergyWebMapper.findFenergyListAll();
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        model.addAttribute("yhType", yh.getUserType());
        model.addAttribute("dCategory", selectByExample);
        model.addAttribute("foodFenergy", selectByPrimaryKey);
        model.addAttribute("id", id);

        return "interveneProgramme/addSetMealPage";
    }

    @ApiOperation(value = "新增食谱")
    @RequestMapping(value = "intervene/addSetMeal2", method = RequestMethod.GET)
    public String addSetMeal2(Model model, Integer id) {
        // 疾病集合
        List<YhDiseaseCategory> selectByExample = YhDiseaseCategoryMapper.selectByPrimaryKeyByIsAssessment();
        List<String> selectByPrimaryKey = YhFodClassifenergyWebMapper.findFenergyListAll();
        YhFoodSrecipeNewWeb selectByPrimaryKey2 = YhFoodSrecipeNewWebMapper.selectByPrimaryKey(Long.valueOf(id));
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        model.addAttribute("yhType", yh.getUserType());
        model.addAttribute("dCategory", selectByExample);
        model.addAttribute("selectByPrimaryKey2", selectByPrimaryKey2);
        model.addAttribute("foodFenergy", selectByPrimaryKey);
        model.addAttribute("id", id);
        return "interveneProgramme/addSetMealPage2";
    }

    @ApiOperation(value = "新增食谱")
    @RequestMapping(value = "food/transfer", method = RequestMethod.GET)
    public Map<String, Object> foodtransfer(Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<YhFoodUserDiary> foodtransfer = YhFoodUserDiaryMapper.foodtransfer();
        List<String> notlist = new ArrayList<String>();
        List<String> nocaiyao = new ArrayList<String>();
        for (int i = 0; i < foodtransfer.size(); i++) {
            YhFoodUserDiary yhFoodUserDiary = foodtransfer.get(i);
            //1_2_100;1_1_100;1_8_101;2_15_101;
            String getfElements = yhFoodUserDiary.getfElements();
            String newfelements = "";
            Long getuFdId = yhFoodUserDiary.getuFdId();
            if (StringUtils.isEmpty(getfElements)) {
                continue;
            }
            String[] split = getfElements.split(";");

            //1_2_100
            for (String string : split) {
                String[] split2 = string.split("_");
                if (split2.length == 4) {
                    continue;
                }
                String type = split2[0];
                String foodid = split2[1];
                String keshu = split2[2];
                YhFoodCookBook selectByPrimaryKey = YhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(foodid));
                if (null == selectByPrimaryKey) {
                    notlist.add(foodid);
                } else {
                    String getfType = selectByPrimaryKey.getfType();
                    if ("10".equals(getfType)) {
                        YhFoodCookedWeb selectByPrimaryKey2 = YhFoodCookedWebMapper.selectByPrimaryKey(Integer.valueOf(foodid));
                        if (null == selectByPrimaryKey2) {
                            continue;
                        }
                        //90342_0.6;90472_0.375;91201_0.025;
                        String getfCookedelements = selectByPrimaryKey2.getfCookedelements();
                        String[] split3 = getfCookedelements.split(";");
                        String caiyInfo = "";
                        for (String string2 : split3) {
                            String[] split4 = string2.split("_");
                            String string3 = split4[0];
                            String string4 = split4[1];

                            caiyInfo = caiyInfo + string3 + "_" + (Float.valueOf(keshu) * Float.valueOf(string4)) + ",";
                        }
                        caiyInfo = caiyInfo.substring(0, caiyInfo.length() - 1);
                        newfelements = newfelements + "10_" + string + "," + caiyInfo + ";";
                    } else {
                        newfelements = newfelements + "1_" + string + ";";
                    }
                }
            }

            if (StringUtils.isNotEmpty(newfelements)) {
                YhFoodUserDiary yhFoodUserDiary2 = new YhFoodUserDiary();
                yhFoodUserDiary2.setuFdId(getuFdId);
                yhFoodUserDiary2.setfElements(newfelements);
                YhFoodUserDiaryMapper.updateYhFoodUserDiary2(yhFoodUserDiary2);
            }


        }
        map.put("notlist", notlist);
        map.put("nocaiyao", nocaiyao);
        return map;
    }

    @ApiOperation(value = "医生控制台New")
    @RequestMapping(value = "visualization", method = RequestMethod.GET)
    public String visualization(Model model, Integer id) throws ParseException {

        return "visualization/index";
    }

    /**
     * 健管师控制台
     */

    @ApiOperation(value = "医生控制台New")
    @RequestMapping(value = "total/permissions", method = RequestMethod.GET)
    public String comtrollerNew(Model model, Integer id) throws ParseException {
        YhUserPermissionExample example = new YhUserPermissionExample();
        YhUserPermissionExample.Criteria criteria = example.createCriteria();
        //criteria.andPermissionValueEqualTo("cmd:doctor:controller");
        criteria.andPidEqualTo(id);
        example.setOrderByClause("orders");
        List<YhUserPermission> yhUserPermissions = yhUserPermissionMapper.selectByExample(example);
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        List<YhUserPermission> yhUserPermissionss = yhUserPermissionMapper.selectpermissions(id, yh.getUserCode().toString());
        model.addAttribute("yhUserPermissions", yhUserPermissionss);
        return "doctorConsole/indexNew";
    }

    /**
     * 健管师控制台
     */

    @ApiOperation(value = "用户日记")
    @RequestMapping(value = "diary/management", method = RequestMethod.GET)
    public String diarymanagement(Model model) throws ParseException {

        return "health/diary/index";
    }


    /**
     * 服务任务的总页
     */

    @ApiOperation(value = "服务任务的总页")
    @RequestMapping(value = "taskservice/totaltaskservice", method = RequestMethod.GET)
    public String totaltaskservice(Integer type, Model model) throws ParseException {
        model.addAttribute("type", type);
        return "taskservice/totaltaskservice";
    }

    /**
     * 机构端服务任务的总页
     */

    @ApiOperation(value = "机构端服务任务的总页")
    @RequestMapping(value = "task/orgtotaltaskservice", method = RequestMethod.GET)
    public String orgtotaltaskservice(Model model) throws ParseException {

        return "taskservice/orgtotaltaskservice";
    }


    /**
     * 机构端服务任务的总页
     */

    @ApiOperation(value = "机构端服务任务的总页")
    @RequestMapping(value = "basic/user", method = RequestMethod.GET)
    public String basicuser(Model model) throws ParseException {

        return "exportdata/basicuser";
    }

    @ApiOperation(value = "中医辨识Pdf导出")
    @RequestMapping(value = "/usertosee/zhongyibianshi", method = RequestMethod.GET)
    public String zhongyibianshi(Model model, Integer reportId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String getimg = null,getrepotimg = null;
        String orgCode = redisUtils.get("ORG_CODE");
        if(StringUtils.isNotEmpty(orgCode)){
            getimg = yhUserPageSettingService.getOrgNameInfo(orgCode);
        }
        if(StringUtils.isNotEmpty(getimg)){
            JSONObject jsonObject = JSONObject.parseObject(getimg);
            getrepotimg = jsonObject.getString("22");
        }else{
            getrepotimg = yhUserPageSettingService.getimg(22);
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<YhPainResolveWithBLOBs> reportList = new ArrayList<YhPainResolveWithBLOBs>();
        YhChineseAnwserr yhChineseAnwser = yhcheidentificationService.selectByPrimaryKey(reportId);
        if (yhChineseAnwser.getGender() == 1) {
            yhChineseAnwser.setSex("男");
        } else {
            yhChineseAnwser.setSex("女");
        }
        YhChineseAnwser2r yhChineseAnwser2 = new YhChineseAnwser2r();
        List<YhChineseAnwser3r> anwser3s = new ArrayList<>();
        List<YhChineseAnwser4r> anwser4s = new ArrayList<>();
        String A = "不是";
        String B = "不是";
        String C = "不是";
        String D = "不是";
        String E = "不是";
        String F = "不是";
        String G = "不是";
        String H = "不是";
        String I = "不是";
        if (yhChineseAnwser.getaIstype() == 1) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("平和质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getbIstype() == 1) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("气虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getcIstype() == 1) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("阳虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getdIstype() == 1) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("阴虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.geteIstype() == 1) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("痰湿质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getfIstype() == 1) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("湿热质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getgIstype() == 1) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("血瘀质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.gethIstype() == 1) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("气郁质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getiIstype() == 1) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("特禀质");
            anwser3s.add(yhChineseAnwser3);
        }

        if (yhChineseAnwser.getaIstype() == 2) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("平和质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getbIstype() == 2) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("气虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getcIstype() == 2) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("阳虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getdIstype() == 2) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("阴虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.geteIstype() == 2) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("痰湿质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getfIstype() == 2) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("湿热质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getgIstype() == 2) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("血瘀质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.gethIstype() == 2) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("气郁质");
            anwser3s.add(yhChineseAnwser3);
        }
        if (yhChineseAnwser.getiIstype() == 2) {
            YhChineseAnwser3r yhChineseAnwser3 = new YhChineseAnwser3r();
            yhChineseAnwser3.setName("特禀质");
            anwser3s.add(yhChineseAnwser3);
        }
        yhChineseAnwser2.setZonghe(anwser3s);
        List<YhChineseAnwser4r> yhChineseAnwser4s = new ArrayList<>();
        for (int i = 0; i < anwser3s.size(); i++) {
            if (anwser3s.get(i).getName().equals("平和质")) {
                YhChineseAnwser4r yhChineseAnwser4 = new YhChineseAnwser4r();
                yhChineseAnwser4.setName("平和质");
                yhChineseAnwser4.setTizhi("正常体质");
                if (yhChineseAnwser.getaIstype() == 1) {
                    yhChineseAnwser4.setState("是");
                } else if (yhChineseAnwser.getaIstype() == 2) {
                    yhChineseAnwser4.setState("基本是");
                }
                List<YhChineseAnwser5r> yhChineseAnwser5s = new ArrayList<>();

                YhChineseAnwser5r yhChineseAnwser5 = new YhChineseAnwser5r();
                yhChineseAnwser5.setTitle("总体特征");
                yhChineseAnwser5.setTxt("阴阳气血调和，以体态适中、面色红润、精力充沛等为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser5);

                YhChineseAnwser5r yhChineseAnwser51 = new YhChineseAnwser5r();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("体形匀称健壮。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5r yhChineseAnwser57 = new YhChineseAnwser5r();
                yhChineseAnwser57.setTitle("常见表现");
                yhChineseAnwser57.setTxt("面色、肤色润泽，头发稠密有光泽，目光有神，鼻色明润，嗅觉通利，唇色红润，不易疲劳， 精力充沛，耐受寒热，睡眠良好，胃纳佳，二便正常，舌色淡红，苔薄白，脉和缓有力。");
                yhChineseAnwser5s.add(yhChineseAnwser57);

                YhChineseAnwser5r yhChineseAnwser53 = new YhChineseAnwser5r();
                yhChineseAnwser53.setTitle("心理特征");
                yhChineseAnwser53.setTxt("性格随和开朗。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5r yhChineseAnwser54 = new YhChineseAnwser5r();
                yhChineseAnwser54.setTitle("发病倾向");
                yhChineseAnwser54.setTxt("平素患病较少");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5r yhChineseAnwser55 = new YhChineseAnwser5r();
                yhChineseAnwser55.setTitle("对外界环境适应能力");
                yhChineseAnwser55.setTxt("对自然环境和社会环境适应能力较强。");
                yhChineseAnwser5s.add(yhChineseAnwser55);
                yhChineseAnwser4.setZong(yhChineseAnwser5s);

                List<YhChineseAnwser5r> yhChineseAnwser52 = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser60 = new YhChineseAnwser5r();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。宜保持开朗舒畅的情绪。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5r yhChineseAnwser61 = new YhChineseAnwser5r();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜丰富饮食的种类，形成多样化的饮食习惯，多吃五谷杂粮、蔬菜瓜果，少食过于油腻及辛辣之物。\n" +
                        "建议选择具有健脾、滋肾作用的饮食，如小麦、黄豆、山药、豆腐、木耳、苹果等。\n" +
                        "参考食疗药膳：山药扁豆粥");
                yhChineseAnwser52.add(yhChineseAnwser55);

                YhChineseAnwser5r yhChineseAnwser62 = new YhChineseAnwser5r();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("应持之以恒地保持良好的生活起居习惯。\n" +
                        "保持充足的睡眠时间，不宜食后即睡。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5r yhChineseAnwser63 = new YhChineseAnwser5r();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("建议形成良好的运动习惯，每日进行半小时～1小时的有氧运动。推荐保健运动为八段锦、太极剑以及太极拳。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5r yhChineseAnwser64 = new YhChineseAnwser5r();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：足三里、气海；\n" +
                        "定位：足三里穴位于外膝眼下三寸，胫骨前嵴外1横指处；气海穴位于前正中线上，脐下1.5寸。\n" +
                        "操作：点按法，用大拇指或中指按压足三里、气海穴，足三里穴可以两侧穴位同时操作。每次按压操作5～10分钟，每日两次，10天1个疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if (anwser3s.get(i).getName().equals("气虚质")) {
                YhChineseAnwser4r yhChineseAnwser4 = new YhChineseAnwser4r();
                yhChineseAnwser4.setName("气虚质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if (yhChineseAnwser.getbIstype() == 1) {
                    yhChineseAnwser4.setState("是");
                } else if (yhChineseAnwser.getbIstype() == 2) {
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5r> yhChineseAnwser5s = new ArrayList<>();

                YhChineseAnwser5r yhChineseAnwser50 = new YhChineseAnwser5r();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("元气不足，以疲乏、气短、自汗等气虚表现为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5r yhChineseAnwser51 = new YhChineseAnwser5r();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("肌肉松软不实。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5r yhChineseAnwser59 = new YhChineseAnwser5r();
                yhChineseAnwser59.setTitle("常见表现");
                yhChineseAnwser59.setTxt("平素语音低弱，气短懒言，容易疲乏，精神不振，易出汗，舌淡红，舌边有齿痕，脉弱。");
                yhChineseAnwser5s.add(yhChineseAnwser59);

                YhChineseAnwser5r yhChineseAnwser53 = new YhChineseAnwser5r();
                yhChineseAnwser53.setTitle("心理特征");
                yhChineseAnwser53.setTxt("性格内向，不喜冒险。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5r yhChineseAnwser54 = new YhChineseAnwser5r();
                yhChineseAnwser54.setTitle("发病倾向");
                yhChineseAnwser54.setTxt("易患感冒、内脏下垂等病;病后康复缓慢。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5r yhChineseAnwser55 = new YhChineseAnwser5r();
                yhChineseAnwser55.setTitle("对外界环境适应能力");
                yhChineseAnwser55.setTxt("不耐受风、寒、暑、湿邪。");
                yhChineseAnwser5s.add(yhChineseAnwser55);
                yhChineseAnwser4.setZong(yhChineseAnwser5s);


                List<YhChineseAnwser5r> yhChineseAnwser52 = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser60 = new YhChineseAnwser5r();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。\n" +
                        "宜多与别人交谈、沟通，以积极进取的态度面对生活。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5r yhChineseAnwser61 = new YhChineseAnwser5r();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜多食益气健脾作用的食物，如粳米、小米、黄米、大麦、黄豆、白扁豆、豇豆、蚕豆、豌豆、土豆、白薯、红薯、山药、胡萝卜、香菇、鲫鱼、鹌鹑、鹅肉、羊心、羊肚、莲子、蘑菇、芡实、栗子、人参等。\n" +
                        "少吃具有耗气作用的食物，如槟榔、空心菜等。\n" +
                        "参考食疗药膳：黄芪童子鸡、山药莲子粥、人参桂圆母鸡汤、香菇豆腐皮排骨汤、灵芝红枣茶、牛奶仔汤、高枝杜梨汤（鸭脚茶）等。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5r yhChineseAnwser62 = new YhChineseAnwser5r();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("气虚质者卫阳不足，易于感受外邪，应注意保暖，不要劳汗当风，防止外邪侵袭。\n" +
                        "避免劳累：劳则气耗，气虚质者尤当注意不可过于劳作，以免更伤正气。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5r yhChineseAnwser63 = new YhChineseAnwser5r();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("应避免剧烈的体育活动，太极拳和八段锦比较适合这类群体。\n" +
                        "推荐：呼气提肛法。明代“养生十六宜”指出“谷道宜常撮”，谷道指肛门。首先吸气收腹，收缩并提升肛门，停顿2～3秒之后，再缓慢放松呼气，如此反复10～15次。八段锦的“两手攀足固肾腰”和“攒拳怒目增力气”加做1～3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5r yhChineseAnwser64 = new YhChineseAnwser5r();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：足三里、关元、气海、神阙；\n" +
                        "定位：关元穴位于前正中线上，脐下3寸；气海穴位于前正中线上，脐下1.5寸；神阙穴位于脐窝中央。\n" +
                        "操作：艾灸法\n" +
                        "平躺，借助温灸盒，对每个穴位进行温灸，每个穴位时间10分钟，隔日一次，10天为1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if (anwser3s.get(i).getName().equals("阳虚质")) {
                YhChineseAnwser4r yhChineseAnwser4 = new YhChineseAnwser4r();
                yhChineseAnwser4.setName("阳虚质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if (yhChineseAnwser.getcIstype() == 1) {
                    yhChineseAnwser4.setState("是");
                } else if (yhChineseAnwser.getcIstype() == 2) {
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5r> yhChineseAnwser5s = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser50 = new YhChineseAnwser5r();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("阳气不足，以畏寒怕冷、手足不温等虚寒表现为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5r yhChineseAnwser51 = new YhChineseAnwser5r();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("肌肉松软不实。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5r yhChineseAnwser53 = new YhChineseAnwser5r();
                yhChineseAnwser53.setTitle("常见表现");
                yhChineseAnwser53.setTxt("平素畏冷，手足不温，喜热饮食，精神不振，舌淡胖嫩，脉沉迟。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5r yhChineseAnwser54 = new YhChineseAnwser5r();
                yhChineseAnwser54.setTitle("心理特征");
                yhChineseAnwser54.setTxt("性格多沉静、内向。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5r yhChineseAnwser55 = new YhChineseAnwser5r();
                yhChineseAnwser55.setTitle("发病倾向");
                yhChineseAnwser55.setTxt("易患痰饮、肿胀、泄泻等病；感邪易从寒化。");
                yhChineseAnwser5s.add(yhChineseAnwser55);

                YhChineseAnwser5r yhChineseAnwser56 = new YhChineseAnwser5r();
                yhChineseAnwser56.setTitle("对外界环境适应能力");
                yhChineseAnwser56.setTxt("耐夏不耐冬；易感风、寒、湿邪。");
                yhChineseAnwser5s.add(yhChineseAnwser56);


                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5r> yhChineseAnwser52 = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser60 = new YhChineseAnwser5r();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。\n" +
                        "宜加强精神调养，善于调节自己的情感，去忧悲、防惊恐、和喜怒、消除不良情绪的影响。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5r yhChineseAnwser61 = new YhChineseAnwser5r();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜多吃甘温益气的食物，比如牛羊狗肉、葱、姜、蒜、花椒、鳝鱼、韭菜、辣椒、胡椒等。\n" +
                        "少食生冷寒凉食物，如黄瓜、藕、梨、西瓜等。\n" +
                        "参考食疗药膳：当归生姜羊肉汤、小米芝麻粥、银耳百合汤、黄精石斛老鸭汤、石橄榄汤等。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5r yhChineseAnwser62 = new YhChineseAnwser5r();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("阳虚质者耐春夏不耐秋冬，秋冬季节要适当暖衣温食以养护阳气，尤其要注意腰部和下肢保暖，每天以热水泡脚为宜。夏季暑热多汗，也易导致阳气外泄，使阳气虚于内。\n" +
                        "建议尽量避免强力劳作和大汗，也不可恣意贪凉饮冷。\n" +
                        "多在阳光充足的情况下适当进行户外活动，不可在阴暗潮湿寒冷的环境下长期工作和生活。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5r yhChineseAnwser63 = new YhChineseAnwser5r();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("在运动中应注意避风寒，不宜大汗，适合做一些温和的有氧运动如慢走、太极剑、太极拳等。\n" +
                        "八段锦的“背后七颠百病消”和“两手攀足固肾腰”加做1～3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5r yhChineseAnwser64 = new YhChineseAnwser5r();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：足三里、命门、肾俞。\n" +
                        "定位：命门穴位于后正中线上，第2腰椎棘突下凹陷中；肾俞穴位于第2腰椎棘突下，旁开1.5寸。\n" +
                        "操作：艾灸法\n" +
                        "俯卧，借助温灸盒，对穴位进行温灸，时间10～15分钟，隔日一次，10天为1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);

                YhChineseAnwser5r yhChineseAnwser65 = new YhChineseAnwser5r();
                yhChineseAnwser65.setTitle("其它");
                yhChineseAnwser65.setTxt("推拿保健：采用摩擦腰肾法\n" +
                        "以两手平掌的鱼际、掌根或两手虚拳的拳眼，拳背着力，同时做上下左右摩擦两侧腰骶部。每次15分钟，每天2次，10天1疗程。\n" +
                        "做坐式八段锦的“闭气搓手热，背后摩精门，左右辘轳转，两脚放舒伸。翻掌向上托，弯腰攀足频”。");
                yhChineseAnwser52.add(yhChineseAnwser65);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if (anwser3s.get(i).getName().equals("阴虚质")) {
                YhChineseAnwser4r yhChineseAnwser4 = new YhChineseAnwser4r();
                yhChineseAnwser4.setName("阴虚质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if (yhChineseAnwser.getdIstype() == 1) {
                    yhChineseAnwser4.setState("是");
                } else if (yhChineseAnwser.getdIstype() == 2) {
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5r> yhChineseAnwser5s = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser50 = new YhChineseAnwser5r();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("阴液亏少，以口燥咽干、手足心热等虚热表现为主要特征。 ");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5r yhChineseAnwser51 = new YhChineseAnwser5r();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("体形偏瘦。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5r yhChineseAnwser59 = new YhChineseAnwser5r();
                yhChineseAnwser59.setTitle("常见表现");
                yhChineseAnwser59.setTxt("手足心热，口燥咽干，鼻微干，喜冷饮，大便干燥，舌红少津，脉细数。");
                yhChineseAnwser5s.add(yhChineseAnwser59);

                YhChineseAnwser5r yhChineseAnwser53 = new YhChineseAnwser5r();
                yhChineseAnwser53.setTitle("心理特征");
                yhChineseAnwser53.setTxt("性情急躁，外向好动，活泼。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5r yhChineseAnwser54 = new YhChineseAnwser5r();
                yhChineseAnwser54.setTitle("发病倾向");
                yhChineseAnwser54.setTxt("易患虚劳、失精、不寐等病；感邪易从热化。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5r yhChineseAnwser55 = new YhChineseAnwser5r();
                yhChineseAnwser55.setTitle("对外界环境适应能力");
                yhChineseAnwser55.setTxt("耐冬不耐夏；不耐受暑、热、燥邪。");
                yhChineseAnwser5s.add(yhChineseAnwser55);


                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5r> yhChineseAnwser52 = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser60 = new YhChineseAnwser5r();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。\n" +
                        "宜应遵循《黄帝内经》中“恬淡虚无”、“精神内守”之养神大法，少与人争，以减少激怒。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5r yhChineseAnwser61 = new YhChineseAnwser5r();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜吃甘凉滋润的食物，比如黑大豆、黑芝麻、蚌肉、兔肉、鸭肉、百合、豆腐、豆浆、猪头、猪髓、燕窝、银耳、木耳、甲鱼、牡蛎肉、鱼翅、干贝、麻油、番茄、葡萄、柑橘、荸荠、香蕉、梨、苹果、桑葚、柿子、甘蔗等。\n" +
                        "少吃羊肉、狗肉、辣椒、葱、蒜等性温燥烈之品。\n" +
                        "参考食疗药膳：莲子百合煲瘦肉、韭菜炒鸡蛋、板栗核桃粥、乌根汤。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5r yhChineseAnwser62 = new YhChineseAnwser5r();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("熬夜、剧烈运动、高温酷暑的工作生活环境等能加重阴虚倾向，应尽量避免。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5r yhChineseAnwser63 = new YhChineseAnwser5r();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("对于阴虚体质的老年人应保证每天半小时～1小时的有氧运动，如慢走、游泳、太极拳等。\n" +
                        "可做搅海、漱津，即齿常叩 ,津常咽。\n" +
                        "八段锦的“五劳七伤往后瞧”和“两手攀足固肾腰”加做1～3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5r yhChineseAnwser64 = new YhChineseAnwser5r();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：三阴交、太溪；\n" +
                        "定位：三阴交穴位于内踝尖上三寸，胫骨后缘；太溪穴位于足内侧，内踝后方，内踝尖与跟腱之间的凹陷处。\n" +
                        "操作方法：用大拇指或中指按压三阴交和太溪穴，两侧穴位同时操作。每次按压操作5～10分钟。每日2次，10天1个疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if (anwser3s.get(i).getName().equals("痰湿质")) {
                YhChineseAnwser4r yhChineseAnwser4 = new YhChineseAnwser4r();
                yhChineseAnwser4.setName("痰湿质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if (yhChineseAnwser.geteIstype() == 1) {
                    yhChineseAnwser4.setState("是");
                } else if (yhChineseAnwser.geteIstype() == 2) {
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5r> yhChineseAnwser5s = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser50 = new YhChineseAnwser5r();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("痰湿凝聚，以形体肥胖、腹部肥满、口黏苔腻等痰湿表现为主要特征。 ");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5r yhChineseAnwser51 = new YhChineseAnwser5r();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("体形肥胖，腹部肥满松软。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5r yhChineseAnwser53 = new YhChineseAnwser5r();
                yhChineseAnwser53.setTitle("常见表现");
                yhChineseAnwser53.setTxt("面部皮肤油脂较多，多汗且黏，胸闷，痰多，口黏腻或甜，喜食肥甘甜黏，苔腻，脉滑。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5r yhChineseAnwser54 = new YhChineseAnwser5r();
                yhChineseAnwser54.setTitle("心理特征");
                yhChineseAnwser54.setTxt("性格偏温和、稳重，多善于忍耐。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5r yhChineseAnwser55 = new YhChineseAnwser5r();
                yhChineseAnwser55.setTitle("发病倾向");
                yhChineseAnwser55.setTxt("易患消渴、中风、胸痹等病。");
                yhChineseAnwser5s.add(yhChineseAnwser55);

                YhChineseAnwser5r yhChineseAnwser56 = new YhChineseAnwser5r();
                yhChineseAnwser56.setTitle("对外界环境适应能力");
                yhChineseAnwser56.setTxt("对梅雨季节及湿重环境适应能力差。");
                yhChineseAnwser5s.add(yhChineseAnwser56);


                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5r> yhChineseAnwser52 = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser60 = new YhChineseAnwser5r();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。\n" +
                        "宜欣赏激进、振奋的音乐，如二胡《赛马》等。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5r yhChineseAnwser61 = new YhChineseAnwser5r();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜选用健脾助运、祛湿化痰的食物，如冬瓜、白萝卜、薏苡仁、赤小豆、荷叶、山楂、生姜、荠菜、紫菜、海带、鲫鱼、鲤鱼、鲈鱼、文蛤等。\n" +
                        "少食肥、甜、油、黏（腻）的食物。\n" +
                        "参考食疗药膳：荷叶粥、冬瓜海带薏米排骨汤、薏米燕麦粥、香菇竹笋汤、紫菜白萝卜汤、鲫鱼豆腐汤、绞股蓝玉米须茶、盐肤木根汤（蒲芦盐根汤）。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5r yhChineseAnwser62 = new YhChineseAnwser5r();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("居住环境宜干燥，不宜潮湿，穿衣面料以棉、麻、丝等透气散湿的天然纤维为佳，尽量保持宽松，有利于汗液蒸发，祛除体内湿气。 \n" +
                        "晚上睡觉枕头不宜过高，防止打鼾加重；早睡早起，不要过于安逸，勿贪恋沙发和床榻。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5r yhChineseAnwser63 = new YhChineseAnwser5r();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("坚持长期运动锻炼，强度应根据自身的状况循序渐进。不宜在阴雨季节、天气湿冷的气候条件下运动。 \n" +
                        "可选择快走、武术以及打羽毛球等，使松弛的肌肉逐渐变得结实、致密。如果体重过重、膝盖受损，可选择游泳。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5r yhChineseAnwser64 = new YhChineseAnwser5r();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：丰隆、足三里。\n" +
                        "定位：足三里（位置见气虚质）。丰隆位于小腿前外侧，当外踝尖上8寸，条口外，距胫骨前缘二横指处（见图6）。\n" +
                        "操作：采用指揉法，用大拇指或中指按压，足三里穴可以两侧穴位同时操作。每次按压操作5～10分钟，每日两次，10天1个疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if (anwser3s.get(i).getName().equals("湿热质")) {
                YhChineseAnwser4r yhChineseAnwser4 = new YhChineseAnwser4r();
                yhChineseAnwser4.setName("湿热质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if (yhChineseAnwser.getfIstype() == 1) {
                    yhChineseAnwser4.setState("是");
                } else if (yhChineseAnwser.getfIstype() == 2) {
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5r> yhChineseAnwser5s = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser50 = new YhChineseAnwser5r();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("湿热内蕴，以面垢油光、口苦、苔黄腻等湿热表现为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5r yhChineseAnwser51 = new YhChineseAnwser5r();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("形体中等或偏瘦。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5r yhChineseAnwser53 = new YhChineseAnwser5r();
                yhChineseAnwser53.setTitle("常见表现");
                yhChineseAnwser53.setTxt("面垢油光，易生痤疮，口苦口干，身重困倦，大便黏滞不畅或燥结，小便短黄，男性易阴囊潮湿， 女性易带下增多， 舌质偏红，苔黄腻，脉滑数。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5r yhChineseAnwser54 = new YhChineseAnwser5r();
                yhChineseAnwser54.setTitle("心理特征");
                yhChineseAnwser54.setTxt("容易心烦急躁。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5r yhChineseAnwser55 = new YhChineseAnwser5r();
                yhChineseAnwser55.setTitle("发病倾向");
                yhChineseAnwser55.setTxt("易患疮疖、黄疸、热淋等病。");
                yhChineseAnwser5s.add(yhChineseAnwser55);

                YhChineseAnwser5r yhChineseAnwser56 = new YhChineseAnwser5r();
                yhChineseAnwser56.setTitle("对外界环境适应能力");
                yhChineseAnwser56.setTxt("对夏末秋初湿热气候，湿重或气温偏高环境较难适应。");
                yhChineseAnwser5s.add(yhChineseAnwser56);
                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5r> yhChineseAnwser52 = new ArrayList<>();


                YhChineseAnwser5r yhChineseAnwser60 = new YhChineseAnwser5r();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。\n" +
                        "宜注意精神调节，保持心情开朗舒畅。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5r yhChineseAnwser61 = new YhChineseAnwser5r();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("提倡饮食清淡，多吃甘寒、甘平、清利湿热的食物，如薏苡仁、莲子、茯苓、红小豆、绿豆、冬瓜、丝瓜、葫芦、苦瓜、黄瓜、西瓜、白菜、芹菜、卷心菜、莲藕、空心菜、苋菜等。\n" +
                        "少吃胡桃仁、鹅肉、羊肉、狗肉、鳝鱼、香菜、辣椒、花椒、酒、饴糖、胡椒、蜂蜜等甘酸滋腻之品及火锅、烹炸、烧烤等辛温助热食品。\n" +
                        "参考食疗药膳：薏米绿豆粥、绿豆玉米粥、冬瓜排骨汤、海带排骨汤、莲藕排骨汤等。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5r yhChineseAnwser62 = new YhChineseAnwser5r();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("不宜熬夜，或过度疲劳。\n" +
                        "要保持二便通畅，防止湿热郁聚。\n" +
                        "注意个人卫生，预防皮肤病变。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5r yhChineseAnwser63 = new YhChineseAnwser5r();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("对于湿热体质的老年人每天应有规律地有氧运动，如游泳、爬山、慢走、太极拳、八段锦等。\n" +
                        "八段锦的“摇头摆尾去心火”和“调理脾胃须单举”加做1～3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5r yhChineseAnwser64 = new YhChineseAnwser5r();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：阴陵泉、阳陵泉。\n" +
                        "定位：阴陵泉穴位于胫骨内侧踝下方凹陷处；阳陵泉穴位于小腿外侧，当腓骨小头前下方凹陷处。\n" +
                        "操作方法：用大拇指或中指按压阴陵泉穴和阳陵泉穴，两侧穴位同时操作。每次按压操作5～10分钟。每日2次，10天1个疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if (anwser3s.get(i).getName().equals("血瘀质")) {
                YhChineseAnwser4r yhChineseAnwser4 = new YhChineseAnwser4r();
                yhChineseAnwser4.setName("血瘀质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if (yhChineseAnwser.getgIstype() == 1) {
                    yhChineseAnwser4.setState("是");
                } else if (yhChineseAnwser.getgIstype() == 2) {
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5r> yhChineseAnwser5s = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser50 = new YhChineseAnwser5r();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("血行不畅，以肤色晦黯、舌质紫黯等血瘀表现为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5r yhChineseAnwser51 = new YhChineseAnwser5r();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("胖瘦均见。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5r yhChineseAnwser59 = new YhChineseAnwser5r();
                yhChineseAnwser59.setTitle("常见表现");
                yhChineseAnwser59.setTxt("肤色晦黯，色素沉着，容易出现瘀斑，口唇黯淡，舌黯或有瘀点，舌下络脉紫黯或增粗，脉涩。");
                yhChineseAnwser5s.add(yhChineseAnwser59);

                YhChineseAnwser5r yhChineseAnwser53 = new YhChineseAnwser5r();
                yhChineseAnwser53.setTitle("心理特征");
                yhChineseAnwser53.setTxt("易烦，健忘。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5r yhChineseAnwser54 = new YhChineseAnwser5r();
                yhChineseAnwser54.setTitle("发病倾向");
                yhChineseAnwser54.setTxt("易患症瘕及痛证、血证等。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5r yhChineseAnwser55 = new YhChineseAnwser5r();
                yhChineseAnwser55.setTitle("对外界环境适应能力");
                yhChineseAnwser55.setTxt("不耐受寒邪。");
                yhChineseAnwser5s.add(yhChineseAnwser55);


                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5r> yhChineseAnwser52 = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser60 = new YhChineseAnwser5r();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。精神调养上，要培养乐观的情绪。精神愉快则气血和畅，营卫流通，有利血瘀体质的改善。反之，苦闷、忧郁则可加重血瘀倾向。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5r yhChineseAnwser61 = new YhChineseAnwser5r();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜多吃具有活血化瘀的食物如黑豆、黄豆、香菇、茄子、油菜、羊血、芒果、木瓜、海藻、海带、紫菜、萝卜、胡萝卜、金橘、橙子、柚子、桃子、李子、山楂、醋、玫瑰花、绿茶、红糖、黄酒、葡萄糖、白酒等具有活血、散结、行气、疏肝解郁作用的食物。\n" +
                        "少吃肥猪肉等滋腻之品。应解除烟酒。\n" +
                        "参考食疗药膳：黑豆川芎粥、黑豆粥、田七鸡肉煲、醋黑豆、山楂红枣茶、黑木耳炒大白菜、洋葱炒羊肉、乌根汤。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5r yhChineseAnwser62 = new YhChineseAnwser5r();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("血得温则行，得寒则凝。血瘀质者要避免寒冷刺激。\n" +
                        "日常生活中应注意动静结合，不可贪图安逸，加重气血郁滞。气为血帅，故亦需注意情志舒畅，勿恼怒郁愤。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5r yhChineseAnwser63 = new YhChineseAnwser5r();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("每天应有规律的有氧运动，避免剧烈以及过量的体育运动。可采用“步行健身法”，通过步行运动，促进全身血液的运行，有活血化瘀的功效。\n" +
                        "八段锦的“左右开弓似射雕”和“双手托天理三焦”加做1—3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5r yhChineseAnwser64 = new YhChineseAnwser5r();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：血海、内关\n" +
                        "定位：屈膝，在髌骨内上缘上2寸，当股四头肌内侧头的隆起处。内关穴位于腕横纹上2寸，掌长肌腱与桡侧曲肌腱之间。\n" +
                        "操作方法：用大拇指或中指按压血海穴及内关穴，两侧穴位同时操作。每次按压操作5—10分钟。每日2次，10天1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if (anwser3s.get(i).getName().equals("气郁质")) {
                YhChineseAnwser4r yhChineseAnwser4 = new YhChineseAnwser4r();
                yhChineseAnwser4.setName("气郁质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if (yhChineseAnwser.gethIstype() == 1) {
                    yhChineseAnwser4.setState("是");
                } else if (yhChineseAnwser.gethIstype() == 2) {
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5r> yhChineseAnwser5s = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser50 = new YhChineseAnwser5r();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("气机郁滞，以神情抑郁、忧虑脆弱等气郁表现为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5r yhChineseAnwser51 = new YhChineseAnwser5r();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("形体瘦者为多。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5r yhChineseAnwser53 = new YhChineseAnwser5r();
                yhChineseAnwser53.setTitle("常见表现");
                yhChineseAnwser53.setTxt("神情抑郁，情感脆弱，烦闷不乐，舌淡红，苔薄白，脉弦。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5r yhChineseAnwser54 = new YhChineseAnwser5r();
                yhChineseAnwser54.setTitle("心理特征");
                yhChineseAnwser54.setTxt("性格内向不稳定、敏感多虑。 ");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5r yhChineseAnwser55 = new YhChineseAnwser5r();
                yhChineseAnwser55.setTitle("发病倾向");
                yhChineseAnwser55.setTxt("易患脏躁、梅核气、百合病及郁证等。");
                yhChineseAnwser5s.add(yhChineseAnwser55);

                YhChineseAnwser5r yhChineseAnwser56 = new YhChineseAnwser5r();
                yhChineseAnwser56.setTitle("对外界环境适应能力");
                yhChineseAnwser56.setTxt("对精神刺激适应能力较差；不适应阴雨天气。");
                yhChineseAnwser5s.add(yhChineseAnwser56);
                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5r> yhChineseAnwser52 = new ArrayList<>();


                YhChineseAnwser5r yhChineseAnwser60 = new YhChineseAnwser5r();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜主动寻求快乐，多参加社会活动、集体文娱活动，常看喜剧、滑稽剧、听相声，以及富有鼓励、激励意义的电影、电视，勿看悲剧、苦剧。\n" +
                        "多听轻快、开朗、激动的音乐，多读积极的、鼓励的、富有乐趣的、展现美好生活前景的书籍，以培养开朗、豁达的意识，在名利上不计较得失，知足常乐。\"");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5r yhChineseAnwser61 = new YhChineseAnwser5r();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("\"宜多吃小麦、高粱、蒿子秆、香菜、葱、蒜、萝卜、洋葱、苦瓜、黄花菜、海带、海藻、橘子、柚子、槟榔、玫瑰花、梅花等行气、解郁、消食、醒神之品。\n" +
                        "睡前避免饮茶、咖啡等提神醒脑的饮料。\n" +
                        "参考食疗药膳：黄花菜香菇排骨汤、三花茶、糖醋卷心菜、金针菜汤。\n" +
                        "菊花玫瑰茶：杭白菊4朵，玫瑰花2朵，90℃水沏，可以经常服用。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5r yhChineseAnwser62 = new YhChineseAnwser5r();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("气郁日久易致血行不畅，衣着方面宜选择宽松透气性好的款式，还应注意鞋袜也不宜约束过紧，否则易影响气血运行，出现肢体麻木或发凉等症状。\n" +
                        "居室环境宽敞明亮，温度、湿度适宜。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5r yhChineseAnwser63 = new YhChineseAnwser5r();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("气郁体质的老年人每天有半小时～1小时的有氧运动。\n" +
                        "可选择下棋、打牌、瑜珈等体娱游戏，以闲情怡志，促进人际交流。\n" +
                        "八段锦的“左右开弓似射雕”和“双手托天理三焦”加做1～3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5r yhChineseAnwser64 = new YhChineseAnwser5r();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：太冲、膻中。\n" +
                        "定位：太冲穴位于足背，第1、2跖骨结合部之前凹陷中；膻中穴位于胸部,当前正中线上,平第四肋间,两乳头连线的中点。\n" +
                        "操作方法：用大拇指或中指按压太冲穴和膻中穴，太冲穴两侧穴位同时操作。每次按压操作5～10分钟。每日2次，10天1个疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);

                YhChineseAnwser5r yhChineseAnwser65 = new YhChineseAnwser5r();
                yhChineseAnwser65.setTitle("其它");
                yhChineseAnwser65.setTxt("经络按摩：选取足厥阴肝经的循行路线，进行经络敲打，每次敲打1个来回，每日2次，10天1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser65);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if (anwser3s.get(i).getName().equals("特禀质")) {
                YhChineseAnwser4r yhChineseAnwser4 = new YhChineseAnwser4r();
                yhChineseAnwser4.setName("特禀质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if (yhChineseAnwser.getiIstype() == 1) {
                    yhChineseAnwser4.setState("是");
                } else if (yhChineseAnwser.getiIstype() == 2) {
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5r> yhChineseAnwser5s = new ArrayList<>();
                YhChineseAnwser5r yhChineseAnwser50 = new YhChineseAnwser5r();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("先天失常，以生理缺陷、过敏反应等为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5r yhChineseAnwser51 = new YhChineseAnwser5r();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("过敏体质者一般无特殊；先天禀赋异常者或有畸形，或有生理缺陷。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5r yhChineseAnwser53 = new YhChineseAnwser5r();
                yhChineseAnwser53.setTitle("常见表现");
                yhChineseAnwser53.setTxt("过敏体质者常见哮喘、风团、咽痒、鼻塞、喷嚏等；患遗传性疾病者有垂直遗传、先天性、 家族性特征；患胎传性疾病者具有母体影响胎儿个体生长发育及相关疾病特征。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5r yhChineseAnwser54 = new YhChineseAnwser5r();
                yhChineseAnwser54.setTitle("心理特征");
                yhChineseAnwser54.setTxt("随禀质不同情况各异。 ");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5r yhChineseAnwser55 = new YhChineseAnwser5r();
                yhChineseAnwser55.setTitle("发病倾向");
                yhChineseAnwser55.setTxt("过敏体质者易患哮喘、荨麻疹、花粉症及药物过敏等；遗传性疾病如血友病、先天愚型等；胎传性疾病如五迟（立迟、行迟、发迟、齿迟和语迟）、五软（头软、项软、手足软、肌肉软、口软）、解颅、 胎惊等。");
                yhChineseAnwser5s.add(yhChineseAnwser55);

                YhChineseAnwser5r yhChineseAnwser56 = new YhChineseAnwser5r();
                yhChineseAnwser56.setTitle("对外界环境适应能力");
                yhChineseAnwser56.setTxt("适应能力差，如过敏体质者对易致过敏季节适应能力差，易引发宿疾。");
                yhChineseAnwser5s.add(yhChineseAnwser56);
                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5r> yhChineseAnwser52 = new ArrayList<>();


                YhChineseAnwser5r yhChineseAnwser60 = new YhChineseAnwser5r();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5r yhChineseAnwser61 = new YhChineseAnwser5r();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("饮食宜清淡、均衡、粗细搭配适当、荤素配伍合理。\n" +
                        "少吃荞麦、蚕豆、白扁豆、牛肉、鹅肉、鲤鱼、虾、蟹、茄子、酒、辣椒、浓茶、咖啡等辛辣之品、腥发及含致敏物质的食品。\n" +
                        "参考食疗药膳：黄芪山药粥、黑米花生粥、灵芝红枣茶。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5r yhChineseAnwser62 = new YhChineseAnwser5r();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("避免过敏原的刺激，生活环境中接触的物品如枕头、棉被、床垫、地毯、窗帘、衣橱易附有尘螨，可引起过敏，应常清洗、日晒。\n" +
                        "外出也要避免处在花粉及粉刷油漆的空气中，以免刺激而诱发过敏病症。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5r yhChineseAnwser63 = new YhChineseAnwser5r();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("特禀体质的老年人每天有半小时～1小时的有氧运动。\n" +
                        "注意避风寒。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5r yhChineseAnwser64 = new YhChineseAnwser5r();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：足三里、关元、神阙、肾俞。\n" +
                        "定位：足三里位于外膝眼下三寸，胫骨前嵴外1横指处；关元穴位于前正中线上，脐下3寸；神阙穴位于脐窝中央；肾俞穴位于第2腰椎棘突下，旁开1.5寸。\n" +
                        "操作：点按法\n" +
                        "用大拇指或中指按压足三里穴，两侧穴位同时操作，每次按压操作5～10分钟，每日两次，10天1个疗程。\n" +
                        "操作：艾灸法\n" +
                        "对足三里穴、关元穴、神阙穴、肾俞穴进行温灸，可以借助温灸器，每次时间10～15分钟即可，隔日一次，10天为1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);

                YhChineseAnwser5r yhChineseAnwser65 = new YhChineseAnwser5r();
                yhChineseAnwser65.setTitle("其它");
                yhChineseAnwser65.setTxt("经络按摩：选取足少阴肾经的循行路线，进行经络敲打，每次敲打1个来回，每日2次，10天1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser65);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }
        }
        yhChineseAnwser2.setTabData(yhChineseAnwser4s);
        yhChineseAnwser.setTime(yhChineseAnwser.getTime().substring(0, 10));
        System.out.println(yhChineseAnwser2);
        model.addAttribute("yhChineseAnwser2", yhChineseAnwser2);
        model.addAttribute("getrepotimg", getrepotimg);
        model.addAttribute("totalReport", yhChineseAnwser);

        return "personalUser/healthRecord/zhongyi";

    }

    /**
     * 中医体质辨识
     */

    @ApiOperation(value = "中医体质辨识")
    @RequestMapping(value = "medicine/identification", method = RequestMethod.GET)
    public String identification(Model model) {
        return "medicine/identification";
    }


    @ApiOperation(value = "个人基本信息")
    @RequestMapping(value = "newpage/personalBasicInformation", method = RequestMethod.GET)
    public String personalBasicInformation(Model model) {
        return "newpage/1/personalBasicInformation";
    }

    @ApiOperation(value = "个人基本信息表单")
    @RequestMapping(value = "newpage/personalBasicInformationForm", method = RequestMethod.GET)
    public String personalBasicInformationForm(Model model) {
        return "newpage/form/personalBasicInformationForm";
    }

    @ApiOperation(value = "个人基本信息表单")
    @RequestMapping(value = "newpage/personalBasicInformationFormG", method = RequestMethod.GET)
    public String personalBasicInformationFormG(Model model) {
        return "newpage/form/personalBasicInformationFormG";
    }

    @ApiOperation(value = "individualOccupationalHealthMonitoringRecordsWorkers")
    @RequestMapping(value = "newpage/individualOccupationalHealthMonitoringRecordsWorkers", method = RequestMethod.GET)
    public String individualOccupationalHealthMonitoringRecordsWorkers(Model model) {
        return "newpage/form/individualOccupationalHealthMonitoringRecordsWorkers";
    }

    @ApiOperation(value = "个人基本信息表单")
    @RequestMapping(value = "newpage/personalBasicInformationFormF", method = RequestMethod.GET)
    public String personalBasicInformationFormF(Model model) {
        return "newpage/form/personalBasicInformationFormF";
    }

    @RequestMapping(value = "newpage/annualOccupationalHealthPromotionTrainingListForm", method = RequestMethod.GET)
    public String annualOccupationalHealthPromotionTrainingListForm(Model model) {
        return "newpage/form/annualOccupationalHealthPromotionTrainingListForm";
    }

    @RequestMapping(value = "newpage/unitOccupationalHealthSurveillanceManagementArchivesForm", method = RequestMethod.GET)
    public String unitOccupationalHealthSurveillanceManagementArchivesForm(Model model) {
        return "newpage/form/unitOccupationalHealthSurveillanceManagementArchivesForm";
    }


    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/duringEmployment", method = RequestMethod.GET)
    public String duringEmployment(Model model) {
        return "newpage/2/duringEmployment";
    }


    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/annualOccupationalHealthPromotionTrainingList", method = RequestMethod.GET)
    public String annualOccupationalHealthPromotionTrainingList(Model model) {
        return "newpage/3/annualOccupationalHealthPromotionTrainingList";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/1", method = RequestMethod.GET)
    public String newpage1(Model model) {
        return "newpage/worker/1";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/1html", method = RequestMethod.GET)
    public String newpage1html(Model model,Integer personalBasicInformation) {
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard=new NewEmployeePersonalInformationCard();
        newEmployeePersonalInformationCard.setId(personalBasicInformation);
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard1=newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard);
        model.addAttribute("data",Result.ok(newEmployeePersonalInformationCard1));
        model.addAttribute("medicalHistory",JSONArray.fromObject(newEmployeePersonalInformationCard1.getOccupationalHistoryExposureHistoryToOccupationalHazards()));
        model.addAttribute("surgicalHistory",JSONArray.fromObject(newEmployeePersonalInformationCard1.getTextJson()));
        return "newpage/worker/1html";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/2html", method = RequestMethod.GET)
    public String newpage2html(Model model,Integer personalBasicInformation) {
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard=new NewEmployeePersonalInformationCard();
        newEmployeePersonalInformationCard.setId(personalBasicInformation);
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard1=newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard);
        model.addAttribute("data",Result.ok(newEmployeePersonalInformationCard1));
        model.addAttribute("medicalHistory",JSONArray.fromObject(newEmployeePersonalInformationCard1.getTestingResultsOccupationalHazardsInWorkplace()));
        return "newpage/worker/2html";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/3html", method = RequestMethod.GET)
    public String newpage3html(Model model,Integer personalBasicInformation) {
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard=new NewEmployeePersonalInformationCard();
        newEmployeePersonalInformationCard.setId(personalBasicInformation);
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard1=newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard);
        model.addAttribute("data",Result.ok(newEmployeePersonalInformationCard1));
        model.addAttribute("medicalHistory",JSONArray.fromObject(newEmployeePersonalInformationCard1.getPreviousOccupationalHealthExaminationResults()));
        return "newpage/worker/3html";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/4html", method = RequestMethod.GET)
    public String newpage4html(Model model,Integer personalBasicInformation) {
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard=new NewEmployeePersonalInformationCard();
        newEmployeePersonalInformationCard.setId(personalBasicInformation);
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard1=newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard);
        model.addAttribute("data",Result.ok(newEmployeePersonalInformationCard1));
        model.addAttribute("noticeOccupationalHealthExaminationResults",JSONObject.parseObject(newEmployeePersonalInformationCard1.getNoticeOccupationalHealthExaminationResults()));
        return "newpage/worker/4html";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/5html", method = RequestMethod.GET)
    public String newpage5html(Model model,Integer personalBasicInformation) {
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard=new NewEmployeePersonalInformationCard();
        newEmployeePersonalInformationCard.setId(personalBasicInformation);
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard1=newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard);
        model.addAttribute("data",Result.ok(newEmployeePersonalInformationCard1));
        model.addAttribute("occupationalHealthExamination1",JSONArray.fromObject(newEmployeePersonalInformationCard1.getOccupationalHealthExamination1()));
        model.addAttribute("occupationalHealthExamination2",JSONArray.fromObject(newEmployeePersonalInformationCard1.getOccupationalHealthExamination2()));
        model.addAttribute("occupationalHealthExamination3",JSONArray.fromObject(newEmployeePersonalInformationCard1.getOccupationalHealthExamination3()));
        model.addAttribute("occupationalHealthExamination4",JSONArray.fromObject(newEmployeePersonalInformationCard1.getOccupationalHealthExamination4()));
        return "newpage/worker/5html";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/6html", method = RequestMethod.GET)
    public String newpage6html(Model model,Integer personalBasicInformation) {
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard=new NewEmployeePersonalInformationCard();
        newEmployeePersonalInformationCard.setId(personalBasicInformation);
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard1=newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard);

        model.addAttribute("medicalHistory",JSONArray.fromObject(newEmployeePersonalInformationCard1.getExaminationResults()));
        model.addAttribute("occupationalHealthExamination4",JSONArray.fromObject(newEmployeePersonalInformationCard1.getExaminationResultsFile()));
        return "newpage/worker/6html";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/7html", method = RequestMethod.GET)
    public String newpage7html(Model model,Integer personalBasicInformation) {
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard=new NewEmployeePersonalInformationCard();
        newEmployeePersonalInformationCard.setId(personalBasicInformation);
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard1=newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard);
        model.addAttribute("occupationalHealthExamination4",JSONArray.fromObject(newEmployeePersonalInformationCard1.getRegardingMaterials()));
        return "newpage/worker/7html";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/8html", method = RequestMethod.GET)
    public String newpage8html(Model model,Integer personalBasicInformation) {
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard=new NewEmployeePersonalInformationCard();
        newEmployeePersonalInformationCard.setId(personalBasicInformation);
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard1=newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard);
        model.addAttribute("occupationalHealthExamination4",JSONArray.fromObject(newEmployeePersonalInformationCard1.getNotes()));
        return "newpage/worker/8html";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/9html", method = RequestMethod.GET)
    public String newpage9html(Model model,Integer personalBasicInformation) {
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard=new NewEmployeePersonalInformationCard();
        newEmployeePersonalInformationCard.setId(personalBasicInformation);
        NewEmployeePersonalInformationCard newEmployeePersonalInformationCard1=newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard);
        model.addAttribute("occupationalHealthExamination4",JSONArray.fromObject(newEmployeePersonalInformationCard1.getOther()));
        return "newpage/worker/9html";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/2", method = RequestMethod.GET)
    public String newpage2(Model model) {
        return "newpage/worker/2";
    }
    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/3", method = RequestMethod.GET)
    public String newpage3(Model model) {
        return "newpage/worker/3";
    }
    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/4", method = RequestMethod.GET)
    public String newpage4(Model model) {
        return "newpage/worker/4";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/4check", method = RequestMethod.GET)
    public String newpage4html(Model model) {
        return "newpage/worker/4check";
    }
    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/5", method = RequestMethod.GET)
    public String newpage5(Model model) {
        return "newpage/worker/5";
    }
    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/6", method = RequestMethod.GET)
    public String newpage6(Model model) {
        return "newpage/worker/6";
    }
    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/7", method = RequestMethod.GET)
    public String newpage7(Model model) {
        return "newpage/worker/7";
    }
    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/8", method = RequestMethod.GET)
    public String newpage8(Model model) {
        return "newpage/worker/8";
    }
    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/9", method = RequestMethod.GET)
    public String newpage9(Model model) {
        return "newpage/worker/9";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/beforeEmployment", method = RequestMethod.GET)
    public String beforeEmployment(Model model) {
        return "newpage/2/beforeEmployment";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/beforeEmploymentAll", method = RequestMethod.GET)
    public String beforeEmploymentAll(Model model) {
        return "newpage/2/beforeEmploymentAll";
    }


    @ApiOperation(value = "个人健康评估")
    @RequestMapping(value = "newpage/assessment2", method = RequestMethod.GET)
    public String assessment2(String userCode, String type, Model model) throws ParseException {
        System.out.println("-----------------" + userCode);
        model.addAttribute("type", type);
        model.addAttribute("usercode", userCode);
        return "newpage/2/assessment2";
    }

    @ApiOperation(value = "心理评估管理")
    @RequestMapping(value = "newpage/psychological", method = RequestMethod.GET)
    public String psychological2(Model model) {
        return "newpage/2/psychological";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/afterLeavingPost", method = RequestMethod.GET)
    public String afterLeavingPost(Model model) {
        return "newpage/2/afterLeavingPost";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/constructionProjectOccupationalHealth", method = RequestMethod.GET)
    public String constructionProjectOccupationalHealth(Model model) {
        return "newpage/3/constructionProjectOccupationalHealth";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/monitoringOccupationalHazardFactors", method = RequestMethod.GET)
    public String monitoringOccupationalHazardFactors(Model model) {
        return "newpage/3/monitoringOccupationalHazardFactors";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/occupationalHealthPromotionTraining", method = RequestMethod.GET)
    public String occupationalHealthPromotionTraining(Model model) {
        return "newpage/3/occupationalHealthPromotionTraining";
    }

    @RequestMapping(value = "newpage/occupationalHealthManagementForm", method = RequestMethod.GET)
    public String occupationalHealthManagementForm(Model model) {
        return "newpage/form/occupationalHealthManagementForm";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "newpage/occupationalHealthManagement", method = RequestMethod.GET)
    public String occupationalHealthManagement(Model model) {
        return "newpage/3/occupationalHealthManagement";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/healthRiskAssessmentReport", method = RequestMethod.GET)
    public String healthRiskAssessmentReport(Model model) {
        return "newpage/4/healthRiskAssessmentReport";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/listChronicDiseasePatients", method = RequestMethod.GET)
    public String listChronicDiseasePatients(Model model) {
        return "newpage/4/listChronicDiseasePatients";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/interventionRecordsForHighriskPopulations", method = RequestMethod.GET)
    public String interventionRecordsForHighriskPopulations(Model model) {
        return "newpage/4/interventionRecordsForHighriskPopulations";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/recordPneumoconiosisService", method = RequestMethod.GET)
    public String recordPneumoconiosisService(Model model) {
        return "newpage/4/recordPneumoconiosisService";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/serviceRecordForObesePatients", method = RequestMethod.GET)
    public String serviceRecordForObesePatients(Model model) {
        return "newpage/4/serviceRecordForObesePatients";
    }
    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/serviceRecordForProstatitisPatients", method = RequestMethod.GET)
    public String serviceRecordForProstatitisPatients(Model model) {
        return "newpage/4/serviceRecordForProstatitisPatients";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/serviceRecordsForHypertensivePatients", method = RequestMethod.GET)
    public String serviceRecordsForHypertensivePatients(Model model) {
        return "newpage/4/serviceRecordsForHypertensivePatients";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/serviceRecordForCoronaryHeartDiseasePatients", method = RequestMethod.GET)
    public String serviceRecordForCoronaryHeartDiseasePatients(Model model) {
        return "newpage/4/serviceRecordForCoronaryHeartDiseasePatients";
    }


    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/serviceRecordForOsteoarthritisPatients", method = RequestMethod.GET)
    public String serviceRecordForOsteoarthritisPatients(Model model) {
        return "newpage/4/serviceRecordForOsteoarthritisPatients";
    }

    @RequestMapping(value = "/newpage/unitOccupationalHealthSurveillanceManagementArchives", method = RequestMethod.GET)
    public String unitOccupationalHealthSurveillanceManagementArchives(Model model) {
        return "newpage/3/unitOccupationalHealthSurveillanceManagementArchives";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/diabetesPatientServiceRecord", method = RequestMethod.GET)
    public String diabetesPatientServiceRecord(Model model) {
        return "newpage/4/diabetesPatientServiceRecord";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/goutPatientServiceRecord", method = RequestMethod.GET)
    public String goutPatientServiceRecord(Model model) {
        return "newpage/4/goutPatientServiceRecord";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/serviceRecordForPatientsWithAbnormalBloodLipids", method = RequestMethod.GET)
    public String serviceRecordForPatientsWithAbnormalBloodLipids(Model model) {
        return "newpage/4/serviceRecordForPatientsWithAbnormalBloodLipids";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/serviceRecordForStrokePatients", method = RequestMethod.GET)
    public String serviceRecordForStrokePatients(Model model) {
        return "newpage/4/serviceRecordForStrokePatients";
    }


    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/serviceRecordForOsteoporosisPatients", method = RequestMethod.GET)
    public String serviceRecordForOsteoporosisPatients(Model model) {
        return "newpage/4/serviceRecordForOsteoporosisPatients";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/skinDiseasePatientServiceRecord", method = RequestMethod.GET)
    public String skinDiseasePatientServiceRecord(Model model) {
        return "newpage/4/skinDiseasePatientServiceRecord";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/serviceRecordForBreastHyperplasiaPatients", method = RequestMethod.GET)
    public String serviceRecordForBreastHyperplasiaPatients(Model model) {
        return "newpage/4/serviceRecordForBreastHyperplasiaPatients";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/serviceRecordForChronicGastritisPatients", method = RequestMethod.GET)
    public String serviceRecordForChronicGastritisPatients(Model model) {
        return "newpage/4/serviceRecordForChronicGastritisPatients";
    }


    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/impactAssessment", method = RequestMethod.GET)
    public String impactAssessment(Model model) {
        return "newpage/4/impactAssessment";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/anxietyDisorderInterventionRecord", method = RequestMethod.GET)
    public String anxietyDisorderInterventionRecord(Model model) {
        return "newpage/4/anxietyDisorderInterventionRecord";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/otherPsychologicalDiseaseInterventionRecordForm", method = RequestMethod.GET)
    public String otherPsychologicalDiseaseInterventionRecordForm(Model model) {
        return "newpage/4/otherPsychologicalDiseaseInterventionRecordForm";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/insomniaInterventionRecord", method = RequestMethod.GET)
    public String insomniaInterventionRecord(Model model) {
        return "newpage/4/insomniaInterventionRecord";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/obsessiveCompulsiveDisorderInterventionRecord", method = RequestMethod.GET)
    public String obsessiveCompulsiveDisorderInterventionRecord(Model model) {
        return "newpage/4/obsessiveCompulsiveDisorderInterventionRecord";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/stressInterventionRecord", method = RequestMethod.GET)
    public String stressInterventionRecord(Model model) {
        return "newpage/4/stressInterventionRecord";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/depressionInterventionRecord", method = RequestMethod.GET)
    public String depressionInterventionRecord(Model model) {
        return "newpage/4/depressionInterventionRecord";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/psychologicalAssessmentReport", method = RequestMethod.GET)
    public String psychologicalAssessmentReport(Model model) {
        return "newpage/4/psychologicalAssessmentReport";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/employeePsychologicalCounselingRecord", method = RequestMethod.GET)
    public String employeePsychologicalCounselingRecord(Model model) {
        return "newpage/4/employeePsychologicalCounselingRecord";
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/listPsychologicalPatients", method = RequestMethod.GET)
    public String listPsychologicalPatients(Model model) {
        return "newpage/4/listPsychologicalPatients";
    }


    @ApiOperation(value = "")
    @RequestMapping(value = "/newpage/individualOccupationalHealthMonitoring", method = RequestMethod.GET)
    public String individualOccupationalHealthMonitoring(Model model) {
        return "newpage/3/individualOccupationalHealthMonitoring";
    }


    @RequestMapping(value = "/newpage/insomniaInterventionRecordForm", method = RequestMethod.GET)
    public String insomniaInterventionRecordForm(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        String roleName = "健管师";  // 默认角色为健管师

        if (Optional.ofNullable(yh).isPresent()) {
            roleName = yhUserRoleMapper.selectRoleName(yh.getUserCode());
            // 如果 roleName 包含 "心理" 字符串，则修改显示角色为 心理咨询师
            if (roleName != null && roleName.contains("心理")) {
                roleName = "心理咨询师";
            }
        } else {
            roleName = "管理员";
        }
        model.addAttribute("roleName", roleName);
        return "newpage/form/insomniaInterventionRecordForm";
    }


    @RequestMapping(value = "/newpage/stressInterventionRecordForm", method = RequestMethod.GET)
    public String stressInterventionRecordForm(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        String roleName = "健管师";  // 默认角色为健管师

        if (Optional.ofNullable(yh).isPresent()) {
            roleName = yhUserRoleMapper.selectRoleName(yh.getUserCode());
            // 如果 roleName 包含 "心理" 字符串，则修改显示角色为 心理咨询师
            if (roleName != null && roleName.contains("心理")) {
                roleName = "心理咨询师";
            }
        } else {
            roleName = "管理员";
        }
        model.addAttribute("roleName", roleName);
        return "newpage/form/stressInterventionRecordForm";
    }

    @RequestMapping(value = "/newpage/otherPsychologicalDisordersForm", method = RequestMethod.GET)
    public String otherPsychologicalDisordersForm(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        String roleName = "健管师";  // 默认角色为健管师

        if (Optional.ofNullable(yh).isPresent()) {
            roleName = yhUserRoleMapper.selectRoleName(yh.getUserCode());
            // 如果 roleName 包含 "心理" 字符串，则修改显示角色为 心理咨询师
            if (roleName != null && roleName.contains("心理")) {
                roleName = "心理咨询师";
            }
        } else {
            roleName = "管理员";
        }
        model.addAttribute("roleName", roleName);
        return "newpage/form/otherPsychologicalDisordersForm";
    }


    @RequestMapping(value = "/newpage/serviceRecordFormForOsteoarthritisPatients", method = RequestMethod.GET)
    public String serviceRecordFormForOsteoarthritisPatients(Model model) {
        return "newpage/form/serviceRecordFormForOsteoarthritisPatients";
    }

    @RequestMapping(value = "/newpage/prostatitisPatientServiceRecordForm", method = RequestMethod.GET)
    public String prostatitisPatientServiceRecordForm(Model model) {
        return "newpage/form/prostatitisPatientServiceRecordForm";
    }

    @RequestMapping(value = "/newpage/serviceRecordFormForBreastHyperplasiaPatients", method = RequestMethod.GET)
    public String serviceRecordFormForBreastHyperplasiaPatients(Model model) {
        return "newpage/form/serviceRecordFormForBreastHyperplasiaPatients";
    }

    @RequestMapping(value = "/newpage/serviceRecordFormForOsteoporosisPatients", method = RequestMethod.GET)
    public String serviceRecordFormForOsteoporosisPatients(Model model) {
        return "newpage/form/serviceRecordFormForOsteoporosisPatients";
    }

    @RequestMapping(value = "/newpage/occupationalHealthPromotionTrainingArchive", method = RequestMethod.GET)
    public String occupationalHealthPromotionTrainingArchive(Model model) {
        return "newpage/form/occupationalHealthPromotionTrainingArchive";
    }

    @RequestMapping(value = "/newpage/skinDiseasePatientServiceRecordForm", method = RequestMethod.GET)
    public String skinDiseasePatientServiceRecordForm(Model model) {
        return "newpage/form/skinDiseasePatientServiceRecordForm";
    }

    @RequestMapping(value = "/newpage/serviceRecordFormForChronicGastritisPatients", method = RequestMethod.GET)
    public String serviceRecordFormForChronicGastritisPatients(Model model) {
        return "newpage/form/serviceRecordFormForChronicGastritisPatients";
    }

    @RequestMapping(value = "/newpage/serviceRecordFormForPatientsWithAbnormalBloodLipids", method = RequestMethod.GET)
    public String serviceRecordFormForPatientsWithAbnormalBloodLipids(Model model) {
        return "newpage/form/serviceRecordFormForPatientsWithAbnormalBloodLipids";
    }

    @RequestMapping(value = "/newpage/serviceRecordForCoronaryHeartDiseasePatientsFor", method = RequestMethod.GET)
    public String serviceRecordForCoronaryHeartDiseasePatientsFor(Model model) {
        return "newpage/form/serviceRecordForCoronaryHeartDiseasePatientsFor";
    }

    @RequestMapping(value = "/newpage/serviceRecordFormForStrokePatients", method = RequestMethod.GET)
    public String serviceRecordFormForStrokePatients(Model model) {
        return "newpage/form/serviceRecordFormForStrokePatients";
    }




    @RequestMapping(value = "/newpage/type2DiabetesPatientServiceRecordForm", method = RequestMethod.GET)
    public String type2DiabetesPatientServiceRecordForm(Model model) {
        return "newpage/form/type2DiabetesPatientServiceRecordForm";
    }

    @RequestMapping(value = "/newpage/goutPatientServiceRecordForm", method = RequestMethod.GET)
    public String goutPatientServiceRecordForm(Model model) {
        return "newpage/form/goutPatientServiceRecordForm";
    }

    @RequestMapping(value = "/newpage/anxietyDisorderInterventionRecordForm", method = RequestMethod.GET)
    public String anxietyDisorderInterventionRecordForm(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        String roleName = "健管师";  // 默认角色为健管师

        if (Optional.ofNullable(yh).isPresent()) {
            roleName = yhUserRoleMapper.selectRoleName(yh.getUserCode());
            // 如果 roleName 包含 "心理" 字符串，则修改显示角色为 心理咨询师
            if (roleName != null && roleName.contains("心理")) {
                roleName = "心理咨询师";
            }
        } else {
            roleName = "管理员";
        }
        model.addAttribute("roleName", roleName);
        return "newpage/form/anxietyDisorderInterventionRecordForm";
    }

    @RequestMapping(value = "/newpage/obsessiveCompulsiveDisorderInterventionRecordForm", method = RequestMethod.GET)
    public String obsessiveCompulsiveDisorderInterventionRecordForm(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        String roleName = "健管师";  // 默认角色为健管师

        if (Optional.ofNullable(yh).isPresent()) {
            roleName = yhUserRoleMapper.selectRoleName(yh.getUserCode());
            // 如果 roleName 包含 "心理" 字符串，则修改显示角色为 心理咨询师
            if (roleName != null && roleName.contains("心理")) {
                roleName = "心理咨询师";
            }
        } else {
            roleName = "管理员";
        }
        model.addAttribute("roleName", roleName);
        return "newpage/form/obsessiveCompulsiveDisorderInterventionRecordForm";
    }

    @RequestMapping(value = "/newpage/depressionInterventionRecordForm", method = RequestMethod.GET)
    public String depressionInterventionRecordForm(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        String roleName = "健管师";  // 默认角色为健管师

        if (Optional.ofNullable(yh).isPresent()) {
            roleName = yhUserRoleMapper.selectRoleName(yh.getUserCode());
            // 如果 roleName 包含 "心理" 字符串，则修改显示角色为 心理咨询师
            if (roleName != null && roleName.contains("心理")) {
                roleName = "心理咨询师";
            }
        } else {
            roleName = "管理员";
        }
        model.addAttribute("roleName", roleName);
        return "newpage/form/depressionInterventionRecordForm";
    }

    @RequestMapping(value = "/newpage/listChronicDiseasePatientsForm", method = RequestMethod.GET)
    public String listChronicDiseasePatientsForm(Model model) {
        return "newpage/form/listChronicDiseasePatientsForm";
    }

    @RequestMapping(value = "/newpage/threeSimultaneousForms", method = RequestMethod.GET)
    public String threeSimultaneousForms(Model model) {
        return "newpage/form/threeSimultaneousForms";
    }


    @RequestMapping(value = "/newpage/individualOccupationalHealthMonitoringRecordFormForWorkers", method = RequestMethod.GET)
    public String individualOccupationalHealthMonitoringRecordFormForWorkers(Model model) {
        return "newpage/form/individualOccupationalHealthMonitoringRecordFormForWorkers";
    }

    @RequestMapping(value = "/newpage/occupationalDiseaseHazardMonitoringForm", method = RequestMethod.GET)
    public String occupationalDiseaseHazardMonitoringForm(Model model) {
        return "newpage/form/occupationalDiseaseHazardMonitoringForm";
    }


    @RequestMapping(value = "/newpage/riskAssessmentForm", method = RequestMethod.GET)
    public String riskAssessmentForm(Model model) {
        return "newpage/form/riskAssessmentForm";
    }

    @RequestMapping(value = "/newpage/psychologicalAssessmentForm", method = RequestMethod.GET)
    public String psychologicalAssessmentForm(Model model) {
        return "newpage/form/psychologicalAssessmentForm";
    }

    @RequestMapping(value = "/newpage/medicalExaminationInformation", method = RequestMethod.GET)
    public String medicalExaminationInformation(Model model) {
        return "newpage/form/medicalExaminationInformation";
    }

    @RequestMapping(value = "/newpage/serviceRecordFormForHypertensivePatients", method = RequestMethod.GET)
    public String serviceRecordFormForHypertensivePatients(Model model) {
        return "newpage/form/serviceRecordFormForHypertensivePatients";
    }

    @RequestMapping(value = "/newpage/effectEvaluationForm", method = RequestMethod.GET)
    public String effectEvaluationForm(Model model,String userCode) {
        model.addAttribute("userCode", userCode);
        return "newpage/form/effectEvaluationForm";
    }

    @RequestMapping(value = "/newpage/employeePsychologicalCounselingRecordForm", method = RequestMethod.GET)
    public String employeePsychologicalCounselingRecordForm(Model model) {
        return "newpage/form/employeePsychologicalCounselingRecordForm";
    }

    @RequestMapping(value = "/newpage/employeePsychologicalChart", method = RequestMethod.GET)
    public String employeePsychologicalChart(Model model) {
        return "newpage/form/employeePsychologicalChart";
    }

    @RequestMapping(value = "/newpage/interventionRecordFormForHighRiskPopulationFrom", method = RequestMethod.GET)
    public String interventionRecordFormForHighRiskPopulationFrom(Model model) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        String roleName = "健管师";  // 默认角色为健管师

        if (Optional.ofNullable(yh).isPresent()) {
            roleName = yhUserRoleMapper.selectRoleName(yh.getUserCode());
            // 如果 roleName 包含 "心理" 字符串，则修改显示角色为 心理咨询师
            if (roleName != null && roleName.contains("心理")) {
                roleName = "心理咨询师";
            }
        } else {
            roleName = "管理员";
        }
        model.addAttribute("roleName", roleName);
        return "newpage/form/interventionRecordFormForHighRiskPopulationFrom";
    }

    @RequestMapping(value = "/newpage/pneumoconiosisServiceRecordForm", method = RequestMethod.GET)
    public String pneumoconiosisServiceRecordForm(Model model) {
        return "newpage/form/pneumoconiosisServiceRecordForm";
    }

    @RequestMapping(value = "/newpage/serviceRecordFormForObesePatientsForm", method = RequestMethod.GET)
    public String serviceRecordFormForObesePatientsForm(Model model) {
        return "newpage/form/serviceRecordFormForObesePatientsForm";
    }


}
