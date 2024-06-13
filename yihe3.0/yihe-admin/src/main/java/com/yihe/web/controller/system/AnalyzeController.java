/**
 * 
 */
package com.yihe.web.controller.system;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.YhFoodUserDiary;
import com.yihe.system.domain.YhLifeHabitSign;
import com.yihe.system.domain.YhSleepUserDiary;
import com.yihe.system.domain.YhSportDiary;
import com.yihe.system.domain.YhUserFoodGuide;
import com.yihe.system.domain.YhUserHabitGuide;
import com.yihe.system.domain.YhUserNotify;
import com.yihe.system.domain.YhUserNotifyVo;
import com.yihe.system.domain.YhUserSleepGuide;
import com.yihe.system.domain.YhUserSportsGuide;
import com.yihe.system.mapper.YhFoodUserDiaryMapper;
import com.yihe.system.mapper.YhLifeHabitSignMapper;
import com.yihe.system.mapper.YhSleepUserDiaryMapper;
import com.yihe.system.mapper.YhSportDiaryMapper;
import com.yihe.system.mapper.YhUserFoodGuideMapper;
import com.yihe.system.mapper.YhUserHabitGuideMapper;
import com.yihe.system.mapper.YhUserNotifyMapper;
import com.yihe.system.mapper.YhUserSleepGuideMapper;
import com.yihe.system.mapper.YhUserSportsGuideMapper;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/**
 *生成分析报告
 * @author dong
 * 
 *
 */
//1.主要用于标记配置类，兼备Component的效果。
@Configuration 
//2.开启定时任务     
@EnableScheduling 
@Component("AnalyzeController")
public class AnalyzeController {

	@Autowired
	YhSportDiaryMapper yhSportDiaryMapper;
	
	@Autowired
	YhUserSportsGuideMapper yhUserSportsGuideMapper;
	
	@Autowired
	YhFoodUserDiaryMapper yhFoodUserDiaryMapper;
	
	@Autowired
	YhUserFoodGuideMapper yhUserFoodGuideMapper;
	
	@Autowired
	YhSleepUserDiaryMapper yhSleepUserDiaryMapper;
	
	@Autowired
	YhUserSleepGuideMapper yhUserSleepGuideMapper;
	
	@Autowired
	YhUserNotifyMapper yhUserNotifyMapper;
	
	@Autowired
	YhLifeHabitSignMapper yhLifeHabitSignMapper;
	
	@Autowired
	YhUserHabitGuideMapper yhUserHabitGuideMapper;
	
	
	public void ryMultipleParams()
    {
        System.out.println(StringUtils.format("来了哈哈哈哈呵呵呵呵呵呵呵呵呵0-"));
    }
	
	
	
	public String qidata() {
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
		String hehe = dateFormat.format(now); 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
		date = new SimpleDateFormat("yy-MM-dd").parse(hehe); 
		} catch (ParseException e) { 
		e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-6); 

		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		System.out.println(dayBefore);
		return dayBefore;
	}
	
	
	@Scheduled(cron = "0 0 0 1 * ?")
	public void HebdomadRecordHabit() throws ParseException {
		List<YhLifeHabitSign> list=yhLifeHabitSignMapper.HebdomadRecordHabit();
		if(list!=null && !list.isEmpty()) {
			Date now = new Date(); 
			int i=0;
			for (YhLifeHabitSign yhLifeHabitSign : list) {
				Date date = new SimpleDateFormat("yy-MM-dd").parse(qidata());
				YhUserHabitGuide guide=new  YhUserHabitGuide();
				guide.setStartTime(date);
				guide.setEndTime(now);
				guide.setUserCode(yhLifeHabitSign.getUserCode());
				i=yhUserHabitGuideMapper.insertSelective(guide);
				YhUserNotifyVo tify=new YhUserNotifyVo();
				tify.setTitle("习惯报告");
				tify.setContent("您的习惯报告，已生成立即查看详情>>");
				tify.setUserId(yhLifeHabitSign.getUserCode());
				tify.setSendDate(now);
				tify.setToServiceId(4);
				tify.setNewsId(guide.getHabitGuideId());
				yhUserNotifyMapper.insertSelective(tify);
			}
		}
		
	}
	
	
	@Scheduled(cron = "0 59 23 ? * SUN")
	public void HebdomadRecordSleep() throws ParseException {
		List<YhSleepUserDiary> list=yhSleepUserDiaryMapper.HebdomadRecordSleep();
		if(list!=null && !list.isEmpty()) {
			Date now = new Date(); 
			int i=0;
			for (YhSleepUserDiary yhSleepUserDiary : list) {
				Date date = new SimpleDateFormat("yy-MM-dd").parse(qidata());
				YhUserSleepGuide guide=new  YhUserSleepGuide();
				guide.setStartTime(date);
				guide.setEndTime(now);
				guide.setUserCode(String.valueOf(yhSleepUserDiary.getUserCode()));
				i=yhUserSleepGuideMapper.insertSelective(guide);
				YhUserNotifyVo tify=new YhUserNotifyVo();
				tify.setTitle("睡眠报告");
				tify.setContent("您的睡眠报告，已生成立即查看详情>>");
				tify.setUserId(yhSleepUserDiary.getUserCode());
				tify.setSendDate(now);
				tify.setToServiceId(3);
				tify.setNewsId(guide.getSleepGuideId());
				yhUserNotifyMapper.insertSelective(tify);
			}
		}
		
	}
	
	

	
	
	// 每五秒执行一次
    @Scheduled(cron = "0 59 23 ? * SUN")
	public void HebdomadRecord() throws ParseException {
		System.out.println("来了定时器——————————————————————————");
		List<YhSportDiary> list=yhSportDiaryMapper.HebdomadRecordSport();
		if(list!=null && !list.isEmpty()) {
		Date now = new Date(); 
		for (YhSportDiary yhSportDiary : list) {
			Date date = new SimpleDateFormat("yy-MM-dd").parse(qidata());
			YhUserSportsGuide guide=new  YhUserSportsGuide();
			guide.setStartTime(date);
			guide.setEndTime(now);
			guide.setUserCode(yhSportDiary.getUserCode());
			int i=yhUserSportsGuideMapper.insertSelective(guide);
			YhUserNotifyVo tify=new YhUserNotifyVo();
			tify.setTitle("运动报告");
			tify.setContent("您的运动报告，已生成立即查看详情>>");
			tify.setUserId(yhSportDiary.getUserCode());
			tify.setSendDate(now);
			tify.setToServiceId(1);
			tify.setNewsId(guide.getSportsGuideId());
			yhUserNotifyMapper.insertSelective(tify);
		}
		}
		
	}
	
	
	
	
}
