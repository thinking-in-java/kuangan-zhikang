/**
 * 
 */
package com.yihe.applet.api.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.DecimalFormat;
import com.yihe.applet.api.domain.YhDiseaseAssessTotalReportApp;
import com.yihe.applet.api.domain.YhFoodCategoryApi;
import com.yihe.applet.api.domain.YhFoodCookbook2;
import com.yihe.applet.api.domain.YhSportCategoryApp;
import com.yihe.applet.api.domain.YhUserLifeDiaryApp;
import com.yihe.applet.api.domain.YhUserLifeDiaryAppWithBLOBs;
import com.yihe.applet.api.mapper.YhDiseaseAssessTotalReportAppMapper;
import com.yihe.applet.api.mapper.YhFoodCategoryApiMapper;
import com.yihe.applet.api.mapper.YhFoodSrecipeApiNewMapper;
import com.yihe.applet.api.mapper.YhFoodSrecipeRecordAppMapper;
import com.yihe.applet.api.mapper.YhSportCategoryAppMapper;
import com.yihe.applet.api.mapper.YhUserLifeDiaryAppMapper;
import com.yihe.applet.api.util.common.util.DateUtil;
import com.yihe.common.utils.DateUtils;
import com.yihe.system.domain.FoodArray;
import com.yihe.system.domain.YhFoodUserDiary;
import com.yihe.system.domain.YhSleepUserDiary;
import com.yihe.system.domain.YhSportDiary;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.domain.YhSportWeekly;
import com.yihe.system.domain.YhUserFoodWeeklyWeb;
import com.yihe.system.domain.YhUserNotifyVo;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhFoodSrecipeRecordMapper;
import com.yihe.system.mapper.YhFoodUserDiaryMapper;
import com.yihe.system.mapper.YhSleepUserDiaryMapper;
import com.yihe.system.mapper.YhSportDiaryMapper;
import com.yihe.system.mapper.YhSportMotionMapper;
import com.yihe.system.mapper.YhUserFoodGuideMapper;
import com.yihe.system.mapper.YhUserFoodWeeklyWebMapper;
import com.yihe.system.mapper.YhUserNotifyMapper;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.service.YhSleepUserDiaryService;
import com.yihe.system.service.YhSportMotionService;
import com.yihe.system.service.impl.YhFoodUserDiaryServiceImpl;
import com.yihe.system.utils.StringUtil;

/**
 * 月，季，年报告
 * @author dong
 *
 */
@Service
public class TimingAppServiceImpl {
	@Autowired
	private YhFoodSrecipeRecordMapper YhFoodSrecipeRecordMapper;
	@Autowired
	private YhFoodUserDiaryMapper yhFoodUserDiaryMapper;
	@Autowired
	private YhFoodSrecipeRecordAppServiceImpl YhFoodSrecipeRecordAppServiceImpl;
	@Autowired
	private YhFoodCookbook2ServiceImpl YhFoodCookbook2ServiceImpl;
	@Autowired
	private YhUserFoodWeeklyWebMapper YhUserFoodWeeklyWebMapper;
	@Autowired
	private YhSleepUserDiaryMapper yhSleepUserDiaryMapper;
	@Autowired
	private YhSportMotionMapper yhSportMotionMapper;
	@Autowired
	private YhDiseaseAssessTotalReportAppMapper yhDiseaseAssessTotalReportAppMapper;
	@Autowired
	private YhFoodCategoryApiMapper yhFoodCategoryApiMapper;
	@Autowired
	private YhSportDiaryMapper yhSportDiaryMapper;
	@Autowired
	YhSportMotionService yhSportMotionService;
	@Autowired
	YhSportCategoryAppMapper yhSportCategoryAppMapper;
	@Autowired
	YhUserReginfoMapper yhUserReginfoMapper;
	@Autowired
	private YhSleepUserDiaryService yhSleepUserDiaryService;
	@Autowired
	private YhUserLifeDiaryAppMapper yhUserLifeDiaryAppMapper;
	@Autowired
	private YhUserNotifyMapper YhUserNotifyMapper;
	
	
	public static void main(String[] args) {
		Calendar calendar = new GregorianCalendar();
		
		
		// 3.当前月的第一天
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println("当前月的第一天：" + print(calendar.getTime()));
		// 4.当前月的最后一天
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		System.out.println("当前月的最后一天：" + print(calendar.getTime()));
		
		 // 季度初
	    calendar.setTime(new Date());
	    int month = getQuarterInMonth(calendar.get(Calendar.MONTH), true);
	    calendar.set(Calendar.MONTH, month);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    System.out.println("当季度的第一天：" + print(calendar.getTime()));
	    // 季度末
	    calendar.setTime(new Date());
	    month = getQuarterInMonth(calendar.get(Calendar.MONTH), false);
	    calendar.set(Calendar.MONTH, month + 1);
	    calendar.set(Calendar.DAY_OF_MONTH, 0);
	    System.out.println("当前时间的季度末：" + print(calendar.getTime()));	
	    
	 // 年初
	    calendar.setTime(new Date());
	    month = getQuarterInMonth(calendar.get(Calendar.MONTH), false);
	    calendar.set(Calendar.MONTH,0);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    System.out.println("当前时间的年初：" + print(calendar.getTime()));	
	}
	
	private static String print(Date d) {
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    return df.format(d);
	}
	
	
	public static   double   convert(double   value){   
        long   l1   =   Math.round(value*100);   //四舍五入   
        double   ret   =   l1/100.0;               //注意：使用   100.0   而不是   100   
        return   ret;   
    }  

