package com.yihe.web.controller.system;

import com.yihe.system.domain.NewCommodityData;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.NewCommodityDataService;
import com.yihe.system.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/NewCommodityDataController")
public class NewCommodityDataController {
    @Resource
    NewCommodityDataService newCommodityDataService;

    @RequestMapping(value = "/selectInvent")
    @ResponseBody
    public Result selectInvent(NewCommodityData newCommodityData) {
        return Result.ok(newCommodityDataService.selectInvent(newCommodityData));
    }


    @RequestMapping(value = "/insertOrUpdate")
    @ResponseBody
    public Result insertOrUpdate(NewCommodityData newCommodityData) {
        return Result.ok(newCommodityDataService.insertOrUpdate(newCommodityData));
    }

    @RequestMapping(value = "/selectByIdAndCode")
    @ResponseBody
    public Result selectByIdAndCode(NewCommodityData newCommodityData) {
        YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        newCommodityData.setOrgCode(yhUserReginfo.getOrgCode());
        return Result.ok(newCommodityDataService.selectByIdAndCode(newCommodityData));
    }


}
