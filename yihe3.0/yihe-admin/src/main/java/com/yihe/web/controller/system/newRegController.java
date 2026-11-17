package com.yihe.web.controller.system;

import com.yihe.system.domain.*;
import com.yihe.system.mapper.*;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.service.YhUserAdminInfoService;
import com.yihe.system.service.YhUserMakeCardService;
import com.yihe.system.service.newRegService;
import com.yihe.system.utils.Result;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/newRegController")
public class newRegController {
    @Resource
    newRegService newRegService;
    @Resource
    YhUserReginfoMapper yhUserReginfoMapper;
    @Resource
    YhUserAdminInfoMapper yhUserAdminInfoMapper;

    @Resource
    NewInterventionRecordFormForHighRiskPopulationMapper newInterventionRecordFormForHighRiskPopulationMapper;

    @Resource
    YhUserMakeCardService yhUserMakeCardService;
    @Resource
    YhUserMakeCardMapper yhUserMakeCardMapper;

    @Resource
    NewMedicalExaminationRecordsMapper newMedicalExaminationRecordsMapper;

    @Autowired
    YhOrgTreeService yhOrgTreeService;

    @Autowired
    private YhOrgTreeService YhOrgTreeService;

    @Autowired
    private com.yihe.system.service.YhUserAdminInfoService YhUserAdminInfoService;


    @RequestMapping(value = "/insertNewReg")
    @ResponseBody
    public Result insertNewReg(newRegWithBLOBs newRegWithBLOBs) {
        //根据userCode查询健管师code
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        //newRegWithBLOBs.setResponsibleHealthManagerCode(yh.getUserCode());
        newRegWithBLOBs.setArchivist(yh.getUserCode());
        newRegWithBLOBs.setInsertDate(new Date());

        int insertCount=newRegService.insertCount(newRegWithBLOBs);
        if (insertCount>0) return Result.error("已存在该用户档案");

        int count=newRegService.insertNewReg(newRegWithBLOBs);
        return count>0?Result.ok("添加成功"):Result.error("添加失败");

    }


    @RequestMapping(value = "/selectNewReg")
    @ResponseBody
    public Result selectNewReg(newRegWithBLOBs newRegWithBLOBs){
        return Result.ok(newRegService.selectNewReg(newRegWithBLOBs));
    }

    @RequestMapping(value = "/selectRegInfo")
    @ResponseBody
    public Result selectRegInfo(newRegWithBLOBs newRegWithBLOBs,@RequestParam(required = false, defaultValue = "1") int page,@RequestParam(required = false, defaultValue = "5") int limit){
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        if (yh == null) return Result.error("登录已失效，请重新登录");
        // 旧表单传来的空格不得被当作一个实际的健管师编码或单位名。
        if (newRegWithBLOBs.getResponsibleHealthManagerCode() != null) newRegWithBLOBs.setResponsibleHealthManagerCode(newRegWithBLOBs.getResponsibleHealthManagerCode().trim());
        if (newRegWithBLOBs.getWorkUnit() != null) newRegWithBLOBs.setWorkUnit(newRegWithBLOBs.getWorkUnit().trim());
        page = Math.max(1, page);
        limit = Math.max(1, Math.min(100, limit));
        if (newRegWithBLOBs.getTimes() != null && !newRegWithBLOBs.getTimes().trim().isEmpty()) {
            String[] range = newRegWithBLOBs.getTimes().trim().split("\\s+-\\s+", -1);
            if (range.length != 2) return Result.error("建档时间范围格式不正确");
            try {
                java.time.LocalDateTime from = java.time.LocalDateTime.parse(range[0], java.time.format.DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").withResolverStyle(java.time.format.ResolverStyle.STRICT));
                java.time.LocalDateTime to = java.time.LocalDateTime.parse(range[1], java.time.format.DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").withResolverStyle(java.time.format.ResolverStyle.STRICT));
                if (from.isAfter(to)) return Result.error("建档起始时间不能晚于结束时间");
            } catch (java.time.DateTimeException e) { return Result.error("建档时间范围格式不正确"); }
            newRegWithBLOBs.setBeginTime(range[0]);
            newRegWithBLOBs.setEndTime(range[1]);
        }
        newRegWithBLOBs.setDoctor(yh.getUserCode());
        List<newRegWithBLOBs> newRegWithBLOBs1= newRegService.selectRegInfo(newRegWithBLOBs,page,limit);




        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newRegService.selectRegCount(newRegWithBLOBs));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newRegWithBLOBs1);
        return Result.ok(rsMap);
    }