	// 返回第几个月份，不是几月
	// 季度一年四季， 第一季度：2月-4月， 第二季度：5月-7月， 第三季度：8月-10月， 第四季度：11月-1月
	private static int getQuarterInMonth(int month, boolean isQuarterStart) {
	    int months[] = { 1, 4, 7, 10 };
	    if (!isQuarterStart) {
	        months = new int[] { 3, 6, 9, 12 };
	    }
	    if (month >= 2 && month <= 4)
	        return months[0];
	    else if (month >= 5 && month <= 7)
	        return months[1];
	    else if (month >= 8 && month <= 10)
	        return months[2];
	    else
	        return months[3];
	}
	/**
	 * 生活日记统计
	 * @param type 类型（1，月2，季3，年）
	 * @return
	 * @throws ParseException 
	 */
	public int livereport(int type) throws ParseException {
		
		List<String> findWeekInfoForUserCode=new ArrayList<String>();
		Set<String> set=new HashSet<String>();
		if(type==1) {
			// 月记录了的用户集合
			findWeekInfoForUserCode.addAll(yhFoodUserDiaryMapper.showfoodformatMonth());
			findWeekInfoForUserCode.addAll(yhSportMotionMapper.showsportformatMonth());
			findWeekInfoForUserCode.addAll(yhSleepUserDiaryMapper.showsleepformatMonth());
		}else if(type==2){
			// 季记录了的用户集合
			findWeekInfoForUserCode.addAll(yhFoodUserDiaryMapper.showfoodQujilu());
			findWeekInfoForUserCode.addAll(yhSportMotionMapper.showsportQujilu());
			findWeekInfoForUserCode.addAll(yhSleepUserDiaryMapper.showsleepQujilu());
		}else {
			findWeekInfoForUserCode.addAll(yhFoodUserDiaryMapper.showfoodNianjilu());
			findWeekInfoForUserCode.addAll(yhSportMotionMapper.showsportNianjilu());
			findWeekInfoForUserCode.addAll(yhSleepUserDiaryMapper.showsleepNianjilu());
		}
		for (String string : findWeekInfoForUserCode) {
			set.add(string);
		}
		
		for (String usercode : set) {
			YhUserLifeDiaryAppWithBLOBs app=new YhUserLifeDiaryAppWithBLOBs();
			app=addYhUsersleepWeekly(type,app,usercode);//增加睡眠报告
			
			app=addYhUsersportWeekly(type,app,usercode);//增加运动报告
			
			app=addYhUserFoodWeekly(type,app,usercode);//增加膳食报告
			
			app.setUserCode(usercode);
			app.setType(type);
			
			 Date currentTime = new Date();
			   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			   String dateString = formatter.format(currentTime);//获取现在时间
			   Calendar calendar = new GregorianCalendar();
			YhUserNotifyVo tify = new YhUserNotifyVo();
			if(type==1) {
				tify.setTitle("月度生活日记统计报告");
				tify.setContent("您的月度生活日记统计报告，已生成立即查看详情>>");
				//当前月的第一天
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				app.setBeginTime(print(calendar.getTime()));
				app.setFinishTime(dateString);
			}else if(type==2) {
				tify.setTitle("季度生活日记统计报告");
				tify.setContent("您的季度生活日记统计报告，已生成立即查看详情>>");
				 // 季度初
			    calendar.setTime(new Date());
			    int month = getQuarterInMonth(calendar.get(Calendar.MONTH), true);
			    calendar.set(Calendar.MONTH, month);
			    calendar.set(Calendar.DAY_OF_MONTH, 1);
				app.setBeginTime(print(calendar.getTime()));
				app.setFinishTime(dateString);
			}else {
				tify.setTitle("年度生活日记统计报告");
				tify.setContent("您的年度生活日记统计报告，已生成立即查看详情>>");
				 // 年初
			    calendar.setTime(new Date());
			    calendar.set(Calendar.MONTH,0);
			    calendar.set(Calendar.DAY_OF_MONTH, 1);
				app.setBeginTime(print(calendar.getTime()));
				app.setFinishTime(dateString);
			}
			yhUserLifeDiaryAppMapper.insertSelective(app);
			
			Date date=new Date();
			
			tify.setUserId(usercode);
			tify.setSendDate(date);
			tify.setToServiceId(6);
			tify.setNewsId(app.getId());
			YhUserNotifyMapper.insertSelective(tify);
		}
		
		
		return 0;
	}
	
	
	
