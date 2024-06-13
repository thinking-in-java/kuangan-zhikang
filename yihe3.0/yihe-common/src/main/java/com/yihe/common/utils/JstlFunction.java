// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JstlFunction.java

package com.yihe.common.utils;


import java.util.Date;

import javax.servlet.http.HttpSession;



public class JstlFunction
{

	public JstlFunction()
	{
	}

	public static Object getTokenHtml(HttpSession session)
	{
		return (new StringBuilder("<input type=\"hidden\" name=\"org.apache.struts.taglib.html.TOKEN\" value=\"")).append(session.getAttribute("org.apache.struts.action.TOKEN")).append("\">").toString();
	}

	public static float getBMI(float height, float weight)
	{
		return FloatRigor.getFloNum((10000F * weight) / (height * height), 1);
	}

	public static float getWaistcHipc(float waistc, float hipc)
	{
		return FloatRigor.getFloNum(waistc / hipc, 1);
	}

	
	public static String educToString(int key)
	{
		String value = "";
		switch (key)
		{
		case 1: // '\001'
			value = "博士";
			break;

		case 2: // '\002'
			value = "硕士";
			break;

		case 3: // '\003'
			value = "本科";
			break;

		case 4: // '\004'
			value = "专科";
			break;

		case 5: // '\005'
			value = "中专";
			break;

		case 6: // '\006'
			value = "高中";
			break;

		case 7: // '\007'
			value = "高中以下";
			break;
		}
		return value;
	}

	public static String getSexToString(int sex)
	{
		String value = "性别未知";
		if (sex == 1)
			value = "男";
		else
		if (sex == 2)
			value = "女";
		return value;
	}

	public static String getMarriToString(int marri)
	{
		String value = "";
		switch (marri)
		{
		case 1: // '\001'
			value = "未婚";
			break;

		case 2: // '\002'
			value = "已婚";
			break;

		case 3: // '\003'
			value = "丧偶";
			break;

		case 4: // '\004'
			value = "离婚";
			break;

		case 5: // '\005'
			value = "其他";
			break;
		}
		return value;
	}

	public static String getBloodTypeToString(int type)
	{
		String value = "";
		switch (type)
		{
		case 1: // '\001'
			value = "A型血";
			break;

		case 2: // '\002'
			value = "B型血";
			break;

		case 3: // '\003'
			value = "O型血";
			break;

		case 4: // '\004'
			value = "AB型血";
			break;

		case 5: // '\005'
			value = "其他";
			break;
		}
		return value;
	}

	



	public static int getDisToInteger(int flag)
	{
		int value = 1;
		if (flag == 1)
			value = 3;
		else
		if (flag == 2)
			value = 1;
		return value;
	}

	public static String getDisToString(int flag)
	{
		String value = "未知";
		if (flag == 1)
			value = "有";
		else
		if (flag == 2)
			value = "无";
		return value;
	}

	public static String getPollutToString(int flag)
	{
		String value = "&nbsp;";
		if (flag == 1)
			value = "高度";
		else
		if (flag == 2)
			value = "低度";
		return value;
	}

	public static String getUsername(String name, String userName)
	{
		if (Util.checkStr(name))
			return name;
		else
			return userName;
	}

	public static String getFormatDate(String date)
	{
		if (date.length() > 10)
			return (new StringBuilder("（")).append(date.substring(0, 10)).append("）").toString();
		else
			return date;
	}

	public static int getIntegerVal(float resouceVal)
	{
		return Math.round(resouceVal);
	}

	public static String getSmokeToString(int flag)
	{
		String value = "&nbsp;";
		if (flag == 1)
			value = "不吸烟";
		else
		if (flag == 2)
			value = "吸烟";
		else
		if (flag == 3)
			value = "已戒烟";
		return value;
	}

	public static String getActToString(int flag)
	{
		String value = "&nbsp;";
		if (flag == 1)
			value = "充分";
		else
		if (flag == 2)
			value = "中等";
		else
		if (flag == 3)
			value = "不足";
		return value;
	}

	public static String getCookToString(int flag)
	{
		String value = "&nbsp;";
		if (flag == 1)
			value = "差";
		else
		if (flag == 2)
			value = "一般";
		else
		if (flag == 3)
			value = "良好";
		return value;
	}

