// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReport.java

package com.yihe.common.riskresult;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.TextAnchor;


// Referenced classes of package cn.well.step.util.riskresult:
//			GroupReportCreatData, GroupReportBean, GroupReportBasic, GroupReportLifestyle, 
//			GroupReportHealthRisks, GroupReportBodyMassIndex, GroupReportWaistline, GroupReportBloodPressure, 
//			GroupReportBloodSugar, GroupReportLipid, GroupReportMealHabit, GroupReportSmoke, 
//			GroupReportDrinking, GroupReportPhysicalActivity, GroupReportRiskComplication, GroupReportAdvice

public class GroupReport 
{

	

	public static JFreeChart getBarChart(CategoryDataset dataset, java.awt.Color colr[], String title, double itemMargin)
	{
		JFreeChart jfreechart = ChartFactory.createBarChart(title,"","",  dataset);
		jfreechart.setTitle(new TextTitle(title, new java.awt.Font("宋体", 1, 14)));
		jfreechart.getRenderingHints().put(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		jfreechart.setBackgroundPaint(java.awt.Color.WHITE);
		jfreechart.getLegend().setItemFont(new java.awt.Font("宋体", 1, 12));
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		CategoryAxis categoryAxis = categoryplot.getDomainAxis();
		categoryAxis.setTickLabelFont(new java.awt.Font("黑体", 0, 12));
		categoryplot.setBackgroundPaint(java.awt.Color.WHITE);
		categoryplot.setRangeGridlinePaint(java.awt.Color.DARK_GRAY);
		BarRenderer renderer = (BarRenderer)categoryplot.getRenderer();
		setBarRendererColor(renderer, colr);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0.0%")));
		renderer.setBaseItemLabelFont(new java.awt.Font("宋体", 0, 12));
		renderer.setBaseItemLabelsVisible(false);
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		renderer.setItemLabelAnchorOffset(8D);
		renderer.setItemMargin(itemMargin);
		renderer.setMaximumBarWidth(0.10000000000000001D);
		renderer.setMinimumBarLength(0.10000000000000001D);
		categoryplot.setRenderer(renderer);
		NumberAxis verticalAxis = (NumberAxis)categoryplot.getRangeAxis();
		verticalAxis.setTickUnit(new NumberTickUnit(0.10000000000000001D));
		verticalAxis.setUpperBound(1.0D);
		verticalAxis.setAutoTickUnitSelection(false);
		DecimalFormat df = new DecimalFormat("0%");
		verticalAxis.setNumberFormatOverride(df);
		return jfreechart;
	}

	
	public static JFreeChart getPieChart(DefaultPieDataset dataset, java.awt.Color colr[], String title, double itemMargin)
	{
		JFreeChart jfreechart = ChartFactory.createPieChart3D(title, (PieDataset) dataset, true, true, false);
		jfreechart.setTitle(new TextTitle(title, new java.awt.Font("宋体", 1, 14)));
		jfreechart.getRenderingHints().put(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		jfreechart.setBackgroundPaint(java.awt.Color.WHITE);
		jfreechart.getLegend().setItemFont(new java.awt.Font("宋体", 1, 12));
		PiePlot3D  categoryplot = (PiePlot3D)jfreechart.getPlot();
		categoryplot.setBackgroundPaint(java.awt.Color.WHITE);
		//设置图形的生成格式为（上海 2 （10%））  
        String format = "{0} {1} ({2})";  
        categoryplot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));  
		return jfreechart;
	}

