package com.yihe.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.yihe.common.utils.file.FileUtils;
import com.yihe.system.domain.YhChineseAnwserr;
import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.service.YhUserApiService;
import com.yihe.system.service.YhUserMakeCardService;
import com.yihe.system.service.YhcheidentificationService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import com.yihe.web.controller.tool.HtmlToPdf;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.SheetUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/YhcheidentificationController")
public class YhcheidentificationController {
    @Autowired
    YhcheidentificationService yhcheidentificationService;
    @Autowired
    private YhUserApiService yhUserApiService;
    @Autowired
    YhUserMakeCardService yhUserMakeCardService;
    @Autowired
    YhOrgTreeService yhOrgTreeService;

    @ResponseBody
    @RequestMapping(value="/allPage",method= RequestMethod.GET)
    public Map<String,Object> queryAllYhPainTotalReport(String symptom, String orgCode, String name, int page, int limit, String qtime){
        YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        String userCode = yhUserReginfo.getUserCode();
        String qtstrTime = "";
        String qtendTime = "";
        if (StringUtil.isNotEmpty(qtime)) {
            String[] split = qtime.split(" - ");
            qtstrTime = split[0];
            qtendTime = split[1];
        }

        //查询集合
        PageInfo<YhChineseAnwserr> data = yhcheidentificationService.queryAllUserByCriteriaWithPage(symptom,orgCode,name,userCode, qtstrTime,
                qtendTime, page, limit);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("data",data.getList());
        result .put("count", data.getTotal());
        result.put("msg", "");
        result.put("code", 20000);
        return result;
    }

    @ApiOperation(value = "分页查询症状评估数据")
    @ResponseBody
    @RequestMapping(value="/page",method=RequestMethod.GET)
    public Map<String,Object> queryYhPainTotalReport(String userCode,int page,int limit,String qtime){
        String qtstrTime = "";
        String qtendTime = "";
        if (StringUtil.isNotEmpty(qtime)) {
            String[] split = qtime.split(" - ");
            qtstrTime = split[0];
            qtendTime = split[1];
        }

        //查询集合
        PageInfo<YhChineseAnwserr> data = yhcheidentificationService.queryUserByCriteriaWithPage(userCode,qtstrTime,qtendTime,page, limit);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("data",data.getList());
        result .put("count", data.getTotal());
        result.put("msg", "");
        result.put("code", 20000);
        return result;
    }

