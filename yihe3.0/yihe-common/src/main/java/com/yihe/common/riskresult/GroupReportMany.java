// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportMany.java

package com.yihe.common.riskresult;
import com.yihe.common.utils.ParamType;
import com.yihe.common.utils.Path;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtilities;

// Referenced classes of package cn.well.step.util.riskresult:
//			AgePhaseUtil, GroupReport

public class GroupReportMany
{

	private List ageList;
	private String gendarStr;
	private String title;
	private String riskNames;
	private int manyRiskGender[][];
	private int allNo;
	private int manNo;
	private int womanNo;
	private float manyRiskGenderMax[][];
	private int manyRiskAgeAll[][];
	private int manyRiskAgeMan[][];
	private int manyRiskAgeWoman[][];
	private int allMount[];
	private int manMount[];
	private int womanMount[];
	private float manyRiskAgeAllMax[][];
	private float manyRiskAgeManMax[][];
	private float manyRiskAgeWomanMax[][];

	public GroupReportMany()
	{
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void getAgePhase(String ageConditions[])
	{
		List list = new ArrayList();
		for (int i = 0; i < ageConditions.length; i++)
		{
			List subList = new ArrayList();
			String temp = ageConditions[i];
			int firstSeparator = temp.indexOf(",");
			int lastSeparator = temp.lastIndexOf(",");
			int startAge = Integer.parseInt(temp.substring(0, firstSeparator));
			int endAge = Integer.parseInt(temp.substring(firstSeparator + 1, lastSeparator));
			String phaseStr = temp.substring(lastSeparator + 1);
			if (phaseStr.equals("-1"))
			{
				AgePhaseUtil tempObj = new AgePhaseUtil();
				tempObj.setStartAge(startAge);
				tempObj.setEndAge(endAge);
				if (endAge == startAge)
				{
					tempObj.setName((new StringBuilder(String.valueOf(startAge))).toString());
					tempObj.setName2((new StringBuilder(String.valueOf(startAge))).toString());
				} else
				{
					tempObj.setName((new StringBuilder(String.valueOf(startAge))).append("--").append(endAge).toString());
					tempObj.setName2((new StringBuilder(String.valueOf(startAge))).append("--").append(endAge).toString());
				}
				subList.add(tempObj);
			} else
			{
				int phase = Integer.parseInt(temp.substring(lastSeparator + 1));
				if (endAge - startAge == phase - 1)
				{
					AgePhaseUtil tempObj = new AgePhaseUtil();
					tempObj.setStartAge(startAge);
					tempObj.setEndAge(endAge);
					if (endAge == startAge)
					{
						tempObj.setName((new StringBuilder(String.valueOf(startAge))).toString());
						tempObj.setName2((new StringBuilder(String.valueOf(startAge))).toString());
					} else
					{
						tempObj.setName((new StringBuilder(String.valueOf(startAge))).append("--").append(endAge).toString());
						tempObj.setName2((new StringBuilder(String.valueOf(startAge))).append("--").append(endAge).toString());
					}
					subList.add(tempObj);
				} else
				{
					int size = (endAge - startAge) / phase;
					for (int j = 1; j <= size; j++)
					{
						AgePhaseUtil tempObj = new AgePhaseUtil();
						if (j == size && (endAge - startAge) % phase == 0)
						{
							tempObj.setStartAge(startAge + phase * (j - 1));
							tempObj.setEndAge(startAge + phase * j);
							tempObj.setName((new StringBuilder(String.valueOf(startAge + phase * (j - 1)))).append("--").append(startAge + phase * j).toString());
							tempObj.setName2((new StringBuilder(String.valueOf(startAge + phase * (j - 1)))).append("~").toString());
							subList.add(tempObj);
							AgePhaseUtil tempObj2 = new AgePhaseUtil();
							tempObj2.setStartAge(endAge);
							tempObj2.setEndAge(endAge);
							tempObj2.setName((new StringBuilder(String.valueOf(endAge))).toString());
							tempObj2.setName2((new StringBuilder(String.valueOf(endAge))).toString());
							subList.add(tempObj2);
						} else
						if (j == size && (endAge - startAge) % phase != 0)
						{
							tempObj.setStartAge(startAge + phase * (j - 1));
							tempObj.setEndAge(startAge + phase * j);
							tempObj.setName((new StringBuilder(String.valueOf(startAge + phase * (j - 1)))).append("--").append(startAge + phase * j).toString());
							tempObj.setName2((new StringBuilder(String.valueOf(startAge + phase * (j - 1)))).append("~").toString());
							subList.add(tempObj);
							AgePhaseUtil tempObj2 = new AgePhaseUtil();
							tempObj2.setStartAge(startAge + phase * j);
							tempObj2.setEndAge(startAge + phase * j + (endAge - startAge) % phase);
							tempObj2.setName((new StringBuilder(String.valueOf(startAge + phase * j))).append("--").append(startAge + phase * j + (endAge - startAge) % phase).toString());
							tempObj2.setName2((new StringBuilder(String.valueOf(startAge + phase * j))).append("--").append(startAge + phase * j + (endAge - startAge) % phase).toString());
							subList.add(tempObj2);
						} else
						{
							tempObj.setStartAge(startAge + phase * (j - 1));
							tempObj.setEndAge(startAge + phase * j);
							tempObj.setName((new StringBuilder(String.valueOf(startAge + phase * (j - 1)))).append("--").append(startAge + phase * j).toString());
							tempObj.setName2((new StringBuilder(String.valueOf(startAge + phase * (j - 1)))).append("~").toString());
							subList.add(tempObj);
						}
					}

				}
			}
			list.add(subList);
		}

		setAgeList(list);
	}

	public List getAgeList()
	{
		return ageList;
	}

	public void setAgeList(List ageList)
	{
		this.ageList = ageList;
	}

	public int[] getAllMount()
	{
		return allMount;
	}

	public int[] getManMount()
	{
		return manMount;
	}

	public int[] getWomanMount()
	{
		return womanMount;
	}

	public String getJFreeChartPath()
	{
		JFreeChart jFreeChart = getJFreeChart();
		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChart, 680, 300);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}

