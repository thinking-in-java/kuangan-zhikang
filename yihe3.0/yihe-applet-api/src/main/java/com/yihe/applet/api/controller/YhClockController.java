/**
 * 
 */
package com.yihe.applet.api.controller;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhHealthLectureCommentApp;
import com.yihe.applet.api.domain.YhLifeHabitPersonDictApp;
import com.yihe.applet.api.domain.YhLifeHabitSignApp;
import com.yihe.applet.api.mapper.YhLifeHabitPersonDictAppMapper;
import com.yihe.applet.api.mapper.YhLifeHabitSignAppMapper;
import com.yihe.applet.api.service.YhHealthLectureCommentAppService;
import com.yihe.system.utils.Result;

/**
 * @author Administrator
 *
 */
@Controller
public class YhClockController {

	@Autowired
	YhHealthLectureCommentAppService yhHealthLectureCommentService;
	
	@Autowired
	private YhLifeHabitSignAppMapper yhLifeHabitSignAppMapper;
	
	@Autowired
	private YhLifeHabitPersonDictAppMapper yhLifeHabitPersonDictAppMapper;
	
	@RequestMapping(value = "/messge/contentClockxg")
	@ResponseBody
	public Result contentClock (String userCode,String id,String neir) {
		String[] strArr = userCode.split(",");
		String[] strid = id.split(",");
		String[] strneir = neir.split(",");
		for (int i = 0; i < strArr.length; ++i){//循环去code
			final double d = Math.random();//设置随机数
			final int k = (int)(d*strneir.length);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  Calendar nowTime = Calendar.getInstance();
			  if(i>0) {
				  nowTime.add(Calendar.MINUTE, k);//分钟加随机数
				  nowTime.add(Calendar.SECOND, (k+5));//秒数加随机数
			  }
			  String sd=sdf.format(nowTime.getTime());//当前时间
			  
			  
			YhHealthLectureCommentApp record=new YhHealthLectureCommentApp();
			record.setLectureinfoid(58);
			record.setUsercode(strArr[i]);
			record.setCommentinfo(strneir[k]);//随机去数组中的内容
			record.setStatus((byte) 2);
			record.setCommenttime(sd);
			int insertSelective = yhHealthLectureCommentService.insertSelective(record);//增加内容
			 ParsePosition pos = new ParsePosition(0);
			 Date strtodate = sdf.parse(sd, pos);
			YhLifeHabitSignApp sign=new YhLifeHabitSignApp();
			sign.setUserCode(strArr[i]);
			sign.setHabitId(58);
			sign.setSignidDate(strtodate);
			int l=yhLifeHabitSignAppMapper.insertSelective(sign);
			int sustain=0;//总天数
			if(l!=0) {
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("habitId", 58);
				map1.put("userCode", strArr[i]);
				List<YhLifeHabitSignApp> complete=yhLifeHabitSignAppMapper.showSignCount(map1);//总完成天数
				if(complete!=null && !complete.isEmpty()) {
					Map<String,Object> map2 = new HashMap<String,Object>();
					map2.put("habitId", 58);
					map2.put("userCode", strArr[i]);
					map2.put("signidDate", complete.get(0).getSignidDate());
					map2.put("signidDate1", complete.get(complete.size()-1).getSignidDate());
					sustain=yhLifeHabitSignAppMapper.ShowDaysStatistics(map2);
				}
				
				YhLifeHabitPersonDictApp person=new YhLifeHabitPersonDictApp();
				person.setId(Integer.valueOf(strid[i]));
				person.setContinueDay(sustain);
				person.setTotalDay(complete.size());
				yhLifeHabitPersonDictAppMapper.updateByPrimaryKeySelective(person);
			}		
			
		}
		
		
		
		return Result.ok();
	}
}
