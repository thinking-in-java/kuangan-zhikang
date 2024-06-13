package com.yihe.system.domain;

/**
 * 数据监测管理封装实体
 * 
 * @author Administrator
 *
 */
public class TestingVOJO {
	/**
	 * 用户编号
	 */
	private String usercode;
	/**
	 * 用户账号
	 */
	private String username;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 用户性别
	 */
	private String sex;
	/**
	 * 最新检测日期
	 * @return
	 */
	private String testTime;
	/**
	 * 检测项目数
	 * @return
	 */
	private String testNum;
	/**
	 * 异常
	 * @return
	 */
	private String abnormal;
	
	/**
	 * 检测id
	 * @return
	 */
	private String infoId;
	
	/**
	 * 检测(项)类型
	 * @return
	 */
	private String infoName;
	
	/**
	 * 检测时间
	 * @retur
	 * n
	 */
	private String infoTime;
	
	
	
	private  int ycnum;
	
	private String ycStr;
	
	private String num1;
	private String num2;
	private String num3;
	private String num4;
	
	
	
	//结果result
	private String result;
	
	
	
	
	
	
	

	public int getYcnum() {
		return ycnum;
	}

	public void setYcnum(int ycnum) {
		this.ycnum = ycnum;
	}

	public String getYcStr() {
		return ycStr;
	}

	public void setYcStr(String ycStr) {
		this.ycStr = ycStr;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getNum1() {
		return num1;
	}

	public void setNum1(String num1) {
		this.num1 = num1;
	}

	public String getNum2() {
		return num2;
	}

	public void setNum2(String num2) {
		this.num2 = num2;
	}

	public String getNum3() {
		return num3;
	}

	public void setNum3(String num3) {
		this.num3 = num3;
	}

	public String getNum4() {
		return num4;
	}

	public void setNum4(String num4) {
		this.num4 = num4;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	public String getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(String infoTime) {
		this.infoTime = infoTime;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getTestNum() {
		return testNum;
	}

	public void setTestNum(String testNum) {
		this.testNum = testNum;
	}

	public String getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(String abnormal) {
		this.abnormal = abnormal;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
