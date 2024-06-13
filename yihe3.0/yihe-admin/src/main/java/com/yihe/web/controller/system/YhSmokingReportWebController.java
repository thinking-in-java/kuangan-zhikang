package com.yihe.web.controller.system;

import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.yihe.common.annotation.Log;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.common.core.page.TableDataInfo;
import com.yihe.common.enums.BusinessType;
import com.yihe.common.utils.DateUtils;
import com.yihe.common.utils.DownloadPDFUtils;
import com.yihe.common.utils.poi.ExcelUtil;
import com.yihe.system.domain.*;
import com.yihe.system.service.*;
import com.yihe.system.utils.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 尼古丁评估结果Controller
 *
 * @author dengyongbo
 * @date 2022-05-24
 */
@Controller
@RequestMapping("/dataDetection/report")
public class YhSmokingReportWebController extends BaseController {
    @Autowired
    TcmConstitutionIdentificationService tcmConstitutionIdentificationService;
    private String prefix = "dataDetection/report";
    @Autowired
    private IYhSmokingReportWebService yhSmokingReportService;

    @Autowired
    private YhUserReginfoService yhUserReginfoService;
    @Autowired
    private YhPeriodComparisonService userService;

    @Autowired
    private YhAssessResultService yhAssessResultService;

    @Autowired
    private IYhQuestionSmokingWebService yhQuestionSmokingWebService;

    @Autowired
    private YhUserMakeCardService yhUserMakeCardService;
    @Autowired
    private YhOrgTreeService yhOrgTreeService;

    @RequiresPermissions("dataDetection:report:view")
    @GetMapping()
    public String report() {
        return prefix + "/report";
    }

