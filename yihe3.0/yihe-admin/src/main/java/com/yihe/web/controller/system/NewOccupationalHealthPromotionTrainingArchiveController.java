package com.yihe.web.controller.system;

import com.yihe.system.domain.NewMonitoringOccupationalHazardFactors;
import com.yihe.system.domain.NewOccupationalHealthPromotionTrainingArchive;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.NewOccupationalHealthPromotionTrainingArchiveService;
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
@RequestMapping("/NewOccupationalHealthPromotionTrainingArchiveController")
public class NewOccupationalHealthPromotionTrainingArchiveController {

    @Resource
    NewOccupationalHealthPromotionTrainingArchiveService newOccupationalHealthPromotionTrainingArchiveService;

    @RequestMapping(value = "/insetNew")
    @ResponseBody
    public Result insetNew(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) {
        //根据userCode查询健管师code
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newOccupationalHealthPromotionTrainingArchive.setStaffingPersonnel(yh.getUserCode());
        newOccupationalHealthPromotionTrainingArchive.setInsertTime(new Date());
        int count=newOccupationalHealthPromotionTrainingArchiveService.insetNew(newOccupationalHealthPromotionTrainingArchive);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/updateNew")
    @ResponseBody
    public Result updateNew(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) {
        //根据userCode查询健管师code

        int count=newOccupationalHealthPromotionTrainingArchiveService.updateNew(newOccupationalHealthPromotionTrainingArchive);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) {
        return Result.ok(newOccupationalHealthPromotionTrainingArchiveService.selectById(newOccupationalHealthPromotionTrainingArchive));
    }

    @RequestMapping(value = "/selectPage")
    @ResponseBody
    public Result selectRegInfo(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        newOccupationalHealthPromotionTrainingArchive.setBegin((page-1)*limit);
        newOccupationalHealthPromotionTrainingArchive.setLimit(limit);
        List<NewOccupationalHealthPromotionTrainingArchive> newThreeSimultaneous1= newOccupationalHealthPromotionTrainingArchiveService.selectByPage(newOccupationalHealthPromotionTrainingArchive);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newOccupationalHealthPromotionTrainingArchiveService.selectByPageCount(newOccupationalHealthPromotionTrainingArchive));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newThreeSimultaneous1);
        return Result.ok(rsMap);
    }
    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public Result deleteById(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) {
        return Result.ok(newOccupationalHealthPromotionTrainingArchiveService.deleteById(newOccupationalHealthPromotionTrainingArchive));
    }



}
