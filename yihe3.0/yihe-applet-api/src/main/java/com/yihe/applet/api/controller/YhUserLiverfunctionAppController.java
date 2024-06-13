package com.yihe.applet.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.yihe.applet.api.domain.YhUserHeartRateApp;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserLiverfunctionApp;
import com.yihe.applet.api.service.YhUserLiverfunctionAppService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;

/**
 * 肝功能 Created by dyb on 2019/12/24.
 */
@Controller
@RequestMapping("/api/YhUserLiverfunction")
@Api(value = "YhUserLiverfunction控制器", description = "YhUserLiverfunction管理")
public class YhUserLiverfunctionAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserLiverfunctionAppController.class);

    @Autowired
    YhUserLiverfunctionAppService yhUserLiverfunctionService;
    @Autowired
    YhHeThresholdService yhHeThresholdService;
    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode,Integer timeType,String time) throws java.text.ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> timeList = new HashSet<>();
        if(0==timeType) {
            String[] split = time.split("~");

            List<YhUserLiverfunctionApp> list = yhUserLiverfunctionService.findLiverAllByUserCode(usercode,timeType,split[0],split[1]);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserLiverfunctionApp yhUserLiverfunctionApp = list.get(i);
                    String entryTime = yhUserLiverfunctionApp.getInserttime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    //谷丙转氨酶
                    String guBing = yhUserLiverfunctionApp.getGuBing();
                    //谷草转氨酶
                    String milletStraw = yhUserLiverfunctionApp.getMilletStraw();
                    //血清白蛋白
                    String serumAlbumin = yhUserLiverfunctionApp.getSerumAlbumin();
                    //血清球蛋白
                    String serumGlobulin = yhUserLiverfunctionApp.getSerumGlobulin();
                    if (StringUtil.isNotEmpty(guBing) && StringUtil.isNotEmpty(milletStraw) && StringUtil.isNotEmpty(serumAlbumin) && StringUtil.isNotEmpty(serumGlobulin)) {
                        Double aDouble = Double.valueOf(guBing);
                        Double aDouble1 = Double.valueOf(milletStraw);
                        Double aDouble2 = Double.valueOf(serumAlbumin);
                        Double aDouble3 = Double.valueOf(serumGlobulin);
                        if (aDouble > 0 && aDouble <= 40 && aDouble1 >= 0 && aDouble1 <= 40 && aDouble2 >= 35 && aDouble2 <= 51 && aDouble3 >= 20 && aDouble3 <= 30) {
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

            List<YhUserLiverfunctionApp> list = yhUserLiverfunctionService.findLiverAllByUserCode(usercode,timeType,time,null);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserLiverfunctionApp yhUserLiverfunctionApp = list.get(i);
                    String entryTime = yhUserLiverfunctionApp.getInserttime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    //谷丙转氨酶
                    String guBing = yhUserLiverfunctionApp.getGuBing();
                    //谷草转氨酶
                    String milletStraw = yhUserLiverfunctionApp.getMilletStraw();
                    //血清白蛋白
                    String serumAlbumin = yhUserLiverfunctionApp.getSerumAlbumin();
                    //血清球蛋白
                    String serumGlobulin = yhUserLiverfunctionApp.getSerumGlobulin();
                    if (StringUtil.isNotEmpty(guBing) && StringUtil.isNotEmpty(milletStraw) && StringUtil.isNotEmpty(serumAlbumin) && StringUtil.isNotEmpty(serumGlobulin)) {
                        Double aDouble = Double.valueOf(guBing);
                        Double aDouble1 = Double.valueOf(milletStraw);
                        Double aDouble2 = Double.valueOf(serumAlbumin);
                        Double aDouble3 = Double.valueOf(serumGlobulin);
                        if (aDouble > 0 && aDouble <= 40 && aDouble1 >= 0 && aDouble1 <= 40 && aDouble2 >= 35 && aDouble2 <= 51 && aDouble3 >= 20 && aDouble3 <= 30) {
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
            List<YhUserLiverfunctionApp> list = yhUserLiverfunctionService.findLiverByTimeType(usercode, timetype, split[0], split[1]);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserLiverfunctionApp yhUserLiverfunctionApp = list.get(i);
                    //谷丙转氨酶
                    String guBing = yhUserLiverfunctionApp.getGuBing();
                    //谷草转氨酶
                    String milletStraw = yhUserLiverfunctionApp.getMilletStraw();
                    //血清白蛋白
                    String serumAlbumin = yhUserLiverfunctionApp.getSerumAlbumin();
                    //血清球蛋白
                    String serumGlobulin = yhUserLiverfunctionApp.getSerumGlobulin();
                    if (StringUtil.isNotEmpty(guBing) && StringUtil.isNotEmpty(milletStraw) && StringUtil.isNotEmpty(serumAlbumin) && StringUtil.isNotEmpty(serumGlobulin)) {
                        Double aDouble = Double.valueOf(guBing);
                        Double aDouble1 = Double.valueOf(milletStraw);
                        Double aDouble2 = Double.valueOf(serumAlbumin);
                        Double aDouble3 = Double.valueOf(serumGlobulin);
                        if (aDouble > 0 && aDouble <= 40 && aDouble1 >= 0 && aDouble1 <= 40 && aDouble2 >= 35 && aDouble2 <= 51 && aDouble3 >= 20 && aDouble3 <= 30) {
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
        } else if (1 == timetype) {
            int zhengchang = 0;
            int yichang = 0;
            List<YhUserLiverfunctionApp> list = yhUserLiverfunctionService.findLiverByTimeType(usercode, timetype, time, null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserLiverfunctionApp yhUserLiverfunctionApp = list.get(i);
                    //谷丙转氨酶
                    String guBing = yhUserLiverfunctionApp.getGuBing();
                    //谷草转氨酶
                    String milletStraw = yhUserLiverfunctionApp.getMilletStraw();
                    //血清白蛋白
                    String serumAlbumin = yhUserLiverfunctionApp.getSerumAlbumin();
                    //血清球蛋白
                    String serumGlobulin = yhUserLiverfunctionApp.getSerumGlobulin();
                    if (StringUtil.isNotEmpty(guBing) && StringUtil.isNotEmpty(milletStraw) && StringUtil.isNotEmpty(serumAlbumin) && StringUtil.isNotEmpty(serumGlobulin)) {
                        Double aDouble = Double.valueOf(guBing);
                        Double aDouble1 = Double.valueOf(milletStraw);
                        Double aDouble2 = Double.valueOf(serumAlbumin);
                        Double aDouble3 = Double.valueOf(serumGlobulin);
                        if (aDouble > 0 && aDouble <= 40 && aDouble1 >= 0 && aDouble1 <= 40 && aDouble2 >= 35 && aDouble2 <= 51 && aDouble3 >= 20 && aDouble3 <= 30) {
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
        } else if (2 == timetype) {
            int zhengchang = 0;
            int yichang = 0;
            List<YhUserLiverfunctionApp> list = yhUserLiverfunctionService.findLiverByTimeType(usercode, timetype, time, null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserLiverfunctionApp yhUserLiverfunctionApp = list.get(i);
                    //谷丙转氨酶
                    String guBing = yhUserLiverfunctionApp.getGuBing();
                    //谷草转氨酶
                    String milletStraw = yhUserLiverfunctionApp.getMilletStraw();
                    //血清白蛋白
                    String serumAlbumin = yhUserLiverfunctionApp.getSerumAlbumin();
                    //血清球蛋白
                    String serumGlobulin = yhUserLiverfunctionApp.getSerumGlobulin();
                    if (StringUtil.isNotEmpty(guBing) && StringUtil.isNotEmpty(milletStraw) && StringUtil.isNotEmpty(serumAlbumin) && StringUtil.isNotEmpty(serumGlobulin)) {
                        Double aDouble = Double.valueOf(guBing);
                        Double aDouble1 = Double.valueOf(milletStraw);
                        Double aDouble2 = Double.valueOf(serumAlbumin);
                        Double aDouble3 = Double.valueOf(serumGlobulin);
                        if (aDouble > 0 && aDouble <= 40 && aDouble1 >= 0 && aDouble1 <= 40 && aDouble2 >= 35 && aDouble2 <= 51 && aDouble3 >= 20 && aDouble3 <= 30) {
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
        return Result.error();
    }

    /**
     * 修改肝功能数据
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/modifyBmiInfo")
    @ResponseBody
    public Result modifyBmiInfo(YhUserLiverfunctionApp record) {
        yhUserLiverfunctionService.updateByPrimaryKeySelective(record);
        return Result.ok();
    }

    /**
     * 查询最新肝功能
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/findLiverfunctionNew")
    @ResponseBody
    public Result findLiverfunctionNew(String eid) {
        YhUserLiverfunctionApp selectByPrimaryKeyNewest7 = yhUserLiverfunctionService.selectByPrimaryKeyNewest(eid);// 肝功能
        return Result.ok(selectByPrimaryKeyNewest7);
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
     * 查询已经录入的日期集合selectByPrimaryKeyFindEntryTime
     */

    @RequestMapping(value = "/selectByPrimaryKeyFindEntryTime")
    @ResponseBody
    public Result selectByPrimaryKeyFindEntryTime(String usercode) {
        if (null == usercode) {
            Result.error();
        }

        List<YhUserLiverfunctionApp> selectByPrimaryKeyFindEntryTime = yhUserLiverfunctionService
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

        List<YhUserLiverfunctionApp> list = yhUserLiverfunctionService
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
            if(null!=list){
                for (int i = 0; i < list.size(); i++) {
                    YhUserLiverfunctionApp yhUserLiverfunctionApp = list.get(i);
                    //谷丙转氨酶
                    String guBing = yhUserLiverfunctionApp.getGuBing();
                    //谷草转氨酶
                    String milletStraw = yhUserLiverfunctionApp.getMilletStraw();
                    //血清白蛋白
                    String serumAlbumin = yhUserLiverfunctionApp.getSerumAlbumin();
                    //血清球蛋白
                    String serumGlobulin = yhUserLiverfunctionApp.getSerumGlobulin();
                    yhUserLiverfunctionApp.setTiwen("正常");
                    yhUserLiverfunctionApp.setOpenNo(false);
                    if (i == 0) {
                        yhUserLiverfunctionApp.setOpenNo(true);
                    }

                    if (StringUtil.isNotEmpty(guBing) && StringUtil.isNotEmpty(milletStraw) && StringUtil.isNotEmpty(serumAlbumin) && StringUtil.isNotEmpty(serumGlobulin)) {
                        if (Double.valueOf(guBing) < 0 || Double.valueOf(guBing) > 40) {
                            yhUserLiverfunctionApp.setTiwen("异常");
                        }
                        if (Double.valueOf(milletStraw) < 0 || Double.valueOf(milletStraw) > 40) {
                            yhUserLiverfunctionApp.setTiwen("异常");
                        }

                        if (Double.valueOf(serumAlbumin) < 35 || Double.valueOf(serumAlbumin) > 51) {
                            yhUserLiverfunctionApp.setTiwen("异常");
                        }
                        if (Double.valueOf(serumGlobulin) < 20 || Double.valueOf(serumGlobulin) > 30) {
                            yhUserLiverfunctionApp.setTiwen("异常");
                        }

                    }else{
                        yhUserLiverfunctionApp.setTiwen("异常");
                    }

                    if ("异常".equals(yhUserLiverfunctionApp.getTiwen())) {
                        yhUserLiverfunctionApp.setJiedu("某项或多项指标偏高/偏低，都不能断定是某种疾病所致，无须过于担心和紧张。通常需要做进一步的检查，通过对各项检查结果以及身体症状表现综合分析，才能评估病情，找出肝功能指标偏高的真正原因，对症用药治疗。");
                    } else {
                        yhUserLiverfunctionApp.setJiedu("您的肝功能正常，请继续保持。");
                    }

                }
            }
        return Result.ok(list);
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

        List<YhUserLiverfunctionApp> selectByPrimaryKeyByUserIdLimit7 = yhUserLiverfunctionService
                .selectByPrimaryKeyBYeid(eid);

        if (null != selectByPrimaryKeyByUserIdLimit7) {
            return Result.ok(selectByPrimaryKeyByUserIdLimit7);
        }

        return Result.error();
    }

    /**
     * 根据主键查询肝功能信息
     */
    @RequestMapping(value = "/findInfoByKey")
    @ResponseBody
    public Result findInfoByKey(Integer id) {
        if (null == id) {
            return Result.error();
        }

        return Result.ok(yhUserLiverfunctionService.selectByPrimaryKey(id));
    }

    /**
     * 新增记录
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserLiverfunctionApp record, HttpServletRequest request) {
        System.out.println(record);
        // 修改
        if (null != record.getLiverid() && 0 != record.getLiverid()) {
            record.setInserttime(null);
            int updateByExampleSelective = yhUserLiverfunctionService.updateByPrimaryKeySelective(record);
            try {
                yhHeThresholdService.selectBythresType("transaminase_millet_grass", String.valueOf(record.getUserId()), String.valueOf(record.getMilletStraw()), "谷草转氨酶",request);
                yhHeThresholdService.selectBythresType("alanine_transaminase", String.valueOf(record.getUserId()), String.valueOf(record.getGuBing()), "谷丙转氨酶",request);
                yhHeThresholdService.selectBythresType("serum_globulin", String.valueOf(record.getUserId()), String.valueOf(record.getSerumGlobulin()), "血清球蛋白",request);
                yhHeThresholdService.selectBythresType("serum_serum_protein", String.valueOf(record.getUserId()), String.valueOf(record.getSerumAlbumin()), "血清白蛋白",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return Result.ok(updateByExampleSelective);
        } else {
            record.setIsDel(2);
            record.setInserttime(record.getInserttime() + NowString());

            int insertSelective = yhUserLiverfunctionService.insertSelective(record);
            try {
                yhHeThresholdService.selectBythresType("transaminase_millet_grass", String.valueOf(record.getUserId()), String.valueOf(record.getMilletStraw()), "谷草转氨酶",request);
                yhHeThresholdService.selectBythresType("alanine_transaminase", String.valueOf(record.getUserId()), String.valueOf(record.getGuBing()), "谷丙转氨酶",request);
                yhHeThresholdService.selectBythresType("serum_globulin", String.valueOf(record.getUserId()), String.valueOf(record.getSerumGlobulin()), "血清球蛋白",request);
                yhHeThresholdService.selectBythresType("serum_serum_protein", String.valueOf(record.getUserId()), String.valueOf(record.getSerumAlbumin()), "血清白蛋白",request);
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
    public Result modifyInfo(YhUserLiverfunctionApp record) {
        System.out.println(record);
        record.setIsDel(2);

        record.setInserttime(NowString());

        int insertSelective = yhUserLiverfunctionService.updateByPrimaryKeySelective(record);
        if (insertSelective > 0) {
            return Result.ok();
        }

        return Result.error();
    }

}