package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserMedal;
import com.yihe.applet.api.service.YhUserMedalService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/YhUserMedalController")
@Api(value = "YhUserMedalController", description = "YhUserMedalController管理")
public class YhUserMedalController {
    @Resource
    YhUserMedalService yhUserMedalService;
    @RequestMapping(value = "/insertmeal")
    @ResponseBody
    public Result showNotifyType(YhUserMedal medal) {


        return Result.ok(yhUserMedalService.insertmeal(medal));
    }
}
