/**
 * 
 */
package com.yihe.applet.api.controller;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.mapper.*;
import com.yihe.common.exception.job.TaskException;
import com.yihe.quartz.domain.SysJob;
import com.yihe.quartz.service.ISysJobService;
import com.yihe.system.mapper.YhHealthLectureCommentMapper;
import com.yihe.system.mapper.YhLifeHabitSignMapper;
import com.yihe.system.utils.Result;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.*;

/**
 * 我的生活习惯项目
 * @author dong
 *
 */
@RestController
@RequestMapping("/api/YhLifeHabitPersonDictAppController")
public class YhLifeHabitPersonDictAppController {
	
	@Autowired
	private YhLifeHabitPersonDictAppMapper yhLifeHabitPersonDictAppMapper;
	
	@Autowired
	private YhLifeHabitSignAppMapper yhLifeHabitSignAppMapper;
	
	@Autowired
    YhLifeHabitDictAppMapper yhLifeHabitDictService;
	
	@Autowired
	YhLifeHabitRetroactiveMapper yhLifeHabitRetroactiveMapper;
	
	@Autowired
	YhHabitRemindMapper yhHabitRemindMapper;
	
	@Autowired
	YhJobAppMapper yhJobAppMapper;
	
	@Autowired
	QrtzTriggersAppMapper QrtzTriggersAppMapper;
	
	@Autowired
	ISysJobService jobService;
	
	 @Autowired
	 QrtzJobDetailsAppMapper qrtzJobDetailsAppMapper;
	 
	 @Autowired
	 QrtzCronTriggersAppMapper qrtzCronTriggersAppMapper;
	 
	 @Autowired
	 YhHealthLectureCommentMapper yhHealthLectureCommentMapper;
	 
	 @Autowired
	 YhLifeHabitSignMapper yhLifeHabitSignMapper;
	 
	
    //"ss mm HH dd MM ? yyyy"
    private static final SimpleDateFormat sdf = new SimpleDateFormat("s mm HH");

	
    