    @RequestMapping(value = "/gaoxueyaDataInfo2", method = RequestMethod.GET)
    @ResponseBody
    public Result gaoxueyaDataInfo2(String reportId, String usercode, HttpServletResponse response) {
        Date d = new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println(sbf.format(d));
        YhUserReginfo selectUserRegInfoByUserCode = yhUserApiService.selectUserRegInfoByUserCode(usercode);
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/template/";
        String fileName=selectUserRegInfoByUserCode.getName()+"中医体质辨识"+sbf.format(d)+".pdf";
        System.out.println(HtmlToPdf.TEMP_DIR_PATH+fileName);
        String s = "http://localhost/manage/usertosee/zhongyibianshi?reportId="+reportId;
        boolean convert = HtmlToPdf.convert("http://localhost/manage/usertosee/zhongyibianshi?reportId="+reportId, path+fileName);

        if(convert){
            return Result.ok(fileName);
        }else{
            return Result.error();
        }



    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        //这里文件名称是通过参数传递过来的，要是不需要，直接可以写在这里。String fileName = "a.doc";
        String path =  ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/template/";
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {

            File file = new File(path, fileName);
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"),"iso8859-1"));
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            FileUtils.deleteFile(path+fileName);
        }catch(FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("The file not found!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
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


    @RequestMapping(value = "/symptomExportXls", method = RequestMethod.GET)
    @ResponseBody
    public void exportXls(HttpServletResponse response, String userCode, Integer reportId)
            throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet =workbook.createSheet("健康评估结果");
        // 设置文件名
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String[] headers = new String[] { "用户编码", "姓名", "性别", "单位", "评估时间", "您精力充沛吗？（指精神头足，乐于做事）",
                "您容易疲乏吗？（指体力如何，是否稍活动一下或做一点家务劳动就感到累", "您容易气短，呼吸短促，接不上气吗？", "您说话声音低弱无力吗？（指说话没有力气）",
                "您感到闷闷不乐、情绪低沉吗？（指心情不愉快，情绪低落）", "您容易精神张、焦虑不安吗？（指遇事是否心情紧张）",
                "您因为生活状态改变而感到孤独、失落吗？",
                "您容易感到害怕或受到吓吗？",
                "您感到身体超重不轻松吗？（使用BMI指数，请填写身高和体重即可）",
                "您眼睛干涩吗？",
                "您手脚发凉吗？（不包含因周围温度低或的少导致的手脚发冷）？",
                "（12）您胃脘部、背部或腰膝怕冷吗？（指上腹部、背部、腰部或膝关节等，有一处或多处怕冷）",
                "您比一般人耐受不了寒冷吗？（指比别人容易害怕冬天或是夏天的冷空调、电扇等）",
                "您容易患感冒吗？（指每年感冒的次数）",
                "您没有感冒时也会鼻塞、流鼻涕吗？",
                "您有口粘口腻，或睡眠打鼾吗？",
                "您容易过敏（对药物、食物、气味、花粉或在季节交替、气候变化时）吗？",
                "您的皮肤容易起荨麻疹吗？（包括风团、风疹块、风疙瘩）",
                "您的皮肤在不知不觉中会出现青紫瘀斑、皮下出血吗？（指皮肤在没有外伤的情况下出现青一块紫一块的情况）",
                "您的皮肤一抓就红，并出现抓痕吗？（指被指甲或钝物划过后皮肤的反应",
                "您皮肤或口唇干吗？",
                "您有肢体麻木或固定部位疼痛的感觉吗？",
                "您面部或鼻部有油腻感或者油亮发光吗？（指脸上或鼻子）",
                "您面色或目眶晦黯，或出现褐色斑块/斑点吗？",
                "您有皮肤湿疹、疮疖吗？",
                "您感到口干咽燥、总想喝水吗？",
                "您感到口苦或嘴里有异味吗？（指口苦或口臭）",
                "您腹部肥大吗？（指腹部脂肪肥厚）",
                "您吃（喝）凉的东西会感到不舒服或者怕吃（喝）凉的东西吗？（指不喜欢吃凉的食物，或吃了凉的食物后会不舒服）",
                "您大便黏滞不爽、解不尽的感觉吗？（大便容易粘在马桶或便坑壁上）",
                "您容易大便干燥吗？",
                "您舌苔厚腻或有舌苔厚厚的感觉吗？（如果自我感觉不清可由调查员观察后坊填写）",
                "您舌下静脉瘀紫或增粗吗？（可由调查员辅助观察后填写）"};
        // 新增数据行，并且设置单元格数据
        int rowNum = 1;
        // 在excel表中添加表头
        HSSFRow row = sheet.createRow(0);

        // 列数
        /*
         * int cno=exportDataList.get(0).getClass().getDeclaredFields().length;
         */
        // 合并单元格

        // 设置行高
        row.setHeightInPoints(40);
        // 设置列宽度

        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 10 * 256);

        //设置列宽度

        sheet.setColumnWidth(0,10*256);
        sheet.setColumnWidth(1,10*256);
        sheet.setColumnWidth(2,10*256);


        // 在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            sheet.setColumnWidth(i,25*512);
            //设置为居中加粗
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            HSSFPalette palette = workbook.getCustomPalette();
            palette.setColorAtIndex((short) 9, (byte) (0xff & 204), (byte) (0xff & 255), (byte) (0xff & 204));
            style.setFillForegroundColor((short) 9);// 设置背景色
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

        java.util.Date mydate = null;

        ArrayList<YhChineseAnwserr> exportDataList = new ArrayList<YhChineseAnwserr>();
        // 在表中存放查询到的数据放入对应的列
        YhChineseAnwserr yhChineseAnwserr = yhcheidentificationService.selectByPrimaryKey(reportId);
        exportDataList.add(yhChineseAnwserr);
        String time =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime());
        String fileName = userCode+"_"+yhChineseAnwserr.getName()+"_"+time+"_"+"常见症状评估问卷" + ".xls";// 设置要导出的文件的名字


        YhUserMakeCard userMakeCard = yhUserMakeCardService.selectOrgIdbyParam(userCode);

        String orgName = "";
        if (userMakeCard.getOrgId() != null) {
            orgName = yhOrgTreeService.selectFullOrgName(userMakeCard.getOrgId().toString(), orgName);

        }

        String checkJson = yhChineseAnwserr.getChooselist();
        checkJson=checkJson.substring(1,checkJson.length()-1);
        String [] xuanze=checkJson.split(",");
        // 创建excel中的行
        HSSFRow row1 = sheet.createRow(rowNum);
        row1.createCell(0).setCellValue(yhChineseAnwserr.getUserCode());
        row1.createCell(1).setCellValue(yhChineseAnwserr.getName());
        row1.createCell(2).setCellValue(yhChineseAnwserr.getGender() == 1 ? "男" : "女");
        row1.createCell(3).setCellValue(orgName);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        row1.createCell(4).setCellValue(yhChineseAnwserr.getTime());


        row1.createCell(5).setCellValue(xuanze[0].equals("1") ? "没有（根本不/从来没有）":xuanze[0].equals("2") ? "很少（有一点/偶尔）":xuanze[0].equals("3") ? "有时（有些/少数时间）":xuanze[0].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(6).setCellValue(xuanze[1].equals("1") ? "没有（根本不/从来没有）":xuanze[1].equals("2") ? "很少（有一点/偶尔）":xuanze[1].equals("3") ? "有时（有些/少数时间）":xuanze[1].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(7).setCellValue(xuanze[2].equals("1") ? "没有（根本不/从来没有）":xuanze[2].equals("2") ? "很少（有一点/偶尔）":xuanze[2].equals("3") ? "有时（有些/少数时间）":xuanze[2].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(8).setCellValue(xuanze[3].equals("1") ? "没有（根本不/从来没有）":xuanze[3].equals("2") ? "很少（有一点/偶尔）":xuanze[3].equals("3") ? "有时（有些/少数时间）":xuanze[3].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(9).setCellValue(xuanze[4].equals("1") ? "没有（根本不/从来没有）":xuanze[4].equals("2") ? "很少（有一点/偶尔）":xuanze[4].equals("3") ? "有时（有些/少数时间）":xuanze[4].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(10).setCellValue(xuanze[5].equals("1") ? "没有（根本不/从来没有）":xuanze[5].equals("2") ? "很少（有一点/偶尔）":xuanze[5].equals("3") ? "有时（有些/少数时间）":xuanze[5].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(11).setCellValue(xuanze[6].equals("1") ? "没有（根本不/从来没有）":xuanze[6].equals("2") ? "很少（有一点/偶尔）":xuanze[6].equals("3") ? "有时（有些/少数时间）":xuanze[6].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(12).setCellValue(xuanze[7].equals("1") ? "没有（根本不/从来没有）":xuanze[7].equals("2") ? "很少（有一点/偶尔）":xuanze[7].equals("3") ? "有时（有些/少数时间）":xuanze[7].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(13).setCellValue(xuanze[8].equals("1") ? "BMI  <24":xuanze[8].equals("2") ? "24≤BMI<25":xuanze[8].equals("3") ? "25≤BMI<26":xuanze[8].equals("4") ? "26≤BMI<28":"BMI≥28");
        row1.createCell(14).setCellValue(xuanze[9].equals("1") ? "没有（根本不/从来没有）":xuanze[9].equals("2") ? "很少（有一点/偶尔）":xuanze[9].equals("3") ? "有时（有些/少数时间）":xuanze[9].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(15).setCellValue(xuanze[10].equals("1") ? "没有（根本不/从来没有）":xuanze[10].equals("2") ? "很少（有一点/偶尔）":xuanze[10].equals("3") ? "有时（有些/少数时间）":xuanze[10].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(16).setCellValue(xuanze[11].equals("1") ? "没有（根本不/从来没有）":xuanze[11].equals("2") ? "很少（有一点/偶尔）":xuanze[11].equals("3") ? "有时（有些/少数时间）":xuanze[11].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(17).setCellValue(xuanze[12].equals("1") ? "没有（根本不/从来没有）":xuanze[12].equals("2") ? "很少（有一点/偶尔）":xuanze[12].equals("3") ? "有时（有些/少数时间）":xuanze[12].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(18).setCellValue(xuanze[13].equals("1") ? "一年<2次":xuanze[13].equals("2") ? "一年感冒2~4次":xuanze[13].equals("3") ? "一年感冒5~6次":xuanze[13].equals("4") ? "一年8次以上":"几乎每月");
        row1.createCell(19).setCellValue(xuanze[14].equals("1") ? "没有（根本不/从来没有）":xuanze[14].equals("2") ? "很少（有一点/偶尔）":xuanze[14].equals("3") ? "有时（有些/少数时间）":xuanze[14].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(20).setCellValue(xuanze[15].equals("1") ? "没有（根本不/从来没有）":xuanze[15].equals("2") ? "很少（有一点/偶尔）":xuanze[15].equals("3") ? "有时（有些/少数时间）":xuanze[15].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(21).setCellValue(xuanze[16].equals("1") ? "没有（根本不/从来没有）":xuanze[16].equals("2") ? "很少（有一点/偶尔）":xuanze[16].equals("3") ? "有时（有些/少数时间）":xuanze[16].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(22).setCellValue(xuanze[17].equals("1") ? "没有（根本不/从来没有）":xuanze[17].equals("2") ? "很少（有一点/偶尔）":xuanze[17].equals("3") ? "有时（有些/少数时间）":xuanze[17].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(23).setCellValue(xuanze[18].equals("1") ? "从来没有":xuanze[18].equals("2") ? "很少（有一点/偶尔）":xuanze[18].equals("3") ? "有时（有些/少数时间）":xuanze[18].equals("4") ? "经常（相当/多数时间）":"每次遇到上述原因都过敏");
        row1.createCell(24).setCellValue(xuanze[19].equals("1") ? "没有（根本不/从来没有）":xuanze[19].equals("2") ? "很少（有一点/偶尔）":xuanze[19].equals("3") ? "有时（有些/少数时间）":xuanze[19].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(25).setCellValue(xuanze[20].equals("1") ? "没有（根本不/从来没有）":xuanze[20].equals("2") ? "很少（有一点/偶尔）":xuanze[20].equals("3") ? "有时（有些/少数时间）":xuanze[20].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(26).setCellValue(xuanze[21].equals("1") ? "没有（根本不/从来没有）":xuanze[21].equals("2") ? "很少（有一点/偶尔）":xuanze[21].equals("3") ? "有时（有些/少数时间）":xuanze[21].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(27).setCellValue(xuanze[22].equals("1") ? "没有（根本不/从来没有）":xuanze[22].equals("2") ? "很少（有一点/偶尔）":xuanze[22].equals("3") ? "有时（有些/少数时间）":xuanze[22].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(28).setCellValue(xuanze[23].equals("1") ? "没有（根本不/从来没有）":xuanze[23].equals("2") ? "很少（有一点/偶尔）":xuanze[23].equals("3") ? "有时（有些/少数时间）":xuanze[23].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(29).setCellValue(xuanze[24].equals("1") ? "没有（根本不/从来没有）":xuanze[24].equals("2") ? "很少（有一点/偶尔）":xuanze[24].equals("3") ? "有时（有些/少数时间）":xuanze[24].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(30).setCellValue(xuanze[25].equals("1") ? "没有（根本不/从来没有）":xuanze[25].equals("2") ? "很少（有一点/偶尔）":xuanze[25].equals("3") ? "有时（有些/少数时间）":xuanze[25].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(31).setCellValue(xuanze[26].equals("1") ? "没有（根本不/从来没有）":xuanze[26].equals("2") ? "很少（有一点/偶尔）":xuanze[26].equals("3") ? "有时（有些/少数时间）":xuanze[26].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(32).setCellValue(xuanze[27].equals("1") ? "腹围<80cm，相当于2.4尺":xuanze[27].equals("2") ? "腹围80~85cm，2.4~2.55尺":xuanze[27].equals("3") ? "腹围86~90cm，2.56~2.7尺":xuanze[27].equals("4") ? "腹围1~105cm，2.71~3.15尺":"腹围>105c或3.15尺");
        row1.createCell(33).setCellValue(xuanze[28].equals("1") ? "没有（根本不/从来没有）":xuanze[28].equals("2") ? "很少（有一点/偶尔）":xuanze[28].equals("3") ? "有时（有些/少数时间）":xuanze[28].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(34).setCellValue(xuanze[29].equals("1") ? "没有（根本不/从来没有）":xuanze[29].equals("2") ? "很少（有一点/偶尔）":xuanze[29].equals("3") ? "有时（有些/少数时间）":xuanze[29].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(35).setCellValue(xuanze[30].equals("1") ? "没有（根本不/从来没有）":xuanze[30].equals("2") ? "很少（有一点/偶尔）":xuanze[30].equals("3") ? "有时（有些/少数时间）":xuanze[30].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(36).setCellValue(xuanze[31].equals("1") ? "没有（根本不/从来没有）":xuanze[31].equals("2") ? "很少（有一点/偶尔）":xuanze[31].equals("3") ? "有时（有些/少数时间）":xuanze[31].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");
        row1.createCell(37).setCellValue(xuanze[32].equals("1") ? "没有（根本不/从来没有）":xuanze[32].equals("2") ? "很少（有一点/偶尔）":xuanze[32].equals("3") ? "有时（有些/少数时间）":xuanze[32].equals("4") ? "经常（相当/多数时间）":"总是（非常/每天）");

        // 必须在单元格设值以后进行
        // 设置为根据内容自动调整列宽
        for (int k = 0; k < exportDataList.size(); k++) {
            sheet.autoSizeColumn(k);
        }
        // 处理中文不能自动调整列宽的问题
        setColumnWidthByType(sheet, exportDataList.size());

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        /*
         * response.setHeader("Content-disposition", "attachment;filename=" +
         * fileName);
         */
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    private static void setColumnWidthByType(HSSFSheet sheet,int titleLength) {

        for(int i=0;i<titleLength;i++){
            int columnWidth = sheet.getRow(0).getCell(i).getStringCellValue().length();//获取表头的宽度
            int autowidth=(int) SheetUtil.getColumnWidth(sheet,i,false,1,sheet.getLastRowNum());
            if(columnWidth>autowidth){
                sheet.setColumnWidth(i, (int)256.0D*(columnWidth+1));
            }else{
                sheet.autoSizeColumn(i);
            }
        }
    }

}
