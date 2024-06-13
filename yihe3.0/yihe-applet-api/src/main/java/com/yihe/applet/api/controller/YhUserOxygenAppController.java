package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserOxygenApp;
import com.yihe.applet.api.service.YhUserOxygenAppService;
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

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * YhUserOxygencontroller Created by dyb on 2020/3/3.
 */
@Controller
@RequestMapping("/api/YhUserOxygen")
@Api(value = "YhUserOxygen控制器", description = "YhUserOxygen管理")
public class YhUserOxygenAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserOxygenAppController.class);

    @Autowired
    YhUserOxygenAppService yhUserOxygenService;
    @Autowired
    YhHeThresholdService yhHeThresholdService;

    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode,Integer timeType,String time) throws java.text.ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> timeList = new HashSet<>();
            if(0==timeType) {
            String[] split = time.split("~");
            List<YhUserOxygenApp> list = yhUserOxygenService.findOxygenAllByUserCode(usercode,timeType,split[0],split[1]);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserOxygenApp yhUserOxygenApp = list.get(i);
                    String entryTime = yhUserOxygenApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    Float oxygen = yhUserOxygenApp.getOxygen();
                    if (oxygen >= 95) {
                        k++;
                    }
                }
                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));
            }
        }else{

            List<YhUserOxygenApp> list = yhUserOxygenService.findOxygenAllByUserCode(usercode,timeType,time,null);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserOxygenApp yhUserOxygenApp = list.get(i);
                    String entryTime = yhUserOxygenApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    Float oxygen = yhUserOxygenApp.getOxygen();
                    if (oxygen >= 95) {
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
            int findUserOxygenByZhengchang = yhUserOxygenService.findUserOxygenByZhengchang(usercode, timetype, split[0], split[1], page);
            int findUserOxygenByYichang = yhUserOxygenService.findUserOxygenByYichang(usercode, timetype, split[0], split[1], page);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("zhengchang", findUserOxygenByZhengchang);
            map.put("yichang", findUserOxygenByYichang);
            return Result.ok(map);
        } else if (1 == timetype) {
            int findUserOxygenByZhengchang = yhUserOxygenService.findUserOxygenByZhengchang(usercode, timetype, time, null, page);
            int findUserOxygenByYichang = yhUserOxygenService.findUserOxygenByYichang(usercode, timetype, time, null, page);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("zhengchang", findUserOxygenByZhengchang);
            map.put("yichang", findUserOxygenByYichang);
            return Result.ok(map);
        } else if (2 == timetype) {
            int findUserOxygenByZhengchang = yhUserOxygenService.findUserOxygenByZhengchang(usercode, timetype, time, null, page);
            int findUserOxygenByYichang = yhUserOxygenService.findUserOxygenByYichang(usercode, timetype, time, null, page);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("zhengchang", findUserOxygenByZhengchang);
            map.put("yichang", findUserOxygenByYichang);
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
            return Result.ok(yhUserOxygenService.findOxygenTimeFenBuWeek(split[0], split[1], usercode, page));
        } else if (1 == timeType) {
            return Result.ok(yhUserOxygenService.findOxygenTimeFenBuMonth(time, usercode, page));
        } else {
            return Result.ok(yhUserOxygenService.findOxygenTimeFenBuYear(time, usercode, page));
        }

    }

    /**
     * 根据血氧主键查询
     */
    @RequestMapping(value = "/findInfoByKey")
    @ResponseBody
    public Result findInfoByKey(Integer id) {
        if (null == id) {
            return Result.error();
        }
        YhUserOxygenApp selectByPrimaryKey = yhUserOxygenService.selectByPrimaryKey(id);
        return Result.ok(selectByPrimaryKey);
    }

    /**
     * 根据eid查询
     *
     * @param eid
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/findInfo")
    @ResponseBody
    public Result findInfo(String usercode, @RequestParam(required = false, defaultValue = "0") Integer timeType, String time, Integer page) throws ParseException {
        int pageSize = 7;
        Map<String, Object> map = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();
        if (2 == timeType) {
            List<YhUserOxygenApp> list = yhUserOxygenService.findOxygenByYear(time, usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserOxygenApp yhUserOxygen : list) {
                    String entryTime = yhUserOxygen.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserOxygen.getOxygen()));
                }
                map.put("timeList", timeList);
                map.put("dataList", dataList);
                return Result.ok(map);
            }
        } else if (1 == timeType) {
            List<YhUserOxygenApp> list = yhUserOxygenService.findOxygenByMonth(time, usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserOxygenApp yhUserOxygen : list) {
                    String entryTime = yhUserOxygen.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserOxygen.getOxygen()));
                }
                map.put("timeList", timeList);
                map.put("dataList", dataList);
                return Result.ok(map);
            }
        } else if (0 == timeType) {
            String[] split = time.split("~");
            List<YhUserOxygenApp> list = yhUserOxygenService.findOxygenByWeek(split[0], split[1], usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserOxygenApp yhUserOxygen : list) {
                    String entryTime = yhUserOxygen.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserOxygen.getOxygen()));
                }
                map.put("timeList", timeList);
                map.put("dataList", dataList);
                return Result.ok(map);
            }
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
     * 获取当前系统时间String类型返回值
     *
     * @return
     */
    public String NowString() {
        SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    /**
     * 新增
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserOxygenApp record, HttpServletRequest request) {
        // 修改
        if (null != record.getuOxygenId() && 0 != record.getuOxygenId()) {
            record.setEntryTime(null);
            int updateByExampleSelective = yhUserOxygenService.updateByPrimaryKeySelective(record);
            try {
                yhHeThresholdService.selectBythresType("boold_oxygen", String.valueOf(record.geteUserId()), String.valueOf(record.getOxygen()), "血氧",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Result.ok(updateByExampleSelective);
        } else {
            System.out.println(record);
            record.setIsDel(2);

            record.setEntryTime(record.getEntryTime() + NowString());

            int insertSelective = yhUserOxygenService.insertSelective(record);
            try {
                yhHeThresholdService.selectBythresType("boold_oxygen", String.valueOf(record.geteUserId()), String.valueOf(record.getOxygen()), "血氧",request);
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
     * 修改 数据
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/modifyInfo")
    @ResponseBody
    public Result modifyBmiInfo(YhUserOxygenApp record) {
        int updateByExampleSelective = yhUserOxygenService.updateByPrimaryKeySelective(record);

        return Result.ok(updateByExampleSelective);
    }

    /**
     * 删除 数据
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/delInfo")
    @ResponseBody
    public Result delInfo(Integer oid) {
        if (null == oid || 0 == oid) {
            Result.error();
        }
        int deleteByPrimaryKey = yhUserOxygenService.deleteByPrimaryKey(oid);

        return Result.ok(deleteByPrimaryKey);
    }

    /**
     * 查询最新血氧
     *
     * @param oid
     * @return
     */
    @RequestMapping(value = "/findOxygenInfoNew")
    @ResponseBody
    public Result findOxygenInfoNew(String eid) {
        if (null == eid) {
            Result.error();
        }
        YhUserOxygenApp selectByPrimaryKeyBytime = yhUserOxygenService.selectByPrimaryKeyBytime(eid);

        return Result.ok(selectByPrimaryKeyBytime);
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
        List<String> selectByPrimaryKeyFindEntryTime = yhUserOxygenService.selectByPrimaryKeyFindEntryTime(usercode);
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
     * 根据日期查询
     */
    @RequestMapping(value = "/findByTimeInfo")
    @ResponseBody
    public Result findByTimeInfo(String usercode, String time) {
        if (null == usercode || "" == usercode) {
            Result.error();
        }
        List<YhUserOxygenApp> list = yhUserOxygenService
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                YhUserOxygenApp yhUserOxygenApp = list.get(i);
                Float oxygenInfo = yhUserOxygenApp.getOxygen();
                yhUserOxygenApp.setOpenNo(false);
                if (i == 0) {
                    yhUserOxygenApp.setOpenNo(true);
                }
                if (oxygenInfo < 95) {
                    yhUserOxygenApp.setJiedu("血氧的饱和度低于95，表明身体缺氧，血氧越低，缺氧程度越严重。如果长期血氧值偏低，希望引起重视，及时就医检测。如果过低是不正常的，需要特别的注意，及时就医查找病因。");
                    yhUserOxygenApp.setTiwen("不足");
                    yhUserOxygenApp.setProgressvalue(25);
                } else {
                    yhUserOxygenApp.setJiedu("运动前后人体的血氧值数据差距是很大的，所在环境不同同时也对血氧影响很大。血氧的日常调节，可以选择一些有氧运动每日坚持，保持充足的睡眠，合理的饮食，多去环境好的地方，保持良好心态。");
                    yhUserOxygenApp.setTiwen("正常");
                    yhUserOxygenApp.setProgressvalue(75);
                }
            }
        }
        return Result.ok(list);
    }

}