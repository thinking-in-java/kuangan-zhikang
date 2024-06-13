// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportHealthRisks.java

package com.yihe.common.riskresult;
import com.yihe.common.utils.Path;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtilities;

// Referenced classes of package cn.well.step.util.riskresult:
//			GroupReport

public class GroupReportHealthRisks
{

	public int count;
	private int diabNow;
	private int civNow;
	private int lungNow;
	private int hbpNow;
	private int diabRelaTier1;
	private int diabRelaTier2;
	private int diabRelaTier3;
	private int diabRelaTier4;
	private int diabRelaTier5;
	private int diabIdeTier1;
	private int diabIdeTier2;
	private int diabIdeTier3;
	private int diabIdeTier4;
	private int diabIdeTier5;
	private int civRelaTier1;
	private int civRelaTier2;
	private int civRelaTier3;
	private int civRelaTier4;
	private int civRelaTier5;
	private int civIdeTier1;
	private int civIdeTier2;
	private int civIdeTier3;
	private int civIdeTier4;
	private int civIdeTier5;
	private int lungRelaTier1;
	private int lungRelaTier2;
	private int lungRelaTier3;
	private int lungRelaTier4;
	private int lungRelaTier5;
	private int lungIdeTier1;
	private int lungIdeTier2;
	private int lungIdeTier3;
	private int lungIdeTier4;
	private int lungIdeTier5;
	private int hbpRelaTier1;
	private int hbpRelaTier2;
	private int hbpRelaTier3;
	private int hbpIdeTier1;
	private int hbpIdeTier2;
	private int hbpIdeTier3;
	private java.util.List riskList;
	private String xh_des;
	private int diab_his_now;
	private int hbp_his_now;
	private int chd_his_now;
	private int stroke_his_now;
	private int lc_his_now;
	private int pc_his_now;
	private int bc_his_now;
	private int bf_his_now;

	public GroupReportHealthRisks(int count)
	{
		this.count = count;
	}

	public int getDiabRelaTier1()
	{
		return diabRelaTier1;
	}

	public void setDiabRelaTier1(int diabRelaTier1)
	{
		this.diabRelaTier1 = diabRelaTier1;
	}

	public int getDiabRelaTier2()
	{
		return diabRelaTier2;
	}

	public void setDiabRelaTier2(int diabRelaTier2)
	{
		this.diabRelaTier2 = diabRelaTier2;
	}

	public int getDiabRelaTier3()
	{
		return diabRelaTier3;
	}

	public void setDiabRelaTier3(int diabRelaTier3)
	{
		this.diabRelaTier3 = diabRelaTier3;
	}

	public int getDiabRelaTier4()
	{
		return diabRelaTier4;
	}

	public void setDiabRelaTier4(int diabRelaTier4)
	{
		this.diabRelaTier4 = diabRelaTier4;
	}

	public int getDiabRelaTier5()
	{
		return diabRelaTier5;
	}

	public void setDiabRelaTier5(int diabRelaTier5)
	{
		this.diabRelaTier5 = diabRelaTier5;
	}

	public int getDiabIdeTier1()
	{
		return diabIdeTier1;
	}

	public void setDiabIdeTier1(int diabIdeTier1)
	{
		this.diabIdeTier1 = diabIdeTier1;
	}

	public int getDiabIdeTier2()
	{
		return diabIdeTier2;
	}

	public void setDiabIdeTier2(int diabIdeTier2)
	{
		this.diabIdeTier2 = diabIdeTier2;
	}

	public int getDiabIdeTier3()
	{
		return diabIdeTier3;
	}

	public void setDiabIdeTier3(int diabIdeTier3)
	{
		this.diabIdeTier3 = diabIdeTier3;
	}

	public int getDiabIdeTier4()
	{
		return diabIdeTier4;
	}

	public void setDiabIdeTier4(int diabIdeTier4)
	{
		this.diabIdeTier4 = diabIdeTier4;
	}

	public int getDiabIdeTier5()
	{
		return diabIdeTier5;
	}

	public void setDiabIdeTier5(int diabIdeTier5)
	{
		this.diabIdeTier5 = diabIdeTier5;
	}

	public int getHbpRelaTier1()
	{
		return hbpRelaTier1;
	}

	public void setHbpRelaTier1(int hbpRelaTier1)
	{
		this.hbpRelaTier1 = hbpRelaTier1;
	}

	public int getHbpRelaTier2()
	{
		return hbpRelaTier2;
	}

	public void setHbpRelaTier2(int hbpRelaTier2)
	{
		this.hbpRelaTier2 = hbpRelaTier2;
	}

	public int getHbpRelaTier3()
	{
		return hbpRelaTier3;
	}

	public void setHbpRelaTier3(int hbpRelaTier3)
	{
		this.hbpRelaTier3 = hbpRelaTier3;
	}

	public int getHbpIdeTier1()
	{
		return hbpIdeTier1;
	}

