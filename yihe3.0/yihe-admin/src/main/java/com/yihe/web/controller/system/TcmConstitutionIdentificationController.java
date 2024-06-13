package com.yihe.web.controller.system;


import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.TcmConstitutionIdentificationService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 贺平
 * @Date: 2021/11/1
 * @ClassName: com.yihe.web.controller.system
 * @Description:
 **/
@RestController
public class TcmConstitutionIdentificationController {

    @Autowired
    TcmConstitutionIdentificationService tcmConstitutionIdentificationService;

    //查询所有用户的中医体质辨识信息
    @RequestMapping(value = "/chinesemedicine/selectUserAll", method = RequestMethod.GET)
    public Result tcmConstitutionIdentificationUserAll(
            @RequestParam(required = false) String tree,
            @RequestParam(required = false) String pinggu, @RequestParam(required = false) String name,
            @RequestParam(required = false) String strTime, @RequestParam(required = false) String endTime,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int limit){
        if (StringUtil.isNotEmpty(startTime)) {
            String[] split = startTime.split(" - ");
            strTime = split[0];
            endTime = split[1];
        }
        
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        String id = tcmConstitutionIdentificationService.selectDoctorIdByUserCode(userCode);

        Map<String,Object> map=new HashMap<>();
        map.put("pagenum",page);
        map.put("pagesize",limit);
        map.put("tree",tree);
        map.put("strTime",strTime);
        map.put("endTime",endTime);
        map.put("name",name);
        map.put("doctor",id);
        if(StringUtil.isNotEmpty(pinggu)){
            String[] str = pinggu.split("=");
            map.put("key",str[0]);
            map.put("value",str[1]);
        }


        PageInfo<YhUserReginfo> yhUserReginfoAll = tcmConstitutionIdentificationService.tcmConstitutionIdentificationUserAll(map);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", yhUserReginfoAll.getTotal());
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", yhUserReginfoAll.getList());
        return Result.ok(rsMap);
    }

    //根据筛选条件查询指定用户的中医体质辨识信息
    @RequestMapping(value = "/chinesemedicine/selectUserCondition", method = RequestMethod.POST)
    public Result tcmConstitutionIdentificationConditionUser(){
        List<YhUserReginfo> yhUserReginfoCondition = tcmConstitutionIdentificationService.tcmConstitutionIdentificationConditionUser();
        return Result.ok(yhUserReginfoCondition);
    }

}