    /**
     * 查询尼古丁评估结果列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(YhSmokingReportWeb yhSmokingReport) {
        List<YhSmokingReportWeb> list = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        String id = tcmConstitutionIdentificationService.selectDoctorIdByUserCode(userCode);
        if (StringUtil.isEmpty(id)) {
            return getDataTable(list);
        }
        yhSmokingReport.setDoctor(id);
        if (StringUtil.isNotEmpty(yhSmokingReport.getStrTime())) {
            String[] split = yhSmokingReport.getStrTime().split(" - ");
            yhSmokingReport.setStrTime(split[0]);
            yhSmokingReport.setEndTime(split[1]);
        }


        startPage1();
        list = yhSmokingReportService.selectYhSmokingReportListByWeb(yhSmokingReport);
        return getDataTable(list);
    }

    @GetMapping("/listByUser")
    @ResponseBody
    public TableDataInfo listByUser(YhSmokingReportWeb yhSmokingReport) {
        startPage();
        List<YhSmokingReportWeb> yhSmokingReportWebs = yhSmokingReportService.selectYhSmokingReportList(yhSmokingReport);
        return getDataTable(yhSmokingReportWebs);
    }

    @GetMapping("/getAssessXls")
    public void getAssessXls(Long id, HttpServletResponse response) throws IOException {
        YhSmokingReportWeb result = yhSmokingReportService.selectYhSmokingReportById(id);
        String name = "尼古丁依赖";
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建标题
        HSSFSheet sheet = workbook.createSheet(name + "问卷");
        StringBuffer sb = new StringBuffer("用户编码,姓名,性别,评估年龄,单位,填写时间");
        List<YhQuestionSmokingWeb> yhQuestionSmokingWebs = yhQuestionSmokingWebService.selectYhQuestionSmokingWebList(new YhQuestionSmokingWeb());
        for (YhQuestionSmokingWeb topic : yhQuestionSmokingWebs) {
            sb.append("," + topic.getQuestionTitle());
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
        row1.createCell(2).setCellValue(yhUserReginfo.getGender() == 1 ? "男" : "女");
        row1.createCell(3).setCellValue(DateUtils.getAgeByBirth(yhUserReginfo.getBirthDate()));
        row1.createCell(4).setCellValue(orgName);
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        row1.createCell(5).setCellValue(myFormatter.format(result.getQtime()));

        String[] answer = result.getSmokingReportQueselect().split(";");

        for (int i = 0; i < answer.length; i++) {
            //选择的
            String answerInfo = answer[i];
            String choose1 = yhQuestionSmokingWebs.get(i).getChoose1();
            String choose1Value = yhQuestionSmokingWebs.get(i).getChoose1Value();
            String choose2 = yhQuestionSmokingWebs.get(i).getChoose2();
            String choose2Value = yhQuestionSmokingWebs.get(i).getChoose2Value();
            String choose3 = yhQuestionSmokingWebs.get(i).getChoose3();
            String choose3Value = yhQuestionSmokingWebs.get(i).getChoose3Value();
            String choose4 = yhQuestionSmokingWebs.get(i).getChoose4();
            String choose4Value = yhQuestionSmokingWebs.get(i).getChoose4Value();

            if (choose1Value.equals(answerInfo)) {
                row1.createCell(i + 6).setCellValue(choose1);
                continue;
            }
            if (choose2Value.equals(answerInfo)) {
                row1.createCell(i + 6).setCellValue(choose2);
                continue;
            }
            if (choose3Value.equals(answerInfo)) {
                row1.createCell(i + 6).setCellValue(choose3);
                continue;
            }
            if (choose4Value.equals(answerInfo)) {
                row1.createCell(i + 6).setCellValue(choose4);
                continue;
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

    @GetMapping("/exportPdf")
    public void exportPdf(Long id, HttpServletResponse response) throws IOException, DocumentException {
        YhSmokingReportWeb record = yhSmokingReportService.selectYhSmokingReportById(id);
        YhUserReginfo userInfo = yhUserReginfoService.selectByPrimaryKey(record.getUserCode());
        String name = "尼古丁依赖";
        // 设置要导出的文件的名字
        String fileName = record.getUserCode() + "_" + userInfo.getName() +
                "（" + DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, record.getQtime()) + "）_" + name + "报告.pdf";
        Document document = DownloadPDFUtils.getDocument(response, fileName);
        // 2-初始化 pdf输出对象 PdfWriter
        PdfWriter pdfWriter = PdfWriter.getInstance(document, response.getOutputStream());
        // 为pdf添加属性信息
        document.addAuthor("管理员");
        document.addTitle(name + "报告");
        // 打开 Document
        document.open();
        //添加标题
        DownloadPDFUtils.setFileTitle(document, name + "报告", userService.getrepotimg());
        //添加用户信息
        String[] tableName = {"用户编码", "姓名", "性别", "评估时年龄", "评估日期", "评估结果"};
        String[] userInfos = {record.getUserCode(), userInfo.getName(), userInfo.getGender() == 1 ? "男" : "女", String.valueOf(DateUtils.getAgeByBirth(userInfo.getBirthDate())),
                DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, record.getQtime()), record.getSmokingReportLevel()};
        DownloadPDFUtils.setUserInfo(document, tableName, userInfos);
        document.add(new Paragraph("此次的" + name + "得分：" + record.getSmokingReportScore() + "分",
                DownloadPDFUtils.getChineseFont(12, Font.BOLD, new BaseColor(56, 164, 255))));

        List<YhAssessConclusion> conclusionWebs = yhAssessResultService.findBydivisor(name);
        YhAssessConclusion conclusion = null;
        int score = 0;
        String[] states = new String[conclusionWebs.size()];
        String[] scores = new String[conclusionWebs.size() + 1];
        for (int i = 0; i < conclusionWebs.size(); i++) {
            YhAssessConclusion conclusionWeb = conclusionWebs.get(i);
            states[i] = conclusionWeb.getState();
            if (conclusionWeb.getScoreMin() != null) {
                scores[i] = conclusionWeb.getScoreMin().toString();
                if (i == conclusionWebs.size() - 1) {
                    scores[i + 1] = conclusionWeb.getScoreMax().toString();
                }
            }
            if (conclusionWeb.getState().equals(record.getSmokingReportLevel())) {
                conclusion = conclusionWebs.get(i);
                score = (int) Double.parseDouble(conclusionWeb.getLeftwidth().substring(0, conclusionWeb.getLeftwidth().length() - 1));
            }
        }
        //添加评估结果图形
        String url = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        Image image = Image.getInstance(url + "static/image/ren.png");
        DownloadPDFUtils.setGradeIcon(document, score, image);
        BaseColor[] colors = null;
        int[] colorswidth = null;
        int[] scorewidth = null;
        colors = new BaseColor[]{new BaseColor(24, 144, 255), new BaseColor(67, 217, 184), new BaseColor(236, 93, 81)};
        colorswidth = new int[]{33, 33, 34};
        scorewidth = new int[]{10, 18, 10, 23, 10, 19, 10};
        DownloadPDFUtils.setGradeIcon(document, colorswidth, states, colors);
        if (scores[0] != null && !scores[0].equals("")) {
            DownloadPDFUtils.setGradeIcon(document, scorewidth, scores);
        }
        DownloadPDFUtils.setSecondaryTitel(document, record.getSmokingReportLevel());
        DownloadPDFUtils.setParagraph(document, conclusion.getConclusion());
        DownloadPDFUtils.setStatement(pdfWriter);
        // 6-关闭 Document
        document.close();

    }

    /**
     * 导出尼古丁评估结果列表
     */
    @RequiresPermissions("dataDetection:report:export")
    @Log(title = "尼古丁评估结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YhSmokingReportWeb yhSmokingReport) {
        List<YhSmokingReportWeb> list = yhSmokingReportService.selectYhSmokingReportList(yhSmokingReport);
        ExcelUtil<YhSmokingReportWeb> util = new ExcelUtil<YhSmokingReportWeb>(YhSmokingReportWeb.class);
        return util.exportExcel(list, "report");
    }

    /**
     * 新增尼古丁评估结果
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存尼古丁评估结果
     */
    @RequiresPermissions("dataDetection:report:add")
    @Log(title = "尼古丁评估结果", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(YhSmokingReportWeb yhSmokingReport) {
        return toAjax(yhSmokingReportService.insertYhSmokingReport(yhSmokingReport));
    }

    /**
     * 修改尼古丁评估结果
     */
    @GetMapping("/edit/{smokingReportId}")
    public String edit(@PathVariable("smokingReportId") Long smokingReportId, ModelMap mmap) {
        YhSmokingReportWeb yhSmokingReport = yhSmokingReportService.selectYhSmokingReportById(smokingReportId);
        mmap.put("yhSmokingReport", yhSmokingReport);
        return prefix + "/edit";
    }

    /**
     * 修改保存尼古丁评估结果
     */
    @RequiresPermissions("dataDetection:report:edit")
    @Log(title = "尼古丁评估结果", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(YhSmokingReportWeb yhSmokingReport) {
        return toAjax(yhSmokingReportService.updateYhSmokingReport(yhSmokingReport));
    }

    /**
     * 删除尼古丁评估结果
     */
    @RequiresPermissions("dataDetection:report:remove")
    @Log(title = "尼古丁评估结果", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(yhSmokingReportService.deleteYhSmokingReportByIds(ids));
    }
}
