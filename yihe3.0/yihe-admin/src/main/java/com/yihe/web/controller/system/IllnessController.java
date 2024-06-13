/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhDiseaseAssessReport;

/**
 * @author dong
 *
 */
public class IllnessController {

	/**
	 * 查询疾病
	 * @param dise
	 * @return
	 */
	 public static Map<String,Object> showIllness(List<YhDiseaseAssessReport> dise) { 
		 
		 int[] gxy = new int[5];
		 int Nowwithgxy=0;//高血压现患
		   int tallgxy=0;//高风险
		   int mediumgxy=0;//中等
		   int lowgxy=0;//低风险
		   int suspectedgxy=0;//疑似
		   
		   int[] tnb = new int[5];
		   int Nowwithtnb=0;//糖尿病现患
		   int talltnb=0;//高风险
		   int mediumtnb=0;//中等
		   int lowtnb=0;//低风险
		   int suspectedtnb=0;//疑似
		  
		   
		   int[] gxb = new int[5];
		   int Nowwithgxb=0;//冠心病现患
		   int tallgxb=0;//高风险
		   int mediumgxb=0;//中等
		   int lowgxb=0;//低风险
		   int suspectedgxb=0;//疑似
		 
		   
		   int[] fa = new int[5];
		   int Nowwithfa=0;//肺癌现患
		   int tallfa=0;//高风险
		   int mediumfa=0;//中等
		   int lowfa=0;//低风险
		   int suspectedfa=0;//疑似
		   
		   
		   int[] nzz = new int[5];
		   int Nowwithnzz=0;//脑卒中现患
		   int tallnzz=0;//高风险
		   int mediumnzz=0;//中等
		   int lownzz=0;//低风险
		   int suspectednzz=0;//疑似
		   
		   int[] rxa = new int[5];
		   int Nowwithrxa=0;//乳腺癌现患
		   int tallrxa=0;//高风险
		   int mediumrxa=0;//中等
		   int lowrxa=0;//低风险
		   int suspectedrxa=0;//疑似
		   
		   int[] qlx = new int[5];
		   int Nowwithqlx=0;//前列腺现患
		   int tallqlx=0;//高风险
		   int mediumqlx=0;//中等
		   int lowqlx=0;//低风险
		   int suspectedqlx=0;//疑似
		   
		   int[] tf = new int[5];
		   int Nowwithtf=0;//痛风现患
		   int talltf=0;//高风险
		   int mediumtf=0;//中等
		   int lowtf=0;//低风险
		   int suspectedtf=0;//疑似
		   
		   int[] gzss = new int[5];
		   int Nowwithgzss=0;//骨质疏松现患
		   int tallgzss=0;//高风险
		   int mediumgzss=0;//中等
		   int lowgzss=0;//低风险
		   int suspectedgzss=0;//疑似
		   
		  for (YhDiseaseAssessReport yhDiseaseAssessReport : dise) {
			   if(yhDiseaseAssessReport.getDiseaseName().equals("高血压")) {
				   if(yhDiseaseAssessReport.getRiskLevel().equals("现患")) {
					   Nowwithgxy++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("极高危") || yhDiseaseAssessReport.getRiskLevel().equals("较高风险") || yhDiseaseAssessReport.getRiskLevel().equals("高风险")) {
					   tallgxy++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("中等风险")) {
					   mediumgxy++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("低风险") || yhDiseaseAssessReport.getRiskLevel().equals("较低风险")) {
					   lowgxy++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("疑似")) {
					   suspectedgxy++;
				   }
			   }else if(yhDiseaseAssessReport.getDiseaseName().equals("糖尿病")) {
				   if(yhDiseaseAssessReport.getRiskLevel().equals("现患")) {
					   Nowwithtnb++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("极高危") || yhDiseaseAssessReport.getRiskLevel().equals("较高风险") || yhDiseaseAssessReport.getRiskLevel().equals("高风险")) {
					   talltnb++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("中等风险")) {
					   mediumtnb++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("低风险") || yhDiseaseAssessReport.getRiskLevel().equals("较低风险")) {
					   lowtnb++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("疑似")) {
					   suspectedtnb++;
				   }
			   }else if(yhDiseaseAssessReport.getDiseaseName().equals("冠心病")) {
				   if(yhDiseaseAssessReport.getRiskLevel().equals("现患")) {
					   Nowwithgxb++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("极高危") || yhDiseaseAssessReport.getRiskLevel().equals("较高风险") || yhDiseaseAssessReport.getRiskLevel().equals("高风险")) {
					   tallgxb++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("中等风险")) {
					   mediumgxb++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("低风险") || yhDiseaseAssessReport.getRiskLevel().equals("较低风险")) {
					   lowgxb++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("疑似")) {
					   suspectedgxb++;
				   }
			   }else if(yhDiseaseAssessReport.getDiseaseName().equals("肺癌")) {
				   if(yhDiseaseAssessReport.getRiskLevel().equals("现患")) {
					   Nowwithfa++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("极高危") || yhDiseaseAssessReport.getRiskLevel().equals("较高风险") || yhDiseaseAssessReport.getRiskLevel().equals("高风险")) {
					   tallfa++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("中等风险")) {
					   mediumfa++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("低风险") || yhDiseaseAssessReport.getRiskLevel().equals("较低风险")) {
					   lowfa++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("疑似")) {
					   suspectedfa++;
				   }
			   }else if(yhDiseaseAssessReport.getDiseaseName().equals("脑卒中")) {
				   if(yhDiseaseAssessReport.getRiskLevel().equals("现患")) {
					   Nowwithnzz++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("极高危") || yhDiseaseAssessReport.getRiskLevel().equals("较高风险") || yhDiseaseAssessReport.getRiskLevel().equals("高风险")) {
					   tallnzz++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("中等风险")) {
					   mediumnzz++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("低风险") || yhDiseaseAssessReport.getRiskLevel().equals("较低风险")) {
					   lownzz++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("疑似")) {
					   suspectednzz++;
				   }
			   }else if(yhDiseaseAssessReport.getDiseaseName().equals("乳腺癌")) {
				   if(yhDiseaseAssessReport.getRiskLevel().equals("现患")) {
					   Nowwithrxa++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("极高危") || yhDiseaseAssessReport.getRiskLevel().equals("较高风险") || yhDiseaseAssessReport.getRiskLevel().equals("高风险")) {
					   tallrxa++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("中等风险")) {
					   mediumrxa++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("低风险") || yhDiseaseAssessReport.getRiskLevel().equals("较低风险")) {
					   lowrxa++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("疑似")) {
					   suspectedrxa++;
				   }
			   }else if(yhDiseaseAssessReport.getDiseaseName().equals("前列腺")) {
				   if(yhDiseaseAssessReport.getRiskLevel().equals("现患")) {
					   Nowwithqlx++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("极高危") || yhDiseaseAssessReport.getRiskLevel().equals("较高风险") || yhDiseaseAssessReport.getRiskLevel().equals("高风险")) {
					   tallqlx++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("中等风险")) {
					   mediumqlx++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("低风险") || yhDiseaseAssessReport.getRiskLevel().equals("较低风险")) {
					   lowqlx++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("疑似")) {
					   suspectedqlx++;
				   }
			   }else if(yhDiseaseAssessReport.getDiseaseName().equals("痛风")) {
				   if(yhDiseaseAssessReport.getRiskLevel().equals("现患")) {
					   Nowwithtf++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("极高危") || yhDiseaseAssessReport.getRiskLevel().equals("较高风险") || yhDiseaseAssessReport.getRiskLevel().equals("高风险")) {
					   talltf++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("中等风险")) {
					   mediumtf++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("低风险") || yhDiseaseAssessReport.getRiskLevel().equals("较低风险")) {
					   lowtf++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("疑似")) {
					   suspectedtf++;
				   }
			   }else if(yhDiseaseAssessReport.getDiseaseName().equals("骨质疏松")) {
				   if(yhDiseaseAssessReport.getRiskLevel().equals("现患")) {
					   Nowwithgzss++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("极高危") || yhDiseaseAssessReport.getRiskLevel().equals("较高风险") || yhDiseaseAssessReport.getRiskLevel().equals("高风险")) {
					   tallgzss++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("中等风险")) {
					   mediumgzss++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("低风险") || yhDiseaseAssessReport.getRiskLevel().equals("较低风险")) {
					   lowgzss++;
				   }else if(yhDiseaseAssessReport.getRiskLevel().equals("疑似")) {
					   suspectedgzss++;
				   }
			   }
		}
		  gxy[0]=Nowwithgxy;
		  gxy[1]=tallgxy;
		  gxy[2]=mediumgxy;
		  gxy[3]=lowgxy;
		  gxy[4]=suspectedgxy;
		  
		  gzss[0]=Nowwithgzss;
		   gzss[1]=tallgzss;
		   gzss[2]=mediumgzss;
		   gzss[3]=lowgzss;
		   gzss[4]=suspectedgzss;
		   
		   tf[0]=Nowwithtf;
		   tf[1]=talltf;
		   tf[2]=mediumtf;
		   tf[3]=lowtf;
		   tf[4]=suspectedtf;
		   
		   qlx[0]=Nowwithqlx;
		   qlx[1]=tallqlx;
		   qlx[2]=mediumqlx;
		   qlx[3]=lowqlx;
		   qlx[4]=suspectedqlx;
		   
		   
		   rxa[0]=Nowwithrxa;
		   rxa[1]=tallrxa;
		   rxa[2]=mediumrxa;
		   rxa[3]=lowrxa;
		   rxa[4]=suspectedrxa;
		   
		   nzz[0]=Nowwithnzz;
		   nzz[1]=tallnzz;
		   nzz[2]=mediumnzz;
		   nzz[3]=lownzz;
		   nzz[4]=suspectednzz;
		   
		   fa[0]=Nowwithfa;
		   fa[1]=tallfa;
		   fa[2]=mediumfa;
		   fa[3]=lowfa;
		   fa[4]=suspectedfa;
		   
		   gxb[0]=Nowwithgxb;
		   gxb[1]=tallgxb;
		   gxb[2]=mediumgxb;
		   gxb[3]=lowgxb;
		   gxb[4]=suspectedgxb;
		   
		   tnb[0]=Nowwithtnb;
		   tnb[1]=talltnb;
		   tnb[2]=mediumtnb;
		   tnb[3]=lowtnb;
		   tnb[4]=suspectedtnb;
		   
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("gxy", gxy);
		 map.put("gzss", gzss);
		 map.put("tf", tf);
		 map.put("qlx", qlx);
		 map.put("rxa", rxa);
		 map.put("nzz", nzz);
		 map.put("fa", fa);
		 map.put("gxb", gxb);
		 map.put("tnb", tnb);
	       return map;  
	   } 
}
