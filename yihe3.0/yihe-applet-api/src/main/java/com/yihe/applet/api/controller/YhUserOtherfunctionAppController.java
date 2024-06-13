package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserOtherfunctionApp;
import com.yihe.applet.api.service.YhUserOtherfunctionAppService;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 其他功能 Created by dyb on 2019/12/24.
 */
@Controller
@RequestMapping("/api/YhUserOtherfunction")
@Api(value = "YhUserOtherfunction控制器", description = "YhUserOtherfunction管理")
public class YhUserOtherfunctionAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserOtherfunctionAppController.class);

    @Autowired
    YhUserOtherfunctionAppService yhUserOtherfunctionService;
    @Autowired
    YhHeThresholdService yhHeThresholdService;


    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode,Integer timeType,String time) throws java.text.ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> timeList = new HashSet<>();
        if(0==timeType) {
            String[] split = time.split("~");
            List<YhUserOtherfunctionApp> list = yhUserOtherfunctionService.findOtherAllByUserCode(usercode,timeType,split[0],split[1]);

            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserOtherfunctionApp yhUserOtherfunctionApp = list.get(i);
                    String entryTime = yhUserOtherfunctionApp.getInserttime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    String urineMicroprotein = yhUserOtherfunctionApp.getUrineMicroprotein();
                    String glycosylatedHemoglobin = yhUserOtherfunctionApp.getGlycosylatedHemoglobin();
                    String hcy = yhUserOtherfunctionApp.getHcy();
                    if (StringUtil.isNotEmpty(urineMicroprotein) && StringUtil.isNotEmpty(glycosylatedHemoglobin) && StringUtil.isNotEmpty(hcy)) {
                        Double aDouble = Double.valueOf(urineMicroprotein);
                        Double aDouble1 = Double.valueOf(glycosylatedHemoglobin);
                        Double aDouble2 = Double.valueOf(hcy);
                        if (aDouble >= 0.49 && aDouble <= 2.05 && aDouble1 >= 4 && aDouble1 <= 6 && aDouble2 >= 5 && aDouble2 <= 15) {
                            k++;
                        }
                    }

                }
                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));
            }
        }else{
            List<YhUserOtherfunctionApp> list = yhUserOtherfunctionService.findOtherAllByUserCode(usercode,timeType,time,null);

            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserOtherfunctionApp yhUserOtherfunctionApp = list.get(i);
                    String entryTime = yhUserOtherfunctionApp.getInserttime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    String urineMicroprotein = yhUserOtherfunctionApp.getUrineMicroprotein();
                    String glycosylatedHemoglobin = yhUserOtherfunctionApp.getGlycosylatedHemoglobin();
                    String hcy = yhUserOtherfunctionApp.getHcy();
                    if (StringUtil.isNotEmpty(urineMicroprotein) && StringUtil.isNotEmpty(glycosylatedHemoglobin) && StringUtil.isNotEmpty(hcy)) {
                        Double aDouble = Double.valueOf(urineMicroprotein);
                        Double aDouble1 = Double.valueOf(glycosylatedHemoglobin);
                        Double aDouble2 = Double.valueOf(hcy);
                        if (aDouble >= 0.49 && aDouble <= 2.05 && aDouble1 >= 4 && aDouble1 <= 6 && aDouble2 >= 5 && aDouble2 <= 15) {
                            k++;
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
        if (0 == timetype) {
            String[] split = time.split("~");
            int zhengchang = 0;
            int yichang = 0;
            List<YhUserOtherfunctionApp> list = yhUserOtherfunctionService.findOherInfoByTimeType(usercode, timetype, split[0], split[1]);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserOtherfunctionApp yhUserOtherfunctionApp = list.get(i);
                    String urineMicroprotein = yhUserOtherfunctionApp.getUrineMicroprotein();
                    String glycosylatedHemoglobin = yhUserOtherfunctionApp.getGlycosylatedHemoglobin();
                    String hcy = yhUserOtherfunctionApp.getHcy();
                    if (StringUtil.isNotEmpty(urineMicroprotein) && StringUtil.isNotEmpty(glycosylatedHemoglobin) && StringUtil.isNotEmpty(hcy)) {
                        Double aDouble = Double.valueOf(urineMicroprotein);
                        Double aDouble1 = Double.valueOf(glycosylatedHemoglobin);
                        Double aDouble2 = Double.valueOf(hcy);

                        if (aDouble >= 0.49 && aDouble <= 2.05 && aDouble1 >= 4 && aDouble1 <= 6 && aDouble2 >= 5 && aDouble2 <= 15) {
                            zhengchang++;
                        } else {
                            yichang++;
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
        }else if(1 == timetype){
            int zhengchang = 0;
            int yichang = 0;
            List<YhUserOtherfunctionApp> list = yhUserOtherfunctionService.findOherInfoByTimeType(usercode, timetype,time, null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserOtherfunctionApp yhUserOtherfunctionApp = list.get(i);
                    String urineMicroprotein = yhUserOtherfunctionApp.getUrineMicroprotein();
                    String glycosylatedHemoglobin = yhUserOtherfunctionApp.getGlycosylatedHemoglobin();
                    String hcy = yhUserOtherfunctionApp.getHcy();
                    if (StringUtil.isNotEmpty(urineMicroprotein) && StringUtil.isNotEmpty(glycosylatedHemoglobin) && StringUtil.isNotEmpty(hcy)) {
                        Double aDouble = Double.valueOf(urineMicroprotein);
                        Double aDouble1 = Double.valueOf(glycosylatedHemoglobin);
                        Double aDouble2 = Double.valueOf(hcy);

                        if (aDouble >= 0.49 && aDouble <= 2.05 && aDouble1 >= 4 && aDouble1 <= 6 && aDouble2 >= 5 && aDouble2 <= 15) {
                            zhengchang++;
                        } else {
                            yichang++;
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
        }else if(2 == timetype){
            int zhengchang = 0;
            int yichang = 0;
            List<YhUserOtherfunctionApp> list = yhUserOtherfunctionService.findOherInfoByTimeType(usercode, timetype,time, null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserOtherfunctionApp yhUserOtherfunctionApp = list.get(i);
                    String urineMicroprotein = yhUserOtherfunctionApp.getUrineMicroprotein();
                    String glycosylatedHemoglobin = yhUserOtherfunctionApp.getGlycosylatedHemoglobin();
                    String hcy = yhUserOtherfunctionApp.getHcy();
                    if (StringUtil.isNotEmpty(urineMicroprotein) && StringUtil.isNotEmpty(glycosylatedHemoglobin) && StringUtil.isNotEmpty(hcy)) {
                        Double aDouble = Double.valueOf(urineMicroprotein);
                        Double aDouble1 = Double.valueOf(glycosylatedHemoglobin);
                        Double aDouble2 = Double.valueOf(hcy);

                        if (aDouble >= 0.49 && aDouble <= 2.05 && aDouble1 >= 4 && aDouble1 <= 6 && aDouble2 >= 5 && aDouble2 <= 15) {
                            zhengchang++;
                        } else {
                            yichang++;
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
        return  Result.error();
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

        List<YhUserOtherfunctionApp> selectByPrimaryKeyFindEntryTime = yhUserOtherfunctionService
                .selectByPrimaryKeyFindEntryTime(usercode);

        return Result.ok(selectByPrimaryKeyFindEntryTime);
    }

    /**
     * 查询已经录入的日期集合selectByPrimaryKeyFindEntryTime
     */

    @RequestMapping(value = "/findOtherfunctionInfoNew")
    @ResponseBody
    public Result findOtherfunctionInfoNew(String eid) {
        if (null == eid) {
            Result.error();
        }

        YhUserOtherfunctionApp selectByPrimaryKeyNewest8 = yhUserOtherfunctionService.selectByPrimaryKeyNewest(eid);// 其他功能

        return Result.ok(selectByPrimaryKeyNewest8);
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

        List<YhUserOtherfunctionApp> list = yhUserOtherfunctionService
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
            if(null!=list){
                for (int i = 0; i < list.size(); i++) {
                    YhUserOtherfunctionApp yhUserOtherfunctionApp = list.get(i);

                    yhUserOtherfunctionApp.setTiwen("正常");
                    yhUserOtherfunctionApp.setOpenNo(false);
                    if (i == 0) {
                        yhUserOtherfunctionApp.setOpenNo(true);
                    }

                    String urineMicroprotein = yhUserOtherfunctionApp.getUrineMicroprotein();
                    String glycosylatedHemoglobin = yhUserOtherfunctionApp.getGlycosylatedHemoglobin();
                    String hcy = yhUserOtherfunctionApp.getHcy();
                    if (StringUtil.isNotEmpty(urineMicroprotein) && StringUtil.isNotEmpty(glycosylatedHemoglobin) && StringUtil.isNotEmpty(hcy)) {
                        if (Double.valueOf(urineMicroprotein) < 0.49 || Double.valueOf(urineMicroprotein) > 2.05) {
                            yhUserOtherfunctionApp.setTiwen("异常");
                        }

                        if (Double.valueOf(glycosylatedHemoglobin) < 4 || Double.valueOf(glycosylatedHemoglobin) > 6) {
                            yhUserOtherfunctionApp.setTiwen("异常");
                        }
                        if (Double.valueOf(hcy) < 5 || Double.valueOf(hcy) > 15) {
                            yhUserOtherfunctionApp.setTiwen("异常");
                        }
                    }else{
                        yhUserOtherfunctionApp.setTiwen("异常");
                    }


                    if ("异常".equals(yhUserOtherfunctionApp.getTiwen())) {
                        yhUserOtherfunctionApp.setJiedu("尿微量蛋白异常表示泌尿系统可能存在感染。\n" +
                                "糖化血红蛋白异常表示三到六个月血糖控制情况不佳。\n" +
                                "同型半胱氨酸异常表示患心脑血管疾病增加。");
                    } else {
                        yhUserOtherfunctionApp.setJiedu("您的检测正常，请继续保持健康的生活习惯。");
                    }

                }
            }

        return Result.ok(list);
    }

    /**
     * 修改其他指标数据
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/modifyBmiInfo")
    @ResponseBody
    public Result modifyBmiInfo(YhUserOtherfunctionApp record) {
        yhUserOtherfunctionService.updateByPrimaryKeySelective(record);
        return Result.ok();
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

        List<YhUserOtherfunctionApp> selectByPrimaryKeyByUserIdLimit7 = yhUserOtherfunctionService
                .selectByPrimaryKeyBYeid(eid);

        if (null != selectByPrimaryKeyByUserIdLimit7) {
            return Result.ok(selectByPrimaryKeyByUserIdLimit7);
        }

        return Result.error();
    }

    /**
     * 根据主键查询其他指标信息
     */
    @RequestMapping(value = "/findInfoByKey")
    @ResponseBody
    public Result findInfoByKey(Integer id) {
        if (null == id) {
            return Result.error();
        }
        return Result.ok(yhUserOtherfunctionService.selectByPrimaryKey(id));
    }

    /**
     * 新增记录
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserOtherfunctionApp record, HttpServletRequest request) {
        System.out.println(record);
        // 修改
        if (null != record.getOtherid() && 0 != record.getOtherid()) {
            record.setInserttime(null);
            int updateByExampleSelective = yhUserOtherfunctionService.updateByPrimaryKeySelective(record);
            try {
                yhHeThresholdService.selectBythresType("urinary_microprotein", record.getUserId(), String.valueOf(record.getUrineMicroprotein()), "尿微量蛋白",request);
                yhHeThresholdService.selectBythresType("Hba1c_and_protein", record.getUserId(), String.valueOf(record.getGlycosylatedHemoglobin()), "糖化血红蛋白",request);
                yhHeThresholdService.selectBythresType("homocysteine", record.getUserId(), String.valueOf(record.getHcy()), "同型半胱氨酸",request);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Result.ok(updateByExampleSelective);
        } else {
            record.setIsDel(2);
            record.setInserttime(record.getInserttime() + NowString());
            int insertSelective = yhUserOtherfunctionService.insertSelective(record);
            try {
                yhHeThresholdService.selectBythresType("urinary_microprotein", record.getUserId(), String.valueOf(record.getUrineMicroprotein()), "尿微量蛋白",request);
                yhHeThresholdService.selectBythresType("Hba1c_and_protein", record.getUserId(), String.valueOf(record.getGlycosylatedHemoglobin()), "糖化血红蛋白",request);
                yhHeThresholdService.selectBythresType("homocysteine", record.getUserId(), String.valueOf(record.getHcy()), "同型半胱氨酸",request);

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
    public Result modifyInfo(YhUserOtherfunctionApp record) {
        System.out.println(record);
        record.setIsDel(2);

        record.setInserttime(NowString());

        int insertSelective = yhUserOtherfunctionService.updateByPrimaryKeySelective(record);
        if (insertSelective > 0) {
            return Result.ok();
        }

        return Result.error();
    }

}