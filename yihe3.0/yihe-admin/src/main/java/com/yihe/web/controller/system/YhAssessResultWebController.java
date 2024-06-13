package com.yihe.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.common.utils.Arith;
import com.yihe.common.utils.DateUtils;
import com.yihe.common.utils.DownloadPDFUtils;
import com.yihe.common.utils.JFreeChartUtils;
import com.yihe.system.domain.*;
import com.yihe.system.service.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

@RestController
@RequestMapping("/assessResult")
public class YhAssessResultWebController {
    @Autowired
    private YhAssessResultService yhAssessResultService;
    @Autowired
    private YhAssessTopicService yhAssessTopicService;
    @Autowired
    private YhUserReginfoService yhUserReginfoService;
    @Autowired
    private YhUserMakeCardService yhUserMakeCardService;
    @Autowired
    private YhOrgTreeService yhOrgTreeService;
    @Autowired
    private YhPeriodComparisonService userService;

    /**
     * 查询筛选树
     * @return
     */
    @GetMapping("/findAssRes")
    public AjaxResult findAssRes(){
        return AjaxResult.success(yhAssessResultService.findAssRes());
    }

    /**
     * 查询心理评估列表
     * @param resord
     * @return
     */
    @GetMapping("/assByList")
    public AjaxResult assByList(YhAssessResultVojo resord){
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        resord.setDoctor(principal.getUserCode());
        List<Map<String, String>> mapList = new ArrayList<>();
        if(resord.getIntegral() != null && !"".equals(resord.getIntegral())){
            String[] assvalue = resord.getIntegral() .split(";");
            for (String s : assvalue) {
                String[] ass = s.split(",");
                Map<String, String> assname = new HashMap<>();
                assname.put("assid", ass[0]);
                assname.put("assstate", ass[1]);
                mapList.add(assname);
            }
        }
        resord.setIntegrals(mapList);
        PageInfo pageInfo = yhAssessResultService.findByList(resord);
        return AjaxResult.success(pageInfo);
    }

    /**
     * 查询最新评估结果
     * @param userCode
     * @return
     */
    @GetMapping("/getUserAssess")
    public AjaxResult getUserAssess(String userCode){
        List<Map<String, Object>> mapList = yhAssessResultService.findAssess();
        for (Map<String, Object> map : mapList) {
            YhAssessResult result = yhAssessResultService.findAssessInfo(userCode, Integer.parseInt(map.get("id").toString()));
            if(result != null){
                String time = calculateTime(result.getQtime());
                String color = getColor(result.getState());
                map.put("time", time);
                map.put("color", color);
                map.put("score", result.getScore()+"分");
                map.put("state", result.getState());
            }else {
                map.put("time", "--");
                map.put("color", "#e5e5e5");
                map.put("score", "--");
                map.put("state", "未评估");
            }

        }
        return AjaxResult.success(mapList);
    }

    /**
     * 查询评估结果列表
     * @param resord
     * @return
     */
    @GetMapping("/getUserAssList")
    public AjaxResult getUserAssList(YhAssessResultVojo resord){
        PageInfo pageInfo = yhAssessResultService.findUserAssList(resord);
        return AjaxResult.success(pageInfo);
    }

