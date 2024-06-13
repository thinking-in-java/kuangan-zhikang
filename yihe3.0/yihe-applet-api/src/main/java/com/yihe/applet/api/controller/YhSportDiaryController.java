package com.yihe.applet.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.mapper.*;
import com.yihe.applet.api.service.YhWxSettingService;
import com.yihe.applet.api.util.common.util.HttpRequestor;
import com.yihe.applet.api.util.common.util.Result;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhDiseaseAssessTotalReport2Mapper;
import com.yihe.system.mapper.YhSportSubjectMapper;
import com.yihe.system.mapper.YhSportWeeklyMapper;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.service.*;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * YhSportDiarycontroller
 * Created by dong on 2020/2/24.
 */
@Controller
@RequestMapping("/api/YhSportDiary")
@Api(value = "YhSportDiary控制器", description = "YhSportDiary管理")
public class YhSportDiaryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhSportDiaryController.class);
	
    @Autowired
    IYhSportDiaryService YhSportDiaryService;
    
    @Autowired
	YhSportMotionService yhSportMotionService;
    
    @Autowired
    YhUserReginfoMapper YhUserReginfoService;
    
    @Autowired
    YhWxSettingService yhWxSettingService;
    
    @Autowired
    YhSportRecommendRecordService yhSportRecommendRecordService;
    

    @Autowired
    YhSportSubjectMapper yhSportSubjectService;
    

    @Autowired
    YhSportSetmealService yhSportSetmealService;
    
    @Autowired
    private YhUserSportsGuideService yhUserSportsGuideService;
    
    @Autowired
    private IYhSportDiaryService yhSportDiaryService;
    
    @Autowired
    private YhFoodSrecipeRecordAppMapper YhFoodSrecipeRecordAppMapper;
    
    @Autowired
    private YhDiseaseAssessTotalReport2Mapper yhDiseaseAssessTotalReport2Mapper;
    
    @Autowired
    private YhSportFrequencyAppMapper yhSportFrequencyAppMapper;
    
    @Autowired
	 YhSportCategoryAppMapper yhSportCategoryAppMapper;
    
	@Autowired
	YhUserSportCourseAddMapper yhUserSportCourseAddMapper;
	
	@Autowired
	YhHealthLectureInfoAppMapper yhHealthLectureInfoAppMapper;
	
	@Autowired
    YhSportWeeklyMapper yhSportWeeklyMapper;

	@Resource
	private YhUserSportChallengeAppController yhUserSportChallengeAppController;
	
	/**
	 * 查询运动学堂
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "findHealthLectureInfoBySportClass")
	@ResponseBody
	public Result findHealthLectureInfoBySportClass() {
		// 查询运动学堂 179为运动学堂外键 不予修改
				List<YhHealthLectureInfoApp> findHealthLectureInfoByFoodClass = yhHealthLectureInfoAppMapper
						.findHealthLectureInfoBySportClass(179);
	return Result.ok(findHealthLectureInfoByFoodClass);
	}
	

    /** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd";
        }   
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    }  
 // 步数转卡路里
    public static float getDistanceByStep(long steps) {
        return steps * 32 / 1000;
    }
    
    public static void main(String[] args) {  
    	System.out.println(getDistanceByStep(100)+"卡路里");
//    	String timeStamp = "1588867200";  
//    	 String date = timeStamp2Date(timeStamp, "yyyy-MM-dd");  
//    	 System.out.println(date);
        //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数
//    	String str="1886=1.1316001"; 
//    	String[] strarray=str.split("=");
//    	System.out.println(strarray[0]);
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
    @RequestMapping(value = "/showSportRecordAnalyze", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> showSportRecordAnalyze(int id){
		YhUserSportsGuide guide=yhUserSportsGuideService.selectByPrimaryKey(id);//查询分析报告
		Map<String,Object> map1 = new HashMap<String,Object>();
		if(guide.getStype()==0) {
			int nut=dayCompare(guide.getBirthdate(), guide.getNewlyTime());//年龄
		String userName=guide.getUserName();//姓名
		int age=guide.getGender();//性别
		String userCode=guide.getUserCode();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userCode", guide.getUserCode());
		map.put("cTime", guide.getStartTime());
		map.put("cTime1", guide.getEndTime());
		List<YhSportDiary> list=yhSportDiaryService.showSportRecordAnalyze(map);
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
	
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String time = ft.format(guide.getStartTime());
			String time1 = ft.format(guide.getEndTime());
			
	
		map1.put("userName", userName);
		map1.put("age", age);
		map1.put("userCode", userCode);
		int size=0;
		if(list.size()!=0) {
			size=list.size();
			map1.put("uweight", uweight/size);//平均摄入推荐
			map1.put("tenergy", tenergy/size);//平均摄入实际
		}else {
			map1.put("uweight", 0);//平均摄入推荐
			map1.put("tenergy", 0);//平均摄入实际
		}
		
		
		
		map1.put("walk", walk);//步行
		map1.put("recommend", recommend);//推荐
		map1.put("boutique", boutique);//精品
		map1.put("rests", rests);//其他
		map1.put("aerobic", aerobic);//有氧
		map1.put("anaerobic", anaerobic);//无氧
		map1.put("pliable", pliable);//柔韧
		map1.put("arthrosis", arthrosis);//关节
		
		double zong=walk+recommend+boutique+rests;
		
		if(zong==0 && walk==0) {
			map1.put("walkpercent", 0);
		}else {
			map1.put("walkpercent", String.format("%.2f",walk/zong*100));
		}
		
		if(zong==0 && recommend==0) {
			map1.put("recommendpercent", 0);
		}else {
			map1.put("recommendpercent", String.format("%.2f",recommend/zong*100));
		}
		
		if(zong==0 && boutique==0) {
			map1.put("boutiquepercent", 0);
		}else {
			map1.put("boutiquepercent", String.format("%.2f",boutique/zong*100));
		}
		
		if(rests==0 && boutique==0) {
			map1.put("restspercent", 0);
		}else {
			map1.put("restspercent", String.format("%.2f",rests/zong*100));
		}
		
		
		
		
		
		map1.put("durationnum",duration/60);//运动分钟
		map1.put("frequency",list.size());//记录频率
		map1.put("number", number);//达标天数
		map1.put("rests", rests);//其他运动
//		map1.put("curriculum", curriculum);//课程运动
//		map1.put("step", step);//步数
		map1.put("startTime", time);//开始时间
		map1.put("endTime", time1);//结束时间
		YhSportWeekly kly=new YhSportWeekly();
		kly.setAnaerobic(String.valueOf(anaerobic));
		kly.setNut(nut);
		kly.setUsername(userName);
		kly.setAge(age);
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
		}else {
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
     * 查询步数统计
     * @param code
     * @return
     */
       @RequestMapping(value = "/showSportStepStatistics")
   	@ResponseBody
   	public Map<String,Object> showSportStepStatistics(String userCode) {
    	 
    	   int i=0;
		   List<YhSportDiary> list=YhSportDiaryService.showSportStepStatistics(userCode);
		   String[] time = new String[7];
    	   String[] Sleep = new String[7];
		   for (YhSportDiary yhSportDiary : list) {
			   i++;
			   String[] strarray1=yhSportDiary.getEntryTime().split("-");
			   time[i-1]=strarray1[2];
			   String[] strarray=yhSportDiary.getNumber().split("=");
		       Sleep[i-1]=strarray[0];
		   }
			
			Map<String,Object> map = new HashMap<String,Object>();
	 		map.put("time", time);
	 		map.put("Sleep", Sleep);
	 		return map;
   	}
    
       
       
       /**
        * 首页需要的内容
        * @param userCode
        * @return
        */
     @RequestMapping(value = "ShowpresentDiary")
   	@ResponseBody
   	public Map<String, Object> ShowpresentDiary(String userCode) {
    	 YhUserReginfo info=YhUserReginfoService.findYhUserReginfo(userCode);
    	 String recommend = "0";
    	 YhDiseaseAssessTotalReport2web report=yhDiseaseAssessTotalReport2Mapper.showascReport(userCode);
 		
 		if(report==null) {
 			 int gender=info.getGender();//性别
 			if(gender==1) {//男 
 				recommend=String.valueOf(1600*0.15);//推荐消耗能量男
 			}else {//女 
 				recommend=String.valueOf(1200*0.15);//推荐消耗能量女 }
 			}
 		}else {
 			YhFoodSrecipeRecordApp foodrecord=YhFoodSrecipeRecordAppMapper.findFoodSrecipeRecordByUsercodeForNow(userCode);//饮食套餐的能量等级
 			if(foodrecord!=null) {
 				if(report.getBmi()<=24) {//正常
 					recommend=String.valueOf(foodrecord.getRecordKay()*0.15);
 				}else {
 					recommend=String.valueOf(foodrecord.getRecordKay()*0.2);
 				}
 				
 			}else {
 				if(report.getBmi()<=24) {//正常
 					recommend=String.valueOf(1600*0.15);
 				}else {
 					recommend=String.valueOf(1200*0.2);
 				}
 			}
 			
 		}
 	int num=YhSportDiaryService.ShowpnumberDiary(userCode);
    Map<String, Object> map=new HashMap<String, Object>();
    YhSportDiary selectByPrimaryKey = YhSportDiaryService.ShowpresentDiary(userCode);
    if(selectByPrimaryKey==null) {
    	map.put("beenConsumed", 0);//已消耗
        map.put("step",0);//步数
        map.put("active",0);//活跃时间
        map.put("num",num);//记录天数
        map.put("grade","不足");//等级
    }else {
    	
    	double percent=Double.valueOf(selectByPrimaryKey.getTenergy())/Double.valueOf(recommend)*100;
        
        String step="0";
        if(selectByPrimaryKey.getNumber()!=null) {
        	 String[] strarray1=selectByPrimaryKey.getNumber().split("=");
        	 step=strarray1[0];
        }
       
        String grade="";
        if(percent<80) {
        	grade="不足";
        }else if(percent>=80 && percent<99) {
        	grade="不错";
        }else if(percent>=100) {
        	grade="充足";
        }
        map.put("beenConsumed", String.format("%.2f", Double.valueOf(selectByPrimaryKey.getTenergy())));//已消耗
        map.put("step",step);//步数
        map.put("active",String.format("%.2f", Double.valueOf(Double.valueOf(selectByPrimaryKey.getRemark())/60)));//活跃时间
        map.put("num",num);//记录天数
        map.put("grade",grade);//等级
    }
    
   	return map;
   	}
       
       
    