	public static JFreeChart getBarChartHorizontal(CategoryDataset dataset, java.awt.Color colr[], String title)
	{
		JFreeChart jfreechart = ChartFactory.createBarChart(title, "", "", dataset, PlotOrientation.HORIZONTAL, false, true, false);
		jfreechart.setTitle(new TextTitle(title, new java.awt.Font("宋体", 1, 14)));
		jfreechart.getRenderingHints().put(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		jfreechart.setBackgroundPaint(java.awt.Color.WHITE);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		CategoryAxis categoryAxis = categoryplot.getDomainAxis();
		categoryAxis.setTickLabelFont(new java.awt.Font("黑体", 0, 12));
		categoryplot.setBackgroundPaint(java.awt.Color.WHITE);
		categoryplot.setRangeGridlinePaint(java.awt.Color.DARK_GRAY);
		BarRenderer renderer = (BarRenderer)categoryplot.getRenderer();
		setBarRendererColor(renderer, colr);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0.0%")));
		renderer.setBaseItemLabelFont(new java.awt.Font("宋体", 0, 12));
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER));
		renderer.setItemLabelAnchorOffset(-8D);
		renderer.setItemMargin(0.10000000000000001D);
		renderer.setMaximumBarWidth(0.10000000000000001D);
		renderer.setMinimumBarLength(0.10000000000000001D);
		categoryplot.setRenderer(renderer);
		NumberAxis verticalAxis = (NumberAxis)categoryplot.getRangeAxis();
		verticalAxis.setUpperBound(1.0D);
		verticalAxis.setTickUnit(new NumberTickUnit(0.10000000000000001D));
		verticalAxis.setAutoTickUnitSelection(false);
		DecimalFormat df = new DecimalFormat("0%");
		verticalAxis.setNumberFormatOverride(df);
		return jfreechart;
	}

	public static JFreeChart getPieChart(DefaultPieDataset dataset, String title)
	{
		JFreeChart jfreechart = ChartFactory.createPieChart(title, dataset, true, true, false);
		jfreechart.setTitle(new TextTitle(title, new java.awt.Font("宋体", 1, 14)));
		jfreechart.getLegend().setItemFont(new java.awt.Font("宋体", 1, 12));
		jfreechart.setBackgroundPaint(java.awt.Color.WHITE);
		PiePlot pieplot = (PiePlot)jfreechart.getPlot();
		pieplot.setLabelFont(new java.awt.Font("黑体", 0, 12));
		pieplot.setNoDataMessage("No data available");
		pieplot.setCircular(true);
		pieplot.setLabelGap(0.11D);
		StandardPieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}: ({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.0%"));
		pieplot.setLabelGenerator(generator);
		return jfreechart;
	}

	public static JFreeChart getPieChart3D(DefaultPieDataset dataset, String title)
	{
		JFreeChart jfreechart = ChartFactory.createPieChart3D("", dataset, true, true, false);
		PiePlot3D pieplot = (PiePlot3D)jfreechart.getPlot();
		pieplot.setLabelFont(new java.awt.Font("黑体", 0, 12));
		pieplot.setNoDataMessage("No data available");
		pieplot.setCircular(true);
		pieplot.setStartAngle(290D);
		pieplot.setForegroundAlpha(0.5F);
		StandardPieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}: ({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.0%"));
		pieplot.setLabelGenerator(generator);
		return jfreechart;
	}

	public static JFreeChart getCrossHairChartTitle(XYDataset dataset, java.awt.Color colr[], String title)
	{
		JFreeChart localJFreeChart = ChartFactory.createTimeSeriesChart(title, "", "", dataset, true, true, false);
		localJFreeChart.setBackgroundPaint(java.awt.Color.WHITE);

		Font titleFont = new Font("黑体", Font.BOLD, 20);
		TextTitle textTitle = localJFreeChart.getTitle();
		textTitle.setFont(titleFont);// 为标题设置上字体


		Font LegendFont = new Font("楷体", Font.PLAIN, 18);
		LegendTitle legend = localJFreeChart.getLegend(0);
		legend.setItemFont(LegendFont);
		XYPlot localXYPlot = (XYPlot)localJFreeChart.getPlot();
		localXYPlot.setBackgroundPaint(java.awt.Color.WHITE);
		localXYPlot.setDomainCrosshairVisible(true);
		localXYPlot.setRangeCrosshairVisible(true);
		localXYPlot.setRangeGridlinePaint(java.awt.Color.DARK_GRAY);
		ValueAxis axis = localXYPlot.getDomainAxis();
		axis.setTickLabelFont(new java.awt.Font("宋体", 0, 12));
		Object localObject = (DateAxis)localXYPlot.getDomainAxis();
		((DateAxis)localObject).setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));
		XYLineAndShapeRenderer localXYLineAndShapeRenderer = (XYLineAndShapeRenderer)localXYPlot.getRenderer();
		localXYLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		if (colr != null)
		{
			for (int i = 0; i < colr.length; i++)
				localXYLineAndShapeRenderer.setSeriesPaint(i, colr[i]);

		}
		localXYLineAndShapeRenderer.setBaseShapesVisible(true);
		localXYLineAndShapeRenderer.setBaseShapesFilled(true);
		NumberAxis localNumberAxis = (NumberAxis)localXYPlot.getRangeAxis();
		localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		localNumberAxis.setTickUnit(new NumberTickUnit(0.10000000000000001D));
		localNumberAxis.setUpperBound(1.0D);
		localNumberAxis.setAutoTickUnitSelection(false);
		DecimalFormat df = new DecimalFormat("0%");
		localNumberAxis.setNumberFormatOverride(df);
		return localJFreeChart;
	}

	public static JFreeChart getStackedBarChart(CategoryDataset dataset, java.awt.Color colr[], String title)
	{
		JFreeChart jfreechart = ChartFactory.createStackedBarChart(title, "", "", dataset, PlotOrientation.VERTICAL, true, true, false);
		jfreechart.getRenderingHints().put(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		jfreechart.setBackgroundPaint(java.awt.Color.WHITE);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		CategoryAxis categoryAxis = categoryplot.getDomainAxis();
		categoryAxis.setTickLabelFont(new java.awt.Font("黑体", 0, 12));
		categoryplot.setBackgroundPaint(java.awt.Color.WHITE);
		categoryplot.setRangeGridlinePaint(java.awt.Color.DARK_GRAY);
		StackedBarRenderer renderer = (StackedBarRenderer)categoryplot.getRenderer();
		setBarRendererColor(renderer, colr);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{1}", new DecimalFormat("100")));
		renderer.setBaseItemLabelFont(new java.awt.Font("宋体", 0, 12));
		categoryplot.setRenderer(renderer);
		NumberAxis verticalAxis = (NumberAxis)categoryplot.getRangeAxis();
		verticalAxis.setTickUnit(new NumberTickUnit((int)verticalAxis.getUpperBound() / 6));
		verticalAxis.setAutoTickUnitSelection(false);
		return jfreechart;
	}

	private static void setBarRendererColor(BarRenderer renderer, java.awt.Color colr[])
	{
		if (colr != null)
		{
			for (int i = 0; i < colr.length; i++)
				renderer.setSeriesPaint(i, colr[i]);

		}
	}

	

}
