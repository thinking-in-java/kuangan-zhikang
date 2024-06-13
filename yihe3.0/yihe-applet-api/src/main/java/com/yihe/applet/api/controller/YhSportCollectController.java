package com.yihe.applet.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhSportCollect;
import com.yihe.applet.api.service.YhSportCollectService;
import com.yihe.applet.api.util.common.util.Result;

import io.swagger.annotations.Api;

/**
 * YhSportCollectcontroller
 * Created by dong on 2020/2/25.
 */
@Controller
@RequestMapping("/api/YhSportCollect")
@Api(value = "YhSportCollect控制器", description = "YhSportCollect管理")
public class YhSportCollectController{

    private static final Logger LOGGER = LoggerFactory.getLogger(YhSportCollectController.class);
	

    @Autowired
    YhSportCollectService yhSportCollectService;
   
    
   /**
    * 删除收藏
    * @param collect
    * @return
    */
     @RequestMapping(value = "/deleteKeymapSportCpllect")
 	@ResponseBody
 	public Result deleteByPrimaryKeymap(YhSportCollect collect) {
     	Map<String, Object> map=new HashMap<String, Object>();
     	map.put("userCode", collect.getUserCode());
     	map.put("sportId", collect.getSportId());
     	int repe=yhSportCollectService.deleteByPrimaryKeymap(map);
 		return Result.ok(repe);
     }
    
    /**
     * 查询该运动是否存在收藏
     * @param collect
     * @return
     */
     @RequestMapping(value = "/showCollectRepetition")
 	@ResponseBody
 	public Result showCollectRepetition(YhSportCollect collect) {
     	Map<String, Object> map=new HashMap<String, Object>();
     	map.put("userCode", collect.getUserCode());
     	map.put("sportId", collect.getSportId());
     	int repe=yhSportCollectService.showCollectRepetition(map);
 		return Result.ok(repe);
     }
    
    
   /**
    * 运动收藏表增加
    * @param collect
    * @return
    */
    @RequestMapping(value = "/SportCollectadd")
	@ResponseBody
	public Result showsky(YhSportCollect collect) {
    	Map<String, Object> map=new HashMap<String, Object>();
    	map.put("userCode", collect.getUserCode());
    	map.put("sportId", collect.getSportId());
    	int repe=yhSportCollectService.showCollectRepetition(map);
    	int coll=0;
    	if(repe==0) {
    		coll=yhSportCollectService.insertSelective(collect);
    	}
		return Result.ok(coll);
    }


}