    /**
     * 心理系列评估问卷导出
     * @param response
     * @param id
     * @throws IOException
     */
    @GetMapping("/getAssessXls")
    public void getAssessXls(HttpServletResponse response,  Integer id) throws IOException {
        YhAssessResult result = yhAssessResultService.findById(id);
        String name = yhAssessResultService.findName(result.getAssess());
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建标题
        HSSFSheet sheet = workbook.createSheet(name+"问卷");
        List<YhAssessTopic> topics = yhAssessTopicService.findByAssess(name);
        StringBuffer sb = new StringBuffer("用户编码,姓名,性别,评估年龄,单位,填写时间");
        for (YhAssessTopic topic : topics) {
            sb.append(","+topic.getName());
        }
        // 创建sheet名
        String[] headers = sb.toString().split(",");
        // 在表中存放查询到的数据放入对应的列
        YhUserReginfo yhUserReginfo = yhUserReginfoService.selectByPrimaryKey(result.getUserCode());
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(result.getQtime());
        String fileName = result.getUserCode() + "_" + yhUserReginfo.getName() + "_" + time + "_" + name + "问卷.xls";// 设置要导出的文件的名字
        YhUserMakeCard userMakeCard = yhUserMakeCardService.selectOrgIdbyParam(result.getUserCode());
        String orgName = "";
        if (userMakeCard.getOrgId() != null) {
            orgName = yhOrgTreeService.selectFullOrgName(userMakeCard.getOrgId().toString(), orgName);
        }
        // 在excel表中添加表头
        HSSFRow row = sheet.createRow(0);
        // 设置行高
        row.setHeightInPoints(36);
        // 设置列宽度
        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        // 在excel表中添加表头
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
        // 创建excel中的行
        HSSFRow row1 = sheet.createRow(1);
        // 在表中存放查询到的数据放入对应的列
        row1.createCell(0).setCellValue(yhUserReginfo.getUserCode());
        row1.createCell(1).setCellValue(yhUserReginfo.getName());
        row1.createCell(2).setCellValue(result.getSex());
        row1.createCell(3).setCellValue(result.getAge());
        row1.createCell(4).setCellValue(orgName);
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        row1.createCell(5).setCellValue(myFormatter.format(result.getQtime()));

        String[] answer = result.getAnswer().substring(1,result.getAnswer().length()-1).split(",");
        for(int i = 6; i < headers.length; i++){
            List<YhAssessOption> options = topics.get(i-6).getOptions();
            for (YhAssessOption option : options) {
                if (option.getScore() == Integer.parseInt(answer[i-6])){
                    row1.createCell(i).setCellValue(option.getOption());
                    break;
                }
            }
        }

        // 必须在单元格设值以后进行
        // 设置为根据内容自动调整列宽
        for (int k = 0; k < headers.length; k++) {
            sheet.autoSizeColumn(k);
        }
        // 处理中文不能自动调整列宽的问题
        setSizeColumn(sheet, headers.length);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * 心理系列评估报告导出
     * @param id
     * @param response
     */
    @GetMapping("/exportPdf")
    public void exportPdf(Integer id,HttpServletResponse response) throws IOException, DocumentException {
        YhAssessResult record = yhAssessResultService.findById(id);
        YhUserReginfo userInfo = yhUserReginfoService.selectByPrimaryKey(record.getUserCode());
        String name = yhAssessResultService.findName(record.getAssess());
        // 设置要导出的文件的名字
        String fileName = record.getUserCode() + "_" + userInfo.getName() +
                "（" + DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, record.getQtime()) + "）_"+name+"报告.pdf";
        Document document = DownloadPDFUtils.getDocument(response, fileName);
        // 2-初始化 pdf输出对象 PdfWriter
        PdfWriter pdfWriter = PdfWriter.getInstance(document, response.getOutputStream());
        // 为pdf添加属性信息
        document.addAuthor("管理员");
        document.addTitle(name+"报告");
        // 打开 Document
        document.open();
        //添加标题
        DownloadPDFUtils.setFileTitle(document, name+"报告", userService.getrepotimg());
        //添加用户信息
        String[] tableName = {"用户编码", "姓名", "性别", "评估时年龄", "评估日期", "评估结果"};
        String[] userInfos = {record.getUserCode(), userInfo.getName(), record.getSex(), record.getAge().toString(),
                DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, record.getQtime()), record.getState()};
        DownloadPDFUtils.setUserInfo(document, tableName, userInfos);
        if (record.getAssess() == 7){
            PdfPTable table = new PdfPTable(2);
            table.setWidths(new int[]{1, 1});// 单元格宽度
            table.setWidthPercentage(100);// 表格的宽度百分比
            PdfPCell cell = new PdfPCell(new Paragraph("此次性格评估量表得分：" + record.getScore() + "分",
                    DownloadPDFUtils.getChineseFont(10, Font.BOLD,new BaseColor(56, 164, 255))));
            cell.setFixedHeight(20f);//固定高度
            cell.setBorder(Rectangle.NO_BORDER);//无边框
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("L量表得分：" + record.getScoreL() + "分",
                    DownloadPDFUtils.getChineseFont(10, Font.BOLD,new BaseColor(56, 164, 255))));
            cell.setFixedHeight(20f);//固定高度
            cell.setBorder(Rectangle.NO_BORDER);//无边框
            table.addCell(cell);
            table.setSpacingAfter(5f);
            table.setSpacingBefore(5f);
            document.add(table);
            //查询L量表评估文案
            String scaleText = "您此次L量表评分为"+ record.getScoreL() + "分,"+yhAssessResultService.findScoreL(record.getScoreL());
            DownloadPDFUtils.setParagraph(document, scaleText);
        }else{
            document.add(new Paragraph("此次的"+name+"得分：" + record.getScore() + "分",
                    DownloadPDFUtils.getChineseFont(12, Font.BOLD, new BaseColor(56, 164, 255))));
        }
        List<YhAssessConclusion> conclusionWebs = yhAssessResultService.findBydivisor(name);
        YhAssessConclusion conclusion = null;
        int score = 0;
        String[] states = new String[conclusionWebs.size()];
        String[] scores = new String[conclusionWebs.size()+1];
        for (int i = 0; i < conclusionWebs.size(); i++) {
            YhAssessConclusion conclusionWeb = conclusionWebs.get(i);
            states[i] = conclusionWeb.getState();
            if(conclusionWeb.getScoreMin() != null){
                scores[i] = conclusionWeb.getScoreMin().toString();
                if(i == conclusionWebs.size()-1){
                    scores[i+1] = conclusionWeb.getScoreMax().toString();
                }
            }
            if(conclusionWeb.getState().equals(record.getState())){
                conclusion = conclusionWebs.get(i);
                score = (int)Double.parseDouble(conclusionWeb.getLeftwidth().substring(0,conclusionWeb.getLeftwidth().length()-1));
            }
        }
        //添加评估结果图形
        String url = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        Image image = Image.getInstance(url + "static/image/ren.png");
        DownloadPDFUtils.setGradeIcon(document, score, image);
        BaseColor[] colors = null;
        int[] colorswidth = null;
        int[] scorewidth = null;
        if(conclusionWebs.size() == 3){
            colors = new BaseColor[]{new BaseColor(24,144,255), new BaseColor(67,217,184),  new BaseColor(236,93,81)};
            colorswidth = new int[]{33, 33, 34};
            scorewidth = new int[]{10, 18, 10, 23, 10, 19, 10};
        }else if(conclusionWebs.size() == 4){
            colors = new BaseColor[]{new BaseColor(24,144,255), new BaseColor(67,217,184),
                    new BaseColor(255,141,141), new BaseColor(236,93,81)};
            colorswidth = new int[]{25, 25, 25, 25};
            scorewidth = new int[]{10, 10, 10, 15, 10, 15, 10, 10, 10};
        }else if(conclusionWebs.size() == 5){
            colors = new BaseColor[]{new BaseColor(155,220,253), new BaseColor(133,194,249),
                    new BaseColor(87,170,239), new BaseColor(87,156,231), new BaseColor(15,134,187)};
            colorswidth = new int[]{20, 20, 20, 20, 20};
            scorewidth = new int[]{6, 11, 6, 14, 6, 14, 6, 14, 6, 11, 6};
        }else{
            colors = new BaseColor[]{new BaseColor(236,93,81), new BaseColor(24,144,255)};
            colorswidth = new int[]{50, 50};
        }
        DownloadPDFUtils.setGradeIcon(document, colorswidth, states, colors);
        if(record.getAssess() == 5){
            String path = url + "static/image/" + record.getUserCode() + DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, record.getQtime()) + ".png";
            File outFile = new File(path);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            FileOutputStream out = new FileOutputStream(path);
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            String[] answer = record.getAnswer().substring(1,record.getAnswer().length()-1).split(",");
            List<YhAssessConclusion> divisors = yhAssessResultService.findBydivisor(name+"(因子)");
            for (int i=1; i < divisors.size(); i++) {
                YhAssessConclusion divisor = divisors.get(i);
                dataset.addValue(0, "参考值", divisor.getState());
                String[] divr = divisor.getSuggest().split(",");
                double divscore = 0;
                for (String s : divr) {
                    divscore += Integer.parseInt(answer[Integer.parseInt(s)-1]);
                }
                divscore = Arith.div(divscore, divr.length, 0);
                dataset.addValue((int) Arith.round(divscore,0) + 1, "我的得分", divisor.getState());
            }
            JFreeChartUtils.saveAsFile(out, 500, 400, dataset, 6);
            Image cellimg = Image.getInstance(path);
            cellimg.scaleToFit(300, 300);// 大小
            cellimg.setAlignment(Image.ALIGN_CENTER);
            document.add(cellimg);
            outFile.delete();
        }
        if(record.getAssess() == 4){
            String path = url + "static/image/" + record.getUserCode() + DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, record.getQtime()) + ".png";
            File outFile = new File(path);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            FileOutputStream out = new FileOutputStream(path);
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            String[] answer = record.getAnswer().substring(1,record.getAnswer().length()-1).split(",");
            List<YhAssessConclusion> divisors = yhAssessResultService.findBydivisor(name+"(因子)");
            List<String> contents=new ArrayList<>();
            contents.add("因子");
            contents.add("得分");
            contents.add("说明");
            for (int i=1; i < divisors.size(); i++) {
                YhAssessConclusion divisor = divisors.get(i);
                dataset.addValue(1, "参考值", divisor.getState());
                String[] divr = divisor.getSuggest().split(",");
                double divscore = 0;
                for (String s : divr) {
                    divscore += Integer.parseInt(answer[Integer.parseInt(s)-1]);
                }
                divscore = Arith.div(divscore, divr.length, 2);
                dataset.addValue((int) Arith.round(divscore,0) + 1, "我的得分", divisor.getState());
                contents.add(divisor.getState());
                contents.add(String.valueOf(divscore));
                contents.add(divisor.getConclusion());
            }
            JFreeChartUtils.saveAsFile(out, 500, 400, dataset, 5);
            Image cellimg = Image.getInstance(path);
            cellimg.scaleToFit(300, 300);// 大小
            cellimg.setAlignment(Image.ALIGN_CENTER);
            document.add(cellimg);
            outFile.delete();
            document.add(new Paragraph(conclusion.getConclusion(), DownloadPDFUtils.getChineseFont(12, Font.NORMAL, new BaseColor(190,190,190))));
            DownloadPDFUtils.setPsychologyTable(document, contents);
            if(record.getState().equals("阳性")){
                DownloadPDFUtils.setSecondaryTitel(document, "建议");
                String[] suggest = conclusion.getSuggest().split("/");
                //建议
                for (int i = 0; i < suggest.length; i++)DownloadPDFUtils.setParagraph(document, suggest[i]);
            }
        }else{
            if(scores[0] != null && !scores[0].equals("")){
                DownloadPDFUtils.setGradeIcon(document, scorewidth, scores);
            }
            DownloadPDFUtils.setSecondaryTitel(document, record.getState());
            DownloadPDFUtils.setParagraph(document, conclusion.getConclusion());
            DownloadPDFUtils.setSecondaryTitel(document, "建议");
            String[] suggest = conclusion.getSuggest().split("/");
            //建议
            for (int i = 0; i < suggest.length; i++)DownloadPDFUtils.setParagraph(document, suggest[i]);
        }
        DownloadPDFUtils.setStatement(pdfWriter);
        // 6-关闭 Document
        document.close();
    }

    private void setSizeColumn(HSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                // 当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }

    }

    private String calculateTime(Date time) {
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 指定时间格式
        long reset = time.getTime(); // 获取指定时间的毫秒数
        long dateDiff = nowTime - reset;
        if (dateDiff < 0) {
            msg = "输入的时间不对";
        } else {
            long dateTemp1 = dateDiff / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60; // 分钟
            long dateTemp3 = dateTemp2 / 60; // 小时
            long dateTemp4 = dateTemp3 / 24; // 天数
            long dateTemp5 = dateTemp4 / 30; // 月数
            long dateTemp6 = dateTemp5 / 12; // 年数
            if (dateTemp6 > 0) {
                msg = dateTemp6 + "年前";
            } else if (dateTemp5 > 0) {
                msg = dateTemp5 + "个月前";
            } else if (dateTemp4 > 0) {
                msg = dateTemp4 + "天前";
            } else if (dateTemp3 > 0) {
                msg = dateTemp3 + "小时前";
            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";
            } else if (dateTemp1 > 0) {
                msg = "不久前";
            }
        }
        return msg;
    }

    private String getColor(String state){
        if (state.indexOf("无") != -1 || state.indexOf("低") != -1 || state.equals("阴性") || state.equals("正常")){
            return "#1890ff";
        }else if (state.indexOf("轻") != -1 || state.equals("适中")){
            return "#fcc3c3";
        }else if (state.equals("中度") || state.equals("较高") || state.equals("橙色预警")){
            return "#ff8d8d";
        }else if (state.equals("B型")){
            return "#9bdcfd";
        }else if (state.equals("中间偏B型")){
            return "#85c2f9";
        }else if (state.equals("AB型")){
            return "#57aaef";
        }else if (state.equals("中间偏A型")){
            return "#579ce7";
        }else if (state.equals("A型")){
            return "#0f86bb";
        }else{
            return "#fc5656";
        }
    }
}
