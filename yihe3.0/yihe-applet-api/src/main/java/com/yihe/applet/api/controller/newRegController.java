package com.yihe.applet.api.controller;

import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.service.newRegService;
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
@RequestMapping("/api/newRegControllerApi")
public class newRegController {
    @Resource
    newRegService newRegService;

    @RequestMapping(value = "/selectNewRegCount")
    @ResponseBody
    public Result selectNewRegCount(String usercode){
        return Result.ok(newRegService.selectByUserCodeUnsignedCount(usercode));
    }

    @RequestMapping(value = "/selectRegInfoPage")
    @ResponseBody
    public Result selectRegInfoPage(String usercode){
        return Result.ok(newRegService.selectByUser(usercode));
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Result update(Integer id,Integer index, String type,String img){
        return Result.ok(newRegService.updateByNewMedical(id,index,type,img));
    }



}
