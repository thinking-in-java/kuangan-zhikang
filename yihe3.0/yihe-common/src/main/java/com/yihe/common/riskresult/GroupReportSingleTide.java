package com.yihe.common.riskresult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.yihe.common.utils.ParamType;
import com.yihe.common.utils.Path;



public class GroupReportSingleTide
{

	private List ageList;
	private String gendarStr;
	private String title;
	private String riskNames;
	private String dateNames;
	private List dateList;
	private int singleRiskGender[][];
	private int allNo;
	private int manNo;
	private int womanNo;
	private float singleRiskGenderMax[][];
	private int singleRiskAgeAll[][];
	private int singleRiskAgeMan[][];
	private int singleRiskAgeWoman[][];
	private int allMount[];
	private int manMount[];
	private int womanMount[];
	private float singleRiskAgeAllMax[][];
	private float singleRiskAgeManMax[][];
	private float singleRiskAgeWomanMax[][];

	public GroupReportSingleTide()
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

	public void getDatePhase(String dateArrayStr)
	{
		List dateList = new ArrayList();
		String tempArray[] = dateArrayStr.split(";");
		int count = 0;
		for (int i = 0; i < tempArray.length; i++)
		{
			String tempArraySub[] = tempArray[i].split(",");
			count += monthCount(tempArraySub[0], tempArraySub[1]) + 1;
		}

		String tempArray2[] = new String[count];
		int count1 = 0;
		for (int i = 0; i < tempArray.length; i++)
		{
			String tempArraySub[] = tempArray[i].split(",");
			int countSub = monthCount(tempArraySub[0], tempArraySub[1]);
			tempArray2[count1] = tempArraySub[0];
			count1++;
			int year = Integer.parseInt(tempArraySub[0].substring(0, 4));
			int month = Integer.parseInt(tempArraySub[0].substring(5));
			for (int j = 1; j <= countSub; j++)
			{
				String tempDate = "";
				if (month + j > 12)
				{
					if ((month + j) % 12 != 0)
						tempDate = (new StringBuilder(String.valueOf(tempDate))).append(year + (month + j) / 12).toString();
					else
						tempDate = (new StringBuilder(String.valueOf(tempDate))).append(year + ((month + j) / 12 - 1)).toString();
					if ((month + j) % 12 == 0)
						tempDate = (new StringBuilder(String.valueOf(tempDate))).append("-12").toString();
					else
					if ((month + j) % 12 < 10)
						tempDate = (new StringBuilder(String.valueOf(tempDate))).append("-0").append((month + j) % 12).toString();
					else
						tempDate = (new StringBuilder(String.valueOf(tempDate))).append("-").append((month + j) % 12).toString();
					tempArray2[count1] = tempDate;
				} else
				if (month + j < 10)
					tempArray2[count1] = (new StringBuilder(String.valueOf(year))).append("-0").append(month + j).toString();
				else
					tempArray2[count1] = (new StringBuilder(String.valueOf(year))).append("-").append(month + j).toString();
				count1++;
			}

		}

		for (int i = 0; i < tempArray2.length; i++)
		{
			String dateNext = tempArray2[i];
			DatePhaseUtil utilNext = new DatePhaseUtil();
			utilNext.setDate(dateNext);
			String month = dateNext.substring(5);
			String year = dateNext.substring(0, 4);
			if (month.equals("12"))
			{
				String tempYear = "";
				tempYear = (new StringBuilder(String.valueOf(Integer.parseInt(year) + 1))).toString();
				utilNext.setHql((new StringBuilder("wellRiskresult.riskTime>='")).append(dateNext).append("-01' and ").append("wellRiskresult.riskTime<'").append(tempYear).append("-01-01'").toString());
			} else
			{
				String tempDate = "";
				tempDate = (new StringBuilder(String.valueOf(tempDate))).append(year).toString();
				if (Integer.parseInt(month) + 1 < 10)
					tempDate = (new StringBuilder(String.valueOf(tempDate))).append("-0").append(Integer.parseInt(month) + 1).toString();
				else
					tempDate = (new StringBuilder(String.valueOf(tempDate))).append("-").append(Integer.parseInt(month) + 1).toString();
				utilNext.setHql((new StringBuilder("wellRiskresult.riskTime>='")).append(dateNext).append("-01' and ").append("wellRiskresult.riskTime<'").append(tempDate).append("-01'").toString());
			}
			DatePhaseUtil utilPre = null;
			if (i - 1 >= 0)
			{
				String datePre = tempArray2[i - 1];
				if (monthCount(datePre, dateNext) >= 2)
				{
					utilPre = new DatePhaseUtil();
					utilPre.setHql(null);
					if (month.equals("01"))
					{
						String tempYear = "";
						tempYear = (new StringBuilder(String.valueOf(Integer.parseInt(year) - 1))).toString();
						utilPre.setDate((new StringBuilder(String.valueOf(tempYear))).append("-12").toString());
					} else
					{
						String tempDate = "";
						tempDate = (new StringBuilder(String.valueOf(tempDate))).append(year).toString();
						if (Integer.parseInt(month) - 1 < 10)
							tempDate = (new StringBuilder(String.valueOf(tempDate))).append("-0").append(Integer.parseInt(month) - 1).toString();
						else
							tempDate = (new StringBuilder(String.valueOf(tempDate))).append("-").append(Integer.parseInt(month) - 1).toString();
						utilPre.setDate(tempDate);
					}
				}
			}
			if (utilPre != null)
				dateList.add(utilPre);
			dateList.add(utilNext);
		}

		List dateList2 = new ArrayList();
		for (int i = 0; i < dateList.size(); i++)
		{
			DatePhaseUtil util = (DatePhaseUtil)dateList.get(i);
			//if (monthCount(report.getGroupReportBean().getFirstMonth(), util.getDate()) >= 0 && monthCount(util.getDate(), report.getGroupReportBean().getLastMonth()) >= 0)
				dateList2.add(util);
		}

		setDateList(dateList2);
	}

