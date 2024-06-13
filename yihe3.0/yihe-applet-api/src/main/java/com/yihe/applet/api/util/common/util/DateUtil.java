package com.yihe.applet.api.util.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;


/**
 * 时间工具类
 * 
 * @author hermit
 * @date 2017 -06-10 14:04:41
 */
public class DateUtil {
	
	private static final long ONE_DAY_MILL_SECS=1000*3600*24;

    /**
     * 周一盒周日集合
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
    
    // 将字符串日期 转换成 yyyy-MM-dd HH:mm:ss
    public static Date changeStrToDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将字符串日期 转换成 yyyy-MM-dd
    public static Date changeStrToDate2(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 将字符串日期 转换成 yyyy-MM-dd
    public static Date changeStrToDate3(String dateStr,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String getDateToStr(Date date, SimpleDateFormat sdf) {
        String dateStr = null;
        try {
            dateStr = sdf.format(date);
            return dateStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // 将date类型转换为字符串yyyy-MM-dd HH:mm:ss
    public static String changeDateTOStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateToStr(date, sdf);
    }
    
    // 将date类型转换为字符串yyyy-MM-dd
    public static String changeDateTOStr3(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return getDateToStr(date, sdf);
    }
    
    // 将date类型转换为字符串yyyy-MM-dd
    public static String changeDateTOStrYue(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return getDateToStr(date, sdf);
    }
    
    // 将date类型转换为字符串yyyy年MM月dd日
    public static String changeDateTOStr4(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return getDateToStr(date, sdf);
    }
    // 将date类型转换为字符串yyyy年MM月dd日
    public static String changeDateTOStr5(Date date,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return getDateToStr(date, sdf);
    }
    
    // 将date类型转换为字符串yyyyMMddHHmmss
    public static String changeDateToYmdhms(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return getDateToStr(date, sdf);
    }
    
    // 将date类型转换为字符串yyyyMMdd--20100302
    public static String changeDateTOStr2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return getDateToStr(date, sdf);
    }
    
    // 得到前一天的日期
    public static String getFormerDate() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // 得到前一天
        
        return changeDateTOStr2(calendar.getTime());
    }
    // 得到前一天的日期
    public static String getFormerDate2() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // 得到前一天

        return changeDateTOStr3(calendar.getTime());
    }

    
    // 得到下一天的日期
    public static String getAfterOneDayDate() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // 得到前一天
        
        return changeDateTOStr2(calendar.getTime());
    }

    public static String getAfterOneDayDate2() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // 得到前一天

        return changeDateTOStr3(calendar.getTime());
    }
    
    // 得到 mountday前的日期，mountday为天数
    public static String getFormerMonth(int mountday) {
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.add(Calendar.DATE, -mountday);
        
        return changeDateTOStr(calendar.getTime());
        
    }

    // 得到 mountday前的日期，mountday为天数
    public static String getFormerMonth2(int mountday) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -mountday);

        return changeDateTOStr3(calendar.getTime());

    }
    /**
     * @Description 获取指定的时间格式(yyyy-MM-dd)(yyyyMMdd)
     * @author hermit
     * @date 2015年3月6日 上午8:44:01
     * @return
     */
    public static String DateToStrOfPattern(Date date, String pattern)
	{
		if (date == null)
		{
			return "";
		} else
		{
			SimpleDateFormat ft = new SimpleDateFormat(pattern);
			return ft.format(date);
		}
	}
    
    
    /**
     * 获得当前天时间指定格式类型
     * 
     * @Description
     * @author hermit
     * @date 2015年3月6日 上午8:44:01
     * @return
     */
    public static String getNowDateToStrOfPattern(String pattern) {
    	TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
	    TimeZone.setDefault(tz);
	    Date date = new Date();//获得系统时间.
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return getDateToStr(date, sdf);
    }
    
    
    /**
     * 获得当前天(yyyy-MM-dd)
     * 
     * @Description
     * @author hermit
     * @date 2015年3月6日 上午8:44:01
     * @return
     */
    public static String getNowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return getDateToStr(new Date(), sdf);
    }
    
    
    /**
     * 获得当时间(yyyy-MM-dd HH:mm:ss)
     * 
     * @Description
     * @author hermit
     * @date 2015年3月6日 上午8:44:45
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateToStr(new Date(), sdf);
    }
    
    /**
     * 获取当前时间戳
     * 
     * @return
     * @author hermit
     * @date 2015年10月
     */
    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return getDateToStr(new Date(), sdf);
    }
    
    /**
     * 
     * @Description 获取yyyyMM年月
     * @author hermit
     * @date 2015年3月19日 下午2:23:38
     * @param date
     * @return
     */
    public static String getDateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return getDateToStr(date, sdf);
    }
    
    /**
     * 
     * @Description 获取任意时间
     * @author hermit
     * @date 2015年4月14日 下午12:28:26
     * @param year 0为 当年，1为下一年
     * @param month 0为 当月，1为下一月
     * @param day 0为 当日，1为下一天
     * @param hour 0为 00点，1为1点
     * @param minute 0为 00分，1为1分
     * @param second 0为 00秒，1为1秒
     * @return
     */
    public static Date getSysDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DAY_OF_MONTH, day);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        cal.add(Calendar.MINUTE, minute);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }
    
    /**
     * 
     * @Description 加num个月
     * @author hermit
     * @date 2015年4月14日 上午11:23:23
     * @param date 当前时间
     * @param num 延长的月数
     * @return
     */
    public static Date delayMonth(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, num);
        date = cal.getTime();
        return date;
    }
    
    /**
     * 当前时间延期num个月数
     * 
     * @author hermit
     * @date 2015年4月14日 下午5:24:06
     * @param num
     * @return
     */
    public static Date delayMonth(int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, num);
        return cal.getTime();
    }
    
    /**
     * 计算两个日期之间相隔的天数，不到一天不算
     * 如果开始日期大于截止日期，返回负数
     * @param s 开始日期
     * @param e 截止日期
     * @return
     */
    public static int diffDays(Date s,Date e){
    	long difMill=e.getTime()-s.getTime();
    	long days= difMill/ONE_DAY_MILL_SECS;
    	return (int)days;
    }


    /**
     * 计算两个日期之间相隔的天数，不到一天不算
     * 如果开始日期大于截止日期，返回负数
     * @param s 开始日期
     * @param e 截止日期
     * @return
     */
    public static String diffYears(Date s,Date e){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String s1=sdf.format(s);
        String e1=sdf.format(e);
        String[] arg1 = s1.split("-");
        String[] arg2 = e1.split("-");
        int year1 = Integer.valueOf(arg1[0]);
        int year2 = Integer.valueOf(arg2[0]);
        int month1 = Integer.valueOf(arg1[1]);
        int month2 = Integer.valueOf(arg2[1]);
        int day1 = Integer.valueOf(arg1[2]);
        int day2 = Integer.valueOf(arg2[2]);
        int md = 0 ;
        if(year1!=year2){
            md = day2>day1?0:-1;
        }
        int diffMonth = (year2*12+month2)-(year1*12+month1)+md;
        int yearNum = diffMonth/12;
        int monthNum = diffMonth % 12;
        String year =Integer.toString(yearNum);
        String month = Integer.toString(monthNum);
        String workdays=year+"年"+month+"月";
        return workdays;
    }
    /**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}


    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }
    
    public static String getMatTime(Date date){
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
    	simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    	String format = simpleDateFormat.format(new Date());
    	return format;
    }
    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDate2(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        return today;
    }

    public static Date timestampToDate(String str_num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (str_num.length() == 13) {
            String date = format.format(new Date(Long.parseLong(str_num)));
            return changeStrToDate(date);
        } else {
            String date = format.format(new Date(Integer.parseInt(str_num) * 1000L));
            return changeStrToDate(date);
        }
    }
    
    
    /**
     * 获取本周日期
     * @param date
     * @param pattern
     * @return
     */
    public static List<String> getNowWeekDateStr(Date date,String pattern){
    	List<String> list=new ArrayList<String>();
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期  
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);  
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);  
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        if(pattern==""){
        	pattern="yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String one = sdf.format(cal.getTime());  
         //System.out.println("所在周星期一的日期：" + imptimeBegin);  
        cal.add(Calendar.DATE, 1);  
		String two = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, 1);  
		String three = sdf.format(cal.getTime()); 
		cal.add(Calendar.DATE, 1);  
		String four = sdf.format(cal.getTime()); 
		cal.add(Calendar.DATE, 1);  
		String five = sdf.format(cal.getTime()); 
		cal.add(Calendar.DATE, 1);  
		String six = sdf.format(cal.getTime()); 
		cal.add(Calendar.DATE, 1);  
		String seven = sdf.format(cal.getTime()); 
		list.add(one);
		list.add(two);
		list.add(four);
		list.add(five);
		list.add(six);
		list.add(seven); 
		return list;
    }
    /**
     * 获取本周星期几
     * @param
     */
    public static int getDayOfWeek(String dateStr,String pattern){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			calendar.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i =calendar.get(Calendar.DAY_OF_WEEK);
		if(i == 1){
			return i=7;
		}else{
			return i=i-1;
		}
    }
    
    
    /**
     * 
     * endTime比startTime多的天数
     *
     * @param
     * @param
     * @return
     */
    public static int calcDayOffset(Date startTime, Date endTime) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startTime);
 
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endTime);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
 
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {  //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {  //闰年
                    timeDistance += 366;
                } else {  //不是闰年
 
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else { //不同年
            return day2 - day1;
        }
    }
 
    /**
     * endTime比startTime多的周数
     * @param startTime
     * @param endTime
     * @return
     */
    public static int calcWeekOffset(Date startTime, Date endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) dayOfWeek = 7;
 
        int dayOffset = calcDayOffset(startTime, endTime);
 
        int weekOffset = dayOffset / 7;
        int a;
        if (dayOffset > 0) {
            a = (dayOffset % 7 + dayOfWeek > 7) ? 1 : 0;
        } else {
            a = (dayOfWeek + dayOffset % 7 < 1) ? -1 : 0;
        }
        weekOffset = weekOffset + a;
        return weekOffset;
    }

    public static String dateDiff(Date begin) {
        Date end = new Date();
        long between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
        if (day > 0) {
            return day + "天前";
        }
        if (day <= 0 && hour > 0) {
            return hour + "小时前";
        }
        if (hour <= 0 && min > 0) {
            return min + "分钟前";
        }
        if (s < 60) {
            return s + "秒前";
        }
        return "";
    }
}