    @RequestMapping(value = "/updateReg")
    @ResponseBody
    public Result updateReg(newRegWithBLOBs newRegWithBLOBs){
        newRegWithBLOBs.setInsertDate(new Date());
        return Result.ok(newRegService.updateReg(newRegWithBLOBs));
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(newRegWithBLOBs newRegWithBLOBs){
        return Result.ok(newRegService.delete(newRegWithBLOBs));
    }

    //查询本地管理的档案
    @RequestMapping(value = "/queryLocallyManagedArchives")
    @ResponseBody
    public Result queryLocallyManagedArchives(newRegWithBLOBs newRegWithBLOBs,@RequestParam(required = false, defaultValue = "1") int page,@RequestParam(required = false, defaultValue = "10") int limit){
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        newRegWithBLOBs.setResponsibleHealthManagerCode(yh.getUserCode());
        newRegWithBLOBs.setBegin((page-1)*limit);
        newRegWithBLOBs.setLimit(limit);
        Map<String, Object> rsMap = new HashMap<String, Object>();


        String selectChildnodeMax = YhOrgTreeService.selectChildnodeMax("0");

        String userCode = yh.getUserCode();
        if(null==userCode) {
            return null;
        }
        //根据userCode查询YhUserAdminInfo表
        YhUserAdminInfoExample example=new YhUserAdminInfoExample();
        YhUserAdminInfoExample.Criteria createCriteria = example.createCriteria();
        createCriteria.andUserCodeEqualTo(userCode);
        List<YhUserAdminInfo> selectByExample = YhUserAdminInfoService.selectByExample(example);
        if(null==selectByExample||selectByExample.size()==0) {
            return null;
        }
        YhUserAdminInfo yhUserAdminInfo = selectByExample.get(0);
        Integer id = yhUserAdminInfo.getId();

        List<YhOrgTreeVojo> upmsPermissionst=YhOrgTreeService.selectalltreeOrg2(String.valueOf(id));
        List<String> orgCodes=upmsPermissionst.stream().map(YhOrgTreeVojo::getId).collect(Collectors.toList());
        newRegWithBLOBs.setLists(orgCodes);


        rsMap.put("count", newRegService.queryLocallyManagedArchivesCount(newRegWithBLOBs));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newRegService.queryLocallyManagedArchives(newRegWithBLOBs) );
        return Result.ok(rsMap);
    }


    @RequestMapping(value = "/searchForDoctors")
    @ResponseBody
    public Result queryLocallyManagedArchives(){

        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yhUserReginfo = (YhUserReginfo) subject.getPrincipal();
        String orgCode=yhUserReginfo.getOrgCode();

        return Result.ok(yhUserAdminInfoMapper.selectAll(orgCode));
    }



    @RequestMapping(value = "/selectArchives")
    @ResponseBody
    public Result selectArchives(){
        return Result.ok(newMedicalExaminationRecordsMapper.selectArchives());
    }


    //查询人数

    @RequestMapping(value = "/selectCount")
    @ResponseBody
    public Result selectCount(){
        Map<String,Integer> countMap=new HashMap<>();
        Integer count1=yhUserAdminInfoMapper.selectAllCount();
        Integer count2=yhUserAdminInfoMapper.selectAllCountToday();
        countMap.put("count1",count1);
        countMap.put("count2",count2);
        countMap.put("count3",yhUserAdminInfoMapper.selectAllCountNM());

        Integer count4=yhUserAdminInfoMapper.selectAllCountNM();
        Integer count5=yhUserAdminInfoMapper.selectAllCountTodayNM();
        countMap.put("count4",count4);
        countMap.put("count5",count5);
        countMap.put("count6",yhUserAdminInfoMapper.selectAllCountMoNM());
        return Result.ok(countMap);
    }