/** 
 * 对于官方加密数据（encryptData）解密说明如下： 加密数据解密算法 接口如果涉及敏感数据（如wx.getUserInfo当中的 
 * openId 和unionId ），接口的明文内容将不包含这些敏感数据。开发者如需要获取敏感数据，需要对接口返回的加密数据( 
 * encryptedData )进行对称解密。 解密算法如下： 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。 
 * 对称解密的目标密文为 Base64_Decode(encryptedData), 对称解密秘钥 aeskey = 
 * Base64_Decode(session_key), aeskey 是16字节 对称解密算法初始向量 iv 会在数据接口中返回。 
 *  
 * @Description (TODO这里用一句话描述这个方法的作用) 
 * @param encryptedData 
 *            加密内容 
 * @param sessionKey 
 *            小程序登录sessionKey 
 * @param iv 
 *            解密算法初始向量 iv 会在数据接口中返回。 
 * @param encodingFormat 
 *            编码格式默认UTF-8
 * @param isSynchronize 用来标识是否同步【挑战赛】的步数
 * @return 返回解密后的字符串 
 * @throws Exception 
 */  
@RequestMapping(value = "/runData")
@ResponseBody
public Result decrypt(String encryptedData, String sessionKey, String iv,String userCode,@RequestParam(required = false,defaultValue = "true") Boolean isSynchronize) throws Exception {
	YhUserReginfo info=YhUserReginfoService.findYhUserReginfo(userCode);
	
	
    try {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");  
        BASE64Decoder base64Decoder = new BASE64Decoder();  
        byte[] _encryptedData = base64Decoder.decodeBuffer(encryptedData);  
        byte[] _sessionKey = base64Decoder.decodeBuffer(sessionKey);  
        byte[] _iv = base64Decoder.decodeBuffer(iv);  
        SecretKeySpec secretKeySpec = new SecretKeySpec(_sessionKey, "AES");  
        IvParameterSpec ivParameterSpec = new IvParameterSpec(_iv);  
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);  
        byte[] original = cipher.doFinal(_encryptedData);  
        byte[] bytes = MyAESUtil.decode(original);  
        String originalString = new String(bytes, "UTF-8");  
        JSONObject jsonObject = JSONObject.parseObject(originalString);  
        //解析json，获取stepInfoList下面的步数
        JSONArray stepInfoList = JSON.parseArray(jsonObject.getString("stepInfoList"));
//        for (int i = 0; i < stepInfoList.size(); i++) {
//        	Map<String, Object> map=new HashMap<String, Object>();
//        	  //JSONObject obj = Objects.requireNonNull(stepInfoList).getJSONObject(i);
//        	
//        	  if (stepInfoList != null) {
//                  JSONObject obj = stepInfoList.getJSONObject(i);
//        	  String time=timeStamp2Date(obj.get("timestamp").toString(), "yyyy-MM-dd");
//        	 String bushu=obj.get("step").toString();//步数
//        	  map.put("userCode", userCode);
//        	  map.put("entryTime", time);
//        	  YhSportDiary sport=YhSportDiaryService.showSportWhetherThere(map);
//        	  YhSportDiary diar=new YhSportDiary();
//    		  diar.setEntryTime(time);
//    		  diar.setUweight(String.valueOf(recommend));
//    		 
//    		  diar.setUserCode(userCode);
//    		  diar.setNumber(bushu+"="+String.valueOf(getDistanceByStep(Long.valueOf(bushu))));
//        	  if(sport==null) {
//        		  diar.setTenergy(String.valueOf(getDistanceByStep(Long.valueOf(bushu))));
//        		  YhSportDiaryService.insertSelective(diar);
//        	  }else if(sport!=null && sport.getNumber()==null) {
//        		  diar.setuSportId(sport.getuSportId());
//        		  diar.setTenergy(String.valueOf(Double.valueOf(sport.getTenergy())+Double.valueOf(getDistanceByStep(Long.valueOf(bushu)))));
//        		  YhSportDiaryService.updateByPrimaryKeySelective(diar);
//        	  }else if(i==stepInfoList.size()-1) {
//        		  diar.setuSportId(sport.getuSportId());
//        		  diar.setTenergy(String.valueOf(Double.valueOf(sport.getTenergy())+Double.valueOf(getDistanceByStep(Long.valueOf(bushu)))));
//        		  YhSportDiaryService.updateByPrimaryKeySelective(diar);
//        	  }
//        	  }else {
//              	return null;
//              }
//		}
		if(isSynchronize){
			Result result = yhUserSportChallengeAppController.selectByCodeDnow(userCode);
			if(null != result.getData()){
				JSONObject data = JSONObject.parseObject(JSON.toJSONString(result.getData()));
				if(null != data && Objects.equals("2",data.get("type").toString())){
					JSONObject chall = JSONObject.parseObject(JSON.toJSONString(data.get("chall")));
					//同步挑战赛的步数
					yhUserSportChallengeAppController.decrypt(encryptedData,sessionKey,iv,Integer.parseInt(chall.get("chId").toString()),false);
				}
			}
		}

		String recommend = "0";
   	 YhDiseaseAssessTotalReport2web report=yhDiseaseAssessTotalReport2Mapper.showascReport(userCode);
		
		if(report==null) {
			 int gender=info.getGender();//性别
			if(gender==1) {//男 
				recommend=String.valueOf(1600*0.15);//推荐消耗能量男
			}else {//女 
				recommend=String.valueOf(1200*0.15);//推荐消耗能量女 }
			}
		}else {
			YhFoodSrecipeRecordApp foodrecord=YhFoodSrecipeRecordAppMapper.findFoodSrecipeRecordByUsercodeForNow(userCode);//饮食套餐的能量等级
			if(foodrecord!=null) {
				if(report.getBmi()<=24) {//正常
					recommend=String.valueOf(foodrecord.getRecordKay()*0.15);
				}else {
					recommend=String.valueOf(foodrecord.getRecordKay()*0.2);
				}
				
			}else {
				if(report.getBmi()<=24) {//正常
					recommend=String.valueOf(1600*0.15);
				}else {
					recommend=String.valueOf(1200*0.2);
				}
			}
			
		}
		
		
        //获取今天的步数
        JSONObject today = stepInfoList.getJSONObject(stepInfoList.size()-1);
        String message = today.get("step").toString();
        Map<String, Object> map=new HashMap<String, Object>();
        Date now = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        String hehe = dateFormat.format(now); 
         map.put("userCode", userCode);
      	  map.put("entryTime", hehe);
      	  YhSportDiary sport=YhSportDiaryService.showSportWhetherThere(map);
    	  YhSportDiary diar=new YhSportDiary();
    	  diar.setEntryTime(hehe);
    	  diar.setUweight(recommend);
    	  diar.setUserCode(userCode);
    	  diar.setNumber(message+"="+String.valueOf(getDistanceByStep(Long.valueOf(message))));
    	  if(sport==null) {
    		  diar.setTenergy(String.valueOf(getDistanceByStep(Long.valueOf(message))));
    		  YhSportDiaryService.insertSelective(diar);
    	  }else if(sport!=null) {
    		  if(sport.getNumber()!=null) {
    			  String[] strArr1 = sport.getNumber().split("=");
    			  String Tenergy=String.valueOf(Double.valueOf(sport.getTenergy())-Double.valueOf(strArr1[1]));
    			  diar.setuSportId(sport.getuSportId());
        		  diar.setTenergy(String.valueOf(Double.valueOf(Tenergy)+Double.valueOf(getDistanceByStep(Long.valueOf(message)))));
    		  }else {
    			  diar.setuSportId(sport.getuSportId());
        		  diar.setTenergy(String.valueOf(Double.valueOf(sport.getTenergy())+Double.valueOf(getDistanceByStep(Long.valueOf(message)))));
    		  }
    		 YhSportDiaryService.updateByPrimaryKeySelective(diar);
    	  }
    	  
        return Result.ok(message);
        
    } catch (Exception ex) {
        return null;
    }
}  
/***
 * 获取当天的步数
 * @param encryptedData
 * @param sessionKey
 * @param iv
 * @param userCode
 * @return
 * @throws Exception
 */
