package com.yihe.applet.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.yihe.applet.api.domain.YhUserBloodsugerApp;
import com.yihe.applet.api.domain.YhUserTemperatureApp;
import com.yihe.system.service.YhHeThresholdService;
import com.yihe.system.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserBloodfatApp;
import com.yihe.applet.api.service.YhUserBloodfatAppService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;

/**
 * 血脂四项 Created by dyb on 2019/12/23.
 */
@Controller
@RequestMapping("/api/YhUserBloodfat")
@Api(value = "YhUserBloodfat控制器", description = "YhUserBloodfat管理")
public class YhUserBloodfatAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserBloodfatAppController.class);

    @Autowired
    YhUserBloodfatAppService yhUserBloodfatService;
    @Autowired
    YhHeThresholdService yhHeThresholdService;


    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode,Integer timeType,String time) throws java.text.ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> timeList = new HashSet<>();
        if(0==timeType) {
            String[] split = time.split("~");
            List<YhUserBloodfatApp> list = yhUserBloodfatService.findBloodfatAllByUserCode(usercode,timeType,split[0],split[1]);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserBloodfatApp yhUserBloodfatApp = list.get(i);
                    String entryTime = yhUserBloodfatApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    Float cholesterin = yhUserBloodfatApp.getCholesterin();
                    Float tring = yhUserBloodfatApp.getTring();
                    Float hdl = yhUserBloodfatApp.getHdl();
                    Float ldl = yhUserBloodfatApp.getLdl();
                    if (cholesterin >= 2.8 && cholesterin < 5.18 && tring > 0.56 && tring < 1.70 && hdl >= 1.04 && ldl > 0 && ldl < 3.37) {
                        k++;
                    }
                }
                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));
            }
        }else{
            List<YhUserBloodfatApp> list = yhUserBloodfatService.findBloodfatAllByUserCode(usercode,timeType,time,null);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserBloodfatApp yhUserBloodfatApp = list.get(i);
                    String entryTime = yhUserBloodfatApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    Float cholesterin = yhUserBloodfatApp.getCholesterin();
                    Float tring = yhUserBloodfatApp.getTring();
                    Float hdl = yhUserBloodfatApp.getHdl();
                    Float ldl = yhUserBloodfatApp.getLdl();
                    if (cholesterin >= 2.8 && cholesterin < 5.18 && tring > 0.56 && tring < 1.70 && hdl >= 1.04 && ldl > 0 && ldl < 3.37) {
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
     * 根据主键查询血脂四项信息
     */
    @RequestMapping(value = "/findInfoByKey")
    @ResponseBody
    public Result findInfoByKey(Integer id) {
        if (null == id) {
            return Result.error();
        }
        return Result.ok(yhUserBloodfatService.selectByPrimaryKey(id));
    }

    /**
     * 查询最新血脂四项
     */
    @RequestMapping(value = "/findBloodfatInfoNew")
    @ResponseBody
    public Result findBloodfatInfoNew(String eid) {
        if (null == eid) {
            return Result.error();
        }
        YhUserBloodfatApp selectByPrimaryKeyNewest5 = yhUserBloodfatService.selectByPrimaryKeyNewest(eid);// 血脂
        return Result.ok(selectByPrimaryKeyNewest5);
    }

    /**
     * 修改血脂四项数据
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/modifyBmiInfo")
    @ResponseBody
    public Result modifyBmiInfo(YhUserBloodfatApp record) {
        yhUserBloodfatService.updateByPrimaryKeySelective(record);
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

        List<YhUserBloodfatApp> selectByPrimaryKeyByUserIdLimit7 = yhUserBloodfatService.selectByPrimaryKeyBYeid(eid);

        if (null != selectByPrimaryKeyByUserIdLimit7) {
            return Result.ok(selectByPrimaryKeyByUserIdLimit7);
        }

        return Result.error();
    }

    @RequestMapping(value = "/pieChartAssignment")
    @ResponseBody
    public Result pieChartAssignment(String usercode, Integer timetype, String time, Integer page) {
        if (StringUtil.isEmpty(usercode)) {
            return Result.error();
        }
        if (0 == timetype) {
            String[] split = time.split("~");
            int yichang = 0;
            int zhengchang = 0;
            List<YhUserBloodfatApp> list = yhUserBloodfatService.findBingTuInfo(usercode, timetype, split[0], split[1], page);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserBloodfatApp yhUserBloodfatApp = list.get(i);
                    Float cholesterin = yhUserBloodfatApp.getCholesterin();
                    Float tring = yhUserBloodfatApp.getTring();
                    Float hdl = yhUserBloodfatApp.getHdl();
                    Float ldl = yhUserBloodfatApp.getLdl();
                    if (cholesterin < 5.18 && tring < 1.7 && hdl >= 1.04 && ldl < 3.37) {
                        zhengchang++;
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
            int yichang = 0;
            int zhengchang = 0;
            List<YhUserBloodfatApp> list = yhUserBloodfatService.findBingTuInfo(usercode, timetype, time, null, page);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserBloodfatApp yhUserBloodfatApp = list.get(i);
                    Float cholesterin = yhUserBloodfatApp.getCholesterin();
                    Float tring = yhUserBloodfatApp.getTring();
                    Float hdl = yhUserBloodfatApp.getHdl();
                    Float ldl = yhUserBloodfatApp.getLdl();
                    if (cholesterin < 5.18 && tring < 1.7 && hdl >= 1.04 && ldl < 3.37) {
                        zhengchang++;
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
            int yichang = 0;
            int zhengchang = 0;
            List<YhUserBloodfatApp> list = yhUserBloodfatService.findBingTuInfo(usercode, timetype, time, null, page);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserBloodfatApp yhUserBloodfatApp = list.get(i);
                    Float cholesterin = yhUserBloodfatApp.getCholesterin();
                    Float tring = yhUserBloodfatApp.getTring();
                    Float hdl = yhUserBloodfatApp.getHdl();
                    Float ldl = yhUserBloodfatApp.getLdl();
                    if (cholesterin < 5.18 && tring < 1.7 && hdl >= 1.04 && ldl < 3.37) {
                        zhengchang++;
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
     * 根据eid查询全部历史信息
     *
     * @param eid
     * @return
     */
    @RequestMapping(value = "/findInfo")
    @ResponseBody
    public Result findInfo(String usercode, @RequestParam(required = false, defaultValue = "0") Integer timeType, String time, Integer page) throws ParseException {
        int pageSize = 7;
        Map<String, Object> map = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        List<String> dataListCho = new ArrayList<>();
        List<String> dataListTring = new ArrayList<>();
        List<String> dataListHdl = new ArrayList<>();
        List<String> dataListLdl = new ArrayList<>();
        if (2 == timeType) {
            List<YhUserBloodfatApp> list = yhUserBloodfatService.findTemperatureByYear(time, usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserBloodfatApp yhUserBloodfatApp : list) {
                    String entryTime = yhUserBloodfatApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataListCho.add(String.valueOf(yhUserBloodfatApp.getCholesterin()));
                    dataListTring.add(String.valueOf(yhUserBloodfatApp.getTring()));
                    dataListHdl.add(String.valueOf(yhUserBloodfatApp.getHdl()));
                    dataListLdl.add(String.valueOf(yhUserBloodfatApp.getLdl()));

                }
            }
            map.put("timeList", timeList);
            map.put("dataListCho", dataListCho);
            map.put("dataListTring", dataListTring);
            map.put("dataListHdl", dataListHdl);
            map.put("dataListLdl", dataListLdl);
            return Result.ok(map);
        } else if (1 == timeType) {
            List<YhUserBloodfatApp> list = yhUserBloodfatService.findTemperatureByMonth(time, usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserBloodfatApp yhUserBloodfatApp : list) {
                    String entryTime = yhUserBloodfatApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataListCho.add(String.valueOf(yhUserBloodfatApp.getCholesterin()));
                    dataListTring.add(String.valueOf(yhUserBloodfatApp.getTring()));
                    dataListHdl.add(String.valueOf(yhUserBloodfatApp.getHdl()));
                    dataListLdl.add(String.valueOf(yhUserBloodfatApp.getLdl()));

                }
            }
            map.put("timeList", timeList);
            map.put("dataListCho", dataListCho);
            map.put("dataListTring", dataListTring);
            map.put("dataListHdl", dataListHdl);
            map.put("dataListLdl", dataListLdl);
            return Result.ok(map);
        } else if (0 == timeType) {
            String[] split = time.split("~");
            List<YhUserBloodfatApp> list = yhUserBloodfatService.findTemperatureByWeek(split[0], split[1], usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserBloodfatApp yhUserBloodfatApp : list) {
                    String entryTime = yhUserBloodfatApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataListCho.add(String.valueOf(yhUserBloodfatApp.getCholesterin()));
                    dataListTring.add(String.valueOf(yhUserBloodfatApp.getTring()));
                    dataListHdl.add(String.valueOf(yhUserBloodfatApp.getHdl()));
                    dataListLdl.add(String.valueOf(yhUserBloodfatApp.getLdl()));

                }
            }
            map.put("timeList", timeList);
            map.put("dataListCho", dataListCho);
            map.put("dataListTring", dataListTring);
            map.put("dataListHdl", dataListHdl);
            map.put("dataListLdl", dataListLdl);
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

    /**
     * 新增记录
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserBloodfatApp record, HttpServletRequest request) {
        System.out.println(record);
        if (null != record.getuBloodfatId() && 0 != record.getuBloodfatId()) {
            record.setEntryTime(null);
            int updateByExampleSelective = yhUserBloodfatService.updateByPrimaryKeySelective(record);
            try {
                yhHeThresholdService.selectBythresType("tc", String.valueOf(record.geteUserId()), String.valueOf(record.getCholesterin()), "总胆固醇",request);
                yhHeThresholdService.selectBythresType("trig", String.valueOf(record.geteUserId()), String.valueOf(record.getTring()), "甘油三脂",request);
                yhHeThresholdService.selectBythresType("ldl-c", String.valueOf(record.geteUserId()), String.valueOf(record.getLdl()), "低密度蛋白",request);
                yhHeThresholdService.selectBythresType("hdl-c", String.valueOf(record.geteUserId()), String.valueOf(record.getHdl()), "高密度蛋白",request);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Result.ok(updateByExampleSelective);
        } else {
            record.setIsDel(2);

            record.setEntryTime(record.getEntryTime() + NowString());

            int insertSelective = yhUserBloodfatService.insertSelective(record);
            try {
                yhHeThresholdService.selectBythresType("tc", String.valueOf(record.geteUserId()), String.valueOf(record.getCholesterin()), "总胆固醇",request);
                yhHeThresholdService.selectBythresType("trig", String.valueOf(record.geteUserId()), String.valueOf(record.getTring()), "甘油三脂",request);
                yhHeThresholdService.selectBythresType("ldl-c", String.valueOf(record.geteUserId()), String.valueOf(record.getLdl()), "低密度蛋白",request);
                yhHeThresholdService.selectBythresType("hdl-c", String.valueOf(record.geteUserId()), String.valueOf(record.getHdl()), "高密度蛋白",request);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (insertSelective > 0) {
                return Result.ok();
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
    public Result modifyInfo(YhUserBloodfatApp record) {
        System.out.println(record);
        record.setIsDel(2);
        record.setDataSource("常规测量");
        record.setEntryTime(record.getEntryTime() + NowString());

        int insertSelective = yhUserBloodfatService.updateByPrimaryKeySelective(record);
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

        List<YhUserBloodfatApp> selectByPrimaryKeyFindEntryTime = yhUserBloodfatService
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

        List<YhUserBloodfatApp> selectByPrimaryKeyFindEntryTimeAnduserid = yhUserBloodfatService
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);

        return Result.ok(selectByPrimaryKeyFindEntryTimeAnduserid);
    }
}