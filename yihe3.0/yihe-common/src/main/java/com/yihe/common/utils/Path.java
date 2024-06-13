// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Path.java

package com.yihe.common.utils;


import java.io.File;

import com.yihe.common.config.SystemConf;

public class Path
{

	public static final String ALL = SystemConf.getInstance().getProperty("all", true);
	public static final String RISK_SERVICE = SystemConf.getInstance().getProperty("risk_service", true);
	public static final String STATIC_CONTEXT_LOCATION = Util.getProjectName();
	public static final String EDATE;
	public static final String UPLOAD_IMAGES_FOOD;
	public static final String UPLOAD_MESSAGE_FILE;
	public static final String READINGROOM;
	public static final String PASSING;
	public static final String NEWFILEPATH;
	public static final String TEMPLATE;
	public static final String FOODIMAGES;
	public static final String RISKIMAGES;
	public static final String HELPMAGES;
	public static final String ECGIMAGES;
	public static final String USERPHOTO;
	public static final String IMAGES;
	public static final String SOFT;
	public static final String NEWS;
	public static final String IMAGE_DEFAULT;
	public static final String REPORT;
	public static final String SCAN;
	public static final String UEDITORIMAGES;
	public static final String APPFILETEMP;
	public static final String RISK_HTML_LOCATION;
	public static final String RISK_PDF_LOCATION;
	public static final String RISK_FILEFORUSER_LOCATION;
	public static final String APK_LOCATION;
	public static final String RISK_IMG_SYS;
	public static final String RISK_IMG_PASS;
	public static final String risk_report_m_left_1;
	public static final String risk_report_m_left_2;
	public static final String risk_report_m_left_ms;
	public static final String risk_report_m_left_life;
	public static final String risk_report_m_left_bmi;
	public static final String risk_report_m_life1;
	public static final String risk_report_m_life2;
	public static final String risk_report_m_male;
	public static final String risk_report_m_female;

	public Path()
	{
	}

	static 
	{
		EDATE = (new StringBuilder(String.valueOf(ALL))).append("conf").append(File.separatorChar).append("data").append(File.separatorChar).toString();
		UPLOAD_IMAGES_FOOD = (new StringBuilder(String.valueOf(ALL))).append("uploadfile").append(File.separatorChar).append("food_image").append(File.separatorChar).toString();
		UPLOAD_MESSAGE_FILE = (new StringBuilder(String.valueOf(ALL))).append("uploadfile").append(File.separatorChar).append("message_file").append(File.separatorChar).toString();
		READINGROOM = (new StringBuilder(String.valueOf(ALL))).append("uploadfile").append(File.separatorChar).append("readingRoom").append(File.separatorChar).toString();
		PASSING = (new StringBuilder(String.valueOf(ALL))).append("passing").append(File.separatorChar).toString();
		NEWFILEPATH = (new StringBuilder(String.valueOf(ALL))).append("uploadfile").append(File.separatorChar).append("news_file").append(File.separatorChar).toString();
		TEMPLATE = (new StringBuilder(String.valueOf(ALL))).append("template").append(File.separatorChar).toString();
		FOODIMAGES = (new StringBuilder(String.valueOf(ALL))).append("uploadfile").append(File.separatorChar).append("food_images").append(File.separatorChar).toString();
		RISKIMAGES = (new StringBuilder(String.valueOf(PASSING))).append("risk_images").append(File.separatorChar).toString();
		HELPMAGES = (new StringBuilder(String.valueOf(ALL))).append("uploadfile").append(File.separatorChar).append("help_images").append(File.separatorChar).toString();
		ECGIMAGES = (new StringBuilder(String.valueOf(ALL))).append("uploadfile").append(File.separatorChar).append("ecg_images").append(File.separatorChar).toString();
		USERPHOTO = (new StringBuilder(String.valueOf(ALL))).append("userphoto").append(File.separatorChar).toString();
		IMAGES = (new StringBuilder(String.valueOf(ALL))).append("images").append(File.separatorChar).toString();
		SOFT = (new StringBuilder(String.valueOf(ALL))).append("soft").append(File.separatorChar).toString();
		NEWS = (new StringBuilder(String.valueOf(ALL))).append("uploadfile").append(File.separatorChar).append("news").append(File.separatorChar).toString();
		IMAGE_DEFAULT = (new StringBuilder(String.valueOf(ALL))).append("images").append(File.separatorChar).append("default.jpg").toString();
		REPORT = (new StringBuilder(String.valueOf(ALL))).append("conf").append(File.separatorChar).append("report").append(File.separatorChar).toString();
		SCAN = (new StringBuilder(String.valueOf(ALL))).append("scan").append(File.separatorChar).toString();
		UEDITORIMAGES = (new StringBuilder(String.valueOf(ALL))).append("ueditor").append(File.separatorChar).append("images").append(File.separatorChar).toString();
		APPFILETEMP = (new StringBuilder(String.valueOf(ALL))).append("apptemp").append(File.separatorChar).toString();
		RISK_HTML_LOCATION = (new StringBuilder("riskReport")).append(File.separatorChar).append("html").append(File.separatorChar).toString();
		RISK_PDF_LOCATION = (new StringBuilder("riskReport")).append(File.separatorChar).append("pdf").append(File.separatorChar).toString();
		RISK_FILEFORUSER_LOCATION = (new StringBuilder("userFiles")).append(File.separatorChar).toString();
		APK_LOCATION = (new StringBuilder(String.valueOf(ALL))).append("app").append(File.separatorChar).append("apk").append(File.separatorChar).toString();
		RISK_IMG_SYS = (new StringBuilder("riskReport")).append(File.separatorChar).toString();
		RISK_IMG_PASS = (new StringBuilder(String.valueOf(RISK_IMG_SYS))).append("pass").append(File.separatorChar).toString();
		risk_report_m_left_1 = (new StringBuilder(String.valueOf(ALL))).append(RISK_IMG_SYS).append("sysPic").append(File.separatorChar).append("01.png").toString();
		risk_report_m_left_2 = (new StringBuilder(String.valueOf(ALL))).append(RISK_IMG_SYS).append("sysPic").append(File.separatorChar).append("02.png").toString();
		risk_report_m_left_ms = (new StringBuilder(String.valueOf(ALL))).append(RISK_IMG_SYS).append("sysPic").append(File.separatorChar).append("ms.png").toString();
		risk_report_m_left_life = (new StringBuilder(String.valueOf(ALL))).append(RISK_IMG_SYS).append("sysPic").append(File.separatorChar).append("life.png").toString();
		risk_report_m_left_bmi = (new StringBuilder(String.valueOf(ALL))).append(RISK_IMG_SYS).append("sysPic").append(File.separatorChar).append("fp.png").toString();
		risk_report_m_life1 = (new StringBuilder(String.valueOf(ALL))).append(RISK_IMG_SYS).append("sysPic").append(File.separatorChar).append("life_001.png").toString();
		risk_report_m_life2 = (new StringBuilder(String.valueOf(ALL))).append(RISK_IMG_SYS).append("sysPic").append(File.separatorChar).append("life_002.png").toString();
		risk_report_m_male = (new StringBuilder(String.valueOf(ALL))).append(RISK_IMG_SYS).append("sysPic").append(File.separatorChar).append("gender_01.png").toString();
		risk_report_m_female = (new StringBuilder(String.valueOf(ALL))).append(RISK_IMG_SYS).append("sysPic").append(File.separatorChar).append("gender_02.png").toString();
	}
}
