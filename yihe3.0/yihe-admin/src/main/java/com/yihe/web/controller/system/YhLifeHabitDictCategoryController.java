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

import com.yihe.system.domain.YhLifeHabitDictCategory;
import com.yihe.system.mapper.YhLifeHabitDictCategoryMapper;
import com.yihe.system.mapper.YhLifeHabitDictMapper;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;

/**
 * 习惯分类管理
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhLifeHabitDictCategoryController")
public class YhLifeHabitDictCategoryController {	

	
	@Autowired
	YhLifeHabitDictCategoryMapper yhLifeHabitDictCategoryMapper;
	
	@Autowired
	YhLifeHabitDictMapper yhLifeHabitDictMapper;
	
	
	@ApiOperation(value = "删除分类")
	   @RequestMapping(value = "/deleteDictId", method = RequestMethod.POST)
		@ResponseBody
	   public Result  deleteDictId(int id) { 
		 int i=yhLifeHabitDictCategoryMapper.deleteByPrimaryKey(id);
	       return Result.ok(i);
	   }
	
	
	 @ApiOperation(value = "根据id查询分类")
	   @RequestMapping(value = "/selectDictId", method = RequestMethod.POST)
		@ResponseBody
	   public Result  selectByPrimaryKey(int id) { 
		 YhLifeHabitDictCategory book=yhLifeHabitDictCategoryMapper.selectByPrimaryKey(id);
	       return Result.ok(book);
	   }
	
	 @ApiOperation(value = "增加/修改分类")
	   @RequestMapping(value = "/insertDict", method = RequestMethod.POST)
		@ResponseBody
	   public Result  insertfood(YhLifeHabitDictCategory book) { 
		 int i;
		 if(book.getId()==0) {
			 i=yhLifeHabitDictCategoryMapper.insertSelective(book);
		 }else {
			 i=yhLifeHabitDictCategoryMapper.updateByPrimaryKeySelective(book);
		 }
	       return Result.ok(i);
	   }
	 
	 
	
	@ApiOperation(value = "查询分类名称是否存在")
	@RequestMapping(value = "/selctCategoryName", method = RequestMethod.GET)
	@ResponseBody
	public int selctCategoryName(String categoryName,int id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("categoryName", categoryName);
		map.put("id", id);
		int show = yhLifeHabitDictCategoryMapper.selctCategoryName(map);
		return show;
	}
	
	
	   @ApiOperation(value = "查询习惯分类")
	      @RequestMapping(value = "/ShowselectByPrimaryKey", method = RequestMethod.POST)
	  	@ResponseBody
	  	public Map<String,Object> showSport(){
		  List<YhLifeHabitDictCategory> list=yhLifeHabitDictCategoryMapper.ShowselectByPrimaryKey();
		  for (YhLifeHabitDictCategory yhLifeHabitDictCategory : list) {
			  yhLifeHabitDictCategory.setNum(yhLifeHabitDictMapper.CountHAbitType(yhLifeHabitDictCategory.getId()));
		}
	  		Map<String,Object> map = new HashMap<String,Object>();
	  		map.put("code", 20000);
	  		map.put("msg", "");
	  		map.put("count","");
	  		map.put("data", list);
	  		return map;
	  	}
	
}
