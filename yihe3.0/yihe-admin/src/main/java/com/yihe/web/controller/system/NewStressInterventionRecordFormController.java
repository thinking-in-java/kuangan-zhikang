package com.yihe.web.controller.system;

import com.yihe.system.domain.NewAnxietyDisorderInterventionRecordForm;
import com.yihe.system.domain.NewStressInterventionRecordForm;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.NewStressInterventionRecordFormService;
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
@RequestMapping("/NewStressInterventionRecordFormController")
public class NewStressInterventionRecordFormController {
    @Resource
    NewStressInterventionRecordFormService newStressInterventionRecordFormService;
    @RequestMapping(value = "/insetNew")
    @ResponseBody
    public Result insetNew(NewStressInterventionRecordForm newStressInterventionRecordForm) {
        newStressInterventionRecordForm.setInsertTime(new Date());
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newStressInterventionRecordForm.setHealthManagementSpecialist(yh.getUserCode());
        int count=newStressInterventionRecordFormService.insetNew(newStressInterventionRecordForm);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/updateNew")
    @ResponseBody
    public Result updateNew(NewStressInterventionRecordForm newStressInterventionRecordForm) {
        //根据userCode查询健管师code

        int count=newStressInterventionRecordFormService.updateNew(newStressInterventionRecordForm);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewStressInterventionRecordForm newStressInterventionRecordForm) {
        return Result.ok(newStressInterventionRecordFormService.selectById(newStressInterventionRecordForm));
    }

    @RequestMapping(value = "/selectPage")
    @ResponseBody
    public Result selectRegInfo(NewStressInterventionRecordForm newStressInterventionRecordForm, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        newStressInterventionRecordForm.setBegin((page-1)*limit);
        newStressInterventionRecordForm.setLimit(limit);
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newStressInterventionRecordForm.setHealthManagementSpecialist(yh.getUserCode());
        List<NewStressInterventionRecordForm> newThreeSimultaneous1= newStressInterventionRecordFormService.selectByPage(newStressInterventionRecordForm);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newStressInterventionRecordFormService.selectByPageCount(newStressInterventionRecordForm));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newThreeSimultaneous1);
        return Result.ok(rsMap);
    }
}