	public void setHbpIdeTier1(int hbpIdeTier1)
	{
		this.hbpIdeTier1 = hbpIdeTier1;
	}

	public int getHbpIdeTier2()
	{
		return hbpIdeTier2;
	}

	public void setHbpIdeTier2(int hbpIdeTier2)
	{
		this.hbpIdeTier2 = hbpIdeTier2;
	}

	public int getHbpIdeTier3()
	{
		return hbpIdeTier3;
	}

	public void setHbpIdeTier3(int hbpIdeTier3)
	{
		this.hbpIdeTier3 = hbpIdeTier3;
	}

	public int getLungRelaTier1()
	{
		return lungRelaTier1;
	}

	public void setLungRelaTier1(int lungRelaTier1)
	{
		this.lungRelaTier1 = lungRelaTier1;
	}

	public int getLungRelaTier2()
	{
		return lungRelaTier2;
	}

	public void setLungRelaTier2(int lungRelaTier2)
	{
		this.lungRelaTier2 = lungRelaTier2;
	}

	public int getLungRelaTier3()
	{
		return lungRelaTier3;
	}

	public void setLungRelaTier3(int lungRelaTier3)
	{
		this.lungRelaTier3 = lungRelaTier3;
	}

	public int getLungRelaTier4()
	{
		return lungRelaTier4;
	}

	public void setLungRelaTier4(int lungRelaTier4)
	{
		this.lungRelaTier4 = lungRelaTier4;
	}

	public int getLungRelaTier5()
	{
		return lungRelaTier5;
	}

	public void setLungRelaTier5(int lungRelaTier5)
	{
		this.lungRelaTier5 = lungRelaTier5;
	}

	public int getLungIdeTier1()
	{
		return lungIdeTier1;
	}

	public void setLungIdeTier1(int lungIdeTier1)
	{
		this.lungIdeTier1 = lungIdeTier1;
	}

	public int getLungIdeTier2()
	{
		return lungIdeTier2;
	}

	public void setLungIdeTier2(int lungIdeTier2)
	{
		this.lungIdeTier2 = lungIdeTier2;
	}

	public int getLungIdeTier3()
	{
		return lungIdeTier3;
	}

	public void setLungIdeTier3(int lungIdeTier3)
	{
		this.lungIdeTier3 = lungIdeTier3;
	}

	public int getLungIdeTier4()
	{
		return lungIdeTier4;
	}

	public void setLungIdeTier4(int lungIdeTier4)
	{
		this.lungIdeTier4 = lungIdeTier4;
	}

	public int getLungIdeTier5()
	{
		return lungIdeTier5;
	}

	public void setLungIdeTier5(int lungIdeTier5)
	{
		this.lungIdeTier5 = lungIdeTier5;
	}

	public int getCivRelaTier1()
	{
		return civRelaTier1;
	}

	public void setCivRelaTier1(int civRelaTier1)
	{
		this.civRelaTier1 = civRelaTier1;
	}

	public int getCivRelaTier2()
	{
		return civRelaTier2;
	}

	public void setCivRelaTier2(int civRelaTier2)
	{
		this.civRelaTier2 = civRelaTier2;
	}

	public int getCivRelaTier3()
	{
		return civRelaTier3;
	}

	public void setCivRelaTier3(int civRelaTier3)
	{
		this.civRelaTier3 = civRelaTier3;
	}

	public int getCivRelaTier4()
	{
		return civRelaTier4;
	}

	public void setCivRelaTier4(int civRelaTier4)
	{
		this.civRelaTier4 = civRelaTier4;
	}

	public int getCivRelaTier5()
	{
		return civRelaTier5;
	}

	public void setCivRelaTier5(int civRelaTier5)
	{
		this.civRelaTier5 = civRelaTier5;
	}

	public int getCivIdeTier1()
	{
		return civIdeTier1;
	}

	public void setCivIdeTier1(int civIdeTier1)
	{
		this.civIdeTier1 = civIdeTier1;
	}

	public int getCivIdeTier2()
	{
		return civIdeTier2;
	}

	public void setCivIdeTier2(int civIdeTier2)
	{
		this.civIdeTier2 = civIdeTier2;
	}

	public int getCivIdeTier3()
	{
		return civIdeTier3;
	}

	public void setCivIdeTier3(int civIdeTier3)
	{
		this.civIdeTier3 = civIdeTier3;
	}

	public int getCivIdeTier4()
	{
		return civIdeTier4;
	}

	public void setCivIdeTier4(int civIdeTier4)
	{
		this.civIdeTier4 = civIdeTier4;
	}

	public int getCivIdeTier5()
	{
		return civIdeTier5;
	}

