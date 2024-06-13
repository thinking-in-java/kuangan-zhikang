// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WebDownload.java

package com.yihe.common.pdf;

import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebDownload
{

	public WebDownload()
	{
	}

	public static void downloadFormFile(HttpServletRequest request, HttpServletResponse response, File file, String name)
	{
		InputStream fis;
		FileInputStream fileInputStream;
		OutputStream toClient;
		OutputStream outputStream;
		if (file == null || !file.exists() || file.isDirectory())
			return;
		fis = null;
		fileInputStream = null;
		toClient = null;
		outputStream = null;
		try
		{
			fileInputStream = new FileInputStream(file);
			fis = new BufferedInputStream(fileInputStream);
			byte buffer[] = new byte[fis.available()];
			fis.read(buffer);
			response.reset();
			request.setCharacterEncoding("GBK");
			response.addHeader("Content-Disposition", (new StringBuilder("attachment;filename=")).append(new String(name.getBytes("GBK"), "8859_1")).toString());
			response.addHeader("Content-Length", (new StringBuilder()).append(file.length()).toString());
			outputStream = response.getOutputStream();
			toClient = new BufferedOutputStream(outputStream);
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			outputStream.flush();
		}
		catch (Exception exception)
		{
			try
			{
				if (fileInputStream != null)
					fileInputStream.close();
				if (fis != null)
					fis.close();
				if (toClient != null)
					toClient.close();
				if (outputStream != null)
					outputStream.close();
			}
			catch (Exception exception2) {
				
			}
		}
		try
		{
			if (fileInputStream != null)
				fileInputStream.close();
			if (fis != null)
				fis.close();
			if (toClient != null)
				toClient.close();
			if (outputStream != null)
				outputStream.close();
		}
		catch (Exception exception3) {
			
		}
		try
		{
			if (fileInputStream != null)
				fileInputStream.close();
			if (fis != null)
				fis.close();
			if (toClient != null)
				toClient.close();
			if (outputStream != null)
				outputStream.close();
		}
		catch (Exception exception4) { }
	}

	public static String toUtf8String(String src)
	{
		byte b[] = src.getBytes();
		char c[] = new char[b.length];
		for (int i = 0; i < b.length; i++)
			c[i] = (char)(b[i] & 0xff);

		return new String(c);
	}

	public static void downloadFormFile(HttpServletRequest request, HttpServletResponse response, String name)
	{
		try
		{
			response.setHeader("content-Type", "application/pdf");
            // 下载文件的默认名称
            String fileName = new String(name.getBytes("GBK"), "ISO8859-1").toString();
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
