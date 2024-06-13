package com.yihe.web.controller.system;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ibm.icu.math.BigDecimal;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhDiseaseAssessTotalReport2Mapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yihe.common.annotation.Log;
import com.yihe.common.enums.BusinessType;
import com.yihe.system.mapper.YhFoodCategoryWebMapper;
import com.yihe.system.mapper.YhUserFoodWeeklyWebMapper;
import com.yihe.system.service.IYhFoodUserDiaryService;
import com.yihe.system.service.YhFoodCookBookService;
import com.yihe.system.service.YhUserFoodGuideService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.service.impl.YhFoodUserDiaryServiceImpl;
import com.yihe.system.service.impl.YhSleepUserDiaryServiceImpl;
import com.yihe.system.service.impl.YhSportDiaryServiceImpl;
import com.yihe.system.service.impl.YhUserFoodWeeklyWebServiceImpl;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.ApiOperation;

import com.yihe.common.core.controller.BaseController;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.common.utils.poi.ExcelUtil;
import com.yihe.common.core.page.TableDataInfo;

/**
 * 用户膳食日志Controller
 * 
 * @author yihe
 * @date 2020-04-07
 */
@Controller
@RequestMapping("/system/diary")
public class YhFoodUserDiaryController extends BaseController {
	private String prefix = "system/diary";

	@Autowired
	private IYhFoodUserDiaryService yhFoodUserDiaryService;
	@Autowired
	YhFoodUserDiaryServiceImpl YhFoodUserDiaryServiceImpl;
	@Autowired
	YhSportDiaryServiceImpl YhSportDiaryServiceImpl;
	@Autowired
	private YhFoodCookBookService yhFoodCookBookService;
	
	@Autowired
	private YhUserFoodGuideService yhUserFoodGuideService;
	
	 @Autowired
	 YhUserFoodWeeklyWebServiceImpl YhUserFoodWeeklyWebServiceImpl;
	 
	 @Autowired
	 YhFoodCategoryWebMapper YhFoodCategoryWebMapper;
	 @Autowired
	 com.yihe.system.mapper.YhDiseaseAssessTotalReport2Mapper YhDiseaseAssessTotalReport2Mapper;

	@Autowired
	private com.yihe.system.service.impl.YhFoodSrecipeServiceImpl YhFoodSrecipeServiceImpl;

	@Autowired
	private com.yihe.system.service.impl.YhQuestionCheckJsonServiceImpl YhQuestionCheckJsonServiceImpl;
	@Autowired
	private com.yihe.system.service.impl.YhDiseaseAssessReportServiceImpl YhDiseaseAssessReportServiceImpl;

	@Autowired
	private com.yihe.system.service.impl.YhDiseaseAssessTotalReportServiceImpl YhDiseaseAssessTotalReportServiceImpl;
	
	@Autowired
	YhSleepUserDiaryServiceImpl  YhSleepUserDiaryServiceImpl;
	
