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

import com.yihe.system.domain.YhSportCourseDetailWeb;
import com.yihe.system.domain.YhSportDiary;
import com.yihe.system.domain.YhSportViconWeb;
import com.yihe.system.mapper.YhSportCourseDetailWebMapper;
import com.yihe.system.mapper.YhSportViconWebMapper;
import com.yihe.system.service.IYhSportDiaryService;
import com.yihe.system.utils.Pages;

import io.swagger.annotations.ApiOperation;

/**
 * 精彩运动
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhSportViconWebController")
public class YhSportViconWebController {

	 @Autowired
	 private YhSportCourseDetailWebMapper yhSportViconWebMapper;

	

	/**
	 * 查询精彩运动课程
	 * @param page
	 * @param limit
	 * @param viconName
	 * @return
	 */
     @ApiOperation(value = "查询精彩运动课程")
     @RequestMapping(value = "/selectSportVicon", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectSportVicon(Integer page,Integer limit,String viconName){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("cdetailName", viconName);
 		
 		Map<String,Object> map = new HashMap<String,Object>();
 		List<YhSportCourseDetailWeb> list = yhSportViconWebMapper.selectSportVicon(map1);
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhSportViconWebMapper.selectSportViconCount(map1));
 		map.put("data", list);
 		return map;
 	}
     
}
