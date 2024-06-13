package com.yihe.web.controller.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBmi;
import com.yihe.system.service.YHUserBmiService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YHUserBmi")
public class YHUserBmiController {
	@Autowired
	YHUserBmiService yHUserBmiService;
	
	@ApiOperation(value = "分页查询体重")
    @ResponseBody
    @RequestMapping(value="/page",method=RequestMethod.GET)
    public Map<String,Object> queryYHUserBmi(String userCode,int page,int limit){
       //查询集合
          PageInfo<YHUserBmi> data = yHUserBmiService.queryUserByCriteriaWithPage(userCode, page, limit);
	       Map<String,Object> result = new HashMap<String, Object>();
	       result.put("data",data.getList());
	       result .put("count", data.getTotal());
	       result.put("msg", "");
	       result.put("code", 20000);
          return result;
    }
    @ApiOperation(value = "查询体重日期")
    @ResponseBody
    @RequestMapping(value="/selectYHUserBmi",method=RequestMethod.GET)
    public Map<String,Object> selectYHUserBmi(String userCode,int page,int limit){
       //查询集合
     PageInfo<YHUserBmi> data = yHUserBmiService.selectBmiPage(userCode, page, limit);
     Map<String,Object> result = new HashMap<String, Object>();
         result.put("data",data.getList());
         result .put("count", data.getTotal());
         result.put("msg", "");
         result.put("code", 20000);
          return result;
    }
}
