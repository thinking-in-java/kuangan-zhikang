// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThreeDes.java

package com.yihe.common.utils;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.sun.crypto.provider.SunJCE;

public class ThreeDes
{

	private static String strDefaultKey = "WELLWOO";
	private static Cipher encryptCipher = null;
	private static Cipher decryptCipher = null;

	public static String byteArr2HexStr(byte arrB[])
		throws Exception
	{
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++)
		{
			int intTmp;
			for (intTmp = arrB[i]; intTmp < 0; intTmp += 256);
			if (intTmp < 16)
				sb.append("0");
			sb.append(Integer.toString(intTmp, 16));
		}

		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn)
		throws Exception
	{
		byte arrB[] = strIn.getBytes();
		int iLen = arrB.length;
		byte arrOut[] = new byte[iLen / 2];
		for (int i = 0; i < iLen; i += 2)
		{
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte)Integer.parseInt(strTmp, 16);
		}

		return arrOut;
	}

	public ThreeDes()
		throws Exception
	{
		this(strDefaultKey);
	}

	public ThreeDes(String strKey)
		throws Exception
	{
		Security.addProvider(new SunJCE());
		Key key = getKey(strKey.getBytes());
		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(1, key);
		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(2, key);
	}

	public static void init()
		throws Exception
	{
		if (decryptCipher == null || encryptCipher == null)
		{
			Security.addProvider(new SunJCE());
			Key key = getKey(strDefaultKey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(1, key);
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(2, key);
		}
	}

	public static byte[] encrypt(byte arrB[])
		throws Exception
	{
		init();
		return encryptCipher.doFinal(arrB);
	}

	public static synchronized String encrypt(String strIn)
	{
		try {
			return byteArr2HexStr(encrypt(strIn.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static byte[] decrypt(byte arrB[])
		throws Exception
	{
		init();
		return decryptCipher.doFinal(arrB);
	}

	public static synchronized String decrypt(String strIn)
	{
		try {
			return new String(decrypt(hexStr2ByteArr(strIn)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}

	private static Key getKey(byte arrBTmp[])
		throws Exception
	{
		byte arrB[] = new byte[8];
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++)
			arrB[i] = arrBTmp[i];

		Key key = new SecretKeySpec(arrB, "DES");
		return key;
	}

	public static void main(String args[])
	{
		String test = "zhangkai@fomxMail.com";
		try
		{
			ThreeDes des = new ThreeDes();
			System.out.println((new StringBuilder("解密后的字符：")).append(decrypt("edfe668136138cba90ca11ffddbb3221438ba53970350054")).toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


}
