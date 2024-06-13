package com.yihe.web.controller.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserOxygen;
import com.yihe.system.service.YHUserOxygenService;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YHUserOxygen")
public class YHUserOxygenController {
	@Autowired
	YHUserOxygenService yHUserOxygenService;
	
	@ApiOperation(value = "分页查询血氧")
    @ResponseBody
    @RequestMapping(value="/page",method=RequestMethod.GET)
    public Map<String,Object> queryUserOxygen(String userCode,int page,int limit){
       //查询集合
      PageInfo<YHUserOxygen> data = yHUserOxygenService.queryUserByCriteriaWithPage(userCode, page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
    @ApiOperation(value = "查询血氧日期")
    @ResponseBody
    @RequestMapping(value="/selectOxygen",method=RequestMethod.GET)
    public Map<String,Object> selectOxygen(String userCode,int page,int limit){
       //查询集合
     PageInfo<YHUserOxygen> data = yHUserOxygenService.selectYHUserOxygen2(userCode, page, limit);
     Map<String,Object> result = new HashMap<String, Object>();
         result.put("data",data.getList());
         result .put("count", data.getTotal());
         result.put("msg", "");
         result.put("code", 20000);
          return result;
    }
    
    
    @ApiOperation(value = "分页查询心率")
    @ResponseBody
    @RequestMapping(value="/pageHeart",method=RequestMethod.GET)
    public Map<String,Object> queryUserHeart(String userCode,int page,int limit){
       //查询集合
      PageInfo<YHUserOxygen> data = yHUserOxygenService.queryUserHeartByCriteriaWithPage(userCode, page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
    @ApiOperation(value = "查询心率日期")
    @ResponseBody
    @RequestMapping(value="/selectHeart",method=RequestMethod.GET)
    public Map<String,Object> selectHeart(String userCode,int page,int limit){
       //查询集合
     PageInfo<YHUserOxygen> data = yHUserOxygenService.selectYHUserHeart(userCode, page, limit);
     Map<String,Object> result = new HashMap<String, Object>();
         result.put("data",data.getList());
         result .put("count", data.getTotal());
         result.put("msg", "");
         result.put("code", 20000);
          return result;
    }
    
}
