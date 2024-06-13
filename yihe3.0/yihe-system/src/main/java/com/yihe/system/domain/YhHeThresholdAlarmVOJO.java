package com.yihe.system.domain;

import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class YhHeThresholdAlarmVOJO {
	private Integer id;
	private String usercode;
	private String name;
	private String terminalId;
	private String type;
	private String typename;
	private String typenum;
	private String content;
	private String status;
	private String statusid;
	private String time;
	private String statusnum;
	private String sex;
	private Date birthDate;
	private String mobile;
	private String devicename;
	
	private String sosNos;
	private int age;
	
	//受理人
	private String acceptUsercode;
	private String acceptUsername;
	//受理时间
	private String acceptTime;
	//受理情况
	private String acceptInfo;
	//归档时间
	private String archiveTime;
	
	
	
	public String getAcceptUsername() {
		return acceptUsername;
	}
	public void setAcceptUsername(String acceptUsername) {
		this.acceptUsername = acceptUsername;
	}
	public String getAcceptUsercode() {
		return acceptUsercode;
	}
	public void setAcceptUsercode(String acceptUsercode) {
		this.acceptUsercode = acceptUsercode;
	}
	public String getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getAcceptInfo() {
		return acceptInfo;
	}
	public void setAcceptInfo(String acceptInfo) {
		this.acceptInfo = acceptInfo;
	}
	public String getArchiveTime() {
		return archiveTime;
	}
	public void setArchiveTime(String archiveTime) {
		this.archiveTime = archiveTime;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getSosNos() {
		return sosNos;
	}
	public void setSosNos(String sosNos) {
		this.sosNos = sosNos;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getStatusid() {
		return statusid;
	}
	public void setStatusid(String statusid) {
		this.statusid = statusid;
	}
	public String getStatusnum() {
		return statusnum;
	}
	public void setStatusnum(String statusnum) {
		this.statusnum = statusnum;
	}
	public String getTypenum() {
		return typenum;
	}
	public void setTypenum(String typenum) {
		this.typenum = typenum;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "YhHeThresholdAlarmVOJO [id=" + id + ", status=" + status + ", acceptUsercode=" + acceptUsercode
				+ ", acceptTime=" + acceptTime + ", acceptInfo=" + acceptInfo + ", archiveTime=" + archiveTime + "]";
	}
	
	
	
	
}