	/**
	 * 添加睡眠报告部分
	 * @param type
	 * @param app
	 * @param
	 * @return
	 * @throws ParseException 
	 */
	public YhUserLifeDiaryAppWithBLOBs addYhUsersleepWeekly(int type,YhUserLifeDiaryAppWithBLOBs app,String usercode) throws ParseException{
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			SimpleDateFormat sdf3 = new SimpleDateFormat("HH");
			List<YhSleepUserDiary> list=new ArrayList<YhSleepUserDiary>();
			if(type==1) {//本月
				list=yhSleepUserDiaryService.showSleepMonthUser(usercode);
			}else if(type==2) {//季度
				list=yhSleepUserDiaryService.showSleepQuarter(usercode);
			}else {
				list=yhSleepUserDiaryService.showSleepYear(usercode);
			}
			if(list.size()>0) {
				String[] huors = new String[list.size()];
				String[] earlys = new String[list.size()];
					for (int i = 0; i < list.size(); i++) {
						Date hour = list.get(i).getSleepTime(); 
						Date early = list.get(i).getWakeTime();
						 huors[i] = sdf3.format(hour);
						 earlys[i] = sdf2.format(early);
					}
//					map1.put("huors", huors);//每天早睡
//					map1.put("earlys", earlys);//每天起床
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
			        
				 double num = 0;
				if(type==1) {
					num=list.size()/(30/7);
				}else if(type==2){
					num=list.size()/(90/7);
				}else {
					num=list.size()/(365/7);
				}
				double duration=Double.valueOf(resultStr)/list.size();
				if(num==0) {
					app.setSleepWeek("0");//睡眠记录天数
				}else {
					app.setSleepWeek(String.valueOf(num));//睡眠记录天数
				}
				
				double   ret   =   convert(duration);
				app.setSleepDuration(String.valueOf(ret));//睡眠时长
				if(duration<6) {//睡眠时长
					String content="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span>日平均睡眠时长："+df.format(duration)+"小时，睡眠<span style=\"color: red;\">不足</span>，建议适当增加睡眠时长，至少达到6小时/天，7-8个小时/天为宜。</span></div></div>";
					String content1="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">快速入睡方法：</div></div>"
							+ "<div style=\"margin-top: -10px;margin-left: 15px;\">· 调暗房间照明</div><div style=\"margin-top: -10px;margin-left: 15px;\">· 进行轻度拉伸，勿剧烈运动</div><div style=\"margin-top: -10px;margin-left: 15px;\">· 播放舒缓音乐</div><div style=\"margin-top: -10px;margin-left: 15px;\">· 洗澡，水温在38-40℃为宜</div><div style=\"margin-top: -10px;margin-left: 15px;\">· 关闭电脑、手机</div><div style=\"margin-top: -10px;margin-left: 15px;\">· 就寝。</div>";
					app.setSleepDurationContent(content+content1);
				}else if(duration>9) {
					String content="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span>日平均睡眠时长："+df.format(duration)+"小时，睡眠<span style=\"color: red;\">过多</span>，睡觉时间长和睡觉时间短一样不利于健康，建议适当调整。</span></div></div>";
					app.setSleepDurationContent(content);
				}else {
					String content="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span>日平均睡眠时长："+df.format(duration)+"小时，睡眠充足，建议继续保持充足的睡眠，让每一天都精力充沛。</span></div></div>";
					app.setSleepDurationContent(content);
				}
			
				
				 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
		        Date date = simpleDateFormat.parse(averagesleep);     
		        String dateString = simpleDateFormat.format(date);
		        long ts = date.getTime();//平均入睡时间
		        
		        if(Integer.valueOf(dateString)<21) {
		        String timecon="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span>晚上睡觉最佳时间在21点-22点半之间，您的平均入睡时间："+averagesleep+"。睡眠的时间不宜过早，建议适当调整，科学睡眠，促健康。</span></div></div>";
				app.setSleepTimeContent(timecon);
		        }else if(Integer.valueOf(dateString)>22) {
		        	  String timecon="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span>晚上睡觉最佳时间在21点-22点半之间，您的平均入睡时间："+averagesleep+"。睡眠的时间不宜过晚，建议适当调整，早睡早起，身体好。</span></div></div>";
						app.setSleepTimeContent(timecon);
		        }else {
		        	  String timecon="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span>晚上睡觉最佳时间在21点-22点半之间，您的平均入睡时间："+averagesleep+"。非常好，建议继续保持。</span></div></div>";
						app.setSleepTimeContent(timecon);
		        }
		}else {
			app.setSleepWeek("0");//睡眠记录天数
			app.setSleepDuration("0");//睡眠时长
			String timecon="<div class=\"cu-bar bg-white solid-bottom\" style=\\\"margin-top: -50px;\\\"><div class=\"action\"><span>记录每天的睡眠日记，了解自己作息时间的合理性，睡眠指导，从记录睡眠日记开始。\r\n" + 
					"日出而作，日落而息，是符合生命规律的。晚上睡觉最佳时间在21点-22点半之间，根据个人的具体情况，睡眠时间在7-8个小时为宜。根据个人的体质和具体情况，睡眠的时间不宜过早，更不能过晚，要保证充足的睡眠时间。</span></div></div>";
			app.setSleepTimeContent(timecon);
		}
		return app;
		
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
	 * 添加运动报告部分
	 * @param type
	 * @param app
	 * @param
	 * @return
	 */
	public YhUserLifeDiaryAppWithBLOBs addYhUsersportWeekly(int type,YhUserLifeDiaryAppWithBLOBs app,String usercode){
			List<YhSportDiary> list=new ArrayList<YhSportDiary>();
			if(type==1) {//本月
				list = yhSportDiaryMapper.showSportMonthUser(usercode);
			}else if(type==2) {//季度
				list = yhSportDiaryMapper.showSportQuarter(usercode);
			}else {
				list = yhSportDiaryMapper.showSportYear(usercode);
			}
			
			if(list.size()>0) {
				
				YhUserReginfo info=yhUserReginfoMapper.selectByPrimaryKey(usercode);//查询用户表
			double uweight=0;//平均能耗推荐
			double tenergy=0;//平均能耗实际
			int number=0;//天数
			double duration=0;//运动时长
			
			
			double walk=0;//步行
			double recommend=0;//推荐
			double boutique=0;//精品
			double rests=0;//其他
			
			
			int aerobic=0;//有氧
			int anaerobic=0;//无氧
			int pliable=0;//柔韧
			int arthrosis=0;//关节
			int j=0;
			List<FoodArray> array=new ArrayList<FoodArray>();
			for (YhSportDiary yhSportDiary : list) {
				if(yhSportDiary.getUweight()!=null) {
					uweight+=Double.valueOf(yhSportDiary.getUweight());
				}
				if(yhSportDiary.getTenergy()!=null) {
					tenergy+=Double.valueOf(yhSportDiary.getTenergy());
				}
				if(Double.valueOf(yhSportDiary.getTenergy())>=Double.valueOf(yhSportDiary.getUweight())) {
					number++;
				}
				
				if(yhSportDiary.getRemark()!=null) {
					duration+=Double.valueOf(yhSportDiary.getRemark());
				}
				
				j++;
				String sKcalId="";
				//其他运动
				if(yhSportDiary.getDiaryDetails()!=null){
				String diaryDetails=yhSportDiary.getDiaryDetails();
				diaryDetails=diaryDetails.substring(0, diaryDetails.length());
				for (String diaryDetail : diaryDetails.split(";")) {
					sKcalId=diaryDetail.split("=")[0];
					rests+=Double.valueOf(diaryDetail.split("=")[1]);
					YhSportMotion wellSportKcall = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sKcalId));//运动表
					if(wellSportKcall!=null){
						String[] classity = wellSportKcall.getSportClassity().split(";");//运动分类
						
						for (int i = 0; i < classity.length; i++) {
							YhSportCategoryApp cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
							if(cate!=null) {
								if(cate.getCateName().equals("无氧运动")) {
									if(j>anaerobic) {
										anaerobic+=1;
									}
								}else if(cate.getCateName().equals("有氧运动")) {
									if(j>aerobic) {
										aerobic+=1;
									}
								}else if(cate.getCateName().equals("柔韧运动")) {
									if(j>pliable+1) {
										pliable+=1;
									}
								}else if(cate.getCateName().equals("关节活动")) {
									if(j>arthrosis+1) {
										arthrosis+=1;
									}
								}
							}
							
						}
						
					}	
				}
			}
				
				
				
				
				//精品课程
				if(yhSportDiary.getDiaryBoutique()!=null){
					String diaryDetails=yhSportDiary.getDiaryBoutique();
					
					for (String diaryDetail : diaryDetails.split(";")) {
						sKcalId=diaryDetail.split("=")[0];
						boutique+=Double.valueOf(diaryDetail.split("=")[1]);
						YhSportMotion wellSportKcall = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sKcalId));//运动表
						if(wellSportKcall!=null){
							String[] classity = wellSportKcall.getSportClassity().split(";");//运动分类
							
							for (int i = 0; i < classity.length; i++) {
								YhSportCategoryApp cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
								if(cate!=null) {
									if(cate.getCateName().equals("无氧运动")) {
										if(j>anaerobic) {
											anaerobic+=1;
										}
									}else if(cate.getCateName().equals("有氧运动")) {
										if(j>aerobic) {
											aerobic+=1;
										}
									}else if(cate.getCateName().equals("柔韧运动")) {
										if(j>pliable+1) {
											pliable+=1;
										}
									}else if(cate.getCateName().equals("关节活动")) {
										if(j>arthrosis+1) {
											arthrosis+=1;
										}
									}
								}
							}
							
						}	
					}
					
					}
				
