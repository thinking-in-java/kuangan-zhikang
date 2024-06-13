package com.yihe.applet.api.controller;

import java.text.ParsePosition;
import java.util.Date;
import java.util.Objects;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.applet.api.domain.QrtzCronTriggersAppKey;
import com.yihe.applet.api.domain.QrtzJobDetailsAppKey;
import com.yihe.applet.api.domain.QrtzTriggersAppKey;
import com.yihe.applet.api.domain.SleepRemindApp;
import com.yihe.applet.api.domain.YhHabitRemind;
import com.yihe.applet.api.domain.YhJobAppKey;
import com.yihe.applet.api.domain.sleepSkipApp;
import com.yihe.applet.api.mapper.QrtzCronTriggersAppMapper;
import com.yihe.applet.api.mapper.QrtzJobDetailsAppMapper;
import com.yihe.applet.api.mapper.QrtzTriggersAppMapper;
import com.yihe.applet.api.mapper.YhJobAppMapper;
import com.yihe.applet.api.service.SleepRemindAppService;
import com.yihe.common.exception.job.TaskException;
import com.yihe.quartz.domain.SysJob;
import com.yihe.quartz.service.ISysJobService;
import com.yihe.system.utils.Result;

@Controller
@RequestMapping("/api/SleepRemindApp")
public class SleepRemindAppController {
	@Autowired
	SleepRemindAppService sleepRemindAppService;
	 @Autowired
	 QrtzJobDetailsAppMapper qrtzJobDetailsAppMapper;
	 @Autowired
	 QrtzCronTriggersAppMapper qrtzCronTriggersAppMapper;
	 @Autowired
	YhJobAppMapper yhJobAppMapper;	
	 @Autowired
		ISysJobService jobService;
	@Autowired
	QrtzTriggersAppMapper QrtzTriggersAppMapper;
	
    //"ss mm HH dd MM ? yyyy"
    private static final SimpleDateFormat sdf = new SimpleDateFormat("s mm HH");

	
    
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
	
	 @RequestMapping(value = "/addSleepRemindApp")
	  @ResponseBody
	public Result addSleepRemindApp(SleepRemindApp skip,boolean kaiqi) throws SchedulerException, TaskException {
		 int row = 0;
		 if(skip.getTimerId()!=null && !skip.getTimerId().equals("")) {
			 
				//删除之前定时器
					 YhJobAppKey key=new YhJobAppKey();
					 key.setJobId(Long.valueOf(skip.getTimerId()));
					 key.setJobName("睡眠提醒");
					 key.setJobGroup("DEFAULT");
					 yhJobAppMapper.deleteByPrimaryKey(key);//删除定时任务调度表
					 
					 
					 QrtzCronTriggersAppKey cron=new QrtzCronTriggersAppKey();
					 cron.setSchedName("yiheScheduler");
					 cron.setTriggerName("TASK_CLASS_NAME"+skip.getTimerId());
					 cron.setTriggerGroup("DEFAULT");
					 qrtzCronTriggersAppMapper.deleteByPrimaryKey(cron);
					 
					 
					 QrtzTriggersAppKey qrtz=new QrtzTriggersAppKey();
					 qrtz.setSchedName("yiheScheduler");
					 qrtz.setTriggerName("TASK_CLASS_NAME"+skip.getTimerId());
					 qrtz.setTriggerGroup("DEFAULT");
					 QrtzTriggersAppMapper.deleteByPrimaryKey(qrtz);//删除定时任务触发器
					 
					 QrtzJobDetailsAppKey detail=new QrtzJobDetailsAppKey();
					 detail.setSchedName("yiheScheduler");
					 detail.setJobName("TASK_CLASS_NAME"+skip.getTimerId());
					 detail.setJobGroup("DEFAULT");
					 qrtzJobDetailsAppMapper.deleteByPrimaryKey(detail);//删除定时任务详情
					 
					 row = sleepRemindAppService.deleteByPrimaryKey(skip.getId());
		
			}
		 Date date=new Date();
			if(kaiqi==true) {//开启提醒功能
				 
				 //每天提醒
					 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			    	   ParsePosition pos = new ParsePosition(0);
			    	   Date strtodate = formatter.parse(skip.getRemindTime(), pos);
			    	   System.out.println(getCron(strtodate));
			    	   //0 59 23 ? * SUN
			    	   String cron=getCron(strtodate)+" * * ?";
			    	   
			    	   SysJob job=new SysJob();
			    	   job.setJobName("睡眠提醒");//任务名称
						job.setJobGroup("DEFAULT");//任务组名
						job.setInvokeTarget("TimerSend.sleepremind('"+skip.getUserCode()+"')");//调用目标字符串
						job.setCronExpression(cron);//cron执行表达式
						job.setMisfirePolicy("1");//计划执行错误策略（1立即执行 2执行一次 3放弃执行）
						job.setConcurrent("0");//是否并发执行（0允许 1禁止）
						//job.setStatus("0");//状态（0正常 1暂停）
						job.setCreateTime(date);
						job.setRemark(String.valueOf(skip.getId()));//习惯id
			    	    jobService.insertJob(job);
			    	    skip.setTimerId( new Long(job.getJobId()).intValue());
			    	    skip.setQtime(new Date());
			    	    String remindTime = skip.getRemindTime().substring(0, 5);
			    	    skip.setRemindTime(remindTime);
						 row = sleepRemindAppService.insertSelective(skip);
			}else {
				
				
				 
			}
	
		
			return Result.ok(row);
		
	}
	 @RequestMapping(value = "/selectSleepRemindApp")
	   	@ResponseBody
	public Result selectSleepRemindApp(String userCode) {
		 SleepRemindApp selectSleepRemindApp = sleepRemindAppService.selectBySleepRemindApp(userCode);
		return Result.ok(selectSleepRemindApp);
		
	}
	  public static void main(String args[]) {

	        String Str = new String("www.php.cn");

	  

	        System.out.print("返回值 :" );

	        System.out.println(Str.substring(4) );

	  

	        System.out.print("返回值 :" );

	        System.out.println(Str.substring(0, 6) );

	    }
}
