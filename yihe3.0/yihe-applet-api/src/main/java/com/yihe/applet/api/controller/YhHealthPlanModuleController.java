package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.service.YhDiseaseAssessReportAppService;
import com.yihe.applet.api.service.YhDiseaseAssessTotalReportAppService;
import com.yihe.applet.api.service.YhDiseaseCategoryAppService;
import com.yihe.applet.api.service.YhHealthyPlanService;
import com.yihe.applet.api.util.common.util.CommonUtils;
import com.yihe.applet.api.vojo.YhDiseaseRiskFactor;
import com.yihe.applet.api.vojo.YhDiseaseRiskFactors;
import com.yihe.system.domain.YhDiseaseAssessReport;
import com.yihe.system.domain.YhFoodSrecipe;
import com.yihe.system.domain.YhFoodSrecipeRecord;
import com.yihe.system.domain.YhFoodUserScrecipe;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/YhHealthPlanModuleController")
@Api(value = "YhHealthPlanModuleController控制器", description = "YhHealthPlanModuleController管理")
public class YhHealthPlanModuleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthPlanModuleController.class);
		@Autowired
		YhHealthyPlanService healthyplanservice;
		@Resource
		private YhDiseaseAssessTotalReportAppService yhDiseaseAssessTotalReportAppService;
		@Resource
		private YhDiseaseCategoryAppService yhDiseaseCategoryAppService;
		@Resource
		private YhDiseaseAssessReportAppService yhDiseaseAssessReportAppService;

		// 查询所有的健康计划
		@RequestMapping(value = "/selectallheathplan")
		@ResponseBody
		public Result selectallheathplan(Integer curPage, Integer pageSize,String Usercode,String gender) {
			long a = healthyplanservice.selectyhhealthycount();
			//查询评估总表
			// 查询评估报告结果
			YhDiseaseRiskFactor yhDiseaseRiskFactor = new YhDiseaseRiskFactor();
			int disCode = 0;
			if (("1").equals(gender)) {
				disCode = 10;
			} else {
				disCode = 9;
			}
			YhDiseaseRiskFactors newyhDiseaseRiskFactors = null;
				List<YhDiseaseCategoryApp> yhDiseaseCategoryList = yhDiseaseCategoryAppService.selTopTenData(disCode);
			YhDiseaseAssessTotalReportApp selectTotalReport = yhDiseaseAssessTotalReportAppService.selectTotalReportByuserCode(Usercode);
			List<YhDiseaseRiskFactors> yhDiseaseRiskFactorsList = new ArrayList<YhDiseaseRiskFactors>();
			List<YhHealthyPlanApp> yhhealthyplan=healthyplanservice.yhhealthyplan(curPage, pageSize);
			List<YhHealthyPlanApp> newyhhealthyplan=new ArrayList<YhHealthyPlanApp>();
			if (yhDiseaseCategoryList.size() > 0 && yhDiseaseCategoryList != null && selectTotalReport!=null) {
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
				try{
					//进行等级排序
					Collections.sort(yhDiseaseCategoryList);
					//return Result.ok(yhhealthyplan, a);
				}catch (Exception  e){

				}


				for (int i=0;i<yhDiseaseCategoryList.size();i++){
					if(yhDiseaseCategoryList.get(i).getDiseaseName().equals("血脂异常")&&yhDiseaseCategoryList.get(i).getRiskGroup().equals("血脂异常")){
						for (int j=0;j<yhhealthyplan.size();j++){
							if(yhhealthyplan.get(j).getPlanName().equals("28天血脂管理计划") || yhhealthyplan.get(j).getPlanName().equals("28天 血脂管理计划")){
								yhhealthyplan.get(j).setHavedeiss(true);
							}
						}
					}
					if(yhDiseaseCategoryList.get(i).getDiseaseName().equals("高血压")&&(yhDiseaseCategoryList.get(i).getRiskGroup().equals("较高风险")|| yhDiseaseCategoryList.get(i).getRiskGroup().equals("高风险"))){
						for (int j=0;j<yhhealthyplan.size();j++){
							if(yhhealthyplan.get(j).getPlanName().equals("28天血压管理计划") || yhhealthyplan.get(j).getPlanName().equals("28天 血压管理计划")){
								yhhealthyplan.get(j).setHavedeiss(true);
							}
						}
					}
					if(yhDiseaseCategoryList.get(i).getDiseaseName().equals("糖尿病")&&(yhDiseaseCategoryList.get(i).getRiskGroup().equals("较高风险")|| yhDiseaseCategoryList.get(i).getRiskGroup().equals("高风险"))){
						for (int j=0;j<yhhealthyplan.size();j++){
							if(yhhealthyplan.get(j).getPlanName().equals("28天血糖管理计划")|| yhhealthyplan.get(j).getPlanName().equals("28天 血糖管理计划")){
								yhhealthyplan.get(j).setHavedeiss(true);
							}
						}
					}
					if(yhDiseaseCategoryList.get(i).getDiseaseName().equals("肥胖症")&&(yhDiseaseCategoryList.get(i).getRiskGroup().equals("超重")|| yhDiseaseCategoryList.get(i).getRiskGroup().equals("肥胖"))){
						for (int j=0;j<yhhealthyplan.size();j++){
							if(yhhealthyplan.get(j).getPlanName().equals("28天高效减肥计划")|| yhhealthyplan.get(j).getPlanName().equals("28天 减脂计划")){
								yhhealthyplan.get(j).setHavedeiss(true);
							}
						}
					}
					if(yhDiseaseCategoryList.get(i).getDiseaseName().equals("痛风")&&(yhDiseaseCategoryList.get(i).getRiskGroup().equals("较高风险")|| yhDiseaseCategoryList.get(i).getRiskGroup().equals("高风险"))){
						for (int j=0;j<yhhealthyplan.size();j++){
							if(yhhealthyplan.get(j).getPlanName().equals("28天 高尿酸计划")){
								yhhealthyplan.get(j).setHavedeiss(true);
							}
						}
					}

				}

				for (int h=0; h<yhhealthyplan.size();h++){
					if(yhhealthyplan.get(h).getHavedeiss()!=null){
						newyhhealthyplan.add(yhhealthyplan.get(h));
					}
				}
				for (int k=0; k<yhhealthyplan.size();k++){
					if(yhhealthyplan.get(k).getHavedeiss()==null){
						newyhhealthyplan.add(yhhealthyplan.get(k));
					}
				}
				yhhealthyplan=newyhhealthyplan;
			}
			return Result.ok(yhhealthyplan, a);
		}

		// 增加观看人数，小眼睛
		@RequestMapping(value = "/yhhealthplanwatch")
		@ResponseBody
		public Result yhhealthplanwatch(Integer id) {
			return Result.ok(healthyplanservice.yhhealthplanwatch(id));
		}

		// 查询计划详情
		@RequestMapping(value = "/selectHealthPlanDetail")
		@ResponseBody
		public Result selectHealthPlanDetail(Integer id) {
			return Result.ok(healthyplanservice.selectyhhealthyplanbyid(id));
		}

		// 添加个人计划
		@RequestMapping(value = "/addHealthPlanDetail")
		@ResponseBody
		public Result addHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			yhuserhealthyplan.setPlanDay(1);
			yhuserhealthyplan.setCourseseq(0);
			yhuserhealthyplan.setPlanState(0);
			Date date=new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE,1);
			date=calendar.getTime();
			yhuserhealthyplan.setPlanStartdate(df.format(date)+" 00:00:00");
			return Result.ok(healthyplanservice.addHealthPlanDetail(yhuserhealthyplan));
		}

		// 判断计划是否拥有健康计划
		@RequestMapping(value = "/boolenaddHealthPlanDetail")
		@ResponseBody
		public Result boolenaddHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan) {
			return Result.ok(healthyplanservice.boolenaddHealthPlanDetail(yhuserhealthyplan));
		}

		// 拥有计划的更改计划
		@RequestMapping(value = "/updateHealthPlanDetail")
		@ResponseBody
		public Result updateHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE,1);
			date=calendar.getTime();
			yhuserhealthyplan.setPlanDay(1);
			yhuserhealthyplan.setCourseseq(0);
			yhuserhealthyplan.setPlanState(0);
			yhuserhealthyplan.setPlanStartdate(df.format(date)+" 00:00:00");
			return Result.ok(healthyplanservice.updateHealthPlanDetail(yhuserhealthyplan));
		}

		// 退出计划
		@RequestMapping(value = "/delHealthPlanDetail")
		@ResponseBody
		public Result delHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan) {
			return Result.ok(healthyplanservice.delHealthPlanDetail(yhuserhealthyplan));
		}

		// 查询我的健康计划
		@RequestMapping(value = "/selectMyHealthPlanDetail")
		@ResponseBody
		public Result selectyhuserhealthplanisbegin(YhUserHealthyPlanApp yhuserhealthyplan) {

			return Result.ok(healthyplanservice.selectyhuserhealthplanisbegin(yhuserhealthyplan));
		}

		// 查询已经开始的健康计划
		@RequestMapping(value = "/selectalreadybegun")
		@ResponseBody
		public Result selectalreadybegun(YhUserHealthyPlanApp yhuserhealthyplan) {
			return Result.ok(healthyplanservice.selectalreadybegun(yhuserhealthyplan));
		}

		// 查询开始的健康计划各个任务详情
		@RequestMapping(value = "/selectdisplanbyid")
		@ResponseBody
		public Result selectdisplanbyid(YhUserHealthyPlanApp yhuserhealthyplan) {
			YhHealthyPlandetail detal=healthyplanservice.plandetail(yhuserhealthyplan.getId());

			if(detal.getPlandetailType()==9){
				String[] detals=detal.getPlandetailContent().split("&&");
				//查询这个人是否有健康计划的套餐
				YhFoodSrecipeRecord recode=healthyplanservice.selectrecode(yhuserhealthyplan.getUserCode());
				//查询出现在是第几天的健康计划
				Integer day=healthyplanservice.selectday(yhuserhealthyplan.getUserCode());
				//查询出28天的饮食
				YhFoodSrecipe yhfoodsrecipe=healthyplanservice.selectsrecipe(recode.getMealId());
				String[] ids=yhfoodsrecipe.getfElements().split(";");
				//找到28天中那一天的饮食下标
				String nowfood=ids[day-1];
				//查询当前一天推荐的饮食
				YhFoodUserScrecipe  selectscrecipe=healthyplanservice.selectscrecipe(nowfood);
				System.out.println(selectscrecipe);
				String[] foods=selectscrecipe.getfElements().split(";");
				String zaohtml="";
				String wuhtml="";
				String wanhtml="";
				String jiahtml="";
				for (int i=0;i<foods.length;i++){
					String foodsid=foods[i].split("_")[1];
					YhFoodCookbook2 cookbook=healthyplanservice.selectcookbook(foodsid);
					if(foods[i].split("_")[0].equals("1")){
						zaohtml+=" <div   class='txt_style1 '>"+cookbook.getfCName()+"</div>";
					}else if(foods[i].split("_")[0].equals("2")){
						wuhtml+="<div   class='txt_style1 '>"+cookbook.getfCName()+"</div>";
					}else if(foods[i].split("_")[0].equals("3")){
						wanhtml+="<div   class='txt_style1 '>"+cookbook.getfCName()+"</div>";
					}else if(foods[i].split("_")[0].equals("4")){
						jiahtml+="<div   class='type_style1 '>"+cookbook.getfCName()+"</div>";
					}

				}
				Document doc = Jsoup.parse(detals[0]);
				Element zaoclass = doc.getElementById("zao");
				zaoclass.html(zaohtml);
				Element wuclass = doc.getElementById("wu");
				wuclass.html(wuhtml);
				Element wanclass = doc.getElementById("wan");
				wanclass.html(wanhtml);
				Element jiaclass = doc.getElementById("jia");
				jiaclass.html(jiahtml);
				detal.setPlandetailContent(doc+"&&"+detals[1]);
			}
		return Result.ok(detal);
	}

		// 完成任务时
		@RequestMapping(value = "/tofinishoneajax")
		@ResponseBody
		public Result tofinishoneajax(YhUserHealthyPlanApp yhUserhealthypaln) {
			return Result.ok(healthyplanservice.tofinishoneajax(yhUserhealthypaln));
		}

		//获取计划报告（第一步）

		@RequestMapping(value = "/getreportstepone")
		@ResponseBody
		public Result getreportstepone(YhHealthyPlanApp YhHealthyPlan) {
			return Result.ok(healthyplanservice.selectyhhealthyplanbyid(YhHealthyPlan.getId()));
		}
		//获取计划报告(第二部)
		@RequestMapping(value = "/getrepotsetptwo")
		@ResponseBody
		public Result getrepotsetptwo(YhUserHealthyPlanApp YhUserHealthyPlan) {

			return Result.ok(healthyplanservice.selectreportsteptwo(YhUserHealthyPlan));
		}
		//查询当前用户是否有记录血压
		@RequestMapping(value = "/selectbloodpressurenowday")
		@ResponseBody
		public Result selectbloodpressurenowday(String userCode) {
			return Result.ok(healthyplanservice.selectbloodpressurenowday(userCode));
		}
		//查询当前用户是否有记录体重
		@RequestMapping(value = "/selectbminowday")
		@ResponseBody
		public Result selectbminowday(String userCode) {
			return Result.ok(healthyplanservice.selectbminowday(userCode));
		}
		//查询当前用户是否有记录血糖
		@RequestMapping(value = "/selectbloodsugernowday")
		@ResponseBody
		public Result selectbloodsugernowday(String userCode) {
			return Result.ok(healthyplanservice.selectbloodsugernowday(userCode));
		}
		//查询当前用户是否有记录膳食日记
		@RequestMapping(value = "/selectfoodnowday")
		@ResponseBody
				public Result selectfoodnowday(String userCode) {
			return Result.ok(healthyplanservice.selectfoodnowday(userCode));
		}

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
	private String getResultRiskStep(String level) {
		String step = null;
		if (level.contains("低")) {
			step = "450";
		} else if (level.contains("较低")) {
			step = "300";
		} else if (level.contains("正常")) {
			step = "200";
		} else if (level.contains("中等")) {
			step = "100";
		} else if (level.contains("较高")) {
			step = "-100";
		} else if (level.contains("高")){
			step = "-280";
		} else if (level.contains("患")){
			step = "-300";
		} else if (level.contains("异")) {
			step = "-50";
		} else if (level.contains("肥胖")) {
			step = "-300";
		}else if (level.contains("超重")){
			step = "-300";
		} else{
			step = "-450";
		}
		return step;
	}


	//查询当前用户是否拥有饮食套餐
	@RequestMapping(value = "/selectuserfoodbyten")
	@ResponseBody
	public Result selectuserfoodbyten(String userCode) {
		YhFoodUserScrecipe yhFoodUserScrecipe=healthyplanservice.selectIsHaveFood(userCode);
		if(null==yhFoodUserScrecipe){
			return Result.ok(null);
		}
		yhFoodUserScrecipe.setfElements("1:"+yhFoodUserScrecipe.getfElements());
		return Result.ok(yhFoodUserScrecipe);
	}
}