	public static void main(String args1[])
	{
	}

	public int monthCount(String param1, String param2)
	{
		String month2 = param2.substring(5);
		String month1 = param1.substring(5);
		return (Integer.parseInt(param2.substring(0, 4)) - Integer.parseInt(param1.substring(0, 4))) * 12 + (Integer.parseInt(month2) - Integer.parseInt(month1));
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
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChart, 680, 400);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}

	public JFreeChart getJFreeChart()
	{
		if (getGendarStr().equals("100"))
		{
			TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("总体", 0));
			return GroupReport.getCrossHairChartTitle(localTimeSeriesCollection, new java.awt.Color[] {
				java.awt.Color.red
			}, (new StringBuilder(String.valueOf(getTitle()))).append("(性别)").toString());
		}
		if (getGendarStr().equals("010"))
		{
			TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("男", 1));
			return GroupReport.getCrossHairChartTitle(localTimeSeriesCollection, new java.awt.Color[] {
				java.awt.Color.red
			}, (new StringBuilder(String.valueOf(getTitle()))).append("(性别)").toString());
		}
		if (getGendarStr().equals("001"))
		{
			TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("女", 2));
			return GroupReport.getCrossHairChartTitle(localTimeSeriesCollection, new java.awt.Color[] {
				java.awt.Color.red
			}, (new StringBuilder(String.valueOf(getTitle()))).append("(性别)").toString());
		}
		if (getGendarStr().equals("110"))
		{
			TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("总体", 0));
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("男", 1));
			return GroupReport.getCrossHairChartTitle(localTimeSeriesCollection, new java.awt.Color[] {
				java.awt.Color.red, java.awt.Color.green
			}, (new StringBuilder(String.valueOf(getTitle()))).append("(性别)").toString());
		}
		if (getGendarStr().equals("101"))
		{
			TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("总体", 0));
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("女", 2));
			return GroupReport.getCrossHairChartTitle(localTimeSeriesCollection, new java.awt.Color[] {
				java.awt.Color.red, java.awt.Color.green
			}, (new StringBuilder(String.valueOf(getTitle()))).append("(性别)").toString());
		}
		if (getGendarStr().equals("011"))
		{
			TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("男", 1));
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("女", 2));
			return GroupReport.getCrossHairChartTitle(localTimeSeriesCollection, new java.awt.Color[] {
				java.awt.Color.red, java.awt.Color.green
			}, (new StringBuilder(String.valueOf(getTitle()))).append("(性别)").toString());
		} else
		{
			TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("总体", 0));
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("男", 1));
			localTimeSeriesCollection.addSeries(getTimeSeriesCollection("女", 2));
			return GroupReport.getCrossHairChartTitle(localTimeSeriesCollection, new java.awt.Color[] {
				java.awt.Color.red, java.awt.Color.green, java.awt.Color.yellow
			}, (new StringBuilder(String.valueOf(getTitle()))).append("(性别)").toString());
		}
	}

	public TimeSeries getTimeSeriesCollection(String title, int temp)
	{
		TimeSeries localTimeSeries1 = new TimeSeries(title, GroupReportSingleTide.class);
		for (int i = 0; i < getDateList().size(); i++)
		{
			DatePhaseUtil util = (DatePhaseUtil)getDateList().get(i);
			if (util.getHql() != null)
			{
				System.out.println(getSingleRiskGenderMax()[i][temp] / 100F);
				localTimeSeries1.add(new Month(Integer.parseInt(util.getDate().substring(5)), Integer.parseInt(util.getDate().substring(0, 4))), getSingleRiskGenderMax()[i][temp] / 100F);
			} else
			{
				localTimeSeries1.add(new Month(Integer.parseInt(util.getDate().substring(5)), Integer.parseInt(util.getDate().substring(0, 4))), null);
			}
		}

		return localTimeSeries1;
	}

	public String getJFreeChartPath2()
	{
		JFreeChart jFreeChart = getJFreeChart2();
		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChart, 680, 400);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}

	public JFreeChart getJFreeChart2()
	{
		return GroupReport.getCrossHairChartTitle(getTimeSeriesCollectionAll(0), null, (new StringBuilder(String.valueOf(getTitle()))).append("(总体-年龄段)").toString());
	}

	public String getJFreeChartPath3()
	{
		JFreeChart jFreeChart = getJFreeChart3();
		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChart, 680, 400);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}

	public JFreeChart getJFreeChart3()
	{
		return GroupReport.getCrossHairChartTitle(getTimeSeriesCollectionAll(1), null, (new StringBuilder(String.valueOf(getTitle()))).append("(男-年龄段)").toString());
	}

	public String getJFreeChartPath4()
	{
		JFreeChart jFreeChart = getJFreeChart4();
		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChart, 680, 400);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}

	public JFreeChart getJFreeChart4()
	{
		return GroupReport.getCrossHairChartTitle(getTimeSeriesCollectionAll(2), null, (new StringBuilder(String.valueOf(getTitle()))).append("(女-年龄段)").toString());
	}

	public TimeSeriesCollection getTimeSeriesCollectionAll(int temp)
	{
		float tempFloat[][] = null;
		if (temp == 0)
			tempFloat = getSingleRiskAgeAllMax();
		else
		if (temp == 1)
			tempFloat = getSingleRiskAgeManMax();
		else
			tempFloat = getSingleRiskAgeWomanMax();
		TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
		List tempList = getAgeList();
		int countTemp = 0;
		for (int i = 0; i < tempList.size(); i++)
		{
			List subList = (List)tempList.get(i);
			for (int j = 0; j < subList.size(); j++)
			{
				TimeSeries localTimeSeries1 = new TimeSeries(((AgePhaseUtil)subList.get(j)).getName(), GroupReportSingleTide.class);
				for (int k = 0; k < getDateList().size(); k++)
				{
					DatePhaseUtil util = (DatePhaseUtil)getDateList().get(k);
					if (util.getHql() != null)
						localTimeSeries1.add(new Month(Integer.parseInt(util.getDate().substring(5)), Integer.parseInt(util.getDate().substring(0, 4))), tempFloat[k][countTemp] / 100F);
					else
						localTimeSeries1.add(new Month(Integer.parseInt(util.getDate().substring(5)), Integer.parseInt(util.getDate().substring(0, 4))), null);
				}

				localTimeSeriesCollection.addSeries(localTimeSeries1);
				countTemp++;
			}

		}

		return localTimeSeriesCollection;
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

	public int[][] getSingleRiskGender()
	{
		return singleRiskGender;
	}

	public void setSingleRiskGender(int singleRiskGender[][])
	{
		this.singleRiskGender = singleRiskGender;
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

	public int[][] getSingleRiskAgeAll()
	{
		return singleRiskAgeAll;
	}

	public void setSingleRiskAgeAll(int singleRiskAgeAll[][])
	{
		this.singleRiskAgeAll = singleRiskAgeAll;
	}

	public int[][] getSingleRiskAgeMan()
	{
		return singleRiskAgeMan;
	}

	public void setSingleRiskAgeMan(int singleRiskAgeMan[][])
	{
		this.singleRiskAgeMan = singleRiskAgeMan;
	}

	public int[][] getSingleRiskAgeWoman()
	{
		return singleRiskAgeWoman;
	}

	public void setSingleRiskAgeWoman(int singleRiskAgeWoman[][])
	{
		this.singleRiskAgeWoman = singleRiskAgeWoman;
	}

	public float[][] getSingleRiskGenderMax()
	{
		int count = getSingleRiskGender().length;
		float temp[][] = new float[count][3];
		for (int i = 0; i < count; i++)
		{
			float tempAllMax = ParamType.getFloNum(((float)getSingleRiskGender()[i][0] * 100F) / (float)getAllNo(), 1);
			float tempManMax = ParamType.getFloNum(((float)getSingleRiskGender()[i][1] * 100F) / (float)getManNo(), 1);
			float tempWomanMax = ParamType.getFloNum(((float)getSingleRiskGender()[i][2] * 100F) / (float)getWomanNo(), 1);
			temp[i] = (new float[] {
				tempAllMax, tempManMax, tempWomanMax
			});
		}

		return temp;
	}

	public float[][] getSingleRiskAgeAllMax()
	{
		int count = getSingleRiskAgeAll().length;
		float temp[][] = new float[count][];
		for (int i = 0; i < count; i++)
		{
			float subTemp[] = new float[getSingleRiskAgeAll()[i].length];
			for (int j = 0; j < subTemp.length; j++)
				subTemp[j] = ParamType.getFloNum(((float)getSingleRiskAgeAll()[i][j] * 100F) / (float)getAllMount()[j], 1);

			temp[i] = subTemp;
		}

		return temp;
	}

	public float[][] getSingleRiskAgeManMax()
	{
		int count = getSingleRiskAgeMan().length;
		float temp[][] = new float[count][];
		for (int i = 0; i < count; i++)
		{
			float subTemp[] = new float[getSingleRiskAgeMan()[i].length];
			for (int j = 0; j < subTemp.length; j++)
				subTemp[j] = ParamType.getFloNum(((float)getSingleRiskAgeMan()[i][j] * 100F) / (float)getManMount()[j], 1);

			temp[i] = subTemp;
		}

		return temp;
	}

	public float[][] getSingleRiskAgeWomanMax()
	{
		int count = getSingleRiskAgeWoman().length;
		float temp[][] = new float[count][];
		for (int i = 0; i < count; i++)
		{
			float subTemp[] = new float[getSingleRiskAgeWoman()[i].length];
			for (int j = 0; j < subTemp.length; j++)
				subTemp[j] = ParamType.getFloNum(((float)getSingleRiskAgeWoman()[i][j] * 100F) / (float)getWomanMount()[j], 1);

			temp[i] = subTemp;
		}

		return temp;
	}

	public String getDateNames()
	{
		return dateNames;
	}

	public void setDateNames(String dateNames)
	{
		this.dateNames = dateNames;
	}

	public List getDateList()
	{
		return dateList;
	}

	public void setDateList(List dateList)
	{
		this.dateList = dateList;
	}
}

