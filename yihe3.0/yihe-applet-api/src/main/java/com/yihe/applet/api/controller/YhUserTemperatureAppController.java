package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import com.yihe.applet.api.service.impl.YhUserTemperatureAppServiceImpl;
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

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * YhUserTemperaturecontroller Created by dyb on 2020/3/3.
 */
@Controller
@RequestMapping("/api/YhUserTemperature")
@Api(value = "YhUserTemperature控制器", description = "YhUserTemperature管理")
public class YhUserTemperatureAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserTemperatureAppController.class);

    @Autowired
    YhUserTemperatureAppServiceImpl yhUserTemperatureService;
    @Autowired
    YhHeThresholdService yhHeThresholdService;

    /**
     * 体温数据分析Data analysis
     */
    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode,Integer timeType,String time) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        if(0==timeType) {
            String[] split = time.split("~");

            List<YhUserTemperatureApp> list = yhUserTemperatureService.findTiwenAllInfoByUserCode(usercode,timeType,split[0],split[1]);
            Set<String> timeList = new HashSet<>();
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserTemperatureApp yhUserTemperatureApp = list.get(i);
                    String entryTime = yhUserTemperatureApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    Float temperature = yhUserTemperatureApp.getTemperature();

                    if (temperature >= 36 && temperature <= 37.2) {
                        k++;
                    }
                }

                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));
            }
        }else{

            List<YhUserTemperatureApp> list = yhUserTemperatureService.findTiwenAllInfoByUserCode(usercode,timeType,time,null);
            Set<String> timeList = new HashSet<>();
            if (null != list) {
                double k = 0;
                for (int i = 0; i < list.size(); i++) {
                    YhUserTemperatureApp yhUserTemperatureApp = list.get(i);
                    String entryTime = yhUserTemperatureApp.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    Float temperature = yhUserTemperatureApp.getTemperature();

                    if (temperature >= 36 && temperature <= 37.2) {
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
            return Result.error();
        }
        if (0 == timetype) {
            String[] split = time.split("~");
            int findTemperatureByDiRe = yhUserTemperatureService.findTemperatureByDiRe(usercode, timetype, split[0], split[1], page);

            int findTemperatureByGaoRe = yhUserTemperatureService.findTemperatureByGaoRe(usercode, timetype, split[0], split[1], page);

            int findTemperatureByZC = yhUserTemperatureService.findTemperatureByZC(usercode, timetype, split[0], split[1], page);

            int findTemperatureByGD = yhUserTemperatureService.findTemperatureByGD(usercode, timetype, split[0], split[1], page);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("guodi", findTemperatureByGD);
            map.put("zhengchang", findTemperatureByZC);
            map.put("dire", findTemperatureByDiRe);
            map.put("gaore", findTemperatureByGaoRe);
            return Result.ok(map);
        } else if (1 == timetype) {
            int findTemperatureByDiRe = yhUserTemperatureService.findTemperatureByDiRe(usercode, timetype, time, null, page);

            int findTemperatureByGaoRe = yhUserTemperatureService.findTemperatureByGaoRe(usercode, timetype, time, null, page);

            int findTemperatureByZC = yhUserTemperatureService.findTemperatureByZC(usercode, timetype, time, null, page);

            int findTemperatureByGD = yhUserTemperatureService.findTemperatureByGD(usercode, timetype, time, null, page);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("guodi", findTemperatureByGD);
            map.put("zhengchang", findTemperatureByZC);
            map.put("dire", findTemperatureByDiRe);
            map.put("gaore", findTemperatureByGaoRe);
            return Result.ok(map);
        } else {
            int findTemperatureByDiRe = yhUserTemperatureService.findTemperatureByDiRe(usercode, timetype, time, null, page);

            int findTemperatureByGaoRe = yhUserTemperatureService.findTemperatureByGaoRe(usercode, timetype, time, null, page);

            int findTemperatureByZC = yhUserTemperatureService.findTemperatureByZC(usercode, timetype, time, null, page);

            int findTemperatureByGD = yhUserTemperatureService.findTemperatureByGD(usercode, timetype, time, null, page);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("guodi", findTemperatureByGD);
            map.put("zhengchang", findTemperatureByZC);
            map.put("dire", findTemperatureByDiRe);
            map.put("gaore", findTemperatureByGaoRe);
            return Result.ok(map);
        }


    }

    /**
     * 柱状图
     */
    @RequestMapping(value = "/timePeriodDistribution")
    @ResponseBody
    public Result timePeriodDistribution(String usercode, Integer timeType, String time, Integer page) {
        if (StringUtil.isEmpty(usercode)) {
            return Result.error();
        }
        if (0 == timeType) {
            String[] split = time.split("~");
            return Result.ok(yhUserTemperatureService.findtemperatureTimeFenBuWeek(split[0], split[1], usercode, page));
        } else if (1 == timeType) {
            return Result.ok(yhUserTemperatureService.findtemperatureTimeFenBuMonth(time, usercode, page));
        } else {
            return Result.ok(yhUserTemperatureService.findtemperatureTimeFenBuYear(time, usercode, page));
        }

    }

    /**
     * 根据主键查询
     */
    @RequestMapping(value = "/findInfoBykey")
    @ResponseBody
    public Result findInfoBykey(Integer tid) {
        if (null == tid) {
            return Result.error();
        }
        YhUserTemperatureApp selectByPrimaryKey = yhUserTemperatureService.selectByPrimaryKey(tid);
        return Result.ok(selectByPrimaryKey);
    }


    public static Calendar getFirstDayOfWeek(Date date, int firstDayOfWeek) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        cal.setFirstDayOfWeek(firstDayOfWeek);// 设置一星期的第一天是哪一天
        cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);// 指示一个星期中的某天
        cal.set(Calendar.HOUR_OF_DAY, 0);// 指示一天中的小时。HOUR_OF_DAY 用于 24
        // 小时制时钟。例如，在 10:04:15.250 PM
        // 这一时刻，HOUR_OF_DAY 为 22。
        cal.set(Calendar.MINUTE, 0);// 指示一小时中的分钟。例如，在 10:04:15.250 PM
        // 这一时刻，MINUTE 为 4。
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH));

        int dayOfMonth = cal.get(Calendar.DATE);
        System.out.println(dayOfMonth);

        return cal;
    }

    /**
     * 获取周天时间
     */
    public static Calendar lastMonday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, 4 + cal.getFirstDayOfWeek());

        return getFirstDayOfWeek(cal.getTime(), 1);
    }

    public static List<String> getTimeListForFanweiMonth() {
        List<String> timeList = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                timeList.add("2019-0" + i);
            } else {
                timeList.add("2019-" + i);
            }
        }
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        for (int i = 2019 + 1; i <= year; i++) {
            if (i == year) {
                for (int j = 1; j < month + 1; j++) {
                    if (j < 10) {
                        timeList.add(i + "-0" + j);
                    } else {
                        timeList.add(i + "-" + j);
                    }
                }
            } else {
                for (int k = 1; k < 13; k++) {
                    if (k < 10) {
                        timeList.add(i + "-0" + k);
                    } else {
                        timeList.add(i + "-" + k);
                    }
                }
            }

        }

        return timeList;

    }

    /**
     * 获得周历时间集合
     */
    public static List<String> getTimeListForFanwei() {
        List<String> strTime = new ArrayList<>();
        List<String> endTime = new ArrayList<>();
        Calendar c_begin = new GregorianCalendar();
        Calendar c_end = new GregorianCalendar();
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] weeks = dfs.getWeekdays();

        // 2020-10-31

        c_begin.set(2019, 01, 01); // Calendar的月从0-11，所以4月是3.

        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar d = lastMonday();
        System.out.println(smf.format(d.getTime()));
        int dayOfMonth = d.get(Calendar.DATE);


        c_end.set(d.get(Calendar.YEAR), d.get(Calendar.MONTH), dayOfMonth); // Calendar的月从0-11，所以5月是4.

        int count = 1;

        c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天

        while (c_begin.before(c_end)) {

            if ("星期一".equals(weeks[c_begin.get(Calendar.DAY_OF_WEEK)])) {
                System.out.println("第" + count + "周  日期：" + new java.sql.Date(c_begin.getTime().getTime()) + ","
                        + weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
                strTime.add("" + new java.sql.Date(c_begin.getTime().getTime()));

            }
            if ("星期日".equals(weeks[c_begin.get(Calendar.DAY_OF_WEEK)])) {
                if (strTime.size() > 0) {


                    System.out.println("第" + count + "周  日期：" + new java.sql.Date(c_begin.getTime().getTime()) + ","
                            + weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
                    endTime.add("" + new java.sql.Date(c_begin.getTime().getTime()));
                }
            }

            if (c_begin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                count++;
            }

            c_begin.add(Calendar.DAY_OF_YEAR, 1);
        }

        List<String> listime = new ArrayList<>();
        for (int i = 0; i < strTime.size(); i++) {
            listime.add(strTime.get(i) + "~" + endTime.get(i));

        }

        return listime;
    }

    /**
     *
     */
    @RequestMapping(value = "/getTimeListForXcx")
    @ResponseBody
    public Result getTimeListForXcx(String usercode) {
        Map<String, Object> map = new HashMap<>();
        //周历
        map.put("week", weekListInfo());
        map.put("month", getTimeListForFanweiMonth());
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int leng= year-2019+1;
        String[] arr = new String[leng];
        for (int i = 2019,k=0; i <= year; i++,k++) {
            arr[k]=String.valueOf(i);
        }
        map.put("year", arr);

        return Result.ok(map);
    }

    public static void main(String[] args) {

    }
    /**
     * 根据eid查询
     *
     * @param
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
            List<YhUserTemperatureApp> list = yhUserTemperatureService.findTemperatureByYear(time, usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserTemperatureApp yhUserTemperature : list) {
                    String entryTime = yhUserTemperature.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserTemperature.getTemperature()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataList", dataList);
            return Result.ok(map);
        } else if (1 == timeType) {

            List<YhUserTemperatureApp> list = yhUserTemperatureService.findTemperatureByMonth(time, usercode
                    , (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserTemperatureApp yhUserTemperature : list) {
                    String entryTime = yhUserTemperature.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserTemperature.getTemperature()));
                }
            }
            map.put("timeList", timeList);
            map.put("dataList", dataList);
            return Result.ok(map);
        } else if (0 == timeType) {
            String[] split = time.split("~");
            List<YhUserTemperatureApp> list = yhUserTemperatureService.findTemperatureByWeek(split[0], split[1], usercode, (page - 1) * pageSize, pageSize);
            if (null != list) {
                for (YhUserTemperatureApp yhUserTemperature : list) {
                    String entryTime = yhUserTemperature.getEntryTime();
                    String transferFormat = transferFormat2(entryTime);
                    timeList.add(transferFormat);
                    dataList.add(String.valueOf(yhUserTemperature.getTemperature()));
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
     * 周集合
     * */
    public static List<String> weekListInfo(){
        List<String> list=new ArrayList<>();
        for (int i = -30; i <= 0; i++) {
            String week="";
            Calendar cal = Calendar.getInstance();
//n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
            int n = i;
            String monday;
            cal.add(Calendar.DATE, n*7);
//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
            cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
            monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
           // System.out.println(monday);
            week=week+monday;

            Calendar cal2 = Calendar.getInstance();
            int m = i+1;
            String riday;
            cal2.add(Calendar.DATE, m*7);
//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
            cal2.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
            riday = new SimpleDateFormat("yyyy-MM-dd").format(cal2.getTime());
            //System.out.println(riday);
            week=week+"~"+riday;
            list.add(week);
        }

return  list;
    }

    /***
     * 传入String格式日期格式化并返回日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Date data = sdf.parse(date);
        return data;
    }

    public static String dateToStr(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 新增体温与修改
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserTemperatureApp record, HttpServletRequest request) {

        // 修改
        if (null != record.getTid() && 0 != record.getTid()) {
            record.setEntryTime(null);

            int updateByExampleSelective = yhUserTemperatureService.updateByPrimaryKeySelective(record);
            try {
                yhHeThresholdService.selectBythresType("temperature", record.geteUserId(), String.valueOf(record.getTemperature().doubleValue()), "体温",request);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Result.ok(updateByExampleSelective);
        } else {
            record.setIsDel(2);
            record.setEntryTime(record.getEntryTime() + NowString());

            int insertSelective = yhUserTemperatureService.insertSelective(record);
            try {
                yhHeThresholdService.selectBythresType("temperature", record.geteUserId(), String.valueOf(record.getTemperature().doubleValue()), "体温",request);
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
     * 修改体温数据
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/modifyInfo")
    @ResponseBody
    public Result modifyBmiInfo(YhUserTemperatureApp record) {
        yhUserTemperatureService.updateByPrimaryKeySelective(record);

        return Result.ok();
    }

    /**
     * 删除体温数据
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/delInfo")
    @ResponseBody
    public Result delInfo(Integer tid) {
        if (null == tid || 0 == tid) {
            Result.error();
        }
        int deleteByPrimaryKey = yhUserTemperatureService.deleteByPrimaryKey(tid);

        return Result.ok(deleteByPrimaryKey);
    }

    /**
     * 查询已经存在数据的日期
     */
    @RequestMapping(value = "/findTimeInfo")
    @ResponseBody
    public Result findTimeInfo(String usercode) throws ParseException {
        if (null == usercode) {
            Result.error();
        }
        List<String> selectByPrimaryKeyFindEntryTime = yhUserTemperatureService
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
     * 查询最新体温
     */
    @RequestMapping(value = "/findTemperatureInfoNew")
    @ResponseBody
    public Result findTemperatureInfoNew(String eid) {
        if (null == eid) {
            Result.error();
        }
        YhUserTemperatureApp selectByPrimaryKeyOrderTime = yhUserTemperatureService.selectByPrimaryKeyOrderTime(eid);// 体温

        return Result.ok(selectByPrimaryKeyOrderTime);
    }

    /**
     * 根据日期查询体温信息
     */
    @RequestMapping(value = "/findByTimeInfo")
    @ResponseBody
    public Result findByTimeInfo(String usercode, String time) {
        if (null == usercode) {
            Result.error();
        }
        List<YhUserTemperatureApp> selectByPrimaryKeyFindEntryTimeAnduserid = yhUserTemperatureService
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
        if (null != selectByPrimaryKeyFindEntryTimeAnduserid) {
            for (int i = 0; i < selectByPrimaryKeyFindEntryTimeAnduserid.size(); i++) {

                YhUserTemperatureApp yhUserTemperatureApp = selectByPrimaryKeyFindEntryTimeAnduserid.get(i);
                yhUserTemperatureApp.setOpenNo(false);
                if (i == 0) {
                    yhUserTemperatureApp.setOpenNo(true);
                }
                Float temperature = yhUserTemperatureApp.getTemperature();
                if (temperature >= 37.3f && temperature <= 38) {
                    yhUserTemperatureApp.setJieduInfo("可以多喝温热开水，通过排汗、排尿带走体内的热量。另外，还可以用热毛巾或者酒精擦浴腋窝、腹股沟、腘窝等血管比较丰富的地方，促进散热达到降温。同时，建议还要在病因上予以治疗，如果出现反复的低烧症状，建议最好要到医院进行必要的检查，根据病因予以治疗。");
                    yhUserTemperatureApp.setTiwenInfo("低热");
                    yhUserTemperatureApp.setProgressvalueinfo(62.5);
                } else if (temperature >= 38.1f) {
                    yhUserTemperatureApp.setJieduInfo("这时候要多测量体温，需要多喝水，注意休息，补充维生素C，饮食清淡、警惕高烧引起的脱水现象，给予物理降温，及时就医。");
                    yhUserTemperatureApp.setTiwenInfo("高热");
                    yhUserTemperatureApp.setProgressvalueinfo(87.5);
                } else if (temperature >= 36 && temperature <= 37.2f) {
                    yhUserTemperatureApp.setJieduInfo("维持正常的体温要注意饮食正常，衣着适宜，运动出汗后马上要擦干汗等。");
                    yhUserTemperatureApp.setTiwenInfo("正常");
                    yhUserTemperatureApp.setProgressvalueinfo(37.5);
                } else {
                    yhUserTemperatureApp.setJieduInfo("请检查体温测量是否准确、如果长时间持续处于体温低，要引起重视，请及时去医院查明原因，做进一步的改善。");
                    yhUserTemperatureApp.setTiwenInfo("偏低");
                    yhUserTemperatureApp.setProgressvalueinfo(12.5);
                }
            }
        }

        return Result.ok(selectByPrimaryKeyFindEntryTimeAnduserid);
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