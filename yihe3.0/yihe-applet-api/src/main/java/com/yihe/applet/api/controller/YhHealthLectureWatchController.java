package com.yihe.applet.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.impl.Row;
import com.yihe.applet.api.domain.YhHealthLectureInfoApp;
import com.yihe.applet.api.domain.YhHealthLectureWatchApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.service.YhHealthLectureWatchAppService;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api/YhHealthLectureWatch")
@Api(value = "YhHealthLectureWatch控制器", description = "YhHealthLectureWatch管理")
public class YhHealthLectureWatchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthLectureWatchController.class);
	@Autowired
	YhHealthLectureWatchAppService  yhHealthLectureWatchAppService;
	@Autowired
	YhUserReginfoAppService  yhUserReginfoAppService;

	
	/**
	 * 添加用户课堂观看数据
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/addHealthLectureWatchData")
	@ResponseBody
	public Result addHealthLectureWatchData(YhHealthLectureWatchApp yhHealthLectureWatchApp,String birthDate,Integer currentTime) {
		 int row = 0;
		
		 SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		 if(birthDate!=null && !"".equals(birthDate)) {
			 String timedeta2 = birthDate.substring(0, birthDate.length() - 9);
			 try {
					java.util.Date mydate = myFormatter.parse(timedeta2);
					  //System.out.println(getAge(mydate));
					yhHealthLectureWatchApp.setAge(getAge(mydate));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		/* yhHealthLectureWatchApp.setName(userReginfo.getName());
		 yhHealthLectureWatchApp.setMobile(userReginfo.getMobile());*/
		 String userCode = yhHealthLectureWatchApp.getUserCode();
		 Integer classId = yhHealthLectureWatchApp.getClassId();
		  YhHealthLectureWatchApp selectclassIdByParam = yhHealthLectureWatchAppService.selectclassIdByParam(userCode, classId);
		
		  
		  if(selectclassIdByParam == null) {
			 yhHealthLectureWatchApp.setWatchDate(new Date());
			 if(birthDate != null) {
				 row = yhHealthLectureWatchAppService.insertSelective(yhHealthLectureWatchApp); 
			 }
				
		 }else{
			 if(selectclassIdByParam.getTypeid()==1) {
					yhHealthLectureWatchApp.setWatchDate(new Date());
					
					 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date2 =new Date();
						if(currentTime!=null && !"".equals(currentTime)) {
							date2.setSeconds(date2.getSeconds() + currentTime);
							yhHealthLectureWatchApp.setEndTime(date2);
							System.out.println(date2+"时间2");
						}
						yhHealthLectureWatchApp.setWatchid(selectclassIdByParam.getWatchid());
						row = yhHealthLectureWatchAppService.updateByPrimaryKeySelective(yhHealthLectureWatchApp);
						
				}
			 	
				
			 //"2".equals(selectclassIdByParam.getStatus()) && 
			 if( selectclassIdByParam.getTypeid()==5) {
				 yhHealthLectureWatchApp.setWatchDate(new Date());
				// YhUserReginfoApp findYhUserReginfo = yhUserReginfoAppService.findYhUserReginfo(userCode);
				 
					yhHealthLectureWatchApp.setWatchid(selectclassIdByParam.getWatchid());
					Date date2 =new Date();
					if(currentTime!=null && !"".equals(currentTime)) {
						date2.setSeconds(date2.getSeconds() + currentTime);
						yhHealthLectureWatchApp.setEndTime(date2);
						System.out.println(date2+"时间333");
					}
				 row = yhHealthLectureWatchAppService.updateByPrimaryKeySelective(yhHealthLectureWatchApp);
			 }
	    	
	    		
		}
	
		
		return Result.ok(row);
		
}
	/*public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String createdate = sdf.format(date);
		//createdate.setTime(createdate.getTime() + 1000);
		System.out.println("当前时间      ："+createdate);
		System.out.println("当前时间 2     ："+sdf.format(date));
	
	}
	*/
	/*public static void main(String[] args) throws ParseException {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 =new Date();
		date2.setSeconds(date.getSeconds() + 50);
		String createdate = df.format(date);
		System.out.println("当前时间      ："+createdate);
		System.out.println("当前时间  2    ："+df.format(date2));
		
	
	}*/
	
	@RequestMapping(value = "/selectclassIdByParam")
	@ResponseBody
	public YhHealthLectureWatchApp selectclassIdByParam(String userCode,Integer classId) {
		YhHealthLectureWatchApp selectclassIdByParam = yhHealthLectureWatchAppService.selectclassIdByParam(userCode, classId);
		return selectclassIdByParam;
	}
	
	
	
	
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
}
