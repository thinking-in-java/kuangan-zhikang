// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportManyTide.java

package com.yihe.common.riskresult;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.yihe.common.utils.ParamType;
import com.yihe.common.utils.Path;

// Referenced classes of package cn.well.step.util.riskresult:
//			DatePhaseUtil, GroupReportCreatData, GroupReportBean, GroupReport

public class GroupReportManyTide
{

	private String title;
	private String riskNames;
	private String dateNames;
	private String genderStr;
	private List dateList;
	private int mount[];
	private int manyRisk[][];
	private float manyRiskMax[][];

	public GroupReportManyTide()
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

	public static List getDatePhase(String dateArrayStr)
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
			///if (monthCount(report.getGroupReportBean().getFirstMonth(), util.getDate()) >= 0 && monthCount(util.getDate(), report.getGroupReportBean().getLastMonth()) >= 0)
				dateList2.add(util);
		}

		return dateList2;
	}

	public static void main(String args[])
	{
		//GroupReportManyTide x = new GroupReportManyTide();
		TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
		TimeSeries localTimeSeries1 = new TimeSeries("sfef", Month.class);
		List datePhase = getDatePhase("2018-12,2020-04;");
		for (int i = 0; i < datePhase.size(); i++) {
			DatePhaseUtil util = (DatePhaseUtil) datePhase.get(i);
			//System.out.println(util.getDate() +"==="+util.getHql());
			//System.out.println(Integer.parseInt(util.getDate().substring(0, 4)));
			localTimeSeries1.add(new Month(Integer.parseInt(util.getDate().substring(5)), Integer.parseInt(util.getDate().substring(0, 4))),5);

		}
		localTimeSeriesCollection.addSeries(localTimeSeries1);
		List items = localTimeSeries1.getItems();
		for (int i = 0; i < items.size(); i++) {
			items.get(i);

		}
	}
	
	public static int monthCount(String param1, String param2)
	{
		String month2 = param2.substring(5);
		String month1 = param1.substring(5);
		return (Integer.parseInt(param2.substring(0, 4)) - Integer.parseInt(param1.substring(0, 4))) * 12 + (Integer.parseInt(month2) - Integer.parseInt(month1));
	}

	public String getJFreeChartPath()
	{
		JFreeChart jFreeChart = getJFreeChart();
		XYPlot localXYPlot = (XYPlot)jFreeChart.getPlot();
		XYPlot xyplot = jFreeChart.getXYPlot();
		DateAxis domainAxis = (DateAxis)xyplot.getDomainAxis();
        //设置时间间隔和时间轴显示格式：1个月一个间隔
		domainAxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1, new SimpleDateFormat("MM")));
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
		ChartFactory.setChartTheme(standardChartTheme);
		localXYPlot.getDomainAxis().setTickLabelFont(new Font("宋体", Font.BOLD, 13));
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

		return GroupReport.getCrossHairChartTitle(getTimeSeriesCollection(), null, (new StringBuilder(String.valueOf("多因素"))).append("(").append("因素分析").append(")").toString());
	}

	public TimeSeriesCollection getTimeSeriesCollection()
	{
		float tempFloat[][] = getManyRiskMax();

		TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
		//分割需要导出的因素名字
		String riskNameArray[] = getRiskNames().split(";");
		for (int i = 0; i < tempFloat.length; i++)
		{
			TimeSeries localTimeSeries1 = new TimeSeries(riskNameArray[i], Month.class);
			System.out.println(getDateList().size());
			for (int k = 0; k < getDateList().size(); k++)
			{
				DatePhaseUtil util = (DatePhaseUtil)getDateList().get(k);
				System.out.println(util.getDate().substring(5,7));
				System.out.println(util.getDate().substring(0, 4));
				if (util.getHql() != null)
						localTimeSeries1.add(new Month(Integer.parseInt(util.getDate().substring(5,7)), Integer.parseInt(util.getDate().substring(0, 4))), tempFloat[i][k] / 100F);
				else
					System.out.println("123456");
					//localTimeSeries1.add(new Month(Integer.parseInt(util.getDate().substring(5,7)), Integer.parseInt(util.getDate().substring(0, 4))), null);
			}

			localTimeSeriesCollection.addSeries(localTimeSeries1);
		}

		return localTimeSeriesCollection;
	}

	public String getRiskNames()
	{
		return riskNames;
	}

	public void setRiskNames(String riskNames)
	{
		this.riskNames = riskNames;
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

	public String getGenderStr()
	{
		return genderStr;
	}

	public void setGenderStr(String genderStr)
	{
		this.genderStr = genderStr;
	}

	public int[][] getManyRisk()
	{
		return manyRisk;
	}

	public void setManyRisk(int manyRisk[][])
	{
		this.manyRisk = manyRisk;
	}

	public float[][] getManyRiskMax()
	{
		float temp[][] = new float[getManyRisk().length][];
		for (int i = 0; i < temp.length; i++)
		{
			float subTemp[] = new float[getManyRisk()[i].length];
			for (int j = 0; j < subTemp.length; j++)
				if (getManyRisk()[i][j] != -1)
					subTemp[j] = ParamType.getFloNum(((float)getManyRisk()[i][j] * 100F) / (float)getMount()[j], 1);

			temp[i] = subTemp;
		}

		return temp;
	}

	public int[] getMount()
	{
		return mount;
	}

	public void setMount(int mount[])
	{
		this.mount = mount;
	}
}
