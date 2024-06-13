package com.yihe.system.utils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.yihe.system.domain.*;

import java.util.List;

public class Analysisindividual {
	//获取管理干预建议数据
	public static Object[] handleDF(YhExportHealthAssess basicInfo,int gender)
	{
		//初始化数据
		/***
		 * df_count疾病因素数量
		 * df_names疾病因素名称
		 * waist腰围
		 * 
		 */
		int df_count = 0;
		StringBuffer df_names = new StringBuffer("");
		float waist = 0.0F;
		float sbp = 0.0F;
		float dbp = 0.0F;
		float fbg = 0.0F;
		float cholesterol = 0.0F;
		float tg = 0.0F;
		float hdlc = 0.0F;
		float ldlc = 0.0F;
		float bmi = 0.0F;
		float smoke = 0.0F;
		float drink = 0.0F;
		float sport = 0.0F;
		float food = 0.0F;
		//疑似糖尿病，疑似高血压，糖尿病，心血管，房颤，高血压，冠心病,肺气肿,前列腺炎或肥大,脑卒中
		Boolean maydiabetes=false;
		Boolean mayhypertension=false;
		Boolean diab_his=false;
		Boolean card_his=false;
		Boolean af_his=false;
		Boolean hbp_his=false;
		Boolean chd_his=false;
		Boolean emphy_his=false;
		Boolean prostate_disease=false;
		Boolean stroke_his=false;
		Boolean iswaist=false;
		Boolean issmoke=false;
		Boolean smoke_sencod=false;
		Boolean bf=false;
		for (int i = 0; i < basicInfo.getYhDiseaseRiskResult2s().size(); i++) {
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getFbg()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getFbg());
				fbg= Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getSbp()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getSbp());
				sbp= Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getDbp()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getDbp());
				dbp= Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getWaist()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getWaist());
				waist= Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getCholesterol()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getCholesterol());
				cholesterol= Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getTg()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getTg());
				tg=  Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getHdlc()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getHdlc());
				hdlc= Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getLdlc()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getLdlc());
				ldlc=  Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getBmi()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getBmi());
				bmi=  Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getSmoke()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getSmoke());
				smoke= Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getDrink()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getDrink());
				drink= Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getSport()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getSport());
				sport= Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getFood()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getFood());
				food= Float.parseFloat(JsonPath.read(document, "$.now_value"));
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getDiabHis()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getDiabHis());
				String ros=JsonPath.read(document, "$.now_value").toString();
				if(ros.equals("0")) {
					diab_his=false;
				}else {
					diab_his=true;
				}
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getCardHis()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getCardHis());
				String ros=JsonPath.read(document, "$.now_value").toString();
				if(ros.equals("0")) {
					card_his=false;
				}else {
					card_his=true;
				}
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getAfHis()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getCardHis());
				String ros=JsonPath.read(document, "$.now_value").toString();
				if(ros.equals("0")) {
					af_his=false;
				}else {
					af_his=true;
				}
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getHbpHis()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getHbpHis());
				String ros=JsonPath.read(document, "$.now_value").toString();
				if(ros.equals("0")) {
					hbp_his=false;
				}else {
					hbp_his=true;
				}
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getChdHis()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getChdHis());
				String ros=JsonPath.read(document, "$.now_value").toString();
				if(ros.equals("0")) {
					chd_his=false;
				}else {
					chd_his=true;
				}
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getEmphyHis()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getEmphyHis());
				String ros=JsonPath.read(document, "$.now_value").toString();
				if(ros.equals("0")) {
					emphy_his=false;
				}else {
					emphy_his=true;
				}
			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getProstateDisease()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getProstateDisease());
				String ros=JsonPath.read(document, "$.now_value").toString();
				if(ros.equals("0")) {
					prostate_disease=false;
				}else {
					prostate_disease=true;
				}
			}
