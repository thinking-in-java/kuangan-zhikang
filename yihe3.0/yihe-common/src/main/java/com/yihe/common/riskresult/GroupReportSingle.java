// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportSingle.java

package com.yihe.common.riskresult;
import com.yihe.common.utils.ParamType;
import com.yihe.common.utils.Path;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtilities;

// Referenced classes of package cn.well.step.util.riskresult:
//			AgePhaseUtil, GroupReport

public class GroupReportSingle
{

	private List ageList;
	private String gendarStr;
	private String title;
	private int all[];
	private int woman[];
	private int man[];
	private float allMax[];
	private float womanMax[];
	private float manMax[];
	private int allMount[];
	private int manMount[];
	private int womanMount[];

	public GroupReportSingle()
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

	public int[] getAll()
	{
		return all;
	}

	public void setAll(int all[])
	{
		this.all = all;
	}

	public float[] getAllMax()
	{
		int allTemp[] = getAll();
		float allMaxTemp[] = new float[allTemp.length];
		for (int i = 0; i < allTemp.length; i++)
			allMaxTemp[i] = ParamType.getFloNum(((float)allTemp[i] * 100F) / (float)getAllMount()[i], 1);

		return allMaxTemp;
	}

	public int[] getAllMount()
	{
		return allMount;
	}

	public int[] getMan()
	{
		return man;
	}

	public void setMan(int man[])
	{
		this.man = man;
	}

	public float[] getManMax()
	{
		int manTemp[] = getMan();
		float manMaxTemp[] = new float[manTemp.length];
		for (int i = 0; i < manTemp.length; i++)
			manMaxTemp[i] = ParamType.getFloNum(((float)manTemp[i] * 100F) / (float)getManMount()[i], 1);

		return manMaxTemp;
	}

	public int[] getManMount()
	{
		return manMount;
	}

	public int[] getWoman()
	{
		return woman;
	}

	public void setWoman(int woman[])
	{
		this.woman = woman;
	}

	public float[] getWomanMax()
	{
		int womanTemp[] = getWoman();
		float womanMaxTemp[] = new float[womanTemp.length];
		for (int i = 0; i < womanTemp.length; i++)
			womanMaxTemp[i] = ParamType.getFloNum(((float)womanTemp[i] * 100F) / (float)getWomanMount()[i], 1);

		return womanMaxTemp;
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
		List tempList = getAgeList();
		int count = getAllMax().length;
		String value2[] = new String[count];
		int countTemp = 0;
		for (int i = 0; i < tempList.size(); i++)
		{
			List subList = (List)tempList.get(i);
			for (int j = 0; j < subList.size(); j++)
			{
				value2[countTemp] = ((AgePhaseUtil)subList.get(j)).getName();
				countTemp++;
			}

		}

		double manAll[] = new double[count];
		double allAll[] = new double[count];
		double womanAll[] = new double[count];
		for (int i = 0; i < count; i++)
		{
			allAll[i] = getAllMax()[i] / 100F;
			manAll[i] = getManMax()[i] / 100F;
			womanAll[i] = getWomanMax()[i] / 100F;
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
			}, getTitle(), 0.10000000000000001D);
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
			}, getTitle(), 0.10000000000000001D);
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
			}, getTitle(), 0.10000000000000001D);
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
			}, getTitle(), 0.10000000000000001D);
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
			}, getTitle(), 0.10000000000000001D);
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
			}, getTitle(), 0.10000000000000001D);
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
			}, getTitle(), 0.10000000000000001D);
		}
	}

	public static void main(String args[])
	{
		int x[][] = new int[2][4];
		x[0] = (new int[] {
			1, 2
		});
		System.out.println(x[0][1]);
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
}
