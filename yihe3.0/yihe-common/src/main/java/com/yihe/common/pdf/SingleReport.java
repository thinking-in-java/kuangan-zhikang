// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SingleReport.java

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
import com.yihe.common.riskresult.GroupReportSingle;
import com.yihe.common.utils.PDFUtil;
import com.yihe.common.utils.ReportUtil;

import ch.qos.logback.core.joran.spi.ActionException;

// Referenced classes of package cn.well.step.util.risk:
//			ReportUtil, RiskConnection

public class SingleReport extends ReportUtil
	implements RiskConnection
{

	private GroupReport groupReport;
	private GroupReportSingle single;
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

	public SingleReport(GroupReport groupReport, GroupReportSingle single)
	{
		//footerStr = "注：谷宜健康风险评估技术授权自中华预防医学会健康风险评估与控制专业委员会";
		footerStr = "";
		this.groupReport = groupReport;
		this.single = single;
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

	public PdfPTable singleReportPdf()
		throws ActionException, BadElementException, MalformedURLException, IOException
	{
		int all[] = single.getAll();
		int allMount[] = single.getAllMount();
		float allMax[] = single.getAllMax();
		int man[] = single.getMan();
		int manMount[] = single.getManMount();
		float manMax[] = single.getManMax();
		int woman[] = single.getWoman();
		int womanMount[] = single.getWomanMount();
		float womanMax[] = single.getWomanMax();
		String gendarStr = single.getGendarStr();
		List ageList = single.getAgeList();
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		str1 = "单因素分析  ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		table_img1 = getJfreeChar(single.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象按年龄及性别分布(单因素)";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.163F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner("总体", "患病人数", "总人数", "%", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("男性", "患病人数", "总人数", "%", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("女性", "患病人数", "总人数", "%", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		int count = 0;
		for (int i = 0; i < ageList.size(); i++)
		{
			List subList = (List)ageList.get(i);
			for (int j = 0; j < subList.size(); j++)
			{
				AgePhaseUtil ageUtil = (AgePhaseUtil)subList.get(j);
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(ageUtil.getName2(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(0, 1).equals("1") ? ((Object) (Integer.valueOf(all[count]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(0, 1).equals("1") ? ((Object) (Integer.valueOf(allMount[count]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(0, 1).equals("1") ? ((Object) (Float.valueOf(allMax[count]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(1, 2).equals("1") ? ((Object) (Integer.valueOf(man[count]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(1, 2).equals("1") ? ((Object) (Integer.valueOf(manMount[count]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(1, 2).equals("1") ? ((Object) (Float.valueOf(manMax[count]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(2).equals("1") ? ((Object) (Integer.valueOf(woman[count]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(2).equals("1") ? ((Object) (Integer.valueOf(womanMount[count]))) : " ").toString(), font2, 1, TableLineColor));
				table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder()).append(gendarStr.substring(2).equals("1") ? ((Object) (Float.valueOf(womanMax[count]))) : " ").toString(), font2, 1, TableLineColor));
				count++;
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
