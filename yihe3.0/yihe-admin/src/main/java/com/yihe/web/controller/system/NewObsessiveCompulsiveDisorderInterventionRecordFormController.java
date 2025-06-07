package com.yihe.web.controller.system;

import com.yihe.system.domain.NewAnxietyDisorderInterventionRecordForm;
import com.yihe.system.domain.NewObsessiveCompulsiveDisorderInterventionRecordForm;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.NewObsessiveCompulsiveDisorderInterventionRecordFormService;
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
@RequestMapping("/NewObsessiveCompulsiveDisorderInterventionRecordFormController")
public class NewObsessiveCompulsiveDisorderInterventionRecordFormController {
    @Resource
    NewObsessiveCompulsiveDisorderInterventionRecordFormService newObsessiveCompulsiveDisorderInterventionRecordFormService;
    @RequestMapping(value = "/insetNew")
    @ResponseBody
    public Result insetNew(NewObsessiveCompulsiveDisorderInterventionRecordForm newObsessiveCompulsiveDisorderInterventionRecordForm) {
        newObsessiveCompulsiveDisorderInterventionRecordForm.setInsertTime(new Date());
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newObsessiveCompulsiveDisorderInterventionRecordForm.setHealthManagementSpecialist(yh.getUserCode());
        int count=newObsessiveCompulsiveDisorderInterventionRecordFormService.insetNew(newObsessiveCompulsiveDisorderInterventionRecordForm);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/updateNew")
    @ResponseBody
    public Result updateNew(NewObsessiveCompulsiveDisorderInterventionRecordForm newObsessiveCompulsiveDisorderInterventionRecordForm) {
        //根据userCode查询健管师code

        int count=newObsessiveCompulsiveDisorderInterventionRecordFormService.updateNew(newObsessiveCompulsiveDisorderInterventionRecordForm);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewObsessiveCompulsiveDisorderInterventionRecordForm newObsessiveCompulsiveDisorderInterventionRecordForm) {
        return Result.ok(newObsessiveCompulsiveDisorderInterventionRecordFormService.selectById(newObsessiveCompulsiveDisorderInterventionRecordForm));
    }

    @RequestMapping(value = "/selectPage")
    @ResponseBody
    public Result selectRegInfo(NewObsessiveCompulsiveDisorderInterventionRecordForm newObsessiveCompulsiveDisorderInterventionRecordForm, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        newObsessiveCompulsiveDisorderInterventionRecordForm.setBegin((page-1)*limit);
        newObsessiveCompulsiveDisorderInterventionRecordForm.setLimit(limit);
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newObsessiveCompulsiveDisorderInterventionRecordForm.setHealthManagementSpecialist(yh.getUserCode());
        List<NewObsessiveCompulsiveDisorderInterventionRecordForm> newThreeSimultaneous1= newObsessiveCompulsiveDisorderInterventionRecordFormService.selectByPage(newObsessiveCompulsiveDisorderInterventionRecordForm);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newObsessiveCompulsiveDisorderInterventionRecordFormService.selectByPageCount(newObsessiveCompulsiveDisorderInterventionRecordForm));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newThreeSimultaneous1);
        return Result.ok(rsMap);
    }

}