	@Autowired
	YhUserReginfoService yhUserReginfoService;
	 
	
	/**
	 * 计算2个日期之间相差的  以年、月、日为单位，各自计算结果是多少
	 * 比如：2011-02-02 到  2017-03-02
	 *                                以年为单位相差为：6年
	 *                                以月为单位相差为：73个月
	 *                                以日为单位相差为：2220天
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int dayCompare(Date fromDate,Date toDate){
	    Calendar  from  =  Calendar.getInstance();
	    from.setTime(fromDate);
	    Calendar  to  =  Calendar.getInstance();
	    to.setTime(toDate);
	    //只要年月
	    int fromYear = from.get(Calendar.YEAR);
	    int fromMonth = from.get(Calendar.MONTH);

	    int toYear = to.get(Calendar.YEAR);
	    int toMonth = to.get(Calendar.MONTH);

	    int year = toYear  -  fromYear;
	    int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
	    int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
	    return year;
	}
	
	@ApiOperation(value = "膳食分析结果")
    @RequestMapping(value = "/pagodaAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> pagodaAnalysis(int id){
		Map<String,Object> map = new HashMap<String,Object>();
 		 
  	 
  	
		YhUserFoodWeeklyWeb selectByPrimaryKey = YhUserFoodWeeklyWebServiceImpl.selectByPrimaryKey(id);
		List<YhFoodCategoryWeb> findYhFoodCategoryByAnalysis = YhFoodCategoryWebMapper.findYhFoodCategoryByAnalysis();
		 for (int i = 0; i < findYhFoodCategoryByAnalysis.size(); i++) {
			 YhFoodCategoryWeb yhFoodCategoryWeb = findYhFoodCategoryByAnalysis.get(i);
			 Integer everydaymax = yhFoodCategoryWeb.getEverydaymax();
			 Integer everydaymin = yhFoodCategoryWeb.getEverydaymin();
			 Integer ecorddays = selectByPrimaryKey.getEcorddays();
			 if(yhFoodCategoryWeb.getId()==1){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType1());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType1()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==2){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType2());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType2()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==3){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType3());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType3()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==4){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType4());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType4()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==5){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType5());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType5()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==6){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType6());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType6()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==7){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType7());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType7()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==8){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType8());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType8()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==9){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType9());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType9()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==10){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType10());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType10()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==11){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType11());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType11()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==12){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType12());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType12()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
			
			}
			if(yhFoodCategoryWeb.getId()==13){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType13());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType13()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));

			}
			if(yhFoodCategoryWeb.getId()==14){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType14());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType14()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
			if(yhFoodCategoryWeb.getId()==15){
				findYhFoodCategoryByAnalysis.get(i).setValue(selectByPrimaryKey.getType15());
				findYhFoodCategoryByAnalysis.get(i).setStatus(getstatusInfo(everydaymax, everydaymin, new BigDecimal(selectByPrimaryKey.getType15()).divide(new BigDecimal(ecorddays),4,BigDecimal.ROUND_HALF_UP)));
				
			}
		}
			map.put("code", 0);
	  		map.put("msg", "");
	  		map.put("count",15);
	  		map.put("data", findYhFoodCategoryByAnalysis);
	  		return map;
		
	}
	
	
	public String getstatusInfo(Integer everydaymax,Integer everydaymin,BigDecimal value){
		if(null==everydaymax&&null==everydaymin){
			return "没有参考值";
		}
		if(null==everydaymax&&null!=everydaymin){
			if(value.compareTo(new BigDecimal(everydaymin))==1){
				return "很好";
			}else{
				return "不足";
			}
		}
		
		if(null!=everydaymax&&null==everydaymin){
			if(value.compareTo(new BigDecimal(everydaymin))==-1){
				return "很好";
			}else{
				return "过量";
			}
		}
		
		if(null!=everydaymax&&null!=everydaymin){
			if(value.compareTo(new BigDecimal(everydaymax))==-1&&value.compareTo(new BigDecimal(everydaymin))==1){
				return "很好";
			}else if(value.compareTo(new BigDecimal(everydaymax))==1){
				return "过量";
			}else if(value.compareTo(new BigDecimal(everydaymin))==-1){
				return "不足";
			}
		}
		return "";
		
	}
	
	@ApiOperation(value = "膳食分析结果")
    @RequestMapping(value = "/showRecordAnalyze", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> showRecordAnalyze(int id){
		YhUserFoodWeeklyWeb selectByPrimaryKey = YhUserFoodWeeklyWebServiceImpl.selectByPrimaryKey(id);
		YhUserReginfo yhUserReginfo = yhUserReginfoService.selectYhUserReginfoData(selectByPrimaryKey.getUserCode());
		Map<String, Object> map1=new HashMap<String, Object>();
		int nut=dayCompare(yhUserReginfo.getBirthDate(), new Date());//年龄
		yhUserReginfo.setBirthtime(String.valueOf(nut));
		List<FoodArray> array=new ArrayList<FoodArray>();
		
		String recommend = selectByPrimaryKey.getRecommend();
		if(StringUtil.isNotEmpty(recommend)){
			//2020-09-21,1733,116.0;2020-09-23,1953,116.0;2020-09-25,1953,353.91;
			String[] split = recommend.split(";");
			for (String string : split) {
				FoodArray foodArray=new FoodArray();
				String[] split2 = string.split(",");
				foodArray.setTime(split2[0]);
				foodArray.setRecommend(split2[1]);
				if(Float.valueOf(split2[2])>Float.valueOf(split2[1])){
					foodArray.setBeenoverweight(String.valueOf(split2[2]));
					foodArray.setNotoverweight("0");
				}else{
					foodArray.setBeenoverweight("0");
					foodArray.setNotoverweight(split2[2]);
				}
				array.add(foodArray);
			}
		}
		boolean tnb =false;
		YhDiseaseAssessTotalReport2web yhDiseaseAssessTotalReport2web = YhDiseaseAssessTotalReport2Mapper.selectAssessByParam(selectByPrimaryKey.getUserCode());
		if(null!=yhDiseaseAssessTotalReport2web&&null!=yhDiseaseAssessTotalReport2web.getSkNames()){
			tnb = yhDiseaseAssessTotalReport2web.getSkNames().contains("糖尿病");

		}

		//脂肪 脂肪的重量 * 9 / （碳水的重量 * 4 + 蛋白质的重量 * 4 + 脂肪的重量 * 9） * 100%
		BigDecimal fat = new BigDecimal("0");
		if(StringUtil.isNotEmpty(selectByPrimaryKey.getFat())){
			  fat = new BigDecimal(selectByPrimaryKey.getFat());
		}


		//碳水化合物 碳水的重量 * 4 / （碳水的重量 * 4 + 蛋白质的重量 * 4 + 脂肪的重量 * 9） * 100%
		BigDecimal carbohydrate = new BigDecimal("0");
		if(StringUtil.isNotEmpty(selectByPrimaryKey.getCarbohydrate())){
		    carbohydrate = new BigDecimal(selectByPrimaryKey.getCarbohydrate());
		}
		//蛋白质 蛋白质的重量 * 4 / （碳水的重量 * 4 + 蛋白质的重量 * 4 + 脂肪的重量 * 9） * 100%
		BigDecimal protein = new BigDecimal("0");
		if(StringUtil.isNotEmpty(selectByPrimaryKey.getProtein())){
			protein = new BigDecimal(selectByPrimaryKey.getProtein());
		}






		BigDecimal carbohydratemultiply = carbohydrate.multiply(new BigDecimal("4"));
		BigDecimal proteinmultiply = protein.multiply(new BigDecimal("4"));
		BigDecimal fatmultiply = fat.multiply(new BigDecimal("9"));
		BigDecimal addinfo = carbohydratemultiply.add(proteinmultiply).add(fatmultiply);


		BigDecimal fatNum = fat.multiply(new BigDecimal("9")).divide(addinfo,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));


		BigDecimal carbohydrateNum = carbohydrate.multiply(new BigDecimal("4")).divide(addinfo,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));



		BigDecimal proteinNum = protein.multiply(new BigDecimal("4")).divide(addinfo,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));


		String fatbbdf = fatNum.setScale(2, BigDecimal.ROUND_HALF_UP).toString();


		String carbohydratebbdf = carbohydrateNum.setScale(2, BigDecimal.ROUND_HALF_UP).toString();


		String pbgbdf = proteinNum.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

		selectByPrimaryKey.setFat(fatbbdf);

		selectByPrimaryKey.setCarbohydrate(carbohydratebbdf);

		selectByPrimaryKey.setProtein(pbgbdf);

		map1.put("selectByPrimaryKey", selectByPrimaryKey);
		map1.put("yhUserReginfo", yhUserReginfo);
		map1.put("array", array);
		map1.put("tnb", tnb);
		return map1;
	}
	
	
	/**
	 * 查询用户查看食物记录内容统计图
	 * @param page
	 * @param limit
	 * @return
	 */
     @ApiOperation(value = "查询用户查看食物记录内容统计图")
     @RequestMapping(value = "/selectDiaryCtime", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectDiaryCtime(String usercode,String ctime){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		map1.put("userCode", usercode);
 		if(ctime!=null  && !ctime.equals("")) {
 			String[] strArr = ctime.split(" - ");
 		map1.put("cTime", strArr[0]);
 		map1.put("cTime1", strArr[1]);
 		}
 		List<YhFoodUserDiary> list = yhFoodUserDiaryService.selectDiaryCtime(map1);
 		String[] cTime = new String[list.size()];
 		String[] energyLevel = new String[list.size()];
 		String[] energyKcal = new String[list.size()];
 		int i=0;
 		for (YhFoodUserDiary yhFoodUserDiary : list) {
 			i++;
 			cTime[i-1]=""+yhFoodUserDiary.getcTime()+"";
 			energyLevel[i-1]=""+yhFoodUserDiary.getEnergyLevel()+"";
 			energyKcal[i-1]=""+yhFoodUserDiary.getEnergyKcal()+"";
		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("cTime", cTime);
 		map.put("energyLevel", energyLevel);
 		map.put("energyKcal",energyKcal);
 		return map;
 	}
     
     
     
     /**
 	 * 查询用户查看食物分析报告
 	 * @param page
 	 * @param limit
 	 * @return
 	 */
      @ApiOperation(value = "查询用户查看食物分析报告")
      @RequestMapping(value = "/selectfoodanalyze", method = RequestMethod.POST)
  	@ResponseBody
  	public Map<String,Object> selectfoodanalyze(Integer page,Integer limit,String usercode){
  		Map<String,Object> map = new HashMap<String,Object>();
  		 
  		PageInfo<YhUserFoodWeeklyWeb> findFoodWeekLyByUserCode = YhUserFoodWeeklyWebServiceImpl.findFoodWeekLyByUserCode(usercode, page, limit);
  	 
  		map.put("code", 20000);
  		map.put("msg", "");
  		map.put("count",findFoodWeekLyByUserCode.getTotal());
  		map.put("data", findFoodWeekLyByUserCode.getList());
  		return map;
  	}
	
	
	/**
	 * 查询用户查看食物记录内容
	 * @param page
	 * @param limit
	 * @return
	 */
     @ApiOperation(value = "查询用户查看食物记录内容")
     @RequestMapping(value = "/selectDiary", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectDiary(Integer page,Integer limit,String usercode,String cTime,String reportLevel){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("userCode", usercode);
 		map1.put("reportLevel", reportLevel);
 		if(cTime!=null && !cTime.equals("")) {
 			String[] strArr = cTime.split(" - ");
 		map1.put("cTime", strArr[0]);
 		map1.put("cTime1", strArr[1]);
 		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		List<YhFoodUserDiary> list = yhFoodUserDiaryService.selectDiary(map1);
  if(null!=list){
 			for (int i = 0; i < list.size(); i++) {
				String energyKcal = list.get(i).getEnergyKcal();
				if(StringUtil.isEmpty(energyKcal)||"0".equals(energyKcal)){
					continue;
				}
				//脂肪 脂肪的重量 * 9 / （碳水的重量 * 4 + 蛋白质的重量 * 4 + 脂肪的重量 * 9） * 100%
				BigDecimal fat = new BigDecimal("0");
				if(StringUtil.isNotEmpty(list.get(i).getFat())){
					fat = new BigDecimal(list.get(i).getFat());
				}


				//碳水化合物 碳水的重量 * 4 / （碳水的重量 * 4 + 蛋白质的重量 * 4 + 脂肪的重量 * 9） * 100%
				BigDecimal carbohydrate = new BigDecimal("0");
				if(StringUtil.isNotEmpty(list.get(i).getCho())){
					carbohydrate = new BigDecimal(list.get(i).getCho());
				}
				//蛋白质 蛋白质的重量 * 4 / （碳水的重量 * 4 + 蛋白质的重量 * 4 + 脂肪的重量 * 9） * 100%
				BigDecimal protein = new BigDecimal("0");
				if(StringUtil.isNotEmpty(list.get(i).getProtein())){
					protein = new BigDecimal(list.get(i).getProtein());
				}






				BigDecimal carbohydratemultiply = carbohydrate.multiply(new BigDecimal("4"));
				BigDecimal proteinmultiply = protein.multiply(new BigDecimal("4"));
				BigDecimal fatmultiply = fat.multiply(new BigDecimal("9"));


				list.get(i).setFat(fatmultiply.toString());
				list.get(i).setCho(carbohydratemultiply.toString());
				list.get(i).setProtein(proteinmultiply.toString());

			}
 		}
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhFoodUserDiaryService.selectDiaryConut(map1));
 		map.put("data", list);
 		return map;
 	}
	
	@RequestMapping(value = "/findFoodDiaryByWeekDay", method = RequestMethod.POST)
	@ResponseBody
	public Result findFoodDiaryByWeekDay(@RequestParam(required = false) String type,@RequestParam(required = false) String usercode) {
		//本周饮食连续天数
				
		if("0".equals(type)){
			//本周
			int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDuary(usercode);
			int[] findSportDiaryByUserCodeForWeekDay = YhSportDiaryServiceImpl.findSportDiaryByUserCodeForWeekDay(usercode);
			int[] findsleepDiaryByUserCodeForWeekDay = YhSleepUserDiaryServiceImpl.findsleepDiaryByUserCodeForWeekDay(usercode);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("food", findFoodUserDuary);
			map.put("sport", findSportDiaryByUserCodeForWeekDay);
			map.put("sleep", findsleepDiaryByUserCodeForWeekDay);
			return Result.ok(map);
		}else{
			//上周
			int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDuaryLastWeek(usercode);
			int[] findSportUserDuaryLastWeek = YhSportDiaryServiceImpl.findSportUserDuaryLastWeek(usercode);
			int[] findSleepUserDuaryLastWeek = YhSleepUserDiaryServiceImpl.findSleepUserDuaryLastWeek(usercode);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("food", findFoodUserDuary);
			map.put("sport", findSportUserDuaryLastWeek);
			map.put("sleep", findSleepUserDuaryLastWeek);
			return Result.ok(map);
		}
		 
	}
	
	@RequestMapping(value = "/findFoodDiaryByMonth", method = RequestMethod.POST)
	@ResponseBody
	public Result findFoodDiaryByMonth(@RequestParam(required = false) String type,@RequestParam(required = false) String usercode) {
		 Calendar cal = Calendar.getInstance();
		 int month = cal.get(Calendar.MONTH) + 1;
	      int year = cal.get(Calendar.YEAR);
		if("0".equals(type)){
			//本月
			int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDuaryYue(usercode, String.valueOf(year), String.valueOf(month));
			int[] findSportDiaryByUserCodeForMonth = YhSportDiaryServiceImpl.findSportDiaryByUserCodeForMonth(usercode, String.valueOf(year), String.valueOf(month));
			int[] findsleepDiaryByUserCodeForMonth = YhSleepUserDiaryServiceImpl.findsleepDiaryByUserCodeForMonth(usercode, String.valueOf(year), String.valueOf(month));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("food", findFoodUserDuary);
			map.put("sport", findSportDiaryByUserCodeForMonth);
			map.put("sleep", findsleepDiaryByUserCodeForMonth);
			return Result.ok(map);
		}else{
			//上月
			int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDuaryYue(usercode, String.valueOf(getlastYear()), String.valueOf(getlastMonth()));
			int[] findSportDiaryByUserCodeForMonth = YhSportDiaryServiceImpl.findSportDiaryByUserCodeForMonth(usercode, String.valueOf(getlastYear()), String.valueOf(getlastMonth()));
			int[] findsleepDiaryByUserCodeForMonth = YhSleepUserDiaryServiceImpl.findsleepDiaryByUserCodeForMonth(usercode, String.valueOf(getlastYear()), String.valueOf(getlastMonth()));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("food", findFoodUserDuary);
			map.put("sport", findSportDiaryByUserCodeForMonth);
			map.put("sleep", findsleepDiaryByUserCodeForMonth);
			return Result.ok(map);
		}
		 
	}
	
	@RequestMapping(value = "/findFoodDiaryByYear", method = RequestMethod.POST)
	@ResponseBody
	public Result findFoodDiaryByYear(@RequestParam(required = false) String type,@RequestParam(required = false) String usercode) {
		 Calendar cal = Calendar.getInstance();
		 
	      int year = cal.get(Calendar.YEAR);
	       
		if("0".equals(type)){
			//今年
			
			int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDiaryByYear(usercode, String.valueOf(year));
			int[] findSportDiaryByuserCodeForyear = YhSportDiaryServiceImpl.findSportDiaryByuserCodeForyear(usercode, String.valueOf(year));
			int[] findsleepDiaryByuserCodeForyear = YhSleepUserDiaryServiceImpl.findsleepDiaryByuserCodeForyear(usercode, String.valueOf(year));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("food", findFoodUserDuary);
			map.put("sport", findSportDiaryByuserCodeForyear);
			map.put("sleep", findsleepDiaryByuserCodeForyear);
			return Result.ok(map);
			
			 
		}else{
			year = cal.get(Calendar.YEAR)-1;
			//去年
			int[] findFoodUserDuary = yhFoodUserDiaryService.findFoodUserDiaryByYear(usercode, String.valueOf(year));
			int[] findSportDiaryByuserCodeForyear = YhSportDiaryServiceImpl.findSportDiaryByuserCodeForyear(usercode, String.valueOf(year));
			int[] findsleepDiaryByuserCodeForyear = YhSleepUserDiaryServiceImpl.findsleepDiaryByuserCodeForyear(usercode, String.valueOf(year));
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("food", findFoodUserDuary);
			map.put("sport", findSportDiaryByuserCodeForyear);
			map.put("sleep", findsleepDiaryByuserCodeForyear);
			return Result.ok(map);
		}
		 
	}
	
	
	 
	public static void main(String[] args) {
		 Calendar cal = Calendar.getInstance();
		 
	      int year = cal.get(Calendar.YEAR);
	     
	      System.out.println(year);
	}
	 public static  int getlastMonth(){
		 Date date = new Date();
		 Calendar calendar = Calendar.getInstance();
	     calendar.setTime(date); // 设置为当前时间
	     calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
	     int month = calendar.get(Calendar.MONTH) + 1;
	     
		 return month;
	 }

	 public static  int getlastYear(){
		 Date date = new Date();
		 Calendar calendar = Calendar.getInstance();
	     calendar.setTime(date); // 设置为当前时间
	     calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
	      int year = calendar.get(Calendar.YEAR);
	     date = calendar.getTime();
	     
		 return year;
	 }
	  
	@RequiresPermissions("system:diary:view")
	@GetMapping()
	public String diary() {
		return prefix + "/diary";
	}
	


	@RequestMapping(value = "/findFoodDiaryInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result healthAssessment2(@RequestParam(required = false) String nameLike,
			@RequestParam(required = false) String usernameLike, @RequestParam(required = false) String type,
			@RequestParam(required = false) String strTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String standard,

			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {

		if (StringUtil.isNotEmpty(startTime)) {
			String[] split = startTime.split(" - ");
			strTime = split[0];
			endTime = split[1];
		}
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		PageInfo<YHUserFoodDiaryVOJO> findUserFoodDiaryInfo = yhFoodUserDiaryService.findUserFoodDiaryInfo(standard,usercode,
				nameLike, usernameLike, strTime, endTime, page, limit);
		List<YHUserFoodDiaryVOJO> list = findUserFoodDiaryInfo.getList();
		for (YHUserFoodDiaryVOJO yhUserFoodDiaryVOJO : list) {
			// 10_1_2834_245.0,90342_120.0,90472_75.0,91201_50;10_1_2841_100,3971_20.0,90066_80.0;
			String felements = yhUserFoodDiaryVOJO.getFelements();
			Float zaoKca = 0f;
			Float jiaKca = 0f;
			Float wuKca = 0f;
			Float wanKca = 0f;
			// 13为未评估通用
			if (StringUtil.isNotEmpty(felements)) {
				// // 10_1_2834_245.0,90342_120.0,90472_75.0,91201_50
				String[] split = felements.split(";");
				for (String string : split) {
					// 1
					String[] split2 = string.split("_");
					String foodCaiyao = split2[0];
					String foodType = split2[1];
					if("10".equals(foodCaiyao)){
						String[] split3 = string.split(",");
						for (int i = 1; i < split3.length; i++) {
							
							String string2 = split3[i].split("_")[0];
							YhFoodCookBook book = yhFoodCookBookService.selectByPrimaryKey(Integer.valueOf(string2));
							if(null==book){
								System.out.println("-----*****************----------");
								System.out.println("-----*****************----------");
								System.out.println("-----*****************----------");
								System.out.println("-----*****************----------");
								System.out.println("foodid为空----------");
								System.out.println("foodid为空----------"+Integer.valueOf(string2));
								System.out.println("foodid为空----------");
								System.out.println("-----*****************----------");
								System.out.println("-----*****************----------");
								System.out.println("-----*****************----------");
								continue;
							}
							
							
						 String string3 = split3[i].split("_")[1];
							Float energyKcal100 = book.getEnergyKcal();// 每一百克食物热量
							Float energyKcal1 = energyKcal100 / 100 * Float.valueOf(string3);
							if ("1".equals(foodType)) {
								zaoKca = zaoKca + energyKcal1;
							}
							if ("2".equals(foodType)) {
								wuKca = wuKca + energyKcal1;
								
							}
							if ("3".equals(foodType)) {
								wanKca = wanKca + energyKcal1;
							}
							if ("4".equals(foodType)) {
								jiaKca = jiaKca + energyKcal1;
							}
						}
					}else{
						if(split2.length==3){
							continue;
						}
						String foodID = split2[2];
						String foodweight = split2[3];
						float foodweightInt = Float.valueOf(foodweight);

						// foodType:1:早2:加3:午4:晚
						if (null != foodType) {
							YhFoodCookBook book = yhFoodCookBookService.selectByPrimaryKey(Integer.valueOf(foodID));
							if(null==book){
								System.out.println("-----*****************----------");
								System.out.println("-----*****************----------");
								System.out.println("-----*****************----------");
								System.out.println("-----*****************----------");
								System.out.println("foodid为空----------");
								System.out.println("foodid为空----------"+Integer.valueOf(foodID));
								System.out.println("foodid为空----------");
								System.out.println("-----*****************----------");
								System.out.println("-----*****************----------");
								System.out.println("-----*****************----------");
								continue;
							}
							Float energyKcal100 = book.getEnergyKcal();// 每一百克食物热量
							Float energyKcal1 = energyKcal100 / 100 * foodweightInt;
							if ("1".equals(foodType)) {
								zaoKca = zaoKca + energyKcal1;
							}
							if ("2".equals(foodType)) {
								wuKca = wuKca + energyKcal1;
								
							}
							if ("3".equals(foodType)) {
								wanKca = wanKca + energyKcal1;
							}
							if ("4".equals(foodType)) {
								jiaKca = jiaKca + energyKcal1;
							}
						}
					}
					
				
				}
				Float allInfoNum = zaoKca + jiaKca + wuKca + wanKca;// 一天的热量日记
				Float energyLevel = yhUserFoodDiaryVOJO.getEnergyLevel();
				if(allInfoNum>energyLevel){
					yhUserFoodDiaryVOJO.setWhether("是");
				}else{
					yhUserFoodDiaryVOJO.setWhether("否");
				}
				
				
				DecimalFormat zaoKcanum = new DecimalFormat("##0.00");
				String zao = zaoKcanum.format(zaoKca);
				yhUserFoodDiaryVOJO.setZao(zao);
				DecimalFormat jiaKcanum = new DecimalFormat("##0.00");
				String jia = jiaKcanum.format(jiaKca);
				yhUserFoodDiaryVOJO.setJia(jia);
				DecimalFormat wuKcanum = new DecimalFormat("##0.00");
				String wu = wuKcanum.format(wuKca);
				yhUserFoodDiaryVOJO.setZhong(wu);
				DecimalFormat wanKcanum = new DecimalFormat("##0.00");
				String wan = wanKcanum.format(wanKca);
				yhUserFoodDiaryVOJO.setWan(wan);
			}

		}
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", findUserFoodDiaryInfo.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}

	/**
	 * 查询用户膳食日志列表
	 */
	@RequiresPermissions("system:diary:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(YhFoodUserDiary yhFoodUserDiary) {
		startPage();
		List<YhFoodUserDiary> list = yhFoodUserDiaryService.selectYhFoodUserDiaryList(yhFoodUserDiary);
		return getDataTable(list);
	}

	/**
	 * 导出用户膳食日志列表
	 */
	@RequiresPermissions("system:diary:export")
	@Log(title = "用户膳食日志", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(YhFoodUserDiary yhFoodUserDiary) {
		List<YhFoodUserDiary> list = yhFoodUserDiaryService.selectYhFoodUserDiaryList(yhFoodUserDiary);
		ExcelUtil<YhFoodUserDiary> util = new ExcelUtil<YhFoodUserDiary>(YhFoodUserDiary.class);
		return util.exportExcel(list, "diary");
	}

	/**
	 * 新增用户膳食日志
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存用户膳食日志
	 */
	@RequiresPermissions("system:diary:add")
	@Log(title = "用户膳食日志", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(YhFoodUserDiary yhFoodUserDiary) {
		return toAjax(yhFoodUserDiaryService.insertYhFoodUserDiary(yhFoodUserDiary));
	}

	/**
	 * 修改用户膳食日志
	 */
	@GetMapping("/edit/{uFdId}")
	public String edit(@PathVariable("uFdId") Long uFdId, ModelMap mmap) {
		YhFoodUserDiary yhFoodUserDiary = yhFoodUserDiaryService.selectYhFoodUserDiaryById(uFdId);
		mmap.put("yhFoodUserDiary", yhFoodUserDiary);
		return prefix + "/edit";
	}

	/**
	 * 修改保存用户膳食日志
	 */
	@RequiresPermissions("system:diary:edit")
	@Log(title = "用户膳食日志", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(YhFoodUserDiary yhFoodUserDiary) {
		return toAjax(yhFoodUserDiaryService.updateYhFoodUserDiary(yhFoodUserDiary));
	}

	/**
	 * 删除用户膳食日志
	 */
	@RequiresPermissions("system:diary:remove")
	@Log(title = "用户膳食日志", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(yhFoodUserDiaryService.deleteYhFoodUserDiaryByIds(ids));
	}
}
