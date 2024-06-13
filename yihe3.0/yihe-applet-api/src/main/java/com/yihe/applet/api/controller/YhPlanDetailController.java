package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhplanDetail;
import com.yihe.applet.api.service.YhPlanDetailService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/YhPlanDetail")
@Api(value = "YhPressureAssessment控制器", description = "YhPressureAssessment管理")
public class YhPlanDetailController {
    @Autowired
    YhPlanDetailService yhPlanDetailService;
    @RequestMapping(value = "/findfood")
    @ResponseBody
    public Result findInfoAll(String usercode) {
        int count=yhPlanDetailService.selectfoodcount(usercode);
        return Result.ok(count);
    }
    //查询今日是否完成过运动
    @RequestMapping(value = "/findsport")
    @ResponseBody
    public Result findsport(String usercode) {
        int count=yhPlanDetailService.selectsportcount(usercode);
        return Result.ok(count);
    }

    //插入健康计划食谱and修改个人信息
    @RequestMapping(value = "/findfooddis")
    @ResponseBody
    public Result insertfoorse(YhplanDetail yhplanDetail) {

        return Result.ok(yhPlanDetailService.changefoodse(yhplanDetail));
    }
}
