package com.yihe.applet.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.util.common.util.Result;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.domain.YhSportSubject;
import com.yihe.system.mapper.YhSportSubjectMapper;
import com.yihe.system.service.YhSportMotionService;

import io.swagger.annotations.Api;

/**
 * YhSportSubjectcontroller
 * Created by dyb on 2020/1/9.
 */
@Controller
@RequestMapping("/api/YhSportSubject")
@Api(value = "YhSportSubject控制器", description = "YhSportSubject管理")
public class YhSportSubjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhSportSubjectController.class);
	
    @Autowired
    YhSportSubjectMapper yhSportSubjectService;

	@Autowired
	YhSportMotionService yhSportMotionService;
	
	
	/*
	 * @RequestMapping(value = "/findInfoAll")
	 * 
	 * @ResponseBody public Result findInfoAll(){ YhSportSubjectExample example=new
	 * YhSportSubjectExample(); List<YhSportSubject> selectByExample =
	 * yhSportSubjectService.selectByExample(example); return
	 * Result.ok(selectByExample); }
	 */

    
    @RequestMapping(value = "/findInfoAllById")
   	@ResponseBody
       public Result findInfoAllById(Integer id){
        List<YhSportMotion> list=new ArrayList<YhSportMotion>();
       	YhSportSubject selectByPrimaryKey = yhSportSubjectService.selectByPrimaryKey(id);
       	String subjectMotion = selectByPrimaryKey.getSubjectMotion();
       	if(null!=subjectMotion){
       		String[]  strs=subjectMotion.split(",");
       		if(strs != null && strs.length != 0){
       			for(int i=0,len=strs.length;i<len;i++){
           			String string = strs[i];
           		    //string 为yh_sport_motion表id
           			YhSportMotion yhSportMotionInfo = yhSportMotionService.selectByPrimaryKey(Integer.valueOf(string));
           			if(null==yhSportMotionInfo){
           				continue;
           			}
           			String sportImg = yhSportMotionInfo.getSportImg();
           			if(null!=sportImg){
           				String[]  Imgstr=sportImg.split(",");
           				if(Imgstr != null && Imgstr.length != 0){
           					String stringImg = Imgstr[0];
               				yhSportMotionInfo.setSportImg(stringImg);
           				}
           				
           			}
           			list.add(yhSportMotionInfo);
           		}
       		}
       	}
       	return Result.ok(list);
       }
    
    
}