@RequestMapping(value = "/nowrunData")
@ResponseBody
public Result nowdecrypt(String encryptedData, String sessionKey, String iv,String userCode) throws Exception {  
    try {  
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");  
        BASE64Decoder base64Decoder = new BASE64Decoder();  
        byte[] _encryptedData = base64Decoder.decodeBuffer(encryptedData);  
        byte[] _sessionKey = base64Decoder.decodeBuffer(sessionKey);  
        byte[] _iv = base64Decoder.decodeBuffer(iv);  
        SecretKeySpec secretKeySpec = new SecretKeySpec(_sessionKey, "AES");  
        IvParameterSpec ivParameterSpec = new IvParameterSpec(_iv);  
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);  
        byte[] original = cipher.doFinal(_encryptedData);  
        byte[] bytes = MyAESUtil.decode(original);  
        String originalString = new String(bytes, "UTF-8");  
        JSONObject jsonObject = JSONObject.parseObject(originalString);  
        //解析json，获取stepInfoList下面的步数
        JSONArray stepInfoList = JSON.parseArray(jsonObject.getString("stepInfoList"));
        //获取今天的步数
        JSONObject today =stepInfoList.getJSONObject(stepInfoList.size()-1);
        String message = today.get("step").toString();
        return Result.ok(message);
    } catch (Exception ex) {  
        return null;  
    }  
}

