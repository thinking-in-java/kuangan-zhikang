// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileUtil.java

package com.yihe.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil
{

	public FileUtil()
	{
	}

	public static void creatFile(String path, String fileName)
	{
		try
		{
			String filePath = path;
			filePath = filePath.toString();
			NewFile(path);
			File myFilePath = new File((new StringBuilder(String.valueOf(filePath))).append(fileName).toString());
			if (!myFilePath.exists())
				myFilePath.createNewFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void NewFile(String path)
	{
		try
		{
			String filePath = path.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists())
				myFilePath.mkdirs();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void NewFile(String path, String split)
	{
		try
		{
			String filePath = path;
			filePath = filePath.toString();
			File myFilePath = new File((new StringBuilder(String.valueOf(filePath))).append(split).toString());
			if (!myFilePath.exists())
				myFilePath.mkdirs();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void newFile(String filePathAndName, String fileContent)
	{
		try
		{
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists())
				myFilePath.createNewFile();
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static boolean getFlag(String path, String name)
	{
		File myFilePath = new File((new StringBuilder(String.valueOf(path))).append("/").append(name).toString());
		return !myFilePath.exists();
	}

	public static void delFile(String filePathAndName)
	{
		try
		{
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();
			myDelFile = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void delFolder(String folderPath)
	{
		try
		{
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void delAllFile(String filePath, String name)
	{
		File file = new File(filePath);
		File fileList[] = file.listFiles();
		if (fileList != null)
		{
			for (int i = 0; i < fileList.length; i++)
				if (fileList[i].isFile() && !fileList[i].getName().equals(name))
					fileList[i].delete();

		}
	}

	public static void delAllFile(String path)
	{
		File file = new File(path);
		if (!file.exists())
			return;
		if (!file.isDirectory())
			return;
		String tempList[] = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++)
		{
			if (path.endsWith(File.separator))
				temp = new File((new StringBuilder(String.valueOf(path))).append(tempList[i]).toString());
			else
				temp = new File((new StringBuilder(String.valueOf(path))).append(File.separator).append(tempList[i]).toString());
			if (temp.isFile())
				temp.delete();
			if (temp.isDirectory())
			{
				delAllFile((new StringBuilder(String.valueOf(path))).append("/").append(tempList[i]).toString());
				delFolder((new StringBuilder(String.valueOf(path))).append("/").append(tempList[i]).toString());
			}
		}

	}

	public static List getFileList(String path)
	{
		File p = new File(path);
		List tmpList = new ArrayList();
		if (!p.exists())
			return tmpList;
		List list = new ArrayList();
		File file[] = p.listFiles();
		for (int i = 0; i < file.length; i++)
			tmpList.add(file[i]);

		for (int i = 0; i < tmpList.size(); i++)
		{
			p = (File)tmpList.get(i);
			if (p.isDirectory())
			{
				list.add(p);
				tmpList.remove(i);
			}
		}

		list.addAll(tmpList);
		return list;
	}

	public static List getFilesList(String path)
	{
		File p = new File(path);
		List tmpList = new ArrayList();
		if (!p.exists())
			return tmpList;
		List list = new ArrayList();
		File file[] = p.listFiles();
		for (int i = 0; i < file.length; i++)
			tmpList.add(file[i]);

		for (int i = 0; i < tmpList.size(); i++)
		{
			p = (File)tmpList.get(i);
			if (p.isDirectory())
				tmpList.remove(i);
		}

		list.addAll(tmpList);
		return list;
	}

	public static List getMkdirsList(String path)
	{
		File p = new File(path);
		List tmpList = new ArrayList();
		if (!p.exists())
			return tmpList;
		List list = new ArrayList();
		File file[] = p.listFiles();
		for (int i = 0; i < file.length; i++)
			if (p.isDirectory())
				tmpList.add(file[i]);

		list.addAll(tmpList);
		return list;
	}

	public static int getCom(String path)
	{
		int i = 0;
		if (path != null && !path.equals(""))
		{
			File p = new File(path);
			if (!p.exists())
				return i;
			else
				return p.listFiles().length;
		} else
		{
			return i;
		}
	}

	public static List getAllTrueFiles(ArrayList list, String path)
	{
		List childfiles = getFileList(path);
		if (childfiles != null && childfiles.size() > 0)
		{
			for (int i = 0; i < childfiles.size(); i++)
			{
				File file = (File)childfiles.get(i);
				if (file.isFile())
					list.add(file);
				else
					getAllTrueFiles(list, file.getPath());
			}

		}
		return list;
	}
}