	public static String getDisCryOrLaugh(int now, int pre, int now_, int pre_, int flag)
	{
		if (flag == 1)
		{
			if (now < pre || now_ < pre_)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		} else
		{
			if (now < pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
			if (now > pre && flag == 2)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		}
		return "－";
	}

	public static String getSmokeCryOrLaugh(int now, int pre)
	{
		if (now == 3 && pre == 2)
			return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		if (now == 2 && pre == 1 || now == 2 && pre == 3)
			return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		else
			return "－";
	}

	public static String getCryOrLaugh(int now, int pre, int flag)
	{
		if (flag == 0)
		{
			if (now > pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
			if (now < pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		} else
		{
			if (now > pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
			if (now < pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		}
		return "－";
	}

	public static String getRangeCryOrLaugh(float now, float pre, float targetMin, float targetMax)
	{
		if (now >= targetMin && pre >= targetMin)
		{
			if (now > pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
			if (now < pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		} else
		if (now < targetMin && pre < targetMin)
		{
			if (now > pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
			if (now < pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		} else
		if (now < targetMin)
		{
			if (pre < targetMax && pre >= targetMin)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		} else
		if (now < targetMax && now >= targetMin && pre < targetMin)
			return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		return "－";
	}

	public static String getBpCryOrLaugh(int now, int pre, int target)
	{
		if (now < target && pre < target)
		{
			if (now > pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
			if (now < pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		} else
		{
			if (now > pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
			if (now < pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		}
		return "－";
	}

	public static String getDiaryCryOrLaugh(int now, int pre, int target)
	{
		int nowFlag = 0;
		int preFlag = 0;
		if (now >= target)
			nowFlag = 1;
		if (pre >= target)
			preFlag = 1;
		if (nowFlag > preFlag)
			return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		if (nowFlag < preFlag)
			return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		else
			return "－";
	}

	public static String getBodyCryOrLaugh(float now, float pre, float target, int flag)
	{
		if (flag == 0)
		{
			if (now > pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
			if (now < pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		} else
		{
			if (now > pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face02.gif\" alt=\"\" width=\"50\" height=\"22\" />";
			if (now < pre)
				return "<img src=\"/jsp/risk_evaluate/report/wellwoo/face01.gif\" alt=\"\" width=\"50\" height=\"22\" />";
		}
		return "－";
	}

/*	public static String getHistogram(String usercode, String usertimes, int picName)
	{
		String picNames[] = {
			"diab_l.jpg", "diab_r.jpg", "icvd_l.jpg", "icvd_r.jpg", "lung_l.jpg", "lung_r.jpg", "bhp_l.jpg", "bhp_r.jpg"
		};
		return (new StringBuilder("/Image.do?imageUrl=")).append(ImageImpAction.imageEncrypt((new StringBuilder(String.valueOf(Path.RISKIMAGES))).append(usercode).append("_").append(usertimes).append(File.separatorChar).append(picNames[picName]).toString())).toString();
	}
*/
	public static String getInfoMsg(int flag, int type)
	{
		if (type == 0)
		{
			if (flag == 1)
				return "您从不吸烟！";
			if (flag == 2)
				return "您目前仍在吸烟！";
			else
				return "您目前已经戒烟！";
		}
		if (flag == 1)
			return "您目前的体力活动水平：充分！";
		if (flag == 2)
			return "您目前的体力活动水平：中等！";
		else
			return "您目前的体力活动水平：不足！";
	}

	public static String getLifeInfo(String nowStr, String preStr)
	{
		String nowStrs[] = nowStr.split(",");
		String preStrs[] = {
			"", "", ""
		};
		if (Util.checkStr(preStr))
			preStrs = preStr.split(",");
		String resultStr = "";
		if (nowStrs[0].equals("1"))
		{
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td height=\"25\" ><img src=\"/jsp/risk_evaluate/report/wellwoo/gou.gif\" alt=\"\" width=\"16\" height=\"16\" align=\"absmiddle\" /></td>\n").toString();
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td >&nbsp;</td>\n").toString();
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td >&nbsp;</td>\n").toString();
		} else
		if (nowStrs[0].equals("2"))
		{
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td >&nbsp;</td>\n").toString();
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td height=\"25\" ><img src=\"/jsp/risk_evaluate/report/wellwoo/gou.gif\" alt=\"\" width=\"16\" height=\"16\" align=\"absmiddle\" /></td>\n").toString();
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td >&nbsp;</td>\n").toString();
		} else
		{
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td >&nbsp;</td>\n").toString();
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td >&nbsp;</td>\n").toString();
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td height=\"25\" ><img src=\"/jsp/risk_evaluate/report/wellwoo/gou.gif\" alt=\"\" width=\"16\" height=\"16\" align=\"absmiddle\" /></td>\n").toString();
		}
		resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td align=\"center\" >").append(nowStrs[1]).append("/").append(nowStrs[2]).append("</td>").toString();
		if (Util.checkStr(preStrs[2]))
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td align=\"center\" >").append(preStrs[1]).append("/").append(preStrs[2]).append("</td>").toString();
		else
			resultStr = (new StringBuilder(String.valueOf(resultStr))).append("<td align=\"center\" >&nbsp;</td>").toString();
		return resultStr;
	}

	

	public static String getDiseaseRisk(int no, int flag)
	{
		if (flag == 0)
		{
			if (no == 1)
				return "极低风险";
			if (no == 2)
				return "低风险";
			if (no == 3)
				return "中等风险";
			if (no == 4)
				return "高风险";
			if (no == 5)
				return "极高风险";
		} else
		{
			if (no == 1)
				return "低风险";
			if (no == 2)
				return "中等风险";
			if (no == 3)
				return "高风险";
		}
		return "--";
	}

	public static String getState(String infoStr, int flag)
	{
		String tmpStr = infoStr.split(",")[0];
		int tmpInt = ParamType.getint(tmpStr);
		if (flag == 0)
		{
			if (tmpInt == 1)
				return "过量";
			if (tmpInt == 2)
				return "不饮酒";
			else
				return "不饮酒";
		}
		if (tmpInt == 1)
			return "差";
		if (tmpInt == 2)
			return "一般";
		else
			return "良好";
	}

	public static String getDailyState(float now, int minTarget, int maxTarget, int flag)
	{
		if (flag == 0)
			if (now <= (float)minTarget)
				return "适量";
			else
				return "过多";
		if (flag == 1)
			if (now < (float)minTarget)
				return "不足";
			else
				return "充分";
		if (now < (float)minTarget)
			return "不足";
		if (now > (float)maxTarget)
			return "过多";
		else
			return "适量";
	}

	/*public static String getDangerRemind(Object obj, int gender)
	{
		WellRiskresult wr = (WellRiskresult)obj;
		StringBuffer sb = new StringBuffer();
		int flag = 0;
		int isP = 0;
		sb.append("<tr>");
		if (wr.getBmi() >= 24F)
		{
			isP++;
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">体重指数（BMI）</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(wr.getBmi()).append("</td><td height=\"25\" >18.5≤BMI&lt;24</td>");
			flag++;
		}
		if ((double)wr.getHdlc() < 1.04D)
		{
			isP++;
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">高密度脂蛋白胆固醇</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(wr.getHdlc()).append("</td><td height=\"25\" >≥1.04 mmol/L</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr><tr>");
		if (wr.getSbp() > 120 || wr.getDbp() > 80)
		{
			isP++;
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">血压</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(wr.getSbp()).append("/").append(wr.getDbp()).append("</td><td height=\"25\" >&lt;120/80mmHg</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr><tr>");
		if ((double)wr.getFastingBg() >= 5.5999999999999996D)
		{
			isP++;
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">空腹血糖</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(wr.getFastingBg()).append("</td><td height=\"25\" >&lt;5.6mmol/L</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr><tr>");
		if ((double)wr.getTriglycerin() >= 1.7D)
		{
			isP++;
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">甘油三酯</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(wr.getTriglycerin()).append("</td><td height=\"25\" >&lt;1.7mmol/L</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr><tr>");
		if (wr.getAct() == 3)
		{
			isP++;
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">体力活动水平</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(getActToString(wr.getAct())).append("</td><td height=\"25\" >充分</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr><tr>");
		if ((double)wr.getCholesterol() >= 5.1799999999999997D)
		{
			isP++;
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">总胆固醇</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(wr.getCholesterol()).append("</td><td height=\"25\" >&lt;5.18mmol/L</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr><tr>");
		if (wr.getWaistLine() > 85F && gender == 1)
		{
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">腰围</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(wr.getWaistLine()).append("</td><td height=\"25\" >&lt;85cm</td>");
			flag++;
		} else
		if (wr.getWaistLine() > 80F && gender == 2)
		{
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">腰围</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(wr.getWaistLine()).append("</td><td height=\"25\" >&lt;80cm</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr><tr>");
		if (wr.getFruitDaily() + wr.getVegetableDaily() < 500F)
		{
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">蔬菜水果摄入</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(getIntegerVal(wr.getFruitDaily()) + getIntegerVal(wr.getVegetableDaily())).append("</td><td height=\"25\" >≥500克/天</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr><tr>");
		if (wr.getSmokingStatus() != 1)
		{
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">吸烟状况</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(getSmokeToString(wr.getSmokingStatus())).append("</td><td height=\"25\" >不吸烟</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr><tr>");
		if (wr.getLungCookhotFactor() == 1)
		{
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">烹调习惯</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(getCookToString(wr.getLungCookhotFactor())).append("</td><td height=\"25\" >优</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr><tr>");
		if (wr.getPassiveFreq() == 1)
		{
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">被动吸烟</td>");
			sb.append("<td height=\"25\" align=\"center\" >").append(getDisToString(wr.getPassiveFreq())).append("</td><td height=\"25\" >无</td>");
			flag++;
		}
		if (flag != 0 && flag % 2 == 0)
			sb.append("</tr>");
		else
			sb.append("<td height=\"25\" align=\"left\" bgcolor=\"#DCEEF3\">&nbsp;</td><td height=\"25\" align=\"center\" >&nbsp;</td><td height=\"25\" >&nbsp;</td></tr>");
		return sb.toString();
	}*/

	/*public static String getKeyPoint(Object obj, int gender)
	{
		WellRiskresult wr = (WellRiskresult)obj;
		StringBuffer sb = new StringBuffer();
		StringBuffer bmidis = new StringBuffer();
		StringBuffer bmiper = new StringBuffer();
		StringBuffer bpdis = new StringBuffer();
		StringBuffer bpper = new StringBuffer();
		StringBuffer vfdis = new StringBuffer();
		StringBuffer vfper = new StringBuffer();
		StringBuffer smokedis = new StringBuffer();
		StringBuffer smokeper = new StringBuffer();
		int op = 1;
		if (wr.getBmi() >= 24F)
		{
			int cflag = 0;
			int dflag = 0;
			int bflag = 0;
			op++;
			sb.append("<div class=\"info_tit\">体重</div>");
			sb.append((new StringBuilder("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/bmi.gif\" width=\"56\" height=\"56\" />您的体重为")).append(wr.getBodyWeight()).append("kg，相应的体重指数（BMI）为").append(wr.getBmi()).append("，说明您的体重已超过正常范围。").append("虽然减肥十分困难，但适当减掉一些体重以达到理想体重范围，不仅可以降低血压及胆固醇水平，还会改善您的总体健康状况，从而达到降低各疾病发病风险的目的。（请注意：运动员、健身者、孕妇、被看护的病人上述体重指数解释不适用）。").toString());
			if (wr.getChdHis() != 1 && wr.getCivBmiDecline() > 0.0F)
			{
				bmidis.append("缺血性心血管疾病、");
				bmiper.append(wr.getCivBmiDecline()).append("%、");
				cflag = 1;
			}
			if (wr.getDiabHis() != 1 && wr.getFastingBg() < 7F && wr.getDiabBmiDecline() > 0.0F)
			{
				bmidis.append("糖尿病、");
				bmiper.append(wr.getDiabBmiDecline()).append("%、");
				dflag = 1;
			}
			if (wr.getHbpHis() != 1 && wr.getSbp() < 140 && wr.getDbp() < 90 && wr.getHbpBmiDecline() > 0.0F)
			{
				bmidis.append("高血压、");
				bmiper.append(wr.getHbpBmiDecline()).append("%、");
				bflag = 1;
			}
			if (cflag + dflag + bflag > 0)
			{
				sb.append("<span>如果您将体重降至正常范围，可使您患").append(bmidis.toString().substring(0, bmidis.toString().length() - 1));
				sb.append(cflag + dflag + bflag <= 1 ? "的风险降低" : "的风险分别降低").append(bmiper.toString().substring(0, bmiper.toString().length() - 1)).append("。</span>");
			}
			sb.append("</div>");
		}
		if (wr.getWaistLine() >= 85F && gender == 1)
		{
			op++;
			sb.append("<div class=\"info_tit\">腰围</div>");
			sb.append((new StringBuilder("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/yw.gif\" width=\"56\" height=\"56\" />您的腰围为")).append(wr.getWaistLine()).append("cm，说明您的腰围过大（≥85cm）。目前公认腰围是衡量脂肪在腹部蓄积（向心性肥胖）程度最简单实用的指标，脂肪在体内的分布，").append("尤其是腹部脂肪堆积的程度，与糖尿病等肥胖相关性疾病有更强的关联。对于体重指数不太高者，腹部脂肪增加被认为是独立的危险性预测因素。").toString());
			if (wr.getDiabHis() != 1 && wr.getFastingBg() < 7F && wr.getDiabWaistDecline() > 0.0F)
				sb.append((new StringBuilder("<span>如果您将腰围控制至正常范围，可使您患糖尿病的风险降低")).append(wr.getDiabWaistDecline()).append("%。</span>").toString());
			sb.append("</div>");
		} else
		if (wr.getWaistLine() >= 80F && gender == 2)
		{
			op++;
			sb.append("<div class=\"info_tit\">腰围</div>");
			sb.append((new StringBuilder("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/gmddgc.gif\" width=\"56\" height=\"56\" />您的腰围为")).append(wr.getWaistLine()).append("cm，说明您的腰围过大（≥80cm）。目前公认腰围是衡量脂肪在腹部蓄积（向心性肥胖）程度最简单实用的指标，脂肪在体内的分布，").append("尤其是腹部脂肪堆积的程度，与糖尿病等肥胖相关性疾病有更强的关联。对于体重指数不太高者，腹部脂肪增加被认为是独立的危险性预测因素。").toString());
			if (wr.getDiabHis() != 1 && wr.getFastingBg() < 7F && wr.getDiabWaistDecline() > 0.0F)
				sb.append((new StringBuilder("<span>如果您将腰围控制至正常范围，可使您患糖尿病的风险降低")).append(wr.getDiabWaistDecline()).append("%。</span>").toString());
			sb.append("</div>");
		}
		if (wr.getSbp() > 120 || wr.getDbp() > 80)
		{
			op++;
			int cflag = 0;
			int bflag = 0;
			sb.append("<div class=\"info_tit\">血压</div>");
			sb.append((new StringBuilder("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/xy.gif\" width=\"56\" height=\"56\" />您的血压为")).append(wr.getSbp()).append("/").append(wr.getDbp()).append("mmHg，已高于《中国高血压防治指南》所建议的正常血压范围。").append("您的血压越高，患心血管疾病的危险性就越大。大多数自我感觉健康的人并没意识到他们患有高血压，所以高血压被称为隐形杀手。您应该努力通过改善膳食、运动习惯达到理想血压。请向医生咨询有关控制措施。").toString());
			if (wr.getChdHis() != 1 && wr.getCivSbpDecline() > 0.0F)
			{
				bpdis.append("缺血性心血管疾病、");
				bpper.append(wr.getCivSbpDecline()).append("%、");
				cflag = 1;
			}
			if (wr.getHbpHis() != 1 && wr.getSbp() < 140 && wr.getDbp() < 90 && wr.getHbpBpDecline() > 0.0F)
			{
				bpdis.append("高血压、");
				bpper.append(wr.getHbpBpDecline()).append("%、");
				bflag = 1;
			}
			if (cflag + bflag > 0)
			{
				sb.append("<span>如果您的血压降至理想范围，可使您患").append(bpdis.toString().substring(0, bpdis.toString().length() - 1));
				sb.append(cflag + bflag <= 1 ? "的风险降低" : "的风险分别降低").append(bpper.toString().substring(0, bpper.toString().length() - 1)).append("。</span>");
			}
			sb.append("</div>");
		}
		if ((double)wr.getCholesterol() > 5.1799999999999997D)
		{
			if (op == 4)
				sb.append("<p class=\"op\"></p>");
			op++;
			sb.append("<div class=\"info_tit\">总胆固醇</div>");
			sb.append((new StringBuilder("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/dgc.gif\" width=\"56\" height=\"56\" />您的总胆固醇为")).append(wr.getCholesterol()).append("mmol/L，高于《中国成人血脂异常防治指南》所建议的理想范围。").append("为了保护您的心脏，您应该咨询医生，将总胆固醇降至正常范围。胆固醇通常来源于动物性食物（所有动物性食物都含有胆固醇），如动物内脏、蛋黄等，请尽量减少该类食物的摄入量。").toString());
			if (wr.getChdHis() != 1 && wr.getCivCholDecline() > 0.0F)
				sb.append((new StringBuilder("<span>如果您的总胆固醇降至理想范围，可使您患缺血性心血管疾病的风险降低")).append(wr.getCivCholDecline()).append("%。</span>").toString());
			sb.append("</div>");
		}
		if ((double)wr.getHdlc() < 1.04D)
		{
			if (op == 4)
				sb.append("<p class=\"op\"></p>");
			op++;
			sb.append("<div class=\"info_tit\">高密度脂蛋白胆固醇</div>");
			sb.append((new StringBuilder("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/gmddgc.gif\" width=\"56\" height=\"56\" />您的HDL胆固醇为")).append(wr.getHdlc()).append("mmol/L，低于《中国成人血脂异常防治指南》所建议理想范围。").append("高密度脂蛋白胆固醇有助于从血液清除多余的胆固醇，因而对于冠心病和糖尿病具有保护作用。").toString());
			if (wr.getDiabHis() != 1 && wr.getFastingBg() < 7F && wr.getDiabHdlcDecline() > 0.0F)
				sb.append((new StringBuilder("<span>如果您将高密度脂蛋白胆固醇改善至正常范围，可使您患糖尿病的风险降低")).append(wr.getDiabHdlcDecline()).append("%。</span>").toString());
			sb.append("</div>");
		}
		if ((double)wr.getTriglycerin() >= 1.7D)
		{
			if (op == 4)
				sb.append("<p class=\"op\"></p>");
			op++;
			sb.append("<div class=\"info_tit\">甘油三酯</div>");
			sb.append((new StringBuilder("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/gyszh.gif\" width=\"56\" height=\"56\" />您的甘油三酯为")).append(wr.getTriglycerin()).append("mmol/L，高于《中国成人血脂异常防治指南》所建议的正常范围。").append("值得您重视的是，甘油三酯是引起动脉阻塞的重要危险因素，动脉阻塞进一步发展会导致心脏病和中风。当您的膳食能量摄入量大于身体需要量时，多余的能量则会转换成甘油三酯并储存与脂肪细胞。").toString());
			if (wr.getDiabHis() != 1 && wr.getFastingBg() < 7F && wr.getDiabTrigDecline() > 0.0F)
				sb.append((new StringBuilder("<span>如果您将甘油三酯降至正常范围，可使您患糖尿病的风险降低")).append(wr.getDiabTrigDecline()).append("%。</span>").toString());
			sb.append("</div>");
		}
		if ((double)wr.getFastingBg() >= 5.5999999999999996D)
		{
			if (op == 4)
				sb.append("<p class=\"op\"></p>");
			op++;
			sb.append("<div class=\"info_tit\">空腹血糖</div>");
			sb.append((new StringBuilder("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/xt.gif\" width=\"56\" height=\"56\" />您的空腹血糖为")).append(wr.getFastingBg()).append("mmol/L，已处于糖尿病前期（5.6≤空腹血糖<7）。糖尿病前期可增加您患糖尿病的风险。").append("血糖是供机体利用以产能的主要能源之一。一般来说，进餐后，血糖水平会轻微上升，从而刺激胰腺释放胰岛素以避免血糖上升过高。持续升高的血糖水平会增加患糖尿病的风险，进一步发展还会引起其它器官如眼、肾脏、神经以及血管等的损伤。").toString());
			if (wr.getDiabHis() != 1 && wr.getFastingBg() < 7F && wr.getDiabGluDecline() > 0.0F)
				sb.append((new StringBuilder("<span>如果您将空腹血糖降至正常范围，可使您患糖尿病的风险降低")).append(wr.getDiabGluDecline()).append("%。</span>").toString());
			sb.append("</div>");
		}
		if (wr.getSmokingStatus() == 2)
		{
			if (op == 4)
				sb.append("<p class=\"op\"></p>");
			op++;
			int cflag = 0;
			int dflag = 0;
			int bflag = 0;
			int lfalg = 0;
			sb.append("<div class=\"info_tit\">吸烟</div>");
			sb.append((new StringBuilder("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/xiyan.gif\" width=\"56\" height=\"56\" />您的吸烟年限为")).append(wr.getSmokeYear()).append("年，结合您提供的每日吸烟支数，计算出您的吸烟指数（每日吸烟支数\327吸烟年数）为").append(wr.getSmokeIndex()).append("，吸烟指数超过400即为肺癌的高危因素。").append("由于烟雾中含有上百种有害的化学物质，其中许多已被证明是致癌的，长期吸烟不仅会增加患肺癌及其它癌症的危险性，还会增加患心脏病、高血压和各种肺部疾病的危险性。").toString());
			if (wr.getChdHis() != 1 && wr.getCivSmokeDecline() > 0.0F)
			{
				smokedis.append("缺血性心血管疾病、");
				smokeper.append((new StringBuilder(String.valueOf(wr.getCivSmokeDecline()))).append("%、").toString());
				cflag = 1;
			}
			if (wr.getDiabHis() != 1 && wr.getFastingBg() < 7F && wr.getDiabSmokeDecline() > 0.0F)
			{
				smokedis.append("糖尿病、");
				smokeper.append((new StringBuilder(String.valueOf(wr.getDiabSmokeDecline()))).append("%、").toString());
				dflag = 1;
			}
			if (wr.getHbpHis() != 1 && wr.getSbp() < 140 && wr.getDbp() < 90 && wr.getHbpSmokeDecline() > 0.0F)
			{
				smokedis.append("高血压、");
				smokeper.append((new StringBuilder(String.valueOf(wr.getHbpSmokeDecline()))).append("%、").toString());
				bflag = 1;
			}
			if (wr.getLungHis() != 1 && wr.getLungSomkeDecline() > 0.0F)
			{
				smokedis.append("肺癌、");
				smokeper.append((new StringBuilder(String.valueOf(wr.getLungSomkeDecline()))).append("%、").toString());
				lfalg = 1;
			}
			if (cflag + dflag + bflag + lfalg > 0)
			{
				sb.append("<span>如果您能戒烟，可使您患").append(smokedis.toString().substring(0, smokedis.toString().length() - 1));
				sb.append(cflag + dflag + bflag + lfalg <= 1 ? "的风险降低" : "的风险分别降低").append(smokeper.toString().substring(0, smokeper.toString().length() - 1)).append("。</span>");
			}
			sb.append("</div>");
		}
		if (wr.getPassiveFreq() == 1)
		{
			if (op == 4)
				sb.append("<p class=\"op\"></p>");
			op++;
			sb.append("<div class=\"info_tit\">被动吸烟</div>");
			sb.append("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/xiyan.gif\" width=\"56\" height=\"56\" />被动吸烟者吸入的烟尘中有害物质浓度并不比吸烟者低，研究显示，吸烟者吐出的冷烟雾中，烟焦油含量比吸烟者吸入的热烟雾中的多1倍，苯并芘多2倍， 一氧化碳多4倍。被动吸烟不仅是非吸烟者中肺癌发生的一个确切原因，也是导致心血管疾病的可预防的原因。尽量避免被动吸烟，是延长健康寿命切实有效的方法。");
			if (wr.getLungHis() != 1 && wr.getLungPassiveDecline() > 0.0F)
				sb.append((new StringBuilder("<span>如果您能避免被动吸烟，可使您患肺癌的风险降低")).append(wr.getLungPassiveDecline()).append("%。</span>").toString());
			sb.append("</div>");
		}
		if (wr.getLungCookhotFactor() == 1)
		{
			if (op == 4)
				sb.append("<p class=\"op\"></p>");
			op++;
			sb.append("<div class=\"info_tit\">烹调习惯</div>");
			sb.append("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/pengtiao.gif\" width=\"56\" height=\"56\" />您炒菜做饭时经常把油烧得很热，您需要了解的是，当食用油加热到100℃以上时，即开始产生一系列对人体呼吸系统有害的物质，研究者对烹调油烟进行分析，共检出近200种有害物质，包括多环芳烃、杂环胺等，这些物质不仅会增加人们患肺癌的概率，还有致癌性和遗传毒性，对健康造成严重危害。希望为了您的健康，尽量采用蒸、煮、炖、凉拌等较健康的烹饪方式。");
			if (wr.getLungHis() != 1 && wr.getLungCookhotDecline() > 0.0F)
				sb.append((new StringBuilder("<span>如果您能改善烹调习惯，可使您患肺癌的风险降低")).append(wr.getLungCookhotDecline()).append("%。</span>").toString());
			sb.append("</div>");
		}
		if (wr.getFruitDaily() + wr.getVegetableDaily() < 500F)
		{
			if (op == 4)
				sb.append("<p class=\"op\"></p>");
			op++;
			int dflag = 0;
			int lfalg = 0;
			sb.append("<div class=\"info_tit\">蔬菜水果摄入</div>");
			sb.append("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/shucai.gif\" width=\"56\" height=\"56\" />根据您提供的膳食信息，评价出您的蔬菜水果摄入不足。新鲜蔬菜水果因富含维生素、矿物质、膳食纤维和植物化学物质，且能量低，对于预防血脂异常、糖尿病、心血管疾病和癌症等慢性病具有重要作用。建议每天吃蔬菜300~500克，每天吃水果200~400克，且遵循食物品种多样化、深浅搭配的原则。");
			if (wr.getDiabHis() != 1 && wr.getFastingBg() < 7F && wr.getDiabIntakeDecline() > 0.0F)
			{
				vfdis.append("糖尿病、");
				vfper.append(wr.getDiabIntakeDecline()).append("%、");
				dflag = 1;
			}
			if (wr.getLungHis() != 1 && wr.getLungIntakeDecline() > 0.0F)
			{
				vfdis.append("肺癌、");
				vfper.append(wr.getLungIntakeDecline()).append("%、");
				lfalg = 1;
			}
			if (dflag + lfalg > 0)
			{
				sb.append("<span>如果您能增加蔬菜水果摄入量，可使您患").append(vfdis.toString().substring(0, vfdis.toString().length() - 1));
				sb.append(dflag + lfalg <= 1 ? "的风险降低" : "的风险分别降低").append(vfper.toString().substring(0, vfper.toString().length() - 1)).append("。</span>");
			}
			sb.append("</div>");
		}
		if (wr.getAct() == 3)
		{
			if (op == 4)
				sb.append("<p class=\"op\"></p>");
			op++;
			sb.append("<div class=\"info_tit\">体力活动水平 </div>");
			sb.append("<div class=\"info_tb\"><img src=\"/jsp/risk_evaluate/report/wellwoo/tili.gif\" width=\"56\" height=\"56\" />根据您提供的体力活动信息，评价出您的体力活动水平不足。适当的体力活动可以降低患慢性疾病的危险。研究表明有规律的有氧运动对于健康有着多方面的益处：①可以提高心肺功能，降低血压、血糖，调节血脂，防治动脉粥样硬化，从而有效预防心脑血管意外的发生；②有助于降低体内多余的脂肪，使体重恢复到正常范围；③可以增加骨骼中血流量，促进骨骼中钙的沉积，防治骨质疏松；④可以促进内啡肽样物质的分泌，使人运动后身心愉悦；⑤许多吸烟者发现有氧运动对戒烟有很大的帮助作用。");
			if (wr.getDiabHis() != 1 && wr.getFastingBg() < 7F && wr.getDiabActDecline() > 0.0F)
				sb.append((new StringBuilder("<span>如果您能增加体力活动至充足水平（详细运动方案请参见“运动处方”），可使您患糖尿病的风险降低")).append(wr.getDiabActDecline()).append("%。</span>").toString());
			sb.append("</div>");
		}
		return sb.toString();
	}
*/
	
	public static String getNoteTxt(String exsCode, String exsTxt, float bmiVal, int susDM)
	{
		String txts[] = exsTxt.split("__");
		String codes[] = exsCode.split("");
		if (codes[5].equals("1") && codes[6].equals("1"))
		{
			if (bmiVal >= 28F && susDM == 1)
				return txts[1];
			if (bmiVal >= 28F && susDM == 2)
				return txts[3];
			if (bmiVal >= 24F && susDM == 1)
				return txts[0];
			if (bmiVal >= 24F && susDM == 2)
				return txts[2];
			else
				return exsTxt;
		}
		if (codes[5].equals("1"))
		{
			if (bmiVal >= 28F)
				return txts[1];
			if (bmiVal >= 24F)
				return txts[0];
			else
				return exsTxt;
		}
		if (codes[6].equals("1"))
		{
			if (susDM == 1)
				return txts[0];
			if (susDM == 2)
				return txts[1];
			else
				return exsTxt;
		} else
		{
			return exsTxt;
		}
	}

	public static String getAttentionTxt(String attentionTxt, float bmiVal)
	{
		String txts[] = attentionTxt.split("__");
		if (bmiVal >= 28F)
			return txts[1];
		if (bmiVal >= 24F)
			return txts[0];
		else
			return attentionTxt;
	}

	public static String getSchedule(int weekTimes, String startDate, String aeArrange, String stArrange, String ftArrange)
	{
		StringBuffer sb = new StringBuffer();
		Date sdate = DateUtil.Str2Date(startDate, "yyyy-MM-dd");
		String edate = DateUtil.Date2Str(DateUtil.getGoDay(sdate, 6), "yyyy-MM-dd");
		Date tmpdate = null;
		int theweeks[] = new int[7];
		sb.append("<tr class=\"tab_tit\">");
		sb.append((new StringBuilder("<td height=\"22\" colspan=\"8\" class=\"tab_left_line\"><div align=\"center\">一周锻炼安排表（第")).append(weekTimes).append("阶段）</div></td>").toString());
		sb.append("</tr><tr class=\"tab_tit\">");
		sb.append("<td height=\"22\" class=\"tab_left_line\">锻炼内容</td>");
		for (int i = 0; i < 7; i++)
		{
			tmpdate = DateUtil.getGoDay(sdate, i);
			theweeks[i] = DateUtil.getWeekInt(tmpdate);
			sb.append((new StringBuilder("<td align=\"center\" class=\"tab_tit\">")).append(DateUtil.getWeek(tmpdate)).append("</td>").toString());
		}

		sb.append("</tr><tr><td height=\"22\" class=\"tab_left_line td\">有氧运动</td>");
		if (Util.checkStr(aeArrange))
		{
			for (int i = 1; i < 8; i++)
				if (aeArrange.indexOf((new StringBuilder(String.valueOf(theweeks[i - 1]))).toString()) != -1)
					sb.append("<td align=\"center\" class=\"tab_left_line\"><img src=\"/jsp/risk_evaluate/report/wellwoo/paobu.gif\"/></td>");
				else
					sb.append("<td align=\"center\" class=\"tab_left_line\">&nbsp;</td>");

		}
		sb.append("</tr><tr><td height=\"22\" class=\"tab_left_line td\">力量练习</td>");
		if (Util.checkStr(stArrange))
		{
			for (int i = 1; i < 8; i++)
				if (stArrange.indexOf((new StringBuilder(String.valueOf(theweeks[i - 1]))).toString()) != -1)
					sb.append("<td align=\"center\" class=\"tab_left_line\"><img src=\"/jsp/risk_evaluate/report/wellwoo/liliang.gif\"/></td>");
				else
					sb.append("<td align=\"center\" class=\"tab_left_line\">&nbsp;</td>");

		}
		sb.append("</tr><tr><td height=\"22\" class=\"tab_left_line td\">柔韧性练习</td>");
		if (Util.checkStr(ftArrange))
		{
			for (int i = 1; i < 8; i++)
				if (ftArrange.indexOf((new StringBuilder(String.valueOf(theweeks[i - 1]))).toString()) != -1)
					sb.append("<td align=\"center\" class=\"tab_left_line\"><img src=\"/jsp/risk_evaluate/report/wellwoo/rouren.gif\"/></td>");
				else
					sb.append("<td align=\"center\" class=\"tab_left_line\">&nbsp;</td>");

		}
		sb.append("</tr>");
		return sb.toString();
	}

	public static String getDateRange(String startDate)
	{
		Date sdate = DateUtil.Str2Date(startDate, "yyyy-MM-dd");
		String edate = DateUtil.Date2Str(DateUtil.getGoDay(sdate, 6), "yyyy-MM-dd");
		return (new StringBuilder()).append(sdate).append("至").append(edate).toString();
	}

	public static String getActWord(int top, int bot)
	{
		StringBuffer sb = new StringBuffer();
		float level = (float)top / (float)bot;
		if (level >= 0.86F)
			sb.append(" <div id=\"font_txt\"> <p>规律的体力活动可以显著降低血糖、血压、血脂等异常指标，改善慢性病患者的健康状况。坚持适当运动的人其心脏、肌肉和骨骼的健康水平以及精神状态与不锻炼的同龄人相比，要“年轻”许多。因此请继续保持现在的运动水平吧，它会带给您更多的健康与快乐。想了解更多运动与健康的知识，请进入“健康阅览室”。 若有任何问题，请咨询健康管理师。</p></div> ");
		else
		if (level >= 0.66F)
			sb.append(" <div id=\"font_txt\"> <p>适当运动可以降低肥胖症、冠心病、高血压、2型糖尿病、血脂异常等疾病的发生率，促进心血管、肌肉和骨骼的健康；还可以减少忧郁和焦虑的情绪，有助于缓解心理压力，促进心理健康。所以多增加一些身体活动吧，这会使您的身体得到更大的益处。想了解更多运动与健康的知识，请进入“健康阅览室”。 若有任何问题，请咨询健康管理师。</p></div> ");
		else
			sb.append(" <div id=\"font_txt\"> <p>运动不足可导致体力不济，身体各个器官的功能也会衰退，慢性病发病风险上升。要想预防或推迟某些疾病的发生，最好的武器之一就是运动，常规的体力活动可以改善导致这些疾病的危险因素。请从现在开始注意加强运动，您会发现管理或控制这些疾病并不是您想像地那么难。 想了解更多运动与健康的知识，请进入“健康阅览室”。 若有任何问题，请咨询健康管理师。</p></div> ");
		return sb.toString();
	}

	public static String getActLevel(int top, int bot)
	{
		StringBuffer sb = new StringBuffer();
		float level = (float)top / (float)bot;
		if (level >= 0.86F)
		{
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"234\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/risk_evaluate/report/wellwoo/sport_ren.gif\" width=\"100\" height=\"60\" /></td>");
		} else
		if (level >= 0.66F)
		{
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"234\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/risk_evaluate/report/wellwoo/sport_ren.gif\" width=\"100\" height=\"60\" /></td>");
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
		} else
		{
			sb.append("<td width=\"234\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/risk_evaluate/report/wellwoo/sport_ren.gif\" width=\"100\" height=\"60\" /></td>");
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"232\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
		}
		return sb.toString();
	}

	public static String getLifeLevel(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if (flag >= 80F)
		{
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/risk_evaluate/report/wellwoo/life_ren.gif\" width=\"100\" height=\"60\" /></td>");
		} else
		if (flag >= 60F)
		{
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/risk_evaluate/report/wellwoo/life_ren.gif\" width=\"100\" height=\"60\" /></td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
		} else
		if (flag >= 40F)
		{
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/risk_evaluate/report/wellwoo/life_ren.gif\" width=\"100\" height=\"60\" /></td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
		} else
		{
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/risk_evaluate/report/wellwoo/life_ren.gif\" width=\"100\" height=\"60\" /></td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"175\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
		}
		return sb.toString();
	}

	public static String getBMI(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag < 18.5D)
		{
			sb.append("<span class=\"red\"/>");
			return sb.toString();
		}
		if (flag > 24F)
		{
			sb.append("<span class=\"red\"/>");
			return sb.toString();
		} else
		{
			return null;
		}
	}

	public static String getCholesterol(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag >= 5.1799999999999997D)
		{
			sb.append("<span class=\"red\"/>");
			return sb.toString();
		} else
		{
			return null;
		}
	}

	public static String getLdlc(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag >= 3.3700000000000001D)
		{
			sb.append("<span class=\"red\"/>");
			return sb.toString();
		} else
		{
			return null;
		}
	}

	public static String getWaistLine(float flag, int gender)
	{
		StringBuffer sb = new StringBuffer();
		if (gender == 1)
		{
			if (flag >= 85F)
			{
				sb.append("<span class=\"red\"/>");
				return sb.toString();
			}
		} else
		if (flag >= 80F)
		{
			sb.append("<span class=\"red\"/>");
			return sb.toString();
		}
		return null;
	}

	public static String getTriglycerin(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag >= 1.7D)
		{
			sb.append("<span class=\"red\"/>");
			return sb.toString();
		} else
		{
			return null;
		}
	}

	public static String getHdlc(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag < 1.04D)
		{
			sb.append("<span class=\"red\"/>");
			return sb.toString();
		} else
		{
			return null;
		}
	}

	public static String getBpLevel(float flag1, float flag2)
	{
		StringBuffer sb = new StringBuffer();
		if (flag1 >= 120F)
		{
			sb.append("<span class=\"red\"/>");
			return sb.toString();
		}
		if (flag2 >= 80F)
		{
			sb.append("<span class=\"red\"/>");
			return sb.toString();
		} else
		{
			return null;
		}
	}

	public static String getFastingBg(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag >= 5.5999999999999996D)
		{
			sb.append("<span class=\"red\"/>");
			return sb.toString();
		} else
		{
			return null;
		}
	}

	public static String getWaistLineState(float flag, int gender)
	{
		StringBuffer sb = new StringBuffer();
		if (gender == 1)
		{
			if (flag >= 85F)
			{
				sb.append("腰围过大");
				return sb.toString();
			}
		} else
		if (flag >= 80F)
		{
			sb.append("腰围过大，");
			return sb.toString();
		}
		return "";
	}

	public static String getTriglycerinState(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag >= 1.7D)
		{
			sb.append("血脂紊乱，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getHdlcState(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag < 1.04D)
		{
			sb.append("血脂紊乱，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getBpLevelState(float flag1, float flag2)
	{
		StringBuffer sb = new StringBuffer();
		if (flag1 >= 120F)
		{
			sb.append("血压偏高，");
			return sb.toString();
		}
		if (flag2 >= 80F)
		{
			sb.append("血压偏高，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getFastingBgState(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag >= 5.5999999999999996D)
		{
			sb.append("血糖偏高，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getWaistLinebygender(int gender)
	{
		StringBuffer sb = new StringBuffer();
		if (gender == 1)
		{
			sb.append(" <85 ");
			return sb.toString();
		} else
		{
			sb.append("<80");
			return sb.toString();
		}
	}

	public static String getWaistLineState1(float flag, int gender)
	{
		StringBuffer sb = new StringBuffer();
		if (gender == 1)
		{
			if (flag >= 85F)
			{
				sb.append("控制体重，");
				return sb.toString();
			}
		} else
		if (flag >= 80F)
		{
			sb.append("控制体重，");
			return sb.toString();
		}
		return "";
	}

	public static String getTriglycerinState1(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag >= 1.7D)
		{
			sb.append("改善血脂紊乱，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getHdlcState1(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag < 1.04D)
		{
			sb.append("改善血脂紊乱，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getBpLevelState1(float flag1, float flag2)
	{
		StringBuffer sb = new StringBuffer();
		if (flag1 >= 120F)
		{
			sb.append("控制血压水平，");
			return sb.toString();
		}
		if (flag2 >= 80F)
		{
			sb.append("控制血压水平，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getFastingBgState1(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag >= 5.5999999999999996D)
		{
			sb.append("控制血糖水平，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getWaistLineState2(float flag, int gender)
	{
		StringBuffer sb = new StringBuffer();
		if (gender == 1)
		{
			if (flag >= 85F)
			{
				sb.append("改善腰围，");
				return sb.toString();
			}
		} else
		if (flag >= 80F)
		{
			sb.append("改善腰围，");
			return sb.toString();
		}
		return "";
	}

	public static String getTriglycerinState2(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag >= 1.7D)
		{
			sb.append("降低甘油三酯水平，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getHdlcState2(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag < 1.04D)
		{
			sb.append("改善血脂紊乱，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getBpLevelState2(float flag1, float flag2)
	{
		StringBuffer sb = new StringBuffer();
		if (flag1 >= 120F)
		{
			sb.append("控制血压，");
			return sb.toString();
		}
		if (flag2 >= 80F)
		{
			sb.append("控制血压，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getFastingBgState2(float flag)
	{
		StringBuffer sb = new StringBuffer();
		if ((double)flag >= 5.5999999999999996D)
		{
			sb.append("控制血糖，");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getMsSuggest(float flag1, float flag2, float flag3, float flag4, float flag5, float flag6, int flag7)
	{
		StringBuffer sb = new StringBuffer();
		int i = 0;
		if (flag7 == 1)
		{
			if (flag1 >= 85F)
				i++;
		} else
		if (flag1 >= 80F)
			i++;
		if ((double)flag2 >= 1.7D)
			i++;
		if ((double)flag3 < 1.04D)
			i++;
		if (flag4 >= 120F)
			i++;
		else
		if (flag5 >= 80F)
			i++;
		if ((double)flag6 >= 5.5999999999999996D)
			i++;
		if (i == 0)
		{
			sb.append(" <div id=\"font_txt\"> <p>希望您继续保持健康的生活方式，预防代谢综合征的发生。</p></div> ");
			return sb.toString();
		}
		if (((double)flag2 >= 1.7D) & ((double)flag3 < 1.04D))
		{
			sb.append((new StringBuilder(" <div id=\"font_txt\"> <p> 代谢综合征是指在个体中多种代谢异常情况集结存在的现象，这些代谢异常大多为动脉硬化性心、脑及周围血管病（简称心血管疾病）的危险因素，故代谢综合征患者是心血管疾病的高危人群。</p><p align=\"left\" > 您目前存在异常的指标包括<u><span class=\"STYLE1\">")).append(getWaistLineState(flag1, flag7)).append(getHdlcState(flag3)).append(getBpLevelState(flag4, flag5)).append(getFastingBgState(flag6)).append("</span></u><u></u>您可以通过<u><span class=\"STYLE1\">").append(getWaistLineState1(flag1, flag7)).append(getHdlcState1(flag3)).append(getBpLevelState1(flag4, flag5)).append(getFastingBgState1(flag6)).append("</span></u>").append("<u><span class=\"STYLE1\">增加体力活动水平</span></u>等治疗性生活方式改善措施来有效预防代谢综合征的发生。</p>").append("<span class=\"STYLE3\"><img align=\"left\" src=\"/jsp/risk_evaluate/report/wellwoo/light.emf\" alt=\"\" width=\"20\" height=\"25\" />    </span>").append(" <p><span class=\"STYLE3\"> &nbsp;</span> 如需获得详细的指导方案，请在医生指导下参考您的<strong>“膳食处方”</strong>和<strong>“运动处方”</strong>来开始您的代谢综合征改善行动。</p>").append("</div> ").toString());
			return sb.toString();
		} else
		{
			sb.append((new StringBuilder(" <div id=\"font_txt\"> <p> 代谢综合征是指在个体中多种代谢异常情况集结存在的现象，这些代谢异常大多为动脉硬化性心、脑及周围血管病（简称心血管疾病）的危险因素，故代谢综合征患者是心血管疾病的高危人群。</p><p align=\"left\" > 您目前存在异常的指标包括<u><span class=\"STYLE1\">")).append(getWaistLineState(flag1, flag7)).append(getTriglycerinState(flag2)).append(getHdlcState(flag3)).append(getBpLevelState(flag4, flag5)).append(getFastingBgState(flag6)).append("</span></u><u></u>您可以通过<u><span class=\"STYLE1\">").append(getWaistLineState1(flag1, flag7)).append(getTriglycerinState1(flag2)).append(getHdlcState1(flag3)).append(getBpLevelState1(flag4, flag5)).append(getFastingBgState1(flag6)).append("</span></u>").append("<u><span class=\"STYLE1\">增加体力活动水平</span></u>等治疗性生活方式改善措施来有效预防代谢综合征的发生。</p>").append("<span class=\"STYLE3\"><img align=\"left\" src=\"/jsp/risk_evaluate/report/wellwoo/light.emf\" alt=\"\" width=\"20\" height=\"25\" />    </span>").append(" <p><span class=\"STYLE3\"> &nbsp;</span> 如需获得详细的指导方案，请在医生指导下参考您的<strong>“膳食处方”</strong>和<strong>“运动处方”</strong>来开始您的代谢综合征改善行动。</p>").append("</div> ").toString());
			return sb.toString();
		}
	}

	public static String getMsState(float flag1, float flag2, float flag3, float flag4, float flag5, float flag6, int flag7)
	{
		int i = 0;
		if (flag7 == 1)
		{
			if (flag1 >= 85F)
				i++;
		} else
		if (flag1 >= 80F)
			i++;
		if ((double)flag2 >= 1.7D)
			i++;
		if ((double)flag3 < 1.04D)
			i++;
		if (flag4 >= 130F)
			i++;
		else
		if (flag5 >= 85F)
			i++;
		if ((double)flag6 >= 5.5999999999999996D)
			i++;
		if (i == 0)
			return "低危";
		if (i > 2)
			return "代谢综合征";
		else
			return "高危";
	}

	public static String getMsLevel(float flag1, float flag2, float flag3, float flag4, float flag5, float flag6, int flag7)
	{
		int i = 0;
		if (flag7 == 1)
		{
			if (flag1 >= 85F)
				i++;
		} else
		if (flag1 >= 80F)
			i++;
		if ((double)flag2 >= 1.7D)
			i++;
		if ((double)flag3 < 1.04D)
			i++;
		if (flag4 >= 130F)
			i++;
		else
		if (flag5 >= 85F)
			i++;
		if ((double)flag6 >= 5.5999999999999996D)
			i++;
		StringBuffer sb = new StringBuffer();
		if (i == 0)
		{
			sb.append("<td width=\"220\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/risk_evaluate/report/wellwoo/arrowhead.png\" width=\"25\" height=\"60\" /></td>");
			sb.append("<td width=\"220\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"220\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
		} else
		if (i > 2)
		{
			sb.append("<td width=\"220\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"220\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"220\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/risk_evaluate/report/wellwoo/arrowhead.png\" width=\"25\" height=\"60\" /></td>");
		} else
		{
			sb.append("<td width=\"220\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
			sb.append("<td width=\"220\" align=\"center\" valign=\"bottom\"><img src=\"/jsp/risk_evaluate/report/wellwoo/arrowhead.png\" width=\"25\" height=\"60\" /></td>");
			sb.append("<td width=\"220\" align=\"center\" valign=\"bottom\">&nbsp;</td>");
		}
		return sb.toString();
	}

	public static String getLifeString(float flag)
	{
		if (flag >= 80F)
			return "优秀";
		if (flag >= 60F)
			return "良好";
		if (flag >= 40F)
			return "一般";
		else
			return "差";
	}


	public static String getNotsports(String notSports)
	{
		if (Util.checkStr(notSports))
		{
			StringBuffer sb = new StringBuffer();
			sb.append("<div class=\"info_tit\">哪些情况下不宜进行运动？</div>");
			sb.append("<div class=\"info_tb\">");
			sb.append("<img src=\"/jsp/risk_evaluate/report/wellwoo/sp2.gif\" alt=\"\" width=\"56\" height=\"59\" />");
			sb.append(notSports);
			sb.append("</div>");
			return sb.toString();
		} else
		{
			return "";
		}
	}

	public static String getIntensity(int flag)
	{
		if (flag == 1)
			return "很轻松";
		if (flag == 2)
			return "轻松";
		if (flag == 3)
			return "有些吃力";
		if (flag == 4)
			return "吃力";
		if (flag == 5)
			return "很吃力";
		if (flag == 6)
			return "精疲力竭";
		else
			return "";
	}


	public static String getServerTime()
	{
		return DateUtil.getReturnTime("yyyy-MM-dd");
	}


	public static String getPressureInfo(int l_score)
	{
		StringBuffer sb = new StringBuffer();
		String flag = "<img src=\"/jsp/risk_evaluate/report/wellwoo/jiantou.gif\" width=\"16\" height=\"18\" />";
		sb.append("<tr>");
		if (l_score < 20)
			sb.append("<td align=\"center\">").append(flag).append("</td>");
		else
			sb.append("<td align=\"center\">&nbsp;</td>");
		if (l_score >= 20 && l_score < 27)
			sb.append("<td align=\"center\">").append(flag).append("</td>");
		else
			sb.append("<td align=\"center\">&nbsp;</td>");
		if (l_score >= 27 && l_score < 30)
			sb.append("<td align=\"center\">").append(flag).append("</td>");
		else
			sb.append("<td align=\"center\">&nbsp;</td>");
		if (l_score >= 30 && l_score < 37)
			sb.append("<td align=\"center\">").append(flag).append("</td>");
		else
			sb.append("<td align=\"center\">&nbsp;</td>");
		if (l_score >= 37)
			sb.append("<td align=\"center\">").append(flag).append("</td>");
		else
			sb.append("<td align=\"center\">&nbsp;</td>");
		sb.append("</tr>");
		return sb.toString();
	}

	public static String getPressureNote(int l_score, int s_score)
	{
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_flag = new StringBuffer();
		if (l_score < 7)
			sb_flag.append("<p>您此次L量表评分为").append(l_score).append("分，说明您的回答能真实地反映您的情况，该问卷结果有效。您能够诚恳地面对对自己不利的评价，为人真诚，不好掩饰。 </p>");
		else
			sb_flag.append("<p>您此次L量表评分为").append(l_score).append("分，说明您回答某些题时不够诚实，该问卷结果无效。您喜欢对自己不利的评价掩饰，为人容易表现出虚伪、圆滑，也可能由于自身定位不准确，自我认识不清或理解能力不足造成的。 </p>");
		if (s_score < 20)
		{
			sb.append("<div id=\"readme_nobold\">");
			sb.append("B型：您具有较典型的B型性格特征： <br />");
			sb.append("您性情随和，顺从，随遇而安，不喜争斗；生活方式悠闲自在，对成败得失看得较淡，不太在意成就的大小，有时缺乏抱负和主见；时间观念不强，对于布置给自己的任务能否完成，从不着急；情绪不易激动，容易与人相处，人际关系较好。 </p>");
			sb.append(sb_flag);
			sb.append("建议：<br />");
			sb.append("您的工作生活悠闲自得，但现代社会节奏日益加快，您需要提速以便更有效率地学习和工作。 <br />");
			sb.append("1、制定一个符合自己实际能力并可实现的目标； <br />");
			sb.append("2、对工作进行有效的规划，制定出完成的时间；如果您要完成的任务有很多，首先区分他们的先后、主次次序，完成一件工作后再做第二件； <br />");
			sb.append("3、勇于弘扬自己的优点：一些B型性格的人认为在别人面前承认自己的优点非常不自在。其实不然，正是我们自身的优点使我们迎来一个又一个机会。 <br />");
			sb.append("4、敢于交流和发表自己的看法，对待不公平的人与事，要适当的宣泄自己的情感。 </p>");
			sb.append("</div>");
		} else
		if (s_score < 27)
		{
			sb.append("<div id=\"readme_nobold\">");
			sb.append("中间型<br />");
			sb.append("现实中大多数人可能都跟您一样，居于典型的A型和B型性格之间。您不像典型的A型性格那样追求成就，好胜心切；您也不像典型B型那样内向、逆来顺受。您有一定的时间观念和竞争意识，总之您同时具有A型和B型性格特征，得分越高，A型倾向（进取、好胜、冲动）越明显；得分越低，B型倾向（悠然、顺从、沉稳）越明显。 </p>");
			sb.append(sb_flag);
			sb.append("建议<br />");
			sb.append("根据您的性格是更多的倾向A型还是B型，有不同的改善方法。 <br />");
			sb.append("如果您更多地倾向A型，那么请您减缓工作生活的节奏，为每件事情制定好计划，不追赶时间；学会承认自己的不足，接受现实；学会微笑和夸奖别人；为自己留出一定的休闲时间，培养自己的体育、音乐、文艺方面的业余爱好，享受生活。 <br />");
			sb.append("如果您更多的倾向B型，那么您可能很会享受生活，人际关系较好。您也许可以尝试为自己制定更高的目标，确定每件事的完成时间，以获得更大的成就感。 </p>");
			sb.append("</div>");
		} else
		if (s_score < 30)
		{
			sb.append("<div id=\"readme_nobold\">");
			sb.append("中间型：<br />");
			sb.append("现实中大多数人可能都跟您一样，居于典型的A型和B型性格之间。您不像典型的A型性格那样追求成就，好胜心切；您也不像典型B型那样内向、逆来顺受。您有一定的时间观念和竞争意识，总之您同时具有A型和B型性格特征，得分越高，A型倾向（进取、好胜、冲动）越明显；得分越低，B型倾向（悠然、顺从、沉稳）越明显。 </p>");
			sb.append(sb_flag);
			sb.append("建议：<br />");
			sb.append("根据您的性格是更多的倾向A型还是B型，有不同的改善方法。 <br />");
			sb.append("如果您更多地倾向A型，那么请您减缓工作生活的节奏，为每件事情制定好计划，不追赶时间；学会承认自己的不足，接受现实；学会微笑和夸奖别人；为自己留出一定的休闲时间，培养自己的体育、音乐、文艺方面的业余爱好，享受生活。 <br />");
			sb.append("如果您更多的倾向B型，那么您可能很会享受生活，人际关系较好。您也许可以尝试为自己制定更高的目标，确定每件事的完成时间，以获得更大的成就感。 </p>");
			sb.append("</div>");
		} else
		if (s_score < 37)
		{
			sb.append("<div id=\"readme_nobold\">");
			sb.append("中间型<br />");
			sb.append("现实中大多数人可能都跟您一样，居于典型的A型和B型性格之间。您不像典型的A型性格那样追求成就，好胜心切；您也不像典型B型那样内向、逆来顺受。您有一定的时间观念和竞争意识，总之您同时具有A型和B型性格特征，得分越高，A型倾向（进取、好胜、冲动）越明显；得分越低，B型倾向（悠然、顺从、沉稳）越明显。 </p>");
			sb.append(sb_flag);
			sb.append("建议<br />");
			sb.append("根据您的性格是更多的倾向A型还是B型，有不同的改善方法。 <br />");
			sb.append("如果您更多地倾向A型，那么请您减缓工作生活的节奏，为每件事情制定好计划，不追赶时间；学会承认自己的不足，接受现实；学会微笑和夸奖别人；为自己留出一定的休闲时间，培养自己的体育、音乐、文艺方面的业余爱好，享受生活。 <br />");
			sb.append("如果您更多的倾向B型，那么您可能很会享受生活，人际关系较好。您也许可以尝试为自己制定更高的目标，确定每件事的完成时间，以获得更大的成就感。 </p>");
			sb.append("</div>");
		} else
		{
			sb.append("<div id=\"readme_nobold\">");
			sb.append("A型：您具有较典型的A型性格特征： <br />");
			sb.append("您好胜心强，追求成就，有很强的事业心，做工作总要尽善尽美，有很强的竞争欲；时间观念强，总觉得时间紧张、不够用，做事匆忙、急躁，不干完事不踏实；反应快而强烈，行动迅速；情绪易波动，易被激怒，对人怀有戒心，人际关系比较紧张。 </p>");
			sb.append(sb_flag);
			sb.append("建议：<br />");
			sb.append("您的生活充满激情但行事匆匆。科学家们发现A型性格与心脏病关系密切，您需要采 <br />");
			sb.append("取一些措施让自己慢下来。 <br />");
			sb.append("1、 每日记录自己的匆忙事例，检查出现匆忙的原因，每周小结一次，及时定制解决方案。 <br />");
			sb.append("2、放弃自己要做一个超人的意图，勇于接受自己的缺点和不足，相信他人也能做好工作。 <br />");
			sb.append("3、尽量淡化荣誉及成就感，注意学习生活与工作的和谐及生活质量。 <br />");
			sb.append("4、放弃同时思考多个问题或完成几件事的习惯，在时间安排上井然有序，留有余地，踏实稳健地干每一件事情。 <br />");
			sb.append("5、主动控制调节自己的情绪，避免争论，冲突，以幽默的态度化解生活的挫折。 <br />");
			sb.append("6、善于倾听他人的意见，不要随意打断别人的讲话。学会对所有认识的人微笑。 <br />");
			sb.append("7、有目的地培养耐心和悠闲感。每天留给自己一定的时间，散散步，听听音乐，放松一下。 <br />");
			sb.append("8、多交知心朋友，建立归宿感，安全感，多与朋友交流自己内心的真切感受。 </p>");
			sb.append("</div>");
		}
		return sb.toString();
	}

	public static String getPstriInfo(int score)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<tr>");
		if (score < 43)
			sb.append("<td align=\"center\"><img src=\"/jsp/risk_evaluate/report/wellwoo/jiantou.gif\" width=\"16\" height=\"18\" /></td>");
		else
			sb.append("<td align=\"center\">&nbsp;</td>");
		if (score >= 43 && score <= 65)
			sb.append("<td align=\"center\"><img src=\"/jsp/risk_evaluate/report/wellwoo/jiantou.gif\" width=\"16\" height=\"18\" /></td>");
		else
			sb.append("<td align=\"center\">&nbsp;</td>");
		if (score > 65)
			sb.append("<td align=\"center\"><img src=\"/jsp/risk_evaluate/report/wellwoo/jiantou.gif\" width=\"16\" height=\"18\" /></td>");
		else
			sb.append("<td align=\"center\">&nbsp;</td>");
		sb.append("</tr>");
		return sb.toString();
	}

	public static String getPstriNote(int score)
	{
		StringBuffer sb = new StringBuffer();
		if (score < 43)
		{
			sb.append("<div id=\"readme_nobold\">");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您需要更主动些，在工作、社交、娱乐等活动上多寻求些刺激，增加自己的压力。您也许可以尝试制定更高的目标，留给自己更多的工作，规定更紧的期限。事实上，适当的压力有助于您保持旺盛的精力和敏捷的思维。做放松练习对您没有用，它只会让您更加放松。         <br />");
			sb.append("建议：<br />");
			sb.append("1、健康饮食，平衡膳食能够有效稳定情绪。 <br />");
			sb.append("2、适当运动，如慢跑、散步、游泳等。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。 <br />");
			sb.append("3、睡眠充足，尽量固定睡觉和起床的时间。 <br />");
			sb.append("4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上而不是头疼的问题上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。 <br />");
			sb.append("5、学会坚守底线，不要接受不必要的负担。 </p>");
			sb.append("</div>");
		} else
		if (score <= 65)
		{
			sb.append("<div id=\"readme_nobold\">");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您可以适度寻求刺激，比如参与文娱活动，增加生活的乐趣。您也许可以多接触新鲜的人和事，并表达出自己的感受，是惊喜、快乐还是厌恶。您能够感受到压力，但是您常常把它忽略掉，您需要增加压力反应的强度，适度的压力反应会让您感受到生活的多姿多彩。<br />");
			sb.append("建议：<br />");
			sb.append("1、健康饮食，平衡膳食能够有效稳定情绪。 <br />");
			sb.append("2、适当运动，如慢跑、散步、游泳等。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。 <br />");
			sb.append("3、睡眠充足，尽量固定睡觉和起床的时间。 <br />");
			sb.append("4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上而不是头疼的问题上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。 <br />");
			sb.append("5、学会坚守底线，不要接受不必要的负担。 </p>");
			sb.append("</div>");
		} else
		{
			sb.append("<div id=\"readme_nobold\">");
			sb.append("<p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您需要仔细反省自己对压力做出的反应，并学习在压力出现时，控制自己的肌肉紧张，以消除生理激活反应。以下几点将有助于您减轻压力反应。 </p>");
			sb.append("建议：<br />");
			sb.append("1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，以便在日后尽量避免这些事件的发生，或是有所针对的各个击破。 <br />");
			sb.append("2、了解自己最易受制于压力的时刻并在这一时刻到来之前做好准备。你最易受到影响的时候是在早晨？或是周一？抑或是冬季？ <br />");
			sb.append("3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应。在同等压力下，做运动的人比不做运动的人血压要低，心率要慢。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。 <br />");
			sb.append("4、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉能使您心情舒畅。有条件的话，不妨出去旅行一段时间，让自己的心灵休息一会儿。 <br />");
			sb.append("5、深呼吸。将注意力转移到腹部，在用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中。呼气时想象空气反向流动。 </p>");
			sb.append("</div>");
		}
		return sb.toString();
	}

	public static String getHealthInfo(int gender, int score)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\"><tr class=\"info_tb\">");
		switch (gender)
		{
		case 2: // '\002'
			sb.append("<td width=\"33%\" height=\"40\" align=\"center\" bgcolor=\"#FF0000\"><strong>疑似心理疾患&lt;25</strong></td>");
			sb.append("<td width=\"33%\" align=\"center\" bgcolor=\"#FF6600\"><strong>橙色预警</strong></td>");
			sb.append("<td width=\"34%\" align=\"center\" bgcolor=\"#99CC00\"><strong>正常&gt;45</strong><strong></strong></td>");
			sb.append("</tr><tr>");
			if (score < 25)
				sb.append("<td align=\"center\"><img src=\"/jsp/risk_evaluate/report/wellwoo/jiantou.gif\" width=\"16\" height=\"18\" /></td>");
			else
				sb.append("<td align=\"center\">&nbsp;</td>");
			if (score >= 25 && score <= 45)
				sb.append("<td align=\"center\"><img src=\"/jsp/risk_evaluate/report/wellwoo/jiantou.gif\" width=\"16\" height=\"18\" /></td>");
			else
				sb.append("<td align=\"center\">&nbsp;</td>");
			if (score > 45)
				sb.append("<td align=\"center\"><img src=\"/jsp/risk_evaluate/report/wellwoo/jiantou.gif\" width=\"16\" height=\"18\" /></td>");
			else
				sb.append("<td align=\"center\">&nbsp;</td>");
			break;

		default:
			sb.append("<td width=\"33%\" height=\"40\" align=\"center\" bgcolor=\"#FF0000\"><strong>疑似心理疾患&lt;10</strong></td>");
			sb.append("<td width=\"33%\" align=\"center\" bgcolor=\"#FF6600\"><strong>橙色预警</strong></td>");
			sb.append("<td width=\"34%\" align=\"center\" bgcolor=\"#99CC00\"><strong>正常&gt;65</strong><strong></strong></td>");
			sb.append("</tr><tr>");
			if (score < 10)
				sb.append("<td align=\"center\"><img src=\"/jsp/risk_evaluate/report/wellwoo/jiantou.gif\" width=\"16\" height=\"18\" /></td>");
			else
				sb.append("<td align=\"center\">&nbsp;</td>");
			if (score >= 10 && score <= 65)
				sb.append("<td align=\"center\"><img src=\"/jsp/risk_evaluate/report/wellwoo/jiantou.gif\" width=\"16\" height=\"18\" /></td>");
			else
				sb.append("<td align=\"center\">&nbsp;</td>");
			if (score > 65)
				sb.append("<td align=\"center\"><img src=\"/jsp/risk_evaluate/report/wellwoo/jiantou.gif\" width=\"16\" height=\"18\" /></td>");
			else
				sb.append("<td align=\"center\">&nbsp;</td>");
			break;
		}
		sb.append("</tr></table>");
		return sb.toString();
	}

	public static String getHealthNote(int gender, int score)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<div id=\"readme_nobold\">");
		switch (gender)
		{
		case 2: // '\002'
			if (score < 25)
			{
				sb.append("特别声明：此量表仅用于自测，不具有诊断价值。请确定您是否有文字理解错误、故意 犯错、没有认真作答等情况。若无，您需要寻求专业人士的帮助，请咨询心理医生。");
				break;
			}
			if (score <= 45)
			{
				sb.append("建议：");
				sb.append("您可以尝试以下建议，也许有助于您恢复平稳的心境。<br />");
				sb.append("1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，有所针对的各个击破。<br />");
				sb.append("2、健康饮食。平衡膳食能够有效稳定情绪。<br />");
				sb.append("3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应，增加血液循环，对改善情绪有好处。<br />");
				sb.append("4、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。<br />");
				sb.append("5、睡眠充足。压力会干扰人们放松的状态，影响睡眠质量，从而导致疲劳和问题处理能力下降。为了能够保证好的睡眠，应该尽量固定睡觉和起床的时间。<br />");
				sb.append("6、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。<br />");
				sb.append("7、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好的释放压力并塑造积极的人生态度。<br />");
				sb.append("8、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉不但可增强人们的友谊和信任，更能使您心情舒畅。<br />");
				sb.append("9、学会接受现实。现实中没有人能事事如意，对于一些不能改变的事实，慢慢地试着去接受它，改变一下自己看待问题的角度和心态，也许心情会好一些。");
			} else
			{
				sb.append("建议：");
				sb.append("下面是一些常见的提高压力应对能力的技巧，希望对您有所帮助。<br />");
				sb.append("1、健康饮食，平衡膳食能够有效稳定情绪。<br />");
				sb.append("2、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应。在同等压力下，做运动的人比不做运动的人血压要低，心率要慢。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。<br />");
				sb.append("3、睡眠充足，尽量固定睡觉和起床的时间。<br />");
				sb.append("4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。<br />");
				sb.append("5、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好地释放压力并塑造积极的人生态度。<br />");
				sb.append("6、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。<br/>");
				sb.append("7、回忆快乐的事，给自己正性的暗示，将有助于您积极向上地生活。 </p>");
			}
			break;

		default:
			if (score < 10)
			{
				sb.append("特别声明：此量表仅用于自测，不具有诊断价值。请确定您是否有文字理解错误、故意 犯错、没有认真作答等情况。若无，您需要寻求专业人士的帮助，请咨询心理医生。");
				break;
			}
			if (score <= 65)
			{
				sb.append("建议：");
				sb.append("您可以尝试以下建议，也许有助于您恢复平稳的心境。<br />");
				sb.append("1、将造成您压力的情境或事件一一列出。这可以让您了解是什么导致了紧张情绪，有所针对的各个击破。<br />");
				sb.append("2、健康饮食。平衡膳食能够有效稳定情绪。<br />");
				sb.append("3、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应，增加血液循环，对改善情绪有好处。<br />");
				sb.append("4、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。<br />");
				sb.append("5、睡眠充足。压力会干扰人们放松的状态，影响睡眠质量，从而导致疲劳和问题处理能力下降。为了能够保证好的睡眠，应该尽量固定睡觉和起床的时间。<br />");
				sb.append("6、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。<br />");
				sb.append("7、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好的释放压力并塑造积极的人生态度。<br />");
				sb.append("8、释放压抑的情绪。与家人、亲友推心置腹的交流和倾诉不但可增强人们的友谊和信任，更能使您心情舒畅。<br />");
				sb.append("9、学会接受现实。现实中没有人能事事如意，对于一些不能改变的事实，慢慢地试着去接受它，改变一下自己看待问题的角度和心态，也许心情会好一些。");
			} else
			{
				sb.append("建议：");
				sb.append("下面是一些常见的提高压力应对能力的技巧，希望对您有所帮助。<br />");
				sb.append("1、健康饮食，平衡膳食能够有效稳定情绪。<br />");
				sb.append("2、适当运动，如慢跑、散步、游泳等。运动能减轻人们对压力的生理反应。在同等压力下，做运动的人比不做运动的人血压要低，心率要慢。运动有利于增加血液循环，提高机体含氧量，这样对改善情绪有好处。<br />");
				sb.append("3、睡眠充足，尽量固定睡觉和起床的时间。<br />");
				sb.append("4、尽情享受自己的业余爱好。做自己感兴趣的事情能使自己将注意力集中在愉快的事情上，这能让您精神有所寄托，增加您的愉悦感和成就感，安抚您的情绪。<br />");
				sb.append("5、积极参与社交，多接触乐观向上的人与事。与亲朋好友的愉悦交流、良好的社会关系能够很好的释放压力并塑造积极的人生态度。<br />");
				sb.append("6、深呼吸放松，将注意力转移到腹部，用鼻子吸气的同时鼓起肚子，呼气收腹。吸气时，闭眼并想象空气通过鼻子进入到肺中，呼气时想象空气反向流动。<br />");
				sb.append("7、回忆快乐的事，给自己正性的暗示，将有助于您积极向上地生活。 </p>");
			}
			break;
		}
		sb.append("</div>");
		return sb.toString();
	}


	

	public static String getRiskLevel(int le)
	{
		String leTxt = "--";
		if (le == 1)
			leTxt = "低风险";
		else
		if (le == 2)
			leTxt = "较低风险";
		else
		if (le == 3)
			leTxt = "中等风险";
		else
		if (le == 4)
			leTxt = "较高风险";
		else
		if (le == 5)
			leTxt = "高风险";
		else
			leTxt = "--";
		return leTxt;
	}

	
}
