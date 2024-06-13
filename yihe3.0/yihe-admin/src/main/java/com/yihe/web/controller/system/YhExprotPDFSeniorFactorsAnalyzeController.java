package com.yihe.web.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.common.pdf.ManyTideReport;
import com.yihe.common.riskresult.DatePhaseUtil;
import com.yihe.common.riskresult.GroupReportManyTide;
import org.jfree.data.time.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.icu.math.BigDecimal;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.yihe.common.pdf.WebDownload;
import com.yihe.common.riskresult.GroupReport;
import com.yihe.system.domain.YhExportHealthAssess;
import com.yihe.system.domain.YhExportPdfSenior;
import com.yihe.system.domain.YhSeniorFactorsAnalyze;
import com.yihe.system.service.YhExprotPDFSeniorFactorsAnalyzeService;
import com.yihe.system.utils.BaseReport;
import com.yihe.system.utils.YhSeniorFactorsAnalyzeUtil;

import static com.yihe.common.riskresult.GroupReportManyTide.getDatePhase;

@Controller
@RequestMapping("/ExprotPDFSenior")
public class YhExprotPDFSeniorFactorsAnalyzeController {
	@Autowired
	YhExprotPDFSeniorFactorsAnalyzeService analyzeservice;
    public static BaseFont baseFont;
    public static Font head;
	@RequestMapping(value = "/exportsenior", method = RequestMethod.GET)
	public String  export(YhSeniorFactorsAnalyze factorsanalyze,HttpServletRequest request, HttpServletResponse response){
		String[] selectyinsu=factorsanalyze.getSelect().split(",");
		YhSeniorFactorsAnalyze factor=new YhSeniorFactorsAnalyze();
		factor.setSelect(factorsanalyze.getSelect());
		factor.setSelect1(factorsanalyze.getSelect1());
		factor.setSex(factorsanalyze.getSex());
		factor.setTimes(factorsanalyze.getTimes());
		if(factorsanalyze.getChooseyinsu()==1){
			factorsanalyze.setTimes(factorsanalyze.getTimes().substring(0,7)+","+factorsanalyze.getTimes().substring(13,20)+";");
		}
		String duoyinsuname="";
		for (int i = 0; i < selectyinsu.length; i++) {
			if(selectyinsu[i].equals("0")) {
				if(factorsanalyze.getShousuo()==0) {
					factor.setShousuo_max(120.0);			
				}
				if(factorsanalyze.getShousuo()==1) {
					factor.setShousuo_max(140.0);
					factor.setShousuo_min(120.0);
				}
				if(factorsanalyze.getShousuo()==2) {
					factor.setShousuo_min(140.0);			
				}
				if(factorsanalyze.getShousuo()==3) {
					factor.setShousuo_min(factorsanalyze.getShousuo_min());
					factor.setShousuo_max(factorsanalyze.getShousuo_max());	
				}
				duoyinsuname+="收缩压;";
			}
			if(selectyinsu[i].equals("1")) {
				if(factorsanalyze.getShuzhang()==0) {
					factor.setShuzhang_max(80.0);			
				}
				if(factorsanalyze.getShuzhang()==1) {
					factor.setShuzhang_max(90.0);
					factor.setShuzhang_min(80.0);
				}
				if(factorsanalyze.getShuzhang()==2) {
					factor.setShuzhang_min(90.0);			
				}
				if(factorsanalyze.getShuzhang()==3) {
					factor.setShuzhang_min(factorsanalyze.getShuzhang_min());
					factor.setShuzhang_max(factorsanalyze.getShuzhang_max());	
				}
				duoyinsuname+="舒张压;";
			}
			
			if(selectyinsu[i].equals("2")) {
				if(factorsanalyze.getKongfu()==0) {
					factor.setKongfu_max(5.6);			
				}
				if(factorsanalyze.getKongfu()==1) {
					factor.setKongfu_max(7.0);
					factor.setKongfu_min(5.6);
				}
				if(factorsanalyze.getKongfu()==2) {
					factor.setKongfu_min(7.0);			
				}
				if(factorsanalyze.getKongfu()==3) {
					factor.setKongfu_min(factorsanalyze.getKongfu_min());
					factor.setKongfu_max(factorsanalyze.getKongfu_max());	
				}
				duoyinsuname+="空腹血糖;";
			}
			
			if(selectyinsu[i].equals("3")) {
				if(factorsanalyze.getZongdan()==0) {
					factor.setZongdan_max(5.18);			
				}
				if(factorsanalyze.getZongdan()==1) {
					factor.setZongdan_min(5.18);
				}
				if(factorsanalyze.getZongdan()==2) {
					factor.setZongdan_min(factorsanalyze.getZongdan_min());
					factor.setZongdan_max(factorsanalyze.getZongdan_max());		
				}
				duoyinsuname+="总胆固醇;";
			}
			
			if(selectyinsu[i].equals("4")) {
				if(factorsanalyze.getGanyou()==0) {
					factor.setGanyou_max(1.7);			
				}
				if(factorsanalyze.getGanyou()==1) {
					factor.setGanyou_max(2.25);
					factor.setGanyou_min(1.7);
				}
				if(factorsanalyze.getGanyou()==2) {
					factor.setGanyou_min(2.25);			
				}
				if(factorsanalyze.getGanyou()==3) {
					factor.setGanyou_min(factorsanalyze.getGanyou_min());
					factor.setGanyou_max(factorsanalyze.getGanyou_max());	
				}
				duoyinsuname+="甘油三酯;";
			}
			
			if(selectyinsu[i].equals("5")) {
				if(factorsanalyze.getHDL()==0) {
					factor.setHDL_max(1.04);			
				}
				if(factorsanalyze.getHDL()==1) {
					factor.setHDL_min(1.04);
				}
				if(factorsanalyze.getHDL()==2) {
					factor.setHDL_min(factorsanalyze.getHDL_min());
					factor.setHDL_max(factorsanalyze.getHDL_max());		
				}
				duoyinsuname+="HDL胆固醇;";
			}
			
			if(selectyinsu[i].equals("6")) {
				if(factorsanalyze.getLDL()==0) {
					factor.setLDL_max(3.37);			
				}
				if(factorsanalyze.getLDL()==1) {
					factor.setLDL_min(3.37);
				}
				if(factorsanalyze.getLDL()==2) {
					factor.setLDL_min(factorsanalyze.getLDL_min());
					factor.setLDL_max(factorsanalyze.getLDL_max());		
				}
				duoyinsuname+="LDL胆固醇;";
			}
			
			if(selectyinsu[i].equals("7")) {
				if(factorsanalyze.getTizhong()==0) {
					factor.setTizhong_max(18.5);			
				}
				if(factorsanalyze.getTizhong()==1) {
					factor.setTizhong_max(24.0);
					factor.setTizhong_min(18.5);
				}
				if(factorsanalyze.getTizhong()==2) {
					factor.setTizhong_max(28.0);
					factor.setTizhong_min(24.0);		
				}
				if(factorsanalyze.getTizhong()==3) {
					factor.setTizhong_min(factorsanalyze.getTizhong_min());
					factor.setTizhong_max(factorsanalyze.getTizhong_max());	
				}
				duoyinsuname+="体重指数;";
			}
			
			if(selectyinsu[i].equals("8")) {
				if(factorsanalyze.getYaowei()==2) {
					factor.setYaowei_min(factorsanalyze.getYaowei_min());
					factor.setYaowei_max(factorsanalyze.getYaowei_max());
				}
				if(factorsanalyze.getYaowei()==0) {
					factor.setYaowei_max(85.0);
				}
				if(factorsanalyze.getYaowei()==1) {
					factor.setYaowei_min(85.0);
				}
				duoyinsuname+="腰围;";
			}
			if(selectyinsu[i].equals("9")) {
					factor.setXiyan(factorsanalyze.getXiyan());
				duoyinsuname+="吸烟比率;";
			}
			if(selectyinsu[i].equals("10")) {
				factor.setYinjiu(factorsanalyze.getYinjiu());
				duoyinsuname+="饮酒比率;";
			}

			if(selectyinsu[i].equals("11")) {
				factor.setTili(factorsanalyze.getTili());
				duoyinsuname+="体力活动比率;";
			}
			if(selectyinsu[i].equals("12")) {
				factor.setFeiai(factorsanalyze.getFeiai());
			}
			if(selectyinsu[i].equals("14")) {
				factor.setTangniao(factorsanalyze.getTangniao());

			}
			if(selectyinsu[i].equals("15")) {
				factor.setGaoxueya(factorsanalyze.getGaoxueya());

			}
			if(selectyinsu[i].equals("16")) {
				factor.setTangniaoshi(factorsanalyze.getTangniaoshi());
				duoyinsuname+="个人糖尿病史;";
			}
			if(selectyinsu[i].equals("17")) {
				factor.setGaoxueyashi(factorsanalyze.getGaoxueyashi());
				duoyinsuname+="个人高血压史;";
			}
			if(selectyinsu[i].equals("18")) {
				factor.setFeiaishi(factorsanalyze.getFeiaishi());
			}
			if(selectyinsu[i].equals("19")) {
				factor.setXuezhi(factorsanalyze.getXuezhi());
				duoyinsuname+="个人血脂异常;";
			}
 			if(selectyinsu[i].equals("20")) {
				factor.setGuanxin(factorsanalyze.getGuanxin());
				duoyinsuname+="个人冠心病;";
			}


			
		}
		List<YhExportHealthAssess> assess=analyzeservice.exportfactors(factor);
		
		YhExportPdfSenior senior= YhSeniorFactorsAnalyzeUtil.getresultbloofat(assess, factor);
		senior.setAllpeople(assess.size());
		Document document;
		GroupReport groupReport;
		groupReport = new GroupReport();
		document = null;
		// 告诉浏览器用什么软件可以打开此文件
		try
		{
			document = new Document(PageSize.A4);
			WebDownload.downloadFormFile(request,response, "********.pdf");
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			Phrase p = new Phrase("********", head);
			HeaderFooter header = new HeaderFooter(p, true);
			header.setAlignment(2);
			header.setBorder(0);
			document.setHeader(header);
			//添加内容
			BaseReport baseReport = new BaseReport(groupReport);
			if(factorsanalyze.getChooseyinsu()==0){
				document.add(baseReport.getPdfPTableseniors(factor,senior));
			}else{
				GroupReportManyTide groupReportManyTide=new GroupReportManyTide();
				//日期/人数/个数
				//int manyRisk[][]= {{1,2,5,1,2,1,2,5,1,2},{2,3,5,2,4,2,3,5,2,4},{2,3,5,2,4,2,3,5,2,4},{2,3,5,2,4,2,3,5,2,4}};

				//查询出来月份的评估的总人数


				//设置X轴下标的值
				List dateList = new ArrayList();
				List datePhase = getDatePhase(factorsanalyze.getTimes());
				int sizeming=duoyinsuname.split(";").length;
				String []sizemings=duoyinsuname.split(";");
				int manyRisk[][]=new int[sizeming][datePhase.size()];
				int count[]=new int[datePhase.size()];
				for (int k = 0; k < datePhase.size(); k++) {
					DatePhaseUtil util = (DatePhaseUtil) datePhase.get(k);
					//System.out.println(util.getDate() +"==="+util.getHql());
					//System.out.println(Integer.parseInt(util.getDate().substring(0, 4)));
					dateList.add(util);
					factor.setTimes(((DatePhaseUtil) datePhase.get(k)).getDate());
					List<YhExportHealthAssess> acc= analyzeservice.selectallcount(factor);
					YhExportPdfSenior seniora= YhSeniorFactorsAnalyzeUtil.getresultbloofat(acc, factor);
						if(acc.isEmpty()){
							count[k]=0;
						}else{
							count[k]=acc.size();
						}
					for (int i=0;i<sizeming;i++){
						if(sizemings[i].equals("收缩压")){
							manyRisk[i][k]=seniora.getMansbp()+seniora.getWomansbp();
						}else if(sizemings[i].equals("舒张压")){
							manyRisk[i][k]=seniora.getMandbp()+seniora.getWomandbp();
						}else if(sizemings[i].equals("空腹血糖")){
							manyRisk[i][k]=seniora.getManFBG()+seniora.getWomanFBG();
						}else if(sizemings[i].equals("总胆固醇")){
							manyRisk[i][k]=seniora.getMancholesterol()+seniora.getWomancholesterol();
						}else if(sizemings[i].equals("甘油三酯")){
							manyRisk[i][k]=seniora.getManTG()+seniora.getWomanTG();
						}else if(sizemings[i].equals("HDL胆固醇")){
							manyRisk[i][k]=seniora.getManhdlc()+seniora.getWomanhdlc();
						}else if(sizemings[i].equals("LDL胆固醇")){
							manyRisk[i][k]=seniora.getManldlc()+seniora.getWomanldlc();
						}else if(sizemings[i].equals("体重指数")){
							manyRisk[i][k]=seniora.getManbmi()+seniora.getWomanbmi();
						}else if(sizemings[i].equals("腰围")){
							manyRisk[i][k]=seniora.getManwaist()+seniora.getWomanwaist();
						}else if(sizemings[i].equals("吸烟比率")){
							manyRisk[i][k]=seniora.getMansmoke()+seniora.getWomansmoke();
						}else if(sizemings[i].equals("饮酒比率")){
							manyRisk[i][k]=seniora.getMandrink()+seniora.getWomandrink();
						}else if(sizemings[i].equals("体力活动比率")){
							manyRisk[i][k]=seniora.getManactive()+seniora.getWomanactive();
						}else if(sizemings[i].equals("个人糖尿病史")){
							manyRisk[i][k]=seniora.getMandiab_his()+seniora.getWomandiab_his();
						}else if(sizemings[i].equals("个人高血压史")){
							manyRisk[i][k]=seniora.getManhbp_his()+seniora.getWomanhbp_his();
						}else if(sizemings[i].equals("个人血脂异常")){
							manyRisk[i][k]=seniora.getManBF()+seniora.getWomanBF();
						}else if(sizemings[i].equals("个人冠心病")){
							manyRisk[i][k]=seniora.getManchd_his()+seniora.getWomanchd_his();
						}

					}



				}
				System.out.println(manyRisk);
				System.out.println(count);
				groupReportManyTide.setManyRisk(manyRisk);
				groupReportManyTide.setMount(count);
				groupReportManyTide.setDateList(dateList);
				//设置标注的的值
				groupReportManyTide.setRiskNames(duoyinsuname);
				ManyTideReport manyTideReport=new ManyTideReport(groupReport,groupReportManyTide);
				document.add(manyTideReport.manyTideReportPdf());
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