    @RequestMapping(value = "/interventionProportion")
    @ResponseBody
    public Result interventionProportion(){
        Map<String,Integer> countMap=new HashMap<>();
        countMap.put("count1",newInterventionRecordFormForHighRiskPopulationMapper.selectCountALl());
        countMap.put("count2",newInterventionRecordFormForHighRiskPopulationMapper.selectCountALlBySex(1));
        countMap.put("count3",newInterventionRecordFormForHighRiskPopulationMapper.selectCountALlBySex(2));
        return Result.ok(countMap);
    }


    @RequestMapping(value = "/proportionPsychologicalIntervention")
    @ResponseBody
    public Result proportionPsychologicalIntervention(){
        Map<String,Integer> countMap=new HashMap<>();
        countMap.put("count1",newInterventionRecordFormForHighRiskPopulationMapper.selectAnxI());
        countMap.put("count2",newInterventionRecordFormForHighRiskPopulationMapper.selectOb());
        countMap.put("count3",newInterventionRecordFormForHighRiskPopulationMapper.selectNd());

        countMap.put("count4",newInterventionRecordFormForHighRiskPopulationMapper.selectSI());
        countMap.put("count5",newInterventionRecordFormForHighRiskPopulationMapper.selectII());
        countMap.put("count6",newInterventionRecordFormForHighRiskPopulationMapper.selectOP());

        countMap.put("count7",newInterventionRecordFormForHighRiskPopulationMapper.selectEP());

        return Result.ok(countMap);
    }
    @RequestMapping(value = "/findDoctorAllInfo")
    @ResponseBody
    public Result findDoctorAllInfo(){
        return Result.ok( yhUserMakeCardService.findDoctorAllInfo());
    }

    @RequestMapping(value = "/numberEvaluations")
    @ResponseBody
    public Result numberEvaluations(){
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        int day = yhUserMakeCardMapper.findAssessTotalByToDayNum(userCode);
        int week = yhUserMakeCardMapper.findAssessTotalByWeek(userCode);
        int mouth = yhUserMakeCardMapper.findAssessTotalByMouth(userCode);
        int year = yhUserMakeCardMapper.findAssessTotalByYear(userCode);

        Map<String,Object> map=new HashMap<>();
        map.put("day",day);
        map.put("week",week);
        map.put("mouth",mouth);
        map.put("year",year);
        List<YhUserReginfo> yhUserReginfos=yhUserReginfoMapper.selectLastInfo(userCode);
        map.put("yhUserReginfos",yhUserReginfos);


        return Result.ok(map);
    }

    @RequestMapping(value = "/personalInformation")
    @ResponseBody
    public Result personalInformation(){
        List<newRegWithBLOBs> newRegWithBLOBs=newRegService.getOrderList();

        return Result.ok(newRegWithBLOBs);
    }

