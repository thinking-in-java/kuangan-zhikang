package com.yihe.web.controller.system;

import com.yihe.system.domain.NewAnxietyDisorderInterventionRecordForm;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.newDepressionInterventionRecordForm;
import com.yihe.system.service.newDepressionInterventionRecordFormService;
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
@RequestMapping("/newDepressionInterventionRecordFormController")
public class newDepressionInterventionRecordFormController {
    @Resource
    newDepressionInterventionRecordFormService newDepressionInterventionRecordFormService;

    @RequestMapping(value = "/insetNew")
    @ResponseBody
    public Result insetNew(newDepressionInterventionRecordForm newDepressionInterventionRecordForm) {
        newDepressionInterventionRecordForm.setInsertTime(new Date());
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newDepressionInterventionRecordForm.setHealthManagementSpecialist(yh.getUserCode());
        int count=newDepressionInterventionRecordFormService.insetNew(newDepressionInterventionRecordForm);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/updateNew")
    @ResponseBody
    public Result updateNew(newDepressionInterventionRecordForm newDepressionInterventionRecordForm) {
        //根据userCode查询健管师code

        int count=newDepressionInterventionRecordFormService.updateNew(newDepressionInterventionRecordForm);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(newDepressionInterventionRecordForm newDepressionInterventionRecordForm) {
        return Result.ok(newDepressionInterventionRecordFormService.selectById(newDepressionInterventionRecordForm));
    }

    @RequestMapping(value = "/selectPage")
    @ResponseBody
    public Result selectRegInfo(newDepressionInterventionRecordForm newDepressionInterventionRecordForm, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        newDepressionInterventionRecordForm.setBegin((page-1)*limit);
        newDepressionInterventionRecordForm.setLimit(limit);
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newDepressionInterventionRecordForm.setHealthManagementSpecialist(yh.getUserCode());
        List<newDepressionInterventionRecordForm> newThreeSimultaneous1= newDepressionInterventionRecordFormService.selectByPage(newDepressionInterventionRecordForm);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newDepressionInterventionRecordFormService.selectByPageCount(newDepressionInterventionRecordForm));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newThreeSimultaneous1);
        return Result.ok(rsMap);
    }

}
