/**
 * 
 */
package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.mapper.*;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.InformationSummary;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 个人习惯记录
 * @author dong
 *
 */
@RestController
@RequestMapping("/api/YhLifeHabitSignAppController")
public class YhLifeHabitSignAppController {

	@Autowired
	private YhLifeHabitSignAppMapper yhLifeHabitSignAppMapper;
	
	@Autowired
	private YhLifeHabitPersonDictAppMapper yhLifeHabitPersonDictAppMapper;
	
	@Autowired
    YhLifeHabitDictAppMapper yhLifeHabitDictService;
	

	@Autowired
	YhAwardMedalMapper yhAwardMedalMapper;
	
	@Autowired
	YhUserAwardMedalMapper yhUserAwardMedalMapper;
	
	
	@Autowired
	YhLifeHabitRetroactiveMapper yhLifeHabitRetroactiveMapper;
	
	 /**
	    * 习惯统计图
	    * @param userCode
	    * @param figure 判断是哪个时间段
	    * @return
	 * @throws ParseException 
	    */
		@RequestMapping(value = "selectByHabitStatistics")
		@ResponseBody
		public Result selectByHabitStatistics(String userCode,String type,int habitid) throws ParseException {
			if (StringUtil.isEmpty(userCode) || StringUtil.isEmpty(type)) {
				return Result.error("参数异常");
			}
			
			
			if (type.equals("本周")) {
				List<InformationSummary> findSportDiaryByUserCodeForWeekDay = yhLifeHabitSignAppMapper.findHabitDiaryByUserCodeForWeekDay(userCode,habitid);
				int[] zhou=new int[7];
				int i=0;
				for (int j=0;j<7;j++) {
					 
					int daynum = findSportDiaryByUserCodeForWeekDay.get(j).getDaynum();
					if(daynum>0){
						i++;
					}else{
						i=0;
					}
					zhou[j]=i;
					
				}
				List<String> timeInterval = getTimeInterval();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("timeList", timeInterval);
				map.put("infoList", zhou);
				return Result.ok(map);
			}
			
			
			if (type.equals("本月")) {
				
				int[] dayNum = new int[31];
				for (int k = 1; k <= 31; k++) {
					dayNum[k - 1] = k;
				}
				Calendar cal = Calendar.getInstance();
				int month = cal.get(Calendar.MONTH) + 1;
				int year = cal.get(Calendar.YEAR);
				int[] yuelist=new int[31];
				int i=0;
				int num=0;
				HashMap<String, Integer> findFoodUserDuaryYue = yhLifeHabitSignAppMapper.findHabitDiaryByUserCodeForMonth(userCode, String.valueOf(year), String.valueOf(month),habitid);
				
				if(null==findFoodUserDuaryYue){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("timeList", dayNum);
					map.put("infoList", yuelist);
					return Result.ok(map);
				}
				List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(findFoodUserDuaryYue.entrySet()); //转换为list
				
				list.sort(new Comparator<Map.Entry<String, Integer>>() {
			          @Override
			          public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
			        	  String key2 = o2.getKey();
			        	  String key1 = o1.getKey();
			        	  if(StringUtils.isNumeric(key2)&&StringUtils.isNumeric(key1)){
			        		  Integer intkey2=Integer.valueOf(key2);
			        		  Integer intkey1=Integer.valueOf(key1);
			        		  return intkey1.compareTo(intkey2);
			        	  }
			              return o1.getKey().compareTo(o2.getKey());
			          }
			      });
				list.remove(list.size()-1);
				list.remove(list.size()-1);
				
				 for (Map.Entry<String, Integer> mapping : list){
					 
			            	Integer valueOf = Integer.valueOf(""+mapping.getValue());
			            	if(valueOf>0){
			            		num++;
			            	}else{
			            		num=0;
			            	}
			            	yuelist[i]=num;
			          
			            
			            	i++;
			            
			            
			        }    
				 
				
				
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("timeList", dayNum);
				map.put("infoList", yuelist);
				return Result.ok(map);
			}
			
		
			
