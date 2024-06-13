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

import com.yihe.system.domain.YhLifeHabitImprovementDict;
import com.yihe.system.domain.YhLifeHabitImprovementDictVojo;
import com.yihe.system.domain.YhSleepReportVojo;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhLifeHabitImprovementDictMapper;
import com.yihe.system.service.YhSleepReportService;
import com.yihe.system.utils.Pages;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 习惯评估
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhLifeHabitImprovementDictController")
public class YhLifeHabitImprovementDictController {

	
	@Autowired
	private YhLifeHabitImprovementDictMapper yhLifeHabitImprovementDictMapper;
	
	
	
	@ApiOperation(value = "用户查看习惯评估")
    @RequestMapping(value = "/ShowPersonageHabit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> ShowPersonageHabit(Integer page,Integer limit,String result,String dictTime,String usercode){
		Map<String,Object> map1 = new HashMap<String,Object>();
		Pages pa = new Pages();
		pa.setCurPage(page);
		pa.setMaxResult(limit);
		map1.put("page", pa.getFirstRows());
		map1.put("limit", pa.getMaxResult());
		map1.put("userCode", usercode);
		map1.put("result", result);
		if(dictTime!=null && !dictTime.equals("")) {
 			String[] strArr = dictTime.split(" - ");
 		map1.put("dictTime", strArr[0]);
 		map1.put("dictTime1", strArr[1]);
 		}
		Map<String,Object> map = new HashMap<String,Object>();
		List<YhLifeHabitImprovementDict> list=yhLifeHabitImprovementDictMapper.ShowPersonageHabit(map1);
		for (YhLifeHabitImprovementDict yhLifeHabitImprovementDict : list) {
			yhLifeHabitImprovementDict.setResult(yhLifeHabitImprovementDict.getResult()+"("+yhLifeHabitImprovementDict.getAssessScores()+")"+yhLifeHabitImprovementDict.getAnalyzename());
		}
		map.put("code", 20000);
		map.put("msg", "");
		map.put("count",yhLifeHabitImprovementDictMapper.ShowPersonageHabitCount(map1));
		map.put("data", list);
		return map;
	}
	
	
	@ApiOperation(value = "习惯评估记录")
    @RequestMapping(value = "/ShowHabitrecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> ShowHabitrecord(Integer page,Integer limit,String name,String result,String userName,String dictTime){
		Map<String,Object> map1 = new HashMap<String,Object>();
		Pages pa = new Pages();
		pa.setCurPage(page);
		pa.setMaxResult(limit);
		map1.put("page", pa.getFirstRows());
		map1.put("limit", pa.getMaxResult());
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		map1.put("userCode", usercode);
		map1.put("name", name);
		map1.put("result", result);
		map1.put("userName", userName);
		if(dictTime!=null && !dictTime.equals("")) {
 			String[] strArr = dictTime.split(" - ");
 		map1.put("dictTime", strArr[0]);
 		map1.put("dictTime1", strArr[1]);
 		}
		Map<String,Object> map = new HashMap<String,Object>();
		List<YhLifeHabitImprovementDictVojo> list = yhLifeHabitImprovementDictMapper.ShowHabitrecord(map1);
		for (YhLifeHabitImprovementDictVojo yhLifeHabitImprovementDictVojo : list) {
			yhLifeHabitImprovementDictVojo.setResult(yhLifeHabitImprovementDictVojo.getResult()+"("+yhLifeHabitImprovementDictVojo.getAssessScores()+")"+yhLifeHabitImprovementDictVojo.getAnalyzename());
		}
		map.put("code", 20000);
		map.put("msg", "");
		map.put("count",yhLifeHabitImprovementDictMapper.ShowHabitrecordCount(map1));
		map.put("data", list);
		return map;
	}
	
	
	
	  @ApiOperation(value = "习惯评估管理")
	     @RequestMapping(value = "/sleepbyImprovement", method = RequestMethod.POST)
	 	@ResponseBody
	 	public Map<String,Object> sleepbyImprovement(Integer page,Integer limit,String name,String result,String orgCode){
	 		Map<String,Object> map1 = new HashMap<String,Object>();
	 		Pages pa = new Pages();
	 		pa.setCurPage(page);
	 		pa.setMaxResult(limit);
	 		map1.put("page", pa.getFirstRows());
	 		map1.put("limit", pa.getMaxResult());
	 		Subject subject = SecurityUtils.getSubject();
			YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
			String usercode = principal.getUserCode();
	 		map1.put("userCode", usercode);
	 		map1.put("name", name);
	 		map1.put("result", result);
	 		map1.put("orgCode", orgCode);
	 		Map<String,Object> map = new HashMap<String,Object>();
	 		List<YhLifeHabitImprovementDictVojo> list = yhLifeHabitImprovementDictMapper.sleepbyImprovement(map1);
	 		for (YhLifeHabitImprovementDictVojo yhLifeHabitImprovementDictVojo : list) {
	 			yhLifeHabitImprovementDictVojo.setResult(yhLifeHabitImprovementDictVojo.getResult()+"("+yhLifeHabitImprovementDictVojo.getAssessScores()+")"+yhLifeHabitImprovementDictVojo.getAnalyzename());
	 			yhLifeHabitImprovementDictVojo.setFrequency(yhLifeHabitImprovementDictMapper.ShowByHabitCode(yhLifeHabitImprovementDictVojo.getUserCode()));
	    		 
	 		}
	 		map.put("code", 20000);
	 		map.put("msg", "");
	 		map.put("count",yhLifeHabitImprovementDictMapper.sleepbyImprovementCount(map1).size());
	 		map.put("data", list);
	 		return map;
	 	}
	  
}
