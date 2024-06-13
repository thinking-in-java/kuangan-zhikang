package com.yihe.web.controller.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBloodPressure;
import com.yihe.system.service.YHUserBloodPressureService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YHUserBloodPressure")
public class YHUserBloodPressureController {
	@Autowired
	YHUserBloodPressureService yHUserBloodPressureService;
	
	@ApiOperation(value = "分页查询血压")
    @ResponseBody
    @RequestMapping(value="/page",method=RequestMethod.GET)
    public Map<String,Object> queryBloodPressure(String userCode,int page,int limit){
       //查询集合
          PageInfo<YHUserBloodPressure> data = yHUserBloodPressureService.queryUserByCriteriaWithPage(userCode, page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
    @ApiOperation(value = "查询血压日期")
    @ResponseBody
    @RequestMapping(value="/selectBloodPressure",method=RequestMethod.GET)
    public Map<String,Object> selectBloodPressure(@RequestParam Map<String, Object> map,int page,int limit){
       //查询集合
     PageInfo<YHUserBloodPressure> data = yHUserBloodPressureService.selectPressure(map, page, limit);
     Map<String,Object> result = new HashMap<String, Object>();
         result.put("data",data.getList());
         result .put("count", data.getTotal());
         result.put("msg", "");
         result.put("code", 20000);
          return result;
    }
}