//https://api.weixin.qq.com/sns/jscode2session?appid=wx34b18110d3cefb31&secret=53fddeb005d303ad8b6c95a0db7683ae&js_code= &grant_type=authorization_code&grant_type=authorization_code

    /**
     * 获取微信sessionKey
     * @param code
     * @return
     */
       @RequestMapping(value = "/gainsessionKey")
   	@ResponseBody
   	public Result gainsessionKey(String code) {
       	YhWxSetting selectByPrimaryKey = yhWxSettingService.selectByPrimaryKey(1);
        String oppid="";
        JSONObject jsonObject=null;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + selectByPrimaryKey.getAppid() + "&secret=" + selectByPrimaryKey.getAppsecret() + "&js_code=";
        String url0 = "&grant_type=authorization_code";
        try {
           oppid = new HttpRequestor().doGet(url+code+url0);    //url+code+url0
            jsonObject = JSONObject.parseObject(oppid);  
           System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok(jsonObject);
   	}
    
       
    
    /**
     * 运动排行榜
     * @param userCode
     * @param figure 判断是哪个时间段
     * @return
     */
 	@RequestMapping(value = "selectBySportRanking")
 	@ResponseBody
 	public Result selectBySportranking(String userCode,String figure) {
 		if (StringUtil.isEmpty(userCode) || StringUtil.isEmpty(figure)) {
			return Result.error("参数异常");
		}
 		
 		List<SportRankingvojo>  StatisticsMapvojo=new ArrayList<SportRankingvojo>();
 		List<YhSportDiary> selectByPrimaryKey=null;
		
		if (figure.equals("本周")) {
			selectByPrimaryKey = YhSportDiaryService.selectBySportthisWeek(userCode);//本周
		}
		
		if (figure.equals("上周")) {
			selectByPrimaryKey = YhSportDiaryService.selectBySportlastWeek(userCode);//上周
		}
		
		if (figure.equals("本月")) {
			selectByPrimaryKey = YhSportDiaryService.selectBySportMonth(userCode);//本月
		}
 		
		if (figure.equals("上月")) {
			selectByPrimaryKey = YhSportDiaryService.selectBySportlastMonth(userCode);//上月
		}
		
		if (figure.equals("本年")) {
			selectByPrimaryKey = YhSportDiaryService.selectBySportthisYear(userCode);//本年
		}
		
		if (figure.equals("去年")) {
			selectByPrimaryKey = YhSportDiaryService.selectBySportlastYear(userCode);//上年
		}
 		
 		String record="";
	 		for (YhSportDiary yhSportDiary : selectByPrimaryKey) {
	 			if(yhSportDiary.getDiaryDetails()!=null) {
	 				record+=yhSportDiary.getDiaryDetails();//把所有运动相加
	 			}
	 			
	 			if(yhSportDiary.getDiaryBoutique()!=null) {
	 				record+=yhSportDiary.getDiaryBoutique();//把所有运动相加
	 			}
	 			
	 			if(yhSportDiary.getDiaryRecommend()!=null) {
	 				record+=yhSportDiary.getDiaryRecommend();//把所有运动相加
	 			}
	 			
			}
	 		if(!record.equals("")) {
	 			int sportrecord=record.split(";").length;//运动记录
		 		String diaryDetails=record;
		 		String[] arr = new String[sportrecord];//取到所有运动id
			diaryDetails=diaryDetails.substring(0, diaryDetails.length());
			int i=0;
			for (String diaryDetail : diaryDetails.split(";")) {
				i++;
				arr[i-1]=diaryDetail.split("=")[0];
			}
			 Set<String> set = new HashSet<String>();
			
			  for(int j=0;j<arr.length;j++){
			   set.add(arr[j]);
			  }
			  for (String string : set) {//把set结合中的id赋值到list中
				  SportRankingvojo vo=new SportRankingvojo();
				  vo.setId(string);
				  StatisticsMapvojo.add(vo);
			}
			 int zong=0;
			for (SportRankingvojo sportRankingvojo : StatisticsMapvojo) {
				for (String diaryDetail : diaryDetails.split(";")) {
						if(diaryDetail.split("=")[0].equals(sportRankingvojo.getId())) {
							zong+=1;
							sportRankingvojo.setDays(sportRankingvojo.getDays()+1);
						}
				}
			}
			
			  
			//查询运动
			for (SportRankingvojo sportRankingvojo : StatisticsMapvojo) {
				YhSportMotion motion = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sportRankingvojo.getId()));//运动表
				if(motion!=null) {
				sportRankingvojo.setImg(motion.getSportImg());
				sportRankingvojo.setName(motion.getSportName());
				// 创建一个数值格式化对象   
				   
		        NumberFormat numberFormat = NumberFormat.getInstance();  
		   
		        // 设置精确到小数点后2位  
		   
		        numberFormat.setMaximumFractionDigits(2);  
		   
		        String result = numberFormat.format((float) sportRankingvojo.getDays() / (float) zong * 100);
		        
				sportRankingvojo.setPercentage(String.valueOf(result));
				}
			}
	 		
	 		}
	 		
	 		
	 		
	 		 Collections.sort(StatisticsMapvojo, new Comparator<SportRankingvojo>(){

	             /*

	              * int compare(Person p1, Person p2) 返回一个基本类型的整型，

	              * 返回负数表示：p1 小于p2，

	              * 返回0 表示：p1和p2相等，

	              * 返回正数表示：p1大于p2

	              */
	             public int compare(SportRankingvojo p1, SportRankingvojo p2) {
	                 //按照Person的年龄进行升序排列
	                 if(p1.getDays() < p2.getDays()){
	                     return 1;
	                 }
	                 if(p1.getDays() == p2.getDays()){
	                     return 0;
	                 }
	                 return -1;
	             }
	         });
 		  
 		return Result.ok(StatisticsMapvojo);
 	}

 	
 	public static List<String> getLastTimeInterval() throws ParseException {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		calendar2.add(Calendar.DATE, offset2 - 7);
		// System.out.println(sdf.format(calendar1.getTime()));// last Monday
		String lastBeginDate = sdf.format(calendar1.getTime());
		// System.out.println(sdf.format(calendar2.getTime()));// last Sunday
		String lastEndDate = sdf.format(calendar2.getTime());
		Date dBegin = sdf.parse(lastBeginDate);
		Date dEnd = sdf.parse(lastEndDate);
		List<Date> lDate = findDates(dBegin, dEnd);// 获取这周所有date
		sdf = new SimpleDateFormat("MM-dd");
		for (Date date : lDate) {
			list.add(sdf.format(date));

		}
		return list;
	}
    
   /**
    * 运动统计图
    * @param userCode
    * @param figure 判断是哪个时间段
    * @return
 * @throws ParseException 
    */
	@RequestMapping(value = "selectBySportStatistics")
	@ResponseBody
	public Result selectBySportStatistics(String userCode,String type) throws ParseException {
		if (StringUtil.isEmpty(userCode) || StringUtil.isEmpty(type)) {
			return Result.error("参数异常");
		}
		
		
		if (type.equals("本周")) {
			int[] findFoodUserDuary = YhSportDiaryService.findSportDiaryByUserCodeForWeekDay(userCode);
			List<String> timeInterval = getTimeInterval();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", timeInterval);
			map.put("infoList", findFoodUserDuary);
			
			int maxLx=0;
			for (int i = 0; i < findFoodUserDuary.length; i++) {
				if(findFoodUserDuary[i]>0) {
					maxLx++;
				}
			}
			
			
			map.put("maxLx", maxLx);
			return Result.ok(map);
		}
		
		
		
		if (type.equals("上周")) {
			int[] findFoodUserDuary = YhSportDiaryService.findSportUserDuaryLastWeek(userCode);
			List<String> timeInterval = getLastTimeInterval();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", timeInterval);
			map.put("infoList", findFoodUserDuary);
			int maxLx=0;
			for (int i = 0; i < findFoodUserDuary.length; i++) {
				if(findFoodUserDuary[i]>0) {
					maxLx++;
				}
			}
			
			
			map.put("maxLx", maxLx);
			return Result.ok(map);
		}
		
		if (type.equals("本月")) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int[] findFoodUserDuary = YhSportDiaryService.findSportDiaryByUserCodeForMonth(userCode, String.valueOf(year),
					String.valueOf(month));
			int[] dayNum = new int[31];
			for (int i = 1; i <= 31; i++) {
				dayNum[i - 1] = i;
			}
			
			int maxLx=0;
			for (int i = 0; i < findFoodUserDuary.length; i++) {
				if(findFoodUserDuary[i]>0) {
					maxLx++;
				}
			}
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", dayNum);
			map.put("infoList", findFoodUserDuary);
			map.put("maxLx", maxLx);
			return Result.ok(map);
		}
		
		if (type.equals("上月")) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			// 上月
			int[] findFoodUserDuary = YhSportDiaryService.findSportDiaryByUserCodeForMonth(userCode,
					String.valueOf(year), String.valueOf(month));
			int[] dayNum = new int[31];
			for (int i = 1; i <= 31; i++) {
				dayNum[i - 1] = i;
			}
			int maxLx=0;
			for (int i = 0; i < findFoodUserDuary.length; i++) {
				if(findFoodUserDuary[i]>0) {
					maxLx++;
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", dayNum);
			map.put("infoList", findFoodUserDuary);
			map.put("maxLx", maxLx);
			return Result.ok(map);
		}
		
		if (type.equals("本年")) {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int[] findFoodUserDuary = YhSportDiaryService.findSportDiaryByuserCodeForyear(userCode,
					String.valueOf(year));
			int maxLx=YhSportDiaryService.findSportDiaryByuserCodeForyear1(userCode,
					String.valueOf(year));
			int[] dayNum = new int[12];
			for (int i = 1; i <= 12; i++) {
				dayNum[i - 1] = i;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", dayNum);
			map.put("infoList", findFoodUserDuary);
			map.put("maxLx", maxLx);
			return Result.ok(map);
		}
		
		if (type.equals("去年")) {
			Calendar cal = Calendar.getInstance();

			int year = cal.get(Calendar.YEAR) - 1;
			// 去年
			int[] findFoodUserDuary = YhSportDiaryService.findSportDiaryByuserCodeForyear(userCode,
					String.valueOf(year));
			int maxLx=0;
			for (int i = 0; i < findFoodUserDuary.length; i++) {
				maxLx+=findFoodUserDuary[i];
			}
			int[] dayNum = new int[12];
			for (int i = 1; i <= 12; i++) {
				dayNum[i - 1] = i;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", dayNum);
			map.put("infoList", findFoodUserDuary);
			map.put("maxLx", maxLx);
			return Result.ok(map);
		}
		return Result.error("参数异常");
	}

	
	public static int getlastMonth() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		int month = calendar.get(Calendar.MONTH) + 1;

		return month;
	}

	public static int getlastYear() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		int year = calendar.get(Calendar.YEAR);
		date = calendar.getTime();

		return year;
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
               * 根据code查询用户本月的记录
     * @param userCode
     * @return
     * @throws ParseException
     */
 	@RequestMapping(value = "selectBySportMonth")
 	@ResponseBody
 	public Result selectBySportMonth(String userCode) throws ParseException {
 		MonthVojo monthvojo=new MonthVojo();
 		 Calendar cal = Calendar.getInstance();
         int month = cal.get(Calendar.MONTH) + 1;//获取当前月份
         monthvojo.setMonths(String.valueOf(month));
 		Date date=new Date();
 		List<YhSportDiary> diary=YhSportDiaryService.selectBySportMonth(userCode);//本月的记录
		int k=1;
		int k1=0;
 		
 		if(diary!=null && !diary.isEmpty()) {
 			for (YhSportDiary yhSportDiary : diary) {
 	 			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
 	 	        Date date1 = simpleDateFormat.parse(yhSportDiary.getEntryTime());
 		        	int time=isYeaterday(date1, date);//判断是否昨天
 		        	if(time==0 || time==-1) {
 		        		k++;
 		        	}else {
 		        		date=date1;
 		        		k1=k;
 		        	}
 		        	if(k<k1) {
 	        			k1=k;
 	        		}
 			}
 			
 			String record="";
 	 		for (YhSportDiary yhSportDiary : diary) {
 	 			record+=yhSportDiary.getDiaryDetails();//把所有运动相加
 	 			record+=yhSportDiary.getDiaryBoutique();
 	 			record+=yhSportDiary.getDiaryRecommend();
 			}
 	 		int sportrecord=record.split(";").length;//运动记录
 	 		int number=diary.size();//运动天数
 	 		
 	 		String diaryDetails=record;
 	 		String[] arr = new String[sportrecord];//取到所有运动id
			diaryDetails=diaryDetails.substring(0, diaryDetails.length());
			int i=0;
			for (String diaryDetail : diaryDetails.split(";")) {
				i++;
				arr[i-1]=diaryDetail.split("=")[0];
			}
			 Set<String> set = new HashSet<String>();
			  for(int j=0;j<arr.length;j++){
			   set.add(arr[j]);
			  }
			  int kind=set.toArray().length;//运动种类
			  monthvojo.setKind(String.valueOf(kind));
			  monthvojo.setRecord(String.valueOf(sportrecord));
			  monthvojo.setNumber(String.valueOf(number));
			  if(k>k1) {
				  monthvojo.setContinuous(String.valueOf(k-1));
			  }else {
				  monthvojo.setContinuous(String.valueOf(k1));
			  }
 		}else {
 			 monthvojo.setContinuous("0");
 			  monthvojo.setKind("0");
			  monthvojo.setRecord("0");
			  monthvojo.setNumber("0");
 		}
 		
 		return Result.ok(monthvojo);
 		
 	}
    
 	
 	/** 
     * @author LuoB. 
     * @param oldTime 较小的时间 
     * @param newTime 较大的时间 (如果为空   默认当前时间 ,表示和当前时间相比) 
     * @return -1 ：同一天.    0：昨天 .   1 ：至少是前天. 
     * @throws ParseException 转换异常 
     */  
    private static int isYeaterday(Date oldTime,Date newTime) throws ParseException{  
        if(newTime==null){  
            newTime=new Date();  
        }  
               //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String todayStr = format.format(newTime);  
        Date today = format.parse(todayStr);  
        //昨天 86400000=24*60*60*1000 一天  
        if((today.getTime()-oldTime.getTime())>0 && (today.getTime()-oldTime.getTime())<=86400000) {  
            return 0;  
        }  
        else if((today.getTime()-oldTime.getTime())<=0){ //至少是今天  
            return -1;  
        }  
        else{ //至少是前天  
            return 1;  
        }  
          
    }
    
   /**
    * 根据code查询推荐与消耗能量，今日记录，百分比值
    * @param userCode
    * @return
    * @throws ParseException
    */
	@RequestMapping(value = "selectByenergy")
	@ResponseBody
	public Result energy(String userCode) throws ParseException {
		Recommendvojo1 recovojo=new Recommendvojo1();
		YhUserReginfo info=YhUserReginfoService.findYhUserReginfo(userCode);
		
//		List<YhSportRecommendRecordVojo> list=yhSportRecommendRecordService.showSportRecommended(userCode);
//		int i=0;
//		if(list.size()==0) {
//			YhSportSubject jec=yhSportSubjectService.selectByPrimaryKey(92);
//			recommend=jec.getRemark();
//		}else {
//			for (YhSportRecommendRecordVojo yhSportRecommendRecordVojo : list) {
//				i++;
//				if(i==1) {
//					System.out.println(yhSportRecommendRecordVojo.getSubjectId());
//					YhSportSubject jec=yhSportSubjectService.selectByPrimaryKey(Integer.valueOf(yhSportRecommendRecordVojo.getSubjectId().split(",")[0]));
//					recommend=jec.getRemark();
//				}
//			}
//		}
		
		YhDiseaseAssessTotalReport2web report=yhDiseaseAssessTotalReport2Mapper.showascReport(userCode);
		String recommend = "0";
		if(report==null) {
			 Date birthDate=info.getBirthDate(); int age=getAgeByBirth(birthDate);//获取年龄
			 int gender=info.getGender();//性别
			if(gender==1) {//男 
				recommend=String.valueOf(1600*0.15);//推荐消耗能量男
			}else {//女 
				recommend=String.valueOf(1200*0.15);//推荐消耗能量女 }
			}
		}else {
			YhFoodSrecipeRecordApp foodrecord=YhFoodSrecipeRecordAppMapper.findFoodSrecipeRecordByUsercodeForNow(userCode);//饮食套餐的能量等级
			if(foodrecord!=null) {
				if(report.getBmi()<=24) {//正常
					recommend=String.valueOf(foodrecord.getRecordKay()*0.15);
				}else {
					recommend=String.valueOf(foodrecord.getRecordKay()*0.2);
				}
				
			}else {
				if(report.getBmi()<=24) {//正常
					recommend=String.valueOf(1600*0.15);
				}else {
					recommend=String.valueOf(1200*0.2);
				}
			}
			
		}
		
		
			double f1 = Double.valueOf(recommend);
			recovojo.setRecommend(String.format("%.2f", f1));
			Map<String, Object> map=new HashMap<String, Object>();
	    	Date dt = new Date();   
	      	DateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
	    	String format = ss.format(dt);
	      	map.put("entryTime", format);
	    	map.put("userCode", userCode);
			YhSportDiary selectByPrimaryKey = YhSportDiaryService.showsky(map);//根据code和时间查询当前是否存在数据
			if(selectByPrimaryKey!=null && !selectByPrimaryKey.equals("")) {
				Double f = Double.valueOf(selectByPrimaryKey.getTenergy());
				recovojo.setConsume(String.format("%.2f", Double.valueOf(f)));//增加已消耗能量
				int record=0;
				if(selectByPrimaryKey.getDiaryDetails()!=null) {
					record+=selectByPrimaryKey.getDiaryDetails().split(";").length;//查询今日记录记录
					
				}
				if(selectByPrimaryKey.getDiaryBoutique()!=null){
					record+=selectByPrimaryKey.getDiaryBoutique().split(";").length;//查询今日记录记录
				}
				if(selectByPrimaryKey.getDiaryRecommend()!=null) {
					record+=selectByPrimaryKey.getDiaryRecommend().split(";").length;//查询今日记录记录
				}
				recovojo.setRecord(String.valueOf(record));//今日记录
				double percent=Double.valueOf(selectByPrimaryKey.getTenergy())/Double.valueOf(recommend)*100;
				if(percent>0 && percent<1) {
					recovojo.setPercent("1");
				}else {
					recovojo.setPercent(String.valueOf(percent));
				}
				if(selectByPrimaryKey.getNumber()!=null) {
					recovojo.setStep(selectByPrimaryKey.getNumber().split("=")[0]);
				}else {
					recovojo.setStep("0");
				}
			}else {
				recovojo.setStep("0");
				recovojo.setConsume("0");//增加已消耗能量
				recovojo.setRecord("0");//今日记录
				recovojo.setPercent("0");
			}
			
			
			  YhSportFrequencyApp freq=yhSportFrequencyAppMapper.selectByPrimaryKey(userCode);
		        String frequency="";
		        if(freq==null) {
		        	frequency="每周二、四";
		        }else {
		        	frequency=freq.getFrFrequency();
		        }
		        recovojo.setFrequency(frequency);
		        
		        
			return Result.ok(recovojo);
		
	}

    
	
	@RequestMapping(value = "showDiary")
	@ResponseBody
	public Result showYhSportDiary(Integer usportid) {
		YhSportDiary list=YhSportDiaryService.selectByPrimaryKey(usportid);
		return Result.ok(list);
	}
	
	
	/**
	 * 根据日期查询数据
	 * @param entryTime
	 * @param userCode
	 * @return
	 */
	@RequestMapping(value = "showSelectVal")
	@ResponseBody
	public Result showYhSportDiarySelectVal(String entryTime,String userCode) {
		Map<String, Object> map=new HashMap<String, Object>();
    	if(entryTime==null || entryTime=="") {
    		Date dt = new Date();   
      	  DateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
    	    String format = ss.format(dt);
      	map.put("entryTime", format);
    	}else {
    		map.put("entryTime", entryTime);
    	}
    	map.put("userCode", userCode);
		YhSportDiary selectByPrimaryKey = YhSportDiaryService.showsky(map);//根据code和时间查询当前是否存在数据
		return Result.ok(selectByPrimaryKey);
	}
	
	
    /**
     * 修改和删除记录运动
     * @param foodid
     * @param kcalTotal
     * @param time
     * @param usportid
     * @return
     */
	@RequestMapping(value = "updateSportDiary")
	@ResponseBody
	public Result showlist1(String sportid,String kcaltotal,String time,Integer usportid,String nums,String mealname) {
		if(nums==null || nums.equals("")) {
			nums="0";
		}
		YhSportDiary list=YhSportDiaryService.selectByPrimaryKey(usportid);

		String diaryDetails=sportid+"="+kcaltotal+"="+time+";";
		NumberFormat ddf1=NumberFormat.getNumberInstance() ; 
		ddf1.setMaximumFractionDigits(2); 
		if(nums==null || nums=="" || nums=="0") {
			String newStr="";
			if(mealname.equals("其他运动")) {
				newStr = list.getDiaryDetails().replaceFirst(""+diaryDetails+"","");
				if( list.getNumber()==null && newStr.equals("")  &&  list.getDiaryBoutique()==null  &&  list.getDiaryRecommend()==null) {
					int i=YhSportDiaryService.deleteByPrimaryKey(usportid);
					return Result.ok(i);
				}
			}else if(mealname.equals("精品课程")) {
				newStr = list.getDiaryBoutique().replaceFirst(""+diaryDetails+"","");
				if( list.getNumber()==null && newStr.equals("")  &&  list.getDiaryDetails()==null &&  list.getDiaryRecommend()==null) {
					int i=YhSportDiaryService.deleteByPrimaryKey(usportid);
					return Result.ok(i);
				}
			}else if(mealname.equals("推荐运动")) {
				newStr = list.getDiaryRecommend().replaceFirst(""+diaryDetails+"","");
				if( list.getNumber()==null && newStr.equals("")  &&  list.getDiaryDetails()==null &&  list.getDiaryBoutique()==null) {
					int i=YhSportDiaryService.deleteByPrimaryKey(usportid);
					return Result.ok(i);
				}
			}
			
			
				if(newStr.equals("") || newStr==null) {
					newStr=null;
				}
				double kacl=Double.valueOf(list.getTenergy())-Double.valueOf(kcaltotal);//减去之前卡路里
				YhSportDiary yh=new YhSportDiary();
				if(mealname.equals("其他运动")) {
					yh.setDiaryDetails(newStr);
					yh.setDiaryBoutique(list.getDiaryBoutique());
					yh.setDiaryRecommend(list.getDiaryRecommend());
				}else if(mealname.equals("精品课程")) {
					yh.setDiaryBoutique(newStr);
					yh.setDiaryDetails(list.getDiaryDetails());
					yh.setDiaryRecommend(list.getDiaryRecommend());
				}else if(mealname.equals("推荐运动")) {
					yh.setDiaryRecommend(newStr);
					yh.setDiaryBoutique(list.getDiaryBoutique());
					yh.setDiaryDetails(list.getDiaryDetails());
				}
				yh.setTenergy(String.valueOf(kacl));
				yh.setuSportId(Long.valueOf(usportid));
				YhSportMotion motion = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sportid));//运动表
				String minute="";
				double s=0;
				if(motion.getSportType()==1) {//分钟
					if(time.length() == 1){
						s =Double.valueOf(time)*60;
					}else {
						s=Double.valueOf(time.substring(0,time.length() - 1))*60;
					}
				}else {//秒
					if(time.length() == 1){
						s=Double.valueOf(time)*Double.valueOf(motion.getSportNumberduration());
					}else {
						s=Double.valueOf(time.substring(0,time.length() - 1))*Double.valueOf(motion.getSportNumberduration());
					}
				}
				
				
				yh.setRemark(String.valueOf((Double.valueOf(list.getRemark())-s)+Double.valueOf(nums)*60));
				int i=YhSportDiaryService.updateByPrimaryKeySelective2(yh);
				return Result.ok(i);
			
			
		}else {
			double kacl=Double.valueOf(list.getTenergy())-Double.valueOf(kcaltotal);//减去之前卡路里
			YhSportMotion motion = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sportid));//运动表
			String minute="";
			double s=0;
			if(motion.getSportType()==1) {//分钟
				if(time.length() == 1){
					s =Double.valueOf(time)*60;
				}else {
					s=Double.valueOf(time.substring(0,time.length() - 1))*60;
				}
			}else {//秒
				if(time.length() == 1){
					s=Double.valueOf(time)*Double.valueOf(motion.getSportNumberduration());
				}else {
					s=Double.valueOf(time.substring(0,time.length() - 1))*Double.valueOf(motion.getSportNumberduration());
				}
			}
			double Tenergy=kacl+Double.valueOf(motion.getKcal())*Double.valueOf(nums);//获得最新卡路里
			String newStr="";
			if(mealname.equals("其他运动")) {
				newStr = list.getDiaryDetails().replaceFirst(""+diaryDetails+"",""+sportid+"="+String.format("%.2f", Double.valueOf(motion.getKcal())*Double.valueOf(nums))+"="+nums+minute+";"+"");
			}else if(mealname.equals("精品课程")) {
				newStr = list.getDiaryBoutique().replaceFirst(""+diaryDetails+"",""+sportid+"="+String.format("%.2f", Double.valueOf(motion.getKcal())*Double.valueOf(nums))+"="+nums+minute+";"+"");
			}else if(mealname.equals("推荐运动")) {
				newStr = list.getDiaryRecommend().replaceFirst(""+diaryDetails+"",""+sportid+"="+String.format("%.2f", Double.valueOf(motion.getKcal())*Double.valueOf(nums))+"="+nums+minute+";"+"");
			}
			
			YhSportDiary yh=new YhSportDiary();
			yh.setTenergy(String.valueOf(Tenergy));
			if(mealname.equals("其他运动")) {
				yh.setDiaryDetails(newStr);
			}else if(mealname.equals("精品课程")) {
				yh.setDiaryBoutique(newStr);
			}else if(mealname.equals("推荐运动")) {
				yh.setDiaryRecommend(newStr);
			}
			yh.setuSportId(Long.valueOf(usportid));
			yh.setRemark(String.valueOf((Double.valueOf(list.getRemark())-s)+Double.valueOf(nums)*60));
			int i=YhSportDiaryService.updateByPrimaryKeySelective(yh);
			return Result.ok(i);
		}
		
	}
    
    
    /**
	     * 根据code查询用户运动日期
	* @param userCode
	* @param entryTime
	* @return
	*/
	@RequestMapping(value = "selectBySportTime")
	@ResponseBody
	public Result showlist(String userCode) {
	List<YhSportDiary> selectByPrimaryKey = YhSportDiaryService.selectBySportTime(userCode);
	String floatArray[] = new String[selectByPrimaryKey.size()];
	int i=0;
	for (YhSportDiary yhSportDiary : selectByPrimaryKey) {
		floatArray[i]=yhSportDiary.getEntryTime();
		i++;
	}
	return Result.ok(floatArray);
	}


    /**
                * 运动日记查询
     * @param userCode
     * @param entryTime
     * @return
     */
    @RequestMapping(value = "showsky")
    @ResponseBody
    public Result showlist(String userCode,String entryTime) {

      	Map<String, Object> map=new HashMap<String, Object>();
    	if(entryTime==null || entryTime=="") {
    		Date dt = new Date();   
      	  DateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
    	    String format = ss.format(dt);
      	map.put("entryTime", format);
    	}else {
    		map.put("entryTime", entryTime);
    	}
    	map.put("userCode", userCode);
		YhSportDiary selectByPrimaryKey = YhSportDiaryService.showsky(map);//根据code和时间查询当前是否存在数据
		if(selectByPrimaryKey==null) {
			return Result.ok();
		}else {
			SportListVojo wellUserSportDiaryList=getSportDiaryByDate(selectByPrimaryKey);
			if(selectByPrimaryKey.getNumber()!=null) {
//				  String[] strarray=selectByPrimaryKey.getNumber().split("=");
//			       Sleep[i-1]=strarray[0];
				return Result.ok(selectByPrimaryKey.getNumber(),wellUserSportDiaryList);
			}else {
				return Result.ok(wellUserSportDiaryList);
			}
			
		}
		
    }
    
    
    public SportListVojo getSportDiaryByDate(YhSportDiary wellUserSportDiary){
    	SportListVojo vojo=new SportListVojo();
		List<SportDiaryDetails> wellUserSportDiaryList=new ArrayList<SportDiaryDetails>();
		List<SportDiaryDetails> wellUserSportDiaryList1=new ArrayList<SportDiaryDetails>();
		List<SportDiaryDetails> wellUserSportDiaryList2=new ArrayList<SportDiaryDetails>();
		String sKcalId="";
				//不等于空的时候处理日志详情
				if(wellUserSportDiary!=null){
					
					if(wellUserSportDiary.getDiaryDetails()!=null){
					String diaryDetails=wellUserSportDiary.getDiaryDetails();
					
					diaryDetails=diaryDetails.substring(0, diaryDetails.length());
					
					double kcalTotal2=0;
					for (String diaryDetail : diaryDetails.split(";")) {
						kcalTotal2+=Double.valueOf(diaryDetail.split("=")[1]);
					}
					for (String diaryDetail : diaryDetails.split(";")) {
						sKcalId=diaryDetail.split("=")[0];
						
						YhSportMotion wellSportKcall = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sKcalId));//运动表
						if(wellSportKcall!=null){
							SportDiaryDetails sportDiaryDetails=new SportDiaryDetails();
							sportDiaryDetails.setId(Integer.valueOf(String.valueOf(wellUserSportDiary.getuSportId())));
							sportDiaryDetails.setTimeData(wellUserSportDiary.getEntryTime());
							if(wellUserSportDiary.getNumber()!=null) {
								sportDiaryDetails.setKcalTotal1(String.valueOf(Double.valueOf(wellUserSportDiary.getTenergy())-Double.valueOf(wellUserSportDiary.getNumber().split("=")[1])));
							}else {
								sportDiaryDetails.setKcalTotal1(wellUserSportDiary.getTenergy());
							}
							sportDiaryDetails.setKcalTotal2(String.valueOf(kcalTotal2));
							sportDiaryDetails.setKcalTotal(diaryDetail.split("=")[1]);
							sportDiaryDetails.setTime(diaryDetail.split("=")[2]);
							sportDiaryDetails.setSportName(wellSportKcall.getSportName());
							sportDiaryDetails.setSportId(sKcalId);
							sportDiaryDetails.setSportImg(wellSportKcall.getSportImg());
							wellUserSportDiaryList.add(sportDiaryDetails);
						}	
					}
					
					}
					
					if(wellUserSportDiary.getDiaryBoutique()!=null){
						String diaryDetails=wellUserSportDiary.getDiaryBoutique();
						
						diaryDetails=diaryDetails.substring(0, diaryDetails.length());
						double kcalTotal2=0;
						for (String diaryDetail : diaryDetails.split(";")) {
							kcalTotal2+=Double.valueOf(diaryDetail.split("=")[1]);
						}
						for (String diaryDetail : diaryDetails.split(";")) {
							sKcalId=diaryDetail.split("=")[0];
							
							YhSportMotion wellSportKcall = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sKcalId));//运动表
							if(wellSportKcall!=null){
								SportDiaryDetails sportDiaryDetails=new SportDiaryDetails();
								sportDiaryDetails.setId(Integer.valueOf(String.valueOf(wellUserSportDiary.getuSportId())));
								sportDiaryDetails.setTimeData(wellUserSportDiary.getEntryTime());
								if(wellUserSportDiary.getNumber()!=null) {
									sportDiaryDetails.setKcalTotal1(String.valueOf(Double.valueOf(wellUserSportDiary.getTenergy())-Double.valueOf(wellUserSportDiary.getNumber().split("=")[1])));
								}else {
									sportDiaryDetails.setKcalTotal1(wellUserSportDiary.getTenergy());
								}
								sportDiaryDetails.setKcalTotal2(String.valueOf(kcalTotal2));
								sportDiaryDetails.setKcalTotal(diaryDetail.split("=")[1]);
								sportDiaryDetails.setTime(diaryDetail.split("=")[2]);
								sportDiaryDetails.setSportName(wellSportKcall.getSportName());
								sportDiaryDetails.setSportId(sKcalId);
								sportDiaryDetails.setSportImg(wellSportKcall.getSportImg());
								wellUserSportDiaryList1.add(sportDiaryDetails);
							}	
						}
						
						}
					
					if(wellUserSportDiary.getDiaryRecommend()!=null){
						String diaryDetails=wellUserSportDiary.getDiaryRecommend();
						
						diaryDetails=diaryDetails.substring(0, diaryDetails.length());
						double kcalTotal2=0;
						for (String diaryDetail : diaryDetails.split(";")) {
							kcalTotal2+=Double.valueOf(diaryDetail.split("=")[1]);
						}
						for (String diaryDetail : diaryDetails.split(";")) {
							sKcalId=diaryDetail.split("=")[0];
							
							YhSportMotion wellSportKcall = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sKcalId));//运动表
							if(wellSportKcall!=null){
								SportDiaryDetails sportDiaryDetails=new SportDiaryDetails();
								sportDiaryDetails.setId(Integer.valueOf(String.valueOf(wellUserSportDiary.getuSportId())));
								sportDiaryDetails.setTimeData(wellUserSportDiary.getEntryTime());
								if(wellUserSportDiary.getNumber()!=null) {
									sportDiaryDetails.setKcalTotal1(String.valueOf(Double.valueOf(wellUserSportDiary.getTenergy())-Double.valueOf(wellUserSportDiary.getNumber().split("=")[1])));
								}else {
									sportDiaryDetails.setKcalTotal1(wellUserSportDiary.getTenergy());
								}
								
								sportDiaryDetails.setKcalTotal2(String.valueOf(kcalTotal2));
								sportDiaryDetails.setKcalTotal(diaryDetail.split("=")[1]);
								sportDiaryDetails.setTime(diaryDetail.split("=")[2]);
								sportDiaryDetails.setSportName(wellSportKcall.getSportName());
								sportDiaryDetails.setSportId(sKcalId);
								sportDiaryDetails.setSportImg(wellSportKcall.getSportImg());
								wellUserSportDiaryList2.add(sportDiaryDetails);
							}	
						}
						
						}
					
				}
				
				vojo.setDetails(wellUserSportDiaryList);
				vojo.setBoutique(wellUserSportDiaryList1);
				vojo.setRecomSport(wellUserSportDiaryList2);
		return vojo;
		
		
	}
    

