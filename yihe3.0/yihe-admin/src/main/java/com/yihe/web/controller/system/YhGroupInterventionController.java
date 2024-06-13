package com.yihe.web.controller.system;

import com.yihe.system.domain.YHDocGroup;
import com.yihe.system.domain.YhProgramsTemplate;
import com.yihe.system.domain.YhTemplateDetail;
import com.yihe.system.domain.Yhgroupintervention;
import com.yihe.system.service.YhGroupInterventionService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/YhGroupIntervention")
public class YhGroupInterventionController {
    @Autowired
    YhGroupInterventionService yhGroupInterventionService;
    @ApiOperation(value = "干预分组管理的修改")
    @RequestMapping(value = "/updategroupintervention", method = RequestMethod.GET)
    @ResponseBody
    public Result updategroupintervention(YHDocGroup docgrop){

        return Result.ok( yhGroupInterventionService.updategroupintervention(docgrop));
    }

    @ApiOperation(value = "干预分组管理的删除")
    @RequestMapping(value = "/deletegroupintervention", method = RequestMethod.GET)
    @ResponseBody
    public Result deletegroupintervention(YHDocGroup docgrop){
        return Result.ok( yhGroupInterventionService.deletegroupintervention(docgrop));
    }

    @ApiOperation(value = "查询组并分页")
    @RequestMapping(value = "/selectgroppage",method =RequestMethod.GET )
    @ResponseBody
    public Map selectgroppage(YHDocGroup Yhdocgrop){
        Map<String, Object> map=new HashMap<String, Object>();
        List<Yhgroupintervention> intervention=yhGroupInterventionService.selectgroppage(Yhdocgrop);
        map.put("msg","");
        map.put("code",20000);
        map.put("count",yhGroupInterventionService.selectgroppagecount(Yhdocgrop));
        map.put("data",intervention);
        return map;
    }


    @ApiOperation(value = "移除某人的分组")
    @RequestMapping(value = "/deletegroup", method = RequestMethod.POST)
    @ResponseBody
    public Result deletegroup(YHDocGroup docgrop){
        return Result.ok( yhGroupInterventionService.deletegroup(docgrop));
    }

    @ApiOperation(value = "修改某人的分组等级")
    @RequestMapping(value = "/updategroup", method = RequestMethod.POST)
    @ResponseBody
    public Result updategroup(YHDocGroup docgrop){
        return Result.ok( yhGroupInterventionService.updategroup(docgrop));
    }

    @ApiOperation(value = "查找某人的计划")
    @RequestMapping(value = "/selectplanbyuser", method = RequestMethod.GET)
    @ResponseBody
    public Map selectplanbyuser(YhTemplateDetail yhTemplateDetail){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("msg","");
        map.put("code",20000);
        map.put("count",yhGroupInterventionService.selectcount(yhTemplateDetail));
        map.put("data",yhGroupInterventionService.selectplanbyuser(yhTemplateDetail));
        return map;
    }
    @ApiOperation(value = "查找某人的计划")
    @RequestMapping(value = "/deletesplanbyid", method = RequestMethod.POST)
    @ResponseBody
    public Result deletesplanbyid(YhTemplateDetail yhTemplateDetail){

        return Result.ok(yhGroupInterventionService.deletesplanbyid(yhTemplateDetail));
    }


    @ApiOperation(value = "查询计划内容")
    @RequestMapping(value = "/selectplancount", method = RequestMethod.GET)
    @ResponseBody
    public Result selectplancount(){

        return Result.ok(yhGroupInterventionService.selectplancount());
    }

}
