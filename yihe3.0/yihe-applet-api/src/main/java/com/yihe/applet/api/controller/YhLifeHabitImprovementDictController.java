package com.yihe.applet.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhLifeHabitImprovementDictApp;
import com.yihe.applet.api.mapper.YhLifeHabitImprovementDictAppMapper;

/**
 * YhLifeHabitImprovementDictcontroller
 * Created by dong on 2020/1/17.
 */
@Controller
@RequestMapping("/api/YhLifeHabitImprovementDictController")
@Api(value = "YhLifeHabitImprovementDict控制器", description = "YhLifeHabitImprovementDict管理")
public class YhLifeHabitImprovementDictController{

    private static final Logger LOGGER = LoggerFactory.getLogger(YhLifeHabitImprovementDictController.class);

    @Autowired
    YhLifeHabitImprovementDictAppMapper yhLifeHabitImprovementDictService;
    
    
    
    @RequestMapping(value="/selectimprovementCount",method=RequestMethod.POST)
    @ResponseBody
    private int selectimprovementCount() {
    	int list=yhLifeHabitImprovementDictService.selectimprovementCount();
		return list;
    }
    
    /**
     * 根据usercode的查询生活习惯改膳文件答案
     * @param usercode
     * @return
     */
    @RequestMapping(value="/selectimprovement",method=RequestMethod.POST)
    @ResponseBody
    private YhLifeHabitImprovementDictApp showlist(String usercode) {
    	YhLifeHabitImprovementDictApp list=yhLifeHabitImprovementDictService.selectimprovement(usercode);
		return list;
    }
    
    
    /**
     * 根据usercode的查询生活习惯改膳文件答案
     * @param usercode
     * @return
     */
    @RequestMapping(value="/selectimprovementKeyid",method=RequestMethod.POST)
    @ResponseBody
    private YhLifeHabitImprovementDictApp selectimprovementKeyid(int id) {
    	YhLifeHabitImprovementDictApp list=yhLifeHabitImprovementDictService.selectByPrimaryKey(id);
		return list;
    }
    
    
    
   /**
    * 根据code查询所有答案
    * @param usercode
    * @return
    */
    @RequestMapping(value="/selectimprovementAll",method=RequestMethod.POST)
    @ResponseBody
    private List<YhLifeHabitImprovementDictApp> selectimprovementAll(String usercode) {
    	List<YhLifeHabitImprovementDictApp> list=yhLifeHabitImprovementDictService.selectHabitCodeDict(usercode);
		return list;
    }

}