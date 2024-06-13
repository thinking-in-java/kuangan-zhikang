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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yihe.common.annotation.Log;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.common.core.page.TableDataInfo;
import com.yihe.common.enums.BusinessType;
import com.yihe.common.utils.poi.ExcelUtil;
import com.yihe.system.domain.FoodArray;
import com.yihe.system.domain.YHUserSportDiaryVOJO;
import com.yihe.system.domain.YhSportCategoryWeb;
import com.yihe.system.domain.YhSportDiary;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.domain.YhSportRecommendRecordVojo;
import com.yihe.system.domain.YhSportSubject;
import com.yihe.system.domain.YhSportWeekly;
import com.yihe.system.domain.YhUserNotify;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserSportsGuide;
import com.yihe.system.mapper.YhSportCategoryWebMapper;
import com.yihe.system.mapper.YhSportRecommendRecordMapper;
import com.yihe.system.mapper.YhSportSubjectMapper;
import com.yihe.system.mapper.YhSportWeeklyMapper;
import com.yihe.system.mapper.YhUserNotifyMapper;
import com.yihe.system.mapper.YhUserSportsGuideMapper;
import com.yihe.system.service.IYhSportDiaryService;
import com.yihe.system.service.YhSportMotionService;
import com.yihe.system.service.YhUserSportsGuideService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 运动记录Controller
 * 
 * @author yihe
 * @date 2020-04-08
 */
@Controller
@RequestMapping("/system/SportDiary")
public class YhSportDiaryController extends BaseController
{
    private String prefix = "system/diary";

    @Autowired
    private IYhSportDiaryService yhSportDiaryService;

    @Autowired
    private YhUserSportsGuideService yhUserSportsGuideService;
    
    @Autowired
	YhSportMotionService yhSportMotionService;
    
    @Autowired
 	YhSportCategoryWebMapper yhSportCategoryAppMapper;
    
    @Autowired
    YhSportWeeklyMapper yhSportWeeklyMapper;
    
    @Autowired
    YhSportRecommendRecordMapper yhSportRecommendRecordMapper;
    
    @Autowired
    YhSportSubjectMapper yhSportSubjectMapper;
    
    @Autowired
    YhUserSportsGuideMapper yhUserSportsGuideMapper;
    
    
    @Autowired
    YhUserNotifyMapper yhUserNotifyMapper;
    
    
    
