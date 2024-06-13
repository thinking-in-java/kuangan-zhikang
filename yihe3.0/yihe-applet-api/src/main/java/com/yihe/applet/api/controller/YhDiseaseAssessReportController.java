package com.yihe.applet.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.yihe.applet.api.domain.YhDiseaseAssessTotalReportApp;
import com.yihe.applet.api.domain.YhDiseaseCategoryApp;
import com.yihe.applet.api.domain.YhDiseaseRiskResultApp;
import com.yihe.applet.api.domain.YhQuestionRiskTitleFront;
import com.yihe.applet.api.service.*;
import com.yihe.applet.api.util.common.constant.ApiResult;
import com.yihe.applet.api.util.common.constant.ApiResultConstant;
import com.yihe.applet.api.util.common.util.CommonUtils;
import com.yihe.applet.api.util.common.util.Result;
import com.yihe.applet.api.util.common.util.UUIDUtil;
import com.yihe.applet.api.util.constans.Constant;
import com.yihe.applet.api.util.request.DefaultYiheClient;
import com.yihe.applet.api.util.request.YiheClient;
import com.yihe.applet.api.vojo.CheckDiseaseAssessReport;
import com.yihe.applet.api.vojo.YhDiseaseRiskFactor;
import com.yihe.applet.api.vojo.YhDiseaseRiskFactors;
import com.yihe.common.utils.DateUtil;
import com.yihe.common.utils.ReflectionUtil;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.YHDiseaseAssessResolve;
import com.yihe.system.domain.YHDiseaseRiskOptimal;
import com.yihe.system.domain.YhDiseaseAssessReport;
import com.yihe.system.domain.YhDiseaseRiskFactorGroups;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年7月7日 下午3:46:45 
 *@version 1.0 
 */
@Controller
@RequestMapping("/api")
@Api(value = "YhDiseaseAssessReport控制器", description = "YhDiseaseAssessReport管理")
public class YhDiseaseAssessReportController {

	 @Resource
     private YhDiseaseAssessReportAppService yhDiseaseAssessReportAppService;
     @Resource
     private YhDiseaseAssessResolveAppService yhDiseaseAssessResolveAppService;
	 @Resource
	 private YhDiseaseAssessTotalReportAppService yhDiseaseAssessTotalReportAppService;
	 @Resource
	 private YhQuestionCheckJsonAppService yhQuestionCheckJsonAppService;
	 @Resource
	 private YhQuestionRiskTitleFrontService yhQuestionRiskTitleFrontService;
	 @Resource
	 private YhDiseaseRiskOptimalService yhDiseaseRiskOptimalService;
	 @Resource
	 private YhDiseaseCategoryAppService yhDiseaseCategoryAppService;
	 @Resource
	 private YhDiseaseRiskResultAppService yhDiseaseRiskResultAppService;
	 
