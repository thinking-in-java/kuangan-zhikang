package com.yihe.system.domain;

public class YhFoodSrecipe {
	private Integer id;

	private Integer dType;

	private Integer eLevel;

	private Integer genter;

	private Integer isUse;

	private Integer packageNumber;

	private Integer isDel;

	private String diseaseid;

	private String fElements;

	private String name;

	private String employ;

	private int chang;

	private Integer oneself;
	private String oneselfStr;

	private String fsDescribe;

	private String muxin;
	
	private String doctor;
	private String usercode;
	
	private String creationTime;
	
	
	private Integer ftype;
	
	private String diseaseName;
	
	private int yhFoodSrecipeNum;
	
	private int quanxuan;

	public int getQuanxuan() {
		return quanxuan;
	}

	public void setQuanxuan(int quanxuan) {
		this.quanxuan = quanxuan;
	}

	public int getYhFoodSrecipeNum() {
		return yhFoodSrecipeNum;
	}

	public void setYhFoodSrecipeNum(int yhFoodSrecipeNum) {
		this.yhFoodSrecipeNum = yhFoodSrecipeNum;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public Integer getFtype() {
		return ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}

	public String getOneselfStr() {
		return oneselfStr;
	}

	public void setOneselfStr(String oneselfStr) {
		this.oneselfStr = oneselfStr;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getMuxin() {
		return muxin;
	}

	public void setMuxin(String muxin) {
		this.muxin = muxin;
	}

	public String getFsDescribe() {
		return fsDescribe;
	}

	public void setFsDescribe(String fsDescribe) {
		this.fsDescribe = fsDescribe;
	}

	public Integer getOneself() {
		return oneself;
	}

	public void setOneself(Integer oneself) {
		this.oneself = oneself;
	}

	public int getChang() {
		return chang;
	}

	public void setChang(int chang) {
		this.chang = chang;
	}

	public String getEmploy() {
		return employ;
	}

	public void setEmploy(String employ) {
		this.employ = employ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getdType() {
		return dType;
	}

	public void setdType(Integer dType) {
		this.dType = dType;
	}

	public Integer geteLevel() {
		return eLevel;
	}

	public void seteLevel(Integer eLevel) {
		this.eLevel = eLevel;
	}

	public Integer getGenter() {
		return genter;
	}

	public void setGenter(Integer genter) {
		this.genter = genter;
	}

	public Integer getIsUse() {
		return isUse;
	}

	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}

	public Integer getPackageNumber() {
		return packageNumber;
	}

	public void setPackageNumber(Integer packageNumber) {
		this.packageNumber = packageNumber;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getDiseaseid() {
		return diseaseid;
	}

	public void setDiseaseid(String diseaseid) {
		this.diseaseid = diseaseid;
	}

	public String getfElements() {
		return fElements;
	}

	public void setfElements(String fElements) {
		this.fElements = fElements == null ? null : fElements.trim();
	}
}