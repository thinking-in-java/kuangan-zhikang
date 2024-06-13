package com.yihe.web.controller.system;

import com.yihe.system.domain.YhInterventionTemplate;
import com.yihe.system.domain.YhProgramsDictionary;
import com.yihe.system.domain.YhProgramsTemplate;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhInterventionTemplateService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/YhInterventionTemplateController")
@Api(value = "YhInterventionTemplateController", description = "YhInterventionTemplateController管理")
public class YhInterventionTemplateController {
    @Autowired
    YhInterventionTemplateService yhInterventionTemplateService;

    @ApiOperation(value = "分页查询干预计划模板")
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    @ResponseBody
    public Map selectByPrimaryKey(YhInterventionTemplate templte) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        Map<String, Object> map=new HashMap<String, Object>();
        if(templte.getSourceType()!=null && !templte.getSourceType().equals("0")){
            if(templte.getSourceType().equals("1")){
                templte.setOperation("0");
                templte.setSourceType(null);
            }else if(templte.getSourceType().equals("2")){
                templte.setOperation(yh.getUserCode());
                templte.setSourceType(null);
            }else if(templte.getSourceType().equals("3")){
                templte.setSourceType("0");
                templte.setOperation(yh.getUserCode());
            }
        }
        int count=yhInterventionTemplateService.selectcount(templte);
        List<YhInterventionTemplate> temp= yhInterventionTemplateService.selectBypage(templte);


        String Usercode=yhInterventionTemplateService.roleid(yh.getUserCode());
        if(temp!=null){

        for (int i=0 ;i<temp.size();i++){
            temp.get(i).setNowusercode(yh.getUserCode());
            temp.get(i).setRoleid(Usercode);
          }

        }
        map.put("msg","");
        map.put("code",20000);
        map.put("count",count);
        map.put("data", temp);
        return map;
    }
    @ApiOperation(value = "查询服务的内容")
    @RequestMapping(value = "/turntomove", method = RequestMethod.GET)
    @ResponseBody
    public int turntomove(YhInterventionTemplate templte) {
        if(templte.getInterventionId()==null){
            return 0;
        }else{
            Subject subject = SecurityUtils.getSubject();
            YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
            templte.setUsercode(yh.getUserCode());
            return yhInterventionTemplateService.changetable(templte);
        }

    }

    @ApiOperation(value = "查询干预计划模板每天的计划")
    @RequestMapping(value = "/selectyhprograms", method = RequestMethod.GET)
    @ResponseBody
    public Map selectyhprograms(YhProgramsTemplate templte) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        templte.setUsercode(yh.getUserCode());
        Map<String, Object> map=new HashMap<String, Object>();
        int count=yhInterventionTemplateService.selectyhprogramstemplatecount(templte);
        List<YhProgramsTemplate> temp= yhInterventionTemplateService.selectbyprograms(templte);

        map.put("msg","");
        map.put("code",20000);
        map.put("count",count);
        map.put("data", temp);
        return map;
    }


    @ApiOperation(value = "查询服务的内容")
    @RequestMapping(value = "/selectcount", method = RequestMethod.GET)
    @ResponseBody
    public List<YhProgramsDictionary> selectyhprograms() {

        return yhInterventionTemplateService.selectyhprogramsdictionary();
    }
    @ApiOperation(value = "查询计划数")
    @RequestMapping(value = "/selectcountbyname", method = RequestMethod.GET)
    @ResponseBody
    public Result selectcountbyname(String  templateName) {
        return Result.ok(yhInterventionTemplateService.selectcountbyname(templateName));
    }

    @ApiOperation(value = "查询计划数")
    @RequestMapping(value = "/selectcountdata", method = RequestMethod.GET)
    @ResponseBody
    public Result selectcountdata() {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        return Result.ok(yhInterventionTemplateService.selectcountdata(yh.getUserCode()));
    }

    @ApiOperation(value = "添加计划")
    @RequestMapping(value = "/insertplan", method = RequestMethod.POST)
    @ResponseBody
    public int insertplan(YhProgramsTemplate templte) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        templte.setUsercode(yh.getUserCode());
        return yhInterventionTemplateService.insertproogram(templte);
    }

    @ApiOperation(value = "修改计划")
    @RequestMapping(value = "/updateplan", method = RequestMethod.POST)
    @ResponseBody
    public int updateplan(YhProgramsTemplate templte) {
        return yhInterventionTemplateService.updateplan(templte);
    }

    @ApiOperation(value = "删除计划")
    @RequestMapping(value = "/deleteplan", method = RequestMethod.POST)
    @ResponseBody
    public int deleteplan(@RequestBody List<YhProgramsTemplate> programsTemplates) {
        return yhInterventionTemplateService.deleteplan(programsTemplates);
    }

    @ApiOperation(value = "查询干预计划模板")
    @RequestMapping(value = "/selectinterventionId", method = RequestMethod.POST)
    @ResponseBody
    public YhInterventionTemplate interventionId(YhInterventionTemplate templte) {
        return yhInterventionTemplateService.interventionId(templte);
    }

    @ApiOperation(value = "根据id跟usercode清除临时表")
    @RequestMapping(value = "/removetemporary", method = RequestMethod.POST)
    @ResponseBody
    public int removetemporary(YhProgramsTemplate templte) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        templte.setUsercode(yh.getUserCode());
        return yhInterventionTemplateService.removetemporary(templte);
    }


    @ApiOperation(value = "保存干预计划模板")
    @RequestMapping(value = "/changetempdate", method = RequestMethod.POST)
    @ResponseBody
    public int changetempdate(YhInterventionTemplate templte) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        templte.setUsercode(yh.getUserCode());
        return yhInterventionTemplateService.changetempdate(templte);
    }


    @ApiOperation(value = "删除干预计划模板")
    @RequestMapping(value = "/deletetempdate", method = RequestMethod.POST)
    @ResponseBody
    public int deletetempdate(YhInterventionTemplate templte) {

        return yhInterventionTemplateService.deletetempdate(templte);
    }


    @ApiOperation(value = "查询模板并按照条件分好")
    @RequestMapping(value = "/selecttemplatebyconditions", method = RequestMethod.GET)
    @ResponseBody
    public Result selecttemplatebyconditions(YhInterventionTemplate templte) {
        if(templte.getSourceType()!=null){
            if(templte.getSourceType().equals("1")){
                templte.setSouceName("0");
            }else if(templte.getSourceType().equals("2") || templte.getSourceType().equals("3") ){
                Subject subject = SecurityUtils.getSubject();
                YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
                templte.setSouceName(yh.getUserCode());
            }
        }
        List<YhInterventionTemplate> temp= yhInterventionTemplateService.selecttemplate(templte);
        return Result.ok(temp);
    }

}
