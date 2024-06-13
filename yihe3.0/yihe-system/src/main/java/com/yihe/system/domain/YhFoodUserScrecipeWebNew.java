package com.yihe.system.domain;

import java.util.Date;
import java.util.List;

public class YhFoodUserScrecipeWebNew {
	private Integer uFoodErecipeId;

	private Date cTime;

	private Date startTime;

	private Integer stateFlag;

	private Integer srecipeId;

	private Integer isDel;

	private Integer eLevel;

	private String diseaseid;

	private String diseaseidStr;

	private Integer status;

	private Integer oneself;
	private String oneselfStr;

	private String userCode;
	private String updateUsercode;

	private String fElements;

	private String fElementsStr;
	
	
	private String doctor;
	
	
	private int[] diseaseidArray;
	
	private int page;
	private int limit;
	
	private List<YhFoodCookBook> zao;
	private List<YhFoodCookBook> wu;
	private List<YhFoodCookBook> wan;
	private List<YhFoodCookBook> jia;
	
	
	private int quanxuan;

	public int getQuanxuan() {
		return quanxuan;
	}

	public void setQuanxuan(int quanxuan) {
		this.quanxuan = quanxuan;
	}

	public String getUpdateUsercode() {
		return updateUsercode;
	}

	public void setUpdateUsercode(String updateUsercode) {
		this.updateUsercode = updateUsercode;
	}

	public List<YhFoodCookBook> getZao() {
		return zao;
	}

	public void setZao(List<YhFoodCookBook> zao) {
		this.zao = zao;
	}

	public List<YhFoodCookBook> getWu() {
		return wu;
	}

	public void setWu(List<YhFoodCookBook> wu) {
		this.wu = wu;
	}

	public List<YhFoodCookBook> getWan() {
		return wan;
	}

	public void setWan(List<YhFoodCookBook> wan) {
		this.wan = wan;
	}

	public List<YhFoodCookBook> getJia() {
		return jia;
	}

	public void setJia(List<YhFoodCookBook> jia) {
		this.jia = jia;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int[] getDiseaseidArray() {
		return diseaseidArray;
	}

	public void setDiseaseidArray(int[] diseaseidArray) {
		this.diseaseidArray = diseaseidArray;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getOneselfStr() {
		return oneselfStr;
	}

	public void setOneselfStr(String oneselfStr) {
		this.oneselfStr = oneselfStr;
	}

	public String getDiseaseidStr() {
		return diseaseidStr;
	}

	public void setDiseaseidStr(String diseaseidStr) {
		this.diseaseidStr = diseaseidStr;
	}

	public String getfElementsStr() {
		return fElementsStr;
	}

	public void setfElementsStr(String fElementsStr) {
		this.fElementsStr = fElementsStr;
	}

	public Integer getuFoodErecipeId() {
		return uFoodErecipeId;
	}

	public void setuFoodErecipeId(Integer uFoodErecipeId) {
		this.uFoodErecipeId = uFoodErecipeId;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getStateFlag() {
		return stateFlag;
	}

	public void setStateFlag(Integer stateFlag) {
		this.stateFlag = stateFlag;
	}

	public Integer getSrecipeId() {
		return srecipeId;
	}

	public void setSrecipeId(Integer srecipeId) {
		this.srecipeId = srecipeId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer geteLevel() {
		return eLevel;
	}

	public void seteLevel(Integer eLevel) {
		this.eLevel = eLevel;
	}

	public String getDiseaseid() {
		return diseaseid;
	}

	public void setDiseaseid(String diseaseid) {
		this.diseaseid = diseaseid == null ? null : diseaseid.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOneself() {
		return oneself;
	}

	public void setOneself(Integer oneself) {
		this.oneself = oneself;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode == null ? null : userCode.trim();
	}

	public String getfElements() {
		return fElements;
	}

	public void setfElements(String fElements) {
		this.fElements = fElements == null ? null : fElements.trim();
	}
}