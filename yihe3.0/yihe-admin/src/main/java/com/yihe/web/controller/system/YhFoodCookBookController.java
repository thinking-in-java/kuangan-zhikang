/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhUserReginfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhFoodCookBook;
import com.yihe.system.domain.YhFoodCookedWeb;
import com.yihe.system.mapper.YhFoodCookedWebMapper;
import com.yihe.system.service.YhFoodCookBookService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 食物控制器
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhFoodCookBook")
@Api(value = "YhFoodCookBook控制器", description = "YhFoodCookBook管理")
public class YhFoodCookBookController {

	@Autowired
	 private YhFoodCookBookService yhFoodCookBookService;
	
	@Autowired
	private YhFoodCookedWebMapper  YhFoodCookedMapper;
	
	


	@ApiOperation(value = "查询膳食名称是否存在")
	@RequestMapping(value = "/selctFcNameRepetition", method = RequestMethod.GET)
	@ResponseBody
	public int selctFcNameRepetition(String fCName,int fCId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fCName", fCName);
		map.put("fCId", fCId);
		int show = yhFoodCookBookService.selctFcNameRepetition(map);
		return show;
	}
	
	
	 @ApiOperation(value = "删除食物")
	   @RequestMapping(value = "/fCIdfood", method = RequestMethod.POST)
		@ResponseBody
	   public Result  fCIdfood(int fCId) { 
		 int i=yhFoodCookBookService.deleteByPrimaryKey(fCId);
	       return Result.ok(i);
	   }
	 
	 @ApiOperation(value = "根据id查询食物")
	   @RequestMapping(value = "/selectfCIdfood", method = RequestMethod.POST)
		@ResponseBody
	   public Result  selectByPrimaryKey(int fCId) { 
		 YhFoodCookBook book=yhFoodCookBookService.selectByPrimaryKey(fCId);
		 
	       return Result.ok(book);
	   }
	 @ApiOperation(value = "根据id查询食物")
	 @RequestMapping(value = "/selectfCIdfood10", method = RequestMethod.POST)
	 @ResponseBody
	 public Result  selectByPrimaryKey10(int fCId) { 
		 Map<String, Object> map=new HashMap<>();
		 YhFoodCookBook book=yhFoodCookBookService.selectByPrimaryKey(fCId);
		 if(null!=book){
			 
			 if("10".equals(book.getfType())){
				 List<YhFoodCookBook> book10List=new ArrayList<YhFoodCookBook>();
				 YhFoodCookedWeb selectByPrimaryKey = YhFoodCookedMapper.selectByPrimaryKey(fCId);
				 String getfCookedelements = selectByPrimaryKey.getfCookedelements();
				 String[] split = getfCookedelements.split(";");
				 for (int i = 0; i < split.length; i++) {
					 if(StringUtil.isNotEmpty(split[i])){
						 String[] split2 = split[i].split("_");
						 YhFoodCookBook book10Info=yhFoodCookBookService.selectByPrimaryKey(Integer.valueOf(split2[0]));
						 book10Info.setRecipesNumber(Float.valueOf(split2[1]));
						 book10List.add(book10Info);
					 }
					 
				}
				 map.put("book10", selectByPrimaryKey);
				 map.put("book10List", book10List);
			 }
		 }
		 map.put("book", book);
		 return Result.ok(map);
	 }
	
	
	 @ApiOperation(value = "增加/修改食物")
	   @RequestMapping(value = "/insertfood", method = RequestMethod.POST)
		@ResponseBody
	   public Result  insertfood(YhFoodCookBook book) { 
		 book.setOneself(2);
		 int i;
		 if(StringUtil.isNotEmpty(book.getNotRecommendFood())){
			 String replace = book.getNotRecommendFood().replace(",", ";");
			 book.setNotRecommendFood(replace);
		 }
		 Subject subject = SecurityUtils.getSubject();
		 YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		 String usercode = principal.getUserCode();
		 book.setUserCode(usercode);
		 if(book.getfCId()==0) {
			 i=yhFoodCookBookService.insertSelective(book);
		 }else {
			 i=yhFoodCookBookService.updateByPrimaryKeySelective(book);
		 }
	       return Result.ok(i);
	   }
	 
		/**
		 * 查询所有食物
		 * @param page
		 * @param limit
		 * @param fCName
		 * @return
		 */
	     @ApiOperation(value = "查询所有食物内容")
	     @RequestMapping(value = "/selectCookBook", method = RequestMethod.POST)
	 	@ResponseBody
	 	public Map<String,Object> selectCookBook(Integer page,Integer limit,String fCName,String fType){
	 		Map<String,Object> map1 = new HashMap<String,Object>();
	 		Pages pa = new Pages();
	 		pa.setCurPage(page);
	 		pa.setMaxResult(limit);
	 		map1.put("page", pa.getFirstRows());
	 		map1.put("limit", pa.getMaxResult());
	 		map1.put("fCName", fCName);
	 		map1.put("fType", fType);
			 Subject subject = SecurityUtils.getSubject();
			 YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();

			 String orgCode = principal.getOrgCode();
			 List<YhFoodCookBook> list = yhFoodCookBookService.selectCookBook(map1);
			 for (int i = 0; i < list.size(); i++) {
				 YhFoodCookBook yhFoodCookBook = list.get(i);
				 String userCode = yhFoodCookBook.getUserCode();
				 if(StringUtil.isNotEmpty(userCode)){
					 List<String> strings = yhFoodCookBookService.findjigouInfo(orgCode);
					 for (int j = 0; j < strings.size(); j++) {
						 if(userCode.equals(strings.get(j))){
							 list.get(i).setQuanxian(1);
						 }
					 }
					 List<String> doctorInfo = yhFoodCookBookService.findDoctorInfo(orgCode);
					 for (int k = 0; k < doctorInfo.size(); k++) {
						 if(userCode.equals(doctorInfo.get(k))){
							 list.get(i).setQuanxian(1);
						 }
					 }
				 }
			 }
	 		Map<String,Object> map = new HashMap<String,Object>();
	 		map.put("code", 20000);
	 		map.put("msg", "");
	 		map.put("count",yhFoodCookBookService.selectCookBookConut(map1));
	 		map.put("data", list);
	 		return map;
	 	}
}
