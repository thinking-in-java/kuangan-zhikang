// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITextUtil.java

package com.yihe.common.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.util.IO;
import org.springframework.core.io.ClassPathResource;

import java.awt.Color;
import java.io.*;
import java.net.URL;

public class ITextUtil
{

	public ITextUtil()
	{
	}

	public static void setFile(Document document, String fileName, String url)
	{
		try
		{
			FileUtil.creatFile(url, fileName);
			PdfWriter.getInstance(document, new FileOutputStream((new StringBuilder(String.valueOf(url))).append("/").append(fileName).toString()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static BaseFont getBaseFontInstance()
	{
		try {
			return BaseFont.createFont("fonts/MSYH.TTF", "Identity-H", false);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}

	public static PdfPCell getCorderPdfPCellA(String str, Font font, int col, int align, boolean vlign, int valign, int border, float leftborder, 
			float bottomborder, float rightborder, float topborder, float heigth, Color leftcolor, Color bottomcolor, Color rightcolor, 
			Color topcolor, Color groundcorlor)
	{
		PdfPCell pdfcell = new PdfPCell(new Phrase(str, font));
		pdfcell.setColspan(col);
		pdfcell.setBorder(border);
		pdfcell.setFixedHeight(heigth);
		pdfcell.setBorderWidthBottom(bottomborder);
		pdfcell.setBorderWidthLeft(leftborder);
		pdfcell.setBorderWidthRight(rightborder);
		pdfcell.setBorderWidthTop(topborder);
		pdfcell.setBorderColorBottom(bottomcolor);
		pdfcell.setBorderColorLeft(leftcolor);
		pdfcell.setBorderColorRight(rightcolor);
		pdfcell.setBorderColorTop(topcolor);
		pdfcell.setBackgroundColor(groundcorlor);
		pdfcell.setHorizontalAlignment(align);
		if (vlign)
			pdfcell.setVerticalAlignment(5);
		return pdfcell;
	}

	public static PdfPCell getCorderPdfPCellC(String str, Font font, int col, int align, boolean vlign, int valign, int border, float leftborder, 
			float bottomborder, float rightborder, float topborder, float heigth, float leading, Color leftcolor, Color bottomcolor, 
			Color rightcolor, Color topcolor, Color groundcorlor)
	{
		PdfPCell pdfcell = new PdfPCell();
		pdfcell.addElement(new Phrase(leading, str, font));
		pdfcell.setColspan(col);
		pdfcell.setBorder(border);
		pdfcell.setFixedHeight(heigth);
		pdfcell.setBorderWidthBottom(bottomborder);
		pdfcell.setBorderWidthLeft(leftborder);
		pdfcell.setBorderWidthRight(rightborder);
		pdfcell.setBorderWidthTop(topborder);
		pdfcell.setBorderColorBottom(bottomcolor);
		pdfcell.setBorderColorLeft(leftcolor);
		pdfcell.setBorderColorRight(rightcolor);
		pdfcell.setBorderColorTop(topcolor);
		pdfcell.setBackgroundColor(groundcorlor);
		pdfcell.setHorizontalAlignment(align);
		if (vlign)
			pdfcell.setVerticalAlignment(5);
		return pdfcell;
	}

	public static PdfPCell getCorderPdfPCellD(String str, Font font, int col, int align, boolean vlign, int valign, int border, float leftborder, 
			float bottomborder, float rightborder, float topborder, float leading, Color leftcolor, Color bottomcolor, Color rightcolor, 
			Color topcolor, Color groundcorlor)
	{
		PdfPCell pdfcell = new PdfPCell();
		pdfcell.addElement(new Phrase(leading, str, font));
		pdfcell.setColspan(col);
		pdfcell.setBorder(border);
		pdfcell.setBorderWidthBottom(bottomborder);
		pdfcell.setBorderWidthLeft(leftborder);
		pdfcell.setBorderWidthRight(rightborder);
		pdfcell.setBorderWidthTop(topborder);
		pdfcell.setBorderColorBottom(bottomcolor);
		pdfcell.setBorderColorLeft(leftcolor);
		pdfcell.setBorderColorRight(rightcolor);
		pdfcell.setBorderColorTop(topcolor);
		pdfcell.setBackgroundColor(groundcorlor);
		pdfcell.setHorizontalAlignment(align);
		if (vlign)
			pdfcell.setVerticalAlignment(5);
		return pdfcell;
	}

	public static PdfPCell getCorderPdfPCellA(Image image, Font font, int col, int align, boolean vlign, int valign, int oborderrder, float leftborder, 
			float bottomborder, float rightborder, float topborder, float heigth, Color leftcolor, Color bottomcolor, Color rightcolor, 
			Color topcolor, Color groundcorlor)
	{
		PdfPCell pdfcell = new PdfPCell(image);
		pdfcell.setColspan(col);
		pdfcell.setBorder(oborderrder);
		pdfcell.setFixedHeight(heigth);
		pdfcell.setBorderWidthBottom(bottomborder);
		pdfcell.setBorderWidthLeft(leftborder);
		pdfcell.setBorderWidthRight(rightborder);
		pdfcell.setBorderWidthTop(topborder);
		pdfcell.setBorderColorBottom(bottomcolor);
		pdfcell.setBorderColorLeft(leftcolor);
		pdfcell.setBorderColorRight(rightcolor);
		pdfcell.setBorderColorTop(topcolor);
		pdfcell.setBackgroundColor(groundcorlor);
		pdfcell.setHorizontalAlignment(align);
		if (vlign)
			pdfcell.setVerticalAlignment(5);
		return pdfcell;
	}

	public static PdfPCell getCorderPdfPCellC(Paragraph pg, int col, int align, boolean vlign, int valign, int border, float leftborder, float bottomborder, 
			float rightborder, float topborder, float heigth, float leading, Color leftcolor, Color bottomcolor, Color rightcolor, 
			Color topcolor, Color groundcorlor)
	{
		PdfPCell pdfcell = new PdfPCell();
		pdfcell.addElement(pg);
		pdfcell.setColspan(col);
		pdfcell.setBorder(border);
		pdfcell.setFixedHeight(heigth);
		pdfcell.setBorderWidthBottom(bottomborder);
		pdfcell.setBorderWidthLeft(leftborder);
		pdfcell.setBorderWidthRight(rightborder);
		pdfcell.setBorderWidthTop(topborder);
		pdfcell.setBorderColorBottom(bottomcolor);
		pdfcell.setBorderColorLeft(leftcolor);
		pdfcell.setBorderColorRight(rightcolor);
		pdfcell.setBorderColorTop(topcolor);
		pdfcell.setBackgroundColor(groundcorlor);
		pdfcell.setHorizontalAlignment(align);
		if (vlign)
			pdfcell.setVerticalAlignment(5);
		return pdfcell;
	}

	public static Image getImageScaleToFit(String path, int width, int heigth)
	{
		Image jpg = null;
		try
		{
			jpg = Image.getInstance(path);
			jpg.scaleToFit(width, heigth);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return jpg;
	}

	public static Image getImageScaleAbsolute(String path, float width, float heigth)
	{
		Image jpg = null;
		try
		{

		//	BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path),"GBK"));
//			File file = new File(path);
//			if (!file.exists())
//				file.getParentFile().mkdirs();
			//URL path2 =      this.getClass().getResource(path);
			//转成String路径
			//String imagePath = path2.toString();

			jpg = Image.getInstance(path);
			jpg.scaleAbsolute(width, heigth);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return jpg;
	}

	public static Image getImageScaleAbsolute(byte bytes[], float width, float heigth)
	{
		Image jpg = null;
		try
		{
			jpg = Image.getInstance(bytes);
			jpg.scaleAbsolute(width, heigth);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return jpg;
	}
}
