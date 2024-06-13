/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhFoodCookBook;
import com.yihe.system.domain.YhSportCategoryWeb;
import com.yihe.system.domain.YhSportGoals;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.domain.YhSportPart;
import com.yihe.system.domain.YhSportSetmeal;
import com.yihe.system.domain.YhSportStrength;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhSportCategoryWebMapper;
import com.yihe.system.mapper.YhSportGoalsMapper;
import com.yihe.system.mapper.YhSportPartMapper;
import com.yihe.system.mapper.YhSportStrengthMapper;
import com.yihe.system.service.YhFoodCookBookService;
import com.yihe.system.service.YhSportMotionService;
import com.yihe.system.service.YhSportSetmealService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 运动库
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhSportMotion")
@Api(value = "YhSportMotion控制器", description = "YhSportMotion管理")
public class YhSportMotionController {

	@Autowired
	 private YhSportMotionService yhSportMotionService;
	
	@Autowired
	 private YhSportSetmealService yhSportSetmealService;
	
	@Autowired
	 private YhSportCategoryWebMapper yhSportCategoryWebMapper;
	
	@Autowired
	 private YhSportPartMapper yhSportPartMapper;
	
	
	@Autowired
	 private YhSportGoalsMapper yhSportGoalsMapper;
	
	
	@Autowired
	 private YhSportStrengthMapper yhSportStrengthMapper;
	
	@Autowired
	 private YhFoodCookBookService yhFoodCookBookService;
	
	@ApiOperation(value = "查询动作强度")
	@RequestMapping(value = "/showSportStrength", method = RequestMethod.GET)
	@ResponseBody
	public List<YhSportStrength> showSportStrength() {
		List<YhSportStrength> show = yhSportStrengthMapper.showSportStrength();
		return show;
	}
	
	@ApiOperation(value = "查询锻炼目标")
	@RequestMapping(value = "/showSportGoals", method = RequestMethod.GET)
	@ResponseBody
	public List<YhSportGoals> showSportGoals() {
		List<YhSportGoals> show = yhSportGoalsMapper.showSportGoals();
		return show;
	}
	
	@ApiOperation(value = "查询锻炼部位")
	@RequestMapping(value = "/showSportPart", method = RequestMethod.GET)
	@ResponseBody
	public List<YhSportPart> showSportPart() {
		List<YhSportPart> show = yhSportPartMapper.showSportPart();
		return show;
	}
	
	@ApiOperation(value = "查询运动分类")
	@RequestMapping(value = "/showSportCategory", method = RequestMethod.GET)
	@ResponseBody
	public List<YhSportCategoryWeb> showSportCategory() {
		List<YhSportCategoryWeb> show = yhSportCategoryWebMapper.showSportCategory();
		return show;
	}
	
	
	
	@ApiOperation(value = "查询运动名称是否存在")
	@RequestMapping(value = "/selctSportNmaeRepetition", method = RequestMethod.GET)
	@ResponseBody
	public int selctSportNmaeRepetition(String sportName,int id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("sportName", sportName);
		map.put("id", id);
		int show = yhSportMotionService.selctSportNmaeRepetition(map);
		return show;
	}
	
	@ApiOperation(value = "删除运动")
	   @RequestMapping(value = "/deleSport", method = RequestMethod.POST)
		@ResponseBody
	   public Result  deleSport(int id) { 
		YhSportMotion tion=new YhSportMotion();
		tion.setId(id);
		tion.setSportShow(1);
		int i=yhSportMotionService.updateByPrimaryKeySelective(tion);
		 
		//  i=yhSportMotionService.deleteByPrimaryKey(id);
	       return Result.ok(i);
	   }
	 
	 @ApiOperation(value = "根据id查询运动")
	   @RequestMapping(value = "/selectidSport", method = RequestMethod.POST)
		@ResponseBody
	   public Result  selectidSport(int id) { 
		 YhSportMotion book=yhSportMotionService.selectByPrimaryKey(id);
	       return Result.ok(book);
	   }
	 
