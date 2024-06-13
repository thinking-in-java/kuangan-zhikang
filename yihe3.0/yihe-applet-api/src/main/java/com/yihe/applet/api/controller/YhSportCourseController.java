/**
 * 
 */
package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhSportCourse;
import com.yihe.applet.api.domain.YhSportCourseDetail;
import com.yihe.applet.api.domain.YhUserSportCourseAdd;
import com.yihe.applet.api.mapper.YhSportCourseDetailMapper;
import com.yihe.applet.api.mapper.YhSportCourseMapper;
import com.yihe.applet.api.mapper.YhSportViconMapper;
import com.yihe.applet.api.mapper.YhUserSportCourseAddMapper;
import com.yihe.applet.api.util.common.util.Result;
import com.yihe.system.domain.YhSportGoals;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.domain.YhSportPart;
import com.yihe.system.domain.YhSportStrength;
import com.yihe.system.mapper.*;
import com.yihe.system.service.YhQuestionCheckJson2WebService;
import com.yihe.system.service.YhSportMotionService;
import com.yihe.system.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运动课程
 * @author dong
 *
 */
@Controller
@RequestMapping("/api/YhSportCourseController")
public class YhSportCourseController {
	 
    @Autowired
    YhUserReginfoMapper YhUserReginfoService;
    
	@Autowired
	YhSportCourseMapper yhSportCourseMapper;
	
	@Autowired
	YhSportCourseDetailMapper yhSportCourseDetailMapper;
	
	@Autowired
	YhSportViconMapper yhSportViconMapper;
	
	@Autowired
	YhSportMotionService yhSportMotionService;
	
	@Autowired
	YhUserSportCourseAddMapper yhUserSportCourseAddMapper;
	
	 @Autowired
	 private YhDiseaseAssessTotalReport2Mapper yhDiseaseAssessTotalReport2Mapper;
	 
	 @Autowired
		YhQuestionCheckJson2WebService yhQuestionCheckJson2WebService;
	 
	 @Autowired
	 YhSportGoalsMapper YhSportGoalsMapper;
	 
	 @Autowired
	 YhSportPartMapper yhSportPartMapper;
	 
	 @Autowired
	 YhSportStrengthMapper yhSportStrengthMapper;
	 
	 
	 
