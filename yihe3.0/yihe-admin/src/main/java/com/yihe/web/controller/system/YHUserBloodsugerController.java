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
import com.yihe.system.domain.YHUserBloodsuger;
import com.yihe.system.service.YHUserBloodsugerService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YHUserBloodsuger")
public class YHUserBloodsugerController {
	@Autowired
	YHUserBloodsugerService yHUserBloodsugerService;
	
	
	@ApiOperation(value = "分页查询血糖")
    @ResponseBody
    @RequestMapping(value="/page",method=RequestMethod.GET)
    public Map<String,Object> queryBloodsuger(String userCode,int page,int limit){
       //查询集合
          PageInfo<YHUserBloodsuger> data = yHUserBloodsugerService.queryUserByCriteriaWithPage(userCode, page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
    @ApiOperation(value = "查询血糖日期")
    @ResponseBody
    @RequestMapping(value="/selectBloodsuger",method=RequestMethod.GET)
    public Map<String,Object> selectBloodsuger(@RequestParam Map<String, Object> map,int page,int limit){
       //查询集合
     PageInfo<YHUserBloodsuger> data = yHUserBloodsugerService.selectBloodsuger(map, page, limit);
     Map<String,Object> result = new HashMap<String, Object>();
         result.put("data",data.getList());
         result .put("count", data.getTotal());
         result.put("msg", "");
         result.put("code", 20000);
          return result;
    }
    
}
