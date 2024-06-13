package com.yihe.applet.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.util.common.util.Result;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.service.YhSportMotionService;

import io.swagger.annotations.Api;

/**
 * YhSportMotioncontroller
 * Created by dong on 2020/2/24.
 */
@Controller
@RequestMapping("/api/YhSportMotion")
@Api(value = "YhSportMotion控制器", description = "YhSportMotion管理")
public class YhSportMotionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhSportMotionController.class);
	@Autowired
	YhSportMotionService yhSportMotionService;

	@RequestMapping(value = "/findInfoAll")
	@ResponseBody
	public Result findInfoAll(Integer id) {
		YhSportMotion selectByPrimaryKey = yhSportMotionService.selectByPrimaryKey(id);
		return Result.ok(selectByPrimaryKey);
	}
	
	
	/**
	 * 查询运动热量
	 * @param sportName
	 * @return
	 */
	@RequestMapping(value = "/sportShowName")
	@ResponseBody
	public Result sportShowName(String sportName) {
		if(sportName==null || sportName=="") {
			return Result.ok();
		}else {
			YhSportMotion selectByPrimaryKey = yhSportMotionService.sportShowName(sportName);
			return Result.ok(selectByPrimaryKey);
		}
		
	}
	
	/**
	 * 查询最新的10条运动
	 * @return
	 */
	@RequestMapping(value = "/selectNewestTendata")
	@ResponseBody
	public Result selectNewestTendata() {
			 List<YhSportMotion> list = yhSportMotionService.selectNewestTendata();
			return Result.ok(list);
		
		
	}
	
	/**
	 * 模糊查询五条运动名称
	 * @author yx
	 * @date 2020年10月12日15:20:34
	 * @param sportName
	 * @return
	 */
	@RequestMapping(value = "/selectSportNameByParam")
	@ResponseBody
	public Result selectSportNameByParam(String sportName) {
			 List<YhSportMotion> list = yhSportMotionService.selectSportNameByParam(sportName);
			return Result.ok(list);
		
		
	}
	
	
	/**
	 * 模糊查询
	 * @param sportName
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/selectByname")
	@ResponseBody
	public Result selectByname(String sportName,int curPage,int pageSize) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("sportName", sportName);
		map.put("curPage", (curPage-1)*pageSize);
		map.put("pageSize", pageSize);
		List<YhSportMotion> selectByPrimaryKey = yhSportMotionService.selectByname(map);
		return Result.ok(selectByPrimaryKey);
	}
	
	/**
	 * 热门运动
	 * @param sportName
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/selectByhot")
	@ResponseBody
	public Map<String, Object> selectByhot(int curPage,int pageSize,String userCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("curPage",(curPage-1)*pageSize);
		map.put("pageSize", pageSize);
		List<YhSportMotion> selectByPrimaryKey = yhSportMotionService.selectByhot(map);
		
		List<YhSportMotion> selectByhotCollect = yhSportMotionService.selectByhotCollect(userCode);
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("hottest", selectByPrimaryKey);
		map1.put("collect", selectByhotCollect);
		return map1;
	}
	
	


}