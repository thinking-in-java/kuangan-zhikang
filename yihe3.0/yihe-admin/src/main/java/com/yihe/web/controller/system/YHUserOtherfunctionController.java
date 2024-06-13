package com.yihe.web.controller.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserOtherfunction;
import com.yihe.system.service.YHUserOtherfunctionService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YHUserOtherfunction")
public class YHUserOtherfunctionController {

		@Autowired
		YHUserOtherfunctionService yHUserOtherfunctionService;
		
		@ApiOperation(value = "分页查询其他指标")
	     @ResponseBody
	     @RequestMapping(value="/page",method=RequestMethod.GET)
	     public Map<String,Object> queryUserDate(String userCode,int page,int limit){
	        //查询集合
	           PageInfo<YHUserOtherfunction> data = yHUserOtherfunctionService.queryUserByCriteriaWithPage(userCode, page, limit);
	        Map<String,Object> result = new HashMap<String, Object>();
	        result.put("data",data.getList());
	        result .put("count", data.getTotal());
	        result.put("msg", "");
	        result.put("code", 20000);
	           return result;
	     }
}
