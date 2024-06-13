package com.yihe.web.controller.system;

import com.yihe.system.domain.*;
import com.yihe.system.service.YhInterventiongroupingService;
import com.yihe.system.service.YhUserSettingService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("/YhInterventiongroupingController")
@Api(value = "YhInterventiongroupingController", description = "YhInterventiongroupingController管理")
public class YhInterventiongroupingController {
    @Autowired
    YhInterventiongroupingService yhInterventiongroupingService;
    @Autowired
    YhUserSettingService yhusersettingservice;

    @ApiOperation(value = "创建计划选择干预对象的查询")
    @RequestMapping(value = "/selectnameandplan", method = RequestMethod.GET)
    @ResponseBody
    public Map selectnameandplan(Interventiongrouping interventiongrouping) {
        Calendar mycalendar=Calendar.getInstance();
        String year=String.valueOf(mycalendar.get(Calendar.YEAR));
        if(interventiongrouping.getAgemin()!=null){
            interventiongrouping.setMinage((Integer.valueOf(year)-interventiongrouping.getAgemin())+"");
        }
        if(interventiongrouping.getAgemax()!=null){
            interventiongrouping.setMaxage((Integer.valueOf(year)-interventiongrouping.getAgemax())+"");
        }
        if(interventiongrouping.getDis()!=null){
            interventiongrouping.setDis("f"+interventiongrouping.getDis()+"_");
        }

        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        Map<String, Object> map=new HashMap<String, Object>();
        interventiongrouping.setUser_code(yh.getUserCode());
        List<Interventiongrouping> inters= yhInterventiongroupingService.selectnameandplan(interventiongrouping);
        List<YhOrgTree> yhallorg=yhusersettingservice.selectallorg();
        for (int i = 0; i < inters.size(); i++) {
            inters.set(i,findorgtpo(inters.get(i), yhallorg)) ;
        }
        int count=0;
        if(inters.size()!=0){
            count=inters.get(0).getCount();
        }
        map.put("msg","");
        map.put("code",20000);
        map.put("count",count);
        map.put("data", inters);
        return map;
    }

    @ApiOperation(value = "根据选择的模板把计划排好")
    @RequestMapping(value = "/selectserviceplan")
    @ResponseBody
    public Map selectserviceplan(YhProgramsTemplate yhProgramsTemplate) {
        Map<String, Object> map=new HashMap<String, Object>();
        if(yhProgramsTemplate.getPlanContent()!=null){


        String[] itemLists=yhProgramsTemplate.getPlanContent().split(",");
        List<Integer> itemList=new ArrayList<>();
        for (int i=0;i<itemLists.length;i++){
            itemList.add(Integer.valueOf(itemLists[i]));
        }
        yhProgramsTemplate.setTemplatid(itemList);
        }
        List<YhProgramsTemplate> template =yhInterventiongroupingService.selectserviceplan(yhProgramsTemplate);
        int count= yhInterventiongroupingService.selecttemplatecount(yhProgramsTemplate);
        map.put("msg","");
        map.put("code",20000);
        map.put("count",count);
        map.put("data", template);
        return map;
    }

    @ApiOperation(value = "修改是否选中")
    @RequestMapping(value = "/updateischoose")
    @ResponseBody
    public Integer updateischoose(YhProgramsTemplate yhProgramsTemplate){

        return yhInterventiongroupingService.updateischoose(yhProgramsTemplate);
    }

    @ApiOperation(value = "测试定时器")
    @RequestMapping(value = "/testtime")
    @ResponseBody
    public void insert(){

       yhInterventiongroupingService.insertservice();
    }

    //插入计划
    @RequestMapping(value = "/insertplans")
    @ResponseBody
    public Integer insertplans(YhProgramsTemplate yhProgramsTemplate) throws ParseException {
        System.out.println(yhProgramsTemplate);
        return yhInterventiongroupingService.insertplans(yhProgramsTemplate);
    }

    @ApiOperation(value = "添加临时表计划内容")
    @RequestMapping(value = "/addtemplatetempro", method = RequestMethod.POST)
    @ResponseBody
    public Result addtemplatetempro(YhProgramsTemplate yhProgramsTemplate){

        return Result.ok(yhInterventiongroupingService.addtemplatetempro(yhProgramsTemplate));
    }

    @ApiOperation(value = "修改临时表计划内容")
    @RequestMapping(value = "/updatetemplatetempro", method = RequestMethod.POST)
    @ResponseBody
    public Result updatetemplatetempro(YhProgramsTemplate yhProgramsTemplate){

        return Result.ok(yhInterventiongroupingService.updatetemplatetempro(yhProgramsTemplate));
    }

    //查询最顶层的机构
    public Interventiongrouping findorgtpo(Interventiongrouping cardinfo, List<YhOrgTree> yhallorg) {
        if(cardinfo.getSuper_Org_Code().equals("0")) {
            return cardinfo;
        }else {
            for (int i = 0; i < yhallorg.size(); i++) {
                if(cardinfo.getSuper_Org_Code().equals(yhallorg.get(i).getOrgCode())) {
                    cardinfo.setOrg_name(yhallorg.get(i).getOrgName()+">"+cardinfo.getOrg_name());
                    cardinfo.setSuper_Org_Code(yhallorg.get(i).getSuperOrgCode());
                }
            }
            return findorgtpo(cardinfo, yhallorg);
        }

    }
}
