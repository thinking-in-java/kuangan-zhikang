// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseConfig.java

package com.yihe.common.config;

import java.io.*;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yihe.common.utils.Util;

public abstract class BaseConfig
{

	protected static String CONFIG_FILE_PATH;
	private static long expireTime = 60000L;
	private long lastCheckTime;
	private Properties m_props;
	private long lastModified;
	private String filepath;
	private static final Logger log = LoggerFactory.getLogger(BaseConfig.class);
	

	public BaseConfig()
	{
		filepath = (new StringBuilder(String.valueOf(CONFIG_FILE_PATH))).append(getPorpFileName()).toString();
		lastCheckTime = System.currentTimeMillis();
		load();
	}

	public BaseConfig(String path)
	{
		filepath = path;
		lastCheckTime = System.currentTimeMillis();
		load();
	}

	public void load()
	{
		InputStream is = null;
		try
		{
			File file = new File((new StringBuilder(String.valueOf(CONFIG_FILE_PATH))).append(getPorpFileName()).toString());
			lastModified = file.lastModified();
			m_props = null;
			Properties props = new Properties();
			is = new FileInputStream(file);
			props.load(is);
			System.out.println((new StringBuilder("!Load properties! ")).append(CONFIG_FILE_PATH).append(getPorpFileName()).toString());
			m_props = props;
		}
		catch (Exception ex)
		{
			m_props = new Properties();
			ex.printStackTrace();
		}
		if (is != null)
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		Exception exception;
		if (is != null)
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		if (is != null)
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}

	public void store()
	{
		FileOutputStream out = null;
		if (m_props.size() <= 0)
		out = null;
		try
		{
			File f = new File(filepath);
			File parent = new File(f.getParent());
			try
			{
				parent.mkdirs();
			}
			catch (Exception ex)
			{
				log.error((new StringBuilder("mkdir ")).append(parent.getPath()).append(" error").toString());
			}
			if (!f.exists())
				f.createNewFile();
			out = new FileOutputStream(f);
			m_props.store(out, getPorpFileName());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println((new StringBuilder("Store to ")).append(filepath).append(" Error").toString());
		}
		if (out != null)
			try
			{
				out.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		Exception exception;
		if (out != null)
			try
			{
				out.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		if (out != null)
			try
			{
				out.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}

	public abstract String getPorpFileName();

	public String getProperty(String key, String defVal)
	{
		refresh();
		String result = m_props.getProperty(key, defVal);
		return result;
	}

	public int getProperty(String key, int defVal)
	{
		refresh();
		int res = defVal;
		try
		{
			res = Integer.parseInt(m_props.getProperty(key, ""));
		}
		catch (Exception exception) { }
		return res;
	}

	public String getProperty(String key)
	{
		return getProperty(key, "");
	}

	public String getProperty1(String value)
	{
		return getProperty("", value);
	}

	public Properties getProperties()
	{
		return m_props;
	}

	public void setProperty(String key, String value)
	{
		if (key != null && value != null)
			m_props.setProperty(key, value);
	}

	public boolean refresh()
	{
		boolean isupload = false;
		long t1 = System.currentTimeMillis();
		if (t1 - lastCheckTime > expireTime)
		{
			File f = new File(filepath);
			if (f.lastModified() != lastModified)
			{
				load();
				isupload = true;
			}
			lastCheckTime = System.currentTimeMillis();
		}
		return isupload;
	}

	static 
	{
		CONFIG_FILE_PATH = (new StringBuilder(String.valueOf(Util.getWebinfPath()))).append("classes").append(File.separatorChar).append("conf").append(File.separatorChar).toString();
	}
}
