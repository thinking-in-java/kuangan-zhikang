package com.yihe.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.yihe.common.utils.ReflectionUtil;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.*;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.ExcelUtil;
import com.yihe.system.utils.RelativeDateFormat;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * YhUserReginfoService实现 Created by lll on 2020/3/6.
 */
@Service
@Transactional

public class YhUserReginfoServiceImpl implements YhUserReginfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserReginfoServiceImpl.class);

    @Autowired
    YhHeThresholdServiceImpl yhHeThresholdServiceImpl;
    @Autowired
    YhHeThresholdService yhHeThresholdService;
    @Autowired
    YHUserBmiMapper yHUserBmiMapper;
    @Autowired
    YhUserReginfoMapper yhUserReginfoMapper;
    @Autowired
    YHDocGroupServiceImpl YHDocGroupServiceImpl;
    @Autowired
    YhUserMakeCardMapper yhUserMakeCardMapper;
    @Autowired
    YhOrgTreeMapper yhOrgTreeMapper;
    @Autowired
    YhDiseaseAssessReportMapper yhDiseaseAssessReportMapper;
    @Autowired
    YhDiseaseAssessTotalReportServiceImpl YhDiseaseAssessTotalReportServiceImpl;
    @Autowired
    YhHealthLectureWatchMapper YhHealthLectureWatchMapper;

    @Autowired
    YhPressureEvaluateServiceImpl YhPressureEvaluateServiceImpl;
    @Autowired
    YhDiseaseAssessReportServiceImpl YhDiseaseAssessReportServiceImpl;
    @Autowired
    YhFoodUserDiaryServiceImpl YhFoodUserDiaryServiceImpl;
    @Autowired
    YhSportDiaryServiceImpl YhSportDiaryServiceImpl;
    @Autowired
    YhHealthLectureCommentServiceImpl YhHealthLectureCommentServiceImpl;

    @Autowired
    YhSleepUserDiaryServiceImpl YhSleepUserDiaryServiceImpl;

    @Autowired
    YhUserHealthyPlanServiceImpl YhUserHealthyPlanServiceImpl;
    @Autowired
    YhLifeHabitImprovementDictMapper YhLifeHabitImprovementDictMapper;

    @Autowired
    YhSleepReportServiceImpl YhSleepReportServiceImpl;
    @Autowired
    YhUserEnrolinfoMapper yhUserEnrolinfoMapper;
    @Autowired
    YhUserRolePowerMapper yhUserRolePowerMapper;
    @Autowired
    YhUserRoleMapper yhUserRoleMapper;

    @Autowired
    YhUserPersonageConditionMapper yhUserPersonageConditionMapper;
    @Autowired
    YHUserBloodsugerMapper yHUserBloodsugerMapper;
    @Autowired
    YHUserBloodfatMapper yHUserBloodfatMapper;
    @Autowired
    YHUserBloodPressureMapper yHUserBloodPressureMapper;
    @Autowired
    private YhDiseaseRiskResultMapper YhDiseaseRiskResultMapper;
    @Autowired
    private YhUserChatRecordWedMapper YhUserChatRecordWedMapper;


    @Override
    public int insertSelective(YhUserReginfo yhUserReginfo) {

        return yhUserReginfoMapper.regSubmit(yhUserReginfo);
    }

    @Override
    public int regSubmitActivation(YhUserReginfo yhUserReginfo) {
        return yhUserReginfoMapper.regSubmitActivation(yhUserReginfo);
    }

    @Override
    public String selectMaxUserCode(Integer userType) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.selectMaxUserCode(userType);
    }

    @Override
    public YhUserReginfo selectMobileAndName(String userName) {
        return yhUserReginfoMapper.selectMobileAndName(userName);
    }

    @Override
    public YhUserReginfo selectMobileAndName2(String mobile) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.selectMobileAndName2(mobile);
    }

    @Override
    public YhUserReginfo selectMobileAndName3(String mobile, Integer userType) {
        return yhUserReginfoMapper.showmobile2( mobile,userType);
    }

    @Override
    public PageInfo<YhUserReginfo> selectByPrimaryKeyBydoctor(String userCode, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<YhUserReginfo> list = yhUserReginfoMapper.selectByPrimaryKeyBydoctor(userCode);

        return new PageInfo<YhUserReginfo>(list);

    }

    @Override
    public Map<String, Object> ajaxUploadExcel(String orgCode, InputStream in, MultipartFile file, HttpServletRequest request) throws Exception {
        //定义返回消息体
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 200);
        //获取excel数据
        List<List<Object>> listob = ExcelUtil.getBankListByExcel(in, file.getOriginalFilename());
        //服务告警提醒
        ArrayList<EmergencyVojo> EmergencyVojoList = new ArrayList<EmergencyVojo>();
        YhUserReginfo userReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        String curUserCode = userReginfo.getUserCode();
        //查询未用的注册卡
        List<YhUserReginfo> regCard = yhUserReginfoMapper.selectRegCard(curUserCode, orgCode);

        //组装数据c
        ArrayList<YhUserReginfo> yhUserReginfoList = new ArrayList<YhUserReginfo>();
        ArrayList<YhUserPersonageCondition> listPersonageCondition = new ArrayList<YhUserPersonageCondition>();
        //体重
        ArrayList<YHUserBmi> listYHUserBmi = new ArrayList<YHUserBmi>();
        ArrayList<YHUserBloodsuger> listyHUserBloodsuger = new ArrayList<YHUserBloodsuger>();
        ArrayList<YHUserBloodfat> listyHUserBloodfat = new ArrayList<YHUserBloodfat>();
        ArrayList<YHUserBloodPressure> listyHUserBloodPressure = new ArrayList<YHUserBloodPressure>();
        for (int i = 0; i < listob.size(); i++) {

            if (!assembleData(listob.get(i), i + 1, orgCode, yhUserReginfoList, result, listob, regCard, listPersonageCondition, listYHUserBmi,
                    listyHUserBloodsuger, listyHUserBloodfat, listyHUserBloodPressure, request, EmergencyVojoList)) {
                return result;
            }
        }

        //保存数据
        yhUserReginfoMapper.importYhUserReginfoData(yhUserReginfoList);

        if (listPersonageCondition.size() > 0) {
            yhUserPersonageConditionMapper.importYhUserPersonageConditionData(listPersonageCondition);
        }
        if (listYHUserBmi.size() > 0) {
            yHUserBmiMapper.importYHUserBmi(listYHUserBmi);
        }
        if (listyHUserBloodsuger.size() > 0) {
            yHUserBloodsugerMapper.importYHUserBloodsugerData(listyHUserBloodsuger);
        }
        if (listyHUserBloodfat.size() > 0) {
            yHUserBloodfatMapper.importYHUserBloodfatData(listyHUserBloodfat);
        }

        if (listyHUserBloodPressure.size() > 0) {
            yHUserBloodPressureMapper.importYHUserBloodPressureData(listyHUserBloodPressure);
        }
        for (int i = 0; i < yhUserReginfoList.size(); i++) {
            YhUserMakeCard record = new YhUserMakeCard();
            record.setEnrolCardId(yhUserReginfoList.get(i).getEnrolCardId());
            record.setUserCode(yhUserReginfoList.get(i).getUserCode());
            yhUserMakeCardMapper.updateByPrimaryKeySelective(record);
            YhUserReginfo yhUserReginfo = new YhUserReginfo();
            YhUserMakeCard makeCard = yhUserMakeCardMapper.selectByPrimaryKey(yhUserReginfoList.get(i).getEnrolCardId());
            yhUserReginfo.setUserCode(yhUserReginfoList.get(i).getUserCode());
            yhUserReginfo.setOrgCode(makeCard.getOrgId());
            yhUserReginfoMapper.updateByPrimaryKeySelective(yhUserReginfo);
            //往用户信息扩展表插入一条数据 userCode Evaluateflag
            YhUserEnrolinfo enrolinfo = yhUserEnrolinfoMapper.selectByPrimaryKeyByUsercode(yhUserReginfoList.get(i).getUserCode());
            if (enrolinfo == null) {
                YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
                yhUserEnrolinfo.setUserCode(yhUserReginfoList.get(i).getUserCode());
                yhUserEnrolinfo.setEvaluateflag(0);
                yhUserEnrolinfoMapper.insertSelective(yhUserEnrolinfo);
            }

            //查询角色表
            Integer roleId = yhUserRoleMapper.selectRoleId("用户");
            //用户角色关联表插入id和code
            YhUserRolePower yhUserRolePower = new YhUserRolePower();
            yhUserRolePower.setRoleid(roleId);
            yhUserRolePower.setUserid(yhUserReginfoList.get(i).getUserCode());
            yhUserRolePowerMapper.insert(yhUserRolePower);


        }
        result.put("msg", "已成功导入" + yhUserReginfoList.size() + "个账号，初始密码为12345678。");
        yhHeThresholdServiceImpl.EmergencyAdd(EmergencyVojoList, request);
        return result;
    }

    /**
     * 判断是否是过去的日期
     *
     * @param str输入的日期
     * @return
     */
    public boolean isPastDate(String str) {

        boolean flag = false;
        Date nowDate = new Date();
        Date pastDate = null;
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        //在日期字符串非空时执行
        if (str != null && !"".equals(str)) {
            try {
                //将字符串转为日期格式，如果此处字符串为非合法日期就会抛出异常。
                pastDate = sdf.parse(str);
                //调用Date里面的before方法来做判断
                flag = pastDate.before(nowDate);
                if (flag) {
                    System.out.println("该日期早于今日");
                } else {
                    System.out.println("该日期晚于今日");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("日期参数不可为空");
        }
        return flag;
    }

    public static boolean isNumeric(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        String regx = "[+-]*\\d+\\.?\\d*[Ee]*[+-]*\\d+";
        Pattern pattern = Pattern.compile(regx);
        boolean isNumber = pattern.matcher(str).matches();
        if (isNumber) {
            return isNumber;
        }
        regx = "^[-\\+]?[.\\d]*$";
        pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    private boolean assembleData(List<Object> ob, int rowIndex, String orgCode, ArrayList<YhUserReginfo> list, Map<String, Object> result, List<List<Object>> listob,
                                 List<YhUserReginfo> regCard, List<YhUserPersonageCondition> listPersonageCondition, List<YHUserBmi> listYHUserBmi, ArrayList<YHUserBloodsuger> listyHUserBloodsuger,
                                 ArrayList<YHUserBloodfat> listyHUserBloodfat, ArrayList<YHUserBloodPressure> listyHUserBloodPressure, HttpServletRequest request, ArrayList<EmergencyVojo> EmergencyVojoList) throws ParseException {
        //存储excel的所有账号名字
        List<String> userNamelist = new ArrayList<String>();
        //存储excel的所有账号手机号
        List<String> userPhpnelist = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        YhUserReginfo yhUserReginfo = new YhUserReginfo();
        //身高 体重  腰围
        YhUserPersonageCondition yhUserPersonageCondition = null;
        //血糖
        YHUserBloodsuger yhUserBloodsuger = null;

        //总胆固醇  甘油三酯  高密度脂蛋白胆固醇 低密度脂蛋白胆固醇
        YHUserBloodfat yhUserBloodfat = null;
        // 收缩压 舒张压
        YHUserBloodPressure yhUserBloodPressure = null;
		/*//判断注册卡号
		if(StringUtil.isEmpty(String.valueOf(ob.get(0)))) {
			result.put("code", "500");
			result.put("msg","第"+rowIndex+"行,注册卡号不能为空");
			return false;
		}*/

        //查询注册卡表（3个条件：注册卡ID、usercode为空、doctor为当前医生）

        /*String enrolCardId = String.valueOf(ob.get(0));*/
        YhUserReginfo userReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        String curUserCode = userReginfo.getUserCode();
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf2.format(date);
        //查询当前医生下剩余的注册卡总数
        int SurplusCount = yhUserReginfoMapper.selectRegCardSurplus(curUserCode, orgCode);
        if (regCard == null || regCard.size() < listob.size()) {
            result.put("code", "500");
            result.put("msg", "可用注册卡" + SurplusCount + "张，小于导入的用户数量");
            return false;
        }
        yhUserReginfo.setEnrolCardId(regCard.get(rowIndex - 1).getEnrolCardId());
        //截止时间
        String linetime = "";
        if (26 < ob.size()) linetime = ob.get(26).toString();
        //如果截止时间为空的话注册卡的截止时间为机构的过期时间
        if (linetime == "") {
            yhUserReginfoMapper.setmakecardlinetime(regCard.get(rowIndex - 1).getEnrolCardId());
        } else {
            //如果有值的话判断表格时间比机构过期时间长就用机构过期时间否则用表格设置的过期时间
            String lasttime = yhUserReginfoMapper.selectlintime(regCard.get(rowIndex - 1).getEnrolCardId());

            if (lasttime == null) {
                if (linetime != null) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    if (compare(linetime, df.format(new Date()))) {
                        yhUserReginfoMapper.setmakecarlinetimes(df.format(new Date()), regCard.get(rowIndex - 1).getEnrolCardId());
                    } else {
                        yhUserReginfoMapper.setmakecarlinetimes(linetime, regCard.get(rowIndex - 1).getEnrolCardId());

                    }

                } else {
                    yhUserReginfoMapper.setmakecardlinetime(regCard.get(rowIndex - 1).getEnrolCardId());
                }
            } else {
                //如果表格的值要小于机构的值
                if (compare(linetime, lasttime)) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    if (compare(linetime, df.format(new Date()))) {
                        //如果大于机构过期时间的话取机构的过期时间
                        yhUserReginfoMapper.setmakecarlinetimes(df.format(new Date()), regCard.get(rowIndex - 1).getEnrolCardId());
                    } else {
                        yhUserReginfoMapper.setmakecarlinetimes(linetime, regCard.get(rowIndex - 1).getEnrolCardId());

                    }

                } else {
                    //如果大于机构过期时间的话取机构的过期时间
                    yhUserReginfoMapper.setmakecardlinetime(regCard.get(rowIndex - 1).getEnrolCardId());
                }
            }

        }
        String userCode = yhUserReginfoMapper.selectMaxUserCode2();
        if (userCode == null) {
            userCode = "100059";
        }
        String userCode1 = String.valueOf(Integer.valueOf(userCode) + 1 + list.size());
        yhUserReginfo.setUserCode(userCode1);

        //判断姓名
        String name = null;
        if (isNumeric(String.valueOf(ob.get(0)))) {
            BigDecimal name2 = new BigDecimal(Double.valueOf(ob.get(0).toString()));
            name = name2.toPlainString();
        } else {
            name = String.valueOf(ob.get(0));
        }
        char[] nameChs = name.toCharArray();
        Integer name2 = 0;
        if (StringUtil.isEmpty(name)) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,姓名不能为空：必须填写姓名");
            return false;

        }

        for (int i = 0; i < nameChs.length; i++) {
            System.out.println(name.charAt(i));
            name2 += (nameChs[i] > 0xff) ? 2 : 1;
        }

        if (name2 > 20) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,姓名不能超过最大限制20个字符");
            return false;
        }
        yhUserReginfo.setName(name);

        //判断性别
        String gender = String.valueOf(ob.get(1));
        if (StringUtil.isEmpty(gender)) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,性别不能为空：必须填写性别");
            return false;
        }
        //yhUserReginfo.setGender("男".equals(gender)?1 : 2);
        yhUserReginfo.setGender("男".equals(gender) ? 1 : 2);
        if ("男".equals(gender)) {
            yhUserReginfo.setPostCode("https://qny.guyijk.com/xiaochengxu/toxiangnan.png");
        }
        if ("女".equals(gender)) {
            yhUserReginfo.setPostCode("https://qny.guyijk.com/xiaochengxu/toxiangnv.png");
        }

        //判断出生日期
        String birthDate = String.valueOf(ob.get(2));
        if (StringUtil.isEmpty(birthDate)) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,出生日期不能为空：必须填写出生日期");
            return false;
        }
        if (!isPastDate(birthDate)) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,出生日期不可大于当前日期");
            return false;
        }
        try {
            yhUserReginfo.setBirthDate(sdf.parse(ob.get(2).toString()));
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,出生日期格式不正确：必须填写“2020/3/10”格式");
            return false;
        }


        yhUserReginfo.setUserType(5);
        yhUserReginfo.setRegTime(new Date());
		
		
		/*SysPasswordService sysPasswordService = new SysPasswordService();
		sysPasswordService.encryptPassword(String.valueOf(ob.get(0)), userPwd);
		String nameAndPwd = new Md5Hash(String.valueOf(ob.get(0)) + userPwd).toHex();*/


        yhUserReginfo.setLockFlag(2);
        yhUserReginfo.setRegSource(2);

        //判断手机号
        //String mobile = String.valueOf(ob.get(3));

        BigDecimal mobile = new BigDecimal(Double.valueOf(ob.get(3).toString()));
        String phone = mobile.toPlainString();
        String regex = "^1[3|4|5|6|7|8][0-9]\\d{4,8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        boolean isMatch = m.matches();

        if (StringUtil.isEmpty(phone)) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,手机号码不能为空：必须填写手机号码");
            return false;
        } else if (phone.length() != 11) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,手机号必须是11位");
            return false;
        } else if (!phone.startsWith("1")) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,手机号码开头数字必须为1");
            return false;
        }


        /*
         * else if(mobile.length() != 11 || isMatch==false){ result.put("code", "500");
         * result.put("msg", "第"+rowIndex+"行,手机号码格式不正确"); return false; }
         */
        YhUserReginfo yhUserReginfo2 = yhUserReginfoMapper.selectMobileAndName2(phone);
        if (yhUserReginfo2 != null) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,手机号码已存在：已有其他用户使用了该手机号码，你不能重复使用");
            return false;
        }
        yhUserReginfo.setMobile(phone);

        //判断登录账号
        String userName = null;
        if (isNumeric(String.valueOf(ob.get(4)))) {
            BigDecimal userName2 = new BigDecimal(Double.valueOf(ob.get(4).toString()));
            userName = userName2.toPlainString();
        } else {
            userName = String.valueOf(ob.get(4));
        }


        char[] chs = userName.toCharArray();
        Integer userName2 = 0;
        if (StringUtil.isEmpty(userName)) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,登录账号不能为空：必须填写登录账号");
            return false;
        }
        for (int i = 0; i < chs.length; i++) {
            System.out.println(userName.charAt(i));
            if (chs[i] > 0xff) {
                userName2 = userName2 + 2;
            } else {
                userName2 = userName2 + 1;
            }
        }

        if (userName.indexOf(" ") != -1) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,登录账号存在非法字符");
            return false;
        } else {
            System.out.println("字符串合法");
        }

        if (userName2 < 1) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,登录账号不能少于最低限制1个字符");
            return false;
        }
        if (userName2 > 20) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,登录账号不能超过最大限制20个字符");
            return false;
        }
        //YhExprotDataController
        YhUserReginfo userReginfo2 = yhUserReginfoMapper.selectMobileAndName(userName);
        if (userReginfo2 != null) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,登录账号已存在：已有其他用户使用了该登录名，你不能重复使用");
            return false;
        }

        String userPwd = "12345678";
        String md5Pwd = new Md5Hash(userName + userPwd).toHex();
        yhUserReginfo.setUserPwd(md5Pwd);
        yhUserReginfo.setUserName(userName);
        if (!String.valueOf(ob.get(5)).equals("") && !String.valueOf(ob.get(7)).equals("") && !String.valueOf(ob.get(8)).equals("")) {
            yhUserPersonageCondition = new YhUserPersonageCondition();
            yhUserPersonageCondition.setStature(String.valueOf(ob.get(5)));
            yhUserPersonageCondition.setWeight(String.valueOf(ob.get(7)));
            yhUserPersonageCondition.setWaistline(String.valueOf(ob.get(8)));
            yhUserPersonageCondition.setUserCode(userCode1);
        }


        if (!String.valueOf(ob.get(6)).equals("")) {
            //服务提醒
            EmergencyVojo emergencyVojo = new EmergencyVojo();
            yhUserBloodsuger = new YHUserBloodsuger();
            yhUserBloodsuger.setBs(Float.valueOf(String.valueOf(ob.get(6))));
            yhUserBloodsuger.setEntryTime(createdate);
            yhUserBloodsuger.setIsDel(2);
            yhUserBloodsuger.seteUserId(userCode1);
            yhUserBloodsuger.setEnv("空腹");
            yhUserBloodsuger.setDataSource("健管师导入");
            emergencyVojo.setTypeName("空腹血糖");
            emergencyVojo.setUserCode(userCode1);
            emergencyVojo.setType("blood_sugar");
            emergencyVojo.setValue(String.valueOf(ob.get(6)));
            EmergencyVojoList.add(emergencyVojo);
        }

        if (!String.valueOf(ob.get(7)).equals("") && !String.valueOf(ob.get(5)).equals("")) {
            //体重
            Float getuWeight = Float.valueOf((String) ob.get(7));
            Float getuHeight = Float.valueOf((String) ob.get(5));
            Float bmi = getuWeight / ((getuHeight / 100) * (getuHeight / 100));
            String bmi2 = String.valueOf(bmi);
            //服务提醒
            EmergencyVojo emergencyVojo = new EmergencyVojo();
            emergencyVojo.setTypeName("BMI");
            emergencyVojo.setUserCode(userCode1);
            emergencyVojo.setType("BMI");
            emergencyVojo.setValue(bmi2);
            EmergencyVojoList.add(emergencyVojo);
            YHUserBmi yhUserBmi = new YHUserBmi();
            yhUserBmi.setuHeight(Float.valueOf((String) ob.get(5)));
            yhUserBmi.setuWeight(Float.valueOf((String) ob.get(7)));
            yhUserBmi.setIsDel(2);
            yhUserBmi.setDataSource("健管师导入");
            yhUserBmi.setEnv("常规测量");
            yhUserBmi.setEntryTime(createdate);
            yhUserBmi.seteUserId(Integer.valueOf(userCode1));
            if (yhUserBmi != null) {
                listYHUserBmi.add(yhUserBmi);
            }
        }


        if (!String.valueOf(ob.get(9)).equals("") && !String.valueOf(ob.get(10)).equals("") && !String.valueOf(ob.get(11)).equals("")
                && !String.valueOf(ob.get(12)).equals("")) {
            yhUserBloodfat = new YHUserBloodfat();
            yhUserBloodfat.setCholesterin(Float.valueOf(String.valueOf(ob.get(9))));
            yhUserBloodfat.setTring(Float.valueOf(String.valueOf(ob.get(10))));
            yhUserBloodfat.setHdl(Float.valueOf(String.valueOf(ob.get(11))));
            yhUserBloodfat.setLdl(Float.valueOf(String.valueOf(ob.get(12))));
            yhUserBloodfat.setEntryTime(createdate);
            yhUserBloodfat.setIsDel(2);
            yhUserBloodfat.seteUserId(Integer.valueOf(userCode1));
            yhUserBloodfat.setDataSource("常规测量");
            //服务提醒
            EmergencyVojo emergencyVojo = new EmergencyVojo();
            emergencyVojo.setTypeName("总胆固醇");
            emergencyVojo.setUserCode(userCode1);
            emergencyVojo.setType("tc");
            emergencyVojo.setValue(String.valueOf(ob.get(9)));
            EmergencyVojoList.add(emergencyVojo);
        }
        if (!String.valueOf(ob.get(10)).equals("")) {
            //服务提醒
            EmergencyVojo emergencyVojo = new EmergencyVojo();
            emergencyVojo.setTypeName("甘油三脂");
            emergencyVojo.setUserCode(userCode1);
            emergencyVojo.setType("trig");
            emergencyVojo.setValue(String.valueOf(ob.get(10)));
            EmergencyVojoList.add(emergencyVojo);
        }
        if (!String.valueOf(ob.get(11)).equals("")) {
            //服务提醒
            EmergencyVojo emergencyVojo = new EmergencyVojo();
            emergencyVojo.setTypeName("高密度蛋白");
            emergencyVojo.setUserCode(userCode1);
            emergencyVojo.setType("hdl-c");
            emergencyVojo.setValue(String.valueOf(ob.get(11)));
            EmergencyVojoList.add(emergencyVojo);
        }
        if (!String.valueOf(ob.get(12)).equals("")) {
            //服务提醒
            EmergencyVojo emergencyVojo = new EmergencyVojo();
            emergencyVojo.setTypeName("低密度蛋白");
            emergencyVojo.setUserCode(userCode1);
            emergencyVojo.setType("ldl-c");
            emergencyVojo.setValue(String.valueOf(ob.get(12)));
            EmergencyVojoList.add(emergencyVojo);
        }

        if (!String.valueOf(ob.get(13)).equals("") && !String.valueOf(ob.get(14)).equals("")) {
            yhUserBloodPressure = new YHUserBloodPressure();
            //服务提醒
            EmergencyVojo emergencyVojo = new EmergencyVojo();
            yhUserBloodPressure.setSbp(Integer.valueOf(String.valueOf(ob.get(13))));
            yhUserBloodPressure.setDbp(Integer.valueOf(String.valueOf(ob.get(14))));
            yhUserBloodPressure.setEntryTime(createdate);
            yhUserBloodPressure.setIsDel(2);
            yhUserBloodPressure.seteUserId(userCode1);
            yhUserBloodPressure.setTestHj("常规测量");
            yhUserBloodPressure.setType("健管师导入");
            emergencyVojo.setTypeName("收缩压");
            emergencyVojo.setUserCode(userCode1);
            emergencyVojo.setType("blood_pressure_sys");
            emergencyVojo.setValue(String.valueOf(ob.get(13)));
            EmergencyVojoList.add(emergencyVojo);

        }
        if (!String.valueOf(ob.get(14)).equals("")) {
            //服务提醒
            EmergencyVojo emergencyVojo = new EmergencyVojo();
            emergencyVojo.setTypeName("舒张压");
            emergencyVojo.setUserCode(userCode1);
            emergencyVojo.setType("blood_pressure_dia");
            emergencyVojo.setValue(String.valueOf(ob.get(14)));
            EmergencyVojoList.add(emergencyVojo);

        }


        if (!String.valueOf(ob.get(15)).equals("")) {
            yhUserReginfo.setNation(String.valueOf(String.valueOf(ob.get(15))));
        }
        if (!String.valueOf(ob.get(16)).equals("")) {
            //身份证
            String sfz = String.valueOf(String.valueOf(ob.get(16)));
            String sfzregex = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                    "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
            Pattern sfzp = Pattern.compile(sfzregex);
            Matcher sfzm = sfzp.matcher(sfz);
            boolean sfzisMatch = sfzm.matches();
            System.out.println(sfzisMatch);

            if (!sfzisMatch) {
                result.put("code", "500");
                result.put("msg", "第" + rowIndex + "行,身份证号信息不对");
                return false;
            }
            yhUserReginfo.setCardidentity(String.valueOf(String.valueOf(ob.get(16))));
        }
        if (!String.valueOf(ob.get(17)).equals("")) {
            yhUserReginfo.setAddress(String.valueOf(String.valueOf(ob.get(17))));
        }
        if (!String.valueOf(ob.get(18)).equals("")) {
            yhUserReginfo.setUnit(String.valueOf(String.valueOf(ob.get(18))));
        }
        if (!String.valueOf(ob.get(19)).equals("")) {
            yhUserReginfo.setPermanentaddress(String.valueOf(String.valueOf(ob.get(19))));
        }
        if (!ob.get(20).toString().equals("")) {
            BigDecimal relation2 = new BigDecimal(Double.valueOf(ob.get(20).toString()));
            String relation = relation2.toPlainString();
            if (!relation.startsWith("1")) {
                result.put("code", "500");
                result.put("msg", "第" + rowIndex + "行,紧急联系人手机号码开头数字必须为1");
                return false;
            }
            yhUserReginfo.setRelation(relation);
        }


        if (!String.valueOf(ob.get(21)).equals("")) {
            yhUserReginfo.setMaritalStatus(String.valueOf(String.valueOf(ob.get(21))));
        }
        if (!String.valueOf(ob.get(22)).equals("")) {
            yhUserReginfo.setEdBackground(String.valueOf(String.valueOf(ob.get(22))));
        }
        if (!String.valueOf(ob.get(23)).equals("")) {
            yhUserReginfo.setBloodType(String.valueOf(String.valueOf(ob.get(23))));
        }
        if (!String.valueOf(ob.get(24)).equals("")) {
            yhUserReginfo.setRhfeminine(String.valueOf(String.valueOf(ob.get(24))));
        }
        if (!String.valueOf(ob.get(25)).equals("")) {
            yhUserReginfo.setOccupation(String.valueOf(String.valueOf(ob.get(25))));
        }
        for (int i = 0; i < list.size(); i++) {
            String userName3 = list.get(i).getUserName();
            String mobile2 = list.get(i).getMobile();
            userNamelist.add(userName3);
            userPhpnelist.add(mobile2);
        }
        // 通过去重之后的HashSet长度来判断原list是否包含重复元素
        boolean isRepeat2 = userPhpnelist.size() != new HashSet<String>(userPhpnelist).size();
        System.out.println("list中包含重复元素：" + isRepeat2);
        if (isRepeat2) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,手机号号已存在");
            return false;
        }

        // 通过去重之后的HashSet长度来判断原list是否包含重复元素
        boolean isRepeat = userNamelist.size() != new HashSet<String>(userNamelist).size();
        System.out.println("list中包含重复元素：" + isRepeat);
        if (isRepeat) {
            result.put("code", "500");
            result.put("msg", "第" + rowIndex + "行,登录账号已存在");
            return false;
        }
        list.add(yhUserReginfo);
        if (yhUserPersonageCondition != null) {
            listPersonageCondition.add(yhUserPersonageCondition);
        }
	
		
		/*	if(yhUserPersonageCondition!=null) {
				listPersonageCondition.add(yhUserPersonageCondition);
			}
			if(yhUserPersonageCondition!=null) {
				listPersonageCondition.add(yhUserPersonageCondition);
			}*/


        if (yhUserBloodsuger != null) {
            listyHUserBloodsuger.add(yhUserBloodsuger);
        }
        if (yhUserBloodfat != null) {
            listyHUserBloodfat.add(yhUserBloodfat);
        }
		
		/*	if(yhUserBloodfat!=null) {
			listyHUserBloodfat.add(yhUserBloodfat);
		}
		
			if(yhUserBloodfat!=null) {
				listyHUserBloodfat.add(yhUserBloodfat);
			}
			
			if(yhUserBloodfat!=null) {
				listyHUserBloodfat.add(yhUserBloodfat);
			}*/
        System.out.println("血压" + yhUserBloodPressure);
        if (null != yhUserBloodPressure) {
            listyHUserBloodPressure.add(yhUserBloodPressure);
        }
		/*	if(yhUserBloodPressure!=null){
				listyHUserBloodPressure.add(yhUserBloodPressure);
			}*/

        return true;
    }

    /**
     * 判断对象是否为空，且对象的所有属性都为空
     * ps: boolean类型会有默认值false 判断结果不会为null 会影响判断结果
     * 序列化的默认值也会影响判断结果
     *
     * @param object
     * @return
     */
    public boolean objCheckIsNull(Object object) {
        Class clazz = (Class) object.getClass(); // 得到类对象
        Field fields[] = clazz.getDeclaredFields(); // 得到所有属性
        boolean flag = true; //定义返回结果，默认为true
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object); //得到属性值
                Type fieldType = field.getGenericType();//得到属性类型
                String fieldName = field.getName(); // 得到属性名
                System.out.println("属性类型：" + fieldType + ",属性名：" + fieldName + ",属性值：" + fieldValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldValue != null) {  //只要有一个属性值不为null 就返回false 表示对象不为null
                flag = false;
                break;
            }
        }
        return flag;
    }

    @Override
    public String selectMaxUserCod2() {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.selectMaxUserCode2();
    }

    @Override
    public PageInfo<InterveneVOJO> selectByPrimaryKeyBydoctorNew(String usercode, String strTime, String endTime, String dgroup, String nameLike, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<InterveneVOJO> selectByPrimaryKeyBydoctorNew = yhUserReginfoMapper.selectByPrimaryKeyBydoctorNew(usercode, strTime, endTime, dgroup, nameLike);

        return new PageInfo<InterveneVOJO>(selectByPrimaryKeyBydoctorNew);
    }

    @Override
    public YhUserReginfo selectByPrimaryKey(String userCode) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.selectByPrimaryKey(userCode);
    }

    public InformationSummary selectUserInfo2(String usercode) {
        InformationSummary info = new InformationSummary();
        YhUserReginfo selectByPrimaryKey = selectByPrimaryKey(usercode);
        Date birthDate = selectByPrimaryKey.getBirthDate();
        int ageByBirth = getAgeByBirth(birthDate);
        info.setAge(ageByBirth);
        return info;
    }

    //比较两个时间的方法，time1小于time2返回true
    public boolean compare(String time1, String time2) throws ParseException {
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //将字符串形式的时间转化为Date类型的时间
        Date a = sdf.parse(time1);
        Date b = sdf.parse(time2);
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if (a.before(b))
            return true;
        else
            return false;

    }


    public InformationSummary selectUserInfo(String usercode) throws ParseException {
        InformationSummary info = new InformationSummary();
        YhUserReginfo selectByPrimaryKey = selectByPrimaryKey(usercode);
        info.setUser_code(selectByPrimaryKey.getUserCode());
        info.setUser_name(selectByPrimaryKey.getUserName());
        Date birthDate = selectByPrimaryKey.getBirthDate();
        Date regTime2 = selectByPrimaryKey.getRegTime();
        String format2 = RelativeDateFormat.format(regTime2);
        info.setRegisterTime(format2);
        int ageByBirth = getAgeByBirth(birthDate);
        info.setAge(ageByBirth);

        // 健康评估
        String selectByPrimaryKeyByusercodeForQtime = YhDiseaseAssessTotalReportServiceImpl
                .selectByPrimaryKeyByusercodeForQtime(usercode);

        if (StringUtil.isNotEmpty(selectByPrimaryKeyByusercodeForQtime)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
            Date date = format.parse(selectByPrimaryKeyByusercodeForQtime);
            String assformat = RelativeDateFormat.format(date);
            info.setAssessTime(assformat);
        }

        // 数据监测时间
        String findMonitorInfoByTimeOrder = findMonitorInfoByTimeOrder(usercode);
        if (StringUtil.isNotEmpty(findMonitorInfoByTimeOrder)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
            try {
                Date date = format.parse(findMonitorInfoByTimeOrder);
                String pressureTimeformat = RelativeDateFormat.format(date);
                info.setMonitorTime(pressureTimeformat);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        //档案更新时间
        String userEnergyUptimeInfo = yhUserEnrolinfoMapper.findUserEnergyUptimeInfo(usercode);
        if (StringUtil.isNotEmpty(userEnergyUptimeInfo)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
            Date date = format.parse(userEnergyUptimeInfo);
            String Monitorformat = RelativeDateFormat.format(date);
            info.setArchivesTime(Monitorformat);
        }


        //最新咨询时间
        String findChatRecordByAddTime = YhUserChatRecordWedMapper.findChatRecordByAddTime(usercode);
        if (StringUtil.isNotEmpty(findChatRecordByAddTime)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
            Date date = format.parse(findChatRecordByAddTime);
            String Monitorformat = RelativeDateFormat.format(date);
            info.setConsult(Monitorformat);
        }


        //习惯评估时间 ing
        String findHabitDictNewInfo = YhLifeHabitImprovementDictMapper.findHabitDictNewInfo(usercode);
        if (StringUtil.isNotEmpty(findHabitDictNewInfo)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
            Date date = format.parse(findHabitDictNewInfo);
            String Monitorformat = RelativeDateFormat.format(date);
            info.setHabitTime(Monitorformat);
        }

        //压力评估时间
        String selectYhPressureEvaluateByTimeAndUserCode = YhPressureEvaluateServiceImpl.selectYhPressureEvaluateByTimeAndUserCode(usercode);
        if (StringUtil.isNotEmpty(selectYhPressureEvaluateByTimeAndUserCode)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
            Date date = format.parse(selectYhPressureEvaluateByTimeAndUserCode);
            String Monitorformat = RelativeDateFormat.format(date);
            info.setPressureTime(Monitorformat);
        }
        //健康评估次数
        int selectByPrimaryByUsercodeForCount = YhDiseaseAssessTotalReportServiceImpl.selectByPrimaryByUsercodeForCount(usercode);
        info.setAssesscount(selectByPrimaryByUsercodeForCount);

        //多少次课堂学习
        int findHealthLectureWatchByUsercode = YhHealthLectureWatchMapper.findHealthLectureWatchByUsercode(usercode);
        info.setClasscount(findHealthLectureWatchByUsercode);

        //多少次睡眠评估
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("userCode", usercode);
        int selectSleepDiaryConut = YhSleepReportServiceImpl.selectSleepReportByuserCodeForCount(map1);
        info.setSleepcount(selectSleepDiaryConut);
        //最近一次睡眠评估
        String selectSleepReportByuserCodeForNewTime = YhSleepReportServiceImpl.selectSleepReportByuserCodeForNewTime(map1);

        if (StringUtil.isNotEmpty(selectSleepReportByuserCodeForNewTime)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
            Date date = format.parse(selectSleepReportByuserCodeForNewTime);
            String pressureTimeformat = RelativeDateFormat.format(date);
            info.setSleepNewTime(pressureTimeformat);
        }

        //查询最新评估信息
        String gaofxInfo = "高风险\n\n";
        String zhongfxInfo = "中等风险\n\n";
        String huanfxInfo = "患或异\n\n";
        String df_names = "";
        String sk_names = "";
        String ld_names = "";
        List<String> goodList = new ArrayList<String>();
        List<String> nogoodList = new ArrayList<String>();
        YhDiseaseAssessReport findUserAssessReportInfoByTimeDesc = YhDiseaseAssessReportServiceImpl.findUserAssessReportInfoByTimeDesc(usercode);
        if (null != findUserAssessReportInfoByTimeDesc) {
            String rs = findUserAssessReportInfoByTimeDesc.getRequestSerialnum();
            //star
            //,String.valueOf(selectByPrimaryKey.getGender())

            List<YhDiseaseAssessReport> list = yhDiseaseAssessReportMapper.findDiseaseNameAndRiskLevelByRs(rs);

            for (YhDiseaseAssessReport yhDiseaseAssessReport : list) {
                List<YHDiseaseRiskOptimal> findYhDiseaseRiskOptimal = yhDiseaseAssessReportMapper
                        .findYhDiseaseRiskOptimal2(String.valueOf(yhDiseaseAssessReport.getDiseasid()), 1, String.valueOf(selectByPrimaryKey.getGender()));
                // 查询评估相关危险因素
                YhDiseaseRiskResult2 yhDiseaseRiskResult = YhDiseaseRiskResultMapper.selectRiskResulByRequestNum(rs);
                YhDiseaseRiskFactorGroups yhDiseaseRiskFactors = null;
                for (YHDiseaseRiskOptimal yhDiseaseRiskOptimal : findYhDiseaseRiskOptimal) {

                    yhDiseaseRiskFactors = new YhDiseaseRiskFactorGroups();
                    yhDiseaseRiskFactors.setDiseaseRiskFactorName(yhDiseaseRiskOptimal.getQname());
                    // 调用计算雷达图以及风险因素
                    doDiseaseRiskResult2(yhDiseaseRiskOptimal, yhDiseaseRiskResult, yhDiseaseRiskFactors);
                    if (null == yhDiseaseRiskFactors.getDiseaseRiskFactorSegment() || null == yhDiseaseRiskFactors.getDiseaseRiskFactorBadge()) {
                        continue;
                    }
                    if (yhDiseaseRiskFactors.getDiseaseRiskFactorSegment() < yhDiseaseRiskFactors.getDiseaseRiskFactorBadge()) {
                        if ("1".equals(yhDiseaseRiskOptimal.getRadarRangePoint())) {
                            nogoodList.add(yhDiseaseRiskOptimal.getQname());
                        } else {
                            if (!"age".equals(yhDiseaseRiskOptimal.getXpath())) {
                                goodList.add(yhDiseaseRiskOptimal.getQname());
                            }

                        }
                    }

                }
            }


            //end
        }
        if (goodList.size() > 0) {
            List<String> listTemp = new ArrayList<String>();
            for (int i = 0; i < goodList.size(); i++) {
                if (!listTemp.contains(goodList.get(i))) {
                    listTemp.add(goodList.get(i));
                }
            }


            info.setGoodList(listTemp);

        } else {
            info.setGoodList(goodList);
        }

        if (nogoodList.size() > 0) {

            List<String> listTemp2 = new ArrayList<String>();
            for (int i = 0; i < nogoodList.size(); i++) {
                if (!listTemp2.contains(nogoodList.get(i))) {
                    listTemp2.add(nogoodList.get(i));
                }
            }

            info.setNogoodList(listTemp2);
        } else {
            info.setNogoodList(nogoodList);
        }


        if (null != findUserAssessReportInfoByTimeDesc) {

            df_names = findUserAssessReportInfoByTimeDesc.getDf_names();
            if (StringUtil.isNotEmpty(df_names)) {
                String[] split = df_names.split(",");
                String gaofx = "";
                for (int i = 0; i < split.length; i++) {
                    String string = split[i];
                    if (StringUtil.isEmpty(string)) {
                        continue;
                    }
                    if (StringUtil.isNotEmpty(string)) {
                        if (i == split.length - 1) {
                            gaofx = gaofx + string;
                        } else {
                            gaofx = gaofx + string + "、";
                        }
                    }
                }
                gaofxInfo = gaofxInfo + gaofx;
            }
            sk_names = findUserAssessReportInfoByTimeDesc.getSk_names();
            if (StringUtil.isNotEmpty(sk_names)) {
                String[] split = sk_names.split(",");
                String huanfx = "";
                for (int i = 0; i < split.length; i++) {
                    String string = split[i];
                    if (StringUtil.isEmpty(string)) {
                        continue;
                    }
                    if (StringUtil.isNotEmpty(string)) {
                        if (i == split.length - 1) {
                            huanfx = huanfx + string;
                        } else {
                            huanfx = huanfx + string + "、";
                        }
                    }
                }
                huanfxInfo = huanfxInfo + huanfx;

            }
            ld_names = findUserAssessReportInfoByTimeDesc.getLd_names();
            if (StringUtil.isNotEmpty(ld_names)) {
                String[] split = ld_names.split(";");
                String zhongfx = "";
                for (int i = 0; i < split.length; i++) {
                    String string = split[i];
                    if (StringUtil.isEmpty(string)) {
                        continue;
                    }

                    if (StringUtil.isNotEmpty(string)) {
                        if (i == split.length - 1) {
                            zhongfx = zhongfx + string;
                        } else {
                            zhongfx = zhongfx + string + "、";
                        }
                    }
                }
                zhongfxInfo = zhongfxInfo + zhongfx;
            }


        }

        if (StringUtil.isEmpty(df_names)) {
            gaofxInfo = gaofxInfo + "--";
        }

        if (StringUtil.isEmpty(ld_names)) {
            zhongfxInfo = zhongfxInfo + "--";
        }
        if (StringUtil.isEmpty(sk_names)) {
            huanfxInfo = huanfxInfo + "--";
        }


        info.setGaofxInfo(gaofxInfo);
        info.setZhongfxInfo(zhongfxInfo);
        if (huanfxInfo.split(";").length > 6) {
            String newhuanfxInfo = "";
            String[] split = huanfxInfo.split(";");
            for (int i = 0; i < split.length; i++) {
                String s = split[i] + ";";
                if (i == 5) {
                    newhuanfxInfo = newhuanfxInfo + s + "\n\n";
                } else {
                    newhuanfxInfo = newhuanfxInfo + s;
                }
            }
            info.setHuanInfo(newhuanfxInfo);
        } else {
            info.setHuanInfo(huanfxInfo);
        }


        //查询干预状态
        InterveneVOJO findinterveneByUserCode = findinterveneByUserCode(usercode);
        if (null != findinterveneByUserCode) {
            List<YHDocGroup> glist = new ArrayList<YHDocGroup>();
            Integer evaluateFlag = findinterveneByUserCode.getEvaluateFlag();
            info.setInterveneYesOrNo(evaluateFlag);
            String grouping = findinterveneByUserCode.getGrouping();
            if (com.yihe.system.utils.StringUtil.isNotEmpty(grouping)) {
                String[] split = grouping.split(";");
                for (int i = 0; i < split.length; i++) {
                    String id = split[0];
                    id = id.replace("f", "");
                    String[] split2 = id.split("_");
                    id = split2[0];
                    if (com.yihe.system.utils.StringUtil.isEmpty(id)) {
                        continue;
                    }

                    YHDocGroup gInfo = YHDocGroupServiceImpl.selectByPrimaryKey(Integer.valueOf(id));
                    if (null != gInfo) {

                        glist.add(gInfo);
                    }


                }
            }
            info.setGroupList(glist);
        }

        //健康计划种类个数
        int findUserHealthyPlanGroupByPlanId = YhUserHealthyPlanServiceImpl.findUserHealthyPlanGroupByPlanId(usercode);
        info.setPlanTypeCount(findUserHealthyPlanGroupByPlanId);
        //健康计划日记天数条数
        int findUserHealthyPlanByuserCodeForCount = YhUserHealthyPlanServiceImpl.findUserHealthyPlanByuserCodeForCount(usercode);
        info.setPlanDairyCount(findUserHealthyPlanByuserCodeForCount);
        //压力评估次数
        int findPressureEvaluateByuserCodeForCount = YhPressureEvaluateServiceImpl.findPressureEvaluateByuserCodeForCount(usercode);
        info.setPressurecount(findPressureEvaluateByuserCodeForCount);

        //膳食日记记录天数
        int findFlldUserDiaryByusercodeForCount = YhFoodUserDiaryServiceImpl.findFlldUserDiaryByusercodeForCount(usercode);
        info.setFoodDiarycount(findFlldUserDiaryByusercodeForCount);
        //运动日记记录天数
        int findSportDiaryByUsercodeForCount = YhSportDiaryServiceImpl.findSportDiaryByUsercodeForCount(usercode);
        info.setSportDiarycount(findSportDiaryByUsercodeForCount);
        //睡眠日记天数
        int selectSleepDiaryConut2 = YhSleepUserDiaryServiceImpl.selectSleepDiaryConut(map1);
        info.setSleepDiarycount(selectSleepDiaryConut2);
        //评论和回复条数
        int findCommentByUserCodeForCount = YhHealthLectureCommentServiceImpl.findCommentByUserCodeForCount(usercode);
        info.setCommentcount(findCommentByUserCodeForCount);

        //食物记录条数
        int findFoodUserDiaRyBYusercodeForElements = YhFoodUserDiaryServiceImpl.findFoodUserDiaRyBYusercodeForElements(usercode);
        info.setFoodTypeCount(findFoodUserDiaRyBYusercodeForElements);


        //运动种类条数
        int findSportDiaryByusercodeForDetails = YhSportDiaryServiceImpl.findSportDiaryByusercodeForDetails(usercode);
        info.setSportTypeCount(findSportDiaryByusercodeForDetails);


        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        //本周饮食连续天数
        int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDuary(usercode);
        info.setFooddataweek(findFoodUserDuary);
        //本月饮食连续天数
        int[] findFoodUserDuaryYue = YhFoodUserDiaryServiceImpl.findFoodUserDuaryYue(usercode, String.valueOf(year), String.valueOf(month));
        info.setFooddatamonth(findFoodUserDuaryYue);
        String[] daysByYearMonth = getDaysByYearMonth(year, month);
        info.setFooddatamonthTime(daysByYearMonth);


        //本年饮食连续天数
        int[] findFoodUserDiaryByYear = YhFoodUserDiaryServiceImpl.findFoodUserDiaryByYear(usercode, String.valueOf(year));
        info.setFooddatayear(findFoodUserDiaryByYear);
        //本周运动
        int[] findSportDiaryByUserCodeForWeekDay = YhSportDiaryServiceImpl.findSportDiaryByUserCodeForWeekDay(usercode);
        info.setSportdataweek(findSportDiaryByUserCodeForWeekDay);
        //本月运动
        int[] findSportDiaryByUserCodeForMonth = YhSportDiaryServiceImpl.findSportDiaryByUserCodeForMonth(usercode, String.valueOf(year), String.valueOf(month));
        info.setSportdatamonth(findSportDiaryByUserCodeForMonth);
        //本年运动
        int[] findSportDiaryByuserCodeForyear = YhSportDiaryServiceImpl.findSportDiaryByuserCodeForyear(usercode, String.valueOf(year));
        info.setSportdatayear(findSportDiaryByuserCodeForyear);

        //本周睡眠连续天数
        int[] findsleepDiaryByUserCodeForWeekDay = YhSleepUserDiaryServiceImpl.findsleepDiaryByUserCodeForWeekDay(usercode);
        info.setSleepdataweek(findsleepDiaryByUserCodeForWeekDay);
        //本月
        int[] findsleepDiaryByUserCodeForMonth = YhSleepUserDiaryServiceImpl.findsleepDiaryByUserCodeForMonth(usercode, String.valueOf(year), String.valueOf(month));
        info.setSleepdatamonth(findsleepDiaryByUserCodeForMonth);
        //本年
        int[] findsleepDiaryByuserCodeForyear = YhSleepUserDiaryServiceImpl.findsleepDiaryByuserCodeForyear(usercode, String.valueOf(year));
        info.setSleepdatayear(findsleepDiaryByuserCodeForyear);


        //风险评估list
        List<YhDiseaseAssessReport> findAssessReportInfoList = YhDiseaseAssessReportServiceImpl.findAssessReportInfoList(usercode);
        info.setAssessMap(findAssessReportInfoList);

        System.out.println(info.toString());
        return info;
    }

    public static String toCamelCase(String s) {
        /** 下划线 */
        final char SEPARATOR = '_';
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public void doDiseaseRiskResult2(YHDiseaseRiskOptimal yhDiseaseRiskOptimal,
                                     YhDiseaseRiskResult2 yhDiseaseRiskResult, YhDiseaseRiskFactorGroups yhDiseaseRiskFactors) {
        //当前分数
        Integer badge = 0;
        //理想分数
        Integer Segment = 50;
        String nowValue = null;
        String qcode = toCamelCase(yhDiseaseRiskOptimal.getXpath());
        String qtype = yhDiseaseRiskOptimal.getQtype();
        String qvalue = yhDiseaseRiskOptimal.getValue();
        Collection<String> yhDiseaseRiskResultReflect;
        try {
            yhDiseaseRiskResultReflect = ReflectionUtil.getReflect(yhDiseaseRiskResult);
            for (String string : yhDiseaseRiskResultReflect) {
                if (string.equals(qcode)) {
                    System.out.println(yhDiseaseRiskResult + "---------------------------------------");
                    Object document = Configuration.defaultConfiguration().jsonProvider().parse(ReflectionUtil.getFieldValue(yhDiseaseRiskResult, string).toString());
                    nowValue = JsonPath.read(document, "$.now_value").toString();
                    //判断是否是数值
                    if ("T".equals(qtype)) {
                        Segment = 50;
                        if (qvalue.equals(nowValue)) {
                            badge = 50;
                        } else {
                            badge = 100;
                        }
                    } else {
                        Double reqvalue = 0.0;
                        if (StringUtil.isNotEmpty(nowValue)) {
                            reqvalue = Double.valueOf(nowValue);
                        }
                        Integer rangePoint = Integer.parseInt(yhDiseaseRiskOptimal.getRadarRangePoint());
                        int s = 100 / rangePoint;
                        Segment = s;
                        // 获取区间值
                        Double minValue = yhDiseaseRiskOptimal.getMinv();
                        Double maxvalue = yhDiseaseRiskOptimal.getMaxv();
                        if (rangePoint > 2) {
                            if (minValue <= reqvalue && reqvalue < maxvalue) {
                                badge = s;
                            } else {
                                badge = 100;
                            }

                        } else {
                            if (reqvalue < maxvalue) {
                                badge = 50;
                            } else {
                                badge = 100;
                            }
                        }
                    }
                    yhDiseaseRiskFactors.setDiseaseRiskFactorBadge(badge);
                    yhDiseaseRiskFactors.setDiseaseRiskFactorSegment(Segment);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public String[] getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);

        String[] zhou = new String[maxDate];
        for (int i = 0; i < maxDate; i++) {
            String day = (i + 1) + "号";
            zhou[i] = day;
        }
        return zhou;
    }

    @Override
    public YhUserReginfo selectYhUserReginfoData(String userCode) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.selectYhUserReginfoData(userCode);
    }

    @Override
    public int updateByPrimaryKeySelective(YhUserReginfo record) {
        // TODO Auto-generated method stub
        if (null != record.getLinetime()) {
            if (record.getLinetime().equals("")) {
                record.setLinetime(null);
            }
        }
        String makecard = yhUserReginfoMapper.selectorgcrd(record.getUserCode());
        yhUserReginfoMapper.updatelinetime(record.getLinetime(), makecard);
        return yhUserReginfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelectives(YhUserReginfo record) {
        // TODO Auto-generated method stub

        return yhUserReginfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int resetPwd(String userCode, String userPwd) {
        // TODO Auto-generated method stub
        int resetPwd = yhUserReginfoMapper.resetPwd(userCode, userPwd);
        return resetPwd;
    }

    @Override
    public List<YhUserReginfo> exportYhUserReginfoData(String userCode) {
        List<YhUserReginfo> exportDataList = yhUserReginfoMapper.exportYhUserReginfoData(userCode);
        return exportDataList;
    }

    @Override
    public List<YhUserReginfo> exportYhUserReginfoData1(String[] userCode) {
        List<YhUserReginfo> exportDataList = yhUserReginfoMapper.exportYhUserReginfoData1(userCode);
        return exportDataList;
    }

    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

        }
        return matches;
    }

    public static void main(String[] args) {
        String phone = "430524200003204071";
        String regex = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        boolean isMatch = m.matches();
        System.out.println(isMatch);
    }

    /**
     * 判断时间格式 格式必须为“YYYY-MM-dd”
     * 2004-2-30 是无效的
     * 2003-2-29 是无效的
     *
     * @param sDate
     * @return
     */
    private static boolean isLegalDate(String sDate) {
        int legalLen = 10;
        if ((sDate == null) || (sDate.length() != legalLen)) {
            return false;
        }

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    public String betweenDay(String date1, String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = sdf.parse(date1);

        Date d2 = sdf.parse(date2);

        long daysBetween = (d2.getTime() - d1.getTime() + 1000000) / (60 * 60 * 24 * 1000);
        return daysBetween + "";

    }

    /**
     * date转String
     */

    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 根据出生算年龄
     *
     * @param birthday
     * @return
     */
    private int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {// 兼容性更强,异常后返回数据
            return 0;
        }
    }

    @Override
    public String findMonitorInfoByTimeOrder(String usercode) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.findMonitorInfoByTimeOrder(usercode);
    }

    @Override
    public PageInfo<Map<String, Object>> selecthealthRecordManagePageByParam(Map<String, Object> healthMap, int page, int limit) {
        // TODO Auto-generated method stub
        //取当前登录用户名
        YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
        healthMap.put("userCode", yhUserReginfo.getUserCode());

        //设置分页参数
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> list = yhUserReginfoMapper.selecthealthRecordManagePageByParam(healthMap);
        return new PageInfo<Map<String, Object>>(list);

    }

    @Override
    public int updateuserStatus(String userCode, String lockFlag) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.updateuserStatus(userCode, lockFlag);
    }

    @Override
    public YhUserReginfo selectUserPwd(String userCode, String userPwd) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.selectUserPwd(userCode, userPwd);
    }

    @Override
    public InterveneVOJO findinterveneByUserCode(String usercode) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.findinterveneByUserCode(usercode);
    }

    @Override
    public List<YhUserReginfo> queryDoctorList() {
        return yhUserReginfoMapper.queryDoctorList();
    }

    @Override
    public List<YhUserReginfo> selectAllUserinfoByuserCode(String userCode) {
        return yhUserReginfoMapper.selectAllUserinfoByuserCode(userCode);
    }

    @Override
    public String selectOrgName(String usercode) {
        return yhUserReginfoMapper.selectOrgName(usercode);
    }

    @Override
    public int modifyinterveneByUserCode(String usercode) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.modifyinterveneByUserCode(usercode);
    }

    @Override
    public YhUserReginfo findYhUserReginfo(String userCode) {
        return yhUserReginfoMapper.findYhUserReginfo(userCode);
    }

    @Override
    public String selectCardId(YhUserMakeCard yhUserMakeCard) {
        return yhUserReginfoMapper.selectCardId(yhUserMakeCard);
    }

    @Override
    public YhUserMakeCard selectActivationCardId(String key) {
        return yhUserReginfoMapper.selectActivationCardId(key);
    }

    @Override
    public int modifyinterveneByUserCodeAndGroupInfo(String groupInfo, String usercode) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.modifyinterveneByUserCodeAndGroupInfo(groupInfo, usercode);
    }

    @Override
    public List<YhUserReginfo> selectPhoneAndName(String mobile, String userName) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.selectPhoneAndName(mobile, userName);
    }

    @Override
    public int updatelinetime(String time, String orgcode) {
        return yhUserReginfoMapper.updatelinetime(time, orgcode);
    }

    @Override
    public int changefinishidoctor() {
        List<YhJzService> list = yhUserReginfoMapper.selecttotalservice();
        for (int i = 0; i < list.size(); i++) {
            yhUserReginfoMapper.updateshidoctor(list.get(i));
        }
        return 1;
    }

    @Override
    public Map<String, Object> findUserInfo(String userCode) {
        YhUserReginfo reginfoApp = yhUserReginfoMapper.findYhUserReginfo(userCode);
        Map<String, Object> map = new HashMap<>();
        map.put("sex", reginfoApp.getGender() - 1);
        map.put("age", getAgeByBirth(reginfoApp.getBirthDate()));
        map.put("xm", reginfoApp.getName());
        map.put("sjh", reginfoApp.getMobile());
        return map;
    }

    @Override
    public List<YhUserReginfo> yhuserenrolinfo() {
        // TODO Auto-generated method stub
        List<YhUserReginfo> yhuserenrolinfo = yhUserReginfoMapper.yhuserenrolinfo();
        return yhuserenrolinfo;
    }

    @Override
    public List<Map<String, Object>> selecthealthRecordManagePageByParam2(String userCode) {
        // TODO Auto-generated method stub
        return yhUserReginfoMapper.selecthealthRecordManagePageByParam2(userCode);
    }

    @Override
    public YhUserReginfo selectByCardNum(String cardNum) {
        return yhUserReginfoMapper.selectUserCodeByIdCard(cardNum);
    }
}