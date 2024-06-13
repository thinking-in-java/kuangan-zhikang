// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Util.java

package com.yihe.common.utils;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import org.omg.CORBA.Object;

public class Util
{

	private static String _webinfPath = null;
	private static String _systemPath = null;
	private static String _projectName = null;

	public Util()
	{
	}

	private static void byte2hex(byte b, StringBuffer buf)
	{
		char hexChars[] = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'A', 'B', 'C', 'D', 'E', 'F'
		};
		int high = (b & 0xf0) >> 4;
		int low = b & 0xf;
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}

	public static String toHexStr(byte t[], int start, int end)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < t.length; i++)
			if (start <= i && end >= i && t[i] != 0)
			{
				byte2hex(t[i], sb);
				sb.append(" ");
			}

		return sb.toString();
	}

	public static String[] splitString(String string, String onToken)
	{
		if (string == null)
			return null;
		StringTokenizer tokenizer = new StringTokenizer(string, onToken);
		String result[] = new String[tokenizer.countTokens()];
		for (int i = 0; i < result.length; i++)
			result[i] = tokenizer.nextToken();

		return result;
	}

	public static String getWebModulPath()
	{
		String ret = getWebinfPath();
		int cnt = ret.indexOf("/WEB-INF");
		if (cnt > -1)
			ret = ret.substring(0, cnt);
		return ret;
	}

	public static String getWebinfPath()
	{
		if (_webinfPath == null)
		{
			String res = null;
			Util u = new Util();
			
			String classname = (new StringBuilder(String.valueOf(u.getClass().getName().replace('.', '/')))).append(".class").toString();
			ClassLoader cl = u.getClass().getClassLoader();
			if (cl != null)
			{
				URL url = cl.getResource(classname);
				if (url != null)
				{
					String path = url.getFile();
					int fileStrPosition = path.indexOf("file:/");
					int begin = 0;
					int end = path.length();
					if (fileStrPosition >= 0)
						begin = fileStrPosition + 5;
					end = path.indexOf((new StringBuilder("classes/")).append(classname).toString());
					if (end < 0)
					{
						
						end = path.indexOf("lib/");
						if (end < 0)
						{
							int tmpend = path.indexOf("!/");
							end = path.substring(0, tmpend).lastIndexOf("/");
						}
					}
					String rf = path.substring(begin, end);
					res = (new StringBuilder(String.valueOf((new File(rf)).getAbsolutePath().replace('\\', '/')))).append("/").toString();
				}
			}
			try
			{
				_webinfPath = URLDecoder.decode(res, "UTF-8");
			}
			catch (UnsupportedEncodingException unsupportedencodingexception) { }
		}
		return _webinfPath;
	}

	public static String getSystemPath()
	{
		if (_systemPath == null)
		{
			String res = getWebinfPath();
			System.out.println(res);
			if (res.indexOf("WEB-INF/") > 0 && res.length() > 10)
				res = (new StringBuilder(String.valueOf(res.substring(0, res.lastIndexOf("/", res.length() - 12))))).append("/").toString();
			_systemPath = res;
			System.out.println((new StringBuilder("System Path=")).append(_systemPath).toString());
		}
		return _systemPath;
	}

	public static String getProjectName()
	{
		if (_projectName == null)
		{
			String res = getWebinfPath();
			if (res.indexOf("WEB-INF/") > 0 && res.length() > 10)
				res = (new StringBuilder(String.valueOf(res.substring(0, res.lastIndexOf("/", res.length() - 6))))).append("/").toString();
			String substring = res.substring(0, res.lastIndexOf("/"));
			String substring2 = substring.substring(substring.lastIndexOf("/") + 1);
			_projectName = substring2;
			System.out.println((new StringBuilder("Project Name=")).append(_projectName).toString());
		}
		return _projectName;
	}

	public static boolean checkStr(String str)
	{
		return str != null && !"".equals(str);
	}

	public static boolean checkDate(Date date)
	{
		return date != null && !date.toString().equals("");
	}

	public static boolean checkObj(org.omg.CORBA.Object obj)
	{
		return obj != null && !obj.equals("");
	}

	public static void main(String args[])
	{
		System.out.println(getWebinfPath());
	}

	public static int energyDJ(int sex, float weight, float height, int age)
	{
		float kcal = 0.0F;
		float lx = height - 100F;
		if (height >= 150F)
			if (sex == 2)
				lx = (height - 100F) * 0.85F;
			else
				lx = height - 105F;
		float degree_fat = (weight - lx) / lx;
		Integer stand_score[] = {
			Integer.valueOf(35), Integer.valueOf(32), Integer.valueOf(30), Integer.valueOf(28), Integer.valueOf(25)
		};
		int index_score = 0;
		if (degree_fat < 0.0F && (double)Math.abs(degree_fat) > 0.20000000000000001D)
			index_score = 0;
		if (degree_fat < 0.0F && (double)Math.abs(degree_fat) > 0.10000000000000001D && (double)Math.abs(degree_fat) <= 0.20000000000000001D)
			index_score = 1;
		if (degree_fat >= 0.0F && (double)degree_fat <= 0.10000000000000001D || degree_fat < 0.0F && (double)Math.abs(degree_fat) <= 0.10000000000000001D)
			index_score = 2;
		if ((double)degree_fat > 0.10000000000000001D && (double)degree_fat <= 0.20000000000000001D)
			index_score = 3;
		if ((double)degree_fat > 0.20000000000000001D)
			index_score = 4;
		kcal = lx * (float)stand_score[index_score].intValue();
		return kcalClass(kcal);
	}
	
	public static int kcalClass(float value)
	{
		if (2400F <= value)
			return 2400;
		if (2300F <= value)
			return 2400;
		if (2100F <= value)
			return 2200;
		if (1900F <= value)
			return 2000;
		if (1700F <= value)
			return 1800;
		if (1500F <= value)
			return 1600;
		return 1300F > value ? 1200 : 1400;
	}

	public static Properties getProperties(String path)
		throws IOException
	{
		File file = new File(path);
		Properties p = new Properties();
		if (file.exists())
		{
			FileInputStream input = new FileInputStream(path);
			p.load(input);
			return p;
		} else
		{
			return null;
		}
	}

	public static String capitalize(String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
			return str;
		else
			return (new StringBuilder(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
	}

}
