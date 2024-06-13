package com.yihe.system.domain;

/**
 * 饮食日记管理
 * 
 * @author Administrator
 *
 */
public class YHUserFoodDiaryVOJO {
	private String usercode;
	private String username;// 用户账号
	private String name;// 用户姓名
	private Integer sex;// 用户姓名
	private String cTime;// 日记日期
	private String zao;// 早餐
	private String zhong;// 中餐
	private String wan;// 晚餐
	private String jia;// 加餐
	private String whether;// 是否过量
	private String felements;// 日记内容XX_XX_XX
	private Float energyLevel;
	
	
	
	public Float getEnergyLevel() {
		return energyLevel;
	}
	public void setEnergyLevel(Float energyLevel) {
		this.energyLevel = energyLevel;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getFelements() {
		return felements;
	}
	public void setFelements(String felements) {
		this.felements = felements;
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
	public String getcTime() {
		return cTime;
	}
	public void setcTime(String cTime) {
		this.cTime = cTime;
	}
	public String getZao() {
		return zao;
	}
	public void setZao(String zao) {
		this.zao = zao;
	}
	public String getZhong() {
		return zhong;
	}
	public void setZhong(String zhong) {
		this.zhong = zhong;
	}
	public String getWan() {
		return wan;
	}
	public void setWan(String wan) {
		this.wan = wan;
	}
	public String getJia() {
		return jia;
	}
	public void setJia(String jia) {
		this.jia = jia;
	}
	public String getWhether() {
		return whether;
	}
	public void setWhether(String whether) {
		this.whether = whether;
	}

}
