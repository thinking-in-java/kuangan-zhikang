package com.yihe.system.domain;

import java.io.Serializable;

public class YhExportUserUBA implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Org_Name;
	private String name;
	private Integer gender;
	private String User_Code;
	private String addDate;
	private String env;
	private String glucose;
	private String bilirubin;
	private String ketone;
	private String proportion;
	private String redBloodCell;
	private String ph;
	private String urineProtein;
	private String urobilinogen;
	private String nitrite;
	private String whiteBloodCell;
	public String getOrg_Name() {
		return Org_Name;
	}
	public void setOrg_Name(String org_Name) {
		Org_Name = org_Name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getUser_Code() {
		return User_Code;
	}
	public void setUser_Code(String user_Code) {
		User_Code = user_Code;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getGlucose() {
		return glucose;
	}
	public void setGlucose(String glucose) {
		this.glucose = glucose;
	}
	public String getBilirubin() {
		return bilirubin;
	}
	public void setBilirubin(String bilirubin) {
		this.bilirubin = bilirubin;
	}
	public String getKetone() {
		return ketone;
	}
	public void setKetone(String ketone) {
		this.ketone = ketone;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	public String getRedBloodCell() {
		return redBloodCell;
	}
	public void setRedBloodCell(String redBloodCell) {
		this.redBloodCell = redBloodCell;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getUrineProtein() {
		return urineProtein;
	}
	public void setUrineProtein(String urineProtein) {
		this.urineProtein = urineProtein;
	}
	public String getUrobilinogen() {
		return urobilinogen;
	}
	public void setUrobilinogen(String urobilinogen) {
		this.urobilinogen = urobilinogen;
	}
	public String getNitrite() {
		return nitrite;
	}
	public void setNitrite(String nitrite) {
		this.nitrite = nitrite;
	}
	public String getWhiteBloodCell() {
		return whiteBloodCell;
	}
	public void setWhiteBloodCell(String whiteBloodCell) {
		this.whiteBloodCell = whiteBloodCell;
	}
	@Override
	public String toString() {
		return "YhExportUserUBA [Org_Name=" + Org_Name + ", name=" + name + ", gender=" + gender + ", User_Code="
				+ User_Code + ", addDate=" + addDate + ", env=" + env + ", glucose=" + glucose + ", bilirubin="
				+ bilirubin + ", ketone=" + ketone + ", proportion=" + proportion + ", redBloodCell=" + redBloodCell
				+ ", ph=" + ph + ", urineProtein=" + urineProtein + ", urobilinogen=" + urobilinogen + ", nitrite="
				+ nitrite + ", whiteBloodCell=" + whiteBloodCell + "]";
	}
	
}
