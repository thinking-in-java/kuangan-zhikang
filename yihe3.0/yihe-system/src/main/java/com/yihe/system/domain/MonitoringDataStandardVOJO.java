package com.yihe.system.domain;
/**
 * 数据解读返回实体
 * @author Administrator
 *
 */
public class MonitoringDataStandardVOJO {
//监测项
	private String typeName;
	//状态
	private String state;
	//参数条件
	private String parameter1;
	private String parameter2;
	private String parameter3;
	private String parameter4;
	//数据解读内容
	private String unscramble;
	
	
	//是否异常 0为正常 1 为异常
	private int abnormal;


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	 

	public String getParameter1() {
		return parameter1;
	}


	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}


	public String getParameter2() {
		return parameter2;
	}


	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}


	public String getParameter3() {
		return parameter3;
	}


	public void setParameter3(String parameter3) {
		this.parameter3 = parameter3;
	}


	public String getParameter4() {
		return parameter4;
	}


	public void setParameter4(String parameter4) {
		this.parameter4 = parameter4;
	}


	public String getUnscramble() {
		return unscramble;
	}


	public void setUnscramble(String unscramble) {
		this.unscramble = unscramble;
	}


	public int getAbnormal() {
		return abnormal;
	}


	public void setAbnormal(int abnormal) {
		this.abnormal = abnormal;
	}
	
	
	

}
