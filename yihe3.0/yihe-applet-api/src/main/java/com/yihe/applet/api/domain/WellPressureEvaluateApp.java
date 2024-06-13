package com.yihe.applet.api.domain;

import java.io.Serializable;

/** 
 *压力评估结果实体类
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2019年7月26日 下午2:50:36 
 *@version 1.0 
 */
public class WellPressureEvaluateApp implements Serializable{
	private int l;
	private int ch;
	private int th;
	private int pstri;
	private int health;
	public WellPressureEvaluateApp()
	{
		l = 0;
		ch = 0;
		th = 0;
		pstri = 0;
		health = 0;
	}
	private static final long serialVersionUID = 7791183577378662257L;
    //id
	private Integer id;
	//压力评估数据
	private String evaluateData;
	//压力评估类型
	private Integer evaluateType;
	//压力评估分数
	private String evaluateScore;
	//用户code
	private String userCode;
	//压力评估日期
	private String evaluateDate;
	//压力日期
	private String doDate;
	//是否删除
	private Integer isDel;
	//压力选择数据
	private String evaluateSelect;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEvaluateData() {
		return evaluateData;
	}

	public void setEvaluateData(String evaluateData) {
		this.evaluateData = evaluateData;
	}

	public Integer getEvaluateType() {
		return evaluateType;
	}

	public void setEvaluateType(Integer evaluateType) {
		this.evaluateType = evaluateType;
	}

	public String getEvaluateScore() {
		return evaluateScore;
	}

	public void setEvaluateScore(String evaluateScore) {
		this.evaluateScore = evaluateScore;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getEvaluateDate() {
		return evaluateDate;
	}

	public void setEvaluateDate(String evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	public String getDoDate() {
		return doDate;
	}

	public void setDoDate(String doDate) {
		this.doDate = doDate;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getEvaluateSelect() {
		return evaluateSelect;
	}

	public void setEvaluateSelect(String evaluateSelect) {
		this.evaluateSelect = evaluateSelect;
	}

	public WellPressureEvaluateApp(Integer id, String evaluateData, Integer evaluateType, String evaluateScore,
			String userCode, String evaluateDate, String doDate, Integer isDel, String evaluateSelect) {
		super();
		this.id = id;
		this.evaluateData = evaluateData;
		this.evaluateType = evaluateType;
		this.evaluateScore = evaluateScore;
		this.userCode = userCode;
		this.evaluateDate = evaluateDate;
		this.doDate = doDate;
		this.isDel = isDel;
		this.evaluateSelect = evaluateSelect;
	}

	
	
	public String queryEvaluateScore(String queList, int type)
	{
		//判断第一个字符是否为“[”
		if (queList.startsWith("[")) {
			queList = queList.substring(1);
		}

		//判断最后一个字符是否为“]”
		if (queList.endsWith("]")) {
			queList = queList.substring(0,queList.length() - 1);
		}

		//0,1,1
		String ques[] = queList.split(",");
		for (int i = 0; i < ques.length; i++)
			if (checkStr(ques[i]))
			{
				 
				 
					if (type == 1)
					{
						initA(i + 1, ques[i]);
						if (i == ques.length - 1)
							return (new StringBuilder(String.valueOf(l))).append(";").append(ch).append(";").append(th).toString();
					} else
					if (type == 2)
					{
						initPstri(i + 1, ques[i]);
						if (i == ques.length - 1)
							return (new StringBuilder(String.valueOf(pstri))).toString();
					} else
					if (type == 3)
					{
						 
						initHealth(i + 1, ques[i]);
						if (i == ques.length - 1)
							return (new StringBuilder(String.valueOf(health))).toString();
					}
			}

		return "0";
	}
	
	private void initA(int i, String value)
	{
		switch (i)
		{
		case 8: // '\b'
		case 13: // '\r'
		case 20: // '\024'
		case 24: // '\030'
		case 33: // '!'
		case 37: // '%'
		case 43: // '+'
		case 48: // '0'
		case 52: // '4'
		case 56: // '8'
			l += getint(value);
			break;

		case 1: // '\001'
		case 2: // '\002'
		case 3: // '\003'
		case 6: // '\006'
		case 7: // '\007'
		case 10: // '\n'
		case 11: // '\013'
		case 14: // '\016'
		case 19: // '\023'
		case 21: // '\025'
		case 22: // '\026'
		case 26: // '\032'
		case 27: // '\033'
		case 30: // '\036'
		case 32: // ' '
		case 34: // '"'
		case 40: // '('
		case 42: // '*'
		case 44: // ','
		case 46: // '.'
		case 50: // '2'
		case 53: // '5'
		case 54: // '6'
		case 55: // '7'
		case 58: // ':'
			ch += getint(value);
			break;

		case 4: // '\004'
		case 5: // '\005'
		case 9: // '\t'
		case 12: // '\f'
		case 15: // '\017'
		case 16: // '\020'
		case 17: // '\021'
		case 18: // '\022'
		case 23: // '\027'
		case 25: // '\031'
		case 28: // '\034'
		case 29: // '\035'
		case 31: // '\037'
		case 35: // '#'
		case 36: // '$'
		case 38: // '&'
		case 39: // '\''
		case 41: // ')'
		case 45: // '-'
		case 47: // '/'
		case 49: // '1'
		case 51: // '3'
		case 57: // '9'
		case 59: // ';'
		case 60: // '<'
			th += getint(value);
			break;
		}
	}

	private void initPstri(int i, String value)
	{
		pstri += getint(value);
	}

	private void initHealth(int i, String value)
	{
		health += getint(value);
	}
	
	public static boolean checkStr(String str)
	{
		return str != null && !"".equals(str);
	}

	public static int getint(String s)
	{
		int i = 0;
		if (s == null)
			return 0;
		try
		{
			i = Integer.parseInt(s);
		}
		catch (Exception e)
		{
			return 0;
		}
		return i;
	}

}

