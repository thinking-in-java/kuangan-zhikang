// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReportUtil.java

package com.yihe.common.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.yihe.common.risk.base.RiskBase;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;

public class ReportUtil extends RiskBase
{

	public ReportUtil()
	{
	}

	protected String getFooter()
	{
		return null;
	}

	protected PdfPTable addRemard(float width_table_Page2[], Font font3, String str2)
	{
		PdfPTable table_Page2 = new PdfPTable(width_table_Page2);
		table_Page2.getDefaultCell().setBorder(0);
		table_Page2.addCell("");
		table_Page2.addCell(PDFUtil.getCorderPdfPCellA(str2, font3, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		table_Page2.addCell(" ");
		return table_Page2;
	}

	protected void addBlankCell(PdfPTable table_Page5, int no)
	{
		for (int i = 0; i < no; i++);
	}

	protected PdfPTable getTitleContent(float width_table_Page1[], Image image, String str1, Font font1)
	{
		PdfPTable table_Page1 = new PdfPTable(width_table_Page1);
		table_Page1.getDefaultCell().setBorder(0);
		table_Page1.addCell("");
		PdfPCell pdfCell1 = PDFUtil.getCorderPdfPCellA(str1, font1, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
		pdfCell1.setVerticalAlignment(6);
		table_Page1.addCell(pdfCell1);
		table_Page1.addCell(PDFUtil.getPicPdfPCell(image, 1, 1, 0, 0.0F, 0.0F, 0.0F, 0.0F));
		return table_Page1;
	}

	protected PdfPTable getContent(float width_table_Page2[], String str2, Font font2)
	{
		PdfPTable table_Page2 = new PdfPTable(width_table_Page2);
		table_Page2.getDefaultCell().setBorder(0);
		table_Page2.addCell("");
		table_Page2.addCell(PDFUtil.getCorderPdfPCellA(str2, font2, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		table_Page2.addCell("");
		return table_Page2;
	}

	protected PdfPTable getJfreeChar(String path, float width_table_Page2[])
		throws BadElementException, MalformedURLException, IOException
	{
		PdfPTable table_img1 = new PdfPTable(width_table_Page2);
		table_img1.addCell("");
		Image image1 = Image.getInstance(path);
		image1.scaleToFit(400F, 200F);
		PdfPCell cell = PDFUtil.getPicPdfPCell(image1, 1, 1, 0, 0.1F, 0.1F, 0.1F, 0.1F);
		cell.setBorderColor(TableLineColor);
		cell.setBorderColorBottom(TableLineColor);
		cell.setBorderColorRight(TableLineColor);
		cell.setBorderColorLeft(TableLineColor);
		cell.setBorderColorTop(TableLineColor);
		table_img1.addCell(cell);
		table_img1.addCell("");
		return table_img1;
	}

	protected PdfPCell createInner(String str, String str1, String str2, Font font3, Color color, Color color1, float left, 
			float bottom, float right, float top)
	{
		PdfPCell pdfPCell = PDFUtil.getCorderPdfPCellA("", null, 2, 1, true, 0, 0, left, bottom, right, top, 0.0F, color1, color1, color1, color1, color);
		pdfPCell.setPadding(0.0F);
		PdfPTable table_inner = new PdfPTable(2);
		table_inner.getDefaultCell().setBorder(0);
		table_inner.setWidthPercentage(100F);
		PdfPCell pdfCellInner = PDFUtil.getCorderPdfPCellA(str, font3, 2, 1, true, 0, 0, 0.0F, 0.1F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str1, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str2, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfPCell.addElement(table_inner);
		return pdfPCell;
	}

	protected PdfPCell createInner1(String str, String str1, String str2, Font font3, Color color, Color color1, float left, 
			float bottom, float right, float top)
	{
		PdfPCell pdfPCell = PDFUtil.getCorderPdfPCellA("", null, 1, 1, true, 0, 0, left, bottom, right, top, 0.0F, color1, color1, color1, color1, color);
		pdfPCell.setPadding(0.0F);
		PdfPTable table_inner = new PdfPTable(2);
		table_inner.getDefaultCell().setBorder(0);
		table_inner.setWidthPercentage(100F);
		PdfPCell pdfCellInner = PDFUtil.getCorderPdfPCellA(str, font3, 2, 1, true, 0, 0, 0.0F, 0.1F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfPCell.addElement(table_inner);
		return pdfPCell;
	}

	protected PdfPCell createInner2(String str, String str1, String str2, Font font3, Color color, Color color1, float left, 
			float bottom, float right, float top)
	{
		PdfPCell pdfPCell = PDFUtil.getCorderPdfPCellA("", null, 1, 1, true, 0, 0, left, bottom, right, top, 0.0F, color1, color1, color1, color1, color);
		pdfPCell.setPadding(0.0F);
		PdfPTable table_inner = new PdfPTable(2);
		table_inner.getDefaultCell().setBorder(0);
		table_inner.setWidthPercentage(100F);
		PdfPCell pdfCellInner = PDFUtil.getCorderPdfPCellA(str, font3, 2, 1, true, 0, 0, 0.0F, 0.1F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str1, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str2, font3, 2, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfPCell.addElement(table_inner);
		return pdfPCell;
	}

	protected PdfPCell createInner(String str, String str1, String str2, String str3, String str4, Font font3, Color color, 
			Color color1, float left, float bottom, float right, float top)
	{
		PdfPCell pdfPCell = PDFUtil.getCorderPdfPCellA("", null, 4, 1, true, 0, 0, left, bottom, right, top, 0.0F, color1, color1, color1, color1, color);
		pdfPCell.setPadding(0.0F);
		PdfPTable table_inner = new PdfPTable(4);
		table_inner.getDefaultCell().setBorder(0);
		table_inner.setWidthPercentage(100F);
		PdfPCell pdfCellInner = PDFUtil.getCorderPdfPCellA(str, font3, 4, 1, true, 0, 0, 0.0F, 0.1F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str1, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str2, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str3, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str4, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfPCell.addElement(table_inner);
		return pdfPCell;
	}

	protected PdfPCell createInner(String str, String str1, String str2, String str3, Font font3, Color color, Color color1, 
			float left, float bottom, float right, float top)
	{
		PdfPCell pdfPCell = PDFUtil.getCorderPdfPCellA("", null, 3, 1, true, 0, 0, left, bottom, right, top, 0.0F, color1, color1, color1, color1, color);
		pdfPCell.setPadding(0.0F);
		PdfPTable table_inner = new PdfPTable(3);
		table_inner.getDefaultCell().setBorder(0);
		table_inner.setWidthPercentage(100F);
		PdfPCell pdfCellInner = PDFUtil.getCorderPdfPCellA(str, font3, 3, 1, true, 0, 0, 0.0F, 0.1F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str1, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str2, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str3, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfPCell.addElement(table_inner);
		return pdfPCell;
	}

	protected PdfPCell createInner(String str, String str1, String str2, String str3, String str4, String str5, String str6, 
			Font font3, Color color, Color color1, float left, float bottom, float right, float top)
	{
		PdfPCell pdfPCell = PDFUtil.getCorderPdfPCellA("", null, 4, 1, true, 0, 0, left, bottom, right, top, 0.0F, color1, color1, color1, color1, color);
		pdfPCell.setPadding(0.0F);
		PdfPTable table_inner = new PdfPTable(4);
		table_inner.getDefaultCell().setBorder(0);
		table_inner.setWidthPercentage(100F);
		PdfPCell pdfCellInner = PDFUtil.getCorderPdfPCellA(str, font3, 4, 1, true, 0, 0, 0.0F, 0.1F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str1, font3, 2, 1, true, 0, 0, 0.0F, 0.1F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str2, font3, 2, 1, true, 0, 0, 0.0F, 0.1F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str3, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str4, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str5, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfCellInner = PDFUtil.getCorderPdfPCellA(str6, font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, color1, color1, color1, color1, color);
		table_inner.addCell(pdfCellInner);
		pdfPCell.addElement(table_inner);
		return pdfPCell;
	}
//
	protected PdfPTable addFooter(String str, Font footFont, int no)
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		for (int i = 0; i < no; i++)
			pdfPTable.addCell(" ");

		pdfPTable.addCell(PDFUtil.getCorderPdfPCellA(str, footFont, 1, 2, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Color.white, Color.white, Color.white, Color.white, Color.white));
		return pdfPTable;
	}

	public void creatImage(String as[], int i)
	{
	}
}