				//推荐运动
				if(yhSportDiary.getDiaryRecommend()!=null){
					String diaryDetails=yhSportDiary.getDiaryRecommend();
					for (String diaryDetail : diaryDetails.split(";")) {
						sKcalId=diaryDetail.split("=")[0];
						recommend+=Double.valueOf(diaryDetail.split("=")[1]);
						YhSportMotion wellSportKcall = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sKcalId));//运动表
						if(wellSportKcall!=null){
							String[] classity = wellSportKcall.getSportClassity().split(";");//运动分类
							
							for (int i = 0; i < classity.length; i++) {
								YhSportCategoryApp cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
								if(cate!=null) {
									if(cate.getCateName().equals("无氧运动")) {
										if(j>anaerobic) {
											anaerobic+=1;
										}
									}else if(cate.getCateName().equals("有氧运动")) {
										if(j>aerobic) {
											aerobic+=1;
										}
									}else if(cate.getCateName().equals("柔韧运动")) {
										if(j>pliable+1) {
											pliable+=1;
										}
									}else if(cate.getCateName().equals("关节活动")) {
										if(j>arthrosis+1) {
											arthrosis+=1;
										}
									}
								}
							}
							
						}	
					}
					
					}
				
				
				
				
				
				if(yhSportDiary.getNumber()!=null) {
					String[] stepArr = yhSportDiary.getNumber().split("=");
					walk+=Double.valueOf(stepArr[1]);
					if(j>aerobic) {
						aerobic+=1;
					}
				}
				
				
			}
			
			
			
			double num = 0;
			double duration2=0f;//平均每周运动时长
			if(type==1) {
				num=list.size()/(30/7);
				duration2=(duration/60)/(30/7);
				aerobic=aerobic/(30/7);
				anaerobic=anaerobic/(30/7);
				pliable=pliable/(30/7);
				arthrosis=arthrosis/(30/7);
			}else if(type==2){
				num=list.size()/(90/7);
				duration2=(duration/60)/(90/7);
				aerobic=aerobic/(90/7);
				anaerobic=anaerobic/(90/7);
				pliable=pliable/(90/7);
				arthrosis=arthrosis/(90/7);
			}else {
				num=list.size()/(365/7);
				duration2=(duration/60)/(365/7);
				aerobic=aerobic/(365/7);
				anaerobic=anaerobic/(365/7);
				pliable=pliable/(365/7);
				arthrosis=arthrosis/(365/7);
			}
		
	        
			double   num1   =   convert(num);
			
			double   uwei   =   convert(uweight/list.size());
			
			double   tene   =   convert(tenergy/list.size());
			if(num==0) {
				app.setSportPracticalNum("0");//运动实际情况
			}else {
				app.setSportPracticalNum(String.valueOf(num));//运动实际情况
			}
			
			app.setSportRecommendHeat(String.valueOf(uwei));//运动推荐耗量
			app.setSportPracticalHeat(String.valueOf(tene));//运动实际总耗量
			Date date=new Date();
			int nut=0;
			if(info.getBirthDate()!=null) {
				nut=dayCompare(info.getBirthDate(), date);//年龄
			}
			
			
			if(nut<=64) {//成年人（18~64岁）：150分钟 / 每周
				String recommendcoum="";
				String recommendcoum1="";
				if(duration2>=150) {//运动充足
					recommendcoum="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">为获得更多的健康效益，您可以增加有氧身体活动，达到300分钟/周。</div></div>";
					String wuyang="";
					if(anaerobic>=2) {
						wuyang=anaerobic+"天，充足;";
					}else {
						wuyang=anaerobic+"天，<span style=\"color: red;\">不足</span>;";
					}
					String rren="";
					if(pliable>=2) {
						rren=anaerobic+"天，充足;";
					}else {
						rren=anaerobic+"天，<span style=\"color: red;\">不足</span>;";
					}
					
					String gjie="";
					if(pliable>=4) {
						gjie=anaerobic+"天，充足。";
					}else {
						gjie=anaerobic+"天，<span style=\"color: red;\">不足</span>。";
					}
					recommendcoum1="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span> 您当前平均每周的有氧运动时间为："+convert(duration2)+"分钟。您每周的无氧运动(2天/周)，"+wuyang+"柔韧运动(2天/周)，"+rren+"关节活动(4天/周)，"+gjie+"除了有氧运动以外，日常锻炼要注意无氧、柔韧和关节活动的多样化锻炼方式。</span></div></div>";
				}else if(duration2>=130) {//运动中等
					recommendcoum="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">	<span> 身体活动总量是决定健康效益的关键。您平均每周的运动时间为："+convert(duration2)+"分钟，与150分钟/周的中等强度运动目标相差不远了。</span></div></div>";
					recommendcoum1="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">	可以通过调整运动频率、单次运动时长等方式来增加总量。</div></div>";
				}else {//＜推荐量的80%
					recommendcoum="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">	成年人身体活动建议：每周至少150分钟左右的中等强度运动。</div></div>";
					recommendcoum1="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span> 您平均每周的运动时间为："+convert(duration2)+"分钟，<span style=\"color: red;\">不足</span>，建议您在身体条件允许的前提下，适度增加身体活动量，可以获得更大的健康效益。</span></div></div>";
				}
				
				app.setSportRecommendContent(recommendcoum+recommendcoum1);
			}else {//老年人（65岁~）：300分钟 / 每周
				String recommendcoum="";
				String recommendcoum1="";
				if(duration2>=300) {//运动充足
					recommendcoum="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">为获得更多的健康效益，您可以增加有氧身体活动，达到300分钟/周。</div></div>";
					String wuyang="";
					if(anaerobic>=2) {
						wuyang=anaerobic+"天，充足;";
					}else {
						wuyang=anaerobic+"天，<span style=\"color: red;\">不足</span>;";
					}
					String rren="";
					if(pliable>=2) {
						rren=anaerobic+"天，充足;";
					}else {
						rren=anaerobic+"天，<span style=\"color: red;\">不足</span>;";
					}
					
					String gjie="";
					if(pliable>=4) {
						gjie=anaerobic+"天，充足。";
					}else {
						gjie=anaerobic+"天，<span style=\"color: red;\">不足</span>。";
					}
					recommendcoum1="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span> 您当前平均每周的有氧运动时间为："+duration2+"分钟。您每周的无氧运动(2天/周)，"+wuyang+"柔韧运动(2天/周)，"+rren+"关节活动(4天/周)，"+gjie+"除了有氧运动以外，日常锻炼要注意无氧、柔韧和关节活动的多样化锻炼方式。</span></div></div>";
				}else if(duration2>=250) {//运动中等
					recommendcoum="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">	<span> 身体活动总量是决定健康效益的关键。您平均每周的运动时间为："+duration2+"分钟，与300分钟/周的中等强度运动目标相差不远了。</span></div></div>";
					recommendcoum1="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">	可以通过调整运动频率、单次运动时长等方式来增加总量。</div></div>";
				}else {//＜推荐量的80%
					recommendcoum="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">	成年人身体活动建议：每周至少300分钟左右的中等强度运动。</div></div>";
					recommendcoum1="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span> 您平均每周的运动时间为："+duration2+"分钟，<span style=\"color: red;\">不足</span>，建议您在身体条件允许的前提下，适度增加身体活动量，可以获得更大的健康效益。</span></div></div>";
				}
				
				app.setSportRecommendContent(recommendcoum+recommendcoum1);
			}
			
			
		}else {
			app.setSportPracticalNum("0");//运动实际情况
			app.setSportRecommendHeat("240.0");//运动推荐耗量
			app.setSportPracticalHeat("0");//运动实际总耗量
			String recommendcoum="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">通过记录自己的运动日记，掌握自己的身体活动总量，试着养成记录运动日记的习惯吧。</div></div>";
			String recommendcoum1="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"><span>平常缺乏身体活动的人，可以从每周参加三次以上中等强度的身体活动开始。请注意：个人体质不同，所能承受的运动负荷也不同，根据自己的感觉判断运动强度是最方便实用的。</span></div></div>";
			app.setSportRecommendContent(recommendcoum+recommendcoum1);
		}
		return app;
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
	
	/**
	 * 添加膳食报告部分
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	public YhUserLifeDiaryAppWithBLOBs addYhUserFoodWeekly(int type,YhUserLifeDiaryAppWithBLOBs app,String usercode) throws ParseException {
		
		
		int insertSelective = 0;
			List<YhFoodUserDiary> findWeekInfo =new ArrayList<YhFoodUserDiary>();
			if(type==1) {//本月
				 findWeekInfo = yhFoodUserDiaryMapper.showfoodMonthUser(usercode);
			}else if(type==2) {
				findWeekInfo=yhFoodUserDiaryMapper.showfoodQuarter(usercode);
			}else {
				findWeekInfo=yhFoodUserDiaryMapper.showfoodYear(usercode);
			}
			if(findWeekInfo.size()>0) {
			// 定义超标天数
			int exceedingDays = 0;
			// 定义不重复食材数
			HashSet<String> classSet = new HashSet<String>();
			// 全部摄入
			BigDecimal energyKcalAll = new BigDecimal("0");
			// 全部蛋白质
				BigDecimal proteinAll =  new BigDecimal("0");
			// 全部脂肪
				BigDecimal fat =  new BigDecimal("0");
			// 全部碳水化合物
				BigDecimal carbohydrate =  new BigDecimal("0");


			/*
			 * 谷薯及杂豆类 蛋类 奶类及制品 蔬菜和菌藻 坚果、大豆及制品 饮料 食用油、油脂及制品 调味品 零食、点心、冷饮 菜肴 其它
			 * 畜禽肉类 水果 水产品 罐头
			 */

			BigDecimal type1Kacl = new BigDecimal("0");// 谷薯及杂豆类
			BigDecimal type2Kacl = new BigDecimal("0");//蔬菜和菌藻
			BigDecimal type3Kacl = new BigDecimal("0");//水果
			BigDecimal type4Kacl = new BigDecimal("0");//畜禽肉类
			BigDecimal type5Kacl = new BigDecimal("0");//水产品
			BigDecimal type6Kacl = new BigDecimal("0");//蛋类
			BigDecimal type7Kacl = new BigDecimal("0");//奶类及制品
			BigDecimal type8Kacl = new BigDecimal("0");//坚果、大豆及制品
			BigDecimal type9Kacl = new BigDecimal("0");//饮料
			BigDecimal type10Kacl = new BigDecimal("0");//食用油、油脂及制品
			BigDecimal type11Kacl = new BigDecimal("0");//调味品
			BigDecimal type12Kacl = new BigDecimal("0");//零食、点心、冷饮
			BigDecimal type13Kacl = new BigDecimal("0");//菜肴
			BigDecimal type14Kacl = new BigDecimal("0");//其它
			BigDecimal type15Kacl = new BigDecimal("0");//罐头

			// 本周推荐摄入与实际摄入
			String recommend = "";

			for (YhFoodUserDiary yhFoodUserDiary : findWeekInfo) {
				if (StringUtil.isEmpty(yhFoodUserDiary.getfElements())) {
					continue;
				}

				Long energyLevel = yhFoodUserDiary.getEnergyLevel();
				BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
				recommend = recommend + yhFoodUserDiary.getcTime() + "," + energyLevel + "," + energyKcal + ";";

				proteinAll = proteinAll.add(new BigDecimal(yhFoodUserDiary.getProtein()));

				fat = fat.add(new BigDecimal(yhFoodUserDiary.getFat()));



				carbohydrate = carbohydrate.add(new BigDecimal(yhFoodUserDiary.getCho()));


				energyKcalAll = energyKcalAll.add(energyKcal);
				if (energyKcal.compareTo(new BigDecimal(energyLevel))==1 ) {
					exceedingDays++;
				}
				String getfElements = yhFoodUserDiary.getfElements();
				String[] split = getfElements.split(";");
				for (String string2 : split) {
					String[] split2 = string2.split("_");
					String string5 = split2[0];
					// 餐次类型
					String string4 = split2[1];
					// 食物id
					String string3 = split2[2];
					// 克数
					String string7 = "";
					if ("10".equals(string5)) {
						String[] split3 = string2.split(",");
						for (int i = 1; i < split3.length; i++) {
							// 90354_47.5
							String string6 = split3[i];
							String[] split4 = string6.split("_");
							// foodid
							String string8 = split4[0];
							String string9 = split4[1];
							YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl
									.selectByPrimaryKey(Integer.valueOf(string8));
							if (null == foodInfo) {
								continue;
							}

							// 食物类型
							String getfType = foodInfo.getfType();

							// 食物每100克热量
							BigDecimal energyKcal2 = new BigDecimal(foodInfo.getEnergyKcal());

							BigDecimal rijiKacl = energyKcal2.divide(new BigDecimal("100")).multiply(new BigDecimal(string9));

							if ("1".equals(getfType)) {
								type1Kacl = type1Kacl.add(rijiKacl);
							}
							if ("2".equals(getfType)) {
								type2Kacl = type2Kacl.add(rijiKacl);
							}
							if ("3".equals(getfType)) {
								type3Kacl = type3Kacl.add(rijiKacl);
							}
							if ("4".equals(getfType)) {
								type4Kacl = type4Kacl.add(rijiKacl);
							}
							if ("5".equals(getfType)) {
								type5Kacl = type5Kacl.add(rijiKacl);
							}
							if ("6".equals(getfType)) {
								type6Kacl = type6Kacl.add(rijiKacl);
							}
							if ("7".equals(getfType)) {
								type7Kacl = type7Kacl.add(rijiKacl);
							}
							if ("8".equals(getfType)) {
								type8Kacl = type8Kacl.add(rijiKacl);
							}
							if ("9".equals(getfType)) {
								type9Kacl = type9Kacl.add(rijiKacl);
							}
							if ("10".equals(getfType)) {
								type10Kacl = type10Kacl.add(rijiKacl);
							}
							if ("11".equals(getfType)) {
								type11Kacl = type11Kacl.add(rijiKacl);
							}
							if ("12".equals(getfType)) {
								type12Kacl = type12Kacl.add(rijiKacl);
							}
							if ("13".equals(getfType)) {
								type13Kacl = type13Kacl.add(rijiKacl);
							}
							if ("14".equals(getfType)) {
								type14Kacl = type14Kacl.add(rijiKacl);
							}
							if ("15".equals(getfType)) {
								type15Kacl = type15Kacl.add(rijiKacl);
							}


						}
						String string6 = split3[0];
						String[] split4 = string6.split("_");
						// 克数
						string7 = split4[3];

					} else {
						// 克数
						string7 = split2[3];
						YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl
								.selectByPrimaryKey(Integer.valueOf(string3));
						if (null == foodInfo) {
							continue;
						}

						// 食物类型
						String getfType = foodInfo.getfType();

						// 食物每100克热量
						BigDecimal energyKcal2 = new BigDecimal(foodInfo.getEnergyKcal());

						BigDecimal rijiKacl =energyKcal2.divide(new BigDecimal("100")).multiply(new BigDecimal(string7));

						if ("1".equals(getfType)) {
							type1Kacl = type1Kacl.add(rijiKacl);
						}
						if ("2".equals(getfType)) {
							type2Kacl = type2Kacl.add(rijiKacl);
						}
						if ("3".equals(getfType)) {
							type3Kacl = type3Kacl.add(rijiKacl);
						}
						if ("4".equals(getfType)) {
							type4Kacl = type4Kacl.add(rijiKacl);
						}
						if ("5".equals(getfType)) {
							type5Kacl = type5Kacl.add(rijiKacl);
						}
						if ("6".equals(getfType)) {
							type6Kacl = type6Kacl.add(rijiKacl);
						}
						if ("7".equals(getfType)) {
							type7Kacl = type7Kacl.add(rijiKacl);
						}
						if ("8".equals(getfType)) {
							type8Kacl = type8Kacl.add(rijiKacl);
						}
						if ("9".equals(getfType)) {
							type9Kacl = type9Kacl.add(rijiKacl);
						}
						if ("10".equals(getfType)) {
							type10Kacl = type10Kacl.add(rijiKacl);
						}
						if ("11".equals(getfType)) {
							type11Kacl = type11Kacl.add(rijiKacl);
						}
						if ("12".equals(getfType)) {
							type12Kacl = type12Kacl.add(rijiKacl);
						}
						if ("13".equals(getfType)) {
							type13Kacl = type13Kacl.add(rijiKacl);
						}
						if ("14".equals(getfType)) {
							type14Kacl = type14Kacl.add(rijiKacl);
						}
						if ("15".equals(getfType)) {
							type15Kacl = type15Kacl.add(rijiKacl);
						}


					}

					classSet.add(string3);

				}

			}

		

			float weekValue1 = 0f;
			String tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTime(usercode);
			if (StringUtil.isEmpty(tuijianNum)) {
				tuijianNum = YhFoodSrecipeRecordAppServiceImpl.tuijianNum(usercode);
			}
			weekValue1 = weekValue1 + Integer.valueOf(tuijianNum);
