package com.yihe.common.utils;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class JFreeChartUtils {

    public static void saveAsFile(FileOutputStream out,int weight, int height,DefaultCategoryDataset dataset, int ticks) {
        try {
            //保存为PNG
            ChartUtilities.writeChartAsPNG(out, createChart(dataset, ticks),weight, height);
            // 保存为JPEG
            // ChartUtilities.writeChartAsJPEG(out, chart, 500, 400);
            out.flush();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if(out != null){
                try {
                    out.close();
                }catch (IOException e){
                    // do nothing
                }
            }
        }
    }
    public static JFreeChart createChart(DefaultCategoryDataset dataset, int ticks) {
        MySpiderWebPlot spiderwebplot = new MySpiderWebPlot(dataset, ticks);

        JFreeChart jfreechart = new JFreeChart(null, TextTitle.DEFAULT_FONT,spiderwebplot, false);
        // 设置外层图片 无边框 无背景色 背景图片透明
        jfreechart.setBorderVisible(false);
        jfreechart.setBackgroundPaint(null);
        jfreechart.setBackgroundImageAlpha(0.0f);
        //jfreechart.getPlot().setOutlinePaint(null);
        LegendTitle legendtitle = new LegendTitle(spiderwebplot);

        legendtitle.setPosition(RectangleEdge.BOTTOM);

        jfreechart.addSubtitle(legendtitle);

        return jfreechart;

    }
}
