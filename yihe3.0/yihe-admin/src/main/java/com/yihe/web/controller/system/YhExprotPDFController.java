package com.yihe.web.controller.system;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.SimpleDateFormat;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.yihe.common.pdf.WebDownload;
import com.yihe.common.riskresult.GroupReport;
import com.yihe.system.domain.*;
import com.yihe.system.service.YhExprotPDFService;
import com.yihe.system.utils.Analysisindividual;
import com.yihe.system.utils.BaseReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ExprotPDF")
public class YhExprotPDFController {
	@Autowired
	YhExprotPDFService pdfservice;
    public static BaseFont baseFont;
    public static Font head;
	@RequestMapping(value = "/exportnow", method = RequestMethod.GET)
	public String  export(YhExportPDFData yhexportnow,HttpServletRequest request, HttpServletResponse response){
		YhExportIntervention intervention=new YhExportIntervention();
		YhExportPeopleBasic basic=new YhExportPeopleBasic();
		YhExportDiseaseStatistical disstatis=new YhExportDiseaseStatistical(); 
		YhExportPdfBmi pdfbmi=new YhExportPdfBmi();
		YhExportPdfHypertension pdfhypertension=new YhExportPdfHypertension();
		YhExportPdfWaist pdfwaist=new YhExportPdfWaist(); 
		YhExportPdfSugar sugar=new YhExportPdfSugar();
		YhExportPdfBloodFat bloodfat=new YhExportPdfBloodFat();
		YhExportPdfFood food=new YhExportPdfFood();
		YhExportPdfDrink drink=new YhExportPdfDrink();
		YhExportPdfSmoke smoke=new YhExportPdfSmoke();
		YhExportPdfActive active=new YhExportPdfActive();
		YhExportPdfFactors pdffactors=new YhExportPdfFactors();
		if(yhexportnow.getTimes()!=null) {
			yhexportnow.setBegintime(yhexportnow.getTimes().substring(0, 10));
			yhexportnow.setEndtime(yhexportnow.getTimes().substring(13));	
		}
		//导出管理干预建议
		if(yhexportnow.getGuanli()!=null) {
			int havemanypeople=0;
			int maydiabetescount=0;
			int mayhypertensioncount=0;
			int diab_hiscount=0;
			int card_hiscount=0;
			int af_hiscount=0;
			int hbp_hiscount=0;
			int chd_hiscount=0;
			int emphy_hiscount=0;
			int prostate_diseasecount=0;
			int stroke_hiscount=0;
			int smokecount=0;
			int waistcount=0;
			int smoke_sencod=0;
			int bf=0;
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			intervention.setAssesspeople(assess.size());
			for (int i = 0; i < assess.size(); i++) {
				Object al[]=Analysisindividual.handleDF(assess.get(i), assess.get(i).getGender());
				for (int j = 0; j < al.length; j++) {
					if(j==0&&Integer.valueOf(String.valueOf(al[0]))>0) {
						havemanypeople++;
					}
					if(j==2&&Boolean.parseBoolean(String.valueOf(al[2]))) {
						maydiabetescount++;
					}
					if(j==3&&Boolean.parseBoolean(String.valueOf(al[3]))) {
						mayhypertensioncount++;
					}
					if(j==4&&Boolean.parseBoolean(String.valueOf(al[4]))) {
						diab_hiscount++;
					}
					if(j==5&&Boolean.parseBoolean(String.valueOf(al[5]))) {
						card_hiscount++;
					}
					if(j==6&&Boolean.parseBoolean(String.valueOf(al[6]))) {
						af_hiscount++;
					}
					if(j==7&&Boolean.parseBoolean(String.valueOf(al[7]))) {
						hbp_hiscount++;
					}
					if(j==8&&Boolean.parseBoolean(String.valueOf(al[8]))) {
						chd_hiscount++;
					}
					if(j==9&&Boolean.parseBoolean(String.valueOf(al[9]))) {
						emphy_hiscount++;
					}
					if(j==10&&Boolean.parseBoolean(String.valueOf(al[10]))) {
						prostate_diseasecount++;
					}
					if(j==11&&Boolean.parseBoolean(String.valueOf(al[11]))) {
						stroke_hiscount++;
					}
					if(j==12&&Boolean.parseBoolean(String.valueOf(al[12]))) {
						waistcount++;
					}
					if(j==13&&Boolean.parseBoolean(String.valueOf(al[13]))) {
						smokecount++;
					}
					if(j==14&&Boolean.parseBoolean(String.valueOf(al[14]))) {
						smoke_sencod++;
					}
					if(j==15&&Boolean.parseBoolean(String.valueOf(al[15]))) {
						bf++;
					}
				}
			}
			intervention.setMaydiabetes(maydiabetescount);
			intervention.setMayhypertension(mayhypertensioncount);
			intervention.setHaveriskpeople(havemanypeople);
			intervention.setSmoke_sencod(smoke_sencod);
			intervention.setBfcount(bf);
			StringBuffer xh_des = new StringBuffer("");
			if(diab_hiscount==0&&card_hiscount==0&&af_hiscount==0&&hbp_hiscount==0&&chd_hiscount==0&&emphy_hiscount==0&&prostate_diseasecount==0&&stroke_hiscount==0) {
				xh_des.append("暂无各种疾病的现患病者");
			}else {
				if(diab_hiscount>0) {
					xh_des.append("糖尿病患者"+diab_hiscount+"人；");
				}else if(card_hiscount>0) {
					xh_des.append("心血管疾病患者"+card_hiscount+"人；");
				}else if(af_hiscount>0) {
					xh_des.append("房颤患者"+af_hiscount+"人；");
				}else if(hbp_hiscount>0) {
					xh_des.append("高血压患者"+hbp_hiscount+"人；");
				}else if(emphy_hiscount>0) {
					xh_des.append("冠心病患者"+emphy_hiscount+"人；");
				}else if(chd_hiscount>0) {
					xh_des.append("肺气肿患者"+chd_hiscount+"人；");
				}else if(prostate_diseasecount>0) {
					xh_des.append("前列腺炎或肥大患者"+prostate_diseasecount+"人；");
				}else if(stroke_hiscount>0) {
					xh_des.append("脑卒中"+stroke_hiscount+"人；");
				}
			}
			intervention.setHavedis(xh_des.toString());
			intervention.setSmokepeople(smokecount);
			intervention.setWaistcount(waistcount);
			double f;
			if(intervention.getAssesspeople()==0) {
				f=0;
			}else {
				f=intervention.getHaveriskpeople()/(double)intervention.getAssesspeople();
			}
			
			BigDecimal bg = new BigDecimal(f);
			double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			intervention.setPersent(f1);
		}
		if(yhexportnow.getPeople()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			basic= Analysisindividual.setvaluepeplebasic(assess);
			//设置报告起始结束时间
			if(yhexportnow.getTimes()!=null) {
				basic.setBegintime(yhexportnow.getTimes().substring(0, 10));
				basic.setEndtime(yhexportnow.getTimes().substring(13));	
			}
		}
		if(yhexportnow.getDis()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseasestatistical(yhexportnow);
			disstatis=Analysisindividual.getdisstatement(assess);
		}
		if(yhexportnow.getBmi()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			pdfbmi=Analysisindividual.getresultbmi(assess);
		}
		if(yhexportnow.getYaowei()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			pdfwaist=Analysisindividual.getresultwaist(assess);
		}
		if(yhexportnow.getXueya()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			pdfhypertension=Analysisindividual.getresulthypertension(assess);
		}
		if(yhexportnow.getXuetang()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			sugar=Analysisindividual.getresultsugar(assess);
		}
		if(yhexportnow.getXuezhi()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			bloodfat=Analysisindividual.getresultbloofat(assess);
			bloodfat.setAllcount(assess.size());
		}
		if(yhexportnow.getYinshi()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			food=Analysisindividual.getresultfood(assess);
			food.setAllcount(assess.size());
		}
		if(yhexportnow.getYinjiu()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			drink=Analysisindividual.getresultdrink(assess);
			drink.setAllpeoplecount(assess.size());
		}
		if(yhexportnow.getXiyan()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			smoke=Analysisindividual.getresultsmoke(assess);
			smoke.setAllpeople(assess.size());
		}
		if(yhexportnow.getTili()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			active=Analysisindividual.getresultactitv(assess);
			active.setAllcount(assess.size());
		}
		if(yhexportnow.getWeixian()!=null) {
			List<YhExportHealthAssess> assess=pdfservice.selectdiseaseriskresult(yhexportnow);
			pdffactors=Analysisindividual.getresultfactors(assess);
			pdffactors.setAllpeople(assess.size());
		}
		Document document;
		GroupReport groupReport;
		groupReport = new GroupReport();
		document = null;
		// 告诉浏览器用什么软件可以打开此文件
		try
		{
			document = new Document(PageSize.A4);
			Date now = new Date();
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
			WebDownload.downloadFormFile(request,response, "《基本数据分析-"+f.format(now)+".pdf》.pdf");
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			Phrase p = new Phrase("管理干预建议", head);
			HeaderFooter header = new HeaderFooter(p, true);
			header.setAlignment(2);
			header.setBorder(0);
			document.setHeader(header);
			//添加内容
			BaseReport baseReport = new BaseReport(groupReport);


			//管理干预建议，人群基本情况，疾病风险,体重指数
			//document.add(baseReport.groupReportBasicPdf(basic));
			//document.add(baseReport.groupReportAdvicePdf(intervention));
			//document.add(baseReport.groupReportHealthRisksPdf(disstatis));
			//document.add(baseReport.groupReportBodyMassIndexPdf(pdfbmi));
			//document.add(baseReport.groupReportWaistlinePdf(pdfwaist));
			//document.add(baseReport.groupReportBloodPressurePdf(pdfhypertension));
			//document.add(baseReport.groupReportBloodSugarPdf(sugar));
			//document.add(baseReport.groupReportLipidPdf(bloodfat));
			//document.add(baseReport.groupReportMealHabitPdf(food));
			//document.add(baseReport.groupReportDrinkingPdf(drink));
			//document.add(baseReport.groupReportSmokePdf(smoke));
			//document.add(baseReport.groupReportPhysicalActivityPdf(active));
			//document.add(baseReport.groupReportRiskComplicationPdf(pdffactors));	
				if(yhexportnow.getGuanli()!=null) {
					document.add(baseReport.groupReportAdvicePdf(intervention));

				}
				if(yhexportnow.getPeople()!=null) {
					document.add(baseReport.groupReportBasicPdf(basic));
				}
				if(yhexportnow.getDis()!=null) {
					document.add(baseReport.groupReportHealthRisksPdf(disstatis));
				}
				if(yhexportnow.getBmi()!=null) {
					document.add(baseReport.groupReportBodyMassIndexPdf(pdfbmi));
				}
				if(yhexportnow.getYaowei()!=null) {
					document.add(baseReport.groupReportWaistlinePdf(pdfwaist));
				}
				if(yhexportnow.getXueya()!=null) {
					document.add(baseReport.groupReportBloodPressurePdf(pdfhypertension));
				}
				if(yhexportnow.getXuetang()!=null) {
					document.add(baseReport.groupReportBloodSugarPdf(sugar));
				}
				if(yhexportnow.getXuezhi()!=null) {
					document.add(baseReport.groupReportLipidPdf(bloodfat));
				}
				if(yhexportnow.getYinshi()!=null) {
					document.add(baseReport.groupReportMealHabitPdf(food));
				}
				if(yhexportnow.getYinjiu()!=null) {
					document.add(baseReport.groupReportDrinkingPdf(drink));
				}
				if(yhexportnow.getXiyan()!=null) {
					document.add(baseReport.groupReportSmokePdf(smoke));
				}
				if(yhexportnow.getTili()!=null) {
					document.add(baseReport.groupReportPhysicalActivityPdf(active));
				}
				if(yhexportnow.getWeixian()!=null) {
					 document.add(baseReport.groupReportRiskComplicationPdf(pdffactors));
				}
			//document.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			if (document != null && document.isOpen())
				document.close();
			
		}
		catch (Exception exception1) { 
		}
		return null;
	}
}
