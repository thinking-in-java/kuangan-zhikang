/**
 * 
 */
package com.yihe.web.controller.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.DecimalFormat;
import com.yihe.common.utils.DateUtils;
import com.yihe.system.domain.YHUserSleepDiaryVOJO;
import com.yihe.system.domain.YhSleepUserDiary;
import com.yihe.system.domain.YhSportDiary;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserSleepGuide;
import com.yihe.system.service.YhSleepUserDiaryService;
import com.yihe.system.service.YhUserSleepGuideService;
import com.yihe.system.utils.Pages;

import io.swagger.annotations.ApiOperation;

/**
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhSleepUserDiaryController")
public class YhSleepUserDiaryController {

	 @Autowired
	    private YhSleepUserDiaryService yhSleepUserDiaryService;
	 
	 
	 @Autowired
	    private YhUserSleepGuideService yhUserSleepGuideService;
	 
	 
	 public static void main(String[] args) throws Exception { 
	     List<String> timestr = new ArrayList<String>();
	     timestr.add("2020-04-27 07:00");
	     timestr.add("2020-04-28 23:05");
	     timestr.add("2020-04-29 07:00");
	     timestr.add("2020-04-26 06:00");
	     timestr.add("2020-04-25 06:00");
	     //眠时间
	     Date  slepptime = StringToDate("2020-04-28 22:00");
	     long totaltime=0;
	     int size = timestr.size();
	     for (int i = 0; i < size; i++) {
	      //判断睡眠时间与起床时间是否在同一天
	      if(DateUtils.isSameDay(slepptime, StringToDate(timestr.get(i)))){
	       totaltime = totaltime- (dateToStamp("23:59")-dateToStamp(timestr.get(i).substring(10))-dateToStamp("00:00"));
	         }else{
	          totaltime = totaltime+ dateToStamp(timestr.get(i).substring(10));
	         }
	  }
	     long avgTime= totaltime/size;
	     System.out.println(stampToDate(avgTime+""));
	    } 
	 
	 
	    public static long dateToStamp(String s) throws java.text.ParseException{
	     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
	        Date date = simpleDateFormat.parse(s);      
	        long ts = date.getTime();      
	     return ts;
	    }
	    
	    
	    public static Date StringToDate(String s) throws java.text.ParseException{
	     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        Date date = simpleDateFormat.parse(s);      
	        return date;
	    }
	    
	    public static String stampToDate(String s){
	     String reString = null;
	     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
	     long lt = new Long(s);      
	        Date date = new Date(lt);       
	        reString = simpleDateFormat.format(date);       
	     return reString;
	    }
	 
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
		
		
		@ApiOperation(value = "运动分析结果")
	    @RequestMapping(value = "/showSleepRecordAnalyze", method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> showSleepRecordAnalyze(int id) throws ParseException{
			YhUserSleepGuide guide=yhUserSleepGuideService.selectByPrimaryKey(id);//查询分析报告
			int nut=dayCompare(guide.getBirthdate(), guide.getNewlyTime());//年龄
			String userName=guide.getUserName();//姓名
			int age=guide.getGender();//性别
			String userCode=guide.getUserCode();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userCode", guide.getUserCode());
			map.put("cTime", guide.getStartTime());
			map.put("cTime1", guide.getEndTime());
			List<YhSleepUserDiary> list=yhSleepUserDiaryService.showSleepRecordAnalyze(map);
			long hour=0;//小时
			long min=0;//分钟
			String[] cTime = new String[list.size()];
	 		String[] energyLevel = new String[list.size()];
	 		int i=0;
	 		int xinlai=0;
	 		int rshui=0;
	 		 List<String> timestr = new ArrayList<String>();
			    List<String> timestr1 = new ArrayList<String>();
			for (YhSleepUserDiary yhSleepUserDiary : list) {
				i++;
				 long nd = 1000 * 24 * 60 * 60;
				    long nh = 1000 * 60 * 60;
				    long nm = 1000 * 60;
				    // long ns = 1000;
				    // 获得两个时间的毫秒时间差异
				    long diff = yhSleepUserDiary.getWakeTime().getTime() - yhSleepUserDiary.getSleepTime().getTime();
				    // 计算差多少小时
				     hour+= diff % nd / nh;
				    // 计算差多少分钟
				    min+= diff % nd % nh / nm;
				    
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
					String time = ft.format(yhSleepUserDiary.getSleepDiarytime());
				    cTime[i-1]=""+time+"";
				    energyLevel[i-1]=""+diff % nd / nh+"."+diff % nd % nh / nm+"";
				    //给定输出格式
				    SimpleDateFormat sdf = new SimpleDateFormat("H");
				    //调用方法
				    String str = sdf.format(yhSleepUserDiary.getWakeTime());//醒来时间
				    if(Integer.valueOf(str)>=5 || Integer.valueOf(str)<=8) {
				    	xinlai++;
				    }
				  //调用方法
				    String str1 = sdf.format(yhSleepUserDiary.getSleepTime());//入睡时间
				    if(Integer.valueOf(str1)>=22 || Integer.valueOf(str1)<=23) {
				    	rshui++;
				    }
				    
				    SimpleDateFormat date= new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String SleepTime = date.format(yhSleepUserDiary.getSleepTime());
					String WakeTime = date.format(yhSleepUserDiary.getWakeTime());
					
				    timestr.add(""+SleepTime+"");//入睡时间
				    timestr1.add(""+WakeTime+"");//醒来时间
			}
			String averagesleep= showdeta(timestr, timestr1);//平均入睡时间
			String averagewakeup= showdeta1(timestr1, timestr);//平均起床时间
			 int hours = (int) Math.floor(min / 60);
			 String resultStr =hour+hours+"."+min%60;
			 DecimalFormat df = new DecimalFormat(".00");
		        
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
				String time = ft.format(guide.getStartTime());
				String time1 = ft.format(guide.getEndTime());
				
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("nut", nut);
			map1.put("userName", userName);
			map1.put("age", age);
			map1.put("userCode", userCode);
			map1.put("startTime", time);//开始时间
			map1.put("endTime", time1);//结束时间
			map1.put("meantime", df.format(Double.valueOf(resultStr)/list.size()));//日平均睡眠时间
			map1.put("cTime", cTime);//统计图时间
			map1.put("energyLevel", energyLevel);//睡眠时间
			map1.put("xinlai", xinlai);//早起天数
			map1.put("rshui", rshui);//早睡天数
			map1.put("averagesleep", averagesleep);//平均入睡时间
			map1.put("averagewakeup", averagewakeup);//平均起床时间
			map1.put("size", list.size());//记录总数
			return map1;
		}
	 
		public String showdeta(List<String> timestr,List<String> timestr1) throws ParseException {
	     long totaltime=0;
		     int size = timestr.size();
		     for (int i = 0; i < size; i++) {
		      //判断睡眠时间与起床时间是否在同一天
		      if(DateUtils.isSameDay(StringToDate(timestr1.get(i)), StringToDate(timestr.get(i)))){
		       totaltime = totaltime- (dateToStamp("23:59")-dateToStamp(timestr.get(i).substring(10))-dateToStamp("00:00"));
		         }else{
		          totaltime = totaltime+ dateToStamp(timestr.get(i).substring(10));
		         }
		  }
		     long avgTime= totaltime/size;
		     System.out.println(stampToDate(avgTime+""));
			return stampToDate(avgTime+"");
		}
		
		
		public String showdeta1(List<String> timestr,List<String> timestr1) throws ParseException {
		     long totaltime=0;
			     int size = timestr.size();
			     for (int i = 0; i < size; i++) {
			          totaltime = totaltime+ dateToStamp(timestr.get(i).substring(10));
			  }
			     long avgTime= totaltime/size;
			     System.out.println(stampToDate(avgTime+""));
				return stampToDate(avgTime+"");
			}
		
	  /**
	  	 * 查询用户查看睡眠分析报告
	  	 * @param page
	  	 * @param limit
	  	 * @return
	  	 */
	       @ApiOperation(value = "查询用户查看睡眠分析报告")
	       @RequestMapping(value = "/selectSleepsanalyze", method = RequestMethod.POST)
	   	@ResponseBody
	   	public Map<String,Object> selectSleepsanalyze(Integer page,Integer limit,String usercode){
	   		Map<String,Object> map1 = new HashMap<String,Object>();
	   		Pages pa = new Pages();
	   		pa.setCurPage(page);
	   		pa.setMaxResult(limit);
	   		map1.put("page", pa.getFirstRows());
	   		map1.put("limit", pa.getMaxResult());
	   		map1.put("userCode", usercode);
	   		
	   		Map<String,Object> map = new HashMap<String,Object>();
	   		List<YhUserSleepGuide> list = yhUserSleepGuideService.selectAnalyzeSleep(map1);
	   		for (YhUserSleepGuide yhUserSleepGuide : list) {
	   			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	   			String time = ft.format(yhUserSleepGuide.getStartTime());
	   			String time1 = ft.format(yhUserSleepGuide.getEndTime());
	   			yhUserSleepGuide.setSource(time+"~"+time1);
	 		}
	   		map.put("code", 20000);
	   		map.put("msg", "");
	   		map.put("count",yhUserSleepGuideService.selectAnalyzeSleepConut(map1));
	   		map.put("data", list);
	   		return map;
	   	}
	 
	 
	 /**
		 * 查询用户查看睡眠记录内容
		 * @param page
		 * @param limit
		 * @param usercode 用户id
		 * @param cTime 时间
		 * @return
		 */
	     @ApiOperation(value = "查询医生下用户的睡眠日记")
	     @RequestMapping(value = "/showSleepDiaryPor", method = RequestMethod.POST)
	 	@ResponseBody
	 	public Map<String,Object> showSleepDiaryPor(Integer page,Integer limit,String sleepDiarytime,String name,String Username,String result){
	 		Map<String,Object> map1 = new HashMap<String,Object>();
	 		Pages pa = new Pages();
	 		pa.setCurPage(page);
	 		pa.setMaxResult(limit);
	 		map1.put("page", pa.getFirstRows());
	 		map1.put("limit", pa.getMaxResult());
	 		Subject subject = SecurityUtils.getSubject();
			YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
			String usercode = principal.getUserCode();
	 		map1.put("userCode", usercode);
	 		map1.put("name", name);
	 		map1.put("username", Username);
	 		map1.put("result", result);
	 		if(sleepDiarytime!=null && !sleepDiarytime.equals("")) {
	 			String[] strArr = sleepDiarytime.split(" - ");
	 		map1.put("sleepDiarytime", strArr[0]);
	 		map1.put("sleepDiarytime1", strArr[1]);
	 		}
	 		Map<String,Object> map = new HashMap<String,Object>();
	 		List<YHUserSleepDiaryVOJO> list = yhSleepUserDiaryService.showSleepDiaryPor(map1);
	 		for (YHUserSleepDiaryVOJO yhSleepUserDiary : list) {
	 			String data=getDatePoor(yhSleepUserDiary.getWakeTime(), yhSleepUserDiary.getSleepTime());
	 			yhSleepUserDiary.setDuration(data);
	 			if(yhSleepUserDiary.getDiaryDesc()!=null || !yhSleepUserDiary.getDiaryDesc().equals("")) {
	 				String[] strArr1 = yhSleepUserDiary.getDiaryDesc().split(":");
	 				yhSleepUserDiary.setResult(strArr1[0]);
	 			}
	 			
			}
	 		map.put("code", 20000);
	 		map.put("msg", "");
	 		map.put("count",yhSleepUserDiaryService.showSleepDiaryPorConut(map1));
	 		map.put("data", list);
	 		return map;
	 	}
	 
	 
	 
	 /**
	  * 时间差
	  * @param endDate
	  * @param nowDate
	  * @return
	  */
	 public static String getDatePoor(Date endDate, Date nowDate) {
		 
		    long nd = 1000 * 24 * 60 * 60;
		    long nh = 1000 * 60 * 60;
		    long nm = 1000 * 60;
		    // long ns = 1000;
		    // 获得两个时间的毫秒时间差异
		    long diff = endDate.getTime() - nowDate.getTime();
		    // 计算差多少天
		    long day = diff / nd;
		    // 计算差多少小时
		    long hour = diff % nd / nh;
		    // 计算差多少分钟
		    long min = diff % nd % nh / nm;
		    // 计算差多少秒//输出结果
		    // long sec = diff % nd % nh % nm / ns;
		    return  hour + "小时" + min + "分钟";
		}
	 
	  /**
	     * 查询用户查看睡眠记录内容统计图
	     * @param usercode
	     * @param ctime
	     * @return
	     */
	     @ApiOperation(value = "查询用户查看睡眠记录内容统计图")
	     @RequestMapping(value = "/selectSleepDiaryCtime", method = RequestMethod.POST)
	 	@ResponseBody
	 	public Map<String,Object> selectSleepDiaryCtime(String usercode,String sleepDiarytime){
	 		Map<String,Object> map1 = new HashMap<String,Object>();
	 		map1.put("userCode", usercode);
	 		if(sleepDiarytime!=null && !sleepDiarytime.equals("")) {
	 			String[] strArr = sleepDiarytime.split(" - ");
	 		map1.put("sleepDiarytime", strArr[0]);
	 		map1.put("sleepDiarytime1", strArr[1]);
	 		}else {
	 			map1.put("sleepDiarytime", "");
		 		map1.put("sleepDiarytime1", "");
	 		}
	 		List<YhSleepUserDiary> list = yhSleepUserDiaryService.selectSleepDiaryCtime(map1);
	 		Collections.reverse(list);
	 		String[] cTime = new String[list.size()];
	 		String[] energyKcal = new String[list.size()];
	 		int i=0;
	 		for (YhSleepUserDiary yhSleepUserDiary : list) {
	 			i++;
	 			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
	 			String startTime = sdf.format(yhSleepUserDiary.getSleepDiarytime());
	 			cTime[i-1]=""+startTime+"";
	 			
	 		   long nd = 1000 * 24 * 60 * 60;
			    long nh = 1000 * 60 * 60;
			    long nm = 1000 * 60;
			    // long ns = 1000;
			    // 获得两个时间的毫秒时间差异
			    long diff = yhSleepUserDiary.getWakeTime().getTime() - yhSleepUserDiary.getSleepTime().getTime();
			    // 计算差多少小时
			    long hour = diff % nd / nh;
			    // 计算差多少分钟
			    long min = diff % nd % nh / nm;
	 			energyKcal[i-1]=""+hour+"."+min+"";
			}
	 		Map<String,Object> map = new HashMap<String,Object>();
	 		map.put("cTime", cTime);
	 		map.put("energyKcal",energyKcal);
	 		return map;
	 	}
	 
	     
	 
	 
	/**
	 * 查询用户查看睡眠记录内容
	 * @param page
	 * @param limit
	 * @param usercode 用户id
	 * @param cTime 时间
	 * @return
	 */
     @ApiOperation(value = "查询用户查看睡眠记录内容")
     @RequestMapping(value = "/selectSleepDiary", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectSportDiary(Integer page,Integer limit,String usercode,String sleepDiarytime){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("userCode", usercode);
 		if(sleepDiarytime!=null && !sleepDiarytime.equals("")) {
 			String[] strArr = sleepDiarytime.split(" - ");
 		map1.put("sleepDiarytime", strArr[0]);
 		map1.put("sleepDiarytime1", strArr[1]);
 		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		List<YhSleepUserDiary> list = yhSleepUserDiaryService.selectSleepDiary(map1);
 		for (YhSleepUserDiary yhSleepUserDiary : list) {
 			String data=getDatePoor(yhSleepUserDiary.getWakeTime(), yhSleepUserDiary.getSleepTime());
 			yhSleepUserDiary.setDuration(data);
 			if(yhSleepUserDiary.getDiaryDesc()!=null || !yhSleepUserDiary.getDiaryDesc().equals("")) {
 				String[] strArr1 = yhSleepUserDiary.getDiaryDesc().split(":");
 				yhSleepUserDiary.setResult(strArr1[0]);
 			}
 			
		}
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhSleepUserDiaryService.selectSleepDiaryConut(map1));
 		map.put("data", list);
 		return map;
 	}
}
