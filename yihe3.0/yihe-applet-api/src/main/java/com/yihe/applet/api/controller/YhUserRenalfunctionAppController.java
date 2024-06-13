package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.domain.YhUserRenalfunctionApp;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.applet.api.service.YhUserRenalfunctionAppService;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 肾功能 Created by dyb on 2019/12/24.
 */
@Controller
@RequestMapping("/api/YhUserRenalfunction")
@Api(value = "YhUserRenalfunction控制器", description = "YhUserRenalfunction管理")
public class YhUserRenalfunctionAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserRenalfunctionAppController.class);

    @Autowired
    YhUserRenalfunctionAppService yhUserRenalfunctionService;
    @Autowired
    YhHeThresholdService yhHeThresholdService;
    @Autowired
    YhUserReginfoAppService YhUserReginfoService;
    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode,Integer timeType,String time) throws java.text.ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> timeList = new HashSet<>();

        YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(usercode);
        if (null == findYhUserReginfo) {
            return Result.error("参数异常");
        }
        Integer gender = findYhUserReginfo.getGender();
        if(0==timeType) {
            String[] split = time.split("~");
            List<YhUserRenalfunctionApp> list = yhUserRenalfunctionService.findRenInfoAllByUserCode(usercode,timeType,split[0],split[1]);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserRenalfunctionApp yhUserRenalfunctionApp = list.get(i);
                    String entryTime = yhUserRenalfunctionApp.getInerttime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    String creatinine = yhUserRenalfunctionApp.getCreatinine();//肌酐
                    String ureaNitrogen = yhUserRenalfunctionApp.getUreaNitrogen();//尿素氮
                    String uricAcid = yhUserRenalfunctionApp.getUricAcid();//尿酸
                    if (StringUtil.isNotEmpty(uricAcid) && StringUtil.isNotEmpty(ureaNitrogen) && StringUtil.isNotEmpty(creatinine)) {
                        Double aDouble = Double.valueOf(creatinine);
                        Double aDouble1 = Double.valueOf(ureaNitrogen);
                        Double aDouble2 = Double.valueOf(uricAcid);
                        //男
                        if (gender == 1) {
                            if (aDouble >= 54 && aDouble <= 106 && aDouble1 >= 2.9 && aDouble1 <= 7.5 && aDouble2 >= 210 && aDouble2 <= 420) {
                                k++;
                            }
                        } else {
                            if (aDouble >= 44 && aDouble <= 97 && aDouble1 >= 2.9 && aDouble1 <= 7.5 && aDouble2 >= 150 && aDouble2 <= 360) {
                                k++;
                            }
                        }
                    }
                }
                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));
            }

        }else{
            List<YhUserRenalfunctionApp> list = yhUserRenalfunctionService.findRenInfoAllByUserCode(usercode,timeType,time,null);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserRenalfunctionApp yhUserRenalfunctionApp = list.get(i);
                    String entryTime = yhUserRenalfunctionApp.getInerttime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    String creatinine = yhUserRenalfunctionApp.getCreatinine();//肌酐
                    String ureaNitrogen = yhUserRenalfunctionApp.getUreaNitrogen();//尿素氮
                    String uricAcid = yhUserRenalfunctionApp.getUricAcid();//尿酸
                    if (StringUtil.isNotEmpty(uricAcid) && StringUtil.isNotEmpty(ureaNitrogen) && StringUtil.isNotEmpty(creatinine)) {
                        Double aDouble = Double.valueOf(creatinine);
                        Double aDouble1 = Double.valueOf(ureaNitrogen);
                        Double aDouble2 = Double.valueOf(uricAcid);
                        //男
                        if (gender == 1) {
                            if (aDouble >= 54 && aDouble <= 106 && aDouble1 >= 2.9 && aDouble1 <= 7.5 && aDouble2 >= 210 && aDouble2 <= 420) {
                                k++;
                            }
                        } else {
                            if (aDouble >= 44 && aDouble <= 97 && aDouble1 >= 2.9 && aDouble1 <= 7.5 && aDouble2 >= 150 && aDouble2 <= 360) {
                                k++;
                            }
                        }
                    }
                }
                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));
            }
        }
        return Result.ok(map);
    }
    public static String transferFormat2(String inTime) throws ParseException {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");

        Date tempDate = null;
        String outTime = null;
        tempDate = s1.parse(inTime);
        outTime = s1.format(tempDate);
        return outTime;
    }
    @RequestMapping(value = "/pieChartAssignment")
    @ResponseBody
    public Result pieChartAssignment(String usercode, Integer timetype, String time) {
        if (StringUtil.isEmpty(usercode)) {
            return Result.error();
        }
        YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(usercode);
        if (null == findYhUserReginfo) {
            return Result.error("参数异常");
        }
        Integer gender = findYhUserReginfo.getGender();

        if (0 == timetype) {
            String[] split = time.split("~");
            int zhengchang = 0;
            int yichang = 0;
            List<YhUserRenalfunctionApp> list = yhUserRenalfunctionService.findRenInfoByTimeType(usercode, timetype, split[0], split[1]);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserRenalfunctionApp yhUserRenalfunctionApp = list.get(i);
                    String creatinine = yhUserRenalfunctionApp.getCreatinine();//肌酐
                    String ureaNitrogen = yhUserRenalfunctionApp.getUreaNitrogen();//尿素氮
                    String uricAcid = yhUserRenalfunctionApp.getUricAcid();//尿酸
                    if (StringUtil.isNotEmpty(uricAcid) && StringUtil.isNotEmpty(ureaNitrogen) && StringUtil.isNotEmpty(creatinine)) {
                        Double aDouble = Double.valueOf(creatinine);
                        Double aDouble1 = Double.valueOf(ureaNitrogen);
                        Double aDouble2 = Double.valueOf(uricAcid);
                        //男
                        if (gender == 1) {
                            if (aDouble >= 54 && aDouble <= 106 && aDouble1 >= 2.9 && aDouble1 <= 7.5 && aDouble2 >= 210 && aDouble2 <= 420) {
                                zhengchang++;
                            } else {
                                yichang++;
                            }
                        } else {
                            if (aDouble >= 44 && aDouble <= 97 && aDouble1 >= 2.9 && aDouble1 <= 7.5 && aDouble2 >= 150 && aDouble2 <= 360) {
                                zhengchang++;
                            } else {
                                yichang++;
                            }
                        }
                    } else {
                        yichang++;
                    }

                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("yichang", yichang);
            map.put("zhengchang", zhengchang);
            return Result.ok(map);
        } else if (1 == timetype) {
            int zhengchang = 0;
            int yichang = 0;
            List<YhUserRenalfunctionApp> list = yhUserRenalfunctionService.findRenInfoByTimeType(usercode, timetype, time, null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserRenalfunctionApp yhUserRenalfunctionApp = list.get(i);
                    String creatinine = yhUserRenalfunctionApp.getCreatinine();//肌酐
                    String ureaNitrogen = yhUserRenalfunctionApp.getUreaNitrogen();//尿素氮
                    String uricAcid = yhUserRenalfunctionApp.getUricAcid();//尿酸
                    if (StringUtil.isNotEmpty(uricAcid) && StringUtil.isNotEmpty(ureaNitrogen) && StringUtil.isNotEmpty(creatinine)) {
                        Double aDouble = Double.valueOf(creatinine);
                        Double aDouble1 = Double.valueOf(ureaNitrogen);
                        Double aDouble2 = Double.valueOf(uricAcid);
                        //男
                        if (gender == 1) {
                            if (aDouble >= 54 && aDouble <= 106 && aDouble1 >= 2.9 && aDouble1 <= 7.5 && aDouble2 >= 210 && aDouble2 <= 420) {
                                zhengchang++;
                            } else {
                                yichang++;
                            }
                        } else {
                            if (aDouble >= 44 && aDouble <= 97 && aDouble1 >= 2.9 && aDouble1 <= 7.5 && aDouble2 >= 150 && aDouble2 <= 360) {
                                zhengchang++;
                            } else {
                                yichang++;
                            }
                        }
                    } else {
                        yichang++;
                    }
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("yichang", yichang);
            map.put("zhengchang", zhengchang);
            return Result.ok(map);
        } else if (2 == timetype) {
            int zhengchang = 0;
            int yichang = 0;
            List<YhUserRenalfunctionApp> list = yhUserRenalfunctionService.findRenInfoByTimeType(usercode, timetype, time, null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserRenalfunctionApp yhUserRenalfunctionApp = list.get(i);
                    String creatinine = yhUserRenalfunctionApp.getCreatinine();//肌酐
                    String ureaNitrogen = yhUserRenalfunctionApp.getUreaNitrogen();//尿素氮
                    String uricAcid = yhUserRenalfunctionApp.getUricAcid();//尿酸
                    if (StringUtil.isNotEmpty(uricAcid) && StringUtil.isNotEmpty(ureaNitrogen) && StringUtil.isNotEmpty(creatinine)) {
                        Double aDouble = Double.valueOf(creatinine);
                        Double aDouble1 = Double.valueOf(ureaNitrogen);
                        Double aDouble2 = Double.valueOf(uricAcid);
                        //男
                        if (gender == 1) {
                            if (aDouble >= 54 && aDouble <= 106 && aDouble1 >= 2.9 && aDouble1 <= 7.5 && aDouble2 >= 210 && aDouble2 <= 420) {
                                zhengchang++;
                            } else {
                                yichang++;
                            }
                        } else {
                            if (aDouble >= 44 && aDouble <= 97 && aDouble1 >= 2.9 && aDouble1 <= 7.5 && aDouble2 >= 150 && aDouble2 <= 360) {
                                zhengchang++;
                            } else {
                                yichang++;
                            }
                        }
                    } else {
                        yichang++;
                    }
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("yichang", yichang);
            map.put("zhengchang", zhengchang);
            return Result.ok(map);
        }
        return Result.error();
    }

    /**
     * 修改肾功能数据
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/modifyBmiInfo")
    @ResponseBody
    public Result modifyBmiInfo(YhUserRenalfunctionApp record) {
        yhUserRenalfunctionService.updateByPrimaryKeySelective(record);
        return Result.ok();
    }

    /**
     * 查询最新肾功能
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/findRenalfunctionNew")
    @ResponseBody
    public Result findRenalfunctionNew(String eid) {
        YhUserRenalfunctionApp selectByPrimaryKeyNewest6 = yhUserRenalfunctionService.selectByPrimaryKeyNewest(eid);// 肾功能
        return Result.ok(selectByPrimaryKeyNewest6);
    }

    /**
     * 获取当前系统时间String类型返回值
     *
     * @return
     */
    public String NowString() {
        SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    /**
     * 根据eid查询全部历史信息
     *
     * @param eid
     * @return
     */
    @RequestMapping(value = "/findInfoByeidAll")
    @ResponseBody
    public Result findInfoByeidAll(String eid) {
        System.out.println(eid);

        List<YhUserRenalfunctionApp> selectByPrimaryKeyByUserIdLimit7 = yhUserRenalfunctionService
                .selectByPrimaryKeyBYeid(eid);

        if (null != selectByPrimaryKeyByUserIdLimit7) {
            return Result.ok(selectByPrimaryKeyByUserIdLimit7);
        }

        return Result.error();
    }

    /**
     * 根据主键查询肾功能信息
     */
    @RequestMapping(value = "/findInfoByKey")
    @ResponseBody
    public Result findInfoByKey(Integer id) {
        if (null == id) {
            return Result.error();
        }
        YhUserRenalfunctionApp selectByPrimaryKey = yhUserRenalfunctionService.selectByPrimaryKey(id);
        return Result.ok(selectByPrimaryKey);
    }

    /**
     * 新增记录
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserRenalfunctionApp record, HttpServletRequest request) {

        System.out.println(record);
        // 修改
        if (null != record.getRenalid() && 0 != record.getRenalid()) {
            record.setInerttime(null);
            try {
                if(record.getIsDel()!=1){
                yhHeThresholdService.selectBythresType("blood_uric_acid", String.valueOf(record.getUserId()), String.valueOf(record.getUricAcid()), "血尿酸",request);
                yhHeThresholdService.selectBythresType("blood_muscle_anhydride", String.valueOf(record.getUserId()), String.valueOf(record.getCreatinine()), "血肌酐",request);
                yhHeThresholdService.selectBythresType("blood_urea_nitrogen", String.valueOf(record.getUserId()), String.valueOf(record.getUreaNitrogen()), "血尿素氮",request);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int updateByExampleSelective = yhUserRenalfunctionService.updateByPrimaryKeySelective(record);
            return Result.ok(updateByExampleSelective);

        } else {
            record.setIsDel(2);
            record.setInerttime(record.getInerttime() + NowString());
            int insertSelective = yhUserRenalfunctionService.insertSelective(record);
            try {

                yhHeThresholdService.selectBythresType("blood_uric_acid", String.valueOf(record.getUserId()), String.valueOf(record.getUricAcid()), "血尿酸",request);
                yhHeThresholdService.selectBythresType("blood_muscle_anhydride", String.valueOf(record.getUserId()), String.valueOf(record.getCreatinine()), "血肌酐",request);
                yhHeThresholdService.selectBythresType("blood_urea_nitrogen", String.valueOf(record.getUserId()), String.valueOf(record.getUreaNitrogen()), "血尿素氮",request);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (insertSelective > 0) {
                return Result.ok(insertSelective);
            }

            return Result.error();
        }

    }

    /**
     * 修改记录
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/modifyInfo")
    @ResponseBody
    public Result modifyInfo(YhUserRenalfunctionApp record) {
        System.out.println(record);
        record.setIsDel(2);

        record.setInerttime(record.getInerttime() + NowString());

        int insertSelective = yhUserRenalfunctionService.updateByPrimaryKeySelective(record);
        if (insertSelective > 0) {
            return Result.ok();
        }

        return Result.error();
    }

    /**
     * 查询已经录入的日期集合selectByPrimaryKeyFindEntryTime
     */

    @RequestMapping(value = "/selectByPrimaryKeyFindEntryTime")
    @ResponseBody
    public Result selectByPrimaryKeyFindEntryTime(String usercode) {
        if (null == usercode) {
            Result.error();
        }

        List<YhUserRenalfunctionApp> selectByPrimaryKeyFindEntryTime = yhUserRenalfunctionService
                .selectByPrimaryKeyFindEntryTime(usercode);

        return Result.ok(selectByPrimaryKeyFindEntryTime);
    }

    /**
     * 查询当前日记录
     */
    @RequestMapping(value = "/selectByPrimaryKeyFindEntryTimeAnduserid")
    @ResponseBody
    public Result selectByPrimaryKeyFindEntryTimeAnduserid(String usercode, String time) {
        if (null == usercode || null == time) {
            Result.error();
        }

        List<YhUserRenalfunctionApp> list = yhUserRenalfunctionService
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
        YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(usercode);
        if (null == findYhUserReginfo) {
            return Result.error("参数异常");
        }
        Integer gender = findYhUserReginfo.getGender();
        if(null!=list){
            for (int i = 0; i < list.size(); i++) {
                YhUserRenalfunctionApp yhUserRenalfunctionApp = list.get(i);
                yhUserRenalfunctionApp.setOpenNo(false);
                if(i==0){
                    yhUserRenalfunctionApp.setOpenNo(true);
                }
                String dataSource = yhUserRenalfunctionApp.getDataSource();
                String creatinine = yhUserRenalfunctionApp.getCreatinine();
                String ureaNitrogen = yhUserRenalfunctionApp.getUreaNitrogen();
                String uricAcid = yhUserRenalfunctionApp.getUricAcid();

                yhUserRenalfunctionApp.setTiwen("正常");
                if (dataSource.equals("一体机")  || dataSource.equals("随诊包")|| dataSource.equals("中科善益康S5")){

                    if(StringUtil.isNotEmpty(uricAcid)){
                        if (gender== 1) {
                            if ( Double.valueOf(uricAcid)  < 210 ||  Double.valueOf(uricAcid)  > 420) {
                                yhUserRenalfunctionApp.setTiwen("异常");
                            }
                        }else{
                            if ( Double.valueOf(uricAcid)  < 150 ||  Double.valueOf(uricAcid)  > 360) {
                                yhUserRenalfunctionApp.setTiwen("异常");
                            }
                        }
                    }else{
                        yhUserRenalfunctionApp.setTiwen("异常");
                    }
                }else{

                    if(StringUtil.isNotEmpty(creatinine)&&StringUtil.isNotEmpty(ureaNitrogen)&&StringUtil.isNotEmpty(uricAcid)){
                        if (gender == 1) {
                            if (Double.valueOf(creatinine)  < 54 || Double.valueOf(creatinine) > 106) {
                                yhUserRenalfunctionApp.setTiwen("异常");
                            }
                            if (Double.valueOf(ureaNitrogen) < 2.9 || Double.valueOf(ureaNitrogen)  > 7.5) {
                                yhUserRenalfunctionApp.setTiwen("异常");
                            }
                            if (Double.valueOf(uricAcid)  < 210 || Double.valueOf(uricAcid)  > 420) {
                                yhUserRenalfunctionApp.setTiwen("异常");
                            }
                        } else {
                            if (Double.valueOf(creatinine) < 44 || Double.valueOf(creatinine) > 97) {
                                yhUserRenalfunctionApp.setTiwen("异常");
                            }
                            if (Double.valueOf(ureaNitrogen) < 2.9 || Double.valueOf(ureaNitrogen) > 7.5) {
                                yhUserRenalfunctionApp.setTiwen("异常");
                            }
                            if (Double.valueOf(uricAcid) < 150 || Double.valueOf(uricAcid) > 360) {
                                yhUserRenalfunctionApp.setTiwen("异常");
                            }
                        }
                    }else{
                        yhUserRenalfunctionApp.setTiwen("异常");
                    }





                }
                if("异常".equals(yhUserRenalfunctionApp.getTiwen())){
                    yhUserRenalfunctionApp.setJiedu("评估肾脏有没有问题，仅通过血液生化查肾功能三项是不够的。肾功能三项出现异常，可以由医生根据具体情况，进一步选做其他相关检查，最后由医生结合临床资料和其他检查结果综合分析，得出客观结论。");
                }else{
                    yhUserRenalfunctionApp.setJiedu("一般这种情况下，如果没有症状就可以不用担心肾脏疾病。如果伴有具体症状，建议到医院再做其他方面的检查，让医生帮忙检查诊断一下，再给予相应的治疗。");

                }

            }
        }
        return Result.ok(list);
    }
}