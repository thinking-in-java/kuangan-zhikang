// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ManyReport.java

package com.yihe.common.pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.yihe.common.riskresult.AgePhaseUtil;
import com.yihe.common.riskresult.GroupReport;
import com.yihe.common.riskresult.GroupReportMany;
import com.yihe.common.utils.PDFUtil;
import com.yihe.common.utils.ReportUtil;

import ch.qos.logback.core.joran.spi.ActionException;

// Referenced classes of package cn.well.step.util.risk:
//			ReportUtil, RiskConnection

public class ManyReport extends ReportUtil
	implements RiskConnection
{

	private GroupReport groupReport;
	private GroupReportMany many;
	private Font font2;
	private java.awt.Color color1;
	private Font font3;
	private Image image;
	private Font font1;
	private float width_table_Page1[] = {
		0.0F, 0.5F, 0.5F
	};
	private float width_table_Page2[] = {
		0.0F, 1.0F, 0.0F
	};
	private PdfPTable table_img1;
	private PdfPTable table_Page2;
	private String str1;
	private String str2;
	private PdfPTable table_Page5;
	private PdfPCell pdfCell2;
	private PdfPTable table_Page1;
	private String footerStr;
	private Font footerFont;
	private String ageStr;

	public ManyReport(GroupReport groupReport, GroupReportMany many, String ageStr)
	{
		//footerStr = "注：鲨鱼风险评估技术授权自中华预防医学会健康风险评估与控制专业委员会";
		footerStr = "";
		this.groupReport = groupReport;
		this.many = many;
		this.ageStr = ageStr;
		font2 = textFont;
		font2.setSize(10F);
		font3 = font2;
		color1 = TableBackgroundColor;
		image = getLogo();
		font1 = topFont;
		font1.setSize(14F);
		font1.setStyle(1);
		footerFont = headerFooterFont;
		footerFont.setSize(8F);
	}

	public PdfPTable manyReportPdf()
		throws ActionException, BadElementException, MalformedURLException, IOException
	{
		int manyRiskGender[][] = many.getManyRiskGender();
		int allNo = many.getAllNo();
		int manNo = many.getManNo();
		int womanNo = many.getWomanNo();
		float manyRiskGenderMax[][] = many.getManyRiskGenderMax();
		int allMount[] = many.getAllMount();
		int manMount[] = many.getManMount();
		int womanMount[] = many.getWomanMount();
		int manyRiskAgeAll[][] = many.getManyRiskAgeAll();
		int manyRiskAgeMan[][] = many.getManyRiskAgeMan();
		int manyRiskAgeWoman[][] = many.getManyRiskAgeWoman();
		float manyRiskAgeAllMax[][] = null;
		float manyRiskAgeManMax[][] = null;
		float manyRiskAgeWomanMax[][] = null;
		String gendarStr = many.getGendarStr();
		List ageList = many.getAgeList();
		String ageStr = "";
		if (this.ageStr != null)
			ageStr = this.ageStr;
		if (!ageStr.equals(""))
		{
			manyRiskAgeAllMax = many.getManyRiskAgeAllMax();
			manyRiskAgeManMax = many.getManyRiskAgeManMax();
			manyRiskAgeWomanMax = many.getManyRiskAgeWomanMax();
		}
		String riskNames = many.getRiskNames();
		String riskNameArray[] = riskNames.split(";");
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		str1 = "多因素分析  ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		if (!ageStr.equals("") && gendarStr.substring(0, 1).equals("1"))
		{
			table_img1 = getJfreeChar(many.getJFreeChartPath2(), width_table_Page2);
			pdfPTable.addCell(table_img1);
		}
		if (!ageStr.equals("") && gendarStr.substring(1, 2).equals("1"))
		{
			table_img1 = getJfreeChar(many.getJFreeChartPath3(), width_table_Page2);
			pdfPTable.addCell(table_img1);
		}
		if (!ageStr.equals("") && gendarStr.substring(2).equals("1"))
		{
			table_img1 = getJfreeChar(many.getJFreeChartPath4(), width_table_Page2);
			pdfPTable.addCell(table_img1);
		}
		if (ageStr.equals(""))
		{
			table_img1 = getJfreeChar(many.getJFreeChartPath(), width_table_Page2);
			pdfPTable.addCell(table_img1);
		}
		if (ageStr.equals(""))
		{
			str2 = "评估对象按性别分布（多因素）";
			table_Page2 = addRemard(width_table_Page2, font3, str2);
			pdfPTable.addCell(table_Page2);
			Font font3_temp = font3;
			if (riskNameArray.length == 4)
				font3_temp.setSize(8F);
			float f[] = new float[riskNameArray.length * 3 + 1];
			f[0] = 0.1F;
			for (int i = 1; i <= riskNameArray.length * 3; i++)
				f[i] = (float)(0.90000000000000002D / (double)riskNameArray.length / 3D);

			table_Page5 = new PdfPTable(f);
			pdfCell2 = PDFUtil.getCorderPdfPCellA("性别", font3_temp, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
			pdfCell2.setVerticalAlignment(5);
			table_Page5.addCell(pdfCell2);
			for (int i = 0; i < riskNameArray.length; i++)
				table_Page5.addCell(createInner(riskNameArray[i], "患病人数", "总人数", "%", font3_temp, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));

			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("男性", font2, 1, TableLineColor));
			for (int i = 0; i < riskNameArray.length; i++)
			{
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(1, 2).equals("1") ? ((Object) (Integer.valueOf(manyRiskGender[i][1]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(1, 2).equals("1") ? ((Object) (Integer.valueOf(manNo))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(1, 2).equals("1") ? ((Object) (Float.valueOf(manyRiskGenderMax[i][1]))) : " ").toString(), font2, 1, TableLineColor));
			}

			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("女性", font2, 1, TableLineColor));
			for (int i = 0; i < riskNameArray.length; i++)
			{
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(2).equals("1") ? ((Object) (Integer.valueOf(manyRiskGender[i][2]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(2).equals("1") ? ((Object) (Integer.valueOf(womanNo))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(2).equals("1") ? ((Object) (Float.valueOf(manyRiskGenderMax[i][2]))) : " ").toString(), font2, 1, TableLineColor));
			}

			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计", font2, 1, TableLineColor));
			for (int i = 0; i < riskNameArray.length; i++)
			{
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(0, 1).equals("1") ? ((Object) (Integer.valueOf(manyRiskGender[i][0]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(0, 1).equals("1") ? ((Object) (Integer.valueOf(allNo))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(0, 1).equals("1") ? ((Object) (Float.valueOf(manyRiskGenderMax[i][0]))) : " ").toString(), font2, 1, TableLineColor));
			}

			pdfPTable.addCell(table_Page5);
		}
		if (!ageStr.equals("") && gendarStr.substring(0, 1).equals("1"))
		{
			str2 = "评估对象按年龄分布（多因素-总体）";
			table_Page2 = addRemard(width_table_Page2, font3, str2);
			pdfPTable.addCell(table_Page2);
			Font font3_temp = font3;
			if (riskNameArray.length == 4)
				font3_temp.setSize(8F);
			float f[] = new float[riskNameArray.length * 3 + 1];
			f[0] = 0.1F;
			for (int i = 1; i <= riskNameArray.length * 3; i++)
				f[i] = (float)(0.90000000000000002D / (double)riskNameArray.length / 3D);

			table_Page5 = new PdfPTable(f);
			pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3_temp, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
			pdfCell2.setVerticalAlignment(5);
			table_Page5.addCell(pdfCell2);
			for (int i = 0; i < riskNameArray.length; i++)
				table_Page5.addCell(createInner(riskNameArray[i], "患病人数", "总人数", "%", font3_temp, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));

			int count = 0;
			for (int i = 0; i < ageList.size(); i++)
			{
				List subList = (List)ageList.get(i);
				for (int j = 0; j < subList.size(); j++)
				{
					AgePhaseUtil ageUtil = (AgePhaseUtil)subList.get(j);
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(ageUtil.getName2(), font2, 1, TableLineColor));
					for (int k = 0; k < manyRiskAgeAll.length; k++)
					{
						table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(manyRiskAgeAll[k][count]))).toString(), font2, 1, TableLineColor));
						table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(allMount[count]))).toString(), font2, 1, TableLineColor));
						table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(manyRiskAgeAllMax[k][count]))).toString(), font2, 1, TableLineColor));
					}

					count++;
				}

			}

			pdfPTable.addCell(table_Page5);
		}
		if (!ageStr.equals("") && gendarStr.substring(1, 2).equals("1"))
		{
			str2 = "评估对象按年龄分布（多因素-男）";
			table_Page2 = addRemard(width_table_Page2, font3, str2);
			pdfPTable.addCell(table_Page2);
			Font font3_temp = font3;
			if (riskNameArray.length == 4)
				font3_temp.setSize(8F);
			float f[] = new float[riskNameArray.length * 3 + 1];
			f[0] = 0.1F;
			for (int i = 1; i <= riskNameArray.length * 3; i++)
				f[i] = (float)(0.90000000000000002D / (double)riskNameArray.length / 3D);

			table_Page5 = new PdfPTable(f);
			pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3_temp, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
			pdfCell2.setVerticalAlignment(5);
			table_Page5.addCell(pdfCell2);
			for (int i = 0; i < riskNameArray.length; i++)
				table_Page5.addCell(createInner(riskNameArray[i], "患病人数", "总人数", "%", font3_temp, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));

			int count = 0;
			for (int i = 0; i < ageList.size(); i++)
			{
				List subList = (List)ageList.get(i);
				for (int j = 0; j < subList.size(); j++)
				{
					AgePhaseUtil ageUtil = (AgePhaseUtil)subList.get(j);
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(ageUtil.getName2(), font2, 1, TableLineColor));
					for (int k = 0; k < manyRiskAgeMan.length; k++)
					{
						table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(manyRiskAgeMan[k][count]))).toString(), font2, 1, TableLineColor));
						table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(manMount[count]))).toString(), font2, 1, TableLineColor));
						table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(manyRiskAgeManMax[k][count]))).toString(), font2, 1, TableLineColor));
					}

					count++;
				}

			}

			pdfPTable.addCell(table_Page5);
		}
		if (!ageStr.equals("") && gendarStr.substring(1, 2).equals("1"))
		{
			str2 = "评估对象按年龄分布（多因素-女）";
			table_Page2 = addRemard(width_table_Page2, font3, str2);
			pdfPTable.addCell(table_Page2);
			Font font3_temp = font3;
			if (riskNameArray.length == 4)
				font3_temp.setSize(8F);
			float f[] = new float[riskNameArray.length * 3 + 1];
			f[0] = 0.1F;
			for (int i = 1; i <= riskNameArray.length * 3; i++)
				f[i] = (float)(0.90000000000000002D / (double)riskNameArray.length / 3D);

			table_Page5 = new PdfPTable(f);
			pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3_temp, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
			pdfCell2.setVerticalAlignment(5);
			table_Page5.addCell(pdfCell2);
			for (int i = 0; i < riskNameArray.length; i++)
				table_Page5.addCell(createInner(riskNameArray[i], "患病人数", "总人数", "%", font3_temp, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));

			int count = 0;
			for (int i = 0; i < ageList.size(); i++)
			{
				List subList = (List)ageList.get(i);
				for (int j = 0; j < subList.size(); j++)
				{
					AgePhaseUtil ageUtil = (AgePhaseUtil)subList.get(j);
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(ageUtil.getName2(), font2, 1, TableLineColor));
					for (int k = 0; k < manyRiskAgeWoman.length; k++)
					{
						table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(manyRiskAgeWoman[k][count]))).toString(), font2, 1, TableLineColor));
						table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(womanMount[count]))).toString(), font2, 1, TableLineColor));
						table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(manyRiskAgeWomanMax[k][count]))).toString(), font2, 1, TableLineColor));
					}

					count++;
				}

			}

			pdfPTable.addCell(table_Page5);
		}
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public boolean createPDF()
	{
		return false;
	}


	public PdfPTable getPdfPTable()
		throws BadElementException, MalformedURLException, IOException
	{
		return null;
	}

	@Override
	public void setParameter(Object[] aobj) {
		
	}
}
