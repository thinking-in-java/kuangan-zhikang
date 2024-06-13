// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RiskBase.java

package com.yihe.common.risk.base;


import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.yihe.common.utils.EvaluateUtil;
import com.yihe.common.utils.ITextUtil;
import com.yihe.common.utils.JstlFunction;
import com.yihe.common.utils.PDFUtil;

// Referenced classes of package cn.well.step.util.risk.base:
//			RiskBaseAttribute

public abstract class RiskBase extends RiskBaseAttribute
{

	private ByteArrayOutputStream out;

	public RiskBase()
	{
		out = null;
	}

	public RiskBase(String path, String fileName)
	{
		out = null;
		out = new ByteArrayOutputStream();
		if (path != null)
			ITextUtil.setFile(document, fileName, path);
	}

	public RiskBase(boolean isNewOutputStream)
	{
		out = null;
		if (isNewOutputStream)
			out = new ByteArrayOutputStream();
	}

	protected PdfPTable createTop(String title)
		throws BadElementException, MalformedURLException, IOException
	{
		PdfPTable pdftab = new PdfPTable(new float[] {
			350F, 300F
		});
		pdftab.getDefaultCell().setBorder(0);
		PdfPTable pdftabf = new PdfPTable(1);
		pdftabf.addCell(PDFUtil.getCorderPdfPCellA(" ", topFont, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 50F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		pdftabf.addCell(PDFUtil.getCorderPdfPCellA(title, topFont, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 50F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		pdftabf.addCell(PDFUtil.getCorderPdfPCellA(" ", topFont, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 10F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		pdftab.addCell(pdftabf);
		pdftab.addCell(PDFUtil.getCorderPdfPCellA(getLogo(), null, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 100F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		return pdftab;
	}

	/*public PdfPTable createRiskTop(String title, WellRegUserinfo wru, WellRiskresultImp wr)
		throws BadElementException, MalformedURLException, IOException
	{
		PdfPTable topPDF = new PdfPTable(new float[] {
			0.7F, 0.25F
		});
		topPDF.getDefaultCell().setBorder(0);
		String age = Integer.toString(wru.getAge());
		String gender = JstlFunction.getSexToString(wru.getGender());
		if (wr != null)
		{
			age = (new StringBuilder()).append(EvaluateUtil.getEvaluateAge(wr.getRiskTime(), wru.getBirthDate())).toString();
			gender = JstlFunction.getSexToString(wr.getGender());
		}
		PdfPTable topLeft = new PdfPTable(1);
		topLeft.getDefaultCell().setBorder(0);
		topLeft.addCell(PDFUtil.getCorderPdfPCellA(" ", topBoldFont, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 20F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		topLeft.addCell(PDFUtil.getCorderPdfPCellA(title, topBoldFont, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		topLeft.addCell(PDFUtil.getCorderPdfPCellA(" ", topBoldFont, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 10F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		PdfPTable topTable = new PdfPTable(4);
		topTable.getDefaultCell().setBorder(0);
		topTable.addCell(PDFUtil.getCorderPdfPCellA("姓名", topTextBoldFont, 1, 1, false, 0, 0, 0.1F, 0.1F, 0.1F, 0.1F, 20F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, TableBackgroundColor));
		topTable.addCell(PDFUtil.getCorderPdfPCellA("性别", topTextBoldFont, 1, 1, false, 0, 0, 0.0F, 0.1F, 0.1F, 0.1F, 20F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, TableBackgroundColor));
		topTable.addCell(PDFUtil.getCorderPdfPCellA("年龄", topTextBoldFont, 1, 1, false, 0, 0, 0.0F, 0.1F, 0.1F, 0.1F, 20F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, TableBackgroundColor));
		topTable.addCell(PDFUtil.getCorderPdfPCellA("编码", topTextBoldFont, 1, 1, false, 0, 0, 0.0F, 0.1F, 0.1F, 0.1F, 20F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, TableBackgroundColor));
		topTable.addCell(PDFUtil.getCorderPdfPCellA(JstlFunction.getUsername(wru.getName(), wru.getUserName()), topTextFont, 1, 1, false, 0, 0, 0.1F, 0.1F, 0.1F, 0.0F, 20F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, Color.WHITE));
		topTable.addCell(PDFUtil.getCorderPdfPCellA(gender, topTextFont, 1, 1, false, 0, 0, 0.0F, 0.1F, 0.1F, 0.0F, 20F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, Color.WHITE));
		topTable.addCell(PDFUtil.getCorderPdfPCellA(age, topTextFont, 1, 1, false, 0, 0, 0.0F, 0.1F, 0.1F, 0.0F, 20F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, Color.WHITE));
		topTable.addCell(PDFUtil.getCorderPdfPCellA(wru.getUserCode(), topTextFont, 1, 1, false, 0, 0, 0.0F, 0.1F, 0.1F, 0.0F, 20F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, Color.WHITE));
		topLeft.addCell(topTable);
		PdfPTable topRight = new PdfPTable(1);
		topRight.getDefaultCell().setBorder(0);
		topRight.addCell(PDFUtil.getCorderPdfPCellA(getLogo(), null, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 100F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		topPDF.addCell(topLeft);
		topPDF.addCell(topRight);
		return topPDF;
	}*/

	public boolean synthesisPdf(String topName, String footer, PdfPTable pdfPTableBody)
	{
		try
		{
			PdfPTable pdfPTable = new PdfPTable(1);
			pdfPTable.getDefaultCell().setBorder(0);
			pdfPTable.addCell(createTop(topName));
			pdfPTable.addCell(pdfPTableBody);
			document.add(pdfPTable);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void begin()
	{
		if (getFooter() != null && !"".equals(getFooter()))
		{
			PdfWriter footer;
			try
			{
				footer = PdfWriter.getInstance(document, out);
			}
			catch (DocumentException e)
			{
				e.printStackTrace();
			}
		}
		document.open();
	}

	public void end()
	{
		try
		{
			if (document != null)
				document.close();
		}
		catch (Exception exception) { }
	}

	protected abstract String getFooter();

	public ByteArrayOutputStream getStream()
	{
		return out;
	}

	public void addFooter()
	{
		if (getFooter() != null && !"".equals(getFooter()))
		{
			PdfPTable p = new PdfPTable(1);
			p.getDefaultCell().setBorder(0);
			for (int i = 0; i < 2; i++)
			{
				PdfPCell c = new PdfPCell(new Phrase("   ", headerFooterFont));
				c.setBorder(0);
				p.addCell(c);
			}

			PdfPCell c = new PdfPCell(new Phrase(getFooter(), headerFooterFont));
			c.setBorder(0);
			c.setHorizontalAlignment(2);
			p.addCell(c);
			try
			{
				document.add(p);
			}
			catch (DocumentException e)
			{
				e.printStackTrace();
			}
		}
	}
}
