package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserHeartRateApp;
import com.yihe.applet.api.service.YhUserHeartRateAppService;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * YhUserHeartRatecontroller Created by dyb on 2020/3/6.
 */
@Controller
@RequestMapping("/api/YhUserHeartRate")
@Api(value = "YhUserHeartRate控制器", description = "YhUserHeartRate管理")
public class YhUserHeartRateAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserHeartRateAppController.class);

    @Autowired
    YhUserHeartRateAppService yhUserHeartRateService;
    @Autowired
    YhHeThresholdService yhHeThresholdService;

    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode,Integer timeType,String time) throws java.text.ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> timeList = new HashSet<>();
        if(0==timeType) {
            String[] split = time.split("~");
            List<YhUserHeartRateApp> list = yhUserHeartRateService.findHeartRateAllByUserCode(usercode,timeType,split[0],split[1]);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserHeartRateApp yhUserHeartRateApp = list.get(i);
                    String entryTime = yhUserHeartRateApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);

                    Integer heartRate = yhUserHeartRateApp.getHeartRate();
                    if (heartRate >= 60 && heartRate <= 100) {
                        k++;
                    }
                }
                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));
            }
        }else{
            List<YhUserHeartRateApp> list = yhUserHeartRateService.findHeartRateAllByUserCode(usercode,timeType,time,null);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserHeartRateApp yhUserHeartRateApp = list.get(i);
                    String entryTime = yhUserHeartRateApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);

                    Integer heartRate = yhUserHeartRateApp.getHeartRate();
                    if (heartRate >= 60 && heartRate <= 100) {
                        k++;
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

    /**
     * 体温饼图赋值
     */
    @RequestMapping(value = "/pieChartAssignment")
    @ResponseBody
    public Result pieChartAssignment(String usercode, Integer timetype, String time, Integer page) {
        if (StringUtil.isEmpty(usercode)) {
            return Result.error("参数异常");
        }
        if (0 == timetype) {
            String[] split = time.split("~");
            int findHeartRateGuoSu = yhUserHeartRateService.findHeartRateGuoSu(usercode, timetype, split[0], split[1], page);
            int findHeartRateGuoHuan = yhUserHeartRateService.findHeartRateGuoHuan(usercode, timetype, split[0], split[1], page);
            int findHeartRateZhengChang = yhUserHeartRateService.findHeartRateZhengChang(usercode, timetype, split[0], split[1], page);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("zhengchang", findHeartRateZhengChang);
            map.put("guohuan", findHeartRateGuoHuan);
            map.put("guosu", findHeartRateGuoSu);
            return Result.ok(map);
        } else if (1 == timetype) {
            int findHeartRateGuoSu = yhUserHeartRateService.findHeartRateGuoSu(usercode, timetype, time, null, page);
            int findHeartRateGuoHuan = yhUserHeartRateService.findHeartRateGuoHuan(usercode, timetype, time, null, page);
            int findHeartRateZhengChang = yhUserHeartRateService.findHeartRateZhengChang(usercode, timetype, time, null, page);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("zhengchang", findHeartRateZhengChang);
            map.put("guohuan", findHeartRateGuoHuan);
            map.put("guosu", findHeartRateGuoSu);
            return Result.ok(map);
        } else if (2 == timetype) {
            int findHeartRateGuoSu = yhUserHeartRateService.findHeartRateGuoSu(usercode, timetype, time, null, page);
            int findHeartRateGuoHuan = yhUserHeartRateService.findHeartRateGuoHuan(usercode, timetype, time, null, page);
            int findHeartRateZhengChang = yhUserHeartRateService.findHeartRateZhengChang(usercode, timetype, time, null, page);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("zhengchang", findHeartRateZhengChang);
            map.put("guohuan", findHeartRateGuoHuan);
            map.put("guosu", findHeartRateGuoSu);
            return Result.ok(map);
        }
        return Result.error();
    }

    /**
     * 时间段分布
     */
    @RequestMapping(value = "/timePeriodDistribution")
    @ResponseBody
    public Result timePeriodDistribution(String usercode, Integer timeType, String time, Integer page) {
        if (StringUtil.isEmpty(usercode)) {
            return Result.error();
        }
        if (0 == timeType) {
            String[] split = time.split("~");
            return Result.ok(yhUserHeartRateService.findHeartRateTimeFenBuWeek(split[0], split[1], usercode, page));
        } else if (1 == timeType) {
            return Result.ok(yhUserHeartRateService.findHeartRateTimeFenBuMonth(time, usercode, page));
        } else {
            return Result.ok(yhUserHeartRateService.findHeartRateTimeFenBuYear(time, usercode, page));
        }

    }

    /**
     * 根据心率主键查询
     */
    @RequestMapping(value = "/findInfoByKey")
    @ResponseBody
    public Result findInfoByKey(Integer id) {
        if (null == id) {
            Result.error();
        }
        return Result.ok(yhUserHeartRateService.selectByPrimaryKey(id));
    }

    /**
     * 根据心率主键查询
     */
    @RequestMapping(value = "/findHeartRateInfoNew")
    @ResponseBody
    public Result findHeartRateInfoNew(String eid) {
        if (null == eid) {
            Result.error();
        }
        YhUserHeartRateApp selectByPrimaryKeyOrderTime2 = yhUserHeartRateService.selectByPrimaryKeyOrderTime(eid);// 心率
        return Result.ok(selectByPrimaryKeyOrderTime2);
    }

    /**
     * 查询
     *
     * @param eid
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/findInfoAll")
    @ResponseBody
    public Result findInfo(String usercode, @RequestParam(required = false, defaultValue = "0") Integer timeType, String time, Integer page) throws ParseException {
        int pageSize = 7;
        Map<String, Object> map = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();
        if (2 == timeType) {
            List<YhUserHeartRateApp> list = yhUserHeartRateService.findHeartRateByYear(time, usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserHeartRateApp yhUserHeartRate : list) {
                    String entryTime = yhUserHeartRate.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserHeartRate.getHeartRate()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataList", dataList);
            return Result.ok(map);
        } else if (1 == timeType) {
            List<YhUserHeartRateApp> list = yhUserHeartRateService.findHeartRateByMonth(time, usercode
                    , (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserHeartRateApp yhUserHeartRate : list) {
                    String entryTime = yhUserHeartRate.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserHeartRate.getHeartRate()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataList", dataList);
            return Result.ok(map);
        } else if (0 == timeType) {
            String[] split = time.split("~");
            List<YhUserHeartRateApp> list = yhUserHeartRateService.findHeartRateByWeek(split[0], split[1], usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserHeartRateApp yhUserHeartRate : list) {
                    String entryTime = yhUserHeartRate.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserHeartRate.getHeartRate()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataList", dataList);
            return Result.ok(map);
        }
        return Result.error();
    }

    public static String transferFormat2(String inTime) throws ParseException {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");

        Date tempDate = null;
        String outTime = null;
        tempDate = s1.parse(inTime);
        outTime = s1.format(tempDate);
        return outTime;
    }

    public static String transferFormat(String inTime) throws ParseException {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        Date tempDate = null;
        String outTime = null;
        tempDate = s1.parse(inTime);
        outTime = s2.format(s2.parse(s1.format(tempDate)));
        return outTime;
    }

    /**
     * 查询已经存在数据的日期
     */
    @RequestMapping(value = "/findTimeInfo")
    @ResponseBody
    public Result findTimeInfo(String usercode) throws ParseException {
        if (StringUtil.isNotEmpty(usercode)) {
            Result.error();
        }
        List<String> selectByPrimaryKeyFindEntryTime = yhUserHeartRateService.selectByPrimaryKeyFindEntryTime(usercode);
        if (null == selectByPrimaryKeyFindEntryTime) {
            return Result.ok();
        }
        for (int i = 0; i < selectByPrimaryKeyFindEntryTime.size(); i++) {
            String entryTime = selectByPrimaryKeyFindEntryTime.get(i);
            String transferFormat = transferFormat2(entryTime);
            selectByPrimaryKeyFindEntryTime.set(i, transferFormat);

        }
        return Result.ok(selectByPrimaryKeyFindEntryTime);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserHeartRateApp record, HttpServletRequest request) {

        if (null == record) {
            Result.error();
        }
        // 修改
        if (null != record.getRid() && 0 != record.getRid()) {
            record.setEntryTime(null);
            int updateByExampleSelective = yhUserHeartRateService.updateByPrimaryKeySelective(record);
            try {
                yhHeThresholdService.selectBythresType("pulse", String.valueOf(record.geteUserId()), String.valueOf(record.getHeartRate()), "心率",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Result.ok(updateByExampleSelective);
        } else {
            record.setEntryTime(record.getEntryTime() + NowString());
            int insertSelective = yhUserHeartRateService.insertSelective(record);
            try {
                yhHeThresholdService.selectBythresType("pulse", String.valueOf(record.geteUserId()), String.valueOf(record.getHeartRate()), "心率",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Result.ok(insertSelective);
        }

    }

    /**
     * 修改
     */

    @RequestMapping(value = "/modifyInfo")
    @ResponseBody
    public Result modifyInfo(YhUserHeartRateApp record) {
        if (null == record) {
            Result.error();
        }

        int updateByPrimaryKeySelective = yhUserHeartRateService.updateByPrimaryKeySelective(record);
        return Result.ok(updateByPrimaryKeySelective);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delInfo")
    @ResponseBody
    public Result delInfo(Integer rid) {

        if (null == rid) {
            Result.error();
        }
        YhUserHeartRateApp record = new YhUserHeartRateApp();
        record.setRid(rid);
        record.setIsDel(1);
        int updateByPrimaryKeySelective = yhUserHeartRateService.updateByPrimaryKeySelective(record);
        return Result.ok(updateByPrimaryKeySelective);
    }

    /**
     * 根据用户和日期查询
     */

    @RequestMapping(value = "/findHeartRateInfoByUserAndTime")
    @ResponseBody
    public Result findHeartRateInfoByUserAndTime(String usercode, String time) {
        if (null == usercode || null == time) {
            Result.error();
        }
        List<YhUserHeartRateApp> list = yhUserHeartRateService
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                YhUserHeartRateApp yhUserHeartRateApp = list.get(i);
                Integer xinlvInfo = yhUserHeartRateApp.getHeartRate();

                yhUserHeartRateApp.setOpenNo(false);
                if (i == 0) {
                    yhUserHeartRateApp.setOpenNo(true);
                }
                if(null==xinlvInfo){
                    yhUserHeartRateApp.setJiedu("数据异常");
                    yhUserHeartRateApp.setTiwen("异常");
                    yhUserHeartRateApp.setProgressvalue(0);
                }else
                if (xinlvInfo < 60) {
                    yhUserHeartRateApp.setJiedu("心动过缓可能会引起有头晕、乏力、倦怠等这些情况的发生。如果轻度心率过缓在50-60次/分钟之间，常见于运动员之间，但如果心率低于50次/分钟，建议及时就医检查，并通过适当的进行一些比较缓和的体育运动，饮食做到营养全面均衡，荤素搭配和少吃多餐，多注意休息等方面来进行调整。");
                    yhUserHeartRateApp.setTiwen("过缓");
                    yhUserHeartRateApp.setProgressvalue(16.5);
                } else if (xinlvInfo >= 60 && xinlvInfo <= 100) {
                    yhUserHeartRateApp.setJiedu("维持好正常的心率要注意适当运动，控制自己的体重，戒烟，拒绝油腻的食物，多吃水果和蔬菜。还要注意保持充足的睡眠。");
                    yhUserHeartRateApp.setTiwen("正常");
                    yhUserHeartRateApp.setProgressvalue(49.5);
                } else {
                    yhUserHeartRateApp.setJiedu("发生心动过速后，立即蹲下，屏气，通常换几次气即有效。当心动过速发作开始后，间隔几秒再施用此法效果更好。虽然该方法对于缓解心动过速效果很好，但对心动过速只能起到中止发作的作用，而对原发性疾病仍要积极对症治疗，才可完全控制其发作。如果心率超过160次/分钟，大多见于心脏病患者。");
                    yhUserHeartRateApp.setTiwen("过速");
                    yhUserHeartRateApp.setProgressvalue(82.5);
                }
            }
        }
        return Result.ok(list);
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

}