//			if(basicInfo.getYhexpotriskresult().get(i).getQcode().equals("stroke_his")) {
//				if(basicInfo.getYhexpotriskresult().get(i).getQvalue().equals("0")) {
//					stroke_his=false;
//				}else {
//					stroke_his=true;
//				}
//			}
			if(basicInfo.getYhDiseaseRiskResult2s().get(i).getSmokeSencod()!=null) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(basicInfo.getYhDiseaseRiskResult2s().get(i).getSmokeSencod());
				String ros=JsonPath.read(document, "$.now_value").toString();
				if(ros.equals("1")) {
					smoke_sencod=true;
				}else {
					smoke_sencod=false;
				}
			}
		}
		if (bmi >= 24F)
		{
			df_count++;
			df_names.append("bmi;");
		}
		if (gender == 1)
		{
			if (waist >= 85F)
			{
				df_count++;
				df_names.append("waist;");
				iswaist=true;
			}
		} else
		if (gender == 2 && waist >= 80F)
		{
			df_count++;
			df_names.append("waist;");
			iswaist=true;
		}
		if (sbp >= 120F || dbp >= 80F)
		{
			df_count++;
			df_names.append("hbp;");
		}
		if ((double)fbg >= 6.0999999999999996D)
		{
			df_count++;
			df_names.append("fbg;");
		}
		if ((double)fbg >= 7.0D && diab_his==false)
		{
			maydiabetes=true;
		}
		if ((sbp >= 140F || dbp >= 90F)&&hbp_his==false )
		{
			mayhypertension=true;
		}
		if ((double)cholesterol >= 5.1799999999999997D || (double)tg >= 1.7D || (double)ldlc >= 3.3700000000000001D || (double)hdlc < 1.04D)
		{
			df_count++;
			df_names.append("bf;");
			bf=true;
		}
		if (food == 4F)
		{
			df_count++;
			df_names.append("food;");
		}
		
		if (smoke == 1.0F)
		{
			df_count++;
			df_names.append("smoke;");
			issmoke=true;
		}
		if (drink == 1.0F)
		{
			df_count++;
			df_names.append("drink;");
		}
		if (sport == 3F)
		{
			df_count++;
			df_names.append("sport;");
		}
		return (new Object[] {
				df_count, df_names.toString(),maydiabetes,mayhypertension,diab_his,card_his,af_his,hbp_his,chd_his,emphy_his,prostate_disease,stroke_his,iswaist,issmoke,smoke_sencod,bf
			});
	}
	public static YhExportPeopleBasic setvaluepeplebasic(List<YhExportHealthAssess> baseassess) {
		YhExportPeopleBasic peoplebasic=new YhExportPeopleBasic();
		peoplebasic.setPeoplecount(baseassess.size());
		int woman=0;
		int man=0;
		int age30Man=0;
		int age30_40Man=0;
		int age40_45Man=0;
		int age45_50Man=0;
		int age50_55Man=0;
		int age55_60Man=0;
		int age60Man=0;
		int age30Woman=0;
		int age30_40Woman=0;
		int age40_45Woman=0;
		int age45_50Woman=0;
		int age50_55Woman=0;
		int age55_60Woman=0;
		int age60Woman=0;
		int mandiab_hiscount=0;
		int mancard_hiscount=0;
		int manaf_hiscount=0;
		int manhbp_hiscount=0;
		int manchd_hiscount=0;
		int manemphy_hiscount=0;
		int manbronch_hiscount=0;
		int manprostate_diseasecount=0;
		int manstroke_hiscount=0;
		int womandiab_hiscount=0;
		int womancard_hiscount=0;
		int womanaf_hiscount=0;
		int womanhbp_hiscount=0;
		int womanchd_hiscount=0;
		int womanemphy_hiscount=0;
		int womanbronch_hiscount=0;
		int womanprostate_diseasecount=0;
		int womanstroke_hiscount=0;
		for (int i = 0; i < baseassess.size(); i++) {
			
			
			if(baseassess.get(i).getGender()==1) {
				man++;
			}else {
				woman++;
			}
			if(baseassess.get(i).getAge()<30&&baseassess.get(i).getGender()==1) {
				age30Man++;
			}else if(baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&baseassess.get(i).getGender()==1) {
				age30_40Man++;
			}else if(baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&baseassess.get(i).getGender()==1) {
				age40_45Man++;
			}else if(baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&baseassess.get(i).getGender()==1) {
				age45_50Man++;
			}else if(baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&baseassess.get(i).getGender()==1) {
				age50_55Man++;
			}else if(baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&baseassess.get(i).getGender()==1) {
				age55_60Man++;
			}else if(baseassess.get(i).getAge()>=60&&baseassess.get(i).getGender()==1) {
				age60Man++;
			}
			if(baseassess.get(i).getAge()<30&&baseassess.get(i).getGender()==2) {
				age30Woman++;
			}else if(baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&baseassess.get(i).getGender()==2) {
				age30_40Woman++;
			}else if(baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&baseassess.get(i).getGender()==2) {
				age40_45Woman++;
			}else if(baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&baseassess.get(i).getGender()==2) {
				age45_50Woman++;
			}else if(baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&baseassess.get(i).getGender()==2) {
				age50_55Woman++;
			}else if(baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&baseassess.get(i).getGender()==2) {
				age55_60Woman++;
			}else if(baseassess.get(i).getAge()>=60&&baseassess.get(i).getGender()==2) {
				age60Woman++;
			}
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size(); j++) {
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDiabHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDiabHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==1){
						mandiab_hiscount++;
					}

				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getCardHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getCardHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==1) {
						mancard_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getAfHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getAfHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==1) {
						manaf_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHbpHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHbpHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==1) {
						manhbp_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getChdHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getChdHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==1) {
						manchd_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getEmphyHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getEmphyHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==1) {
						manemphy_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBronchHis()!=null&&baseassess.get(i).getGender()==1) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBronchHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==1) {
						manbronch_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getProstateDisease()!=null&&baseassess.get(i).getGender()==1) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getProstateDisease());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==1) {
						manprostate_diseasecount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getStrokeHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getStrokeHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==1) {
						manstroke_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDiabHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDiabHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==2) {
						womandiab_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getCardHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getCardHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==2) {
						womancard_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getAfHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getAfHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==2) {
						womanaf_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHbpHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHbpHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==2) {
						womanhbp_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getChdHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getChdHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==2) {
						womanchd_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getEmphyHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getEmphyHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==2) {
						womanemphy_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBronchHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBronchHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==2) {
						womanbronch_hiscount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getProstateDisease()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getProstateDisease());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==2) {
						womanprostate_diseasecount++;
					}
				}
				if(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getStrokeHis()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getStrokeHis());
					if(JsonPath.read(document, "$.now_value").equals("1")&&baseassess.get(i).getGender()==2) {
						womanstroke_hiscount++;
					}
				}
			}
			
		}
		StringBuffer story=new StringBuffer();
		if(mandiab_hiscount+womandiab_hiscount!=0) {
			story.append("糖尿病患者"+(mandiab_hiscount+womandiab_hiscount)+"人，");
		}
		if(mancard_hiscount+womancard_hiscount!=0) {
			story.append("心血管疾病患者"+(mancard_hiscount+womancard_hiscount)+"人，");
		}
		if(manaf_hiscount+womanaf_hiscount!=0) {
			story.append("房颤患者"+(manaf_hiscount+womanaf_hiscount)+"人，");
		}
		if(manhbp_hiscount+womanhbp_hiscount!=0) {
			story.append("高血压患者"+(manhbp_hiscount+womanhbp_hiscount)+"人，");
		}
		if(manchd_hiscount+womanchd_hiscount!=0) {
			story.append("冠心病患者"+(manchd_hiscount+womanchd_hiscount)+"人，");
		}
		if(manemphy_hiscount+womanemphy_hiscount!=0) {
			story.append("肺气肿患者"+(manemphy_hiscount+womanemphy_hiscount)+"人，");
		}
		if(manbronch_hiscount+womanbronch_hiscount!=0) {
			story.append("慢性支气管炎患者"+(manbronch_hiscount+womanbronch_hiscount)+"人，");
		}
		if(manprostate_diseasecount+womanprostate_diseasecount!=0) {
			story.append("前列腺炎或肥大患者"+(manprostate_diseasecount+womanprostate_diseasecount)+"人，");
		}
		if(manstroke_hiscount+womanstroke_hiscount!=0) {
			story.append("脑卒中患者"+(manstroke_hiscount+womanstroke_hiscount)+"人，");
		}
		peoplebasic.setMancount(man);
		peoplebasic.setWomancount(woman);
		peoplebasic.setAge30_40Man(age30_40Man);
		peoplebasic.setAge30_40Woman(age30_40Woman);
		peoplebasic.setAge30Man(age30Man);
		peoplebasic.setAge30Woman(age30Woman);
		peoplebasic.setAge40_45Man(age40_45Man);
		peoplebasic.setAge40_45Woman(age40_45Woman);
		peoplebasic.setAge45_50Man(age45_50Man);
		peoplebasic.setAge45_50Woman(age45_50Woman);
		peoplebasic.setAge50_55Man(age50_55Man);
		peoplebasic.setAge50_55Woman(age50_55Woman);
		peoplebasic.setAge55_60Man(age55_60Man);
		peoplebasic.setAge55_60Woman(age55_60Woman);
		peoplebasic.setAge60Man(age60Man);
		peoplebasic.setAge60Woman(age60Woman);
		peoplebasic.setManaf_hiscount(manaf_hiscount);
		peoplebasic.setManbronch_hiscount(manbronch_hiscount);
		peoplebasic.setMancard_hiscount(mancard_hiscount);
		peoplebasic.setManchd_hiscount(manchd_hiscount);
		peoplebasic.setMandiab_hiscount(mandiab_hiscount);
		peoplebasic.setManemphy_hiscount(manemphy_hiscount);
		peoplebasic.setManhbp_hiscount(manhbp_hiscount);
		peoplebasic.setManprostate_diseasecount(manprostate_diseasecount);
		peoplebasic.setManstroke_hiscount(manstroke_hiscount);
		peoplebasic.setWomanaf_hiscount(womanaf_hiscount);
		peoplebasic.setWomanbronch_hiscount(womanbronch_hiscount);
		peoplebasic.setWomancard_hiscount(womancard_hiscount);
		peoplebasic.setWomanchd_hiscount(womanchd_hiscount);
		peoplebasic.setWomandiab_hiscount(womandiab_hiscount);
		peoplebasic.setWomanemphy_hiscount(womanemphy_hiscount);
		peoplebasic.setWomanhbp_hiscount(womanhbp_hiscount);
		peoplebasic.setWomanprostate_diseasecount(womanprostate_diseasecount);
		peoplebasic.setWomanstroke_hiscount(womanstroke_hiscount);
		peoplebasic.setStatements(story.toString());
		return peoplebasic;
	}
	
	
	
	
	public static YhExportDiseaseStatistical getdisstatement(List<YhExportHealthAssess> baseassess) {
		YhExportDiseaseStatistical disstatis=new YhExportDiseaseStatistical(); 
		disstatis.setPeoplecount(baseassess.size());
		int chd_hisVeryhighcount=0;
		int chd_hishighcount=0;
		int chd_hismediumcount=0;
		int chd_histhelowercount=0;
		int chd_hislowcount=0;
		int diab_hisVeryhighcount=0;
		int diab_hishighcount=0;
		int diab_hismediumcount=0;
		int diab_histhelowercount=0;
		int diab_hislowcount=0;
		int hbp_hisVeryhighcount=0;
		int hbp_hishighcount=0;
		int hbp_hismediumcount=0;
		int hbp_histhelowercount=0;
		int hbp_hislowcount=0;
		int stroke_hisVeryhighcount=0;
		int stroke_hishighcount=0;
		int stroke_hismediumcount=0;
		int stroke_histhelowercount=0;
		int stroke_hislowcount=0;
		int lc_hisVeryhighcount=0;
		int lc_hishighcount=0;
		int lc_hismediumcount=0;
		int lc_histhelowercount=0;
		int lc_hislowcount=0;
		int pc_hisVeryhighcount=0;
		int pc_hishighcount=0;
		int pc_hismediumcount=0;
		int pc_histhelowercount=0;
		int pc_hislowcount=0;
		int op_hisVeryhighcount=0;
		int op_hishighcount=0;
		int op_hismediumcount=0;
		int op_histhelowercount=0;
		int op_hislowcount=0;
		int pd_hisVeryhighcount=0;
		int pd_hishighcount=0;
		int pd_hismediumcount=0;
		int pd_histhelowercount=0;
		int pd_hislowcount=0;
		int bc_hisVeryhighcount=0;
		int bc_hishighcount=0;
		int bc_hismediumcount=0;
		int bc_histhelowercount=0;
		int bc_hislowcount=0;
		for (int i = 0; i < baseassess.size(); i++) {
			for (int j = 0; j < baseassess.get(i).getAssessreport().size(); j++) {
				//冠心病
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("1")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("疑似已患")) {
					chd_hisVeryhighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("1")&&(baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("高风险") || baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较高风险")) ) {
					chd_hishighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("1")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("中等风险")) {
					chd_hismediumcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("1")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较低风险")) {
					chd_histhelowercount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("1")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("低风险")) {
					chd_hislowcount++;
				}
				//脑卒中
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("2")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("疑似已患")) {
					stroke_hisVeryhighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("2")&&(baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("高风险")|| baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较高风险"))) {
					stroke_hishighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("2")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("中等风险")) {
					stroke_hismediumcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("2")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较低风险")) {
					stroke_histhelowercount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("2")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("低风险")) {
					stroke_hislowcount++;
				}
				//高血压
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("3")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("疑似已患")) {
					hbp_hisVeryhighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("3")&&(baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("高风险")|| baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较高风险"))) {
					hbp_hishighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("3")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("中等风险")) {
					hbp_hismediumcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("3")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较低风险")) {
					hbp_histhelowercount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("3")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("低风险")) {
					hbp_hislowcount++;
				}
				//糖尿病
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("4")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("疑似已患")) {
					diab_hisVeryhighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("4")&&(baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("高风险")|| baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较高风险"))) {
					diab_hishighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("4")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("中等风险")) {
					diab_hismediumcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("4")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较低风险")) {
					diab_histhelowercount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("4")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("低风险")) {
					diab_hislowcount++;
				}
				//痛风
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("5")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("疑似已患")) {
					pd_hisVeryhighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("5")&&(baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("高风险")|| baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较高风险"))) {
					pd_hishighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("5")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("中等风险")) {
					pd_hismediumcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("5")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较低风险")) {
					pd_histhelowercount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("5")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("低风险")) {
					pd_hislowcount++;
				}
				//骨质疏松
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("7")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("疑似已患")) {
					op_hisVeryhighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("7")&&(baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("高风险")|| baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较高风险"))) {
					op_hishighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("7")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("中等风险")) {
					op_hismediumcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("7")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较低风险")) {
					op_histhelowercount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("7")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("低风险")) {
					op_hislowcount++;
				}
				//肺癌
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("8")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("疑似已患")) {
					lc_hisVeryhighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("8")&&(baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("高风险")|| baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较高风险"))) {
					lc_hishighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("8")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("中等风险")) {
					lc_hismediumcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("8")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较低风险")) {
					lc_histhelowercount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("8")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("低风险")) {
					lc_hislowcount++;
				}
				//前列腺癌
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("9")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("疑似已患")) {
					pc_hisVeryhighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("9")&&(baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("高风险")|| baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较高风险"))) {
					pc_hishighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("9")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("中等风险")) {
					pc_hismediumcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("9")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较低风险")) {
					pc_histhelowercount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("9")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("低风险")) {
					pc_hislowcount++;
				}
				//前列腺癌
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("10")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("疑似已患")) {
					bc_hisVeryhighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("10")&&(baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("高风险")|| baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较高风险"))) {
					bc_hishighcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("10")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("中等风险")) {
					bc_hismediumcount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("10")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("较低风险")) {
					bc_histhelowercount++;
				}
				if(baseassess.get(i).getAssessreport().get(j).getDiseasId().equals("10")&&baseassess.get(i).getAssessreport().get(j).getRisk_level().equals("低风险")) {
					bc_hislowcount++;
				}
			}
		}
		disstatis.setChd_hishighcount(chd_hishighcount);
		disstatis.setChd_hislowcount(chd_hislowcount);
		disstatis.setChd_hismediumcount(chd_hismediumcount);
		disstatis.setChd_histhelowercount(chd_histhelowercount);
		disstatis.setChd_hisVeryhighcount(chd_hisVeryhighcount);
		disstatis.setDiab_hishighcount(diab_hishighcount);
		disstatis.setDiab_hislowcount(diab_hislowcount);
		disstatis.setDiab_hismediumcount(diab_hismediumcount);
		disstatis.setDiab_histhelowercount(diab_histhelowercount);
		disstatis.setDiab_hisVeryhighcount(diab_hisVeryhighcount);
		disstatis.setHbp_hishighcount(hbp_hishighcount);
		disstatis.setHbp_hislowcount(hbp_hislowcount);
		disstatis.setHbp_hismediumcount(hbp_hismediumcount);
		disstatis.setHbp_histhelowercount(hbp_histhelowercount);
		disstatis.setHbp_hisVeryhighcount(hbp_hisVeryhighcount);
		disstatis.setLc_hishighcount(lc_hishighcount);
		disstatis.setLc_hislowcount(lc_hislowcount);
		disstatis.setLc_hismediumcount(lc_hismediumcount);
		disstatis.setLc_histhelowercount(lc_histhelowercount);
		disstatis.setLc_hisVeryhighcount(lc_hisVeryhighcount);
		disstatis.setOp_hishighcount(op_hishighcount);
		disstatis.setOp_hislowcount(op_hislowcount);
		disstatis.setOp_hismediumcount(op_hismediumcount);
		disstatis.setOp_histhelowercount(op_histhelowercount);
		disstatis.setOp_hisVeryhighcount(op_hisVeryhighcount);
		disstatis.setPc_hishighcount(pc_hishighcount);
		disstatis.setPc_hislowcount(pc_hislowcount);
		disstatis.setPc_hismediumcount(pc_hismediumcount);
		disstatis.setPc_histhelowercount(pc_histhelowercount);
		disstatis.setPc_hisVeryhighcount(pc_hisVeryhighcount);
		disstatis.setStroke_hishighcount(stroke_hishighcount);
		disstatis.setStroke_hislowcount(stroke_hislowcount);
		disstatis.setStroke_hismediumcount(stroke_hismediumcount);
		disstatis.setStroke_histhelowercount(stroke_histhelowercount);
		disstatis.setStroke_hisVeryhighcount(stroke_hisVeryhighcount);
		disstatis.setPd_hishighcount(pd_hishighcount);
		disstatis.setPd_hislowcount(pd_hislowcount);
		disstatis.setPd_hismediumcount(pd_hismediumcount);
		disstatis.setPd_histhelowercount(pd_histhelowercount);
		disstatis.setPd_hisVeryhighcount(pd_hisVeryhighcount);
		disstatis.setBc_hishighcount(bc_hishighcount);
		disstatis.setBc_hislowcount(bc_hislowcount);
		disstatis.setBc_hismediumcount(bc_hismediumcount);
		disstatis.setBc_histhelowercount(bc_histhelowercount);
		disstatis.setBc_hisVeryhighcount(bc_hisVeryhighcount);
		return disstatis;
	}
	
	
	
	
	public static YhExportPdfBmi getresultbmi(List<YhExportHealthAssess> baseassess) {
		YhExportPdfBmi pdfbmi=new YhExportPdfBmi(); 
		int mancountall=0;
		int mancountthin=0;
		int mancountnormal=0;
		int mancountoverweight=0;
		int mancountobesity=0;
		int womancountall=0;
		int womancountthin=0;
		int womancountnormal=0;
		int womancountoverweight=0;
		int womancountobesity=0;
		int age30mancountall=0;
		int age30mancountthin=0;
		int age30mancountnormal=0;
		int age30mancountoverweight=0;
		int age30mancountobesity=0;
		int age30_40mancountall=0;
		int age30_40mancountthin=0;
		int age30_40mancountnormal=0;
		int age30_40mancountoverweight=0;
		int age30_40mancountobesity=0;
		int age40_45mancountall=0;
		int age40_45mancountthin=0;
		int age40_45mancountnormal=0;
		int age40_45mancountoverweight=0;
		int age40_45mancountobesity=0;
		int age45_50mancountall=0;
		int age45_50mancountthin=0;
		int age45_50mancountnormal=0;
		int age45_50mancountoverweight=0;
		int age45_50mancountobesity=0;
		int age50_55mancountall=0;
		int age50_55mancountthin=0;
		int age50_55mancountnormal=0;
		int age50_55mancountoverweight=0;
		int age50_55mancountobesity=0;
		int age55_60mancountall=0;
		int age55_60mancountthin=0;
		int age55_60mancountnormal=0;
		int age55_60mancountoverweight=0;
		int age55_60mancountobesity=0;
		int age60mancountall=0;
		int age60mancountthin=0;
		int age60mancountnormal=0;
		int age60mancountoverweight=0;
		int age60mancountobesity=0;
		int age30womancountall=0;
		int age30womancountthin=0;
		int age30womancountnormal=0;
		int age30womancountoverweight=0;
		int age30womancountobesity=0;
		int age30_40womancountall=0;
		int age30_40womancountthin=0;
		int age30_40womancountnormal=0;
		int age30_40womancountoverweight=0;
		int age30_40womancountobesity=0;
		int age40_45womancountall=0;
		int age40_45womancountthin=0;
		int age40_45womancountnormal=0;
		int age40_45womancountoverweight=0;
		int age40_45womancountobesity=0;
		int age45_50womancountall=0;
		int age45_50womancountthin=0;
		int age45_50womancountnormal=0;
		int age45_50womancountoverweight=0;
		int age45_50womancountobesity=0;
		int age50_55womancountall=0;
		int age50_55womancountthin=0;
		int age50_55womancountnormal=0;
		int age50_55womancountoverweight=0;
		int age50_55womancountobesity=0;
		int age55_60womancountall=0;
		int age55_60womancountthin=0;
		int age55_60womancountnormal=0;
		int age55_60womancountoverweight=0;
		int age55_60womancountobesity=0;
		int age60womancountall=0;
		int age60womancountthin=0;
		int age60womancountnormal=0;
		int age60womancountoverweight=0;
		int age60womancountobesity=0;
		double Bmi=0.0;
		for (int i = 0; i < baseassess.size(); i++) {
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBmi()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBmi());
					Bmi=Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&Bmi<18.5) {
				age30mancountall++;
				mancountall++;
				mancountthin++;
				age30mancountthin++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&Bmi>=18.5&&Bmi<24) {
				age30mancountall++;
				mancountall++;
				mancountnormal++;
				age30mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&Bmi>=24&&Bmi<28) {
				age30mancountall++;
				mancountall++;
				mancountoverweight++;
				age30mancountoverweight++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&Bmi>=28) {
				age30mancountall++;
				mancountall++;
				mancountobesity++;
				age30mancountobesity++;
			}
			//
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&Bmi<18.5) {
				age30_40mancountall++;
				mancountall++;
				mancountthin++;
				age30_40mancountthin++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&Bmi>=18.5&&Bmi<24) {
				age30_40mancountall++;
				mancountall++;
				mancountnormal++;
				age30_40mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&Bmi>=24&&Bmi<28) {
				age30_40mancountall++;
				mancountall++;
				mancountoverweight++;
				age30_40mancountoverweight++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&Bmi>=28) {
				age30_40mancountall++;
				mancountall++;
				mancountobesity++;
				age30_40mancountobesity++;
			}
			//
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&Bmi<18.5) {
				age40_45mancountall++;
				mancountall++;
				mancountthin++;
				age40_45mancountthin++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&Bmi>=18.5&&Bmi<24) {
				age40_45mancountall++;
				mancountall++;
				mancountnormal++;
				age40_45mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&Bmi>=24&&Bmi<28) {
				age40_45mancountall++;
				mancountall++;
				mancountoverweight++;
				age40_45mancountoverweight++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&Bmi>=28) {
				age40_45mancountall++;
				mancountall++;
				mancountobesity++;
				age40_45mancountobesity++;
			}
			//
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&Bmi<18.5) {
				age45_50mancountall++;
				mancountall++;
				mancountthin++;
				age45_50mancountthin++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&Bmi>=18.5&&Bmi<24) {
				age45_50mancountall++;
				mancountall++;
				mancountnormal++;
				age45_50mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&Bmi>=24&&Bmi<28) {
				age45_50mancountall++;
				mancountall++;
				mancountoverweight++;
				age45_50mancountoverweight++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&Bmi>=28) {
				age45_50mancountall++;
				mancountall++;
				mancountobesity++;
				age45_50mancountobesity++;
			}
			//
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&Bmi<18.5) {
				age50_55mancountall++;
				mancountall++;
				mancountthin++;
				age50_55mancountthin++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&Bmi>=18.5&&Bmi<24) {
				age50_55mancountall++;
				mancountall++;
				mancountnormal++;
				age50_55mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&Bmi>=24&&Bmi<28) {
				age50_55mancountall++;
				mancountall++;
				mancountoverweight++;
				age50_55mancountoverweight++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&Bmi>=28) {
				age50_55mancountall++;
				mancountall++;
				mancountobesity++;
				age50_55mancountobesity++;
			}
			
			//
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&Bmi<18.5) {
				age55_60mancountall++;
				mancountall++;
				mancountthin++;
				age55_60mancountthin++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&Bmi>=18.5&&Bmi<24) {
				age55_60mancountall++;
				mancountall++;
				mancountnormal++;
				age55_60mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&Bmi>=24&&Bmi<28) {
				age55_60mancountall++;
				mancountall++;
				mancountoverweight++;
				age55_60mancountoverweight++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&Bmi>=28) {
				age55_60mancountall++;
				mancountall++;
				mancountobesity++;
				age55_60mancountobesity++;
			}
			//
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&Bmi<18.5) {
				age60mancountall++;
				mancountall++;
				mancountthin++;
				age60mancountthin++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&Bmi>=18.5&&Bmi<24) {
				age60mancountall++;
				mancountall++;
				mancountnormal++;
				age60mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&Bmi>=24&&Bmi<28) {
				age60mancountall++;
				mancountall++;
				mancountoverweight++;
				age60mancountoverweight++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&Bmi>=28) {
				age60mancountall++;
				mancountall++;
				mancountobesity++;
				age60mancountobesity++;
			}
			
			//----------------------------------------------------
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&Bmi<18.5) {
				age30womancountall++;
				womancountall++;
				womancountthin++;
				age30womancountthin++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&Bmi>=18.5&&Bmi<24) {
				age30womancountall++;
				womancountall++;
				womancountnormal++;
				age30womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&Bmi>=24&&Bmi<28) {
				age30womancountall++;
				womancountall++;
				womancountoverweight++;
				age30womancountoverweight++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&Bmi>=28) {
				age30womancountall++;
				womancountall++;
				womancountobesity++;
				age30womancountobesity++;
			}
			//
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&Bmi<18.5) {
				age30_40womancountall++;
				womancountall++;
				womancountthin++;
				age30_40womancountthin++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&Bmi>=18.5&&Bmi<24) {
				age30_40womancountall++;
				womancountall++;
				womancountnormal++;
				age30_40womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&Bmi>=24&&Bmi<28) {
				age30_40womancountall++;
				womancountall++;
				womancountoverweight++;
				age30_40womancountoverweight++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&Bmi>=28) {
				age30_40womancountall++;
				womancountall++;
				womancountobesity++;
				age30_40womancountobesity++;
			}
			//
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&Bmi<18.5) {
				age40_45womancountall++;
				womancountall++;
				womancountthin++;
				age40_45womancountthin++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&Bmi>=18.5&&Bmi<24) {
				age40_45womancountall++;
				womancountall++;
				womancountnormal++;
				age40_45womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&Bmi>=24&&Bmi<28) {
				age40_45womancountall++;
				womancountall++;
				womancountoverweight++;
				age40_45womancountoverweight++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&Bmi>=28) {
				age40_45womancountall++;
				womancountall++;
				womancountobesity++;
				age40_45womancountobesity++;
			}
			//
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&Bmi<18.5) {
				age45_50womancountall++;
				womancountall++;
				womancountthin++;
				age45_50womancountthin++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&Bmi>=18.5&&Bmi<24) {
				age45_50womancountall++;
				womancountall++;
				womancountnormal++;
				age45_50womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&Bmi>=24&&Bmi<28) {
				age45_50womancountall++;
				womancountall++;
				womancountoverweight++;
				age45_50womancountoverweight++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&Bmi>=28) {
				age45_50womancountall++;
				womancountall++;
				womancountobesity++;
				age45_50womancountobesity++;
			}
			//
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&Bmi<18.5) {
				age50_55womancountall++;
				womancountall++;
				womancountthin++;
				age50_55womancountthin++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&Bmi>=18.5&&Bmi<24) {
				age50_55womancountall++;
				womancountall++;
				womancountnormal++;
				age50_55womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&Bmi>=24&&Bmi<28) {
				age50_55womancountall++;
				womancountall++;
				womancountoverweight++;
				age50_55womancountoverweight++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&Bmi>=28) {
				age50_55womancountall++;
				womancountall++;
				womancountobesity++;
				age50_55womancountobesity++;
			}
			
			//
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&Bmi<18.5) {
				age55_60womancountall++;
				womancountall++;
				womancountthin++;
				age55_60womancountthin++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&Bmi>=18.5&&Bmi<24) {
				age55_60womancountall++;
				womancountall++;
				womancountnormal++;
				age55_60womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&Bmi>=24&&Bmi<28) {
				age55_60womancountall++;
				womancountall++;
				womancountoverweight++;
				age55_60womancountoverweight++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&Bmi>=28) {
				age55_60womancountall++;
				womancountall++;
				womancountobesity++;
				age55_60womancountobesity++;
			}
			//
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&Bmi<18.5) {
				age60womancountall++;
				womancountall++;
				womancountthin++;
				age60womancountthin++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&Bmi>=18.5&&Bmi<24) {
				age60womancountall++;
				womancountall++;
				womancountnormal++;
				age60womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&Bmi>=24&&Bmi<28) {
				age60womancountall++;
				womancountall++;
				womancountoverweight++;
				age60womancountoverweight++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&Bmi>=28) {
				age60womancountall++;
				womancountall++;
				womancountobesity++;
				age60womancountobesity++;
			}
		}
		pdfbmi.setMancountall(mancountall);
		pdfbmi.setMancountthin(mancountthin);
		pdfbmi.setMancountnormal(mancountnormal);
		pdfbmi.setMancountoverweight(mancountoverweight);
		pdfbmi.setMancountobesity(mancountobesity);
		pdfbmi.setAge30mancountall(age30mancountall);
		pdfbmi.setAge30mancountthin(age30mancountthin);
		pdfbmi.setAge30mancountnormal(age30mancountnormal);
		pdfbmi.setAge30mancountoverweight(age30mancountoverweight);
		pdfbmi.setAge30mancountobesity(age30mancountobesity);
		pdfbmi.setAge30_40mancountall(age30_40mancountall);
		pdfbmi.setAge30_40mancountthin(age30_40mancountthin);
		pdfbmi.setAge30_40mancountnormal(age30_40mancountnormal);
		pdfbmi.setAge30_40mancountoverweight(age30_40mancountoverweight);
		pdfbmi.setAge30_40mancountobesity(age30_40mancountobesity);
		pdfbmi.setAge40_45mancountall(age40_45mancountall);
		pdfbmi.setAge40_45mancountthin(age40_45mancountthin);
		pdfbmi.setAge40_45mancountnormal(age40_45mancountnormal);
		pdfbmi.setAge40_45mancountoverweight(age40_45mancountoverweight);
		pdfbmi.setAge40_45mancountobesity(age40_45mancountobesity);
		pdfbmi.setAge45_50mancountall(age45_50mancountall);
		pdfbmi.setAge45_50mancountthin(age45_50mancountthin);
		pdfbmi.setAge45_50mancountnormal(age45_50mancountnormal);
		pdfbmi.setAge45_50mancountoverweight(age45_50mancountoverweight);
		pdfbmi.setAge45_50mancountobesity(age45_50mancountobesity);
		pdfbmi.setAge50_55mancountall(age50_55mancountall);
		pdfbmi.setAge50_55mancountthin(age50_55mancountthin);
		pdfbmi.setAge50_55mancountnormal(age50_55mancountnormal);
		pdfbmi.setAge50_55mancountoverweight(age50_55mancountoverweight);
		pdfbmi.setAge50_55mancountobesity(age50_55mancountobesity);
		pdfbmi.setAge55_60mancountall(age55_60mancountall);
		pdfbmi.setAge55_60mancountthin(age55_60mancountthin);
		pdfbmi.setAge55_60mancountnormal(age55_60mancountnormal);
		pdfbmi.setAge55_60mancountoverweight(age55_60mancountoverweight);
		pdfbmi.setAge55_60mancountobesity(age55_60mancountobesity);
		pdfbmi.setAge60mancountall(age60mancountall);
		pdfbmi.setAge60mancountthin(age60mancountthin);
		pdfbmi.setAge60mancountnormal(age60mancountnormal);
		pdfbmi.setAge60mancountoverweight(age60mancountoverweight);
		pdfbmi.setAge60mancountobesity(age60mancountobesity);
		
		pdfbmi.setWomancountall(womancountall);
		pdfbmi.setWomancountthin(womancountthin);
		pdfbmi.setWomancountnormal(womancountnormal);
		pdfbmi.setWomancountoverweight(womancountoverweight);
		pdfbmi.setWomancountobesity(womancountobesity);
		pdfbmi.setAge30womancountall(age30womancountall);
		pdfbmi.setAge30womancountthin(age30womancountthin);
		pdfbmi.setAge30womancountnormal(age30womancountnormal);
		pdfbmi.setAge30womancountoverweight(age30womancountoverweight);
		pdfbmi.setAge30womancountobesity(age30womancountobesity);
		pdfbmi.setAge30_40womancountall(age30_40womancountall);
		pdfbmi.setAge30_40womancountthin(age30_40womancountthin);
		pdfbmi.setAge30_40womancountnormal(age30_40womancountnormal);
		pdfbmi.setAge30_40womancountoverweight(age30_40womancountoverweight);
		pdfbmi.setAge30_40womancountobesity(age30_40womancountobesity);
		pdfbmi.setAge40_45womancountall(age40_45womancountall);
		pdfbmi.setAge40_45womancountthin(age40_45womancountthin);
		pdfbmi.setAge40_45womancountnormal(age40_45womancountnormal);
		pdfbmi.setAge40_45womancountoverweight(age40_45womancountoverweight);
		pdfbmi.setAge40_45womancountobesity(age40_45womancountobesity);
		pdfbmi.setAge45_50womancountall(age45_50womancountall);
		pdfbmi.setAge45_50womancountthin(age45_50womancountthin);
		pdfbmi.setAge45_50womancountnormal(age45_50womancountnormal);
		pdfbmi.setAge45_50womancountoverweight(age45_50womancountoverweight);
		pdfbmi.setAge45_50womancountobesity(age45_50womancountobesity);
		pdfbmi.setAge50_55womancountall(age50_55womancountall);
		pdfbmi.setAge50_55womancountthin(age50_55womancountthin);
		pdfbmi.setAge50_55womancountnormal(age50_55womancountnormal);
		pdfbmi.setAge50_55womancountoverweight(age50_55womancountoverweight);
		pdfbmi.setAge50_55womancountobesity(age50_55womancountobesity);
		pdfbmi.setAge55_60womancountall(age55_60womancountall);
		pdfbmi.setAge55_60womancountthin(age55_60womancountthin);
		pdfbmi.setAge55_60womancountnormal(age55_60womancountnormal);
		pdfbmi.setAge55_60womancountoverweight(age55_60womancountoverweight);
		pdfbmi.setAge55_60womancountobesity(age55_60womancountobesity);
		pdfbmi.setAge60womancountall(age60womancountall);
		pdfbmi.setAge60womancountthin(age60womancountthin);
		pdfbmi.setAge60womancountnormal(age60womancountnormal);
		pdfbmi.setAge60womancountoverweight(age60womancountoverweight);
		pdfbmi.setAge60womancountobesity(age60womancountobesity);
		return pdfbmi;
	}
	
	
	
	public static YhExportPdfWaist getresultwaist(List<YhExportHealthAssess> baseassess) {
		YhExportPdfWaist pdfwaist=new YhExportPdfWaist(); 
		int mancountnormal=0;
		int mancounthigh=0;
		int womancountnormal=0;
		int womancounthigh=0;
		int age30mancountnormal=0;
		int age30mancounthigh=0;
		int age30womancountnormal=0;
		int age30womancounthigh=0;
		int age30_40mancountnormal=0;
		int age30_40mancounthigh=0;
		int age30_40womancountnormal=0;
		int age30_40womancounthigh=0;
		int age40_45mancountnormal=0;
		int age40_45mancounthigh=0;
		int age40_45womancountnormal=0;
		int age40_45womancounthigh=0;
		int age45_50mancountnormal=0;
		int age45_50mancounthigh=0;
		int age45_50womancountnormal=0;
		int age45_50womancounthigh=0;
		int age50_55mancountnormal=0;
		int age50_55mancounthigh=0;
		int age50_55womancountnormal=0;
		int age50_55womancounthigh=0;
		int age55_60mancountnormal=0;
		int age55_60mancounthigh=0;
		int age55_60womancountnormal=0;
		int age55_60womancounthigh=0;
		int age60mancountnormal=0;
		int age60mancounthigh=0;
		int age60womancountnormal=0;
		int age60womancounthigh=0;
		double waist=0.0;
		for (int i = 0; i < baseassess.size(); i++) {
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getWaist()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getWaist());
					waist=Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&waist<85) {
				mancountnormal++;
				age30mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&waist>=85) {
				mancounthigh++;
				age30mancounthigh++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&waist<85) {
				mancountnormal++;
				age30_40mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&waist>=85) {
				mancounthigh++;
				age30_40mancounthigh++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&waist<85) {
				mancountnormal++;
				age40_45mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&waist>=85) {
				mancounthigh++;
				age40_45mancounthigh++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&waist<85) {
				mancountnormal++;
				age45_50mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&waist>=85) {
				mancounthigh++;
				age45_50mancounthigh++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&waist<85) {
				mancountnormal++;
				age50_55mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&waist>=85) {
				mancounthigh++;
				age50_55mancounthigh++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&waist<85) {
				mancountnormal++;
				age55_60mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&waist>=85) {
				mancounthigh++;
				age55_60mancounthigh++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&waist<85) {
				mancountnormal++;
				age55_60mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&waist>=85) {
				mancounthigh++;
				age60mancounthigh++;
			}
			
			//
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&waist<80) {
				womancountnormal++;
				age30womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&waist>=80) {
				womancounthigh++;
				age30womancounthigh++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&waist<80) {
				womancountnormal++;
				age30_40womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&waist>=80) {
				womancounthigh++;
				age30_40womancounthigh++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&waist<80) {
				womancountnormal++;
				age40_45womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&waist>=80) {
				womancounthigh++;
				age40_45womancounthigh++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&waist<80) {
				womancountnormal++;
				age45_50womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&waist>=80) {
				womancounthigh++;
				age45_50womancounthigh++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&waist<80) {
				womancountnormal++;
				age50_55womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&waist>=80) {
				womancounthigh++;
				age50_55womancounthigh++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&waist<80) {
				womancountnormal++;
				age55_60womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&waist>=80) {
				womancounthigh++;
				age55_60womancounthigh++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&waist<85) {
				womancountnormal++;
				age55_60womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&waist>=85) {
				womancounthigh++;
				age60womancounthigh++;
			}

		}
		pdfwaist.setMancounthigh(mancounthigh);
		pdfwaist.setMancountnormal(mancountnormal);
		pdfwaist.setWomancounthigh(womancounthigh);
		pdfwaist.setWomancountnormal(womancountnormal);
		pdfwaist.setAge30mancounthigh(age30mancounthigh);
		pdfwaist.setAge30mancountnormal(age30mancountnormal);
		pdfwaist.setAge30womancounthigh(age30womancounthigh);
		pdfwaist.setAge30womancountnormal(age30womancountnormal);
		pdfwaist.setAge30_40mancounthigh(age30_40mancounthigh);
		pdfwaist.setAge30_40mancountnormal(age30_40mancountnormal);
		pdfwaist.setAge30_40womancounthigh(age30_40womancounthigh);
		pdfwaist.setAge30_40womancountnormal(age30_40womancountnormal);
		pdfwaist.setAge40_45mancounthigh(age40_45mancounthigh);
		pdfwaist.setAge40_45mancountnormal(age40_45mancountnormal);
		pdfwaist.setAge40_45womancounthigh(age40_45womancounthigh);
		pdfwaist.setAge40_45womancountnormal(age40_45womancountnormal);
		pdfwaist.setAge45_50mancounthigh(age45_50mancounthigh);
		pdfwaist.setAge45_50mancountnormal(age45_50mancountnormal);
		pdfwaist.setAge45_50womancounthigh(age45_50womancounthigh);
		pdfwaist.setAge45_50womancountnormal(age45_50womancountnormal);
		pdfwaist.setAge50_55mancounthigh(age50_55mancounthigh);
		pdfwaist.setAge50_55mancountnormal(age50_55mancountnormal);
		pdfwaist.setAge50_55womancounthigh(age50_55womancounthigh);
		pdfwaist.setAge50_55womancountnormal(age50_55womancountnormal);
		pdfwaist.setAge55_60mancounthigh(age55_60mancounthigh);
		pdfwaist.setAge55_60mancountnormal(age55_60mancountnormal);
		pdfwaist.setAge55_60womancounthigh(age55_60womancounthigh);
		pdfwaist.setAge55_60womancountnormal(age55_60womancountnormal);
		pdfwaist.setAge60mancounthigh(age60mancounthigh);
		pdfwaist.setAge60mancountnormal(age60mancountnormal);
		pdfwaist.setAge60womancounthigh(age60womancounthigh);
		pdfwaist.setAge60womancountnormal(age60womancountnormal);
		return pdfwaist;
	}
	
	
	public static YhExportPdfHypertension getresulthypertension(List<YhExportHealthAssess> baseassess) {
		YhExportPdfHypertension pdfhypertension=new YhExportPdfHypertension();
		int mancountnormal=0;
		int manounthighside=0;
		int manounthigh=0;
		int womancountnormal=0;
		int womanounthighside=0;
		int womanounthigh=0;	
		int age30mancountnormal=0;
		int age30manounthighside=0;
		int age30manounthigh=0;
		int age30womancountnormal=0;
		int age30womanounthighside=0;
		int age30womanounthigh=0;
		int age30_40mancountnormal=0;
		int age30_40manounthighside=0;
		int age30_40manounthigh=0;
		int age30_40womancountnormal=0;
		int age30_40womanounthighside=0;
		int age30_40womanounthigh=0;	
		int age40_45mancountnormal=0;
		int age40_45manounthighside=0;
		int age40_45manounthigh=0;
		int age40_45womancountnormal=0;
		int age40_45womanounthighside=0;
		int age40_45womanounthigh=0;	
		int age45_50mancountnormal=0;
		int age45_50manounthighside=0;
		int age45_50manounthigh=0;
		int age45_50womancountnormal=0;
		int age45_50womanounthighside=0;
		int age45_50womanounthigh=0;	
		int age50_55mancountnormal=0;
		int age50_55manounthighside=0;
		int age50_55manounthigh=0;
		int age50_55womancountnormal=0;
		int age50_55womanounthighside=0;
		int age50_55womanounthigh=0;	
		int age55_60mancountnormal=0;
		int age55_60manounthighside=0;
		int age55_60manounthigh=0;
		int age55_60womancountnormal=0;
		int age55_60womanounthighside=0;
		int age55_60womanounthigh=0;	
		int age60mancountnormal=0;
		int age60manounthighside=0;
		int age60manounthigh=0;
		int age60womancountnormal=0;
		int age60womanounthighside=0;
		int age60womanounthigh=0;
		//收缩压
		double sbp=0.0;
		//舒张压
		double dbp=0.0;
		for (int i = 0; i < baseassess.size(); i++) {
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSbp()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSbp());
					sbp=Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDbp()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDbp());
					dbp=Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&sbp<120&&dbp<80) {
				mancountnormal++;
				age30mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				manounthighside++;
				age30manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&(sbp>=140||dbp>=90)) {
				manounthigh++;
				age30manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&sbp<120&&dbp<80) {
				mancountnormal++;
				age30_40mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				manounthighside++;
				age30_40manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&(sbp>=140||dbp>=90)) {
				manounthigh++;
				age30_40manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&sbp<120&&dbp<80) {
				mancountnormal++;
				age40_45mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				manounthighside++;
				age40_45manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&(sbp>=140||dbp>=90)) {
				manounthigh++;
				age40_45manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&sbp<120&&dbp<80) {
				mancountnormal++;
				age45_50mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				manounthighside++;
				age45_50manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&(sbp>=140||dbp>=90)) {
				manounthigh++;
				age45_50manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&sbp<120&&dbp<80) {
				mancountnormal++;
				age50_55mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				manounthighside++;
				age50_55manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&(sbp>=140||dbp>=90)) {
				manounthigh++;
				age50_55manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&sbp<120&&dbp<80) {
				mancountnormal++;
				age55_60mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				manounthighside++;
				age55_60manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&(sbp>=140||dbp>=90)) {
				manounthigh++;
				age55_60manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&sbp<120&&dbp<80) {
				mancountnormal++;
				age60mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				manounthighside++;
				age60manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&(sbp>=140||dbp>=90)) {
				manounthigh++;
				age60manounthigh++;
			}
			//
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&sbp<120&&dbp<80) {
				womancountnormal++;
				age30womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				womanounthighside++;
				age30womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&(sbp>=140||dbp>=90)) {
				womanounthigh++;
				age30womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&sbp<120&&dbp<80) {
				womancountnormal++;
				age30_40womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				womanounthighside++;
				age30_40womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&(sbp>=140||dbp>=90)) {
				womanounthigh++;
				age30_40womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&sbp<120&&dbp<80) {
				womancountnormal++;
				age40_45womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				womanounthighside++;
				age40_45womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&(sbp>=140||dbp>=90)) {
				womanounthigh++;
				age40_45womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&sbp<120&&dbp<80) {
				womancountnormal++;
				age45_50womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				womanounthighside++;
				age45_50womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&(sbp>=140||dbp>=90)) {
				womanounthigh++;
				age45_50womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&sbp<120&&dbp<80) {
				womancountnormal++;
				age50_55womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				womanounthighside++;
				age50_55womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&(sbp>=140||dbp>=90)) {
				womanounthigh++;
				age50_55womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&sbp<120&&dbp<80) {
				womancountnormal++;
				age55_60womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				womanounthighside++;
				age55_60womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&(sbp>=140||dbp>=90)) {
				womanounthigh++;
				age55_60womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&sbp<120&&dbp<80) {
				womancountnormal++;
				age60womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&(sbp>=120&&sbp<140 || dbp>=80&&dbp<90)) {
				womanounthighside++;
				age60womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&(sbp>=140||dbp>=90)) {
				womanounthigh++;
				age60womanounthigh++;
			}
		}
		pdfhypertension.setMancountnormal(mancountnormal);
		pdfhypertension.setManounthigh(manounthigh);
		pdfhypertension.setManounthighside(manounthighside);
		pdfhypertension.setWomancountnormal(womancountnormal);
		pdfhypertension.setWomanounthigh(womanounthigh);
		pdfhypertension.setWomanounthighside(womanounthighside);
		pdfhypertension.setAge30mancountnormal(age30mancountnormal);
		pdfhypertension.setAge30manounthigh(age30manounthigh);
		pdfhypertension.setAge30manounthighside(age30manounthighside);
		pdfhypertension.setAge30womancountnormal(age30womancountnormal);
		pdfhypertension.setAge30womanounthigh(age30womanounthigh);
		pdfhypertension.setAge30womanounthighside(age30womanounthighside);
		pdfhypertension.setAge30_40mancountnormal(age30_40mancountnormal);
		pdfhypertension.setAge30_40manounthigh(age30_40manounthigh);
		pdfhypertension.setAge30_40manounthighside(age30_40manounthighside);
		pdfhypertension.setAge30_40womancountnormal(age30_40womancountnormal);
		pdfhypertension.setAge30_40womanounthigh(age30_40womanounthigh);
		pdfhypertension.setAge30_40womanounthighside(age30_40womanounthighside);
		pdfhypertension.setAge40_45mancountnormal(age40_45mancountnormal);
		pdfhypertension.setAge40_45manounthigh(age40_45manounthigh);
		pdfhypertension.setAge40_45manounthighside(age40_45manounthighside);
		pdfhypertension.setAge40_45womancountnormal(age40_45womancountnormal);
		pdfhypertension.setAge40_45womanounthigh(age40_45womanounthigh);
		pdfhypertension.setAge40_45womanounthighside(age40_45womanounthighside);
		pdfhypertension.setAge45_50mancountnormal(age45_50mancountnormal);
		pdfhypertension.setAge45_50manounthigh(age45_50manounthigh);
		pdfhypertension.setAge45_50manounthighside(age45_50manounthighside);
		pdfhypertension.setAge45_50womancountnormal(age45_50womancountnormal);
		pdfhypertension.setAge45_50womanounthigh(age45_50womanounthigh);
		pdfhypertension.setAge45_50womanounthighside(age45_50womanounthighside);
		pdfhypertension.setAge50_55mancountnormal(age50_55mancountnormal);
		pdfhypertension.setAge50_55manounthigh(age50_55manounthigh);
		pdfhypertension.setAge50_55manounthighside(age50_55manounthighside);
		pdfhypertension.setAge50_55womancountnormal(age50_55womancountnormal);
		pdfhypertension.setAge50_55womanounthigh(age50_55womanounthigh);
		pdfhypertension.setAge50_55womanounthighside(age50_55womanounthighside);
		pdfhypertension.setAge55_60mancountnormal(age55_60mancountnormal);
		pdfhypertension.setAge55_60manounthigh(age55_60manounthigh);
		pdfhypertension.setAge55_60manounthighside(age55_60manounthighside);
		pdfhypertension.setAge55_60womancountnormal(age55_60womancountnormal);
		pdfhypertension.setAge55_60womanounthigh(age55_60womanounthigh);
		pdfhypertension.setAge55_60womanounthighside(age55_60womanounthighside);
		pdfhypertension.setAge60mancountnormal(age60mancountnormal);
		pdfhypertension.setAge60manounthigh(age60manounthigh);
		pdfhypertension.setAge60manounthighside(age60manounthighside);
		pdfhypertension.setAge60womancountnormal(age60womancountnormal);
		pdfhypertension.setAge60womanounthigh(age60womanounthigh);
		pdfhypertension.setAge60womanounthighside(age60womanounthighside);
		return pdfhypertension;
	}
	
	public static YhExportPdfSugar getresultsugar(List<YhExportHealthAssess> baseassess) {
		YhExportPdfSugar sugar=new YhExportPdfSugar();
		int mancountnormal=0;
		int manounthighside=0;
		int manounthigh=0;
		int womancountnormal=0;
		int womanounthighside=0;
		int womanounthigh=0;	
		int age30mancountnormal=0;
		int age30manounthighside=0;
		int age30manounthigh=0;
		int age30womancountnormal=0;
		int age30womanounthighside=0;
		int age30womanounthigh=0;
		int age30_40mancountnormal=0;
		int age30_40manounthighside=0;
		int age30_40manounthigh=0;
		int age30_40womancountnormal=0;
		int age30_40womanounthighside=0;
		int age30_40womanounthigh=0;	
		int age40_45mancountnormal=0;
		int age40_45manounthighside=0;
		int age40_45manounthigh=0;
		int age40_45womancountnormal=0;
		int age40_45womanounthighside=0;
		int age40_45womanounthigh=0;	
		int age45_50mancountnormal=0;
		int age45_50manounthighside=0;
		int age45_50manounthigh=0;
		int age45_50womancountnormal=0;
		int age45_50womanounthighside=0;
		int age45_50womanounthigh=0;	
		int age50_55mancountnormal=0;
		int age50_55manounthighside=0;
		int age50_55manounthigh=0;
		int age50_55womancountnormal=0;
		int age50_55womanounthighside=0;
		int age50_55womanounthigh=0;	
		int age55_60mancountnormal=0;
		int age55_60manounthighside=0;
		int age55_60manounthigh=0;
		int age55_60womancountnormal=0;
		int age55_60womanounthighside=0;
		int age55_60womanounthigh=0;	
		int age60mancountnormal=0;
		int age60manounthighside=0;
		int age60manounthigh=0;
		int age60womancountnormal=0;
		int age60womanounthighside=0;
		int age60womanounthigh=0;
		//血糖
		double FBG=0.0;
		for (int i = 0; i < baseassess.size(); i++) {
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFbg()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFbg());
					FBG=Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&FBG>=3.9&&FBG<5.6) {
				mancountnormal++;
				age30mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&FBG>=5.6&&FBG<7.0) {
				manounthighside++;
				age30manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&FBG>=7.0) {
				manounthigh++;
				age30manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&FBG>=3.9&&FBG<5.6) {
				mancountnormal++;
				age30_40mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&FBG>=5.6&&FBG<7.0) {
				manounthighside++;
				age30_40manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&FBG>=7.0) {
				manounthigh++;
				age30_40manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&FBG>=3.9&&FBG<5.6) {
				mancountnormal++;
				age40_45mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&FBG>=5.6&&FBG<7.0) {
				manounthighside++;
				age40_45manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&FBG>=7.0) {
				manounthigh++;
				age40_45manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&FBG>=3.9&&FBG<5.6) {
				mancountnormal++;
				age45_50mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&FBG>=5.6&&FBG<7.0) {
				manounthighside++;
				age45_50manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&FBG>=7.0) {
				manounthigh++;
				age45_50manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&FBG>=3.9&&FBG<5.6) {
				mancountnormal++;
				age50_55mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&FBG>=5.6&&FBG<7.0) {
				manounthighside++;
				age50_55manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&FBG>=7.0) {
				manounthigh++;
				age50_55manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&FBG>=3.9&&FBG<5.6) {
				mancountnormal++;
				age55_60mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&FBG>=5.6&&FBG<7.0) {
				manounthighside++;
				age55_60manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&FBG>=7.0) {
				manounthigh++;
				age55_60manounthigh++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&FBG>=3.9&&FBG<5.6) {
				mancountnormal++;
				age60mancountnormal++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&FBG>=5.6&&FBG<7.0) {
				manounthighside++;
				age60manounthighside++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&FBG>=7.0) {
				manounthigh++;
				age60manounthigh++;
			}
			//
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&FBG>=3.9&&FBG<5.6) {
				womancountnormal++;
				age30womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&FBG>=5.6&&FBG<7.0) {
				womanounthighside++;
				age30womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&FBG>=7.0) {
				womanounthigh++;
				age30womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&FBG>=3.9&&FBG<5.6) {
				womancountnormal++;
				age30_40womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&FBG>=5.6&&FBG<7.0) {
				womanounthighside++;
				age30_40womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<40&&baseassess.get(i).getAge()>=30&&FBG>=7.0) {
				womanounthigh++;
				age30_40womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&FBG>=3.9&&FBG<5.6) {
				womancountnormal++;
				age40_45womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&FBG>=5.6&&FBG<7.0) {
				womanounthighside++;
				age40_45womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<45&&baseassess.get(i).getAge()>=40&&FBG>=7.0) {
				womanounthigh++;
				age40_45womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&FBG>=3.9&&FBG<5.6) {
				womancountnormal++;
				age45_50womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&FBG>=5.6&&FBG<7.0) {
				womanounthighside++;
				age45_50womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<50&&baseassess.get(i).getAge()>=45&&FBG>=7.0) {
				womanounthigh++;
				age45_50womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&FBG>=3.9&&FBG<5.6) {
				womancountnormal++;
				age50_55womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&FBG>=5.6&&FBG<7.0) {
				womanounthighside++;
				age50_55womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<55&&baseassess.get(i).getAge()>=50&&FBG>=7.0) {
				womanounthigh++;
				age50_55womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&FBG>=3.9&&FBG<5.6) {
				womancountnormal++;
				age55_60womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&FBG>=5.6&&FBG<7.0) {
				womanounthighside++;
				age55_60womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<60&&baseassess.get(i).getAge()>=55&&FBG>=7.0) {
				womanounthigh++;
				age55_60womanounthigh++;
			}
			
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&FBG>=3.9&&FBG<5.6) {
				womancountnormal++;
				age60womancountnormal++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&FBG>=5.6&&FBG<7.0) {
				womanounthighside++;
				age60womanounthighside++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&FBG>=7.0) {
				womanounthigh++;
				age60womanounthigh++;
			}
		}
		sugar.setMancountnormal(mancountnormal);
		sugar.setManounthigh(manounthigh);
		sugar.setManounthighside(manounthighside);
		sugar.setWomancountnormal(womancountnormal);
		sugar.setWomanounthigh(womanounthigh);
		sugar.setWomanounthighside(womanounthighside);
		sugar.setAge30mancountnormal(age30mancountnormal);
		sugar.setAge30manounthigh(age30manounthigh);
		sugar.setAge30manounthighside(age30manounthighside);
		sugar.setAge30womancountnormal(age30womancountnormal);
		sugar.setAge30womanounthigh(age30womanounthigh);
		sugar.setAge30womanounthighside(age30womanounthighside);
		sugar.setAge30_40mancountnormal(age30_40mancountnormal);
		sugar.setAge30_40manounthigh(age30_40manounthigh);
		sugar.setAge30_40manounthighside(age30_40manounthighside);
		sugar.setAge30_40womancountnormal(age30_40womancountnormal);
		sugar.setAge30_40womanounthigh(age30_40womanounthigh);
		sugar.setAge30_40womanounthighside(age30_40womanounthighside);
		sugar.setAge40_45mancountnormal(age40_45mancountnormal);
		sugar.setAge40_45manounthigh(age40_45manounthigh);
		sugar.setAge40_45manounthighside(age40_45manounthighside);
		sugar.setAge40_45womancountnormal(age40_45womancountnormal);
		sugar.setAge40_45womanounthigh(age40_45womanounthigh);
		sugar.setAge40_45womanounthighside(age40_45womanounthighside);
		sugar.setAge45_50mancountnormal(age45_50mancountnormal);
		sugar.setAge45_50manounthigh(age45_50manounthigh);
		sugar.setAge45_50manounthighside(age45_50manounthighside);
		sugar.setAge45_50womancountnormal(age45_50womancountnormal);
		sugar.setAge45_50womanounthigh(age45_50womanounthigh);
		sugar.setAge45_50womanounthighside(age45_50womanounthighside);
		sugar.setAge50_55mancountnormal(age50_55mancountnormal);
		sugar.setAge50_55manounthigh(age50_55manounthigh);
		sugar.setAge50_55manounthighside(age50_55manounthighside);
		sugar.setAge50_55womancountnormal(age50_55womancountnormal);
		sugar.setAge50_55womanounthigh(age50_55womanounthigh);
		sugar.setAge50_55womanounthighside(age50_55womanounthighside);
		sugar.setAge55_60mancountnormal(age55_60mancountnormal);
		sugar.setAge55_60manounthigh(age55_60manounthigh);
		sugar.setAge55_60manounthighside(age55_60manounthighside);
		sugar.setAge55_60womancountnormal(age55_60womancountnormal);
		sugar.setAge55_60womanounthigh(age55_60womanounthigh);
		sugar.setAge55_60womanounthighside(age55_60womanounthighside);
		sugar.setAge60mancountnormal(age60mancountnormal);
		sugar.setAge60manounthigh(age60manounthigh);
		sugar.setAge60manounthighside(age60manounthighside);
		sugar.setAge60womancountnormal(age60womancountnormal);
		sugar.setAge60womanounthigh(age60womanounthigh);
		sugar.setAge60womanounthighside(age60womanounthighside);
		return sugar;
	}
	
	
	
	public static YhExportPdfBloodFat getresultbloofat(List<YhExportHealthAssess> baseassess) {
		YhExportPdfBloodFat bloodfat=new YhExportPdfBloodFat();
		int mancholesterolcount=0;
		int manhdlccount=0;
		int manldlccount=0;
		int manTGcount=0;
		int manBFcount=0;
		int womancholesterolcount=0;
		int womanhdlccount=0;
		int womanldlccount=0;
		int womanTGcount=0;
		int womanBFcount=0;
		double cholesterol=0;
		double TG=0;
		double hdlc=0;
		double ldlc=0;
		int mancount=0;
		int womancount=0;
		int BF=0;
		for (int i = 0; i < baseassess.size(); i++) {
			if(baseassess.get(i).getGender()==1) {
				mancount++;
			}
			if(baseassess.get(i).getGender()==2) {
				womancount++;
			}
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getCholesterol()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getCholesterol());
					cholesterol=Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getTg()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getTg());
					TG=Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHdlc()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHdlc());
					hdlc=Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getLdlc()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getLdlc());
					ldlc=Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBf()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBf());
					BF=Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
			}
			if(baseassess.get(i).getGender()==1&&cholesterol>=5.18) {
				mancholesterolcount++;
			}
			if(baseassess.get(i).getGender()==1&&TG>=1.7) {
				manTGcount++;
			}
			if(baseassess.get(i).getGender()==1&&hdlc<1.04) {
				manhdlccount++;
			}
			if(baseassess.get(i).getGender()==1&&ldlc>=3.37) {
				manldlccount++;
			}
			if(baseassess.get(i).getGender()==1&&BF==1) {
				manBFcount++;
			}
			if(baseassess.get(i).getGender()==2&&cholesterol>=5.18) {
				womancholesterolcount++;
			}
			if(baseassess.get(i).getGender()==2&&TG>=1.7) {
				womanTGcount++;
			}
			if(baseassess.get(i).getGender()==2&&hdlc<1.04) {
				womanhdlccount++;
			}
			if(baseassess.get(i).getGender()==2&&ldlc>=3.37) {
				womanldlccount++;
			}
			if(baseassess.get(i).getGender()==2&&BF==1) {
				womanBFcount++;
			}
		}
		bloodfat.setManBFcount(manBFcount);
		bloodfat.setMancholesterolcount(mancholesterolcount);
		bloodfat.setManhdlccount(manhdlccount);
		bloodfat.setManldlccount(manldlccount);
		bloodfat.setManTGcount(manTGcount);
		bloodfat.setWomanBFcount(womanBFcount);
		bloodfat.setWomancholesterolcount(womancholesterolcount);
		bloodfat.setWomanhdlccount(womanhdlccount);
		bloodfat.setWomanldlccount(womanldlccount);
		bloodfat.setWomanTGcount(womanTGcount);
		bloodfat.setMancount(mancount);
		bloodfat.setWomancount(womancount);
		return bloodfat;
	}
	
	
	public static YhExportPdfFood getresultfood(List<YhExportHealthAssess> baseassess) {
		YhExportPdfFood food=new YhExportPdfFood();
		int manfood_purine=0;
		int manFruit=0;
		int manmilk=0;
		int womanfood_purine=0;
		int womanFruit=0;
		int womanmilk=0;
		int mancount=0;
		int womancount=0;
		int allcount=0;
		int food_purine=0;
		int Fruit=0;
		int milk=0;
		for (int i = 0; i < baseassess.size(); i++) {
			if(baseassess.get(i).getGender()==1) {
				mancount++;
			}
			if(baseassess.get(i).getGender()==2) {
				womancount++;
			}
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFoodPurine()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFoodPurine());
					food_purine=Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFood()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFood());
					Fruit=Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getMilk()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getMilk());
					milk=Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
			}
			if(baseassess.get(i).getGender()==1&&manfood_purine==1) {
				manfood_purine++;
			}
			if(baseassess.get(i).getGender()==1&&Fruit==0) {
				manFruit++;
			}
			if(baseassess.get(i).getGender()==1&&milk==0) {
				manmilk++;
			}
			if(baseassess.get(i).getGender()==1&&womanfood_purine==1) {
				womanfood_purine++;
			}
			if(baseassess.get(i).getGender()==2&&Fruit==0) {
				womanFruit++;
			}
			if(baseassess.get(i).getGender()==2&&milk==0) {
				womanmilk++;
			}
		}
		food.setMancount(mancount);
		food.setManfood_purine(manfood_purine);
		food.setManFruit(manFruit);
		food.setManmilk(manmilk);
		food.setWomancount(womancount);
		food.setWomanfood_purine(womanfood_purine);
		food.setWomanFruit(womanFruit);
		food.setWomanmilk(womanmilk);
		return food;
	}
	
	
	public static YhExportPdfDrink getresultdrink(List<YhExportHealthAssess> baseassess) {
		YhExportPdfDrink drink=new YhExportPdfDrink();
		int drinking_high_man=0;
		int drinking_high_woman=0;
		int drinking_in_man=0;
		int drinking_in_woman=0;
		int drinking_low_man=0;
		int drinking_low_woman=0;
		int drinking_man=0;
		int drinking_woman=0;
		int drinks=0;
		for (int i = 0; i < baseassess.size(); i++) {
			if(baseassess.get(i).getGender()==1) {
				drinking_man++;
			}
			if(baseassess.get(i).getGender()==2) {
				drinking_woman++;
			}
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDrink()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDrink());
					drinks = Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
			}
			if(baseassess.get(i).getGender()==1&&drinks==0) {
				drinking_low_man++;
			}
			if(baseassess.get(i).getGender()==1&&drinks==1) {
				drinking_in_man++;
			}
			if(baseassess.get(i).getGender()==1&&drinks==2) {
				drinking_high_man++;
			}
			if(baseassess.get(i).getGender()==2&&drinks==0) {
				drinking_low_woman++;
			}
			if(baseassess.get(i).getGender()==2&&drinks==1) {
				drinking_in_woman++;
			}
			if(baseassess.get(i).getGender()==2&&drinks==2) {
				drinking_high_woman++;
			}
		}
		drink.setDrinking_high_man(drinking_high_man);
		drink.setDrinking_high_woman(drinking_high_woman);
		drink.setDrinking_in_man(drinking_in_man);
		drink.setDrinking_in_woman(drinking_in_woman);
		drink.setDrinking_low_man(drinking_low_man);
		drink.setDrinking_low_woman(drinking_low_woman);
		drink.setDrinking_man(drinking_man);
		drink.setDrinking_woman(drinking_woman);
		return drink;
	}
	
	public static YhExportPdfSmoke getresultsmoke(List<YhExportHealthAssess> baseassess) {
		YhExportPdfSmoke smoke=new YhExportPdfSmoke();
		int smoke_man=0;
		int smoke_woman=0;
		int smoke_no_man=0;
		int smoke_no_woman=0;
		int man=0;
		int woman=0;
		int secondSmokeMan=0;
		int secondSmokeWoman=0;
		int nowsmoke=0;
		int nowsencodsmoke=0;
		for (int i = 0; i < baseassess.size(); i++) {
			if(baseassess.get(i).getGender()==1) {
				man++;
			}
			if(baseassess.get(i).getGender()==2) {
				woman++;
			}
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSmoke()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSmoke());
					nowsmoke = Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSmokeSencod()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSmokeSencod());
					nowsencodsmoke = Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
			}
			if(baseassess.get(i).getGender()==1&&nowsmoke==1) {
				smoke_man++;
			}
			if(baseassess.get(i).getGender()==1&&nowsmoke==0) {
				smoke_no_man++;
			}
			if(baseassess.get(i).getGender()==1&&nowsencodsmoke==1) {
				secondSmokeMan++;
			}
			if(baseassess.get(i).getGender()==1&&nowsencodsmoke==2) {
				secondSmokeMan++;
			}
			
			if(baseassess.get(i).getGender()==2&&nowsmoke==1) {
				smoke_woman++;
			}
			if(baseassess.get(i).getGender()==2&&nowsmoke==0) {
				smoke_no_woman++;
			}
			if(baseassess.get(i).getGender()==2&&nowsencodsmoke==1) {
				secondSmokeWoman++;
			}
			if(baseassess.get(i).getGender()==2&&nowsencodsmoke==2) {
				secondSmokeWoman++;
			}
		}
		smoke.setMan(man);
		smoke.setSecondSmokeMan(secondSmokeMan);
		smoke.setSecondSmokeWoman(secondSmokeWoman);
		smoke.setSmoke_man(smoke_man);
		smoke.setSmoke_no_man(smoke_no_man);
		smoke.setSmoke_no_woman(smoke_no_woman);
		smoke.setSmoke_woman(smoke_woman);
		smoke.setWoman(woman);
		return smoke;
	}
	
	public static YhExportPdfActive getresultactitv(List<YhExportHealthAssess> baseassess) {
		YhExportPdfActive active=new YhExportPdfActive();
		int Age30mansufficient=0;
		int Age30womansufficient=0;
		int Age30mannosufficient=0;
		int Age30womannosufficient=0;
		int Age30_40mansufficient=0;
		int Age30_40womansufficient=0;
		int Age30_40mannosufficient=0;
		int Age30_40womannosufficient=0;
		int Age40_45mansufficient=0;
		int Age40_45womansufficient=0;
		int Age40_45mannosufficient=0;
		int Age40_45womannosufficient=0;
		int Age45_50mansufficient=0;
		int Age45_50womansufficient=0;
		int Age45_50mannosufficient=0;
		int Age45_50womannosufficient=0;
		int Age50_55mansufficient=0;
		int Age50_55womansufficient=0;
		int Age50_55mannosufficient=0;
		int Age50_55womannosufficient=0;
		int Age55_60mansufficient=0;
		int Age55_60womansufficient=0;
		int Age55_60mannosufficient=0;
		int Age55_60womannosufficient=0;
		int Age60mansufficient=0;
		int Age60womansufficient=0;
		int Age60mannosufficient=0;
		int Age60womannosufficient=0;
		int mansufficient=0;
		int mannosufficient=0;
		int womansufficient=0;
		int womannosufficient=0;
		int activ=0;
		for (int i = 0; i < baseassess.size(); i++) {
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSport()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSport());
					activ = Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&activ==1) {
				Age30mansufficient++;
				mansufficient++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()<30&&activ==0) {
				Age30mannosufficient++;
				mannosufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&activ==1) {
				Age30womansufficient++;
				womansufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()<30&&activ==0) {
				Age30womannosufficient++;
				womannosufficient++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&activ==1) {
				Age30_40mansufficient++;
				mansufficient++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&activ==0) {
				Age30_40mannosufficient++;
				mannosufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&activ==1) {
				Age30_40womansufficient++;
				womansufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=30&&baseassess.get(i).getAge()<40&&activ==0) {
				Age30_40womannosufficient++;
				womannosufficient++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&activ==1) {
				Age40_45mansufficient++;
				mansufficient++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&activ==0) {
				Age40_45mannosufficient++;
				mannosufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&activ==1) {
				Age40_45womansufficient++;
				womansufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=40&&baseassess.get(i).getAge()<45&&activ==0) {
				Age40_45womannosufficient++;
				womannosufficient++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&activ==1) {
				Age45_50mansufficient++;
				mansufficient++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&activ==0) {
				Age45_50mannosufficient++;
				mannosufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&activ==1) {
				Age45_50womansufficient++;
				womansufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=45&&baseassess.get(i).getAge()<50&&activ==0) {
				Age45_50womannosufficient++;
				womannosufficient++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&activ==1) {
				Age50_55mansufficient++;
				mansufficient++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&activ==0) {
				Age50_55mannosufficient++;
				mannosufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&activ==1) {
				Age50_55womansufficient++;
				womansufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=50&&baseassess.get(i).getAge()<55&&activ==0) {
				Age50_55womannosufficient++;
				womannosufficient++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&activ==1) {
				Age55_60mansufficient++;
				mansufficient++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&activ==0) {
				Age55_60mannosufficient++;
				mannosufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&activ==1) {
				Age55_60womansufficient++;
				womansufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=55&&baseassess.get(i).getAge()<60&&activ==0) {
				Age55_60womannosufficient++;
				womannosufficient++;
			}
			
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&activ==1) {
				Age60mansufficient++;
				mansufficient++;
			}
			if(baseassess.get(i).getGender()==1&&baseassess.get(i).getAge()>=60&&activ==0) {
				Age60mannosufficient++;
				mannosufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&activ==1) {
				Age60womansufficient++;
				womansufficient++;
			}
			if(baseassess.get(i).getGender()==2&&baseassess.get(i).getAge()>=60&&activ==0) {
				Age60womannosufficient++;
				womannosufficient++;
			}
		}
		active.setMannosufficient(mannosufficient);
		active.setMansufficient(mansufficient);
		active.setWomannosufficient(womannosufficient);
		active.setWomansufficient(womansufficient);
		active.setAge30mansufficient(Age30mansufficient);
		active.setAge30mannosufficient(Age30mannosufficient);
		active.setAge30womansufficient(Age30womansufficient);
		active.setAge30womannosufficient(Age30womannosufficient);
		
		active.setAge30_40mansufficient(Age30_40mansufficient);
		active.setAge30_40mannosufficient(Age30_40mannosufficient);
		active.setAge30_40womansufficient(Age30_40womansufficient);
		active.setAge30_40womannosufficient(Age30_40womannosufficient);
		
		active.setAge40_45mansufficient(Age40_45mansufficient);
		active.setAge40_45mannosufficient(Age40_45mannosufficient);
		active.setAge40_45womansufficient(Age40_45womansufficient);
		active.setAge40_45womannosufficient(Age40_45womannosufficient);
		
		active.setAge45_50mansufficient(Age45_50mansufficient);
		active.setAge45_50mannosufficient(Age45_50mannosufficient);
		active.setAge45_50womansufficient(Age45_50womansufficient);
		active.setAge45_50womannosufficient(Age45_50womannosufficient);
		
		active.setAge50_55mansufficient(Age50_55mansufficient);
		active.setAge50_55mannosufficient(Age50_55mannosufficient);
		active.setAge50_55womansufficient(Age50_55womansufficient);
		active.setAge50_55womannosufficient(Age50_55womannosufficient);
		
		active.setAge55_60mansufficient(Age55_60mansufficient);
		active.setAge55_60mannosufficient(Age55_60mannosufficient);
		active.setAge55_60womansufficient(Age55_60womansufficient);
		active.setAge55_60womannosufficient(Age55_60womannosufficient);
		
		active.setAge60mansufficient(Age60mansufficient);
		active.setAge60mannosufficient(Age60mannosufficient);
		active.setAge60womansufficient(Age60womansufficient);
		active.setAge60womannosufficient(Age60womannosufficient);
		return active;
	}
	
	
	
	public static YhExportPdfFactors getresultfactors(List<YhExportHealthAssess> baseassess) {
		YhExportPdfFactors factors=new YhExportPdfFactors();
		int nofactors=0;
		int onefactors=0;
		int twofactors=0;
		int threefactors=0;
		int fourfactors=0;
		int fivefactors=0;
		int allpeople=0;
		int drinkcount=0;
		int FBGcount=0;
		int bloodpressurecount=0;
		int waistcount=0;
		int BMIcount=0;
		int smokecount=0;
		int sportcount=0;
		int foodcount=0;
		int BFcount=0;
		int drink=0;
		double FBG=0;
		double sbp=0;
		double dbp=0;
		double waist=0;
		double BMI=0;
		int smoke=0;
		int sport=0;
		int food=0;
		int BF=0;
		double cholesterol=0;
		double TG=0;
		double ldlc=0;
		double hdlc=0;
		for (int i = 0; i < baseassess.size(); i++) {
			int focto=0;
			for (int j = 0; j < baseassess.get(i).getYhDiseaseRiskResult2s().size();j++) {
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDrink()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDrink());
					drink = Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFbg()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFbg());
					FBG = Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSbp()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSbp());
					sbp = Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDbp()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getDbp());
					dbp =Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getWaist()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getWaist());
					waist = Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBmi()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBmi());
					BMI = Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSmoke()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSmoke());
					smoke = Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSport()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getSport());
					sport = Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFood()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getFood());
					food = Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBf()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getBf());
					BF = Integer.valueOf(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getCholesterol()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getCholesterol());
					cholesterol = Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getTg()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getTg());
					TG = Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getLdlc()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getLdlc());
					ldlc = Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
				if( baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHdlc()!=null) {
					Object document = Configuration.defaultConfiguration().jsonProvider().parse(baseassess.get(i).getYhDiseaseRiskResult2s().get(j).getHdlc());
					hdlc = Double.parseDouble(JsonPath.read(document, "$.now_value"));
				}
			}
			if(drink==2) {
				focto++;
				drinkcount++;
			}
			if(FBG>=5.6) {
				focto++;
				FBGcount++;
			}
			if(sbp>=120 || dbp>=80) {
				focto++;
				bloodpressurecount++;
			}
			if(waist>85&&baseassess.get(i).getGender()==1) {
				focto++;
				waistcount++;
			}
			if(waist>80&&baseassess.get(i).getGender()==2) {
				focto++;
				waistcount++;
			}
			if(BMI>=24) {
				focto++;
				BMIcount++;
			}
			if(smoke==1) {
				focto++;
				smokecount++;
			}
			if(sport==0) {
				focto++;
				sportcount++;
			}
			if(food==0) {
				focto++;
				foodcount++;
			}
			if((double)cholesterol >= 5.1799999999999997D || (double)TG >= 1.7D || (double)ldlc >= 3.3700000000000001D || (double)hdlc < 1.04D) {
				focto++;
				BFcount++;
			}
			if(focto==0) {
				nofactors++;
			}
			if(focto==1) {
				onefactors++;
			}
			if(focto==2) {
				twofactors++;
			}
			if(focto==3) {
				threefactors++;
			}
			if(focto==4) {
				fourfactors++;
			}
			if(focto>=5) {
				fivefactors++;
			}
		}
		factors.setBFcount(BFcount);
		factors.setBloodpressurecount(bloodpressurecount);
		factors.setBMIcount(BMIcount);
		factors.setDrinkcount(drinkcount);
		factors.setFBGcount(FBGcount);
		factors.setFivefactors(fivefactors);
		factors.setFoodcount(foodcount);
		factors.setFourfactors(fourfactors);
		factors.setNofactors(nofactors);
		factors.setOnefactors(onefactors);
		factors.setSmokecount(smokecount);
		factors.setSportcount(sportcount);
		factors.setThreefactors(threefactors);
		factors.setTwofactors(twofactors);
		factors.setWaistcount(waistcount);
		return factors;
	}
}
