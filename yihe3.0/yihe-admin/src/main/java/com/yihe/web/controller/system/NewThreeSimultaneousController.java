package com.yihe.web.controller.system;

import com.yihe.system.domain.NewMedicalExaminationRecords;
import com.yihe.system.domain.NewThreeSimultaneous;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.NewThreeSimultaneousService;
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
@RequestMapping("/NewThreeSimultaneousController")
public class NewThreeSimultaneousController {
    @Resource
    NewThreeSimultaneousService newThreeSimultaneousService;

    @RequestMapping(value = "/insetNew")
    @ResponseBody
    public Result insetNew(NewThreeSimultaneous newThreeSimultaneous) {
        //根据userCode查询健管师code
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newThreeSimultaneous.setStaffingPersonnel(yh.getUserCode());
        newThreeSimultaneous.setInsertTime(new Date());
        int count=newThreeSimultaneousService.insetNew(newThreeSimultaneous);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/updateNew")
    @ResponseBody
    public Result updateNew(NewThreeSimultaneous newThreeSimultaneous) {
        //根据userCode查询健管师code

        int count=newThreeSimultaneousService.updateNew(newThreeSimultaneous);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewThreeSimultaneous newThreeSimultaneous){
        return Result.ok(newThreeSimultaneousService.selectById(newThreeSimultaneous));
    }


    @RequestMapping(value = "/exportXls", method = RequestMethod.GET)
    @ResponseBody
    public void exportXls(HttpServletResponse response, NewThreeSimultaneous newThreeSimultaneous){
        newThreeSimultaneous.setBegin(0);
        newThreeSimultaneous.setLimit(10000);
        List<NewThreeSimultaneous> items= newThreeSimultaneousService.selectByPage(newThreeSimultaneous);

        String[] headers = {
                "序号",
                "项目类型",
                "项目名称",
                "档案编号",
                "档案年度",
                "归档时间"
        };
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("建设项目职业卫生“三同时”档案");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = "建设项目职业卫生“三同时”档案记录" + ".xls";// 设置要导出的文件的名字
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
        for (NewThreeSimultaneous item : items) {
            // 创建excel中的行
            HSSFRow row1 = sheet.createRow(rowNum);

            // 在表中存放查询到的数据放入对应的列
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(item.getStage());
            row1.createCell(2).setCellValue(item.getEntryName());
            row1.createCell(3).setCellValue(item.getArchiveNumber() == null ? "" : item.getArchiveNumber());
            row1.createCell(4).setCellValue(item.getArchiveYear() == null ? "" : sdf.format(item.getArchiveYear()).substring(0,4));
            row1.createCell(5).setCellValue(item.getArchiveTime() == null ? "" : sdf.format(item.getArchiveTime()));
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
    public Result selectRegInfo(NewThreeSimultaneous newThreeSimultaneous, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        newThreeSimultaneous.setBegin((page-1)*limit);
        newThreeSimultaneous.setLimit(limit);
        List<NewThreeSimultaneous> newThreeSimultaneous1= newThreeSimultaneousService.selectByPage(newThreeSimultaneous);
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newThreeSimultaneousService.selectByPageCount(newThreeSimultaneous));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newThreeSimultaneous1);
        return Result.ok(rsMap);
    }

    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public Result deleteById(NewThreeSimultaneous newThreeSimultaneous){
        return Result.ok(newThreeSimultaneousService.deleteById(newThreeSimultaneous));
    }


}
