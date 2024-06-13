package com.yihe.applet.api.controller;

import com.alibaba.fastjson.JSON;
import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.service.*;
import com.yihe.applet.api.util.common.util.DateTest;
import com.yihe.applet.api.util.common.util.Result;
import com.yihe.common.utils.SleepScoreUtil;
import com.yihe.system.domain.YhJzServiceBodydataSwitch;
import com.yihe.system.domain.YhManageTask;
import com.yihe.system.service.YhTaskServiceAPIService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * @version: shayu3.0
 * @Description: 睡眠模块
 * @author: lhh
 * @date: 2020年4月15日 上午11:04:28
 */ 
@RestController
@RequestMapping("/api/YhSleepModule")
public class YhSleepModuleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserReginfoController.class);
	@Autowired
	private YhSleepMusicAppService yhSleepMusicAppService;
	@Autowired
	private YhSleepQuestionAppService yhSleepQuestionAppService;
	@Autowired
	private YhSleepReportAppService yhSleepReportAppService;
	@Autowired
	private YhSleepUserDiaryAppService yhSleepUserDiaryAppService;
	@Autowired
	private YhSleepUserMusiclistAppService yhSleepUserMusiclistAppService;
	@Autowired
	private YhSleepMusicMealAppService yhSleepMusicMealAppService;
	
	@Resource
    YhTaskServiceAPIService yhtaskservuceapiservice;     

	/*	@RequestMapping("/text")
		public String text() {
			YhSleepUserDiaryApp dia=new YhSleepUserDiaryApp();
		     dia.setEntryTime("2020-07-17");
		     dia.setUweight("224");
		     dia.setTenergy("388.92");
		     dia.setDiaryDetails("36=5.6=14个;637=354.92=76分;639=14.4=20秒;617=12.6=29秒;");
		     dia.setRemark("140");
		     dia.setNumber("2328=1.3968");
		     for (int i = 10016; i < 10131; i++) {
		   System.out.println(i);
		   dia.setUserCode(String.valueOf(i));
		   yhSportDiaryService.insertYhSportDiary(dia);
		  }
			return "进入测试";
		}*/

	/*
	 * 首页睡眠模块
	 */
	@RequestMapping("/pageHomeSleep")
	public Result pageHomeSleep(String usercode) {
		 Map<String, Object> map=new HashMap<String, Object>();
		 String sleepTimeStr="";
		 String wakeTimeStr="";
		 String info="--";
		 String num="0";
		//连续天数
		Integer findUserSleepLXNum = yhSleepUserDiaryAppService.findUserSleepLXNum(usercode);
		if(null==findUserSleepLXNum){
			findUserSleepLXNum=0;
		}
		//记录天数
		Integer findSleepByuserCodeCount = yhSleepUserDiaryAppService.findSleepByuserCodeCount(usercode);
		//查询今天睡眠信息
		YhSleepUserDiaryApp findTodaySleepInfo = yhSleepUserDiaryAppService.findTodaySleepInfo(usercode);
		if(null!=findTodaySleepInfo){
			Date sleepTime = findTodaySleepInfo.getSleepTime();
			Date wakeTime = findTodaySleepInfo.getWakeTime();
			//睡眠时长>9h：睡多了 
			//6~9h：良好
			//<6h：不足
			
			num = DateTest.dateDiff(sleepTime, wakeTime);
			  info = DateTest.dateDiffJisuan(sleepTime, wakeTime);
			   SimpleDateFormat sdf =new SimpleDateFormat("HH:mm:ss" );
		        
		         sleepTimeStr = sdf.format(sleepTime);
		         wakeTimeStr = sdf.format(wakeTime);

		}
		
		 
       
        map.put("sleepTime", sleepTimeStr);
        map.put("wakeTime", wakeTimeStr);
        map.put("num", num);
        map.put("info", info);
        map.put("lxNum", findUserSleepLXNum);
        map.put("jlNum", findSleepByuserCodeCount);
		return Result.ok(map);
	}
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间

		String nowtime = d.format(new Date());// 按以上格式 将当前时间转换成字符串

		System.out.println("当前时间：" + nowtime);
		String testtime = "2020-08-20 14:07:35";// 测试时间
		System.out.println("测试时间：" + testtime);

		

	}
	/**
	 * 查询用户当天的睡眠记录
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/selectUserIntradayData")
	public Result selectUserIntradayData(String userCode) {
		YhSleepUserDiaryApp selectlastmonthData = yhSleepUserDiaryAppService.selectUserIntradayData(userCode);
		return Result.ok(selectlastmonthData);
		
	}
	
	/**
	 * 根据睡眠记录和code查询
	 * @param userCode
	 * @param sleepDiarytime
	 * @return
	 */
	@RequestMapping("/selectsleepByParam")
	public Result selectsleepByParam(String userCode,String sleepDiarytime) {
		YhSleepUserDiaryApp selectsleepByParam = yhSleepUserDiaryAppService.selectsleepByParam(userCode, sleepDiarytime);
		return Result.ok(selectsleepByParam);
		
	}
	
	/*
	 * 记录睡眠模块
	 */
	@RequestMapping("/recordSleep")
	public Result recordSleep(String usercode,Date sleepDiarytime) throws ParseException {
		 Map<String, Object> map=new HashMap<String, Object>();
		 String sleepTimeStr="";
		 String wakeTimeStr="";
		 String info="--";
		 String num="0";
		
		//平均睡眠时长
		 String average = "";
		 long subtract=0;
		 String sleeptime ="";
		 SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		
		//获取String类型的时间
		if(sleepDiarytime!=null) {
		 //Date date = sd.parse(sleepDiarytime);
		  sleeptime = sd.format(sleepDiarytime);
		}
		
			 //获取当前月份的天数
			  Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		   int day=aCalendar.getActualMaximum(Calendar.DATE);
		   
		
		//连续天数
		Integer findUserSleepLXNum = yhSleepUserDiaryAppService.findUserSleepLXNum2(usercode);
		if(null==findUserSleepLXNum){
			findUserSleepLXNum=0;
		}
		//记录天数
		Integer findSleepByuserCodeCount = yhSleepUserDiaryAppService.findSleepByuserCodeCount(usercode);
		//查询今天睡眠信息
		YhSleepUserDiaryApp findTodaySleepInfo = yhSleepUserDiaryAppService.findTodaySleepInfo2(usercode,sleeptime);
		if(null!=findTodaySleepInfo){
			Date sleepTime = findTodaySleepInfo.getSleepTime();
			Date wakeTime = findTodaySleepInfo.getWakeTime();
			//睡眠时长>9h：睡多了 
			//6~9h：良好
			//<6h：不足
			
			num = DateTest.dateDiff2(sleepTime, wakeTime);
			  info = DateTest.dateDiffJisuan(sleepTime, wakeTime);
			   SimpleDateFormat sdf =new SimpleDateFormat("HH:mm" );
		        
		         sleepTimeStr = sdf.format(sleepTime);
		         wakeTimeStr = sdf.format(wakeTime);

		}
		
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
		 List<YhSleepUserDiaryApp> monthData = yhSleepUserDiaryAppService.selectThisMonthData(usercode);
		 long times=0;
		 if(monthData.size()>0) {
			 for (int i = 0; i < monthData.size(); i++) {
				 Date sleepTime2 = monthData.get(i).getSleepTime();
				 Date wakeTime2 = monthData.get(i).getWakeTime();
				
				long result = (wakeTime2.getTime() - sleepTime2.getTime()) / 3600000;// 当前时间减去测试时间
				times += result;
				System.out.println("当前时间减去测试时间=" + result + "小时");
			       
			}
			
			//平均睡眠小时
				subtract = times / monthData.size();
				System.out.println("月平均睡眠时间"+subtract);
		 }
		
		
		 
       
        map.put("sleepTime", sleepTimeStr);
        map.put("wakeTime", wakeTimeStr);
        map.put("num", num);
        map.put("info", info);
        map.put("lxNum", findUserSleepLXNum);
        map.put("jlNum", monthData.size());
        map.put("subtract", subtract);
        map.put("times", times);
		return Result.ok(map);
	}

	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List lDate = new ArrayList();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	public static List<String> getTimeInterval() throws ParseException {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		// System.out.println("所在周星期日的日期：" + imptimeEnd);
		Date dBegin = sdf.parse(imptimeBegin);
		Date dEnd = sdf.parse(imptimeEnd);
		List<Date> lDate = findDates(dBegin, dEnd);// 获取这周所有date
		sdf = new SimpleDateFormat("MM-dd");
		for (Date date : lDate) {
			list.add(sdf.format(date));

		}
		return list;
	}
	
	public static List<String> getLastTimeInterval() throws ParseException {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		calendar2.add(Calendar.DATE, offset2 - 7);
		// System.out.println(sdf.format(calendar1.getTime()));// last Monday
		String lastBeginDate = sdf.format(calendar1.getTime());
		// System.out.println(sdf.format(calendar2.getTime()));// last Sunday
		String lastEndDate = sdf.format(calendar2.getTime());
		Date dBegin = sdf.parse(lastBeginDate);
		Date dEnd = sdf.parse(lastEndDate);
		List<Date> lDate = findDates(dBegin, dEnd);// 获取这周所有date
		sdf = new SimpleDateFormat("MM-dd");
		for (Date date : lDate) {
			list.add(sdf.format(date));

		}
		return list;
	}
	
	public static int getlastMonth() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		int month = calendar.get(Calendar.MONTH) + 1;

		return month;
	}

	public static int getlastYear() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		int year = calendar.get(Calendar.YEAR);
		date = calendar.getTime();

		return year;
	}
	
	@RequestMapping("/weeksMonthsyears")
	public Result weeksMonthsyears (String userCode,String type) throws ParseException {
		 Map<String, Object> map=new HashMap<String, Object>();
		long times=0;
		String subtract ="";
		int num = 0;
		//睡眠时长>9h：睡多了 
		int sleepMore = 0;
		//6~9h：良好
		int good = 0;
		//<6h：不足
		int insufficient = 0;
		if("上周".equals(type)) {
			//上周
			int[] findSleepastWeekUserData = yhSleepUserDiaryAppService.findSleepastWeekUserData(userCode);
			List<String> lastTimeInterval = getLastTimeInterval();
			List<YhSleepUserDiaryApp> selectlastweekData = yhSleepUserDiaryAppService.selectlastweekData(userCode);
			
				if(selectlastweekData.size()>0) {
					num = selectlastweekData.size();
					 for (int i = 0; i < selectlastweekData.size(); i++) {
						 Date lastmonthSleepTime = selectlastweekData.get(i).getSleepTime();
						 Date lastmonthWakeTime = selectlastweekData.get(i).getWakeTime();
						long result = (lastmonthWakeTime.getTime() - lastmonthSleepTime.getTime()) / 3600000;// 醒来时间减去入睡
						if(result>9) {
							sleepMore++;
						}else if(result>=6 && result<=9){
							good++;
						}else if(result<6) {
							insufficient++;
						}
						times += result;
						System.out.println("入睡时间减去醒来时间=" + result + "小时");
					}
					//平均睡眠小时
					 DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
				     subtract = df.format((float)times/selectlastweekData.size());
						System.out.println("上月平均睡眠时间"+subtract);
				}
				map.put("timeList", lastTimeInterval);
				map.put("infoList", findSleepastWeekUserData);
				map.put("times", times);
				map.put("subtract", subtract);
				map.put("quantity", num);
				map.put("sleepMore", sleepMore);
				map.put("good", good);
				map.put("insufficient", insufficient);
				
				
		}
		if("本周".equals(type)) {
			//本周
			int[] findSleepUserDuary = yhSleepUserDiaryAppService.findSleepUserDuary(userCode);
			List<String> timeInterval = getTimeInterval();
			List<YhSleepUserDiaryApp> thisweekData = yhSleepUserDiaryAppService.selectthisweekData(userCode);
				if(thisweekData.size()>0) {
					num = thisweekData.size();
					 for (int i = 0; i < thisweekData.size(); i++) {
						 Date lastmonthSleepTime = thisweekData.get(i).getSleepTime();
						 Date lastmonthWakeTime = thisweekData.get(i).getWakeTime();
						long result = (lastmonthWakeTime.getTime() - lastmonthSleepTime.getTime()) / 3600000;// 当前时间减去测试时间
						if(result>9) {
							sleepMore++;
						}else if(result>=6 && result<=9){
							good++;
						}else if(result<6) {
							insufficient++;
						}
						times += result;
						
					}
					//平均睡眠小时
					 DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
				     subtract = df.format((float)times/thisweekData.size());
						
				}
				map.put("times", times);
				map.put("subtract", subtract);
				map.put("quantity", num);
				map.put("infoList", findSleepUserDuary);
				map.put("timeList", timeInterval);
				map.put("sleepMore", sleepMore);
				map.put("good", good);
				map.put("insufficient", insufficient);
			}
		
				//本月数据
				if("本月".equals(type)) {
					Calendar cal = Calendar.getInstance();
					int month = cal.get(Calendar.MONTH) + 1;
					int year = cal.get(Calendar.YEAR);
					int[] findSleepUserDuaryYue = yhSleepUserDiaryAppService.findSleepUserDuaryYue(userCode,String.valueOf(year),String.valueOf(month));
					String usercode = userCode;
					List<YhSleepUserDiaryApp> thisweekData = yhSleepUserDiaryAppService.selectThisMonthData(usercode);
					if(thisweekData.size()>0) {
						num = thisweekData.size();
						 for (int i = 0; i < thisweekData.size(); i++) {
							 Date lastmonthSleepTime = thisweekData.get(i).getSleepTime();
							 Date lastmonthWakeTime = thisweekData.get(i).getWakeTime();
							long result = (lastmonthWakeTime.getTime() - lastmonthSleepTime.getTime()) / 3600000;// 当前时间减去测试时间
							if(result>9) {
								sleepMore++;
							}else if(result>=6 && result<=9){
								good++;
							}else if(result<6) {
								insufficient++;
							}
							times += result;
							
						}
						//平均睡眠小时
						 DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
					     subtract = df.format((float)times/thisweekData.size());
							
					}
					int[] dayNum = new int[31];
					for (int i = 1; i <= 31; i++) {
						dayNum[i - 1] = i;
					}
					map.put("times", times);
					map.put("subtract", subtract);
					map.put("quantity", num);
					map.put("infoList", findSleepUserDuaryYue);
					map.put("timeList", dayNum);
					map.put("sleepMore", sleepMore);
					map.put("good", good);
					map.put("insufficient", insufficient);
				
				}
		
		//上月数据
		if("上月".equals(type)) {
			// 上月
			int[] findFoodUserDuary = yhSleepUserDiaryAppService.findSleepUserDuaryYue(userCode,
					String.valueOf(getlastYear()), String.valueOf(getlastMonth()));
			List<YhSleepUserDiaryApp> selectlastmonthData = yhSleepUserDiaryAppService.selectlastmonthData(userCode);
			 if(selectlastmonthData.size()>0) {
				 num = selectlastmonthData.size();
				 for (int i = 0; i < selectlastmonthData.size(); i++) {
					 Date lastmonthSleepTime = selectlastmonthData.get(i).getSleepTime();
					 Date lastmonthWakeTime = selectlastmonthData.get(i).getWakeTime();
					// 当前时间减去测试时间
					 long result = (lastmonthWakeTime.getTime() - lastmonthSleepTime.getTime()) / 3600000;
					
					if(result>9) {
						sleepMore++;
					}else if(result>=6 && result<=9){
						good++;
					}else if(result<6) {
						insufficient++;
					}
					times += result;
					
				}
				//平均睡眠小时
				 DecimalFormat df=new DecimalFormat("0.0");//设置保留位数
			     subtract = df.format((float)times/selectlastmonthData.size());
					
					
			 }
				int[] dayNum = new int[31];
				for (int i = 1; i <= 31; i++) {
					dayNum[i - 1] = i;
				}
				map.put("times", times);
				map.put("subtract", subtract);
				map.put("quantity", num);
				map.put("infoList", findFoodUserDuary);
				map.put("timeList", dayNum);
				map.put("sleepMore", sleepMore);
				map.put("good", good);
				map.put("insufficient", insufficient);
			
		}
		if("本年".equals(type)) {
			//今年
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int[] findSleepUserDiaryByYear = yhSleepUserDiaryAppService.findSleepUserDiaryByYear(userCode, String.valueOf(year));
			List<YhSleepUserDiaryApp> selectthisyearData = yhSleepUserDiaryAppService.selectthisyearData(userCode);
			 if(selectthisyearData.size()>0) {
				 num = selectthisyearData.size();
				 for (int i = 0; i < selectthisyearData.size(); i++) {
					 Date lastmonthSleepTime = selectthisyearData.get(i).getSleepTime();
					 Date lastmonthWakeTime = selectthisyearData.get(i).getWakeTime();
					long result = (lastmonthWakeTime.getTime() - lastmonthSleepTime.getTime()) / 3600000;// 当前时间减去测试时间
					if(result>9) {
						sleepMore++;
					}else if(result>=6 && result<=9){
						good++;
					}else if(result<6) {
						insufficient++;
					}
					times += result;
					
				}
				//平均睡眠小时
				    DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
				     subtract = df.format((float)times/selectthisyearData.size());
					
			 }
			 int[] dayNum = new int[12];
				for (int i = 1; i <= 12; i++) {
					dayNum[i - 1] = i;
				}
				map.put("times", times);
				map.put("subtract", subtract);
				map.put("quantity", num);
				map.put("infoList", findSleepUserDiaryByYear);
				map.put("timeList", dayNum);
				map.put("sleepMore", sleepMore);
				map.put("good", good);
				map.put("insufficient", insufficient);
		}
		
		if("去年".equals(type)) {
			//去年
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR) - 1;
			int[] findSleepUserDiaryByYear = yhSleepUserDiaryAppService.findSleepUserDiaryByYear(userCode, String.valueOf(year));
			List<YhSleepUserDiaryApp> selectlastyearData = yhSleepUserDiaryAppService.selectlastyearData(userCode);
			 if(selectlastyearData.size()>0) {
				 num = selectlastyearData.size();
				 for (int i = 0; i < selectlastyearData.size(); i++) {
					 Date lastmonthSleepTime = selectlastyearData.get(i).getSleepTime();
					 Date lastmonthWakeTime = selectlastyearData.get(i).getWakeTime();
					long result = (lastmonthWakeTime.getTime() - lastmonthSleepTime.getTime()) / 3600000;// 当前时间减去测试时间
					if(result>9) {
						sleepMore++;
					}else if(result>=6 && result<=9){
						good++;
					}else if(result<6) {
						insufficient++;
					}
					times += result;
					
				}
				//平均睡眠小时
				    DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
				     subtract = df.format((float)times/selectlastyearData.size());
					
			 }
			 int[] dayNum = new int[12];
				for (int i = 1; i <= 12; i++) {
					dayNum[i - 1] = i;
				}
				map.put("times", times);
				map.put("subtract", subtract);
				map.put("quantity", num);
				map.put("infoList", findSleepUserDiaryByYear);
				map.put("timeList", dayNum);
				map.put("sleepMore", sleepMore);
				map.put("good", good);
				map.put("insufficient", insufficient);
			
		}
		
		return Result.ok(map);
		
	}
	
	/*
	 * 生成问卷
	 */
	@RequestMapping("/getAllQuestion")
	public Result getAllQuestion() {
		List<YhSleepQuestionApp> list = yhSleepQuestionAppService.selAllQuestion();
		return Result.ok(list);
	}

	/*
	 * 得到问卷调用工具评估睡眠情况
	 */
	@RequestMapping("/getSleepResult")
	public Result getSleepResult(String resultList, String userCode) throws ParseException {
		LinkedHashMap<String, Object> assessmentRes = new LinkedHashMap<String, Object>();
		assessmentRes = SleepScoreUtil.querySleepScore(resultList);
		YhSleepReportApp yhSleepReport = new YhSleepReportApp();
		yhSleepReport.setQtime(new Date());
		yhSleepReport.setReportDesc(JSON.toJSONString(assessmentRes.get("data")));
		yhSleepReport.setReportLevel(assessmentRes.get("sleepLevel").toString());
		yhSleepReport.setReportQueselect(resultList);
		yhSleepReport.setReportScore(Integer.parseInt(assessmentRes.get("score").toString()));
		yhSleepReport.setUserCode(userCode);
		int res = yhSleepReportAppService.addAssessmentResult(yhSleepReport);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String createdate = sdf.format(date);
        YhJzServiceBodydataSwitch ok=yhtaskservuceapiservice.queryswitchbyid1(7,2, "睡眠质量评估");
        if(ok.getEvery_switch()==1) {
            YhManageTask task=new YhManageTask();
            task.setUsercode(userCode);
            Date d = null;
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sd.parse(createdate);
            long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); 
            String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
            task.setService_waiting_time(newtime);
            task.setService_type(7);
            task.setService_content(ok.getService_content());
            task.setService_record("完成睡眠质量评估");
            task.setReturnvisit(ok.getReturnvisit());
            task.setChildnodes(ok.getId());
            task.setChildtag("2");
            yhtaskservuceapiservice.insertperson(task);
        }
        
		String json = JSON.toJSONString(yhSleepReport);
		if (res > 0) {
			return Result.ok(json);
		} else {
			return null;
		}
	}

	/*
	 * 查询某用户所有评估记录
	 */
	@RequestMapping("/selAllReportByUser")
	public Result selAllReportByUser(String userCode) {
		YhSleepReportApp yhSleepReport = new YhSleepReportApp();
		yhSleepReport.setUserCode(userCode);
		return Result.ok(yhSleepReportAppService.selAllReportByUser(yhSleepReport));
	}

	/*
	 * 查询某用户某条评估记录
	 */
	@RequestMapping("/selOneReportByUser")
	public Result selOneReportByUser(String userCode, int reportId) {
		YhSleepReportApp yhSleepReport = new YhSleepReportApp();
		yhSleepReport.setUserCode(userCode);
		yhSleepReport.setReportId(reportId);
		return Result.ok(JSON.toJSONString(yhSleepReportAppService.selOneReportByUser(yhSleepReport)));
	}

	/*
	 * 查询评估条数
	 */
	@RequestMapping("/selReportCount")
	public Result selReportCount() {
		return Result.ok(yhSleepReportAppService.selReportCount());
	}

	/*
	 * 添加用户睡眠记录  
	 */
	@RequestMapping("/addUserDiaryReport")
	public Result addUserDiaryReport(YhSleepUserDiaryApp yhSleepUserDiary)  {
		try {
			
			/*
			 * String time = yhSleepUserDiary.getSleeptime();
			 * System.out.println("时间戳"+time); SimpleDateFormat sdf=new
			 * SimpleDateFormat("yyyy-MM-dd"); String sd = sdf.format(new
			 * Date(Long.parseLong(String.valueOf(time)))); // 时间戳转换成时间
			 * System.out.println("格式化结果：" + sd); yhSleepUserDiary.setSleeptime(sd);
			 */
			 
			
			
	        return Result.ok(yhSleepUserDiaryAppService.addUserDiaryReport(yhSleepUserDiary));
		} catch (Exception e) {
			LOGGER.error("添加用户睡眠异常,入参为:{},原因是:", yhSleepUserDiary,e);
			return Result.error();
		}
		
		
	}
	
	/*
	 * public static void main(String[] args){ Long timeStamp =
	 * System.currentTimeMillis(); //获取当前时间戳
	 * 
	 * 
	 * SimpleDateFormat sdf2 = new
	 * SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 时 mm 分 ss 秒"); String sd2 =
	 * sdf2.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
	 * System.out.println("格式化结果：" + sd2); }
	 */
	/*
	 * 根据日期查询用户睡眠记录
	 */
	@RequestMapping("/selUserDiaryByDate")
	public Result selUserDiaryByDate(YhSleepUserDiaryApp yhSleepUserDiary) {
		return Result.ok(yhSleepUserDiaryAppService.selUserDiaryByDate(yhSleepUserDiary));
	}

	/*
	 * 根据用户code查询所有记录
	 */
	@RequestMapping("/selAllDiaryByUser")
	public Result selAllDiaryByUser(String userCode) {
		return Result.ok(yhSleepUserDiaryAppService.selAllDiaryByUser(userCode));
	}
	
	/*
	 * 查询连续天数
	 */
	@RequestMapping("/selContinueDays")
	public Result selContinueDays(String userCode) {
		return Result.ok(yhSleepUserDiaryAppService.selContinueDays(userCode));
	}
	
	/*
	 * 根据时间区间查询用户记录
	 */
	@RequestMapping("/selSleepDiaryByTimeAndCode")
	public Result selSleepDiaryByTimeAndCode(String userCode, String startDiaryT, String endDiaryT) {
		return Result.ok(yhSleepUserDiaryAppService.selSleepDiaryByTimeAndCode(userCode, startDiaryT, endDiaryT));
	}
	/*
	 * 根据用户code更改用户记录
	 */
	@RequestMapping("/uptSleepDiaryByCode")
	public Result uptSleepDiaryByCode(YhSleepUserDiaryApp yhSleepUserDiary) {
		/*
		 * String time = yhSleepUserDiary.getSleeptime();
		 * System.out.println("时间戳"+time); SimpleDateFormat sdf=new
		 * SimpleDateFormat("yyyy-MM-dd"); String sd = sdf.format(new
		 * Date(Long.parseLong(String.valueOf(time)))); // 时间戳转换成时间
		 * System.out.println("格式化结果：" + sd); yhSleepUserDiary.setSleeptime(sd);
		 */
		return Result.ok(yhSleepUserDiaryAppService.uptSleepDiaryByCode(yhSleepUserDiary));
	}
	/*
	 * 得到所有音乐
	 */
	@RequestMapping("/getAllMusic")
	public Result getAllMusic() {
		List<YhSleepMusicApp> list = yhSleepMusicAppService.selAllMusic();
		return Result.ok(list);
	}
	
	
	@RequestMapping("/getTypeAllMusic")
	public Result getTypeAllMusic(String mealTitle) {
		YhSleepMusicMealApp musicMeal = yhSleepMusicMealAppService.selTypeMusicMeal(mealTitle);
		//把数组转成list
		List<Integer> ids = Arrays.stream(musicMeal.getMusicTds().split(",")).map(Integer::parseInt).collect(Collectors.toList());
		List<YhSleepMusicApp> list = yhSleepMusicAppService.selAllMusicByIds(ids);
		return Result.ok(list);
	}

	/*
	 * 添加用户需要播放的助眠音
	 */
	@RequestMapping("/addAidList")
	public Result addAidList(YhSleepUserMusiclistApp yhSleepUserMusiclist) {
		return Result.ok(yhSleepUserMusiclistAppService.addAidList(yhSleepUserMusiclist));
	}

	/*
	 * 查询用户需要播放的助眠音
	 */
	@RequestMapping("/selAidListByUserCode")
	public Result selAidListByUserCode(String userCode) {
		YhSleepUserMusiclistApp yhSleepUserMusiclist = new YhSleepUserMusiclistApp();
		yhSleepUserMusiclist = yhSleepUserMusiclistAppService.selAidListByUserCode(userCode);
		if (yhSleepUserMusiclist == null) {
			return Result.ok(null);
		} else {
			if (yhSleepUserMusiclist.getMusicId().equals("")) {
				return Result.ok(null);
			} else {
				String aidList = yhSleepUserMusiclistAppService.selAidListByUserCode(userCode).getMusicId();
				String[] musicIdList = aidList.split(",");
				List<YhSleepMusicApp> musicList = new ArrayList<YhSleepMusicApp>();
				for (int i = 0; i < musicIdList.length; i++) {
					YhSleepMusicApp yhSleepMusic = new YhSleepMusicApp();
					yhSleepMusic = yhSleepMusicAppService.selMusicById(Integer.parseInt(musicIdList[i]));
					if (yhSleepMusic != null) {
						musicList.add(yhSleepMusic);
					}
				}
				return Result.ok(musicList);
			}
		}
	}

	/*
	 * 修改用户需要播放的助眠音
	 */
	@RequestMapping("/updAidList")
	public Result updAidList(YhSleepUserMusiclistApp yhSleepUserMusiclist) {
		return Result.ok(yhSleepUserMusiclistAppService.updAidList(yhSleepUserMusiclist));
	}

	/*
	 * 删除用户需要播放的助眠音
	 */
	@RequestMapping("/delAidList")
	public Result delAidList(YhSleepUserMusiclistApp yhSleepUserMusiclist) {
		return Result.ok(yhSleepUserMusiclistAppService.delAidList(yhSleepUserMusiclist));
	}

	/*
	 * 查询所有助眠音套餐
	 */
	@RequestMapping("/selAllMusicMeal")
	public Result selAllMusicMeal() {
		return Result.ok(yhSleepMusicMealAppService.selAllMusicMeal());
	}

	/*
	 * 查询所有助眠，添加一个音乐id到音乐集id
	 */
	@RequestMapping("/addMusicIdToAidList")
	public Result addMusicIdToAidList(String userCode, String musicId) {
		YhSleepUserMusiclistApp yhSleepUserMusiclist = new YhSleepUserMusiclistApp();
		yhSleepUserMusiclist = yhSleepUserMusiclistAppService.selAidListByUserCode(userCode);
		if (yhSleepUserMusiclist != null) {
			String oldMusicId = yhSleepUserMusiclist.getMusicId();
			List<String> list =Arrays.asList(oldMusicId.split(","));
			List<String> oldMusicIdList=new ArrayList<String>(list);
			oldMusicIdList.remove(musicId);

			//String newMusicId=String.join(",", oldMusicIdList);
			//yhSleepUserMusiclist.setMusicId(musicId + ',' + newMusicId);

			
			String newMusicId=StringUtils.join(oldMusicIdList, ",");
			yhSleepUserMusiclist.setMusicId(musicId + ',' + newMusicId);

			return Result.ok(yhSleepUserMusiclistAppService.updAidList(yhSleepUserMusiclist));
		} else {
			YhSleepUserMusiclistApp yhSleepUserMusiclist2 = new YhSleepUserMusiclistApp();
			yhSleepUserMusiclist2.setMusicId(musicId);
			yhSleepUserMusiclist2.setUserCode(userCode);
			return Result.ok(yhSleepUserMusiclistAppService.addAidList(yhSleepUserMusiclist2));
		}
	}

	/*
	 * 根据音乐数组id查询音乐
	 */
	@RequestMapping("/selMusicById")
	public Result selMusicById(String musicId) {
		String[] musicIdList = musicId.split(",");
		List<YhSleepMusicApp> musicList = new ArrayList<YhSleepMusicApp>();
		for (int i = 0; i < musicIdList.length; i++) {
			YhSleepMusicApp yhSleepMusic = new YhSleepMusicApp();
			yhSleepMusic = yhSleepMusicAppService.selMusicById(Integer.parseInt(musicIdList[i]));
			if (yhSleepMusic != null) {
				musicList.add(yhSleepMusic);
			}
		}
		return Result.ok(musicList);
	}
	/*
	 * 听完音乐修改已听人数musicListennum+1
	 */
	@RequestMapping("/uptMusicListenById")
	public Result uptMusicListenById(int musicId) {
		return Result.ok(yhSleepMusicAppService.uptMusicListenById(musicId));
	}

}