			if (type.equals("本年")) {
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int[] yearlist=new int[12];
				HashMap<String, Integer> findFoodUserDiaryByYear = yhLifeHabitSignAppMapper.findHabitDiaryByuserCodeForyear(userCode, String.valueOf(year),habitid);
				if(null==findFoodUserDiaryByYear){
					int[] dayNum = new int[12];
					for (int k = 1; k <= 12; k++) {
						dayNum[k - 1] = k;
					}
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("timeList", dayNum);
					map.put("infoList", yearlist);
					return Result.ok(map);
				}
				List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(findFoodUserDiaryByYear.entrySet()); //转换为list
				
				
				list.sort(new Comparator<Map.Entry<String, Integer>>() {
			          @Override
			          public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
			        	  String key2 = o2.getKey();
			        	  String key1 = o1.getKey();
			        	  if(StringUtils.isNumeric(key2)&&StringUtils.isNumeric(key1)){
			        		  Integer intkey2=Integer.valueOf(key2);
			        		  Integer intkey1=Integer.valueOf(key1);
			        		  return intkey1.compareTo(intkey2);
			        	  }
			              return o1.getKey().compareTo(o2.getKey());
			          }
			      });
				list.remove(list.size()-1);
				int i=0;
				 for (Map.Entry<String, Integer> mapping : list){
			            	Integer valueOf = Integer.valueOf(""+mapping.getValue());
			            	yearlist[i]=valueOf;
			            i++;
			        }    
				int[] dayNum = new int[12];
				for (int k = 1; k <= 12; k++) {
					dayNum[k - 1] = k;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("timeList", dayNum);
				map.put("infoList", yearlist);
				return Result.ok(map);
			}
			return Result.error("参数异常");
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
	/**
	     * 根据code查询习惯天数
	* @param userCode
	* @param entryTime
	* @return
	*/
	@RequestMapping(value = "ShowthisweekSign")
	@ResponseBody 
	public Result ShowthisweekSign(String userCode,String habitid) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userCode", userCode);
		map.put("habitId", habitid);
		List<YhLifeHabitSignApp> week=yhLifeHabitSignAppMapper.ShowthisweekSign(map);//根据code与习惯id查询本周记录 
		List<YhLifeHabitSignApp> month=yhLifeHabitSignAppMapper.ShowthismonthSign(map);//根据code与习惯id查询本月记录
		List<YhLifeHabitSignApp> all=yhLifeHabitSignAppMapper.ShowthisAllSign(map);//所有记录
		int max=yhLifeHabitSignAppMapper.ShowMaxSign(map);//根据code与习惯id查询最大持续天数
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("week", week.size());
		map1.put("month", month.size());
		map1.put("all", all.size());
		map1.put("max", max);
		
		return Result.ok(map1);
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
	
