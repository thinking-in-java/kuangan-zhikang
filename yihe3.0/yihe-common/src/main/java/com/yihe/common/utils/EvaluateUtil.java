// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EvaluateUtil.java

package com.yihe.common.utils;

import java.util.Date;

// Referenced classes of package com.huier.utils:
//			StringUtils

public class EvaluateUtil
{

	public EvaluateUtil()
	{
	}

	
	

	public static Integer getEvaluateAge(Date riskTime, Date BirthDate)
	{
		String end = DateUtil.Date2Str(riskTime, "yyyyMMdd");
		String begin = DateUtil.Date2Str(BirthDate, "yyyyMMdd");
		int age = 0;
		if (ParamType.getint(end.substring(4, 8)) < ParamType.getint(begin.substring(4, 8)))
			age--;
		age += ParamType.getint(end.substring(0, 4)) - ParamType.getint(begin.substring(0, 4));
		return Integer.valueOf(age);
	}

	public static String getReportName(String reportFlag)
	{
		String reportName = "";
		String s;
		switch ((s = reportFlag).hashCode())
		{
		default:
			break;

		case -1809282850: 
			if (s.equals("allReports"))
				reportName = "所有报告";
			break;

		case -891980232: 
			if (s.equals("stroke"))
				reportName = "脑卒中";
			break;

		case 3137: 
			if (s.equals("bc"))
				reportName = "乳腺癌";
			break;

		case 3140: 
			if (s.equals("bf"))
				reportName = "血脂异常";
			break;

		case 3494: 
			if (s.equals("ms"))
				reportName = "代谢综合症";
			break;

		case 3571: 
			if (s.equals("pc"))
				reportName = "前列腺癌";
			break;

		case 97662: 
			if (s.equals("bmi"))
				reportName = "肥胖";
			break;

		case 98463: 
			if (s.equals("chd"))
				reportName = "冠心病";
			break;

		case 103094: 
			if (s.equals("hbp"))
				reportName = "高血压";
			break;

		case 109003: 
			if (s.equals("ngd"))
				reportName = "尼古丁成瘾";
			break;

		case 3083110: 
			if (s.equals("diab"))
				reportName = "糖尿病";
			break;

		case 3148894: 
			if (s.equals("food"))
				reportName = "膳食";
			break;

		case 3182880: 
			if (s.equals("gszn"))
				reportName = "健康改善指南";
			break;

		case 3189395: 
			if (s.equals("gzss"))
				reportName = "骨质疏松症";
			break;

		case 3321596: 
			if (s.equals("life"))
				reportName = "生活方式";
			break;

		case 3333378: 
			if (s.equals("lung"))
				reportName = "肺癌";
			break;

		case 97615798: 
			if (s.equals("foodT"))
				reportName = "膳食糖尿病";
			break;

		case 109651828: 
			if (s.equals("sport"))
				reportName = "运动";
			break;
		}
		return reportName;
	}

	
}
