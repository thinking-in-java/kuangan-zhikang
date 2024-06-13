/**
 * 
 */
package com.yihe.web.controller.system;

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

import com.yihe.system.domain.SportDiaryDetailsWeb;
import com.yihe.system.domain.SportListWeb;
import com.yihe.system.domain.YhSportCategoryWeb;
import com.yihe.system.domain.YhSportDiary;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.mapper.YhSportCategoryWebMapper;
import com.yihe.system.service.IYhSportDiaryService;
import com.yihe.system.service.YhSportMotionService;
import com.yihe.system.utils.Result;

/**
 * 运动报告
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhSportReportWeb")
public class YhSportReportWeb {
	
	 	@Autowired
	 	YhSportCategoryWebMapper yhSportCategoryAppMapper;
	 

	    @Autowired
		YhSportMotionService yhSportMotionService;
	    
	    
	    @Autowired
	    IYhSportDiaryService YhSportDiaryService;
	    
	    
	    /**
         * 运动日记查询
		* @param userCode
		* @param entryTime
		* @return
		*/
		@RequestMapping(value = "showsky")
		@ResponseBody
		public Result showlist1(String userCode,String entryTime) {
		
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
				SportListWeb wellUserSportDiaryList=getSportDiaryByDate1(selectByPrimaryKey);
				return Result.ok(wellUserSportDiaryList);
			}
			
		}
		
		
		 public SportListWeb getSportDiaryByDate1(YhSportDiary wellUserSportDiary){
			 SportListWeb vojo=new SportListWeb();
				List<SportDiaryDetailsWeb> wellUserSportDiaryList=new ArrayList<SportDiaryDetailsWeb>();
				List<SportDiaryDetailsWeb> wellUserSportDiaryList1=new ArrayList<SportDiaryDetailsWeb>();
				List<SportDiaryDetailsWeb> wellUserSportDiaryList2=new ArrayList<SportDiaryDetailsWeb>();
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
									SportDiaryDetailsWeb sportDiaryDetails=new SportDiaryDetailsWeb();
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
										SportDiaryDetailsWeb sportDiaryDetails=new SportDiaryDetailsWeb();
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
										SportDiaryDetailsWeb sportDiaryDetails=new SportDiaryDetailsWeb();
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
	     * 运动每日报告
	     * @param userCode
	     * @param entryTime
	     * @return
	     */
		@RequestMapping(value = "/showReportDaily")
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
									YhSportCategoryWeb cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
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
										YhSportCategoryWeb cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
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
										YhSportCategoryWeb cate=yhSportCategoryAppMapper.selectByPrimaryKey(Integer.valueOf(classity[i]));
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
