package com.yihe.web.controller.system;

import com.yihe.system.domain.NewAnnualOccupationalHealthPromotionTrainingList;
import com.yihe.system.domain.NewOccupationalHealthManagementArchives;
import com.yihe.system.domain.NewUnitOccupationalHealthSurveillanceManagementArchives;
import com.yihe.system.service.unitOccupationalHealthSurveillanceManagementArchivesFormService;
import com.yihe.system.utils.Result;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
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
@RequestMapping("/unitOccupationalHealthSurveillanceManagementArchivesFormController")
public class unitOccupationalHealthSurveillanceManagementArchivesFormController {
    @Resource
    unitOccupationalHealthSurveillanceManagementArchivesFormService unitOccupationalHealthSurveillanceManagementArchivesFormService;
    @RequestMapping(value = "/insetNew")
    @ResponseBody
    public Result insetNew(NewUnitOccupationalHealthSurveillanceManagementArchives newUnitOccupationalHealthSurveillanceManagementArchives) {
        //根据userCode查询健管师code
        newUnitOccupationalHealthSurveillanceManagementArchives.setInsertTime(new Date());
        int count=unitOccupationalHealthSurveillanceManagementArchivesFormService.insetNew(newUnitOccupationalHealthSurveillanceManagementArchives);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/updateNew")
    @ResponseBody
    public Result updateNew(NewUnitOccupationalHealthSurveillanceManagementArchives newUnitOccupationalHealthSurveillanceManagementArchives) {
        //根据userCode查询健管师code
        int count=unitOccupationalHealthSurveillanceManagementArchivesFormService.updateNew(newUnitOccupationalHealthSurveillanceManagementArchives);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewUnitOccupationalHealthSurveillanceManagementArchives newUnitOccupationalHealthSurveillanceManagementArchives) {
        return Result.ok(unitOccupationalHealthSurveillanceManagementArchivesFormService.selectById(newUnitOccupationalHealthSurveillanceManagementArchives));
    }

    @RequestMapping(value = "/exportXls", method = RequestMethod.GET)
    @ResponseBody
    public void exportXls(HttpServletResponse response, NewUnitOccupationalHealthSurveillanceManagementArchives newUnitOccupationalHealthSurveillanceManagementArchives){
        newUnitOccupationalHealthSurveillanceManagementArchives.setBegin(0);
        newUnitOccupationalHealthSurveillanceManagementArchives.setLimit(10000);
        List<NewUnitOccupationalHealthSurveillanceManagementArchives> items= unitOccupationalHealthSurveillanceManagementArchivesFormService.selectByPage(newUnitOccupationalHealthSurveillanceManagementArchives);

        String[] headers = {
                "序号",
                "档案编号",
                "档案年度",
                "归档时间"
        };
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("单位职业健康监护管理档案");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = "单位职业健康监护管理档案记录" + ".xls";// 设置要导出的文件的名字
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
        for (NewUnitOccupationalHealthSurveillanceManagementArchives item : items) {
            // 创建excel中的行
            HSSFRow row1 = sheet.createRow(rowNum);

            // 在表中存放查询到的数据放入对应的列
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(item.getArchiveNumber());
            row1.createCell(2).setCellValue(item.getArchiveYear() == null ? "" : item.getArchiveYear().substring(0,4));
            row1.createCell(3).setCellValue(item.getArchiveTime() == null ? "" : sdf.format(item.getArchiveTime()));
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
    public Result selectRegInfo(NewUnitOccupationalHealthSurveillanceManagementArchives newUnitOccupationalHealthSurveillanceManagementArchives, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        newUnitOccupationalHealthSurveillanceManagementArchives.setBegin((page-1)*limit);
        newUnitOccupationalHealthSurveillanceManagementArchives.setLimit(limit);
        List<NewUnitOccupationalHealthSurveillanceManagementArchives> newThreeSimultaneous1= unitOccupationalHealthSurveillanceManagementArchivesFormService.selectByPage(newUnitOccupationalHealthSurveillanceManagementArchives);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", unitOccupationalHealthSurveillanceManagementArchivesFormService.selectByPageCount(newUnitOccupationalHealthSurveillanceManagementArchives));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newThreeSimultaneous1);
        return Result.ok(rsMap);
    }



    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public Result deleteById(NewUnitOccupationalHealthSurveillanceManagementArchives newUnitOccupationalHealthSurveillanceManagementArchives) {
        return Result.ok(unitOccupationalHealthSurveillanceManagementArchivesFormService.deleteById(newUnitOccupationalHealthSurveillanceManagementArchives));
    }
}
