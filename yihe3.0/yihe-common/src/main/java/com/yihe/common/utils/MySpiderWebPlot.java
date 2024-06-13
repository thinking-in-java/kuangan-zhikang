package com.yihe.common.utils;

import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.data.category.CategoryDataset;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.*;
import java.text.NumberFormat;

public class MySpiderWebPlot extends SpiderWebPlot {
    private int ticks = 0;
    /**
     * 画环
     */
    //private boolean drawRing = false;

    //private static final int DEFAULT_TICKS = 4;

    private NumberFormat format = NumberFormat.getInstance();

    private static final double PERPENDICULAR = 90;

    private static final double TICK_SCALE = 0.015;

    private int valueLabelGap = DEFAULT_GAP;

    private static final int DEFAULT_GAP = 20;

    private static final double THRESHOLD = 15;

    MySpiderWebPlot(CategoryDataset createCategoryDataset) {
        super(createCategoryDataset);
    }

    MySpiderWebPlot(CategoryDataset createCategoryDataset, int ticks) {
        super(createCategoryDataset);
        //设置雷达图最大值
        super.setMaxValue(ticks);
        this.ticks= ticks;
    }

    @Override
    protected void drawLabel(final Graphics2D g2, final Rectangle2D plotArea,

                             final double value, final int cat, final double startAngle,

                             final double extent) {
        super.drawLabel(g2, plotArea, value, cat, startAngle, extent);

        final FontRenderContext frc = g2.getFontRenderContext();

        final double[] transformed = new double[2];

        final double[] transformer = new double[2];

        final Arc2D arc1 = new Arc2D.Double(plotArea, startAngle, 0, Arc2D.OPEN);

        for (int i = 1; i <= ticks; i++) {
            final Point2D point1 = arc1.getEndPoint();

            final double deltaX = plotArea.getCenterX();

            final double deltaY = plotArea.getCenterY();

            double labelX = point1.getX() - deltaX;

            double labelY = point1.getY() - deltaY;

            final double scale = ((double) i / (double) ticks);

            final AffineTransform tx = AffineTransform.getScaleInstance(scale,scale);

            final AffineTransform pointTrans = AffineTransform.getScaleInstance(scale + TICK_SCALE, scale + TICK_SCALE);

            transformer[0] = labelX;

            transformer[1] = labelY;

            pointTrans.transform(transformer, 0, transformed, 0, 1);

            final double pointX = transformed[0] + deltaX;

            final double pointY = transformed[1] + deltaY;

            tx.transform(transformer, 0, transformed, 0, 1);

            labelX = transformed[0] + deltaX;

            labelY = transformed[1] + deltaY;

            double rotated = (PERPENDICULAR);

            AffineTransform rotateTrans = AffineTransform.getRotateInstance(

                    Math.toRadians(rotated), labelX, labelY);

            transformer[0] = pointX;

            transformer[1] = pointY;

            rotateTrans.transform(transformer, 0, transformed, 0, 1);

            final double x1 = transformed[0];

            final double y1 = transformed[1];

            rotated = (-PERPENDICULAR);

            rotateTrans = AffineTransform.getRotateInstance(

                    Math.toRadians(rotated), labelX, labelY);

            rotateTrans.transform(transformer, 0, transformed, 0, 1);

            final Composite saveComposite = g2.getComposite();

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,

                    1.0f));

            g2.draw(new Line2D.Double(transformed[0], transformed[1], x1, y1));
            if (startAngle == this.getStartAngle()) {
                //final String label = format.format(((double) i / (double) ticks) * this.getMaxValue());
                final String label = format.format(((double) i / (double) ticks) * ticks-1);
                final LineMetrics lm = getLabelFont().getLineMetrics(label, frc);
                final double ascent = lm.getAscent();
                if (Math.abs(labelX - plotArea.getCenterX()) < THRESHOLD) {
                    labelX += valueLabelGap;
                    labelY += ascent / (float) 2;
                }else if (Math.abs(labelY - plotArea.getCenterY())< THRESHOLD) {
                    labelY += valueLabelGap;
                }else if(labelX >= plotArea.getCenterX()) {
                    if (labelY < plotArea.getCenterY()){
                        labelX += valueLabelGap;
                        labelY += valueLabelGap;
                    }else{
                        labelX -= valueLabelGap;
                        labelY += valueLabelGap;
                    }
                }else{
                    if (labelY > plotArea.getCenterY()){
                        labelX -= valueLabelGap;
                        labelY -= valueLabelGap;
                    }else{
                        labelX += valueLabelGap;
                        labelY -= valueLabelGap;
                    }
                }
                g2.setPaint(getLabelPaint());
                g2.setFont(getLabelFont());
                g2.drawString(label, (float) labelX, (float)labelY);
                //以90度为轴心，计算各个圆环的x、y坐标
                Point2D topPoint = getWebPoint(plotArea, 90, 1);
                //轴心顶点圆的半径
                double topPointR = deltaY - topPoint.getY();
                //每个刻度的半径长
                double step = topPointR / ticks;
                double r = i * step;
                double upperLeftX = deltaX - r;
                double upperLeftY = deltaY - r;
                double d = 2 * r;
                Ellipse2D ring = new Ellipse2D.Double(upperLeftX, upperLeftY, d, d);
                g2.setPaint(Color.lightGray);
                g2.draw(ring);
            }
            g2.setComposite(saveComposite);
        }
    }
}
