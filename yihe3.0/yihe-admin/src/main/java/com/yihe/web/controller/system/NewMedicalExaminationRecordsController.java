package com.yihe.web.controller.system;

import com.yihe.system.domain.NewMedicalExaminationRecords;
import com.yihe.system.domain.NewType2DiabetesPatientServiceRecord;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.service.NewMedicalExaminationRecordsService;
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
@RequestMapping("/NewMedicalExaminationRecordsController")
public class NewMedicalExaminationRecordsController {
    @Resource
    NewMedicalExaminationRecordsService newMedicalExaminationRecordsService;



    @RequestMapping(value = "/exportXls", method = RequestMethod.GET)
    @ResponseBody
    public void exportXls(HttpServletResponse response,NewMedicalExaminationRecords newMedicalExaminationRecords){
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newMedicalExaminationRecords.setDoctor(yh.getUserCode());
        if(newMedicalExaminationRecords.getV18()!=null&&(newMedicalExaminationRecords.getV18().equals(0)||newMedicalExaminationRecords.getV18().equals("0")||newMedicalExaminationRecords.getV18().trim().equals(""))){
            newMedicalExaminationRecords.setV18(null);
        }
        List<NewMedicalExaminationRecords> items= newMedicalExaminationRecordsService.selectByPage(newMedicalExaminationRecords,1,10000);

        String[] headers = {
                "序号",
                "年度",
                "用户编码",
                "姓名",
                "性别",
                "年龄",
                "部门",
                "班组",
                "电话",
                "身高",
                "体重",
                "体重指数",
                "血压",
                "心率",
                "血氧",
                "建档时间",
                "责任健管师"
        };
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(newMedicalExaminationRecords.getStage());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = newMedicalExaminationRecords.getStage()+"记录" + ".xls";// 设置要导出的文件的名字
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
        for (NewMedicalExaminationRecords item : items) {
            // 创建excel中的行
            HSSFRow row1 = sheet.createRow(rowNum);

            // 在表中存放查询到的数据放入对应的列
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(item.getV16().length()<4 ? "" :item.getV16().substring(0,4));
            row1.createCell(2).setCellValue(item.getUserCode()== null ? "" :item.getUserCode());
            row1.createCell(3).setCellValue(item.getName() == null ? "" : item.getName());
            row1.createCell(4).setCellValue(item.getGender() == null ? "" : item.getGender()==1?"男":"女");
            row1.createCell(5).setCellValue(item.getAge() == null ? "" : String.valueOf(item.getAge()));
            row1.createCell(6).setCellValue(item.getMarriage() == null ? "" : item.getMarriage());
            row1.createCell(7).setCellValue(item.getOccupation() == null ? "" : item.getOccupation());
            row1.createCell(8).setCellValue(item.getPhone() == null ? "" : item.getPhone());
            row1.createCell(9).setCellValue(item.getHeight() == null ? "" : item.getHeight());
            row1.createCell(10).setCellValue(item.getWeight() == null ? "" : item.getWeight());
            row1.createCell(11).setCellValue(item.getHeartRate() == null ? "" : item.getHeartRate());
            row1.createCell(12).setCellValue(item.getBloodPressure() == null ? "" : item.getBloodPressure());
            row1.createCell(13).setCellValue(item.getHeartRate() == null ? "" : item.getHeartRate());
            row1.createCell(14).setCellValue(item.getBloodOxygen() == null ? "" : item.getBloodOxygen());
            row1.createCell(15).setCellValue(item.getTime() == null ? "" : item.getTime());
            row1.createCell(16).setCellValue(item.getDname() == null ? "" : item.getDname());
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
    public Result selectRegInfo(NewMedicalExaminationRecords newMedicalExaminationRecords, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newMedicalExaminationRecords.setDoctor(yh.getUserCode());
        if(newMedicalExaminationRecords.getV18()!=null&&(newMedicalExaminationRecords.getV18().equals(0)||newMedicalExaminationRecords.getV18().equals("0")||newMedicalExaminationRecords.getV18().trim().equals(""))){
            newMedicalExaminationRecords.setV18(null);
        }
        List<NewMedicalExaminationRecords> newMedicalExaminationRecords1= newMedicalExaminationRecordsService.selectByPage(newMedicalExaminationRecords,page,limit);


        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newMedicalExaminationRecordsService.selectByPageCount(newMedicalExaminationRecords));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newMedicalExaminationRecords1);
        return Result.ok(rsMap);
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewMedicalExaminationRecords newMedicalExaminationRecords){
        return Result.ok(newMedicalExaminationRecordsService.selectById(newMedicalExaminationRecords));
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public Result insert(NewMedicalExaminationRecords newMedicalExaminationRecords){
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newMedicalExaminationRecords.setTime(dfs.format(new Date()));
        newMedicalExaminationRecords.setV17(dfs.format(new Date()));
        return Result.ok(newMedicalExaminationRecordsService.insert(newMedicalExaminationRecords));
    }

    @RequestMapping(value = "/updateById")
    @ResponseBody
    public Result updateById(NewMedicalExaminationRecords newMedicalExaminationRecords){
        return Result.ok(newMedicalExaminationRecordsService.updateById(newMedicalExaminationRecords));
    }


    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public Result deleteById(NewMedicalExaminationRecords newMedicalExaminationRecords){
        return Result.ok(newMedicalExaminationRecordsService.deleteById(newMedicalExaminationRecords));
    }




}