    public static void main(String[] args) {
//    	for (int i = 10016; i < 10131; i++) {
//			System.out.println(i);
//		}
    	for (int i = 0; i < 30; i++) {
    		String rqi=String.valueOf((i+1));
      		if((i+1)<10) {
      			rqi="0"+String.valueOf((i+1));
      		}
    		System.out.println("2020-09-"+rqi);
    	}
	}
   
    
    @ApiOperation(value = "查询省级数据")
    @RequestMapping(value = "/addGuideMapper", method = RequestMethod.POST)
  	@ResponseBody
  	public int add1(String userCode) throws ParseException{
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
    	YhUserSportsGuide gui=new YhUserSportsGuide();
    	Date date = simpleDateFormat.parse("2020-09-01");
    	Date date1 = simpleDateFormat.parse("2020-09-06");
    	gui.setStartTime(date);
    	gui.setEndTime(date1);
    	gui.setUserCode(userCode);
    	yhUserSportsGuideMapper.insertSelective(gui);
    	YhUserNotify not=new YhUserNotify();
    	not.setTitle("运动报告");
    	not.setContent("您的运动报告，已生成立即查看详情>>");
    	not.setUser_id(userCode);
    	not.setSend_date(date1);
    	not.setType(1);
    	not.setTo_service_id("1");
    	not.setNews_id(gui.getSportsGuideId());
    	yhUserNotifyMapper.insertnotify(not);
    	
    	
    	YhUserSportsGuide gui2=new YhUserSportsGuide();
    	Date date2 = simpleDateFormat.parse("2020-09-07");
    	Date date12 = simpleDateFormat.parse("2020-09-13");
    	gui2.setStartTime(date2);
    	gui2.setEndTime(date12);
    	gui2.setUserCode(userCode);
    	yhUserSportsGuideMapper.insertSelective(gui2);
    	YhUserNotify not1=new YhUserNotify();
    	not1.setTitle("运动报告");
    	not1.setContent("您的运动报告，已生成立即查看详情>>");
    	not1.setUser_id(userCode);
    	not1.setSend_date(date12);
    	not1.setType(1);
    	not1.setTo_service_id("1");
    	not1.setNews_id(gui2.getSportsGuideId());
    	yhUserNotifyMapper.insertnotify(not1);
    	
    	
    	YhUserSportsGuide gui3=new YhUserSportsGuide();
    	Date date3 = simpleDateFormat.parse("2020-09-14");
    	Date date13 = simpleDateFormat.parse("2020-09-20");
    	gui3.setStartTime(date3);
    	gui3.setEndTime(date13);
    	gui3.setUserCode(userCode);
    	yhUserSportsGuideMapper.insertSelective(gui3);
    	YhUserNotify not2=new YhUserNotify();
    	not2.setTitle("运动报告");
    	not2.setContent("您的运动报告，已生成立即查看详情>>");
    	not2.setUser_id(userCode);
    	not2.setSend_date(date13);
    	not2.setType(1);
    	not2.setTo_service_id("1");
    	not2.setNews_id(gui3.getSportsGuideId());
    	yhUserNotifyMapper.insertnotify(not2);
    	
    	
    	YhUserSportsGuide gui4=new YhUserSportsGuide();
    	Date date4 = simpleDateFormat.parse("2020-09-21");
    	Date date14 = simpleDateFormat.parse("2020-09-27");
    	gui4.setStartTime(date4);
    	gui4.setEndTime(date14);
    	gui4.setUserCode(userCode);
    	yhUserSportsGuideMapper.insertSelective(gui4);
    	
    	YhUserNotify not3=new YhUserNotify();
    	not3.setTitle("运动报告");
    	not3.setContent("您的运动报告，已生成立即查看详情>>");
    	not3.setUser_id(userCode);
    	not3.setSend_date(date14);
    	not3.setType(1);
    	not3.setTo_service_id("1");
    	not3.setNews_id(gui4.getSportsGuideId());
    	yhUserNotifyMapper.insertnotify(not3);
    	
  	return 0;
  	}
    
    
    
    
    @ApiOperation(value = "查询省级数据")
  @RequestMapping(value = "/showprovincial", method = RequestMethod.POST)
	@ResponseBody
	public int add1(String userCode,String uweight){
  	YhSportDiary dia=new YhSportDiary();
  	List<YhSportRecommendRecordVojo>  vojo=yhSportRecommendRecordMapper.showSportRecommended(userCode);
  	int standby=0;
  	String subjectid="";
	for (YhSportRecommendRecordVojo yhSportRecommendRecordVojo : vojo) {
		standby+=Integer.valueOf(yhSportRecommendRecordVojo.getStandby());
		subjectid+=yhSportRecommendRecordVojo.getSubjectId();
	}
	if(standby<30) {
		subjectid=subjectidadd(subjectid);
	}
	String[] classity = subjectid.split(",");
  	for (int i = 0; i < 30; i++) {
  		YhSportSubject subject=yhSportSubjectMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
  		
  		String diay="";
  		String[] motion = subject.getSubjectMotion().split(",");
  		for (int j = 0; j < motion.length; j++) {
  			YhSportMotion mot=yhSportMotionService.selectByPrimaryKey(Integer.valueOf(motion[j]));
  			String[] sec = mot.getFollowSecond().split("_");
  			if(sec[0].equals("1")) {
  				diay+=motion[j]+"="+mot.getSportTotalkcal()+"="+sec[1]+"次;";
  			}else {
  				diay+=motion[j]+"="+mot.getSportTotalkcal()+"="+sec[1]+"个;";
  			}
		}
  		dia.setDiaryRecommend(diay);
  		String rqi=String.valueOf((i+1));
  		if((i+1)<10) {
  			rqi="0"+String.valueOf((i+1));
  		}
  		dia.setEntryTime("2020-09-"+rqi);
  		dia.setUweight(uweight);
  		dia.setTenergy(subject.getRemark());
  		dia.setRemark(String.valueOf(Double.valueOf(subject.getSubjectTime())*60));
  		dia.setUserCode(userCode);
  		yhSportDiaryService.insertSelective(dia);
	}
	return 0;
	}
    
