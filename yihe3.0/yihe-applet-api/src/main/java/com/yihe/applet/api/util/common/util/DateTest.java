package com.yihe.applet.api.util.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateTest {
	public static void main(String[] args) throws ParseException {
		// 获取今年第一天
		/*Calendar currCal = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, currCal.get(Calendar.YEAR));
		Date time = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String firstday = format.format(time);
		System.out.println(firstday);
		 System.out.println(getSundayOfThisWeek());
		 */
		  // List<String> dateOfWeek = getDateOfWeek("2020-07-01", getSundayOfThisWeek());
		 //String dateDiff = dateDiff("2020-07-04 22:11:00", "2020-07-05 07:00:00");
		 //System.out.println(dateDiff);
		List<String> dateOfWeek = getEndDay("2020-06-29", "2020-07-06");
		for (String string : dateOfWeek) {
			System.out.println(string);
		}
	}
	public static List<String> getEndDay(String beginDate,String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = sdf.parse(beginDate);
        Date dEnd = sdf.parse(endDate);
        List<String> datas = findDates(dBegin, dEnd);
        return datas;
    }
	
	public static List<String> getEndYear(String beginDate,String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = sdf.parse(beginDate);
        Date dEnd = sdf.parse(endDate);
        List<String> datas = findDatesYear(dBegin, dEnd);
        return datas;
    }
	
	public static List<String> findDates(Date dBegin, Date dEnd){
		  List<String> lDate = new ArrayList<String>();
		  SimpleDateFormat sd = new SimpleDateFormat("MM-dd");
		  lDate.add(sd.format(dBegin));
		  Calendar calBegin = Calendar.getInstance();
		  // 使用给定的 Date 设置此 Calendar 的时间
		  calBegin.setTime(dBegin);
		  Calendar calEnd = Calendar.getInstance();
		  // 使用给定的 Date 设置此 Calendar 的时间
		  calEnd.setTime(dEnd);
		  // 测试此日期是否在指定日期之后
		  while (dEnd.after(calBegin.getTime()))
		  {
			   // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			   calBegin.add(Calendar.DAY_OF_MONTH, 1);
			   lDate.add(sd.format(calBegin.getTime()));
		  }
		  lDate.remove(lDate.size()-1);
		  return lDate;
	 }
	public static List<String> findDatesYear(Date dBegin, Date dEnd){
		  List<String> lDate = new ArrayList<String>();
		  SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		  lDate.add(sd.format(dBegin));
		  Calendar calBegin = Calendar.getInstance();
		  // 使用给定的 Date 设置此 Calendar 的时间
		  calBegin.setTime(dBegin);
		  Calendar calEnd = Calendar.getInstance();
		  // 使用给定的 Date 设置此 Calendar 的时间
		  calEnd.setTime(dEnd);
		  // 测试此日期是否在指定日期之后
		  while (dEnd.after(calBegin.getTime()))
		  {
			   // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			   calBegin.add(Calendar.DAY_OF_MONTH, 1);
			   lDate.add(sd.format(calBegin.getTime()));
		  }
		  lDate.remove(lDate.size()-1);
		  return lDate;
	 }
	
	public static List<String> getTimeList(){
		return getDateOfWeek("2020-07-01", getSundayOfThisWeek());
	}
	
	public static String dateDiff(Date startTime, Date endTime  
             ) {  
        // 按照传入的格式生成一个simpledateformate对象  
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数  
        long nh = 1000 * 60 * 60;// 一小时的毫秒数  
        long nm = 1000 * 60;// 一分钟的毫秒数  
        long ns = 1000;// 一秒钟的毫秒数  
        long diff;  
        long day = 0;  
        long hour = 0;  
        long min = 0;  
        long sec = 0;  
        diff = endTime.getTime() - startTime.getTime();  
		day = diff / nd;// 计算差多少天  
		hour = diff % nd / nh + day * 24;// 计算差多少小时  
		min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟  
		sec = diff % nd % nh % nm / ns;// 计算差多少秒  
		// 输出结果  
		System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"  
		        + (min - day * 24 * 60) + "分钟" + sec + "秒。");  
		if(min==0){
			 return hour +".0";
		}else{
			 return hour +"."+Math.round(min*100/60);
		}
       
    } 
	
	
	public static String dateDiff2(Date startTime, Date endTime  
            ) {  
       // 按照传入的格式生成一个simpledateformate对象  
       long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数  
       long nh = 1000 * 60 * 60;// 一小时的毫秒数  
       long nm = 1000 * 60;// 一分钟的毫秒数  
       long ns = 1000;// 一秒钟的毫秒数  
       long diff;  
       long day = 0;  
       String hour = "";  
       String min = "";  
       long sec = 0;  
       diff = endTime.getTime() - startTime.getTime();  
		day = diff / nd;// 计算差多少天  
		hour = diff % nd / nh + day * 24 + "小时";// 计算差多少小时  
		min = diff % nd % nh / nm + day * 24 * 60 + "分钟";// 计算差多少分钟  
		sec = diff % nd % nh % nm / ns;// 计算差多少秒  
		// 输出结果  
		
       return hour +":"+ min;
   } 
	
	
	
	public static String dateDiffJisuan(Date startTime, Date endTime  
            ) {  
       // 按照传入的格式生成一个simpledateformate对象  
       long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数  
       long nh = 1000 * 60 * 60;// 一小时的毫秒数  
       long nm = 1000 * 60;// 一分钟的毫秒数  
       long ns = 1000;// 一秒钟的毫秒数  
       long diff;  
       long day = 0;  
       long hour = 0;  
       long min = 0;  
       long sec = 0;  
       diff = endTime.getTime() - startTime.getTime();  
		day = diff / nd;// 计算差多少天  
		hour = diff % nd / nh + day * 24;// 计算差多少小时  
		min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟  
		sec = diff % nd % nh % nm / ns;// 计算差多少秒  
		// 输出结果  
		System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"  
		        + (min - day * 24 * 60) + "分钟" + sec + "秒。");
		if(hour<6){
			return "不足";
		}else if(hour>=6&&hour<=9){
			return "良好";
		}else{
			return "过多";
		}
   }  

	/**
	 * 得到本周周日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek() {
		 SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		return format.format(c.getTime());
	}

	/**
	 * @Title: getDateOfWeek
	 * @Description: 获取两个时间内所有周之间的时间段，并且获得是今年第几周（同一年内）
	 * @param start_time
	 *            开始时间
	 * @param end_time
	 *            结束时间
	 */
	public static List<String> getDateOfWeek(String start_time, String end_time) {
		List<String> timelist=new ArrayList<String>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar s_c = Calendar.getInstance();
			Calendar e_c = Calendar.getInstance();
			java.util.Date s_time = sdf.parse(start_time);
			java.util.Date e_time = sdf.parse(end_time);

			String first_week = getFirstDayOfWeek(start_time);// 取得开始日期指定日期所在周的第一天
			String last_week = getLastDayOfWeek(end_time);// 取得结束日期指定日期所在周的最后一天

			s_c.setTime(s_time);
			e_c.setTime(e_time);
			int year = s_c.get(Calendar.YEAR);

			int currentWeekOfYear = s_c.get(Calendar.WEEK_OF_YEAR);
			int currentWeekOfYear_e = e_c.get(Calendar.WEEK_OF_YEAR);

			if (currentWeekOfYear_e == 1) {
				currentWeekOfYear_e = 53;
			}

			int j = 12;
			for (int i = 0; i < currentWeekOfYear_e; i++) {
				int dayOfWeek = e_c.get(Calendar.DAY_OF_WEEK) - 2;
				e_c.add(Calendar.DATE, -dayOfWeek); // 得到本周的第一天

				String s_date = sdf.format(e_c.getTime());
				e_c.add(Calendar.DATE, 6); // 得到本周的最后一天

				String e_date = sdf.format(e_c.getTime());
				e_c.add(Calendar.DATE, -j); // 减去增加的日期

				// 只取两个日期之间的周
				if (currentWeekOfYear == currentWeekOfYear_e - i + 2) {
					break;
				}

				// 只取两个日期之间的周
				if (compareDate(first_week, s_date) && compareDate(s_date, last_week) && compareDate(first_week, e_date)
						&& compareDate(e_date, last_week)) {
					// 超过选择的日期，按选择日期来算
					if (!compareDate(start_time, s_date)) {
						s_date = start_time;
					}
					if (!compareDate(e_date, end_time)) {
						e_date = end_time;
					}
					String s =s_date + "~" + e_date;
					timelist.add(s);
					System.out.println(s);
				}

			}
			 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return timelist;
	}

	/**
	 * 取得指定日期所在周的第一天
	 */
	public static String getFirstDayOfWeek(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date time = sdf.parse(date);
			Calendar c = new GregorianCalendar();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setTime(time);
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
			return sdf.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 取得指定日期所在周的最后一天
	 */
	public static String getLastDayOfWeek(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date time = sdf.parse(date);
			Calendar c = new GregorianCalendar();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setTime(time);
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
			return sdf.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * compareDate方法
	 * <p>
	 * 方法说明： 比较endDate是否是晚于startDate； 如果是，返回true， 否则返回false
	 * </p>
	 */
	public static boolean compareDate(String startDate, String endDate) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = dateFormat.parse(startDate);
			java.util.Date date2 = dateFormat.parse(endDate);
			if (date1.getTime() > date2.getTime())
				return false;
			return true; // startDate时间上早于endDate

		} catch (Exception e) {
			return false;
		}
	}
}