/**
 * 将秒化为分钟和秒
 * @author  zll
 * @throws ParseException 
 */
 
//    public static void main(String[] args) throws ParseException {
//    	DateFormat df = new SimpleDateFormat("yyyy:MM:dd");
//    	Date data=new Date();
//		Date d2 = df.parse("1998:4:12");
//		System.out.println(getAgeByBirth(d2));
//		
//    }
//    
    
    /**
     * 根据出生日期自动计算年龄
     * @param birthDay
     * @return
     * @throws ParseException
     */
    public static int getAgeByBirth(Date birthDay) throws ParseException {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }
    
    
   /**
    * 精彩课程增加
    * @param userCode
    * @param id
    * @param recid
    * @param index
    * @param length
    * @return
    * @throws ParseException
    */
    @RequestMapping(value = "/brilliantSportadd")
	@ResponseBody
	public Result brilliantSportadd(String userCode,String id,String recid,int index,int length,int number) throws ParseException {
    	
    	
		Map<String, Object> map=new HashMap<String, Object>();
		NumberFormat ddf1=NumberFormat.getNumberInstance() ; 
		ddf1.setMaximumFractionDigits(2); 
		YhSportDiary diary=new YhSportDiary();
		YhSportMotion ssmotion=new YhSportMotion();
		Date dt = new Date();  
		String shijian="";
	    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    shijian=simpleDateFormat.format(dt);
	    map.put("entryTime", shijian);
		map.put("userCode", userCode);
		YhSportDiary selectByPrimaryKey = YhSportDiaryService.showsky(map);//根据code和时间查询当前是否存在数据
		
		YhSportMotion motion = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(id));//运动表
		double  second=0;//秒数
		String minute="";
		String nums="";
		if(motion.getFollowSecond()!=null) {
			String[] strarray1=motion.getFollowSecond().split("_");
			nums=strarray1[1];
			if(strarray1[0].equals("1")) {
				minute="次";
				if(motion.getSportNumberduration()!=null && !motion.getSportNumberduration().equals("")) {
					second=Double.valueOf(strarray1[1])*Double.valueOf(motion.getSportNumberduration());
				}else {
					second=Double.valueOf(strarray1[1]);
				}
				
			}else {
				minute="秒";
				second=Double.valueOf(strarray1[1]);
			}
		}else {
			minute="次";
			second=20;
			nums="20";
		}
		
		if(recid!=null && !recid.equals("")) {
    		YhUserSportCourseAdd cord=yhUserSportCourseAddMapper.selectByPrimaryKey(Integer.valueOf(recid));
    		//YhSportSubject jec=yhSportSubjectService.selectByPrimaryKey();
    		YhUserSportCourseAdd rec=new YhUserSportCourseAdd();
    		rec.setAddId(cord.getAddId());
    		Set<String> set=new HashSet<String>();
    		if(cord.getFinishid()==null || cord.getFinishid().equals("")) {
    			 if(number==Integer.valueOf(cord.getAddNum())) {
    				 rec.setFinishid(String.valueOf(index));
	    		 }
    		}else {
    			 String[] strarray1=cord.getFinishid().split(",");
    	    		 for (int i = 0; i < strarray1.length; i++) {
    	    	    	set.add(strarray1[i]);
    	 			}
    	    		 set.add(String.valueOf(index));
    	    		 if(number==Integer.valueOf(cord.getAddNum())) {
    	    			 rec.setFinishid(cord.getFinishid()+","+index);
    	    		 }
    		}
    		
//    		YhSportSubject setmeal=yhSportSubjectService.selectByPrimaryKey(Integer.valueOf(recommend));
//    		 String[] strarray2=setmeal.getSubjectMotion().split(",");
    		 if(set.size()>=length) {
    			 if(cord.getStandby().equals(cord.getAddNum())) {
    				 rec.setAddType(2);
    			 }else {
    				 int in=Integer.valueOf(cord.getAddNum())+1;
        				 rec.setFinishid("");
        				 rec.setAddNum(String.valueOf(in));
    			 }
    			 
    			
    		 }
    		 rec.setAddTime(cord.getAddTime());
    		 yhUserSportCourseAddMapper.updateByPrimaryKeySelective(rec);
    	}
		
		
		YhUserReginfo info=YhUserReginfoService.findYhUserReginfo(userCode);
			YhDiseaseAssessTotalReport2web report=yhDiseaseAssessTotalReport2Mapper.showascReport(userCode);
			String recommend1 = "0";
			if(report==null) {
				 Date birthDate=info.getBirthDate(); int age=getAgeByBirth(birthDate);//获取年龄
				 int gender=info.getGender();//性别
				if(gender==1) {//男 
					recommend1=String.valueOf(1600*0.15);//推荐消耗能量男
				}else {//女 
					recommend1=String.valueOf(1200*0.15);//推荐消耗能量女 }
				}
			}else {
				YhFoodSrecipeRecordApp foodrecord=YhFoodSrecipeRecordAppMapper.findFoodSrecipeRecordByUsercodeForNow(userCode);//饮食套餐的能量等级
				if(foodrecord!=null) {
					if(report.getBmi()<=24) {//正常
						recommend1=String.valueOf(foodrecord.getRecordKay()*0.15);
					}else {
						recommend1=String.valueOf(foodrecord.getRecordKay()*0.2);
					}
					
				}else {
					if(report.getBmi()<=24) {//正常
						recommend1=String.valueOf(1600*0.15);
					}else {
						recommend1=String.valueOf(1200*0.2);
					}
				}
				
			}
			
		int sport=0;
		if(selectByPrimaryKey!=null) {
			diary.setuSportId(selectByPrimaryKey.getuSportId());
			diary.setTenergy(String.valueOf(Double.valueOf(selectByPrimaryKey.getTenergy())+Double.valueOf(motion.getKcal())));
			if(selectByPrimaryKey.getDiaryBoutique()!=null) {
				diary.setDiaryBoutique(selectByPrimaryKey.getDiaryBoutique()+motion.getId()+"="+ddf1.format(Double.valueOf(motion.getKcal()))+"="+nums+""+minute+";");
			}else {
				diary.setDiaryBoutique(motion.getId()+"="+ddf1.format(Double.valueOf(motion.getKcal()))+"="+nums+""+minute+";");
			}
			diary.setRemark(String.valueOf(second+Double.valueOf(selectByPrimaryKey.getRemark())));
			diary.setUweight(recommend1);
			sport=YhSportDiaryService.updateByPrimaryKeySelective(diary);//修改
			
			ssmotion.setHot(motion.getHot()+1);
			ssmotion.setId(Integer.valueOf(id));
			yhSportMotionService.updateByPrimaryKeySelective(ssmotion);
		}else {
			diary.setUweight(recommend1);
			diary.setEntryTime(shijian);
			diary.setTenergy(String.valueOf(Double.valueOf(motion.getKcal())));
			diary.setDiaryBoutique(motion.getId()+"="+ddf1.format(Double.valueOf(motion.getKcal()))+"="+nums+""+minute+";");
			diary.setUserCode(userCode);
			diary.setRemark(String.valueOf(second));
			sport=YhSportDiaryService.insertSelective(diary);//增加
			
			ssmotion.setHot(motion.getHot()+1);
			ssmotion.setId(Integer.valueOf(id));
			yhSportMotionService.updateByPrimaryKeySelective(ssmotion);
		}
		
		return Result.ok(sport);
	}
    
    
    
    
    /**
     * 健康运动增加
     * @param userCode
     * @param id
     * @param nums
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/healthSportadd")
	@ResponseBody
	public Result healthSportadd(String userCode,String id,String recommend,String recid,int index,int length,int number) throws ParseException {
    	
    	
		Map<String, Object> map=new HashMap<String, Object>();
		NumberFormat ddf1=NumberFormat.getNumberInstance() ; 
		ddf1.setMaximumFractionDigits(2); 
		YhSportDiary diary=new YhSportDiary();
		YhSportMotion ssmotion=new YhSportMotion();
		Date dt = new Date();  
		String shijian="";
	    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    shijian=simpleDateFormat.format(dt);
	    map.put("entryTime", shijian);
		map.put("userCode", userCode);
		YhSportDiary selectByPrimaryKey = YhSportDiaryService.showsky(map);//根据code和时间查询当前是否存在数据
		
		YhSportMotion motion = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(id));//运动表
		double  second=0;//秒数
		String minute="";
		String nums="";
		if(motion.getFollowSecond()!=null) {
			String[] strarray1=motion.getFollowSecond().split("_");
			nums=strarray1[1];
			if(strarray1[0].equals("1")) {
				minute="次";
				if(motion.getSportNumberduration()!=null && !motion.getSportNumberduration().equals("")) {
					second=Double.valueOf(strarray1[1])*Double.valueOf(motion.getSportNumberduration());
				}else {
					second=Double.valueOf(strarray1[1]);
				}
				
			}else {
				minute="秒";
				second=Double.valueOf(strarray1[1]);
			}
		}else {
			minute="次";
			second=20;
			nums="20";
		}
		
		if(recid!=null && !recid.equals("")) {
    		YhSportRecommendRecord cord=yhSportRecommendRecordService.selectByPrimaryKey(Integer.valueOf(recid));
    		//YhSportSubject jec=yhSportSubjectService.selectByPrimaryKey();
    		YhSportRecommendRecord rec=new YhSportRecommendRecord();
    		rec.setId(cord.getId());
    		Set<String> set=new HashSet<String>();
    		if(cord.getFinishid()==null || cord.getFinishid().equals("")) {
    			 if(number==Integer.valueOf(cord.getNumber())) {
    				 rec.setFinishid(String.valueOf(index));
	    		 }
    		}else {
    			 String[] strarray1=cord.getFinishid().split(",");
    	    		 for (int i = 0; i < strarray1.length; i++) {
    	    	    	set.add(strarray1[i]);
    	 			}
    	    		 set.add(String.valueOf(index));
    	    		 if(number==Integer.valueOf(cord.getNumber())) {
    	    			 rec.setFinishid(cord.getFinishid()+","+index);
    	    		 }
    			
    		}
    		
//    		YhSportSubject setmeal=yhSportSubjectService.selectByPrimaryKey(Integer.valueOf(recommend));
//    		 String[] strarray2=setmeal.getSubjectMotion().split(",");
    		 if(set.size()>=length) {
    			 int in=Integer.valueOf(cord.getNumber())+1;
    			 if(cord.getStandby().equals(String.valueOf(in))) {
    				 rec.setNumber("0");
    				 rec.setFinishid("");
    			 }else {
    				 rec.setFinishid("");
    				 rec.setNumber(String.valueOf(in));
    			 }
    			
    		 }
    		 
    		yhSportRecommendRecordService.updateByPrimaryKeySelective(rec);
    	}
		
		
		YhUserReginfo info=YhUserReginfoService.findYhUserReginfo(userCode);
			YhDiseaseAssessTotalReport2web report=yhDiseaseAssessTotalReport2Mapper.showascReport(userCode);
			String recommend1 = "0";
			if(report==null) {
				 Date birthDate=info.getBirthDate(); int age=getAgeByBirth(birthDate);//获取年龄
				 int gender=info.getGender();//性别
				if(gender==1) {//男 
					recommend1=String.valueOf(1600*0.15);//推荐消耗能量男
				}else {//女 
					recommend1=String.valueOf(1200*0.15);//推荐消耗能量女 }
				}
			}else {
				YhFoodSrecipeRecordApp foodrecord=YhFoodSrecipeRecordAppMapper.findFoodSrecipeRecordByUsercodeForNow(userCode);//饮食套餐的能量等级
				if(foodrecord!=null) {
					if(report.getBmi()<=24) {//正常
						recommend1=String.valueOf(foodrecord.getRecordKay()*0.15);
					}else {
						recommend1=String.valueOf(foodrecord.getRecordKay()*0.2);
					}
					
				}else {
					if(report.getBmi()<=24) {//正常
						recommend1=String.valueOf(1600*0.15);
					}else {
						recommend1=String.valueOf(1200*0.2);
					}
				}
				
			}
			
		int sport=0;
		if(selectByPrimaryKey!=null) {
			diary.setuSportId(selectByPrimaryKey.getuSportId());
			
			diary.setTenergy(String.valueOf(Double.valueOf(selectByPrimaryKey.getTenergy())+Double.valueOf(motion.getKcal())));
			if(selectByPrimaryKey.getDiaryRecommend()!=null) {
				diary.setDiaryRecommend(selectByPrimaryKey.getDiaryRecommend()+motion.getId()+"="+ddf1.format(Double.valueOf(motion.getKcal()))+"="+nums+""+minute+";");
			}else {
				diary.setDiaryRecommend(motion.getId()+"="+ddf1.format(Double.valueOf(motion.getKcal()))+"="+nums+""+minute+";");
			}
			diary.setRemark(String.valueOf(second+Double.valueOf(selectByPrimaryKey.getRemark())));
			diary.setUweight(recommend1);
			sport=YhSportDiaryService.updateByPrimaryKeySelective(diary);//修改
			
			ssmotion.setHot(motion.getHot()+1);
			ssmotion.setId(Integer.valueOf(id));
			yhSportMotionService.updateByPrimaryKeySelective(ssmotion);
		}else {
			diary.setUweight(recommend1);
			diary.setEntryTime(shijian);
			diary.setTenergy(String.valueOf(Double.valueOf(motion.getKcal())));
			diary.setDiaryRecommend(motion.getId()+"="+ddf1.format(Double.valueOf(motion.getKcal()))+"="+nums+""+minute+";");
			diary.setUserCode(userCode);
			diary.setRemark(String.valueOf(second));
			sport=YhSportDiaryService.insertSelective(diary);//增加
			
			ssmotion.setHot(motion.getHot()+1);
			ssmotion.setId(Integer.valueOf(id));
			yhSportMotionService.updateByPrimaryKeySelective(ssmotion);
		}
		
		return Result.ok(sport);
	}
    
    
    /**
     * 运动增加
     * @param userCode
     * @param id
     * @param nums
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/Sportadd")
	@ResponseBody
	public Result showsky(String userCode,String id,String nums,String entryTime,String datasouce) throws ParseException {
		Map<String, Object> map=new HashMap<String, Object>();
		NumberFormat ddf1=NumberFormat.getNumberInstance() ; 
		ddf1.setMaximumFractionDigits(2); 
		YhSportDiary diary=new YhSportDiary();
		if(datasouce!=null && !datasouce.equals("")) {
			diary.setData_Source(datasouce);
		}
		YhSportMotion ssmotion=new YhSportMotion();
		Date dt = new Date();  
		map.put("entryTime", entryTime);
		map.put("userCode", userCode);
		YhSportDiary selectByPrimaryKey = YhSportDiaryService.showsky(map);//根据code和时间查询当前是否存在数据
		
		YhSportMotion motion = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(id));//运动表
		double  second=0;//秒数
		String minute="";
		if(motion.getSportType()==1) {
			minute="分";
			second=Double.valueOf(nums)*60;
		}else {
			minute="个";
			if(motion.getSportNumberduration()!=null) {
				second=Double.valueOf(nums)*Double.valueOf(motion.getSportNumberduration());
			}else {
				second=Double.valueOf(nums)*2;
			}
			
		}
		
		YhUserReginfo info=YhUserReginfoService.findYhUserReginfo(userCode);
		YhDiseaseAssessTotalReport2web report=yhDiseaseAssessTotalReport2Mapper.showascReport(userCode);
		String recommend1 = "0";
		if(report==null) {
			 Date birthDate=info.getBirthDate(); int age=getAgeByBirth(birthDate);//获取年龄
			 int gender=info.getGender();//性别
			if(gender==1) {//男 
				recommend1=String.valueOf(1600*0.15);//推荐消耗能量男
			}else {//女 
				recommend1=String.valueOf(1200*0.15);//推荐消耗能量女 }
			}
		}else {
			YhFoodSrecipeRecordApp foodrecord=YhFoodSrecipeRecordAppMapper.findFoodSrecipeRecordByUsercodeForNow(userCode);//饮食套餐的能量等级
			if(foodrecord!=null) {
				if(report.getBmi()<=24) {//正常
					recommend1=String.valueOf(foodrecord.getRecordKay()*0.15);
				}else {
					recommend1=String.valueOf(foodrecord.getRecordKay()*0.2);
				}
				
			}else {
				if(report.getBmi()<=24) {//正常
					recommend1=String.valueOf(1600*0.15);
				}else {
					recommend1=String.valueOf(1200*0.2);
				}
			}
			
		}

		
		
			
		int sport=0;
		if(selectByPrimaryKey!=null) {
			diary.setuSportId(selectByPrimaryKey.getuSportId());
			diary.setTenergy(String.valueOf(Double.valueOf(selectByPrimaryKey.getTenergy())+Double.valueOf(motion.getKcal())*Double.valueOf(nums)));
			if(selectByPrimaryKey.getDiaryDetails()!=null) {
				diary.setDiaryDetails(selectByPrimaryKey.getDiaryDetails()+motion.getId()+"="+String.format("%.2f",Double.valueOf(motion.getKcal())*Double.valueOf(nums))+"="+nums+minute+";");
			}else {
				diary.setDiaryDetails(motion.getId()+"="+String.format("%.2f",Double.valueOf(motion.getKcal())*Double.valueOf(nums))+"="+nums+minute+";");
			}
			diary.setUweight(recommend1);
			diary.setRemark(String.valueOf(second+Double.valueOf(selectByPrimaryKey.getRemark())));

			sport=YhSportDiaryService.updateByPrimaryKeySelective(diary);//修改
			
			ssmotion.setHot(motion.getHot()+1);
			ssmotion.setId(Integer.valueOf(id));
			yhSportMotionService.updateByPrimaryKeySelective(ssmotion);
		}else {
			diary.setEntryTime(entryTime);
			diary.setUweight(recommend1);
			diary.setTenergy(String.valueOf(Double.valueOf(motion.getKcal())*Double.valueOf(nums)));
			diary.setDiaryDetails(motion.getId()+"="+String.format("%.2f",Double.valueOf(motion.getKcal())*Double.valueOf(nums))+"="+nums+minute+";");
			diary.setUserCode(userCode);
			diary.setRemark(String.valueOf(second));
			sport=YhSportDiaryService.insertSelective(diary);//增加
			
			ssmotion.setHot(motion.getHot()+1);
			ssmotion.setId(Integer.valueOf(id));
			yhSportMotionService.updateByPrimaryKeySelective(ssmotion);
		}
		return Result.ok(sport);
	}
	


}