    public String subjectidadd(String subjectid){
    	subjectid+=subjectid;
    	String[] classity = subjectid.split(",");
    	if(classity.length<30) {
    		subjectidadd(subjectid);
    	}
    	return subjectid;
    	}
    
//    @ApiOperation(value = "查询省级数据")
//    @RequestMapping(value = "/showprovincial", method = RequestMethod.POST)
//	@ResponseBody
//	public int add1(){
//    	YhSportDiary dia=new YhSportDiary();
//    	dia.setEntryTime("2020-07-17");
//    	dia.setUweight("224");
//    	dia.setTenergy("388.92");
//    	dia.setDiaryDetails("36=5.6=14个;637=354.92=76分;639=14.4=20秒;617=12.6=29秒;");
//    	dia.setRemark("140");
//    	dia.setNumber("2328=1.3968");
//    	for (int i = 10016; i < 10131; i++) {
//			System.out.println(i);
//			dia.setUserCode(String.valueOf(i));
//			yhSportDiaryService.insertYhSportDiary(dia);
//		}
//		return 0;
//	}
	
    
    
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
	
//	public static void main(String[] args) {
//		 
//		System.out.println(66 + (13.7*54) + (5.0*168) - (6.8*0));
//		System.out.println(655 + (9.6*54) + (1.7*168) - (4.7*43));
// 
//		
//	}
	
