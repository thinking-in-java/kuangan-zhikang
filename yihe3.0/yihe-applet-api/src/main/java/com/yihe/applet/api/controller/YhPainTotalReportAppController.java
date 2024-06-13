package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhPainResolveApp;
import com.yihe.applet.api.domain.YhPainTotalReportApp;
import com.yihe.applet.api.domain.YhPainTotalReportRecordApp;
import com.yihe.applet.api.service.YhPainTotalReportAppService;
import com.yihe.applet.api.service.YhPainTotalReportRecordAppService;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/YhPainTotalReportApp")
public class YhPainTotalReportAppController {
	@Autowired
	YhPainTotalReportAppService yhPainTotalReportAppService;
	@Autowired
	YhUserReginfoService yhUserReginfoService;
	@Autowired
	YhPainTotalReportRecordAppService yhPainTotalReportRecordAppService;
	
	public static int getAge(Date dateOfBirth) {
	       int age = 0;
	       Calendar born = Calendar.getInstance();
	       Calendar now = Calendar.getInstance();
	       if (dateOfBirth != null) {
	           now.setTime(new Date());
	           born.setTime(dateOfBirth);
	           if (born.after(now)) {
	               throw new IllegalArgumentException("年龄不能超过当前日期");
	           }
	           age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
	           int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
	           int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
	           System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
	           if (nowDayOfYear < bornDayOfYear) {
	               age -= 1;
	           }
	
	       }
	
	       return age;
	
	   }
	/**
	 * 添加评估报告数据
	 * @param yhPainTotalReportApp
	 * @return
	 */
	@RequestMapping(value = "addYhPainTotalReport")
	@ResponseBody
	public Result addYhPainTotalReport(YhPainTotalReportApp yhPainTotalReportApp,String sumData) {
		YhPainTotalReportRecordApp recordApp = new YhPainTotalReportRecordApp();
		YhUserReginfo yhUserReginfo = yhUserReginfoService.selectByPrimaryKey(yhPainTotalReportApp.getUserCode());
		YhPainTotalReportApp byParam = yhPainTotalReportAppService.selectYhPainResolveLimitByParam(yhPainTotalReportApp.getUserCode());
		if(byParam==null) {
			yhPainTotalReportApp.setTimes(1);
		}else {
			yhPainTotalReportApp.setTimes(byParam.getTimes()+1);
		}
		yhPainTotalReportApp.setGender(yhUserReginfo.getGender());
		int age = getAge(yhUserReginfo.getBirthDate()); 
		yhPainTotalReportApp.setAge(age);
		yhPainTotalReportApp.setUserName(yhUserReginfo.getUserName());
		
		yhPainTotalReportApp.setQtime(new Date());
		int insertSelective = yhPainTotalReportAppService.insertSelective(yhPainTotalReportApp);
		recordApp.setReportId(yhPainTotalReportApp.getReportId());  
		recordApp.setUserCode(yhPainTotalReportApp.getUserCode());
		recordApp.setResultTime(new Date());
		recordApp.setAssessImproved(sumData);
		yhPainTotalReportRecordAppService.insertSelective(recordApp);
		return Result.ok(insertSelective);
		
	}
	
	public static String dateDiff(String startTime,String endTime) {
        
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     long between = 0;
     try {
         java.util.Date begin = dfs.parse(startTime);
         java.util.Date end = dfs.parse(endTime);
         between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
     } catch (Exception ex) {
         ex.printStackTrace();
     }
     long day = between / (24 * 60 * 60 * 1000);
     long hour = (between / (60 * 60 * 1000) - day * 24);
     long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
     long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
     System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
    if(day>0) {
      return day + "天前";
    }
    if(day <= 0 && hour > 0) {
      return hour + "小时前";
    }
    if(hour <= 0 && min > 0) {
      return min + "分钟前";
    }
    if(s < 60) {
      return s + "秒前";
    }
  
   return "";
        
   }
	/**
	 * 查询最新评估数据和评估人次
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "/selectYhPainResolveLimitByParam",method = RequestMethod.POST)
	@ResponseBody
	public Result selectYhPainResolveLimitByParam (String userCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		YhPainTotalReportApp yhPainTotalReportApp = yhPainTotalReportAppService.selectYhPainResolveLimitByParam(userCode);
		Date date = new Date();
		//设置要获取到什么样的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(yhPainTotalReportApp!=null) {
			String endTime = sdf.format(date);
			String startTime = sdf.format(yhPainTotalReportApp.getQtime());
			String rusult = dateDiff(startTime,endTime);
			map.put("rusult", rusult);
		}
		int count = yhPainTotalReportAppService.YhPainTotalReportAppCount(userCode);
		map.put("yhPainTotalReportApp", yhPainTotalReportApp);
		map.put("count", count);
		return Result.ok(map);
		
	}
	@RequestMapping(value = "/selectAppraisalreportByParam",method = RequestMethod.POST)
	@ResponseBody
	public Result selectAppraisalreportByParam (String userCode) {
		List<YhPainResolveApp> list = yhPainTotalReportAppService.selectAppraisalreportByParam(userCode);
		return Result.ok(list);
		
	}
	

}