	@RequestMapping(value = "/showremindCount", method = RequestMethod.POST)
	@ResponseBody // ajax注解
	public int showremindCount(String userCode) {
		int i=yhHabitRemindMapper.showremindCount(userCode);
		return i;
	}
	
	
	/***
     *  功能描述：日期转换cron表达式
     * @param date
     * @return
     */
    public static String formatDateByPattern(Date date) {
        String formatTimeStr = null;
        if (Objects.nonNull(date)) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
	
	 /***
     * convert Date to cron, eg "0 07 10 15 1 ?"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date date) {
        return formatDateByPattern(date);
    }
    
	
		@RequestMapping(value = "/updateperson", method = RequestMethod.POST)
		@ResponseBody // ajax注解
		public int addhabit(YhLifeHabitPersonDictApp yuhLifeHabitPersonDict) throws SchedulerException, TaskException {
			if(yuhLifeHabitPersonDict.getStandby2()!=null && !yuhLifeHabitPersonDict.getStandby2().equals("") && !yuhLifeHabitPersonDict.getStandby2().equals("null")) {
			 String[] stand = yuhLifeHabitPersonDict.getStandby2().split(",");//定时器id
				 for (int i = 0; i < stand.length; i++) {//删除之前定时器
					 YhJobAppKey key=new YhJobAppKey();
					 key.setJobId(Long.valueOf(stand[i]));
					 key.setJobName("健康习惯提醒");
					 key.setJobGroup("DEFAULT");
					 yhJobAppMapper.deleteByPrimaryKey(key);//删除定时任务调度表
					 
					 
					 QrtzCronTriggersAppKey cron=new QrtzCronTriggersAppKey();
					 cron.setSchedName("yiheScheduler");
					 cron.setTriggerName("TASK_CLASS_NAME"+stand[i]);
					 cron.setTriggerGroup("DEFAULT");
					 qrtzCronTriggersAppMapper.deleteByPrimaryKey(cron);
					 
					 
					 QrtzTriggersAppKey qrtz=new QrtzTriggersAppKey();
					 qrtz.setSchedName("yiheScheduler");
					 qrtz.setTriggerName("TASK_CLASS_NAME"+stand[i]);
					 qrtz.setTriggerGroup("DEFAULT");
					 QrtzTriggersAppMapper.deleteByPrimaryKey(qrtz);//删除定时任务触发器
					 
					 QrtzJobDetailsAppKey detail=new QrtzJobDetailsAppKey();
					 detail.setSchedName("yiheScheduler");
					 detail.setJobName("TASK_CLASS_NAME"+stand[i]);
					 detail.setJobGroup("DEFAULT");
					 qrtzJobDetailsAppMapper.deleteByPrimaryKey(detail);//删除定时任务详情
					 
					
				}
			}
			 
			Date date=new Date();
			if(yuhLifeHabitPersonDict.isSwitch2()==true) {//开启提醒功能
				
				YhHabitRemind record=new YhHabitRemind();
				record.setHaId(String.valueOf(yuhLifeHabitPersonDict.getHabitId()));
				record.setUserCode(yuhLifeHabitPersonDict.getUserCode());
				yhHabitRemindMapper.insertSelective(record);
				
				
				
				yuhLifeHabitPersonDict.setStandby("1");
				 yuhLifeHabitPersonDict.setStandby1(yuhLifeHabitPersonDict.getRemindTime());
				 
				 String[] strArr = yuhLifeHabitPersonDict.getPeriod().split(",");//周几
				 if(strArr.length>=7) {//每天提醒
					 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			    	   ParsePosition pos = new ParsePosition(0);
			    	   Date strtodate = formatter.parse(yuhLifeHabitPersonDict.getRemindTime(), pos);
			    	   System.out.println(getCron(strtodate));
			    	   //0 59 23 ? * SUN
			    	   String cron=getCron(strtodate)+" * * ?";
			    	   
			    	   SysJob job=new SysJob();
			    	   job.setJobName("健康习惯提醒");//任务名称
						job.setJobGroup("DEFAULT");//任务组名
						job.setInvokeTarget("TimerSend.HebdomadRecordHabit('"+String.valueOf(yuhLifeHabitPersonDict.getId())+"')");//调用目标字符串
						job.setCronExpression(cron);//cron执行表达式
						job.setMisfirePolicy("1");//计划执行错误策略（1立即执行 2执行一次 3放弃执行）
						job.setConcurrent("0");//是否并发执行（0允许 1禁止）
						//job.setStatus("0");//状态（0正常 1暂停）
						job.setCreateTime(date);
						job.setRemark(String.valueOf(yuhLifeHabitPersonDict.getId()));//习惯id
			    	    jobService.insertJob(job);
			    	    yuhLifeHabitPersonDict.setStandby2(String.valueOf(job.getJobId()));
				 }else {
					 String jobid="";
					 for (int i = 0; i < strArr.length; i++) {
						 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
				    	   ParsePosition pos = new ParsePosition(0);
				    	   Date strtodate = formatter.parse(yuhLifeHabitPersonDict.getRemindTime(), pos);
				    	   String cron="";
							if(strArr[i].equals("1") || strArr[i]=="1") {
								//0 59 23 ? * SUN
						    	   cron=getCron(strtodate)+" "+"? * MON";
							}else if(strArr[i].equals("2") || strArr[i]=="2") {
								//0 59 23 ? * SUN
						    	   cron=getCron(strtodate)+" "+"? * TUE";
							}else if(strArr[i].equals("3") || strArr[i]=="3") {
								//0 59 23 ? * SUN
						    	   cron=getCron(strtodate)+" "+"? * WED";
							}else if(strArr[i].equals("4") || strArr[i]=="4") {
								//0 59 23 ? * SUN
						    	   cron=getCron(strtodate)+" "+"? * THU";
							}else if(strArr[i].equals("5") || strArr[i]=="5") {
								//0 59 23 ? * SUN
						    	   cron=getCron(strtodate)+" "+"? * FRI";
							}else if(strArr[i].equals("6") || strArr[i]=="6") {
								//0 59 23 ? * SUN
						    	   cron=getCron(strtodate)+" "+"? * SAT";
							}else if(strArr[i].equals("7") || strArr[i]=="7") {
								//0 59 23 ? * SUN
						    	   cron=getCron(strtodate)+" "+"? * SUN";
							}
							
							   SysJob job=new SysJob();
					    	   job.setJobName("健康习惯提醒");//任务名称
								job.setJobGroup("DEFAULT");//任务组名
								job.setInvokeTarget("TimerSend.HebdomadRecordHabit('"+String.valueOf(yuhLifeHabitPersonDict.getId())+"')");//调用目标字符串
								job.setCronExpression(cron);//cron执行表达式
								job.setMisfirePolicy("1");//计划执行错误策略（1立即执行 2执行一次 3放弃执行）
								job.setConcurrent("0");//是否并发执行（0允许 1禁止）
								//job.setStatus("0");//状态（0正常 1暂停）
								job.setCreateTime(date);
								job.setRemark(String.valueOf(yuhLifeHabitPersonDict.getId()));//习惯id
					    	    jobService.insertJob(job);
					    	    jobid+=job.getJobId()+",";
					 }
					 yuhLifeHabitPersonDict.setStandby2(jobid);
					 
				 }
				 
		    	  
			}else {
				
				YhHabitRemind record=new YhHabitRemind();
				record.setHaId(String.valueOf(yuhLifeHabitPersonDict.getHabitId()));
				record.setUserCode(yuhLifeHabitPersonDict.getUserCode());
				yhHabitRemindMapper.deleteremind(record);
				
				 yuhLifeHabitPersonDict.setStandby1("");
				 yuhLifeHabitPersonDict.setStandby2("");
				yuhLifeHabitPersonDict.setStandby("0");
			}
			
			if(yuhLifeHabitPersonDict.isSwitch1()==false) {
				yuhLifeHabitPersonDict.setBeginTime(null);
				yuhLifeHabitPersonDict.setFinishTim(null);
			}
			
			yuhLifeHabitPersonDict.setIncreaseDate(date);
			int i=yhLifeHabitPersonDictAppMapper.updateByPrimaryKeySelective(yuhLifeHabitPersonDict);
		return i;
		}



	
	/**
	 * 查询当前用户是否有习惯
	 * @param userCode
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/showUserHabit")
	public Result showUserHabit(String userCode,String habitid) throws ParseException {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userCode", userCode);
		map.put("habitid", habitid);
		YhLifeHabitPersonDictApp dict=yhLifeHabitPersonDictAppMapper.showUserHabit(map);
		DateFormat format1 = new SimpleDateFormat("HH:mm"); 
		Date begin = format1.parse(dict.getBeginDate());   
		Date finish = format1.parse(dict.getFinishDate());  
		dict.setBeginDate(format1.format(begin));
		dict.setFinishDate(format1.format(finish));
		List<WeekDateVojo> vojo=new ArrayList<WeekDateVojo>();
		if(dict.getPeriod()==null || dict.getPeriod().equals("")) {
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
			 String[] strArr = dict.getPeriod().split(",");
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
		dict.setWeekDate(vojo);

		//查询
		return Result.ok(dict);
	}
	
	  /**
	   * 查询是否有补签卡
	   * @param userCode
	   * @return
	 * @throws ParseException 
	   */
		@RequestMapping("/showRetroactivecode")
		public Result showRetroactivecode(String userCode,int habitid,int id,String selectVal) throws ParseException {
			YhLifeHabitRetroactive retroa=yhLifeHabitRetroactiveMapper.showRetroactivecode(userCode);
			int all=0;
			if(retroa==null) {
				all=0;
			}else {
				retroa.setWhether(1);
				yhLifeHabitRetroactiveMapper.updateByPrimaryKeySelective(retroa);
				
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
				Date date2 = format1.parse(selectVal); //当前时间
				YhLifeHabitSignApp sign=new YhLifeHabitSignApp();
				sign.setUserCode(userCode);
				sign.setHabitId(habitid);
				sign.setSignidDate(date2);
				int i=yhLifeHabitSignAppMapper.insertSelective(sign);
				int sustain=0;//总天数
				if(i!=0) {
					Map<String,Object> map1 = new HashMap<String,Object>();
					map1.put("habitId", habitid);
					map1.put("userCode", userCode);
					List<YhLifeHabitSignApp> complete=yhLifeHabitSignAppMapper.showSignCount(map1);//总完成天数
					if(complete!=null && !complete.isEmpty()) {
						Map<String,Object> map2 = new HashMap<String,Object>();
						map2.put("habitId", habitid);
						map2.put("userCode", userCode);
						map2.put("signidDate", complete.get(0).getSignidDate());
						map2.put("signidDate1", complete.get(complete.size()-1).getSignidDate());
						sustain=yhLifeHabitSignAppMapper.ShowDaysStatistics(map2);
					}
					
					YhLifeHabitPersonDictApp person=new YhLifeHabitPersonDictApp();
					person.setId(id);
					person.setContinueDay(sustain);
					person.setTotalDay(complete.size());
					yhLifeHabitPersonDictAppMapper.updateByPrimaryKeySelective(person);
				}
				all=1;
			}
			return Result.ok(all);
		}
	
	/**
     * Description: 判断一个时间是否在一个时间段内 </br>
     *
     * @param nowTime 当前时间 </br>
     * @param beginTime 开始时间 </br>
     * @param endTime 结束时间 </br>
     */
    private static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
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
	 * 删除习惯
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/deleteByPrimaryKeyPersonDict")
	public Result deleteByPrimaryKeyPersonDict(int id,String userCode,int habitid) {
		
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("habitId", habitid);
//		map.put("usercode", userCode);
//		yhHealthLectureCommentMapper.deleteLectureCode(map);
//		yhLifeHabitSignMapper.deleteByPrimaryKeyCode(map);
		
		YhLifeHabitPersonDictApp person=yhLifeHabitPersonDictAppMapper.selectByPersonId(id);
		if(person.getStandby2()!=null && !person.getStandby2().equals("") && !person.getStandby2().equals("null")) {
			 String[] stand = person.getStandby2().split(",");//定时器id
				 for (int i = 0; i < stand.length; i++) {//删除之前定时器
					 YhJobAppKey key=new YhJobAppKey();
					 key.setJobId(Long.valueOf(stand[i]));
					 key.setJobName("健康习惯提醒");
					 key.setJobGroup("DEFAULT");
					 yhJobAppMapper.deleteByPrimaryKey(key);//删除定时任务调度表
					 
					 
					 QrtzCronTriggersAppKey cron=new QrtzCronTriggersAppKey();
					 cron.setSchedName("yiheScheduler");
					 cron.setTriggerName("TASK_CLASS_NAME"+stand[i]);
					 cron.setTriggerGroup("DEFAULT");
					 qrtzCronTriggersAppMapper.deleteByPrimaryKey(cron);
					 
					 
					 QrtzTriggersAppKey qrtz=new QrtzTriggersAppKey();
					 qrtz.setSchedName("yiheScheduler");
					 qrtz.setTriggerName("TASK_CLASS_NAME"+stand[i]);
					 qrtz.setTriggerGroup("DEFAULT");
					 QrtzTriggersAppMapper.deleteByPrimaryKey(qrtz);//删除定时任务触发器
					 
					 QrtzJobDetailsAppKey detail=new QrtzJobDetailsAppKey();
					 detail.setSchedName("yiheScheduler");
					 detail.setJobName("TASK_CLASS_NAME"+stand[i]);
					 detail.setJobGroup("DEFAULT");
					 qrtzJobDetailsAppMapper.deleteByPrimaryKey(detail);//删除定时任务详情
					 
					
				}
			}
		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
		yuhLifeHabitPersonDict.setId(id);
		yuhLifeHabitPersonDict.setAccomplishType("4");
		yuhLifeHabitPersonDict.setBeginTime(null);
		yuhLifeHabitPersonDict.setFinishTim(null);
		yuhLifeHabitPersonDict.setStandby(null);
		yuhLifeHabitPersonDict.setStandby1(null);
		yuhLifeHabitPersonDict.setStandby2(null);
		int i=yhLifeHabitPersonDictAppMapper.updateByPrimaryKeySelective(yuhLifeHabitPersonDict);
		//删除习惯提醒记录
		YhHabitRemind record=new YhHabitRemind();
		record.setHaId(String.valueOf(habitid));
		record.setUserCode(userCode);
		yhHabitRemindMapper.deleteremind(record);
		return Result.ok(i);
	}
	
    
    
    /**
	     * 增加个人习惯
	* @param usercode
	* @param id
	* @return
	*/
	@RequestMapping(value = "/addhabit", method = RequestMethod.POST)
	@ResponseBody // ajax注解
	public int addhabit(String usercode,int id,String period,String beginTime,String finishTime) {
		YhLifeHabitDictApp dict=yhLifeHabitDictService.selectByPrimaryKey(id);
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userCode", usercode);
		map.put("habitid", id);
		YhLifeHabitPersonDictApp dict1=yhLifeHabitPersonDictAppMapper.showUserHabit1(map);
		int i=0;
		YhLifeHabitPersonDictApp yuhLifeHabitPersonDict=new YhLifeHabitPersonDictApp();
		yuhLifeHabitPersonDict.setUserCode(usercode);
		yuhLifeHabitPersonDict.setHabitId(dict.getId());
		yuhLifeHabitPersonDict.setMissingDay(3);
		if(beginTime==null || beginTime.equals("")) {
			yuhLifeHabitPersonDict.setBeginDate(dict.getBeginTime());
			yuhLifeHabitPersonDict.setFinishDate(dict.getFinishTime());
		}else {
			yuhLifeHabitPersonDict.setBeginDate(beginTime);
			yuhLifeHabitPersonDict.setFinishDate(finishTime);
			yuhLifeHabitPersonDict.setPeriod(period);
		}
		if(dict1==null) {
				i=yhLifeHabitPersonDictAppMapper.insertSelective(yuhLifeHabitPersonDict);
		}else {
			yuhLifeHabitPersonDict.setId(dict1.getId());
			yuhLifeHabitPersonDict.setAccomplishType("3");
			yuhLifeHabitPersonDict.setBeginTime(null);
			yuhLifeHabitPersonDict.setFinishTim(null);
			yuhLifeHabitPersonDict.setStandby(null);
			yuhLifeHabitPersonDict.setStandby1(null);
			yuhLifeHabitPersonDict.setStandby2(null);
			i=yhLifeHabitPersonDictAppMapper.updateByPrimaryKeySelective(yuhLifeHabitPersonDict);
		}
	return i;
	}


	/**
	 * 查询当前用户是否有习惯
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/showUserCodePersonDict")
	public Result showUserCodePersonDict(String userCode) {
		return Result.ok(yhLifeHabitPersonDictAppMapper.showUserCodePersonDict(userCode));
	}
	
	
	/**
	 * 	根据id查询出记录
	 * @param userCode
	 * @param time
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/showByHabitPrimaryKey")
	public Result showByHabitPrimaryKey(String userCode,String time,int id) throws ParseException {
		YhLifeHabitPersonDictApp yhLifeHabitPersonDictApp=yhLifeHabitPersonDictAppMapper.selectByPrimaryKey(id);
		
		int join=yhLifeHabitPersonDictAppMapper.selectByPersonCount(yhLifeHabitPersonDictApp.getHabitId());
		int clock=yhLifeHabitSignAppMapper.selectBySignCount(yhLifeHabitPersonDictApp.getHabitId());
		yhLifeHabitPersonDictApp.setJoin(join);
		yhLifeHabitPersonDictApp.setClock(clock);
		 Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		if(time==null || time.equals("")) {
			time=dateString;
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userCode", userCode);
		map.put("signidDate", time);
		map.put("habitId", yhLifeHabitPersonDictApp.getHabitId());
		int sign=yhLifeHabitSignAppMapper.showHabitSignRecord(map);//查询用户是否打卡
		if(sign!=0) {
			yhLifeHabitPersonDictApp.setType(3);
		}else {
			boolean result =false;
			if(yhLifeHabitPersonDictApp.getPeriod()!=null) {
				String[] strArr = yhLifeHabitPersonDictApp.getPeriod().split(",");
				 
				Date da=new Date();
			    Calendar cal1 = Calendar.getInstance();
			    cal1.setTime(da);
			    int w = cal1.get(Calendar.DAY_OF_WEEK) - 1;//获取当前星期几
			    if(w==0) {//星期天等于0
			    	w=7;
			    }
			    result = Arrays.asList(strArr).contains(String.valueOf(w));
			}
		    
			if(yhLifeHabitPersonDictApp.getPeriod()==null || result==true) {
			if(time!=null) {
				Date day = formatter.parse(time);
				Date same = formatter.parse(dateString);
				long beginMillisecond =same.getTime();//当前时间
				long endMillisecond = day.getTime();
				
				if(beginMillisecond-endMillisecond==0) {

					String beginDate=yhLifeHabitPersonDictApp.getBeginDate();//开始打卡时间2
					String finishDate=yhLifeHabitPersonDictApp.getFinishDate();//结束打卡时间
					if(beginDate==null || beginDate.equals("")) {
						yhLifeHabitPersonDictApp.setType(1);
					}else {
						DateFormat format1 = new SimpleDateFormat("HH:mm"); 
						Calendar cal=Calendar.getInstance();
						int h=cal.get(Calendar.HOUR_OF_DAY);
						int mi=cal.get(Calendar.MINUTE);
						String str3 = ""+h+":"+mi+"";
						Date date2 = format1.parse(str3); //当前时间
						Date date = format1.parse(beginDate);   
						Date date1 = format1.parse(finishDate);
						if(belongCalendar(date2,date,date1)==true) {
							yhLifeHabitPersonDictApp.setType(1);
						}else {
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							//获取年
					        int year = cal.get(Calendar.YEAR);
					        //获取月份，0表示1月份
					        int month = cal.get(Calendar.MONTH) + 1;
					        //获取当前天数
					        int day1 = cal.get(Calendar.DAY_OF_MONTH);
					        String se=year+"-"+month+"-"+day1+" "+finishDate;
//							//将字符串形式的时间转化为Date类型的时间
							Date a=new Date();
							Date b=sdf.parse(se);
							if(a.getTime()-b.getTime()<=0)
								yhLifeHabitPersonDictApp.setType(2);
							else
								yhLifeHabitPersonDictApp.setType(4);
						}
						
						
						
					}
				}else if(beginMillisecond-endMillisecond>0) {
					//System.out.println("前天");
					
					String beginDate=yhLifeHabitPersonDictApp.getBeginDate();//开始打卡时间
					if(beginDate==null || beginDate.equals("")) {
						yhLifeHabitPersonDictApp.setType(1);
					}else {
						yhLifeHabitPersonDictApp.setType(4);
					}
					
					
					
				}else {
					String beginDate=yhLifeHabitPersonDictApp.getBeginDate();//开始打卡时间
					if(beginDate==null || beginDate.equals("")) {
						yhLifeHabitPersonDictApp.setType(1);
					}else {
						yhLifeHabitPersonDictApp.setType(5);
					}
				}
				
			}else {

				String beginDate=yhLifeHabitPersonDictApp.getBeginDate();//开始打卡时间
				String finishDate=yhLifeHabitPersonDictApp.getFinishDate();//结束打卡时间
				if(beginDate==null || beginDate.equals("")) {
					yhLifeHabitPersonDictApp.setType(1);
				}else {
					DateFormat format1 = new SimpleDateFormat("HH:mm"); 
					Calendar cal=Calendar.getInstance();
					int h=cal.get(Calendar.HOUR_OF_DAY);
					int mi=cal.get(Calendar.MINUTE);
					String str3 = ""+h+":"+mi+"";
					Date date2 = format1.parse(str3); //当前时间
					Date date = format1.parse(beginDate);   
					Date date1 = format1.parse(finishDate);
					if(belongCalendar(date2,date,date1)==true) {
						yhLifeHabitPersonDictApp.setType(1);
					}else {
						yhLifeHabitPersonDictApp.setType(2);
					}
				}
			}
		}else {
			yhLifeHabitPersonDictApp.setType(2);
		}
			
			
			
			
		}
		return Result.ok(yhLifeHabitPersonDictApp);
	}
	
	
	public static void main(String[] args) throws ParseException {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String startTimeStr = "2021-03-03";

		String endTimeStr = "2021-03-05";//当前时间
		
		
		Date startTime= df.parse(startTimeStr);

		Date endTime= df.parse(endTimeStr);

		  if (startTime.before(endTime)){
			   //表示startTime早于endTime
			  	System.out.println("早于");
			  }else{
				  System.out.println("等于晚于");

			        }
	}
	
	
	
	
	/**
	 * 	
	 * @param userCode
	 * @param time
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/showByHabitRecord")
	public Result showByHabitRecord(String userCode,String time) throws ParseException {
		 Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			if(time==null || time.equals("")) {
				time=dateString;
			}
			Map<String, Object> map1=new HashMap<String, Object>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			String startTimeStr = time;

			String endTimeStr = dateString;//当前时间
			Date startTime= df.parse(startTimeStr);
			Date endTime= df.parse(endTimeStr);
			  if (startTime.before(endTime)){
			   //表示startTime早于endTime
			  	System.out.println("早于");
			  }else{
				  map1.put("remindTime", "1");
			  }
			
			
			
		
		map1.put("userCode", userCode);
		map1.put("increaseDate", time);
		List<YhLifeHabitPersonDictApp> list=yhLifeHabitPersonDictAppMapper.showByHabitRecord(map1);
		
		
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userCode", userCode);
		map.put("signidDate", time);
		for (YhLifeHabitPersonDictApp yhLifeHabitPersonDictApp : list) {
			
			if(yhLifeHabitPersonDictApp.getBeginTime()!=null) {
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	  			Date time1 = ft.parse(yhLifeHabitPersonDictApp.getBeginTime());
	  			Date time2 = ft.parse(yhLifeHabitPersonDictApp.getFinishTim());
				int num=dayCompare1(time1,time2);
				yhLifeHabitPersonDictApp.setSum(""+num+"天");
			}else {
				yhLifeHabitPersonDictApp.setSum("无期限");
			}
			
			
			map.put("habitId", yhLifeHabitPersonDictApp.getHabitId());
			int sign=yhLifeHabitSignAppMapper.showHabitSignRecord(map);//查询用户是否打卡
			if(sign!=0) {
				yhLifeHabitPersonDictApp.setType(3);
			}else {
				boolean result =false;
				if(yhLifeHabitPersonDictApp.getPeriod()!=null) {
					String[] strArr = yhLifeHabitPersonDictApp.getPeriod().split(",");
					 
					Date da=new Date();
				    Calendar cal1 = Calendar.getInstance();
				    cal1.setTime(da);
				    int w = cal1.get(Calendar.DAY_OF_WEEK) - 1;//获取当前星期几
				    if(w==0) {//星期天等于0
				    	w=7;
				    }
				    result = Arrays.asList(strArr).contains(String.valueOf(w));
				}
			    
				if(yhLifeHabitPersonDictApp.getPeriod()==null || result==true) {
				if(time!=null) {
					Date day = formatter.parse(time);
					Date same = formatter.parse(dateString);
					long beginMillisecond =same.getTime();//当前时间
					long endMillisecond = day.getTime();
					
					if(beginMillisecond-endMillisecond==0) {

						String beginDate=yhLifeHabitPersonDictApp.getBeginDate();//开始打卡时间2
						String finishDate=yhLifeHabitPersonDictApp.getFinishDate();//结束打卡时间
						if(beginDate==null || beginDate.equals("")) {
							yhLifeHabitPersonDictApp.setType(1);
						}else {
							DateFormat format1 = new SimpleDateFormat("HH:mm"); 
							Calendar cal=Calendar.getInstance();
							int h=cal.get(Calendar.HOUR_OF_DAY);
							int mi=cal.get(Calendar.MINUTE);
							String str3 = ""+h+":"+mi+"";
							Date date2 = format1.parse(str3); //当前时间
							Date date = format1.parse(beginDate);   
							Date date1 = format1.parse(finishDate);
							if(belongCalendar(date2,date,date1)==true) {
								yhLifeHabitPersonDictApp.setType(1);
							}else {
								SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
								//获取年
						        int year = cal.get(Calendar.YEAR);
						        //获取月份，0表示1月份
						        int month = cal.get(Calendar.MONTH) + 1;
						        //获取当前天数
						        int day1 = cal.get(Calendar.DAY_OF_MONTH);
						        String se=year+"-"+month+"-"+day1+" "+finishDate;
//								//将字符串形式的时间转化为Date类型的时间
								Date a=new Date();
								Date b=sdf.parse(se);
								if(a.getTime()-b.getTime()<=0)
									yhLifeHabitPersonDictApp.setType(2);
								else
									yhLifeHabitPersonDictApp.setType(4);
							}
							
							
							
						}
					}else if(beginMillisecond-endMillisecond>0) {
						//System.out.println("前天");
						
						String beginDate=yhLifeHabitPersonDictApp.getBeginDate();//开始打卡时间
						if(beginDate==null || beginDate.equals("")) {
							yhLifeHabitPersonDictApp.setType(1);
						}else {
							yhLifeHabitPersonDictApp.setType(4);
						}
						
						
						
					}else {
						String beginDate=yhLifeHabitPersonDictApp.getBeginDate();//开始打卡时间
						if(beginDate==null || beginDate.equals("")) {
							yhLifeHabitPersonDictApp.setType(1);
						}else {
							yhLifeHabitPersonDictApp.setType(5);
						}
					}
					
				}else {

					String beginDate=yhLifeHabitPersonDictApp.getBeginDate();//开始打卡时间
					String finishDate=yhLifeHabitPersonDictApp.getFinishDate();//结束打卡时间
					if(beginDate==null || beginDate.equals("")) {
						yhLifeHabitPersonDictApp.setType(1);
					}else {
						DateFormat format1 = new SimpleDateFormat("HH:mm"); 
						Calendar cal=Calendar.getInstance();
						int h=cal.get(Calendar.HOUR_OF_DAY);
						int mi=cal.get(Calendar.MINUTE);
						String str3 = ""+h+":"+mi+"";
						Date date2 = format1.parse(str3); //当前时间
						Date date = format1.parse(beginDate);   
						Date date1 = format1.parse(finishDate);
						if(belongCalendar(date2,date,date1)==true) {
							yhLifeHabitPersonDictApp.setType(1);
						}else {
							yhLifeHabitPersonDictApp.setType(2);
						}
					}
				}
			}else {
				yhLifeHabitPersonDictApp.setType(2);
			}
				
				
				
				
			}
		}
		List<YhLifeHabitPersonDictApp> vojo=new ArrayList<YhLifeHabitPersonDictApp>();
		for (YhLifeHabitPersonDictApp yhLifeHabitPersonDictApp : list) {
			if(yhLifeHabitPersonDictApp.getType()==1) {
				vojo.add(yhLifeHabitPersonDictApp);
			}
		}
		for (YhLifeHabitPersonDictApp yhLifeHabitPersonDictApp : list) {
			if(yhLifeHabitPersonDictApp.getType()!=1) {
				vojo.add(yhLifeHabitPersonDictApp);
			}
		}
		return Result.ok(vojo);
	}
	
		
	
	

	public boolean compare(Date time1,Date time2) throws ParseException
	{
		//如果想比较日期则写成"yyyy-MM-dd"就可以了
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		//将字符串形式的时间转化为Date类型的时间
//		Date a=sdf.parse(time1);
//		Date b=sdf.parse(time2);
		//Date类的一个方法，如果a早于b返回true，否则返回false
		if(time1.before(time2))
			return true;
		else
			return false;
		/*
		 * 如果你不喜欢用上面这个太流氓的方法，也可以根据将Date转换成毫秒
		if(a.getTime()-b.getTime()<0)
			return true;
		else
			return false;
		*/
	}

}
