package com.yihe.web.controller.system;

import com.yihe.common.utils.poi.ExcelUtilQ;
import com.yihe.system.domain.YhServicePage;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhManageTaskJgService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/yhmanagetaskjgcontroller")
@Api(value = "yhmanagetaskjgcontroller", description = "yhmanagetaskjgcontroller")
public class YhManageTaskJgController {
    @Autowired
    YhManageTaskJgService yhManageTaskJgService;

    @ApiOperation(value = "分页查询个数")
    @RequestMapping(value = "/selectpagecount", method = RequestMethod.POST)
    @ResponseBody
    public Result selectpagecount(YhServicePage Yhservicepage) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
        Yhservicepage.setUsercode(reginfo.getUserCode());
        Yhservicepage.setOrgcode(reginfo.getOrgCode());
        return Result.ok(yhManageTaskJgService.selectcountpage(Yhservicepage));
    }

    @ApiOperation(value = "查询顶部的四个框框")
    @RequestMapping(value = "/selectservicecount", method = RequestMethod.POST)
    @ResponseBody
    public Result selectservicecount(YhServicePage Yhservicepage) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
        Yhservicepage.setUsercode(reginfo.getUserCode());
        Yhservicepage.setOrgcode(reginfo.getOrgCode());
        return Result.ok(yhManageTaskJgService.selectservicecount(Yhservicepage));
    }


    @ApiOperation(value = "查询服务的人列表")
    @RequestMapping(value = "/selectcount", method = RequestMethod.POST)
    @ResponseBody
    public Result selectcount(YhServicePage Yhservicepage) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
        Yhservicepage.setUsercode(reginfo.getUserCode());
        Yhservicepage.setOrgcode(reginfo.getOrgCode());
        return Result.ok(yhManageTaskJgService.selectlist(Yhservicepage));
    }

    @ApiOperation(value = "查询统计分析")
    @RequestMapping(value = "/selectfenxi", method = RequestMethod.POST)
    @ResponseBody
    public Result selectfenxi(YhServicePage Yhservicepage) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
        Yhservicepage.setUsercode(reginfo.getUserCode());
        Yhservicepage.setOrgcode(reginfo.getOrgCode());
        return Result.ok(yhManageTaskJgService.selectfenxi(Yhservicepage));
    }

    @ApiOperation(value = "查询每个医生的信息")
    @RequestMapping(value = "/selectalldoctor", method = RequestMethod.POST)
    @ResponseBody
    public Result selectalldoctor(YhServicePage Yhservicepage) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
        Yhservicepage.setUsercode(reginfo.getUserCode());
        Yhservicepage.setOrgcode(reginfo.getOrgCode());
        return Result.ok(yhManageTaskJgService.getmundoctorby(Yhservicepage));
    }

    @ApiOperation(value = "导出每个医生的信息")
    @RequestMapping(value = "/exportalldoctor", method = RequestMethod.GET)
    public void exportalldoctor(YhServicePage Yhservicepage, HttpServletRequest request, HttpServletResponse response) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建标题
        List<List<String>> titles = new ArrayList<List<String>>();
        // 创建sheet名
        List<String> sheetname = new ArrayList<String>();

        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo reginfo =  (YhUserReginfo)subject.getPrincipal();
        Yhservicepage.setUsercode(reginfo.getUserCode());
        Yhservicepage.setOrgcode(reginfo.getOrgCode());
        List<YhServicePage> servicepage=yhManageTaskJgService.getmundoctorby(Yhservicepage);
        List<YhServicePage> servicepage2=yhManageTaskJgService.getmundoctorby2(Yhservicepage);
        List<String> titi = Arrays.asList("序号", "健管师编号", "健管师姓名", "任务数", "等待服务任务", "正在服务任务", "关闭服务任务", "完成服务任务", "未完成率", "关闭率", "完成率");
        titles.add(titi);
        List<String> titls=Arrays.asList("序号","健管师编号","健管师姓名","完成服务任务","小程序站内信","小程序订阅通知","公众号消息通知","短信通知","电话回访","上门回访");
        titles.add(titls);
        sheetname.add("服务任务处理");
        sheetname.add("已完成服务任务处理方式统计");
        for (int i = 0; i < sheetname.size(); i++) {
            HSSFSheet sheet = workbook.createSheet(sheetname.get(i));
            ExcelUtilQ.createTitle(workbook, sheet, titles.get(i),i);
            if (sheetname.get(i).equals("服务任务处理")) {
                // 循环表数据
                for (int j = 0; j < servicepage.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(j+1);
                    row.createCell(1).setCellValue(servicepage.get(j).getUsercode());
                    row.createCell(2).setCellValue(servicepage.get(j).getName());
                    row.createCell(3).setCellValue(servicepage.get(j).getAllshuliang()==null?0:servicepage.get(j).getAllshuliang());
                    row.createCell(4).setCellValue(servicepage.get(j).getDengdai()==null?0:servicepage.get(j).getDengdai());
                    row.createCell(5).setCellValue(servicepage.get(j).getZhengzai()==null?0:servicepage.get(j).getZhengzai());
                    row.createCell(6).setCellValue(servicepage.get(j).getGuanbi()==null?0:servicepage.get(j).getGuanbi());
                    row.createCell(7).setCellValue(servicepage.get(j).getWancheng()==null?0:servicepage.get(j).getWancheng());
                    if(servicepage.get(j).getAllshuliang()==null){
                        row.createCell(8).setCellValue(0);
                        row.createCell(9).setCellValue(0);
                        row.createCell(10).setCellValue(0);
                    }else{
                        if(servicepage.get(j).getWancheng()==null){
                            row.createCell(8).setCellValue(0+"%");

                        }else{
                            row.createCell(8).setCellValue(100-((double)Math.round(((double)servicepage.get(j).getWancheng()/(double)servicepage.get(j).getAllshuliang())*10000)/100)+"%");
                        }
                        if(servicepage.get(j).getGuanbi()==null){
                            row.createCell(9).setCellValue(0+"%");
                        }else{
                            row.createCell(9).setCellValue(((double)Math.round(((double)servicepage.get(j).getGuanbi()/(double)servicepage.get(j).getAllshuliang())*10000)/100)+"%");
                        }
                        if(servicepage.get(j).getWancheng()==null){
                            row.createCell(10).setCellValue(0+"%");
                        }else{
                            row.createCell(10).setCellValue((double)Math.round(((double)servicepage.get(j).getWancheng()/(double)servicepage.get(j).getAllshuliang()*10000)/100)+"%");
                        }

                    }


                }
            }
            if(sheetname.get(i).equals("已完成服务任务处理方式统计")){
                for (int j=0;j<servicepage2.size();j++){
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(j+1);
                    row.createCell(1).setCellValue(servicepage2.get(j).getUsercode());
                    row.createCell(2).setCellValue(servicepage2.get(j).getName());
                    row.createCell(3).setCellValue(servicepage2.get(j).getAllshuliang()==null?0:servicepage2.get(j).getAllshuliang());
                    row.createCell(4).setCellValue(servicepage2.get(j).getZnx()==null?0:servicepage2.get(j).getZnx());
                    row.createCell(5).setCellValue(servicepage2.get(j).getDytz()==null?0:servicepage2.get(j).getDytz());
                    row.createCell(6).setCellValue(servicepage2.get(j).getGzhxxtz()==null?0:servicepage2.get(j).getGzhxxtz());
                    row.createCell(7).setCellValue(servicepage2.get(j).getDxtz()==null?0:servicepage2.get(j).getDxtz());
                    row.createCell(8).setCellValue(servicepage2.get(j).getDhhf()==null?0:servicepage2.get(j).getDhhf());
                    row.createCell(9).setCellValue(servicepage2.get(j).getSmhf()==null?0:servicepage2.get(j).getSmhf());
                }
            }
        }
        try {
            ExcelUtilQ.exportExcel(response, workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
