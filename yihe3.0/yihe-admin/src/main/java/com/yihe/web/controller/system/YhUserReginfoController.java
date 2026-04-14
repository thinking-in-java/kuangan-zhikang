package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.yihe.common.config.Global;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.utils.DateUtils;
import com.yihe.common.utils.RestfulDemo;
import com.yihe.common.utils.StringUtils;
import com.yihe.common.utils.poi.ExcelUtilQ;
import com.yihe.framework.shiro.service.SysPasswordService;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhDiseaseAssessReportMapper;
import com.yihe.system.mapper.YhExprotDataMapper;
import com.yihe.system.mapper.YhJzSysSmsMapper;
import com.yihe.system.service.*;
import com.yihe.system.service.impl.YhUserReginfoServiceImpl;
import com.yihe.system.utils.FileUtils;
import com.yihe.system.utils.GetAge;
import com.yihe.system.utils.Result;
import com.yihe.web.controller.tool.HttpClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * YhUserReginfocontroller Created by lll on 2020/3/6.
 */
@Controller
@RequestMapping("/YhUserReginfo")
@Api(value = "YhUserReginfo控制器", description = "YhUserReginfo管理")
public class YhUserReginfoController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserReginfoController.class);
    @Autowired
    private YhJzSysSmsMapper yhJzSysSmsMapper;
    @Autowired
    YhUserReginfoService yhUserReginfoService;
    @Autowired
    YhUserReginfoServiceImpl YhUserReginfoServiceImpl;
    @Autowired
    YhOrgTreeService yhOrgTreeService;
    @Autowired
    YhUserMakeCardService yhUserMakeCardService;
    @Autowired
    YhUserEnrolinfoService yhUserEnrolinfoService;
    @Autowired
    YhUserRoleService yhUserRoleService;
    @Autowired
    YhUserRolePowerService yhUserRolePowerService;
    @Autowired
    YhExprotDataService yhExprotDataService;

    @ApiOperation(value = "查询是否配置消息模板")
    @RequestMapping(value = "/signonClick", method = RequestMethod.GET)
    @ResponseBody
    public Result signonClick() {
        Map<String, String> params = new HashMap<>();
        params.put("appid", "99edd524-90ee-4f20-9dc4-f903db8f2de9");
        //获取当前登陆的用户
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        params.put("loginid", yh.getMobile());
        System.out.println("loginid: " + yh.getMobile());
        String s = HttpClientUtil.get("http://" + Global.getOaIPAddress() + "/ssologin/getToken", params);
        if (s.indexOf("Token") >= 0 || s.equals("未找到对应的在职人员信息")) {
            return Result.error(s);
        } else {
            return Result.ok("http://" + Global.getOaIPAddress() + "/wui/index.html?ssoToken=" + s + "#/main");
        }
    }


    @ApiOperation(value = "查询是否配置消息模板")
    @RequestMapping(value = "/showSysSmsAll", method = RequestMethod.GET)
    @ResponseBody
    public Result showSysSmsAll() {
        List<YhJzSysSmsWeb> showSysSmsAll = yhJzSysSmsMapper.showSysSmsAll();
        return Result.ok(showSysSmsAll);
    }

    @ApiOperation(value = "查询注册卡是否被注册")
    @RequestMapping(value = "/selectCardId", method = RequestMethod.GET)
    @ResponseBody
    public String selectCardId(String cardId, String orgId) {
        YhUserMakeCard canshu = new YhUserMakeCard();
        canshu.setEnrolCardId(cardId);
        canshu.setOrgId(orgId);
        System.out.println("YhUserMakeCard:" + canshu);
        String sc = yhUserReginfoService.selectCardId(canshu);
        System.out.println(">>>>sc:" + sc);
        if (sc == null) {
            return "true";
        } else {
            return "false";
        }
    }

    @ApiOperation(value = "注册提交")
    @RequestMapping(value = "/regSubmit", method = RequestMethod.POST)
    @ResponseBody
    public int addYhUserReginfo(YhUserReginfo yhUserReginfo) {
        LOGGER.info("yhUserReginfo={}", yhUserReginfo);
        // 16674674756
        String userCode = yhUserReginfoService.selectMaxUserCode(yhUserReginfo.getUserType());
        if (yhUserReginfo.getLinetime().equals("")) {
            yhUserReginfo.setLinetime(null);
        }
        yhUserReginfoService.updatelinetime(yhUserReginfo.getLinetime(), yhUserReginfo.getEnrolCardId());
        if (yhUserReginfo.getUserType() == 4) {
            yhUserReginfo.newUserCode(userCode);
        } else if (yhUserReginfo.getUserType() == 5) {

            String userCode1 = String.valueOf(Integer.valueOf(userCode) + 1);
            yhUserReginfo.setUserCode(userCode1);

        }
        String userName = yhUserReginfo.getUserName();
        String userPwd = yhUserReginfo.getUserPwd();
        SysPasswordService sysPasswordService = new SysPasswordService();
        sysPasswordService.encryptPassword(userName, userPwd);
        String nameAndPwd = new Md5Hash(userName + userPwd).toHex();
        if (yhUserReginfo.getGender() == 1) {
            yhUserReginfo.setPostCode("https://qny.guyijk.com/xiaochengxu/toxiangnan.png");
        }
        if (yhUserReginfo.getGender() == 2) {
            yhUserReginfo.setPostCode("https://qny.guyijk.com/xiaochengxu/toxiangnv.png");
        }

        yhUserReginfo.setUserPwd(nameAndPwd);
        int addReg = yhUserReginfoService.insertSelective(yhUserReginfo);
        // 添加注册卡表的userCode
        YhUserMakeCard record = new YhUserMakeCard();
        record.setEnrolCardId(yhUserReginfo.getEnrolCardId());
        record.setUserCode(yhUserReginfo.getUserCode());
        yhUserMakeCardService.updateByPrimaryKeySelective(record);
        // 往用户信息扩展表插入一条数据 userCode Evaluateflag
        YhUserEnrolinfo enrolinfo = yhUserEnrolinfoService.selectByPrimaryKeyByUsercode(yhUserReginfo.getUserCode());
        if (enrolinfo == null) {
            YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
            yhUserEnrolinfo.setUserCode(yhUserReginfo.getUserCode());
            yhUserEnrolinfo.setEvaluateflag(0);
            yhUserEnrolinfoService.insertSelective(yhUserEnrolinfo);
        }
        // 查询角色表
        Integer roleId = yhUserRoleService.selectRoleId("用户");
        // 用户角色关联表插入id和code
        YhUserRolePower yhUserRolePower = new YhUserRolePower();
        yhUserRolePower.setRoleid(roleId);
        yhUserRolePower.setUserid(yhUserReginfo.getUserCode());
        yhUserRolePowerService.inssert(yhUserRolePower);
        if (yhUserReginfo.getUserType() == 5) {
            JSONArray jsonArray = new JSONArray();
            JSONObject map = new JSONObject();

            JSONObject operationinfo = new JSONObject();
            operationinfo.put("operator", "1");
            map.put("operationinfo", operationinfo);

            JSONObject mainTable = new JSONObject();
            mainTable.put("xm", yhUserReginfo.getName());
            mainTable.put("xb", yhUserReginfo.getGender() - 1);
            mainTable.put("csrq", DateUtils.dateTime(yhUserReginfo.getBirthDate()));
            mainTable.put("brdh", yhUserReginfo.getMobile());
            mainTable.put("sfzh", yhUserReginfo.getCardidentity());
            //查询客户绑定id
            String oaTreeId = yhOrgTreeService.queryOaTreeId2(yhUserReginfo.getEnrolCardId());
            if (StringUtils.isNotEmpty(oaTreeId)) {
                mainTable.put("gzdw", oaTreeId);
            }
            map.put("mainTable", mainTable);
            jsonArray.add(map);
            String s = RestfulDemo.doAction("http://" + Global.getOaIPAddress() + "/api/cube/restful/interface/saveOrUpdateModeData/sysncPersionInfo", jsonArray);
        }
        return addReg;
    }

    @ApiOperation(value = "往扩展表插入数据")
    @RequestMapping(value = "/yhuserenrolinfo", method = RequestMethod.GET)
    @ResponseBody
    public List<YhUserReginfo> yhuserenrolinfo() {
        List<YhUserReginfo> yhuserenrolinfo = yhUserReginfoService.yhuserenrolinfo();
        YhUserEnrolinfo userEnrolinfo = new YhUserEnrolinfo();
        for (int i = 0; i < yhuserenrolinfo.size(); i++) {
            userEnrolinfo.setUserCode(yhuserenrolinfo.get(i).getUserCode());
            userEnrolinfo.setDocRuserid("0");
            userEnrolinfo.setSexCode(1);
            userEnrolinfo.setHeight(Float.valueOf("0.0000"));
            userEnrolinfo.setWaistc(Float.valueOf("0.0000"));
            userEnrolinfo.setWeight(Float.valueOf("0.0000"));
            userEnrolinfo.setHipc(Float.valueOf("0.0000"));
            userEnrolinfo.setSbp(0);
            userEnrolinfo.setDbp(0);
            userEnrolinfo.setBs(Float.valueOf("0.0000"));
            userEnrolinfo.setCholesterin(Float.valueOf("0.0000"));
            userEnrolinfo.setTring(Float.valueOf("0.0000"));
            userEnrolinfo.setHdl(Float.valueOf("0.0000"));
            userEnrolinfo.setLdl(Float.valueOf("0.0000"));
            userEnrolinfo.setIsDel(2);
            userEnrolinfo.setEvaluateflag(0);
            yhUserEnrolinfoService.insertSelective(userEnrolinfo);
        }
        return yhuserenrolinfo;
    }


    @ApiOperation(value = "查询用户手机号")
    @RequestMapping(value = "/selectMobile", method = RequestMethod.GET)
    @ResponseBody
    public YhUserReginfo selectMobile(String mobile) {
        System.out.println("--" + mobile);
        YhUserReginfo yhUserReginfo2 = yhUserReginfoService.selectMobileAndName2(mobile);
        return yhUserReginfo2;
    }

    @ApiOperation(value = "查询用户名")
    @RequestMapping(value = "/selectName", method = RequestMethod.GET)
    @ResponseBody
    public YhUserReginfo selectName(String userName) {
        System.out.println("--" + userName);
        YhUserReginfo yhUserReginfo = yhUserReginfoService.selectMobileAndName(userName);
        return yhUserReginfo;
    }

    @ApiOperation(value = "查询用户名和手机号")
    @RequestMapping(value = "/selectPhoneAndName", method = RequestMethod.GET)
    @ResponseBody
    public List<YhUserReginfo> selectPhoneAndName(String userName, String mobile) {
        try {
            return yhUserReginfoService.selectPhoneAndName(mobile, userName);
        } catch (Exception e) {
            logger.error("selectPhoneAndName异常，原因是：{}", e.getMessage(), e);
        }
        return null;
    }

    @ApiOperation(value = "查询用户名和手机号")
    @RequestMapping(value = "/selectMobileAndName", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectMobileAndName(String userName, String mobile) {

        System.out.println(userName + "--" + mobile);
        Map<String, Object> map = new HashMap<String, Object>();

        YhUserReginfo yhUserReginfo2 = yhUserReginfoService.selectMobileAndName2(mobile);
        if (yhUserReginfo2 != null) {
            map.put("code", 100);
            map.put("msg", "手机号码已被其他用户使用");
            return map;
        }

        YhUserReginfo yhUserReginfo = yhUserReginfoService.selectMobileAndName(userName);
        if (yhUserReginfo != null) {
            map.put("code", 100);
            map.put("msg", "登录账号已存在，不能重名创建");
            return map;
        }
        map.put("code", 200);
        return map;

    }

    /*
     * public static void main(String[] args) { YhUserReginfo yhUserReginfo =
     * new YhUserReginfo(); yhUserReginfo.setUserType(4);
     * yhUserReginfo.newUserCode("DA00002");
     *
     * System.out.println(yhUserReginfo.getUserCode()); }
     */
    /*
     * public static void main(String[] args) { String nameAndPwd = new Md5Hash(
     * " 测试9" + "123456").toHex(); System.out.println(nameAndPwd); }
     */

    @ApiOperation(value = "查询当前登录用户的用户密码")
    @RequestMapping(value = "/yhUserPwd", method = RequestMethod.GET)
    @ResponseBody
    public YhUserReginfo selectUserPwd(String userCode, String original) {
        YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        String userName = yhUserReginfo.getUserName();
        SysPasswordService sysPasswordService = new SysPasswordService();
        sysPasswordService.encryptPassword(userName, original);
        String nameAndPwd = new Md5Hash(userName + original).toHex();
        // yhUserReginfo.setUserPwd(nameAndPwd);
        YhUserReginfo userReginfo = yhUserReginfoService.selectUserPwd(yhUserReginfo.getUserCode(), nameAndPwd);
        return userReginfo;
    }

    @ApiOperation(value = "查询用户信息")
    @RequestMapping(value = "/yhUserReginfo", method = RequestMethod.GET)
    @ResponseBody
    public YhUserReginfo selectYhUserReginfoData(String userCode) {
        String oneHoursAgoTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
        YhUserReginfo yhUserReginfo = yhUserReginfoService.selectYhUserReginfoData(userCode);
        if (yhUserReginfo.getBirthDate() != null) {
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(yhUserReginfo.getBirthDate());
            rightNow.add(Calendar.HOUR, +1);
            Date dt1 = rightNow.getTime();
            oneHoursAgoTime = sdf.format(dt1);
            yhUserReginfo.setBirthtime(oneHoursAgoTime);
        }
        return yhUserReginfo;
    }

    @ApiOperation(value = "查询登录用户信息")
    @RequestMapping(value = "/loginMessage", method = RequestMethod.GET)
    @ResponseBody
    public YhUserReginfo selectYhUserloginData() {
        String oneHoursAgoTime = "";
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        // 取当前登录用户名
        YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        YhUserReginfo yhUserReginfo2 = yhUserReginfoService.selectYhUserReginfoData(yhUserReginfo.getUserCode());
        Calendar rightNow = Calendar.getInstance();
        if (yhUserReginfo2.getBirthDate() != null) {
            rightNow.setTime(yhUserReginfo2.getBirthDate());
            rightNow.add(Calendar.HOUR, +1);
            Date dt1 = rightNow.getTime();
            oneHoursAgoTime = sdf2.format(dt1);
            yhUserReginfo2.setBirthtime(oneHoursAgoTime);
        }
        System.out.println(yhUserReginfo2 + "当前用户登录的信息");
        return yhUserReginfo2;
    }

    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public int resetPwd(String userCode, String userPwd) {
        YhUserReginfo yhUserReginfo = yhUserReginfoService.selectYhUserReginfoData(userCode);
        String userName = yhUserReginfo.getUserName();
        SysPasswordService sysPasswordService = new SysPasswordService();
        sysPasswordService.encryptPassword(userName, userPwd);
        String nameAndPwd = new Md5Hash(userName + userPwd).toHex();
        yhUserReginfo.setUserPwd(nameAndPwd);
        int pwd = yhUserReginfoService.resetPwd(userCode, nameAndPwd);
        return pwd;

    }

    @ApiOperation(value = "保存用户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public int updateYhUserReginfo(YhUserReginfo YhUserReginfo) {
		/*if (YhUserReginfo.getGender() == 1) {
			YhUserReginfo.setPostCode("https://qny.guyijk.com/xiaochengxu/toxiangnan.png");
		}
		if (YhUserReginfo.getGender() == 2) {
			YhUserReginfo.setPostCode("https://qny.guyijk.com/xiaochengxu/toxiangnv.png");
		}*/
        YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
        yhUserEnrolinfo.setEnergyUptime(new Date());
        yhUserEnrolinfo.setUserCode(YhUserReginfo.getUserCode());
        int update = yhUserReginfoService.updateByPrimaryKeySelective(YhUserReginfo);
        yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
        return update;

    }

    @ApiOperation(value = "修改用户头像")
    @RequestMapping(value = "/updateUserHeader", method = RequestMethod.POST)
    @ResponseBody
    public int updateUserHeader(YhUserReginfo YhUserReginfo) {
        int update = yhUserReginfoService.updateByPrimaryKeySelective(YhUserReginfo);
        return update;

    }

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/updatepwd", method = RequestMethod.POST)
    @ResponseBody
    public int updatepwd(YhUserReginfo YhUserReginfo) {

        String userPwd = YhUserReginfo.getUserPwd();
        YhUserReginfo yhUserReginfo2 = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        String userName = yhUserReginfo2.getUserName();
        SysPasswordService sysPasswordService = new SysPasswordService();
        sysPasswordService.encryptPassword(userName, userPwd);
        String nameAndPwd = new Md5Hash(userName + userPwd).toHex();
        YhUserReginfo.setUserPwd(nameAndPwd);
        YhUserReginfo.setUserCode(yhUserReginfo2.getUserCode());
        int update = yhUserReginfoService.updateByPrimaryKeySelective(YhUserReginfo);
        return update;

    }

    /**
     * 下载模板
     *
     * @param response
     * @param request
     */
    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request) {
        try {
            // 将项目的文件转成了流
            InputStream storeName = this.getClass().getClassLoader()
                    .getResourceAsStream("static/template/import用户档案新建.xls");
            // String storeName =
            // ResourceUtils.getFile("classpath:static/template/import用户档案新建.xls").getPath();
            // vnd.ms-excel浏览器返回的类型
            FileUtils.downloadFile(request, response, storeName, "vnd.ms-excel", "import用户档案新建.xls");
        } catch (Exception ex) {
            LOGGER.error("用户管理-下载导入模板失败，原因是：", ex);
        }

    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importData(HttpServletRequest request, String orgCode) {
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("file");
        InputStream in = null;
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            in = file.getInputStream();
            result = yhUserReginfoService.ajaxUploadExcel(orgCode, in, file, request);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.put("msg", "导入异常！请联系管理员");
            try {
                in.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return result;

    }


    @RequestMapping(value = "/changefinishidoctor", method = RequestMethod.GET)
    @ResponseBody
    public Integer changefinishidoctor() {
        yhUserReginfoService.changefinishidoctor();
        return 1;
    }

    @RequestMapping(value = "/exportXls")
    @ResponseBody
    public void exportXls(@RequestBody Map<String, String[]> data, HttpServletResponse response) throws Exception {
        String[] shuzu = data.get("shuzu");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("注册卡列表");
        YhUserReginfo userReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        String userCode = userReginfo.getUserCode();

        List<YhUserReginfo> exportDataList = yhUserReginfoService.exportYhUserReginfoData1(shuzu);
        // 设置文件名
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = "注册卡导出" + ".xls";// 设置要导出的文件的名字
        // 新增数据行，并且设置单元格数据
        int rowNum = 1;
        // headers表示excel表中第一行的表头
        String[] headers = {"注册卡号", "归属", "状态", "激活码", "用户姓名", "登录账号", "手机号码", "注册时间"};
        // 在excel表中添加表头
        HSSFRow row = sheet.createRow(0);
        // 列数
        /*
         * int cno=exportDataList.get(0).getClass().getDeclaredFields().length;
         */
        // 合并单元格

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
        // 在表中存放查询到的数据放入对应的列
        for (YhUserReginfo yhUserReginfo : exportDataList) {
            String orgName = "";
            if (yhUserReginfo.getOrgCode() != null) {
                orgName = yhOrgTreeService.selectFullOrgName(yhUserReginfo.getOrgCode().toString(), orgName);

            }
            // 创建excel中的行
            HSSFRow row1 = sheet.createRow(rowNum);

            // 在表中存放查询到的数据放入对应的列
            row1.createCell(0).setCellValue(yhUserReginfo.getEnrolCardId());
            row1.createCell(1).setCellValue(orgName);
            row1.createCell(2).setCellValue(yhUserReginfo.getUserCode() == null ? (yhUserReginfo.getKey() == null ? "未用" : "待激活") : "已用");
            row1.createCell(3).setCellValue(yhUserReginfo.getKey());
            row1.createCell(4).setCellValue(yhUserReginfo.getName());
            row1.createCell(5).setCellValue(yhUserReginfo.getUserName());
            row1.createCell(6).setCellValue(yhUserReginfo.getMobile());
            row1.createCell(7)
                    .setCellValue(yhUserReginfo.getRegTime() == null ? "" : sdf.format(yhUserReginfo.getRegTime()));
            rowNum++;
        }

        // 必须在单元格设值以后进行
        // 设置为根据内容自动调整列宽
        for (int k = 0; k < exportDataList.size(); k++) {
            sheet.autoSizeColumn(k);
        }
        // 处理中文不能自动调整列宽的问题
        setSizeColumn(sheet, exportDataList.size());

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        /*
         * response.setHeader("Content-disposition", "attachment;filename=" +
         * fileName);
         */
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

    @Autowired
    YhDiseaseAssessReportMapper yhDiseaseAssessReportMapper;

    @Autowired
    YhExprotDataMapper yhexportdata;

    @RequestMapping(value = "/assessmentExportXls2", method = RequestMethod.GET)
    @ResponseBody
    public void assessmentExportXls2(HttpServletResponse response, String rs) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建标题
        List<List<String>> titles = new ArrayList<List<String>>();
        // 创建sheet名
        List<String> sheetname = new ArrayList<String>();

        YhExportCheck findCheckJsonInfoByRs = yhExprotDataService.findCheckJsonInfoByRs(rs);
        if (null == findCheckJsonInfoByRs) {
            return;
        }

        // 健康评估结果2
        List<yhDiseaseAssessTotalReport2> riskresult2 = yhexportdata.exportassessreport3(rs);
        List<String> titis = null;
        sheetname.add("健康评估问卷");
        if (findCheckJsonInfoByRs.getGender() == 1) {
            titis = Arrays.asList("用户编码", "姓名", "性别", "评估年龄", "单位", "填写时间", "您的居住地", "所住城市空气质量", "高血压", "脑卒中", "冠心病", "严重心律失常", "不稳定心绞痛", "动脉硬化/狭窄/闭塞", "未控制的有症状的心律衰竭", "左心室肥大", "房颤", "其他急性心脏事件", "糖尿病（1或2型）", "痛风急性发作期", "痛风非急性发作期", "慢性支气管炎	", "肺气肿", "肺结核", "肺癌", "骨质疏松症", "既往骨折", "其他限制运动的疾病（如关节炎、足部溃疡、肌肉韧带损伤等）", "引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、慢性营养不良等）", "前列腺炎或肥大", "前列腺癌", "乳腺癌（男性也有患病几率）", "尿路感染", "尿频、尿痛、尿不尽、排尿困难等症状", "尿血或尿路结石", "是否患过类风湿关节炎", "您的居住环境", "您的家族中是否有人患有糖尿病", "您的家族中是否有人患高血压", "您的家族中是否有人患冠心病", "您的家族中是否有人患脑卒中", "您的家族中是否有人患髋部骨折", "您的家族中是否有人患肺癌", "您的家族中是否有人患前列腺癌 ", "您的家族中是否有人患乳腺癌", "您的家族中是否有人患过痛风", "服用降压药", "注射肾上腺皮质激素", "您平均每天吃多少新鲜蔬菜？（1碗炒熟的青菜≈300克）", "您平均每天吃多少新鲜水果？（一个中等大小苹果≈200克）", "您平均每天喝几杯牛奶或酸奶？（240毫升≈1杯）", "您平均每天吃多少奶制品(如奶酪、奶粉等)？（一片奶酪≈40克,一勺奶粉≈5克）", "您通常每周吃几次油炸食品（如油饼、油条、炸糕等）", "您通常每周吃几次点心或甜食（如蛋糕、巧克力、饼干及各种烘培点心等）?", "您通常每周喝几次含糖饮料（如可乐、果汁饮料等）吗？", "您通常每周吃几次咸菜或腌渍食品（如咸鱼）吗？", "您每周吃虾、蟹、蛤蜊等海鲜次数是", "您每天饮茶的次数是", "您喝酒吗？(这里喝酒是指平均每周饮酒1次以上）", "您每周喝几次酒？", "您平均每次喝多少啤酒?", "您是否吸烟", "您的吸烟情况", "您开始戒烟的年龄为 ", "无论在家还是工作场所，您经常吸二手烟吗", "您在过去3个月中是否参加过运动锻炼", "一般情况下您参加运动锻炼的频率是？", "一般情况下您每次锻炼的持续时间是？", "近期（2年）是否新房装修", "最近两周，您感到精神紧张，很难放松吗？", "您的睡眠时间是？", "您的月收入？", "有油烟接触史（长期厨房做菜或为厨师职业）", "长期从事化工、染料、橡胶及印刷等职业", "粉尘接触史（如：矿工、环卫工人等或长期接触过汽油等有害物质）", "石棉接触史（长期接触过石棉）", "您的血脂四项检查结果", "您的肾功能检查结果", "您的其它医学指标检查结果 ");
        } else {
            titis = Arrays.asList("用户编码", "姓名", "性别", "评估年龄", "单位", "填写时间", "您的居住地", "所住城市空气质量", "高血压", "脑卒中", "冠心病", "严重心律失常", "不稳定心绞痛", "动脉硬化/狭窄/闭塞", "未控制的有症状的心律衰竭", "左心室肥大", "房颤", "其他急性心脏事件", "糖尿病（1或2型）", "痛风急性发作期", "痛风非急性发作期", "慢性支气管炎	", "肺气肿", "肺结核", "肺癌", "骨质疏松症", "既往骨折", "其他限制运动的疾病（如关节炎、足部溃疡、肌肉韧带损伤等）", "引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、慢性营养不良等）", "乳腺癌（男性也有患病几率）", "您第一次来月经的年龄是多大？", "您是否生育过？", "您生第一胎年龄是多大？", "您的孩子是母乳喂养吗？", "您的哺乳时间总共有多长时间（累计）", "您是否绝经", "您的绝经年龄是多大？", "您绝经后使用过雌激素替代疗法吗？", "您绝经后体重上升超过5公斤吗？", "您绝经后使用雌激素替代疗法的时长", "长期口服避孕药", "长期使用丰乳产品或做过乳房整形手术", "曾有子宫、卵巢和输卵管等的手术", "是否患过类风湿关节炎", "您的居住环境", "您的家族中是否有人患有糖尿病", "您的家族中是否有人患高血压", "您的家族中是否有人患冠心病", "您的家族中是否有人患脑卒中", "您的家族中是否有人患髋部骨折", "您的家族中是否有人患肺癌", "您的家族中是否有人患乳腺癌", "您的家族中是否有人患过痛风", "服用降压药", "注射肾上腺皮质激素", "您平均每天吃多少新鲜蔬菜？（1碗炒熟的青菜≈300克）", "您平均每天吃多少新鲜水果？（一个中等大小苹果≈200克）", "您平均每天喝几杯牛奶或酸奶？（240毫升≈1杯）", "您平均每天吃多少奶制品(如奶酪、奶粉等)？（一片奶酪≈40克,一勺奶粉≈5克）", "您通常每周吃几次油炸食品（如油饼、油条、炸糕等）", "您通常每周吃几次点心或甜食（如蛋糕、巧克力、饼干及各种烘培点心等）?", "您通常每周喝几次含糖饮料（如可乐、果汁饮料等）吗？", "您通常每周吃几次咸菜或腌渍食品（如咸鱼）吗？", "您每周吃虾、蟹、蛤蜊等海鲜次数是", "您每天饮茶的次数是", "您喝酒吗？(这里喝酒是指平均每周饮酒1次以上）", "您每周喝几次酒？", "您平均每次喝多少啤酒?", "您是否吸烟", "您的吸烟情况", "您开始戒烟的年龄为 ", "无论在家还是工作场所，您经常吸二手烟吗", "您在过去3个月中是否参加过运动锻炼", "一般情况下您参加运动锻炼的频率是？", "一般情况下您每次锻炼的持续时间是？", "近期（2年）是否新房装修", "最近两周，您感到精神紧张，很难放松吗？", "您的睡眠时间是？", "您的月收入？", "有油烟接触史（长期厨房做菜或为厨师职业）", "长期从事化工、染料、橡胶及印刷等职业", "粉尘接触史（如：矿工、环卫工人等或长期接触过汽油等有害物质）", "石棉接触史（长期接触过石棉）", "您的血脂四项检查结果", "您的肾功能检查结果", "您的其它医学指标检查结果 ", "您的月经是否规律");

        }
        titles.add(titis);

        sheetname.add("健康评估结果");
        List<String> titiggu = Arrays.asList("用户编码", "姓名", "性别", "评估年龄", "单位", "评估时间", "BMI", "饮食情况蔬菜水果", "饮食情况高脂饮食", "饮食情况高嘌呤饮食", "饮食情况反式脂肪酸摄入过量", "运动水平", "饮酒情况", "精神压力", "冠心病当前风险（‰）", "冠心病理想风险（‰）", "冠心病风险等级", "脑卒中理想风险（/10万）", "脑卒中理想风险（/10万）", "脑卒中风险等级", "高血压当前风险（‰）", "高血压理想风险（‰）", "高血压风险等级", "糖尿病当前风险（‰）", "糖尿病理想风险（‰）", "糖尿病风险等级", "痛风当前风险（‰）", "痛风理想风险（‰）", "痛风风险等级", "主要部位骨折风险（‰）", "髋部骨折风险（‰）", "骨质疏松症风险等级", "肺癌当前风险（‰）", "肺癌理想风险（‰）", "肺癌风险等级", "前列腺癌当前风险（/10万）", "前列腺癌理想风险（/10万）", "前列腺癌风险等级", "乳腺癌当前风险（‰）", "乳腺癌理想风险（‰）", "乳腺癌风险等级", "血脂异常", "全身性肥胖", "中心性肥胖");
        titles.add(titiggu);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        for (int i = 0; i < sheetname.size(); i++) {
            // 创建sheet
            HSSFSheet sheet = workbook.createSheet(sheetname.get(i));
            // 循环标题（多页结构）

            ExcelUtilQ.createTitle(workbook, sheet, titles.get(i), i);

            if (sheetname.get(i).equals("健康评估问卷")) {

                // 循环表数据
                if (findCheckJsonInfoByRs.getGender() == 1) {

                    Object document = Configuration.defaultConfiguration().jsonProvider()
                            .parse(findCheckJsonInfoByRs.getUser_check_answer());
                    HSSFRow row = sheet.createRow(1);
                    row.setHeightInPoints(18);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(findCheckJsonInfoByRs.getUser_code());
                    row.createCell(1).setCellValue(findCheckJsonInfoByRs.getName());
                    row.createCell(2).setCellValue(findCheckJsonInfoByRs.getGender() == 1 ? "男" : "女");
                    row.createCell(3).setCellValue(GetAge.GetAgeByBirth(findCheckJsonInfoByRs.getBirth_date(),
                            findCheckJsonInfoByRs.getUser_check_time()));
                    row.createCell(4).setCellValue(findCheckJsonInfoByRs.getOrg_Name());
                    row.createCell(5).setCellValue(sdf.format(findCheckJsonInfoByRs.getUser_check_time()));
                    if (JsonPath.read(document, "$.person.jzd").equals("2")) {
                        if (JsonPath.read(document, "$.person.jzhj").equals("1")) {
                            row.createCell(6).setCellValue("北方城市");
                        } else {
                            row.createCell(6).setCellValue("北方农村");
                        }
                    } else {
                        if (JsonPath.read(document, "$.person.jzhj").equals("1")) {
                            row.createCell(6).setCellValue("南方城市");
                        } else {
                            row.createCell(6).setCellValue("南方农村");
                        }
                    }
                    row.createCell(7).setCellValue(Integer.valueOf(JsonPath.read(document, "$.pm25")) == 1 ? "优良" : "差");
                    if (JsonPath.read(document, "$.hbp_his.disFlag") != null && !JsonPath.read(document, "$.hbp_his.disFlag").equals("")) {
                        row.createCell(8).setCellValue(Integer.valueOf(JsonPath.read(document, "$.hbp_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.stroke_his.disFlag") != null && !JsonPath.read(document, "$.stroke_his.disFlag").equals("")) {
                        row.createCell(9).setCellValue(Integer.valueOf(JsonPath.read(document, "$.stroke_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.chd_his.disFlag") != null && !JsonPath.read(document, "$.chd_his.disFlag").equals("")) {
                        row.createCell(10).setCellValue(Integer.valueOf(JsonPath.read(document, "$.chd_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.mi_his.disFlag") != null && !JsonPath.read(document, "$.mi_his.disFlag").equals("")) {
                        row.createCell(11).setCellValue(Integer.valueOf(JsonPath.read(document, "$.mi_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.angina_his.disFlag") != null && !JsonPath.read(document, "$.angina_his.disFlag").equals("")) {
                        row.createCell(12).setCellValue(Integer.valueOf(JsonPath.read(document, "$.angina_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.arterial_status") != null && !JsonPath.read(document, "$.arterial_status").equals("")) {
                        row.createCell(13).setCellValue(Integer.valueOf(JsonPath.read(document, "$.arterial_status").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.ushf_his.disFlag") != null && !JsonPath.read(document, "$.ushf_his.disFlag").equals("")) {
                        row.createCell(14).setCellValue(Integer.valueOf(JsonPath.read(document, "$.ushf_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.lvd_his.disFlag") != null && !JsonPath.read(document, "$.lvd_his.disFlag").equals("")) {
                        row.createCell(15).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lvd_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.af_his.disFlag") != null && !JsonPath.read(document, "$.af_his.disFlag").equals("")) {
                        row.createCell(16).setCellValue(Integer.valueOf(JsonPath.read(document, "$.af_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.ace_his.disFlag") != null && !JsonPath.read(document, "$.ace_his.disFlag").equals("")) {
                        row.createCell(17).setCellValue(Integer.valueOf(JsonPath.read(document, "$.ace_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.diab2_his.disFlag") != null && !JsonPath.read(document, "$.diab2_his.disFlag").equals("")) {
                        row.createCell(18).setCellValue(Integer.valueOf(JsonPath.read(document, "$.diab2_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.gae_his.disFlag") != null && !JsonPath.read(document, "$.gae_his.disFlag").equals("")) {
                        row.createCell(19).setCellValue(Integer.valueOf(JsonPath.read(document, "$.gae_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.gna_his.disFlag") != null && !JsonPath.read(document, "$.gna_his.disFlag").equals("")) {
                        row.createCell(20).setCellValue(Integer.valueOf(JsonPath.read(document, "$.gna_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.bronch_his.disFlag") != null && !JsonPath.read(document, "$.bronch_his.disFlag").equals("")) {
                        row.createCell(21).setCellValue(Integer.valueOf(JsonPath.read(document, "$.bronch_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.emphy_his.disFlag") != null && !JsonPath.read(document, "$.emphy_his.disFlag").equals("")) {
                        row.createCell(22).setCellValue(Integer.valueOf(JsonPath.read(document, "$.emphy_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.decline_his.disFlag") != null && !JsonPath.read(document, "$.decline_his.disFlag").equals("")) {
                        row.createCell(23).setCellValue(Integer.valueOf(JsonPath.read(document, "$.decline_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.lc_his.disFlag") != null && !JsonPath.read(document, "$.lc_his.disFlag").equals("")) {
                        row.createCell(24).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lc_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.op_his.disFlag") != null && !JsonPath.read(document, "$.op_his.disFlag").equals("")) {
                        row.createCell(25).setCellValue(Integer.valueOf(JsonPath.read(document, "$.op_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.fracture_his.disFlag") != null && !JsonPath.read(document, "$.fracture_his.disFlag").equals("")) {
                        row.createCell(26).setCellValue(Integer.valueOf(JsonPath.read(document, "$.fracture_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.sport_his.disFlag") != null && !JsonPath.read(document, "$.sport_his.disFlag").equals("")) {
                        row.createCell(27).setCellValue(Integer.valueOf(JsonPath.read(document, "$.sport_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.lc_his.disFlag") != null && !JsonPath.read(document, "$.lc_his.disFlag").equals("")) {
                        row.createCell(28).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lc_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.prostate_disease.disFlag") != null && !JsonPath.read(document, "$.prostate_disease.disFlag").equals("")) {
                        row.createCell(29).setCellValue(Integer.valueOf(JsonPath.read(document, "$.prostate_disease.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.pc_his.disFlag") != null && !JsonPath.read(document, "$.pc_his.disFlag").equals("")) {
                        row.createCell(30).setCellValue(Integer.valueOf(JsonPath.read(document, "$.pc_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.bc_his.disFlag") != null && !JsonPath.read(document, "$.bc_his.disFlag").equals("")) {
                        row.createCell(31).setCellValue(Integer.valueOf(JsonPath.read(document, "$.bc_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.urinary_tract_infection") != null && !JsonPath.read(document, "$.urinary_tract_infection").equals("")) {
                        row.createCell(32).setCellValue(Integer.valueOf(JsonPath.read(document, "$.urinary_tract_infection").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.urine_symptom") != null && !JsonPath.read(document, "$.urine_symptom").equals("")) {
                        row.createCell(33).setCellValue(Integer.valueOf(JsonPath.read(document, "$.urine_symptom").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.hematuresis") != null && !JsonPath.read(document, "$.hematuresis").equals("")) {
                        row.createCell(34).setCellValue(Integer.valueOf(JsonPath.read(document, "$.hematuresis").toString()) == 1 ? "有" : "-");
                    }

                    if (JsonPath.read(document, "$.ra_his.disFlag") != null && !JsonPath.read(document, "$.ra_his.disFlag").equals("")) {
                        row.createCell(35).setCellValue(Integer.valueOf(JsonPath.read(document, "$.ra_his.disFlag").toString().toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.person.living_envir") != null && !JsonPath.read(document, "$.person.living_envir").equals("")) {
                        row.createCell(36).setCellValue(Integer.valueOf(JsonPath.read(document, "$.person.living_envir").toString()) == 1 ? "寒冷潮湿" : "不寒冷不潮湿");
                    }
                    if ((JsonPath.read(document, "$.family_diab_f") != null && !JsonPath.read(document, "$.family_diab_f").equals("") && JsonPath.read(document, "$.family_diab_f").equals("1")) || (JsonPath.read(document, "$.family_diab_m") != null && !JsonPath.read(document, "$.family_diab_m").equals("") && JsonPath.read(document, "$.family_diab_m").equals("1")) || (JsonPath.read(document, "$.family_diab_bs") != null && !JsonPath.read(document, "$.family_diab_bs").equals("") && JsonPath.read(document, "$.family_diab_bs").equals("1")) || (JsonPath.read(document, "$.family_diab_ch") != null && !JsonPath.read(document, "$.family_diab_ch").equals("") && JsonPath.read(document, "$.family_diab_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_diab_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_diab_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_diab_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_diab_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(37).setCellValue(html);
                    } else {
                        row.createCell(37).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_hbp_f") != null && !JsonPath.read(document, "$.family_hbp_f").equals("") && JsonPath.read(document, "$.family_hbp_f").equals("1")) || (JsonPath.read(document, "$.family_hbp_m") != null && !JsonPath.read(document, "$.family_hbp_m").equals("") && JsonPath.read(document, "$.family_hbp_m").equals("1")) || (JsonPath.read(document, "$.family_hbp_bs") != null && !JsonPath.read(document, "$.family_hbp_bs").equals("") && JsonPath.read(document, "$.family_hbp_bs").equals("1")) || (JsonPath.read(document, "$.family_hbp_ch") != null && !JsonPath.read(document, "$.family_hbp_ch").equals("") && JsonPath.read(document, "$.family_hbp_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hbp_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hbp_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hbp_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hbp_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(38).setCellValue(html);
                    } else {
                        row.createCell(38).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_chd_f") != null && !JsonPath.read(document, "$.family_chd_f").equals("") && JsonPath.read(document, "$.family_chd_f").equals("1")) || (JsonPath.read(document, "$.family_chd_m") != null && !JsonPath.read(document, "$.family_chd_m").equals("") && JsonPath.read(document, "$.family_chd_m").equals("1")) || (JsonPath.read(document, "$.family_chd_bs") != null && !JsonPath.read(document, "$.family_chd_bs").equals("") && JsonPath.read(document, "$.family_chd_bs").equals("1")) || (JsonPath.read(document, "$.family_chd_ch") != null && !JsonPath.read(document, "$.family_chd_ch").equals("") && JsonPath.read(document, "$.family_chd_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_chd_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_chd_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_chd_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_chd_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(39).setCellValue(html);
                    } else {
                        row.createCell(39).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_stroke_f") != null && !JsonPath.read(document, "$.family_stroke_f").equals("") && JsonPath.read(document, "$.family_stroke_f").equals("1")) || (JsonPath.read(document, "$.family_stroke_m") != null && !JsonPath.read(document, "$.family_stroke_m").equals("") && JsonPath.read(document, "$.family_stroke_m").equals("1")) || (JsonPath.read(document, "$.family_stroke_bs") != null && !JsonPath.read(document, "$.family_stroke_bs").equals("") && JsonPath.read(document, "$.family_stroke_bs").equals("1")) || (JsonPath.read(document, "$.family_stroke_ch") != null && !JsonPath.read(document, "$.family_stroke_ch").equals("") && JsonPath.read(document, "$.family_stroke_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_stroke_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_stroke_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_stroke_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_stroke_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(40).setCellValue(html);
                    } else {
                        row.createCell(40).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_hf_f") != null && !JsonPath.read(document, "$.family_hf_f").equals("") && JsonPath.read(document, "$.family_hf_f").equals("1")) || (JsonPath.read(document, "$.family_hf_m") != null && !JsonPath.read(document, "$.family_hf_m").equals("") && JsonPath.read(document, "$.family_hf_m").equals("1")) || (JsonPath.read(document, "$.family_hf_bs") != null && !JsonPath.read(document, "$.family_hf_bs").equals("") && JsonPath.read(document, "$.family_hf_bs").equals("1")) || (JsonPath.read(document, "$.family_hf_ch") != null && !JsonPath.read(document, "$.family_hf_ch").equals("") && JsonPath.read(document, "$.family_hf_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hf_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hf_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hf_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hf_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(41).setCellValue(html);
                    } else {
                        row.createCell(41).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_lung_f") != null && !JsonPath.read(document, "$.family_lung_f").equals("") && JsonPath.read(document, "$.family_lung_f").equals("1")) || (JsonPath.read(document, "$.family_lung_m") != null && !JsonPath.read(document, "$.family_lung_m").equals("") && JsonPath.read(document, "$.family_lung_m").equals("1")) || (JsonPath.read(document, "$.family_lung_bs") != null && !JsonPath.read(document, "$.family_lung_bs").equals("") && JsonPath.read(document, "$.family_lung_bs").equals("1")) || (JsonPath.read(document, "$.family_lung_ch") != null && !JsonPath.read(document, "$.family_lung_ch").equals("") && JsonPath.read(document, "$.family_lung_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_lung_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_lung_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_lung_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_lung_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(42).setCellValue(html);
                    } else {
                        row.createCell(42).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_pro_f") != null && !JsonPath.read(document, "$.family_pro_f").equals("") && JsonPath.read(document, "$.family_pro_f").equals("1")) || (JsonPath.read(document, "$.family_pro_b") != null && !JsonPath.read(document, "$.family_pro_b").equals("") && JsonPath.read(document, "$.family_pro_b").equals("1")) || (JsonPath.read(document, "$.family_pro_s") != null && !JsonPath.read(document, "$.family_pro_s").equals("") && JsonPath.read(document, "$.family_pro_s").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_pro_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_pro_b").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_pro_s").toString()) == 1) {
                            html += "儿子;";
                        }
                        html += "患病";
                        row.createCell(43).setCellValue(html);
                    } else {
                        row.createCell(43).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_breast_m") != null && !JsonPath.read(document, "$.family_breast_m").equals("") && JsonPath.read(document, "$.family_breast_m").equals("1")) || (JsonPath.read(document, "$.family_breast_s") != null && !JsonPath.read(document, "$.family_breast_s").equals("") && JsonPath.read(document, "$.family_breast_s").equals("1")) || (JsonPath.read(document, "$.family_breast_d") != null && !JsonPath.read(document, "$.family_breast_d").equals("") && JsonPath.read(document, "$.family_breast_d").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_breast_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_breast_s").toString()) == 1) {
                            html += "姐妹;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_breast_d").toString()) == 1) {
                            html += "女儿;";
                        }
                        html += "患病";
                        row.createCell(44).setCellValue(html);
                    } else {
                        row.createCell(44).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_gout_f") != null && !JsonPath.read(document, "$.family_gout_f").equals("") && JsonPath.read(document, "$.family_gout_f").equals("1")) || (JsonPath.read(document, "$.family_gout_m") != null && !JsonPath.read(document, "$.family_gout_m").equals("") && JsonPath.read(document, "$.family_gout_m").equals("1")) || (JsonPath.read(document, "$.family_gout_bs") != null && !JsonPath.read(document, "$.family_gout_bs").equals("") && JsonPath.read(document, "$.family_gout_bs").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_gout_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_gout_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_gout_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_gout_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(45).setCellValue(html);
                    } else {
                        row.createCell(45).setCellValue("否");
                    }
                    if (JsonPath.read(document, "$.med_hbp") != null && !JsonPath.read(document, "$.med_hbp").equals("")) {
                        row.createCell(46).setCellValue(Integer.valueOf(JsonPath.read(document, "$.med_hbp").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.med_ach") != null && !JsonPath.read(document, "$.med_ach").equals("")) {
                        row.createCell(47).setCellValue(Integer.valueOf(JsonPath.read(document, "$.med_ach").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.food_vegetables") != null && !JsonPath.read(document, "$.food_vegetables").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString()) == 1) {
                            row.createCell(48).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString()) == 2) {
                            row.createCell(48).setCellValue("少于50克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString()) == 3) {
                            row.createCell(48).setCellValue("50-75克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString()) == 4) {
                            row.createCell(48).setCellValue("150-300克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString()) == 5) {
                            row.createCell(48).setCellValue("300克以上");
                        }

                    }
                    if (JsonPath.read(document, "$.food_fruits") != null && !JsonPath.read(document, "$.food_fruits").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString()) == 1) {
                            row.createCell(49).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString()) == 2) {
                            row.createCell(49).setCellValue("少于50克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString()) == 3) {
                            row.createCell(49).setCellValue("50-100克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString()) == 4) {
                            row.createCell(49).setCellValue("100-200克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString()) == 5) {
                            row.createCell(49).setCellValue("200克以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_milk") != null && !JsonPath.read(document, "$.food_milk").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_milk").toString()) == 1) {
                            row.createCell(50).setCellValue("不喝");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_milk").toString()) == 2) {
                            row.createCell(50).setCellValue("少于0.5杯");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_milk").toString()) == 3) {
                            row.createCell(50).setCellValue("0.5-1杯");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_milk").toString()) == 4) {
                            row.createCell(50).setCellValue("1-2杯");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_milk").toString()) == 5) {
                            row.createCell(50).setCellValue("2杯以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_dairy") != null && !JsonPath.read(document, "$.food_dairy").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString()) == 1) {
                            row.createCell(51).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString()) == 2) {
                            row.createCell(51).setCellValue("少于10克 ");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString()) == 3) {
                            row.createCell(51).setCellValue("10-40克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString()) == 4) {
                            row.createCell(51).setCellValue("40-80克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString()) == 5) {
                            row.createCell(51).setCellValue("80克以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_fired") != null && !JsonPath.read(document, "$.food_fired").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_fired").toString()) == 1) {
                            row.createCell(52).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fired").toString()) == 2) {
                            row.createCell(52).setCellValue("1-4次 ");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fired").toString()) == 3) {
                            row.createCell(52).setCellValue("5-7次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fired").toString()) == 4) {
                            row.createCell(52).setCellValue("7次以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_sweet") != null && !JsonPath.read(document, "$.food_sweet").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString()) == 1) {
                            row.createCell(53).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString()) == 2) {
                            row.createCell(53).setCellValue("1-4次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString()) == 3) {
                            row.createCell(53).setCellValue("5-7次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString()) == 4) {
                            row.createCell(53).setCellValue("7次以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_sugary") != null && !JsonPath.read(document, "$.food_sugary").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString()) == 1) {
                            row.createCell(54).setCellValue("不喝");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString()) == 2) {
                            row.createCell(54).setCellValue("1-4次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString()) == 3) {
                            row.createCell(54).setCellValue("5-7次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString()) == 4) {
                            row.createCell(54).setCellValue("7次以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_pickled") != null && !JsonPath.read(document, "$.food_pickled").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString()) == 1) {
                            row.createCell(55).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString()) == 2) {
                            row.createCell(55).setCellValue("1-4次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString()) == 3) {
                            row.createCell(55).setCellValue("5-7次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString()) == 4) {
                            row.createCell(55).setCellValue("7次以上");
                        }

                    }
                    if (JsonPath.read(document, "$.seafood_cnt") != null && !JsonPath.read(document, "$.seafood_cnt").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString()) == 1) {
                            row.createCell(56).setCellValue("几乎不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString()) == 2) {
                            row.createCell(56).setCellValue("1~2次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString()) == 3) {
                            row.createCell(56).setCellValue("2~4次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString()) == 4) {
                            row.createCell(56).setCellValue("4次以上");
                        }

                    }
                    if (JsonPath.read(document, "$.drink_tea_cnt") != null && !JsonPath.read(document, "$.drink_tea_cnt").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.drink_tea_cnt").toString()) == 1) {
                            row.createCell(57).setCellValue("几乎不饮茶");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.drink_tea_cnt").toString()) == 2) {
                            row.createCell(57).setCellValue("不超过1杯");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.drink_tea_cnt").toString()) == 3) {
                            row.createCell(57).setCellValue("1杯以上");
                        }

                    }
                    if (JsonPath.read(document, "$.food_drink") != null && !JsonPath.read(document, "$.food_drink").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_drink").toString()) == 1) {
                            row.createCell(58).setCellValue("喝酒");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink").toString()) == 2) {
                            row.createCell(58).setCellValue("不喝");
                        }

                    }
                    if (JsonPath.read(document, "$.food_drink_times") != null && !JsonPath.read(document, "$.food_drink_times").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_drink_times").toString()) == 1) {
                            row.createCell(59).setCellValue("1-2次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink_times").toString()) == 2) {
                            row.createCell(59).setCellValue("3-5次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink_times").toString()) == 3) {
                            row.createCell(59).setCellValue(">5次");
                        }
                    }
                    if (JsonPath.read(document, "$.food_drink_beer") != null && !JsonPath.read(document, "$.food_drink_beer").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString()) == 1) {
                            row.createCell(60).setCellValue("少于300毫升");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString()) == 2) {
                            row.createCell(60).setCellValue("300-600毫升");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString()) == 3) {
                            row.createCell(60).setCellValue("600-900毫升");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString()) == 4) {
                            row.createCell(60).setCellValue("900毫升以上");
                        }
                    }
                    if (JsonPath.read(document, "$.smoke_status") != null && !JsonPath.read(document, "$.smoke_status").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.smoke_status").toString()) == 1) {
                            row.createCell(61).setCellValue("吸烟");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.smoke_status").toString()) == 2) {
                            row.createCell(61).setCellValue("不吸烟");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.smoke_status").toString()) == 3) {
                            row.createCell(61).setCellValue("已戒烟");
                        }
                    }
                    if (JsonPath.read(document, "$.smoke_amount") != null && !JsonPath.read(document, "$.smoke_amount").equals("")) {
                        row.createCell(62).setCellValue(Integer.valueOf(JsonPath.read(document, "$.smoke_amount").toString()) == 1 ? "有" : "无");
                    }
                    if (JsonPath.read(document, "$.smoke_end_age") != null && !JsonPath.read(document, "$.smoke_end_age").equals("")) {
                        row.createCell(63).setCellValue(Integer.valueOf(JsonPath.read(document, "$.smoke_end_age").toString()) == 1 ? "有" : "无");
                    }
                    if (JsonPath.read(document, "$.smoke_sencod") != null && !JsonPath.read(document, "$.smoke_sencod").equals("")) {
                        row.createCell(64).setCellValue(Integer.valueOf(JsonPath.read(document, "$.smoke_sencod").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.sport_in") != null && !JsonPath.read(document, "$.sport_in").equals("")) {
                        row.createCell(65).setCellValue(Integer.valueOf(JsonPath.read(document, "$.sport_in").toString()) == 1 ? "参加过" : "没参加过");
                    }
                    if (JsonPath.read(document, "$.sport_freq") != null && !JsonPath.read(document, "$.sport_freq").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString()) == 1) {
                            row.createCell(66).setCellValue("每月少于1次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString()) == 2) {
                            row.createCell(66).setCellValue("每月至少1次，但每周不足1次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString()) == 3) {
                            row.createCell(66).setCellValue("每周1-2次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString()) == 4) {
                            row.createCell(66).setCellValue("每周3-4次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString()) == 5) {
                            row.createCell(66).setCellValue("每周5次以上");
                        }

                    }
                    if (JsonPath.read(document, "$.sport_time") != null && !JsonPath.read(document, "$.sport_time").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.sport_time").toString()) == 1) {
                            row.createCell(67).setCellValue("少于30分钟");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_time").toString()) == 2) {
                            row.createCell(67).setCellValue("30-60分钟");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_time").toString()) == 3) {
                            row.createCell(67).setCellValue("60分钟以上");
                        }
                    }
                    if (JsonPath.read(document, "$.decorate_house") != null && !JsonPath.read(document, "$.decorate_house").equals("")) {
                        row.createCell(68).setCellValue(Integer.valueOf(JsonPath.read(document, "$.decorate_house").toString()) == 1 ? "有" : "无");
                    }
                    if (JsonPath.read(document, "$.psy_nervous") != null && !JsonPath.read(document, "$.psy_nervous").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.psy_nervous").toString()) == 1) {
                            row.createCell(69).setCellValue("没有");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.psy_nervous").toString()) == 2) {
                            row.createCell(69).setCellValue("偶尔");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.psy_nervous").toString()) == 3) {
                            row.createCell(69).setCellValue("经常");
                        }
                    }
                    if (JsonPath.read(document, "$.bedtime") != null && !JsonPath.read(document, "$.bedtime").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.bedtime").toString()) == 1) {
                            row.createCell(70).setCellValue("每天8小时以上");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.bedtime").toString()) == 2) {
                            row.createCell(70).setCellValue("每天6~8小时");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.bedtime").toString()) == 3) {
                            row.createCell(70).setCellValue("每天不足6小时");
                        }
                    }
                    if (JsonPath.read(document, "$.person.income") != null && !JsonPath.read(document, "$.person.income").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.person.income").toString()) == 1) {
                            row.createCell(71).setCellValue("小于1000元");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.person.income").toString()) == 2) {
                            row.createCell(71).setCellValue("1000~2999元");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.person.income").toString()) == 3) {
                            row.createCell(71).setCellValue("3000~4999元");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.person.income").toString()) == 4) {
                            row.createCell(71).setCellValue("5000元以上");
                        }

                    }
//					if( JsonPath.read(document, "$.hw_check.xt_exception")!=null && ! JsonPath.read(document, "$.hw_check.xt_exception").equals("")) {
//						row.createCell(85).setCellValue(Integer.valueOf(JsonPath.read(document, "$.hw_check.xt_exception").toString())==0?"正常":"异常");
//					}
                    if (JsonPath.read(document, "$.lampblack_contact.disFlag") != null && !JsonPath.read(document, "$.lampblack_contact.disFlag").equals("")) {
                        row.createCell(72).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lampblack_contact.disFlag").toString()) == 1 ? "有" : "无");
                    }
                    if (JsonPath.read(document, "$.chemical_occupation") != null && !JsonPath.read(document, "$.chemical_occupation").equals("")) {
                        row.createCell(73).setCellValue(Integer.valueOf(JsonPath.read(document, "$.chemical_occupation").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.dust_exposure_his.disFlag") != null && !JsonPath.read(document, "$.dust_exposure_his.disFlag").equals("")) {
                        row.createCell(74).setCellValue(Integer.valueOf(JsonPath.read(document, "$.dust_exposure_his.disFlag").toString()) == 1 ? "有" : "无");
                    }
                    if (JsonPath.read(document, "$.asbestos_contact_his.disFlag") != null && !JsonPath.read(document, "$.asbestos_contact_his.disFlag").equals("")) {
                        row.createCell(75).setCellValue(Integer.valueOf(JsonPath.read(document, "$.asbestos_contact_his.disFlag").toString()) == 1 ? "有" : "无");
                    }
                    row.createCell(76).setCellValue("总胆固醇（单位mmol/l）" + JsonPath.read(document, "$.cholesterol").toString() + ";甘油三酯（单位mmol/l）" + JsonPath.read(document, "$.tg").toString() + "；高密度脂蛋白胆固醇（单位mmol/l）" + JsonPath.read(document, "$.hdlc").toString() + "；低密度脂蛋白胆固醇（单位mmol/l）" + JsonPath.read(document, "$.ldlc").toString());
                    row.createCell(77).setCellValue("血尿酸（单位μmol/L）" + JsonPath.read(document, "$.bua").toString() + ";血肌酐（单位mmol/L）" + JsonPath.read(document, "$.serum_creatinine").toString());
                    row.createCell(78).setCellValue("空腹血糖（单位mmol/l）" + JsonPath.read(document, "$.fbg").toString() + ";收缩压（单位mmHg）" + JsonPath.read(document, "$.sbp").toString() + "；舒张压（单位mmHg）" + JsonPath.read(document, "$.dbp").toString() + "；血常规检查白细胞（单位/L）" + JsonPath.read(document, "$.wbc").toString() + "；骨密度检查指标-T值" + JsonPath.read(document, "$.t_score").toString());


                } else {

                    Object document = Configuration.defaultConfiguration().jsonProvider()
                            .parse(findCheckJsonInfoByRs.getUser_check_answer());
                    HSSFRow row = sheet.createRow(1);
                    row.setHeightInPoints(18);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(findCheckJsonInfoByRs.getUser_code());
                    row.createCell(1).setCellValue(findCheckJsonInfoByRs.getName());
                    row.createCell(2).setCellValue(findCheckJsonInfoByRs.getGender() == 1 ? "男" : "女");
                    row.createCell(3).setCellValue(GetAge.GetAgeByBirth(findCheckJsonInfoByRs.getBirth_date(),
                            findCheckJsonInfoByRs.getUser_check_time()));
                    row.createCell(4).setCellValue(findCheckJsonInfoByRs.getOrg_Name());
                    row.createCell(5).setCellValue(sdf.format(findCheckJsonInfoByRs.getUser_check_time()));
                    if (JsonPath.read(document, "$.person.jzd").equals("2")) {
                        if (JsonPath.read(document, "$.person.jzhj").equals("1")) {
                            row.createCell(6).setCellValue("北方城市");
                        } else {
                            row.createCell(6).setCellValue("北方农村");
                        }
                    } else {
                        if (JsonPath.read(document, "$.person.jzhj").equals("1")) {
                            row.createCell(6).setCellValue("南方城市");
                        } else {
                            row.createCell(6).setCellValue("南方农村");
                        }
                    }
                    row.createCell(7).setCellValue(Integer.valueOf(JsonPath.read(document, "$.pm25")) == 1 ? "优良" : "差");
                    if (JsonPath.read(document, "$.hbp_his.disFlag") != null && !JsonPath.read(document, "$.hbp_his.disFlag").equals("")) {
                        row.createCell(8).setCellValue(Integer.valueOf(JsonPath.read(document, "$.hbp_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.stroke_his.disFlag") != null && !JsonPath.read(document, "$.stroke_his.disFlag").equals("")) {
                        row.createCell(9).setCellValue(Integer.valueOf(JsonPath.read(document, "$.stroke_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.chd_his.disFlag") != null && !JsonPath.read(document, "$.chd_his.disFlag").equals("")) {
                        row.createCell(10).setCellValue(Integer.valueOf(JsonPath.read(document, "$.chd_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.mi_his.disFlag") != null && !JsonPath.read(document, "$.mi_his.disFlag").equals("")) {
                        row.createCell(11).setCellValue(Integer.valueOf(JsonPath.read(document, "$.mi_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.angina_his.disFlag") != null && !JsonPath.read(document, "$.angina_his.disFlag").equals("")) {
                        row.createCell(12).setCellValue(Integer.valueOf(JsonPath.read(document, "$.angina_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.arterial_status") != null && !JsonPath.read(document, "$.arterial_status").equals("")) {
                        row.createCell(13).setCellValue(Integer.valueOf(JsonPath.read(document, "$.arterial_status").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.ushf_his.disFlag") != null && !JsonPath.read(document, "$.ushf_his.disFlag").equals("")) {
                        row.createCell(14).setCellValue(Integer.valueOf(JsonPath.read(document, "$.ushf_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.lvd_his.disFlag") != null && !JsonPath.read(document, "$.lvd_his.disFlag").equals("")) {
                        row.createCell(15).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lvd_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.af_his.disFlag") != null && !JsonPath.read(document, "$.af_his.disFlag").equals("")) {
                        row.createCell(16).setCellValue(Integer.valueOf(JsonPath.read(document, "$.af_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.ace_his.disFlag") != null && !JsonPath.read(document, "$.ace_his.disFlag").equals("")) {
                        row.createCell(17).setCellValue(Integer.valueOf(JsonPath.read(document, "$.ace_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.diab2_his.disFlag") != null && !JsonPath.read(document, "$.diab2_his.disFlag").equals("")) {
                        row.createCell(18).setCellValue(Integer.valueOf(JsonPath.read(document, "$.diab2_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.gae_his.disFlag") != null && !JsonPath.read(document, "$.gae_his.disFlag").equals("")) {
                        row.createCell(19).setCellValue(Integer.valueOf(JsonPath.read(document, "$.gae_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.gna_his.disFlag") != null && !JsonPath.read(document, "$.gna_his.disFlag").equals("")) {
                        row.createCell(20).setCellValue(Integer.valueOf(JsonPath.read(document, "$.gna_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.bronch_his.disFlag") != null && !JsonPath.read(document, "$.bronch_his.disFlag").equals("")) {
                        row.createCell(21).setCellValue(Integer.valueOf(JsonPath.read(document, "$.bronch_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.emphy_his.disFlag") != null && !JsonPath.read(document, "$.emphy_his.disFlag").equals("")) {
                        row.createCell(22).setCellValue(Integer.valueOf(JsonPath.read(document, "$.emphy_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.decline_his.disFlag") != null && !JsonPath.read(document, "$.decline_his.disFlag").equals("")) {
                        row.createCell(23).setCellValue(Integer.valueOf(JsonPath.read(document, "$.decline_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.lc_his.disFlag") != null && !JsonPath.read(document, "$.lc_his.disFlag").equals("")) {
                        row.createCell(24).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lc_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.op_his.disFlag") != null && !JsonPath.read(document, "$.op_his.disFlag").equals("")) {
                        row.createCell(25).setCellValue(Integer.valueOf(JsonPath.read(document, "$.op_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.fracture_his.disFlag") != null && !JsonPath.read(document, "$.fracture_his.disFlag").equals("")) {
                        row.createCell(26).setCellValue(Integer.valueOf(JsonPath.read(document, "$.fracture_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.sport_his.disFlag") != null && !JsonPath.read(document, "$.sport_his.disFlag").equals("")) {
                        row.createCell(27).setCellValue(Integer.valueOf(JsonPath.read(document, "$.sport_his.disFlag").toString()) == 1 ? "有" : "-");
                    }
                    if (JsonPath.read(document, "$.lc_his.disFlag") != null && !JsonPath.read(document, "$.lc_his.disFlag").equals("")) {
                        row.createCell(28).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lc_his.disFlag").toString()) == 1 ? "有" : "-");
                    }

                    if (JsonPath.read(document, "$.bc_his.disFlag") != null && !JsonPath.read(document, "$.bc_his.disFlag").equals("")) {
                        row.createCell(29).setCellValue(Integer.valueOf(JsonPath.read(document, "$.bc_his.disFlag").toString()) == 1 ? "有" : "-");
                    }

                    if (JsonPath.read(document, "$.female_menstruation") != null && !JsonPath.read(document, "$.female_menstruation").equals("")) {
                        row.createCell(30).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_menstruation").toString()) == 1 ? "<12岁" : "≥12岁");
                    }
                    if (JsonPath.read(document, "$.female_birth") != null && !JsonPath.read(document, "$.female_birth").equals("")) {
                        row.createCell(31).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_birth").toString()) == 1 ? "是" : Integer.valueOf(JsonPath.read(document, "$.female_birth")) == 2 ? "产后超过3个月" : "否");
                    }
                    if (JsonPath.read(document, "$.female_tire") != null && !JsonPath.read(document, "$.female_tire").equals("")) {
                        row.createCell(32).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_tire").toString()) == 1 ? "<30岁" : "≥30岁");
                    }
                    if (JsonPath.read(document, "$.female_breast") != null && !JsonPath.read(document, "$.female_breast").equals("")) {
                        row.createCell(33).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_breast").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.female_breast_time") != null && !JsonPath.read(document, "$.female_breast_time").equals("")) {
                        row.createCell(34).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_breast_time").toString()) == 1 ? "<12个月" : "≥12个月");
                    }

                    if (JsonPath.read(document, "$.female_menopause") != null && !JsonPath.read(document, "$.female_menopause").equals("")) {
                        row.createCell(35).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_menopause").toString()) == 1 ? "是" : "否");
                    }

                    if (JsonPath.read(document, "$.female_menopause_age") != null && !JsonPath.read(document, "$.female_menopause_age").equals("")) {
                        row.createCell(36).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_menopause_age").toString()) == 1 ? "<55岁" : "≥55岁");
                    }

                    if (JsonPath.read(document, "$.has_med_ert") != null && !JsonPath.read(document, "$.has_med_ert").equals("")) {
                        row.createCell(37).setCellValue(Integer.valueOf(JsonPath.read(document, "$.has_med_ert").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.weight_up") != null && !JsonPath.read(document, "$.weight_up").equals("")) {
                        row.createCell(38).setCellValue(Integer.valueOf(JsonPath.read(document, "$.weight_up").toString()) == 1 ? "是" : "否");
                    }


                    if (JsonPath.read(document, "$.med_ert") != null && !JsonPath.read(document, "$.med_ert").equals("")) {
                        row.createCell(39).setCellValue(Integer.valueOf(JsonPath.read(document, "$.med_ert").toString()) == 1 ? "<5年" : ">=5年");
                    }
                    if (JsonPath.read(document, "$.med_use_pill") != null && !JsonPath.read(document, "$.med_use_pill").equals("")) {
                        row.createCell(40).setCellValue(Integer.valueOf(JsonPath.read(document, "$.med_use_pill").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.breast_plastic_surgery") != null && !JsonPath.read(document, "$.breast_plastic_surgery").equals("")) {
                        row.createCell(41).setCellValue(Integer.valueOf(JsonPath.read(document, "$.breast_plastic_surgery").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.gynecology_operation") != null && !JsonPath.read(document, "$.gynecology_operation").equals("")) {
                        row.createCell(42).setCellValue(Integer.valueOf(JsonPath.read(document, "$.gynecology_operation").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.ra_his.disFlag") != null && !JsonPath.read(document, "$.ra_his.disFlag").equals("")) {
                        row.createCell(43).setCellValue(Integer.valueOf(JsonPath.read(document, "$.ra_his.disFlag").toString().toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.person.living_envir") != null && !JsonPath.read(document, "$.person.living_envir").equals("")) {
                        row.createCell(44).setCellValue(Integer.valueOf(JsonPath.read(document, "$.person.living_envir").toString()) == 1 ? "寒冷潮湿" : "不寒冷不潮湿");
                    }
                    if ((JsonPath.read(document, "$.family_diab_f") != null && !JsonPath.read(document, "$.family_diab_f").equals("") && JsonPath.read(document, "$.family_diab_f").equals("1")) || (JsonPath.read(document, "$.family_diab_m") != null && !JsonPath.read(document, "$.family_diab_m").equals("") && JsonPath.read(document, "$.family_diab_m").equals("1")) || (JsonPath.read(document, "$.family_diab_bs") != null && !JsonPath.read(document, "$.family_diab_bs").equals("") && JsonPath.read(document, "$.family_diab_bs").equals("1")) || (JsonPath.read(document, "$.family_diab_ch") != null && !JsonPath.read(document, "$.family_diab_ch").equals("") && JsonPath.read(document, "$.family_diab_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_diab_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_diab_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_diab_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_diab_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(45).setCellValue(html);
                    } else {
                        row.createCell(45).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_hbp_f") != null && !JsonPath.read(document, "$.family_hbp_f").equals("") && JsonPath.read(document, "$.family_hbp_f").equals("1")) || (JsonPath.read(document, "$.family_hbp_m") != null && !JsonPath.read(document, "$.family_hbp_m").equals("") && JsonPath.read(document, "$.family_hbp_m").equals("1")) || (JsonPath.read(document, "$.family_hbp_bs") != null && !JsonPath.read(document, "$.family_hbp_bs").equals("") && JsonPath.read(document, "$.family_hbp_bs").equals("1")) || (JsonPath.read(document, "$.family_hbp_ch") != null && !JsonPath.read(document, "$.family_hbp_ch").equals("") && JsonPath.read(document, "$.family_hbp_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hbp_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hbp_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hbp_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hbp_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(46).setCellValue(html);
                    } else {
                        row.createCell(46).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_chd_f") != null && !JsonPath.read(document, "$.family_chd_f").equals("") && JsonPath.read(document, "$.family_chd_f").equals("1")) || (JsonPath.read(document, "$.family_chd_m") != null && !JsonPath.read(document, "$.family_chd_m").equals("") && JsonPath.read(document, "$.family_chd_m").equals("1")) || (JsonPath.read(document, "$.family_chd_bs") != null && !JsonPath.read(document, "$.family_chd_bs").equals("") && JsonPath.read(document, "$.family_chd_bs").equals("1")) || (JsonPath.read(document, "$.family_chd_ch") != null && !JsonPath.read(document, "$.family_chd_ch").equals("") && JsonPath.read(document, "$.family_chd_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_chd_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_chd_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_chd_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_chd_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(47).setCellValue(html);
                    } else {
                        row.createCell(47).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_stroke_f") != null && !JsonPath.read(document, "$.family_stroke_f").equals("") && JsonPath.read(document, "$.family_stroke_f").equals("1")) || (JsonPath.read(document, "$.family_stroke_m") != null && !JsonPath.read(document, "$.family_stroke_m").equals("") && JsonPath.read(document, "$.family_stroke_m").equals("1")) || (JsonPath.read(document, "$.family_stroke_bs") != null && !JsonPath.read(document, "$.family_stroke_bs").equals("") && JsonPath.read(document, "$.family_stroke_bs").equals("1")) || (JsonPath.read(document, "$.family_stroke_ch") != null && !JsonPath.read(document, "$.family_stroke_ch").equals("") && JsonPath.read(document, "$.family_stroke_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_stroke_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_stroke_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_stroke_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_stroke_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(48).setCellValue(html);
                    } else {
                        row.createCell(48).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_hf_f") != null && !JsonPath.read(document, "$.family_hf_f").equals("") && JsonPath.read(document, "$.family_hf_f").equals("1")) || (JsonPath.read(document, "$.family_hf_m") != null && !JsonPath.read(document, "$.family_hf_m").equals("") && JsonPath.read(document, "$.family_hf_m").equals("1")) || (JsonPath.read(document, "$.family_hf_bs") != null && !JsonPath.read(document, "$.family_hf_bs").equals("") && JsonPath.read(document, "$.family_hf_bs").equals("1")) || (JsonPath.read(document, "$.family_hf_ch") != null && !JsonPath.read(document, "$.family_hf_ch").equals("") && JsonPath.read(document, "$.family_hf_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hf_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hf_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hf_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_hf_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(49).setCellValue(html);
                    } else {
                        row.createCell(49).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_lung_f") != null && !JsonPath.read(document, "$.family_lung_f").equals("") && JsonPath.read(document, "$.family_lung_f").equals("1")) || (JsonPath.read(document, "$.family_lung_m") != null && !JsonPath.read(document, "$.family_lung_m").equals("") && JsonPath.read(document, "$.family_lung_m").equals("1")) || (JsonPath.read(document, "$.family_lung_bs") != null && !JsonPath.read(document, "$.family_lung_bs").equals("") && JsonPath.read(document, "$.family_lung_bs").equals("1")) || (JsonPath.read(document, "$.family_lung_ch") != null && !JsonPath.read(document, "$.family_lung_ch").equals("") && JsonPath.read(document, "$.family_lung_ch").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_lung_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_lung_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_lung_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_lung_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(50).setCellValue(html);
                    } else {
                        row.createCell(50).setCellValue("否");
                    }

                    if ((JsonPath.read(document, "$.family_breast_m") != null && !JsonPath.read(document, "$.family_breast_m").equals("") && JsonPath.read(document, "$.family_breast_m").equals("1")) || (JsonPath.read(document, "$.family_breast_s") != null && !JsonPath.read(document, "$.family_breast_s").equals("") && JsonPath.read(document, "$.family_breast_s").equals("1")) || (JsonPath.read(document, "$.family_breast_d") != null && !JsonPath.read(document, "$.family_breast_d").equals("") && JsonPath.read(document, "$.family_breast_d").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_breast_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_breast_s").toString()) == 1) {
                            html += "姐妹;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_breast_d").toString()) == 1) {
                            html += "女儿;";
                        }
                        html += "患病";
                        row.createCell(51).setCellValue(html);
                    } else {
                        row.createCell(51).setCellValue("否");
                    }
                    if ((JsonPath.read(document, "$.family_gout_f") != null && !JsonPath.read(document, "$.family_gout_f").equals("") && JsonPath.read(document, "$.family_gout_f").equals("1")) || (JsonPath.read(document, "$.family_gout_m") != null && !JsonPath.read(document, "$.family_gout_m").equals("") && JsonPath.read(document, "$.family_gout_m").equals("1")) || (JsonPath.read(document, "$.family_gout_bs") != null && !JsonPath.read(document, "$.family_gout_bs").equals("") && JsonPath.read(document, "$.family_gout_bs").equals("1"))) {
                        String html = "已有";
                        if (Integer.valueOf(JsonPath.read(document, "$.family_gout_f").toString()) == 1) {
                            html += "父亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_gout_m").toString()) == 1) {
                            html += "母亲;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_gout_bs").toString()) == 1) {
                            html += "兄弟;";
                        }
                        if (Integer.valueOf(JsonPath.read(document, "$.family_gout_ch").toString()) == 1) {
                            html += "子女;";
                        }
                        html += "患病";
                        row.createCell(52).setCellValue(html);
                    } else {
                        row.createCell(52).setCellValue("否");
                    }
                    if (JsonPath.read(document, "$.med_hbp") != null && !JsonPath.read(document, "$.med_hbp").equals("")) {
                        row.createCell(53).setCellValue(Integer.valueOf(JsonPath.read(document, "$.med_hbp").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.med_ach") != null && !JsonPath.read(document, "$.med_ach").equals("")) {
                        row.createCell(54).setCellValue(Integer.valueOf(JsonPath.read(document, "$.med_ach").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.food_vegetables") != null && !JsonPath.read(document, "$.food_vegetables").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString()) == 1) {
                            row.createCell(55).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString()) == 2) {
                            row.createCell(55).setCellValue("少于50克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString()) == 3) {
                            row.createCell(55).setCellValue("50-75克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString()) == 4) {
                            row.createCell(55).setCellValue("150-300克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_vegetables").toString()) == 5) {
                            row.createCell(55).setCellValue("300克以上");
                        }

                    }
                    if (JsonPath.read(document, "$.food_fruits") != null && !JsonPath.read(document, "$.food_fruits").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString()) == 1) {
                            row.createCell(56).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString()) == 2) {
                            row.createCell(56).setCellValue("少于50克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString()) == 3) {
                            row.createCell(56).setCellValue("50-100克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString()) == 4) {
                            row.createCell(56).setCellValue("100-200克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fruits").toString()) == 5) {
                            row.createCell(56).setCellValue("200克以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_milk") != null && !JsonPath.read(document, "$.food_milk").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_milk").toString()) == 1) {
                            row.createCell(57).setCellValue("不喝");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_milk").toString()) == 2) {
                            row.createCell(57).setCellValue("少于0.5杯");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_milk").toString()) == 3) {
                            row.createCell(57).setCellValue("0.5-1杯");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_milk").toString()) == 4) {
                            row.createCell(57).setCellValue("1-2杯");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_milk").toString()) == 5) {
                            row.createCell(57).setCellValue("2杯以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_dairy") != null && !JsonPath.read(document, "$.food_dairy").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString()) == 1) {
                            row.createCell(58).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString()) == 2) {
                            row.createCell(58).setCellValue("少于10克 ");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString()) == 3) {
                            row.createCell(58).setCellValue("10-40克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString()) == 4) {
                            row.createCell(58).setCellValue("40-80克");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_dairy").toString()) == 5) {
                            row.createCell(58).setCellValue("80克以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_fired") != null && !JsonPath.read(document, "$.food_fired").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_fired").toString()) == 1) {
                            row.createCell(59).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fired").toString()) == 2) {
                            row.createCell(59).setCellValue("1-4次 ");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fired").toString()) == 3) {
                            row.createCell(59).setCellValue("5-7次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_fired").toString()) == 4) {
                            row.createCell(59).setCellValue("7次以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_sweet") != null && !JsonPath.read(document, "$.food_sweet").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString()) == 1) {
                            row.createCell(60).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString()) == 2) {
                            row.createCell(60).setCellValue("1-4次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString()) == 3) {
                            row.createCell(60).setCellValue("5-7次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sweet").toString()) == 4) {
                            row.createCell(60).setCellValue("7次以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_sugary") != null && !JsonPath.read(document, "$.food_sugary").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString()) == 1) {
                            row.createCell(61).setCellValue("不喝");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString()) == 2) {
                            row.createCell(61).setCellValue("1-4次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString()) == 3) {
                            row.createCell(61).setCellValue("5-7次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_sugary").toString()) == 4) {
                            row.createCell(61).setCellValue("7次以上");
                        }
                    }
                    if (JsonPath.read(document, "$.food_pickled") != null && !JsonPath.read(document, "$.food_pickled").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString()) == 1) {
                            row.createCell(62).setCellValue("不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString()) == 2) {
                            row.createCell(62).setCellValue("1-4次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString()) == 3) {
                            row.createCell(62).setCellValue("5-7次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_pickled").toString()) == 4) {
                            row.createCell(62).setCellValue("7次以上");
                        }

                    }
                    if (JsonPath.read(document, "$.seafood_cnt") != null && !JsonPath.read(document, "$.seafood_cnt").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString()) == 1) {
                            row.createCell(63).setCellValue("几乎不吃");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString()) == 2) {
                            row.createCell(63).setCellValue("1~2次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString()) == 3) {
                            row.createCell(63).setCellValue("2~4次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.seafood_cnt").toString()) == 4) {
                            row.createCell(63).setCellValue("4次以上");
                        }

                    }
                    if (JsonPath.read(document, "$.drink_tea_cnt") != null && !JsonPath.read(document, "$.drink_tea_cnt").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.drink_tea_cnt").toString()) == 1) {
                            row.createCell(64).setCellValue("几乎不饮茶");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.drink_tea_cnt").toString()) == 2) {
                            row.createCell(64).setCellValue("不超过1杯");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.drink_tea_cnt").toString()) == 3) {
                            row.createCell(64).setCellValue("1杯以上");
                        }

                    }
                    if (JsonPath.read(document, "$.food_drink") != null && !JsonPath.read(document, "$.food_drink").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_drink").toString()) == 1) {
                            row.createCell(65).setCellValue("喝酒");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink").toString()) == 2) {
                            row.createCell(65).setCellValue("不喝");
                        }

                    }
                    if (JsonPath.read(document, "$.food_drink_times") != null && !JsonPath.read(document, "$.food_drink_times").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_drink_times").toString()) == 1) {
                            row.createCell(66).setCellValue("1-2次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink_times").toString()) == 2) {
                            row.createCell(66).setCellValue("3-5次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink_times").toString()) == 3) {
                            row.createCell(66).setCellValue(">5次");
                        }
                    }
                    if (JsonPath.read(document, "$.food_drink_beer") != null && !JsonPath.read(document, "$.food_drink_beer").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString()) == 1) {
                            row.createCell(67).setCellValue("少于300毫升");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString()) == 2) {
                            row.createCell(67).setCellValue("300-600毫升");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString()) == 3) {
                            row.createCell(67).setCellValue("600-900毫升");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.food_drink_beer").toString()) == 4) {
                            row.createCell(67).setCellValue("900毫升以上");
                        }
                    }
                    if (JsonPath.read(document, "$.smoke_status") != null && !JsonPath.read(document, "$.smoke_status").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.smoke_status").toString()) == 1) {
                            row.createCell(68).setCellValue("吸烟");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.smoke_status").toString()) == 2) {
                            row.createCell(68).setCellValue("不吸烟");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.smoke_status").toString()) == 3) {
                            row.createCell(68).setCellValue("已戒烟");
                        }
                    }
                    if (JsonPath.read(document, "$.smoke_amount") != null && !JsonPath.read(document, "$.smoke_amount").equals("")) {
                        row.createCell(69).setCellValue(Integer.valueOf(JsonPath.read(document, "$.smoke_amount").toString()) == 1 ? "有" : "无");
                    }
                    if (JsonPath.read(document, "$.smoke_end_age") != null && !JsonPath.read(document, "$.smoke_end_age").equals("")) {
                        row.createCell(70).setCellValue(Integer.valueOf(JsonPath.read(document, "$.smoke_end_age").toString()) == 1 ? "有" : "无");
                    }
                    if (JsonPath.read(document, "$.smoke_sencod") != null && !JsonPath.read(document, "$.smoke_sencod").equals("")) {
                        row.createCell(71).setCellValue(Integer.valueOf(JsonPath.read(document, "$.smoke_sencod").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.sport_in") != null && !JsonPath.read(document, "$.sport_in").equals("")) {
                        row.createCell(72).setCellValue(Integer.valueOf(JsonPath.read(document, "$.sport_in").toString()) == 1 ? "参加过" : "没参加过");
                    }
                    if (JsonPath.read(document, "$.sport_freq") != null && !JsonPath.read(document, "$.sport_freq").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString()) == 1) {
                            row.createCell(73).setCellValue("每月少于1次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString()) == 2) {
                            row.createCell(73).setCellValue("每月至少1次，但每周不足1次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString()) == 3) {
                            row.createCell(73).setCellValue("每周1-2次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString()) == 4) {
                            row.createCell(73).setCellValue("每周3-4次");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_freq").toString()) == 5) {
                            row.createCell(73).setCellValue("每周5次以上");
                        }

                    }
                    if (JsonPath.read(document, "$.sport_time") != null && !JsonPath.read(document, "$.sport_time").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.sport_time").toString()) == 1) {
                            row.createCell(74).setCellValue("少于30分钟");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_time").toString()) == 2) {
                            row.createCell(74).setCellValue("30-60分钟");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.sport_time").toString()) == 3) {
                            row.createCell(74).setCellValue("60分钟以上");
                        }
                    }
                    if (JsonPath.read(document, "$.decorate_house") != null && !JsonPath.read(document, "$.decorate_house").equals("")) {
                        row.createCell(75).setCellValue(Integer.valueOf(JsonPath.read(document, "$.decorate_house").toString()) == 1 ? "有" : "无");
                    }
                    if (JsonPath.read(document, "$.psy_nervous") != null && !JsonPath.read(document, "$.psy_nervous").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.psy_nervous").toString()) == 1) {
                            row.createCell(76).setCellValue("没有");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.psy_nervous").toString()) == 2) {
                            row.createCell(76).setCellValue("偶尔");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.psy_nervous").toString()) == 3) {
                            row.createCell(76).setCellValue("经常");
                        }
                    }
                    if (JsonPath.read(document, "$.bedtime") != null && !JsonPath.read(document, "$.bedtime").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.bedtime").toString()) == 1) {
                            row.createCell(77).setCellValue("每天8小时以上");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.bedtime").toString()) == 2) {
                            row.createCell(77).setCellValue("每天6~8小时");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.bedtime").toString()) == 3) {
                            row.createCell(77).setCellValue("每天不足6小时");
                        }
                    }
                    if (JsonPath.read(document, "$.person.income") != null && !JsonPath.read(document, "$.person.income").equals("")) {
                        if (Integer.valueOf(JsonPath.read(document, "$.person.income").toString()) == 1) {
                            row.createCell(78).setCellValue("小于1000元");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.person.income").toString()) == 2) {
                            row.createCell(78).setCellValue("1000~2999元");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.person.income").toString()) == 3) {
                            row.createCell(78).setCellValue("3000~4999元");
                        } else if (Integer.valueOf(JsonPath.read(document, "$.person.income").toString()) == 4) {
                            row.createCell(78).setCellValue("5000元以上");
                        }

                    }
//					if( JsonPath.read(document, "$.hw_check.xt_exception")!=null && ! JsonPath.read(document, "$.hw_check.xt_exception").equals("")) {
//						row.createCell(85).setCellValue(Integer.valueOf(JsonPath.read(document, "$.hw_check.xt_exception").toString())==0?"正常":"异常");
//					}
                    if (JsonPath.read(document, "$.lampblack_contact.disFlag") != null && !JsonPath.read(document, "$.lampblack_contact.disFlag").equals("")) {
                        row.createCell(79).setCellValue(Integer.valueOf(JsonPath.read(document, "$.lampblack_contact.disFlag").toString()) == 1 ? "有" : "无");
                    }
                    if (JsonPath.read(document, "$.chemical_occupation") != null && !JsonPath.read(document, "$.chemical_occupation").equals("")) {
                        row.createCell(80).setCellValue(Integer.valueOf(JsonPath.read(document, "$.chemical_occupation").toString()) == 1 ? "是" : "否");
                    }
                    if (JsonPath.read(document, "$.dust_exposure_his.disFlag") != null && !JsonPath.read(document, "$.dust_exposure_his.disFlag").equals("")) {
                        row.createCell(81).setCellValue(Integer.valueOf(JsonPath.read(document, "$.dust_exposure_his.disFlag").toString()) == 1 ? "有" : "无");
                    }
                    if (JsonPath.read(document, "$.asbestos_contact_his.disFlag") != null && !JsonPath.read(document, "$.asbestos_contact_his.disFlag").equals("")) {
                        row.createCell(82).setCellValue(Integer.valueOf(JsonPath.read(document, "$.asbestos_contact_his.disFlag").toString()) == 1 ? "有" : "无");
                    }
                    row.createCell(83).setCellValue("总胆固醇（单位mmol/l）" + JsonPath.read(document, "$.cholesterol").toString() + ";甘油三酯（单位mmol/l）" + JsonPath.read(document, "$.tg").toString() + "；高密度脂蛋白胆固醇（单位mmol/l）" + JsonPath.read(document, "$.hdlc").toString() + "；低密度脂蛋白胆固醇（单位mmol/l）" + JsonPath.read(document, "$.ldlc").toString());
                    row.createCell(84).setCellValue("血尿酸（单位μmol/L）" + JsonPath.read(document, "$.bua").toString() + ";血肌酐（单位mmol/L）" + JsonPath.read(document, "$.serum_creatinine").toString());
                    row.createCell(85).setCellValue("空腹血糖（单位mmol/l）" + JsonPath.read(document, "$.fbg").toString() + ";收缩压（单位mmHg）" + JsonPath.read(document, "$.sbp").toString() + "；舒张压（单位mmHg）" + JsonPath.read(document, "$.dbp").toString() + "；血常规检查白细胞（单位/L）" + JsonPath.read(document, "$.wbc").toString() + "；骨密度检查指标-T值" + JsonPath.read(document, "$.t_score").toString());
                    if (JsonPath.read(document, "$.female_menstruation_regular") != null && !JsonPath.read(document, "$.female_menstruation_regular").equals("")) {
                        row.createCell(86).setCellValue(Integer.valueOf(JsonPath.read(document, "$.female_menstruation_regular").toString()) == 1 ? "是" : "不是");
                    }


                }


            }
            if (sheetname.get(i).equals("健康评估结果")) {
                // 循环表数据
                for (int j = 0; j < riskresult2.size(); j++) {
                    System.out.println(riskresult2);
                    HSSFRow row = sheet.createRow(j + 1);
                    row.setHeightInPoints(18);
                    row.createCell(0).setCellValue(riskresult2.get(j).getUserCode());
                    row.createCell(1).setCellValue(riskresult2.get(j).getUserName());
                    row.createCell(2).setCellValue(riskresult2.get(j).getGender() == 1 ? "男" : "女");
                    row.createCell(3).setCellValue(GetAge.GetAgeByBirth(findCheckJsonInfoByRs.getBirth_date(),
                            findCheckJsonInfoByRs.getUser_check_time()));
                    row.createCell(4).setCellValue(riskresult2.get(j).getOrgname());
                    row.createCell(5).setCellValue(riskresult2.get(j).getQtime());
                    for (int h = 0; h < riskresult2.get(j).getYhDiseaseRiskResult2s().size(); h++) {
                        if (riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getBmi() != null) {
                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getBmi());
                            row.createCell(6).setCellValue(JsonPath.read(document, "$.now_value").toString());
                        }
                        if (riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFood() != null) {
                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFood());
                            row.createCell(7).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0") ? "不足" : "充足");
                        }
                        if (riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getB4() != null) {
                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getB4());
                            row.createCell(8).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0") ? "存在" : "不存在");
                        }
                        if (riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFoodPurine() != null) {
                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFoodPurine());
                            row.createCell(9).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0") ? "不存在" : "存在");
                        }
                        if (riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getTfa() != null) {
                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getTfa());
                            row.createCell(10).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0") ? "适量" : "过量");
                        }
                        if (riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getSport() != null) {
                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getSport());
                            row.createCell(11).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0") ? "不足" : "充分");
                        }
                        if (riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getDrink() != null) {
                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getDrink());
                            row.createCell(12).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0") ? "喝酒" : "不喝");
                        }
                        if (riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPsyNervous() != null) {
                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPsyNervous());
                            row.createCell(13).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0") ? "有" : "没有");
                        }
//                        if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHfHis()!=null){
//                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHfHis());
//                            row.createCell(14).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//                            row.createCell(16).setCellValue(JsonPath.read(document, "$.Ideal_value").toString());
//                        }
//                        if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHfs()!=null){
//                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHfs());
//                            row.createCell(15).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//                        }
//                        if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHbps()!=null){
//                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getHbps());
//                            row.createCell(17).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//                        }
//                        if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getB4()!=null){
//                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getB4());
//                            row.createCell(18).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//                        }
//                        if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getLungs()!=null){
//                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getLungs());
//                            row.createCell(19).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//                        }
//                        if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFood()!=null){
//                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getFood());
//                            row.createCell(20).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"不足":"充足");
//                           row.createCell(24).setCellValue(JsonPath.read(document, "$.Ideal_value").toString());
//                        }
//                        if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getSmoke()!=null){
//                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getSmoke());
//                            row.createCell(26).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"不吸烟":"吸烟");
//							row.createCell(25).setCellValue(JsonPath.read(document, "$.Ideal_value").toString());
//                        }
//						if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPcs()!=null){
//							Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPcs());
//							row.createCell(27).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"不足":"充足");
//						}
//						if(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPcs()!=null){
//							Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getPcs());
//							row.createCell(28).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0")?"无":"有");
//						}
                        row.createCell(14).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(0).getNow_risk_value());
                        row.createCell(15).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(0).getIdeal_risk_level());
                        row.createCell(16).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(0).getRisk_level());
                        row.createCell(17).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(1).getNow_risk_value());
                        row.createCell(18).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(1).getIdeal_risk_level());
                        row.createCell(19).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(1).getRisk_level());
                        row.createCell(20).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(2).getNow_risk_value());
                        row.createCell(21).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(2).getIdeal_risk_level());
                        row.createCell(22).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(2).getRisk_level());
                        row.createCell(23).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(3).getNow_risk_value());
                        row.createCell(24).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(3).getIdeal_risk_level());
                        row.createCell(25).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(3).getRisk_level());
                        row.createCell(26).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(4).getNow_risk_value());
                        row.createCell(27).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(4).getIdeal_risk_level());
                        row.createCell(28).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(4).getRisk_level());
                        row.createCell(29).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(6).getNow_risk_value());
                        row.createCell(30).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(6).getIdeal_risk_level());
                        row.createCell(31).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(6).getRisk_level());
                        row.createCell(32).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(7).getNow_risk_value());
                        row.createCell(33).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(7).getIdeal_risk_level());
                        row.createCell(34).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(7).getRisk_level());
                        if (riskresult2.get(j).getYhExportAssessReports().get(8).getDiseasId().equals("9")) {
                            row.createCell(35).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getNow_risk_value());
                            row.createCell(36).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getIdeal_risk_level());
                            row.createCell(37).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getRisk_level());
                        }
                        if (riskresult2.get(j).getYhExportAssessReports().get(8).getDiseasId().equals("10")) {
                            row.createCell(38).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getNow_risk_value());
                            row.createCell(39).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getIdeal_risk_level());
                            row.createCell(40).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(8).getRisk_level());
                        }

                        if (riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getBf() != null) {
                            Object document = Configuration.defaultConfiguration().jsonProvider().parse(riskresult2.get(j).getYhDiseaseRiskResult2s().get(h).getBf());
                            row.createCell(41).setCellValue(JsonPath.read(document, "$.now_value").toString().equals("0") ? "血脂正常" : "血脂异常");
                        }
                        row.createCell(42).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(9).getRisk_level());
                        row.createCell(43).setCellValue(riskresult2.get(j).getYhExportAssessReports().get(9).getIdeal_risk_level());
                    }
                }
            }

        }
        String fileName = "健康评估导出" + ".xls";// 设置要导出的文件的名字

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        /*
         * response.setHeader("Content-disposition", "attachment;filename=" +
         * fileName);
         */
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

    /*
     * public static void main(String[] args) { // 定义字符串 String nameAndPwd = new
     * Md5Hash("王凤妮" + "123456").toHex(); System.out.println(nameAndPwd); }
     */

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese() {
        String str = "哈哈";
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * @param page
     * @param limit
     * @param params
     * @return
     */
    @ApiOperation(value = "分页查询健康档案管理")
    @GetMapping(value = "/page")
    @ResponseBody
    public Map<String, Object> selecthealthRecordManagePageByParam(@RequestParam Map<String, Object> healthMap,
                                                                   @RequestParam(value = "page", required = true) int page,
                                                                   @RequestParam(value = "limit", required = true) int limit) {
        System.out.println(healthMap);
        /*
         * if(healthMap.get("orgCode")!=null &&
         * StringUtil.isNotEmpty(healthMap.get("orgCode").toString())) {
         * List<YhOrgTree> list =
         * yhOrgTreeService.selectBYLikeCode(healthMap.get("orgCode").toString()
         * ); //取出list中所有的orgId，转成新的list List<String> orgCodes =
         * list.stream().map(YhOrgTree::getOrgCode).collect(Collectors.toList())
         * ; orgCodes.add(healthMap.get("orgCode").toString());
         * healthMap.put("orgCodes", orgCodes); }
         *
         * PageInfo<Map<String, Object>> data =
         * yhUserReginfoService.selecthealthRecordManagePageByParam(healthMap,
         * page,limit); for (Map<String, Object> item : data.getList()) {
         * if(item.get("Org_Code")!= null) { item.put("Org_Name",
         * yhOrgTreeService.selectFullOrgName(item.get("Org_Code").toString(),
         * new String())); } }
         */
        if (com.yihe.system.utils.StringUtil.isNotEmpty(healthMap.get("regTime").toString())) {
            String regdate1 = healthMap.get("regTime").toString().substring(0, 19);
            String regdate2 = healthMap.get("regTime").toString().substring(22, 41);
            healthMap.put("regdate1", regdate1);
            healthMap.put("regdate2", regdate2);
        }

        if (healthMap.get("orgCode") != null && StringUtil.isNotEmpty(healthMap.get("orgCode").toString())) {
            List<YhOrgTree> list = yhOrgTreeService.selectBYLikeCode(healthMap.get("orgCode").toString());
            // 取出list中所有的orgId，转成新的list
            List<String> orgCodes = list.stream().map(YhOrgTree::getOrgCode).collect(Collectors.toList());
            orgCodes.add(healthMap.get("orgCode").toString());
            healthMap.put("orgCodes", orgCodes);
        }
        PageInfo<Map<String, Object>> data = yhUserReginfoService.selecthealthRecordManagePageByParam(healthMap, page,
                limit);
        for (Map<String, Object> item : data.getList()) {
            if (item.get("Org_Code") != null) {
                item.put("Org_Name", yhOrgTreeService.selectFullOrgName(item.get("Org_Code").toString(), new String()));
            }
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("data", data.getList());
        result.put("count", data.getTotal());
        result.put("msg", "");
        result.put("code", 20000);
        return result;
    }

    /**
     * @param userCode
     * @param lockFlag
     * @return
     */

    @ApiOperation(value = "修改用户状态")
    @RequestMapping(value = "/updateLockFlag", method = RequestMethod.POST)
    @ResponseBody
    public int updateuserLockFlag(String userCode, String lockFlag) {
        int status = yhUserReginfoService.updateuserStatus(userCode, lockFlag);
        return status;

    }
}