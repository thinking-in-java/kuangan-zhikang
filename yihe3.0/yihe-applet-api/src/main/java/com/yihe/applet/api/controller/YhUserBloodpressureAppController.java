package com.yihe.applet.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import com.yihe.system.service.YhHeThresholdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserBloodpressureApp;
import com.yihe.applet.api.service.YhUserBloodpressureAppService;
import com.yihe.applet.api.util.common.util.DateTest;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.Api;

/**
 * 数据监测血压 Created by dyb on 2019/12/19.
 */
@Controller
@RequestMapping("/api/YhUserBloodpressure")
@Api(value = "YhUserBloodpressure控制器", description = "YhUserBloodpressure管理")
public class YhUserBloodpressureAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserBloodpressureAppController.class);

    @Autowired
    YhUserBloodpressureAppService yhUserBloodpressureService;
    @Autowired
    YhHeThresholdService yhHeThresholdService;

    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode,Integer timeType,String time,String type) throws java.text.ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> timeList = new HashSet<>();

        if(0==timeType) {
            String[] split = time.split("~");
            List<YhUserBloodpressureApp> list = yhUserBloodpressureService.findBloodpressureAllByUserCode(usercode,timeType,split[0],split[1],type);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserBloodpressureApp yhUserBloodpressureApp = list.get(i);
                    String entryTime = yhUserBloodpressureApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    Integer dbp = yhUserBloodpressureApp.getDbp();
                    Integer sbp = yhUserBloodpressureApp.getSbp();
                    if (sbp>=90&&sbp < 120 && dbp>=60&&dbp < 80) {
                        k++;
                    }
                }
                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));
            }
        }else{

            List<YhUserBloodpressureApp> list = yhUserBloodpressureService.findBloodpressureAllByUserCode(usercode,timeType,time,null,type);
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserBloodpressureApp yhUserBloodpressureApp = list.get(i);
                    String entryTime = yhUserBloodpressureApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    Integer dbp = yhUserBloodpressureApp.getDbp();
                    Integer sbp = yhUserBloodpressureApp.getSbp();
                    if (sbp>=90&&sbp < 120 && dbp>=60&&dbp < 80) {
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
     * 饼图
     */
    @RequestMapping(value = "/pieChartAssignment")
    @ResponseBody
    public Result pieChartAssignment(String usercode, Integer timetype, String time, Integer page, String type) {
        if (StringUtil.isEmpty(usercode)) {
            return Result.error();
        }
        if (0 == timetype) {
            String[] split = time.split("~");
            List<YhUserBloodpressureApp> userBloodPressureAllInfoByTime = yhUserBloodpressureService.findUserBloodPressureAllInfoByTime(usercode, timetype, split[0], split[1], page, type);
            //偏低
            int findUserBloodPressurePianDi = 0;
            //正常
            int findUserBloodPressureZC = 0;
            //偏高
            int findUserBloodPressurepiangao = 0;
            //轻度
            int findUserBloodPressureqingdu =0;
            //中度
            int findUserBloodPressurezhongdu = 0;
            //重度
            int findUserBloodPressurezhongdu2 = 0;
            //单纯
            int findUserBloodPressuredanchun = 0;
            if(null==userBloodPressureAllInfoByTime){
                return null;
            }
            for (int i = 0; i < userBloodPressureAllInfoByTime.size(); i++)
            {
                YhUserBloodpressureApp yhUserBloodpressureApp = userBloodPressureAllInfoByTime.get(i);

                Integer dbpinfo = yhUserBloodpressureApp.getDbp();
                Integer sbpinfo = yhUserBloodpressureApp.getSbp();
                int sbpNum = 0;
                int dbpNum = 0;
                if (sbpinfo >= 140 && dbpinfo < 90) {
                    findUserBloodPressuredanchun++;
                } else {
                    if (sbpinfo < 90) {
                        sbpNum = 1;
                    } else if (sbpinfo < 120) {
                        sbpNum = 0;
                    } else if (sbpinfo >= 120 && sbpinfo < 140) {
                        sbpNum = 2;
                    } else if (sbpinfo >= 140 && sbpinfo < 160) {
                        sbpNum = 3;
                    } else if (sbpinfo >= 160 && sbpinfo < 180) {
                        sbpNum = 4;
                    } else if (sbpinfo >= 180) {
                        sbpNum = 5;
                    }

                    if (dbpinfo < 60) {
                        dbpNum = 1;
                    } else if (dbpinfo < 80) {
                        dbpNum = 0;
                    } else if (dbpinfo >= 80 && dbpinfo < 90) {
                        dbpNum = 2;
                    } else if (dbpinfo >= 90 && dbpinfo < 100) {
                        dbpNum = 3;
                    } else if (dbpinfo >= 100 && dbpinfo < 110) {
                        dbpNum = 4;
                    } else if (dbpinfo >= 110) {
                        dbpNum = 5;
                    }

                    if (dbpNum > sbpNum) {
                        if (dbpNum == 1) {
                            findUserBloodPressurePianDi++;
                        } else if (dbpNum == 0) {
                            findUserBloodPressureZC++;
                        } else if (dbpNum == 2) {
                            findUserBloodPressurepiangao++;
                        } else if (dbpNum == 3) {
                            findUserBloodPressureqingdu++;
                        } else if (dbpNum == 4) {
                            findUserBloodPressurezhongdu++;
                        } else if (dbpNum == 5) {
                            findUserBloodPressurezhongdu2++;
                        }
                    }
                    if (dbpNum <= sbpNum) {
                        if (sbpNum == 1) {
                            findUserBloodPressurePianDi++;
                        } else if (sbpNum == 0) {
                            findUserBloodPressureZC++;
                        } else if (sbpNum == 2) {
                            findUserBloodPressurepiangao++;
                        } else if (sbpNum == 3) {
                            findUserBloodPressureqingdu++;
                        } else if (sbpNum == 4) {
                            findUserBloodPressurezhongdu++;
                        } else if (sbpNum == 5) {
                            findUserBloodPressurezhongdu2++;
                        }
                    }
                }

            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("piandi", findUserBloodPressurePianDi);
            map.put("zhengchang", findUserBloodPressureZC);
            map.put("piangao", findUserBloodPressurepiangao);
            map.put("qingdu", findUserBloodPressureqingdu);
            map.put("zhongdu", findUserBloodPressurezhongdu);
            map.put("zhongdu2", findUserBloodPressurezhongdu2);
            map.put("danchun", findUserBloodPressuredanchun);
            return Result.ok(map);
        } else if (1 == timetype) {

            List<YhUserBloodpressureApp> userBloodPressureAllInfoByTime = yhUserBloodpressureService.findUserBloodPressureAllInfoByTime(usercode, timetype, time, null, page, type);
//偏低
            int findUserBloodPressurePianDi = 0;
            //正常
            int findUserBloodPressureZC = 0;
            //偏高
            int findUserBloodPressurepiangao = 0;
            //轻度
            int findUserBloodPressureqingdu =0;
            //中度
            int findUserBloodPressurezhongdu = 0;
            //重度
            int findUserBloodPressurezhongdu2 = 0;
            //单纯
            int findUserBloodPressuredanchun = 0;

            if(null==userBloodPressureAllInfoByTime){
                return null;
            }
            for (int i = 0; i < userBloodPressureAllInfoByTime.size(); i++)
            {
                YhUserBloodpressureApp yhUserBloodpressureApp = userBloodPressureAllInfoByTime.get(i);

                Integer dbpinfo = yhUserBloodpressureApp.getDbp();
                Integer sbpinfo = yhUserBloodpressureApp.getSbp();
                int sbpNum = 0;
                int dbpNum = 0;
                if (sbpinfo >= 140 && dbpinfo < 90) {
                    findUserBloodPressuredanchun++;
                } else {
                    if (sbpinfo < 90) {
                        sbpNum = 1;
                    } else if (sbpinfo < 120) {
                        sbpNum = 0;
                    } else if (sbpinfo >= 120 && sbpinfo < 140) {
                        sbpNum = 2;
                    } else if (sbpinfo >= 140 && sbpinfo < 160) {
                        sbpNum = 3;
                    } else if (sbpinfo >= 160 && sbpinfo < 180) {
                        sbpNum = 4;
                    } else if (sbpinfo >= 180) {
                        sbpNum = 5;
                    }

                    if (dbpinfo < 60) {
                        dbpNum = 1;
                    } else if (dbpinfo < 80) {
                        dbpNum = 0;
                    } else if (dbpinfo >= 80 && dbpinfo < 90) {
                        dbpNum = 2;
                    } else if (dbpinfo >= 90 && dbpinfo < 100) {
                        dbpNum = 3;
                    } else if (dbpinfo >= 100 && dbpinfo < 110) {
                        dbpNum = 4;
                    } else if (dbpinfo >= 110) {
                        dbpNum = 5;
                    }

                    if (dbpNum > sbpNum) {
                        if (dbpNum == 1) {
                            findUserBloodPressurePianDi++;
                        } else if (dbpNum == 0) {
                            findUserBloodPressureZC++;
                        } else if (dbpNum == 2) {
                            findUserBloodPressurepiangao++;
                        } else if (dbpNum == 3) {
                            findUserBloodPressureqingdu++;
                        } else if (dbpNum == 4) {
                            findUserBloodPressurezhongdu++;
                        } else if (dbpNum == 5) {
                            findUserBloodPressurezhongdu2++;
                        }
                    }
                    if (dbpNum <= sbpNum) {
                        if (sbpNum == 1) {
                            findUserBloodPressurePianDi++;
                        } else if (sbpNum == 0) {
                            findUserBloodPressureZC++;
                        } else if (sbpNum == 2) {
                            findUserBloodPressurepiangao++;
                        } else if (sbpNum == 3) {
                            findUserBloodPressureqingdu++;
                        } else if (sbpNum == 4) {
                            findUserBloodPressurezhongdu++;
                        } else if (sbpNum == 5) {
                            findUserBloodPressurezhongdu2++;
                        }
                    }
                }

            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("piandi", findUserBloodPressurePianDi);
            map.put("zhengchang", findUserBloodPressureZC);
            map.put("piangao", findUserBloodPressurepiangao);
            map.put("qingdu", findUserBloodPressureqingdu);
            map.put("zhongdu", findUserBloodPressurezhongdu);
            map.put("zhongdu2", findUserBloodPressurezhongdu2);
            map.put("danchun", findUserBloodPressuredanchun);
            return Result.ok(map);
        } else {
            int findUserBloodPressurePianDi = yhUserBloodpressureService.findUserBloodPressurePianDi(usercode, timetype, time, null, page, type);
            //正常
            int findUserBloodPressureZC = yhUserBloodpressureService.findUserBloodPressureZC(usercode, timetype, time, null, page, type);
            //偏高
            int findUserBloodPressurepiangao = yhUserBloodpressureService.findUserBloodPressurepiangao(usercode, timetype, time, null, page, type);
            //轻度
            int findUserBloodPressureqingdu = yhUserBloodpressureService.findUserBloodPressureqingdu(usercode, timetype, time, null, page, type);
            //中度
            int findUserBloodPressurezhongdu = yhUserBloodpressureService.findUserBloodPressurezhongdu(usercode, timetype, time, null, page, type);
            //重度
            int findUserBloodPressurezhongdu2 = yhUserBloodpressureService.findUserBloodPressurezhongdu2(usercode, timetype, time, null, page, type);
            //单纯
            int findUserBloodPressuredanchun = yhUserBloodpressureService.findUserBloodPressuredanchun(usercode, timetype, time, null, page, type);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("piandi", findUserBloodPressurePianDi);
            map.put("zhengchang", findUserBloodPressureZC);
            map.put("piangao", findUserBloodPressurepiangao);
            map.put("qingdu", findUserBloodPressureqingdu);
            map.put("zhongdu", findUserBloodPressurezhongdu);
            map.put("zhongdu2", findUserBloodPressurezhongdu2);
            map.put("danchun", findUserBloodPressuredanchun);
            return Result.ok(map);
        }


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
     * 获得时间分布
     */
    @RequestMapping(value = "/findTimeFB")
    @ResponseBody
    public Result findTimeFB(String usercode, Integer timeType, String time, Integer page, String type) {
        if (StringUtil.isEmpty(usercode)) {
            return Result.error();
        }
        if (0 == timeType) {
            String[] split = time.split("~");
            return Result.ok(yhUserBloodpressureService.findBloodPressureTimeFenBuWeek(split[0], split[1], usercode, page, type));
        } else if (1 == timeType) {
            return Result.ok(yhUserBloodpressureService.findBloodPressureTimeFenBuMonth(time, usercode, page, type));
        } else {
            return Result.ok(yhUserBloodpressureService.findBloodPressureTimeFenBuYear(time, usercode, page, type));
        }

    }

    /**
     * 获得周时间集合
     */
    @RequestMapping(value = "/findWeekTimeList")
    @ResponseBody
    public Result findWeekTimeList() {
        return Result.ok(DateTest.getTimeList());
    }

    /**
     * 根据时间段查询血压记录
     */
    @RequestMapping(value = "/findBloodPressureByTime")
    @ResponseBody
    public Result findBloodPressureByTime(String usercode, String strTime, String endTime) {
        if (StringUtil.isEmpty(usercode) || StringUtil.isEmpty(strTime) || StringUtil.isEmpty(endTime)) {
            return Result.error("参数错误");
        }
        List<YhUserBloodpressureApp> findBloodPressureByStrTimeAndEndTime = yhUserBloodpressureService.findBloodPressureByStrTimeAndEndTime(usercode, strTime, endTime);
        return Result.ok(findBloodPressureByStrTimeAndEndTime);
    }

    /**
     * 根据主键查询血压信息
     */
    @RequestMapping(value = "/findInfoByKey")
    @ResponseBody
    public Result findInfoByKey(Integer id) {
        if (null == id) {
            return Result.error();
        }
        YhUserBloodpressureApp selectByPrimaryKey = yhUserBloodpressureService.selectByPrimaryKey(id);
        if (null != selectByPrimaryKey) {
            Integer sbp = selectByPrimaryKey.getSbp();
            Integer dbp = selectByPrimaryKey.getDbp();
            selectByPrimaryKey.setDbp(sbp);
            selectByPrimaryKey.setSbp(dbp);
        }
        return Result.ok(selectByPrimaryKey);
    }

    /**
     * 根据用户查询血压已经记录的日期
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/findBloodpressureByUserCode")
    @ResponseBody
    public Result findBloodpressureByUserCode(String usercode) throws ParseException {
        if (null == usercode) {
            Result.error();
        }
        List<String> selectByPrimaryKeyFindEntryTime = yhUserBloodpressureService
                .selectByPrimaryKeyFindEntryTime(usercode);
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
     * 根据时间和用户查询当日的信息
     */

    @RequestMapping(value = "/findBloodpressureByUserCodeAndTime")
    @ResponseBody
    public Result findBloodpressureByUserCodeAndTime(String usercode, String time) {
        if (null == usercode || null == time) {
            Result.error();
        }
        List<YhUserBloodpressureApp> selectByPrimaryKeyFindEntryTimeAnduserid = yhUserBloodpressureService
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
        if (null != selectByPrimaryKeyFindEntryTimeAnduserid) {
            for (int i = 0; i < selectByPrimaryKeyFindEntryTimeAnduserid.size(); i++) {
                YhUserBloodpressureApp yhUserBloodpressureApp = selectByPrimaryKeyFindEntryTimeAnduserid.get(i);
                yhUserBloodpressureApp.setOpenNo(false);
                if (i == 0) {
                    yhUserBloodpressureApp.setOpenNo(true);
                }
                Integer dbpinfo = yhUserBloodpressureApp.getDbp();
                Integer sbpinfo = yhUserBloodpressureApp.getSbp();
                int sbpNum = 0;
                int dbpNum = 0;
                if (sbpinfo >= 140 && dbpinfo < 90) {
                    yhUserBloodpressureApp.setJieduInfo("您的血压为单纯收缩期高血压，建议您调整膳食结构：低脂、低盐饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持良好心态，坚持运动。");
                    yhUserBloodpressureApp.setTiwenInfo("单纯收缩期");
                    yhUserBloodpressureApp.setProgressvalueinfo(65.1);
                } else {
                    if (sbpinfo < 90) {
                        sbpNum = 1;
                    } else if (sbpinfo < 120) {
                        sbpNum = 0;
                    } else if (sbpinfo >= 120 && sbpinfo < 140) {
                        sbpNum = 2;
                    } else if (sbpinfo >= 140 && sbpinfo < 160) {
                        sbpNum = 3;
                    } else if (sbpinfo >= 160 && sbpinfo < 180) {
                        sbpNum = 4;
                    } else if (sbpinfo >= 180) {
                        sbpNum = 5;
                    }

                    if (dbpinfo < 60) {
                        dbpNum = 1;
                    } else if (dbpinfo < 80) {
                        dbpNum = 0;
                    } else if (dbpinfo >= 80 && dbpinfo < 90) {
                        dbpNum = 2;
                    } else if (dbpinfo >= 90 && dbpinfo < 100) {
                        dbpNum = 3;
                    } else if (dbpinfo >= 100 && dbpinfo < 110) {
                        dbpNum = 4;
                    } else if (dbpinfo >= 110) {
                        dbpNum = 5;
                    }

                    if (dbpNum > sbpNum) {
                        if (dbpNum == 1) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压已经偏低，请注意补充营养，加强锻炼，改善神经、血管的调节功能；饮食注意增加血容量，多喝水补充水分、少吃芹菜，苦瓜等降压食品；定期检测血压，关注血压变化。");
                            yhUserBloodpressureApp.setTiwenInfo("偏低");
                            yhUserBloodpressureApp.setProgressvalueinfo(4.1);
                        } else if (dbpNum == 0) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压正常，请继续保持健康的生活习惯。合理饮食，控制好体重，定期测量血压，关注血压变化。");
                            yhUserBloodpressureApp.setTiwenInfo("正常");
                            yhUserBloodpressureApp.setProgressvalueinfo(13.1);
                        } else if (dbpNum == 2) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压不是最理想的哦，请改变不良的生活习惯，否则长期下去很可能导致高血压！");
                            yhUserBloodpressureApp.setTiwenInfo("偏高");
                            yhUserBloodpressureApp.setProgressvalueinfo(23.1);
                        } else if (dbpNum == 3) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压已触及一级高血压，建议您在服药控制血压的同时，调整膳食结构：低脂、低盐饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持良好心态，坚持运动。");
                            yhUserBloodpressureApp.setTiwenInfo("轻度");
                            yhUserBloodpressureApp.setProgressvalueinfo(32.1);
                        } else if (dbpNum == 4) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压已触及二级高血压，建议您听从医生药物控制血压的同时，要关注您的眼、肾等其他相关器官功能，按时体检、关注心脑血管健康，请勿做突然坐起、倒立等快速变换体位的动作，时刻预防心脑血管疾病的发生。调整膳食结构：低脂、低盐饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持良好心态，坚持运动。");
                            yhUserBloodpressureApp.setTiwenInfo("中度");
                            yhUserBloodpressureApp.setProgressvalueinfo(42.1);
                        } else if (dbpNum == 5) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压已经严重偏高，已达到三级高血压，建议您及时就医，在医生的指导下合理降压。");
                            yhUserBloodpressureApp.setTiwenInfo("重度");
                            yhUserBloodpressureApp.setProgressvalueinfo(51.1);
                        }
                    }
                    if (dbpNum <= sbpNum) {
                        if (sbpNum == 1) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压已经偏低，请注意补充营养，加强锻炼，改善神经、血管的调节功能；饮食注意增加血容量，少吃芹菜，苦瓜等降压食品；坚持锻炼，改善体质；定期检测血压，关注血压变化。");
                            yhUserBloodpressureApp.setTiwenInfo("偏低");
                            yhUserBloodpressureApp.setProgressvalueinfo(4.1);
                        } else if (sbpNum == 0) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压正常，请继续保持健康的生活习惯。合理饮食，控制好体重，定期测量血压，关注血压变化。");
                            yhUserBloodpressureApp.setTiwenInfo("正常");
                            yhUserBloodpressureApp.setProgressvalueinfo(13.1);
                        } else if (sbpNum == 2) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压不是最理想的哦，请改变不良的生活习惯，否则长期下去很可能导致高血压！");
                            yhUserBloodpressureApp.setTiwenInfo("偏高");
                            yhUserBloodpressureApp.setProgressvalueinfo(23.1);
                        } else if (sbpNum == 3) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压已触及一级高血压，建议您在服药控制血压的同时，调整膳食结构：低脂、低盐饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持良好心态，坚持运动。");
                            yhUserBloodpressureApp.setTiwenInfo("轻度");
                            yhUserBloodpressureApp.setProgressvalueinfo(32.1);
                        } else if (sbpNum == 4) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压已触及二级高血压，属于高危人群，建议您听从医生药物控制血压的同时，要关注您的靶器官功能，按时体检监测心脑血管，请勿匆忙变换体位，时刻预防心脑血管疾病的发生几率。调整膳食结构：低脂、低盐饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持良好心态，坚持运动。");
                            yhUserBloodpressureApp.setTiwenInfo("中度");
                            yhUserBloodpressureApp.setProgressvalueinfo(42.1);
                        } else if (sbpNum == 5) {
                            yhUserBloodpressureApp.setJieduInfo("您的血压已经严重偏高，已达到三级高血压，建议您及时就医，在医生的指导下合理降压。");
                            yhUserBloodpressureApp.setTiwenInfo("重度");
                            yhUserBloodpressureApp.setProgressvalueinfo(51.1);
                        }
                    }
                }

            }
        }
        return Result.ok(selectByPrimaryKeyFindEntryTimeAnduserid);
    }

    /**
     * 根据时间和用户查询当日的信息心率
     */

    @RequestMapping(value = "/findBloodpressureByUserCodeAndTimeXinlv")
    @ResponseBody
    public Result findBloodpressureByUserCodeAndTimeXinlv(String usercode, String time) {
        if (null == usercode || null == time) {
            Result.error();
        }
        List<YhUserBloodpressureApp> selectByPrimaryKeyFindEntryTimeAnduserid = yhUserBloodpressureService
                .selectByPrimaryKeyFindEntryTimeAnduseridXinlv(time, usercode);

        return Result.ok(selectByPrimaryKeyFindEntryTimeAnduserid);
    }

    /**
     * 修改血压数据
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/modifyBmiInfo")
    @ResponseBody
    public Result modifyBmiInfo(YhUserBloodpressureApp record) {

        yhUserBloodpressureService.updateByPrimaryKeySelective(record);

        return Result.ok();
    }

    /**
     * 新增血压记录
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserBloodpressureApp record, HttpServletRequest request) {

        // 修改
        if (null != record.getuBloodpressureId() && 0 != record.getuBloodpressureId()) {
            record.setEntryTime(null);
            int updateByExampleSelective = yhUserBloodpressureService.updateByPrimaryKeySelective(record);
            try {
                yhHeThresholdService.selectBythresType("blood_pressure_dia", String.valueOf(record.geteUserId()), String.valueOf(record.getDbp().doubleValue()), "舒张压",request);
                yhHeThresholdService.selectBythresType("blood_pressure_sys", String.valueOf(record.geteUserId()), String.valueOf(record.getSbp().doubleValue()), "收缩压",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Result.ok(updateByExampleSelective);
        } else {
            System.out.println(record);
            record.setIsDel(2);

            record.setEntryTime(record.getEntryTime() + NowString());
            int insertSelective = yhUserBloodpressureService.insertSelective(record);
            try {
                yhHeThresholdService.selectBythresType("blood_pressure_dia", String.valueOf(record.geteUserId()), String.valueOf(record.getDbp().doubleValue()), "舒张压",request);
                yhHeThresholdService.selectBythresType("blood_pressure_sys", String.valueOf(record.geteUserId()), String.valueOf(record.getSbp().doubleValue()), "收缩压",request);
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
     * 根据eid查询最新7条血压记录
     *
     * @param eid
     * @return
     */
    @RequestMapping(value = "/findInfo")
    @ResponseBody
    public Result findInfo(String eid) {
        System.out.println(eid);
        //
        // List<YhUserBloodpressure> selectByPrimaryKeyByUserIdLimit7 =
        // yhUserBloodpressureService
        // .selectByPrimaryKeyByEidLimit7(eid);

        List<YhUserBloodpressureApp> selectByExample = yhUserBloodpressureService.selectByPrimaryKeyByEidLimit7(eid);
        if (null != selectByExample) {
            return Result.ok(selectByExample);
        }

        return Result.error();
    }

    /**
     * 按照年月查询数据 填充前段日历插件
     *
     * @param eid
     * @param ye
     * @param mo
     * @return
     */
    @RequestMapping(value = "/findMonthInfo")
    @ResponseBody
    public Result findMonthInfo(String eid, String ye, String mo) {

        if (null == ye && null == mo) {
            ye = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            mo = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        }
        String shijian = ye + "-" + mo + "-01";
        System.out.println(eid + "" + shijian);
        List<YhUserBloodpressureApp> monthInfoList = yhUserBloodpressureService.selectByPrimaryKeyByMonthInfo(eid,
                shijian);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < monthInfoList.size(); i++) {
            YhUserBloodpressureApp yhUserBloodpressure = monthInfoList.get(i);
            if (null != yhUserBloodpressure) {
                if (null != yhUserBloodpressure.getEntryTime()) {
                    String entryTime = yhUserBloodpressure.getEntryTime();
                    try {
                        Date date = sf.parse(entryTime);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        list.add(day);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (null != list) {
            LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(list);

            ArrayList<Integer> listWithoutDuplicates = new ArrayList<>(hashSet);

            return Result.ok(listWithoutDuplicates);
        }

        return Result.error();
    }

    /**
     * 根据eid查询全部历史信息
     *
     * @param eid
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/findInfoByeidAll")
    @ResponseBody
    public Result findInfoByeidAll(String usercode, @RequestParam(required = false, defaultValue = "0") Integer timeType, String time, Integer page, String type) throws ParseException {
        int pageSize = 7;
        Map<String, Object> map = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        List<String> dataListSbp = new ArrayList<>();
        List<String> dataListDbp = new ArrayList<>();
        if (2 == timeType) {
            List<YhUserBloodpressureApp> list = yhUserBloodpressureService.findBloodpressureByYear(time, usercode, (page - 1) * pageSize, pageSize, type);
            if (null != list) {
                for (YhUserBloodpressureApp yhUserBloodpressure : list) {
                    String entryTime = yhUserBloodpressure.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataListSbp.add(String.valueOf(yhUserBloodpressure.getSbp()));
                    dataListDbp.add(String.valueOf(yhUserBloodpressure.getDbp()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataListSbp", dataListSbp);
            map.put("dataListDbp", dataListDbp);
            return Result.ok(map);
        } else if (1 == timeType) {
            List<YhUserBloodpressureApp> list = yhUserBloodpressureService.findBloodpressureByMonth(time, usercode
                    , (page - 1) * pageSize, pageSize, type);
            if (null != list) {
                for (YhUserBloodpressureApp yhUserBloodpressure : list) {
                    String entryTime = yhUserBloodpressure.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataListSbp.add(String.valueOf(yhUserBloodpressure.getSbp()));
                    dataListDbp.add(String.valueOf(yhUserBloodpressure.getDbp()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataListSbp", dataListSbp);
            map.put("dataListDbp", dataListDbp);
            return Result.ok(map);
        } else if (0 == timeType) {
            String[] split = time.split("~");
            List<YhUserBloodpressureApp> list = yhUserBloodpressureService.findBloodpressureByWeek(split[0], split[1], usercode, (page - 1) * pageSize, pageSize, type);
            if (null != list) {
                for (YhUserBloodpressureApp yhUserBloodpressure : list) {
                    String entryTime = yhUserBloodpressure.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataListSbp.add(String.valueOf(yhUserBloodpressure.getSbp()));
                    dataListDbp.add(String.valueOf(yhUserBloodpressure.getDbp()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataListSbp", dataListSbp);
            map.put("dataListDbp", dataListDbp);
            return Result.ok(map);
        }


        return Result.error();
    }

    /**
     * 查询最新1条血压记录
     *
     * @param inTime
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/findBloodpressureNew")
    @ResponseBody
    public Result findBloodpressureNew(String eid, String type) throws ParseException {
        System.out.println(eid);

        YhUserBloodpressureApp selectByPrimaryKeyNewest2 = yhUserBloodpressureService.selectByPrimaryKeyNewest(eid);// 血压
        if (null != selectByPrimaryKeyNewest2) {
            Integer sbp = selectByPrimaryKeyNewest2.getSbp();
            Integer dbp = selectByPrimaryKeyNewest2.getDbp();
            selectByPrimaryKeyNewest2.setDbp(sbp);

            selectByPrimaryKeyNewest2.setSbp(dbp);
        }
        return Result.ok(selectByPrimaryKeyNewest2);
    }
    // YhUserBloodpressure selectByPrimaryKeyNewest2 =
    // yhUserBloodpressureService.selectByPrimaryKeyNewest(eid);// 血压

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
     * 根据eid查询全部历史信息
     *
     * @param eid
     * @return
     */
    @RequestMapping(value = "/findInfoByeidAllXinlv")
    @ResponseBody
    public Result findInfoByeidAllXinlv(String eid) {
        System.out.println(eid);

        List<YhUserBloodpressureApp> selectByPrimaryKeyByUserIdLimit7 = yhUserBloodpressureService
                .selectByPrimaryKeyBYeidXinlv(eid);

        if (null != selectByPrimaryKeyByUserIdLimit7) {
            return Result.ok(selectByPrimaryKeyByUserIdLimit7);
        }

        return Result.error();
    }

}