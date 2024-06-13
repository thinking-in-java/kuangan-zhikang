// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ManyTideReport.java

package com.yihe.common.pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.yihe.common.riskresult.DatePhaseUtil;
import com.yihe.common.riskresult.GroupReport;
import com.yihe.common.riskresult.GroupReportManyTide;
import com.yihe.common.utils.PDFUtil;
import com.yihe.common.utils.ReportUtil;

import ch.qos.logback.core.joran.spi.ActionException;


// Referenced classes of package cn.well.step.util.risk:
//			ReportUtil, RiskConnection

public class ManyTideReport extends ReportUtil
	implements RiskConnection
{

	private GroupReport groupReport;
	private GroupReportManyTide manyTide;
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

	public ManyTideReport(GroupReport groupReport, GroupReportManyTide manyTide)
	{
		//footerStr = "注：鲨鱼风险评估技术授权自中华预防医学会健康风险评估与控制专业委员会";
		footerStr = "";
		this.groupReport = groupReport;
		this.manyTide = manyTide;
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

	public PdfPTable manyTideReportPdf()
		throws ActionException, BadElementException, MalformedURLException, IOException
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		int mount[] = manyTide.getMount();
		float manyRiskMax[][] = manyTide.getManyRiskMax();
		int manyRisk[][] = manyTide.getManyRisk();
		String genderStr = manyTide.getGenderStr();
		List dateList = manyTide.getDateList();
		String riskNames = manyTide.getRiskNames();
		String riskNameArray[] = riskNames.split(";");
		str1 = "趋势分析-多因素  ";
			table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
			pdfPTable.addCell(table_Page1);
			table_img1 = getJfreeChar(manyTide.getJFreeChartPath(), width_table_Page2);
			pdfPTable.addCell(table_img1);
		str2 = "评估对象按时间分布（多因素）";
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
		pdfCell2 = PDFUtil.getCorderPdfPCellA("月份", font3_temp, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		for (int i = 0; i < riskNameArray.length; i++)
			table_Page5.addCell(createInner(riskNameArray[i], "符合人数", "总人数", "百分比", font3_temp, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));

		for (int i = 0; i < dateList.size(); i++)
		{
			DatePhaseUtil dateUtil = (DatePhaseUtil)dateList.get(i);
			if (dateUtil.getHql() != null)
			{
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(dateUtil.getDate()))).toString(), font2, 1, TableLineColor));
				for (int k = 0; k < manyRisk.length; k++)
				{
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(manyRisk[k][i]))).toString(), font2, 1, TableLineColor));
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(mount[i]))).toString(), font2, 1, TableLineColor));
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(manyRiskMax[k][i]))).toString(), font2, 1, TableLineColor));
				}

			}
		}

		pdfPTable.addCell(table_Page5);
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
		// TODO Auto-generated method stub
		
	}
}
