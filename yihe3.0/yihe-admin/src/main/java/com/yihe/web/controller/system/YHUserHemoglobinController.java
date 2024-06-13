package com.yihe.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserHemoglobin;
import com.yihe.system.service.yHUserHemoglobinService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/YHUserHemoglobin")
public class YHUserHemoglobinController {
    @Autowired
   yHUserHemoglobinService yHUserHemoglobinService;

    @ApiOperation(value = "分页查询血红蛋白")
    @ResponseBody
    @RequestMapping(value="/page",method= RequestMethod.GET)
    public Map<String,Object> queryUserDate(String userCode, int page, int limit){
        //查询集合
        PageInfo<YhUserHemoglobin> data = yHUserHemoglobinService.queryUserByCriteriaWithPage(userCode, page, limit);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("data",data.getList());
        result .put("count", data.getTotal());
        result.put("msg", "");
        result.put("code", 20000);
        return result;
    }

    @ApiOperation(value = "血红蛋白 图表 数据查询")
    @ResponseBody
    @RequestMapping(value="/selectHemoglobin",method= RequestMethod.GET)
    public Map<String,Object> selectHemoglobin(String userCode,int page,int limit){
        //查询集合
        PageInfo<YhUserHemoglobin> data = yHUserHemoglobinService.selectHemoglobin(userCode, page, limit);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("data",data.getList());
        result .put("count", data.getTotal());
        result.put("msg", "");
        result.put("code", 20000);
        return result;
    }


}
