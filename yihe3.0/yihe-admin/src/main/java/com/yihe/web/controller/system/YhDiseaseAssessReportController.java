package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.utils.StringUtils;
import com.yihe.common.utils.file.FileUtils;
import com.yihe.system.domain.*;
import com.yihe.system.domain.YhUserAdminInfoExample.Criteria;
import com.yihe.system.service.*;
import com.yihe.system.service.impl.YhDiseaseAssessTotalReportServiceImpl;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import com.yihe.web.controller.tool.HtmlToPdf;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 评估报告结果 Created by dyb on 2020/3/18.
 */
@Controller
@RequestMapping("/manage/YhDiseaseAssessReport")
@Api(value = "YhDiseaseAssessReport控制器", description = "YhDiseaseAssessReport管理")
public class YhDiseaseAssessReportController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhDiseaseAssessReportController.class);

    @Autowired
    YhDiseaseAssessReportService yhDiseaseAssessReportService;
    @Autowired
    private YhUserApiService yhUserApiService;

    @Autowired
    YhUserAdminInfoService yhUserAdminInfoService;

    @Autowired
    YhUserMakeCardService yhUserMakeCardService;
    @Autowired
    YhDiseaseCategoryService yhDiseaseCategoryService;
    @Autowired
    YhUserEnrolinfoService yhUserEnrolinfoService;

    @Autowired
    YhDiseaseAssessTotalReportService yhDiseaseAssessTotalReportService;

    @Autowired
    YhDicAssessmentScreenService YhDicAssessmentScreenService;

    @Autowired
    YhDiseaseRiskResultService yhDiseaseRiskResultService;

    @Autowired
    YhDiseaseAssessTotalReportServiceImpl YhDiseaseAssessTotalReportServiceImpl;


    @Autowired
    YhDiseaseAssessReportService YhDiseaseAssessReportService;

    @RequestMapping(value = "/gaoxueyaDataInfo2", method = RequestMethod.GET)
    @ResponseBody
    public Result gaoxueyaDataInfo2(String rs, String sex, String usercode, HttpServletResponse response, HttpServletRequest request) {
        //String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/template/";
        //String path =this.getClass().getResource("/static/template/").getPath().substring(1);
        YhUserReginfo selectUserRegInfoByUserCode = yhUserApiService.selectUserRegInfoByUserCode(usercode);
        String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date().getTime());
        String fileName = time + selectUserRegInfoByUserCode.getName() + "评估报告.pdf";
        System.out.println(HtmlToPdf.TEMP_DIR_PATH + fileName);
        String s = "http://localhost/manage/assessmentReport/info2?rs=" + rs + "&sex=" + sex;
        boolean convert = HtmlToPdf.convert(s.replace(" ", ""), HtmlToPdf.TEMP_DIR_PATH + fileName);//本地
        if (convert) {
            return Result.ok(fileName);
        } else {
            return Result.error();
        }


    }

    @RequestMapping(value = "/getxls", method = RequestMethod.GET)
    @ResponseBody
    public void getxls(HttpServletResponse response) throws IOException {
        String url = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        FileInputStream fi=new FileInputStream(url+"/static/template/import健康风险评估问卷-批量导入 自动评估模板.xls");
        HSSFWorkbook wb=new HSSFWorkbook(fi);
        HSSFSheet sheet= wb.getSheetAt(0); //sheet 从0开始
        int rowNum = 5; //sheet.getLastRowNum() + 1;//取得最后一行的行号
        List<Map<String,String>> list = yhDiseaseAssessReportService.findxls();
        for (Map<String,String> map : list){
            HSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            //格式化问卷答案
            JSONObject jsonObject = JSONObject.parseObject(map.get("user_check_answer"));
            JSONObject person = jsonObject.getJSONObject("person");
            // 在表中存放查询到的数据放入对应的列
            row.createCell(0).setCellValue(person.getString("name"));
            row.createCell(1).setCellValue(person.getString("sex").equals("1") ? "男" : "女");
            row.createCell(2).setCellValue(map.get("mobile"));
            row.createCell(3).setCellValue(map.get("birth_date"));
            row.createCell(4).setCellValue(jsonObject.getString("height"));
            row.createCell(5).setCellValue(jsonObject.getString("weight"));
            row.createCell(6).setCellValue(jsonObject.getString("waist"));
            row.createCell(7).setCellValue(person.getString("hipline"));
            row.createCell(8).setCellValue(person.getString("education_levels"));
            row.createCell(9).setCellValue(jsonObject.getString("activity"));
            //您的居住地
            if (person.get("jzd").toString().equals("1")) {
                if (person.get("jzhj").toString().equals("1")) {
                    row.createCell(10).setCellValue(3);
                } else {
                    row.createCell(10).setCellValue(4);
                }
            } else {
                if (person.get("jzhj").toString().equals("1")) {
                    row.createCell(10).setCellValue(1);
                } else {
                    row.createCell(10).setCellValue(2);
                }
            }
            //所住地区空气质量
            row.createCell(11).setCellValue(jsonObject.getString("pm25"));
            //您的居住环境
            row.createCell(12).setCellValue(person.getString("living_envir"));
            //近期（2年）是否新房装修
            row.createCell(13).setCellValue(jsonObject.getString("decorate_house"));
            //有油烟接触史（长期厨房做菜或为厨师职业）
            row.createCell(14).setCellValue(jsonObject.getJSONObject("lampblack_contact").getString("disFlag"));
            //长期从事化工、染料、橡胶及印刷等职业
            row.createCell(15).setCellValue(jsonObject.getString("chemical_occupation"));
            //粉尘接触史（如：矿工、环卫工人等或长期接触过汽油等有害物质）
            row.createCell(16).setCellValue(jsonObject.getJSONObject("dust_exposure_his").getString("disFlag"));
            //石棉接触史（长期接触过石棉）
            row.createCell(17).setCellValue(jsonObject.getJSONObject("asbestos_contact_his").getString("disFlag"));
            //您平均每天吃多少新鲜蔬菜
            row.createCell(18).setCellValue(jsonObject.getString("food_vegetables"));
            //您平均每天吃多少新鲜水果
            row.createCell(19).setCellValue(jsonObject.getString("food_fruits"));
            //您平均每天喝几杯牛奶或酸奶
            row.createCell(20).setCellValue(jsonObject.getString("food_milk"));
            //您平均每天吃多少奶制品
            row.createCell(21).setCellValue(jsonObject.getString("food_dairy"));
            //您通常每周吃几次油炸食品
            row.createCell(22).setCellValue(jsonObject.getString("food_fired"));
            //您通常每周吃几次点心或甜食
            row.createCell(23).setCellValue(jsonObject.getString("food_sweet"));
            //您通常每周喝几次含糖饮料
            row.createCell(24).setCellValue(jsonObject.getString("food_sugary"));
            //您通常每周吃几次咸菜或腌渍食品
            row.createCell(25).setCellValue(jsonObject.getString("food_pickled"));
            //您每周吃虾、蟹、蛤蜊等海鲜次数是
            row.createCell(26).setCellValue(jsonObject.getString("seafood_cnt"));
            //您每天饮茶的次数是
            row.createCell(27).setCellValue(jsonObject.getString("drink_tea_cnt"));
            //您是否吸烟
            row.createCell(28).setCellValue(jsonObject.getString("smoke_status"));
            if(jsonObject.getString("smoke_status").equals("1")){
                //开始吸烟时的年龄（岁）
                row.createCell(29).setCellValue(jsonObject.getString("smoke_begin_age"));
                //平均每日吸烟量（支）
                row.createCell(30).setCellValue(jsonObject.getString("smoke_amount"));
            }else if(jsonObject.getString("smoke_status").equals("3")){
                //开始戒烟的年龄(岁)
                row.createCell(31).setCellValue(jsonObject.getString("smoke_end_age"));
            }
            //无论在家还是工作场所，您经常吸二手烟吗
            row.createCell(32).setCellValue(jsonObject.getString("smoke_sencod"));
            //您是否喝酒
            row.createCell(33).setCellValue(jsonObject.getString("food_drink"));
            if(jsonObject.getString("food_drink").equals("1")){
                //您每周喝几次酒?
                row.createCell(34).setCellValue(jsonObject.getString("food_drink_times"));
                //您平均每次喝多少啤酒?
                row.createCell(35).setCellValue(jsonObject.getString("food_drink_beer"));
            }
            //您在过去3个月中是否参加过运动锻炼
            row.createCell(36).setCellValue(jsonObject.getString("sport_in"));
            if (jsonObject.getString("sport_in").equals("1")){
                //一般情况下您参加运动锻炼的频率是
                row.createCell(37).setCellValue(jsonObject.getString("sport_freq"));
                //一般情况下您每次锻炼的持续时间是
                row.createCell(38).setCellValue(jsonObject.getString("sport_time"));
            }
            //您的睡眠时间是
            row.createCell(39).setCellValue(jsonObject.getString("bedtime"));
            //您是否患过高血压
            row.createCell(40).setCellValue(jsonObject.getJSONObject("hbp_his").getString("disFlag"));
            //您是否患过脑卒中
            row.createCell(41).setCellValue(jsonObject.getJSONObject("stroke_his").getString("disFlag"));
            //您是否患过冠心病
            row.createCell(42).setCellValue(jsonObject.getJSONObject("chd_his").getString("disFlag"));
            //您是否患过严重心律失常
            row.createCell(43).setCellValue(jsonObject.getJSONObject("mi_his").getString("disFlag"));
            //您是否患过不稳定心绞痛
            row.createCell(44).setCellValue(jsonObject.getJSONObject("angina_his").getString("disFlag"));
            //您是否患过动脉硬化/狭窄/闭塞
            row.createCell(45).setCellValue(jsonObject.getString("arterial_status"));
            //您是否患过未控制的有症状的心律衰竭
            row.createCell(46).setCellValue(jsonObject.getJSONObject("ushf_his").getString("disFlag"));
            //您是否患过左心室肥大
            row.createCell(47).setCellValue(jsonObject.getJSONObject("lvd_his").getString("disFlag"));
            //您是否患过房颤
            row.createCell(48).setCellValue(jsonObject.getJSONObject("af_his").getString("disFlag"));
            //您是否患过其他急性心脏事件
            row.createCell(49).setCellValue(jsonObject.getJSONObject("ace_his").getString("disFlag"));
            //您是否患过糖尿病（1或2型）
            row.createCell(50).setCellValue(jsonObject.getJSONObject("diab2_his").getString("disFlag"));
            //您是否患过痛风急性发作期
            row.createCell(51).setCellValue(jsonObject.getJSONObject("gae_his").getString("disFlag"));
            //您是否患过痛风非急性发作期
            row.createCell(52).setCellValue(jsonObject.getJSONObject("gna_his").getString("disFlag"));
            //您是否患过慢性支气管炎
            row.createCell(53).setCellValue(jsonObject.getJSONObject("bronch_his").getString("disFlag"));
            //您是否患过肺气肿
            row.createCell(54).setCellValue(jsonObject.getJSONObject("emphy_his").getString("disFlag"));
            //您是否患过肺结核
            row.createCell(55).setCellValue(jsonObject.getJSONObject("decline_his").getString("disFlag"));
            //您是否患过肺癌
            row.createCell(56).setCellValue(jsonObject.getJSONObject("lc_his").getString("disFlag"));
            //您是否患过骨质疏松症
            row.createCell(57).setCellValue(jsonObject.getJSONObject("op_his").getString("disFlag"));
            //您是否患过既往骨折
            row.createCell(58).setCellValue(jsonObject.getJSONObject("fracture_his").getString("disFlag"));
            //您是否患过类风湿关节炎
            row.createCell(59).setCellValue(jsonObject.getJSONObject("ra_his").getString("disFlag"));
            //您是否患过其他限制运动的疾病（如关节炎、足部溃疡、肌肉韧带损伤等）
            row.createCell(60).setCellValue(jsonObject.getJSONObject("sport_his").getString("disFlag"));
            //您是否患过引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、慢性营养不良等）
            row.createCell(61).setCellValue(jsonObject.getJSONObject("secondary_op_his").getString("disFlag"));
            if(person.getString("sex").equals("1")){
                //您是否患过前列腺炎或肥大
                row.createCell(62).setCellValue(jsonObject.getJSONObject("prostate_disease").getString("disFlag"));
                //您是否患过前列腺癌
                row.createCell(63).setCellValue(jsonObject.getJSONObject("pc_his").getString("disFlag"));
                //您是否患过尿路感染
                row.createCell(64).setCellValue(jsonObject.getString("urinary_tract_infection"));
                //您是否患过尿频、尿痛、尿不尽、排尿困难等症状
                row.createCell(65).setCellValue(jsonObject.getString("urine_symptom"));
                //您是否患过尿血或尿路结石
                row.createCell(66).setCellValue(jsonObject.getString("hematuresis"));
            }
            //您是否患过乳腺癌（男性也有患病几率）
            row.createCell(67).setCellValue(jsonObject.getJSONObject("bc_his").getString("disFlag"));
            if(person.getString("sex").equals("2")){
                //您是否长期口服避孕药
                row.createCell(68).setCellValue(jsonObject.getString("med_use_pill"));
                //您是否长期口服避孕药
                row.createCell(69).setCellValue(jsonObject.getString("breast_plastic_surgery"));
                //您是否曾有子宫、卵巢和输卵管等的手术
                row.createCell(70).setCellValue(jsonObject.getString("gynecology_operation"));
            }
            //您是否服用降压药
            row.createCell(71).setCellValue(jsonObject.getString("med_hbp"));
            //您是否注射肾上腺皮质激素
            row.createCell(72).setCellValue(jsonObject.getString("med_ach"));
            //您的父亲是否患有糖尿病
            row.createCell(73).setCellValue(jsonObject.getString("family_diab_f"));
            //您的母亲是否患有糖尿病
            row.createCell(74).setCellValue(jsonObject.getString("family_diab_m"));
            //您的兄弟姐妹是否患有糖尿病
            row.createCell(75).setCellValue(jsonObject.getString("family_diab_bs"));
            //您的子女是否患有糖尿病
            row.createCell(76).setCellValue(jsonObject.getString("family_diab_ch"));
            //您的父亲是否患高血压
            row.createCell(77).setCellValue(jsonObject.getString("family_hbp_f"));
            //您的母亲是否患高血压
            row.createCell(78).setCellValue(jsonObject.getString("family_hbp_m"));
            //您的兄弟姐妹是否患高血压
            row.createCell(79).setCellValue(jsonObject.getString("family_hbp_bs"));
            //您的子女是否患高血压
            row.createCell(80).setCellValue(jsonObject.getString("family_hbp_ch"));
            //您的父亲是否患冠心病/心绞痛/心肌梗塞
            row.createCell(81).setCellValue(jsonObject.getString("family_chd_f"));
            //您的母亲是否患冠心病/心绞痛/心肌梗塞
            row.createCell(82).setCellValue(jsonObject.getString("family_chd_m"));
            //您的兄弟姐妹是否患冠心病/心绞痛/心肌梗塞
            row.createCell(83).setCellValue(jsonObject.getString("family_chd_bs"));
            //您的子女是否患冠心病/心绞痛/心肌梗塞
            row.createCell(84).setCellValue(jsonObject.getString("family_chd_ch"));
            //您的父亲是否患脑卒中
            row.createCell(85).setCellValue(jsonObject.getString("family_stroke_f"));
            //您的母亲是否患脑卒中
            row.createCell(86).setCellValue(jsonObject.getString("family_stroke_m"));
            //您的兄弟姐妹是否患脑卒中
            row.createCell(87).setCellValue(jsonObject.getString("family_stroke_bs"));
            //您的子女是否患脑卒中
            row.createCell(88).setCellValue(jsonObject.getString("family_stroke_ch"));
            //您的父亲是否患肺癌
            row.createCell(89).setCellValue(jsonObject.getString("family_lung_f"));
            //您的母亲是否患肺癌
            row.createCell(90).setCellValue(jsonObject.getString("family_lung_m"));
            //您的兄弟姐妹是否患肺癌
            row.createCell(91).setCellValue(jsonObject.getString("family_lung_bs"));
            //您的子女是否患肺癌
            row.createCell(92).setCellValue(jsonObject.getString("family_lung_ch"));
            //您的家族中父亲是否患前列腺癌
            row.createCell(93).setCellValue(jsonObject.getString("family_pro_f"));
            //您的家族中兄弟是否患前列腺癌
            row.createCell(94).setCellValue(jsonObject.getString("family_pro_b"));
            //您的家族中儿子是否患前列腺癌
            row.createCell(95).setCellValue(jsonObject.getString("family_pro_s"));
            //您的母亲是否患乳腺癌
            row.createCell(96).setCellValue(jsonObject.getString("family_breast_m"));
            //您的兄弟姐妹是否患乳腺癌
            row.createCell(97).setCellValue(jsonObject.getString("family_breast_s"));
            //您的子女是否患乳腺癌
            row.createCell(98).setCellValue(jsonObject.getString("family_breast_d"));
            //您的父亲是否患过痛风
            row.createCell(99).setCellValue(jsonObject.getString("family_gout_f"));
            //您的母亲是否患过痛风
            row.createCell(100).setCellValue(jsonObject.getString("family_gout_m"));
            //您的兄弟姐妹是否患过痛风
            row.createCell(101).setCellValue(jsonObject.getString("family_gout_bs"));
            //您的子女是否患过痛风
            row.createCell(102).setCellValue(jsonObject.getString("family_gout_ch"));
            //您的父亲是否患髋部骨折
            row.createCell(103).setCellValue(jsonObject.getString("family_hf_f"));
            //您的母亲是否患髋部骨折
            row.createCell(104).setCellValue(jsonObject.getString("family_hf_m"));
            //您的兄弟姐妹是否患髋部骨折
            row.createCell(105).setCellValue(jsonObject.getString("family_hf_bs"));
            //您的子女是否患髋部骨折
            row.createCell(106).setCellValue(jsonObject.getString("family_hf_ch"));
            if(person.getString("sex").equals("2")){
                //您是否生育过？
                row.createCell(107).setCellValue(jsonObject.getString("female_birth"));
                if (jsonObject.getString("female_birth").equals("1") || jsonObject.getString("female_birth").equals("2")){
                    //您生第一胎年龄是多大？
                    row.createCell(108).setCellValue(jsonObject.getString("female_tire"));
                    //您的孩子是母乳喂养吗？是的话，哺乳时间总共有多长时间（累计）
                    row.createCell(109).setCellValue(jsonObject.getString("female_breast"));
                }
                //您是否处于妊娠期
                row.createCell(110).setCellValue(jsonObject.getString("female_gestation"));
                //您第一次来月经的年龄是多大
                row.createCell(111).setCellValue(jsonObject.getString("female_menstruation"));
                //您的月经是否规律
                row.createCell(112).setCellValue(jsonObject.getString("female_menstruation_regular"));
                //您是否绝经
                row.createCell(113).setCellValue(jsonObject.getString("female_menopause"));
                if(jsonObject.getString("female_menopause").equals("1")){
                    //您的绝经年龄是多大
                    row.createCell(114).setCellValue(jsonObject.getString("female_menopause_age"));
                    //您绝经后体重上升超过5公斤吗
                    row.createCell(115).setCellValue(jsonObject.getString("weight_up"));
                    //您绝经后使用雌激素替代疗法的时长
                    if(jsonObject.getString("has_med_ert").equals("")){
                        row.createCell(116).setCellValue(jsonObject.getString("med_ert"));
                    }else row.createCell(116).setCellValue(3);
                }
            }
            //您的月收入
            row.createCell(117).setCellValue(jsonObject.getString("income"));
            //最近两周，您感到精神紧张，很难放松吗？
            row.createCell(118).setCellValue(jsonObject.getString("psy_nervous"));
            //总胆固醇（单位mmol/l）
            row.createCell(119).setCellValue(jsonObject.getString("cholesterol"));
            //甘油三酯（单位mmol/l）
            row.createCell(120).setCellValue(jsonObject.getString("tg"));
            //高密度脂蛋白胆固醇（单位mmol/l）
            row.createCell(121).setCellValue(jsonObject.getString("hdlc"));
            //低密度脂蛋白胆固醇（单位mmol/l）
            row.createCell(122).setCellValue(jsonObject.getString("ldlc"));
            //血尿酸（单位μmol/L）
            row.createCell(123).setCellValue(jsonObject.getString("bua"));
            //血肌酐（单位mmol/L）
            row.createCell(124).setCellValue(jsonObject.getString("serum_creatinine"));
            //空腹血糖（单位mmol/l）
            row.createCell(125).setCellValue(jsonObject.getString("fbg"));
            //收缩压（单位mmHg）
            row.createCell(126).setCellValue(jsonObject.getString("sbp"));
            //舒张压（单位mmHg）
            row.createCell(127).setCellValue(jsonObject.getString("dbp"));
            //血常规检查白细胞（单位/L）
            row.createCell(128).setCellValue(jsonObject.getString("wbc"));
            //骨密度检查指标-T值
            row.createCell(129).setCellValue(jsonObject.getString("t_score"));
            HSSFCellStyle style = wb.createCellStyle();
            //设置上下左右四个边框宽度
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            //设置上下左右四个边框颜色
            style.setTopBorderColor(HSSFColor.BLACK.index);
            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setLeftBorderColor(HSSFColor.BLACK.index);
            style.setRightBorderColor(HSSFColor.BLACK.index);
            for(int cellnum = 0; cellnum < row.getLastCellNum(); cellnum++){
                HSSFCell cell = row.getCell(cellnum);
                if (cell == null) {
                    cell = row.createCell(cellnum);
                    cell.setCellValue("");
                }
                cell.setCellStyle(style);
            }
            rowNum++;
        }
//        // 必须在单元格设值以后进行
//        // 设置为根据内容自动调整列宽
//        for (int k = 0; k < 130; k++) {
//            sheet.autoSizeColumn(k);
//            // 解决自动设置列宽中文失效的问题
//            sheet.setColumnWidth(k, sheet.getColumnWidth(k) * 17 / 10);
//        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("ceshi.xls", "utf-8"));
        response.flushBuffer();
        wb.write(response.getOutputStream());
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response, HttpServletRequest request) {
        //这里文件名称是通过参数传递过来的，要是不需要，直接可以写在这里。String fileName = "a.doc";
        //String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/template/";

        String path = HtmlToPdf.TEMP_DIR_PATH;
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {

            File file = new File(path, fileName);
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "iso8859-1"));
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            FileUtils.deleteFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("The file not found!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @RequestMapping(value = "/gaoxueyaDataInfo", method = RequestMethod.GET)
    @ResponseBody
    public Result gaoxueyaDataInfo(String rs) {
        String findQuestionCheckForCheckAnswerByrs = yhDiseaseAssessReportService.findQuestionCheckForCheckAnswerByrs(rs);
        YhDiseaseRiskResultAppVOJO findAssessResultInfo = yhDiseaseAssessReportService.findAssessResultInfo(rs);
        if (StringUtil.isEmpty(findQuestionCheckForCheckAnswerByrs) && null == findAssessResultInfo) {
            return Result.error();
        }
        JSONObject jsonObject = JSONObject.parseObject(findQuestionCheckForCheckAnswerByrs);
        YhUserAssessmentReportVOJO vojo = new YhUserAssessmentReportVOJO();
        //血糖
        String fbg = jsonObject.getString("fbg");
        vojo.setBs(fbg);
        //收缩压
        String sbp = jsonObject.getString("sbp");
        //舒张压
        String dbp = jsonObject.getString("dbp");
        if (StringUtil.isNotEmpty(sbp) && StringUtil.isNotEmpty(dbp)) {
            vojo.setBloodPressure(sbp + "\\" + dbp);
        }
        //腰围
        String waist = jsonObject.getString("waist");
        vojo.setTheWaist(waist);
        //身高
        String height = jsonObject.getString("height");
        //体重
        String weight = jsonObject.getString("weight");
        if (StringUtil.isNotEmpty(height) && StringUtil.isNotEmpty(weight)) {
            Double heightOf = Double.valueOf(height);
            Double weightOf = Double.valueOf(weight);
            Double bmi = weightOf / ((heightOf / 100) * (heightOf / 100));
            String format = String.format("%.1f", bmi);
            vojo.setBmi(format);
        }
        //总胆固醇
        String cholesterol = jsonObject.getString("cholesterol");
        vojo.setTc(cholesterol);
        //甘油三酯
        String tg = jsonObject.getString("tg");
        vojo.setTg(tg);
        //高密度脂蛋白
        String hdlc = jsonObject.getString("hdlc");
        vojo.setHdlc(hdlc);
        //低密度脂蛋白胆固醇
        String ldlc = jsonObject.getString("ldlc");
        vojo.setLdlc(ldlc);
        if (null != findAssessResultInfo) {
            //膳食结构
            String b4 = findAssessResultInfo.getB4();

            if (StringUtil.isNotEmpty(b4)) {
                JSONObject b4obj = JSONObject.parseObject(b4);
                //1: 低脂饮食0: 高脂饮食
                String b4Str = b4obj.getString("now_value");
                if (StringUtil.isNotEmpty(b4Str)) {
                    vojo.setB4(b4Str.equals("1") ? "低脂饮食" : "高脂饮食");
                }

            }

            //体力活动
            String sport = findAssessResultInfo.getSport();
            if (StringUtil.isNotEmpty(sport)) {
                JSONObject sportobj = JSONObject.parseObject(sport);
                //0：不充足1：充足
                String sportStr = sportobj.getString("now_value");
                if (StringUtil.isNotEmpty(sportStr)) {
                    vojo.setSport(sportStr.equals("1") ? "充足" : "不充足");
                }
            }
        }


        //吸烟
        String smoke_status = jsonObject.getString("smoke_status");
        if (StringUtil.isNotEmpty(smoke_status)) {
            if (smoke_status.equals("1")) {
                vojo.setSmoke("吸烟");
            }
            if (smoke_status.equals("2")) {
                vojo.setSmoke("不吸烟");
            }
            if (smoke_status.equals("3")) {
                vojo.setSmoke("已戒烟");
            }

        }

        //饮酒
        String food_drink = jsonObject.getString("food_drink");
        if (StringUtil.isNotEmpty(food_drink)) {
            if (food_drink.equals("1")) {
                vojo.setFoodDrink("喝酒");
            }
            if (food_drink.equals("2")) {
                vojo.setFoodDrink("不喝");
            }


        }

        return Result.ok(vojo);
    }


    @RequestMapping(value = "/improve", method = RequestMethod.GET)
    @ResponseBody
    public Result improve(String[] info, String rs) {
        if (info == null) {
            return Result.error();
        }
        List<YHDiseaseAssessResolve> list = new ArrayList<YHDiseaseAssessResolve>();
        for (String string : info) {
            List<YHDiseaseAssessResolve> findAssessResolveInfoByrs = YhDiseaseAssessReportService.findAssessResolveInfoByrs(rs, Integer.valueOf(string));
            if (null != findAssessResolveInfoByrs && findAssessResolveInfoByrs.size() > 0) {
                for (YHDiseaseAssessResolve yhDiseaseAssessResolve : findAssessResolveInfoByrs) {
                    list.add(yhDiseaseAssessResolve);
                }
            }

        }

        return Result.ok(list);
    }


    @RequestMapping(value = "/healthAssessment2", method = RequestMethod.GET)
    @ResponseBody
    public Result healthAssessment2(@RequestParam(required = false) String url, @RequestParam(required = false) String assessment, @RequestParam(required = false) String ganyu, @RequestParam(required = false) String orgid, @RequestParam(required = false) String usernameLike, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit) {
        System.out.println(page + "----" + limit);

        System.out.println(url);
        System.out.println(ganyu);
        System.out.println("username:" + usernameLike);

        YhDicAssessmentScreen selectByPrimaryKey = new YhDicAssessmentScreen();
        if (StringUtils.isNotEmpty(usernameLike)) {
            selectByPrimaryKey.setUsername(usernameLike);
        }
        if (StringUtils.isNotEmpty(orgid)) {
            selectByPrimaryKey.setOrgCode(orgid);
        }

        if (StringUtils.isNotEmpty(ganyu)) {
            selectByPrimaryKey.setEvaluateFlag("f" + ganyu);
        }

        if (StringUtils.isNotEmpty(assessment)) {
            selectByPrimaryKey.setAssessment(assessment);
        }


        // 查询用户信息
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        YhUserReginfo upmsUser = yhUserApiService.selectUpmsUserByUsername(principal.getUserName());
        if (null == upmsUser) {
            return Result.error("获取用户信息失败");
        }
        // 得到医生usercode
        String userCode = upmsUser.getUserCode();

        if (null == userCode) {
            return Result.error("获取用户信息失败");
        }
        selectByPrimaryKey.setDoctor(userCode);
        // 筛选
        if (null != url) {
            // 查询筛选条件
            selectByPrimaryKey = YhDicAssessmentScreenService.selectByPrimaryKey(Integer.valueOf(url));
            YhDicAssessmentScreen record = new YhDicAssessmentScreen();
            record.setSid(Integer.valueOf(url));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

            record.setScreeningtime(df.format(new Date()));
            YhDicAssessmentScreenService.updateByPrimaryKeySelective(record);
            if (null == selectByPrimaryKey) {
                return Result.error("筛选失败");
            }

            if (StringUtil.isNotEmpty(userCode)) {
                selectByPrimaryKey.setDoctor(userCode);
                if (StringUtils.isNotEmpty(ganyu)) {
                    selectByPrimaryKey.setEvaluateFlag("f" + ganyu);
                }
                if (StringUtils.isNotEmpty(usernameLike)) {
                    selectByPrimaryKey.setUsername(usernameLike);
                }
                if (StringUtils.isNotEmpty(orgid)) {
                    selectByPrimaryKey.setOrgCode(orgid);
                }

                PageInfo<YhDiseaseAssessReportVOJO> findAssessInfoByDocTor = yhDiseaseAssessTotalReportService.findAssessInfoByDocTor(selectByPrimaryKey, page, limit);
                List<String> findAssessInfoByDocTor2 = yhDiseaseAssessTotalReportService.findAssessInfoByDocTor2(selectByPrimaryKey);

                Map<String, Object> rsMap = new HashMap<String, Object>();
                rsMap.put("count", findAssessInfoByDocTor.getTotal());
                rsMap.put("code", 0);
                rsMap.put("usercodeList", findAssessInfoByDocTor2);
                rsMap.put("msg", "ok");
                rsMap.put("data", findAssessInfoByDocTor.getList());
                return Result.ok(rsMap);
            }
            return Result.error();
        } else {
            // 查询筛选条件


            if (StringUtil.isNotEmpty(userCode)) {

                PageInfo<YhDiseaseAssessReportVOJO> findAssessInfoByDocTor = yhDiseaseAssessTotalReportService.findAssessInfoByDocTor(selectByPrimaryKey, page, limit);
                List<String> findAssessInfoByDocTor2 = yhDiseaseAssessTotalReportService.findAssessInfoByDocTor2(selectByPrimaryKey);

                Map<String, Object> rsMap = new HashMap<String, Object>();
                rsMap.put("count", findAssessInfoByDocTor.getTotal());
                rsMap.put("code", 0);
                rsMap.put("usercodeList", findAssessInfoByDocTor2);
                rsMap.put("msg", "ok");
                rsMap.put("data", findAssessInfoByDocTor.getList());
                return Result.ok(rsMap);
            }
            return Result.error();
        }

    }


    @RequestMapping(value = "/healthAssessment3", method = RequestMethod.GET)
    @ResponseBody
    public Result healthAssessment3(@RequestParam(required = false) String url, @RequestParam(required = false) Integer ganyu, @RequestParam(required = false) String orgid, @RequestParam(required = false) String usernameLike, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit) {
        System.out.println(page + "----" + limit);

        System.out.println(url);
        System.out.println("username:" + usernameLike);

        YhDicAssessmentScreen selectByPrimaryKey = null;

        // 数据表格实体
        List<YhDiseaseAssessReportVOJO> list = new ArrayList<YhDiseaseAssessReportVOJO>();

        // 查询用户信息
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        YhUserReginfo upmsUser = yhUserApiService.selectUpmsUserByUsername(principal.getUserName());
        if (null == upmsUser) {
            return Result.error("获取用户信息失败");
        }
        // 得到医生usercode
        String userCode = upmsUser.getUserCode();

        if (null == userCode) {
            return Result.error("获取用户信息失败");
        }

        // 筛选
        if (null != url) {
            // 查询筛选条件
            selectByPrimaryKey = YhDicAssessmentScreenService.selectByPrimaryKey(Integer.valueOf(url));
            YhDicAssessmentScreen record = new YhDicAssessmentScreen();
            record.setSid(Integer.valueOf(url));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

            record.setScreeningtime(df.format(new Date()));
            YhDicAssessmentScreenService.updateByPrimaryKeySelective(record);
            if (null == selectByPrimaryKey) {
                return Result.error("筛选失败");
            }


            if (StringUtil.isNotEmpty(userCode)) {
                // 查询医生管理的所有注册卡
                List<YhUserMakeCard> selectCarByuserCode = selectCarByuserCode(orgid, usernameLike, ganyu, userCode, page, limit);
                if (null == selectCarByuserCode || selectCarByuserCode.size() == 0) {
                    return Result.error("该医生没有注册卡");
                }

                for (int i = 0; i < selectCarByuserCode.size(); i++) {
                    YhDiseaseAssessReportVOJO vojo = new YhDiseaseAssessReportVOJO();
                    // 获得每个注册卡信息
                    YhUserMakeCard yhUserMakeCard = selectCarByuserCode.get(i);
                    if (null == yhUserMakeCard.getUserCode()) {
                        continue;
                    }
                    // 注册卡所属用户
                    String userCode2 = yhUserMakeCard.getUserCode();
                    vojo.setUsercode(userCode2);// 用户编号
                    // 查询用户信息
                    YhUserReginfo selectUserRegInfoByUserCode = yhUserApiService.selectUserRegInfoByUserCode(userCode2);
                    // 用户为空跳出当前循环
                    if (null == selectUserRegInfoByUserCode) {
                        list.add(vojo);
                        continue;
                    }

                    // 是否干预
                    YhUserEnrolinfoExample userEnrolExa = new YhUserEnrolinfoExample();
                    com.yihe.system.domain.YhUserEnrolinfoExample.Criteria createCriteriauserEnrolExa = userEnrolExa
                            .createCriteria();

                    createCriteriauserEnrolExa.andUserCodeEqualTo(userCode2);
                    List<YhUserEnrolinfo> selectByExampleuserEnrolExa = yhUserEnrolinfoService
                            .selectByExample(userEnrolExa);
                    if (selectByExampleuserEnrolExa.size() == 0) {
                        vojo.setIntervene(0);
                        Result.error("注册表错误");
                        continue;
                    }
                    YhUserEnrolinfo yhUserEnrolinfo = selectByExampleuserEnrolExa.get(0);
                    if (null == yhUserEnrolinfo) {
                        Result.error("注册表错误");
                    }
                    Integer evaluateflag = yhUserEnrolinfo.getEvaluateflag();
                    vojo.setIntervene(evaluateflag);


                    vojo.setUsername(selectUserRegInfoByUserCode.getUserName());// 用户账户
                    vojo.setName(StringUtil.isNotEmpty(selectUserRegInfoByUserCode.getName())// 用户姓名
                            // 如果姓名为空显示账户
                            ? selectUserRegInfoByUserCode.getUserName() : selectUserRegInfoByUserCode.getName());
                    // 筛选男女
                    if (null != selectByPrimaryKey && selectByPrimaryKey.getSex() != 0) {
                        if (selectUserRegInfoByUserCode.getGender() != selectByPrimaryKey.getSex()) {
                            continue;
                        } else {
                            if (selectUserRegInfoByUserCode.getGender() == 1) {
                                vojo.setSex("男");
                            } else {
                                vojo.setSex("女");
                            }
                        }
                    } else {
                        if (selectUserRegInfoByUserCode.getGender() == 1) {
                            vojo.setSex("男");
                        } else {
                            vojo.setSex("女");
                        }
                    }

                    int gfx = 0;// 高风险
                    String gfxInfo = new String();// 高风险详情
                    int zdfx = 0;// 中等风险
                    String zdfxInfo = new String();// 中等风险详情
                    int dfx = 0;// 低风险
                    String dfxInfo = new String();// 低风险详情
                    int hb = 0;// 患病
                    String hbInfo = new String();// 患病详情

                    // 查询评估基本信息表 筛选后得到该用户评估了几次 得到筛选后的请求序列号集合
                    selectByPrimaryKey.setUsercode(userCode2);
                    List<YhDiseaseAssessTotalReport> selectByExample3 = yhDiseaseAssessTotalReportService
                            .selectByPrimaryKeyByAge(selectByPrimaryKey);
                    vojo.setAssessmentNum(selectByExample3.size());
                    if (selectByExample3.size() != 0) {

                        // vojo.setNewestAssessTime();//最新评估时间
                        for (int j = 0; j < selectByExample3.size(); j++) {
                            // 评估结果详情
                            YhDiseaseAssessTotalReport yhDiseaseAssessTotalReport = selectByExample3.get(j);

                            // 得到筛选后的评估详情
                            selectByPrimaryKey.setRs(yhDiseaseAssessTotalReport.getRequestSerialnum());
                            List<YhDiseaseAssessReport> selectByExample = yhDiseaseAssessReportService
                                    .selectByPrimaryKeyByrequestserialNum(selectByPrimaryKey);
                            if (selectByExample.size() > 0) {
                                for (int k = 0; k < selectByExample.size(); k++) {
                                    // 评估详情
                                    YhDiseaseAssessReport yhDiseaseAssessReport = selectByExample.get(k);
                                    // 风险登录
                                    String riskLevel = yhDiseaseAssessReport.getRiskLevel();

                                    if (StringUtil.isEmpty(riskLevel)) {
                                        return Result.error("错误");
                                    }
                                    // 如果风险等级字段内包含“高”字 判断为高风险
                                    if (riskLevel.contains("高")) {
                                        // 根据疾病id查询疾病名称
                                        YhDiseaseCategoryExample gExample = new YhDiseaseCategoryExample();
                                        com.yihe.system.domain.YhDiseaseCategoryExample.Criteria createCriteria2 = gExample.createCriteria();
                                        createCriteria2.andIdEqualTo(yhDiseaseAssessReport.getDiseasid());
                                        List<YhDiseaseCategory> selectByExample2 = yhDiseaseCategoryService.selectByExample(gExample);
                                        YhDiseaseCategory yhDiseaseCategory = selectByExample2.get(0);
                                        String diseaseName = yhDiseaseCategory.getDiseaseName();
                                        if (riskLevel.contains("高血压")) {
                                            if (!hbInfo.contains(diseaseName)) {
                                                hb++;
                                                hbInfo += diseaseName + "; ";
                                            }
                                        } else {

                                            if (!gfxInfo.contains(diseaseName)) {
                                                gfx++;
                                                gfxInfo += diseaseName + "; ";
                                            }
                                        }

                                    } else if (riskLevel.contains("中")) {

                                        YhDiseaseCategoryExample gExample = new YhDiseaseCategoryExample();
                                        com.yihe.system.domain.YhDiseaseCategoryExample.Criteria createCriteria2 = gExample
                                                .createCriteria();
                                        createCriteria2.andIdEqualTo(yhDiseaseAssessReport.getDiseasid());
                                        List<YhDiseaseCategory> selectByExample2 = yhDiseaseCategoryService
                                                .selectByExample(gExample);
                                        YhDiseaseCategory yhDiseaseCategory = selectByExample2.get(0);
                                        String diseaseName = yhDiseaseCategory.getDiseaseName();
                                        if (!zdfxInfo.contains(diseaseName)) {
                                            zdfx++;
                                            zdfxInfo += diseaseName + "; ";
                                        }

                                    } else if (riskLevel.contains("低")) {

                                        YhDiseaseCategoryExample gExample = new YhDiseaseCategoryExample();
                                        com.yihe.system.domain.YhDiseaseCategoryExample.Criteria createCriteria2 = gExample
                                                .createCriteria();
                                        createCriteria2.andIdEqualTo(yhDiseaseAssessReport.getDiseasid());
                                        List<YhDiseaseCategory> selectByExample2 = yhDiseaseCategoryService
                                                .selectByExample(gExample);
                                        YhDiseaseCategory yhDiseaseCategory = selectByExample2.get(0);
                                        String diseaseName = yhDiseaseCategory.getDiseaseName();
                                        if (!dfxInfo.contains(diseaseName)) {
                                            dfx++;
                                            dfxInfo += diseaseName + "; ";
                                        }

                                    } else {

                                        YhDiseaseCategoryExample gExample = new YhDiseaseCategoryExample();
                                        com.yihe.system.domain.YhDiseaseCategoryExample.Criteria createCriteria2 = gExample
                                                .createCriteria();
                                        createCriteria2.andIdEqualTo(yhDiseaseAssessReport.getDiseasid());
                                        List<YhDiseaseCategory> selectByExample2 = yhDiseaseCategoryService
                                                .selectByExample(gExample);
                                        YhDiseaseCategory yhDiseaseCategory = selectByExample2.get(0);
                                        String diseaseName = yhDiseaseCategory.getDiseaseName();
                                        if (!hbInfo.contains(diseaseName)) {
                                            hb++;
                                            hbInfo += diseaseName + "; ";
                                        }
                                    }

                                }

                                vojo.setHighRiskNum(gfx);
                                if (StringUtil.isNotEmpty(gfxInfo)) {
                                    String c = gfxInfo.substring(gfxInfo.length() - 2);
                                    if ("; ".equals(c)) {
                                        gfxInfo = gfxInfo.substring(0, gfxInfo.length() - 2);
                                    }
                                }

                                vojo.setHighRiskInfo(gfxInfo);
                                vojo.setMediumRiskNum(zdfx);

                                if (StringUtil.isNotEmpty(zdfxInfo)) {
                                    String c = zdfxInfo.substring(zdfxInfo.length() - 2);
                                    if ("; ".equals(c)) {
                                        zdfxInfo = zdfxInfo.substring(0, zdfxInfo.length() - 2);
                                    }
                                }
                                vojo.setMediumRiskInfo(zdfxInfo);
                                vojo.setLowRiskNum(dfx);

                                if (StringUtil.isNotEmpty(dfxInfo)) {
                                    String c = dfxInfo.substring(dfxInfo.length() - 2);
                                    if ("; ".equals(c)) {
                                        dfxInfo = dfxInfo.substring(0, dfxInfo.length() - 2);
                                    }
                                }


                                vojo.setLowRiskInfo(dfxInfo);
                                vojo.setAbnormalNum(hb);

                                if (StringUtil.isNotEmpty(hbInfo)) {
                                    String c = hbInfo.substring(hbInfo.length() - 2);
                                    if ("; ".equals(c)) {
                                        hbInfo = hbInfo.substring(0, hbInfo.length() - 2);
                                    }
                                }


                                vojo.setAbnormalInfo(hbInfo);

                            }

                        }
                    } else {
                        vojo.setAssessmentNum(0);
                        vojo.setHighRiskNum(0);
                        vojo.setHighRiskInfo("");
                        vojo.setHigherRiskNum(0);
                        vojo.setHigherRiskInfo("");
                        vojo.setMediumRiskNum(0);
                        vojo.setMediumRiskInfo("");
                        vojo.setLowerRiskNum(0);
                        vojo.setLowerRisInfo("");
                        vojo.setLowRiskNum(0);
                        vojo.setLowRiskInfo("");
                        vojo.setAbnormalNum(0);
                        vojo.setAbnormalInfo("");
                    }

                    list.add(vojo);

                }
            }
            Map<String, Object> rsMap = new HashMap<String, Object>();
            rsMap.put("count", selectCarByuserCodeCount(userCode));
            rsMap.put("code", 0);
            rsMap.put("msg", "ok");
            rsMap.put("data", list);
            return Result.ok(rsMap);
        } else {

            if (StringUtil.isNotEmpty(userCode)) {
                List<YhUserMakeCard> selectCarByuserCode = selectCarByuserCode(orgid, usernameLike, ganyu, userCode, page, limit);
                if (null != selectCarByuserCode || selectCarByuserCode.size() > 0)
                    for (int i = 0; i < selectCarByuserCode.size(); i++) {
                        YhDiseaseAssessReportVOJO vojo = new YhDiseaseAssessReportVOJO();
                        YhUserMakeCard yhUserMakeCard = selectCarByuserCode.get(i);
                        // 注册卡所属用户
                        String userCode2 = yhUserMakeCard.getUserCode();
                        vojo.setUsercode(userCode2);

                        YhUserReginfo selectUserRegInfoByUserCode = yhUserApiService.selectUserRegInfoByUserCode(userCode2);
                        if (null == selectUserRegInfoByUserCode) {
                            list.add(vojo);
                            continue;
                        }

                        vojo.setUsername(selectUserRegInfoByUserCode.getUserName());
                        vojo.setName(StringUtil.isNotEmpty(selectUserRegInfoByUserCode.getName())
                                ? selectUserRegInfoByUserCode.getUserName() : selectUserRegInfoByUserCode.getName());
                        // 是否干预
                        YhUserEnrolinfoExample userEnrolExa = new YhUserEnrolinfoExample();
                        com.yihe.system.domain.YhUserEnrolinfoExample.Criteria createCriteriauserEnrolExa = userEnrolExa
                                .createCriteria();
                        createCriteriauserEnrolExa.andUserCodeEqualTo(userCode2);
                        List<YhUserEnrolinfo> selectByExampleuserEnrolExa = yhUserEnrolinfoService
                                .selectByExample(userEnrolExa);
                        if (selectByExampleuserEnrolExa.size() == 0) {
                            vojo.setIntervene(0);
                            Result.error("注册表错误");
                            continue;
                        }
                        YhUserEnrolinfo yhUserEnrolinfo = selectByExampleuserEnrolExa.get(0);
                        if (null == yhUserEnrolinfo) {
                            Result.error("注册表错误");
                        }
                        Integer evaluateflag = yhUserEnrolinfo.getEvaluateflag();
                        vojo.setIntervene(evaluateflag);


                        if (selectUserRegInfoByUserCode.getGender() == 1) {
                            vojo.setSex("男");
                        } else {
                            vojo.setSex("女");
                        }
                        int gfx = 0;// 高风险
                        String gfxInfo = new String();// 高风险详情
                        int jgfx = 0;// 较高风险
                        String jgfxInfo = new String();// 较高风险详情
                        int zdfx = 0;// 中等风险
                        String zdfxInfo = new String();// 中等风险详情
                        int jdfx = 0;// 较低风险
                        String jdfxInfo = new String();// 较低风险详情
                        int dfx = 0;// 低风险
                        String dfxInfo = new String();// 低风险详情
                        int hb = 0;// 患病
                        String hbInfo = new String();// 患病详情

                        // 查询用户评估几次
                        YhDiseaseAssessTotalReportExample datrsexample = new YhDiseaseAssessTotalReportExample();
                        datrsexample.setOrderByClause("qtime desc");
                        com.yihe.system.domain.YhDiseaseAssessTotalReportExample.Criteria createCriteria3 = datrsexample
                                .createCriteria();
                        createCriteria3.andUserCodeEqualTo(userCode2);

                        List<YhDiseaseAssessTotalReport> selectByExample3 = yhDiseaseAssessTotalReportService
                                .selectByExample(datrsexample);
                        if (selectByExample3.size() != 0) {
                            vojo.setAssessmentNum(selectByExample3.size());
                            // vojo.setNewestAssessTime(newestAssessTime);
                            for (int j = 0; j < 1; j++) {
                                YhDiseaseAssessTotalReport yhDiseaseAssessTotalReport = selectByExample3.get(j);

                                YhDiseaseAssessReportExample example = new YhDiseaseAssessReportExample();
                                com.yihe.system.domain.YhDiseaseAssessReportExample.Criteria createCriteria = example
                                        .createCriteria();
                                createCriteria.andRequestSerialnumEqualTo(yhDiseaseAssessTotalReport.getRequestSerialnum());
                                List<YhDiseaseAssessReport> selectByExample = yhDiseaseAssessReportService
                                        .selectByExample(example);
                                if (selectByExample.size() > 0) {
                                    for (int k = 0; k < selectByExample.size(); k++) {
                                        YhDiseaseAssessReport yhDiseaseAssessReport = selectByExample.get(k);
                                        String riskLevel = yhDiseaseAssessReport.getRiskLevel();

                                        if (StringUtil.isEmpty(riskLevel)) {
                                            return Result.error("错误");
                                        }

                                        if (riskLevel.contains("高")) {
                                            YhDiseaseCategoryExample gExample = new YhDiseaseCategoryExample();
                                            com.yihe.system.domain.YhDiseaseCategoryExample.Criteria createCriteria2 = gExample
                                                    .createCriteria();
                                            createCriteria2.andIdEqualTo(yhDiseaseAssessReport.getDiseasid());
                                            List<YhDiseaseCategory> selectByExample2 = yhDiseaseCategoryService
                                                    .selectByExample(gExample);
                                            YhDiseaseCategory yhDiseaseCategory = selectByExample2.get(0);
                                            String diseaseName = yhDiseaseCategory.getDiseaseName();
                                            if (riskLevel.contains("高血压")) {
                                                if (!hbInfo.contains(diseaseName)) {
                                                    hb++;
                                                    hbInfo += diseaseName + "; ";
                                                }
                                            } else {

                                                if (!gfxInfo.contains(diseaseName)) {
                                                    gfx++;
                                                    gfxInfo += diseaseName + "; ";
                                                }
                                            }

                                        } else if (riskLevel.contains("中")) {

                                            YhDiseaseCategoryExample gExample = new YhDiseaseCategoryExample();
                                            com.yihe.system.domain.YhDiseaseCategoryExample.Criteria createCriteria2 = gExample
                                                    .createCriteria();
                                            createCriteria2.andIdEqualTo(yhDiseaseAssessReport.getDiseasid());
                                            List<YhDiseaseCategory> selectByExample2 = yhDiseaseCategoryService
                                                    .selectByExample(gExample);
                                            YhDiseaseCategory yhDiseaseCategory = selectByExample2.get(0);
                                            String diseaseName = yhDiseaseCategory.getDiseaseName();
                                            if (!zdfxInfo.contains(diseaseName)) {
                                                zdfx++;
                                                zdfxInfo += diseaseName + "; ";
                                            }

                                        } else if (riskLevel.contains("低")) {

                                            YhDiseaseCategoryExample gExample = new YhDiseaseCategoryExample();
                                            com.yihe.system.domain.YhDiseaseCategoryExample.Criteria createCriteria2 = gExample
                                                    .createCriteria();
                                            createCriteria2.andIdEqualTo(yhDiseaseAssessReport.getDiseasid());
                                            List<YhDiseaseCategory> selectByExample2 = yhDiseaseCategoryService
                                                    .selectByExample(gExample);
                                            YhDiseaseCategory yhDiseaseCategory = selectByExample2.get(0);
                                            String diseaseName = yhDiseaseCategory.getDiseaseName();
                                            if (!dfxInfo.contains(diseaseName)) {
                                                dfx++;
                                                dfxInfo += diseaseName + "; ";
                                            }

                                        } else {

                                            YhDiseaseCategoryExample gExample = new YhDiseaseCategoryExample();
                                            com.yihe.system.domain.YhDiseaseCategoryExample.Criteria createCriteria2 = gExample
                                                    .createCriteria();
                                            createCriteria2.andIdEqualTo(yhDiseaseAssessReport.getDiseasid());
                                            List<YhDiseaseCategory> selectByExample2 = yhDiseaseCategoryService
                                                    .selectByExample(gExample);
                                            YhDiseaseCategory yhDiseaseCategory = selectByExample2.get(0);
                                            String diseaseName = yhDiseaseCategory.getDiseaseName();
                                            if (!hbInfo.contains(diseaseName)) {
                                                hb++;
                                                hbInfo += diseaseName + "; ";
                                            }
                                        }

                                    }

                                    vojo.setHighRiskNum(gfx);
                                    if (StringUtil.isNotEmpty(gfxInfo)) {
                                        String c = gfxInfo.substring(gfxInfo.length() - 2);
                                        if ("; ".equals(c)) {
                                            gfxInfo = gfxInfo.substring(0, gfxInfo.length() - 2);
                                        }
                                    }

                                    vojo.setHighRiskInfo(gfxInfo);

                                    vojo.setHigherRiskNum(jgfx);

                                    if (StringUtil.isNotEmpty(jgfxInfo)) {
                                        String c = jgfxInfo.substring(jgfxInfo.length() - 2);
                                        if ("; ".equals(c)) {
                                            jgfxInfo = jgfxInfo.substring(0, jgfxInfo.length() - 2);
                                        }
                                    }
                                    vojo.setHigherRiskInfo(jgfxInfo);
                                    vojo.setMediumRiskNum(zdfx);

                                    if (StringUtil.isNotEmpty(zdfxInfo)) {
                                        String c = zdfxInfo.substring(zdfxInfo.length() - 2);
                                        if ("; ".equals(c)) {
                                            zdfxInfo = zdfxInfo.substring(0, zdfxInfo.length() - 2);
                                        }
                                    }
                                    vojo.setMediumRiskInfo(zdfxInfo);
                                    vojo.setLowerRiskNum(jdfx);

                                    if (StringUtil.isNotEmpty(jdfxInfo)) {
                                        String c = jdfxInfo.substring(jdfxInfo.length() - 2);
                                        if ("; ".equals(c)) {
                                            jdfxInfo = jdfxInfo.substring(0, jdfxInfo.length() - 2);
                                        }
                                    }
                                    vojo.setLowerRisInfo(jdfxInfo);
                                    vojo.setLowRiskNum(dfx);

                                    if (StringUtil.isNotEmpty(dfxInfo)) {
                                        String c = dfxInfo.substring(dfxInfo.length() - 2);
                                        if ("; ".equals(c)) {
                                            dfxInfo = dfxInfo.substring(0, dfxInfo.length() - 2);
                                        }
                                    }
                                    vojo.setLowRiskInfo(dfxInfo);
                                    vojo.setAbnormalNum(hb);

                                    if (StringUtil.isNotEmpty(hbInfo)) {
                                        String c = hbInfo.substring(hbInfo.length() - 2);
                                        if ("; ".equals(c)) {
                                            hbInfo = hbInfo.substring(0, hbInfo.length() - 2);
                                        }
                                    }
                                    vojo.setAbnormalInfo(hbInfo);


                                }


                            }
                        } else {
                            vojo.setAssessmentNum(0);
                            vojo.setHighRiskNum(0);
                            vojo.setHighRiskInfo("");
                            vojo.setHigherRiskNum(0);
                            vojo.setHigherRiskInfo("");
                            vojo.setMediumRiskNum(0);
                            vojo.setMediumRiskInfo("");
                            vojo.setLowerRiskNum(0);
                            vojo.setLowerRisInfo("");
                            vojo.setLowRiskNum(0);
                            vojo.setLowRiskInfo("");
                            vojo.setAbnormalNum(0);
                            vojo.setAbnormalInfo("");

                        }
                        list.add(vojo);

                    }

            }

            Map<String, Object> rsMap = new HashMap<String, Object>();
            rsMap.put("count", selectCarByuserCodeCount(userCode));
            rsMap.put("code", 0);
            rsMap.put("msg", "ok");
            rsMap.put("data", list);
            return Result.ok(rsMap);
        }

    }


    @RequestMapping(value = "/screeningList", method = RequestMethod.GET)
    public String screeningList() {

        return "health/assess/screeningList";
    }

    @RequestMapping(value = "/screeningAdd", method = RequestMethod.GET)
    public String screeningAdd(Integer id, Model model) {
        YhDicAssessmentScreen selectByPrimaryKey = new YhDicAssessmentScreen();
        if (null == id) {
            model.addAttribute("selectByPrimaryKey", selectByPrimaryKey);
            return "/health/assess/screeningAdd";
        }
        selectByPrimaryKey = YhDicAssessmentScreenService.selectByPrimaryKey(id);
        model.addAttribute("selectByPrimaryKey", selectByPrimaryKey);
        return "/health/assess/screeningAdd";
    }

    /**
     * 获取当前系统时间String类型返回值
     *
     * @return
     */
    public String NowString() {
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }


    @RequestMapping(value = "/findPGList", method = RequestMethod.GET)
    @ResponseBody
    public Result findPGList(@RequestParam(required = false) String nameLike, @RequestParam(required = false) String startTime, @RequestParam(required = false) String zhangh, @RequestParam(required = false) String strtime, @RequestParam(required = false) String endtime, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit) {
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("code", 0);
        System.out.println("nameLike---" + nameLike);
        System.out.println("zhangh---" + zhangh);
        System.out.println("startTime---" + startTime);
        System.out.println("strtime---" + strtime);
        System.out.println("endtime---" + endtime);
        System.out.println("page---" + page);
        System.out.println("limit---" + limit);
        if (StringUtil.isNotEmpty(startTime)) {
            String[] split = startTime.split(" - ");
            strtime = split[0];
            endtime = split[1];
        }

        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo upmsUser = (YhUserReginfo) subject.getPrincipal();
        if (null == upmsUser) {
            return Result.error();
        }
        PageInfo<YhDiseaseAssessReportVOJO> listpage = yhDiseaseAssessTotalReportService.selectByPrimaryKeyByusercode(upmsUser.getUserCode(), nameLike, zhangh, strtime, endtime, page, limit);
        List<YhDiseaseAssessReportVOJO> selectByPrimaryKeyByusercode = listpage.getList();
        if (null == selectByPrimaryKeyByusercode || selectByPrimaryKeyByusercode.size() == 0) {
            return Result.ok(rsMap);
        }


        rsMap.put("count", listpage.getTotal());

        rsMap.put("msg", "ok");
        rsMap.put("data", selectByPrimaryKeyByusercode);
        return Result.ok(rsMap);

    }


    @RequestMapping(value = "/findHealthAssessmentByuser", method = RequestMethod.GET)
    @ResponseBody
    public Result findHealthAssessmentByuser(@RequestParam(required = false) String usercode, @RequestParam(required = false) String strTime, @RequestParam(required = false) String endTime,
                                             @RequestParam(required = false) String startTime,
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "5") int limit) {
        System.out.println("usercode---" + usercode);
        System.out.println("page---" + page);
        System.out.println("limit---" + limit);
        if (StringUtil.isNotEmpty(startTime)) {
            String[] split = startTime.split(" - ");
            strTime = split[0];
            endTime = split[1];
        }
        PageInfo<HealthAssessmentVOJO> findAssessTotalByUserCode = YhDiseaseAssessTotalReportServiceImpl.findAssessTotalByUserCode(usercode, strTime, endTime, page, limit);
        List<HealthAssessmentVOJO> list = findAssessTotalByUserCode.getList();
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", findAssessTotalByUserCode.getTotal());
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", list);
        return Result.ok(rsMap);

    }


    @RequestMapping(value = "/addInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result addInfo(@RequestBody YhDicAssessmentScreen screen) {
        // 查询用户信息
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        String userCode = principal.getUserCode();
        screen.setUsercode(userCode);
        if (null == screen.getSid()) {
            screen.setCreationtime(NowString());
            int insertSelective = YhDicAssessmentScreenService.insertSelective(screen);
            return Result.ok(insertSelective);
        } else {
            YhDicAssessmentScreenService.updateByPrimaryKey(screen);
        }
        return Result.ok();
    }

    @RequestMapping(value = "/delInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result delInfo(Integer sid) {
        System.out.println(sid);
        if (null == sid) {
            return Result.error();
        }
        YhDicAssessmentScreenService.deleteByPrimaryKey(sid);

        return Result.ok();
    }

    @RequestMapping(value = "/findListInfo", method = RequestMethod.GET)
    @ResponseBody
    public Result findListInfo(@RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false, defaultValue = "5") int limit) {
        // 查询用户信息
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo username = (YhUserReginfo) subject.getPrincipal();
        String userCode = username.getUserCode();
        YhDicAssessmentScreenExample example = new YhDicAssessmentScreenExample();
        example.setOrderByClause("screeningTime desc");
        com.yihe.system.domain.YhDicAssessmentScreenExample.Criteria createCriteria = example.createCriteria();
        createCriteria.andUsercodeEqualTo(userCode);
        PageInfo<YhDicAssessmentScreen> selectByExample = YhDicAssessmentScreenService.selectByExample(example, page, limit);

        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", selectByExample.getTotal());
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", selectByExample.getList());
        return Result.ok(rsMap);
    }

    public YhUserAdminInfo findInfo(String usercode) {
        YhUserAdminInfoExample example = new YhUserAdminInfoExample();
        Criteria createCriteria = example.createCriteria();
        createCriteria.andUserCodeEqualTo(usercode);
        List<YhUserAdminInfo> selectByExample = yhUserAdminInfoService.selectByExample(example);
        if (selectByExample.size() > 0) {
            YhUserAdminInfo yhUserAdminInfo = selectByExample.get(0);
            return yhUserAdminInfo;
        }

        return null;
    }

    public List<YhUserMakeCard> selectCarByuserCode(String orgid, String name, Integer ganyu, String usercode, Integer pageNum, Integer pageSize) {


        YhUserAdminInfo selectByExample = yhUserAdminInfoService.selectByPrimaryKeyByusercode(usercode);
        if (null == selectByExample || null == selectByExample.getId()) {
            return null;
        }

        PageInfo<YhUserMakeCard> selectByOrgStuckByNameAndEvaAndOrgidAndDoctor = yhUserMakeCardService.selectByOrgStuckByNameAndEvaAndOrgidAndDoctor(selectByExample.getId(), orgid, name, ganyu, pageNum, pageSize);
        List<YhUserMakeCard> list = selectByOrgStuckByNameAndEvaAndOrgidAndDoctor.getList();


        return list;
    }

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(199).pow(200);
        BigDecimal b = new BigDecimal(200).pow(199);
        System.out.println(a);
        System.out.println(b);

        //前提为a、b均不能为null
        if (a.compareTo(b) == -1) {
            System.out.println("a小于b");
        }

        if (a.compareTo(b) == 0) {
            System.out.println("a等于b");
        }

        if (a.compareTo(b) == 1) {
            System.out.println("a大于b");
        }

    }

    public Integer selectCarByuserCodeCount(String usercode) {
        YhUserAdminInfoExample example2 = new YhUserAdminInfoExample();
        Criteria createCriteria2 = example2.createCriteria();
        createCriteria2.andUserCodeEqualTo(usercode);
        List<YhUserAdminInfo> selectByExample = yhUserAdminInfoService.selectByExample(example2);
        if (null == selectByExample || selectByExample.size() == 0) {
            return 0;
        }
        YhUserAdminInfo yhUserAdminInfo = selectByExample.get(0);
        if (null == yhUserAdminInfo || null == yhUserAdminInfo.getId()) {
            return 0;
        }
        YhUserMakeCardExample example = new YhUserMakeCardExample();
        com.yihe.system.domain.YhUserMakeCardExample.Criteria createCriteria = example.createCriteria();
        createCriteria.andDoctorEqualTo(yhUserAdminInfo.getId());
        createCriteria.andUserCodeNotEqualTo("");
        createCriteria.andUserCodeIsNotNull();
        List<YhUserMakeCard> selectByExample2 = yhUserMakeCardService.selectByExample(example);
        return selectByExample2.size();
    }


}