package com.yihe.web.controller.system;

import com.yihe.system.domain.NewIndividualOccupationalHealthMonitoringRecordsWorkers;
import com.yihe.system.domain.NewThreeSimultaneous;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.NewIndividualOccupationalHealthMonitoringRecordsWorkersService;
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
@RequestMapping("/NewIndividualOccupationalHealthMonitoringRecordsWorkersController")
public class NewIndividualOccupationalHealthMonitoringRecordsWorkersController {
    @Resource
    NewIndividualOccupationalHealthMonitoringRecordsWorkersService newIndividualOccupationalHealthMonitoringRecordsWorkersService;

    @RequestMapping(value = "/insetNew")
    @ResponseBody
    public Result insetNew(NewIndividualOccupationalHealthMonitoringRecordsWorkers newIndividualOccupationalHealthMonitoringRecordsWorkers) {
        //根据userCode查询健管师code
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newIndividualOccupationalHealthMonitoringRecordsWorkers.setStaffingPersonnel(yh.getUserCode());
        newIndividualOccupationalHealthMonitoringRecordsWorkers.setInsertTime(new Date());
        int count=newIndividualOccupationalHealthMonitoringRecordsWorkersService.insetNew(newIndividualOccupationalHealthMonitoringRecordsWorkers);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/updateNew")
    @ResponseBody
    public Result updateNew(NewIndividualOccupationalHealthMonitoringRecordsWorkers newIndividualOccupationalHealthMonitoringRecordsWorkers) {
        //根据userCode查询健管师code

        int count=newIndividualOccupationalHealthMonitoringRecordsWorkersService.updateNew(newIndividualOccupationalHealthMonitoringRecordsWorkers);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewIndividualOccupationalHealthMonitoringRecordsWorkers newIndividualOccupationalHealthMonitoringRecordsWorkers) {
        return Result.ok(newIndividualOccupationalHealthMonitoringRecordsWorkersService.selectById(newIndividualOccupationalHealthMonitoringRecordsWorkers));
    }

    @RequestMapping(value = "/selectPage")
    @ResponseBody
    public Result selectRegInfo(NewIndividualOccupationalHealthMonitoringRecordsWorkers newIndividualOccupationalHealthMonitoringRecordsWorkers, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        newIndividualOccupationalHealthMonitoringRecordsWorkers.setBegin((page-1)*limit);
        newIndividualOccupationalHealthMonitoringRecordsWorkers.setLimit(limit);
        List<NewIndividualOccupationalHealthMonitoringRecordsWorkers> newThreeSimultaneous1= newIndividualOccupationalHealthMonitoringRecordsWorkersService.selectByPage(newIndividualOccupationalHealthMonitoringRecordsWorkers);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newIndividualOccupationalHealthMonitoringRecordsWorkersService.selectByPageCount(newIndividualOccupationalHealthMonitoringRecordsWorkers));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newThreeSimultaneous1);
        return Result.ok(rsMap);
    }


}
