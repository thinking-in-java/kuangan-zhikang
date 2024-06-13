package com.yihe.system.utils;


import com.ibm.icu.math.BigDecimal;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.yihe.common.pdf.RiskConnection;
import com.yihe.common.riskresult.GroupReport;
import com.yihe.common.utils.PDFUtil;
import com.yihe.common.utils.Path;
import com.yihe.common.utils.ReportUtil;
import com.yihe.system.domain.*;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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

	public PdfPTable groupReportBasicPdf(YhExportPeopleBasic basic)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		GroupReportBasic groupReportBasic = new GroupReportBasic(0);
		str1 = "人群基本情况";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = (new StringBuilder("      从")).append(basic.getBegintime()).append("到").append(basic.getEndtime()).append("共有").append(basic.getPeoplecount()).append("人参与评估，其中男性").append(basic.getMancount()).append("人，占总人数的").append(String.format("%.2f",(double)basic.getMancount()/basic.getPeoplecount()*100)).append("%；女性").append(basic.getWomancount()).append("人，占总人数的").append(String.format("%.2f",(double)basic.getWomancount()/basic.getPeoplecount()*100)).append("%。将本次评估对象按照年龄和性别分布，其基本情况如下图和下表所示。").toString();
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		//获取第一个表格的img
		table_img1 = getJfreeChar(groupReportBasic.getJFreeChartPath(basic), width_table_Page2);
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
		System.out.println(basic.getPeoplecount()+"----------------------------------------");
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge30Man()+basic.getAge30Woman()))).append("\n").append(String.format("%.2f", division((basic.getAge30Man()+basic.getAge30Woman()),basic.getPeoplecount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge30Man()))).append("\n").append(String.format("%.2f", division(basic.getAge30Man(),basic.getMancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge30Woman()))).append("\n").append(String.format("%.2f", division(basic.getAge30Woman(),basic.getWomancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge30_40Man() + basic.getAge30_40Woman()))).append("\n").append(String.format("%.2f", division((basic.getAge30_40Man()+basic.getAge30_40Woman()),basic.getPeoplecount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge30_40Man()))).append("\n").append(String.format("%.2f", division(basic.getAge30_40Man(),basic.getMancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge30_40Woman()))).append("\n").append(String.format("%.2f", division(basic.getAge30_40Woman(),basic.getWomancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge40_45Man() + basic.getAge40_45Woman()))).append("\n").append(String.format("%.2f", division((basic.getAge40_45Man() + basic.getAge40_45Woman()),basic.getPeoplecount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge40_45Man()))).append("\n").append(String.format("%.2f", division(basic.getAge40_45Man(),basic.getMancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge40_45Woman()))).append("\n").append(String.format("%.2f", division(basic.getAge40_45Woman(),basic.getWomancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge45_50Man() + basic.getAge50_55Woman()))).append("\n").append(String.format("%.2f", division((basic.getAge45_50Man() + basic.getAge45_50Woman()),basic.getPeoplecount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge45_50Man()))).append("\n").append(String.format("%.2f", division(basic.getAge45_50Man(),basic.getMancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge45_50Woman()))).append("\n").append(String.format("%.2f", division(basic.getAge45_50Woman(),basic.getWomancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("50-55", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge50_55Man() + basic.getAge50_55Woman()))).append("\n").append(String.format("%.2f", division((basic.getAge50_55Man() + basic.getAge50_55Woman()),basic.getPeoplecount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge50_55Man()))).append("\n").append(String.format("%.2f", division(basic.getAge50_55Man(),basic.getMancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge50_55Woman()))).append("\n").append(String.format("%.2f", division(basic.getAge50_55Woman(),basic.getWomancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("55-60", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge55_60Man() + basic.getAge55_60Woman()))).append("\n").append(String.format("%.2f", division((basic.getAge55_60Man() + basic.getAge55_60Woman()),basic.getPeoplecount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge55_60Man()))).append("\n").append(String.format("%.2f", division(basic.getAge55_60Man(),basic.getMancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge55_60Woman()))).append("\n").append(String.format("%.2f", division(basic.getAge55_60Woman(),basic.getWomancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge60Man() + basic.getAge60Woman()))).append("\n").append(String.format("%.2f", division((basic.getAge60Man() + basic.getAge60Woman()),basic.getPeoplecount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge60Man()))).append("\n").append(String.format("%.2f", division(basic.getAge60Man(),basic.getMancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getAge60Woman()))).append("\n").append(String.format("%.2f", division(basic.getAge60Woman(),basic.getWomancount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计", font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getPeoplecount()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getMancount()))).append("\n").append(String.format("%.2f", division(basic.getMancount(),basic.getPeoplecount())*100)).toString(), font2, 1, TableLineColor));
		table_Page3.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(basic.getWomancount()))).append("\n").append(String.format("%.2f", division(basic.getWomancount(),basic.getPeoplecount())*100)).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page3, 7);
		pdfPTable.addCell(table_Page3);
		if (!basic.getStatements().equals(""))
		{
			str2 = (new StringBuilder("      在参与评估的")).append(basic.getPeoplecount()).append("人中，").append(basic.getStatements()).append("。对于已患有疾病的人群，建议定期到正规医院就诊，获得规范治疗，同时进行疾病管理控制病情、改善预后。个人患病情况如下表所示。").toString();
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
			if(basic.getMandiab_hiscount()+basic.getWomandiab_hiscount()!=0) {
				String[] array = new String[] {"糖尿病", (basic.getMandiab_hiscount()+basic.getWomandiab_hiscount())+"("+String.format("%.2f", (double)(basic.getMandiab_hiscount()+basic.getWomandiab_hiscount())/basic.getPeoplecount())+")", basic.getMandiab_hiscount()+"("+String.format("%.2f", (double)basic.getMandiab_hiscount()/basic.getPeoplecount())+")", basic.getWomandiab_hiscount()+"("+String.format("%.2f", (double)basic.getWomandiab_hiscount()/basic.getPeoplecount())+")"};
				disList.add(array);
			}
			if(basic.getMancard_hiscount()+basic.getWomancard_hiscount()!=0) {
				String[] array1 = new String[] {"心血管疾病", (basic.getMancard_hiscount()+basic.getWomancard_hiscount())+"("+String.format("%.2f", (double)(basic.getMancard_hiscount()+basic.getWomancard_hiscount())/basic.getPeoplecount())+")", basic.getMancard_hiscount()+"("+String.format("%.2f", (double)basic.getMancard_hiscount()/basic.getPeoplecount())+")", basic.getWomancard_hiscount()+"("+String.format("%.2f", (double)basic.getWomancard_hiscount()/basic.getPeoplecount())+")"};
				disList.add(array1);
			}
			if(basic.getManaf_hiscount()+basic.getWomanaf_hiscount()!=0) {
				String[] array2 = new String[] {"房颤", (basic.getManaf_hiscount()+basic.getWomanaf_hiscount())+"("+String.format("%.2f", (double)(basic.getManaf_hiscount()+basic.getWomanaf_hiscount())/basic.getPeoplecount())+")", basic.getManaf_hiscount()+"("+String.format("%.2f", (double)basic.getManaf_hiscount()/basic.getPeoplecount())+")", basic.getWomanaf_hiscount()+"("+String.format("%.2f", (double)basic.getWomanaf_hiscount()/basic.getPeoplecount())+")"};
				disList.add(array2);
			}
			if(basic.getManhbp_hiscount()+basic.getWomanhbp_hiscount()!=0) {
				String[] array3 = new String[] {"高血压", (basic.getManhbp_hiscount()+basic.getWomanhbp_hiscount())+"("+String.format("%.2f", (double)(basic.getManhbp_hiscount()+basic.getWomanhbp_hiscount())/basic.getPeoplecount())+")", basic.getManhbp_hiscount()+"("+String.format("%.2f", (double)basic.getManhbp_hiscount()/basic.getPeoplecount())+")", basic.getWomanhbp_hiscount()+"("+String.format("%.2f", (double)basic.getWomanhbp_hiscount()/basic.getPeoplecount())+")"};
				disList.add(array3);
			}
			if(basic.getManchd_hiscount()+basic.getWomanchd_hiscount()!=0) {
				String[] array4 = new String[] {"冠心病史", (basic.getManchd_hiscount()+basic.getWomanchd_hiscount())+"("+String.format("%.2f", (double)(basic.getManchd_hiscount()+basic.getWomanchd_hiscount())/basic.getPeoplecount())+")", basic.getManchd_hiscount()+"("+String.format("%.2f", (double)basic.getManchd_hiscount()/basic.getPeoplecount())+")", basic.getWomanchd_hiscount()+"("+String.format("%.2f", (double)basic.getWomanchd_hiscount()/basic.getPeoplecount())+")"};
				disList.add(array4);
			}
			if(basic.getManemphy_hiscount()+basic.getWomanemphy_hiscount()!=0) {
				String[] array5 = new String[] {"肺气肿", (basic.getManemphy_hiscount()+basic.getWomanemphy_hiscount())+"("+String.format("%.2f", (double)(basic.getManemphy_hiscount()+basic.getWomanemphy_hiscount())/basic.getPeoplecount())+")", basic.getManemphy_hiscount()+"("+String.format("%.2f", (double)basic.getManemphy_hiscount()/basic.getPeoplecount())+")", basic.getWomanemphy_hiscount()+"("+String.format("%.2f", (double)basic.getWomanemphy_hiscount()/basic.getPeoplecount())+")"};
				disList.add(array5);
			}
			if(basic.getManbronch_hiscount()+basic.getWomanbronch_hiscount()!=0) {
				String[] array6 = new String[] {"慢性支气管炎", (basic.getManbronch_hiscount()+basic.getWomanbronch_hiscount())+"("+String.format("%.2f", (double)(basic.getManbronch_hiscount()+basic.getWomanbronch_hiscount())/basic.getPeoplecount())+")", basic.getManbronch_hiscount()+"("+String.format("%.2f", (double)basic.getManbronch_hiscount()/basic.getPeoplecount())+")", basic.getWomanbronch_hiscount()+"("+String.format("%.2f", (double)basic.getWomanbronch_hiscount()/basic.getPeoplecount())+")"};
				disList.add(array6);
			}
			if(basic.getManprostate_diseasecount()+basic.getWomanprostate_diseasecount()!=0) {
				String[] array7 = new String[] {"前列腺炎或肥大", (basic.getManprostate_diseasecount()+basic.getWomanprostate_diseasecount())+"("+String.format("%.2f", (double)(basic.getManprostate_diseasecount()+basic.getWomanprostate_diseasecount())/basic.getPeoplecount())+")", basic.getManprostate_diseasecount()+"("+String.format("%.2f", (double)basic.getManprostate_diseasecount()/basic.getPeoplecount())+")", basic.getWomanprostate_diseasecount()+"("+String.format("%.2f", (double)basic.getWomanprostate_diseasecount()/basic.getPeoplecount())+")"};
				disList.add(array7);
			}
			if(basic.getManstroke_hiscount()+basic.getWomanstroke_hiscount()!=0) {
				String[] array8 = new String[] {"脑卒中", (basic.getManstroke_hiscount()+basic.getWomanstroke_hiscount())+"("+String.format("%.2f", (double)(basic.getManstroke_hiscount()+basic.getWomanstroke_hiscount())/basic.getPeoplecount())+")", basic.getManstroke_hiscount()+"("+String.format("%.2f", (double)basic.getManstroke_hiscount()/basic.getPeoplecount())+")", basic.getWomanstroke_hiscount()+"("+String.format("%.2f", (double)basic.getWomanstroke_hiscount()/basic.getPeoplecount())+")"};
				disList.add(array8);
			}
			for (Iterator iterator = disList.iterator(); iterator.hasNext();)
			{
				String strings[] = (String[])iterator.next();
				if (!strings[0].equals("0"))
				{
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[0], font2, 1, TableLineColor));
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[1], font2, 1, TableLineColor));
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[2], font2, 1, TableLineColor));
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(strings[3], font2, 1, TableLineColor));
				}
			}

			addBlankCell(table_Page5, 7);
			pdfPTable.addCell(table_Page5);
		}
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	
	public PdfPTable groupReportHealthRisksPdf(YhExportDiseaseStatistical disstate)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportHealthRisks groupReportHealthRisks = groupReport.getGroupReportHealthRisks();
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
			0.15F, 0.2F,0.15F,0.15F,0.15F,0.15F,0.15F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("疾病", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("评估结果", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("极高风险", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("高风险", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("中等风险", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("较低风险", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		pdfCell2 = PDFUtil.getCorderPdfPCellA("低风险", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		String[] disname=new String[]{"糖尿病","高血压","冠心病","脑卒中","肺癌","前列腺癌","骨质疏松","痛风","乳腺癌"};
		String[] veryhe=new String[] {disstate.getDiab_hisVeryhighcount()+"("+String.format("%.2f", division(disstate.getDiab_hisVeryhighcount(),disstate.getPeoplecount())*100)+")",disstate.getHbp_hisVeryhighcount()+"("+String.format("%.2f", division(disstate.getHbp_hisVeryhighcount(),disstate.getPeoplecount())*100)+")",disstate.getChd_hisVeryhighcount()+"("+String.format("%.2f", division(disstate.getChd_hisVeryhighcount(),disstate.getPeoplecount())*100)+")",disstate.getStroke_hisVeryhighcount()+"("+String.format("%.2f", division(disstate.getStroke_hisVeryhighcount(),disstate.getPeoplecount())*100)+")",disstate.getLc_hisVeryhighcount()+"("+String.format("%.2f", division(disstate.getLc_hisVeryhighcount(),disstate.getPeoplecount())*100)+")",disstate.getPc_hisVeryhighcount()+"("+String.format("%.2f", division(disstate.getPc_hisVeryhighcount(),disstate.getPeoplecount())*100)+")",disstate.getOp_hisVeryhighcount()+"("+String.format("%.2f", division(disstate.getOp_hisVeryhighcount(),disstate.getPeoplecount())*100)+")",disstate.getPd_hisVeryhighcount()+"("+String.format("%.2f", division(disstate.getPd_hisVeryhighcount(),disstate.getPeoplecount())*100)+")",disstate.getBc_hisVeryhighcount()+"("+String.format("%.2f", division(disstate.getBc_hisVeryhighcount(),disstate.getPeoplecount())*100)+")"};
		String[] height=new String[] {disstate.getDiab_hishighcount()+"("+String.format("%.2f", division(disstate.getDiab_hishighcount(),disstate.getPeoplecount())*100)+")",disstate.getHbp_hishighcount()+"("+String.format("%.2f", division(disstate.getHbp_hishighcount(),disstate.getPeoplecount())*100)+")",disstate.getChd_hishighcount()+"("+String.format("%.2f", division(disstate.getChd_hishighcount(),disstate.getPeoplecount())*100)+")",disstate.getStroke_hishighcount()+"("+String.format("%.2f", division(disstate.getStroke_hishighcount(),disstate.getPeoplecount())*100)+")",disstate.getLc_hishighcount()+"("+String.format("%.2f", division(disstate.getLc_hishighcount(),disstate.getPeoplecount())*100)+")",disstate.getPc_hishighcount()+"("+String.format("%.2f", division(disstate.getPc_hishighcount(),disstate.getPeoplecount())*100)+")",disstate.getOp_hishighcount()+"("+String.format("%.2f", division(disstate.getOp_hishighcount(),disstate.getPeoplecount())*100)+")",disstate.getPc_hishighcount()+"("+String.format("%.2f", division(disstate.getPc_hishighcount(),disstate.getPeoplecount())*100)+")",disstate.getBc_hishighcount()+"("+String.format("%.2f", division(disstate.getBc_hishighcount(),disstate.getPeoplecount())*100)+")"};
		String[] medium=new String[] {disstate.getDiab_hismediumcount()+"("+String.format("%.2f", division(disstate.getDiab_hismediumcount(),disstate.getPeoplecount())*100)+")",disstate.getHbp_hismediumcount()+"("+String.format("%.2f", division(disstate.getHbp_hismediumcount(),disstate.getPeoplecount())*100)+")",disstate.getChd_hismediumcount()+"("+String.format("%.2f", division(disstate.getChd_hismediumcount(),disstate.getPeoplecount())*100)+")",disstate.getStroke_hismediumcount()+"("+String.format("%.2f", division(disstate.getStroke_hismediumcount(),disstate.getPeoplecount())*100)+")",disstate.getLc_hismediumcount()+"("+String.format("%.2f", division(disstate.getLc_hismediumcount(),disstate.getPeoplecount())*100)+")",disstate.getPc_hismediumcount()+"("+String.format("%.2f", division(disstate.getPc_hismediumcount(),disstate.getPeoplecount())*100)+")",disstate.getOp_hismediumcount()+"("+String.format("%.2f", division(disstate.getOp_hismediumcount(),disstate.getPeoplecount())*100)+")",disstate.getPd_hismediumcount()+"("+String.format("%.2f", division(disstate.getPd_hismediumcount(),disstate.getPeoplecount())*100)+")",disstate.getBc_hismediumcount()+"("+String.format("%.2f", division(disstate.getBc_hismediumcount(),disstate.getPeoplecount())*100)+")"};
		String[] thelower=new String[] {disstate.getDiab_histhelowercount()+"("+String.format("%.2f", division(disstate.getDiab_histhelowercount(),disstate.getPeoplecount())*100)+")",disstate.getHbp_histhelowercount()+"("+String.format("%.2f", division(disstate.getHbp_histhelowercount(),disstate.getPeoplecount())*100)+")",disstate.getChd_histhelowercount()+"("+String.format("%.2f", division(disstate.getChd_histhelowercount(),disstate.getPeoplecount())*100)+")",disstate.getStroke_histhelowercount()+"("+String.format("%.2f", division(disstate.getStroke_histhelowercount(),disstate.getPeoplecount())*100)+")",disstate.getLc_histhelowercount()+"("+String.format("%.2f", division(disstate.getLc_histhelowercount(),disstate.getPeoplecount())*100)+")",disstate.getPc_histhelowercount()+"("+String.format("%.2f", division(disstate.getPc_histhelowercount(),disstate.getPeoplecount())*100)+")",disstate.getOp_histhelowercount()+"("+String.format("%.2f", division(disstate.getOp_histhelowercount(),disstate.getPeoplecount())*100)+")",disstate.getPd_histhelowercount()+"("+String.format("%.2f", division(disstate.getPd_histhelowercount(),disstate.getPeoplecount())*100)+")",disstate.getBc_histhelowercount()+"("+String.format("%.2f", division(disstate.getBc_histhelowercount(),disstate.getPeoplecount())*100)+")"};
		String[] low=new String[] {disstate.getDiab_hislowcount()+"("+String.format("%.2f", division(disstate.getDiab_hislowcount(),disstate.getPeoplecount())*100)+")",disstate.getHbp_hislowcount()+"("+String.format("%.2f", division(disstate.getHbp_hislowcount(),disstate.getPeoplecount())*100)+")",disstate.getChd_hislowcount()+"("+String.format("%.2f", division(disstate.getChd_hislowcount(),disstate.getPeoplecount())*100)+")",disstate.getStroke_hislowcount()+"("+String.format("%.2f", division(disstate.getStroke_hislowcount(),disstate.getPeoplecount())*100)+")",disstate.getLc_hislowcount()+"("+String.format("%.2f", division(disstate.getLc_hislowcount(),disstate.getPeoplecount())*100)+")",disstate.getPc_hislowcount()+"("+String.format("%.2f", division(disstate.getPc_hislowcount(),disstate.getPeoplecount())*100)+")",disstate.getOp_hislowcount()+"("+String.format("%.2f", division(disstate.getOp_hislowcount(),disstate.getPeoplecount())*100)+")",disstate.getPd_hislowcount()+"("+String.format("%.2f", division(disstate.getPd_hislowcount(),disstate.getPeoplecount())*100)+")",disstate.getBc_hislowcount()+"("+String.format("%.2f", division(disstate.getBc_hislowcount(),disstate.getPeoplecount())*100)+")"};
		for (int i = 0; i <9; i++) {
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(disname[i], font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("实际(A)人数（百分比）", font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(veryhe[i], font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(height[i], font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(medium[i], font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(thelower[i], font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder(low[i], font2, 1, TableLineColor));
		}

		//List riskList = groupReport.getGroupReportHealthRisks().getRiskList();
		/*for (Iterator iterator = riskList.iterator(); iterator.hasNext(); table_Page5.addCell(pdfCell2))
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
		}*/

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

	public PdfPTable groupReportBodyMassIndexPdf(YhExportPdfBmi pdfbmi)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportBodyMassIndex groupReportBodyMassIndex = groupReport.getGroupReportBodyMassIndex();
		str1 = "体重指数(BMI)";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      体重指数（body mass index）简称BMI，是国际公认的一种评定肥胖程度的方法。它的计算方法是：BMI＝体重(kg)\367身高的平方 (m2)。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		str2 = "      肥胖是高血压、冠心病、缺血性脑卒中、血脂异常、2型糖尿病发病的独立危险因素。适当减重可降低血压及胆固醇水平，从而降低冠心病、糖尿病等的发病几率。本次评估对象的体重情况按年龄及性别分布如下所示。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(getJFreeChartPath(pdfbmi), width_table_Page2);
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30mancountthin() +pdfbmi.getAge30womancountthin()))).append("\n").append(String.format("%.2f", persent(pdfbmi.getAge30mancountthin()+pdfbmi.getAge30womancountthin(), pdfbmi.getAge30mancountall()+pdfbmi.getAge30womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30mancountnormal() + pdfbmi.getAge30womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30mancountnormal()+ pdfbmi.getAge30womancountnormal(),pdfbmi.getAge30mancountall()+pdfbmi.getAge30womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30mancountoverweight() + pdfbmi.getAge30womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30mancountoverweight()+ pdfbmi.getAge30womancountoverweight(),pdfbmi.getAge30mancountall()+pdfbmi.getAge30womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30mancountobesity() + pdfbmi.getAge30womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30mancountobesity()+ pdfbmi.getAge30womancountobesity(),pdfbmi.getAge30mancountall()+pdfbmi.getAge30womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30mancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30mancountthin(),pdfbmi.getAge30mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30mancountnormal(),pdfbmi.getAge30mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30mancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30mancountoverweight(),pdfbmi.getAge30mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30mancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30mancountobesity(),pdfbmi.getAge30mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30womancountthin(),pdfbmi.getAge30womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30womancountnormal(),pdfbmi.getAge30womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30womancountoverweight(),pdfbmi.getAge30womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30womancountobesity(),pdfbmi.getAge30womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40mancountthin() +pdfbmi.getAge30_40womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40mancountthin()+pdfbmi.getAge30_40womancountthin(), pdfbmi.getAge30_40mancountall()+pdfbmi.getAge30_40womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40mancountnormal() + pdfbmi.getAge30_40womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40mancountnormal()+ pdfbmi.getAge30_40womancountnormal(),pdfbmi.getAge30_40mancountall()+pdfbmi.getAge30_40womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40mancountoverweight() + pdfbmi.getAge30_40womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40mancountoverweight()+ pdfbmi.getAge30_40womancountoverweight(),pdfbmi.getAge30_40mancountall()+pdfbmi.getAge30_40womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40mancountobesity() + pdfbmi.getAge30_40womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40mancountobesity()+ pdfbmi.getAge30_40womancountobesity(),pdfbmi.getAge30_40mancountall()+pdfbmi.getAge30_40womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40mancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40mancountthin(),pdfbmi.getAge30_40mancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40mancountnormal(),pdfbmi.getAge30_40mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40mancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40mancountoverweight(),pdfbmi.getAge30_40mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40mancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40mancountobesity(),pdfbmi.getAge30_40mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40womancountthin(),pdfbmi.getAge30_40womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40womancountnormal(),pdfbmi.getAge30_40womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40womancountoverweight(),pdfbmi.getAge30_40womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge30_40womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge30_40womancountobesity(),pdfbmi.getAge30_40womancountall()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan40_thin() + groupReportBodyMassIndex.getWoman40_thin()))).append("\n").append(groupReportBodyMassIndex.getCount40_thinP()).toString(), font2, 1, TableLineColor));
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman40_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman40_obesityP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45mancountthin() +pdfbmi.getAge40_45womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45mancountthin()+pdfbmi.getAge40_45womancountthin(), pdfbmi.getAge40_45mancountall()+pdfbmi.getAge40_45womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45mancountnormal() + pdfbmi.getAge40_45womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45mancountnormal()+ pdfbmi.getAge40_45womancountnormal(),pdfbmi.getAge40_45mancountall()+pdfbmi.getAge40_45womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45mancountoverweight() + pdfbmi.getAge40_45womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45mancountoverweight()+ pdfbmi.getAge40_45womancountoverweight(),pdfbmi.getAge40_45mancountall()+pdfbmi.getAge40_45womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45mancountobesity() + pdfbmi.getAge40_45womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45mancountobesity()+ pdfbmi.getAge40_45womancountobesity(),pdfbmi.getAge40_45mancountall()+pdfbmi.getAge40_45womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45mancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45mancountthin(),pdfbmi.getAge40_45mancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45mancountnormal(),pdfbmi.getAge40_45mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45mancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45mancountoverweight(),pdfbmi.getAge40_45mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45mancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45mancountobesity(),pdfbmi.getAge40_45mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45womancountthin(),pdfbmi.getAge40_45womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45womancountnormal(),pdfbmi.getAge40_45womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45womancountoverweight(),pdfbmi.getAge40_45womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge40_45womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge40_45womancountobesity(),pdfbmi.getAge40_45womancountall()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan45_thin() + groupReportBodyMassIndex.getWoman45_thin()))).append("\n").append(groupReportBodyMassIndex.getCount45_thinP()).toString(), font2, 1, TableLineColor));
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman45_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman45_obesityP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountthin() +pdfbmi.getAge50_55womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountthin()+pdfbmi.getAge50_55womancountthin(), pdfbmi.getAge50_55mancountall()+pdfbmi.getAge50_55womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountnormal() + pdfbmi.getAge50_55womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountnormal()+ pdfbmi.getAge50_55womancountnormal(),pdfbmi.getAge50_55mancountall()+pdfbmi.getAge50_55womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountoverweight() + pdfbmi.getAge50_55womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountoverweight()+ pdfbmi.getAge50_55womancountoverweight(),pdfbmi.getAge50_55mancountall()+pdfbmi.getAge50_55womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountobesity() + pdfbmi.getAge50_55womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountobesity()+ pdfbmi.getAge50_55womancountobesity(),pdfbmi.getAge50_55mancountall()+pdfbmi.getAge50_55womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountthin(),pdfbmi.getAge50_55mancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountnormal(),pdfbmi.getAge50_55mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountoverweight(),pdfbmi.getAge50_55mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountobesity(),pdfbmi.getAge50_55mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55womancountthin(),pdfbmi.getAge50_55womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55womancountnormal(),pdfbmi.getAge50_55womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55womancountoverweight(),pdfbmi.getAge50_55womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55womancountobesity(),pdfbmi.getAge50_55womancountall()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan50_thin() + groupReportBodyMassIndex.getWoman50_thin()))).append("\n").append(groupReportBodyMassIndex.getCount50_thinP()).toString(), font2, 1, TableLineColor));
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman50_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman50_obesityP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("50－55", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountthin() +pdfbmi.getAge50_55womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountthin()+pdfbmi.getAge50_55womancountthin(), pdfbmi.getAge50_55mancountall()+pdfbmi.getAge50_55womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountnormal() + pdfbmi.getAge50_55womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountnormal()+ pdfbmi.getAge50_55womancountnormal(),pdfbmi.getAge50_55mancountall()+pdfbmi.getAge50_55womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountoverweight() + pdfbmi.getAge50_55womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountoverweight()+ pdfbmi.getAge50_55womancountoverweight(),pdfbmi.getAge50_55mancountall()+pdfbmi.getAge50_55womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountobesity() + pdfbmi.getAge50_55womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountobesity()+ pdfbmi.getAge50_55womancountobesity(),pdfbmi.getAge50_55mancountall()+pdfbmi.getAge50_55womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountthin(),pdfbmi.getAge50_55mancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountnormal(),pdfbmi.getAge50_55mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountoverweight(),pdfbmi.getAge50_55mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55mancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55mancountobesity(),pdfbmi.getAge50_55mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55womancountthin(),pdfbmi.getAge50_55womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55womancountnormal(),pdfbmi.getAge50_55womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55womancountoverweight(),pdfbmi.getAge50_55womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge50_55womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge50_55womancountobesity(),pdfbmi.getAge50_55womancountall()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan55_thin() + groupReportBodyMassIndex.getWoman55_thin()))).append("\n").append(groupReportBodyMassIndex.getCount55_thinP()).toString(), font2, 1, TableLineColor));
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman55_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman55_obesityP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("55－60", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60mancountthin() +pdfbmi.getAge55_60womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60mancountthin()+pdfbmi.getAge55_60womancountthin(), pdfbmi.getAge55_60mancountall()+pdfbmi.getAge55_60womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60mancountnormal() + pdfbmi.getAge55_60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60mancountnormal()+ pdfbmi.getAge55_60womancountnormal(),pdfbmi.getAge55_60mancountall()+pdfbmi.getAge55_60womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60mancountoverweight() + pdfbmi.getAge55_60womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60mancountoverweight()+ pdfbmi.getAge55_60womancountoverweight(),pdfbmi.getAge55_60mancountall()+pdfbmi.getAge55_60womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60mancountobesity() + pdfbmi.getAge55_60womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60mancountobesity()+ pdfbmi.getAge55_60womancountobesity(),pdfbmi.getAge55_60mancountall()+pdfbmi.getAge55_60womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60mancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60mancountthin(),pdfbmi.getAge55_60mancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60mancountnormal(),pdfbmi.getAge55_60mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60mancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60mancountoverweight(),pdfbmi.getAge55_60mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60mancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60mancountobesity(),pdfbmi.getAge55_60mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60womancountthin(),pdfbmi.getAge55_60womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60womancountnormal(),pdfbmi.getAge55_60womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60womancountoverweight(),pdfbmi.getAge55_60womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge55_60womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge55_60womancountobesity(),pdfbmi.getAge55_60womancountall()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan60_thin() + groupReportBodyMassIndex.getWoman60_thin()))).append("\n").append(groupReportBodyMassIndex.getCount60_thinP()).toString(), font2, 1, TableLineColor));
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman60_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman60_obesityP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60mancountthin() +pdfbmi.getAge60womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60mancountthin()+pdfbmi.getAge60womancountthin(), pdfbmi.getAge60mancountall()+pdfbmi.getAge60womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60mancountnormal() + pdfbmi.getAge60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60mancountnormal()+ pdfbmi.getAge60womancountnormal(),pdfbmi.getAge60mancountall()+pdfbmi.getAge60womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60mancountoverweight() + pdfbmi.getAge60womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60mancountoverweight()+ pdfbmi.getAge60womancountoverweight(),pdfbmi.getAge60mancountall()+pdfbmi.getAge60womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60mancountobesity() + pdfbmi.getAge60womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60mancountobesity()+ pdfbmi.getAge60womancountobesity(),pdfbmi.getAge60mancountall()+pdfbmi.getAge60womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60mancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60mancountthin(),pdfbmi.getAge60mancountall())	)).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60mancountnormal(),pdfbmi.getAge60mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60mancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60mancountoverweight(),pdfbmi.getAge60mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60mancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60mancountobesity(),pdfbmi.getAge60mancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60womancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60womancountthin(),pdfbmi.getAge60womancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60womancountnormal(),pdfbmi.getAge60womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60womancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60womancountoverweight(),pdfbmi.getAge60womancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getAge60womancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getAge60womancountobesity(),pdfbmi.getAge60womancountall()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getMan70_thin() + groupReportBodyMassIndex.getWoman70_thin()))).append("\n").append(groupReportBodyMassIndex.getCount70_thinP()).toString(), font2, 1, TableLineColor));
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWoman70_obesity()))).append("\n").append(groupReportBodyMassIndex.getWoman70_obesityP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计: ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getMancountthin() +pdfbmi.getWomancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getMancountthin()+pdfbmi.getWomancountthin(), pdfbmi.getMancountall()+pdfbmi.getWomancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getMancountnormal() + pdfbmi.getWomancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getMancountnormal()+ pdfbmi.getWomancountnormal(),pdfbmi.getMancountall()+pdfbmi.getWomancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getMancountoverweight() + pdfbmi.getWomancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getMancountoverweight()+ pdfbmi.getWomancountoverweight(),pdfbmi.getMancountall()+pdfbmi.getWomancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getMancountobesity() + pdfbmi.getWomancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getMancountobesity()+ pdfbmi.getWomancountobesity(),pdfbmi.getMancountall()+pdfbmi.getWomancountall())	)).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getMancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getMancountthin(),pdfbmi.getMancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getMancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getMancountnormal(),pdfbmi.getMancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getMancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getMancountoverweight(),pdfbmi.getMancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getMancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getMancountobesity(),pdfbmi.getMancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getWomancountthin()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getWomancountthin(),pdfbmi.getWomancountall()	))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getWomancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getWomancountnormal(),pdfbmi.getWomancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getWomancountoverweight()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getWomancountoverweight(),pdfbmi.getWomancountall()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfbmi.getWomancountobesity()))).append("\n").append(String.format("%.2f",persent(pdfbmi.getWomancountobesity(),pdfbmi.getWomancountall()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getManThin() + groupReportBodyMassIndex.getWomanThin()))).append("\n").append(groupReportBodyMassIndex.getAllThinMax()).toString(), font2, 1, TableLineColor));
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBodyMassIndex.getWomanThin()))).append("\n").append(groupReportBodyMassIndex.getWomanobesityMax()).toString(), font2, 1, TableLineColor));*/
		addBlankCell(table_Page5, 13);
		pdfPTable.addCell(table_Page5);
		str2 = "注: 消瘦：BMI＜18.5； 正常：18.5≤BMI＜24； 超重：24≤BMI＜28；肥胖：BMI≥28";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportWaistlinePdf(YhExportPdfWaist pdfwaist)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportWaistline groupReportWaistline = groupReport.getGroupReportWaistline();
		str1 = "腰围情况";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      与其他的体格指标相比，腰围是人体是否存在胰岛素抵抗最重要的标志；腰围过大是糖尿病的独立危险因素。调查对象的腰围统计结果按年龄、性别分布如下所示： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(pdfwaist.getJFreeChartPath(pdfwaist), width_table_Page2);
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30mancountnormal()+pdfwaist.getAge30womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30mancountnormal()+pdfwaist.getAge30womancountnormal(), pdfwaist.getAge30mancounthigh()+pdfwaist.getAge30mancountnormal()+pdfwaist.getAge30womancounthigh()+pdfwaist.getAge30womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30mancounthigh()+pdfwaist.getAge30womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30mancounthigh()+pdfwaist.getAge30womancounthigh(), pdfwaist.getAge30mancounthigh()+pdfwaist.getAge30mancountnormal()+pdfwaist.getAge30womancounthigh()+pdfwaist.getAge30womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30mancountnormal(), pdfwaist.getAge30mancounthigh()+pdfwaist.getAge30mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30mancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30mancounthigh(), pdfwaist.getAge30mancounthigh()+pdfwaist.getAge30mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30womancountnormal(), pdfwaist.getAge30womancounthigh()+pdfwaist.getAge30womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30womancounthigh(), pdfwaist.getAge30womancounthigh()+pdfwaist.getAge30womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30_40mancountnormal()+pdfwaist.getAge30_40womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30_40mancountnormal()+pdfwaist.getAge30_40womancountnormal(), pdfwaist.getAge30_40mancounthigh()+pdfwaist.getAge30_40mancountnormal()+pdfwaist.getAge30_40womancounthigh()+pdfwaist.getAge30_40womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30_40mancounthigh()+pdfwaist.getAge30_40womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30_40mancounthigh()+pdfwaist.getAge30_40womancounthigh(), pdfwaist.getAge30_40mancounthigh()+pdfwaist.getAge30_40mancountnormal()+pdfwaist.getAge30_40womancounthigh()+pdfwaist.getAge30_40womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30_40mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30_40mancountnormal(), pdfwaist.getAge30_40mancounthigh()+pdfwaist.getAge30_40mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30_40mancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30_40mancounthigh(), pdfwaist.getAge30_40mancounthigh()+pdfwaist.getAge30_40mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30_40womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30_40womancountnormal(), pdfwaist.getAge30_40womancounthigh()+pdfwaist.getAge30_40womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge30_40womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge30_40womancounthigh(), pdfwaist.getAge30_40womancounthigh()+pdfwaist.getAge30_40womancountnormal()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan40_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount40_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan40_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount40_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan40_normal()))).append("\n").append(groupReportWaistline.getMan40_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan40_high_risk()))).append("\n").append(groupReportWaistline.getMan40_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman40_normal()))).append("\n").append(groupReportWaistline.getWoman40_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman40_high_risk()))).append("\n").append(groupReportWaistline.getWoman40_high_riskP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge40_45mancountnormal()+pdfwaist.getAge40_45womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge40_45mancountnormal()+pdfwaist.getAge40_45womancountnormal(), pdfwaist.getAge40_45mancounthigh()+pdfwaist.getAge40_45mancountnormal()+pdfwaist.getAge40_45womancounthigh()+pdfwaist.getAge40_45womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge40_45mancounthigh()+pdfwaist.getAge40_45womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge40_45mancounthigh()+pdfwaist.getAge40_45womancounthigh(), pdfwaist.getAge40_45mancounthigh()+pdfwaist.getAge40_45mancountnormal()+pdfwaist.getAge40_45womancounthigh()+pdfwaist.getAge40_45womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge40_45mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge40_45mancountnormal(), pdfwaist.getAge40_45mancounthigh()+pdfwaist.getAge40_45mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge40_45mancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge40_45mancounthigh(), pdfwaist.getAge40_45mancounthigh()+pdfwaist.getAge40_45mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge40_45womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge40_45womancountnormal(), pdfwaist.getAge40_45womancounthigh()+pdfwaist.getAge40_45womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge40_45womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge40_45womancounthigh(), pdfwaist.getAge40_45womancounthigh()+pdfwaist.getAge40_45womancountnormal()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan45_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount45_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan45_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount45_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan45_normal()))).append("\n").append(groupReportWaistline.getMan45_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan45_high_risk()))).append("\n").append(groupReportWaistline.getMan45_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman45_normal()))).append("\n").append(groupReportWaistline.getWoman45_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman45_high_risk()))).append("\n").append(groupReportWaistline.getWoman45_high_riskP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge45_50mancountnormal()+pdfwaist.getAge45_50womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge45_50mancountnormal()+pdfwaist.getAge45_50womancountnormal(), pdfwaist.getAge45_50mancounthigh()+pdfwaist.getAge45_50mancountnormal()+pdfwaist.getAge45_50womancounthigh()+pdfwaist.getAge45_50womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge45_50mancounthigh()+pdfwaist.getAge45_50womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge45_50mancounthigh()+pdfwaist.getAge45_50womancounthigh(), pdfwaist.getAge45_50mancounthigh()+pdfwaist.getAge45_50mancountnormal()+pdfwaist.getAge45_50womancounthigh()+pdfwaist.getAge45_50womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge45_50mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge45_50mancountnormal(), pdfwaist.getAge45_50mancounthigh()+pdfwaist.getAge45_50mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge45_50mancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge45_50mancounthigh(), pdfwaist.getAge45_50mancounthigh()+pdfwaist.getAge45_50mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge45_50womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge45_50womancountnormal(), pdfwaist.getAge45_50womancounthigh()+pdfwaist.getAge45_50womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge45_50womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge45_50womancounthigh(), pdfwaist.getAge45_50womancounthigh()+pdfwaist.getAge45_50womancountnormal()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan50_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount50_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan50_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount50_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan50_normal()))).append("\n").append(groupReportWaistline.getMan50_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan50_high_risk()))).append("\n").append(groupReportWaistline.getMan50_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman50_normal()))).append("\n").append(groupReportWaistline.getWoman50_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman50_high_risk()))).append("\n").append(groupReportWaistline.getWoman50_high_riskP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("50－55 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge50_55mancountnormal()+pdfwaist.getAge50_55womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge50_55mancountnormal()+pdfwaist.getAge50_55womancountnormal(), pdfwaist.getAge50_55mancounthigh()+pdfwaist.getAge50_55mancountnormal()+pdfwaist.getAge50_55womancounthigh()+pdfwaist.getAge50_55womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge50_55mancounthigh()+pdfwaist.getAge50_55womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge50_55mancounthigh()+pdfwaist.getAge50_55womancounthigh(), pdfwaist.getAge50_55mancounthigh()+pdfwaist.getAge50_55mancountnormal()+pdfwaist.getAge50_55womancounthigh()+pdfwaist.getAge50_55womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge50_55mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge50_55mancountnormal(), pdfwaist.getAge50_55mancounthigh()+pdfwaist.getAge50_55mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge50_55mancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge50_55mancounthigh(), pdfwaist.getAge50_55mancounthigh()+pdfwaist.getAge50_55mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge50_55womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge50_55womancountnormal(), pdfwaist.getAge50_55womancounthigh()+pdfwaist.getAge50_55womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge50_55womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge50_55womancounthigh(), pdfwaist.getAge50_55womancounthigh()+pdfwaist.getAge50_55womancountnormal()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan55_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount55_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan55_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount55_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan55_normal()))).append("\n").append(groupReportWaistline.getMan55_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan55_high_risk()))).append("\n").append(groupReportWaistline.getMan55_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman55_normal()))).append("\n").append(groupReportWaistline.getWoman55_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman55_high_risk()))).append("\n").append(groupReportWaistline.getWoman55_high_riskP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("55－60 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge55_60mancountnormal()+pdfwaist.getAge55_60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge55_60mancountnormal()+pdfwaist.getAge55_60womancountnormal(), pdfwaist.getAge55_60mancounthigh()+pdfwaist.getAge55_60mancountnormal()+pdfwaist.getAge55_60womancounthigh()+pdfwaist.getAge55_60womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge55_60mancounthigh()+pdfwaist.getAge55_60womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge55_60mancounthigh()+pdfwaist.getAge55_60womancounthigh(), pdfwaist.getAge55_60mancounthigh()+pdfwaist.getAge55_60mancountnormal()+pdfwaist.getAge55_60womancounthigh()+pdfwaist.getAge55_60womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge55_60mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge55_60mancountnormal(), pdfwaist.getAge55_60mancounthigh()+pdfwaist.getAge55_60mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge55_60mancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge55_60mancounthigh(), pdfwaist.getAge55_60mancounthigh()+pdfwaist.getAge55_60mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge55_60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge55_60womancountnormal(), pdfwaist.getAge55_60womancounthigh()+pdfwaist.getAge55_60womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge55_60womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge55_60womancounthigh(), pdfwaist.getAge55_60womancounthigh()+pdfwaist.getAge55_60womancountnormal()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan60_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount60_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan60_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount60_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan60_normal()))).append("\n").append(groupReportWaistline.getMan60_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan60_high_risk()))).append("\n").append(groupReportWaistline.getMan60_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman60_normal()))).append("\n").append(groupReportWaistline.getWoman60_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman60_high_risk()))).append("\n").append(groupReportWaistline.getWoman60_high_riskP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge60mancountnormal()+pdfwaist.getAge60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge60mancountnormal()+pdfwaist.getAge60womancountnormal(), pdfwaist.getAge60mancounthigh()+pdfwaist.getAge60mancountnormal()+pdfwaist.getAge60womancounthigh()+pdfwaist.getAge60womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge60mancounthigh()+pdfwaist.getAge60womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge60mancounthigh()+pdfwaist.getAge60womancounthigh(), pdfwaist.getAge60mancounthigh()+pdfwaist.getAge60mancountnormal()+pdfwaist.getAge60womancounthigh()+pdfwaist.getAge60womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge60mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge60mancountnormal(), pdfwaist.getAge60mancounthigh()+pdfwaist.getAge60mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge60mancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge60mancounthigh(), pdfwaist.getAge60mancounthigh()+pdfwaist.getAge60mancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge60womancountnormal(), pdfwaist.getAge60womancounthigh()+pdfwaist.getAge60womancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getAge60womancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getAge60womancounthigh(), pdfwaist.getAge60womancounthigh()+pdfwaist.getAge60womancountnormal()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan70_normal() + groupReportWaistline.getWoman30_normal()))).append("\n").append(groupReportWaistline.getCount70_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan70_high_risk() + groupReportWaistline.getWoman30_high_risk()))).append("\n").append(groupReportWaistline.getCount70_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan70_normal()))).append("\n").append(groupReportWaistline.getMan70_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan70_high_risk()))).append("\n").append(groupReportWaistline.getMan70_high_riskP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman70_normal()))).append("\n").append(groupReportWaistline.getWoman70_normalP()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman70_high_risk()))).append("\n").append(groupReportWaistline.getWoman70_high_riskP()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getMancountnormal()+pdfwaist.getWomancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getMancountnormal()+pdfwaist.getWomancountnormal(), pdfwaist.getMancounthigh()+pdfwaist.getMancountnormal()+pdfwaist.getWomancounthigh()+pdfwaist.getWomancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getMancounthigh()+pdfwaist.getWomancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getMancounthigh()+pdfwaist.getWomancounthigh(), pdfwaist.getMancounthigh()+pdfwaist.getMancountnormal()+pdfwaist.getWomancounthigh()+pdfwaist.getWomancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getMancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getMancountnormal(), pdfwaist.getMancounthigh()+pdfwaist.getMancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getMancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getMancounthigh(), pdfwaist.getMancounthigh()+pdfwaist.getMancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getWomancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getWomancountnormal(), pdfwaist.getWomancounthigh()+pdfwaist.getWomancountnormal()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfwaist.getWomancounthigh()))).append("\n").append(String.format("%.2f",persent(pdfwaist.getWomancounthigh(), pdfwaist.getWomancounthigh()+pdfwaist.getWomancountnormal()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan_normal_count() + groupReportWaistline.getWoman_normal_count()))).append("\n").append(groupReportWaistline.getNormal()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan_high_risk_count() + groupReportWaistline.getWoman_high_risk_count()))).append("\n").append(groupReportWaistline.getHigh_risk()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan_normal_count()))).append("\n").append(groupReportWaistline.getMan_normal()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getMan_high_risk_count()))).append("\n").append(groupReportWaistline.getMan_high_risk()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman_normal_count()))).append("\n").append(groupReportWaistline.getWoman_normal()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportWaistline.getWoman_high_risk_count()))).append("\n").append(groupReportWaistline.getWoman_high_risk()).toString(), font2, 1, TableLineColor));*/
		addBlankCell(table_Page5, 13);
		pdfPTable.addCell(table_Page5);
		str2 = "注: 正常：男性<85cm，女性<80cm； 过大：男性≥85cm，女性≥80cm。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportBloodPressurePdf(YhExportPdfHypertension pdfhypertension)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportBloodPressure groupReportBloodPressure = groupReport.getGroupReportBloodPressure();
		str1 = "血压情况";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      高血压是引起心脑血管疾病最重要的危险因素，其并发症脑卒中、冠心病、心力衰竭、肾功能衰竭等疾患具有高度的致死率和致残率，严重危害人类健康。评估对象血压按年龄及性别具体分布如下所示： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(pdfhypertension.getJFreeChartPath(pdfhypertension), width_table_Page2);
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30mancountnormal()+pdfhypertension.getAge30womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30mancountnormal()+pdfhypertension.getAge30womancountnormal(), pdfhypertension.getAge30mancountnormal()+pdfhypertension.getAge30manounthigh()+pdfhypertension.getAge30manounthighside()+pdfhypertension.getAge30womancountnormal()+pdfhypertension.getAge30womanounthigh()+pdfhypertension.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30manounthighside() + pdfhypertension.getAge30womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30manounthighside()+pdfhypertension.getAge30womanounthighside(), pdfhypertension.getAge30mancountnormal()+pdfhypertension.getAge30manounthigh()+pdfhypertension.getAge30manounthighside()+pdfhypertension.getAge30womancountnormal()+pdfhypertension.getAge30womanounthigh()+pdfhypertension.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30manounthigh()+pdfhypertension.getAge30womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30manounthigh()+pdfhypertension.getAge30womanounthigh(), pdfhypertension.getAge30mancountnormal()+pdfhypertension.getAge30manounthigh()+pdfhypertension.getAge30manounthighside()+pdfhypertension.getAge30womancountnormal()+pdfhypertension.getAge30womanounthigh()+pdfhypertension.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30mancountnormal(), pdfhypertension.getAge30mancountnormal()+pdfhypertension.getAge30manounthigh()+pdfhypertension.getAge30manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30manounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30manounthighside(), pdfhypertension.getAge30mancountnormal()+pdfhypertension.getAge30manounthigh()+pdfhypertension.getAge30manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30manounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30manounthigh(), pdfhypertension.getAge30mancountnormal()+pdfhypertension.getAge30manounthigh()+pdfhypertension.getAge30manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30womancountnormal(), pdfhypertension.getAge30womancountnormal()+pdfhypertension.getAge30womanounthigh()+pdfhypertension.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30womanounthighside(), pdfhypertension.getAge30womancountnormal()+pdfhypertension.getAge30womanounthigh()+pdfhypertension.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30womanounthigh(), pdfhypertension.getAge30womancountnormal()+pdfhypertension.getAge30womanounthigh()+pdfhypertension.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30_40mancountnormal()+pdfhypertension.getAge30_40womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30_40mancountnormal()+pdfhypertension.getAge30_40womancountnormal(), pdfhypertension.getAge30_40mancountnormal()+pdfhypertension.getAge30_40manounthigh()+pdfhypertension.getAge30_40manounthighside()+pdfhypertension.getAge30_40womancountnormal()+pdfhypertension.getAge30_40womanounthigh()+pdfhypertension.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30_40manounthighside() + pdfhypertension.getAge30_40womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30_40manounthighside()+pdfhypertension.getAge30_40womanounthighside(), pdfhypertension.getAge30_40mancountnormal()+pdfhypertension.getAge30_40manounthigh()+pdfhypertension.getAge30_40manounthighside()+pdfhypertension.getAge30_40womancountnormal()+pdfhypertension.getAge30_40womanounthigh()+pdfhypertension.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30_40manounthigh()+pdfhypertension.getAge30_40womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30_40manounthigh()+pdfhypertension.getAge30_40womanounthigh(), pdfhypertension.getAge30_40mancountnormal()+pdfhypertension.getAge30_40manounthigh()+pdfhypertension.getAge30_40manounthighside()+pdfhypertension.getAge30_40womancountnormal()+pdfhypertension.getAge30_40womanounthigh()+pdfhypertension.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30_40mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30_40mancountnormal(), pdfhypertension.getAge30_40mancountnormal()+pdfhypertension.getAge30_40manounthigh()+pdfhypertension.getAge30_40manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30_40manounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30_40manounthighside(), pdfhypertension.getAge30_40mancountnormal()+pdfhypertension.getAge30_40manounthigh()+pdfhypertension.getAge30_40manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30_40manounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30_40manounthigh(), pdfhypertension.getAge30_40mancountnormal()+pdfhypertension.getAge30_40manounthigh()+pdfhypertension.getAge30_40manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30_40womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30_40womancountnormal(), pdfhypertension.getAge30_40womancountnormal()+pdfhypertension.getAge30_40womanounthigh()+pdfhypertension.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30_40womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30_40womanounthighside(), pdfhypertension.getAge30_40womancountnormal()+pdfhypertension.getAge30_40womanounthigh()+pdfhypertension.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge30_40womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge30_40womanounthigh(), pdfhypertension.getAge30_40womancountnormal()+pdfhypertension.getAge30_40womanounthigh()+pdfhypertension.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_low() + groupReportBloodPressure.getWoman40_low()))).append("\n").append(groupReportBloodPressure.getAll40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_in() + groupReportBloodPressure.getWoman40_in()))).append("\n").append(groupReportBloodPressure.getAll40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_high() + groupReportBloodPressure.getWoman40_high()))).append("\n").append(groupReportBloodPressure.getAll40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_low()))).append("\n").append(groupReportBloodPressure.getMan40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_in()))).append("\n").append(groupReportBloodPressure.getMan40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan40_high()))).append("\n").append(groupReportBloodPressure.getMan40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman40_low()))).append("\n").append(groupReportBloodPressure.getWoman40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman40_in()))).append("\n").append(groupReportBloodPressure.getWoman40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman40_high()))).append("\n").append(groupReportBloodPressure.getWoman40_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge40_45mancountnormal()+pdfhypertension.getAge40_45womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge40_45mancountnormal()+pdfhypertension.getAge40_45womancountnormal(), pdfhypertension.getAge40_45mancountnormal()+pdfhypertension.getAge40_45manounthigh()+pdfhypertension.getAge40_45manounthighside()+pdfhypertension.getAge40_45womancountnormal()+pdfhypertension.getAge40_45womanounthigh()+pdfhypertension.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge40_45manounthighside() + pdfhypertension.getAge40_45womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge40_45manounthighside()+pdfhypertension.getAge40_45womanounthighside(), pdfhypertension.getAge40_45mancountnormal()+pdfhypertension.getAge40_45manounthigh()+pdfhypertension.getAge40_45manounthighside()+pdfhypertension.getAge40_45womancountnormal()+pdfhypertension.getAge40_45womanounthigh()+pdfhypertension.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge40_45manounthigh()+pdfhypertension.getAge40_45womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge40_45manounthigh()+pdfhypertension.getAge40_45womanounthigh(), pdfhypertension.getAge40_45mancountnormal()+pdfhypertension.getAge40_45manounthigh()+pdfhypertension.getAge40_45manounthighside()+pdfhypertension.getAge40_45womancountnormal()+pdfhypertension.getAge40_45womanounthigh()+pdfhypertension.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge40_45mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge40_45mancountnormal(), pdfhypertension.getAge40_45mancountnormal()+pdfhypertension.getAge40_45manounthigh()+pdfhypertension.getAge40_45manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge40_45manounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge40_45manounthighside(), pdfhypertension.getAge40_45mancountnormal()+pdfhypertension.getAge40_45manounthigh()+pdfhypertension.getAge40_45manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge40_45manounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge40_45manounthigh(), pdfhypertension.getAge40_45mancountnormal()+pdfhypertension.getAge40_45manounthigh()+pdfhypertension.getAge40_45manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge40_45womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge40_45womancountnormal(), pdfhypertension.getAge40_45womancountnormal()+pdfhypertension.getAge40_45womanounthigh()+pdfhypertension.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge40_45womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge40_45womanounthighside(), pdfhypertension.getAge40_45womancountnormal()+pdfhypertension.getAge40_45womanounthigh()+pdfhypertension.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge40_45womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge40_45womanounthigh(), pdfhypertension.getAge40_45womancountnormal()+pdfhypertension.getAge40_45womanounthigh()+pdfhypertension.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_low() + groupReportBloodPressure.getWoman45_low()))).append("\n").append(groupReportBloodPressure.getAll45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_in() + groupReportBloodPressure.getWoman45_in()))).append("\n").append(groupReportBloodPressure.getAll45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_high() + groupReportBloodPressure.getWoman45_high()))).append("\n").append(groupReportBloodPressure.getAll45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_low()))).append("\n").append(groupReportBloodPressure.getMan45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_in()))).append("\n").append(groupReportBloodPressure.getMan45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan45_high()))).append("\n").append(groupReportBloodPressure.getMan45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman45_low()))).append("\n").append(groupReportBloodPressure.getWoman45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman45_in()))).append("\n").append(groupReportBloodPressure.getWoman45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman45_high()))).append("\n").append(groupReportBloodPressure.getWoman45_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge45_50mancountnormal()+pdfhypertension.getAge45_50womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge45_50mancountnormal()+pdfhypertension.getAge45_50womancountnormal(), pdfhypertension.getAge45_50mancountnormal()+pdfhypertension.getAge45_50manounthigh()+pdfhypertension.getAge45_50manounthighside()+pdfhypertension.getAge45_50womancountnormal()+pdfhypertension.getAge45_50womanounthigh()+pdfhypertension.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge45_50manounthighside() + pdfhypertension.getAge45_50womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge45_50manounthighside()+pdfhypertension.getAge45_50womanounthighside(), pdfhypertension.getAge45_50mancountnormal()+pdfhypertension.getAge45_50manounthigh()+pdfhypertension.getAge45_50manounthighside()+pdfhypertension.getAge45_50womancountnormal()+pdfhypertension.getAge45_50womanounthigh()+pdfhypertension.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge45_50manounthigh()+pdfhypertension.getAge45_50womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge45_50manounthigh()+pdfhypertension.getAge45_50womanounthigh(), pdfhypertension.getAge45_50mancountnormal()+pdfhypertension.getAge45_50manounthigh()+pdfhypertension.getAge45_50manounthighside()+pdfhypertension.getAge45_50womancountnormal()+pdfhypertension.getAge45_50womanounthigh()+pdfhypertension.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge45_50mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge45_50mancountnormal(), pdfhypertension.getAge45_50mancountnormal()+pdfhypertension.getAge45_50manounthigh()+pdfhypertension.getAge45_50manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge45_50manounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge45_50manounthighside(), pdfhypertension.getAge45_50mancountnormal()+pdfhypertension.getAge45_50manounthigh()+pdfhypertension.getAge45_50manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge45_50manounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge45_50manounthigh(), pdfhypertension.getAge45_50mancountnormal()+pdfhypertension.getAge45_50manounthigh()+pdfhypertension.getAge45_50manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge45_50womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge45_50womancountnormal(), pdfhypertension.getAge45_50womancountnormal()+pdfhypertension.getAge45_50womanounthigh()+pdfhypertension.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge45_50womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge45_50womanounthighside(), pdfhypertension.getAge45_50womancountnormal()+pdfhypertension.getAge45_50womanounthigh()+pdfhypertension.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge45_50womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge45_50womanounthigh(), pdfhypertension.getAge45_50womancountnormal()+pdfhypertension.getAge45_50womanounthigh()+pdfhypertension.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_low() + groupReportBloodPressure.getWoman50_low()))).append("\n").append(groupReportBloodPressure.getAll50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_in() + groupReportBloodPressure.getWoman50_in()))).append("\n").append(groupReportBloodPressure.getAll50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_high() + groupReportBloodPressure.getWoman50_high()))).append("\n").append(groupReportBloodPressure.getAll50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_low()))).append("\n").append(groupReportBloodPressure.getMan50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_in()))).append("\n").append(groupReportBloodPressure.getMan50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan50_high()))).append("\n").append(groupReportBloodPressure.getMan50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman50_low()))).append("\n").append(groupReportBloodPressure.getWoman50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman50_in()))).append("\n").append(groupReportBloodPressure.getWoman50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman50_high()))).append("\n").append(groupReportBloodPressure.getWoman50_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("50-55 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge50_55mancountnormal()+pdfhypertension.getAge50_55womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge50_55mancountnormal()+pdfhypertension.getAge50_55womancountnormal(), pdfhypertension.getAge50_55mancountnormal()+pdfhypertension.getAge50_55manounthigh()+pdfhypertension.getAge50_55manounthighside()+pdfhypertension.getAge50_55womancountnormal()+pdfhypertension.getAge50_55womanounthigh()+pdfhypertension.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge50_55manounthighside() + pdfhypertension.getAge50_55womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge50_55manounthighside()+pdfhypertension.getAge50_55womanounthighside(), pdfhypertension.getAge50_55mancountnormal()+pdfhypertension.getAge50_55manounthigh()+pdfhypertension.getAge50_55manounthighside()+pdfhypertension.getAge50_55womancountnormal()+pdfhypertension.getAge50_55womanounthigh()+pdfhypertension.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge50_55manounthigh()+pdfhypertension.getAge50_55womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge50_55manounthigh()+pdfhypertension.getAge50_55womanounthigh(), pdfhypertension.getAge50_55mancountnormal()+pdfhypertension.getAge50_55manounthigh()+pdfhypertension.getAge50_55manounthighside()+pdfhypertension.getAge50_55womancountnormal()+pdfhypertension.getAge50_55womanounthigh()+pdfhypertension.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge50_55mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge50_55mancountnormal(), pdfhypertension.getAge50_55mancountnormal()+pdfhypertension.getAge50_55manounthigh()+pdfhypertension.getAge50_55manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge50_55manounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge50_55manounthighside(), pdfhypertension.getAge50_55mancountnormal()+pdfhypertension.getAge50_55manounthigh()+pdfhypertension.getAge50_55manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge50_55manounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge50_55manounthigh(), pdfhypertension.getAge50_55mancountnormal()+pdfhypertension.getAge50_55manounthigh()+pdfhypertension.getAge50_55manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge50_55womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge50_55womancountnormal(), pdfhypertension.getAge50_55womancountnormal()+pdfhypertension.getAge50_55womanounthigh()+pdfhypertension.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge50_55womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge50_55womanounthighside(), pdfhypertension.getAge50_55womancountnormal()+pdfhypertension.getAge50_55womanounthigh()+pdfhypertension.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge50_55womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge50_55womanounthigh(), pdfhypertension.getAge50_55womancountnormal()+pdfhypertension.getAge50_55womanounthigh()+pdfhypertension.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_low() + groupReportBloodPressure.getWoman55_low()))).append("\n").append(groupReportBloodPressure.getAll55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_in() + groupReportBloodPressure.getWoman55_in()))).append("\n").append(groupReportBloodPressure.getAll55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_high() + groupReportBloodPressure.getWoman55_high()))).append("\n").append(groupReportBloodPressure.getAll55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_low()))).append("\n").append(groupReportBloodPressure.getMan55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_in()))).append("\n").append(groupReportBloodPressure.getMan55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan55_high()))).append("\n").append(groupReportBloodPressure.getMan55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman55_low()))).append("\n").append(groupReportBloodPressure.getWoman55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman55_in()))).append("\n").append(groupReportBloodPressure.getWoman55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman55_high()))).append("\n").append(groupReportBloodPressure.getWoman55_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("55-60", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge55_60mancountnormal()+pdfhypertension.getAge55_60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge55_60mancountnormal()+pdfhypertension.getAge55_60womancountnormal(), pdfhypertension.getAge55_60mancountnormal()+pdfhypertension.getAge55_60manounthigh()+pdfhypertension.getAge55_60manounthighside()+pdfhypertension.getAge55_60womancountnormal()+pdfhypertension.getAge55_60womanounthigh()+pdfhypertension.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge55_60manounthighside() + pdfhypertension.getAge55_60womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge55_60manounthighside()+pdfhypertension.getAge55_60womanounthighside(), pdfhypertension.getAge55_60mancountnormal()+pdfhypertension.getAge55_60manounthigh()+pdfhypertension.getAge55_60manounthighside()+pdfhypertension.getAge55_60womancountnormal()+pdfhypertension.getAge55_60womanounthigh()+pdfhypertension.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge55_60manounthigh()+pdfhypertension.getAge55_60womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge55_60manounthigh()+pdfhypertension.getAge55_60womanounthigh(), pdfhypertension.getAge55_60mancountnormal()+pdfhypertension.getAge55_60manounthigh()+pdfhypertension.getAge55_60manounthighside()+pdfhypertension.getAge55_60womancountnormal()+pdfhypertension.getAge55_60womanounthigh()+pdfhypertension.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge55_60mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge55_60mancountnormal(), pdfhypertension.getAge55_60mancountnormal()+pdfhypertension.getAge55_60manounthigh()+pdfhypertension.getAge55_60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge55_60manounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge55_60manounthighside(), pdfhypertension.getAge55_60mancountnormal()+pdfhypertension.getAge55_60manounthigh()+pdfhypertension.getAge55_60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge55_60manounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge55_60manounthigh(), pdfhypertension.getAge55_60mancountnormal()+pdfhypertension.getAge55_60manounthigh()+pdfhypertension.getAge55_60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge55_60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge55_60womancountnormal(), pdfhypertension.getAge55_60womancountnormal()+pdfhypertension.getAge55_60womanounthigh()+pdfhypertension.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge55_60womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge55_60womanounthighside(), pdfhypertension.getAge55_60womancountnormal()+pdfhypertension.getAge55_60womanounthigh()+pdfhypertension.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge55_60womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge55_60womanounthigh(), pdfhypertension.getAge55_60womancountnormal()+pdfhypertension.getAge55_60womanounthigh()+pdfhypertension.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_low() + groupReportBloodPressure.getWoman60_low()))).append("\n").append(groupReportBloodPressure.getAll60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_in() + groupReportBloodPressure.getWoman60_in()))).append("\n").append(groupReportBloodPressure.getAll60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_high() + groupReportBloodPressure.getWoman60_high()))).append("\n").append(groupReportBloodPressure.getAll60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_low()))).append("\n").append(groupReportBloodPressure.getMan60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_in()))).append("\n").append(groupReportBloodPressure.getMan60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan60_high()))).append("\n").append(groupReportBloodPressure.getMan60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman60_low()))).append("\n").append(groupReportBloodPressure.getWoman60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman60_in()))).append("\n").append(groupReportBloodPressure.getWoman60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman60_high()))).append("\n").append(groupReportBloodPressure.getWoman60_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge60mancountnormal()+pdfhypertension.getAge60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge60mancountnormal()+pdfhypertension.getAge60womancountnormal(), pdfhypertension.getAge60mancountnormal()+pdfhypertension.getAge60manounthigh()+pdfhypertension.getAge60manounthighside()+pdfhypertension.getAge60womancountnormal()+pdfhypertension.getAge60womanounthigh()+pdfhypertension.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge60manounthighside() + pdfhypertension.getAge60womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge60manounthighside()+pdfhypertension.getAge60womanounthighside(), pdfhypertension.getAge60mancountnormal()+pdfhypertension.getAge60manounthigh()+pdfhypertension.getAge60manounthighside()+pdfhypertension.getAge60womancountnormal()+pdfhypertension.getAge60womanounthigh()+pdfhypertension.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge60manounthigh()+pdfhypertension.getAge60womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge60manounthigh()+pdfhypertension.getAge60womanounthigh(), pdfhypertension.getAge60mancountnormal()+pdfhypertension.getAge60manounthigh()+pdfhypertension.getAge60manounthighside()+pdfhypertension.getAge60womancountnormal()+pdfhypertension.getAge60womanounthigh()+pdfhypertension.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge60mancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge60mancountnormal(), pdfhypertension.getAge60mancountnormal()+pdfhypertension.getAge60manounthigh()+pdfhypertension.getAge60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge60manounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge60manounthighside(), pdfhypertension.getAge60mancountnormal()+pdfhypertension.getAge60manounthigh()+pdfhypertension.getAge60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge60manounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge60manounthigh(), pdfhypertension.getAge60mancountnormal()+pdfhypertension.getAge60manounthigh()+pdfhypertension.getAge60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge60womancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge60womancountnormal(), pdfhypertension.getAge60womancountnormal()+pdfhypertension.getAge60womanounthigh()+pdfhypertension.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge60womanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge60womanounthighside(), pdfhypertension.getAge60womancountnormal()+pdfhypertension.getAge60womanounthigh()+pdfhypertension.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getAge60womanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getAge60womanounthigh(), pdfhypertension.getAge60womancountnormal()+pdfhypertension.getAge60womanounthigh()+pdfhypertension.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_low() + groupReportBloodPressure.getWoman70_low()))).append("\n").append(groupReportBloodPressure.getAll70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_in() + groupReportBloodPressure.getWoman70_in()))).append("\n").append(groupReportBloodPressure.getAll70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_high() + groupReportBloodPressure.getWoman70_high()))).append("\n").append(groupReportBloodPressure.getAll70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_low()))).append("\n").append(groupReportBloodPressure.getMan70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_in()))).append("\n").append(groupReportBloodPressure.getMan70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan70_high()))).append("\n").append(groupReportBloodPressure.getMan70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman70_low()))).append("\n").append(groupReportBloodPressure.getWoman70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman70_in()))).append("\n").append(groupReportBloodPressure.getWoman70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman70_high()))).append("\n").append(groupReportBloodPressure.getWoman70_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计  ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getMancountnormal()+pdfhypertension.getWomancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getMancountnormal()+pdfhypertension.getWomancountnormal(), pdfhypertension.getMancountnormal()+pdfhypertension.getManounthigh()+pdfhypertension.getManounthighside()+pdfhypertension.getWomancountnormal()+pdfhypertension.getWomanounthigh()+pdfhypertension.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getManounthighside() + pdfhypertension.getWomanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getManounthighside()+pdfhypertension.getWomanounthighside(), pdfhypertension.getMancountnormal()+pdfhypertension.getManounthigh()+pdfhypertension.getManounthighside()+pdfhypertension.getWomancountnormal()+pdfhypertension.getWomanounthigh()+pdfhypertension.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getManounthigh()+pdfhypertension.getWomanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getManounthigh()+pdfhypertension.getWomanounthigh(), pdfhypertension.getMancountnormal()+pdfhypertension.getManounthigh()+pdfhypertension.getManounthighside()+pdfhypertension.getWomancountnormal()+pdfhypertension.getWomanounthigh()+pdfhypertension.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getMancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getMancountnormal(), pdfhypertension.getMancountnormal()+pdfhypertension.getManounthigh()+pdfhypertension.getManounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getManounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getManounthighside(), pdfhypertension.getMancountnormal()+pdfhypertension.getManounthigh()+pdfhypertension.getManounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getManounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getManounthigh(), pdfhypertension.getMancountnormal()+pdfhypertension.getManounthigh()+pdfhypertension.getManounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getWomancountnormal()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getWomancountnormal(), pdfhypertension.getWomancountnormal()+pdfhypertension.getWomanounthigh()+pdfhypertension.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getWomanounthighside()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getWomanounthighside(), pdfhypertension.getWomancountnormal()+pdfhypertension.getWomanounthigh()+pdfhypertension.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(pdfhypertension.getWomanounthigh()))).append("\n").append(String.format("%.2f",persent(pdfhypertension.getWomanounthigh(), pdfhypertension.getWomancountnormal()+pdfhypertension.getWomanounthigh()+pdfhypertension.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getAll_low()))).append("\n").append(groupReportBloodPressure.getAll_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getAll_in()))).append("\n").append(groupReportBloodPressure.getAll_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getAll_high()))).append("\n").append(groupReportBloodPressure.getAll_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan_low()))).append("\n").append(groupReportBloodPressure.getMan_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan_in()))).append("\n").append(groupReportBloodPressure.getMan_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getMan_high()))).append("\n").append(groupReportBloodPressure.getMan_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman_low()))).append("\n").append(groupReportBloodPressure.getWoman_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman_in()))).append("\n").append(groupReportBloodPressure.getWoman_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodPressure.getWoman_high()))).append("\n").append(groupReportBloodPressure.getWoman_high_max()).toString(), font2, 1, TableLineColor));*/
		addBlankCell(table_Page5, 10);
		pdfPTable.addCell(table_Page5);
		str2 = "正常血压：收缩压<120 mmHg且舒张压<80 mmHg； 高值血压：120≤收缩压<140 mmHg，或80≤舒张压<90 mmHg；高血压：收缩压≥140 mmHg，或舒张压≥90 mmHg。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportBloodSugarPdf(YhExportPdfSugar sugar)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportBloodSugar groupReportBloodSugar = groupReport.getGroupReportBloodSugar();
		str1 = "血糖情况 ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      血糖水平越高，患糖尿病的危险性就越大，糖尿病易并发大血管和微血管病变。评估对象的血糖检测结果分布如下所示：  ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(sugar.getJFreeChartPath(sugar), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象血糖检测结果按年龄及性别分布（%）";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		Font font3_temp = font3;
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30mancountnormal()+sugar.getAge30womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30mancountnormal()+sugar.getAge30womancountnormal(), sugar.getAge30mancountnormal()+sugar.getAge30manounthigh()+sugar.getAge30manounthighside()+sugar.getAge30womancountnormal()+sugar.getAge30womanounthigh()+sugar.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30manounthighside() + sugar.getAge30womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30manounthighside()+sugar.getAge30womanounthighside(), sugar.getAge30mancountnormal()+sugar.getAge30manounthigh()+sugar.getAge30manounthighside()+sugar.getAge30womancountnormal()+sugar.getAge30womanounthigh()+sugar.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30manounthigh()+sugar.getAge30womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30manounthigh()+sugar.getAge30womanounthigh(), sugar.getAge30mancountnormal()+sugar.getAge30manounthigh()+sugar.getAge30manounthighside()+sugar.getAge30womancountnormal()+sugar.getAge30womanounthigh()+sugar.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30mancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30mancountnormal(), sugar.getAge30mancountnormal()+sugar.getAge30manounthigh()+sugar.getAge30manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30manounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30manounthighside(), sugar.getAge30mancountnormal()+sugar.getAge30manounthigh()+sugar.getAge30manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30manounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30manounthigh(), sugar.getAge30mancountnormal()+sugar.getAge30manounthigh()+sugar.getAge30manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30womancountnormal(), sugar.getAge30womancountnormal()+sugar.getAge30womanounthigh()+sugar.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30womanounthighside(), sugar.getAge30womancountnormal()+sugar.getAge30womanounthigh()+sugar.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30womanounthigh(), sugar.getAge30womancountnormal()+sugar.getAge30womanounthigh()+sugar.getAge30womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_low() + groupReportBloodSugar.getWoman30_low()))).append("\n").append(groupReportBloodSugar.getAll30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_in() + groupReportBloodSugar.getWoman30_in()))).append("\n").append(groupReportBloodSugar.getAll30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_high() + groupReportBloodSugar.getWoman30_high()))).append("\n").append(groupReportBloodSugar.getAll30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_low()))).append("\n").append(groupReportBloodSugar.getMan30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_in()))).append("\n").append(groupReportBloodSugar.getMan30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan30_high()))).append("\n").append(groupReportBloodSugar.getMan30_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman30_low()))).append("\n").append(groupReportBloodSugar.getWoman30_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman30_in()))).append("\n").append(groupReportBloodSugar.getWoman30_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman30_high()))).append("\n").append(groupReportBloodSugar.getWoman30_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30_40mancountnormal()+sugar.getAge30_40womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30_40mancountnormal()+sugar.getAge30_40womancountnormal(), sugar.getAge30_40mancountnormal()+sugar.getAge30_40manounthigh()+sugar.getAge30_40manounthighside()+sugar.getAge30_40womancountnormal()+sugar.getAge30_40womanounthigh()+sugar.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30_40manounthighside() + sugar.getAge30_40womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30_40manounthighside()+sugar.getAge30_40womanounthighside(), sugar.getAge30_40mancountnormal()+sugar.getAge30_40manounthigh()+sugar.getAge30_40manounthighside()+sugar.getAge30_40womancountnormal()+sugar.getAge30_40womanounthigh()+sugar.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30_40manounthigh()+sugar.getAge30_40womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30_40manounthigh()+sugar.getAge30_40womanounthigh(), sugar.getAge30_40mancountnormal()+sugar.getAge30_40manounthigh()+sugar.getAge30_40manounthighside()+sugar.getAge30_40womancountnormal()+sugar.getAge30_40womanounthigh()+sugar.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30_40mancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30_40mancountnormal(), sugar.getAge30_40mancountnormal()+sugar.getAge30_40manounthigh()+sugar.getAge30_40manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30_40manounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30_40manounthighside(), sugar.getAge30_40mancountnormal()+sugar.getAge30_40manounthigh()+sugar.getAge30_40manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30_40manounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30_40manounthigh(), sugar.getAge30_40mancountnormal()+sugar.getAge30_40manounthigh()+sugar.getAge30_40manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30_40womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30_40womancountnormal(), sugar.getAge30_40womancountnormal()+sugar.getAge30_40womanounthigh()+sugar.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30_40womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30_40womanounthighside(), sugar.getAge30_40womancountnormal()+sugar.getAge30_40womanounthigh()+sugar.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge30_40womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge30_40womanounthigh(), sugar.getAge30_40womancountnormal()+sugar.getAge30_40womanounthigh()+sugar.getAge30_40womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_low() + groupReportBloodSugar.getWoman40_low()))).append("\n").append(groupReportBloodSugar.getAll40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_in() + groupReportBloodSugar.getWoman40_in()))).append("\n").append(groupReportBloodSugar.getAll40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_high() + groupReportBloodSugar.getWoman40_high()))).append("\n").append(groupReportBloodSugar.getAll40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_low()))).append("\n").append(groupReportBloodSugar.getMan40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_in()))).append("\n").append(groupReportBloodSugar.getMan40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan40_high()))).append("\n").append(groupReportBloodSugar.getMan40_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman40_low()))).append("\n").append(groupReportBloodSugar.getWoman40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman40_in()))).append("\n").append(groupReportBloodSugar.getWoman40_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman40_high()))).append("\n").append(groupReportBloodSugar.getWoman40_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge40_45mancountnormal()+sugar.getAge40_45womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge40_45mancountnormal()+sugar.getAge40_45womancountnormal(), sugar.getAge40_45mancountnormal()+sugar.getAge40_45manounthigh()+sugar.getAge40_45manounthighside()+sugar.getAge40_45womancountnormal()+sugar.getAge40_45womanounthigh()+sugar.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge40_45manounthighside() + sugar.getAge40_45womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge40_45manounthighside()+sugar.getAge40_45womanounthighside(), sugar.getAge40_45mancountnormal()+sugar.getAge40_45manounthigh()+sugar.getAge40_45manounthighside()+sugar.getAge40_45womancountnormal()+sugar.getAge40_45womanounthigh()+sugar.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge40_45manounthigh()+sugar.getAge40_45womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge40_45manounthigh()+sugar.getAge40_45womanounthigh(), sugar.getAge40_45mancountnormal()+sugar.getAge40_45manounthigh()+sugar.getAge40_45manounthighside()+sugar.getAge40_45womancountnormal()+sugar.getAge40_45womanounthigh()+sugar.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge40_45mancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge40_45mancountnormal(), sugar.getAge40_45mancountnormal()+sugar.getAge40_45manounthigh()+sugar.getAge40_45manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge40_45manounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge40_45manounthighside(), sugar.getAge40_45mancountnormal()+sugar.getAge40_45manounthigh()+sugar.getAge40_45manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge40_45manounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge40_45manounthigh(), sugar.getAge40_45mancountnormal()+sugar.getAge40_45manounthigh()+sugar.getAge40_45manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge40_45womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge40_45womancountnormal(), sugar.getAge40_45womancountnormal()+sugar.getAge40_45womanounthigh()+sugar.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge40_45womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge40_45womanounthighside(), sugar.getAge40_45womancountnormal()+sugar.getAge40_45womanounthigh()+sugar.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge40_45womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge40_45womanounthigh(), sugar.getAge40_45womancountnormal()+sugar.getAge40_45womanounthigh()+sugar.getAge40_45womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_low() + groupReportBloodSugar.getWoman45_low()))).append("\n").append(groupReportBloodSugar.getAll45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_in() + groupReportBloodSugar.getWoman45_in()))).append("\n").append(groupReportBloodSugar.getAll45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_high() + groupReportBloodSugar.getWoman45_high()))).append("\n").append(groupReportBloodSugar.getAll45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_low()))).append("\n").append(groupReportBloodSugar.getMan45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_in()))).append("\n").append(groupReportBloodSugar.getMan45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan45_high()))).append("\n").append(groupReportBloodSugar.getMan45_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman45_low()))).append("\n").append(groupReportBloodSugar.getWoman45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman45_in()))).append("\n").append(groupReportBloodSugar.getWoman45_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman45_high()))).append("\n").append(groupReportBloodSugar.getWoman45_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge45_50mancountnormal()+sugar.getAge45_50womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge45_50mancountnormal()+sugar.getAge45_50womancountnormal(), sugar.getAge45_50mancountnormal()+sugar.getAge45_50manounthigh()+sugar.getAge45_50manounthighside()+sugar.getAge45_50womancountnormal()+sugar.getAge45_50womanounthigh()+sugar.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge45_50manounthighside() + sugar.getAge45_50womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge45_50manounthighside()+sugar.getAge45_50womanounthighside(), sugar.getAge45_50mancountnormal()+sugar.getAge45_50manounthigh()+sugar.getAge45_50manounthighside()+sugar.getAge45_50womancountnormal()+sugar.getAge45_50womanounthigh()+sugar.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge45_50manounthigh()+sugar.getAge45_50womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge45_50manounthigh()+sugar.getAge45_50womanounthigh(), sugar.getAge45_50mancountnormal()+sugar.getAge45_50manounthigh()+sugar.getAge45_50manounthighside()+sugar.getAge45_50womancountnormal()+sugar.getAge45_50womanounthigh()+sugar.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge45_50mancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge45_50mancountnormal(), sugar.getAge45_50mancountnormal()+sugar.getAge45_50manounthigh()+sugar.getAge45_50manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge45_50manounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge45_50manounthighside(), sugar.getAge45_50mancountnormal()+sugar.getAge45_50manounthigh()+sugar.getAge45_50manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge45_50manounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge45_50manounthigh(), sugar.getAge45_50mancountnormal()+sugar.getAge45_50manounthigh()+sugar.getAge45_50manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge45_50womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge45_50womancountnormal(), sugar.getAge45_50womancountnormal()+sugar.getAge45_50womanounthigh()+sugar.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge45_50womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge45_50womanounthighside(), sugar.getAge45_50womancountnormal()+sugar.getAge45_50womanounthigh()+sugar.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge45_50womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge45_50womanounthigh(), sugar.getAge45_50womancountnormal()+sugar.getAge45_50womanounthigh()+sugar.getAge45_50womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_low() + groupReportBloodSugar.getWoman50_low()))).append("\n").append(groupReportBloodSugar.getAll50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_in() + groupReportBloodSugar.getWoman50_in()))).append("\n").append(groupReportBloodSugar.getAll50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_high() + groupReportBloodSugar.getWoman50_high()))).append("\n").append(groupReportBloodSugar.getAll50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_low()))).append("\n").append(groupReportBloodSugar.getMan50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_in()))).append("\n").append(groupReportBloodSugar.getMan50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan50_high()))).append("\n").append(groupReportBloodSugar.getMan50_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman50_low()))).append("\n").append(groupReportBloodSugar.getWoman50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman50_in()))).append("\n").append(groupReportBloodSugar.getWoman50_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman50_high()))).append("\n").append(groupReportBloodSugar.getWoman50_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("50-55 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge50_55mancountnormal()+sugar.getAge50_55womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge50_55mancountnormal()+sugar.getAge50_55womancountnormal(), sugar.getAge50_55mancountnormal()+sugar.getAge50_55manounthigh()+sugar.getAge50_55manounthighside()+sugar.getAge50_55womancountnormal()+sugar.getAge50_55womanounthigh()+sugar.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge50_55manounthighside() + sugar.getAge50_55womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge50_55manounthighside()+sugar.getAge50_55womanounthighside(), sugar.getAge50_55mancountnormal()+sugar.getAge50_55manounthigh()+sugar.getAge50_55manounthighside()+sugar.getAge50_55womancountnormal()+sugar.getAge50_55womanounthigh()+sugar.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge50_55manounthigh()+sugar.getAge50_55womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge50_55manounthigh()+sugar.getAge50_55womanounthigh(), sugar.getAge50_55mancountnormal()+sugar.getAge50_55manounthigh()+sugar.getAge50_55manounthighside()+sugar.getAge50_55womancountnormal()+sugar.getAge50_55womanounthigh()+sugar.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge50_55mancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge50_55mancountnormal(), sugar.getAge50_55mancountnormal()+sugar.getAge50_55manounthigh()+sugar.getAge50_55manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge50_55manounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge50_55manounthighside(), sugar.getAge50_55mancountnormal()+sugar.getAge50_55manounthigh()+sugar.getAge50_55manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge50_55manounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge50_55manounthigh(), sugar.getAge50_55mancountnormal()+sugar.getAge50_55manounthigh()+sugar.getAge50_55manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge50_55womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge50_55womancountnormal(), sugar.getAge50_55womancountnormal()+sugar.getAge50_55womanounthigh()+sugar.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge50_55womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge50_55womanounthighside(), sugar.getAge50_55womancountnormal()+sugar.getAge50_55womanounthigh()+sugar.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge50_55womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge50_55womanounthigh(), sugar.getAge50_55womancountnormal()+sugar.getAge50_55womanounthigh()+sugar.getAge50_55womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_low() + groupReportBloodSugar.getWoman55_low()))).append("\n").append(groupReportBloodSugar.getAll55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_in() + groupReportBloodSugar.getWoman55_in()))).append("\n").append(groupReportBloodSugar.getAll55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_high() + groupReportBloodSugar.getWoman55_high()))).append("\n").append(groupReportBloodSugar.getAll55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_low()))).append("\n").append(groupReportBloodSugar.getMan55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_in()))).append("\n").append(groupReportBloodSugar.getMan55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan55_high()))).append("\n").append(groupReportBloodSugar.getMan55_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman55_low()))).append("\n").append(groupReportBloodSugar.getWoman55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman55_in()))).append("\n").append(groupReportBloodSugar.getWoman55_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman55_high()))).append("\n").append(groupReportBloodSugar.getWoman55_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("55-60 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge55_60mancountnormal()+sugar.getAge55_60womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge55_60mancountnormal()+sugar.getAge55_60womancountnormal(), sugar.getAge55_60mancountnormal()+sugar.getAge55_60manounthigh()+sugar.getAge55_60manounthighside()+sugar.getAge55_60womancountnormal()+sugar.getAge55_60womanounthigh()+sugar.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge55_60manounthighside() + sugar.getAge55_60womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge55_60manounthighside()+sugar.getAge55_60womanounthighside(), sugar.getAge55_60mancountnormal()+sugar.getAge55_60manounthigh()+sugar.getAge55_60manounthighside()+sugar.getAge55_60womancountnormal()+sugar.getAge55_60womanounthigh()+sugar.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge55_60manounthigh()+sugar.getAge55_60womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge55_60manounthigh()+sugar.getAge55_60womanounthigh(), sugar.getAge55_60mancountnormal()+sugar.getAge55_60manounthigh()+sugar.getAge55_60manounthighside()+sugar.getAge55_60womancountnormal()+sugar.getAge55_60womanounthigh()+sugar.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge55_60mancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge55_60mancountnormal(), sugar.getAge55_60mancountnormal()+sugar.getAge55_60manounthigh()+sugar.getAge55_60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge55_60manounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge55_60manounthighside(), sugar.getAge55_60mancountnormal()+sugar.getAge55_60manounthigh()+sugar.getAge55_60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge55_60manounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge55_60manounthigh(), sugar.getAge55_60mancountnormal()+sugar.getAge55_60manounthigh()+sugar.getAge55_60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge55_60womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge55_60womancountnormal(), sugar.getAge55_60womancountnormal()+sugar.getAge55_60womanounthigh()+sugar.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge55_60womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge55_60womanounthighside(), sugar.getAge55_60womancountnormal()+sugar.getAge55_60womanounthigh()+sugar.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge55_60womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge55_60womanounthigh(), sugar.getAge55_60womancountnormal()+sugar.getAge55_60womanounthigh()+sugar.getAge55_60womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_low() + groupReportBloodSugar.getWoman60_low()))).append("\n").append(groupReportBloodSugar.getAll60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_in() + groupReportBloodSugar.getWoman60_in()))).append("\n").append(groupReportBloodSugar.getAll60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_high() + groupReportBloodSugar.getWoman60_high()))).append("\n").append(groupReportBloodSugar.getAll60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_low()))).append("\n").append(groupReportBloodSugar.getMan60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_in()))).append("\n").append(groupReportBloodSugar.getMan60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan60_high()))).append("\n").append(groupReportBloodSugar.getMan60_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman60_low()))).append("\n").append(groupReportBloodSugar.getWoman60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman60_in()))).append("\n").append(groupReportBloodSugar.getWoman60_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman60_high()))).append("\n").append(groupReportBloodSugar.getWoman60_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge60mancountnormal()+sugar.getAge60womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge60mancountnormal()+sugar.getAge60womancountnormal(), sugar.getAge60mancountnormal()+sugar.getAge60manounthigh()+sugar.getAge60manounthighside()+sugar.getAge60womancountnormal()+sugar.getAge60womanounthigh()+sugar.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge60manounthighside() + sugar.getAge60womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge60manounthighside()+sugar.getAge60womanounthighside(), sugar.getAge60mancountnormal()+sugar.getAge60manounthigh()+sugar.getAge60manounthighside()+sugar.getAge60womancountnormal()+sugar.getAge60womanounthigh()+sugar.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge60manounthigh()+sugar.getAge60womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge60manounthigh()+sugar.getAge60womanounthigh(), sugar.getAge60mancountnormal()+sugar.getAge60manounthigh()+sugar.getAge60manounthighside()+sugar.getAge60womancountnormal()+sugar.getAge60womanounthigh()+sugar.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge60mancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge60mancountnormal(), sugar.getAge60mancountnormal()+sugar.getAge60manounthigh()+sugar.getAge60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge60manounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge60manounthighside(), sugar.getAge60mancountnormal()+sugar.getAge60manounthigh()+sugar.getAge60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge60manounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge60manounthigh(), sugar.getAge60mancountnormal()+sugar.getAge60manounthigh()+sugar.getAge60manounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge60womancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getAge60womancountnormal(), sugar.getAge60womancountnormal()+sugar.getAge60womanounthigh()+sugar.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge60womanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getAge60womanounthighside(), sugar.getAge60womancountnormal()+sugar.getAge60womanounthigh()+sugar.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getAge60womanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getAge60womanounthigh(), sugar.getAge60womancountnormal()+sugar.getAge60womanounthigh()+sugar.getAge60womanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_low() + groupReportBloodSugar.getWoman70_low()))).append("\n").append(groupReportBloodSugar.getAll70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_in() + groupReportBloodSugar.getWoman70_in()))).append("\n").append(groupReportBloodSugar.getAll70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_high() + groupReportBloodSugar.getWoman70_high()))).append("\n").append(groupReportBloodSugar.getAll70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_low()))).append("\n").append(groupReportBloodSugar.getMan70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_in()))).append("\n").append(groupReportBloodSugar.getMan70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan70_high()))).append("\n").append(groupReportBloodSugar.getMan70_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman70_low()))).append("\n").append(groupReportBloodSugar.getWoman70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman70_in()))).append("\n").append(groupReportBloodSugar.getWoman70_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman70_high()))).append("\n").append(groupReportBloodSugar.getWoman70_high_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getMancountnormal()+sugar.getWomancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getMancountnormal()+sugar.getWomancountnormal(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()+sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getManounthighside() + sugar.getWomanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getManounthighside()+sugar.getWomanounthighside(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()+sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getManounthigh()+sugar.getWomanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getManounthigh()+sugar.getWomanounthigh(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()+sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getMancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getMancountnormal(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getManounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getManounthighside(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getManounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getManounthigh(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getWomancountnormal()))).append("\n").append(String.format("%.2f",persent(sugar.getWomancountnormal(), sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getWomanounthighside()))).append("\n").append(String.format("%.2f",persent(sugar.getWomanounthighside(), sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(sugar.getWomanounthigh()))).append("\n").append(String.format("%.2f",persent(sugar.getWomanounthigh(), sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getAll_low()))).append("\n").append(groupReportBloodSugar.getAll_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getAll_in()))).append("\n").append(groupReportBloodSugar.getAll_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getAll_high()))).append("\n").append(groupReportBloodSugar.getAll_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan_low()))).append("\n").append(groupReportBloodSugar.getMan_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan_in()))).append("\n").append(groupReportBloodSugar.getMan_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getMan_high()))).append("\n").append(groupReportBloodSugar.getMan_high_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman_low()))).append("\n").append(groupReportBloodSugar.getWoman_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman_in()))).append("\n").append(groupReportBloodSugar.getWoman_in_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportBloodSugar.getWoman_high()))).append("\n").append(groupReportBloodSugar.getWoman_high_max()).toString(), font2, 1, TableLineColor));*/
		addBlankCell(table_Page5, 10);
		pdfPTable.addCell(table_Page5);
		str2 = "注：正常血糖： 3.9≤空腹血糖＜5.6mmol/l；糖尿病前期：5.6≤空腹血糖＜7.0mmol/l；可疑糖尿病：空腹血糖≥7.0mmol/l。";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportLipidPdf(YhExportPdfBloodFat bloodfat)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportLipid groupReportLipid = groupReport.getGroupReportLipid();
		str1 = "血脂异常 ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "     人体血液中脂质代谢失调称为血脂异常，它与多种疾病有着密切的关系，是导致冠心病、中风、糖尿病、动脉粥样硬化的主要危险因素之一。本次评估对象的血脂检测结果按性别分布如下所示：  ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(bloodfat.getJFreeChartPath(bloodfat), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象血脂检测结果按性别分布(指标异常人数占正常人数百分比)";
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getMancholesterolcount()+bloodfat.getWomancholesterolcount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getMancholesterolcount()+bloodfat.getWomancholesterolcount(), bloodfat.getAllcount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getMancholesterolcount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getMancholesterolcount(), bloodfat.getMancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getWomancholesterolcount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getWomancholesterolcount(), bloodfat.getWomancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("高甘油三酯", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getManTGcount()+bloodfat.getWomanTGcount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getManTGcount()+bloodfat.getWomanTGcount(), bloodfat.getAllcount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getManTGcount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getManTGcount(), bloodfat.getMancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getWomanTGcount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getWomanTGcount(), bloodfat.getWomancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("低HDL胆固醇", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getManhdlccount()+bloodfat.getWomanhdlccount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getManhdlccount()+bloodfat.getWomanhdlccount(), bloodfat.getAllcount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getManhdlccount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getManhdlccount(), bloodfat.getMancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getWomanhdlccount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getWomanhdlccount(), bloodfat.getWomancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("高LDL胆固醇", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getManldlccount() + bloodfat.getWomanldlccount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getManldlccount() + bloodfat.getWomanldlccount(), bloodfat.getAllcount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getManldlccount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getManldlccount() , bloodfat.getMancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getWomanldlccount()))).append("\n").append(String.format("%.2f",persent( bloodfat.getWomanldlccount(), bloodfat.getWomancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("总血脂异常", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getMancount()+bloodfat.getWomancount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getManBFcount() + bloodfat.getWomanBFcount(), bloodfat.getAllcount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getMancount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getManBFcount(), bloodfat.getMancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(bloodfat.getWomancount()))).append("\n").append(String.format("%.2f",persent(bloodfat.getWomanBFcount() , bloodfat.getWomancount()))).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 7);
		pdfPTable.addCell(table_Page5);
		str2 = "注：高总胆固醇≥5.18mmol/L；高甘油三脂≥1.7mmol/L；低HDL胆固醇<1.04mmol/L；高LDL胆固醇≥3.37mmol/L；";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportMealHabitPdf(YhExportPdfFood food)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportMealHabit groupReportMealHabit = groupReport.getGroupReportMealHabit();
		str1 = "膳食习惯 ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      中国居民膳食指南建议：食物多样，粗细搭配，多吃蔬菜水果和薯类，常吃适量的鱼、禽、蛋和瘦肉等。不良的膳食习惯不仅包括每天摄入的总量过多，更重要的是膳食结构的不合理。本次评估对象的膳食习惯按性别分布如下所示： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(food.getJFreeChartPath(food), width_table_Page2);
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("高嘌呤饮食", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(food.getManfood_purine()+food.getWomanfood_purine()))).append("\n").append(String.format("%.2f",persent(food.getManfood_purine()+food.getWomanfood_purine(),food.getAllcount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(food.getManfood_purine()))).append("\n").append(String.format("%.2f",persent(food.getManfood_purine(), food.getWomancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(food.getWomanfood_purine()))).append("\n").append(String.format("%.2f",persent(food.getWomanfood_purine(), food.getWomancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("蔬菜水果摄入不足", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(food.getManFruit()+food.getWomanFruit()))).append("\n").append(String.format("%.2f",persent(food.getManFruit()+food.getWomanFruit(),food.getAllcount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(food.getManFruit()))).append("\n").append(String.format("%.2f",persent(food.getManFruit(), food.getMancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(food.getWomanFruit()))).append("\n").append(String.format("%.2f",persent(food.getWomanFruit(), food.getWomancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("牛奶摄入不足", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(food.getManmilk()+food.getWomanmilk()))).append("\n").append(String.format("%.2f",persent(food.getManmilk()+food.getWomanmilk(), food.getAllcount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(food.getManmilk()))).append("\n").append(String.format("%.2f",persent(food.getManmilk(), food.getMancount()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(food.getWomanmilk()))).append("\n").append(String.format("%.2f",persent(food.getWomanmilk(),food.getWomancount()))).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 7);
		pdfPTable.addCell(table_Page5);
		str2 = "注：蔬菜水果摄入不足：蔬菜水果总量＜500克/天。 ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}
	public PdfPTable groupReportDrinkingPdf(YhExportPdfDrink drrink)
			throws Exception
		{
			PdfPTable pdfPTable = new PdfPTable(1);
			pdfPTable.getDefaultCell().setBorder(0);
			//GroupReportDrinking groupReportDrinking = groupReport.getGroupReportDrinking();
			str1 = "饮酒情况   ";
			table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
			pdfPTable.addCell(table_Page1);
			str2 = "     无节制的饮酒，会使食欲下降，食物摄入量减少，以致发生多种营养缺乏、急慢性酒精中毒、酒精性脂肪肝，严重时还会造成酒精性肝硬化。过量饮酒还会增加患高血压、中风等疾病的危险。另外，饮酒还会增加患某些癌症的危险。若饮酒应尽可能饮用低度酒，并控制在适量范围内。 本次评估对象饮酒情况统计结果如下所示： ";
			table_Page2 = getContent(width_table_Page2, str2, font2);
			pdfPTable.addCell(table_Page2);
			table_img1 = getJfreeChar(drrink.getJFreeChartPath(drrink), width_table_Page2);
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
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_high_man()+drrink.getDrinking_high_woman()))).append("\n").append(String.format("%.2f",persent(drrink.getDrinking_high_man()+drrink.getDrinking_high_woman(), drrink.getAllpeoplecount()))).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_high_man()))).append("\n").append(String.format("%.2f",persent(drrink.getDrinking_high_man(), drrink.getDrinking_man()))).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_high_woman()))).append("\n").append(String.format("%.2f",persent(drrink.getDrinking_high_woman(), drrink.getDrinking_woman()))).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("适量饮酒", font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_in_man()+drrink.getDrinking_in_woman()))).append("\n").append(String.format("%.2f",persent(drrink.getDrinking_in_man()+drrink.getDrinking_in_woman(), drrink.getAllpeoplecount()))).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_in_man()))).append("\n").append(String.format("%.2f",persent(drrink.getDrinking_in_man(), drrink.getDrinking_man()))).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_in_woman()))).append("\n").append(String.format("%.2f",persent(drrink.getDrinking_in_woman(), drrink.getDrinking_woman()))).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("不饮酒", font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_low_man() + drrink.getDrinking_low_woman()))).append("\n").append(String.format("%.2f",persent(drrink.getDrinking_low_man()+drrink.getDrinking_low_woman(), drrink.getAllpeoplecount()))).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_low_man()))).append("\n").append(String.format("%.2f",persent(drrink.getDrinking_low_man(), drrink.getDrinking_man()))).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_low_woman()))).append("\n").append(String.format("%.2f",persent(drrink.getDrinking_low_woman(), drrink.getDrinking_woman()))).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计", font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getAllpeoplecount()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_man()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(drrink.getDrinking_woman()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
			addBlankCell(table_Page5, 7);
			pdfPTable.addCell(table_Page5);
			str2 = "注：适量饮酒：酒精量，男性＜25g/天，女性＜15g/天；过量饮酒：酒精量，男性≥25g/天，女性≥15g/天； ";
			table_Page2 = getContent(width_table_Page2, str2, font2);
			pdfPTable.addCell(table_Page2);
			pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
			return pdfPTable;
		}
	public PdfPTable groupReportSmokePdf(YhExportPdfSmoke smoke)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportSmoke groupReportSmoke = groupReport.getGroupReportSmoke();
		str1 = "吸烟情况  ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "     吸烟是目前危害人类健康最严重的不良行为之一，吸烟越多危害性越大，但几乎是一旦停止吸烟，戒烟的好处既是立竿见影的也是长期的。本次评估对象的吸烟情况如下所示： ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(smoke.getJFreeChartPath(smoke), width_table_Page2);
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
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(smoke.getSmoke_man()+smoke.getSmoke_woman()))).append("\n").append(String.format("%.2f",persent(smoke.getSmoke_man()+smoke.getSmoke_woman(), smoke.getAllpeople()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(smoke.getSmoke_man()))).append("\n").append(String.format("%.2f",persent(smoke.getSmoke_man(), smoke.getMan()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(smoke.getSmoke_woman()))).append("\n").append(String.format("%.2f",persent(smoke.getSmoke_woman(), smoke.getWoman()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("不吸烟", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(smoke.getSmoke_no_man()+smoke.getSmoke_no_woman()))).append("\n").append(String.format("%.2f",persent(smoke.getSmoke_no_man()+smoke.getSmoke_no_woman(), smoke.getAllpeople()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(smoke.getSmoke_no_man()))).append("\n").append(String.format("%.2f",persent(smoke.getSmoke_no_man(), smoke.getMan()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(smoke.getSmoke_no_woman()))).append("\n").append(String.format("%.2f",persent(smoke.getSmoke_no_woman(), smoke.getWoman()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(smoke.getAllpeople()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(smoke.getMan()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(smoke.getWoman()))).append("\n").append(100D).toString(), font2, 1, TableLineColor));
		addBlankCell(table_Page5, 7);
		pdfPTable.addCell(table_Page5);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	

	public PdfPTable groupReportPhysicalActivityPdf(YhExportPdfActive active)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportPhysicalActivity groupReportPhysicalActivity = groupReport.getGroupReportPhysicalActivity();
		str1 = "体力活动水平 ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      健康的生活方式应包括有规律的、强度适当的体力活动，这是最有效的预防慢性疾病的方法，因为适当的体力活动可以降低患慢性病的危险性，对身体的多个器官有益。指南建议：对于一般成年人，每周应至少5天进行30分钟以上中等强度体力活动，每周累计活动时间至少达到150分钟。本次评估对象的体力活动水平统计结果如下所示。  ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(active.getJFreeChartPath(active), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "评估对象体力活动水平按年龄及性别分布（%）";
		table_Page2 = addRemard(width_table_Page2, font3, str2);
		pdfPTable.addCell(table_Page2);
		table_Page5 = new PdfPTable(new float[] {
			0.163F, 0.1395F, 0.1395F, 0.1395F, 0.1395F, 0.1395F, 0.1395F
		});
		pdfCell2 = PDFUtil.getCorderPdfPCellA("年龄组(岁)", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		pdfCell2.setVerticalAlignment(5);
		table_Page5.addCell(pdfCell2);
		table_Page5.addCell(createInner("总体", "不足", "充分", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("男性", "不足", "充分", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(createInner("女性", "不足", "充分", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("＜30 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30mannosufficient()+active.getAge30womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30mannosufficient()+active.getAge30womannosufficient(), active.getAge30mannosufficient()+active.getAge30mansufficient()+active.getAge30womannosufficient()+active.getAge30womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30mansufficient()+active.getAge30womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30mansufficient()+active.getAge30womansufficient(), active.getAge30mannosufficient()+active.getAge30mansufficient()+active.getAge30womannosufficient()+active.getAge30womansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30mannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30mannosufficient(), active.getAge30mannosufficient()+active.getAge30mansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30mansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30mansufficient(), active.getAge30mannosufficient()+active.getAge30mansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30womannosufficient(), active.getAge30womannosufficient()+active.getAge30womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30womansufficient(), active.getAge30womannosufficient()+active.getAge30womansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("30-40 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30_40mannosufficient()+active.getAge30_40womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30_40mannosufficient()+active.getAge30_40womannosufficient(), active.getAge30_40mannosufficient()+active.getAge30_40mansufficient()+active.getAge30_40womannosufficient()+active.getAge30_40womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30_40mansufficient()+active.getAge30_40womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30_40mansufficient()+active.getAge30_40womansufficient(), active.getAge30_40mannosufficient()+active.getAge30_40mansufficient()+active.getAge30_40womannosufficient()+active.getAge30_40womansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30_40mannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30_40mannosufficient(), active.getAge30_40mannosufficient()+active.getAge30_40mansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30_40mansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30_40mansufficient(), active.getAge30_40mannosufficient()+active.getAge30_40mansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30_40womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30_40womannosufficient(), active.getAge30_40womannosufficient()+active.getAge30_40womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30_40womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge30_40womansufficient(), active.getAge30_40womannosufficient()+active.getAge30_40womansufficient()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan40_high() + groupReportPhysicalActivity.getWoman40_high()))).append("\n").append(groupReportPhysicalActivity.getAll40_high_max()).toString(), font2, 1, TableLineColor));
		
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan40_low() + groupReportPhysicalActivity.getWoman40_low()))).append("\n").append(groupReportPhysicalActivity.getAll40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan40_high()))).append("\n").append(groupReportPhysicalActivity.getMan40_high_max()).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan40_low()))).append("\n").append(groupReportPhysicalActivity.getMan40_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman40_high()))).append("\n").append(groupReportPhysicalActivity.getWoman40_high_max()).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman40_low()))).append("\n").append(groupReportPhysicalActivity.getWoman40_low_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("40-45 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge40_45mannosufficient()+active.getAge40_45womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge40_45mannosufficient()+active.getAge40_45womannosufficient(), active.getAge40_45mannosufficient()+active.getAge40_45mansufficient()+active.getAge40_45womannosufficient()+active.getAge40_45womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge40_45mansufficient()+active.getAge40_45womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge40_45mansufficient()+active.getAge40_45womansufficient(), active.getAge40_45mannosufficient()+active.getAge40_45mansufficient()+active.getAge40_45womannosufficient()+active.getAge40_45womansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge40_45mannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge40_45mannosufficient(), active.getAge40_45mannosufficient()+active.getAge40_45mansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge40_45mansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge40_45mansufficient(), active.getAge40_45mannosufficient()+active.getAge40_45mansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge40_45womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge40_45womannosufficient(), active.getAge40_45womannosufficient()+active.getAge40_45womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge40_45womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge40_45womansufficient(), active.getAge40_45womannosufficient()+active.getAge40_45womansufficient()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan45_high() + groupReportPhysicalActivity.getWoman45_high()))).append("\n").append(groupReportPhysicalActivity.getAll45_high_max()).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan45_low() + groupReportPhysicalActivity.getWoman45_low()))).append("\n").append(groupReportPhysicalActivity.getAll45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan45_high()))).append("\n").append(groupReportPhysicalActivity.getMan45_high_max()).toString(), font2, 1, TableLineColor));
		
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan45_low()))).append("\n").append(groupReportPhysicalActivity.getMan45_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman45_high()))).append("\n").append(groupReportPhysicalActivity.getWoman45_high_max()).toString(), font2, 1, TableLineColor));
	
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman45_low()))).append("\n").append(groupReportPhysicalActivity.getWoman45_low_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("45-50 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge45_50mannosufficient()+active.getAge45_50womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge45_50mannosufficient()+active.getAge45_50womannosufficient(), active.getAge45_50mannosufficient()+active.getAge45_50mansufficient()+active.getAge45_50womannosufficient()+active.getAge45_50womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge45_50mansufficient()+active.getAge45_50womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge45_50mansufficient()+active.getAge45_50womansufficient(), active.getAge45_50mannosufficient()+active.getAge45_50mansufficient()+active.getAge45_50womannosufficient()+active.getAge45_50womansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge45_50mannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge45_50mannosufficient(), active.getAge45_50mannosufficient()+active.getAge45_50mansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge45_50mansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge45_50mansufficient(), active.getAge45_50mannosufficient()+active.getAge45_50mansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge45_50womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge45_50womannosufficient(), active.getAge45_50womannosufficient()+active.getAge45_50womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge45_50womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge45_50womansufficient(), active.getAge45_50womannosufficient()+active.getAge45_50womansufficient()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan50_high() + groupReportPhysicalActivity.getWoman50_high()))).append("\n").append(groupReportPhysicalActivity.getAll50_high_max()).toString(), font2, 1, TableLineColor));
	
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan50_low() + groupReportPhysicalActivity.getWoman50_low()))).append("\n").append(groupReportPhysicalActivity.getAll50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan50_high()))).append("\n").append(groupReportPhysicalActivity.getMan50_high_max()).toString(), font2, 1, TableLineColor));
		
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan50_low()))).append("\n").append(groupReportPhysicalActivity.getMan50_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman50_high()))).append("\n").append(groupReportPhysicalActivity.getWoman50_high_max()).toString(), font2, 1, TableLineColor));
		
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman50_low()))).append("\n").append(groupReportPhysicalActivity.getWoman50_low_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("50-55 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge50_55mannosufficient()+active.getAge50_55womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge50_55mannosufficient()+active.getAge50_55womannosufficient(), active.getAge50_55mannosufficient()+active.getAge50_55mansufficient()+active.getAge50_55womannosufficient()+active.getAge50_55womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge50_55mansufficient()+active.getAge50_55womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge50_55mansufficient()+active.getAge50_55womansufficient(), active.getAge50_55mannosufficient()+active.getAge50_55mansufficient()+active.getAge50_55womannosufficient()+active.getAge50_55womansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge50_55mannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge50_55mannosufficient(), active.getAge50_55mannosufficient()+active.getAge50_55mansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge50_55mansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge50_55mansufficient(), active.getAge50_55mannosufficient()+active.getAge50_55mansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge50_55womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge50_55womannosufficient(), active.getAge50_55womannosufficient()+active.getAge50_55womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge50_55womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge50_55womansufficient(), active.getAge50_55womannosufficient()+active.getAge50_55womansufficient()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan55_high() + groupReportPhysicalActivity.getWoman55_high()))).append("\n").append(groupReportPhysicalActivity.getAll55_high_max()).toString(), font2, 1, TableLineColor));
		
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan55_low() + groupReportPhysicalActivity.getWoman55_low()))).append("\n").append(groupReportPhysicalActivity.getAll55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan55_high()))).append("\n").append(groupReportPhysicalActivity.getMan55_high_max()).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan55_low()))).append("\n").append(groupReportPhysicalActivity.getMan55_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman55_high()))).append("\n").append(groupReportPhysicalActivity.getWoman55_high_max()).toString(), font2, 1, TableLineColor));
		
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman55_low()))).append("\n").append(groupReportPhysicalActivity.getWoman55_low_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("55-60", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge55_60mannosufficient()+active.getAge55_60womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge55_60mannosufficient()+active.getAge55_60womannosufficient(), active.getAge55_60mannosufficient()+active.getAge55_60mansufficient()+active.getAge55_60womannosufficient()+active.getAge55_60womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge55_60mansufficient()+active.getAge55_60womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge55_60mansufficient()+active.getAge55_60womansufficient(), active.getAge55_60mannosufficient()+active.getAge55_60mansufficient()+active.getAge55_60womannosufficient()+active.getAge55_60womansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge55_60mannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge55_60mannosufficient(), active.getAge55_60mannosufficient()+active.getAge55_60mansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge55_60mansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge55_60mansufficient(), active.getAge55_60mannosufficient()+active.getAge55_60mansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge55_60womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge55_60womannosufficient(), active.getAge55_60womannosufficient()+active.getAge55_60womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge55_60womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge55_60womansufficient(), active.getAge55_60womannosufficient()+active.getAge55_60womansufficient()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan60_high() + groupReportPhysicalActivity.getWoman60_high()))).append("\n").append(groupReportPhysicalActivity.getAll60_high_max()).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan60_low() + groupReportPhysicalActivity.getWoman60_low()))).append("\n").append(groupReportPhysicalActivity.getAll60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan60_high()))).append("\n").append(groupReportPhysicalActivity.getMan60_high_max()).toString(), font2, 1, TableLineColor));
	
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan60_low()))).append("\n").append(groupReportPhysicalActivity.getMan60_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman60_high()))).append("\n").append(groupReportPhysicalActivity.getWoman60_high_max()).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman60_low()))).append("\n").append(groupReportPhysicalActivity.getWoman60_low_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("≥60 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge60mannosufficient()+active.getAge60womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge60mannosufficient()+active.getAge60womannosufficient(), active.getAge60mannosufficient()+active.getAge60mansufficient()+active.getAge60womannosufficient()+active.getAge60womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge60mansufficient()+active.getAge60womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge60mansufficient()+active.getAge60womansufficient(), active.getAge60mannosufficient()+active.getAge60mansufficient()+active.getAge60womannosufficient()+active.getAge60womansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge60mannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge60mannosufficient(), active.getAge60mannosufficient()+active.getAge60mansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge60mansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge60mansufficient(), active.getAge60mannosufficient()+active.getAge60mansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge60womannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge60womannosufficient(), active.getAge60womannosufficient()+active.getAge60womansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge60womansufficient()))).append("\n").append(String.format("%.2f",persent(active.getAge60womansufficient(), active.getAge60womannosufficient()+active.getAge60womansufficient()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan70_high() + groupReportPhysicalActivity.getWoman70_high()))).append("\n").append(groupReportPhysicalActivity.getAll70_high_max()).toString(), font2, 1, TableLineColor));
		
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan70_low() + groupReportPhysicalActivity.getWoman70_low()))).append("\n").append(groupReportPhysicalActivity.getAll70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan70_high()))).append("\n").append(groupReportPhysicalActivity.getMan70_high_max()).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan70_low()))).append("\n").append(groupReportPhysicalActivity.getMan70_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman70_high()))).append("\n").append(groupReportPhysicalActivity.getWoman70_high_max()).toString(), font2, 1, TableLineColor));
	
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman70_low()))).append("\n").append(groupReportPhysicalActivity.getWoman70_low_max()).toString(), font2, 1, TableLineColor));*/
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("合计 ", font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getMannosufficient()+active.getWomannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getMannosufficient()+active.getWomannosufficient(), active.getMannosufficient()+active.getMansufficient()+active.getWomannosufficient()+active.getWomansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getMansufficient()+active.getWomansufficient()))).append("\n").append(String.format("%.2f",persent(active.getMansufficient()+active.getWomansufficient(), active.getMannosufficient()+active.getMansufficient()+active.getWomannosufficient()+active.getWomansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getMannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getMannosufficient(), active.getMannosufficient()+active.getMansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getMansufficient()))).append("\n").append(String.format("%.2f",persent(active.getMansufficient(), active.getMannosufficient()+active.getMansufficient()))).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getWomannosufficient()))).append("\n").append(String.format("%.2f",persent(active.getWomannosufficient(), active.getWomannosufficient()+active.getWomansufficient()))).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getWomansufficient()))).append("\n").append(String.format("%.2f",persent(active.getWomansufficient(), active.getWomannosufficient()+active.getWomansufficient()))).toString(), font2, 1, TableLineColor));
		/*table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getAll_high()))).append("\n").append(groupReportPhysicalActivity.getAll_high_max()).toString(), font2, 1, TableLineColor));

		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getAll_low()))).append("\n").append(groupReportPhysicalActivity.getAll_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan_high()))).append("\n").append(groupReportPhysicalActivity.getMan_high_max()).toString(), font2, 1, TableLineColor));
	
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getMan_low()))).append("\n").append(groupReportPhysicalActivity.getMan_low_max()).toString(), font2, 1, TableLineColor));
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman_high()))).append("\n").append(groupReportPhysicalActivity.getWoman_high_max()).toString(), font2, 1, TableLineColor));
	
		table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(groupReportPhysicalActivity.getWoman_low()))).append("\n").append(groupReportPhysicalActivity.getWoman_low_max()).toString(), font2, 1, TableLineColor));*/
		addBlankCell(table_Page5, 10);
		pdfPTable.addCell(table_Page5);
		str2 = "注：体力活动水平分级标准参见IPAQ判断标准。 ";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportRiskComplicationPdf(YhExportPdfFactors pdffactors)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportRiskComplication groupReportRiskComplication = groupReport.getGroupReportRiskComplication();
		str1 = "危险因素聚集情况  ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		str2 = "      影响健康的危险因素并不是一成不变的，如果不加以控制，随着时间的推移，有些危险因素会逐步加重，一些新的危险因素也会出现。健康危险因素越多，相应的健康风险越高，同时有4、5项危险因素及以上的个体，由此产生的健康开支至少是没有危险因素个体的5～8倍以上，这其中既包括医疗保健费用开支，还有劳动生产力效益的损失。下图显示了本次评估中，具有不同数量危险因素的人群构成情况：";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_img1 = getJfreeChar(pdffactors.getJFreeChartPath(pdffactors), width_table_Page2);
		pdfPTable.addCell(table_img1);
		str2 = "      除了了解健康危险因素的聚集情况外，还应该清楚了解每种健康危险因素异常比例，并重点从异常比例较高的健康危险因素入手，进行健康风险干预，这样可以在短时间内显著地改善本单位的整体健康风险状况。在所有健康危险因素中，每种健康危险因素异常比例如下";
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_Page1 = new PdfPTable(1);
		//table_Page1.addCell(PDFUtil.getCorderPdfPCellA((new StringBuilder("     ")).append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(10)).getName()).append(":").append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(10)).getMax()).append("% ").toString(), font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white));
		//table_Page1.addCell(PDFUtil.getCorderPdfPCellA((new StringBuilder("     ")).append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(9)).getName()).append(":").append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(9)).getMax()).append("% ").toString(), font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white));
		//table_Page1.addCell(PDFUtil.getCorderPdfPCellA((new StringBuilder("     ")).append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(8)).getName()).append(":").append(((RiskSortUtil)groupReport.getGroupReportAdvice().getRiskList().get(8)).getMax()).append("% ").toString(), font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white));
		pdfPTable.addCell(table_Page1);
		table_img1 = getJfreeChar(pdffactors.getJFreeChartPath2(pdffactors), width_table_Page2);
		pdfPTable.addCell(table_img1);
		pdfPTable.addCell(addFooter(footerStr, footerFont, 1));
		return pdfPTable;
	}

	public PdfPTable groupReportAdvicePdf(YhExportIntervention intervention)
		throws Exception
	{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		str1 = "管理干预建议 ";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		Font font3_temp = font3;
		font3_temp.setSize(12F);
		table_Page1 = new PdfPTable(1);
		PdfPCell pdfPCell = PDFUtil.getCorderPdfPCellA("   基本原则 ", font3_temp, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
		table_Page1.addCell(pdfPCell);
		pdfPTable.addCell(table_Page1);
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
		str2 = (new StringBuilder("      本次参与评估共有")).append(intervention.getAssesspeople()).append("人，其中").append(intervention.getHavedis()).append("空腹血糖≥7.0mmol/L的，即可疑糖尿病").append(intervention.getMaydiabetes()).append("人，已达到高血压诊断标准的").append(intervention.getMayhypertension()).append("人，他们需要咨询医生进一步确诊；另外").append(df.format(intervention.getPersent()*100)).append("%（").append(intervention.getHaveriskpeople()).append("人） 有一个或多个危险因素，有很高的慢性病发病风险。幸运的是，不健康的危险因素是可管理的，有效的干预措施带来的不仅是危险因素的减少，还有缺勤率的降低与工作效率的提高。根据本次的评估分析结果，建议进行管理干预的原则包括以下： ").toString();
		table_Page2 = getContent(width_table_Page2, str2, font2);
		pdfPTable.addCell(table_Page2);
		table_Page1 = new PdfPTable(1);
		pdfPCell = PDFUtil.getCorderPdfPCellA("1.及早发现疾病，评估健康风险 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("2.创造提倡健康生活方式的环境", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("3.低风险的个体要保持低风险", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("4.高风险的个体是干预重点", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
		table_Page1.addCell(pdfPCell);
		pdfPCell = PDFUtil.getCorderPdfPCellA("5.疾病患者要参与疾病管理，减少并发症", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
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
		if (intervention.getWaistcount()> 0)
		{
			table_Page1 = new PdfPTable(1);
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
			pdfPTable.addCell(table_Page1);
		}
		if (intervention.getMayhypertension() + intervention.getBfcount() +intervention.getMaydiabetes()> 0)
		{
			table_Page1 = new PdfPTable(1);
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
			pdfPTable.addCell(table_Page1);
		}
		if (intervention.getSmokepeople() > 0)
		{
			table_Page1 = new PdfPTable(1);
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
			pdfPTable.addCell(table_Page1);
		}
		if (intervention.getSmoke_sencod() > 0)
		{
			table_Page1 = new PdfPTable(1);
			pdfPCell = PDFUtil.getCorderPdfPCellA("※ \t\t避免被动吸烟 ", font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
			table_Page1.addCell(pdfPCell);
			pdfPCell = PDFUtil.getCorderPdfPCellA((new StringBuilder("     贵单位目前有")).append(intervention.getSmoke_sencod()).append("人存在被动吸烟，被动吸烟对身体的危害也是很大的，单位可以告知员工尽可能减少或避免被动吸烟的方法： ").toString(), font3, 1, 0, true, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, java.awt.Color.white);
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
			pdfPTable.addCell(table_Page1);
		}
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
	
	public String getJFreeChartPath(YhExportPdfBmi pdfbmi)
	{
		JFreeChart jFreeChart = getJFreeChart(pdfbmi);
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
	public JFreeChart getJFreeChart(YhExportPdfBmi pdfbmi)
	{
		double value_thin[] = {
				division((pdfbmi.getMancountthin()+pdfbmi.getWomancountthin()) , (pdfbmi.getMancountall()+pdfbmi.getWomancountall())),division(pdfbmi.getMancountthin() , pdfbmi.getMancountall()), division(pdfbmi.getWomancountthin() , pdfbmi.getWomancountall())
		};
		double value_normal[] = {
				division((pdfbmi.getMancountnormal()+pdfbmi.getWomancountnormal()) , (pdfbmi.getMancountall()+pdfbmi.getWomancountall())), division(pdfbmi.getMancountnormal(), pdfbmi.getMancountall()), division(pdfbmi.getWomancountnormal() ,  pdfbmi.getWomancountall())
		};
		double value_overweight[] = {
				division((pdfbmi.getMancountoverweight()+pdfbmi.getWomancountoverweight()) , (pdfbmi.getMancountall()+pdfbmi.getWomancountall())),division(pdfbmi.getMancountoverweight() ,  pdfbmi.getMancountall()), division(pdfbmi.getWomancountoverweight(), pdfbmi.getWomancountall())
		};
		double value_obesity[] = {
				division((pdfbmi.getMancountobesity()+pdfbmi.getWomancountoverweight()), (pdfbmi.getMancountall()+pdfbmi.getWomancountall())), division(pdfbmi.getMancountobesity() , pdfbmi.getMancountall()), division(pdfbmi.getWomancountobesity() , pdfbmi.getWomancountall())
		};
		String value1[] = {
			"消瘦", "正常", "超重", "肥胖"
		};
		String value2[] = {
			"总体", "男", "女"
		};
		double value[][] = {
			value_thin, value_normal, value_overweight, value_obesity
		};
		return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
			Color.YELLOW, Color.GREEN, new Color(253, 95, 77), Color.RED
		},"评估对象消瘦、超重及肥胖按年龄及性别分布（%)",0.29999999999999999D);
	}
	public PdfPTable getPdfPTableseniors(YhSeniorFactorsAnalyze factor,YhExportPdfSenior senior)
			throws Exception
		{
		PdfPTable pdfPTable = new PdfPTable(1);
		pdfPTable.getDefaultCell().setBorder(0);
		//GroupReportBodyMassIndex groupReportBodyMassIndex = groupReport.getGroupReportBodyMassIndex();
		str1 = "多因素分析";
		table_Page1 = getTitleContent(width_table_Page1, image, str1, font1);
		pdfPTable.addCell(table_Page1);
		table_img1 = getJfreeChar(getJFreeChartPathsenior(factor,senior), width_table_Page2);
		pdfPTable.addCell(table_img1);
		table_Page5 = new PdfPTable(new float[] {
				0.163F, 0.1395F, 0.1395F, 0.1395F, 0.1395F, 0.1395F, 0.1395F
			});
			pdfCell2 = PDFUtil.getCorderPdfPCellA("分析因素", font3, 1, 1, true, 0, 0, 0.1F, 0.0F, 0.1F, 0.1F, 0.0F, TableLineColor, TableLineColor, TableLineColor, TableLineColor, color1);
			pdfCell2.setVerticalAlignment(5);
			table_Page5.addCell(pdfCell2);
			table_Page5.addCell(createInner("总体", "人数", "占比", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
			table_Page5.addCell(createInner("男性", "人数", "占比", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
			table_Page5.addCell(createInner("女性", "人数", "占比", font3, color1, TableLineColor, 0.0F, 0.0F, 0.1F, 0.1F));
			String[] selected=factor.getSelect().split(",");
			String[] value1 = new String[selected.length];
			for (int i = 0; i < selected.length; i++) {
				if(selected[i].equals("0")) {
					value1[i]="收缩压";
	
				}
				if(selected[i].equals("1")) {
					value1[i]="舒张压";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("舒张压 ", font2, 1, TableLineColor));
					
				}
				if(selected[i].equals("2")) {
					value1[i]="空腹血糖";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("空腹血糖 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("3")) {
					value1[i]="总胆固醇";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("总胆固醇 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("4")) {
					value1[i]="甘油三酯";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("甘油三酯 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("5")) {
					value1[i]="HDL胆固醇";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("HDL胆固醇 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("6")) {
					value1[i]="LDL胆固醇";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("LDL胆固醇 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("7")) {
					value1[i]="体重指数";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("体重指数 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("8")) {
					value1[i]="腰围";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("腰围 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("9")) {
					value1[i]="吸烟比率";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("吸烟比率 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("10")) {
					value1[i]="饮酒比率";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("饮酒比率 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("11")) {
					value1[i]="体力活动比率";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("体力活动比率 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("16")) {
					value1[i]="个人糖尿病史";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("个人糖尿病史 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("17")) {
					value1[i]="个人高血压史";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("个人高血压史 ", font2, 1, TableLineColor));
				}
				if(selected[i].equals("18")) {
					value1[i]="个人肺癌史";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("个人肺癌史 ", font2, 1, TableLineColor));
					
				}
				if(selected[i].equals("19")) {
					value1[i]="个人血脂异常";
					table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("个人血脂异常 ", font2, 1, TableLineColor));
					
				}
				if(selected[i].equals("20")) {
					value1[i]="个人冠心病";
				}
			}
			table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder("＜30 ", font2, 1, TableLineColor));
			//table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30mannosufficient()+active.getAge30womannosufficient()))).append("\n").append(persent(active.getAge30mannosufficient()+active.getAge30womannosufficient(), active.getAge30mannosufficient()+active.getAge30mansufficient()+active.getAge30womannosufficient()+active.getAge30womansufficient())).toString(), font2, 1, TableLineColor));

			//table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30mansufficient()+active.getAge30womansufficient()))).append("\n").append(persent(active.getAge30mansufficient()+active.getAge30womansufficient(), active.getAge30mannosufficient()+active.getAge30mansufficient()+active.getAge30womannosufficient()+active.getAge30womansufficient())).toString(), font2, 1, TableLineColor));
			//table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30mannosufficient()))).append("\n").append(persent(active.getAge30mannosufficient(), active.getAge30mannosufficient()+active.getAge30mansufficient())).toString(), font2, 1, TableLineColor));

			//table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30mansufficient()))).append("\n").append(persent(active.getAge30mansufficient(), active.getAge30mannosufficient()+active.getAge30mansufficient())).toString(), font2, 1, TableLineColor));
			//table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30womannosufficient()))).append("\n").append(persent(active.getAge30womannosufficient(), active.getAge30womannosufficient()+active.getAge30womansufficient())).toString(), font2, 1, TableLineColor));

			//table_Page5.addCell(PDFUtil.getStringPhraseCellBHasBorder((new StringBuilder(String.valueOf(active.getAge30womansufficient()))).append("\n").append(persent(active.getAge30womansufficient(), active.getAge30womannosufficient()+active.getAge30womansufficient())).toString(), font2, 1, TableLineColor));
			

			addBlankCell(table_Page5, 10);
		return pdfPTable;
		}
	
	public String getJFreeChartPathsenior(YhSeniorFactorsAnalyze factor,YhExportPdfSenior senior)
	{
		JFreeChart jFreeChart = getJFreeChartsenior(factor,senior);
//		String path = "C:/Users/SY/Desktop/tob3.0/all/passing"+(new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);
			File fileParent = fos_jpg.getParentFile();
			if(!fileParent.exists()){
				fileParent.mkdirs();
			}
			fos_jpg.createNewFile();
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChart, 680, 300);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}
	public JFreeChart getJFreeChartsenior(YhSeniorFactorsAnalyze factor,YhExportPdfSenior senior)
	{	
		String[] selected=factor.getSelect().split(",");
		double arrsbp[] = null;
		double arrdbp[]= null;
		double arrFBG[]= null;
		double arrcholesterol[]= null;
		
		double arrTG[]= null;
		double arrhdlc[]= null;
		double arrldlc[]= null;
		double arrBMI[]= null;
		
		double arrwaist[]= null;
		double arrsmoke[]= null;
		double arrdrink[]= null;
		double arractive[]= null;
		
		double arrdiab_his[]= null;
		double arrhbp_his[]= null;
		double arrlc_his[]= null;
		double arrBF[]= null;
		double arrchd_his[]= null;
		String value2[];
		double value[][];
		if(factor.getSex()==0) {
			value=new double[selected.length][selected.length];
		}else {
			value =new double[selected.length][selected.length];
		}
		/*
		 * String value1[] = { "消瘦", "正常", "超重", "肥胖" };
		 */
		/*double value[][] = {
				value_thin, value_normal, value_overweight, value_obesity
			};*/
		String[] value1 = new String[selected.length];
		for (int i = 0; i < selected.length; i++) {
			if(selected[i].equals("0")) {
				value1[i]="收缩压";
				if(factor.getSex()==0) {
					arrsbp = new double []{persentdouble(senior.getMansbp()+senior.getWomansbp(), senior.getAllpeople()),persentdouble(senior.getMansbp(), senior.getMan()),persentdouble(senior.getWomansbp(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrsbp = new double [] { persentdouble(senior.getMansbp(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrsbp = new double [] { persentdouble(senior.getWomansbp(), senior.getWoman())};
				}
				value[i]=arrsbp;
			}
			if(selected[i].equals("1")) {
				value1[i]="舒张压";
					if(factor.getSex()==0) {
						arrdbp = new double []{persentdouble(senior.getMandbp()+senior.getWomandbp(), senior.getAllpeople()),persentdouble(senior.getMandbp(), senior.getMan()),persentdouble(senior.getWomandbp(), senior.getWoman())};
					}else if(factor.getSex()==1){
						arrdbp = new double [] { persentdouble(senior.getMandbp(), senior.getMan())};
					}else if(factor.getSex()==2) {
						arrdbp = new double [] { persentdouble(senior.getWomandbp(), senior.getWoman())};
					}
				value[i]=arrdbp;
			}
			if(selected[i].equals("2")) {
				value1[i]="空腹血糖";
				if(factor.getSex()==0) {
					arrFBG = new double []{persentdouble(senior.getManFBG()+senior.getWomanFBG(), senior.getAllpeople()),persentdouble(senior.getManFBG(), senior.getMan()),persentdouble(senior.getWomanFBG(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrFBG = new double [] { persentdouble(senior.getManFBG(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrFBG = new double [] { persentdouble(senior.getWomanFBG(), senior.getWoman())};
				}
				value[i]=arrFBG;
			}
			if(selected[i].equals("3")) {
				value1[i]="总胆固醇";
				if(factor.getSex()==0) {
					arrcholesterol = new double []{persentdouble(senior.getMancholesterol()+senior.getWomancholesterol(), senior.getAllpeople()),persentdouble(senior.getMancholesterol(), senior.getMan()),persentdouble(senior.getWomancholesterol(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrcholesterol = new double [] { persentdouble(senior.getMancholesterol(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrcholesterol = new double [] { persentdouble(senior.getWomancholesterol(), senior.getWoman())};
				}
				value[i]=arrcholesterol;
			}
			if(selected[i].equals("4")) {
				value1[i]="甘油三酯";
				if(factor.getSex()==0) {
					arrTG = new double []{persentdouble(senior.getManTG()+senior.getWomanTG(), senior.getAllpeople()),persentdouble(senior.getManTG(), senior.getMan()),persentdouble(senior.getWomanTG(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrTG = new double [] { persentdouble(senior.getManTG(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrTG = new double [] { persentdouble(senior.getWomanTG(), senior.getWoman())};
				}
				value[i]=arrTG;
			}
			if(selected[i].equals("5")) {
				value1[i]="HDL胆固醇";
				if(factor.getSex()==0) {
					arrhdlc = new double []{persentdouble(senior.getManhdlc()+senior.getWomanhdlc(), senior.getAllpeople()),persentdouble(senior.getManhdlc(), senior.getMan()),persentdouble(senior.getWomanhdlc(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrhdlc = new double [] { persentdouble(senior.getManhdlc(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrhdlc = new double [] { persentdouble(senior.getWomanhdlc(), senior.getWoman())};
				}
				value[i]=arrhdlc;
			}
			if(selected[i].equals("6")) {
				value1[i]="LDL胆固醇";
				if(factor.getSex()==0) {
					arrldlc = new double []{persentdouble(senior.getManldlc()+senior.getWomanldlc(), senior.getAllpeople()),persentdouble(senior.getManldlc(), senior.getMan()),persentdouble(senior.getWomanldlc(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrldlc = new double [] { persentdouble(senior.getManldlc(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrldlc = new double [] { persentdouble(senior.getWomanldlc(), senior.getWoman())};
				}
				value[i]=arrldlc;
			}
			if(selected[i].equals("7")) {
				value1[i]="体重指数";
				if(factor.getSex()==0) {
					arrBMI = new double []{persentdouble(senior.getManbmi()+senior.getWomanbmi(), senior.getAllpeople()),persentdouble(senior.getManbmi(), senior.getMan()),persentdouble(senior.getWomanbmi(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrBMI = new double [] { persentdouble(senior.getManbmi(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrBMI = new double [] { persentdouble(senior.getWomanbmi(), senior.getWoman())};
				}
				value[i]=arrBMI;
			}
			if(selected[i].equals("8")) {
				value1[i]="腰围";
				if(factor.getSex()==0) {
					arrwaist = new double []{persentdouble(senior.getManwaist()+senior.getWomanwaist(), senior.getAllpeople()),persentdouble(senior.getManwaist(), senior.getMan()),persentdouble(senior.getWomanwaist(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrwaist = new double [] { persentdouble(senior.getManwaist(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrwaist = new double [] { persentdouble(senior.getWomanwaist(), senior.getWoman())};
				}
				value[i]=arrwaist;
			}
			if(selected[i].equals("9")) {
				value1[i]="吸烟比率";
				if(factor.getSex()==0) {
					arrsmoke = new double []{persentdouble(senior.getMansmoke()+senior.getWomansmoke(), senior.getAllpeople()),persentdouble(senior.getMansmoke(), senior.getMan()),persentdouble(senior.getWomansmoke(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrsmoke = new double [] { persentdouble(senior.getMansmoke(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrsmoke = new double [] { persentdouble(senior.getWomansmoke(), senior.getWoman())};
				}
				value[i]=arrsmoke;
			}
			if(selected[i].equals("10")) {
				value1[i]="饮酒比率";
				if(factor.getSex()==0) {
					arrdrink = new double []{persentdouble(senior.getMandrink()+senior.getWomandrink(), senior.getAllpeople()),persentdouble(senior.getMandrink(), senior.getMan()),persentdouble(senior.getWomandrink(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrdrink = new double [] { persentdouble(senior.getMandrink(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrdrink = new double [] { persentdouble(senior.getWomandrink(), senior.getWoman())};
				}
				value[i]=arrdrink;
			}
			if(selected[i].equals("11")) {
				value1[i]="体力活动比率";
				if(factor.getSex()==0) {
					arractive = new double []{persentdouble(senior.getManactive()+senior.getWomanactive(), senior.getAllpeople()),persentdouble(senior.getManactive(), senior.getMan()),persentdouble(senior.getWomanactive(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arractive = new double [] { persentdouble(senior.getManactive(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arractive = new double [] { persentdouble(senior.getWomanactive(), senior.getWoman())};
				}
				value[i]=arractive;
			}
			if(selected[i].equals("16")) {
				value1[i]="个人糖尿病史";
				if(factor.getSex()==0) {
					arrdiab_his = new double []{persentdouble(senior.getMandiab_his()+senior.getWomandiab_his(), senior.getAllpeople()),persentdouble(senior.getMandiab_his(), senior.getMan()),persentdouble(senior.getWomandiab_his(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrdiab_his = new double [] { persentdouble(senior.getMandiab_his(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrdiab_his = new double [] { persentdouble(senior.getWomandiab_his(), senior.getWoman())};
				}
				value[i]=arrdiab_his;
			}
			if(selected[i].equals("17")) {
				value1[i]="个人高血压史";
				if(factor.getSex()==0) {
					arrhbp_his = new double []{persentdouble(senior.getManhbp_his()+senior.getWomanhbp_his(), senior.getAllpeople()),persentdouble(senior.getManhbp_his(), senior.getMan()),persentdouble(senior.getWomanhbp_his(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrhbp_his = new double [] { persentdouble(senior.getManhbp_his(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrhbp_his = new double [] { persentdouble(senior.getWomanhbp_his(), senior.getWoman())};
				}
				value[i]=arrhbp_his;
			}
			if(selected[i].equals("18")) {
				value1[i]="个人肺癌史";
				if(factor.getSex()==0) {
					arrlc_his = new double []{persentdouble(senior.getManlc_his()+senior.getWomanlc_his(), senior.getAllpeople()),persentdouble(senior.getManlc_his(), senior.getMan()),persentdouble(senior.getWomanlc_his(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrlc_his = new double [] { persentdouble(senior.getManlc_his(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrlc_his = new double [] { persentdouble(senior.getWomanlc_his(), senior.getWoman())};
				}
				value[i]=arrlc_his;
			}
			if(selected[i].equals("19")) {
				value1[i]="个人血脂异常";
				if(factor.getSex()==0) {
					arrBF = new double []{persentdouble(senior.getManBF()+senior.getWomanBF(), senior.getAllpeople()),persentdouble(senior.getManBF(), senior.getMan()),persentdouble(senior.getWomanBF(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrBF = new double [] { persentdouble(senior.getManBF(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrBF = new double [] { persentdouble(senior.getWomanBF(), senior.getWoman())};
				}
				value[i]=arrBF;
			}
			if(selected[i].equals("20")) {
				value1[i]="个人冠心病";
				if(factor.getSex()==0) {
					arrchd_his = new double []{persentdouble(senior.getManchd_his()+senior.getWomanchd_his(), senior.getAllpeople()),persentdouble(senior.getManchd_his(), senior.getMan()),persentdouble(senior.getWomanchd_his(), senior.getWoman())};
				}else if(factor.getSex()==1){
					arrchd_his = new double [] { persentdouble(senior.getManchd_his(), senior.getMan())};
				}else if(factor.getSex()==2) {
					arrchd_his = new double [] { persentdouble(senior.getWomanchd_his(), senior.getWoman())};
				}
				value[i]=arrchd_his;
			}
		}
				if(factor.getSex()==0) {
					 value2 =new String [] {
							"总体", "男", "女"
					};
					
				}else if(factor.getSex()==1) {
					 value2= new String []{
							 "男"
					};
				}else {
					 value2= new String []{
							 "女"
					};
				}
				return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
					Color.YELLOW, Color.GREEN, new Color(253, 95, 77), Color.RED
				},"百分比",0.29999999999999999D);
		
	}
	public double persent(Integer beichu,Integer chu) {
		if(chu!=0) {
			BigDecimal bg = new BigDecimal((double)beichu/chu);
			double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return f1;
		}else {
			return 0;
		}
		
	}
	public double persentdouble(Integer beichu,Integer chu) {
		if(chu!=0) {
			BigDecimal bg = new BigDecimal((double)beichu/chu);
			double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return f1;
		}else {
			return 0;
		}
		
	}
	public double division(int beichu,int chu) {
		if(chu==0) {
			return 0;
		}else {
			BigDecimal bg = new BigDecimal((double)beichu/chu);
			double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return f1;
		}
		
	} 

}
