package com.yihe.applet.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhSportMotionVojo;
import com.yihe.applet.api.util.common.util.Result;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.domain.YhSportRecommendRecord;
import com.yihe.system.domain.YhSportSetmeal;
import com.yihe.system.domain.YhSportSubject;
import com.yihe.system.mapper.YhSportSubjectMapper;
import com.yihe.system.service.YhSportMotionService;
import com.yihe.system.service.YhSportRecommendRecordService;
import com.yihe.system.service.YhSportSetmealService;

import io.swagger.annotations.Api;

/**
 * YhSportSetmealcontroller
 * Created by dong on 2020/3/9.
 */
@Controller
@RequestMapping("/api/YhSportSetmeal")
@Api(value = "YhSportSetmeal控制器", description = "YhSportSetmeal管理")
public class YhSportSetmealController{

    private static final Logger LOGGER = LoggerFactory.getLogger(YhSportSetmealController.class);
	
    @Autowired
    YhSportSetmealService yhSportSetmealService;
    
    @Autowired
    YhSportSubjectMapper yhSportSubjectService;
	@Autowired
	YhSportMotionService yhSportMotionService;

    @Autowired
    YhSportRecommendRecordService yhSportRecommendRecordService;
 
    
	@RequestMapping(value = "/SetselectByPrimaryKey")
	@ResponseBody
	public Result SetselectByPrimaryKey(int id) {
		YhSportSetmeal set=yhSportSetmealService.selectByPrimaryKey(id);
	return Result.ok(set);
	}
    
    /**
                * 根据主键查询记录
     * @param id
     * @param finishid 第几天
     * @return
     */
	@RequestMapping(value = "/selectBySetmeal")
	@ResponseBody
	public Result showSportRecommended(int id,int number,int recid) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("recid", recid);
		YhSportRecommendRecord record=yhSportRecommendRecordService.selectByRecord(map);//查询是否打卡
		
		YhSportSetmeal setmeal=yhSportSetmealService.selectByPrimaryKey(id);
		List<YhSportMotionVojo> vojo=new ArrayList<YhSportMotionVojo>();
		if(setmeal!=null) {
			String[] subjectId=setmeal.getSubjectId().split(",");//套餐
			YhSportSubject subject=yhSportSubjectService.selectByPrimaryKey(Integer.valueOf(subjectId[Integer.valueOf(record.getNumber())]));//运动主题表
			String[] subjectMotion=subject.getSubjectMotion().split(",");//运动主题
			if(subjectMotion!=null) {
				for (int i = 0; i < (subjectMotion.length-1); i++) {
					YhSportMotionVojo spor=new YhSportMotionVojo();
					spor.setStandby(setmeal.getDescribe());//运动描述
					spor.setNum(String.valueOf(subject.getSubjectDays()));//天数
					spor.setMinute(subject.getSubjectTime());//运动分钟
					spor.setKcalsum(subject.getRemark());//总kcal里
					spor.setSubjectId(Integer.valueOf(subjectId[Integer.valueOf(record.getNumber())]));
					YhSportMotion motion=yhSportMotionService.selectByPrimaryKey(Integer.valueOf(subjectMotion[i]));
					System.out.println(motion.getId());
					spor.setSportType(motion.getSportType());
					spor.setId(motion.getId());
					spor.setSportImg(motion.getSportImg());
					if(motion.getFollowSecond()!=null) {
						String[] followSecond=motion.getFollowSecond().split("_");//动作完成序号
						if(followSecond[0].equals("1")) {
							spor.setKcal(followSecond[1]+"秒");
						}else {
							spor.setKcal(followSecond[1]+"次");
						}
					}else {
						spor.setKcal("10秒");
					}
					
					spor.setSportName(motion.getSportName());
					
					if(record.getFinishid()==null || record.getFinishid().equals("")) {
						spor.setWhether(0);
					}else {
						String[] finishid=record.getFinishid().split(",");//动作完成序号
						for (int j = 0; j < finishid.length; j++) {
							if(Integer.valueOf(finishid[j])==i+1) {
								spor.setWhether(1);
							}
						}
					}
					
					vojo.add(spor);
				}
			}
			
		}
		return Result.ok(vojo);
	}


}