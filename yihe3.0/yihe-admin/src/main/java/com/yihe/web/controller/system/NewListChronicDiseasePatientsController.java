package com.yihe.web.controller.system;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yihe.system.domain.NewListChronicDiseasePatients;
import com.yihe.system.domain.NewRisk;
import com.yihe.system.domain.NewUnitOccupationalHealthSurveillanceManagementArchives;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.NewListChronicDiseasePatientsService;
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
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/NewListChronicDiseasePatientsController")
public class NewListChronicDiseasePatientsController {

        @Resource
        NewListChronicDiseasePatientsService newListChronicDiseasePatientsService;

        @RequestMapping(value = "/insetNew")
        @ResponseBody
        public Result insetNew(NewListChronicDiseasePatients newListChronicDiseasePatients) {
            newListChronicDiseasePatients.setIsnertTime(new Date());
            Subject subject = SecurityUtils.getSubject();
            YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
            newListChronicDiseasePatients.setDoctor(yh.getUserCode());
            int count=newListChronicDiseasePatientsService.insetNew(newListChronicDiseasePatients);
            return count>0?Result.ok("添加成功"):Result.error("添加失败");
        }

        @RequestMapping(value = "/updateNew")
        @ResponseBody
        public Result updateNew(NewListChronicDiseasePatients newListChronicDiseasePatients) {
            //根据userCode查询健管师code

            int count=newListChronicDiseasePatientsService.updateNew(newListChronicDiseasePatients);
            return count>0?Result.ok("添加成功"):Result.error("添加失败");
        }

        @RequestMapping(value = "/selectById")
        @ResponseBody
        public Result selectById(NewListChronicDiseasePatients newListChronicDiseasePatients){
            return Result.ok(newListChronicDiseasePatientsService.selectById(newListChronicDiseasePatients));
        }


    @RequestMapping(value = "/exportXls", method = RequestMethod.GET)
    @ResponseBody
    public void exportXls(HttpServletResponse response, NewListChronicDiseasePatients newListChronicDiseasePatients){
        newListChronicDiseasePatients.setBegin(0);
        newListChronicDiseasePatients.setLimit(10000);
        List<NewListChronicDiseasePatients> items= newListChronicDiseasePatientsService.selectByPage(newListChronicDiseasePatients);
        String[] headers = {
                "序号",
                "姓名",
                "性别",
                "部门",
                "病症",
                "等级",
                "干预情况",
                "首次干预时间",
                "干预次数",
                "最新干预时间",
                "干预效果",
                "转诊情况"
        };
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("慢病患者一览表");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = "慢病患者一览表记录" + ".xls";// 设置要导出的文件的名字
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
        for (NewListChronicDiseasePatients item : items) {
            // 创建excel中的行
            HSSFRow row1 = sheet.createRow(rowNum);

            // 在表中存放查询到的数据放入对应的列
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(item.getName());

            String jsonData = item.getJsonData();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
            List<Map<String, Object>> rowDataArr = gson.fromJson(jsonData, listType);

            row1.createCell(2).setCellValue((rowDataArr != null && !rowDataArr.isEmpty() && rowDataArr.get(0).get("age") != null)
                    ? rowDataArr.get(0).get("age").toString()
                    : "--");
            row1.createCell(3).setCellValue((rowDataArr != null && !rowDataArr.isEmpty() && rowDataArr.get(0).get("gender") != null)
                    ? rowDataArr.get(0).get("gender").toString()
                    : "--");
            row1.createCell(4).setCellValue((rowDataArr != null && !rowDataArr.isEmpty() && rowDataArr.get(0).get("diseases") != null)
                    ? rowDataArr.get(0).get("diseases").toString()
                    : "--");
            row1.createCell(5).setCellValue((rowDataArr != null && !rowDataArr.isEmpty() && rowDataArr.get(0).get("level") != null)
                    ? rowDataArr.get(0).get("level").toString()
                    : "--");
            row1.createCell(6).setCellValue((rowDataArr != null && !rowDataArr.isEmpty() && rowDataArr.get(0).get("interventionStatus") != null)
                    ? rowDataArr.get(0).get("interventionStatus").toString()
                    : "--");
            row1.createCell(7).setCellValue((rowDataArr != null && !rowDataArr.isEmpty() && rowDataArr.get(0).get("firstInterventionDate") != null)
                    ? rowDataArr.get(0).get("firstInterventionDate").toString()
                    : "--");
            row1.createCell(8).setCellValue((rowDataArr != null && !rowDataArr.isEmpty() && rowDataArr.get(0).get("interventionTimes") != null)
                    ? rowDataArr.get(0).get("interventionTimes").toString()
                    : "--");
            row1.createCell(9).setCellValue((rowDataArr != null && !rowDataArr.isEmpty() && rowDataArr.get(0).get("lastInterventionDate") != null)
                    ? rowDataArr.get(0).get("lastInterventionDate").toString()
                    : "--");
            row1.createCell(10).setCellValue((rowDataArr != null && !rowDataArr.isEmpty() && rowDataArr.get(0).get("evaluation") != null)
                    ? rowDataArr.get(0).get("evaluation").toString()
                    : "--");
            row1.createCell(11).setCellValue((rowDataArr != null && !rowDataArr.isEmpty() && rowDataArr.get(0).get("transferStatus") != null)
                    ? rowDataArr.get(0).get("transferStatus").toString()
                    : "--");
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
        public Result selectRegInfo(NewListChronicDiseasePatients newListChronicDiseasePatients, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
            newListChronicDiseasePatients.setBegin((page-1)*limit);
            newListChronicDiseasePatients.setLimit(limit);
            Subject subject = SecurityUtils.getSubject();
            YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
            newListChronicDiseasePatients.setDoctor(yh.getUserCode());


            if (newListChronicDiseasePatients.getLists() != null && !newListChronicDiseasePatients.getLists().isEmpty()) {
                newListChronicDiseasePatients.setListSelect(Arrays.asList(newListChronicDiseasePatients.getLists().split(";")));
            }


            List<NewListChronicDiseasePatients> newThreeSimultaneous1= newListChronicDiseasePatientsService.selectByPage(newListChronicDiseasePatients);
            Map<String, Object> rsMap = new HashMap<String, Object>();
            rsMap.put("count", newListChronicDiseasePatientsService.selectByPageCount(newListChronicDiseasePatients));
            rsMap.put("code", 0);
            rsMap.put("msg", "ok");
            rsMap.put("data", newThreeSimultaneous1);
            return Result.ok(rsMap);
        }


}
