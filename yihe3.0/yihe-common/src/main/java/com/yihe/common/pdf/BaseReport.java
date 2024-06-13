package com.yihe.common.pdf;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.yihe.common.riskresult.GroupReport;
import com.yihe.common.riskresult.GroupReportBasic;
import com.yihe.common.utils.PDFUtil;
import com.yihe.common.utils.ReportUtil;


// Referenced classes of package cn.well.step.util.risk:
//			ReportUtil, RiskConnection

public class BaseReport extends ReportUtil
	implements RiskConnection
{

	private GroupReport groupReport;
	private Font font2;
	private java.awt.Color color1;
	private Font font3;
	private Image image;
	private Font font1;
	private float width_table_Page1[] = {
		0.0F, 0.5F, 0.5F
	};
	private float width_table_Page2[] = {
		0.0F, 1.0F, 0.0F
	};
	private PdfPTable table_img1;
	private PdfPTable table_Page2;
	private String str1;
	private String str2;
	private PdfPTable table_Page5;
	private PdfPCell pdfCell2;
	private PdfPTable table_Page1;
	private String footerStr;
	private Font footerFont;

	public BaseReport(GroupReport groupReport)
	{
		//footerStr = "注：鲨鱼风险评估技术授权自中华预防医学会健康风险评估与控制专业委员会";
		footerStr = "";
		this.groupReport = groupReport;
		font2 = textFont;
		font2.setSize(10F);
		font3 = font2;
		color1 = TableBackgroundColor;
		image = getLogo();
		font1 = topFont;
		font1.setSize(14F);
		font1.setStyle(1);
		footerFont = headerFooterFont;
		footerFont.setSize(8F);
	}

	public PdfPTable groupReportBasicPdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportBasic groupReportBasic = new GroupReportBasic(0);
		str1 = "人群基本情况";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = (new StringBuilder("      从")).append(groupReportBasic.getSyear()).append("年").append(groupReportBasic.getSmonth()).append("月").append(groupReportBasic.getSday()).append("日至").append(groupReportBasic.getEyear()).append("年").append(groupReportBasic.getEmonth()).append("月").append(groupReportBasic.getEday()).append("日，共有").append(100).append("人参与评估，其中男性").append(groupReportBasic.getAgeManMax()).append("人，占总人数的").append(groupReportBasic.getAgeManMaxP()).append("%；女性").append(groupReportBasic.getAgeWomanMax()).append("人，占总人数的").append(groupReportBasic.getAgeWomanMaxP()).append("%。将本次评估对象按照年龄和性别分布，其基本情况如下图和下表所示。").toString();
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportBasic.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象按年龄及性别分布";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		PdfPTable table_Page3 = new PdfPTable(new float[] {
			0.16F, 0.14F, 0.14F, 0.14F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page3.addCell(pdfCell2);
		table_Page3.addCell(createInner1("总体", "人数", "%", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page3.addCell(createInner1("男性", "人数", "%", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page3.addCell(createInner1("女性", "人数", "%", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("<30", font2, 1, TableLineColor));
		//总数（百分比），男（百分比），女（百分比）
		//System.out.println(groupReportBasic.age30Man + groupReportBasic.age30Woman+"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.age30Man + groupReportBasic.age30Woman))).append("\n").append((groupReportBasic.getAge30Man()+groupReportBasic.getAge30Woman())/(groupReportBasic.getAgeWomanMax()+groupReportBasic.getAgeManMax())).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge30Man()))).append("\n").append(groupReportBasic.getAge30ManP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge30Woman()))).append("\n").append(groupReportBasic.getAge30WomanP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.age30_40Man + groupReportBasic.age30_40Woman))).append("\n").append(groupReportBasic.getAge30_40Man()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge30_40Man()))).append("\n").append(groupReportBasic.getAge30_40ManP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge30_40Woman()))).append("\n").append(groupReportBasic.getAge30_40WomanP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.age40_45Man + groupReportBasic.age40_45Woman))).append("\n").append(groupReportBasic.getAge40_45MaxP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge40_45Man()))).append("\n").append(groupReportBasic.getAge40_45ManP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge40_45Woman()))).append("\n").append(groupReportBasic.getAge40_45WomanP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.age45_50Man + groupReportBasic.age45_50Woman))).append("\n").append(groupReportBasic.getAge45_50MaxP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge45_50Man()))).append("\n").append(groupReportBasic.getAge45_50ManP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge45_50Woman()))).append("\n").append(groupReportBasic.getAge45_50WomanP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("50-55", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.age50_55Man + groupReportBasic.age50_55Woman))).append("\n").append(groupReportBasic.getAge50_55MaxP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge50_55Man()))).append("\n").append(groupReportBasic.getAge50_55ManP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge50_55Woman()))).append("\n").append(groupReportBasic.getAge50_55WomanP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("55-60", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.age55_60Man + groupReportBasic.age55_60Woman))).append("\n").append(groupReportBasic.getAge55_60MaxP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge55_60Man()))).append("\n").append(groupReportBasic.getAge55_60ManP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge55_60Woman()))).append("\n").append(groupReportBasic.getAge55_60WomanP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.age60Man + groupReportBasic.age60Woman))).append("\n").append(groupReportBasic.getAge60MaxP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge60Man()))).append("\n").append(groupReportBasic.getAge60ManP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAge60Woman()))).append("\n").append(groupReportBasic.getAge60WomanP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getCount()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAgeManMax()))).append("\n").append(groupReportBasic.getAgeManMaxP()).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBasic.getAgeWomanMax()))).append("\n").append(groupReportBasic.getAgeWomanMaxP()).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page3, 7);
		pdfPTable.addCell(table_Page3);
		String descriptions = "基于您提供的有关健康信息及临床检查结果,得出您与同年龄同性别人群冠心病发病的风险等级。";
		if (!descriptions.equals(""))
		{
			str2 = (new StringBuilder("      在参与评估的")).append(100).append("人中，").append(descriptions).append("。对于已患有疾病的人群，建议定期到正规医院就诊，获得规范治疗，同时进行疾病管理控制病情、改善预后。个人患病情况如下表所示。").toString();
			table_Page2 = getContent(width_table_Page2, str2, font2);
			pdfPTable.addCell(table_Page2);
			str2 = "个人患病情况";
			table_Page2 = addRemard(width_table_Page2, font3, str2);
			pdfPTable.addCell(table_Page2);
			table_Page5 = new PdfPTable(new float[] {
				0.25F, 0.125F, 0.125F, 0.125F
			});
			pdfCell2 = PDFUtil.getCorderPdfPCellA("疾病名称", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
			pdfCell2.setVerticalAlignment(5);
			table_Page5.addCell(pdfCell2);
			pdfCell2 = PDFUtil.getCorderPdfPCellA("总体人数（百分比）", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
			pdfCell2.setVerticalAlignment(5);
			table_Page5.addCell(pdfCell2);
			pdfCell2 = PDFUtil.getCorderPdfPCellA("男性人数（百分比）", font3, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
			pdfCell2.setVerticalAlignment(5);
			table_Page5.addCell(pdfCell2);
			pdfCell2 = PDFUtil.getCorderPdfPCellA("女性人数（百分比）", font3, 1, 1, true, 0, 0, 0.0F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
			pdfCell2.setVerticalAlignment(5);
			table_Page5.addCell(pdfCell2);
			List disList = new ArrayList<>();
			String[] array = new String[] {"目前状况：吸烟  努力目标：戒烟", "目前状况：血压偏高  努力目标：降低血压至120/80mmHg以下", "目前状况：体力活动不足 努力目标：增加体力活动,至少达到每周5次,每次30分钟的中等体力活动", "目前状况：血压偏高  努力目标：降低血压至120/80mmHg以下"};
			String[] array1 = new String[] {"目前状况：蔬菜水果摄入不足  努力目标：增加水果蔬菜至500克/天以上", "目前状况：血压偏高  努力目标：降低血压至120/80mmHg以下", "目前状况：体力活动不足 努力目标：增加体力活动,至少达到每周5次,每次30分钟的中等体力活动", "目前状况：血压偏高  努力目标：降低血压至120/80mmHg以下"};
			String[] array2 = new String[] {"目前状况：蔬菜水果摄入不足  努力目标：增加水果蔬菜至500克/天以上", "目前状况：血压偏高  努力目标：降低血压至120/80mmHg以下", "目前状况：体力活动不足 努力目标：增加体力活动,至少达到每周5次,每次30分钟的中等体力活动", "目前状况：血压偏高  努力目标：降低血压至120/80mmHg以下"};
			
			disList.add(array);
			disList.add(array1);
			disList.add(array2);
			for (Iterator iterator = disList.iterator(); iterator.hasNext();)
			{
				String strings[] = (String[])iterator.next();
				if (!strings[0].equals("0"))
				{
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[1], font2, 1, TableLineColor));
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[2], font2, 1, TableLineColor));
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[3], font2, 1, TableLineColor));
					//table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[4], font2, 1, TableLineColor));
				}
			}

			addBlankCell(table_Page5, 7);
			pdfPTable.addCell(table_Page5);
		}
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	
	/*public PdfPTable groupReportHealthRisksPdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportHealthRisks groupReportHealthRisks = groupReport.getGroupReportHealthRisks();
		str1 = "疾病风险统计";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      对于还没有患上疾病的人群，疾病风险评估的结果分为高风险、较高风险、中等风险、较低风险和低风险5个风险等级，通过以对每种疾病不同评估结果进行详细的分析，可以清楚地了解疾病评估结果的整体分布情况。本次评估对象未来发病风险的汇总结果如下所示：";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		str2 = "评估对象各种疾病未来发病风险评估结果";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.15F, 0.6F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("疾病", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		PdfPTable iTable1 = new PdfPTable(new float[] {
			0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("评估结果", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		iTable1.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("高风险", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		iTable1.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("较高风险", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		iTable1.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("中等风险", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		iTable1.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("较低风险", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		iTable1.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("低风险", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		iTable1.addCell(pdfCell2);
		pdfCell2 = new PdfPCell(iTable1);
		table_Page5.addCell(pdfCell2);
		List riskList = groupReport.getGroupReportHealthRisks().getRiskList();
		for (Iterator iterator = riskList.iterator(); iterator.hasNext(); table_Page5.addCell(pdfCell2))
		{
			String strings[] = (String[])iterator.next();
			pdfCell2 = PDFUtil.getCorderPdfPCellA(strings[0], font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
			pdfCell2.setVerticalAlignment(5);
			table_Page5.addCell(pdfCell2);
			PdfPTable iTable = new PdfPTable(new float[] {
				0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F
			});
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder("实际(A)人数（百分比）", font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[1], font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[3], font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[5], font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[7], font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[9], font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder("理想(I)人数（百分比）", font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[2], font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[4], font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[6], font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[8], font2, 1, TableLineColor));
			iTable.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[10], font2, 1, TableLineColor));
			pdfCell2 = new PdfPCell(iTable);
		}

		addBlankCell(table_Page5, 9);
		pdfPTable.addCell(table_Page5);
		str2 = "注：实际风险：按照目前的危险因素水平，个体发生某种疾病的风险；理想风险：控制了可改变的危险因素后，预计的个体发病风险。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		str2 = "      以上分析显示了不同发病风险水平的人群比例构成，其中“实际风险”为“极高风险”和“高风险”水平的人群是需要重点干预的目标人群。“理想风险”是指通过有效的干预措施控制了各项可改变的危险因素后个体的理想发病风险，显示了干预措施的潜在成效。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportBodyMassIndexPdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportBodyMassIndex groupReportBodyMassIndex = groupReport.getGroupReportBodyMassIndex();
		str1 = "体重指数(BMI)";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      体重指数（body mass index）简称BMI，是国际公认的一种评定肥胖程度的方法。它的计算方法是：BMI＝体重(kg)\367身高的平方 (m2)。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		str2 = "      肥胖是高血压、冠心病、缺血性脑卒中、血脂异常、2型糖尿病发病的独立危险因素。适当减重可降低血压及胆固醇水平，从而降低冠心病、糖尿病等的发病几率。本次评估对象的体重情况按年龄及性别分布如下所示。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportBodyMassIndex.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象消瘦、超重及肥胖按年龄及性别分布（%）";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.07F, 0.07F, 0.07F, 0.07F, 0.07F, 0.07F, 0.07F, 0.07F, 0.07F, 0.07F, 
			0.07F, 0.07F, 0.07F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner("总体", "消瘦", "正常", "超重", "肥胖", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("男性", "消瘦", "正常", "超重", "肥胖", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("女性", "消瘦", "正常", "超重", "肥胖", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("＜30", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan30_thin() + groupReportBodyMassIndex.getWoman30_thin()))).append("\n").append(groupReportBodyMassIndex.getCount30_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan30_normal() + groupReportBodyMassIndex.getWoman30_normal()))).append("\n").append(groupReportBodyMassIndex.getCount30_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan30_overweight() + groupReportBodyMassIndex.getWoman30_overweight()))).append("\n").append(groupReportBodyMassIndex.getCount30_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan30_obesity() + groupReportBodyMassIndex.getWoman30_obesity()))).append("\n").append(groupReportBodyMassIndex.getCount30_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan30_thin()))).append("\n").append(groupReportBodyMassIndex.getMan30_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan30_normal()))).append("\n").append(groupReportBodyMassIndex.getMan30_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan30_overweight()))).append("\n").append(groupReportBodyMassIndex.getMan30_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan30_obesity()))).append("\n").append(groupReportBodyMassIndex.getMan30_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman30_thin()))).append("\n").append(groupReportBodyMassIndex.getWoman30_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman30_normal()))).append("\n").append(groupReportBodyMassIndex.getWoman30_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman30_overweight()))).append("\n").append(groupReportBodyMassIndex.getWoman30_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman30_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman30_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan40_thin() + groupReportBodyMassIndex.getWoman40_thin()))).append("\n").append(groupReportBodyMassIndex.getCount40_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan40_normal() + groupReportBodyMassIndex.getWoman40_normal()))).append("\n").append(groupReportBodyMassIndex.getCount40_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan40_overweight() + groupReportBodyMassIndex.getWoman40_overweight()))).append("\n").append(groupReportBodyMassIndex.getCount40_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan40_obesity() + groupReportBodyMassIndex.getWoman40_obesity()))).append("\n").append(groupReportBodyMassIndex.getCount40_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan40_thin()))).append("\n").append(groupReportBodyMassIndex.getMan40_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan40_normal()))).append("\n").append(groupReportBodyMassIndex.getMan40_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan40_overweight()))).append("\n").append(groupReportBodyMassIndex.getMan40_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan40_obesity()))).append("\n").append(groupReportBodyMassIndex.getMan40_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman40_thin()))).append("\n").append(groupReportBodyMassIndex.getWoman40_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman40_normal()))).append("\n").append(groupReportBodyMassIndex.getWoman40_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman40_overweight()))).append("\n").append(groupReportBodyMassIndex.getWoman40_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman40_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman40_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan45_thin() + groupReportBodyMassIndex.getWoman45_thin()))).append("\n").append(groupReportBodyMassIndex.getCount45_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan45_normal() + groupReportBodyMassIndex.getWoman45_normal()))).append("\n").append(groupReportBodyMassIndex.getCount45_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan45_overweight() + groupReportBodyMassIndex.getWoman45_overweight()))).append("\n").append(groupReportBodyMassIndex.getCount45_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan45_obesity() + groupReportBodyMassIndex.getWoman45_obesity()))).append("\n").append(groupReportBodyMassIndex.getCount45_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan45_thin()))).append("\n").append(groupReportBodyMassIndex.getMan45_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan45_normal()))).append("\n").append(groupReportBodyMassIndex.getMan45_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan45_overweight()))).append("\n").append(groupReportBodyMassIndex.getMan45_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan45_obesity()))).append("\n").append(groupReportBodyMassIndex.getMan45_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman45_thin()))).append("\n").append(groupReportBodyMassIndex.getWoman45_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman45_normal()))).append("\n").append(groupReportBodyMassIndex.getWoman45_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman45_overweight()))).append("\n").append(groupReportBodyMassIndex.getWoman45_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman45_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman45_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan50_thin() + groupReportBodyMassIndex.getWoman50_thin()))).append("\n").append(groupReportBodyMassIndex.getCount50_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan50_normal() + groupReportBodyMassIndex.getWoman50_normal()))).append("\n").append(groupReportBodyMassIndex.getCount50_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan50_overweight() + groupReportBodyMassIndex.getWoman50_overweight()))).append("\n").append(groupReportBodyMassIndex.getCount50_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan50_obesity() + groupReportBodyMassIndex.getWoman50_obesity()))).append("\n").append(groupReportBodyMassIndex.getCount50_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan50_thin()))).append("\n").append(groupReportBodyMassIndex.getMan50_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan50_normal()))).append("\n").append(groupReportBodyMassIndex.getMan50_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan50_overweight()))).append("\n").append(groupReportBodyMassIndex.getMan50_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan50_obesity()))).append("\n").append(groupReportBodyMassIndex.getMan50_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman50_thin()))).append("\n").append(groupReportBodyMassIndex.getWoman50_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman50_normal()))).append("\n").append(groupReportBodyMassIndex.getWoman50_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman50_overweight()))).append("\n").append(groupReportBodyMassIndex.getWoman50_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman50_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman50_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("50－55", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan55_thin() + groupReportBodyMassIndex.getWoman55_thin()))).append("\n").append(groupReportBodyMassIndex.getCount55_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan55_normal() + groupReportBodyMassIndex.getWoman55_normal()))).append("\n").append(groupReportBodyMassIndex.getCount55_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan55_overweight() + groupReportBodyMassIndex.getWoman55_overweight()))).append("\n").append(groupReportBodyMassIndex.getCount55_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan55_obesity() + groupReportBodyMassIndex.getWoman55_obesity()))).append("\n").append(groupReportBodyMassIndex.getCount55_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan55_thin()))).append("\n").append(groupReportBodyMassIndex.getMan55_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan55_normal()))).append("\n").append(groupReportBodyMassIndex.getMan55_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan55_overweight()))).append("\n").append(groupReportBodyMassIndex.getMan55_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan55_obesity()))).append("\n").append(groupReportBodyMassIndex.getMan55_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman55_thin()))).append("\n").append(groupReportBodyMassIndex.getWoman55_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman55_normal()))).append("\n").append(groupReportBodyMassIndex.getWoman55_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman55_overweight()))).append("\n").append(groupReportBodyMassIndex.getWoman55_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman55_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman55_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("55－60", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan60_thin() + groupReportBodyMassIndex.getWoman60_thin()))).append("\n").append(groupReportBodyMassIndex.getCount60_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan60_normal() + groupReportBodyMassIndex.getWoman60_normal()))).append("\n").append(groupReportBodyMassIndex.getCount60_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan60_overweight() + groupReportBodyMassIndex.getWoman60_overweight()))).append("\n").append(groupReportBodyMassIndex.getCount60_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan60_obesity() + groupReportBodyMassIndex.getWoman60_obesity()))).append("\n").append(groupReportBodyMassIndex.getCount60_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan60_thin()))).append("\n").append(groupReportBodyMassIndex.getMan60_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan60_normal()))).append("\n").append(groupReportBodyMassIndex.getMan60_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan60_overweight()))).append("\n").append(groupReportBodyMassIndex.getMan60_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan60_obesity()))).append("\n").append(groupReportBodyMassIndex.getMan60_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman60_thin()))).append("\n").append(groupReportBodyMassIndex.getWoman60_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman60_normal()))).append("\n").append(groupReportBodyMassIndex.getWoman60_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman60_overweight()))).append("\n").append(groupReportBodyMassIndex.getWoman60_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman60_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman60_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan70_thin() + groupReportBodyMassIndex.getWoman70_thin()))).append("\n").append(groupReportBodyMassIndex.getCount70_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan70_normal() + groupReportBodyMassIndex.getWoman70_normal()))).append("\n").append(groupReportBodyMassIndex.getCount70_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan70_overweight() + groupReportBodyMassIndex.getWoman70_overweight()))).append("\n").append(groupReportBodyMassIndex.getCount70_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan70_obesity() + groupReportBodyMassIndex.getWoman70_obesity()))).append("\n").append(groupReportBodyMassIndex.getCount70_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan70_thin()))).append("\n").append(groupReportBodyMassIndex.getMan70_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan70_normal()))).append("\n").append(groupReportBodyMassIndex.getMan70_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan70_overweight()))).append("\n").append(groupReportBodyMassIndex.getMan70_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan70_obesity()))).append("\n").append(groupReportBodyMassIndex.getMan70_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman70_thin()))).append("\n").append(groupReportBodyMassIndex.getWoman70_thinP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman70_normal()))).append("\n").append(groupReportBodyMassIndex.getWoman70_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman70_overweight()))).append("\n").append(groupReportBodyMassIndex.getWoman70_overweightP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman70_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman70_obesityP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计: ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getManThin() + groupReportBodyMassIndex.getWomanThin()))).append("\n").append(groupReportBodyMassIndex.getAllThinMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getManNormal() + groupReportBodyMassIndex.getWomanNormal()))).append("\n").append(groupReportBodyMassIndex.getAllNormalMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getManOverweight() + groupReportBodyMassIndex.getWomanOverweight()))).append("\n").append(groupReportBodyMassIndex.getAllOverweightMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getManobesity() + groupReportBodyMassIndex.getWomanobesity()))).append("\n").append(groupReportBodyMassIndex.getAllobesityMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getManThin()))).append("\n").append(groupReportBodyMassIndex.getManThinMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getManNormal()))).append("\n").append(groupReportBodyMassIndex.getManNormalMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getManOverweight()))).append("\n").append(groupReportBodyMassIndex.getManOverweightMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getManobesity()))).append("\n").append(groupReportBodyMassIndex.getManobesityMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWomanThin()))).append("\n").append(groupReportBodyMassIndex.getWomanThinMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWomanThin()))).append("\n").append(groupReportBodyMassIndex.getWomanNormalMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWomanThin()))).append("\n").append(groupReportBodyMassIndex.getWomanOverweightMax()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWomanThin()))).append("\n").append(groupReportBodyMassIndex.getWomanobesityMax()).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 13);
		pdfPTable.addCell(table_Page5);
		str2 = "注: 消瘦：BMI＜18.5； 正常：18.5≤BMI＜24； 超重：24≤BMI＜28；肥胖：BMI≥28";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportWaistlinePdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportWaistline groupReportWaistline = groupReport.getGroupReportWaistline();
		str1 = "腰围情况";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      与其他的体格指标相比，腰围是人体是否存在胰岛素抵抗最重要的标志；腰围过大是糖尿病的独立危险因素。调查对象的腰围统计结果按年龄、性别分布如下所示： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportWaistline.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象腰围按年龄及性别分布";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.16F, 0.07F, 0.07F, 0.07F, 0.07F, 0.07F, 0.07F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner("总体", "正常", "过大", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("男性", "正常", "过大", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("女性", "正常", "过大", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("＜30 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan30_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount30_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan30_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount30_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan30_normal()))).append("\n").append(groupReportWaistline.getMan30_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan30_high_risk()))).append("\n").append(groupReportWaistline.getMan30_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getWoman30_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getWoman30_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan40_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount40_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan40_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount40_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan40_normal()))).append("\n").append(groupReportWaistline.getMan40_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan40_high_risk()))).append("\n").append(groupReportWaistline.getMan40_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman40_normal()))).append("\n").append(groupReportWaistline.getWoman40_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman40_high_risk()))).append("\n").append(groupReportWaistline.getWoman40_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan45_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount45_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan45_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount45_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan45_normal()))).append("\n").append(groupReportWaistline.getMan45_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan45_high_risk()))).append("\n").append(groupReportWaistline.getMan45_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman45_normal()))).append("\n").append(groupReportWaistline.getWoman45_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman45_high_risk()))).append("\n").append(groupReportWaistline.getWoman45_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan50_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount50_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan50_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount50_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan50_normal()))).append("\n").append(groupReportWaistline.getMan50_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan50_high_risk()))).append("\n").append(groupReportWaistline.getMan50_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman50_normal()))).append("\n").append(groupReportWaistline.getWoman50_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman50_high_risk()))).append("\n").append(groupReportWaistline.getWoman50_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("50－55 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan55_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount55_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan55_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount55_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan55_normal()))).append("\n").append(groupReportWaistline.getMan55_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan55_high_risk()))).append("\n").append(groupReportWaistline.getMan55_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman55_normal()))).append("\n").append(groupReportWaistline.getWoman55_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman55_high_risk()))).append("\n").append(groupReportWaistline.getWoman55_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("55－60 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan60_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount60_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan60_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount60_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan60_normal()))).append("\n").append(groupReportWaistline.getMan60_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan60_high_risk()))).append("\n").append(groupReportWaistline.getMan60_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman60_normal()))).append("\n").append(groupReportWaistline.getWoman60_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman60_high_risk()))).append("\n").append(groupReportWaistline.getWoman60_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan70_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount70_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan70_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount70_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan70_normal()))).append("\n").append(groupReportWaistline.getMan70_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan70_high_risk()))).append("\n").append(groupReportWaistline.getMan70_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman70_normal()))).append("\n").append(groupReportWaistline.getWoman70_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman70_high_risk()))).append("\n").append(groupReportWaistline.getWoman70_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan_normal_count() + groupReportWaistline.getWoman_normal_count()))).append("\n").append(groupReportWaistline.getNormal()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan_high_risk_count() + groupReportWaistline.getWoman_high_risk_count()))).append("\n").append(groupReportWaistline.getHigh_risk()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan_normal_count()))).append("\n").append(groupReportWaistline.getMan_normal()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan_high_risk_count()))).append("\n").append(groupReportWaistline.getMan_high_risk()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman_normal_count()))).append("\n").append(groupReportWaistline.getWoman_normal()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman_high_risk_count()))).append("\n").append(groupReportWaistline.getWoman_high_risk()).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 13);
		pdfPTable.addCell(table_Page5);
		str2 = "注: 正常：男性<85cm，女性<80cm； 过大：男性≥85cm，女性≥80cm。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportBloodPressurePdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportBloodPressure groupReportBloodPressure = groupReport.getGroupReportBloodPressure();
		str1 = "血压情况";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      高血压是引起心脑血管疾病最重要的危险因素，其并发症脑卒中、冠心病、心力衰竭、肾功能衰竭等疾患具有高度的致死率和致残率，严重危害人类健康。评估对象血压按年龄及性别具体分布如下所示： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportBloodPressure.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "对象的血压检测结果按年龄及性别分布如下所示";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.163F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner("总体", "正常血压", "高值血压", "高血压", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("男性", "正常血压", "高值血压", "高血压", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("女性", "正常血压", "高值血压", "高血压", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("＜30 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan30_low() + groupReportBloodPressure.getWoman30_low()))).append("\n").append(groupReportBloodPressure.getAll30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan30_in() + groupReportBloodPressure.getWoman30_in()))).append("\n").append(groupReportBloodPressure.getAll30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan30_high() + groupReportBloodPressure.getWoman30_high()))).append("\n").append(groupReportBloodPressure.getAll30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan30_low()))).append("\n").append(groupReportBloodPressure.getMan30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan30_in()))).append("\n").append(groupReportBloodPressure.getMan30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan30_high()))).append("\n").append(groupReportBloodPressure.getMan30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman30_low()))).append("\n").append(groupReportBloodPressure.getWoman30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman30_in()))).append("\n").append(groupReportBloodPressure.getWoman30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman30_high()))).append("\n").append(groupReportBloodPressure.getWoman30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_low() + groupReportBloodPressure.getWoman40_low()))).append("\n").append(groupReportBloodPressure.getAll40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_in() + groupReportBloodPressure.getWoman40_in()))).append("\n").append(groupReportBloodPressure.getAll40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_high() + groupReportBloodPressure.getWoman40_high()))).append("\n").append(groupReportBloodPressure.getAll40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_low()))).append("\n").append(groupReportBloodPressure.getMan40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_in()))).append("\n").append(groupReportBloodPressure.getMan40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_high()))).append("\n").append(groupReportBloodPressure.getMan40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman40_low()))).append("\n").append(groupReportBloodPressure.getWoman40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman40_in()))).append("\n").append(groupReportBloodPressure.getWoman40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman40_high()))).append("\n").append(groupReportBloodPressure.getWoman40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_low() + groupReportBloodPressure.getWoman45_low()))).append("\n").append(groupReportBloodPressure.getAll45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_in() + groupReportBloodPressure.getWoman45_in()))).append("\n").append(groupReportBloodPressure.getAll45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_high() + groupReportBloodPressure.getWoman45_high()))).append("\n").append(groupReportBloodPressure.getAll45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_low()))).append("\n").append(groupReportBloodPressure.getMan45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_in()))).append("\n").append(groupReportBloodPressure.getMan45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_high()))).append("\n").append(groupReportBloodPressure.getMan45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman45_low()))).append("\n").append(groupReportBloodPressure.getWoman45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman45_in()))).append("\n").append(groupReportBloodPressure.getWoman45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman45_high()))).append("\n").append(groupReportBloodPressure.getWoman45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_low() + groupReportBloodPressure.getWoman50_low()))).append("\n").append(groupReportBloodPressure.getAll50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_in() + groupReportBloodPressure.getWoman50_in()))).append("\n").append(groupReportBloodPressure.getAll50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_high() + groupReportBloodPressure.getWoman50_high()))).append("\n").append(groupReportBloodPressure.getAll50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_low()))).append("\n").append(groupReportBloodPressure.getMan50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_in()))).append("\n").append(groupReportBloodPressure.getMan50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_high()))).append("\n").append(groupReportBloodPressure.getMan50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman50_low()))).append("\n").append(groupReportBloodPressure.getWoman50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman50_in()))).append("\n").append(groupReportBloodPressure.getWoman50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman50_high()))).append("\n").append(groupReportBloodPressure.getWoman50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("50-55 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_low() + groupReportBloodPressure.getWoman55_low()))).append("\n").append(groupReportBloodPressure.getAll55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_in() + groupReportBloodPressure.getWoman55_in()))).append("\n").append(groupReportBloodPressure.getAll55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_high() + groupReportBloodPressure.getWoman55_high()))).append("\n").append(groupReportBloodPressure.getAll55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_low()))).append("\n").append(groupReportBloodPressure.getMan55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_in()))).append("\n").append(groupReportBloodPressure.getMan55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_high()))).append("\n").append(groupReportBloodPressure.getMan55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman55_low()))).append("\n").append(groupReportBloodPressure.getWoman55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman55_in()))).append("\n").append(groupReportBloodPressure.getWoman55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman55_high()))).append("\n").append(groupReportBloodPressure.getWoman55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("55-60", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_low() + groupReportBloodPressure.getWoman60_low()))).append("\n").append(groupReportBloodPressure.getAll60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_in() + groupReportBloodPressure.getWoman60_in()))).append("\n").append(groupReportBloodPressure.getAll60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_high() + groupReportBloodPressure.getWoman60_high()))).append("\n").append(groupReportBloodPressure.getAll60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_low()))).append("\n").append(groupReportBloodPressure.getMan60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_in()))).append("\n").append(groupReportBloodPressure.getMan60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_high()))).append("\n").append(groupReportBloodPressure.getMan60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman60_low()))).append("\n").append(groupReportBloodPressure.getWoman60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman60_in()))).append("\n").append(groupReportBloodPressure.getWoman60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman60_high()))).append("\n").append(groupReportBloodPressure.getWoman60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_low() + groupReportBloodPressure.getWoman70_low()))).append("\n").append(groupReportBloodPressure.getAll70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_in() + groupReportBloodPressure.getWoman70_in()))).append("\n").append(groupReportBloodPressure.getAll70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_high() + groupReportBloodPressure.getWoman70_high()))).append("\n").append(groupReportBloodPressure.getAll70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_low()))).append("\n").append(groupReportBloodPressure.getMan70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_in()))).append("\n").append(groupReportBloodPressure.getMan70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_high()))).append("\n").append(groupReportBloodPressure.getMan70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman70_low()))).append("\n").append(groupReportBloodPressure.getWoman70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman70_in()))).append("\n").append(groupReportBloodPressure.getWoman70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman70_high()))).append("\n").append(groupReportBloodPressure.getWoman70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计  ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getAll_low()))).append("\n").append(groupReportBloodPressure.getAll_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getAll_in()))).append("\n").append(groupReportBloodPressure.getAll_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getAll_high()))).append("\n").append(groupReportBloodPressure.getAll_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan_low()))).append("\n").append(groupReportBloodPressure.getMan_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan_in()))).append("\n").append(groupReportBloodPressure.getMan_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan_high()))).append("\n").append(groupReportBloodPressure.getMan_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman_low()))).append("\n").append(groupReportBloodPressure.getWoman_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman_in()))).append("\n").append(groupReportBloodPressure.getWoman_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman_high()))).append("\n").append(groupReportBloodPressure.getWoman_high_max()).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 10);
		pdfPTable.addCell(table_Page5);
		str2 = "正常血压：收缩压<120 mmHg且舒张压<80 mmHg； 高值血压：120≤收缩压<140 mmHg，或80≤舒张压<90 mmHg；高血压：收缩压≥140 mmHg，或舒张压≥90 mmHg。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportBloodSugarPdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportBloodSugar groupReportBloodSugar = groupReport.getGroupReportBloodSugar();
		str1 = "血糖情况 ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      血糖水平越高，患糖尿病的危险性就越大，糖尿病易并发大血管和微血管病变。评估对象的血糖检测结果分布如下所示：  ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportBloodSugar.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象血糖检测结果按年龄及性别分布（%）";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		Font font3_temp = font3;
		font3_temp.setSize(8F);
		table_Page5 = new PdfPTable(new float[] {
			0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3_temp, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner("总体", "正常血糖", "糖尿病前期", "可疑糖尿病", font3_temp, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("男性", "正常血糖", "糖尿病前期", "可疑糖尿病", font3_temp, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("女性", "正常血糖", "糖尿病前期", "可疑糖尿病", font3_temp, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("＜30 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_low() + groupReportBloodSugar.getWoman30_low()))).append("\n").append(groupReportBloodSugar.getAll30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_in() + groupReportBloodSugar.getWoman30_in()))).append("\n").append(groupReportBloodSugar.getAll30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_high() + groupReportBloodSugar.getWoman30_high()))).append("\n").append(groupReportBloodSugar.getAll30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_low()))).append("\n").append(groupReportBloodSugar.getMan30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_in()))).append("\n").append(groupReportBloodSugar.getMan30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_high()))).append("\n").append(groupReportBloodSugar.getMan30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman30_low()))).append("\n").append(groupReportBloodSugar.getWoman30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman30_in()))).append("\n").append(groupReportBloodSugar.getWoman30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman30_high()))).append("\n").append(groupReportBloodSugar.getWoman30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_low() + groupReportBloodSugar.getWoman40_low()))).append("\n").append(groupReportBloodSugar.getAll40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_in() + groupReportBloodSugar.getWoman40_in()))).append("\n").append(groupReportBloodSugar.getAll40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_high() + groupReportBloodSugar.getWoman40_high()))).append("\n").append(groupReportBloodSugar.getAll40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_low()))).append("\n").append(groupReportBloodSugar.getMan40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_in()))).append("\n").append(groupReportBloodSugar.getMan40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_high()))).append("\n").append(groupReportBloodSugar.getMan40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman40_low()))).append("\n").append(groupReportBloodSugar.getWoman40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman40_in()))).append("\n").append(groupReportBloodSugar.getWoman40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman40_high()))).append("\n").append(groupReportBloodSugar.getWoman40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_low() + groupReportBloodSugar.getWoman45_low()))).append("\n").append(groupReportBloodSugar.getAll45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_in() + groupReportBloodSugar.getWoman45_in()))).append("\n").append(groupReportBloodSugar.getAll45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_high() + groupReportBloodSugar.getWoman45_high()))).append("\n").append(groupReportBloodSugar.getAll45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_low()))).append("\n").append(groupReportBloodSugar.getMan45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_in()))).append("\n").append(groupReportBloodSugar.getMan45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_high()))).append("\n").append(groupReportBloodSugar.getMan45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman45_low()))).append("\n").append(groupReportBloodSugar.getWoman45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman45_in()))).append("\n").append(groupReportBloodSugar.getWoman45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman45_high()))).append("\n").append(groupReportBloodSugar.getWoman45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_low() + groupReportBloodSugar.getWoman50_low()))).append("\n").append(groupReportBloodSugar.getAll50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_in() + groupReportBloodSugar.getWoman50_in()))).append("\n").append(groupReportBloodSugar.getAll50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_high() + groupReportBloodSugar.getWoman50_high()))).append("\n").append(groupReportBloodSugar.getAll50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_low()))).append("\n").append(groupReportBloodSugar.getMan50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_in()))).append("\n").append(groupReportBloodSugar.getMan50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_high()))).append("\n").append(groupReportBloodSugar.getMan50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman50_low()))).append("\n").append(groupReportBloodSugar.getWoman50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman50_in()))).append("\n").append(groupReportBloodSugar.getWoman50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman50_high()))).append("\n").append(groupReportBloodSugar.getWoman50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("50-55 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_low() + groupReportBloodSugar.getWoman55_low()))).append("\n").append(groupReportBloodSugar.getAll55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_in() + groupReportBloodSugar.getWoman55_in()))).append("\n").append(groupReportBloodSugar.getAll55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_high() + groupReportBloodSugar.getWoman55_high()))).append("\n").append(groupReportBloodSugar.getAll55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_low()))).append("\n").append(groupReportBloodSugar.getMan55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_in()))).append("\n").append(groupReportBloodSugar.getMan55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_high()))).append("\n").append(groupReportBloodSugar.getMan55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman55_low()))).append("\n").append(groupReportBloodSugar.getWoman55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman55_in()))).append("\n").append(groupReportBloodSugar.getWoman55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman55_high()))).append("\n").append(groupReportBloodSugar.getWoman55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("55-60 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_low() + groupReportBloodSugar.getWoman60_low()))).append("\n").append(groupReportBloodSugar.getAll60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_in() + groupReportBloodSugar.getWoman60_in()))).append("\n").append(groupReportBloodSugar.getAll60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_high() + groupReportBloodSugar.getWoman60_high()))).append("\n").append(groupReportBloodSugar.getAll60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_low()))).append("\n").append(groupReportBloodSugar.getMan60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_in()))).append("\n").append(groupReportBloodSugar.getMan60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_high()))).append("\n").append(groupReportBloodSugar.getMan60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman60_low()))).append("\n").append(groupReportBloodSugar.getWoman60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman60_in()))).append("\n").append(groupReportBloodSugar.getWoman60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman60_high()))).append("\n").append(groupReportBloodSugar.getWoman60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_low() + groupReportBloodSugar.getWoman70_low()))).append("\n").append(groupReportBloodSugar.getAll70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_in() + groupReportBloodSugar.getWoman70_in()))).append("\n").append(groupReportBloodSugar.getAll70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_high() + groupReportBloodSugar.getWoman70_high()))).append("\n").append(groupReportBloodSugar.getAll70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_low()))).append("\n").append(groupReportBloodSugar.getMan70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_in()))).append("\n").append(groupReportBloodSugar.getMan70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_high()))).append("\n").append(groupReportBloodSugar.getMan70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman70_low()))).append("\n").append(groupReportBloodSugar.getWoman70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman70_in()))).append("\n").append(groupReportBloodSugar.getWoman70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman70_high()))).append("\n").append(groupReportBloodSugar.getWoman70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getAll_low()))).append("\n").append(groupReportBloodSugar.getAll_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getAll_in()))).append("\n").append(groupReportBloodSugar.getAll_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getAll_high()))).append("\n").append(groupReportBloodSugar.getAll_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan_low()))).append("\n").append(groupReportBloodSugar.getMan_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan_in()))).append("\n").append(groupReportBloodSugar.getMan_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan_high()))).append("\n").append(groupReportBloodSugar.getMan_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman_low()))).append("\n").append(groupReportBloodSugar.getWoman_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman_in()))).append("\n").append(groupReportBloodSugar.getWoman_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman_high()))).append("\n").append(groupReportBloodSugar.getWoman_high_max()).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 10);
		pdfPTable.addCell(table_Page5);
		str2 = "注：正常血糖： 3.9≤空腹血糖＜5.6mmol/l；糖尿病前期：5.6≤空腹血糖＜7.0mmol/l；可疑糖尿病：空腹血糖≥7.0mmol/l。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportLipidPdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportLipid groupReportLipid = groupReport.getGroupReportLipid();
		str1 = "血脂异常 ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "     人体血液中脂质代谢失调称为血脂异常，它与多种疾病有着密切的关系，是导致冠心病、中风、糖尿病、动脉粥样硬化的主要危险因素之一。本次评估对象的血脂检测结果按性别分布如下所示：  ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportLipid.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象血脂检测结果按性别分布";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.28F, 0.12F, 0.12F, 0.12F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("血脂异常分类", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner1("总体人数（百分比）", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(createInner1("男性人数（百分比）", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(createInner1("女性人数（百分比）", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("高总胆固醇", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getTc_man() + groupReportLipid.getTc_woman()))).append("\n").append(groupReportLipid.getTc_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getTc_man()))).append("\n").append(groupReportLipid.getTc_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getTc_woman()))).append("\n").append(groupReportLipid.getTc_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("高甘油三酯", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getTriglyceride_man() + groupReportLipid.getTriglyceride_woman()))).append("\n").append(groupReportLipid.getTriglyceride_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getTriglyceride_man()))).append("\n").append(groupReportLipid.getTriglyceride_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getTriglyceride_woman()))).append("\n").append(groupReportLipid.getTriglyceride_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("低HDL胆固醇", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getHdl_man() + groupReportLipid.getHdl_woman()))).append("\n").append(groupReportLipid.getHdl_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getHdl_man()))).append("\n").append(groupReportLipid.getHdl_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getHdl_woman()))).append("\n").append(groupReportLipid.getHdl_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("高LDL胆固醇", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getLdl_man() + groupReportLipid.getLdl_woman()))).append("\n").append(groupReportLipid.getLdl_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getLdl_man()))).append("\n").append(groupReportLipid.getLdl_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getLdl_woman()))).append("\n").append(groupReportLipid.getLdl_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("总血脂异常", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getAll_num()))).append("\n").append(groupReportLipid.getAll_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getMan_num()))).append("\n").append(groupReportLipid.getMan_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportLipid.getWoman_num()))).append("\n").append(groupReportLipid.getWoman_max()).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 7);
		pdfPTable.addCell(table_Page5);
		str2 = "注：高总胆固醇≥5.18mmol/L；高甘油三脂≥1.7mmol/L；低HDL胆固醇<1.04mmol/L；高LDL胆固醇≥3.37mmol/L；";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportMealHabitPdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportMealHabit groupReportMealHabit = groupReport.getGroupReportMealHabit();
		str1 = "膳食习惯 ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      中国居民膳食指南建议：食物多样，粗细搭配，多吃蔬菜水果和薯类，常吃适量的鱼、禽、蛋和瘦肉等。不良的膳食习惯不仅包括每天摄入的总量过多，更重要的是膳食结构的不合理。本次评估对象的膳食习惯按性别分布如下所示： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportMealHabit.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "膳食习惯危险因素按性别分布";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.28F, 0.12F, 0.12F, 0.12F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("危险因素", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner1("总体", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(createInner1("男性", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(createInner1("女性", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("谷类摄入过多", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportMealHabit.getCereal_man() + groupReportMealHabit.getCereal_woman()))).append("\n").append(groupReportMealHabit.getCreal_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportMealHabit.getCereal_man()))).append("\n").append(groupReportMealHabit.getCereal_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportMealHabit.getCereal_woman()))).append("\n").append(groupReportMealHabit.getCereal_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("蔬菜水果摄入不足", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportMealHabit.getFruits_vegetables_man() + groupReportMealHabit.getFruits_vegetables_woman()))).append("\n").append(groupReportMealHabit.getFruits_vegetables_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportMealHabit.getFruits_vegetables_man()))).append("\n").append(groupReportMealHabit.getFruits_vegetables_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportMealHabit.getFruits_vegetables_woman()))).append("\n").append(groupReportMealHabit.getFruits_vegetables_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("肉类摄入过多", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportMealHabit.getMeat_man() + groupReportMealHabit.getMeat_woman()))).append("\n").append(groupReportMealHabit.getMeat_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportMealHabit.getMeat_man()))).append("\n").append(groupReportMealHabit.getMeat_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportMealHabit.getMeat_woman()))).append("\n").append(groupReportMealHabit.getMeat_woman_max()).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 7);
		pdfPTable.addCell(table_Page5);
		str2 = "注：谷类摄入过多：＞400克/天； 蔬菜水果摄入不足：蔬菜水果总量＜500克/天； 肉类摄入过多：＞75克/天。 ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportSmokePdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportSmoke groupReportSmoke = groupReport.getGroupReportSmoke();
		str1 = "吸烟情况  ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "     吸烟是目前危害人类健康最严重的不良行为之一，吸烟越多危害性越大，但几乎是一旦停止吸烟，戒烟的好处既是立竿见影的也是长期的。本次评估对象的吸烟情况如下所示： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportSmoke.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象吸烟情况按性别分布";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.28F, 0.12F, 0.12F, 0.12F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("吸烟状况", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner1("总体", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(createInner1("男性", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(createInner1("女性", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("吸烟", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_man() + groupReportSmoke.getSmoke_woman()))).append("\n").append(groupReportSmoke.getSmoke_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_man()))).append("\n").append(groupReportSmoke.getSmoke_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_woman()))).append("\n").append(groupReportSmoke.getSmoke_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("不吸烟", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_no_man() + groupReportSmoke.getSmoke_no_woman()))).append("\n").append(groupReportSmoke.getSmoke_all_no_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_no_man()))).append("\n").append(groupReportSmoke.getSmoke_no_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_no_woman()))).append("\n").append(groupReportSmoke.getSmoke_no_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("戒烟", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_cessation_man() + groupReportSmoke.getSmoke_cessation_woman()))).append("\n").append(groupReportSmoke.getSmoke_all_cessation_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_cessation_man()))).append("\n").append(groupReportSmoke.getSmoke_cessation_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_cessation_woman()))).append("\n").append(groupReportSmoke.getSmoke_cessation_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_man() + groupReportSmoke.getSmoke_woman() + groupReportSmoke.getSmoke_no_man() + groupReportSmoke.getSmoke_no_woman() + groupReportSmoke.getSmoke_cessation_man() + groupReportSmoke.getSmoke_cessation_woman()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_man() + groupReportSmoke.getSmoke_no_man() + groupReportSmoke.getSmoke_cessation_man()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportSmoke.getSmoke_woman() + groupReportSmoke.getSmoke_no_woman() + groupReportSmoke.getSmoke_cessation_woman()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 7);
		pdfPTable.addCell(table_Page5);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportDrinkingPdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportDrinking groupReportDrinking = groupReport.getGroupReportDrinking();
		str1 = "饮酒情况   ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "     无节制的饮酒，会使食欲下降，食物摄入量减少，以致发生多种营养缺乏、急慢性酒精中毒、酒精性脂肪肝，严重时还会造成酒精性肝硬化。过量饮酒还会增加患高血压、中风等疾病的危险。另外，饮酒还会增加患某些癌症的危险。若饮酒应尽可能饮用低度酒，并控制在适量范围内。 本次评估对象饮酒情况统计结果如下所示： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportDrinking.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象饮酒情况按性别分布";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.28F, 0.12F, 0.12F, 0.12F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("饮酒状况", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner1("总体", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(createInner1("男性", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(createInner1("女性", "人数", "%", font3, color1, TableLineColor, 0.1F, 0.1F, 0.1F, 0.1F));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("过量饮酒", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_high_man() + groupReportDrinking.getDrinking_high_woman()))).append("\n").append(groupReportDrinking.getDrinking_high_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_high_man()))).append("\n").append(groupReportDrinking.getDrinking_high_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_high_woman()))).append("\n").append(groupReportDrinking.getDrinking_high_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("适量饮酒", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_in_man() + groupReportDrinking.getDrinking_in_woman()))).append("\n").append(groupReportDrinking.getDrinking_in_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_in_man()))).append("\n").append(groupReportDrinking.getDrinking_in_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_in_woman()))).append("\n").append(groupReportDrinking.getDrinking_in_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("不饮酒", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_low_man() + groupReportDrinking.getDrinking_low_woman()))).append("\n").append(groupReportDrinking.getDrinking_low_all_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_low_man()))).append("\n").append(groupReportDrinking.getDrinking_low_man_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_low_woman()))).append("\n").append(groupReportDrinking.getDrinking_low_woman_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_high_man() + groupReportDrinking.getDrinking_high_woman() + groupReportDrinking.getDrinking_in_man() + groupReportDrinking.getDrinking_in_woman() + groupReportDrinking.getDrinking_low_man() + groupReportDrinking.getDrinking_low_woman()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_high_man() + groupReportDrinking.getDrinking_in_man() + groupReportDrinking.getDrinking_low_man()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportDrinking.getDrinking_high_woman() + groupReportDrinking.getDrinking_in_woman() + groupReportDrinking.getDrinking_low_woman()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 7);
		pdfPTable.addCell(table_Page5);
		str2 = "注：适量饮酒：酒精量，男性＜25g/天，女性＜15g/天；过量饮酒：酒精量，男性≥25g/天，女性≥15g/天； ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportPhysicalActivityPdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportPhysicalActivity groupReportPhysicalActivity = groupReport.getGroupReportPhysicalActivity();
		str1 = "体力活动水平 ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      健康的生活方式应包括有规律的、强度适当的体力活动，这是最有效的预防慢性疾病的方法，因为适当的体力活动可以降低患慢性病的危险性，对身体的多个器官有益。指南建议：对于一般成年人，每周应至少5天进行30分钟以上中等强度体力活动，每周累计活动时间至少达到150分钟。本次评估对象的体力活动水平统计结果如下所示。  ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportPhysicalActivity.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象体力活动水平按年龄及性别分布（%）";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.163F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F, 0.093F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner("总体", "不足", "中等", "充分", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("男性", "不足", "中等", "充分", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("女性", "不足", "中等", "充分", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("＜30 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan30_high() + groupReportPhysicalActivity.getWoman30_high()))).append("\n").append(groupReportPhysicalActivity.getAll30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan30_in() + groupReportPhysicalActivity.getWoman30_in()))).append("\n").append(groupReportPhysicalActivity.getAll30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan30_low() + groupReportPhysicalActivity.getWoman30_low()))).append("\n").append(groupReportPhysicalActivity.getAll30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan30_high()))).append("\n").append(groupReportPhysicalActivity.getMan30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan30_in()))).append("\n").append(groupReportPhysicalActivity.getMan30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan30_low()))).append("\n").append(groupReportPhysicalActivity.getMan30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman30_high()))).append("\n").append(groupReportPhysicalActivity.getWoman30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman30_in()))).append("\n").append(groupReportPhysicalActivity.getWoman30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman30_low()))).append("\n").append(groupReportPhysicalActivity.getWoman30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan40_high() + groupReportPhysicalActivity.getWoman40_high()))).append("\n").append(groupReportPhysicalActivity.getAll40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan40_in() + groupReportPhysicalActivity.getWoman40_in()))).append("\n").append(groupReportPhysicalActivity.getAll40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan40_low() + groupReportPhysicalActivity.getWoman40_low()))).append("\n").append(groupReportPhysicalActivity.getAll40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan40_high()))).append("\n").append(groupReportPhysicalActivity.getMan40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan40_in()))).append("\n").append(groupReportPhysicalActivity.getMan40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan40_low()))).append("\n").append(groupReportPhysicalActivity.getMan40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman40_high()))).append("\n").append(groupReportPhysicalActivity.getWoman40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman40_in()))).append("\n").append(groupReportPhysicalActivity.getWoman40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman40_low()))).append("\n").append(groupReportPhysicalActivity.getWoman40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan45_high() + groupReportPhysicalActivity.getWoman45_high()))).append("\n").append(groupReportPhysicalActivity.getAll45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan45_in() + groupReportPhysicalActivity.getWoman45_in()))).append("\n").append(groupReportPhysicalActivity.getAll45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan45_low() + groupReportPhysicalActivity.getWoman45_low()))).append("\n").append(groupReportPhysicalActivity.getAll45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan45_high()))).append("\n").append(groupReportPhysicalActivity.getMan45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan45_in()))).append("\n").append(groupReportPhysicalActivity.getMan45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan45_low()))).append("\n").append(groupReportPhysicalActivity.getMan45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman45_high()))).append("\n").append(groupReportPhysicalActivity.getWoman45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman45_in()))).append("\n").append(groupReportPhysicalActivity.getWoman45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman45_low()))).append("\n").append(groupReportPhysicalActivity.getWoman45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan50_high() + groupReportPhysicalActivity.getWoman50_high()))).append("\n").append(groupReportPhysicalActivity.getAll50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan50_in() + groupReportPhysicalActivity.getWoman50_in()))).append("\n").append(groupReportPhysicalActivity.getAll50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan50_low() + groupReportPhysicalActivity.getWoman50_low()))).append("\n").append(groupReportPhysicalActivity.getAll50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan50_high()))).append("\n").append(groupReportPhysicalActivity.getMan50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan50_in()))).append("\n").append(groupReportPhysicalActivity.getMan50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan50_low()))).append("\n").append(groupReportPhysicalActivity.getMan50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman50_high()))).append("\n").append(groupReportPhysicalActivity.getWoman50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman50_in()))).append("\n").append(groupReportPhysicalActivity.getWoman50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman50_low()))).append("\n").append(groupReportPhysicalActivity.getWoman50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("50-55 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan55_high() + groupReportPhysicalActivity.getWoman55_high()))).append("\n").append(groupReportPhysicalActivity.getAll55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan55_in() + groupReportPhysicalActivity.getWoman55_in()))).append("\n").append(groupReportPhysicalActivity.getAll55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan55_low() + groupReportPhysicalActivity.getWoman55_low()))).append("\n").append(groupReportPhysicalActivity.getAll55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan55_high()))).append("\n").append(groupReportPhysicalActivity.getMan55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan55_in()))).append("\n").append(groupReportPhysicalActivity.getMan55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan55_low()))).append("\n").append(groupReportPhysicalActivity.getMan55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman55_high()))).append("\n").append(groupReportPhysicalActivity.getWoman55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman55_in()))).append("\n").append(groupReportPhysicalActivity.getWoman55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman55_low()))).append("\n").append(groupReportPhysicalActivity.getWoman55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("55-60", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan60_high() + groupReportPhysicalActivity.getWoman60_high()))).append("\n").append(groupReportPhysicalActivity.getAll60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan60_in() + groupReportPhysicalActivity.getWoman60_in()))).append("\n").append(groupReportPhysicalActivity.getAll60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan60_low() + groupReportPhysicalActivity.getWoman60_low()))).append("\n").append(groupReportPhysicalActivity.getAll60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan60_high()))).append("\n").append(groupReportPhysicalActivity.getMan60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan60_in()))).append("\n").append(groupReportPhysicalActivity.getMan60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan60_low()))).append("\n").append(groupReportPhysicalActivity.getMan60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman60_high()))).append("\n").append(groupReportPhysicalActivity.getWoman60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman60_in()))).append("\n").append(groupReportPhysicalActivity.getWoman60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman60_low()))).append("\n").append(groupReportPhysicalActivity.getWoman60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan70_high() + groupReportPhysicalActivity.getWoman70_high()))).append("\n").append(groupReportPhysicalActivity.getAll70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan70_in() + groupReportPhysicalActivity.getWoman70_in()))).append("\n").append(groupReportPhysicalActivity.getAll70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan70_low() + groupReportPhysicalActivity.getWoman70_low()))).append("\n").append(groupReportPhysicalActivity.getAll70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan70_high()))).append("\n").append(groupReportPhysicalActivity.getMan70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan70_in()))).append("\n").append(groupReportPhysicalActivity.getMan70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan70_low()))).append("\n").append(groupReportPhysicalActivity.getMan70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman70_high()))).append("\n").append(groupReportPhysicalActivity.getWoman70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman70_in()))).append("\n").append(groupReportPhysicalActivity.getWoman70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman70_low()))).append("\n").append(groupReportPhysicalActivity.getWoman70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getAll_high()))).append("\n").append(groupReportPhysicalActivity.getAll_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getAll_in()))).append("\n").append(groupReportPhysicalActivity.getAll_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getAll_low()))).append("\n").append(groupReportPhysicalActivity.getAll_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan_high()))).append("\n").append(groupReportPhysicalActivity.getMan_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan_in()))).append("\n").append(groupReportPhysicalActivity.getMan_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan_low()))).append("\n").append(groupReportPhysicalActivity.getMan_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman_high()))).append("\n").append(groupReportPhysicalActivity.getWoman_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman_in()))).append("\n").append(groupReportPhysicalActivity.getWoman_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman_low()))).append("\n").append(groupReportPhysicalActivity.getWoman_low_max()).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 10);
		pdfPTable.addCell(table_Page5);
		str2 = "注：体力活动水平分级标准参见IPAQ判断标准。 ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportRiskComplicationPdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportRiskComplication groupReportRiskComplication = groupReport.getGroupReportRiskComplication();
		str1 = "危险因素聚集情况  ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      影响健康的危险因素并不是一成不变的，如果不加以控制，随着时间的推移，有些危险因素会逐步加重，一些新的危险因素也会出现。健康危险因素越多，相应的健康风险越高，同时有4、5项危险因素及以上的个体，由此产生的健康开支至少是没有危险因素个体的5～8倍以上，这其中既包括医疗保健费用开支，还有劳动生产力效益的损失。下图显示了本次评估中，具有不同数量危险因素的人群构成情况：";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(groupReportRiskComplication.getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "      除了了解健康危险因素的聚集情况外，还应该清楚了解每种健康危险因素异常比例，并重点从异常比例较高的健康危险因素入手，进行健康风险干预，这样可以在短时间内显著地改善本单位的整体健康风险状况。在所有健康危险因素中，分布最普遍的3个危险因素是：";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_Page1 = new PdfPTable(1);
		table_Page1.addCell(PDFUtil.getCorderPdfPCellA((new StringBuilder("     ")).append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(10)).getName()).append(":").append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(10)).getMax()).append("% ").toString(), font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white));
		table_Page1.addCell(PDFUtil.getCorderPdfPCellA((new StringBuilder("     ")).append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(9)).getName()).append(":").append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(9)).getMax()).append("% ").toString(), font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white));
		table_Page1.addCell(PDFUtil.getCorderPdfPCellA((new StringBuilder("     ")).append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(8)).getName()).append(":").append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(8)).getMax()).append("% ").toString(), font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white));
		pdfPTable.addCell(table_Page1);
		table_img1 = getJfreeChar(groupReport.getGroupReportAdvice().getJFreeChartPath(), width_table_Page2);
		pdfPTable.addCell(table_img1);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}*/

	public PdfPTable groupReportAdvicePdf()
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportAdvice groupReportAdvice = groupReport.getGroupReportAdvice();
		//GroupReportBasic groupReportBasic = groupReport.getGroupReportBasic();
		str1 = "管理干预建议 ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		Font font3_temp = font3;
		font3_temp.setSize(12F);
		table_Page1 = new PdfPTable(1);
		PdfPCell pdfPCell = PDFUtil.getCorderPdfPCellA("   基本原则 ", font3_temp, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		table_Page1.addCell(pdfPCell);
		pdfPTable.addCell(table_Page1);
		//str2 = (new StringBuilder("      本次参与评估共有")).append(groupReport.getGroupReportBean().getCount()).append("人，其中").append(groupReport.getGroupReportHealthRisks().getXh_des()).append("；空腹血糖≥7.0mmol/L的，即可疑糖尿病").append(groupReport.getGroupReportBloodSugar().getAll_high()).append("人，已达到高血压诊断标准的").append(groupReport.getGroupReportBloodPressure().getAll_high()).append("人，他们需要咨询医生进一步确诊；另外").append(100F - groupReport.getGroupReportRiskComplication().getNoRisk_max()).append("%（").append(groupReport.getGroupReportBean().getCount() + groupReport.getGroupReportRiskComplication().getNoRisk()).append("人） 有一个或多个危险因素，有很高的慢性病发病风险。幸运的是，不健康的危险因素是可管理的，有效的干预措施带来的不仅是危险因素的减少，还有缺勤率的降低与工作效率的提高。根据本次的评估分析结果，建议进行管理干预的原则包括以下： ").toString();
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_Page1 = new PdfPTable(1);
		pdfPCell = PDFUtil.getCorderPdfPCellA("     及早发现疾病，评估健康风险 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("?\t创造提倡健康生活方式的环境", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("?\t低风险的个体要保持低风险", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("?\t高风险的个体是干预重点", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("?\t疾病患者要参与疾病管理，减少并发症", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPTable.addCell(table_Page1);
		table_Page1 = new PdfPTable(1);
		pdfPCell = PDFUtil.getCorderPdfPCellA("   干预手段  ", font3_temp, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		table_Page1.addCell(pdfPCell);
		pdfPTable.addCell(table_Page1);
		str2 = "      员工健康管理在创造经济效益的同时，也创造着客观的社会效益。主要包括个人健康状况的改善、生活质量的提高、医疗费用的下降；对整个社会而言，还有疾病负担和精神压力的减轻。了解企业人群风险，掌握造成风险的具体原因，采取有针对性的管理措施，能够帮助企业科学地维护宝贵的人力资源，确保企业平稳健康地发展。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		str2 = "        与此同时，人群健康管理的措施必须要有针对性，搞平均主义的员工福利不仅浪费资源，而且往往是收效甚微。在有限的资源配置下，应该着重对关键的几个因素进行重点干预；同时还应考虑干预方案可获得性、可执行性以及有明确的效果评价。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		str2 = "      根据对本次评估人群的发病风险与危险因素分析，我们建议为人群采取健康干预手段，为了达到最好的效果，所提出的建议应结合企业环境和健康服务条件，实行健康促进措施。在未来的六个月内，建议重点管理干预措施如下： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_Page1 = new PdfPTable(1);
		if (1==1)//groupReportAdvice.getWeight() + groupReportAdvice.getWaist() > 0)
		{
			pdfPCell = PDFUtil.getCorderPdfPCellA("※ \t\t 减少超重与肥胖，保持健康腰围", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     a 配备体重秤、腰围尺、BMI换算表等常见体重管理工具； ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     b 制作教育材料，宣传超重与肥胖的害处与发生原因； ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     c 体重控制必须从膳食、运动两方面同时进行管理； ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     d 指导制定科学的体重控制目标，量化膳食目标与运动目标； ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     e 监测膳食目标与运动目标的定量完成； ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     f 推广体重控制日记，监测体重与腰围的变化；", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     g 体重、腰围、运动能量消耗都是采取行动激励措施的指标依据。  ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
		}
		if (1==1)//groupReportAdvice.getBloodSugar() + groupReportAdvice.getPressure() + groupReportAdvice.getLipid() + groupReport.getGroupReportHealthRisks().getDiab_his_now() + groupReport.getGroupReportHealthRisks().getBf_his_now() > 0)
		{
			pdfPCell = PDFUtil.getCorderPdfPCellA("※ \t\t控制“三高” ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     高血压、高血脂、高血糖医学上统称为“三高”。代谢综合症是“三高”症的主要原因之一，此时人体的蛋白、脂肪和糖代谢发生紊乱，最常见的就是出现高血脂、糖尿病和高血压，并且常常是伴随出现或先后发生。单独出现时已经非常危险，如果同时出现，危险性就会增加好几倍，对于“三高”应该综合干预： ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     a 开设营养膳食讲座，提供个人健康教育材料。增加膳食中营养食品的分量，减少高胆固醇食品摄入，降低食盐的摄入量。 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     b 组织适合员工群体开展的活动，如集体球类活动等。增加体育设施，建立员工活动室，适当组织户外活动或集体郊游等。 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     c 必要时随访专科医生，结合药物治疗。  ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
		}
		if (1==1)//groupReportAdvice.getSmoke() > 0)
		{
			pdfPCell = PDFUtil.getCorderPdfPCellA("※ \t\t控制抽烟 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     戒烟能明显降低患冠心病、中风和某些癌症的可能性。消除由吸烟引起的不安全隐患（财物损失），节省开支，不对他人的健康构成威胁，摆脱家人、朋友反对自己吸烟带来的烦恼。该人群的危险因素严重。应让吸烟者认识到吸烟的危害及在公共场所吸烟是不道德的。建议通过以下手段进行干预： ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     a 建立吸烟室、无烟会议室、无烟办公室，保护被动吸烟者，帮助吸烟者戒烟。 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     b 增加工休时间的文体活动，制定锻炼身体的措施和缓解心理压力的措施，有利于减少烟瘾的发作，取得戒烟成功。 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     c 定期同医生联系，电话、网上咨询或通过戒烟朋友聚会增强戒烟的决心，交流戒烟的方法，抵挡香烟的诱惑。  ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
		}
		if (1==1)//groupReport.getGroupReportSmoke().getSecondSmoke_Allmax() > 0.0F)
		{
			pdfPCell = PDFUtil.getCorderPdfPCellA("※ \t\t避免被动吸烟 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			//pdfPCell = PDFUtil.getCorderPdfPCellA((new StringBuilder("     贵单位目前有")).append(groupReport.getGroupReportSmoke().getSecondSmokeMan() + groupReport.getGroupReportSmoke().getSecondSmokeWoman()).append("人存在被动吸烟，被动吸烟对身体的危害也是很大的，单位可以告知员工尽可能减少或避免被动吸烟的方法： ").toString(), font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     a 坚定态度：绝不允许任何人在家里、办公室或汽车里抽烟。 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     b 明确表示厌恶感：可以稍微夸张地皱皱眉头或故意用手捏捏鼻子，或有意识地中止谈话，然后背过身去，如此一般吸烟者都会主动停止吸烟。", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     c 请对方尊重不吸烟者的权利：有礼貌地提醒对方不想被动吸烟，而这是不吸烟者的权利，他们必须尊重。 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     d 在家中专设一间吸烟室：如果家人抽烟，条件允许的话，另设一个通风条件好的房间专供他吸烟；如条件不允许，则要求他们到室外吸烟，决不可在室内吸烟。", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     e 在家里种上一两盆绿色植物：绿色植物有效地吸收烟雾中的一些有害物质，净化居室空气。", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA("     f 采取有效措施减轻被动吸烟的程度:如果家中来了客人，出于礼貌不好阻止他们抽烟，那么及时采取措施如打开门窗、排气扇，让空气流通或将毛巾打湿，或倒一碗醋来吸收烟雾中的有害物质；在一些您无法回避的公共场所如会议室、宴会厅、办公室、火车、公共汽车等地方，您应尽量坐在靠近门窗的地方，尽量多呼吸室外的新鲜空气；可以通过多吃新鲜的蔬菜水果（尤其是富含胡萝卜素及维生素C，如木瓜、蕃茄、胡萝卜、南瓜等）、多喝水、多运动来加速排除体内的尼古丁等有害物质。", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
		}
		pdfPTable.addCell(table_Page1);
		table_Page1 = new PdfPTable(1);
		pdfPCell = PDFUtil.getCorderPdfPCellA("   尾注 ", font3_temp, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		table_Page1.addCell(pdfPCell);
		pdfPTable.addCell(table_Page1);
		str2 = "      本报告是为贵单位所有健康风险评估服务评估对象的群体报告，有关各个个体的分析报告及健康管理实施方案，需要参见个体健康风险评估报告。另外，原始数据的准确性将影响报告的分析结果。报告中的解释及建议将只适用于本人群的情况，其它用途由使用者自行解释。 ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		str2 = "      本报告中使用的分析标准、全国数据水平以及基本管理原则等，均是参考国家相关权威机构的公开出版物，主要参考资料如下： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_Page1 = new PdfPTable(1);
		pdfPCell = PDFUtil.getCorderPdfPCellA("     《年中国居民营养与健康状况调查报告》 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("     《中国居民膳食指南》 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("     《中国成人血脂异常防治指南》 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("     《中国高血压防治指南》 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("     《中国成人超重和肥胖症预防控制指南》 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("     《中国糖尿病防治指南》  ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPTable.addCell(table_Page1);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public void begin()
	{
	}

	public boolean createPDF()
	{
		return false;
	}

	public void end()
	{
	}

	

	protected String getFooter()
	{
		return null;
	}

	@Override
	public void setParameter(Object[] aobj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PdfPTable getPdfPTable() throws BadElementException, MalformedURLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}


}
