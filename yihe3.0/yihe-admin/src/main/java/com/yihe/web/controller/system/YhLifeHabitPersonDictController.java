/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhLifeHabitDict;
import com.yihe.system.domain.YhLifeHabitImprovementDict;
import com.yihe.system.domain.YhLifeHabitPersonDict;
import com.yihe.system.domain.YhSportRecommendRecord;
import com.yihe.system.domain.YhSportSetmeal;
import com.yihe.system.mapper.YhLifeHabitDictMapper;
import com.yihe.system.mapper.YhLifeHabitImprovementDictMapper;
import com.yihe.system.mapper.YhLifeHabitPersonDictMapper;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;

/**
 * 我的生活习惯项目
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhLifeHabitPersonDictController")
public class YhLifeHabitPersonDictController {

	@Autowired
	private YhLifeHabitPersonDictMapper yhLifeHabitPersonDictMapper;
	
	@Autowired
	private YhLifeHabitDictMapper yhLifeHabitDictMapper;
	
	
	
	@ApiOperation(value = "查找所有习惯")
    @RequestMapping(value = "/showHabitName", method = RequestMethod.POST)
	@ResponseBody
    public Result  showHabitName(String habitName,String userCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("habitName", habitName);
    	List<YhLifeHabitDict> list=yhLifeHabitDictMapper.showHabitName(map);
    	List<YhLifeHabitPersonDict> Record=yhLifeHabitPersonDictMapper.selectByPersoncode(userCode);
    	for (YhLifeHabitDict yhLifeHabitDict : list) {
    	for (YhLifeHabitPersonDict yhLifeHabitPersonDict : Record) {
    			if(yhLifeHabitPersonDict.getHabitId()==yhLifeHabitDict.getId()) {
    				yhLifeHabitDict.setOneself(1);
    				 break;
    			}else {
    				yhLifeHabitDict.setOneself(0);
    			}
    		}
		}
    	  return Result.ok(list);
	}
    	
	
	
	@ApiOperation(value = "增加内容")
    @RequestMapping(value = "/insertSelective", method = RequestMethod.POST)
	@ResponseBody
    public Result  deleteByPrimaryKey(String pitch,String userCode) {
		 String[] strArr = pitch.split(",");
		
		 
		 List<YhLifeHabitPersonDict> list=yhLifeHabitPersonDictMapper.selectByPersoncode(userCode);
		 for (YhLifeHabitPersonDict yhLifeHabitPersonDict : list) {
			 if(Arrays.asList(strArr).contains(String.valueOf(yhLifeHabitPersonDict.getHabitId()))==false) {
				 System.out.println(yhLifeHabitPersonDict.getHabitId());
				 yhLifeHabitPersonDict.setMissingDay(2);
				 yhLifeHabitPersonDict.setAccomplishType("2");
				 yhLifeHabitPersonDictMapper.updateByPrimaryKeySelective(yhLifeHabitPersonDict);
			 }
		}
		 int  k=0;
		 Map<String, Object> map=new HashMap<String, Object>();
		 map.put("userCode", userCode);
		 for (int i = 0; i < strArr.length; i++) {
			 map.put("habitId", strArr[i]);
				int st=yhLifeHabitPersonDictMapper.seelctByHabitCodeCount(map);
				if(st==0) {
					YhLifeHabitPersonDict record=new YhLifeHabitPersonDict();
						 record.setUserCode(userCode);
						 record.setHabitId(Integer.valueOf(strArr[i]));
						 record.setMissingDay(2);
						 record.setAccomplishType("3");
						k=yhLifeHabitPersonDictMapper.insertSelective(record);
					}
				}
		
        return Result.ok(k);
    }
	
	
	
    	
	@ApiOperation(value = "用户我的生活习惯项目")
    @RequestMapping(value = "/showPersonHabit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> showPersonHabit(Integer page,Integer limit,String usercode){
		Map<String,Object> map1 = new HashMap<String,Object>();
		Pages pa = new Pages();
		pa.setCurPage(page);
		pa.setMaxResult(limit);
		map1.put("page", pa.getFirstRows());
		map1.put("limit", pa.getMaxResult());
		map1.put("userCode", usercode);
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<YhLifeHabitPersonDict> list=yhLifeHabitPersonDictMapper.showPersonHabit(map1);
		map.put("code", 20000);
		map.put("msg", "");
		map.put("count",yhLifeHabitPersonDictMapper.showPersonHabitCount(map1));
		map.put("data", list);
		return map;
	}
}