	public void setCivIdeTier5(int civIdeTier5)
	{
		this.civIdeTier5 = civIdeTier5;
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
		double diab1[] = {
			(double)getDiabRelaTier1(), (double)getDiabIdeTier1(), (double)getCivRelaTier1(), (double)getCivIdeTier1(), (double)getLungRelaTier1(), (double)getLungIdeTier1(), 0.0D, 0.0D
		};
		double diab2[] = {
			(double)getDiabRelaTier2(), (double)getDiabIdeTier2(), (double)getCivRelaTier2(), (double)getCivIdeTier2(), (double)getLungRelaTier2(), (double)getLungIdeTier2(), (double)getHbpRelaTier1(), (double)getHbpIdeTier1()
		};
		double diab3[] = {
			(double)getDiabRelaTier3(), (double)getDiabIdeTier3(), (double)getCivRelaTier3(), (double)getCivIdeTier3(), (double)getLungRelaTier3(), (double)getLungIdeTier3(), (double)getHbpRelaTier2(), (double)getHbpIdeTier2()
		};
		double diab4[] = {
			(double)getDiabRelaTier4(), (double)getDiabIdeTier4(), (double)getCivRelaTier4(), (double)getCivIdeTier4(), (double)getLungRelaTier4(), (double)getLungIdeTier4(), (double)getHbpRelaTier3(), (double)getHbpIdeTier3()
		};
		double diab5[] = {
			(double)getDiabRelaTier5(), (double)getDiabIdeTier5(), (double)getCivRelaTier5(), (double)getCivIdeTier5(), (double)getLungRelaTier5(), (double)getLungIdeTier5(), 0.0D, 0.0D
		};
		double diab6[] = {
			(double)getDiabNow(), (double)getDiabNow(), (double)getCivNow(), (double)getCivNow(), (double)getLungNow(), (double)getLungNow(), (double)getHbpNow(), (double)getHbpNow()
		};
		String value1[] = {
			"�ֻ��Ϳ���", "���߷���", "�߷���", "�еȷ���", "�ͷ���", "���ͷ���"
		};
		String value2[] = {
			"DM-A", "DM-I", "ICVD-A", "ICVD-I", "LUNG-A", "LUNG-I", "HYP-A", "HYP-I"
		};
		double value[][] = {
			diab6, diab5, diab4, diab3, diab2, diab1
		};
		return GroupReport.getStackedBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
			Color.RED, new Color(255, 147, 147), Color.YELLOW, Color.BLUE, new Color(177, 248, 154), Color.GREEN
		}, "������ȺȱѪ����Ѫ�ܲ����ΰ������򲡡���Ѫѹδ�����������������");
	}

	public int getCivNow()
	{
		return civNow;
	}

	public void setCivNow(int civNow)
	{
		this.civNow = civNow;
	}

	public int getDiabNow()
	{
		return diabNow;
	}

	public void setDiabNow(int diabNow)
	{
		this.diabNow = diabNow;
	}

	public int getHbpNow()
	{
		return hbpNow;
	}

	public void setHbpNow(int hbpNow)
	{
		this.hbpNow = hbpNow;
	}

	public int getLungNow()
	{
		return lungNow;
	}

	public void setLungNow(int lungNow)
	{
		this.lungNow = lungNow;
	}

	public java.util.List getRiskList()
	{
		return riskList;
	}

	public void setRiskList(java.util.List riskList)
	{
		this.riskList = riskList;
	}

	public String getXh_des()
	{
		return xh_des;
	}

	public void setXh_des(String xh_des)
	{
		this.xh_des = xh_des;
	}

	public int getDiab_his_now()
	{
		return diab_his_now;
	}

	public void setDiab_his_now(int diab_his_now)
	{
		this.diab_his_now = diab_his_now;
	}

	public int getHbp_his_now()
	{
		return hbp_his_now;
	}

	public void setHbp_his_now(int hbp_his_now)
	{
		this.hbp_his_now = hbp_his_now;
	}

	public int getChd_his_now()
	{
		return chd_his_now;
	}

	public void setChd_his_now(int chd_his_now)
	{
		this.chd_his_now = chd_his_now;
	}

	public int getStroke_his_now()
	{
		return stroke_his_now;
	}

	public void setStroke_his_now(int stroke_his_now)
	{
		this.stroke_his_now = stroke_his_now;
	}

	public int getLc_his_now()
	{
		return lc_his_now;
	}

	public void setLc_his_now(int lc_his_now)
	{
		this.lc_his_now = lc_his_now;
	}

	public int getPc_his_now()
	{
		return pc_his_now;
	}

	public void setPc_his_now(int pc_his_now)
	{
		this.pc_his_now = pc_his_now;
	}

	public int getBc_his_now()
	{
		return bc_his_now;
	}

	public void setBc_his_now(int bc_his_now)
	{
		this.bc_his_now = bc_his_now;
	}

	public int getBf_his_now()
	{
		return bf_his_now;
	}

	public void setBf_his_now(int bf_his_now)
	{
		this.bf_his_now = bf_his_now;
	}
}
