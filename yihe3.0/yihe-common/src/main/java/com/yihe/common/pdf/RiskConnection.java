// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RiskConnection.java

package com.yihe.common.pdf;

import com.lowagie.text.BadElementException;
import com.lowagie.text.pdf.PdfPTable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public interface RiskConnection
{

	public abstract void begin();

	public abstract boolean createPDF();

	public abstract void end();

	public abstract void setParameter(Object aobj[]);

	public abstract ByteArrayOutputStream getStream();

	public abstract PdfPTable getPdfPTable()
		throws BadElementException, MalformedURLException, IOException;
}