    @RequestMapping(value = "/exportXls", method = RequestMethod.GET)
    @ResponseBody
    public void exportXls(HttpServletResponse response, @RequestParam List<Integer> u){

        List<newRegWithBLOBs> newRegWithBLOBsList= newRegService.selectByList(u);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("个人基本信息");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = "个人基本信息" + ".xls";// 设置要导出的文件的名字
        int rowNum = 1;
        // headers表示excel表中第一行的表头
        String[] headers = {
                "序号",
                "用户编码",
                "姓名",
                "性别",
                "出生日期",
                "年龄",
                "婚姻",
                "身份证号",
                "工作单位",
                "最近体检",
                "最近评估",
                "最近干预",
                "一般健康人群",
                "高危人群",
                "患病人群",
                "责任健管师",
                "建档日期",
                "本人电话"
        };
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
        for (newRegWithBLOBs item : newRegWithBLOBsList) {
            // 创建excel中的行
            HSSFRow row1 = sheet.createRow(rowNum);

            // 在表中存放查询到的数据放入对应的列
            row1.createCell(0).setCellValue(rowNum); // 序号
            row1.createCell(1).setCellValue(item.getUserCode()); // 用户编码
            row1.createCell(2).setCellValue(item.getName()); // 姓名
            row1.createCell(3).setCellValue(item.getSex() == 1 ? "男" : "女"); // 性别
            row1.createCell(4).setCellValue(item.getBirth() == null ? "" : sdf.format(item.getBirth())); // 出生日期
            row1.createCell(5).setCellValue(item.getAge()); // 年龄
            row1.createCell(6).setCellValue(item.getMarriage()); // 婚姻
            row1.createCell(7).setCellValue(item.getIdCard()); // 身份证号
            row1.createCell(8).setCellValue(item.getWorkUnit()); // 工作单位

            row1.createCell(9).setCellValue(
                    item.getEvaluationTime() == null || !isSameYear(parseDate(item.getEvaluationTime()))
                            ? "今年未体检" : sdf.format(parseDate(item.getEvaluationTime()))
            );

            row1.createCell(10).setCellValue(
                    item.getQtime() == null || !isSameQuarter(parseDate(item.getQtime())) || !isSameYear(parseDate(item.getQtime()))
                            ? "本季度未评估" : sdf.format(parseDate(item.getQtime()))
            );

            row1.createCell(11).setCellValue(
                    item.getInterventionTime() == null || !isInCurrentYear(parseDate(item.getInterventionTime()))
                            ? "今年未干预" : sdf.format(parseDate(item.getInterventionTime()))
            );

            // 一般健康人群：如果 dfNames 和 skNames 都是 "否"，则为 "是"，否则为 "否"
            row1.createCell(12).setCellValue(
                    (item.getDfNames() != null && item.getSkNames() != null &&
                            item.getDfNames().equals("否") && item.getSkNames().equals("否")) ? "是" : "否"
            );

        // 高危人群：如果 dfNames 是 "是"，则为 "是"，否则为 "否"
                    row1.createCell(13).setCellValue(
                            item.getDfNames() != null && item.getDfNames().equals("是") ? "是" : "否"
                    );

        // 患病人群：如果 skNames 是 "是"，则为 "是"，否则为 "否"
                    row1.createCell(14).setCellValue(
                            item.getSkNames() != null && item.getSkNames().equals("是") ? "是" : "否"
                    );







            row1.createCell(15).setCellValue(item.getDname()); // 责任健管师

            row1.createCell(16).setCellValue(item.getInsertDate() == null ? "" : sdf.format(item.getInsertDate())); // 建档日期

            row1.createCell(17).setCellValue(item.getPhone()); // 本人电话


            rowNum++;
        }
        for (int k = 0; k < newRegWithBLOBsList.size(); k++) {
            sheet.autoSizeColumn(k);
        }
        setSizeColumn(sheet, newRegWithBLOBsList.size());

        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date parseDate(String dateStr) {
        try {
            if (dateStr != null && !dateStr.isEmpty()) {
                return sdf.parse(dateStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // 判断是否是今年
    private boolean isSameYear(Date date) {
        if (date == null) return false;
        int currentYear = new Date().getYear();
        return date.getYear() == currentYear;
    }

    private boolean isSameQuarter(Date date) {
        if (date == null) return false;
        int currentQuarter = (new Date().getMonth() + 3) / 3;
        int quarter = (date.getMonth() + 3) / 3;
        return currentQuarter == quarter;
    }

    private boolean isInCurrentYear(Date date) {
        if (date == null) return false;
        int currentYear = new Date().getYear();
        return date.getYear() == currentYear;
    }



    static void setSizeColumn(HSSFSheet sheet, int size) {
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
}
