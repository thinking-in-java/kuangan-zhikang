package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
//yh_disease_risk_result
public class YhExportRiskResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer rid;
	private String diseaseId;
	private String user_code;
	private String request_serialNum;
	private String qname;
	private String qcode;
	private String qvalue;
	private String last_value;
	private String changes;
	private String optimal;
	private String remark;
	private Date qtime;
	private Integer isRadar;
	private String request_serialize;
	private List<YhExportAssessReport> assessreport;
	
	
	public List<YhExportAssessReport> getAssessreport() {
		return assessreport;
	}
	public void setAssessreport(List<YhExportAssessReport> assessreport) {
		this.assessreport = assessreport;
	}
	public String getRequest_serialize() {
		return request_serialize;
	}
	public void setRequest_serialize(String request_serialize) {
		this.request_serialize = request_serialize;
	}
	private List<YhExportAssessReport> reports;
	
	public List<YhExportAssessReport> getReports() {
		return reports;
	}
	public void setReports(List<YhExportAssessReport> reports) {
		this.reports = reports;
	}

	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public String getDiseaseId() {
		return diseaseId;
	}
	public void setDiseaseId(String diseaseId) {
		this.diseaseId = diseaseId;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	
	public String getQname() {
		return qname;
	}

	public String getRequest_serialNum() {
		return request_serialNum;
	}
	public void setRequest_serialNum(String request_serialNum) {
		this.request_serialNum = request_serialNum;
	}
	public void setQname(String qname) {
		this.qname = qname;
	}
	

	public String getQcode() {
		return qcode;
	}
	public void setQcode(String qcode) {
		this.qcode = qcode;
	}
	public String getQvalue() {
		return qvalue;
	}
	public void setQvalue(String qvalue) {
		this.qvalue = qvalue;
	}
	public String getLast_value() {
		return last_value;
	}
	public void setLast_value(String last_value) {
		this.last_value = last_value;
	}
	public String getChanges() {
		return changes;
	}
	public void setChanges(String changes) {
		this.changes = changes;
	}
	public String getOptimal() {
		return optimal;
	}
	public void setOptimal(String optimal) {
		this.optimal = optimal;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getQtime() {
		return qtime;
	}
	public void setQtime(Date qtime) {
		this.qtime = qtime;
	}
	public Integer getIsRadar() {
		return isRadar;
	}
	public void setIsRadar(Integer isRadar) {
		this.isRadar = isRadar;
	}



	
}
