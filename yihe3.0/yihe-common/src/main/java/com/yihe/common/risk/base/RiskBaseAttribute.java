// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RiskBaseAttribute.java

package com.yihe.common.risk.base;

import java.awt.Color;
import java.io.File;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.yihe.common.utils.ITextUtil;
import com.yihe.common.utils.Util;


public abstract class RiskBaseAttribute
{

	public Document document;
	public static BaseFont baseFont;
	public static final String cover_image_1;
	public static final String cover_image_2;
	public static final Font cover_font;
	public static final Color cover_TableLineColor;
	public static final float COVER_TLW = 0F;
	public static final float COVER_TLW_2 = 1F;
	public static final String imagePath;
	
	public static final Font topFont;
	public static final Font topBoldFont;
	public static final Font topTextBoldFont;
	public static final Font topTextFont;
	public static final Font redTextFont;
	public static final Font headerFooterFont;
	protected static final Font textFont;
	public static final Color TableBackgroundColor = new Color(153, 204, 204);
	public static final Color TableBackgroundColorShallow = new Color(236, 245, 244);
	public static final Color TableLineColor = new Color(124, 175, 176);
	public static final float TLW = 0.1F;
	public Image logo;
	public Image calibration;
	public Image hbpCalibration;
	public Image gouImage;
	public Image yyImage;
	public Image llImage;
	public Image rrImage;

	public RiskBaseAttribute()
	{
		document = new Document(PageSize.A4, -10F, -50F, 2.0F, 12F);
		logo = null;
		calibration = null;
		hbpCalibration = null;
		gouImage = null;
		yyImage = null;
		llImage = null;
		rrImage = null;
	}

	public Image getLogo()
	{
		if (logo == null)
			logo = ITextUtil.getImageScaleAbsolute(imagePath, 150F, 100F);
		return logo;
	}



	public abstract void creatImage(String as[], int i);



	/*public String getIFValue(Object obj, int gender, int flag)
	{
		WellRiskresultImp wr = (WellRiskresultImp)obj;
		StringBuffer sb = new StringBuffer();
		int disStep = 0;
		if ((wr.getBmi() >= 24F || wr.getWaistLine() >= 85F && gender == 2 || wr.getWaistLine() >= 80F && gender == 1) && flag != 2)
		{
			sb.append("控制体重;");
			disStep++;
		}
		if (wr.getFastingBg() >= 5.6F && flag == 0)
		{
			sb.append("控制血糖水平;");
			disStep++;
		}
		if ((wr.getTriglycerin() >= 1.7F || wr.getHdlc() < 1.04F) && flag == 0 || wr.getCholesterol() >= 5.18F && flag == 1)
			sb.append("控制血脂水平;");
		if ((wr.getSbp() >= 120 || wr.getDbp() >= 80) && flag == 3 || flag == 1 && wr.getSbp() >= 120)
			sb.append("控制血压;");
		if (wr.getAct() == 3 && flag == 0)
			sb.append("增加体力活动;");
		if (wr.getFruitDaily() + wr.getVegetableDaily() < 500F && (flag == 0 || flag == 2))
			sb.append("增加蔬菜水果摄入;");
		if (wr.getSmokingStatus() == 2)
			sb.append("请戒烟;");
		if (wr.getPassiveFreq() == 1 && flag == 2)
			sb.append("避免被动吸烟;");
		if (wr.getLungCookhotFactor() == 1 && flag == 2)
			sb.append("请改善烹调习惯;");
		return sb.toString();
	}*/


	public String getActLevel(int top, int bot)
	{
		StringBuffer sb = new StringBuffer();
		float level = (float)top / (float)bot;
		if (level >= 0.86F)
		{
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"234\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/sport_ren.gif\" width=\"100\" height=\"60\" /></td>");
		} else
		if (level >= 0.66F)
		{
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"234\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/sport_ren.gif\" width=\"100\" height=\"60\" /></td>");
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
		} else
		{
			sb.append("<td width=\"234\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/sport_ren.gif\" width=\"100\" height=\"60\" /></td>");
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
		}
		return sb.toString();
	}

	public String[] getLifeInfo(String nowStr, String preStr)
	{
		String nowStrs[] = nowStr.split(",");
		String preStrs[] = {
			"", "", ""
		};
		if (Util.checkStr(preStr))
			preStrs = preStr.split(",");
		String resultStr[] = new String[3];
		if (nowStrs[0].equals("1"))
			resultStr[0] = "1";
		else
		if (nowStrs[0].equals("2"))
			resultStr[0] = "2";
		else
			resultStr[0] = "3";
		resultStr[1] = (new StringBuilder(String.valueOf(nowStrs[1]))).append("/").append(nowStrs[2]).toString();
		if (Util.checkStr(preStrs[2]))
			resultStr[2] = (new StringBuilder(String.valueOf(preStrs[1]))).append("/").append(preStrs[2]).toString();
		else
			resultStr[2] = "-";
		return resultStr;
	}

	static 
	{
		baseFont = ITextUtil.getBaseFontInstance();
		cover_image_1 = (new StringBuilder(String.valueOf(Util.getSystemPath()))).append("/classes").append("/jsp/cover_1.jpg").toString();
		cover_image_2 = (new StringBuilder(String.valueOf(Util.getSystemPath()))).append("/classes").append("/jsp/cover_2.jpg").toString();
		cover_font = new Font(baseFont, 16F, 1, Color.BLACK);
		cover_TableLineColor = Color.BLACK;
		System.out.println("--------------------------");
		imagePath = (new StringBuilder(String.valueOf(Util.getSystemPath()))).append("/classes").append("/jsp/logo.gif").toString();

		topFont = new Font(baseFont, 24F, 0, Color.BLACK);
		topBoldFont = new Font(baseFont, 20F, 1, Color.BLACK);
		topTextBoldFont = new Font(baseFont, 12F, 1, Color.BLACK);
		topTextFont = new Font(baseFont, 12F, 0, Color.BLACK);
		redTextFont = new Font(baseFont, 12F, 0, Color.RED);  
		headerFooterFont = new Font(baseFont, 10F, Font.getStyleValue("����"), Color.BLACK);
		textFont = new Font(baseFont, 12F, Font.getStyleValue("����"), Color.BLACK);
	}
}
