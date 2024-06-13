// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ParamType.java

package com.yihe.common.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParamType
{

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm";

	public ParamType()
	{
	}

	public static int getint(String s)
	{
		int i = 0;
		if (s == null)
			return 0;
		try
		{
			i = Integer.parseInt(s);
		}
		catch (Exception e)
		{
			return 0;
		}
		return i;
	}

	public static byte getbyte(String s)
	{
		byte b = 0;
		if (s == null)
			return 0;
		try
		{
			b = Byte.parseByte(s);
		}
		catch (Exception e)
		{
			return 0;
		}
		return b;
	}

	public static short getshort(String s)
	{
		short i = 0;
		if (s == null)
			return 0;
		try
		{
			i = Short.parseShort(s);
		}
		catch (Exception e)
		{
			return 0;
		}
		return i;
	}

	public static long getlong(String s)
	{
		long l = 0L;
		if (s == null)
			return 0L;
		try
		{
			l = Long.parseLong(s);
		}
		catch (Exception e)
		{
			return 0L;
		}
		return l;
	}

	public static float getfloat(String s)
	{
		float f = 0.0F;
		if (s == null)
			return 0.0F;
		try
		{
			f = Float.parseFloat(s);
		}
		catch (Exception e)
		{
			return 0.0F;
		}
		return f;
	}

	public static double getdouble(String s)
	{
		double d = 0.0D;
		if (s == null)
			return 0.0D;
		try
		{
			d = Double.parseDouble(s);
		}
		catch (Exception e)
		{
			return 0.0D;
		}
		return d;
	}

	public static Date getDate(String s)
	{
		Date d = null;
		if (s == null)
			return null;
		try
		{
			if (s.length() > 10)
				d = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(s);
			else
				d = (new SimpleDateFormat("yyyy-MM-dd")).parse(s);
		}
		catch (Exception exception) { }
		return d;
	}

	public static boolean getboolean(String s)
	{
		if (s == null)
			return false;
		if (s.trim().toLowerCase().equals("true"))
			return true;
		return s.trim().equals("1");
	}

	public static String getString(String s)
	{
		return s;
	}

	public static String getStringKey(String s)
	{
		return s;
	}

	public static BigDecimal getBigDecimal(String s)
	{
		return new BigDecimal(s);
	}

	public static char getchar(String s)
	{
		if (s.length() == 0)
		{
			return ' ';
		} else
		{
			char chs[] = s.toCharArray();
			return chs[0];
		}
	}

	public static Date getDate(String s, int off)
	{
		Date d = getDate(s);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(5, off);
		return c.getTime();
	}

	public static Boolean getBoolean(String s)
	{
		return new Boolean(getboolean(s));
	}

	public static Integer getInteger(String s)
	{
		return new Integer(getint(s));
	}

	public static Byte getByte(String s)
	{
		return new Byte(s);
	}

	public static Short getShort(String s)
	{
		return new Short(getshort(s));
	}

	public static Long getLong(String s)
	{
		return new Long(getlong(s));
	}

	public static Float getFloat(String s)
	{
		return new Float(getfloat(s));
	}

	public static Double getDouble(String s)
	{
		return new Double(getdouble(s));
	}

	public static float getFloNum(float value, int n)
	{
		int dd = 1;
		for (int i = 0; i < n; i++)
			dd *= 10;

		float tempnum = value * (float)dd;
		int tnum = Math.round(tempnum);
		return (float)tnum / (float)dd;
	}
}
