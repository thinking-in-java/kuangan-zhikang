// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SystemConf.java

package com.yihe.common.config;

import com.yihe.common.config.BaseConfig;
import com.yihe.common.utils.ParamType;

public class SystemConf extends BaseConfig
{
	class InterceptionString
	{

		private String kestruts;
		private int beginEnd;
		private int begini;
		private int endi;
		final SystemConf this$0;

		private void init()
		{
			String kestrutss[] = kestruts.split("_");
			if (KeyStrut.BEGIN.toString().equals(kestrutss[0]))
				beginEnd = 1;
			else
			if (KeyStrut.END.toString().equals(kestrutss[0]))
				beginEnd = 2;
			begini = ParamType.getint(kestrutss[1]);
			if (begini == -1)
				begini = 0;
			endi = ParamType.getint(kestrutss[2]);
		}

		public String getValue(String value)
		{
			if (beginEnd == 0 || value == null || value.equals(null))
				return value;
			int valueLength = value.length();
			if (beginEnd == 1)
			{
				if (endi == -1)
					value = value.substring(begini);
				else
					value = value.substring(begini, endi);
			} else
			if (endi == -1)
				value = value.substring(valueLength - begini);
			else
				value = value.substring(valueLength - endi, valueLength - begini);
			return value;
		}

		public InterceptionString(String kestruts)
		{
			this$0 = SystemConf.this;
			beginEnd = 0;
			begini = 0;
			endi = 0;
			if (kestruts == null || kestruts.equals(""))
				kestruts = "BEGIN_-1_-1";
			this.kestruts = kestruts;
			init();
		}
	}

	public static final class KeyStrut 
	{

		public static final KeyStrut BEGIN;
		public static final KeyStrut END;
		private static final KeyStrut ENUM$VALUES[];

		public static KeyStrut[] values()
		{
			KeyStrut akeystrut[];
			int i;
			KeyStrut akeystrut1[];
			System.arraycopy(akeystrut = ENUM$VALUES, 0, akeystrut1 = new KeyStrut[i = akeystrut.length], 0, i);
			return akeystrut1;
		}

		

		static 
		{
			BEGIN = new KeyStrut("BEGIN", 0);
			END = new KeyStrut("END", 1);
			ENUM$VALUES = (new KeyStrut[] {
				BEGIN, END
			});
		}

		private KeyStrut(String s, int i)
		{
			super();
		}
	}


	private static SystemConf q_conf = null;
	private static InterceptionString interceptionString = null;

	public SystemConf()
	{
	}

	public String getPorpFileName()
	{
		return "system.properties";
	}

	public static SystemConf getInstance()
	{
		synchronized (SystemConf.class)
		{
			if (q_conf == null)
				q_conf = new SystemConf();
		}
		return q_conf;
	}

	public String getProperty(String key, boolean path)
	{
		String value = getProperty(key, "");
		if (path && value.lastIndexOf("/") == -1)
			value = (new StringBuilder(String.valueOf(value))).append("/").toString();
		return value;
	}

	public static String getInterceptionString(String value)
	{
		if (interceptionString == null)
			synchronized (SystemConf.class)
			{
				if (interceptionString == null)
					interceptionString = getInstance(). new InterceptionString(getInstance().getProperty("put_card"));
			}
		return interceptionString.getValue(value);
	}

}
