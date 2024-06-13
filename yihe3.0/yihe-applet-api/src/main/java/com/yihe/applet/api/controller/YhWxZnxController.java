package com.yihe.applet.api.controller;

import com.yihe.system.domain.yhWxNotify;
import com.yihe.system.service.YhWxNotifyService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/YhWxNotifyController")
public class YhWxZnxController {
    @Autowired
    YhWxNotifyService yhWxNotifyService;

    @ApiOperation(value = "查询是否拥有信息")
    @ResponseBody
    @RequestMapping(value="/selectznxquere",method= RequestMethod.POST)
    public Result selectznxquere(yhWxNotify yhWxNotify){

        return Result.ok( yhWxNotifyService.selectyhweno(yhWxNotify));

    }

    @ApiOperation(value = "根据个人查询站内信")
    @ResponseBody
    @RequestMapping(value="/selectznxbyusercode")
    public Result selectznxbyusercode(yhWxNotify yhWxNotify){

        return Result.ok( yhWxNotifyService.selectznxbyusercode(yhWxNotify));
    }

    @ApiOperation(value = "查看完站内信后将信息修改问已读状态")
    @ResponseBody
    @RequestMapping(value="/changedate")
    public Result changedate(yhWxNotify yhwxnotify){
        return Result.ok( yhWxNotifyService.changedate(yhwxnotify));
    }
}