	public JFreeChart getJFreeChart()
	{
		int count = getManyRiskGenderMax().length;
		String value2[] = getRiskNames().split(";");
		double manAll[] = new double[count];
		double allAll[] = new double[count];
		double womanAll[] = new double[count];
		for (int i = 0; i < count; i++)
		{
			allAll[i] = getManyRiskGenderMax()[i][0] / 100F;
			manAll[i] = getManyRiskGenderMax()[i][1] / 100F;
			womanAll[i] = getManyRiskGenderMax()[i][2] / 100F;
		}

		double value[][] = null;
		String value1[] = null;
		if (getGendarStr().equals("100"))
		{
			value = (new double[][] {
				allAll
			});
			value1 = (new String[] {
				"����"
			});
			return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new java.awt.Color[] {
				java.awt.Color.red
			}, getTitle(), 0.40000000000000002D);
		}
		if (getGendarStr().equals("010"))
		{
			value = (new double[][] {
				manAll
			});
			value1 = (new String[] {
				"��"
			});
			return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new java.awt.Color[] {
				java.awt.Color.red
			}, getTitle(), 0.40000000000000002D);
		}
		if (getGendarStr().equals("001"))
		{
			value = (new double[][] {
				womanAll
			});
			value1 = (new String[] {
				"Ů"
			});
			return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new java.awt.Color[] {
				java.awt.Color.red
			}, getTitle(), 0.40000000000000002D);
		}
		if (getGendarStr().equals("110"))
		{
			value = (new double[][] {
				allAll, manAll
			});
			value1 = (new String[] {
				"����", "��"
			});
			return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new java.awt.Color[] {
				java.awt.Color.red, java.awt.Color.yellow
			}, getTitle(), 0.40000000000000002D);
		}
		if (getGendarStr().equals("101"))
		{
			value = (new double[][] {
				allAll, womanAll
			});
			value1 = (new String[] {
				"����", "Ů"
			});
			return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new java.awt.Color[] {
				java.awt.Color.red, java.awt.Color.yellow
			}, getTitle(), 0.40000000000000002D);
		}
		if (getGendarStr().equals("011"))
		{
			value = (new double[][] {
				manAll, womanAll
			});
			value1 = (new String[] {
				"��", "Ů"
			});
			return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new java.awt.Color[] {
				java.awt.Color.red, java.awt.Color.yellow
			}, getTitle(), 0.40000000000000002D);
		} else
		{
			value = (new double[][] {
				allAll, manAll, womanAll
			});
			value1 = (new String[] {
				"����", "��", "Ů"
			});
			return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new java.awt.Color[] {
				java.awt.Color.red, java.awt.Color.yellow, java.awt.Color.green
			}, getTitle(), 0.40000000000000002D);
		}
	}

	public String getJFreeChartPath2()
	{
		JFreeChart jFreeChart = getJFreeChart2();
		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChart, 680, 300);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}

	public JFreeChart getJFreeChart2()
	{
		List tempList = getAgeList();
		int count = getManyRiskAgeAllMax().length;
		String value2[] = getRiskNames().split(";");
		double tempValue[][] = new double[getManyRiskAgeAllMax()[0].length][count];
		for (int j = 0; j < getManyRiskAgeAllMax()[0].length; j++)
		{
			for (int i = 0; i < count; i++)
				tempValue[j][i] = getManyRiskAgeAllMax()[i][j] / 100F;

		}

		double value[][] = tempValue;
		String value1[] = null;
		String value1Str = "";
		List ageList = getAgeList();
		for (int i = 0; i < ageList.size(); i++)
		{
			List subList = (List)ageList.get(i);
			for (int j = 0; j < subList.size(); j++)
				value1Str = (new StringBuilder(String.valueOf(value1Str))).append(((AgePhaseUtil)subList.get(j)).getName()).append(";").toString();

		}

		value1 = value1Str.split(";");
		java.awt.Color c[] = new java.awt.Color[value1.length];
		for (int i = 0; i < c.length; i++)
			c[i] = java.awt.Color.green;

		return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), null, (new StringBuilder(String.valueOf(getTitle()))).append("(����)").toString(), 0.10000000000000001D);
	}

	public String getJFreeChartPath3()
	{
		JFreeChart jFreeChart = getJFreeChart3();
		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChart, 680, 300);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}

	public JFreeChart getJFreeChart3()
	{
		List tempList = getAgeList();
		int count = getManyRiskAgeManMax().length;
		String value2[] = getRiskNames().split(";");
		double tempValue[][] = new double[getManyRiskAgeManMax()[0].length][count];
		for (int j = 0; j < getManyRiskAgeManMax()[0].length; j++)
		{
			for (int i = 0; i < count; i++)
				tempValue[j][i] = getManyRiskAgeManMax()[i][j] / 100F;

		}

		double value[][] = tempValue;
		String value1[] = null;
		String value1Str = "";
		List ageList = getAgeList();
		for (int i = 0; i < ageList.size(); i++)
		{
			List subList = (List)ageList.get(i);
			for (int j = 0; j < subList.size(); j++)
				value1Str = (new StringBuilder(String.valueOf(value1Str))).append(((AgePhaseUtil)subList.get(j)).getName()).append(";").toString();

		}

		value1 = value1Str.split(";");
		java.awt.Color c[] = new java.awt.Color[value1.length];
		for (int i = 0; i < c.length; i++)
			c[i] = java.awt.Color.green;

		return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), null, (new StringBuilder(String.valueOf(getTitle()))).append("(����)").toString(), 0.10000000000000001D);
	}

	public String getJFreeChartPath4()
	{
		JFreeChart jFreeChart = getJFreeChart4();
		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChart, 680, 300);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}

	public JFreeChart getJFreeChart4()
	{
		List tempList = getAgeList();
		int count = getManyRiskAgeWomanMax().length;
		String value2[] = getRiskNames().split(";");
		double tempValue[][] = new double[getManyRiskAgeWomanMax()[0].length][count];
		for (int j = 0; j < getManyRiskAgeWomanMax()[0].length; j++)
		{
			for (int i = 0; i < count; i++)
				tempValue[j][i] = getManyRiskAgeWomanMax()[i][j] / 100F;

		}

		double value[][] = tempValue;
		String value1[] = null;
		String value1Str = "";
		List ageList = getAgeList();
		for (int i = 0; i < ageList.size(); i++)
		{
			List subList = (List)ageList.get(i);
			for (int j = 0; j < subList.size(); j++)
				value1Str = (new StringBuilder(String.valueOf(value1Str))).append(((AgePhaseUtil)subList.get(j)).getName()).append(";").toString();

		}

		value1 = value1Str.split(";");
		java.awt.Color c[] = new java.awt.Color[value1.length];
		for (int i = 0; i < c.length; i++)
			c[i] = java.awt.Color.green;

		return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), null, (new StringBuilder(String.valueOf(getTitle()))).append("(Ů��)").toString(), 0.10000000000000001D);
	}

	public void setAllMount(int allMount[])
	{
		this.allMount = allMount;
	}

	public void setManMount(int manMount[])
	{
		this.manMount = manMount;
	}

	public void setWomanMount(int womanMount[])
	{
		this.womanMount = womanMount;
	}

	public String getGendarStr()
	{
		return gendarStr;
	}

	public void setGendarStr(String gendarStr)
	{
		this.gendarStr = gendarStr;
	}

	public String getRiskNames()
	{
		return riskNames;
	}

	public void setRiskNames(String riskNames)
	{
		this.riskNames = riskNames;
	}

	public int[][] getManyRiskGender()
	{
		return manyRiskGender;
	}

	public void setManyRiskGender(int manyRiskGender[][])
	{
		this.manyRiskGender = manyRiskGender;
	}

	public int getAllNo()
	{
		return allNo;
	}

	public void setAllNo(int allNo)
	{
		this.allNo = allNo;
	}

	public int getManNo()
	{
		return manNo;
	}

	public void setManNo(int manNo)
	{
		this.manNo = manNo;
	}

	public int getWomanNo()
	{
		return womanNo;
	}

	public void setWomanNo(int womanNo)
	{
		this.womanNo = womanNo;
	}

	public int[][] getManyRiskAgeAll()
	{
		return manyRiskAgeAll;
	}

	public void setManyRiskAgeAll(int manyRiskAgeAll[][])
	{
		this.manyRiskAgeAll = manyRiskAgeAll;
	}

	public int[][] getManyRiskAgeMan()
	{
		return manyRiskAgeMan;
	}

	public void setManyRiskAgeMan(int manyRiskAgeMan[][])
	{
		this.manyRiskAgeMan = manyRiskAgeMan;
	}

	public int[][] getManyRiskAgeWoman()
	{
		return manyRiskAgeWoman;
	}

	public void setManyRiskAgeWoman(int manyRiskAgeWoman[][])
	{
		this.manyRiskAgeWoman = manyRiskAgeWoman;
	}

	public float[][] getManyRiskGenderMax()
	{
		int count = getManyRiskGender().length;
		float temp[][] = new float[count][3];
		for (int i = 0; i < count; i++)
		{
			float tempAllMax = ParamType.getFloNum(((float)getManyRiskGender()[i][0] * 100F) / (float)getAllNo(), 1);
			float tempManMax = ParamType.getFloNum(((float)getManyRiskGender()[i][1] * 100F) / (float)getManNo(), 1);
			float tempWomanMax = ParamType.getFloNum(((float)getManyRiskGender()[i][2] * 100F) / (float)getWomanNo(), 1);
			temp[i] = (new float[] {
				tempAllMax, tempManMax, tempWomanMax
			});
		}

		return temp;
	}

	public float[][] getManyRiskAgeAllMax()
	{
		int count = getManyRiskAgeAll().length;
		float temp[][] = new float[count][];
		for (int i = 0; i < count; i++)
		{
			float subTemp[] = new float[getManyRiskAgeAll()[i].length];
			for (int j = 0; j < subTemp.length; j++)
				subTemp[j] = ParamType.getFloNum(((float)getManyRiskAgeAll()[i][j] * 100F) / (float)getAllMount()[j], 1);

			temp[i] = subTemp;
		}

		return temp;
	}

	public float[][] getManyRiskAgeManMax()
	{
		int count = getManyRiskAgeMan().length;
		float temp[][] = new float[count][];
		for (int i = 0; i < count; i++)
		{
			float subTemp[] = new float[getManyRiskAgeMan()[i].length];
			for (int j = 0; j < subTemp.length; j++)
				subTemp[j] = ParamType.getFloNum(((float)getManyRiskAgeMan()[i][j] * 100F) / (float)getManMount()[j], 1);

			temp[i] = subTemp;
		}

		return temp;
	}

	public float[][] getManyRiskAgeWomanMax()
	{
		int count = getManyRiskAgeWoman().length;
		float temp[][] = new float[count][];
		for (int i = 0; i < count; i++)
		{
			float subTemp[] = new float[getManyRiskAgeWoman()[i].length];
			for (int j = 0; j < subTemp.length; j++)
				subTemp[j] = ParamType.getFloNum(((float)getManyRiskAgeWoman()[i][j] * 100F) / (float)getWomanMount()[j], 1);

			temp[i] = subTemp;
		}

		return temp;
	}
}
