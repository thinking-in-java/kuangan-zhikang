package com.yihe.web.controller.system;

import com.yihe.system.domain.NewDermatosisPatientServiceRecordForm;
import com.yihe.system.domain.NewEmployeePsychologicalCounselingRecordForm;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.NewEmployeePsychologicalCounselingRecordFormService;
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
@RequestMapping("/NewEmployeePsychologicalCounselingRecordFormController")
public class NewEmployeePsychologicalCounselingRecordFormController {
    @Resource
    NewEmployeePsychologicalCounselingRecordFormService newEmployeePsychologicalCounselingRecordFormService;
    @RequestMapping(value = "/insetNew")
    @ResponseBody
    public Result insetNew(NewEmployeePsychologicalCounselingRecordForm newEmployeePsychologicalCounselingRecordForm) {
        newEmployeePsychologicalCounselingRecordForm.setInsertTime(new Date());
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newEmployeePsychologicalCounselingRecordForm.setHealthManagementSpecialist(yh.getUserCode());
        newEmployeePsychologicalCounselingRecordForm.setCustomerContact(yh.getUserCode());
        int count=newEmployeePsychologicalCounselingRecordFormService.insetNew(newEmployeePsychologicalCounselingRecordForm);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/updateNew")
    @ResponseBody
    public Result updateNew(NewEmployeePsychologicalCounselingRecordForm newEmployeePsychologicalCounselingRecordForm) {
        //根据userCode查询健管师code

        int count=newEmployeePsychologicalCounselingRecordFormService.updateNew(newEmployeePsychologicalCounselingRecordForm);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewEmployeePsychologicalCounselingRecordForm newEmployeePsychologicalCounselingRecordForm) {
        return Result.ok(newEmployeePsychologicalCounselingRecordFormService.selectById(newEmployeePsychologicalCounselingRecordForm));
    }

    @RequestMapping(value = "/selectPage")
    @ResponseBody
    public Result selectRegInfo(NewEmployeePsychologicalCounselingRecordForm newEmployeePsychologicalCounselingRecordForm, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        newEmployeePsychologicalCounselingRecordForm.setBegin((page-1)*limit);
        newEmployeePsychologicalCounselingRecordForm.setLimit(limit);
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newEmployeePsychologicalCounselingRecordForm.setHealthManagementSpecialist(yh.getUserCode());
        List<NewEmployeePsychologicalCounselingRecordForm> newThreeSimultaneous1= newEmployeePsychologicalCounselingRecordFormService.selectByPage(newEmployeePsychologicalCounselingRecordForm);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newEmployeePsychologicalCounselingRecordFormService.selectByPageCount(newEmployeePsychologicalCounselingRecordForm));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newThreeSimultaneous1);
        return Result.ok(rsMap);
    }
}
