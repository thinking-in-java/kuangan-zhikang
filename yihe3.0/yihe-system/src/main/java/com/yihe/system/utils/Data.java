// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DateUtil.java

package com.yihe.system.utils;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Data
{


	public Data()
	{
	}
	public static String CalculateTime(String time) {
		long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
		String msg = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 指定时间格式
		Date setTime = null; // 指定时间
		try {
			setTime = sdf.parse(time); // 将字符串转换为指定的时间格式
		} catch (ParseException e) {

			e.printStackTrace();
		}
		long reset = setTime.getTime(); // 获取指定时间的毫秒数
		long dateDiff = nowTime - reset;
		if (dateDiff < 0) {
			msg = "刚刚";
		} else {
			long dateTemp1 = dateDiff / 1000; // 秒
			long dateTemp2 = dateTemp1 / 60; // 分钟
			long dateTemp3 = dateTemp2 / 60; // 小时
			long dateTemp4 = dateTemp3 / 24; // 天数
			long dateTemp5 = dateTemp4 / 30; // 月数
			long dateTemp6 = dateTemp5 / 12; // 年数
			if (dateTemp6 > 0) {
				msg = dateTemp6 + "年前";
			} else if (dateTemp5 > 0) {
				msg = dateTemp5 + "个月前";
			} else if (dateTemp4 > 0) {
				msg = dateTemp4 + "天前";
			} else if (dateTemp3 > 0) {
				msg = dateTemp3 + "小时前";
			} else if (dateTemp2 > 0) {
				msg = dateTemp2 + "分钟前";
			} else if (dateTemp1 > 0) {
				msg = "不久前";
			}
		}
		return msg;
	}
	public static String tansFormat(String date)
	{
		String rev = null;
		SimpleDateFormat from = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
		SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = null;
		try
		{
			dt = from.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		rev = to.format(dt);
		return rev;
	}

	public static java.sql.Date tansFormatHour(String date)
	{
		java.sql.Date rev = null;
		SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd HH");
		try
		{
			rev = (java.sql.Date)to.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return rev;
	}

	public static java.sql.Date getNowTime()
	{
		return new java.sql.Date(Calendar.getInstance().getTime().getTime());
	}

	public static java.sql.Date getNowDate()
	{
		java.sql.Date now = getNowTime();
		String today = Date2Str(now, "yyyy-MM-dd");
		now = Str2Date((new StringBuilder(String.valueOf(today))).append(" 00:00:00").toString());
		return now;
	}

	public static java.sql.Date getNowDateEnd()
	{
		java.sql.Date now = getNowTime();
		String today = Date2Str(now, "yyyy-MM-dd");
		now = Str2Date((new StringBuilder(String.valueOf(today))).append(" 23:59:59").toString());
		return now;
	}

	public static String getReturnTime()
	{
		java.sql.Date now = getNowTime();
		String today = Date2Str(now, "yyyy-MM-dd");
		return today;
	}

	public static String getReturnTime(String pattern)
	{
		java.sql.Date now = getNowTime();
		String today = Date2Str(now, pattern);
		return today;
	}

	public static String getReturnTimeM()
	{
		java.sql.Date now = getNowTime();
		String today = Date2Str(now, "yyyy-MM-dd HH:mm");
		return today;
	}

	public static String getReturnTimeMs()
	{
		java.sql.Date now = getNowTime();
		String today = Date2Str(now, "yyyyMMddHHmmss");
		return today;
	}

	public static String getReturnTimeMs1()
	{
		java.sql.Date now = getNowTime();
		String today = Date2Str(now, "yyyy-MM-dd#HH:mm:ss");
		return today;
	}

	public static java.sql.Date Str2Date(String date)
	{
		Date d;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		if (date.length() > 10)
			ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d = ft.parse(date);
			return new java.sql.Date(d.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return new java.sql.Date(Calendar.getInstance().getTime().getTime());
	}

	public static java.sql.Date Str2Date1(String sendTime)
	{
		Date d;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		if (sendTime.length() > 10)
			ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d = ft.parse(sendTime);
			return new java.sql.Date(d.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new java.sql.Date(Calendar.getInstance().getTime().getTime());
	}

	public static java.sql.Date Str2DateX(String date, String pattern)
	{
		return Str2Date(date, pattern);
	}

	public static java.sql.Date Str2Date(String date, String pattern)
	{
		Date d;
		SimpleDateFormat ft = new SimpleDateFormat(pattern);
		try {
			d = ft.parse(date);
			return new java.sql.Date(d.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return null;
	}

	public static Date Str2utilDate(String date, boolean all)
	{
		Date d;
		SimpleDateFormat ft = null;
		if (all)
			ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else
			ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			d = ft.parse(date);
			return d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public static Date getStrutilDate()
	{
		return StrutilDate((new StringBuilder(String.valueOf(getReturnTime()))).append(" 23:59:59").toString(), "yyyy-MM-dd HH:mm:ss");
	}

	public static Date StrutilDate(String date, String pattern)
	{
		Date d;
		SimpleDateFormat ft = new SimpleDateFormat(pattern);
		try {
			d = ft.parse(date);
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		return null;
	}

	public static String Date4Seq()
	{
		return Date2Str(getNowTime(), "yyyyMMddHHmmss");
	}

	public static String Date2Str(java.sql.Date date, String pattern)
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

	public static String Date2Str(Date date, String pattern)
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

	public static String Date2Str(Date date)
	{
		if (date == null)
			date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ft.format(date);
	}

	public static String getYear(java.sql.Date date)
	{
		SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		return ft.format(date);
	}

	public static String getMonth(java.sql.Date date)
	{
		SimpleDateFormat ft = new SimpleDateFormat("MM");
		return ft.format(date);
	}

	public static String getDay(java.sql.Date date)
	{
		SimpleDateFormat ft = new SimpleDateFormat("dd");
		return ft.format(date);
	}

	public static String getHour(java.sql.Date date)
	{
		SimpleDateFormat ft = new SimpleDateFormat("HH");
		return ft.format(date);
	}

	public static String getMinute(java.sql.Date date)
	{
		SimpleDateFormat ft = new SimpleDateFormat("mm");
		return ft.format(date);
	}

	public static String getWeek(Date time)
	{
		String theWeek = "";
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStringToParse = bartDateFormat.format(time);
		try
		{
			Date date = bartDateFormat.parse(dateStringToParse);
			SimpleDateFormat bartDateFormat2 = new SimpleDateFormat("EEEE");
			theWeek = bartDateFormat2.format(date);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return theWeek;
	}

	public static int getWeekInt(Date d)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int i = c.get(7);
		c.clear();
		if (i - 1 == 0)
			i = 8;
		return i - 1;
	}

	public static String getDateStr(Date date)
	{
		String time = "";
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		time = bartDateFormat.format(date);
		time = (new StringBuilder(String.valueOf(time))).append(" ").append(getWeek(date)).toString();
		return time;
	}

	public static String getDateStr2(Date date)
	{
		String time = "";
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		time = bartDateFormat.format(date);
		return time;
	}

	public static String getDateStr3(Date date)
	{
		String time = "";
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		time = bartDateFormat.format(date);
		return time;
	}

	public static String getDateWeek(Date date)
	{
		String time = "";
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		time = bartDateFormat.format(date);
		time = (new StringBuilder(String.valueOf(time.substring(0, 4)))).append("��").append(time.substring(5, 7)).append("��").append(time.substring(8, 10)).append("��").append(" ").append(getWeek(date)).toString();
		return time;
	}

	public static List getDaysOfMonth(Date date)
	{
		LinkedList list = new LinkedList();
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(date);
		int firstDay = cDay1.getActualMinimum(5);
		int lastDay = cDay1.getActualMaximum(5);
		for (int i = firstDay; i <= lastDay; i++)
		{
			Date lastDate = null;
			lastDate = cDay1.getTime();
			lastDate.setDate(i);
			list.add(lastDate);
		}

		return list;
	}

	public static int getDays(Date date1, Date date2)
	{
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(date1);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(date2);
		if (!gc1.after(gc2))
		{
			GregorianCalendar gc3 = (GregorianCalendar)gc2.clone();
			gc2 = (GregorianCalendar)gc1.clone();
			gc1 = (GregorianCalendar)gc3.clone();
		}
		Long beginL = Long.valueOf(gc2.getTimeInMillis());
		Long endL = Long.valueOf(gc1.getTimeInMillis());
		return (int)((endL.longValue() - beginL.longValue()) / 0x5265c00L);
	}

	public static int getHours(Date date1, Date date2)
	{
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(date1);
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime(date2);
		if (!gc1.after(gc2))
		{
			GregorianCalendar gc3 = (GregorianCalendar)gc2.clone();
			gc2 = (GregorianCalendar)gc1.clone();
			gc1 = (GregorianCalendar)gc3.clone();
		}
		Long beginL = Long.valueOf(gc2.getTimeInMillis());
		Long endL = Long.valueOf(gc1.getTimeInMillis());
		return (int)((endL.longValue() - beginL.longValue()) / 0x36ee80L);
	}

	public static int getMonthDay(Date date)
	{
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		return time.getActualMaximum(5);
	}

	public static Date getGoDay(Date date, int i)
	{
		GregorianCalendar gc = new GregorianCalendar();
		if (date != null)
			gc.setTime(date);
		else
			gc.setTime(getNowDate());
		gc.set(6, gc.get(6) + i);
		return gc.getTime();
	}

	public static Date addMonth(Date date, int i)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(2, i);
		return c.getTime();
	}

	public static String getInterval(Date bDate, Date eDate)
	{
		int interval = 0;
		long millisecond = eDate.getTime() - bDate.getTime();
		if (millisecond > 0x5265c00L)
		{
			interval = Math.abs((int)(millisecond / 0x5265c00L));
			return (new StringBuilder(String.valueOf(interval))).append("��").toString();
		}
		if (millisecond > 0x36ee80L)
		{
			interval = Math.abs((int)(millisecond / 0x36ee80L));
			return (new StringBuilder(String.valueOf(interval))).append("Сʱ").toString();
		}
		if (millisecond > 60000L)
		{
			interval = Math.abs((int)(millisecond / 60000L));
			return (new StringBuilder(String.valueOf(interval))).append("����").toString();
		} else
		{
			interval = Math.abs((int)(millisecond / 1000L));
			return (new StringBuilder(String.valueOf(interval))).append("��").toString();
		}
	}

	public static String Str2Str(String date, String formate)
	{
		if (date != null && date.trim().length() != 0)
			Date2Str(Str2Date(date, formate), formate);
		return date;
	}

	public static String getGoDay(int i, String date)
	{
		GregorianCalendar gc = new GregorianCalendar();
		if (date != null && !date.equals(""))
			gc.setTime(Str2Date(date, "yyyy-MM-dd"));
		else
			gc.setTime(getNowDate());
		gc.set(6, gc.get(6) + i);
		return Date2Str(gc.getTime(), "yyyy-MM-dd");
	}

	public static void main(String args[])
	{
		System.out.println(getReturnTimeM());
	}

	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static long gettimeStemp(String time, String format) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!"".equals(format)) {
			simpleDateFormat = new SimpleDateFormat(format);
		}

		long timeStemp = 0;
		try {

			Date date = simpleDateFormat.parse(time);

			timeStemp = date.getTime();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeStemp;
	}
}
