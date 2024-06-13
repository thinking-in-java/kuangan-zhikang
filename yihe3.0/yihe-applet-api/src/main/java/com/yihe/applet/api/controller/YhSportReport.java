/**
 * 
 */
package com.yihe.applet.api.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.SportDiaryDetails;
import com.yihe.applet.api.domain.SportListVojo;
import com.yihe.applet.api.domain.YhSportCategoryApp;
import com.yihe.applet.api.mapper.YhSportCategoryAppMapper;
import com.yihe.applet.api.util.common.util.Result;
import com.yihe.system.domain.YhSportDiary;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.service.IYhSportDiaryService;
import com.yihe.system.service.YhSportMotionService;

/**
 * 运动报告
 * @author dong
 *
 */
@Controller
@RequestMapping("/api/YhSportReport")
public class YhSportReport {

	
	 @Autowired
	 YhSportCategoryAppMapper yhSportCategoryAppMapper;
	 

	    @Autowired
		YhSportMotionService yhSportMotionService;
	    
	    
	    @Autowired
	    IYhSportDiaryService YhSportDiaryService;
	
	    
	    /**
	     * 运动每日报告
	     * @param userCode
	     * @param entryTime
	     * @return
	     */
		@RequestMapping(value = "showReportDaily")
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
		if(selectByPrimaryKey!=null) {
			Map<String, Object> wellUserSportDiaryList=getSportDiaryByDate(selectByPrimaryKey);
			return Result.ok(wellUserSportDiaryList);
			
		}
		return Result.ok();
		}
		
		
		
		
		
		
		public Map<String, Object> getSportDiaryByDate(YhSportDiary wellUserSportDiary){
			double aerobic=0;//有氧
			double anaerobic=0;//无氧
			double pliable=0;//柔韧
			double arthrosis=0;//关节
			
			double walk=0;//步行
			double recommend=0;//推荐
			double boutique=0;//精品
			double rests=0;//其他
			
			String sKcalId="";
					//不等于空的时候处理日志详情
					if(wellUserSportDiary!=null){
						
						//步数
						if(wellUserSportDiary.getNumber()!=null) {
							String[] number = wellUserSportDiary.getNumber().split("=");//运动分类
							walk+=Double.valueOf(number[1]);
							aerobic+=Double.valueOf(number[1]);
						}
						
						//其他运动
						if(wellUserSportDiary.getDiaryDetails()!=null){
						String diaryDetails=wellUserSportDiary.getDiaryDetails();
						
						diaryDetails=diaryDetails.substring(0, diaryDetails.length());
						
						for (String diaryDetail : diaryDetails.split(";")) {
							sKcalId=diaryDetail.split("=")[0];
							rests+=Double.valueOf(diaryDetail.split("=")[1]);
							YhSportMotion wellSportKcall = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sKcalId));//运动表
							if(wellSportKcall!=null){
								String[] classity = wellSportKcall.getSportClassity().split(";");//运动分类
								
								for (int i = 0; i < classity.length; i++) {
									YhSportCategoryApp cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
									if(cate.getCateName().equals("无氧运动")) {
										anaerobic+=Double.valueOf(diaryDetail.split("=")[1]);
									}else if(cate.getCateName().equals("有氧运动")) {
										aerobic+=Double.valueOf(diaryDetail.split("=")[1]);
									}else if(cate.getCateName().equals("柔韧运动")) {
										pliable+=Double.valueOf(diaryDetail.split("=")[1]);
									}else if(cate.getCateName().equals("关节活动")) {
										arthrosis+=Double.valueOf(diaryDetail.split("=")[1]);
									}
								}
								
							}	
						}
					}
						
						//精品课程
						if(wellUserSportDiary.getDiaryBoutique()!=null){
							String diaryDetails=wellUserSportDiary.getDiaryBoutique();
							
							for (String diaryDetail : diaryDetails.split(";")) {
								sKcalId=diaryDetail.split("=")[0];
								boutique+=Double.valueOf(diaryDetail.split("=")[1]);
								YhSportMotion wellSportKcall = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sKcalId));//运动表
								if(wellSportKcall!=null){
									String[] classity = wellSportKcall.getSportClassity().split(";");//运动分类
									
									for (int i = 0; i < classity.length; i++) {
										YhSportCategoryApp cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
										if(cate.getCateName().equals("无氧运动")) {
											anaerobic+=Double.valueOf(diaryDetail.split("=")[1]);
										}else if(cate.getCateName().equals("有氧运动")) {
											aerobic+=Double.valueOf(diaryDetail.split("=")[1]);
										}else if(cate.getCateName().equals("柔韧运动")) {
											pliable+=Double.valueOf(diaryDetail.split("=")[1]);
										}else if(cate.getCateName().equals("关节活动")) {
											arthrosis+=Double.valueOf(diaryDetail.split("=")[1]);
										}
									}
									
								}	
							}
							
							}
						
						//推荐运动
						if(wellUserSportDiary.getDiaryRecommend()!=null){
							String diaryDetails=wellUserSportDiary.getDiaryRecommend();
							
							for (String diaryDetail : diaryDetails.split(";")) {
								sKcalId=diaryDetail.split("=")[0];
								recommend+=Double.valueOf(diaryDetail.split("=")[1]);
								YhSportMotion wellSportKcall = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(sKcalId));//运动表
								if(wellSportKcall!=null){
									String[] classity = wellSportKcall.getSportClassity().split(";");//运动分类
									
									for (int i = 0; i < classity.length; i++) {
										YhSportCategoryApp cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
										if(cate.getCateName().equals("无氧运动")) {
											anaerobic+=Double.valueOf(diaryDetail.split("=")[1]);
										}else if(cate.getCateName().equals("有氧运动")) {
											aerobic+=Double.valueOf(diaryDetail.split("=")[1]);
										}else if(cate.getCateName().equals("柔韧运动")) {
											pliable+=Double.valueOf(diaryDetail.split("=")[1]);
										}else if(cate.getCateName().equals("关节活动")) {
											arthrosis+=Double.valueOf(diaryDetail.split("=")[1]);
										}
									}
									
								}	
							}
							
							}
						
					}
					
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("uweight", wellUserSportDiary.getUweight());
			map.put("tenergy", wellUserSportDiary.getTenergy());
			
			map.put("aerobicpercent", String.format("%.2f",aerobic/Double.valueOf(wellUserSportDiary.getTenergy())*100));
			map.put("anaerobicpercent", String.format("%.2f",anaerobic/Double.valueOf(wellUserSportDiary.getTenergy())*100));
			map.put("pliablepercent", String.format("%.2f",pliable/Double.valueOf(wellUserSportDiary.getTenergy())*100));
			map.put("arthrosispercent", String.format("%.2f",arthrosis/Double.valueOf(wellUserSportDiary.getTenergy())*100));
			map.put("walkpercent", String.format("%.2f",walk/Double.valueOf(wellUserSportDiary.getTenergy())*100));
			map.put("recommendpercent", String.format("%.2f",recommend/Double.valueOf(wellUserSportDiary.getTenergy())*100));
			map.put("boutiquepercent", String.format("%.2f",boutique/Double.valueOf(wellUserSportDiary.getTenergy())*100));
			map.put("restspercent", String.format("%.2f",rests/Double.valueOf(wellUserSportDiary.getTenergy())*100));
			
			
			List<String> list =new ArrayList<String>();
			 list.add("慢跑:22分钟");
			 list.add("快走:25分钟");
			 list.add("游泳:20分钟");
			 list.add("自行车:25分钟");
			 list.add("打篮球:25分钟");
			 list.add("爬楼:31分钟");
			 
			map.put("sportList", list);
			map.put("aerobic", aerobic);
			map.put("anaerobic", anaerobic);
			map.put("pliable", pliable);
			map.put("arthrosis", arthrosis);
			map.put("walk", walk);
			map.put("recommend", recommend);
			map.put("boutique", boutique);
			map.put("rests", rests);
					
			return map;
			
			
		}
	    


}
