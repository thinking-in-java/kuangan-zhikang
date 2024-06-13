// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PDFUtil.java

package com.yihe.common.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.Color;
import java.io.*;
import java.net.MalformedURLException;

// Referenced classes of package cn.well.waf.util:
//			FileUtil

public class PDFUtil
{

	public PDFUtil()
	{
	}

	public static BaseFont getBaseFont(Document document, String fileName, String url)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try
		{
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			FileUtil.NewFile(url, "/");
			String filename = fileName;
			PdfWriter.getInstance(document, new FileOutputStream((new StringBuilder(String.valueOf(url))).append("/").append(filename).toString()));
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (DocumentException e1)
		{
			e1.printStackTrace();
		}
		BaseFont bfChinese = null;
		try
		{
			bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return bfChinese;
	}

	public static Paragraph getParagraph(String str, Font font)
	{
		Paragraph paragraph = new Paragraph(str, font);
		return paragraph;
	}

	public static Cell getEmptyCell(int col, int row, int border)
		throws BadElementException
	{
		Cell cell = new Cell();
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(border);
		return cell;
	}

	public static Cell getEmptyColorCell(int col, int row, int border, int border2, int border3, int border4, int border5, Color color, 
			Color color2, Color color3, Color color4)
		throws BadElementException
	{
		Cell cell = new Cell();
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(border);
		cell.setBorderWidthTop(border2);
		cell.setBorderWidthBottom(border3);
		cell.setBorderWidthLeft(border4);
		cell.setBorderWidthRight(border5);
		cell.setBorderColorTop(color);
		cell.setBorderColorBottom(color2);
		cell.setBorderColorLeft(color3);
		cell.setBorderColorRight(color4);
		return cell;
	}

	public static Cell getColorCell(Paragraph pragraph, int col, int row, float border1, float border2, float border3, float border4, Color color, 
			Color bgcolor)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.add(Chunk.NEWLINE);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(1);
		cell.setHorizontalAlignment(1);
		cell.setBackgroundColor(bgcolor);
		cell.setBorderWidthTop(border1);
		cell.setBorderWidthBottom(border2);
		cell.setBorderWidthLeft(border3);
		cell.setBorderWidthRight(border4);
		cell.setBorderColorTop(color);
		cell.setBorderColorBottom(color);
		cell.setBorderColorLeft(color);
		cell.setBorderColorRight(color);
		return cell;
	}