	/**
	     * 根据code查询习惯运动日期
	* @param userCode
	* @param entryTime
	* @return
	*/
	@RequestMapping(value = "showSignCount")
	@ResponseBody 
	public Result showSignCount(String userCode,String habitid) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userCode", userCode);
		map.put("habitId", habitid);
		List<YhLifeHabitSignApp> complete=yhLifeHabitSignAppMapper.showSignCount(map);//总完成天数
		String floatArray[] = new String[complete.size()];
		int i=0;
		for (YhLifeHabitSignApp yhSportDiary : complete) {
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			 String dateString = formatter.format(yhSportDiary.getSignidDate());
			floatArray[i]=dateString;
			i++;
		}
		return Result.ok(floatArray);
	}



	 	/**
		     * 根据code查询习惯运动日期
		* @param userCode
		* @param entryTime
		* @return
		*/
		@RequestMapping(value = "selectByLifeTime")
		@ResponseBody 
		public Result selectByLifeTime(String userCode) {
		List<YhLifeHabitSignApp> selectByPrimaryKey = yhLifeHabitSignAppMapper.selectByLifeTime(userCode);
		String floatArray[] = new String[selectByPrimaryKey.size()];
		int i=0;
		for (YhLifeHabitSignApp yhSportDiary : selectByPrimaryKey) {
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			 String dateString = formatter.format(yhSportDiary.getSignidDate());
			floatArray[i]=dateString;
			i++;
		}
		return Result.ok(floatArray);
		}
	
	@RequestMapping("/showZongRanking")
	public Map<String, Object> showZongRanking(String userCode,int habitid) {
		int wo=0;
		YhLifeHabitPersonDictApp wones=new YhLifeHabitPersonDictApp();
		List<YhLifeHabitPersonDictApp> sond=yhLifeHabitPersonDictAppMapper.showZongRanking(habitid);//总榜排名
		for (YhLifeHabitPersonDictApp yhLifeHabitPersonDictApp : sond) {
			wo++;
			if(yhLifeHabitPersonDictApp.getPostCode()==null || yhLifeHabitPersonDictApp.getPostCode().equals("")) {
				if(yhLifeHabitPersonDictApp.getGender()==1) {
					yhLifeHabitPersonDictApp.setPostCode("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
				}else {
					yhLifeHabitPersonDictApp.setPostCode("http://xcxqny.yhy.ren/xiaochengxu/toxiangnv.png");
				}
			}
			yhLifeHabitPersonDictApp.setSum(yhLifeHabitPersonDictApp.getTotalDay()+"次");
			if(userCode.equals(yhLifeHabitPersonDictApp.getUserCode())) {
				wones.setRowno(wo);
				wones.setTotalDay(yhLifeHabitPersonDictApp.getTotalDay());
			}
		}
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("userCode", userCode);
		map1.put("habitId", habitid);
		if(wones.getRowno()==0) {
			wones=yhLifeHabitPersonDictAppMapper.ShowOneselfRanking(map1);
		}
		
		
		List<YhLifeHabitSignApp> rankList=yhLifeHabitSignAppMapper.showThatverydayRanking(habitid);
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("topThree", sond);//总部排名
		map.put("wones", wones);//我的总排名
		map.put("rankList", rankList);//今日排名
		return map;
	}
	
	
	
	/**
	 * 增加用户习惯记录
	 * @param userCode
	 * @param habitid
	 * @return
	 */
	@RequestMapping("/SigninsertSelective")
	public Result SigninsertSelective(String userCode,int habitid,int id) {
		Date date=new Date();
		YhLifeHabitSignApp sign=new YhLifeHabitSignApp();
		sign.setUserCode(userCode);
		sign.setHabitId(habitid);
		sign.setSignidDate(date);
		int i=yhLifeHabitSignAppMapper.insertSelective(sign);
		int sustain=0;//总天数
		if(i!=0) {
			Date dakaDate=yhLifeHabitPersonDictAppMapper.setDate(habitid,userCode);//获取上一次获得补签卡的时间
			Calendar now=Calendar.getInstance();
			now.setTime(new Date());
			now.add(Calendar.DAY_OF_MONTH,-10);
			Date nowDate=now.getTime();
			if (dakaDate.before(nowDate)){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("habitId", habitid);
				map1.put("userCode", userCode);
				map1.put("date",sdf.format(now.getTime()));
				List<YhLifeHabitSignApp> complete=yhLifeHabitSignAppMapper.showSignCount(map1);//总完成天数
				sustain=complete.size();
			}
//			if(complete!=null && !complete.isEmpty()) {
//				Map<String,Object> map2 = new HashMap<String,Object>();
//				map2.put("habitId", habitid);
//				map2.put("userCode", userCode);
//				map2.put("signidDate", complete.get(0).getSignidDate());
//				map2.put("signidDate1", complete.get(complete.size()-1).getSignidDate());
//				sustain=yhLifeHabitSignAppMapper.ShowDaysStatistics(map2);
//			}
			if(sustain>=10){
				YhLifeHabitPersonDictApp person=new YhLifeHabitPersonDictApp();
				person.setId(id);
				person.setContinueDay(sustain);
				person.setTotalDay(10);
				person.setIncreaseDate(new Date());
				yhLifeHabitPersonDictAppMapper.updateByPrimaryKeySelective(person);
			}
		}			
		Map<String,Object> map = new HashMap<String,Object>();
		YhLifeHabitPersonDictApp dict=yhLifeHabitPersonDictAppMapper.selectByPrimaryKey(id);
		YhLifeHabitDictApp list=yhLifeHabitDictService.selectByPrimaryKey(habitid);
		map.put("i", i);
		if(sustain==10) {//第一阶段
			map.put("i", 2);
			YhAwardMedal medal=selectdengMedal(habitid, 1,userCode);
			map.put("img", medal.getImg());
		}
//		if(list.getOneRank()!=null && dict.getContinueDay()==list.getOneRank()) {//第一阶段
//			map.put("i", 2);
//			YhAwardMedal medal=selectdengMedal(habitid, 1,userCode);
//			map.put("img", medal.getImg());
//		}else if(list.getOneRank()!=null && dict.getContinueDay()==list.getTwoRank()) {//第2阶段
//			map.put("i", 2);
//			YhAwardMedal medal=selectdengMedal(habitid, 2,userCode);
//			map.put("img", medal.getImg());
//		}else if(list.getOneRank()!=null && dict.getContinueDay()==list.getTwoRank()) {//第3阶段
//			map.put("i", 2);
//			YhAwardMedal medal=selectdengMedal(habitid, 3,userCode);
//			map.put("img", medal.getImg());
//		}else if(list.getOneRank()!=null && dict.getContinueDay()==list.getTwoRank()) {//第4阶段
//			map.put("i", 2);
//			YhAwardMedal medal=selectdengMedal(habitid, 4,userCode);
//			map.put("img", medal.getImg());
//		}else if(list.getOneRank()!=null && dict.getContinueDay()==list.getTwoRank()) {//第5阶段
//			map.put("i", 2);
//			YhAwardMedal medal=selectdengMedal(habitid, 5,userCode);
//			map.put("img", medal.getImg());
//		}
		
		return Result.ok(map);
	}
	
	public YhAwardMedal selectdengMedal(int habitid,int grade,String userCode) {
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("habId", habitid);
		map1.put("grade", grade);
		YhAwardMedal medal=yhAwardMedalMapper.selectdengMedal(map1);
		
		
		
		YhUserAwardMedal dal=new YhUserAwardMedal();
		dal.setImg(medal.getImg());
		dal.setGrade(grade);
		dal.setName(medal.getMeMane());
		dal.setUserCode(userCode);
		dal.setHabId(String.valueOf(habitid));
		yhUserAwardMedalMapper.insertSelective(dal);
		
		Date date=new Date();
		Calendar calc =Calendar.getInstance();
		calc.setTime(date);//当前日期7天后
		calc.add(Calendar.MONTH, 1);//当前日期一个月后  calc.add(Calendar.MONTH, 1);
		Date minDate = calc.getTime();
		YhLifeHabitRetroactive tive=new YhLifeHabitRetroactive();
		tive.setGainTime(date);
		tive.setUserCode(userCode);
		tive.setPastTime(minDate);
		tive.setWhether(0);
		tive.setMode("打卡奖励");
		yhLifeHabitRetroactiveMapper.insertSelective(tive);
		
		return medal;
	}
	
	
}
