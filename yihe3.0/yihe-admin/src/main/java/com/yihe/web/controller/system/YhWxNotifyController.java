package com.yihe.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserUba;
import com.yihe.system.domain.YhUserNotify;
import com.yihe.system.domain.yhWxNotify;
import com.yihe.system.mapper.YhUserNotifyMapper;
import com.yihe.system.mapper.YhWxNotifyMapper;
import com.yihe.system.service.YhWxNotifyService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/YhWxNotifyController")
public class YhWxNotifyController {
    @Autowired
    YhWxNotifyService yhWxNotifyService;
    @Autowired
    YhUserNotifyMapper yhUserNotifyMapper;
    @ApiOperation(value = "插入计划表")
    @ResponseBody
    @RequestMapping(value="/insertnotify",method= RequestMethod.POST)
    public Result insertnotify(yhWxNotify yhWxNotify){
        yhWxNotifyService.insertyhWxNotify(yhWxNotify);
        YhUserNotify userNotify=new YhUserNotify();
        userNotify.setUser_id(yhWxNotify.getUsercode());
        userNotify.setTitle("站内信");
        userNotify.setContent(yhWxNotify.getWxNotifyBackup()+">>");
        userNotify.setIs_read(0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userNotify.setSend_date(new Date());
        userNotify.setTo_service_id(5+"");
        userNotify.setType(2);
        userNotify.setNews_id(yhWxNotify.getWxNotifyId());
        return Result.ok(yhUserNotifyMapper.insertnotify(userNotify));
    }


    @ApiOperation(value = "查询是否拥有信息")
    @ResponseBody
    @RequestMapping(value="/selectznxquere",method= RequestMethod.GET)
    public Result selectznxquere(yhWxNotify yhWxNotify){

        return Result.ok( yhWxNotifyService.selectyhweno(yhWxNotify));
    }



    @ApiOperation(value = "根据个人查询站内信")
    @ResponseBody
    @RequestMapping(value="/selectznxbyusercode")
    public Result selectznxbyusercode(yhWxNotify yhWxNotify){
        yhWxNotifyService.changedate(yhWxNotify);
        return Result.ok( yhWxNotifyService.selectznxbyusercode(yhWxNotify));
    }

    @ApiOperation(value = "插入计划表多人")
    @ResponseBody
    @RequestMapping(value="/insertnotifys",method= RequestMethod.POST)
    public Result insertnotifys(yhWxNotify yhWxNotify){
        int is=0;
        for (int i=0;i<yhWxNotify.getIds().length;i++){
            yhWxNotify.setWxNotifyId(null);
        yhWxNotifyService.insertyhWxNotify(yhWxNotify);
        YhUserNotify userNotify=new YhUserNotify();
        userNotify.setUser_id(yhWxNotify.getIds()[i]);
        userNotify.setTitle("站内信");
        userNotify.setContent(yhWxNotify.getWxNotifyBackup()+">>");
        userNotify.setIs_read(0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userNotify.setSend_date(new Date());
        userNotify.setTo_service_id(5+"");
        userNotify.setType(2);
        userNotify.setNews_id(yhWxNotify.getWxNotifyId());
            is+=yhUserNotifyMapper.insertnotify(userNotify);
        }
        return Result.ok(is);
    }
}
