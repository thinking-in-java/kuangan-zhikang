package com.yihe.web.controller.system;

import com.yihe.system.domain.TheBasicUse;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.TheBasicUseService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/basicuse")
public class TheBasicUseController {
    @Autowired
    TheBasicUseService theBasicUseService;
    @ApiOperation(value = "查询健康风险评估")
    @RequestMapping(value = "/jiankangfengxian", method = RequestMethod.POST)
    @ResponseBody
    public Result jiankangfengxian(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.jiankangfengxian(theBasicUse));
    }

    @ApiOperation(value = "常见症状评估")
    @RequestMapping(value = "/changjian", method = RequestMethod.POST)
    @ResponseBody
    public Result changjian(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.changjian(theBasicUse));
    }

    @ApiOperation(value = "A型")
    @RequestMapping(value = "/Axing", method = RequestMethod.POST)
    @ResponseBody
    public Result Axing(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.Axing(theBasicUse));
    }

    @ApiOperation(value = "PSTRI")
    @RequestMapping(value = "/PSTRI", method = RequestMethod.POST)
    @ResponseBody
    public Result PSTRI(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.PSTRI(theBasicUse));
    }


    @ApiOperation(value = "xinlijiankang")
    @RequestMapping(value = "/xinlijiankang", method = RequestMethod.POST)
    @ResponseBody
    public Result xinlijiankang(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.xinlijiankang(theBasicUse));
    }

    @ApiOperation(value = "sleeprepout")
    @RequestMapping(value = "/sleeprepout", method = RequestMethod.POST)
    @ResponseBody
    public Result sleeprepout(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.sleeprepout(theBasicUse));
    }

    @ApiOperation(value = "xiguanpinggu")
    @RequestMapping(value = "/xiguanpinggu", method = RequestMethod.POST)
    @ResponseBody
    public Result xiguanpinggu(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.xiguanpinggu(theBasicUse));
    }


    @ApiOperation(value = "tiwen")
    @RequestMapping(value = "/tiwen", method = RequestMethod.POST)
    @ResponseBody
    public Result tiwen(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.tiwen(theBasicUse));
    }

    @ApiOperation(value = "tizhong")
    @RequestMapping(value = "/tizhong", method = RequestMethod.POST)
    @ResponseBody
    public Result tizhong(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.tizhong(theBasicUse));
    }

    @ApiOperation(value = "xueya")
    @RequestMapping(value = "/xueya", method = RequestMethod.POST)
    @ResponseBody
    public Result xueya(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.xueya(theBasicUse));
    }

    @ApiOperation(value = "xuetang")
    @RequestMapping(value = "/xuetang", method = RequestMethod.POST)
    @ResponseBody
    public Result xuetang(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.xuetang(theBasicUse));
    }

    @ApiOperation(value = "xueyang")
    @RequestMapping(value = "/xueyang", method = RequestMethod.POST)
    @ResponseBody
    public Result xueyang(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.xueyang(theBasicUse));
    }

    @ApiOperation(value = "xinlv")
    @RequestMapping(value = "/xinlv", method = RequestMethod.POST)
    @ResponseBody
    public Result xinlv(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.xinlv(theBasicUse));
    }

    @ApiOperation(value = "tizhi")
    @RequestMapping(value = "/tizhi", method = RequestMethod.POST)
    @ResponseBody
    public Result tizhi(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.tizhi(theBasicUse));
    }

    @ApiOperation(value = "xuezhi")
    @RequestMapping(value = "/xuezhi", method = RequestMethod.POST)
    @ResponseBody
    public Result xuezhi(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.xuezhi(theBasicUse));
    }

    @ApiOperation(value = "shengongneg")
    @RequestMapping(value = "/shengongneg", method = RequestMethod.POST)
    @ResponseBody
    public Result shengongneg(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.shengongneg(theBasicUse));
    }

    @ApiOperation(value = "gangongneng")
    @RequestMapping(value = "/gangongneng", method = RequestMethod.POST)
    @ResponseBody
    public Result gangongneng(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.gangongneng(theBasicUse));
    }

    @ApiOperation(value = "xindaintu")
    @RequestMapping(value = "/xindaintu", method = RequestMethod.POST)
    @ResponseBody
    public Result xindaintu(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.xindaintu(theBasicUse));
    }

    @ApiOperation(value = "nianjian")
    @RequestMapping(value = "/nianjian", method = RequestMethod.POST)
    @ResponseBody
    public Result nianjian(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.nianjian(theBasicUse));
    }



    @ApiOperation(value = "qitazhibiao")
    @RequestMapping(value = "/qitazhibiao", method = RequestMethod.POST)
    @ResponseBody
    public Result qitazhibiao(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.qitazhibiao(theBasicUse));
    }


    @ApiOperation(value = "yinshiriji")
    @RequestMapping(value = "/yinshiriji", method = RequestMethod.POST)
    @ResponseBody
    public Result yinshiriji(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.yinshiriji(theBasicUse));
    }


    @ApiOperation(value = "yundongriji")
    @RequestMapping(value = "/yundongriji", method = RequestMethod.POST)
    @ResponseBody
    public Result yundongriji(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.yundongriji(theBasicUse));
    }

    @ApiOperation(value = "shuimianriji")
    @RequestMapping(value = "/shuimianriji", method = RequestMethod.POST)
    @ResponseBody
    public Result shuimianriji(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.shuimianriji(theBasicUse));
    }

    @ApiOperation(value = "gaoxueya")
    @RequestMapping(value = "/gaoxueya", method = RequestMethod.POST)
    @ResponseBody
    public Result gaoxueya(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.gaoxueya(theBasicUse));
    }

    @ApiOperation(value = "gaoxuetang")
    @RequestMapping(value = "/gaoxuetang", method = RequestMethod.POST)
    @ResponseBody
    public Result gaoxuetang(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.gaoxuetang(theBasicUse));
    }

    @ApiOperation(value = "gaoxuezhi")
    @RequestMapping(value = "/gaoxuezhi", method = RequestMethod.POST)
    @ResponseBody
    public Result gaoxuezhi(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.gaoxuezhi(theBasicUse));
    }

    @ApiOperation(value = "gaoniansuan")
    @RequestMapping(value = "/gaoniansuan", method = RequestMethod.POST)
    @ResponseBody
    public Result gaoniansuan(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.gaoniansuan(theBasicUse));
    }

    @ApiOperation(value = "jianzhi")
    @RequestMapping(value = "/jianzhi", method = RequestMethod.POST)
    @ResponseBody
    public Result jianzhi(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.jianzhi(theBasicUse));
    }

    @ApiOperation(value = "xiguanriji")
    @RequestMapping(value = "/xiguanriji", method = RequestMethod.POST)
    @ResponseBody
    public Result xiguanriji(TheBasicUse theBasicUse)  {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh.getUserType()==4 && (theBasicUse.getNoworg()==null || theBasicUse.getNoworg()=="")){
            theBasicUse.setUserCode(yh.getUserCode());
        }else{
            if(theBasicUse.getNoworg()==null || theBasicUse.getNoworg()==""){
                theBasicUse.setNoworg(yh.getOrgCode());
            }
        }
        theBasicUse.setBigantime(theBasicUse.getAlltime().substring(0,10));
        theBasicUse.setEndtime(theBasicUse.getAlltime().substring(12)+" 23:59:59");
        return Result.ok(theBasicUseService.xiguanriji(theBasicUse));
    }
}
