// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FloatRigor.java

package com.yihe.common.utils;


public class FloatRigor
{

	public FloatRigor()
	{
	}

	public static float getFloNum(float value, int n)
	{
		int dd = 1;
		for (int i = 0; i < n; i++)
			dd *= 10;

		float tempnum = value * (float)dd;
		int tnum = Math.round(tempnum);
		return (float)tnum / (float)dd;
	}

	public static int getIntNum(float value)
	{
		return (int)(value + 0.5F);
	}

	public static float getFloatValue(String value, int n)
	{
		int dd = 1;
		float floatvalue = 0.0F;
		for (int i = 0; i < n; i++)
			dd *= 10;

		float tempnum = getFloatOk(value) * (float)dd;
		int tnum = Math.round(tempnum);
		floatvalue = (float)tnum / (float)dd;
		return floatvalue;
	}

	public static float getFloatOk(String value)
	{
		try {
			return Float.parseFloat(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 0.0F;
	}

	public static Double getDoubleOk(String value)
	{

		try {
			return Double.valueOf(Double.parseDouble(value));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Double.valueOf(0.0D);
	}
}
