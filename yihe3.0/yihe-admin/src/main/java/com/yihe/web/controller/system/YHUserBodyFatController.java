package com.yihe.web.controller.system;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBodyFat;
import com.yihe.system.service.YHUserBodyFatService;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YHUserBodyFat")
public class YHUserBodyFatController {
	
	@Autowired
	YHUserBodyFatService yHUserBodyFatService;
		
	
	@ApiOperation(value = "分页查询体脂")
    @ResponseBody
    @RequestMapping(value="/page",method=RequestMethod.GET)
    public Map<String,Object> queryUserBodyFat(String userCode,int page,int limit){
       //查询集合
          PageInfo<YHUserBodyFat> data = yHUserBodyFatService.queryUserByCriteriaWithPage(userCode, page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
	
	@ApiOperation(value = "分页查询设备体脂")
    @ResponseBody
    @RequestMapping(value="/page1",method=RequestMethod.GET)
    public Map<String,Object> queryUserBodyFat1(String userCode,int page,int limit){
       //查询集合
          PageInfo<YHUserBodyFat> data = yHUserBodyFatService.queryUserByCriteriaWithPage1(userCode, page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
	
	
    @ApiOperation(value = "查询体脂日期")
    @ResponseBody
    @RequestMapping(value="/selectBodyFat",method=RequestMethod.GET)
    public Map<String,Object> selectTemperature(String userCode,int page,int limit){
       //查询集合
     PageInfo<YHUserBodyFat> data = yHUserBodyFatService.selectBodyFat(userCode, page, limit);
     Map<String,Object> result = new HashMap<String, Object>();
         result.put("data",data.getList());
         result .put("count", data.getTotal());
         result.put("msg", "");
         result.put("code", 20000);
          return result;
    }
}