	@ApiOperation(value = "运动分析结果")
    @RequestMapping(value = "/showSportRecordAnalyze", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> showSportRecordAnalyze(int id){
		Map<String,Object> map1 = new HashMap<String,Object>();
		YhUserSportsGuide guide=yhUserSportsGuideService.selectByPrimaryKey(id);//查询分析报告
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userCode", guide.getUserCode());
		map.put("cTime", guide.getStartTime());
		map.put("cTime1", guide.getEndTime());
		List<YhSportDiary> list=yhSportDiaryService.showSportRecordAnalyze(map);
		List<FoodArray> array=new ArrayList<FoodArray>();
		for (YhSportDiary yhSportDiary : list) {
			FoodArray foodArray=new FoodArray();
			foodArray.setTime(yhSportDiary.getEntryTime());
			foodArray.setRecommend(yhSportDiary.getUweight());
			if(Double.valueOf(yhSportDiary.getTenergy())>=Double.valueOf(yhSportDiary.getUweight())) {
				foodArray.setBeenoverweight(yhSportDiary.getTenergy());
				foodArray.setNotoverweight("0");
			}else {
				foodArray.setBeenoverweight("0");
				foodArray.setNotoverweight(yhSportDiary.getTenergy());
			}
			array.add(foodArray);
		}
		map1.put("array", array);//能耗统计图
		
		if(guide.getStype()==0) {
		int nut=dayCompare(guide.getBirthdate(), guide.getNewlyTime());//年龄
		String userName=guide.getUserName();//姓名
		int age=guide.getGender();//性别
		String userCode=guide.getUserCode();
		
		
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
						YhSportCategoryWeb cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
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
							YhSportCategoryWeb cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
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
							YhSportCategoryWeb cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
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
	
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String time = ft.format(guide.getStartTime());
			String time1 = ft.format(guide.getEndTime());
			
	
		map1.put("nut", nut);
		map1.put("userName", userName);
		map1.put("age", age);
		map1.put("userCode", userCode);
		map1.put("uweight", uweight/list.size());//平均摄入推荐
		map1.put("tenergy", tenergy/list.size());//平均摄入实际
		map1.put("frequency",list.size());//记录频率
		map1.put("number", number);//达标天数
		
		
		map1.put("walk", walk);//步行
		map1.put("recommend", recommend);//推荐
		map1.put("boutique", boutique);//精品
		map1.put("rests", rests);//其他
		map1.put("aerobic", aerobic);//有氧
		map1.put("anaerobic", anaerobic);//无氧
		map1.put("pliable", pliable);//柔韧
		map1.put("arthrosis", arthrosis);//关节
		
		double zong=walk+recommend+boutique+rests;
		
		map1.put("walkpercent", String.format("%.2f",walk/zong*100));
		map1.put("recommendpercent", String.format("%.2f",recommend/zong*100));
		map1.put("boutiquepercent", String.format("%.2f",boutique/zong*100));
		map1.put("restspercent", String.format("%.2f",rests/zong*100));
		
		
		
		map1.put("durationnum",duration/60);//运动分钟
		
		map1.put("startTime", time);//开始时间
		map1.put("endTime", time1);//结束时间
		YhSportWeekly kly=new YhSportWeekly();
		kly.setNut(nut);
		kly.setUsername(userName);
		kly.setAge(age);
		kly.setAnaerobic(String.valueOf(anaerobic));
		kly.setUsercode(userCode);
		kly.setUweight(String.valueOf(uweight/list.size()));
		kly.setTenergy(String.valueOf(tenergy/list.size()));
		kly.setFrequency(String.valueOf(list.size()));
		kly.setNumber(String.valueOf(number));
		//kly.setArray(array);
		kly.setWalk(String.valueOf(walk));
		kly.setRecommend(String.valueOf(recommend));
		kly.setBoutique(String.valueOf(boutique));
		kly.setRests(String.valueOf(rests));
		kly.setAerobic(String.valueOf(aerobic));
		kly.setPliable(String.valueOf(pliable));
		kly.setArthrosis(String.valueOf(arthrosis));
		kly.setWalkpercent(String.format("%.2f",walk/zong*100));
		kly.setRecommendpercent(String.format("%.2f",recommend/zong*100));
		kly.setBoutiquepercent(String.format("%.2f",boutique/zong*100));
		kly.setRestspercent(String.format("%.2f",rests/zong*100));
		kly.setDurationnum(String.valueOf(duration/60));
		kly.setStarttime(time);
		kly.setEndtime(time1);
		int s=yhSportWeeklyMapper.insertSelective(kly);
		
		guide.setStype(kly.getWeId());
		yhUserSportsGuideService.updateByPrimaryKeySelective(guide);
		}else{
			YhSportWeekly kly=yhSportWeeklyMapper.selectByPrimaryKey(guide.getStype());
			map1.put("nut", kly.getNut());
			map1.put("userName", kly.getUsername());
			map1.put("age", kly.getAge());
			map1.put("userCode", kly.getUsercode());
			map1.put("uweight", kly.getUweight());//平均摄入推荐
			map1.put("tenergy", kly.getTenergy());//平均摄入实际
			map1.put("frequency",kly.getFrequency());//记录频率
			map1.put("number", kly.getNumber());//达标天数
			
			map1.put("walk", kly.getWalk());//步行
			map1.put("recommend", kly.getRecommend());//推荐
			map1.put("boutique", kly.getBoutique());//精品
			map1.put("rests", kly.getRests());//其他
			map1.put("aerobic", kly.getAerobic());//有氧
			map1.put("anaerobic", kly.getAnaerobic());//无氧
			map1.put("pliable", kly.getPliable());//柔韧
			map1.put("arthrosis", kly.getArthrosis());//关节
			
			map1.put("walkpercent",kly.getWalkpercent());
			map1.put("recommendpercent", kly.getRecommendpercent());
			map1.put("boutiquepercent", kly.getBoutiquepercent());
			map1.put("restspercent", kly.getRestspercent());
			
			
			
			map1.put("durationnum",kly.getDurationnum());//运动分钟
			
			map1.put("startTime", kly.getStarttime());//开始时间
			map1.put("endTime", kly.getEndtime());//结束时间
		}
		return map1;
	}
	
    /**
 	 * 查询用户查看运动分析报告
 	 * @param page
 	 * @param limit
 	 * @return
 	 */
      @ApiOperation(value = "查询用户查看运动分析报告")
      @RequestMapping(value = "/selectSportsanalyze", method = RequestMethod.POST)
  	@ResponseBody
  	public Map<String,Object> selectfoodanalyze(Integer page,Integer limit,String usercode){
  		Map<String,Object> map1 = new HashMap<String,Object>();
  		Pages pa = new Pages();
  		pa.setCurPage(page);
  		pa.setMaxResult(limit);
  		map1.put("page", pa.getFirstRows());
  		map1.put("limit", pa.getMaxResult());
  		map1.put("userCode", usercode);
  		
  		Map<String,Object> map = new HashMap<String,Object>();
  		List<YhUserSportsGuide> list = yhUserSportsGuideService.selectAnalyzeSports(map1);
  		for (YhUserSportsGuide yhUserSportsGuide : list) {
  			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
  			String time = ft.format(yhUserSportsGuide.getStartTime());
  			String time1 = ft.format(yhUserSportsGuide.getEndTime());
  			yhUserSportsGuide.setSource(time+"~"+time1);
		}
  		map.put("code", 20000);
  		map.put("msg", "");
  		map.put("count",yhUserSportsGuideService.selectAnalyzeSportsConut(map1));
  		map.put("data", list);
  		return map;
  	}

    /**
     * 查询用户查看运动记录内容统计图
     * @param usercode
     * @param ctime
     * @return
     */
     @ApiOperation(value = "查询用户查看运动记录内容统计图")
     @RequestMapping(value = "/selectSportDiaryCtime", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectSportDiaryCtime(String usercode,String entryTime,String standard){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		map1.put("userCode", usercode);
 		if(entryTime!=null && !entryTime.equals("")) {
 			String[] strArr = entryTime.split(" - ");
 		map1.put("entryTime", strArr[0]);
 		map1.put("entryTime1", strArr[1]);
 		}
 		if(standard!=null && !standard.equals("")) {
 			if(standard.equals("1")) {
 		 		map1.put("deny", 1);
 			}else {
 				map1.put("right", 1);
 			}
 		}
 		List<YhSportDiary> list = yhSportDiaryService.selectSportDiaryCtime(map1);
 		Collections.reverse(list);
 		String[] cTime = new String[list.size()];
 		String[] energyLevel = new String[list.size()];
 		String[] energyKcal = new String[list.size()];
 		int i=0;
 		for (YhSportDiary yhSportDiary : list) {
 			i++;
 			cTime[i-1]=""+yhSportDiary.getEntryTime().substring(5)+"";
 			energyLevel[i-1]=""+yhSportDiary.getUweight()+"";
 			energyKcal[i-1]=""+yhSportDiary.getTenergy()+"";
		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("cTime", cTime);
 		map.put("energyLevel", energyLevel);
 		map.put("energyKcal",energyKcal);
 		return map;
 	}
     
	
	
	/**
	 * 查询用户查看运动记录内容
	 * @param page
	 * @param limit
	 * @param usercode 用户id
	 * @param cTime 时间
	 * @return
	 */
     @ApiOperation(value = "查询用户查看运动记录内容")
     @RequestMapping(value = "/selectSportDiary", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectSportDiary(Integer page,Integer limit,String usercode,String entryTime,String standard){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("userCode", usercode);
 		if(entryTime!=null && !entryTime.equals("")) {
 			String[] strArr = entryTime.split(" - ");
 		map1.put("entryTime", strArr[0]);
 		map1.put("entryTime1", strArr[1]);
 		}
 		if(standard!=null && !standard.equals("")) {
 			if(standard.equals("1")) {
 		 		map1.put("deny", 1);
 			}else {
 				map1.put("right", 1);
 			}
 		}
 		
 		Map<String,Object> map = new HashMap<String,Object>();
 		List<YhSportDiary> list = yhSportDiaryService.selectSportDiary(map1);
 		for (YhSportDiary yhSportDiary : list) {
 			double f =Double.valueOf(yhSportDiary.getTenergy());
 			yhSportDiary.setTenergy(String.format("%.2f", f));
		}
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhSportDiaryService.selectSportDiaryConut(map1));
 		map.put("data", list);
 		return map;
 	}
    
    
    @RequestMapping(value = "/findSportDiaryInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result findSportDiaryInfo(@RequestParam(required = false) String nameLike,
			@RequestParam(required = false) String usernameLike, @RequestParam(required = false) String type,
			@RequestParam(required = false) String strTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String standard,

			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit) {

		if (StringUtil.isNotEmpty(startTime)) {
			String[] split = startTime.split(" - ");
			strTime = split[0];
			endTime = split[1];
		}
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		PageInfo<YHUserSportDiaryVOJO> selectYhSportDiaryByusercodeForWeb = yhSportDiaryService.selectYhSportDiaryByusercodeForWeb(standard,usercode, nameLike, usernameLike, strTime, endTime, page, limit);
		List<YHUserSportDiaryVOJO> list = selectYhSportDiaryByusercodeForWeb.getList();
		for (YHUserSportDiaryVOJO yhUserSportDiaryVOJO : list) {
			//步数
			String number = yhUserSportDiaryVOJO.getNumber();
			if(StringUtil.isNotEmpty(number)){
				//2900=180
				String[] SportSplit = number.split("=");
				String snum = SportSplit[0];//步数
				String skcl = SportSplit[1];//步数的热量
				if(StringUtil.isNotEmpty(skcl)){
					Double doubleStr = Double.valueOf(skcl);
					String format = String.format("%.2f", doubleStr);
					yhUserSportDiaryVOJO.setStepNumber(snum+"步"+" "+"("+format+"千卡)");
				}else{
					yhUserSportDiaryVOJO.setStepNumber(snum+"步"+" "+"("+0+"千卡)");
				}
				
			}else{
				yhUserSportDiaryVOJO.setStepNumber(0+"步" +"("+0+"千卡)");
			}
			
			//其他训练
			String diaryDetails = yhUserSportDiaryVOJO.getDiaryDetails();
			Double otherInfo=0.0;
			if(StringUtil.isNotEmpty(diaryDetails)){
				//32=2.49=30分
				String[] split = diaryDetails.split(";");
				for (String str : split) {
					//32
					String[] split2 = str.split("=");
						//每个其他训练的热量
						String string = split2[1];
						Double valueOf = Double.valueOf(string);
						otherInfo=otherInfo+valueOf;
				}
			}
			yhUserSportDiaryVOJO.setOtherSport(Math.floor(otherInfo* 100) / 100+"千卡");
			
			
			//课程训练
			String diaryBoutique = yhUserSportDiaryVOJO.getDiaryBoutique();
			Double classInfo=0.0;
			if(StringUtil.isNotEmpty(diaryBoutique)){
				//32=2.49=30分
				String[] split = diaryBoutique.split(";");
				for (String str : split) {
					//32
					String[] split2 = str.split("=");
						//每个其他训练的热量
						String string = split2[1];
						Double valueOf = Double.valueOf(string);
						classInfo=classInfo+valueOf;
				}
			}
			yhUserSportDiaryVOJO.setClassSport(Math.floor(classInfo* 100) / 100+"千卡");
			
			// 推荐运动日记内容
			String diaryRecommend = yhUserSportDiaryVOJO.getDiaryRecommend();
			Double recommendSport=0.0;
			if(StringUtil.isNotEmpty(diaryRecommend)){
				//32=2.49=30分
				String[] split = diaryRecommend.split(";");
				for (String str : split) {
					//32
					String[] split2 = str.split("=");
						//每个其他训练的热量
						String string = split2[1];
						Double valueOf = Double.valueOf(string);
						recommendSport=recommendSport+valueOf;
				}
			}
			yhUserSportDiaryVOJO.setRecommendSport(Math.floor(recommendSport* 100) / 100+"千卡");
			
			
			
			//是否达标
			if(yhUserSportDiaryVOJO.getTenergy()>=yhUserSportDiaryVOJO.getUweight()){
				yhUserSportDiaryVOJO.setWhether("是");
			}else{
				yhUserSportDiaryVOJO.setWhether("否");
			}
			
			
		System.out.println(yhUserSportDiaryVOJO.toString());
		}
		
		
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", selectYhSportDiaryByusercodeForWeb.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
    }
    
    
    private static int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
           return 0;
        }
    }

    @RequiresPermissions("system:diary:view")
    @GetMapping()
    public String diary()
    {
        return prefix + "/diary";
    }

    /**
     * 查询运动记录列表
     */
    @RequiresPermissions("system:diary:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(YhSportDiary yhSportDiary)
    {
        startPage();
        List<YhSportDiary> list = yhSportDiaryService.selectYhSportDiaryList(yhSportDiary);
        return getDataTable(list);
    }

    /**
     * 导出运动记录列表
     */
    @RequiresPermissions("system:diary:export")
    @Log(title = "运动记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YhSportDiary yhSportDiary)
    {
        List<YhSportDiary> list = yhSportDiaryService.selectYhSportDiaryList(yhSportDiary);
        ExcelUtil<YhSportDiary> util = new ExcelUtil<YhSportDiary>(YhSportDiary.class);
        return util.exportExcel(list, "diary");
    }

    /**
     * 新增运动记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存运动记录
     */
    @RequiresPermissions("system:diary:add")
    @Log(title = "运动记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(YhSportDiary yhSportDiary)
    {
        return toAjax(yhSportDiaryService.insertYhSportDiary(yhSportDiary));
    }

    /**
     * 修改运动记录
     */
    @GetMapping("/edit/{uSportId}")
    public String edit(@PathVariable("uSportId") Long uSportId, ModelMap mmap)
    {
        YhSportDiary yhSportDiary = yhSportDiaryService.selectYhSportDiaryById(uSportId);
        mmap.put("yhSportDiary", yhSportDiary);
        return prefix + "/edit";
    }

    /**
     * 修改保存运动记录
     */
    @RequiresPermissions("system:diary:edit")
    @Log(title = "运动记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(YhSportDiary yhSportDiary)
    {
        return toAjax(yhSportDiaryService.updateYhSportDiary(yhSportDiary));
    }

    /**
     * 删除运动记录
     */
    @RequiresPermissions("system:diary:remove")
    @Log(title = "运动记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(yhSportDiaryService.deleteYhSportDiaryByIds(ids));
    }
}