	 /**
		 * 动作强度
		 * @param courseId
		 * @return
		 */
		@RequestMapping(value = "showSportStrength")
		@ResponseBody
		public Result showSportStrength() {
			List<YhSportStrength> i=yhSportStrengthMapper.showSportStrength();
			return Result.ok(i);
		}
		
		
	 /**
		 * 锻炼部位
		 * @param courseId
		 * @return
		 */
		@RequestMapping(value = "showSportPart")
		@ResponseBody
		public Result showSportPart() {
			List<YhSportPart> i=yhSportPartMapper.showSportPart();
			return Result.ok(i);
		}
		
	 
	 /**
		 * 锻炼目标
		 * @param courseId
		 * @return
		 */
		@RequestMapping(value = "showSportGoals")
		@ResponseBody
		public Result showSportGoals() {
			List<YhSportGoals> i=YhSportGoalsMapper.showSportGoals();
			return Result.ok(i);
		}
		
		
		
	 
	 /**
		 * 估计课程id与天数查询运动课程动作库
		 * @param courseType
		 * @param curPage
		 * @param pageSize
		 * @return
		 */
		@RequestMapping(value = "selectByDetailParticularsArrange")
		@ResponseBody
		public Result selectByDetailParticularsArrange(int courseId) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("courseId", courseId);
			map.put("cdetailDay", 1);
			YhSportCourseDetail detail=yhSportCourseDetailMapper.selectByDetailParticulars(map);
			String[] strarray=detail.getViconIds().split(";");
			List<YhSportMotion> list=new ArrayList<YhSportMotion>();
			for (int i = 0; i < strarray.length; i++) {
				if(strarray[i]!=null && !strarray[i].equals("")) {
					System.out.println(strarray[i]);
					YhSportMotion vojo=yhSportMotionService.selectByPrimaryKey(Integer.valueOf(strarray[i]));
					vojo.setSky(detail.getCdetailDay());//天数
					vojo.setMinute(String.valueOf(detail.getCdetailTotalduration()));//分钟
					vojo.setKilocalorie(String.valueOf(detail.getCdetailTotalkal()));//总千卡
					if(StringUtil.isNotEmpty(vojo.getFollowSecond())) {
						String[] followSecond=vojo.getFollowSecond().split("_");
						if(followSecond[0].equals("1")) {
							vojo.setFollowSecond(followSecond[1]+"秒");
						}else {
							vojo.setFollowSecond(followSecond[1]+"次");
						}
						
					}else {
						vojo.setFollowSecond("15次");
					}
					list.add(vojo);
				}
			}
			return Result.ok(list);
		}
		
		
		
	 
	 /**
		 * 课堂删除
		 * @param courseId
		 * @return
		 */
		@RequestMapping(value = "CourseDelete")
		@ResponseBody
		public Result CourseDelete(int courseid,String userCode) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userCode", userCode);
			map.put("courseId", courseid);
			int i=yhUserSportCourseAddMapper.deleteByPrimaryKey(map);
		return Result.ok(i);
		}
		
		
	/**
	 * 课堂加入
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "CourseAdd")
	@ResponseBody
	public Result CourseAdd(YhUserSportCourseAdd courseId) {
		YhSportCourse course=yhSportCourseMapper.selectByPrimaryKey(courseId.getCourseId());
		if(course!=null) {
			courseId.setStandby(String.valueOf(course.getCourseTotalday()));
		}
		int i=yhUserSportCourseAddMapper.insertSelective(courseId);
	return Result.ok(i);
	}
	
	
	
	/**
	 * 根据课程id查询运动训练
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "selectByPrimaryKeyVicon")
	@ResponseBody
	public Result selectByPrimaryKeyVicon(int viconid) {
		YhSportMotion vojo=yhSportMotionService.selectByPrimaryKey(viconid);
	return Result.ok(vojo);
	}
	
	
	/**
	 * 估计课程id与天数查询运动课程动作库
	 * @param courseType
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "selectByDetailParticulars")
	@ResponseBody
	public Result selectByDetailParticulars(int courseId,String userCode) {
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("userCode", userCode);
		map1.put("courseId", courseId);
		YhUserSportCourseAdd add=yhUserSportCourseAddMapper.selectMapCourse(map1);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("courseId", courseId);
		if(add!=null) {
			map.put("cdetailDay", add.getAddNum());
		}else {
			map.put("cdetailDay", 1);
		}
		
		YhSportCourseDetail detail=yhSportCourseDetailMapper.selectByDetailParticulars(map);
		String[] strarray=detail.getViconIds().split(";");
		List<YhSportMotion> list=new ArrayList<YhSportMotion>();
		for (int i = 0; i < strarray.length; i++) {
			if(strarray[i]!=null && !strarray[i].equals("")) {
				System.out.println(strarray[i]);
				YhSportMotion vojo=yhSportMotionService.selectByPrimaryKey(Integer.valueOf(strarray[i]));
				vojo.setAddnum(add.getAddId());
				if(add.getFinishid()==null || add.getFinishid().equals("")) {
					vojo.setWhether(0);
				}else {
					String[] finishid=add.getFinishid().split(",");//动作完成序号
					for (int j = 0; j < finishid.length; j++) {
						if(Integer.valueOf(finishid[j])==i+1) {
							vojo.setWhether(1);
						}
					}
				}
				vojo.setSky(detail.getCdetailDay());//天数
				vojo.setMinute(String.valueOf(detail.getCdetailTotalduration()));//分钟
				vojo.setKilocalorie(String.valueOf(detail.getCdetailTotalkal()));//总千卡
				if(StringUtil.isNotEmpty(vojo.getFollowSecond())) {
					String[] followSecond=vojo.getFollowSecond().split("_");
					if(followSecond[0].equals("1")) {
						vojo.setFollowSecond(followSecond[1]+"秒");
					}else {
						vojo.setFollowSecond(followSecond[1]+"次");
					}
					
				}else {
					vojo.setFollowSecond("15个");
				}
				list.add(vojo);
			}
		}
		return Result.ok(list);
	}
	
	
	
	/**
	 * 首页查询运动课程
	 * @param courseType
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "showCourseAdd")
	@ResponseBody
	public Result showCourseAdd(String userCode) {
	List<YhSportCourse> list=null;
	list=yhSportCourseMapper.showCourseAdd(userCode);
//	 YhUserReginfo info=YhUserReginfoService.findYhUserReginfo(userCode);
//	if(list==null || list.size()==0) {
//		
//		YhDiseaseAssessTotalReport2web report=yhDiseaseAssessTotalReport2Mapper.showascReport(userCode);
//		if(report==null) {//没有评估
//			int gender=info.getGender();//性别
// 			if(gender==1) {//男 
// 				list=yhSportCourseMapper.selectdawdler("0_2;");
// 			}else {//女 
// 				list=yhSportCourseMapper.selectdawdler("0_1;");
// 			}
//			
//		}else {
//			 YhQuestionCheckJson2Web questionCheckJson2Web = yhQuestionCheckJson2WebService.selectUserByParam(userCode);//查询评估问卷中的运动频率
//			 String checkJson = questionCheckJson2Web.getUserCheckAnswer();
//	         JSONObject object=JSONObject.parseObject(checkJson);//解析json
//			//是否参加运动锻炼
//	         String sport_in = object.getString("sport_in");
//	         String sport="";
//	         if("1".equals(sport_in)) {//参加过运动
//	        	 //运动锻炼的频率
//		         String sport_freq = object.getString("sport_freq");
//		         if("1".equals(sport_freq) || "2".equals(sport_freq) || "3".equals(sport_freq)) {
//		        	 int gender=info.getGender();//性别
//		  			if(gender==1) {//男 
//		  				list=yhSportCourseMapper.selectdawdler("0_2;");
//		  			}else {//女 
//		  				list=yhSportCourseMapper.selectdawdler("0_1;");
//		  			}
//		         }else if("4".equals(sport_freq) || "5".equals(sport_freq)) {
//		        	 Map<String, Object> map=new HashMap<String, Object>();
//		        	 if(report.getBmi()>=24) {//正常
//		        		 map.put("courseStrength", "中级");
//		        		 map.put("courseContent", "燃脂");
//		        		 list=yhSportCourseMapper.selectBySmall(map);
//						}else if(report.getBmi()<18.5) {
//							map.put("courseStrength", "中级");
//			        		 map.put("courseContent", "增肌");
//			        		 list=yhSportCourseMapper.selectBySmall(map);
//						}else {
//							map.put("courseStrength", "中级");
//			        		 map.put("courseContent", "塑形");
//			        		 list=yhSportCourseMapper.selectBySmall(map);
//						}
//		         }
//	         }else {
//	        	 int gender=info.getGender();//性别
//	  			if(gender==1) {//男 
//	  				list=yhSportCourseMapper.selectdawdler("0_2;");
//	  			}else {//女 
//	  				list=yhSportCourseMapper.selectdawdler("0_1;");
//	  			}
//	         }
//		}
//		list.get(0).setChoice(2);
//	}else {
//		list.get(0).setChoice(1);
//	}
	return Result.ok(list);
	}
	
	/**
	 * 根据课程id查询运动训练
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "ShowTrainingcourse")
	@ResponseBody
	public Result showlist(int courseId) {
	List<YhSportCourseDetail> list=yhSportCourseDetailMapper.ShowTrainingcourse(courseId);
	return Result.ok(list);
	}
	
	
	
	/**
	 * 根据课程id查询一个详情
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "ShowCourse")
	@ResponseBody
	public Result selectByPrimaryKey(int courseId,String userCode) {
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("userCode", userCode);
	map.put("courseId", courseId);
	int list=yhSportCourseMapper.showCourseIdRepetition(map);
	YhSportCourse course=yhSportCourseMapper.selectByPrimaryKey(courseId);
	if(list==0) {
		course.setChoice(2);
	}else {
		course.setChoice(1);
	}
	return Result.ok(course);
	}
	
	
	
	/**
	 * 查询运动课程
	 * @param courseType
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "ShowTheSportsCourse")
	@ResponseBody
	public Result showlist(String courseType,String courseStrength,String courseContent,int curPage,int pageSize) {
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("courseType", courseType);
	map.put("courseStrength", courseStrength);
	map.put("courseContent", courseContent);
	map.put("curPage", (curPage-1)*pageSize);
	map.put("pageSize", pageSize);
	List<YhSportCourse> list=yhSportCourseMapper.ShowTheSportsCourse(map);
	return Result.ok(list);
	}
	
}
