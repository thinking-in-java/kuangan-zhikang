// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MealHeadersAndWatermarks.java

package com.yihe.common.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import javax.servlet.http.HttpServletRequest;

public class MealHeadersAndWatermarks extends PdfPageEventHelper
{

	public Image headerImage;
	public PdfPTable table;
	public PdfGState gstate;
	public PdfTemplate tpl;
	public BaseFont helv;
	private HttpServletRequest request;

	public MealHeadersAndWatermarks(HttpServletRequest request)
	{
		this.request = request;
	}

	public PdfGState getGstate()
	{
		return gstate;
	}

	public void setGstate(PdfGState gstate)
	{
		this.gstate = gstate;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public void onOpenDocument(PdfWriter writer, Document document)
	{
		try
		{
			table = new PdfPTable(2);
			table.getDefaultCell().setBorderWidth(0.0F);
			table.getDefaultCell().setHorizontalAlignment(0);
			gstate = new PdfGState();
			gstate.setFillOpacity(0.3F);
			gstate.setStrokeOpacity(0.3F);
			tpl = writer.getDirectContent().createTemplate(100F, 100F);
			tpl.setBoundingBox(new Rectangle(-20F, -20F, 100F, 100F));
			helv = BaseFont.createFont("Helvetica", "Cp1252", false);
		}
		catch (Exception e)
		{
			throw new ExceptionConverter(e);
		}
	}

	public void onEndPage(PdfWriter writer, Document document)
	{
		PdfContentByte cb = writer.getDirectContent();
		cb.saveState();
		table.setTotalWidth(document.right() - document.left());
		table.writeSelectedRows(0, -1, document.left(), document.getPageSize().getHeight() - 50F, cb);
		cb.beginText();
		cb.setFontAndSize(helv, 12F);
		cb.endText();
		cb.saveState();
		cb.setColorStroke(Color.black);
		cb.setLineWidth(0.5F);
		cb.rectangle(20F, 20F, document.getPageSize().getWidth() - 40F, document.getPageSize().getHeight() - 40F);
		cb.stroke();
		cb.restoreState();
		if (writer.getPageNumber() >= 100)
		{
			cb.setGState(gstate);
			cb.setColorFill(Color.red);
			cb.beginText();
			cb.setFontAndSize(helv, 48F);
			cb.showTextAligned(1, (new StringBuilder("Watermark Opacity ")).append(writer.getPageNumber()).toString(), document.getPageSize().getWidth() / 2.0F, document.getPageSize().getHeight() / 2.0F, 45F);
			cb.endText();
			cb.restoreState();
		}
	}

	public void onCloseDocument(PdfWriter writer, Document document)
	{
		tpl.beginText();
		tpl.setFontAndSize(helv, 12F);
		tpl.setTextMatrix(0.0F, 0.0F);
		tpl.showText((new StringBuilder()).append(writer.getPageNumber() - 1).toString());
		tpl.endText();
	}
}