	 @ApiOperation(value = "增加/修改运动")
	   @RequestMapping(value = "/insertSport", method = RequestMethod.POST)
		@ResponseBody
	   public Result  insertSport(YhSportMotion tion) { 
		 tion.setStandby("2");
		 tion.setSportShow(0);
		 if(tion.getSportClassity()!=null && !tion.getSportClassity().equals("")) {
			 String[] arr = tion.getSportClassity().split(",");
			 String classity="";
			 for (int i = 0; i < arr.length; i++) {
				 classity+=arr[i]+";";
			}
			 tion.setSportClassity(classity);
		 }
		 
		 if(tion.getSportPlace()!=null && !tion.getSportPlace().equals("")) {
			 String[] arr = tion.getSportPlace().split(",");
			 String classity="";
			 for (int i = 0; i < arr.length; i++) {
				 classity+=arr[i]+";";
			}
			 tion.setSportPlace(classity);
		 }
		 
		 
		 if(tion.getSportGoals()!=null && !tion.getSportGoals().equals("")) {
			 String[] arr = tion.getSportGoals().split(",");
			 String classity="";
			 for (int i = 0; i < arr.length; i++) {
				 classity+=arr[i]+";";
			}
			 tion.setSportGoals(classity);
		 }
		 Subject subject = SecurityUtils.getSubject();
		 YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		 String usercode = principal.getUserCode();
		 tion.setUserCode(usercode);
		 int i;
		 if(tion.getId()==0) {
			 i=yhSportMotionService.insertSelective(tion);
		 }else {
			 i=yhSportMotionService.updateByPrimaryKeySelective(tion);
		 }
	       return Result.ok(i);
	   }
	 
	
     @ApiOperation(value = "查询所有运动内容")
     @RequestMapping(value = "/selectSport", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectSport(Integer page,Integer limit,String sportName){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("sportName", sportName);
 		List<YhSportMotion> list = yhSportMotionService.selectSport(map1);
 		 Subject subject = SecurityUtils.getSubject();
		 YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();

		 String orgCode = principal.getOrgCode();
 		for (YhSportMotion yhSportMotion : list) {
 			
 			 if(StringUtil.isNotEmpty(yhSportMotion.getUserCode())){
				 List<String> strings = yhFoodCookBookService.findjigouInfo(orgCode);
				 for (int j = 0; j < strings.size(); j++) {
					 if(yhSportMotion.getUserCode().equals(strings.get(j))){
						 yhSportMotion.setQuanxian(1);
					 }
				 }
				 List<String> doctorInfo = yhFoodCookBookService.findDoctorInfo(orgCode);
				 for (int k = 0; k < doctorInfo.size(); k++) {
					 if(yhSportMotion.getUserCode().equals(doctorInfo.get(k))){
						 yhSportMotion.setQuanxian(1);
					 }
				 }
			 }
 			 
			if(yhSportMotion.getSportType()==1) {
				yhSportMotion.setKcal(yhSportMotion.getKcal()+"/1分钟");
			}else if(yhSportMotion.getSportType()==2) {
				yhSportMotion.setKcal(yhSportMotion.getKcal()+"/1个");
			}
		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhSportMotionService.selectSportConut(map1));
 		map.put("data", list);
 		return map;
 	}
     
     
     @ApiOperation(value = "查询所有运动课堂内容")
     @RequestMapping(value = "/selectSetmeal", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectSetmeal(Integer page,Integer limit,String setmealMame){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("setmealMame", setmealMame);
 		List<YhSportSetmeal> list = yhSportSetmealService.selectSetmeal(map1);
 		for (YhSportSetmeal yhSportSetmeal : list) {
 			String[] arr = yhSportSetmeal.getSubjectId().split(",");
 			yhSportSetmeal.setJduang(arr.length);
		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhSportSetmealService.selectSetmealConut(map1));
 		map.put("data", list);
 		return map;
 	}
     
}
