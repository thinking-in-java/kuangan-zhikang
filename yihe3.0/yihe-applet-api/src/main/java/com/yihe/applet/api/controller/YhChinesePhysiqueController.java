package com.yihe.applet.api.controller;

import com.yihe.applet.api.service.YhChinesePhysiqueService;
import com.yihe.applet.api.util.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;

@Controller
@RequestMapping("/api/YhChinesePhysiqueController")
public class YhChinesePhysiqueController {
    @Autowired
    YhChinesePhysiqueService yhChinesePhysiqueService;
    @RequestMapping("/selectallinfoq")
    @ResponseBody
    public Result selectallinfoq(String Usercode) {
        return Result.ok(yhChinesePhysiqueService.selectallinfo(Usercode));
    }

    @RequestMapping(value = "/addhinesePhysique")
    @ResponseBody
    public Result addPressureEvaluate(Integer type, String list, String userCode) throws ParseException {
        System.out.println(list);
        return Result.ok(yhChinesePhysiqueService.addhinesePhysique(type,list,userCode));
    }
    @RequestMapping(value = "/selectanwser")
    @ResponseBody
    public Result selectanwser(String userCode) throws ParseException {
        return Result.ok(yhChinesePhysiqueService.selectanwser(userCode));
    }

    @RequestMapping(value = "/isfirstzy")
    @ResponseBody
    public Result isfirstzy(String userCode) throws ParseException {
        return Result.ok(yhChinesePhysiqueService.isfirstzy(userCode));
    }

    @RequestMapping(value = "/isfirstzy2")
    @ResponseBody
    public Result isfirstzy2(String userCode) throws ParseException {
        return Result.ok(yhChinesePhysiqueService.isfirstzy2(userCode));
    }

    @RequestMapping(value = "/getallhelplan")
    @ResponseBody
    public Result getallhelplan(String userCode) throws ParseException {
        return Result.ok(yhChinesePhysiqueService.getallhelplan(userCode));
    }

}
