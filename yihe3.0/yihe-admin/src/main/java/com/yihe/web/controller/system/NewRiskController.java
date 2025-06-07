package com.yihe.web.controller.system;

import com.yihe.system.domain.NewRisk;
import com.yihe.system.domain.NewThreeSimultaneous;
import com.yihe.system.domain.NewUnitOccupationalHealthSurveillanceManagementArchives;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.NewRiskSerVice;
import com.yihe.system.utils.Result;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/NewRiskController")
public class NewRiskController {

    @Resource
    NewRiskSerVice newRiskSerVice;

    @RequestMapping(value = "/insetNew")
    @ResponseBody
    public Result insetNew(NewRisk newRisk) {
        newRisk.setInserTime(new Date());
        int count=newRiskSerVice.insetNew(newRisk);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/updateNew")
    @ResponseBody
    public Result updateNew(NewRisk newRisk) {
        //根据userCode查询健管师code

        int count=newRiskSerVice.updateNew(newRisk);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewRisk newRisk){
        return Result.ok(newRiskSerVice.selectById(newRisk));
    }

    @RequestMapping(value = "/exportXls", method = RequestMethod.GET)
    @ResponseBody
    public void exportXls(HttpServletResponse response, NewRisk newRisk){
        newRisk.setBegin(0);
        newRisk.setLimit(10000);
        List<NewRisk> items= newRiskSerVice.selectByPage(newRisk);

        String[] headers = {
                "序号",
                "姓名",
                "性别",
                "评估次数",
                "最新评估日期",
                "高风险"
        };
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("健康风险评估报告");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = "健康风险评估报告记录" + ".xls";// 设置要导出的文件的名字
        int rowNum = 1;
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(36);
        // 设置列宽度
        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            // 设置为居中加粗
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());// 设置背景色
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            style.setBorderBottom(BorderStyle.THIN); // 下边框
            style.setBorderLeft(BorderStyle.THIN);// 左边框
            style.setBorderTop(BorderStyle.THIN);// 上边框
            style.setBorderRight(BorderStyle.THIN);// 右边框

            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style);

        }
        for (NewRisk item : items) {
            // 创建excel中的行
            HSSFRow row1 = sheet.createRow(rowNum);

            // 在表中存放查询到的数据放入对应的列
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(item.getName());
            row1.createCell(2).setCellValue(item.getGender());
            row1.createCell(3).setCellValue(item.getRiskFrequency());
            row1.createCell(4).setCellValue(item.getRiskTime()!=null? sdf.format(item.getRiskTime()) : "");
            row1.createCell(5).setCellValue(item.getHighrisk());

            rowNum++;
        }
        for (int k = 0; k < items.size(); k++) {
            sheet.autoSizeColumn(k);
        }
        newRegController.setSizeColumn(sheet, items.size());

        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @RequestMapping(value = "/selectPage")
    @ResponseBody
    public Result selectRegInfo(NewRisk newRisk, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        newRisk.setBegin((page-1)*limit);
        newRisk.setLimit(limit);
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newRisk.setDoctor(yh.getUserCode());
        List<NewRisk> newThreeSimultaneous1= newRiskSerVice.selectByPage(newRisk);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newRiskSerVice.selectByPageCount(newRisk));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newThreeSimultaneous1);
        return Result.ok(rsMap);
    }

}
