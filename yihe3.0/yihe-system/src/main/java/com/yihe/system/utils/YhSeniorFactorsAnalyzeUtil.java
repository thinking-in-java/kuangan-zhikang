package com.yihe.system.utils;

import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.yihe.system.domain.YhExportHealthAssess;
import com.yihe.system.domain.YhExportPdfSenior;
import com.yihe.system.domain.YhSeniorFactorsAnalyze;

public class YhSeniorFactorsAnalyzeUtil {
	public static YhExportPdfSenior getresultbloofat(List<YhExportHealthAssess> baseassess,YhSeniorFactorsAnalyze factor) {
		YhExportPdfSenior senior=new YhExportPdfSenior();
		//活动情况
		float  active=0;
		//吸烟
		float smoke =0;
		//饮酒
		float drink =0;
		//糖尿病史
		float diab_his=0;
		//高血压病史
		float hbp_his=0;
		//高血脂
		float BF=0;
		//冠心病
		float chd_his=0;
		//收缩压
		float sbp=0;
		//舒张压
		float dbp=0;
		//空腹血糖
		float FBG=0;
		//总胆固醇
		float cholesterol=0;
		//甘油三酯
		float TG=0;
		//HDL
		double hdlc=0;
		//LDL
		double ldlc=0;
		//体重指数
		float bmi=0;
		//腰围
		float waist=0;
		float lc_his=0;
		int  manactivecount=0;
		int  womanactivecount=0;
		//吸烟
		int mansmokecount =0;
		int womansmokecount =0;
		//饮酒
		int mandrinkcount =0;
		int womandrinkcount =0;
		//糖尿病史
		int mandiab_hiscount=0;
		int womandiab_hiscount=0;
		//肺癌
		int manlc_hiscount=0;
		int womanlc_hiscount=0;
		//高血压病史
		int manhbp_hiscount=0;
		int womanhbp_hiscount=0;
		//高血脂
		int manBFcount=0;
		int womanBFcount=0;
		//冠心病
		int manchd_hiscount=0;
		int womanchd_hiscount=0;
		//收缩压
		int mansbpcount=0;
		int womansbpcount=0;
		//舒张压
		int mandbpcount=0;
		int womandbpcount=0;
		//空腹血糖
		int manFBGcount=0;
		int womanFBGcount=0;
		//总胆固醇
		int mancholesterolcount=0;
		int womancholesterolcount=0;
		//甘油三酯
		int manTGcount=0;
		int womanTGcount=0;
		//HDL
		int manhdlccount=0;
		int womanhdlccount=0;
		//LDL
		int manldlccount=0;
		int womanldlccount=0;
		//体重指数
		int manbmicount=0;
		int womanbmicount=0;
		//腰围
		int manwaistcount=0;
		int womanwaistcount=0;
		int man=0;
		int woman=0;
		//切割要导出的数据
		String[] selected=factor.getSelect().split(",");
		//循环查出的数据
		for (int i = 0; i < baseassess.size(); i++) {
			if(baseassess.get(i).getGender()==1) {
				man++;
			}
			if(baseassess.get(i).getGender()==2) {
				woman++;
			}
			//循环获取评估各项值的数字
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSport()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSport());
					//活动详情
					active=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSmoke()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSmoke());
					//吸烟情况
					smoke=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}	
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDrink()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDrink());
					//饮酒情况
					drink=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDiabHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDiabHis());
					//糖尿病史
					diab_his=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHbpHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHbpHis());
					//高血压病史
					hbp_his=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}	
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBf()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBf());
					//高血脂
					BF=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}	
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getChdHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getChdHis());
					//冠心病史
					chd_his=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}	
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSbp()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSbp());
					//收缩压
					sbp=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}	
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDbp()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDbp());
					//舒张压
					dbp=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}	
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFbg()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFbg());
					//空腹血糖
					FBG=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}	
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getCholesterol()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getCholesterol());
					//总胆固醇
					cholesterol=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}	
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getTg()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getTg());
					//甘油三酯
					TG=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHdlc()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHdlc());
					//高密度脂蛋白胆固醇
					hdlc=Double.valueOf(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getLdlc()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getLdlc());
					//低密度脂蛋白胆固醇
					ldlc=Double.valueOf(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBmi()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBmi());
					//体重指数（BMI）
					bmi=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getWaist()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getWaist());
					//腰围对应的风险内容
					waist=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getLcHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getLcHis());
					lc_his=Float.parseFloat(JsonPath.read(document, "$.now_value"));
				}
			}
			//循环选中的数值相加
			for (int j = 0; j < selected.length; j++) {
				//判断选中了哪个
				if(selected[j].equals("0")) {
					//判断是否是只大于或小于某个值
					if((factor.getShousuo_min()!=null &&  !("").equals(factor.getShousuo_min()))&&(factor.getShousuo_max()!=null &&  !("").equals(factor.getShousuo_max()))) {
						if(sbp>factor.getShousuo_min()&&sbp<=factor.getShousuo_max()&&baseassess.get(i).getGender()==1 && factor.getShousuo_max()!=null &&  !factor.getShousuo_max().equals("")) {
							mansbpcount++;
						}
						if(sbp>factor.getShousuo_min()&&sbp<=factor.getShousuo_max()&&baseassess.get(i).getGender()==2 && factor.getShousuo_max()!=null &&  !factor.getShousuo_max().equals("")) {
							womansbpcount++;
						}
					}else if(factor.getShousuo_min()==null || factor.getShousuo_min().equals("")) {
						//判断符合条件的跟男女
						if(sbp<factor.getShousuo_max()&&baseassess.get(i).getGender()==1) {
							mansbpcount++;
						}
						if(sbp<factor.getShousuo_max()&&baseassess.get(i).getGender()==2) {
							womansbpcount++;
						}
					}else if(factor.getShousuo_max()==null || factor.getShousuo_max().equals("")) {
						if(sbp>=factor.getShousuo_min()&&baseassess.get(i).getGender()==1) {
							mansbpcount++;
						}
						if(sbp>=factor.getShousuo_min()&&baseassess.get(i).getGender()==2) {
							womansbpcount++;
						}
					}

				}else if(selected[j].equals("1")) {
					if((factor.getShuzhang_min()!=null &&  !("").equals(factor.getShuzhang_min()))&&(factor.getShuzhang_max()!=null &&  !("").equals(factor.getShuzhang_max()))) {
						if(dbp>factor.getShuzhang_min()&&dbp<=factor.getShuzhang_max()&&baseassess.get(i).getGender()==1) {
							mandbpcount++;
						}
						if(dbp>factor.getShuzhang_min()&&dbp<=factor.getShuzhang_max()&&baseassess.get(i).getGender()==2) {
							womandbpcount++;
						}
					}else if(factor.getShuzhang_min()==null || ("").equals(factor.getShuzhang_min())) {
						if(dbp<factor.getShuzhang_max()&&baseassess.get(i).getGender()==1) {
							mandbpcount++;
						}
						if(dbp<factor.getShuzhang_max()&&baseassess.get(i).getGender()==2) {
							womandbpcount++;
						}
					}else if(factor.getShuzhang_max()==null || factor.getShuzhang_max().equals("")) {
						if(dbp>=factor.getShuzhang_min()&&baseassess.get(i).getGender()==1) {
							mandbpcount++;
						}
						if(dbp>=factor.getShuzhang_min()&&baseassess.get(i).getGender()==2) {
							womandbpcount++;
						}
					}
				}else if(selected[j].equals("2")) {
					 if((factor.getKongfu_min()!=null &&  !("").equals(factor.getKongfu_min())) && (factor.getKongfu_max()!=null &&  !("").equals(factor.getKongfu_max()))){
						if(FBG>factor.getKongfu_min()&&FBG<=factor.getKongfu_max()&&baseassess.get(i).getGender()==1) {
							manFBGcount++;
						}
						if(FBG>factor.getKongfu_min()&&FBG<=factor.getKongfu_max()&&baseassess.get(i).getGender()==2) {
							womanFBGcount++;
						}
					}else if(factor.getKongfu_min()==null || factor.getKongfu_min().equals("")) {
						if(FBG<factor.getKongfu_max()&&baseassess.get(i).getGender()==1) {
							manFBGcount++;
						}
						if(FBG<factor.getKongfu_max()&&baseassess.get(i).getGender()==2) {
							womanFBGcount++;
						}
					}else if(factor.getKongfu_max()==null || factor.getKongfu_max().equals("")) {
						if(FBG>=factor.getKongfu_min()&&baseassess.get(i).getGender()==1) {
							manFBGcount++;
						}
						if(FBG>=factor.getKongfu_min()&&baseassess.get(i).getGender()==2) {
							womanFBGcount++;
						}
					}
				}else if(selected[j].equals("3")) {
					if((factor.getZongdan_min()!=null &&  !("").equals(factor.getZongdan_min())) && (factor.getZongdan_max()!=null && !("").equals(factor.getZongdan_max()))) {
						if(cholesterol>factor.getZongdan_min()&&cholesterol<=factor.getZongdan_max()&&baseassess.get(i).getGender()==1) {
							mancholesterolcount++;
						}
						if(cholesterol>factor.getZongdan_min()&&cholesterol<=factor.getZongdan_max()&&baseassess.get(i).getGender()==2) {
							womancholesterolcount++;
						}
					}else if(factor.getZongdan_min()==null || factor.getZongdan_min().equals("")) {
						if(cholesterol<factor.getZongdan_max()&&baseassess.get(i).getGender()==1) {
							mancholesterolcount++;
						}
						if(cholesterol<factor.getZongdan_max()&&baseassess.get(i).getGender()==2) {
							womancholesterolcount++;
						}
					}else if(factor.getZongdan_max()==null || factor.getZongdan_max().equals("")) {
						if(cholesterol>=factor.getZongdan_min()&&baseassess.get(i).getGender()==1) {
							mancholesterolcount++;
						}
						if(cholesterol>=factor.getZongdan_min()&&baseassess.get(i).getGender()==2) {
							womancholesterolcount++;
						}
					}
				}else if(selected[j].equals("4")) {
					 if((factor.getGanyou_min()!=null && !("").equals(factor.getGanyou_min())) &&(factor.getGanyou_max()!=null && !("").equals(factor.getGanyou_max())) ){
						if(TG>factor.getGanyou_min()&&TG<=factor.getGanyou_max()&&baseassess.get(i).getGender()==1) {
							manTGcount++;
						}
						if(TG>factor.getGanyou_min()&&TG<=factor.getGanyou_max()&&baseassess.get(i).getGender()==2) {
							womanTGcount++;
						}
					}else if(factor.getGanyou_min()==null || factor.getGanyou_min().equals("")) {
						if(TG<factor.getGanyou_max()&&baseassess.get(i).getGender()==1) {
							manTGcount++;
						}
						if(TG<factor.getGanyou_max()&&baseassess.get(i).getGender()==2) {
							womanTGcount++;
						}
					}else if(factor.getGanyou_max()==null || factor.getGanyou_max().equals("")) {
						if(TG>=factor.getGanyou_min()&&baseassess.get(i).getGender()==1) {
							manTGcount++;
						}
						if(TG>=factor.getGanyou_min()&&baseassess.get(i).getGender()==2) {
							womanTGcount++;
						}
					}
				}else if(selected[j].equals("5")) {
					if((factor.getHDL_min()!=null && !("").equals(factor.getHDL_min()))&&(factor.getHDL_max()!=null && !("").equals(factor.getHDL_max())) ) {
						if(hdlc>factor.getHDL_min()&&hdlc<=factor.getHDL_max()&&baseassess.get(i).getGender()==1) {
							manhdlccount++;
						}
						if(hdlc>factor.getHDL_min()&&hdlc<=factor.getHDL_max()&&baseassess.get(i).getGender()==2) {
							womanhdlccount++;
						}
					}else if(factor.getHDL_min()==null || factor.getHDL_min().equals("")) {
						if(hdlc<factor.getHDL_max()&&baseassess.get(i).getGender()==1) {
							manhdlccount++;
						}
						if(hdlc<factor.getHDL_max()&&baseassess.get(i).getGender()==2) {
							womanhdlccount++;
						}
					}else if(factor.getHDL_max()==null || factor.getHDL_max().equals("")) {
						if(hdlc>=factor.getHDL_min()&&baseassess.get(i).getGender()==1) {
							manhdlccount++;
						}
						if(hdlc>=factor.getHDL_min()&&baseassess.get(i).getGender()==2) {
							womanhdlccount++;
						}
					}
				}else if(selected[j].equals("6")) {
					if((factor.getLDL_min()!=null && !("").equals(factor.getLDL_min())) && (factor.getLDL_max()!=null && !("").equals(factor.getLDL_max()))) {
						if(ldlc>factor.getLDL_min()&&ldlc<=factor.getLDL_max()&&baseassess.get(i).getGender()==1) {
							manldlccount++;
						}
						if(ldlc>factor.getLDL_min()&&ldlc<=factor.getLDL_max()&&baseassess.get(i).getGender()==2) {
							womanldlccount++;
						}
					}else if(factor.getLDL_min()==null || factor.getLDL_min().equals("")) {
						if(ldlc<factor.getLDL_max()&&baseassess.get(i).getGender()==1) {
							manldlccount++;
						}
						if(ldlc<factor.getLDL_max()&&baseassess.get(i).getGender()==2) {
							womanldlccount++;
						}
					}else if(factor.getLDL_max()==null || factor.getLDL_max().equals("")) {
						if(ldlc>=factor.getLDL_min()&&baseassess.get(i).getGender()==1) {
							manldlccount++;
						}
						if(ldlc>=factor.getLDL_min()&&baseassess.get(i).getGender()==2) {
							womanldlccount++;
						}
					}
				}else if(selected[j].equals("7")) {
					 if((factor.getTizhong_min()!=null && !("").equals(factor.getTizhong_min())) && (factor.getTizhong_max()!=null && !("").equals(factor.getTizhong_max()))){
						if(bmi>factor.getTizhong_min()&&bmi<=factor.getTizhong_max()&&baseassess.get(i).getGender()==1) {
							manbmicount++;
						}
						if(bmi>factor.getTizhong_min()&&bmi<=factor.getTizhong_max()&&baseassess.get(i).getGender()==2) {
							womanbmicount++;
						}
					}else if(factor.getTizhong_min()==null || factor.getTizhong_min().equals("")) {
						if(bmi<factor.getTizhong_max()&&baseassess.get(i).getGender()==1) {
							manbmicount++;
						}
						if(bmi<factor.getTizhong_max()&&baseassess.get(i).getGender()==2) {
							womanbmicount++;
						}
					}else if(factor.getTizhong_max()==null || factor.getTizhong_max().equals("")) {
						if(bmi>=factor.getTizhong_min()&&baseassess.get(i).getGender()==1) {
							manbmicount++;
						}
						if(bmi>=factor.getTizhong_min()&&baseassess.get(i).getGender()==2) {
							womanbmicount++;
						}
					}
				}else if(selected[j].equals("8")) {
					if((factor.getYaowei_min()!=null && !("").equals(factor.getYaowei_min())) && (factor.getYaowei_max()!=null && !("").equals(factor.getYaowei_max()))){
						if(waist>factor.getYaowei_min()&&waist<=factor.getYaowei_max()&&baseassess.get(i).getGender()==1) {
							manwaistcount++;
						}
						if(waist>factor.getYaowei_min()&&waist<=factor.getYaowei_max()&&baseassess.get(i).getGender()==2) {
							womanwaistcount++;
						}
					}else  if(factor.getYaowei_min()==null || factor.getYaowei_min().equals("")) {
						if(waist<factor.getYaowei_max()&&baseassess.get(i).getGender()==1) {
							manwaistcount++;
						}
						if(waist<factor.getYaowei_max()&&baseassess.get(i).getGender()==2) {
							womanwaistcount++;
						}
					}else if(factor.getYaowei_max()==null || factor.getYaowei_max().equals("")) {
						if(waist>=factor.getYaowei_min()&&baseassess.get(i).getGender()==1) {
							manwaistcount++;
						}
						if(waist>=factor.getYaowei_min()&&baseassess.get(i).getGender()==2) {
							womanwaistcount++;
						}
					}
				}else if(selected[j].equals("9")) {
					if(factor.getXiyan()==smoke && baseassess.get(i).getGender()==1) {
						mansmokecount++;
					}
					if(factor.getXiyan()==smoke && baseassess.get(i).getGender()==2) {
						womansmokecount++;
					}

				}else if(selected[j].equals("10")) {
					if(factor.getYinjiu()==drink && baseassess.get(i).getGender()==1) {
						mandrinkcount++;
					}
					if(factor.getYinjiu()==drink && baseassess.get(i).getGender()==2) {
						womandrinkcount++;
					}

				}else if(selected[j].equals("11")) {
					if(factor.getTili()==active && baseassess.get(i).getGender()==1) {
						manactivecount++;
					}
					if(factor.getTili()==active && baseassess.get(i).getGender()==2) {
						womanactivecount++;
					}

				}else if(selected[j].equals("16")) {
					if(factor.getTangniaoshi()==diab_his && baseassess.get(i).getGender()==1) {
						mandiab_hiscount++;
					}
					if(factor.getTangniaoshi()==diab_his && baseassess.get(i).getGender()==2) {
						womandiab_hiscount++;
					}
//					if(factor.getTangniaoshi()==diab_his && baseassess.get(i).getGender()==1) {
//						mandiab_hiscount++;
//					}
//					if(factor.getTangniaoshi()==diab_his && baseassess.get(i).getGender()==2) {
//						womandiab_hiscount++;
//					}
				}else if(selected[j].equals("17")) {
					if(factor.getGaoxueyashi()==hbp_his && baseassess.get(i).getGender()==1) {
						manhbp_hiscount++;
					}
					if(factor.getGaoxueyashi()==hbp_his && baseassess.get(i).getGender()==2) {
						womanhbp_hiscount++;
					}
//					if(factor.getGaoxueyashi()==hbp_his && baseassess.get(i).getGender()==1) {
//						manhbp_hiscount++;
//					}
//					if(factor.getGaoxueyashi()==hbp_his && baseassess.get(i).getGender()==2) {
//						womanhbp_hiscount++;
//					}
				}else if(selected[j].equals("19")) {
					if(factor.getXuezhi()==BF && baseassess.get(i).getGender()==1) {
						manBFcount++;
					}
					if(factor.getXuezhi()==BF && baseassess.get(i).getGender()==2) {
						womanBFcount++;
					}
//					if(factor.getXuezhi()==BF && baseassess.get(i).getGender()==1) {
//						manBFcount++;
//					}
//					if(factor.getXuezhi()==BF && baseassess.get(i).getGender()==2) {
//						womanBFcount++;
//					}
				}else if(selected[j].equals("20")) {
					if(factor.getGuanxin()==chd_his && baseassess.get(i).getGender()==1) {
						manchd_hiscount++;
					}
					if(factor.getGuanxin()==chd_his && baseassess.get(i).getGender()==2) {
						womanchd_hiscount++;
					}
//					if(factor.getGuanxin()==chd_his && baseassess.get(i).getGender()==1) {
//						manchd_hiscount++;
//					}
//						if(factor.getGuanxin()==chd_his && baseassess.get(i).getGender()==2) {
//						womanchd_hiscount++;
//					}
				}
			}
			
		}
		senior.setMan(man);
		senior.setManactive(manactivecount);
		senior.setManBF(manBFcount);
		senior.setManbmi(manbmicount);
		senior.setManchd_his(manchd_hiscount);
		senior.setMancholesterol(mancholesterolcount);
		senior.setMandbp(mandbpcount);
		senior.setMandiab_his(mandiab_hiscount);
		senior.setMandrink(mandrinkcount);
		senior.setManFBG(manFBGcount);
		senior.setManhbp_his(manhbp_hiscount);
		senior.setManhdlc(manhdlccount);
		senior.setManldlc(manldlccount);
		senior.setMansbp(mansbpcount);
		senior.setMansmoke(mansmokecount);
		senior.setManTG(manTGcount);
		senior.setManwaist(manwaistcount);
		
		senior.setWoman(woman);
		senior.setWomanactive(womanactivecount);
		senior.setWomanBF(womanBFcount);
		senior.setWomanbmi(womanbmicount);
		senior.setWomanchd_his(womanchd_hiscount);
		senior.setWomancholesterol(womancholesterolcount);
		senior.setWomandbp(womandbpcount);
		senior.setWomandiab_his(womandiab_hiscount);
		senior.setWomandrink(womandrinkcount);
		senior.setWomanFBG(womanFBGcount);
		senior.setWomanhbp_his(womanhbp_hiscount);
		senior.setWomanhdlc(womanhdlccount);
		senior.setWomanldlc(womanldlccount);
		senior.setWomansbp(womansbpcount);
		senior.setWomansmoke(womansmokecount);
		senior.setWomanTG(womanTGcount);
		senior.setWomanwaist(womanwaistcount);
		
		return senior;
	}

}
