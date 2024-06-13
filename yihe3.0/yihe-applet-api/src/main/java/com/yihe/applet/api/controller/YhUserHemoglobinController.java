package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserOtherfunctionApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.system.domain.YhUserHemoglobin;
import com.yihe.system.mapper.YhUserHemoglobinMapper;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 血红蛋白
 */
@Controller
@RequestMapping("/api/YhUserHemoglobin")
@Api(value = "YhUserHemoglobin", description = "YhUserHemoglobin")
public class YhUserHemoglobinController {
    @Autowired
    com.yihe.system.mapper.YhUserHemoglobinMapper YhUserHemoglobinMapper;
    @Autowired
    YhUserReginfoAppService YhUserReginfoService;

    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode, Integer timeType, String time) throws java.text.ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> timeList = new HashSet<>();
        YhUserReginfoApp info = YhUserReginfoService.findYhUserReginfo(usercode);
        if(0==timeType) {
            String[] split = time.split("~");
            List<YhUserHemoglobin> list = YhUserHemoglobinMapper.findhemoglobinAllByUserCode(usercode,timeType,split[0],split[1]);

            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserHemoglobin yhUserOtherfunctionApp = list.get(i);
                    Date entryTime = yhUserOtherfunctionApp.getHeData();
                    SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
                    String tiem = sformat.format(entryTime);
                    String transferFormat = transferFormat2(tiem);
                    timeList.add(transferFormat);
                    Float heHb = yhUserOtherfunctionApp.getHeHb();

                        if (info.getGender()==1) {
                            if(heHb>=13&&heHb<=18){
                                k++;
                            }
                        }else{
                            if(heHb>=12&&heHb<=16){
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
            List<YhUserHemoglobin> list = YhUserHemoglobinMapper.findhemoglobinAllByUserCode(usercode,timeType,time,null);

            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserHemoglobin yhUserOtherfunctionApp = list.get(i);
                    Date entryTime = yhUserOtherfunctionApp.getHeData();
                    SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
                    String tiem = sformat.format(entryTime);
                    String transferFormat = transferFormat2(tiem);
                    timeList.add(transferFormat);
                    Float heHb = yhUserOtherfunctionApp.getHeHb();

                    if (info.getGender()==1) {
                        if(heHb>=13&&heHb<=18){
                            k++;
                        }
                    }else{
                        if(heHb>=12&&heHb<=16){
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

    @RequestMapping(value = "/pieChartAssignment")
    @ResponseBody
    public Result pieChartAssignment(String usercode, Integer timetype, String time) {
        YhUserReginfoApp info = YhUserReginfoService.findYhUserReginfo(usercode);
        if (0 == timetype) {
            String[] split = time.split("~");
            int zhengchang = 0;
            int yichang = 0;
            List<YhUserHemoglobin> list = YhUserHemoglobinMapper.findhemoglobinAllByUserCode(usercode, timetype, split[0], split[1]);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserHemoglobin yhUserOtherfunctionApp = list.get(i);
                    Float heHb = yhUserOtherfunctionApp.getHeHb();
                    if (info.getGender()==1) {
                        if(heHb>=13&&heHb<=18){
                            zhengchang++;
                        }else{
                            yichang++;
                        }
                    }else{
                        if(heHb>=12&&heHb<=16){
                            zhengchang++;
                        }else{
                            yichang++;
                        }
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
            List<YhUserHemoglobin> list = YhUserHemoglobinMapper.findhemoglobinAllByUserCode(usercode, timetype,time, null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserHemoglobin yhUserOtherfunctionApp = list.get(i);
                    Float heHb = yhUserOtherfunctionApp.getHeHb();
                    if (info.getGender()==1) {
                        if(heHb>=13&&heHb<=18){
                            zhengchang++;
                        }else{
                            yichang++;
                        }
                    }else{
                        if(heHb>=12&&heHb<=16){
                            zhengchang++;
                        }else{
                            yichang++;
                        }
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
            List<YhUserHemoglobin> list = YhUserHemoglobinMapper.findhemoglobinAllByUserCode(usercode, timetype,time, null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserHemoglobin yhUserOtherfunctionApp = list.get(i);
                    Float heHb = yhUserOtherfunctionApp.getHeHb();
                    if (info.getGender()==1) {
                        if(heHb>=13&&heHb<=18){
                            zhengchang++;
                        }else{
                            yichang++;
                        }
                    }else{
                        if(heHb>=12&&heHb<=16){
                            zhengchang++;
                        }else{
                            yichang++;
                        }
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
     * 新增记录
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserHemoglobin record, HttpServletRequest request) throws java.text.ParseException{
        System.out.println(record);
        // 修改
        if (null != record.getId() && 0 != record.getId()) {
            int i=0;
            if(null!=record.getHeHb()){
                i = YhUserHemoglobinMapper.updateByPrimaryKeySelective(record);
            }else{
                 i = YhUserHemoglobinMapper.deleteByPrimaryKey(record.getId());
            }

            return Result.ok(i);
        } else {
            YhUserHemoglobin yhUserHemoglobin = YhUserHemoglobinMapper.selectByPrimaryKey(record.getId());
            int insertSelective=0;

                String timeinfo= record.getTimeStr() + NowString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(timeinfo);
                record.setHeData(date);

                 insertSelective = YhUserHemoglobinMapper.insertSelective(record);



            if (insertSelective > 0) {
                return Result.ok(insertSelective);
            }
            return Result.error();
        }

    }


    /**
     * 查询已经录入的日期集合selectByPrimaryKeyFindEntryTime
     */

    @RequestMapping(value = "/selectByPrimaryKeyFindEntryTime")
    @ResponseBody
    public Result selectByPrimaryKeyFindEntryTime(String usercode) throws ParseException{
        if (null == usercode) {
            Result.error();
        }

        List<String> selectByPrimaryKeyFindEntryTime = YhUserHemoglobinMapper
                .selectByPrimaryKeyFindEntryTime(usercode);
        for (int i = 0; i < selectByPrimaryKeyFindEntryTime.size(); i++) {
            String time = selectByPrimaryKeyFindEntryTime.get(i);

            String dateString = transferFormat2(time);
            selectByPrimaryKeyFindEntryTime.set(i,dateString);
        }
        return Result.ok(selectByPrimaryKeyFindEntryTime);
    }
    @RequestMapping(value = "/findOtherfunctionInfoNew")
    @ResponseBody
    public Result findOtherfunctionInfoNew(String eid) {
        if (null == eid) {
            Result.error();
        }

        YhUserHemoglobin selectByPrimaryKeyNewest8 = YhUserHemoglobinMapper.selectByPrimaryKeyNewest(eid);// 其他功能

        return Result.ok(selectByPrimaryKeyNewest8);
    }
    @RequestMapping(value = "/findInfoByKey")
    @ResponseBody
    public Result findInfoByKey(Integer id) {
        if (null == id) {
            return Result.error();
        }
        return Result.ok(YhUserHemoglobinMapper.selectByPrimaryKey(id));
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
        YhUserReginfoApp info = YhUserReginfoService.findYhUserReginfo(usercode);
        List<YhUserHemoglobin> list = YhUserHemoglobinMapper
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
        if(null!=list){
            for (int i = 0; i < list.size(); i++) {
                YhUserHemoglobin yhUserOtherfunctionApp = list.get(i);
                Date heData = yhUserOtherfunctionApp.getHeData();
                SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式
                String tiem = sformat.format(heData);
                yhUserOtherfunctionApp.setTimeStr(tiem);
                yhUserOtherfunctionApp.setTiwen("正常");
                yhUserOtherfunctionApp.setOpenNo(false);
                if (i == 0) {
                    yhUserOtherfunctionApp.setOpenNo(true);
                }

                Float heHb = yhUserOtherfunctionApp.getHeHb();



                if (info.getGender()==1) {
                    if(heHb<13){
                        yhUserOtherfunctionApp.setTiwen("偏低");
                    }
                    else if(heHb>=13&&heHb<=18){
                        yhUserOtherfunctionApp.setTiwen("正常");
                    }else{
                        yhUserOtherfunctionApp.setTiwen("偏高");
                    }
                }else{
                    if(heHb<12){
                        yhUserOtherfunctionApp.setTiwen("偏低");
                    }
                    else if(heHb>=12&&heHb<=16){
                        yhUserOtherfunctionApp.setTiwen("正常");
                    }else{
                        yhUserOtherfunctionApp.setTiwen("偏高");
                    }
                }



                if ("正常".equals(yhUserOtherfunctionApp.getTiwen())) {
                    yhUserOtherfunctionApp.setJiedu("您血红蛋白正常，请继续保持健康的生活习惯");
                } else {
                    yhUserOtherfunctionApp.setJiedu("您的检测异常。");
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
    public static String transferFormat2(String inTime) throws ParseException {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");

        Date tempDate = null;
        String outTime = null;
        tempDate = s1.parse(inTime);
        outTime = s1.format(tempDate);
        return outTime;
    }
}
