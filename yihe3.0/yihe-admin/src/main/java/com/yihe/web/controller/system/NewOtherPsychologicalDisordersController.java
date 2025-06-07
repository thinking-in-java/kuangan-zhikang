package com.yihe.web.controller.system;

import com.yihe.system.domain.NewObsessiveCompulsiveDisorderInterventionRecordForm;
import com.yihe.system.domain.NewOtherPsychologicalDisorders;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.NewOtherPsychologicalDisordersService;
import com.yihe.system.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/NewOtherPsychologicalDisordersController")
public class NewOtherPsychologicalDisordersController {
    @Resource
    NewOtherPsychologicalDisordersService newOtherPsychologicalDisordersService;
    @RequestMapping(value = "/insetNew")
    @ResponseBody
    public Result insetNew(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders) {
        newOtherPsychologicalDisorders.setInsertTime(new Date());
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newOtherPsychologicalDisorders.setHealthManagementSpecialist(yh.getUserCode());
        int count=newOtherPsychologicalDisordersService.insetNew(newOtherPsychologicalDisorders);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/updateNew")
    @ResponseBody
    public Result updateNew(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders) {
        //根据userCode查询健管师code

        int count=newOtherPsychologicalDisordersService.updateNew(newOtherPsychologicalDisorders);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders) {
        return Result.ok(newOtherPsychologicalDisordersService.selectById(newOtherPsychologicalDisorders));
    }

    @RequestMapping(value = "/selectPage")
    @ResponseBody
    public Result selectRegInfo(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        newOtherPsychologicalDisorders.setBegin((page-1)*limit);
        newOtherPsychologicalDisorders.setLimit(limit);
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newOtherPsychologicalDisorders.setHealthManagementSpecialist(yh.getUserCode());
        List<NewOtherPsychologicalDisorders> newThreeSimultaneous1= newOtherPsychologicalDisordersService.selectByPage(newOtherPsychologicalDisorders);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newOtherPsychologicalDisordersService.selectByPageCount(newOtherPsychologicalDisorders));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newThreeSimultaneous1);
        return Result.ok(rsMap);
    }
}
