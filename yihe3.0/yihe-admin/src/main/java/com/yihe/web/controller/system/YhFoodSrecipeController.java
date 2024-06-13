/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhFoodCookBook;
import com.yihe.system.domain.YhFoodSrecipe;
import com.yihe.system.service.YhFoodCookBookService;
import com.yihe.system.service.YhFoodSrecipeService;
import com.yihe.system.service.impl.YhDiseaseCategoryServiceImpl;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 食物套餐控制器
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhFoodSrecipe")
@Api(value = "YhFoodSrecipe控制器", description = "YhFoodSrecipe管理")
public class YhFoodSrecipeController {

	
	@Autowired
	 private YhFoodSrecipeService yhFoodSrecipeService;
	@Autowired
	private YhDiseaseCategoryServiceImpl  YhDiseaseCategory;
	
	
	  @RequestMapping(value = "/finAll", method = RequestMethod.GET)
	 	@ResponseBody
	 	public Result finAll(){
		  List<YhFoodSrecipe> findSrecipeAllInfo = yhFoodSrecipeService.findSrecipeAllInfo();
		  for (YhFoodSrecipe yhFoodSrecipe : findSrecipeAllInfo) {
			  com.yihe.system.domain.YhDiseaseCategory selectByPrimaryKey = YhDiseaseCategory.selectByPrimaryKey(Integer.valueOf(yhFoodSrecipe.getDiseaseid()));
			  Map<String, Object> map=new HashMap<String, Object>();
			  map.put("elevel", yhFoodSrecipe.geteLevel());
			  map.put("felements", yhFoodSrecipe.getfElements());
			  map.put("diseaseid", selectByPrimaryKey.getId());
			  map.put("fsdescribe", "健康风险评估"+selectByPrimaryKey.getDiseaseName()+"饮食推荐");
			  
			  yhFoodSrecipeService.insertAll(map);
		}
		  
		  return Result.ok(findSrecipeAllInfo);
	  }
	
	/**
	 * 查询所有食物套餐
	 * @param page
	 * @param limit
	 * @param fCName
	 * @return
	 */
     @ApiOperation(value = "查询所有食物套餐内容")
     @RequestMapping(value = "/selectSrecipe", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectSrecipe(Integer page,Integer limit,String name){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("name", name);
 		List<YhFoodSrecipe> list = yhFoodSrecipeService.selectSrecipe(map1);
 		for (YhFoodSrecipe yhFoodSrecipe : list) {
 			//yhFoodSrecipe.setName(yhFoodSrecipe.getName()+"食谱");

				//employ
 			String type="";
 			if(yhFoodSrecipe.getdType()==1) {
 				type="清淡";
 			}else if(yhFoodSrecipe.getdType()==2) {
 				type="甜";
 			}else if(yhFoodSrecipe.getdType()==3){
 				type="咸";
 			}else {
 				type="辣";
 			}
 			String gen="";
 			if(yhFoodSrecipe.getGenter()==1) {
 				gen="男性";
 			}else {
 				gen="女性";
 			}
 			yhFoodSrecipe.setEmploy(gen+yhFoodSrecipe.getName()+"患者     "+type);
 			String str=yhFoodSrecipe.getfElements();
 			String[] arr = str.split(";");
 			yhFoodSrecipe.setChang(arr.length);
		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhFoodSrecipeService.selectSrecipeConut(map1));
 		map.put("data", list);
 		return map;
 	}
}
