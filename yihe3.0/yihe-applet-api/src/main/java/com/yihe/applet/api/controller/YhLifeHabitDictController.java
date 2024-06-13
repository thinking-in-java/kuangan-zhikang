package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.WeekDateVojo;
import com.yihe.applet.api.domain.YhLifeHabitDictApp;
import com.yihe.applet.api.domain.YhLifeHabitPersonDictApp;
import com.yihe.applet.api.mapper.YhLifeHabitDictAppMapper;
import com.yihe.applet.api.mapper.YhLifeHabitPersonDictAppMapper;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhLifeHabitPersonDictMapper;
import com.yihe.system.mapper.YhLifeHabitSignMapper;
import com.yihe.system.mapper.YhUserHabitGuideMapper;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * YhLifeHabitDictcontroller
 * Created by dong on 2020/1/2.
 */
@Controller
@RequestMapping("/api/YhLifeHabitDictController")
@Api(value = "YhLifeHabitDict控制器", description = "YhLifeHabitDict管理")
public class YhLifeHabitDictController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhLifeHabitDictController.class);

    @Autowired
    YhLifeHabitDictAppMapper yhLifeHabitDictService;

    @Autowired
    YhLifeHabitPersonDictAppMapper yhLifeHabitPersonDictService;

    @Autowired
	YhUserHabitGuideMapper yhUserHabitGuideMapper;

    @Autowired
	  private YhLifeHabitPersonDictMapper yhLifeHabitPersonDictMapper;

	@Autowired
	YhLifeHabitSignMapper yhLifeHabitSignMapper;

	@ApiOperation(value = "习惯分析结果")
    @RequestMapping(value = "/showHabitAnalyze", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> showHabitAnalyze(int id) throws ParseException{
		YhUserHabitGuide guide=yhUserHabitGuideMapper.selectByPrimaryKey(id);//查询分析报告

		String userCode=guide.getUserCode();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userCode", guide.getUserCode());
		map.put("signidDate", guide.getStartTime());
		map.put("signidDate1", guide.getEndTime());
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("userCode", guide.getUserCode());
		map3.put("signidDate", guide.getStartTime());
		map3.put("signidDate1", guide.getEndTime());

		List<FoodArray> array=new ArrayList<FoodArray>();
		List<YhLifeHabitSign> Cartogram=yhLifeHabitSignMapper.showCartogram(map3);
		for (YhLifeHabitSign yhLifeHabitSign : Cartogram) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
 			String dateString = formatter.format(yhLifeHabitSign.getSignidDate());
 			Map<String,Object> map2 = new HashMap<String,Object>();
 			map2.put("signidDate", dateString);
 			map2.put("userCode", userCode);
 			int signdate=yhLifeHabitSignMapper.selectBySignDate(map2).size();//当天的总记录数
 			int nul=yhLifeHabitPersonDictMapper.selectBynullDict(userCode).size();//每天都要打卡的习惯数
 			int dict=yhLifeHabitPersonDictMapper.selectBydict(map2).size();//当前用户打卡的记录数
 			int zong=nul+dict;
			FoodArray foodArray=new FoodArray();
			foodArray.setTime(dateString);
			foodArray.setRecommend(String.valueOf(zong));
			foodArray.setNotoverweight(String.valueOf(signdate));
			foodArray.setBeenoverweight("0");
			array.add(foodArray);
		}


		int food=0;//饮食
 		int motion=0;//运动
 		int sleep=0;//睡眠
 		int elsew=0;//其它
 		int lifeadd=0;//是否补卡
		List<YhLifeHabitSign> sing=yhLifeHabitSignMapper.showpunch(map3);
		int signpunch=yhLifeHabitSignMapper.ShowDaysStatisticspunch(map3);//总持续打卡天数
		int food1=0;//饮食
 		int motion1=0;//运动
 		int sleep1=0;//睡眠
 		int elsew1=0;//其它
 		List<YhLifeHabitSign> showmyhabit=yhLifeHabitSignMapper.showmyhabit(map3);
 		for (YhLifeHabitSign yhLifeHabitSign : showmyhabit) {
 			if(yhLifeHabitSign.getCategoryName().equals("饮食")) {
				food1++;
			}else if(yhLifeHabitSign.getCategoryName().equals("运动")) {
				motion1++;
			}else if(yhLifeHabitSign.getCategoryName().equals("睡眠")) {
				sleep1++;
			}else if(yhLifeHabitSign.getCategoryName().equals("其它")) {
				elsew1++;
			}
		}
		List<YhLifeHabitSign> accumulativenum=yhLifeHabitSignMapper.accumulativenum(map3);//累计天数
		for (YhLifeHabitSign yhLifeHabitSign : sing) {
			if(yhLifeHabitSign.getCategoryName().equals("饮食")) {
				food++;
			}else if(yhLifeHabitSign.getCategoryName().equals("运动")) {
				motion++;
			}else if(yhLifeHabitSign.getCategoryName().equals("睡眠")) {
				sleep++;
			}else if(yhLifeHabitSign.getCategoryName().equals("其它")) {
				elsew++;
			}

			lifeadd+=yhLifeHabitSign.getLifeAdd();
		}

		List<YhLifeHabitPersonDict> list=yhLifeHabitPersonDictMapper.showdictdate(map);//查询出系统推荐项
		List<RecommendVojo> vojo=new ArrayList<RecommendVojo>();
		if(list!=null && !list.isEmpty()) {
			for (YhLifeHabitPersonDict yhLifeHabitPersonDict : list) {
				RecommendVojo mmen=new RecommendVojo();
				mmen.setHabitName(yhLifeHabitPersonDict.getHabitName());
				if(yhLifeHabitPersonDict.getBeginTime()!=null) {
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  			Date time = ft.parse(yhLifeHabitPersonDict.getBeginTime());
		  			Date time1 = ft.parse(yhLifeHabitPersonDict.getFinishTim());
					int num=dayCompare1(time,time1);
					mmen.setSum(num);
				}
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("habitId", yhLifeHabitPersonDict.getHabitId());
				map1.put("userCode", guide.getUserCode());
				List<YhLifeHabitSign> complete=yhLifeHabitSignMapper.showSignCount(map1);//总完成天数
				if(complete!=null && !complete.isEmpty()) {
					mmen.setComplete(complete.size());
					Map<String,Object> map2 = new HashMap<String,Object>();
					map2.put("habitId", yhLifeHabitPersonDict.getHabitId());
					map2.put("userCode", guide.getUserCode());
					map2.put("signidDate", complete.get(0).getSignidDate());
					map2.put("signidDate1", complete.get(complete.size()-1).getSignidDate());
					int sustain=yhLifeHabitSignMapper.ShowDaysStatistics(map2);
					mmen.setSustain(sustain);
				}
				vojo.add(mmen);
			}
		}


		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String time = ft.format(guide.getStartTime());
		String time1 = ft.format(guide.getEndTime());

		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("userCode", userCode);
		map1.put("vojo", vojo);
		map1.put("startTime", time);//开始时间
		map1.put("endTime", time1);//结束时间
		map1.put("punch", sing.size());//打卡数
 		map1.put("lifeadd", lifeadd);//补卡数
 		map1.put("signpunch", signpunch);//总持续打卡天数
 		map1.put("accumulativenum", accumulativenum.size());//累计天数
 		map1.put("showmyhabit", showmyhabit.size());
 		map1.put("food1", food1);
 		map1.put("motion1", motion1);
 		map1.put("sleep1", sleep1);
 		map1.put("elsew1", elsew1);

		map1.put("food", food);
 		map1.put("motion", motion);
 		map1.put("sleep", sleep);
 		map1.put("elsew", elsew);

 		map1.put("array", array);//能耗统计图

		return map1;
	}


	public static int dayCompare1(Date fromDate,Date toDate){
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
	    return day;
	}


    /**
     * 增加个人习惯习惯
     * @param habitType
     * @return
     */
  	@RequestMapping(value = "/habitinsertSelective", method = RequestMethod.POST)
  	@ResponseBody // ajax注解
  	public Result habitinsertSelective(YhLifeHabitDictApp hLifeHabitDictApp) {
  		Date date=new Date();
  		hLifeHabitDictApp.setLifeTime(date);
  		hLifeHabitDictApp.setLifeImg("http://xcxqny.yhy.ren/xiguan/alarm_clock.png");
  		int list=yhLifeHabitDictService.insertSelective(hLifeHabitDictApp);
  		return Result.ok(list);
  	}


    /**
     * 根据id查询习惯
     * @param habitType
     * @return
     */
  	@RequestMapping(value = "/habitselectByPrimaryKey", method = RequestMethod.POST)
  	@ResponseBody // ajax注解
  	public Result habitselectByPrimaryKey(int id) {
  		YhLifeHabitDictApp list=yhLifeHabitDictService.selectByPrimaryKey(id);
  		List<WeekDateVojo> vojo=new ArrayList<WeekDateVojo>();
  		if(list.getPeriod()==null || list.getPeriod().equals("")) {
			WeekDateVojo week=new WeekDateVojo();
			week.setName("周一");
			week.setState(true);
			vojo.add(week);
			WeekDateVojo week1=new WeekDateVojo();
			week1.setName("周二");
			week1.setState(true);
			vojo.add(week1);
			WeekDateVojo week2=new WeekDateVojo();
			week2.setName("周三");
			week2.setState(true);
			vojo.add(week2);
			WeekDateVojo week3=new WeekDateVojo();
			week3.setName("周四");
			week3.setState(true);
			vojo.add(week3);
			WeekDateVojo week4=new WeekDateVojo();
			week4.setName("周五");
			week4.setState(true);
			vojo.add(week4);
			WeekDateVojo week5=new WeekDateVojo();
			week5.setName("周六");
			week5.setState(true);
			vojo.add(week5);
			WeekDateVojo week6=new WeekDateVojo();
			week6.setName("周日");
			week6.setState(true);
			vojo.add(week6);
		}else {
			WeekDateVojo week=new WeekDateVojo();
			week.setName("周一");
			WeekDateVojo week1=new WeekDateVojo();
			week1.setName("周二");
			WeekDateVojo week2=new WeekDateVojo();
			week2.setName("周三");
			WeekDateVojo week3=new WeekDateVojo();
			week3.setName("周四");
			WeekDateVojo week4=new WeekDateVojo();
			week4.setName("周五");
			WeekDateVojo week5=new WeekDateVojo();
			week5.setName("周六");
			WeekDateVojo week6=new WeekDateVojo();
			week6.setName("周日");
			 String[] strArr = list.getPeriod().split(",");
		        for (int i = 0; i < strArr.length; ++i){
		        	if(strArr[i].equals("1")){
		    			week.setState(true);
		            }

		        	if(strArr[i].equals("2")){
						week1.setState(true);
		            }

		        	if(strArr[i].equals("3")){
						week2.setState(true);
		            }

		        	if(strArr[i].equals("4")){
						week3.setState(true);
		            }

		        	if(strArr[i].equals("5")){
						week4.setState(true);
		            }

		        	if(strArr[i].equals("6")){
						week5.setState(true);
		            }

		        	if(strArr[i].equals("7")){
						week6.setState(true);
		            }
		        }
		        vojo.add(week);
    			vojo.add(week1);
				vojo.add(week2);
				vojo.add(week3);
				vojo.add(week4);
				vojo.add(week5);
				vojo.add(week6);
		}
  		list.setWeekDate(vojo);
  		return Result.ok(list);
  	}


  /**
   * 根据大类id查询习惯
   * @param habitType
   * @return
   */
	@RequestMapping(value = "/selectbytype", method = RequestMethod.POST)
	@ResponseBody // ajax注解
	public List<YhLifeHabitDictApp> selectbytype(String habitType,String usercode) {
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("habitType",habitType);
		map1.put("userCode",usercode);
		List<YhLifeHabitDictApp> list=yhLifeHabitDictService.selectbytype(map1);
		Map<String, Object> map=new HashMap<String, Object>();
		 Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		   String dateString = formatter.format(currentTime);
		for (YhLifeHabitDictApp yhLifeHabitDict : list) {
			map.put("habitId",yhLifeHabitDict.getId());
			map.put("userCode",usercode);
			map.put("tim", dateString);
			int yhLifeHabitPersonDict=yhLifeHabitPersonDictService.showhabit(map);
			if(yhLifeHabitPersonDict!=0) {
				yhLifeHabitDict.setIftrue(false);
			}else {
				yhLifeHabitDict.setIftrue(true);
			}
		}
		return list;
	}

	/**
	 * 打卡日期范围超过指定范围，习惯自动删除，并重置指定的时间范围
	 */
	@RequestMapping(value = "/deleteTiming", method = RequestMethod.POST)
	@ResponseBody
	public void deleteTiming() throws ParseException {
		//获取当前系统的日期
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = sdf.format(date);
		Date now = sdf.parse(date1);


		//查询系统中所有设置了日期范围的习惯集合
		List<YhLifeHabitPersonDictApp> yhLifeHabitPersonDictApps = yhLifeHabitPersonDictService.selectByHabtisListCondition();
		for (YhLifeHabitPersonDictApp yhLifeHabitPersonDictApp : yhLifeHabitPersonDictApps) {
			Date finishDate = sdf.parse(yhLifeHabitPersonDictApp.getFinishTim());
			if (now.compareTo(finishDate)>0){
				yhLifeHabitPersonDictApp.setBeginTime(null);
				yhLifeHabitPersonDictApp.setFinishTim(null);
				yhLifeHabitPersonDictApp.setAccomplishType("4");
				System.out.println(yhLifeHabitPersonDictApp);
				yhLifeHabitPersonDictService.updateByHabyisListCondition(yhLifeHabitPersonDictApp);
			}
		}
	}
}