//			for (String string2 : weekList) {
//				String tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTimeAndType2(string, string2);
//				if (StringUtil.isEmpty(tuijianNum)) {
//					tuijianNum = YhFoodSrecipeRecordMapper.findRecordKayForTime(string);
//					if (StringUtil.isEmpty(tuijianNum)) {
//						tuijianNum = YhFoodSrecipeRecordAppServiceImpl.tuijianNum(string);
//					}
//				}
//				weekValue1 = weekValue1 + Integer.valueOf(tuijianNum);
//			}


			double num = 0;
			if(type==1) {

				type1Kacl=type1Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type2Kacl=type2Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type3Kacl=type3Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type4Kacl=type4Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type5Kacl=type5Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type6Kacl=type6Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type7Kacl=type7Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type8Kacl=type8Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type9Kacl=type9Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type10Kacl=type10Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type11Kacl=type11Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type12Kacl=type12Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type13Kacl=type13Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type14Kacl=type14Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type15Kacl=type15Kacl.divide(new BigDecimal("30").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				num=findWeekInfo.size()/(30/7);
			}else if(type==2){
				type1Kacl=type1Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type2Kacl=type2Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type3Kacl=type3Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type4Kacl=type4Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type5Kacl=type5Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type6Kacl=type6Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type7Kacl=type7Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type8Kacl=type8Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type9Kacl=type9Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type10Kacl=type10Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type11Kacl=type11Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type12Kacl=type12Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type13Kacl=type13Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type14Kacl=type14Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type15Kacl=type15Kacl.divide(new BigDecimal("90").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				num=findWeekInfo.size()/(90/7);
			}else {
				type1Kacl=type1Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type2Kacl=type2Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type3Kacl=type3Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type4Kacl=type4Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type5Kacl=type5Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type6Kacl=type6Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type7Kacl=type7Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type8Kacl=type8Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type9Kacl=type9Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type10Kacl=type10Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type11Kacl=type11Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type12Kacl=type12Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type13Kacl=type13Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type14Kacl=type14Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				type15Kacl=type15Kacl.divide(new BigDecimal("365").divide(new BigDecimal("7"),2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP);
				num=findWeekInfo.size()/(365/7);
			}
	        
			double num1=convert(num);
			if(num==0) {
				app.setFoodPracticalNum("0");//实际记录天数每周
			}else {
				app.setFoodPracticalNum(String.valueOf(num));//实际记录天数每周
			}
			
			app.setFoodRecommendHeat(String.valueOf(convert(weekValue1)));//膳食推荐热量

			app.setFoodPracticalHeat(energyKcalAll.divide(new BigDecimal(findWeekInfo.size()),2,BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP).toString());//膳食实际总热量
			
			
			BigDecimal entire=proteinAll.add(fat).add(carbohydrate);//三大营养素总量
			// 蛋白质百分比
				BigDecimal proteinAll1 = proteinAll.divide(entire,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			// 全部脂肪百分比

				BigDecimal fat1 =	fat.divide(entire,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			// 全部碳水化合物百分比

				BigDecimal carbohydrate1 = 	carbohydrate.divide(entire,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			YhDiseaseAssessTotalReportApp total=yhDiseaseAssessTotalReportAppMapper.selectTotalReportByuserCodeByNewest(usercode);//根据usercode查询是否糖尿病
			if(total!=null && total.getDiab2His()!=null && total.getDiab2His()==1) {//患有糖尿病
				String tshhw="";
				String tshhw1="";
				if(carbohydrate1.compareTo(new BigDecimal("45"))==-1) {//碳水化合物
					tshhw="碳水化合物(45%~60%)，约"+carbohydrate1+"%，偏<span style=\"color: red;\">低</span>；";
					tshhw1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">碳水化合物偏<span style=\"color: red;\">低：谷类和薯类食物碳水化合物摄入较少，谷薯类是人体最经济的能量来源，建议增加谷物类食物的摄入，同时保证每天全谷物和杂豆类的摄入50-150克，相当于一天谷物的1/4-1/3。</div></div>";
				}else if(carbohydrate1.compareTo(new BigDecimal("60"))==1) {
					tshhw="碳水化合物(45%~60%)，约"+carbohydrate1+"%，偏<span style=\"color: red;\">高</span>；";
					tshhw1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">碳水化合物偏<span style=\"color: red;\">高</span>：谷类和薯类食物碳水化合物摄入较多，建议减少精米白面的摄入，适当增加全谷物和杂豆的比例，推荐每天吃全谷物和杂豆类食物50-150克，相当于一天谷物的1/4-1/3。</div></div>";
				}else {
					tshhw="碳水化合物(45%~60%)，约"+carbohydrate1+"%，合适；";
				}
				String dbz="";
				String dbz1="";
				if(proteinAll1.compareTo(new BigDecimal("15"))==-1) {//蛋白质
					dbz="蛋白质(15%～20%)，"+proteinAll1+"%，偏<span style=\"color: red;\">低</span>；";
					dbz1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">蛋白质偏<span style=\"color: red;\">低</span>：建议每日蛋白质摄入量为每公斤体重1.0g。长期蛋白质摄入偏低会影响身体的新陈代谢，身体免疫力下降等。推荐摄入动物优质蛋白、豆制品和奶类，同时注意动物蛋白和植物蛋白相互搭配。</div></div>";
				}else if(carbohydrate1.compareTo(new BigDecimal("20"))==1) {
					dbz1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">蛋白质偏<span style=\"color: red;\">高</span>：建议每日蛋白质摄入量为每公斤体重1.0g。长期蛋白质摄入过高会增加肾脏的负担，损伤肾脏。推荐摄入动物优质蛋白、豆制品和奶类，同时注意动物蛋白和植物蛋白相互搭配。</div></div>";
					dbz="蛋白质(15%～20%)，"+proteinAll1+"%，偏<span style=\"color: red;\">高</span>；";
				}else {
					dbz="蛋白质(15%～20%)，"+proteinAll1+"%，合适；";
				}
				String zf="";
				String zf1="";
				if(fat1.compareTo(new BigDecimal("20"))==-1) {//脂肪
					zf="脂肪(20%～35%)，"+fat1+"%，偏<span style=\"color: red;\">低</span>；";
					zf1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">脂肪偏<span style=\"color: red;\">低</span>：推荐每天烹调油摄入25-30g，长期脂肪摄入偏低会引起营养不良，会出现身体消瘦，肠胃功能下降。建议以植物性油脂为主。</div></div>";
				}else if(fat1.compareTo(new BigDecimal("35"))==1) {
					zf="脂肪(20%～35%)，"+fat1+"%，偏<span style=\"color: red;\">高</span>；";
					zf1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">脂肪偏<span style=\"color: red;\">高</span>：推荐每天烹调油摄入25-30g，高脂肪的食物有动物的肉、内脏，各类坚果如核桃仁、杏仁、花生仁、葵花籽仁等，每天禽畜肉摄入40-70g、坚果摄入10-15g。长期脂肪摄入偏高会引发肥胖、高血压、高血脂、冠心病等慢性疾病，建议以植物性油脂为主。</div></div>";
				}else {
					zf="脂肪(20%～35%)，"+fat1+"%，合适。";
				}
				String foodrationalnutrient="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"> 您摄入的三大营养素供能比例情况："+tshhw+""+dbz+""+zf+"</div></div>";//膳食营养素合理内容
				
				if(tshhw1.equals("") && dbz1.equals("") && zf1.equals("")) {
					foodrationalnutrient+="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">在控制好总量的前提下，请调整能量摄入比例。</div></div>";
				}else {
					foodrationalnutrient+=tshhw1+dbz1+zf1;
				}
				
				app.setFoodRationalNutrient(foodrationalnutrient);//膳食营养素合理内容
				
			}else {//没有患有糖尿病
				String tshhw="";
				String tshhw1="";
				if(carbohydrate1.compareTo(new BigDecimal("50"))==-1) {//碳水化合物
					tshhw="碳水化合物(50%~65%)，约"+carbohydrate1+"%，偏<span style=\"color: red;\">低</span>；";
					tshhw1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">碳水化合物偏<span style=\"color: red;\">低</span>：谷类和薯类食物碳水化合物摄入较少，谷薯类是人体最经济的能量来源，建议增加谷物类食物的摄入，同时保证每天全谷物和杂豆类的摄入50-150克，相当于一天谷物的1/4-1/3。</div></div>";
				}else if(carbohydrate1.compareTo(new BigDecimal("65"))==1) {
					tshhw="碳水化合物(50%~65%)，约"+carbohydrate1+"%，偏<span style=\"color: red;\">高</span>；";
					tshhw1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">碳水化合物偏<span style=\"color: red;\">高</span>：谷类和薯类食物碳水化合物摄入较多，建议减少精米白面的摄入，适当增加全谷物和杂豆的比例，推荐每天吃全谷物和杂豆类食物50-150克，相当于一天谷物的1/4-1/3。</div></div>";
				}else {
					tshhw="碳水化合物(50%~65%)，约"+carbohydrate1+"%，合适；";
				}
				String dbz="";
				String dbz1="";
				if(proteinAll1.compareTo(new BigDecimal("10"))==-1) {//蛋白质
					dbz="蛋白质(10%～15%)，"+proteinAll1+"%，偏<span style=\"color: red;\">低</span>；";
					dbz1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">蛋白质偏<span style=\"color: red;\">低</span>：建议每日蛋白质摄入量为每公斤体重1.0g。长期蛋白质摄入偏低会影响身体的新陈代谢，身体免疫力下降等。推荐摄入动物优质蛋白、豆制品和奶类，同时注意动物蛋白和植物蛋白相互搭配。</div></div>";
				}else if(carbohydrate1.compareTo(new BigDecimal("15"))==1) {
					dbz1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">蛋白质偏<span style=\"color: red;\">高</span>：建议每日蛋白质摄入量为每公斤体重1.0g。长期蛋白质摄入过高会增加肾脏的负担，损伤肾脏。推荐摄入动物优质蛋白、豆制品和奶类，同时注意动物蛋白和植物蛋白相互搭配。</div></div>";
					dbz="蛋白质(10%～15%)，"+proteinAll1+"%，偏<span style=\"color: red;\">高</span>；";
				}else {
					dbz="蛋白质(10%～15%)，"+proteinAll1+"%，合适；";
				}
				String zf="";
				String zf1="";
				if(fat1.compareTo(new BigDecimal("20"))==-1) {//脂肪
					zf="脂肪(20%～30%)，"+fat1+"%，偏<span style=\"color: red;\">低</span>；";
					zf1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">脂肪偏<span style=\"color: red;\">低</span>：推荐每天烹调油摄入25-30g，长期脂肪摄入偏低会引起营养不良，会出现身体消瘦，肠胃功能下降。建议以植物性油脂为主。</div></div>";
				}else if(fat1.compareTo(new BigDecimal("30"))==1) {
					zf="脂肪(20%～30%)，"+fat1+"%，偏<span style=\"color: red;\">高</span>；";
					zf1="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">脂肪偏<span style=\"color: red;\">高</span>：推荐每天烹调油摄入25-30g，高脂肪的食物有动物的肉、内脏，各类坚果如核桃仁、杏仁、花生仁、葵花籽仁等，每天禽畜肉摄入40-70g、坚果摄入10-15g。长期脂肪摄入偏高会引发肥胖、高血压、高血脂、冠心病等慢性疾病，建议以植物性油脂为主。</div></div>";
				}else {
					zf="脂肪(20%～30%)，"+fat1+"%，合适。";
				}
				String foodrationalnutrient="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\"> 您摄入的三大营养素供能比例情况："+tshhw+""+dbz+""+zf+"</div></div>";//膳食营养素合理内容
				
				if(tshhw1.equals("") && dbz1.equals("") && zf1.equals("")) {
					foodrationalnutrient+="<div class=\"cu-bar bg-white solid-bottom\"><div class=\"action\">在控制好总量的前提下，请调整能量摄入比例。</div></div>";
				}else {
					foodrationalnutrient+=tshhw1+dbz1+zf1;
				}
				
				app.setFoodRationalNutrient(foodrationalnutrient);//膳食营养素合理内容
			}
			

			List<String> excessive=new ArrayList<String>();//过量
			List<String> insufficient=new ArrayList<String>();//不足
			List<YhFoodCategoryApi> api=yhFoodCategoryApiMapper.findAllInfo();//查询食物分类表
			for (YhFoodCategoryApi category : api) {
				if(category.getFoodCategoryName().equals("谷薯及杂豆类")) {
					if(type1Kacl.compareTo(new BigDecimal(category.getEverydaymax()))==1) {//最大值
						excessive.add("谷薯及杂豆类("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type1Kacl+"g;");
					}else if(type1Kacl.compareTo(new BigDecimal(category.getEverydaymin()))==-1) {//最小值
						insufficient.add("谷薯及杂豆类("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type1Kacl+"g;");
					}
				}

				if(category.getFoodCategoryName().equals("蔬菜和菌藻")) {
					if(type2Kacl.compareTo(new BigDecimal(category.getEverydaymax()))==1) {//最大值
						excessive.add("蔬菜和菌藻("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type2Kacl+"g;");
					}else if(type2Kacl.compareTo(new BigDecimal(category.getEverydaymin()))==-1) {//最小值
						insufficient.add("蔬菜和菌藻("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type2Kacl+"g;");
					}
				}
				
				if(category.getFoodCategoryName().equals("水果")) {
					if(type3Kacl.compareTo(new BigDecimal(category.getEverydaymax()))==1) {//最大值
						excessive.add("水果("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type3Kacl+"g;");
					}else if(type3Kacl.compareTo(new BigDecimal(category.getEverydaymin()))==-1) {//最小值
						insufficient.add("水果("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type3Kacl+"g;");
					}
				}
				
				if(category.getFoodCategoryName().equals("畜禽肉类")) {
					if(type4Kacl.compareTo(new BigDecimal(category.getEverydaymax()))==1) {//最大值
						excessive.add("畜禽肉类("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type4Kacl+"g;");
					}else if(type4Kacl.compareTo(new BigDecimal(category.getEverydaymin()))==-1) {//最小值
						insufficient.add("畜禽肉类("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type4Kacl+"g;");
					}
				}
				
				if(category.getFoodCategoryName().equals("水产品")) {
					if(type5Kacl.compareTo(new BigDecimal(category.getEverydaymax()))==1) {//最大值
						excessive.add("水产品("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type5Kacl+"g;");
					}else if(type5Kacl.compareTo(new BigDecimal(category.getEverydaymin()))==-1) {//最小值
						insufficient.add("水产品("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type5Kacl+"g;");
					}
				}
				
				if(category.getFoodCategoryName().equals("蛋类")) {
					if(type6Kacl.compareTo(new BigDecimal(category.getEverydaymax()))==1) {//最大值
						excessive.add("蛋类("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type6Kacl+"g;");
					}else if(type6Kacl.compareTo(new BigDecimal(category.getEverydaymin()))==-1) {//最小值
						insufficient.add("蛋类("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type6Kacl+"g;");
					}
				}
				
				if(category.getFoodCategoryName().equals("奶类及制品")) {
					if(type7Kacl.compareTo(new BigDecimal(category.getEverydaymax()))==1) {//最大值
						excessive.add("奶类及制品("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type7Kacl+"g;");
					}else if(type7Kacl.compareTo(new BigDecimal(category.getEverydaymin()))==-1) {//最小值
						insufficient.add("奶类及制品("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type7Kacl+"g;");
					}
				}
				
				if(category.getFoodCategoryName().equals("坚果、大豆及制品")) {
					if(type8Kacl.compareTo(new BigDecimal(category.getEverydaymax()))==1) {//最大值
						excessive.add("坚果、大豆及制品("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type8Kacl+"g;");
					}else if(type8Kacl.compareTo(new BigDecimal(category.getEverydaymin()))==-1) {//最小值
						insufficient.add("坚果、大豆及制品("+category.getEverydaymin()+"~"+category.getEverydaymax()+"g)，"+type8Kacl+"g;");
					}
				}
			}
			
			
			if(excessive==null && insufficient==null) {
				app.setFoodRationalGuide("<div class=\"article_row\" style=\"margin-left: 5px;margin-top: 15px;\"><div class=\"circle_item\"></div><div class=\"content_item\">根据中国居民膳食指南，您每日摄入的各类食物都适量，非常优秀，请继续保持。食物多样，营养均衡，平均每天不重复的食物种类达到12种以上，每周25种以上，合理搭配，健康美味。</div></div>");
			}else {
				String exc="";
				String grid="";
				if(excessive.size()!=0) {
					for (String excessiveapp : excessive) {
						exc+="<div class=\"grid1\">"+excessiveapp+"</div>";
					}
					grid="<div class=\"grid\"><div class=\"grid1\" style=\"color: red;\">过量：</div><div class=\"grid2\">"+exc+"</div></div>";
				}
				String coent="";
				String insuf="";
				if(insufficient.size()!=0) {
					for (String insufficientapp : insufficient) {
						coent+="<div class=\"grid1\">"+insufficientapp+"</div>";
					}
					insuf="<div class=\"grid\"><div class=\"grid1\" style=\"color: red;\">不足：</div><div class=\"grid2\">"+coent+"</div></div>";
				}
				
				String guide="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: 15px;\"><div class=\"action\">根据中国居民膳食指南，您如下几类食物的日平均摄入不合理，在日常饮食过程中请注意改善：	</div>	</div>"+grid+insuf;
				app.setFoodRationalGuide(guide);
			}

		}else {
			app.setFoodPracticalNum("0");//实际记录天数每周
			app.setFoodRecommendHeat("0");//膳食推荐热量
			app.setFoodPracticalHeat("1600");//膳食实际总热量
			String guide="<div class=\"cu-bar bg-white solid-bottom\" style=\"margin-top: -50px;\"><div class=\"action\">控制饮食总热量，从记录饮食日记开始，饮食指导需要根据您的饮食日记来进行针对性的分析。</div>	</div>";
			app.setFoodRationalGuide(guide);
		}
		return app;
	}
	
	
	public List<String> weekList() {
		List<String> list = new ArrayList<String>();
		String week1 = weekBegin();
		String week2 = getTuesday();
		String week3 = getWednesday();
		String week4 = getThursday();
		String week5 = getFriday();
		String week6 = getSaturday();
		String week7 = weekEnd();
		list.add(week1);
		list.add(week2);
		list.add(week3);
		list.add(week4);
		list.add(week5);
		list.add(week6);
		list.add(week7);
		return list;
	}
	
	public static String weekBegin() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayWeek == 1) {
			dayWeek = 8;
		}

		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
		Date mondayDate = cal.getTime();
		String weekBegin = sdf.format(mondayDate);
		System.out.println("所在周星期一的日期：" + weekBegin);
		return weekBegin;
	}

	// 周二
	public String getTuesday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);// 以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());// 当前时间

		cld.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);// 周二
		System.out.println(df.format(cld.getTime()));

		return df.format(cld.getTime());
	}

	/**
	 * 周三
	 * 
	 * @return
	 */
	public String getWednesday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);// 以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());// 当前时间

		cld.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);// 周三
		System.out.println(df.format(cld.getTime()));

		return df.format(cld.getTime());
	}

	/**
	 * 周四
	 * 
	 * @return
	 */
	public String getThursday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);// 以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());// 当前时间

		cld.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);// 周四
		System.out.println(df.format(cld.getTime()));

		return df.format(cld.getTime());
	}

	/**
	 * 周五
	 * 
	 * @return
	 */
	public String getFriday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);// 以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());// 当前时间

		cld.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);// 周五
		System.out.println(df.format(cld.getTime()));

		return df.format(cld.getTime());
	}

	/**
	 * 周六
	 * 
	 * @return
	 */
	public String getSaturday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);// 以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());// 当前时间

		cld.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);// 周六
		System.out.println(df.format(cld.getTime()));

		return df.format(cld.getTime());
	}

	public static String weekEnd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayWeek == 1) {
			dayWeek = 8;
		}
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		Date mondayDate = cal.getTime();
		String weekBegin = sdf.format(mondayDate);

		cal.add(Calendar.DATE, 4 + cal.getFirstDayOfWeek());
		Date sundayDate = cal.getTime();
		String weekEnd = sdf.format(sundayDate);

		System.out.println("所在周星期日的日期：" + weekEnd);
		return weekEnd;

	}

	/**
	 * 获取当前时间所在周的周一和周日的日期时间
	 * 
	 * @return
	 */
	public static Map<String, String> getWeekDate() {
		Map<String, String> map = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayWeek == 1) {
			dayWeek = 8;
		}

		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		Date mondayDate = cal.getTime();
		String weekBegin = sdf.format(mondayDate);

		cal.add(Calendar.DATE, 4 + cal.getFirstDayOfWeek());
		Date sundayDate = cal.getTime();
		String weekEnd = sdf.format(sundayDate);
		System.out.println(weekBegin);
		System.out.println(weekEnd);
		map.put("mondayDate", weekBegin);
		map.put("sundayDate", weekEnd);
		return map;
	}

	
	
	
}