		public static String dateDiff(String startTime,String endTime) {
			
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        long between = 0;
	        try {
	            java.util.Date begin = dfs.parse(startTime);
	            java.util.Date end = dfs.parse(endTime);
	            between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        long day = between / (24 * 60 * 60 * 1000);
	        long hour = (between / (60 * 60 * 1000) - day * 24);
	        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
	        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
	        System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
	       if(day>0) {
	    	   return day + "天前";
	       }
	       if(day <= 0 && hour > 0) {
	    	   return hour + "小时前";
	       }
	       if(hour <= 0 && min > 0) {
	    	   return min + "分钟前";
	       }
	       if(s < 60) {
	    	   return s + "秒前";
	       }
	      
		return "";
	       
	      
			
		}
	 @RequestMapping("/selectTotalReportByuserCodeNewest")
		@ResponseBody
	 public  Result selectTotalReportByuserCodeNewest(String userCode) {
		 YhDiseaseAssessTotalReportApp assessTotalReportApp = yhDiseaseAssessTotalReportAppService.selectTotalReportByuserCodeByNewest(userCode);
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 if(assessTotalReportApp != null) {
			 Date qtime = assessTotalReportApp.getQtime();
			 Date date = new Date();
			//获取String类型的时间
			String  endTime= sdf.format(date);
			String  startTime= sdf.format(qtime);
			String second = dateDiff(startTime,endTime);
			assessTotalReportApp.setRusult(second.indexOf("-")==-1?second:"1秒前");
		 }
		
		 return Result.ok(assessTotalReportApp);
		 
	 }
	 
	 /**
	  * 首页查询是否评估 评估数
	  */
	    @RequestMapping("/homePageAssessInfo")
		@ResponseBody
		public Result homePageAssessInfo(String usercode) {
		 YhDiseaseAssessTotalReportApp selectTotalReport = yhDiseaseAssessTotalReportAppService.selectTotalReportByuserCode(usercode);
		 if(null==selectTotalReport){
			 return Result.ok(null);
		 }else{
			 int j=0;//高风险
			 int k =0;//患或异常
			 String dfNames = selectTotalReport.getDfNames();
			 String skNames = selectTotalReport.getSkNames();
			 if(StringUtils.isNotEmpty(dfNames) ){
				 String[] split = dfNames.split(";");
				 for (int i = 0; i < split.length; i++) {
						if(StringUtils.isNotEmpty(split[i])){
							j++;
						}
				 }
			 }
			 
			 if(StringUtils.isNotEmpty(skNames) ){
				 String[] split = skNames.split(";");
				 for (int i = 0; i < split.length; i++) {
						if(StringUtils.isNotEmpty(split[i])){
							k++;
						}
				 }
			 }
			 
			 return Result.ok(j+k);
		 }
	 }
	
    /**
	 * @Title: checkImagetext  
	 * @Description: 查询改善因素图文
	 * @param @param response
	 * @param @param reqbody
	 * @param @return    设定文件  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping("/checkImagetext")
	@ResponseBody
	public Result checkImagetext(HttpServletResponse response, String improve) {
		String selectImagetext = null;
		if(StringUtils.isNotEmpty(improve)){
			selectImagetext = yhDiseaseAssessResolveAppService.selectImagetext(improve);
		}
		return Result.ok(selectImagetext);
	}
	 
	/**
	 * @Title: checkReport2  
	 * @Description: 查询报告
	 * @param @param response
	 * @param @param reqbody
	 * @param @return    设定文件  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping("/checkReport")
	@ResponseBody
	public String checkReport2(HttpServletResponse response, @RequestBody Map<String, String> reqbody) {
		ApiResult ApiResult = null;
		net.sf.json.JSONObject jsonApiResult = null;
		//根据用户code 和 疾病id 查询报告
		String disease = reqbody.get("diseaseid");
		String userCode = reqbody.get("userCode");
		String sex = reqbody.get("sex");
		Integer diseaseid = 1;
		if (StringUtil.isNotEmpty(disease)) {
			diseaseid = Integer.parseInt(disease);
		}
		//查询评估总表
		YhDiseaseAssessTotalReportApp selectTotalReport = yhDiseaseAssessTotalReportAppService.selectTotalReportByuserCode(userCode);
		String requestSerialnum = selectTotalReport.getRequestSerialnum();;
		if (StringUtil.isNotEmpty(requestSerialnum)) {
			CheckDiseaseAssessReport checkDiseaseAssessReport = new CheckDiseaseAssessReport();
			List<YHDiseaseAssessResolve> yhDiseaseAssessResolve = yhDiseaseAssessResolveAppService.selectResolves(diseaseid, requestSerialnum);
			int size = yhDiseaseAssessResolve.size();
			YHDiseaseAssessResolve[] yhDiseaseAssessResolveArray = new YHDiseaseAssessResolve[size];
			for (int i = 0; i < size; i++) {
				String type = yhDiseaseAssessResolve.get(i).getType();
				if(StringUtils.isNotEmpty(type)){
					String[] split = type.split("：");
					String nowName = split[1].replaceAll("努力目标", "");
					String target = split[2];
					yhDiseaseAssessResolve.get(i).setType(nowName);
					yhDiseaseAssessResolve.get(i).setResultSerialNum(target);
				}
				yhDiseaseAssessResolveArray[i] = yhDiseaseAssessResolve.get(i);
			}
			// 查询评估报告结果
			YhDiseaseAssessReport yhDiseaseAssessReport = yhDiseaseAssessReportAppService.selectAssessReport(diseaseid, requestSerialnum);
			// 根据疾病id雷达图id以及性别查询相关疾病危险因素
			List<YHDiseaseRiskOptimal> yhDiseaseRiskOptimalList =yhDiseaseRiskOptimalService.selectYhDiseaseRiskOptimalById(1,sex,diseaseid.toString());
			int yhDiseaseRiskOptimalSize = yhDiseaseRiskOptimalList.size();
			YhDiseaseRiskFactorGroups[] yhDiseaseRiskFactorGroups = new YhDiseaseRiskFactorGroups[yhDiseaseRiskOptimalSize];
			YhDiseaseRiskFactorGroups yhDiseaseRiskFactors = null;
			
			if (yhDiseaseRiskOptimalSize > 0) {
				    // 查询评估相关危险因素
				    YhDiseaseRiskResultApp yhDiseaseRiskResult = yhDiseaseRiskResultAppService.selectRiskResulByRequestNum(selectTotalReport.getRequestSerialnum());
					for (int j = 0; j < yhDiseaseRiskOptimalSize; j++) {
						YHDiseaseRiskOptimal yhDiseaseRiskOptimal = yhDiseaseRiskOptimalList.get(j);
						yhDiseaseRiskFactors = new YhDiseaseRiskFactorGroups();
						yhDiseaseRiskFactors.setDiseaseRiskFactorName(yhDiseaseRiskOptimal.getQname());
						// 调用计算雷达图以及风险因素
						doDiseaseRiskResult2(yhDiseaseRiskOptimal, yhDiseaseRiskResult, yhDiseaseRiskFactors);
						yhDiseaseRiskFactorGroups[j] = yhDiseaseRiskFactors;
					}
			}
			
			 //根据疾病id以及性别查询相关除雷达图之外的疾病危险因素
			 List<YHDiseaseRiskOptimal> yhDiseaseRiskOptimalMoreList =yhDiseaseRiskOptimalService.selectYhDiseaseRiskOptimalById(3,sex,diseaseid.toString());
			 int yhDiseaseRiskOptimalMoreSize = yhDiseaseRiskOptimalMoreList.size();
			 YhDiseaseRiskFactorGroups[] yhDiseaseRiskFactorMoreGroups = new YhDiseaseRiskFactorGroups[yhDiseaseRiskOptimalMoreSize];
			 YhDiseaseRiskFactorGroups yhDiseaseRiskMoreFactors = null;
			 if (yhDiseaseRiskOptimalMoreSize > 0) {
				    // 查询评估相关危险因素
				    YhDiseaseRiskResultApp yhDiseaseRiskResult = yhDiseaseRiskResultAppService.selectRiskResulByRequestNum(selectTotalReport.getRequestSerialnum());
					for (int j = 0; j < yhDiseaseRiskOptimalMoreSize; j++) {
						YHDiseaseRiskOptimal yhDiseaseRiskOptimal = yhDiseaseRiskOptimalMoreList.get(j);
						yhDiseaseRiskMoreFactors = new YhDiseaseRiskFactorGroups();
						yhDiseaseRiskMoreFactors.setDiseaseRiskFactorName(yhDiseaseRiskOptimal.getQname());
						// 调用计算雷达图以及风险因素
						doDiseaseRiskResult2(yhDiseaseRiskOptimal, yhDiseaseRiskResult, yhDiseaseRiskMoreFactors);
						yhDiseaseRiskFactorMoreGroups[j] = yhDiseaseRiskMoreFactors;
					}
			 }
			
			// 封装数据
			checkDiseaseAssessReport.setTime(DateUtil.Date2Str(yhDiseaseAssessReport.getQtime()));
			checkDiseaseAssessReport.setYhDiseaseAssessReport(yhDiseaseAssessReport);
			checkDiseaseAssessReport.setYhDiseaseAssessResolve(yhDiseaseAssessResolveArray);
			checkDiseaseAssessReport.setYhDiseaseAssessTotalReport(selectTotalReport);
			checkDiseaseAssessReport.setYhDiseaseRiskFactorGroups(yhDiseaseRiskFactorGroups);
			checkDiseaseAssessReport.setYhDiseaseRiskFactorMoreGroups(yhDiseaseRiskFactorMoreGroups);
			String riskLevel = yhDiseaseAssessReport.getRiskLevel();
			String resultRiskStep = getResultRiskStep(riskLevel);
			checkDiseaseAssessReport.setTmep(resultRiskStep);
			float bmi = selectTotalReport.getBmi();
			if (18.5 > bmi) {
				checkDiseaseAssessReport.setBimStatus("偏瘦");
				checkDiseaseAssessReport.setBimStep("1");
            }else if(bmi<23.9){
            	checkDiseaseAssessReport.setBimStatus("正常");
				checkDiseaseAssessReport.setBimStep("2");
            }else if(bmi<27.9){
            	checkDiseaseAssessReport.setBimStatus("超重");
				checkDiseaseAssessReport.setBimStep("3");
            }else{
            	checkDiseaseAssessReport.setBimStatus("肥胖");
				checkDiseaseAssessReport.setBimStep("4");
            }
			ApiResult = new ApiResult(ApiResultConstant.SUCCESS, checkDiseaseAssessReport);
			jsonApiResult = net.sf.json.JSONObject.fromObject(ApiResult);
		} else {
			ApiResult = new ApiResult(ApiResultConstant.FAIL);
			jsonApiResult = net.sf.json.JSONObject.fromObject(ApiResult);
		}
		return jsonApiResult.toString();
	}
	
	
	/**
     * @param @param  gender
     * @param @return 设定文件
     * @return Result    返回类型
     * @throws
     * @Title: getRiskQuestionsByDisId
     * @Description: 根据影响疾病危险因素查询相关问卷 题目回显
     */
    @RequestMapping("/getRiskQuestions")
    @ResponseBody
    public Result getRiskQuestionsByDisId(String diseaseid) {
        //根据用户code 和 疾病id 查询问卷
        List<YhQuestionRiskTitleFront> quesList = yhQuestionRiskTitleFrontService.selRiskQuestionByDisId(Integer.parseInt(diseaseid));
        List<YhQuestionRiskTitleFront> fatherQuesList = new ArrayList<>();// 父级list
        // 给数据加上下标以及显示层级
        for (int i = 0; i < quesList.size(); i++) {
            quesList.get(i).setIndex(i);
            quesList.get(i).setzIndex(quesList.size() - i);
        }
        List<YhQuestionRiskTitleFront> childQuesList = new ArrayList<>();// 子级list
        // 第一轮筛选，将一级父级题目和一级子题目、二级子题目筛选出来
        for (int i = 0; i < quesList.size(); i++) {
            YhQuestionRiskTitleFront question = quesList.get(i);
            // 如果是大题且没有上级题目的
            if (question.getParentId().equals("-1") || question.getParentId().equals("0")) {
                fatherQuesList.add(question);
            } else {
                childQuesList.add(question);
            }
        }
        // 第二轮筛选，将子题目赋值进父级题目中去
        for (int i = 0; i < fatherQuesList.size(); i++) {
            // 父级题目可能不止一个子级题目
            List<YhQuestionRiskTitleFront> childList = new ArrayList<>();// 即将插入父级的子级list
            for (int j = 0; j < childQuesList.size(); j++) {
                YhQuestionRiskTitleFront childQuestion = childQuesList.get(j);
                List<YhQuestionRiskTitleFront> childTwoQuesList = new ArrayList<>();// 二级子级list
                String[] parentId = childQuesList.get(j).getParentId().split(",");
                // 如果一级子题目中还有子级题目
                if (parentId.length > 1) {
                    for (int a = j; a < childQuesList.size(); a++) {
                        if (Integer.parseInt(childQuesList.get(a).getParentId().split(",")[0]) == childQuestion
                                .getId()) {
                            childTwoQuesList.add(childQuesList.get(a));
                            childQuestion.setChildList(childTwoQuesList);
                        }
                    }
                }
                // 将一级子题目插入父题目
                if (fatherQuesList.get(i).getId() == Integer.parseInt(parentId[0])) {
                    childList.add(childQuestion);
                    fatherQuesList.get(i).setChildList(childList);
                }
            }
        }
        return Result.ok(fatherQuesList);
    }

    
   
    /**
     * @param @param  response
     * @param @param  reqbody
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: doDiseaseCategory
     * @Description: 查询十一大风险疾病
     */
    @RequestMapping("/doDiseaseCategory")
    @ResponseBody
    public String doDiseaseCategory(HttpServletResponse response, @RequestBody Map<String, String> reqbody) {
        ApiResult ApiResult = null;
        net.sf.json.JSONObject jsonApiResult = null;
        YhDiseaseRiskFactors newyhDiseaseRiskFactors = null;
        String userCode = reqbody.get("userCode");
        int disCode = 0;
        if (reqbody.get("sex").toString().equals("1")) {
            disCode = 10;
        } else {
            disCode = 9;
        }
        // 查询评估报告结果
        YhDiseaseRiskFactor yhDiseaseRiskFactor = new YhDiseaseRiskFactor();
        // 根据性别查询疾病分类中的id，将id插入接口得到返回结果
        List<YhDiseaseCategoryApp> yhDiseaseCategoryList = yhDiseaseCategoryAppService.selTopTenData(disCode);
        //查询评估总表
      	YhDiseaseAssessTotalReportApp selectTotalReport = yhDiseaseAssessTotalReportAppService.selectTotalReportByuserCode(userCode);
        List<YhDiseaseRiskFactors> yhDiseaseRiskFactorsList = new ArrayList<YhDiseaseRiskFactors>();
        if (yhDiseaseCategoryList.size() > 0 && yhDiseaseCategoryList != null) {
            for (YhDiseaseCategoryApp yhDiseaseCategory : yhDiseaseCategoryList) {
                newyhDiseaseRiskFactors = new YhDiseaseRiskFactors();
                Integer diseaseCode = (int) yhDiseaseCategory.getDiseaseCode();
                String diseaseName = yhDiseaseCategory.getDiseaseName();
                // 查询评估报告结果
    			YhDiseaseAssessReport selectResolves = yhDiseaseAssessReportAppService.selectAssessReport(diseaseCode, selectTotalReport.getRequestSerialnum());
                   if(null!=selectResolves){
                	   // 计算疾病颜色
                	   String riskLevel = selectResolves.getRiskLevel();
                       YhDiseaseRiskFactors yhDiseaseRiskFactors = getResultRiskFactor(newyhDiseaseRiskFactors,
                       		riskLevel, diseaseName);
                       if (!CommonUtils.isEmpty(yhDiseaseRiskFactors.getRiskName())) {
                           yhDiseaseRiskFactorsList.add(yhDiseaseRiskFactors);
                       }
                       yhDiseaseCategory.setRiskGroup(riskLevel);
                       String orderNum = getResultRiskStep(riskLevel);
                       yhDiseaseCategory.setOrderNum(Integer.parseInt(orderNum));
                   }
            }
            for (YhDiseaseCategoryApp yhDiseaseCategory : yhDiseaseCategoryList){
				if(yhDiseaseCategory.getOrderNum()==null){
					yhDiseaseCategoryList.remove(yhDiseaseCategory);
				}
			}
            //进行等级排序
            Collections.sort(yhDiseaseCategoryList); 
            yhDiseaseRiskFactor.setYhDiseaseCategory(yhDiseaseCategoryList);
            yhDiseaseRiskFactor.setYhDiseaseRiskFactors(yhDiseaseRiskFactorsList);
        }
        ApiResult = new ApiResult(ApiResultConstant.SUCCESS, yhDiseaseRiskFactor);
        jsonApiResult = net.sf.json.JSONObject.fromObject(ApiResult);
        return jsonApiResult.toString();
    }
    
    
    

    /**
     * @param @param yhDiseaseRiskOptimal
     * @param @param document
     * @param @param yhDiseaseRiskFactors    设定文件
     * @return void    返回类型
     * @throws
     * @Title: doDiseaseRiskResult2
     * @Description: 根据每个疾病的雷达图相关危险因素划分区间值，并计算理想与当前风险在区间的位置
     */
    private void doDiseaseRiskResult2(YHDiseaseRiskOptimal yhDiseaseRiskOptimal, YhDiseaseRiskResultApp yhDiseaseRiskResult,
                                      YhDiseaseRiskFactorGroups yhDiseaseRiskFactors) {
        //当前分数
        Integer badge = 0;
        //理想分数
        Integer Segment = 50;
        String nowValue = null;
        String qcode = toCamelCase(yhDiseaseRiskOptimal.getXpath());
        String qtype = yhDiseaseRiskOptimal.getQtype();
        String Remark = yhDiseaseRiskOptimal.getRemark();
        String qvalue = yhDiseaseRiskOptimal.getValue();
        Collection<String> yhDiseaseRiskResultReflect;
        try {
        	yhDiseaseRiskResultReflect = ReflectionUtil.getReflect(yhDiseaseRiskResult);
            for (String string : yhDiseaseRiskResultReflect) {
                if (string.equals(qcode)) {
                	 Object document = Configuration.defaultConfiguration().jsonProvider().parse(ReflectionUtil.getFieldValue(yhDiseaseRiskResult, string).toString());
                	 nowValue = JsonPath.read(document, "$.now_value").toString();
                	 //判断是否是数值
                     if ("T".equals(qtype)) {
                              Segment = 50;
                              boolean contains = Remark.contains("/");
                              if(contains){
                            	  if(StringUtils.isNotEmpty(nowValue)){
                                	  if (qvalue.equals(nowValue)) {
                                          badge = 50;
                                          yhDiseaseRiskFactors.setDiseaseRiskFactorColor(Remark.split("/")[0]);
                                      }else {
                                          badge = 100;
                                          yhDiseaseRiskFactors.setDiseaseRiskFactorColor(Remark.split("/")[1]);
                                      } 
                                  }else{
                                	  badge = 50;
                                	  yhDiseaseRiskFactors.setDiseaseRiskFactorColor(Remark.split("/")[0]);
                                  }
                              }else{
                            	  
                                   if(StringUtils.isNotEmpty(nowValue)){
                                	  if (qvalue.equals(nowValue)) {
                                          badge = 50;
                                      }else {
                                          badge = 100;
                                      } 
                                  }else{
                                	  badge = 50;
                                  }
                              }
                              
                            	
                     }else{
                    	Double reqvalue = 0.0;
                    	if(StringUtil.isNotEmpty(nowValue)){
                             reqvalue = Double.valueOf(nowValue);
                    	}
                        Integer rangePoint = Integer.parseInt(yhDiseaseRiskOptimal.getRadarRangePoint());
                        int s = 100 / rangePoint;
                        Segment = s;
                        // 获取区间值
                        Double minValue = yhDiseaseRiskOptimal.getMinv();
						 Double maxvalue = yhDiseaseRiskOptimal.getMaxv();
                        if (rangePoint > 2) {
                            if (minValue <= reqvalue && reqvalue < maxvalue) {
                            	 badge = s;
                            } else {
                                 badge = 100;
                            }
                            
                        } else {
                            if (reqvalue <= maxvalue) {
                                 badge = 50;
                            }else{
                            	 badge = 100;
                            }
                        }
                        yhDiseaseRiskFactors.setDiseaseRiskFactorColor(nowValue);
                    }
                    yhDiseaseRiskFactors.setDiseaseRiskFactorBadge(badge);
                    yhDiseaseRiskFactors.setDiseaseRiskFactorSegment(Segment);
                    yhDiseaseRiskFactors.setName(yhDiseaseRiskOptimal.getOptimal());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
   /*
    *等级排序
    *
    */
   private String getResultRiskStep(String level) {
	   String step = null;
	   
	   if (level.contains("较低")) {
		   step = "300";
       } else if (level.contains("低")) {
    	   step = "450";
       } else if (level.contains("正常")) {
    	   step = "200";
       } else if (level.contains("中等")) {
    	   step = "100";
       } else if (level.contains("较高")) {
    	   step = "-100";
       } else if (level.contains("高")){
    	   step = "-280";
       } else if (level.contains("患")){
    	   step = "-450";
       } else if (level.contains("异")) {
    	   step = "-290";
       } else if (level.contains("肥胖")) {
    	   step = "-300";
       }else if (level.contains("超重")){
    	   step = "-300";
       } else{
    	   step = "-450";
       }
       return step;
   }
    
    
   /*
    *
    *
    */
   private YhDiseaseRiskFactors getResultRiskFactor(YhDiseaseRiskFactors yhDiseaseRiskFactors, String level,String diseaseName) {
       if (level.contains("高")) {
           yhDiseaseRiskFactors.setRiskBadge("70%");
           yhDiseaseRiskFactors.setRiskColor("high");
           yhDiseaseRiskFactors.setRiskName(diseaseName);
       } else if (level.contains("患")) {
           yhDiseaseRiskFactors.setRiskBadge("100%");
           yhDiseaseRiskFactors.setRiskColor("lipids");
           yhDiseaseRiskFactors.setRiskName(diseaseName);
       } else if (level.contains("异")) {
           yhDiseaseRiskFactors.setRiskBadge("100%");
           yhDiseaseRiskFactors.setRiskColor("lipids");
           yhDiseaseRiskFactors.setRiskName(diseaseName);
       }else if (level.contains("肥胖")) {
           yhDiseaseRiskFactors.setRiskBadge("100%");
           yhDiseaseRiskFactors.setRiskColor("lipids");
           yhDiseaseRiskFactors.setRiskName(diseaseName);
       }else if (level.contains("超重")) {
           yhDiseaseRiskFactors.setRiskBadge("100%");
           yhDiseaseRiskFactors.setRiskColor("high");
           yhDiseaseRiskFactors.setRiskName(diseaseName);
       } 
       return yhDiseaseRiskFactors;
   }
    
   
   
   
   /**
    * @Title: diabetesRadarMap @Description: 改变危险因素风险变化 @param @param
    * request @param @param response @param @param data 设定文件 @return void
    * 返回类型 @throws
    */
   @RequestMapping("/diabetesRadarMap")
   @ResponseBody
   public String diabetesRadarMap(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject rData) {
       ApiResult ApiResult = null;
       net.sf.json.JSONObject jsonApiResult = null;
       JSONObject checkData = (JSONObject) JSON.toJSON(rData.get("rData"));
       String diseaseid=checkData.get("diseaseid").toString();
       JSONObject person=(JSONObject) JSON.toJSON(checkData.get("person"));
       String sex=person.get("sex").toString();
       String userCheckAnswer = checkData.toString();
       DocumentContext document = JsonPath.parse(userCheckAnswer);
       try {
           // 生成请求不重复流水号
           String javaUUID = UUIDUtil.getJavaUUID();
           JsonPath serialNum = JsonPath.compile("$.serialNum");
           document.set(serialNum, javaUUID);
       } catch (Exception e) {
           ApiResult = new ApiResult(ApiResultConstant.FAIL);
           jsonApiResult = net.sf.json.JSONObject.fromObject(ApiResult);
       }
       // 接口路径
       String doAssess_url = Constant.DOASSESS_URL;
       // 格林时间戳
       String matTime = com.yihe.applet.api.util.common.util.DateUtil.getMatTime(new Date());
       // 初始化client
       YiheClient yiheClient = new DefaultYiheClient("1", Constant.PRIVATEKEY, matTime);
       // 进行json转换
       JSONObject data = JSONObject.parseObject(document.jsonString());
       // 根据请求参数
       // 返回json字符串
       String result = yiheClient.execute(data, doAssess_url);
       // 进行json转换
       net.sf.json.JSONObject fromObject = net.sf.json.JSONObject.fromObject(result);
       String dataString = fromObject.getString("data");
       // 评估失败返回结果
       if (!fromObject.getString("result").equals("success")) {
           ApiResult = new ApiResult(ApiResultConstant.FAIL);
           jsonApiResult = net.sf.json.JSONObject.fromObject(ApiResult);
       }
       Object resultDocument = Configuration.defaultConfiguration().jsonProvider().parse(dataString);
       // 获取当前风险
       String now_risk_level = JsonPath.read(resultDocument, "$.now_risk.level") + "";
       // 获取理想风险
       String Ideal_risk_level = JsonPath.read(resultDocument, "$.Ideal_risk.level") + "";
       // 获取改变风险等级
       String now_dis_level = JsonPath.read(resultDocument, "$.risk.level") + "";

       // 根据疾病id雷达图id以及性别查询相关疾病危险因素
       List<YHDiseaseRiskOptimal> yhDiseaseRiskOptimalList =yhDiseaseRiskOptimalService.selectYhDiseaseRiskOptimalById(1,sex,diseaseid);
       int yhDiseaseRiskOptimalSize = yhDiseaseRiskOptimalList.size();
       YhDiseaseRiskFactorGroups[] yhDiseaseRiskFactorGroups = new YhDiseaseRiskFactorGroups[yhDiseaseRiskOptimalSize];
       YhDiseaseRiskFactorGroups yhDiseaseRiskFactors = null;
       if (yhDiseaseRiskOptimalSize > 0) {
           for (int j = 0; j < yhDiseaseRiskOptimalSize; j++) {
               YHDiseaseRiskOptimal yhDiseaseRiskOptimal = yhDiseaseRiskOptimalList.get(j);
               yhDiseaseRiskFactors = new YhDiseaseRiskFactorGroups();
               yhDiseaseRiskFactors.setDiseaseRiskFactorName(yhDiseaseRiskOptimal.getQname());
               // 调用计算雷达图以及风险因素
               doDiseaseRiskMap(yhDiseaseRiskOptimal,resultDocument, yhDiseaseRiskFactors);
               yhDiseaseRiskFactorGroups[j] = yhDiseaseRiskFactors;
           }
       }
       LinkedHashMap<String, Object> personmap = new LinkedHashMap<String, Object>();
       personmap.put("nowrisk", now_risk_level);
       personmap.put("IdealRisk", Ideal_risk_level);
       personmap.put("changeLever", now_dis_level.toString());
       personmap.put("yhDiseaseRiskFactorGroups",net.sf.json.JSONArray.fromObject(yhDiseaseRiskFactorGroups));
       ApiResult = new ApiResult(ApiResultConstant.SUCCESS, personmap);
       jsonApiResult = net.sf.json.JSONObject.fromObject(ApiResult);
       return jsonApiResult.toString();
   }
           
      
   
   
   private void doDiseaseRiskMap(YHDiseaseRiskOptimal yhDiseaseRiskOptimal, Object docment,
           YhDiseaseRiskFactorGroups yhDiseaseRiskFactors) {
					//回显颜色
					//当前分数
					Integer badge = 0;
					//理想分数
					Integer Segment = 50;
					String qcode = yhDiseaseRiskOptimal.getXpath();
			        String qtype = yhDiseaseRiskOptimal.getQtype();
			        String qvalue = yhDiseaseRiskOptimal.getValue();
					// 得到当前危险因素值
					String value=null;
					try {
					    value = JsonPath.read(docment, "$.optimal."+qcode+".now_value") + "";
					}catch (Exception e){
					   
					}
					if(value==null){
					    return;
					}
					//判断是否是数值
                    if ("T".equals(qtype)) {
                    	 if(StringUtils.isNotEmpty(value)){
                        	if (qvalue.equals(value)) {
                                badge = 50;
                            }else {
                                badge = 100;
                            }
                    	 }else{
                    		 badge = 50;
                    	 }
                    } else {
                    	Double reqvalue = 0.0;
                    	if(StringUtil.isNotEmpty(value)){
                             reqvalue = Double.valueOf(value);
                    	}
                        Integer rangePoint = Integer.parseInt(yhDiseaseRiskOptimal.getRadarRangePoint());
                        int s = 100 / rangePoint;
                        Segment = s;
                        // 获取区间值
						Double minValue = yhDiseaseRiskOptimal.getMinv();
                        Double maxvalue = yhDiseaseRiskOptimal.getMaxv();
                        if (rangePoint > 2) {
                            if (minValue <= reqvalue && reqvalue < maxvalue) {
                            	 badge = s;
                            } else {
                                 badge = 80;
                            }
                        } else {
                           
                            if (reqvalue <= maxvalue ) {
                                badge = 50;
                            }else{
                                badge = 80;
                            }
                        }
                    }
					yhDiseaseRiskFactors.setDiseaseRiskFactorBadge(badge);
					yhDiseaseRiskFactors.setDiseaseRiskFactorSegment(Segment);
}
   
   
   
  
   public static String toCamelCase(String s)
   {    /** 下划线 */
	    final char SEPARATOR = '_';
       if (s == null)
       {
           return null;
       }
       s = s.toLowerCase();
       StringBuilder sb = new StringBuilder(s.length());
       boolean upperCase = false;
       for (int i = 0; i < s.length(); i++)
       {
           char c = s.charAt(i);

           if (c == SEPARATOR)
           {
               upperCase = true;
           }
           else if (upperCase)
           {
               sb.append(Character.toUpperCase(c));
               upperCase = false;
           }
           else
           {
               sb.append(c);
           }
       }
       return sb.toString();
   }
   
   
   
    
}
