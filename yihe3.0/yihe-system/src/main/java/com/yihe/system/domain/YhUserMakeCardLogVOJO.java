package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhUserMakeCardLogVOJO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer Id;
	private Date Marke_Ttime;
	private Integer Marke_Amount;
	private String Begin_Number;
	private String End_Number;
	private Integer Card_Type;
	private String Marke_Note;
	private String Org_Code;
	private String Log_Type;
	private String old_file_name;
	private String upload_content;
	private String Org_Name;
	private Integer remaining;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Date getMarke_Ttime() {
		return Marke_Ttime;
	}
	public void setMarke_Ttime(Date marke_Ttime) {
		Marke_Ttime = marke_Ttime;
	}
	public Integer getMarke_Amount() {
		return Marke_Amount;
	}
	public void setMarke_Amount(Integer marke_Amount) {
		Marke_Amount = marke_Amount;
	}
	public String getBegin_Number() {
		return Begin_Number;
	}
	public void setBegin_Number(String begin_Number) {
		Begin_Number = begin_Number;
	}
	public String getEnd_Number() {
		return End_Number;
	}
	public void setEnd_Number(String end_Number) {
		End_Number = end_Number;
	}
	public Integer getCard_Type() {
		return Card_Type;
	}
	public void setCard_Type(Integer card_Type) {
		Card_Type = card_Type;
	}
	public String getMarke_Note() {
		return Marke_Note;
	}
	public void setMarke_Note(String marke_Note) {
		Marke_Note = marke_Note;
	}
	public String getOrg_Code() {
		return Org_Code;
	}
	public void setOrg_Code(String org_Code) {
		Org_Code = org_Code;
	}
	public String getLog_Type() {
		return Log_Type;
	}
	public void setLog_Type(String log_Type) {
		Log_Type = log_Type;
	}
	public String getOld_file_name() {
		return old_file_name;
	}
	public void setOld_file_name(String old_file_name) {
		this.old_file_name = old_file_name;
	}
	public String getUpload_content() {
		return upload_content;
	}
	public void setUpload_content(String upload_content) {
		this.upload_content = upload_content;
	}
	public String getOrg_Name() {
		return Org_Name;
	}
	public void setOrg_Name(String org_Name) {
		Org_Name = org_Name;
	}
	public Integer getRemaining() {
		return remaining;
	}
	public void setRemaining(Integer remaining) {
		this.remaining = remaining;
	}
	@Override
	public String toString() {
		return "YhUserMakeCardLogVOJO [Id=" + Id + ", Marke_Ttime=" + Marke_Ttime + ", Marke_Amount=" + Marke_Amount
				+ ", Begin_Number=" + Begin_Number + ", End_Number=" + End_Number + ", Card_Type=" + Card_Type
				+ ", Marke_Note=" + Marke_Note + ", Org_Code=" + Org_Code + ", Log_Type=" + Log_Type
				+ ", old_file_name=" + old_file_name + ", upload_content=" + upload_content + ", Org_Name=" + Org_Name
				+ ", remaining=" + remaining + "]";
	}
	
}
