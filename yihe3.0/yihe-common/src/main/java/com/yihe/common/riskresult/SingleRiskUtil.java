// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SingleRiskUtil.java

package com.yihe.common.riskresult;
import java.util.Map;
import java.util.TreeMap;

public class SingleRiskUtil
{

	public SingleRiskUtil()
	{
	}

	public static Map getMap(String key)
	{
		Map map = new TreeMap();
		if (key.equals("sbp"))
		{
			map.put("sbp<120", "<120");
			map.put("sbp>=120 and sbp<140", "≥120且<140");
			map.put("sbp>=140", "≥140");
		} else
		if (key.equals("dbp"))
		{
			map.put("dbp<80", "<80");
			map.put("dbp>=80 and dbp<90", "≥80且<90");
			map.put("dbp>=90", ">=90");
		} else
		if (key.equals("fastingBg"))
		{
			map.put("fastingBg<5.6", "<5.6");
			map.put("fastingBg>=5.6 and fastingBg<7", "≥5.6且<7");
			map.put("fastingBg>=7", "≥7");
		} else
		if (key.equals("cholesterol"))
		{
			map.put("cholesterol<5.18", "<5.18");
			map.put("cholesterol>=5.18", "≥5.18");
		} else
		if (key.equals("triglycerin"))
		{
			map.put("triglycerin<1.7", "<1.7");
			map.put("triglycerin>=1.7", "≥1.7");
		} else
		if (key.equals("hdlc"))
		{
			map.put("hdlc>=1.04", "≥1.04");
			map.put("hdlc<1.04", "<1.04");
		} else
		if (key.equals("ldlc"))
		{
			map.put("ldlc<3.37", "<3.37");
			map.put("ldlc>=3.37", "≥3.37");
		} else
		if (key.equals("bmi"))
		{
			map.put("bmi<18.5", "<18.5");
			map.put("bmi>=18.5 and bmi<24", "≥18.5且<24");
			map.put("bmi>=24 and bmi<28", "≥24且<28");
			map.put("bmi>=28", "≥28");
		} else
		if (key.equals("waistLine"))
		{
			map.put("((waistLine<80 and gender=2)or(waistLine<85 and gender=1))", "正常");
			map.put("((waistLine>=80 and gender=2)or(waistLine>=85 and gender=1))", "过大");
		} else
		if (key.equals("smokingStatus"))
		{
			map.put("smokingStatus=1", "不吸烟");
			map.put("smokingStatus=2", "吸烟");
			map.put("smokingStatus=3", "已戒烟");
		} else
		if (key.equals("alcoholDaily"))
		{
			map.put("((alcoholDaily>=15 and gender=2)or(alcoholDaily>=25 and gender=1))", "过量");
			map.put("((alcoholDaily>0 and alcoholDaily<15 and gender=2)or(alcoholDaily>0 and alcoholDaily<25 and gender=1))", "适量");
			map.put("alcoholDaily=0", "不饮酒");
		} else
		if (key.equals("actLevel"))
		{
			map.put("actLevel=1", "充分");
			map.put("actLevel=2", "中等");
			map.put("actLevel=3", "不足");
		} else
		if (key.equals("grainDaily"))
			map.put("grainDaily>400", "过多");
		else
		if (key.equals("meatDaily"))
			map.put("meatDaily>75", "过多");
		else
		if (key.equals("vegetableDailyFruitDaily"))
			map.put("(vegetableDaily+fruitDaily)<500", "不足");
		else
		if (key.equals("vegetableDaily"))
			map.put("vegetableDaily<300", "不足");
		else
		if (key.equals("fruitDaily"))
			map.put("fruitDaily<200", "不足");
		else
		if (key.equals("civRelaTier"))
		{
			map.put("civRelaTier=5", "极高风险");
			map.put("civRelaTier=4", "高风险");
			map.put("civRelaTier=3", "中等风险");
			map.put("civRelaTier=2", "低风险");
			map.put("civRelaTier=1", "极低风险");
		} else
		if (key.equals("lungRelaTier"))
		{
			map.put("lungRelaTier=5", "极高风险");
			map.put("lungRelaTier=4", "高风险");
			map.put("lungRelaTier=3", "中等风险");
			map.put("lungRelaTier=2", "低风险");
			map.put("lungRelaTier=1", "极低风险");
		} else
		if (key.equals("diabRelaTier"))
		{
			map.put("diabRelaTier=5", "极高风险");
			map.put("diabRelaTier=4", "高风险");
			map.put("diabRelaTier=3", "中等风险");
			map.put("diabRelaTier=2", "低风险");
			map.put("diabRelaTier=1", "极低风险");
		} else
		if (key.equals("hbpRelaTier"))
		{
			map.put("hbpRelaTier=3", "高风险");
			map.put("hbpRelaTier=2", "中等风险");
			map.put("hbpRelaTier=1", "低风险");
		} else
		if (key.equals("lifeGrade"))
		{
			map.put("lifeGrade<40", "差");
			map.put("lifeGrade>=40 and lifeGrade<60", "一般");
			map.put("lifeGrade>=60 and lifeGrade<80", "良好");
			map.put("lifeGrade>=80", "优秀");
		} else
		if (key.equals("diabHis"))
		{
			map.put("diabHis=1", "有");
			map.put("diabHis=2", "无");
		} else
		if (key.equals("hbpHis"))
		{
			map.put("hbpHis=1", "有");
			map.put("hbpHis=2", "无");
		} else
		if (key.equals("lungHis"))
		{
			map.put("lungHis=1", "有");
			map.put("lungHis=2", "无");
		} else
		if (key.equals("lipidHis"))
		{
			map.put("lipidHis=1", "有");
			map.put("lipidHis=2", "无");
		} else
		if (key.equals("chdHis"))
		{
			map.put("chdHis=1", "有");
			map.put("chdHis=2", "无");
		} else
		if (key.equals("hisPneum"))
		{
			map.put("hisPneum=1", "有");
			map.put("hisPneum=2", "无");
		} else
		if (key.equals("hisBronch"))
		{
			map.put("hisBronch=1", "有");
			map.put("hisBronch=2", "无");
		} else
		if (key.equals("hisEmphy"))
		{
			map.put("hisEmphy=1", "有");
			map.put("hisEmphy=2", "无");
		} else
		if (key.equals("hisTb"))
		{
			map.put("hisTb=1", "有");
			map.put("hisTb=2", "无");
		} else
		if (key.equals("data1"))
		{
			map.put("exists(from WellCareData wellData where wellData.id=wellRiskresult.id and wellData.diseaseHis like '_______1%' )", "有");
			map.put("exists(from WellCareData wellData where wellData.id=wellRiskresult.id and wellData.diseaseHis like '_______0%' )", "无");
		} else
		if (key.equals("data2"))
		{
			map.put("exists(from WellCareData wellData where wellData.id=wellRiskresult.id and wellData.diseaseHis like '1%' )", "有");
			map.put("exists(from WellCareData wellData where wellData.id=wellRiskresult.id and wellData.diseaseHis like '0%' )", "无");
		} else
		if (key.equals("data3"))
		{
			map.put("exists(from WellCareData wellData where wellData.id=wellRiskresult.id and wellData.diseaseHis like '_1%' )", "有");
			map.put("exists(from WellCareData wellData where wellData.id=wellRiskresult.id and wellData.diseaseHis like '_0%' )", "无");
		}
		return map;
	}
}