	public static Cell getColorCellA(Paragraph pragraph, int col, int row, float border1, float border2, float border3, float border4, Color color, 
			Color bgcolor)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setUseAscender(true);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(1);
		cell.setHorizontalAlignment(1);
		cell.setVerticalAlignment(5);
		cell.setBackgroundColor(bgcolor);
		cell.setBorderWidthTop(border1);
		cell.setBorderWidthBottom(border2);
		cell.setBorderWidthLeft(border3);
		cell.setBorderWidthRight(border4);
		cell.setBorderColorTop(color);
		cell.setBorderColorBottom(color);
		cell.setBorderColorLeft(color);
		cell.setBorderColorRight(color);
		return cell;
	}

	public static Cell getColorCellB(Paragraph pragraph, int col, int row, float border1, float border2, float border3, float border4, Color color, 
			Color bgcolor)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setHorizontalAlignment(1);
		cell.setVerticalAlignment(5);
		cell.setBackgroundColor(bgcolor);
		cell.setBorderWidthTop(border1);
		cell.setBorderWidthBottom(border2);
		cell.setBorderWidthLeft(border3);
		cell.setBorderWidthRight(border4);
		cell.setBorderColorTop(color);
		cell.setBorderColorBottom(color);
		cell.setBorderColorLeft(color);
		cell.setBorderColorRight(color);
		return cell;
	}

	public static Cell getColorPicCell(Image img, int col, int row, float border1, float border2, float border3, float border4, Color color, 
			Color bgcolor)
		throws BadElementException
	{
		Cell cell = new Cell(img);
		cell.setVerticalAlignment(5);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBackgroundColor(bgcolor);
		cell.setBorderWidthTop(border1);
		cell.setBorderWidthBottom(border2);
		cell.setBorderWidthLeft(border3);
		cell.setBorderWidthRight(border4);
		cell.setBorderColorTop(color);
		cell.setBorderColorBottom(color);
		cell.setBorderColorLeft(color);
		cell.setBorderColorRight(color);
		return cell;
	}

	public static Cell getColorPicCellB(Image img, int col, int row, float border1, float border2, float border3, float border4, Color color, 
			Color bgcolor)
		throws BadElementException
	{
		Cell cell = new Cell(img);
		cell.setVerticalAlignment(5);
		cell.setHorizontalAlignment(1);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBackgroundColor(bgcolor);
		cell.setBorderWidthTop(border1);
		cell.setBorderWidthBottom(border2);
		cell.setBorderWidthLeft(border3);
		cell.setBorderWidthRight(border4);
		cell.setBorderColorTop(color);
		cell.setBorderColorBottom(color);
		cell.setBorderColorLeft(color);
		cell.setBorderColorRight(color);
		return cell;
	}

	public static Cell getColorPicCellC(Image img, int col, int row, float border1, float border2, float border3, float border4, Color color, 
			Color bgcolor)
		throws BadElementException
	{
		Cell cell = new Cell(img);
		cell.setHorizontalAlignment(1);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBackgroundColor(bgcolor);
		cell.setBorderWidthTop(border1);
		cell.setBorderWidthBottom(border2);
		cell.setBorderWidthLeft(border3);
		cell.setBorderWidthRight(border4);
		cell.setBorderColorTop(color);
		cell.setBorderColorBottom(color);
		cell.setBorderColorLeft(color);
		cell.setBorderColorRight(color);
		return cell;
	}

	public static Cell getNoChunkColorCell(Paragraph pragraph, int col, int row, float border, Color color1, Color color2, Color color3, Color color4, 
			Color bgcolor)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setHorizontalAlignment(1);
		cell.setBackgroundColor(bgcolor);
		cell.setBorderWidthTop(border);
		cell.setBorderWidthBottom(border);
		cell.setBorderWidthLeft(border);
		cell.setBorderWidthRight(border);
		cell.setBorderColorTop(color1);
		cell.setBorderColorBottom(color2);
		cell.setBorderColorLeft(color3);
		cell.setBorderColorRight(color4);
		return cell;
	}

	public static Cell getNoChunkColorCellA(Paragraph pragraph, int col, int row, float border, Color color1, Color color2, Color color3, Color color4, 
			Color bgcolor)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBackgroundColor(bgcolor);
		cell.setBorderWidthTop(border);
		cell.setBorderWidthBottom(border);
		cell.setBorderWidthLeft(border);
		cell.setBorderWidthRight(border);
		cell.setBorderColorTop(color1);
		cell.setBorderColorBottom(color2);
		cell.setBorderColorLeft(color3);
		cell.setBorderColorRight(color4);
		return cell;
	}

	public static Cell getNoColorCell(Paragraph pragraph, int col, int row, int border, int border2, int border3, int border4, int border5)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setHorizontalAlignment(1);
		cell.setBorder(border);
		cell.setBorderWidthTop(border2);
		cell.setBorderWidthBottom(border3);
		cell.setBorderWidthLeft(border4);
		cell.setBorderWidthRight(border5);
		return cell;
	}

	public static Cell getBorderCell(Paragraph pragraph, int col, int row, int border)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setHorizontalAlignment(1);
		cell.setBorder(border);
		return cell;
	}

	public static Cell getNoBorderCell(Paragraph pragraph, int col, int row, int border, int flag)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		if (flag == 1)
			cell.setBorder(border);
		return cell;
	}

	public static Cell getNoBorderCellB(Paragraph pragraph, int col, int row, int border, int flag)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setHorizontalAlignment(1);
		if (flag == 1)
			cell.setBorder(border);
		return cell;
	}

	public static Cell getNoBorderCellA(Paragraph pragraph, int col, int row, int border, int flag)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setUseAscender(true);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setVerticalAlignment(5);
		if (flag == 1)
			cell.setBorder(border);
		return cell;
	}

	public static Cell getChunkCell(int col, int row, int border, Color color)
		throws BadElementException
	{
		Cell cell = new Cell();
		cell.add(Chunk.NEWLINE);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBackgroundColor(color);
		cell.setBorder(border);
		return cell;
	}

	public static Cell getChunkCellOne(int col, int row, int border, Color color, Color colorTop)
		throws BadElementException
	{
		Cell cell = new Cell(Chunk.NEWLINE);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorderWidthTop(0.0F);
		cell.setBorderWidthBottom(0.5F);
		cell.setBorderWidthLeft(0.5F);
		cell.setBorderWidthRight(0.0F);
		cell.setBackgroundColor(colorTop);
		cell.setBorderColorTop(color);
		cell.setBorderColorBottom(color);
		cell.setBorderColorLeft(color);
		cell.setBorderColorRight(color);
		return cell;
	}

	public static Cell getChunkCellTwo(int col, int row, int border, Color color, Color colorTop)
		throws BadElementException
	{
		Cell cell = new Cell(Chunk.NEWLINE);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorderColorTop(colorTop);
		cell.setBackgroundColor(color);
		cell.setBorder(border);
		return cell;
	}

	public static Cell getParChunkNoBorderCell(Paragraph pragraph, int col, int row, Color color, int border, int flag)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.add(Chunk.NEWLINE);
		cell.setHorizontalAlignment(1);
		cell.setVerticalAlignment(5);
		cell.setColspan(col);
		cell.setRowspan(row);
		if (flag == 1)
			cell.setBorder(border);
		cell.setBackgroundColor(color);
		return cell;
	}

	public static Cell getParChunkNoBorderCellA(Paragraph pragraphOne, Paragraph pragraphTwo, int col, int row, Color color, int border, int flag)
		throws BadElementException
	{
		Cell cell = new Cell();
		cell.add(pragraphOne);
		cell.add(pragraphTwo);
		cell.add(Chunk.NEWLINE);
		cell.setVerticalAlignment(5);
		cell.setColspan(col);
		cell.setRowspan(row);
		if (flag == 1)
			cell.setBorder(border);
		cell.setBackgroundColor(color);
		return cell;
	}

	public static Cell getParChunkNoBorderCellB(Paragraph pragraph, int col, int row, int border, Color color)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(border);
		cell.setHorizontalAlignment(1);
		cell.setVerticalAlignment(5);
		cell.setBackgroundColor(color);
		return cell;
	}

	public static Cell getParChunkNoBorderCellB(Paragraph pragraphOne, Paragraph pragraphTwo, Font f, int col, int row, int border, Color color)
		throws BadElementException
	{
		Cell cell = new Cell();
		cell.add(pragraphOne);
		cell.add(pragraphTwo);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(border);
		cell.setHorizontalAlignment(1);
		cell.setVerticalAlignment(5);
		cell.setBackgroundColor(color);
		return cell;
	}

	public static Cell getParChunkNoBorderCellC(Paragraph pragraph, int col, int row, int border, Color color)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(border);
		cell.setHorizontalAlignment(1);
		cell.setVerticalAlignment(4);
		cell.setBackgroundColor(color);
		return cell;
	}

	public static Cell getParChunkCell(Paragraph pragraph, int col, int row, int border, Color color, int flag)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.add(Chunk.NEWLINE);
		if (flag == 1)
			cell.setHorizontalAlignment(1);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(border);
		cell.setBackgroundColor(color);
		return cell;
	}

	public static Cell getParChunkCellNc(Paragraph pragraph, int col, int row, int border, Color color, int flag, int enmtyline)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		if (enmtyline == 0)
			cell.add(Chunk.NEWLINE);
		if (flag == 1)
			cell.setHorizontalAlignment(1);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(border);
		cell.setBackgroundColor(color);
		return cell;
	}

	public static Cell getTwoParChunkCell(Paragraph pragraph, Paragraph pragraph2, int col, int row, int border, Color color)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.add(pragraph2);
		cell.setHorizontalAlignment(1);
		cell.add(Chunk.NEWLINE);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBackgroundColor(color);
		return cell;
	}

	public static Cell getTwoParChunkCellB(Paragraph pragraph, Paragraph pragraph2, int col, int row, int border, Color color, int flag)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.add(pragraph2);
		if (flag == 1)
			cell.setHorizontalAlignment(1);
		cell.add(Chunk.NEWLINE);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(border);
		cell.setBackgroundColor(color);
		return cell;
	}

	public static Cell getPicCell(Image img, int col, int row, int border)
		throws BadElementException
	{
		Cell cell = new Cell(img);
		cell.setColspan(col);
		cell.setVerticalAlignment(5);
		cell.setRowspan(row);
		cell.setBorder(border);
		return cell;
	}

	public static Image getImage(String path, int width, int heigth)
	{
		Image jpg = null;
		try
		{
			jpg = Image.getInstance(path);
			jpg.scaleToFit(width, heigth);
		}
		catch (Exception exception) { }
		return jpg;
	}

	public static Image getImageOne(String path, float width, float heigth)
		throws BadElementException, MalformedURLException, IOException
	{
		Image jpg = Image.getInstance(path);
		jpg.scaleAbsolute(width, heigth);
		return jpg;
	}

	public static Cell getNoBorderCellB(Paragraph pragraph, int col, int row, int border)
		throws BadElementException
	{
		Cell cell = new Cell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setBorder(border);
		return cell;
	}

	public static PdfPCell getComPdfPCell(String str, Font font, int col, int align)
	{
		PdfPCell pdfcell = new PdfPCell(new Phrase(str, font));
		pdfcell.setColspan(col);
		pdfcell.setHorizontalAlignment(align);
		return pdfcell;
	}

	public static PdfPCell getComPdfPCell(String str, Font font, int col, int align, int border)
	{
		PdfPCell pdfcell = new PdfPCell(new Phrase(str, font));
		pdfcell.setColspan(col);
		pdfcell.setBorder(border);
		pdfcell.setHorizontalAlignment(align);
		return pdfcell;
	}

	public static PdfPCell getPdfPCell(String str, Font font, int col, int align, int border, float leftborder, float bottomborder, float rightborder, 
			float topborder)
	{
		PdfPCell pdfcell = new PdfPCell(new Phrase(str, font));
		pdfcell.setColspan(col);
		pdfcell.setBorder(border);
		pdfcell.setBorderWidthBottom(bottomborder);
		pdfcell.setBorderWidthLeft(leftborder);
		pdfcell.setBorderWidthRight(rightborder);
		pdfcell.setBorderWidthTop(topborder);
		pdfcell.setHorizontalAlignment(align);
		return pdfcell;
	}

	public static PdfPCell getPicPdfPCell(Image image, int col, int align, int border, float leftborder, float bottomborder, float rightborder, float topborder)
	{
		PdfPCell pdfcell = new PdfPCell(new Phrase(new Chunk(image, 0.0F, 0.0F)));
		pdfcell.setColspan(col);
		pdfcell.setBorder(border);
		pdfcell.setBorderWidthBottom(bottomborder);
		pdfcell.setBorderWidthLeft(leftborder);
		pdfcell.setBorderWidthRight(rightborder);
		pdfcell.setBorderWidthTop(topborder);
		pdfcell.setHorizontalAlignment(align);
		pdfcell.setVerticalAlignment(5);
		return pdfcell;
	}

	public static PdfPCell getPicPdfPCellA(Image image, int col, int align, int border, float leftborder, float bottomborder, float rightborder, float topborder)
	{
		PdfPCell pdfcell = new PdfPCell(new Phrase(new Chunk(image, 0.0F, 0.0F)));
		pdfcell.setColspan(col);
		pdfcell.setBorder(border);
		pdfcell.setBorderWidthBottom(bottomborder);
		pdfcell.setBorderWidthLeft(leftborder);
		pdfcell.setBorderWidthRight(rightborder);
		pdfcell.setBorderWidthTop(topborder);
		pdfcell.setHorizontalAlignment(align);
		pdfcell.setVerticalAlignment(5);
		return pdfcell;
	}

	public static PdfPCell getCorderPdfPCell(String str, Font font, int col, int align, int border, int leftborder, int bottomborder, int rightborder, 
			int topborder, Color leftcolor, Color bottomcolor, Color rightcolor, Color topcolor, Color groundcorlor)
	{
		PdfPCell pdfcell = new PdfPCell(new Phrase(str, font));
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
		return pdfcell;
	}

	public static PdfPCell getCoverPdfPCell(String str, Font font, int col, int align, boolean vlign, int valign, int border, float leftborder, 
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
			pdfcell.setVerticalAlignment(6);
		return pdfcell;
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

	public static PdfPCell getCorderPdfPCellA(float leading, String str, Font font, int col, int align, boolean vlign, int valign, int border, 
			float leftborder, float bottomborder, float rightborder, float topborder, float heigth, Color leftcolor, Color bottomcolor, 
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

	public static PdfPCell getCorderPdfPCellB(String str, Font font, int col, int align, boolean vlign, int valign, int border, float leftborder, 
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

	public static PdfPCell getCorderPdfPCellB(float leading, String str, Font font, int col, int align, boolean vlign, int valign, int border, 
			float leftborder, float bottomborder, float rightborder, float topborder, float heigth, float leading1, Color leftcolor, 
			Color bottomcolor, Color rightcolor, Color topcolor, Color groundcorlor)
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

	public static PdfPCell getCorderPdfPCellC(Phrase pg, int col, int align, boolean vlign, int valign, int border, float leftborder, float bottomborder, 
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

	public static PdfPCell getImageCell(Image image, int col, int align, int oborderrder, float leftborder, float bottomborder, float rightborder, float topborder, 
			float heigth, Color leftcolor, Color bottomcolor, Color rightcolor, Color topcolor, Color groundcorlor)
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
		return pdfcell;
	}

	public static PdfPCell getImageCell(Image image, int align, float heigth)
	{
		return getImageCell(image, 1, align, 0, 0.0F, 0.0F, 0.0F, 0.0F, heigth, Color.white, Color.white, Color.white, Color.white, Color.white);
	}

	public static Image getImage(String path, float width, float heigth)
	{
		Image jpg = null;
		try
		{
			jpg = Image.getInstance(path);
			jpg.scaleAbsolute(width, heigth);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return jpg;
	}

	public static PdfPCell getStringCell(String str, Font font, int col, int align, int border, float leftborder, float bottomborder, float rightborder, 
			float topborder, float leading, Color leftcolor, Color bottomcolor, Color rightcolor, Color topcolor, Color groundcorlor)
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
		return pdfcell;
	}

	public static PdfPCell getStringCell(String str, Font font, int align)
	{
		return getStringCell(str, font, 1, align, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Color.white, Color.white, Color.white, Color.white, Color.white);
	}

	public static PdfPCell getStringPhraseCellB(String str, Font font, int align, float leading)
	{
		PdfPCell pdfcell = new PdfPCell();
		pdfcell.addElement(new Phrase(leading, str, font));
		pdfcell.setColspan(1);
		pdfcell.setBorder(0);
		pdfcell.setFixedHeight(0.0F);
		pdfcell.setBorderWidthBottom(0.0F);
		pdfcell.setBorderWidthLeft(0.0F);
		pdfcell.setBorderWidthRight(0.0F);
		pdfcell.setBorderWidthTop(0.0F);
		pdfcell.setHorizontalAlignment(align);
		return pdfcell;
	}

	public static PdfPCell getStringPhraseCellBHasBorder(String str, Font font, int align, Color color)
	{
		PdfPCell pdfcell = new PdfPCell(new Phrase(str, font));
		pdfcell.setFixedHeight(0.0F);
		pdfcell.setBorderColor(color);
		pdfcell.setHorizontalAlignment(align);
		pdfcell.setVerticalAlignment(5);
		return pdfcell;
	}

	public static PdfPCell getStringPhraseCell(String str, Font font, int align, float leading)
	{
		PdfPCell pdfcell = new PdfPCell();
		pdfcell.addElement(new Phrase(leading, str, font));
		pdfcell.setColspan(1);
		pdfcell.setBorder(0);
		pdfcell.setFixedHeight(0.0F);
		pdfcell.setBorderWidthBottom(0.0F);
		pdfcell.setBorderWidthLeft(0.0F);
		pdfcell.setBorderWidthRight(0.0F);
		pdfcell.setBorderWidthTop(0.0F);
		pdfcell.setBorderColorBottom(Color.white);
		pdfcell.setBorderColorLeft(Color.white);
		pdfcell.setBorderColorRight(Color.white);
		pdfcell.setBorderColorTop(Color.white);
		pdfcell.setBackgroundColor(Color.white);
		pdfcell.setHorizontalAlignment(align);
		return pdfcell;
	}
}
